
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <ArduinoJson.h>
#include "FS.h"

String getCapabilities();
void sendReply();
String boolToString(bool value);

const char IPD[] PROGMEM = "IPD,";
const char READY[] PROGMEM = "ready";

unsigned long oldTimerStatus = 0;
const int attachRequestResendTimer = 4000;
bool switchState = false;

byte readCommand(int timeout, const char* text1 = NULL, const char* text2 = NULL);

unsigned int broadcastPort = 11000;
unsigned int localPort = 2390;
String mac = "";
String deviceType = "switch";
String messageType;

//JSON config
const char* deviceName;
const char* deviceLocation;
const char* ssid_conf;
const char* pass_conf;
//~JSON config

String replyBuffer = "empty";

class CommonDevice
{
  public:
    CommonDevice(String deviceName, String deviceLocation) {
      this->deviceName = deviceName;
      this->deviceLocation = deviceLocation;
    }
    virtual void handleIncomingMessage(String message) = 0;
    virtual String getCapabilities() = 0;
    
  protected:
    String deviceName;
    String deviceLocation;
    String deviceType;


};

class LightSwitch : public CommonDevice
{
  public:
    LightSwitch(String deviceName, String deviceLocation, String deviceType, bool switchState, int operablePin) : CommonDevice(deviceName, deviceLocation), operablePin(operablePin), switchState(switchState)
    {
      pinMode(operablePin, OUTPUT);
      digitalWrite(operablePin, LOW);
    }
  private:
    int operablePin;
    bool switchState;

    void handleIncomingMessage(String message)
    {
      if (message == "LightSwitchON")
      {
        message = "";
        digitalWrite(operablePin, HIGH);
        switchState = true;
        messageType = "stateupdate";
        replyBuffer = getCapabilities();
        sendReply();
      }
      if (message == "LightSwitchOFF")
      {
        message = "";
        digitalWrite(operablePin, LOW);
        switchState = false;
        messageType = "stateupdate";
        replyBuffer = getCapabilities();
        sendReply();
      }
    }

    String getCapabilities()
    {
      String capabilities = deviceType;
      capabilities += ";" + messageType;
      capabilities += ";" + mac;
      capabilities += ";1";
      capabilities += ";" + boolToString(switchState);
      return capabilities;
    }

};


CommonDevice *device;
IPAddress broadcastIp(192, 168, 137, 255); //TODO: zmienic zevby sam ustawial w zaleznosci od maski sieci

WiFiUDP Udp;

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  //  pinMode(13, OUTPUT);
  //  digitalWrite(13, LOW);
  Serial.begin(115200);
  Serial.println("");
  Serial.println("Starting module ESP012e");

  device = new LightSwitch("switch", "room", "LightSwitch", false, 13);

  if (!SPIFFS.begin()) {
    Serial.println("Failed to mount file system");
    return;
  }

  loadConfig();
  if (!loadConfig()) {
    Serial.println("Module will not start, can not read WiFi credentials");
    //     ESP.restart();
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

  Serial.print("MAC:");
  Serial.println(mac);

  IPAddress local_ip = WiFi.localIP();
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
    device->handleIncomingMessage(msg);
  }
  else
  {
    Serial.println(WiFi.status());
  }
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
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  Serial.print("reply msg: ");
  Serial.println(replyBuffer);
  int replyMsgLenght = replyBuffer.length();
  Serial.print("replyMsgLenght:");
  Serial.println(replyMsgLenght);
  char *replyMsg = new char[replyMsgLenght];
  replyBuffer.toCharArray(replyMsg, replyMsgLenght + 1);
  Serial.println(replyMsg);

  Udp.write(replyMsg);
  delete [] replyMsg;
  replyBuffer = "empty";
  Udp.endPacket();
}

void sendAttachRequest()
{
  char attachRequestMsg[] = "AttachRequest";
  Serial.println("Send AttachRequest");
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
      hardwareSignalizeConnection();
      break;
    }
  }
}

void hardwareSignalizeConnection()
{
  Serial.println("Device attached to application correctly");
  for (int i = 0; i < 3; i++)
  {
    digitalWrite(LED_BUILTIN, HIGH);
    delay(100);
    digitalWrite(LED_BUILTIN, LOW);
    delay(100);
  }
}

void sendDeviceCapabilities()
{
  String caps = device->getCapabilities();
  const char *capabilityMsg = caps.c_str();
  Serial.println("Send Capabilities");
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  Udp.write(capabilityMsg);
  Udp.endPacket();
}

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
//String getCapabilities()
//{
//  String capabilities = deviceType;
//  capabilities += ";" + messageType;
//  capabilities += ";" + mac;
//  capabilities += ";1";
//  capabilities += ";" + boolToString(switchState);
//  return capabilities;
//}

//SPIFFS memory functions
bool saveConfig() {
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& json = jsonBuffer.createObject();
  json["ssid"] = "DESKTOP_WIFI";
  json["pass"] = "przemek123";
  json["deviceName"] = "switch";
  json["deviceLocation"] = "room";

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

  deviceName = json["deviceName"];
  deviceLocation = json["deviceLocation"];
  ssid_conf = json["ssid"];
  pass_conf = json["pass"];

  Serial.print("Device Name: ");
  Serial.println(deviceName);
  Serial.print("Device Location: ");
  Serial.println(deviceLocation);
  Serial.print("pass_conf: ");
  Serial.println(pass_conf);
  Serial.print("ssid_conf: ");
  Serial.println(ssid_conf);
  return true;
}



