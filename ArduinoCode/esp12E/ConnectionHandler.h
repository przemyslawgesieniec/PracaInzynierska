#ifndef ConnectionHandler_h
#define ConnectionHandler_h

#include "CommonDevice.h"
#include "LightSwitch.h"
#include "Arduino.h"
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>


class CommonDevice;

class ConnectionHandler
{
  
public:
	ConnectionHandler(const char * ssid, const char * pass, unsigned int localPort, unsigned int broadcastPort);
  ConnectionHandler();
	~ConnectionHandler();
 
 //
 //METHODS
 //
	void connectToWiFi();
  void waitForApplicationAttach(CommonDevice* device);
  String receiveUDPPacket();
  void sendAttachRequest();
  void sendAReply();
  void sendDeviceCapabilities(CommonDevice* device);
  void handleIncomingMessages(CommonDevice* device);

 //
 //GETTERS
 //
  String getMac();
  String getReplyBuffer();

 //
 //SETTERS
 //
  void setReplyBuffer(String msg);

 //
 //PRIVATE FIELDS
 //
private:
	const char * ssid;
	const char * pass;
	unsigned int localPort;
	WiFiUDP udp;
	String mac;
	WiFiUDP Udp;
  String replyBuffer = "empty";
  unsigned int broadcastPort;

 //
 //PRIVATE METHODS
 //
	void setMac();
	String macToStr(const uint8_t* mac);
};
#endif
