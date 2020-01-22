# -*- coding: utf-8 -*-
"""
Created on Thu Jun 20 15:48:59 2019

@author: THP6
"""
import time

class Peer:
   '所有员工的基类'
   id=1
   name=""
   ip=""
   port=1234
   interal_ip=""
   interal_port=1234
   status=0
   lastTime=time.time()
   #client=null;
   
 
   def __init__(self, client, ip,port):
      self.client = client
      self.ip = ip
      self.port=port
      