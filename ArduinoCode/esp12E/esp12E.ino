#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

#include "CommonDevice.h"
#include "LightSwitch.h"
#include "ConnectionHandler.h"

const char IPD[] PROGMEM = "IPD,";
const char READY[] PROGMEM = "ready";

bool switchState = false;

byte readCommand(int timeout, const char* text1 = NULL, const char* text2 = NULL);
//

//String mac = "";
//String deviceType = "switch";
//String messageType;

unsigned int broadcastPort = 11000;
unsigned int localPort = 2390;

const char* ssid = "DESKTOP_WIFI";
const char* pass = "przemek123";

//const char* ssid = "PENTAGRAM_P6362";
//const char* pass = "#mopsik123";
//
//const char* ssid = "PLAY-ONLINE-8763";
//const char* pass = "G2TTT9D5";

WiFiUDP Udp;
ConnectionHandler handler;
CommonDevice *device;

void setup() {
  //pinMode(LED_BUILTIN, OUTPUT);
  //  pinMode(13, OUTPUT);
  //  digitalWrite(13,LOW);
//  Serial.begin(115200);
  Serial.begin(9600);
  Serial.println("");
  Serial.println("Starting module ESP12e");

  


  handler = ConnectionHandler(ssid, pass, localPort, broadcastPort);
  Serial.println("");
  Serial.println("Device is connected to the network");

  Serial.print("MAC:");
  Serial.println(handler.getMac());

  IPAddress local_ip = WiFi.localIP();
  Serial.print("IP address: ");
  Serial.println(local_ip);
  Serial.print("UDP begun");
  device = new LightSwitch("light", "kitchen", handler.getMac(), false, 13);
  Serial.print("New device created");
  handler.waitForApplicationAttach(device);
  Serial.print("device successfully connected to application");
}

void loop() {
    handler.handleIncomingMessages(device);
}




