import cv2

cap = cv2.VideoCapture(0)
num=0
name=""
while(1):
    # get a frame
    ret, frame = cap.read()
    # show a frame
    num=num+1
    cv2.imshow("capture", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        name="/F/AI/image/"+str(num)+".jpeg"
        cv2.imwrite(name, frame)
       # break
cap.release()
cv2.destroyAllWindows()
