using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace AVIOTSystem
{
    public enum MessaeIn
    {
        UNDEFINED_OR_NOT_RECEIVED,
        ON_ACK = 10,
        OFF_ACK,
        PIN_STATUS_ON,
        PIN_STATUS_OFF,
        ATTACH_REQUEST
    }
    public enum MessaeOut
    {
        SET_PIN_ON = 10,
        SET_PIN_OFF,
        PIN_STATUS_REQUEST,
        CONNECTION_CONFIRMATION,
        APPLICATION_ONLINE_MESSAGE,
        APPLICATION_SHUTTING_DOWN
    }
    static class MessageHandler
    {
        public static void SendNoReplyPrivateMsg(IPAddress deviceAdress, int port, Enum message)
        {
            var client = new UdpClient();
            IPEndPoint ep = new IPEndPoint(deviceAdress, port);
            client.Connect(ep);
            client.Send(Encoding.ASCII.GetBytes(message.ToString()), Encoding.ASCII.GetByteCount(message.ToString()));
        }
        public static void SendMsgToBroadcast(Enum message)
        {
            int PORT = 11000;
            UdpClient udpClient = new UdpClient();
            IPEndPoint ep = new IPEndPoint(IPAddress.Any, PORT);
            udpClient.Client.Bind(ep);
            var data = Encoding.UTF8.GetBytes(message.ToString());
            udpClient.Send(data, data.Length, "255.255.255.255", PORT);
        }
        /// <summary>
        /// Using connection-oriented protocol to send, and receive message via UDP protocol
        /// </summary>
        /// <param name="deviceAdress"></param>
        /// <param name="port"></param>
        /// <param name="message"></param>
        /// <returns></returns>
        public static MessaeIn SendMsgAndReciveConfirmation(IPAddress deviceAdress, int port, MessaeOut message)
        {
            var client = new UdpClient();
            IPEndPoint endPoint = new IPEndPoint(deviceAdress, port);
            client.Connect(endPoint);
            client.Send(Encoding.ASCII.GetBytes(((int)message).ToString()), Encoding.ASCII.GetByteCount(((int)message).ToString()));
            var receivedData = client.Receive(ref endPoint);
            client.Close();
            int receivedMsg = 0;
          
            if (int.TryParse(Encoding.UTF8.GetString(receivedData), out receivedMsg))
                return (MessaeIn)receivedMsg;
            return MessaeIn.UNDEFINED_OR_NOT_RECEIVED;

        }
        public static void ReceiveMsg(MessaeIn msgType, int port)
        {
            //switch (msgType)
            //{
            //    case MessaeIn.ON_ACK:
            //        break;
            //    case MessaeIn.OFF_ACK:
            //        break;
            //    case MessaeIn.PIN_STATUS_ON:
            //        break;
            //    case MessaeIn.PIN_STATUS_OFF:
            //        break;
            //    case MessaeIn.ATTACH_REQUEST:
            //        break;
            //}
            //MessageBox.Show()
        }
        public static void ReceiveMsg(String msg)
        {
            MessageBox.Show(msg);
        }
    }
}
