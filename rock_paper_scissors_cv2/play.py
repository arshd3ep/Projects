from keras.models import load_model
import cv2
import numpy as np 
import sys
from random import choice

REV_CLASS_MAP = {
	0:"rock",
	1:"paper",
	2:"scissors",
	3:"none"
}

def mapper(val):
	return REV_CLASS_MAP[val]

def calculate_winner(move1,move2):
	if move1 == "rock":
		if move2 == "paper":
			return "Computer"
		if move2 == "scissors":
			return "User"

	if move1 == "paper":
		if move2 == "scissors":
			return "Computer"
		if move2 == "rock":
			return "User"

	if move1 == "scissors":
		if move2 == "rock":
			return "Computer"
		if move2 == "paper":
			return "User"

model = load_model("rock-paper-scissors-model.h5")

cap = cv2.VideoCapture(0)

prev_move = None 

while True:
	ret, frame = cap.read()

	if not ret:
		continue

	cv2.rectangle(frame, (80, 80), (280, 280), (255, 255, 255), 2)
	cv2.rectangle(frame, (360, 80), (560, 280), (255, 255, 255), 2)

	roi = frame[80:280, 80:280]
	img = cv2.cvtColor(roi, cv2.COLOR_BGR2RGB)
	img = cv2.resize(img, (277, 277))

	pred = model.predict(np.array([img]))
	move_code = np.argmax(pred[0])
	user_move_name = mapper(move_code)

	if prev_move != user_move_name:
		if user_move_name != "none":
			computer_move_name = choice(['rock', 'paper', 'scissors'])
			winner = calculate_winner(user_move_name, computer_move_name)
		else:
			computer_move_name = 'none'
			winner = "waiting..."

	prev_move = user_move_name

	font = cv2.FONT_HERSHEY_SIMPLEX
	cv2.putText(frame, "Your Move: " + user_move_name, (70, 50), font, 0.7, (0, 255, 0), 2, cv2.LINE_AA)
	cv2.putText(frame, "Computer Move: " + computer_move_name, (330, 50), font, 0.7, (255, 0, 0), 2, cv2.LINE_AA)
	cv2.putText(frame, "Winner: " + str(winner), (180, 380), font, 1.1, (255, 255, 255), 4, cv2.LINE_AA)

	if computer_move_name != "none":
		icon = cv2.imread("images/{}.png".format(computer_move_name))
		icon = cv2.resize(icon, (200, 200))
		frame[80:280, 360:560] = icon

	cv2.imshow("Rock Paper Scissors", frame)

	k = cv2.waitKey(10)

	if k == ord('q'):
		break

cap.release()
cv2.destroyAllWindows()