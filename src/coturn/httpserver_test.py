# -*- coding: utf-8 -*-
"""
Created on Thu Jun 20 17:27:48 2019

@author: THP6
"""

# coding:utf-8
import threading
import time
import socket
import sys
from wsclient_test import startClient
#import turn_server

from turn_server import turnServer
from multiprocessing import Process


def handle_client(client_socket):
    """
    处理客户端请求
    """
    request_data = client_socket.recv(1024)
    print("request data:", request_data)
    # 构造响应数据
    response_start_line = "HTTP/1.1 200 OK\r\n"
    response_headers = "Server: My server\r\n"
    response_body = "<h1>Python HTTP Test</h1>"
    response = response_start_line + response_headers + "\r\n" + response_body
    # 向客户端返回响应数据
    client_socket.send(bytes(response, "utf-8"))

    # 关闭客户端连接
    client_socket.close()

def startHttpServer(name):
    print ("startHttpServer")
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(("", 8000))
    server_socket.listen(128)

    while True:
        client_socket, client_address = server_socket.accept()
        print("[%s, %s]用户连接上了" % client_address)
        handle_client_process = Process(target=handle_client, args=(client_socket,))
        handle_client_process.start()
        client_socket.close()
        
def startWebsocket(ts):
    print ("startWebsocket")
    #ts=turnServer();
    ts.startWebsocket()    
    print ("startWebsocket end")

def startConnetServer(ws):
    try:
        ws.connect()
        ws.run_forever()
    except KeyboardInterrupt:
        ws.close()
        
if __name__ == "__main__":
    ts=None
    try:
        print ("start") 
        ts=turnServer();
        
        t1 = threading.Thread(target=startHttpServer,args=('ws1', ))
        print ("start1")
        t2 = threading.Thread(target=startWebsocket,args=(ts, ))
        print ("start2")
        t1.start()
        print ("start3")
        t2.start()
        print ("start4")
        
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
                print("send for peer")
                id = int(msg)
                print("starting send")
                ts.send(id,"send upd to peer")
        '''
        time.sleep(10000)
    except:
        print ("Error: unable to start thread")
        
    

        
        
        
        
        