# -*- coding: utf-8 -*-
"""Created on Thu Jun 20 18:20:50 2019

@author: THP6
"""

import socket
import sys
import threading
import time


BUFSIZE = 1024


class UdpUtil:
      udpPort=9999
      udpClientMap={}
      
      def UDPServer(self,ip,port):
          localIp=get_host_ip()
          ip_port = (localIp, port)
          server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)  # udp协议
          server.bind(ip_port)
          print('server is ready:',ip,port)   
          while True:      
               data,client_addr = server.recvfrom(BUFSIZE)
               print('server recvfrom', str(data),client_addr)
               if(True):
                     #lenght=len(udpClientMap)
                     oid=0
                     self.udpClientMap[oid]=client_addr
                     server.sendto(b"logined:",client_addr)
                     #self.sendUDPPKGMSG(client_addr[0],client_addr[1],"test client send,not server response")
               else:
                     server.sendto(data.upper(),client_addr)  
                     #self.sendUDPPKGMSG(client_addr[0],client_addr[1],"test client send,not server response")
          server.close()
          
          
      def startUDPServer(self,ip,port):
            thread = threading.Thread(target=self.UDPServer,args=(ip,port))
            thread.start()
      
      def UDPClient(self,ip,port):
           client = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
           while True:
                #msg = input(">> ").strip()
                msg="auok"
                ip_port = (ip, port)
                client.sendto(msg.encode('utf-8'),ip_port)
                print('send to:',ip_port,msg)
                
                #data,server_addr = client.recvfrom(BUFSIZE)
                #print('client recvfrom:',data,server_addr) 
                time.sleep(3)                       
           client.close()
           
      def AsynUDPClient(self,ip,port,serverIp,serverPort):
           client = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
           flag=True #server start once
           while True:
                #msg = input(">> ").strip()
                msg="auok"
                ip_port = (ip, port)
                server_ip_port=(serverIp, serverPort)
                client.sendto(msg.encode('utf-8'),ip_port)
                print('send to:',ip_port,msg)
                client.sendto(msg.encode('utf-8'),server_ip_port)
                print('send to:',server_ip_port,msg)
                if(flag):
                     flag=False
                     t1 = threading.Thread(target=self.startUDPClientServer,args=(client, ))
                     t1.start()
                time.sleep(3)  
           client.close()     
           
      def startUDPClientServer(self,client):
          while True:
              data,server_addr = client.recvfrom(BUFSIZE)
              print('client recvfrom:',data,server_addr)
      
      def startUDPClient(self,ip,port):
            thread = threading.Thread(target=self.UDPClient,args=(ip,port ))
            thread.start()
            
      def startAsynUDPClient(self,ip,port,serverIp,serverPort):
            thread = threading.Thread(target=self.AsynUDPClient,args=(ip,port,serverIp,serverPort))
            thread.start()
       
      def sendUDPPKG(self,ip,port):
            message="try for nat"
            self.sendUDPPKGMSG(ip,port,message)
           
      def sendUDPPKGMSG(self,ip,port,message):
           client = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)    
           ip_port = (ip, port)
           client.sendto(message.encode('utf-8'),ip_port)
           print('send to:',ip_port,message)
           client.close()     
		   

def get_host_ip():
    try:
        s=socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        s.connect(('8.8.8.8',80))
        ip=s.getsockname()[0]
    finally:
        s.close()

    return ip

if __name__ == '__main__':

      print("test")
      #startAsynUDPClient("192.168.30.15",9999)
      
      udpUtil= UdpUtil()
      
      ip=get_host_ip()
      port=9999
      t1 = threading.Thread(target=udpUtil.UDPServer,args=(ip,port ))
      t1.start()
      
      #udpUtil.startUDPClient(ip,port)
      udpUtil.AsynUDPClient("192.168.30.30",9999,ip,port)
      
      while True:
          msg = input(">> ").strip()
          if("e"==msg):
               print("exit")
               sys.exit(0) 
      t1._stop
     
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      