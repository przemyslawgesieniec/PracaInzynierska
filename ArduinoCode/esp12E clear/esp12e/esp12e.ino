
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

const char IPD[] PROGMEM = "IPD,";
const char READY[] PROGMEM = "ready";
byte readCommand(int timeout, const char* text1 = NULL, const char* text2 = NULL);

    unsigned int broadcastPort = 11000;
    unsigned int localPort = 2390;
//WiFi credentials
const char* ssid = "PENTAGRAM_P6362";
const char* pass = "#mopsik123";

IPAddress broadcastIp(192, 168, 1, 255);
WiFiUDP Udp;

class MessageHandler
{
  private:
    unsigned int broadcastPort = 11000;
    unsigned int localPort = 2390;

    
  public:
    MessageHandler() {    }
    ~MessageHandler() {}

    void sendAttachRequest()
    {
      char attachRequestMsg[] = "AttachRequest";
      Serial.println("Sending AttachRequest");
      Udp.beginPacket(broadcastIp, broadcastPort);
      Udp.write(attachRequestMsg);
      Udp.endPacket();
    }
    String receiveUDPPacket()
    {
      int packetSize = Udp.parsePacket();
      char packetBuffer[255] = {};
      if (packetSize)
      {
        Serial.print("Message From ");
        IPAddress remoteIp = Udp.remoteIP();
        // read the packet into packetBufffer
        int len = Udp.read(packetBuffer, 255);
        if (len > 0) {
          packetBuffer[len] = 0;
        }
      }
      return packetBuffer;
    }
};
class SystemHandler
{
  public:
    SystemHandler() {}
    ~SystemHandler() {}

    void connectToLocalWiFiNetwork(const char* ssid, const char* pass)
    {
      WiFi.begin(ssid, pass);
      while (WiFi.waitForConnectResult() != WL_CONNECTED)
      {
        Serial.print(".");
        delay(100);
      }
      Serial.println("Connection established");
    }
    void printDeviceAndConnectionProperites()
    {
      IPAddress ipAddress = WiFi.localIP();

      Serial.print("Device type: ");
      Serial.println("");
      Serial.print("IP address: ");
      Serial.println(ipAddress);
      Serial.print("MAC address: ");
      Serial.println("");
    }
    void waitForApplicationAttach(MessageHandler *messageHandler)
    {
      unsigned long oldTimerStatus = 0;
      const int attachRequestResendTimer = 4000;

      while (true)
      {
        unsigned long currentTimerStatus = millis();
        if (currentTimerStatus - oldTimerStatus >= attachRequestResendTimer)
        {
          oldTimerStatus = currentTimerStatus;
          messageHandler->sendAttachRequest();
          delay(10);
        }
        //        String receivedMessage = messageHandler->receiveUDPPacket();
        //        if (receivedMessage == "CapabilityRequest")
        //        {
        //          receivedMessage = "";
        //          //sendDeviceCapabilities();
        //          break;
        //        }
      }
    }
};





void setup() {

  Serial.begin(115200);
  Serial.println("Starting module ESP12e");

  //
  // Connection to local WiFi
  //
  SystemHandler *systemHandler = new SystemHandler();
  MessageHandler *messageHandler = new MessageHandler();

  systemHandler->connectToLocalWiFiNetwork(ssid, pass);
  systemHandler->printDeviceAndConnectionProperites();


  //
  // Attach to local apllication
  //
  //systemHandler->waitForApplicationAttach(messageHandler);
 






}

void loop() {
 sendAttachRequest();
 delay(2000);
}

    void sendAttachRequest()
    {
      char attachRequestMsg[] = "AttachRequest";
      Serial.println("Sending AttachRequest");
      Udp.beginPacket(broadcastIp, broadcastPort);
      Udp.write(attachRequestMsg);
      Udp.endPacket();
    }
