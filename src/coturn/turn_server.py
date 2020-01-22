
from peer import Peer      
from websocket_server import WebsocketServer
class turnServer:     
      clientMap={}
      peerMap={}
      server=None
      # 当新的客户端连接时会提示
      def __init__(self):
          print ("new turne server")
          
      def new_client(self,client, server):
          print("当新的客户端连接时会提示:%s" % client['id'])
          #self.server.send_message(client,"only for you")
          server.send_message_to_all("Hey all, a new client has joined us")
          #peerMap.put(peer.id,peer)
          self.clientMap[client['id']]=client
          print("ip:",client["address"])
          #print("ip:",client["address"]["port"])
          server.send_message(client,"only for you")
         
       
      # 当旧的客户端离开
      def client_left(self,client, server):
          print("客户端%s断开" % client['id'])
          #del self.clientMap[client['id']]
          
       
      # 接收客户端的信息。
      def message_received(self,client, server, message):
          print("Client(%d) said: %s" % (client['id'], message))
          server.send_message_to_all(message+"+server")
          #这里要解析消息货到客户端信息
      
          #JSONObject msg = new JSONObject(message);
		  #print("收到消息：" + message);
		  #int code = msg.optInt("code");
          code =0
          return
          
          if(0==code):    #login      
               #self.lgoin(client)
               print("login")
          elif(1==code):#heartbeat
               #self.heartbeat(peerId)
               print("heartbeat")
          elif(2==code):#requst for hole
               #self.sendForHole(peer)
               print("requst for hole")
          else:
             print("unknow message")
          
      def login(self,client):
           peer = Peer(client,"ip",8989)
           self.peerMap.put(peer.id,peer)  
                  
      def send(self,clientId,msg):
          print("send message to client")
          self.server.send_message(self.clientMap[clientId],msg)
          
          
      '''
      def sendForHole(peer):
          #1.发送信息给B，让B给A发信息；
          #2.B发送后，返回已经发送；
          return server.send_message(peer,"")
          
      def connect(peerA,peerB):
          return sendForHole(peerB)
          
          
      def heartbeat(peerId):
          peer=peerMap.get(peerId)
          peer.status=0;
          peer.lastTime= date()
          
      #1.收到clientA的请求 
      def requestConnetPeer(peerId):
          peerB= peerMap.get(peerId)
          if(null==peerB):
              return false;
          else:
              #发起连接A和B
              return connect(peerA,peerB)
          
          return true
      '''  
      def startWebsocket(self):
          print ("turnServer startWebsocket")
          self.server = WebsocketServer(8131, "0.0.0.0")
          self.server.set_fn_new_client(self.new_client)
          self.server.set_fn_client_left(self.client_left)
          self.server.set_fn_message_received(self.message_received)
          self.server.run_forever()
          print ("turnServer startWebsocket end")
          
if __name__ == '__main__':
      print("test")
    
    
    
    
    
    