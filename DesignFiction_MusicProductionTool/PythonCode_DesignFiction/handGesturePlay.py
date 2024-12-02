import serial
import pygame
import time
import os
import cv2
import mediapipe as mp

# Initialize serial communication with Arduino
ser = serial.Serial('COM12', 9600)  # Replace 'COMx' with your actual COM port
time.sleep(2)  # Wait for the serial connection to initialize

# Initialize pygame for audio playback
pygame.mixer.init()

# Mediapipe setup for hand tracking and face mesh
mp_hands = mp.solutions.hands
mp_face_mesh = mp.solutions.face_mesh
hands = mp_hands.Hands(static_image_mode=False, max_num_hands=2, min_detection_confidence=0.5)
face_mesh = mp_face_mesh.FaceMesh(static_image_mode=False, max_num_faces=1, min_detection_confidence=0.5)
mp_draw = mp.solutions.drawing_utils

# Dictionary to store instruments and corresponding folder paths
instrument_folders = {
    "Recording": "C:/Users/preet/Downloads/ADesignFiction/Recording/",
    "Drums": "C:/Users/preet/Downloads/ADesignFiction/Drums/",
    "Bass": "C:/Users/preet/Downloads/ADesignFiction/Bass/",
    "Guitars": "C:/Users/preet/Downloads/ADesignFiction/Guitars/"
}

# Variables to track the last played sound
last_play_time = 0
sound_interval = 0.2  # Time between consecutive sound plays (in seconds)
last_nose_y = None  # Track previous y-coordinate of the nose tip


def play_sound(file_path):
    """Play a specific sound once."""
    global last_play_time
    current_time = time.time()

    # Check if the time since the last play exceeds the interval
    if current_time - last_play_time >= sound_interval:
        if os.path.exists(file_path):
            pygame.mixer.Sound(file_path).play()  # Play sound once
            print(f"Playing: {file_path}")
            last_play_time = current_time
        else:
            print(f"File not found: {file_path}")


def detect_gesture_and_head_nod(instrument):
    cap = cv2.VideoCapture(0)
    both_toggle = 0
    if not cap.isOpened():
        print("Error: Unable to access the webcam.")
        return

    global last_nose_y

    while True:
        ret, frame = cap.read()
        if not ret:
            print("Error: Failed to capture frame.")
            break

        frame = cv2.flip(frame, 1)
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

        hand_results = hands.process(rgb_frame)
        face_results = face_mesh.process(rgb_frame)

        left_hand_raised = False
        right_hand_raised = False

        if hand_results.multi_hand_landmarks:
            for hand_landmarks, handedness in zip(hand_results.multi_hand_landmarks, hand_results.multi_handedness):
                hand_label = handedness.classification[0].label
                wrist_y = hand_landmarks.landmark[mp_hands.HandLandmark.WRIST].y
                middle_tip_y = hand_landmarks.landmark[mp_hands.HandLandmark.MIDDLE_FINGER_TIP].y

                if wrist_y > middle_tip_y + 0.05:
                    if hand_label == "Left":
                        left_hand_raised = True
                    elif hand_label == "Right":
                        right_hand_raised = True

                mp_draw.draw_landmarks(frame, hand_landmarks, mp_hands.HAND_CONNECTIONS)

        gesture = None
        if left_hand_raised and right_hand_raised:
            gesture = "Both.mp3"
        elif left_hand_raised:
            gesture = "Left.mp3"
            ser.write(b"Left\n")
        elif right_hand_raised:
            gesture = "Right.mp3"
            ser.write(b"Right\n")

        if gesture:
            file_path = os.path.join(instrument_folders[instrument], gesture)
            play_sound(file_path)

        # Head Nod Detection
        if face_results.multi_face_landmarks:
            for face_landmarks in face_results.multi_face_landmarks:
                # Get the nose tip y-coordinate (landmark index 1)
                nose_y = face_landmarks.landmark[1].y

                if last_nose_y is not None:
                    # Determine head movement direction
                    if last_nose_y - nose_y > 0.02:  # Threshold for nodding up
                        ser.write(b"Head\n")  # Send 'Head' to Arduino
                        file_path = os.path.join(instrument_folders[instrument], "HeadUp.mp3")
                        play_sound(file_path)

                last_nose_y = nose_y
                mp_draw.draw_landmarks(frame, face_landmarks, mp_face_mesh.FACEMESH_TESSELATION)


        if ser.in_waiting > 0:
            command = ser.readline().decode().strip()
            if command == "Both":
                # Toggle between Both1.mp3 and Both2.mp3
                if both_toggle == 0:
                    
                    file_path = os.path.join(instrument_folders[instrument], "Both1.mp3")
                    both_toggle = 1  # Switch to the other file for next press
                else:
                    file_path = os.path.join(instrument_folders[instrument], "Both2.mp3")
                    both_toggle = 0  # Switch back to the first file
                #file_path = os.path.join(instrument_folders[instrument], "Both.mp3")
                play_sound(file_path)

        cv2.imshow("Hand Gesture and Head Nod Detection", frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()


try:
    while True:
        # Read from serial (from Arduino)
        if ser.in_waiting > 0:
            instrument = ser.readline().decode().strip()
            print(f"Received instrument: {instrument}")

            # Validate the instrument
            if instrument in instrument_folders:
                print(f"Instrument selected: {instrument}")
                print("Starting webcam for gesture and head movement detection...")
                detect_gesture_and_head_nod(instrument)
            else:
                print(f"Unknown instrument: {instrument}")

except KeyboardInterrupt:
    print("Program terminated.")

finally:
    ser.close()
    pygame.mixer.quit()
