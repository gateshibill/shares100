from skimage import io,transform
import tensorflow as tf
import numpy as np
import cv2
from PIL import Image
from websocket_server import WebsocketServer
import threading
import time

PORT = 9001
server = WebsocketServer(PORT, "0.0.0.0")

path0 = "F:/AI/finger/two/100.jpg"
path1 = "F:/AI/finger/one/100.jpg"
path2 = "F:/AI/finger/zero/60.jpg"

#path0 = "F:/AI/flower_photos/8.jpg"
#path1 = "F:/AI/flower_photos/daisy/5547758_eea9edfd54_n.jpg"
#path2 = "F:/AI/flower_photos/dandelion/7355522_b66e5d3078_m.jpg"
#path3 = "F:/AI/flower_photos/roses/394990940_7af082cf8d_n.jpg"
#path4 = "F:/AI/flower_photos/sunflowers/6953297_8576bf4ea3.jpg"
#path5 = "F:/AI/flower_photos/tulips/10791227_7168491604.jpg"

flower_dict = {0:'one',1:'two',2:'zero',3:'sunflowers',4:'tulips'}

w=100
h=100
c=3

def read_one_image(path):
    img = io.imread(path)
    img = transform.resize(img,(w,h))
    return np.asarray(img)


def look_gesture(data0):
 with tf.Session() as sess:
    data = []
    #data0 = read_one_image(path0)
    #data1 = read_one_image(path1)
    #data2 = read_one_image(path2)
   # data3 = read_one_image(path3)
    #data4 = read_one_image(path4)
    #data5 = read_one_image(path5)
    data.append(data0)
   # data.append(data1)
    #data.append(data2)
    #data.append(data3)
    #data.append(data4)
    #data.append(data5)

    saver = tf.train.import_meta_graph('F:/AI/finger/model.ckpt.meta')
    saver.restore(sess,tf.train.latest_checkpoint('F:/AI/finger/'))

   #saver = tf.train.import_meta_graph('F:/AI/flower_photos/model.ckpt.meta')
   #saver.restore(sess,tf.train.latest_checkpoint('F:/AI/flower_photos/'))
    
    graph = tf.get_default_graph()
    x = graph.get_tensor_by_name("x:0")
    feed_dict = {x:data}

    logits = graph.get_tensor_by_name("logits_eval:0")

    classification_result = sess.run(logits,feed_dict)

    #打印出预测矩阵
    print(classification_result)
    #打印出预测矩阵每一行最大值的索引
    print(tf.argmax(classification_result,1).eval())
    #根据索引通过字典对应花的分类
    output = []
    output = tf.argmax(classification_result,1).eval()
    for i in range(len(output)):
        print("第",i+1,"种预测:"+flower_dict[output[i]])
        cmd="第",i+1,"种预测:"+flower_dict[output[i]]
        server.send_message_to_all(flower_dict[output[i]])

# Called for every client connecting (after handshake)
def new_client(client, server):
    print("New client connected and was given id %d" % client['id'])
    server.send_message_to_all("Hey all, a new client has joined us")

#当旧的客户端离开
# Called for every client disconnecting
def client_left(client, server):
    print("Client(%d) disconnected" % client['id'])

#接收客户端的信息。

# Called when a client sends a message
def message_received(client, server, message):
    if len(message) > 200:
        message = message[:200] + '..'
    print("Client(%d) said: %s" % (client['id'], message))

def websocketServer_start():
   server.set_fn_new_client(new_client)
   server.set_fn_client_left(client_left)
   server.set_fn_message_received(message_received)
   server.run_forever()

def thread_run(name):
    print("%s's first thread!!!"% name)
    websocketServer_start()
    #time.sleep(5)

websocketServerThread = threading.Thread(target=thread_run, args=('ws1', ))
websocketServerThread.start()    
    
cap = cv2.VideoCapture('rtsp://192.168.0.110/jpeg')
num=0
print  (cap.isOpened())

while(cap.isOpened()):
    # get a frame
    ret, frame = cap.read()
    #print  (ret)
    if(ret!=True):
       continue
    
    # show a frame
    #cv2.imshow("capture", frame)
    if (cv2.waitKey(1)== ord('q')):
        break
   # name="F://AI//image//"+str(num)+"a.jpg"
    #cv2.imwrite(name, frame)
    #im=Image.open(name)
    #out = im.resize((100,100),Image.ANTIALIAS)
   # im = io.imread(name)
    out = transform.resize(frame,(w,h))
    num=num+1
    if(num%20==0):
      num=0
      look_gesture(out)

cap.release()
cv2.destroyAllWindows()
     
       # break
        
        
        
        
