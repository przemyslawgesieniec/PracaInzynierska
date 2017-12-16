#ifndef ConnectionHandler_h
#define ConnectionHandler_h

#include "CommonDevice.h"
#include "LightSwitch.h"
#include "Arduino.h"
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
class ConnectionHandler
{
  
public:
	ConnectionHandler(const char * ssid, const char * pass, unsigned int localPort, unsigned int broadcastPort);
  ConnectionHandler();
	~ConnectionHandler();
 
	void connectToWiFi();
  void waitForApplicationAttach(CommonDevice* device);
  String receiveUDPPacket();
  void SendAttachRequest();
  void SendAReply();
  void SendDeviceCapabilities(CommonDevice* device);
  void handleIncomingMessages(CommonDevice* device);
  
  String getMac();

private:
	const char * ssid;
	const char * pass;
	unsigned int localPort;
	WiFiUDP udp;
	String mac;
	WiFiUDP Udp;
  String replyBuffer = "empty";
  unsigned int broadcastPort;


	void setMac();
	String macToStr(const uint8_t* mac);
};
#endif
