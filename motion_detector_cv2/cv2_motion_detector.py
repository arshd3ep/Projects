import cv2

first_frame = None

video = cv2.VideoCapture(0)

while video.isOpened():

    check, frame = video.read()

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (21, 21), 0)

    if first_frame is None:
        first_frame = gray
        continue

    delta_frame = cv2.absdiff(first_frame, gray)
    _, thresh_delta = cv2.threshold(delta_frame, 30, 255, cv2.THRESH_BINARY)
    thresh_delta = cv2.dilate(thresh_delta, None, iterations=3)

    cnts, _ = cv2.findContours(thresh_delta.copy(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    for contour in cnts:
        (x, y, w, h) = cv2.boundingRect(contour)

        if cv2.contourArea(contour) < 900:
            continue

        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)

    cv2.imshow('Frame', frame)
    cv2.imshow('Capturing', gray)
    cv2.imshow('Delta', delta_frame)
    cv2.imshow('Thresh', thresh_delta)
    key = cv2.waitKey(1)
    if key == ord('q'):
        break

video.release()
cv2.destroyAllWindows()