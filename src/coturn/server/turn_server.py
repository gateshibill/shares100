
from peer import Peer      
from websocket_server import WebsocketServer
import json
import datetime
import time
import threading
import sys
from wsclient import startClient
from udp import UdpUtil,get_host_ip

class turnServer:     
      clientMap={}
      peerMap={}
      holeMap={}#id,peer,according requestid to response peer.
      # 当新的客户端连接时会提示
      def __init__(self):
          print ("new turne server")
          self.udpUtil= UdpUtil()
          
      def new_client(self,client, server):
          print("当新的客户端连接时会提示:%s" % client['id'])

          self.clientMap[client['id']]=client
          print("ip:",client["address"])

      # 当旧的客户端离开
      def client_left(self,client, server):
          print("客户端%s断开" % client['id'])
          #del self.clientMap[client['id']]
          
      # 接收客户端的信息。
      def message_received(self,client, server, message):
          print("server：",message)
          clientId=client['id']
          clientIp=client["address"][0]
          clientPort=int(client["address"][1])
          print("Client(%d) said: %s" % (clientId, message))
          print("client ip:",clientIp)
          print("client port:",clientPort)
          
          #req = ('{"event":"login", "content":"response: %s %s"}'% (clientIp,clientPort))
          #server.send_message(client,req)
          
          msg = json.loads(str(message))
          if type(msg) is dict:
               event = msg['event']
               content = str(msg["content"])
               print('event:', event)
               print('content:', content)
          if("login"==event):    #login      
               self.clientMap[clientId]=client
               print("login")
          elif("heartbeat"==event):#heartbeat
               print("heartbeat")
               self.clientMap[client["id"]]=client
          elif("hole"==event):#requst for hole
               requstId = int(time.time()) 
               print("requst for hole:",requstId)
               self.holeMap[requstId]=client  #requst client,
               
               #获取peerB公网IP，发给B，创建UPDServer需要用
               peerBId=int(msg["peerId"])
               clientB=self.clientMap[peerBId]
               clientBIp=clientB["address"][0]
               if(clientB!=None):
                    #使用服务里保存的peer的IP和端口,不使用自报的局域网IP
                    print ("peerA:",clientIp,clientPort)
                    peerA=Peer(clientId,"test",clientIp,clientPort,self.udpUtil.udpPort)   
                    peerJson=json.dumps(peerA.__dict__, sort_keys=True, indent=4, separators=(',', ':'))               
                    message = ('{"event":"sendUdp","requstId":%d,"peerIp":"%s","content":%s}' % (requstId,clientBIp,peerJson))
                    server.send_message(clientB,message)
               else:
                    message= '{"event":"error", "content":"peer not exist"}'
                    server.send_message(clientB,message)
               
          elif("peerIsReady"==event):#peer is ready
               print("peer is reay")
               requstId = int(msg["requstId"])
               clientA=self.holeMap[requstId]
              
               #获取客户端B的UDP端口
               print("udpClientMap:",self.udpUtil.udpClientMap)
               clientUdpPort=self.udpUtil.udpClientMap[0][1]
               print("peerB.clientUdpPort:",clientUdpPort)
               peerB=Peer(0,"test",clientIp,clientPort,clientUdpPort)  
                #使用服务里保持peerIP,不使用自报的局域网IP
               clientAIp=clientA["address"][0]
               peerJson=json.dumps(peerB.__dict__, sort_keys=True, indent=4, separators=(',', ':'))               
               message = ('{"event":"peerIsReady","peerIp":"%s", "content":%s}' %(clientAIp, peerJson))
               server.send_message(clientA,message)
               
          else:
               print("server unknow message")
          
      def startWebsocket(self):
          print ("turnServer startWebsocket")
          self.server = WebsocketServer(8131, "0.0.0.0")
          self.server.set_fn_new_client(self.new_client)
          self.server.set_fn_client_left(self.client_left)
          self.server.set_fn_message_received(self.message_received)
          self.server.run_forever()
          print ("turnServer startWebsocket end")

def startWebsocket(ts):
    print ("startWebsocket")
    #ts=turnServer();
    ts.startWebsocket()    
    print ("startWebsocket end")
          
if __name__ == '__main__':
    ts=None
    try:
        print ("start") 
        ts=turnServer();
        thread = threading.Thread(target=startWebsocket,args=(ts, ))
        thread.start()
        print ("start2ed")
        serverIp=get_host_ip()
        ts.udpUtil.startUDPServer(serverIp,ts.udpUtil.udpPort)
            
        startClient()
        '''
        while True:
            msg = input(">> ").strip()
            if("e"==msg):
                print("exit")
                sys.exit(0)
            elif("2"==msg):
                print("live")
            else:
                print("cmd:",msg)
        '''
        time.sleep(10000)
        
    except:
        print ("Error: unable to start thread")      
    
    
    
    
    
    