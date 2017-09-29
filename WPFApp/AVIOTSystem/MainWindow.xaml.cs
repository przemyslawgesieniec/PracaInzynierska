using System.Windows;

namespace AVIOT
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();



        }

        private void Btn_AddNewModule_Click(object sender, RoutedEventArgs e)
        {
            WiFiItemList.Children.Add(new ViewResouces.HwWiFiModuleInstance());
        }

        private void Btn_VerifyStatus_Click(object sender, RoutedEventArgs e)
        {

        }
    }
}
