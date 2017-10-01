
#include <ESP8266WiFi.h>
#include <WiFiUdp.h> 

const char IPD[] PROGMEM = "IPD,";
const char READY[] PROGMEM = "ready";

byte readCommand(int timeout, const char* text1 = NULL, const char* text2 = NULL);
unsigned int localPort = 2390;


//const char* ssid = "PENTAGRAM_P6362";
//const char* pass = "#mopsik123";
const char* ssid = "G4c";
const char* pass = "prosiaczek";

//char packetBuffer[255]; //buffer to hold incoming packet
//char  ReplyBuffer[] = "acknowledged";       // a string to send back
//char * ReplyBuffer;
String ReplyBuffer = "empty";

IPAddress broadcastIp(255,255,255,255);

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
  SendPresenceMessage();
}

void loop() {
  if (WiFi.status() == WL_CONNECTED)
  {
    String msg = receiveUDPPacket();
    if(msg=="abcde")
    {
      msg="";
      digitalWrite(2, HIGH); 
      ReplyBuffer = "MsgONRcvd";   
      SendAReply(); 
    }
    if(msg=="edcba")
    {
      msg="";
      digitalWrite(2, LOW); 
      ReplyBuffer = "MsgOFFRcvd";
      SendAReply();
    }
    if(msg == "bbb")
    {
      Serial.println("Otrzymalem msg z broadcasu HEHE");
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
    int replyMsgLenght = ReplyBuffer.length();
    char *replyMsg = new char[replyMsgLenght];
    ReplyBuffer.toCharArray(replyMsg,replyMsgLenght);
    Udp.write(replyMsg);
    delete [] replyMsg;
    ReplyBuffer= "empty";
    Udp.endPacket();
}
void SendPresenceMessage()
{
  char presenceMsg[] = "ESP8266";    
  Serial.println("Send presence message");
  Udp.beginPacket(broadcastIp, 11000);
  Udp.write(presenceMsg);
  Udp.endPacket();
}
//void StringToCharTableConverter
