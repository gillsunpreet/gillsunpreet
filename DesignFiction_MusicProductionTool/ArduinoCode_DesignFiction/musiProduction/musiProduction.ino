#include <Encoder.h>

// Pins for the rotary encoder
#define ENCODER_CLK 3
#define ENCODER_DT 2
#define ENCODER_SW 4  // Button pin for the rotary encoder

// LED pins for different genres
#define LED1 9  // Recording
#define LED2 10 // Bass
#define LED3 11 // Drums
#define LED4 12 // Guitar

#define LED_LEFT 5    // Left hand raised
#define LED_RIGHT 6   // Right hand raised
#define LED_HEAD 7    // Head nod up

#define BUTTON_PIN 8 // Button on breadboard for playing Both.mp3

Encoder myEncoder(ENCODER_CLK, ENCODER_DT);  // Initialize the encoder

long lastEncoderPos = 0;  // Track the last known position
int currentPos = 0;       // Current position mapped to 0-360
const int stepsPerRotation = 80;  // 80 steps for one full 360-degree rotation
const int maxPosition = 360;      // Full rotation count
bool buttonPressed = false;       // Flag to detect button press
int currentDecade = 0;            // Variable to store current decade
bool breadboardButtonPressed = false;  // Flag for breadboard button state

void setup() {
  Serial.begin(9600);  // Start the Serial Monitor for communication
  pinMode(ENCODER_SW, INPUT_PULLUP);  // Set encoder button as input with pull-up resistor
  pinMode(BUTTON_PIN, INPUT_PULLUP);  // Set breadboard button as input with pull-up resistor

  // Set LED pins as output
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  pinMode(LED3, OUTPUT);
  pinMode(LED4, OUTPUT);
  pinMode(LED_LEFT, OUTPUT);
  pinMode(LED_RIGHT, OUTPUT);
  pinMode(LED_HEAD, OUTPUT);

  // Turn off all LEDs initially
  resetAllLEDs();
}

void resetAllLEDs() {
  // Turn off all LEDs
  digitalWrite(LED1, LOW);
  digitalWrite(LED2, LOW);
  digitalWrite(LED3, LOW);
  digitalWrite(LED4, LOW);
  digitalWrite(LED_LEFT, LOW);
  digitalWrite(LED_RIGHT, LOW);
  digitalWrite(LED_HEAD, LOW);
}

void lightUpLED(int activeLED) {
  // Turn off all LEDs first
  digitalWrite(9, LOW);  // Turn off LED9
  digitalWrite(10, LOW); // Turn off LED10
  digitalWrite(11, LOW); // Turn off LED11
  digitalWrite(12, LOW); // Turn off LED12

  // Turn on the specified LED
  digitalWrite(activeLED, HIGH);
}

void controlGestureLED(const char* gesture) {
  // Turn off all gesture LEDs
  digitalWrite(LED_LEFT, LOW);
  digitalWrite(LED_RIGHT, LOW);
  digitalWrite(LED_HEAD, LOW);

  // Turn on the corresponding LED
  if (strcmp(gesture, "Left") == 0) {
    digitalWrite(LED_LEFT, HIGH);
  } else if (strcmp(gesture, "Right") == 0) {
    digitalWrite(LED_RIGHT, HIGH);
  } else if (strcmp(gesture, "Head") == 0) {
    digitalWrite(LED_HEAD, HIGH);
  }
}

void loop() {
  long newPos = myEncoder.read();  // Read the current encoder position

  // Check if the encoder position has changed
  if (newPos != lastEncoderPos) {
    long stepChange = newPos - lastEncoderPos;
    currentPos += (stepChange * maxPosition) / stepsPerRotation;

    if (currentPos >= maxPosition) currentPos = 0;
    else if (currentPos < 0) currentPos = maxPosition;

    if (currentPos >= 0 && currentPos < 90) {
      currentDecade = 0;  // 1980s
      lightUpLED(9);
    } else if (currentPos >= 90 && currentPos < 180) {
      currentDecade = 1;  // 1990s
      lightUpLED(10);
    } else if (currentPos >= 180 && currentPos < 270) {
      currentDecade = 2;  // 2000s
      lightUpLED(11);
    } else if (currentPos >= 270 && currentPos < 360) {
      currentDecade = 3;  // 2010s
      lightUpLED(12);
    }

    lastEncoderPos = newPos;
    Serial.print("Mapped position: ");
    Serial.println(currentPos);
  }

  // Check if the encoder button is pressed
  if (digitalRead(ENCODER_SW) == LOW && !buttonPressed) {
    buttonPressed = true;

    // Send the current decade only when the button is pressed
    if (currentDecade == 0) {
      Serial.println("Recording");
    } else if (currentDecade == 1) {
      Serial.println("Bass");
    } else if (currentDecade == 2) {
      Serial.println("Drums");
    } else if (currentDecade == 3) {
      Serial.println("Guitars");
    }
  } else if (digitalRead(ENCODER_SW) == HIGH) {
    buttonPressed = false;
  }

  // Read from the serial port (gesture commands from Python)
  if (Serial.available() > 0) {
    String command = Serial.readStringUntil('\n');
    command.trim();
    controlGestureLED(command.c_str());
  }

  // Check if the breadboard button is pressed
  if (digitalRead(BUTTON_PIN) == LOW && !breadboardButtonPressed) {
    breadboardButtonPressed = true;
    Serial.println("Both");  // Send 'Both' to Python
  } else if (digitalRead(BUTTON_PIN) == HIGH) {
    breadboardButtonPressed = false;
  }

  delay(100);  // Small delay for readability
}
