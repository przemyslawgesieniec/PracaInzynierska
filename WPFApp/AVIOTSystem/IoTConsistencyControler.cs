using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Timers;
using System.Windows;
using System.Windows.Threading;

namespace AVIOTSystem
{
    public class IoTConsistencyControler
    {
        private List<IPAddress> connectedDevicesList;

        public delegate void IoTConsistencyControlerEventHandler(object source, IoTConsistencyControlerEventArgs args);
        public event IoTConsistencyControlerEventHandler IoTDeviceConnected;

        public IoTConsistencyControler()
        {
            connectedDevicesList = new List<IPAddress>();
        }

        public List<IPAddress> ConnectedDevicesList { get => connectedDevicesList; set => connectedDevicesList = value; }

        public void SearchForConnectedDevices()
        {
            int PORT = 11000;   
            UdpClient udpClient = new UdpClient();
            IPEndPoint ep = new IPEndPoint(IPAddress.Any, PORT);
            udpClient.Client.Bind(ep);
           // udpClient.Connect(ep);
            var data = Encoding.UTF8.GetBytes("openPortMsg");
            udpClient.Send(data, data.Length, "255.255.255.255", PORT);

            var sender = new IPEndPoint(0, 0);
            Task.Run(() =>
            {
                while (true)
                {
                    var recvBuffer = udpClient.Receive(ref sender);
                    if(Encoding.UTF8.GetString(recvBuffer) == "AttachRequest")
                    {
                        if(CheckForDuplicates(sender.Address))
                        {
                            connectedDevicesList.Add(sender.Address);
                            OnIoTDeviceConnected(sender.Address, "IoTDevice " + connectedDevicesList.Count);
                            SendConnectionConfirmation(sender.Address);
                        }
                    }    
                }
            });
        }
        protected virtual void OnIoTDeviceConnected(IPAddress ip, String name)
        {
            IoTDeviceConnected?.Invoke(this, new IoTConsistencyControlerEventArgs() { Address = ip, ModuleName = name} );//invoke check if there is any subscriber for the event
        }
        protected bool CheckForDuplicates(IPAddress senderAddr)
        {
            foreach (var ip in connectedDevicesList)
                if (ip.Equals(senderAddr)) return false;
            return true;
        }
        private void SendConnectionConfirmation(IPAddress recentlyConnectedDeviceAdress)
        {
            var client = new UdpClient();
            IPEndPoint ep = new IPEndPoint(recentlyConnectedDeviceAdress, 2390);
            client.Connect(ep);
            client.Send(Encoding.ASCII.GetBytes("YouAreConnected"), Encoding.ASCII.GetByteCount("YouAreConnected"));
        }
        //private void VerifyAllAttachedDevicesStatus()
        //{
        //    Task.Run(() =>
        //    {
        //        Timer t = new Timer(60000); // 1 sec = 1000, 60 sec = 60000
        //        t.AutoReset = true;
        //        t.Elapsed += new ElapsedEventHandler(SendSyncMsg);
        //        t.Start();
        //    });
        //}
        
        //private void SendSyncMsg(object sender, ElapsedEventArgs e)
        //{

        //   // Dispatcher.Invoke(() => { WiFiItemList.Children.Add(new ViewResouces.HwWiFiModuleInstance(args.Address, args.ModuleName)); });
        //}
      //  private void SendPrivateMessage(IPAddress deviceAdress, string MessageType)
}
}
