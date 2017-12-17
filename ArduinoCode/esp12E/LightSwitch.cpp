#include "LightSwitch.h"

LightSwitch::LightSwitch(String name, String location, String macAddress, bool switchState, int operablePin) : CommonDevice(name, location, macAddress), switchState(switchState), operablePin(operablePin)
{
  pinMode(operablePin, OUTPUT);
  digitalWrite(operablePin,LOW);
}

LightSwitch::~LightSwitch()
{
}

String LightSwitch::getDeviceType()
{
	return "LightSwitch";
}

bool LightSwitch::getSwitchState()
{
	return switchState;
}

String LightSwitch::getCapabilities()
{
  String capabilities = getDeviceType(); 
  capabilities += ";"+messageType;
  capabilities += ";"+macAddress;
  capabilities += ";1";
  capabilities += ";"+boolToString(switchState);
  return capabilities;
}
String LightSwitch::handleMessage(String msg)
{
   if (msg == "LightSwitchON")
    {
      digitalWrite(operablePin, HIGH);
      switchState = true;
      messageType = "stateupdate";
      return getCapabilities();
      
    }
    if (msg == "LightSwitchOFF")
    {
      msg = "";
      digitalWrite(operablePin, LOW);
      switchState = false;
      messageType = "stateupdate";
      return getCapabilities();
    }
}

