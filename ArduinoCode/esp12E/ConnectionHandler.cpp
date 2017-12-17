#include "ConnectionHandler.h"

ConnectionHandler::ConnectionHandler(const char * ssid, const char * pass, unsigned int localPort, unsigned int broadcastPort)
{
  this->localPort = localPort;
  this->broadcastPort = broadcastPort;
  this->pass = pass;
  this->ssid = ssid;
  setMac();


  WiFi.begin(ssid, pass);
  while (WiFi.waitForConnectResult() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(100);
  }
}

ConnectionHandler::ConnectionHandler()
{
}
ConnectionHandler::~ConnectionHandler()
{
}

void ConnectionHandler::connectToWiFi()
{

}


//
//GETTERS
//
String ConnectionHandler::getReplyBuffer()
{
  return replyBuffer;
}

//
//SETTERS
//
void ConnectionHandler::setReplyBuffer(String replyBuffer)
{
  this->replyBuffer = replyBuffer;
}

//
//METHODS
//
String ConnectionHandler::macToStr(const uint8_t* mac)
{
  String result;
  for (int i = 0; i < 6; ++i) {
    result += String(mac[i], 16);
    if (i < 5)
      result += ':';
  }
  return result;
}

void ConnectionHandler::waitForApplicationAttach(CommonDevice *device)
{
  unsigned long oldTimerStatus = 0;
  const int attachRequestResendTimer = 4000;

  while (true)
  {
    unsigned long currentTimerStatus = millis();
    if (currentTimerStatus - oldTimerStatus >= attachRequestResendTimer)
    {
      oldTimerStatus = currentTimerStatus;
      sendAttachRequest();
    }
    String msg = receiveUDPPacket();
    if (msg == "CapabilityRequest")
    {
      device->setMessageType("capabilities");
      msg = "";
      sendDeviceCapabilities(device);
      break;
    }
  }
}

String ConnectionHandler::receiveUDPPacket()
{
  int packetSize = Udp.parsePacket();
  char packetBuffer[255] = {};
  if (packetSize) {
    IPAddress remoteIp = Udp.remoteIP();

    // read the packet into packetBufffer
    int len = Udp.read(packetBuffer, 255);
    if (len > 0) {
      packetBuffer[len] = 0;
    }
  }
  return packetBuffer;
}

void ConnectionHandler::sendAReply()
{
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  int replyMsgLenght = replyBuffer.length();
  char *replyMsg = new char[replyMsgLenght];
  replyBuffer.toCharArray(replyMsg, replyMsgLenght + 1);

  Udp.write(replyMsg);
  delete [] replyMsg;
  replyBuffer = "empty";
  Udp.endPacket();
}

void ConnectionHandler::sendAttachRequest()
{
  IPAddress broadcastIp(192, 168, 137, 255);
  char attachRequestMsg[] = "AttachRequest";
  //  Serial.println("Send AttachRequest");
  Udp.beginPacket(broadcastIp, broadcastPort);
  Udp.write(attachRequestMsg);
  Udp.endPacket();
}

void ConnectionHandler::sendDeviceCapabilities(CommonDevice *device)
{
  String caps = device->getCapabilities();
  const char *capabilityMsg = caps.c_str();
  //  Serial.println("Send Capabilities");
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  Udp.write(capabilityMsg);
  Udp.endPacket();
}

void ConnectionHandler::handleIncomingMessages(CommonDevice *device)
{
  if (WiFi.status() == WL_CONNECTED)
  {
    String msgBack = device->handleMessage(receiveUDPPacket());
    setReplyBuffer(msgBack);
    sendAReply();
  }
  else
  {
    Serial.println(WiFi.status());
  }
}
String ConnectionHandler::getMac()
{
  return mac;
}
void ConnectionHandler::setMac()
{
  unsigned char mac_char[6];
  WiFi.macAddress(mac_char);
  mac = macToStr(mac_char);
}


