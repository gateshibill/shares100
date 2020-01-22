# -*- coding: utf-8 -*-
"""
Created on Thu Jun 20 17:19:51 2019

@author: THP6
"""

import websocket
import time
import threading
import sys

global wsmap
class client:
   #global ws
   #global ws
   #def connWebsocketServer(self):
      def on_message(ws, message):
          #print(ws)
          print(message)
      
      
      def on_error(ws, error):
          #print(ws)
          print(error)
      
      
      def on_close(ws):
          print(ws)
          print("### closed ###")
      
      def on_open(ws):
         ws.send("Hello %d")
         
      def setws(self,ws):
          self.ws=ws
     
    
      def sendMsgtoServer(self):
        self.ws.send("requst for hole peer")

def startConnetServer(client):
     #client =client()
     websocket.enableTrace(True)
     ws = websocket.WebSocketApp("ws://192.168.30.30:8131",
                                  on_message=client.on_message,
                                  on_error=client.on_error,
                                  on_close=client.on_close)
          #msg=ws.getBasicRemote().sendText("sdfad"); 
          #print ("back msg:",msg)
     client.setws(ws)
     #ws.on_open = client.on_open
     ws.run_forever()
      
      
if __name__ == "__main__":
     client =client()
     t1 = threading.Thread(target=startConnetServer,args=(client, ))
     t1.start()
     
     
     while True:
       msg = input(">> ").strip()
       if("1"==msg):
          print("exit")
          sys.exit(0)
       elif("2"==msg):
          print("send for peer")  
          client.sendMsgtoServer();
     
     time.sleep(30000)
     
     print("ok")
     
     