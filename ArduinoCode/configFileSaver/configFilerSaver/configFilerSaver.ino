#include <ArduinoJson.h>
#include "FS.h"

void setup() {
  // put your setup code here, to run once:
  if(saveConfig()){
     Serial.println("OK");
  }
  Serial.println("FAIL");
}

void loop() {
  // put your main code here, to run repeatedly:

}
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
