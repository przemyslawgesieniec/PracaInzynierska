using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

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

        //public void FindAllConnectedIoTDevices()
        //{
        //    UdpClient client = new UdpClient();
        //    IPEndPoint ip = new IPEndPoint(IPAddress.Broadcast, 15000);
        //    MessageBox.Show(IPAddress.Broadcast.ToString());
        //    byte[] bytes = Encoding.ASCII.GetBytes("bbb");
        //    client.Send(bytes, bytes.Length, ip);
        //    client.Close();
        //}
        public void SearchForConnectedDevices()
        {
            int PORT = 11000;   
            UdpClient udpClient = new UdpClient();
            IPEndPoint ep = new IPEndPoint(IPAddress.Any, PORT);
            udpClient.Client.Bind(ep);
            var data = Encoding.UTF8.GetBytes("openPortMsg");
            udpClient.Send(data, data.Length, "255.255.255.255", PORT);

            var sender = new IPEndPoint(0, 0);
            Task.Run(() =>
            {
                while (true)
                {
                    var recvBuffer = udpClient.Receive(ref sender);
                    if(Encoding.UTF8.GetString(recvBuffer) == "ESP8266")
                    {
                        if(CheckForDuplicates(sender.Address))
                        {
                            connectedDevicesList.Add(sender.Address);
                            OnIoTDeviceConnected(sender.Address, "IoTDevice");
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
            {
                if (ip == senderAddr)
                {
                    return false;
                }
            }
            return true;
        }
        
    }
}
