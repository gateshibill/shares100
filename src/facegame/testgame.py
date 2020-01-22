# -*- coding: cp936 -*-
import cv2
import numpy
import time
import random
import os

def judge():
    # 构造一个3×3的结构元素
    # return 0 stone ,1 jiandao, 2 bu
    img = cv2.imread("wif.jpg", 0)
    element = cv2.getStructuringElement(cv2.MORPH_RECT, (11, 11))
    dilate = cv2.dilate(img, element)
    erode = cv2.erode(img, element)
    # 将两幅图像相减获得边，第一个参数是膨胀后的图像，第二个参数是腐蚀后的图像
    result = cv2.absdiff(dilate, erode);
    # 上面得到的结果是灰度图，将其二值化以便更清楚的观察结果
    retval, result = cv2.threshold(result, 40, 255, cv2.THRESH_BINARY);

    # 反色，即对二值图每个像素取反
    result = cv2.bitwise_not(result);
    result = cv2.medianBlur(result, 23)
    a = []
    posi = []
    width = []
    count = 0
    area = 0
    for i in range(int(result.shape[1])):
        for j in range(result.shape[0]):
            if (result[j][i] == 0):
                area += 1
    for i in range(int(result.shape[1])):
        #print (result)
        if (result[5 * int(result.shape[0]/16)][i] == 0 and result[int(5 * result.shape[0]/16)][i - 1] != 0):
            count += 1
            width.append(0)
            posi.append(i)
        if (result[5 * int(result.shape[0]/16)][i] == 0):
            width[count - 1] += 1
    """
    print 'the pic width is ',result.shape[1],'\n'
    for i in range(count):
        print 'the ',i,'th',' ','is';
        print 'width ',width[i]
        print 'posi ',posi[i],'\n'
    print count,'\n'
    print 'area is ',area,'\n'

    cv2.line(result,(0,5*result.shape[0]/16),(214,5*result.shape[0]/16),(0,0,0))
    cv2.namedWindow("fcuk")
    cv2.imshow("fcuk",result)
    cv2.waitKey(0)
    """
    # 判定时间

    width_length = 0
    width_jiandao = True
    print(count)
    for i in range(count):
        print (width[i],area)
        if width[i] > 45:
            # print 'bu1';
            return 2;
        if width[i] <= 10 or width[i] >= 40:
            width_jiandao = False
        width_length += width[i]
    if width_jiandao == True and count == 2:
        return 1;
    if (area < 8500):
        # print 'shi tou';
        return 0;
    print ("width_leng", width_length)
    if (width_length < 35):
        # 这个时候说明照片是偏下的，所以需要重新测定。
        a = []
        posi = []
        width = []
        count = 0
        for i in range(result.shape[1]):
            if (result[11 * int(result.shape[0]/16)][i] == 0 and result[11 * int(result.shape[0]/16)][i - 1] != 0):
                count += 1
                width.append(0)
                posi.append(i)
            if (result[11 * int(result.shape[0]/16)][i] == 0):
                width[count - 1] += 1
        """
        print 'the pic width is ',result.shape[1],'\n'
        for i in range(count):
            print 'the ',i,'th',' ','is';
            print 'width ',width[i]
            print 'posi ',posi[i],'\n'
        print count,'\n'
        print 'area is ',area,'\n'
        """
    width_length = 0
    width_jiandao = True

    for i in range(count):
        print(width[i])
        if width[i] > 45:
            # print 'bu1';
            return 2;
        if width[i] <= 20 or width[i] >= 40:
            width_jiandao = False
        width_length += width[i]
    if width_jiandao == True and count == 2:
        return 1;
    if (area > 14000 or count >= 3):
        # print 'bu2';
        return 2;
    if (width_length < 110):
        # print 'jian dao';
        return 1;
    else:
        # print 'bu3';
        return 2;


"""
print("这是通过摄像头来玩的剪刀石头布的游戏，输入y开始\n")
s = raw_input()
capture = cv2.VideoCapture(0)
cv2.namedWindow("camera",1)
start_time = time.time()
print("给你5秒的时间把手放到方框的位置\n")
while(s=='y' or s=='Y'):
    ha,img =capture.read()
    end_time = time.time()
    cv2.rectangle(img,(426,0),(640,250),(170,170,0))
    cv2.putText(img,str(int((5-(end_time- start_time)))), (100,100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
    cv2.imshow("camera",img)

    if(end_time-start_time>5):
        break
    if(cv2.waitKey(30)>=0):
        break
ha,img = capture.read()
capture.release()
cv2.imshow("camera",img)
img = img[0:210,426:640]
cv2.imwrite("wif.jpg",img)
judge()  
cv2.waitKey(0)
print "fuck"
"""


def game():
    fuck = []
    fuck.append("石头")
    fuck.append("剪刀")
    fuck.append("布")
    capture = cv2.VideoCapture(0)
    cv2.namedWindow("camera", 1)
    start_time = time.time()
    print("给你5秒的时间把手放到方框的位置")
    while (1):
        ha, img = capture.read()
        end_time = time.time()
        cv2.rectangle(img, (400, 0), (640, 250), (170, 170, 0))
        cv2.putText(img, str(int((5 - (end_time - start_time)))), (100, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
        cv2.imshow("camera", img)
        if (end_time - start_time > 5):
            break
        if (cv2.waitKey(30) >= 0):
            break
    ha, img = capture.read()
    capture.release()
    cv2.imshow("camera", img)
    img = img[0:210, 426:640]
    cv2.imwrite("wif.jpg", img)
    p1 = judge()
    pc = random.randint(0, 2)
    # print p1,' ',pc,'\n'
    print ("你出的是", fuck[p1], " 电脑出的是", fuck[pc])
    cv2.destroyAllWindows()
    if (p1 == pc):
        print ("平局")
        return 0
    if ((p1 == 0 and pc == 1) or (p1 == 1 and pc == 2) or (p1 == 2 and pc == 0)):
        print ('你赢了')
        return 1
    else:
        print ('你输了')
        return -1


def main():
    you_win = 0
    pc_win = 0
    print("这是通过摄像头来玩的剪刀石头布的游戏，请输入回车开始游戏")
    s = input()
    while (1):
        print ("比分(玩家：电脑) ", you_win, ":", pc_win)
        s = input()
        os.system('cls')
        ans = game()
        if (ans == 1):
            you_win += 1
        elif (ans == -1):
            pc_win += 1
        print ("为了减少误判，请尽可能将手占据尽可能大的框框")


main()