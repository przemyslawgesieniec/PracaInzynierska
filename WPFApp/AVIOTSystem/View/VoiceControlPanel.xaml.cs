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
    /// Interaction logic for VoiceControlPanel.xaml
    /// </summary>
    public partial class VoiceControlPanel : UserControl
    {
        private static VoiceControlPanel _instance;
        public static VoiceControlPanel Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new VoiceControlPanel();
                }
                return _instance;
            }
        }
        public VoiceControlPanel()
        {
            InitializeComponent();
        }
    }
}
