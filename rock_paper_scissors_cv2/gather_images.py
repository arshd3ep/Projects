desc = '''Script to gather data images with a particular label.
Usage: python gather_images.py <label_name> <num_samples>
The script will collect <num_samples> number of images and store them
in its own directory.
Only the portion of the image within the box displayed
will be captured and stored.
Press 's' to start/pause the image collecting process.
Press 'q' to quit.
'''

import os
import cv2
import sys

try:
	label_name = sys.argv[1]
	num_sample = int(sys.argv[2])
except:
	print("Argument missing")
	print(desc)
	exit(-1)

IMG_SAVE_PATH = 'image_data'
IMG_CLASS_PATH = os.path.join(IMG_SAVE_PATH, label_name)

try:
	os.mkdir(IMG_SAVE_PATH)
except FileExistsError:
	pass

try:
	os.mkdir(IMG_CLASS_PATH)
except FileExistsError:
	print("{} directory already exist".format(IMG_SAVE_PATH))
	print("All images will be saved along with the existing items in the folder")

cap = cv2.VideoCapture(0)

start = False
count = 0

while True:

	ret, frame = cap.read()
	if not ret:
		continue

	if count==num_sample:
		break

	cv2.rectangle(frame, (80, 80), (280, 280), (255, 255, 255), 2)

	if start:
		roi = frame[80:280, 80:280]
		save_path = os.path.join(IMG_CLASS_PATH, "{}.jpg".format(count+1))
		cv2.imwrite(save_path, roi)
		count+=1

	font = cv2.FONT_HERSHEY_SIMPLEX
	cv2.putText(frame, "Collecting {}".format(count), (5, 50), font, 0.7, (0, 255, 255), 2)
	cv2.imshow("Collecting Images", frame)

	k = cv2.waitKey(10)

	if k==ord('s'):
		start = not start
	if k==ord('q'):
		break

print("{} images are saved to {}".format(count, IMG_CLASS_PATH))
cap.release()
cv2.destroyAllWindows()