#include "LightSwitch.h"

LightSwitch::LightSwitch(String name, String location, String macAddress, bool switchState, int operablePin) : CommonDevice(name, location, macAddress), switchState(switchState), operablePin(operablePin)
{

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

