# -*- coding: utf-8 -*-
"""
Created on Thu Jun 20 15:48:59 2019

@author: THP6
"""
import time
import json

class Peer:
   '所有员工的基类'
   '''
   id=1
   name=""
   ip=""
   port=1234

   interal_ip=""
   interal_port=1234
   status=0
   lastTime=time.time()
   #client=null;
   '''
 
   def __init__(self,oid=0,name="", ip="",port=1234,udpPort=7777):
      #self.client = client
      self.oid=oid
      self.name=name
      self.ip = ip
      self.port=port
      self.udpPort=udpPort
      
   def __repr__(self): 
        return repr((self.name, self.ip, self.port))
      

      