#ifndef CommonDevice_h
#define CommonDevice_h

#include <ESP8266WiFi.h>
//#include "ConnectionHandler.h"
#include "Arduino.h"
class CommonDevice
{
 
public:
	CommonDevice();
	CommonDevice(String name, String location, String macAddress);
	virtual ~CommonDevice();
  virtual String getCapabilities() = 0;
  //virtual void handleMessage() = 0;

  String macToStr(const uint8_t* mac);
  String boolToString(bool value);

  
	//Getters
	String getName();
	String getLocation();
	String getMacAddress();
  String getMessageType();
  String getDeviceType();
  String getMsg();


	//Setters
	void setName(String name);
	void setLocation(String location);
	void setMacAddress(String macAddress);
  void setMessageType(String type);
  void setMsg(String msg);

protected:
	 String name;
	 String location;
	 String macAddress;
   String messageType;
   String msg;

  
};
#endif

