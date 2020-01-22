import cv2

cap = cv2.VideoCapture('rtsp://192.168.10.143//jpeg')
#判断是否正常开启
print  (cap.isOpened())
frameNum = 1
while(cap.isOpened()):
    ret,frame = cap.read()
    print (ret)
    print(frame)
    frameNum = frameNum + 1
    cv2.imshow('frame',frame)
#每10帧存储一张图片
    if frameNum%10 == 1:
        cv2.imwrite('image'+str(frameNum)+'.jpg',frame)
    if cv2.waitKey(1) == ord('q'):
        break
cap.release()
cv2.destroyAllWindows()