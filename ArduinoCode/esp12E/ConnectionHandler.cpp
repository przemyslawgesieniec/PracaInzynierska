#include "ConnectionHandler.h"

ConnectionHandler::ConnectionHandler(const char * ssid, const char * pass, unsigned int localPort, unsigned int broadcastPort)
{
    this->localPort = localPort;
    this->broadcastPort = broadcastPort;
    this->pass = pass;
    this->ssid = ssid;
     WiFi.begin(ssid, pass);
     while (WiFi.waitForConnectResult() != WL_CONNECTED)
     {
          Serial.print(".");
          delay(100);
     }
     setMac();
     Udp.begin(localPort);

    Serial.println("");
    Serial.println("Device is connected to the network");
  
    unsigned char mac_char[6];
    WiFi.macAddress(mac_char);
    mac += macToStr(mac_char);
  
    Serial.print("MAC:");
    Serial.println(mac);
    
    IPAddress local_ip = WiFi.localIP();
    Serial.print("IP address: ");
    Serial.println(local_ip);
    Udp.begin(localPort);
    Serial.print("UDP begun");

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



// IPAddress ConnectionHandler::getBroadcastIp()
// {
// 	return broadcastIp;
// }

// //setters
// void ConnectionHandler::setBroadcastIp(IPAddress broadcastIp)
// {
// 	this->broadcastIp = broadcastIp;
// }


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
      SendAttachRequest();
    }
    String msg = receiveUDPPacket();
    if (msg == "CapabilityRequest")
    {
      device->setMessageType("capabilities");
      msg = "";
      SendDeviceCapabilities(device);
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

void ConnectionHandler::SendAReply()
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

void ConnectionHandler::SendAttachRequest()
{
  IPAddress broadcastIp(192, 168, 137, 255);
  char attachRequestMsg[] = "AttachRequest";
  Serial.println("Send AttachRequest");
  Udp.beginPacket(broadcastIp, broadcastPort);
  Udp.write(attachRequestMsg);
  Udp.endPacket();
}

void ConnectionHandler::SendDeviceCapabilities(CommonDevice *device)
{
  String caps = device->getCapabilities();
  const char *capabilityMsg = caps.c_str();
  Serial.println("Send Capabilities");
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  Udp.write(capabilityMsg);
  Udp.endPacket();
}

void ConnectionHandler::handleIncomingMessages(CommonDevice *device)
{
//  if (WiFi.status() == WL_CONNECTED)
//  {
//    String msg = receiveUDPPacket();
//    if (msg == "LightSwitchON")
//    {
//      msg = "";
//      digitalWrite(13, HIGH);
//      Serial.print("Light on: ");
//      switchState = true;
//      messageType = "stateupdate";
//      ReplyBuffer = getCapabilities();
//      SendAReply();
//    }
//    if (msg == "LightSwitchOFF")
//    {
//      msg = "";
//      Serial.print("Light off: ");
//      digitalWrite(13, LOW);
//      switchState = false;
//      messageType = "stateupdate";
//      ReplyBuffer = getCapabilities();
//      SendAReply();
//    }
//  }
//  else
//  {
//    Serial.println(WiFi.status());
//  }
}

