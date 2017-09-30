using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace AVIOT.ViewResouces
{
    /// <summary>
    /// Interaction logic for HwWiFiModuleInstance.xaml
    /// </summary>
    public partial class HwWiFiModuleInstance : UserControl
    {
        public HwWiFiModuleInstance()
        {
            InitializeComponent();
        }

        private void TriggerButton_Click(object sender, RoutedEventArgs e)
        {

            var client = new UdpClient();
            IPEndPoint ep = new IPEndPoint(IPAddress.Parse("192.168.43.235"), 2390); // endpoint where server is listening
            client.Connect(ep);
            if(TriggerButton.Content.ToString() == "OFF")
            {
                client.Send(new byte[] { 97, 98, 99, 100, 101 }, 5);
                TriggerButton.Content = "ON";
                TriggerButton.Background = Brushes.LightSeaGreen;
            }
            else
            {
                client.Send(new byte[] { 101, 100, 99, 98, 97 }, 5);
                TriggerButton.Content = "OFF";
                TriggerButton.Background = Brushes.MediumVioletRed;
               
            }
            var receivedData = client.Receive(ref ep);
            MessageBox.Show(receivedData.ToString());
            client.Close();
        }
    }
}
