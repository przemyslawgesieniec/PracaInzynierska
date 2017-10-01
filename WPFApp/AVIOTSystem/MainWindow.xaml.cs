using AVIOTSystem;
using System;
using System.Windows;

namespace AVIOT
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        IoTConsistencyControler consistencyControler;
        public MainWindow()
        {
            InitializeComponent();
            consistencyControler = new IoTConsistencyControler();
            consistencyControler.SearchForConnectedDevices();

            consistencyControler.IoTDeviceConnected += ConsistencyControler_IoTDeviceConnected;
        }

        private void ConsistencyControler_IoTDeviceConnected(object source, IoTConsistencyControlerEventArgs args)
        {
           Dispatcher.Invoke( () => { WiFiItemList.Children.Add(new ViewResouces.HwWiFiModuleInstance(args.Address, args.ModuleName)); } );
        }

        private void Btn_AddNewModule_Click(object sender, RoutedEventArgs e)
        {

        }

        private void Btn_VerifyStatus_Click(object sender, RoutedEventArgs e)
        {

        }
    }
}
