
# -*- coding: utf-8 -*-
"""
Created on Thu Jun 20 18:20:27 2019

@author: THP6
"""

import socket
BUFSIZE = 1024
ip_port = ('192.168.30.15', 9999)
server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)  # udp协议
server.bind(ip_port)
while True:
    data,client_addr = server.recvfrom(BUFSIZE)
    print('server收到的数据', data)
 
    server.sendto(data.upper(),client_addr)
 
server.close()