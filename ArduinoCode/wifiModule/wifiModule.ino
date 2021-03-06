
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <ArduinoJson.h>
#include "FS.h"

#define CONF_PIN 12

void sendReply();
String boolToString(bool value);
bool saveConfig();

IPAddress local_ip;
unsigned long oldTimerStatus = 0;
unsigned long oldConnectivityTimerStatus = 0;
const int attachRequestResendTimer = 4000;
const int conectivityCounterDecreseTime = 4000;
int deviceReattachAttempts = 3;
unsigned long currentConnectivityTimer = 0;

unsigned int broadcastPort = 11000;
unsigned int localPort = 2390;
String mac = "";

String messageType;

//JSON config
const char* deviceName_conf;
const char* deviceLocation_conf;
const char* ssid_conf;
const char* pass_conf;
//~JSON config

String replyBuffer = "empty";

class CommonDevice
{
  public:
    CommonDevice(String deviceName, String deviceLocation, String deviceType) {
      this->deviceName = deviceName;
      this->deviceLocation = deviceLocation;
      this->deviceType = deviceType;
    }
    virtual bool handleIncomingMessage(String message) = 0;
    virtual String getCapabilities() = 0;

  protected:
    String deviceName;
    String deviceLocation;
    String deviceType;
    String ip;

  public:
    //Getters
    String getDeviceName()
    {
      return deviceName;
    }
    String getDeviceLocation()
    {
      return deviceLocation;
    }

    //Setters
    void setDeviceName(String deviceName)
    {
      this->deviceName = deviceName;
    }
    void setDeviceLocation (String deviceLocation)
    {
      this->deviceLocation = deviceLocation;
    }

};

class LightSwitch : public CommonDevice
{
  public:
    LightSwitch(String deviceName, String deviceLocation, String deviceType, bool switchState, int operablePin) : CommonDevice(deviceName, deviceLocation, deviceType), operablePin(operablePin), switchState(switchState)
    {
      pinMode(operablePin, OUTPUT);
      digitalWrite(operablePin, LOW);
    }
  private:
    int operablePin;
    bool switchState;

    bool handleIncomingMessage(String message)
    {
      if (message == "SwitchON")
      {
        message = "";
        digitalWrite(operablePin, HIGH);
        switchState = true;
        messageType = "stateupdate";
        replyBuffer = getCapabilities();
        sendReply();
        return true;
      }
      if (message == "SwitchOFF")
      {
        message = "";
        digitalWrite(operablePin, LOW);
        switchState = false;
        messageType = "stateupdate";
        replyBuffer = getCapabilities();
        sendReply();
        return true;
      }
      return false;
    }

    String getCapabilities()
    {
      String capabilities = "";
      capabilities += "" + local_ip.toString();
      capabilities += ";" + deviceType;
      capabilities += ";" + messageType;
      capabilities += ";" + deviceName;
      capabilities += ";" + deviceLocation;
      capabilities += ";" + mac;
      capabilities += ";1";
      capabilities += ";" + boolToString(switchState);
      return capabilities;
    }

};

class MultiLightSwitch : public CommonDevice
{
  public:
    MultiLightSwitch(String deviceName, String deviceLocation, String deviceType, bool switchState[2], int operablePin[2]) : CommonDevice(deviceName, deviceLocation, deviceType)
    {
      this->operablePin[0] = operablePin[0];
      this->switchState[0] = switchState[0];
      this->operablePin[1] = operablePin[1];
      this->switchState[1] = switchState[1];

      pinMode(operablePin[0], OUTPUT);
      pinMode(operablePin[1], OUTPUT);
      digitalWrite(operablePin[0], switchState[0]);
      digitalWrite(operablePin[1], switchState[1]);
    }
  private:
    int operablePin[2];
    bool switchState[2];

    bool handleIncomingMessage(String message)
    {

      if (message.startsWith("MultiSwitch"))
      {
        updateMultiSwitchState(message);
        digitalWrite(operablePin[0], switchState[0]);
        digitalWrite(operablePin[1], switchState[1]);
        message = "";
        messageType = "stateupdate";
        replyBuffer = getCapabilities();
        sendReply();
        return true;
      }
      return false;
    }

    void updateMultiSwitchState(String message)
    {
      Serial.print("message ");
      Serial.println(message);

      String buba = message.substring(0, message.indexOf(";"));
      Serial.print("state ");
      Serial.println(buba);

      message.remove(0, buba.length() + 1);
      Serial.print("message ");
      Serial.println(message);
      for (int i = 0; i < 2; i++)
      {
        String state = message.substring(0, message.indexOf(";"));
        Serial.print("state ");
        Serial.print(i);
        Serial.print(" = ");
        Serial.println(state);
        if (state.equals("true")) {
          switchState[i] = true;
        }
        else {
          switchState[i] = false;
        }
        message.remove(0, state.length() + 1);
      }
    }

    String getCapabilities()
    {
      String capabilities = "";
      capabilities += "" + local_ip.toString();
      capabilities += ";" + deviceType;
      capabilities += ";" + messageType;
      capabilities += ";" + deviceName;
      capabilities += ";" + deviceLocation;
      capabilities += ";" + mac;
      capabilities += ";2";
      capabilities += ";" + boolToString(switchState[0]);
      capabilities += ";" + boolToString(switchState[1]);
      return capabilities;
    }

};

CommonDevice *device;

WiFiUDP Udp;
IPAddress broadcastIp;

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(CONF_PIN, INPUT);
  Serial.begin(115200);
  Serial.println("");
  Serial.println("Starting module ESP012e");

  Serial.println("Mounting file system...");
  if (!SPIFFS.begin()) {
    Serial.println("Failed to mount file system");
    return;
  }
  Serial.println("Reading configuration file...");
  if (!loadConfig()) {
    Serial.println("Can not read WiFi credentials");
    Serial.println("Restoring default settings");
    deviceName_conf = "switch";
    deviceLocation_conf = "room";
    ssid_conf = "DESKTOP_WIFI";
    pass_conf = "przemek123";
  }

  if (digitalRead(CONF_PIN) == HIGH) {
    Serial.println("Creating instance of Light switch");
    device = new LightSwitch(deviceName_conf, deviceLocation_conf, "switch", false, 5);
  }
  else if (digitalRead(CONF_PIN) == LOW) {
    Serial.println("Creating instance of Multi Light Switch");
    bool states[2] = {false, false};
    int operablePins[2] = {4, 5};
    device = new MultiLightSwitch (deviceName_conf, deviceLocation_conf, "multiswitch", states, operablePins);
  }

  WiFi.begin(ssid_conf, pass_conf);
  while (WiFi.waitForConnectResult() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(100);
  }
  Serial.println("");
  Serial.println("Device is connected to the network");

  unsigned char mac_char[6];
  WiFi.macAddress(mac_char);
  mac += macToStr(mac_char);

  broadcastIp = calculateBroadcastAddress();

  Serial.print("MAC:");
  Serial.println(mac);

  local_ip = WiFi.localIP();
  Serial.print("IP address: ");
  Serial.println(local_ip);
  Udp.begin(localPort);
  Serial.print("UDP begun");
  waitForApplicationAttach();

}

void loop() {
  if (WiFi.status() == WL_CONNECTED)
  {
    String msg = receiveUDPPacket();
    if (!device->handleIncomingMessage(msg)) {
      handleGeneralMessage(msg);
    }
    currentConnectivityTimer = millis();
  }
  else
  {
    Serial.println(WiFi.status());
  }
}
void handleGeneralMessage(String message)
{

  if (message.startsWith("UpdateDeviceData"))
  {
    String tmpDeviceName = device->getDeviceName();
    String tmpDeviceLocation = device->getDeviceLocation();
    int slength = message.length();
    message = message.substring(17, slength);
    device->setDeviceName(getDeviceNameFromMessage(message));
    device->setDeviceLocation(getDeviceLocationFromMessage(message));
    messageType = "dataupdatecfm";
    if (!saveConfig()) {
      messageType = "updatefail";
      device->setDeviceName(tmpDeviceName);
      device->setDeviceLocation(tmpDeviceLocation);
    }
    replyBuffer = device->getCapabilities();
    sendReply();
  }

  if (message.startsWith("connectionControl"))
  {
    deviceReattachAttempts = 3;
    message = "empty";
    messageType = "connectioncfm";
    replyBuffer = device->getCapabilities();
    sendReply();
  }
  checkConnectivityTimer();
}
String receiveUDPPacket()
{
  int packetSize = Udp.parsePacket();
  char packetBuffer[255] = {};
  if (packetSize) {
    Serial.print("Received packet of size ");
    Serial.println(packetSize);
    Serial.print("From ");
    IPAddress remoteIp = Udp.remoteIP();
    Serial.print(remoteIp);
    Serial.print(", port ");
    Serial.println(Udp.remotePort());

    // read the packet into packetBufffer
    int len = Udp.read(packetBuffer, 255);
    if (len > 0) {
      packetBuffer[len] = 0;
    }
    Serial.println("Contents: ");
    Serial.println(packetBuffer);
  }
  return packetBuffer;
}
void sendReply()
{
  Serial.print("reply msg: ");
  Serial.println(replyBuffer);
  const char *replyMsg = replyBuffer.c_str();
  sendUDPMessage(replyMsg);
  replyBuffer = "empty";
}

void sendAttachRequest()
{
  String attachRequest = "AttachRequest;" + local_ip.toString();
  const char *attachRequestMsg = attachRequest.c_str();
  Serial.println("Send AttachRequest");
  Serial.println(broadcastIp);
  Udp.beginPacket(broadcastIp, broadcastPort);
  Udp.write(attachRequestMsg);
  Udp.endPacket();
}
void waitForApplicationAttach()
{
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
      messageType = "capabilities";
      msg = "";
      sendDeviceCapabilities();
      Serial.println("Device attached to application correctly");
      break;
    }
  }
}
void checkConnectivityTimer()
{
  if (deviceReattachAttempts < 0)
  {
    waitForApplicationAttach();
    deviceReattachAttempts = 3;
  }
  if (currentConnectivityTimer - oldConnectivityTimerStatus >= conectivityCounterDecreseTime)
  {
    oldConnectivityTimerStatus = currentConnectivityTimer;
    deviceReattachAttempts --;
  }
}
void sendDeviceCapabilities()
{
  String caps = device->getCapabilities();
  const char *capabilityMsg = caps.c_str();
  Serial.println("Send Capabilities: ");
  Serial.println(caps);
  sendUDPMessage(capabilityMsg);
}

void sendUDPMessage(const char* message)
{
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  Udp.write(message);
  Udp.endPacket();
}
//////////////////////////
//support functions
/////////////////////////
String macToStr(const uint8_t* mac)
{
  String result;
  for (int i = 0; i < 6; ++i) {
    result += String(mac[i], 16);
    if (i < 5)
      result += ':';
  }
  return result;
}
String boolToString(bool value)
{
  return value ? "true" : "false";
}

String getDeviceNameFromMessage(String message)
{
  int divider = message.indexOf(";");
  String newName = message.substring(0, divider);
  return newName;
}
String getDeviceLocationFromMessage(String message)
{
  String shorterMessage = message.substring(device->getDeviceName().length() + 1, message.length());
  int divider = shorterMessage.indexOf(";");
  String newLocation = shorterMessage.substring(0, divider);
  return newLocation;
}

IPAddress calculateBroadcastAddress()
{
  IPAddress subnet = WiFi.subnetMask();
  IPAddress ip = WiFi.localIP();
  IPAddress invertedSubnetMask = ~subnet;
  IPAddress netAddress = ip & subnet;
  return netAddress | invertedSubnetMask;
}


//////////////////////////
//SPIFFS memory functions
/////////////////////////
bool saveConfig() {
  Serial.println("SAVING CONFIG !");
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& json = jsonBuffer.createObject();
  json["ssid"] = "DESKTOP_WIFI";
  json["pass"] = "przemek123";
  json["deviceName"] = device->getDeviceName();
  json["deviceLocation"] = device->getDeviceLocation();

  File configFile = SPIFFS.open("/config.json", "w");
  if (!configFile) {
    Serial.println("Failed to open config file for writing");
    return false;
  }

  json.printTo(configFile);
  return true;
}
bool loadConfig() {
  File configFile = SPIFFS.open("/config.json", "r");
  if (!configFile) {
    Serial.println("Failed to open config file");
    return false;
  }

  std::unique_ptr<char[]> buf(new char[configFile.size()]);
  configFile.readBytes(buf.get(), configFile.size());

  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& json = jsonBuffer.parseObject(buf.get());

  if (!json.success()) {
    Serial.println("Parsing failed");
    return false;
  }

  deviceName_conf = json["deviceName"];
  deviceLocation_conf = json["deviceLocation"];
  ssid_conf = json["ssid"];
  pass_conf = json["pass"];

  Serial.print("Device Name: ");
  Serial.println(deviceName_conf);
  Serial.print("Device Location: ");
  Serial.println(deviceLocation_conf);
  Serial.print("pass_conf: ");
  Serial.println(pass_conf);
  Serial.print("ssid_conf: ");
  Serial.println(ssid_conf);
  return true;
}



