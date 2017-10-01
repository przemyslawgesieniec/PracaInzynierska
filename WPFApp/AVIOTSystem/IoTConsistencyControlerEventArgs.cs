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
        public IPAddress Address { get; set; }
        public String ModuleName { get; set; }
    }
}
