using System;
using System.Collections.Generic;
using System.Linq;
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

namespace AVIOT.View
{
    /// <summary>
    /// Interaction logic for ManualControlPanel.xaml
    /// </summary>
    public partial class ManualControlPanel : UserControl
    {
        private static ManualControlPanel _instance;
        public static ManualControlPanel Instance
        {
            get
            {
                if(_instance ==null)
                {
                    _instance = new ManualControlPanel();
                }
                return _instance;
            }
        }
        public ManualControlPanel()
        {
            InitializeComponent();
        }
    }
}
