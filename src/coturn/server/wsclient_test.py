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



class CG_Client(WebSocketClient):

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
               #start udp channel for peeA 
               #send upd to peerA for three times 
               content=content.replace("\'", '\"')
               peerdict=json.loads(content)
               peerA=Peer()
               peerA.__dict__=peerdict
               peerAIp=peerdict["ip"]
               peerAUdpPort=peerdict["udpPort"]
               print("peerAIp:",peerAIp) 
               print("peerAUdpPort:",peerAUdpPort) 
               

               
          elif("peerIsReady"==event):#2
               print("peerIsReady")
               #start udp channel 
               #sendUpdtoCientB for  three times,
               
               
               
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
        ws = CG_Client('ws://192.168.254.107:8131')
        t1 = threading.Thread(target=startConnetServer,args=(ws, ))
        t1.start()
        
        print ("started")
        time.sleep(1)
        print ("sleeped")
        
        while True:
            msg = input(">> ").strip()
            if("e"==msg):
               print("exit")
               sys.exit(0)
            elif("2"==msg):
               print("requst for hole") 
               message = '{"event":"hole", "content":{"id":"1","name":"bill","ip":"12.168.1.1"}}'
            elif("3"==msg):
               print("send message to peer") 
               message = '{"event":"hole", "content":{"id":"1","name":"bill","ip":"12.168.1.1"}}'               
            else:
                print("test beartbeat") 
                message = '{"event":"hearbeat", "content":{"id":"1","name":"bill","ip":"12.168.1.1"}}'
                ws.send(message)
          
    except KeyboardInterrupt:
        ws.close()
        
if __name__ == "__main__":               
    startClient()
    message = '{"event":"hole", "content":{"id":1,"ip":"12.168.1.1"}}'
    msg = json.loads(str(message))
    content =str( msg["content"])
    content=content.replace("\'", '\"')
    
    peerdict=json.loads(content)
    p=Peer()
    p.__dict__=peerdict
    print("peer:",peerdict["ip"])
    print("ok")        
        
        