import cv2
import dlib
from math import atan2, degrees

import serial
ser = serial.Serial('COM9', 9600) 

# Load pre-trained face detector and shape predictor
detector = dlib.get_frontal_face_detector()
predictor = dlib.shape_predictor("shape_predictor_68_face_landmarks.dat")

# Load video capture
cap = cv2.VideoCapture(0)  # Use 0 for webcam, or provide the path to a video file

# Constants for eye landmark points
LEFT_EYE_POINTS = list(range(36, 42))
RIGHT_EYE_POINTS = list(range(42, 48))

def get_eye_center(landmarks, eye_points):
    eye_center_x = sum([landmarks.part(i).x for i in eye_points]) / len(eye_points)
    eye_center_y = sum([landmarks.part(i).y for i in eye_points]) / len(eye_points)
    return int(eye_center_x), int(eye_center_y)



while True:
    ret, frame = cap.read()
    if not ret:
        break

    # Convert the image to grayscale
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Detect faces in the grayscale frame
    faces = detector(gray)

    print("FACE NOT DETECTED")

    ser.write(b"S")
    print("")
           

    for face in faces:
        # Predict facial landmarks
        landmarks = predictor(gray, face)

        # Get center points of left and right eyes
        left_eye_center = get_eye_center(landmarks, LEFT_EYE_POINTS)
        right_eye_center = get_eye_center(landmarks, RIGHT_EYE_POINTS)

        # Calculate the angle between the line connecting eye centers and horizontal axis
        dy = right_eye_center[1] - left_eye_center[1]
        dx = right_eye_center[0] - left_eye_center[0]
        angle = degrees(atan2(dy, dx))

        print(angle)

        # Detect left or right head movement

        if angle:

            if angle < -15:
                direction = 'LEFT'
                ser.write(b"L")
            elif angle > 15:
                direction = 'RIGHT'
                ser.write(b"R")
            else:
                direction = 'Straight'
                ser.write(b"F")
        else:

           # ser.write(b"S")
            print("")
    


        # Display direction on the frame
        cv2.putText(frame, f"Head Direction: {direction}", (50, 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)

    # Display the frame
    cv2.imshow("Head Direction Detection", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# Release video capture and close windows
cap.release()
cv2.destroyAllWindows()
