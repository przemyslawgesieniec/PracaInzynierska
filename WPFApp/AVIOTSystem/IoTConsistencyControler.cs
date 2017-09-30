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
    class IoTConsistencyControler
    {
        public IoTConsistencyControler()
        {

        }

        public void FindAllConnectedIoTDevices()
        {
            UdpClient client = new UdpClient();
            IPEndPoint ip = new IPEndPoint(IPAddress.Broadcast, 15000);
            MessageBox.Show(IPAddress.Broadcast.ToString());
            //IPEndPoint ip = new IPEndPoint(IPAddress.Parse("192.168.43.235"), 2390);
            byte[] bytes = Encoding.ASCII.GetBytes("bbb");
            client.Send(bytes, bytes.Length, ip);
            client.Close();
        }
        public void SearchForConnectedDevices()
        {

            int PORT = 11000;
            UdpClient udpClient = new UdpClient();
            IPEndPoint ep = new IPEndPoint(IPAddress.Any, PORT);

            udpClient.Client.Bind(ep);

            var data = Encoding.UTF8.GetBytes("openPortMsg");
            udpClient.Send(data, data.Length, "255.255.255.255", PORT);


            var from = new IPEndPoint(0, 0);
            Task.Run(() =>
            {
                while (true)
                {
                    var recvBuffer = udpClient.Receive(ref from);
                    MessageBox.Show(Encoding.UTF8.GetString(recvBuffer) + "from" + from.Address.ToString());
                }
            });

           



        }
    }
}
