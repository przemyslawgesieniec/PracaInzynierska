using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace AVIOTSystem
{
    public class IoTConsistencyControlerEventArgs
    {
        public IPAddress address { get; set; }
        public String moduleName { get; set; }
    }
}
