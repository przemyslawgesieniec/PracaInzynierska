#ifndef LightSwitch_h
#define LightSwitch_h


#include "Arduino.h"
#include "CommonDevice.h"

class LightSwitch : public CommonDevice
{
public:
	LightSwitch(String name, String location, String macAddress, bool state, int operablePin);
	~LightSwitch();
	String getDeviceType();
	bool getSwitchState();
  String getCapabilities();
  String handleMessage(String msg);
private:
	bool switchState;
	int operablePin;

};
#endif

