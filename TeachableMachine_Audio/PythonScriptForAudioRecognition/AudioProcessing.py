import pyaudio
import numpy as np
import tensorflow as tf
import serial
import time

# Initialize PyAudio for real-time audio recording
p = pyaudio.PyAudio()
FORMAT = pyaudio.paInt16  # 16-bit resolution
CHANNELS = 1              # Mono audio
RATE = 16000              # Sample rate, should match your model's requirements
CHUNK = 1024              # Frame size
RECORD_SECONDS = 2.75    # Duration to record per input to match 44032 samples

# Load the TFLite model
interpreter = tf.lite.Interpreter(model_path="C:\\Users\\preet\\Downloads\\A_Tutorial\\MMM\\ssm.tflite")
interpreter.allocate_tensors()

# Get input and output details of the model
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

# Initialize the serial connection to Arduino
try:
    arduino = serial.Serial(port='COM12', baudrate=9600, timeout=1)
    time.sleep(2)  # Wait for the connection to be established
except serial.SerialException as e:
    print(f"Error connecting to Arduino: {e}")
    exit()

# Updated recognize_sound function to include all sound classes
def recognize_sound(audio_input):
    # Ensure audio input matches the expected size
    expected_size = input_details[0]['shape'][1]
    if len(audio_input) < expected_size:
        audio_input = np.pad(audio_input, (0, expected_size - len(audio_input)), mode='constant')
    elif len(audio_input) > expected_size:
        audio_input = audio_input[:expected_size]

    # Add batch dimension
    audio_input = np.expand_dims(audio_input, axis=0)

    # Set the input tensor for the model
    interpreter.set_tensor(input_details[0]['index'], audio_input)
    interpreter.invoke()

    # Get the result from the model
    output_data = interpreter.get_tensor(output_details[0]['index'])
    prediction = np.argmax(output_data)
    confidence = output_data[0][prediction]

    # Print prediction and confidence for debugging
    print(f"Prediction: {prediction}, Confidence: {confidence:.2f}")
    print("Model output:", output_data)  # Print raw model output for more insight

    # Set a confidence threshold (e.g., 0.7)
    confidence_threshold = 0.50

    # Only send the signal if the confidence is high enough
    if confidence >= confidence_threshold:
        if prediction == 0:  # Background Noise
            arduino.write(b'B')
            print("Background noise detected with high confidence - Signal sent to Arduino")
        elif prediction == 1:  # Claps
            arduino.write(b'C')
            print("Clap detected with high confidence - Signal sent to Arduino")
        elif prediction == 2:  # Snaps
            arduino.write(b'S')
            print("Snap detected with high confidence - Signal sent to Arduino")
        elif prediction == 3:  # Whistles
            arduino.write(b'W')
            print("Whistle detected with high confidence - Signal sent to Arduino")
    else:
        print("No confident sound detected")

# Start recording and processing audio
print("Recording audio...")
stream = p.open(format=FORMAT, channels=CHANNELS,
                rate=RATE, input=True,
                frames_per_buffer=CHUNK)

try:
    while True:
        frames = []
        for _ in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
            data = stream.read(CHUNK, exception_on_overflow=False)
            frames.append(np.frombuffer(data, dtype=np.int16))

        # Prepare the audio input for model inference
        audio_input = np.hstack(frames).astype(np.float32)
        if np.max(np.abs(audio_input)) != 0:  # Avoid division by zero
            audio_input = audio_input / np.max(np.abs(audio_input))  # Normalize the audio

        # Process and recognize the sound
        recognize_sound(audio_input)

except KeyboardInterrupt:
    print("Stopping recording...")

# Clean up
stream.stop_stream()
stream.close()
p.terminate()
arduino.close()
