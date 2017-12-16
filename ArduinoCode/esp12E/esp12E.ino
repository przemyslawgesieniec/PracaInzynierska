#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

#include "CommonDevice.h"
#include "LightSwitch.h"
#include "ConnectionHandler.h"

const char IPD[] PROGMEM = "IPD,";
const char READY[] PROGMEM = "ready";

//unsigned long oldTimerStatus = 0;
//const int attachRequestResendTimer = 4000;
bool switchState = false;

byte readCommand(int timeout, const char* text1 = NULL, const char* text2 = NULL);
//

//String mac = "";
//String deviceType = "switch";
//String messageType;

unsigned int broadcastPort = 11000;
unsigned int localPort = 2390;

const char* ssid = "DESKTOP_WIFI";
const char* pass = "przemek123";

//const char* ssid = "PENTAGRAM_P6362";
//const char* pass = "#mopsik123";
//
//const char* ssid = "PLAY-ONLINE-8763";
//const char* pass = "G2TTT9D5";

//String ReplyBuffer = "empty";

//IPAddress broadcastIp(192, 168, 137, 255); //TODO: zmienic zevby sam ustawial w zaleznosci od maski sieci

WiFiUDP Udp;
ConnectionHandler handler;
CommonDevice *device;

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
 // pinMode(2, OUTPUT);
  pinMode(13, OUTPUT);
  digitalWrite(13,LOW);
  //digitalWrite(2, LOW);
  Serial.begin(115200);
  Serial.println("");
  Serial.println("Starting module ESP12e");

  handler = ConnectionHandler(ssid, pass, localPort, broadcastPort);

  WiFi.begin(ssid, pass);
  while (WiFi.waitForConnectResult() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(100);
  }
  Serial.println("");
  Serial.println("Device is connected to the network");

//  unsigned char mac_char[6];
//  WiFi.macAddress(mac_char);
//  mac += macToStr(mac_char);

  Serial.print("MAC:");
  Serial.println(handler.getMac());
  
  IPAddress local_ip = WiFi.localIP();
  Serial.print("IP address: ");
  Serial.println(local_ip);
//  Udp.begin(localPort);
  Serial.print("UDP begun");
  device = new LightSwitch("light","kitchen",handler.getMac(),false,13);
  handler.waitForApplicationAttach(device);
}

void loop() {
  handler.handleIncomingMessages(device);
}

//String receiveUDPPacket()
//{
//  int packetSize = Udp.parsePacket();
//  char packetBuffer[255] = {};
//  if (packetSize) {
//    Serial.print("Received packet of size ");
//    Serial.println(packetSize);
//    Serial.print("From ");
//    IPAddress remoteIp = Udp.remoteIP();
//    Serial.print(remoteIp);
//    Serial.print(", port ");
//    Serial.println(Udp.remotePort());
//
//    // read the packet into packetBufffer
//    int len = Udp.read(packetBuffer, 255);
//    if (len > 0) {
//      packetBuffer[len] = 0;
//    }
//    Serial.println("Contents: ");
//    Serial.println(packetBuffer);
//  }
//  return packetBuffer;
//}
//void SendAReply()
//{
//  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
//  Serial.print("reply msg: ");
//  Serial.println(ReplyBuffer);
//  int replyMsgLenght = ReplyBuffer.length();
//  Serial.print("replyMsgLenght:");
//  Serial.println(replyMsgLenght);
//  char *replyMsg = new char[replyMsgLenght];
//  ReplyBuffer.toCharArray(replyMsg, replyMsgLenght + 1);
//  Serial.println(replyMsg);
//  Serial.println(*replyMsg);
//
//  Udp.write(replyMsg);
//  delete [] replyMsg;
//  ReplyBuffer = "empty";
//  Udp.endPacket();
//}
//
//void SendAttachRequest()
//{
//  char attachRequestMsg[] = "AttachRequest";
//  Serial.println("Send AttachRequest");
//  Udp.beginPacket(broadcastIp, broadcastPort);
//  Udp.write(attachRequestMsg);
//  Udp.endPacket();
//}
//void WaitForApplicationAttach()
//{
//  while (true)
//  {
//    unsigned long currentTimerStatus = millis();
//    if (currentTimerStatus - oldTimerStatus >= attachRequestResendTimer)
//    {
//      oldTimerStatus = currentTimerStatus;
//      SendAttachRequest();
//    }
//    String msg = receiveUDPPacket();
//    if (msg == "CapabilityRequest")
//    {
//      messageType = "capabilities";
//      msg = "";
//      SendDeviceCapabilities();
//      HardwareSignalizeConnection();
//      break;
//    }
//  }
//}

//void HardwareSignalizeConnection()
//{
//  Serial.println("Device attached to application correctly");
//  for (int i = 0; i < 3; i++)
//  {
//    digitalWrite(LED_BUILTIN, HIGH);
//    delay(100);
//    digitalWrite(LED_BUILTIN, LOW);
//    delay(100);
//  }
//}

//void SendDeviceCapabilities()
//{
//  String caps = getCapabilities();
//  const char *capabilityMsg = caps.c_str();
//  Serial.println("Send Capabilities");
//  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
//  Udp.write(capabilityMsg);
//  Udp.endPacket();
//}

//String macToStr(const uint8_t* mac)
//{
//  String result;
//  for (int i = 0; i < 6; ++i) {
//    result += String(mac[i], 16);
//    if (i < 5)
//      result += ':';
//  }
//  return result;
//}
//String boolToString(bool value)
//{
//  return value ? "true" : "false";
//}
//String getCapabilities()
//{
//  String capabilities = deviceType; 
//  capabilities += ";"+messageType;
//  capabilities += ";"+mac;
//  capabilities += ";1";
//  capabilities += ";"+boolToString(switchState);
//  return capabilities;
//}




