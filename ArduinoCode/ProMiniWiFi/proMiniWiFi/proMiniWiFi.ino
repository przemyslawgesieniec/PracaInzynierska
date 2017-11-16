#define LIGHT_SWITCH 2

void setup() {


  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(LIGHT_SWITCH, OUTPUT);

  const char* ssid = "DESKTOP_WIFI";
  const char* pass = "przemek123";

  Serial.begin(115200);
  // Serial1.begin(9600);
  while (!Serial);
}

void loop() {
  // read from serial what I get
  Serial.println("ON");
  delay(300);
  int dupa = Serial.read();
  if (dupa == 'a')
  {
    Serial.println("DUPAUDPAUDPAUDPAUDPAUPDAUPD");
  }
}
