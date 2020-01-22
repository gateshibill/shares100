# -*- coding: utf-8 -*-
"""
Created on Fri Jun 21 23:00:45 2019

@author: Administrator
"""
import threading
import sys
import json
import time
from ws4py.client.threadedclient import WebSocketClient
from peer import Peer
from udp import UdpUtil

serverIp="120.24.47.44"
wsPort=8131
class CG_Client(WebSocketClient):
     
    udpUtil=UdpUtil()
    def opened(self):
        req = '{"event":"login", "content":""}'
        self.send(req)

    def closed(self, code, reason=None):
        print("Closed down:", code, reason)

    def received_message(self, message):
          print("resp:",message)
          
          msg = json.loads(str(message))
          if type(msg) is dict:
               event = msg['event']
               content = str(msg["content"])
               print('event:', event)
               print('content:', content)

          if("sendUdp"==event):    #1    
               print("sendUdp")
               requstId = int(msg["requstId"])
               
               content=content.replace("\'", '\"')
               peerdict=json.loads(content)
               peerA=Peer()
               peerA.__dict__=peerdict
               print("peerA:",peerA.ip,peerA.port) 

               #start udp channel for peeA              
               #不能用本地IP，需要用对应的公网IP，同时给A和Server发UDP请求
               peerBIp=content = str(msg["peerIp"])
               print("start udp client:",peerA.ip,peerA.port,serverIp,self.udpUtil.udpPort)
               self.udpUtil.startAsynUDPClient(peerA.ip,self.udpUtil.udpPort,serverIp,self.udpUtil.udpPort)
               #保证Udp启动成功
               time.sleep(3)
               #send PeerB to server
               peer=Peer(0,"test",peerBIp,wsPort,self.udpUtil.udpPort)   
               peerJson=json.dumps(peer.__dict__, sort_keys=True, indent=4, separators=(',', ':'))               
               message = ('{"event":"peerIsReady","requstId":%d, "content":%s}' % (requstId,peerJson))
               self.send(message)
               print("peerB send message to server:",message)
               
          elif("peerIsReady"==event):#2 
               print("peerIsReady")
   
               content=content.replace("\'", '\"')
               peerdict=json.loads(content)
               peerB=Peer()
               peerB.__dict__=peerdict
              
               #start udp channel                 
                #用公网地址
               peerAIp=content = str(msg["peerIp"])
               
               self.udpUtil.startUDPServer(peerAIp,self.udpUtil.udpPort)
               print("peerA updserver is reay:",peerAIp,self.udpUtil.udpPort) 
               
               #这里的peerB.udpPort是clientUdpPort
               #sendUpdtoCientB for  three times,
               self.udpUtil.startUDPClient(peerB.ip,peerB.udpPort);
               print("peerA sended upd to :",peerB.ip,peerB.udpPort) 
               
          elif("udpIsReady"==event):#3
               print("udpIsReady")
               #requst for file
          elif("requestFile"==event):#4
               print("requestFile") 
               #send file UPD to clientA
          elif("fileIsReady"==event):#5
               print("FileIsReady") 
               # start to receive upd file
          elif("fileReceiving"==event):#6
               print("FileReceiving")
               
          elif("udpFileReceiveFinshed"==event):#7
               print("udpFileReceiveFinshed")
               # close udp socket
          else:
               print("client unknow message")                            
   
def startConnetServer(ws):
    try:
        ws.connect()
        ws.run_forever()
    except KeyboardInterrupt:
        ws.close()

def startClient():
    print("startClient()")
    ws = None
    try:
        ws = CG_Client('ws://%s:%d' %(serverIp,wsPort))
        t1 = threading.Thread(target=startConnetServer,args=(ws, ))
        t1.start()
        
        print ("started")
        time.sleep(1)
        print ("sleeped")
        
        while True:
            print("e:exit,h:heartbeat,num is requst for hole")
            msg = input(">> ").strip()
            if("e"==msg):
               print("exit")
               sys.exit(0)
            elif("h"==msg):
                print("test heartbeat") 
                message = '{"event":"hearbeat", "content":{"id":"1","name":"bill","ip":"12.168.1.1"}}'
                ws.send(message)  
            else:
               print("requst for hole") 
               id = int(msg)              
               message = ('{"event":"hole","peerId":%d,"content":""}' % id)
               ws.send(message)    

    except KeyboardInterrupt:
        ws.close()
        
if __name__ == "__main__":               
    
    startClient()

    print("ok")        
     
    
    
    
        