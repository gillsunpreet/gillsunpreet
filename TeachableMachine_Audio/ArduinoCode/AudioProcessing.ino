void setup() {
  Serial.begin(9600);
  
  // Set up pins for each LED
  pinMode(13, OUTPUT);  // LED for Background Noise
  pinMode(12, OUTPUT);  // LED for Clap
  pinMode(11, OUTPUT);  // LED for Snap
  pinMode(10, OUTPUT);  // LED for Whistle
}

void loop() {
  if (Serial.available() > 0) {
    char command = Serial.read();
    
    // Turn off all LEDs before processing a new command
    resetLEDs();
    
    switch (command) {
      case 'B':  // Background Noise
        backgroundNoisePattern();
        break;
      case 'C':  // Clap
        clapPattern();
        break;
      case 'S':  // Snap
        snapPattern();
        break;
      case 'W':  // Whistle
        whistlePattern();
        break;
      default:
        // No valid command received; keep all LEDs off
        resetLEDs();
        break;
    }
  }
}

// Function to reset all LEDs (turn them off)
void resetLEDs() {
  digitalWrite(13, LOW);
  digitalWrite(12, LOW);
  digitalWrite(11, LOW);
  digitalWrite(10, LOW);
}

// LED pattern for background noise (steady blink)
void backgroundNoisePattern() {
  digitalWrite(13, HIGH);
  delay(500);
  digitalWrite(13, LOW);
  delay(500);
}

// LED pattern for clap (quick flashes)
void clapPattern() {
  for (int i = 0; i < 3; i++) {
    digitalWrite(12, HIGH);
    delay(200);
    digitalWrite(12, LOW);
    delay(200);
  }
}

// LED pattern for snap (two longer blinks)
void snapPattern() {
  for (int i = 0; i < 2; i++) {
    digitalWrite(11, HIGH);
    delay(500);
    digitalWrite(11, LOW);
    delay(500);
  }
}

// LED pattern for whistle (short pulses)
void whistlePattern() {
  for (int i = 0; i < 4; i++) {
    digitalWrite(10, HIGH);
    delay(100);
    digitalWrite(10, LOW);
    delay(100);
  }
}
