# -*- coding: utf-8 -*-
"""Created on Thu Jun 20 18:20:50 2019

@author: THP6
"""

import socket
BUFSIZE = 1024
client = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
while True:
    msg = input(">> ").strip()
    ip_port = ('192.168.30.15', 9999)
    client.sendto(msg.encode('utf-8'),ip_port)
 
    data,server_addr = client.recvfrom(BUFSIZE)
    print('客户端recvfrom ',data,server_addr)
 
client.close()