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
//void LightSwitch::handleMessage(ConnectionHandler handler)
//{
//   if (msg == "LightSwitchON")
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
//}

