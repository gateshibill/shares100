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


class CG_Client(WebSocketClient):

    def opened(self):
        req = '{"event":"subscribe", "channel":"eth_usdt.deep"}'
        self.send(req)

    def closed(self, code, reason=None):
        print("Closed down:", code, reason)

    def received_message(self, resp):
        print("resp:",resp)
        #resp = json.loads(str(resp))
        
        '''
        data = resp['data']
        if type(data) is dict:
            ask = data['asks'][0]
            print('Ask:', ask)
            bid = data['bids'][0]
            print('Bid:', bid)
        '''
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
               print("send for peer")  
            else:
                ws.send(msg)
          
    except KeyboardInterrupt:
        ws.close()
        
if __name__ == "__main__":               
    startClient()
    print("ok")        
        
        