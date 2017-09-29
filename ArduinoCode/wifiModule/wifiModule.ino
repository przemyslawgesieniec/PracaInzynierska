
#include <ESP8266WiFi.h>
#include <WiFiUdp.h> 

const char IPD[] PROGMEM = "IPD,";
const char READY[] PROGMEM = "ready";

byte readCommand(int timeout, const char* text1 = NULL, const char* text2 = NULL);
unsigned int localPort = 2390;

const char* ssid = "PENTAGRAM_P6362";
const char* pass = "#mopsik123";
//const char* ssid = "G4c";
//const char* pass = "prosiaczek";

//char packetBuffer[255]; //buffer to hold incoming packet
char  ReplyBuffer[] = "acknowledged";       // a string to send back


WiFiUDP Udp;


void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(2, OUTPUT);
  digitalWrite(2, LOW); 
  Serial.begin(115200);
  Serial.println("Starting module ESP01-0");

  WiFi.begin(ssid, pass);
  while (WiFi.waitForConnectResult() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(100);
  }
  Serial.println("");
  Serial.println("Device is connected to the network");


  IPAddress local_ip = WiFi.localIP();
  Serial.print("IP address: ");
  Serial.println(local_ip);
  Udp.begin(localPort);
  Serial.print("UDP begun");
}

void loop() {
  if (WiFi.status() == WL_CONNECTED)
  {
    String msg = receiveUDPPacket();
    if(msg=="abcde")
    {
      Serial.println("Wlaczam swiatlo");
      msg="";
      digitalWrite(2, HIGH); 
      SendAReply();
    }
    if(msg=="edcba")
    {
      Serial.println("Gasze swiatlo");
      msg="";
      digitalWrite(2, LOW); 
      SendAReply();
    }
  }
  else
  {
     Serial.println(WiFi.status());
  }
}

String receiveUDPPacket()
{
  int packetSize = Udp.parsePacket();
  char packetBuffer[255] = {};
    if (packetSize) {
      Serial.print("Received packet of size ");
      Serial.println(packetSize);
      Serial.print("From ");
      IPAddress remoteIp = Udp.remoteIP();
      Serial.print(remoteIp);
      Serial.print(", port ");
      Serial.println(Udp.remotePort());

      // read the packet into packetBufffer
      int len = Udp.read(packetBuffer, 255);
      if (len > 0) {
        packetBuffer[len] = 0;
      }
      Serial.println("Contents: ");
      Serial.println(packetBuffer);
    }
  return packetBuffer;
}
void SendAReply()
{
    Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
    Serial.println(ReplyBuffer);
    Udp.write(ReplyBuffer);
    Udp.endPacket();
}

