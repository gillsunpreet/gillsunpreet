import serial
import pygame
import time
import os
import random

# Initialize serial communication with Arduino
ser = serial.Serial('COM12', 9600)  # Replace 'COMx' with your actual COM port
time.sleep(2)  # Wait for the serial connection to initialize

# Initialize pygame for audio playback
pygame.mixer.init()

# Dictionary to store decade and corresponding folder paths
decade_folders = {
    "Play 1980s": "C:/Users/preet/Downloads/A_413/1980s/",
    "Play 1990s": "C:/Users/preet/Downloads/A_413/1990s/",
    "Play 2000s": "C:/Users/preet/Downloads/A_413/2000s/",
    "Play 2010s": "C:/Users/preet/Downloads/A_413/2010s/"
}

def play_random_song(decade):
    """Play a random song from the folder corresponding to the given decade."""
    if decade in decade_folders:
        folder_path = decade_folders[decade]
        
        # Get a list of all MP3 files in the folder
        songs = [file for file in os.listdir(folder_path) if file.endswith('.mp3')]
        
        if songs:
            # Select a random song from the list
            song = random.choice(songs)
            song_path = os.path.join(folder_path, song)
            
            # Load and play the selected song
            pygame.mixer.music.load(song_path)
            pygame.mixer.music.play()
            print(f"Playing random {decade} song: {song}")
        else:
            print(f"No songs found in folder for {decade}.")
    else:
        print("Decade not recognized.")

try:
    while True:
        # Read from serial (from Arduino)
        if ser.in_waiting > 0:
            decade = ser.readline().decode().strip()
            print(f"Received decade: {decade}")
            
            # Play a random song from the corresponding folder
            play_random_song(decade)

except KeyboardInterrupt:
    print("Program terminated")

finally:
    ser.close()
    pygame.mixer.quit()
