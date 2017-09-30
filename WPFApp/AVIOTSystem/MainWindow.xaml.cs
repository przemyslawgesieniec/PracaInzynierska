using AVIOTSystem;
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


        }

        private void Btn_AddNewModule_Click(object sender, RoutedEventArgs e)
        {
            WiFiItemList.Children.Add(new ViewResouces.HwWiFiModuleInstance());
        }

        private void Btn_VerifyStatus_Click(object sender, RoutedEventArgs e)
        {
            IoTConsistencyControler consistencyHandler = new IoTConsistencyControler();
            //consistencyHandler.FindAllConnectedIoTDevices();
            consistencyHandler.SearchForConnectedDevices();
        }
    }
}
