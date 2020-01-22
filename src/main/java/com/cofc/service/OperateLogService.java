package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.OperateLog;

   public interface OperateLogService {

   public void addOperateLog(OperateLog log);

   public int getOperateLogCout(Map<String, Object> map);

   public List<OperateLog> getOperateLogList(Map<String, Object> map);

   public void deleteLog(@Param("logId")Integer logId);

}
