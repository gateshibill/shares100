# -*- coding: utf-8 -*-
"""
Created on Thu Jun 20 15:34:16 2019

@author: THP6
"""

class Message:
   '所有员工的基类'
   id
   type = 0
   
 
   def __init__(self, id, type):
      #self.name = name
      #self.salary = salary
      #Employee.empCount += 1
   
   def displayCount(self):
     print "Total Employee %d" % Employee.empCount
 
   def displayEmployee(self):
      print "Name : ", self.name,  ", Salary: ", self.salary