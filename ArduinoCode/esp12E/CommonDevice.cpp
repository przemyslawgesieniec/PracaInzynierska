#include "CommonDevice.h"

CommonDevice::CommonDevice() {}
CommonDevice::CommonDevice(String name, String location, String macAddress)
{
	this->name = name;
	this->location = location;
	this->macAddress = macAddress;
}


CommonDevice::~CommonDevice()
{
}

//
//GETTERS
//
String CommonDevice::getMessageType()
{
  return messageType;
}

String CommonDevice::getName()
{
	return name;
}

String CommonDevice::getLocation()
{
	return location;
}
String CommonDevice::getMsg()
{
  return msg;
}
String CommonDevice::getMacAddress()
{
	return macAddress;
}

//
//SETTERS
//
void CommonDevice::setName(String name)
{
	this->name = name;
}
void CommonDevice::setMsg(String msg)
{
  this->msg = msg;
}
void CommonDevice::setLocation(String location)
{
	this->location = location;
}

void CommonDevice::setMacAddress(String macAddress)
{
	this->macAddress = macAddress;
}
void CommonDevice::setMessageType(String messageType)
{
  this->messageType = messageType;
}

String CommonDevice::macToStr(const uint8_t* mac)
{
  String result;
  for (int i = 0; i < 6; ++i) {
    result += String(mac[i], 16);
    if (i < 5)
      result += ':';
  }
  return result;
}
String CommonDevice::boolToString(bool value)
{
  return value ? "true" : "false";
}

