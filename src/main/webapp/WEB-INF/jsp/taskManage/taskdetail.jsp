<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta charset="utf-8">
	<title>任务详情</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />
	<style type="text/css">
	 .layui-table th{text-align:center}
	</style>
</head>
<body class="childrenBody">
<div class="layui-layer-title" style="cursor: move;margin-bottom:30px;padding-right:0 !important">
任务详情
<button class="layui-btn layui-btn-danger" style="float:right;margin-right:20px !important" onclick="history.go(-1)">返回</button>
<button class="layui-btn" style="float:right;margin-right:20px !important">修改</button>
</div>
  <div class="layui-form-item">
  <div class="layui-inline">
    <label class="layui-form-label">发布者</label>
    <div class="layui-input-block">
      <input type="text" name="title" required  value="${task.usercommon.nickName}" lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-inline">
     <label class="layui-form-label">原因</label>
    <div class="layui-input-block">
      <input type="text" name="title" required  value="${task.publishReason}" lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    </div>
  </div>
   <div class="layui-inline">
     <label class="layui-form-label">内容</label>
    <div class="layui-input-block">
      <input type="text" name="title" required  value="${task.taskContent}" lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    </div>
  </div>
   <div class="layui-inline">
     <label class="layui-form-label">服务</label>
    <div class="layui-input-block">
      <input type="text" name="title" required  value="${task.satisfiedService}" lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    </div>
  </div>
</div>
  <div class="layui-form-item">
     <div class="layui-inline">
     <label class="layui-form-label">地址</label>
    <div class="layui-input-block">
      <input type="text" name="title" required  value="${task.lacationName}" lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-inline">
     <label class="layui-form-label">金额</label>
    <div class="layui-input-block">
      <input type="text" name="title" required value="${task.supplyFee}"  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-inline">
    <label class="layui-form-label">开始时间</label>
    <div class="layui-input-block">
      <input type="text" name="title"  value="<fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd 
HH:mm"/>" placeholder="请输入标题" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-inline">
     <label class="layui-form-label">结束时间</label>
    <div class="layui-input-block">
      <input type="text" name="title" value="<fmt:formatDate value="${task.taskEndTime}" pattern="yyyy-MM-dd 
HH:mm"/>"
        placeholder="请选择结束时间" autocomplete="off" class="layui-input">
    </div>
  </div>
</div>

<div class="layui-layer-title" style="cursor: move;margin-bottom:10px;padding-right:0 !important">抢单列表</div>
<table class="layui-table" style="width:98%;margin:0 auto;text-align:center">
  <thead>
    <tr>
      <th>id</th>
      <th>抢单者</th>
      <th>金额</th>
      <th>状态</th>
      <th>抢单时间</th>
      <!-- <th>操作</th> -->
    </tr> 
  </thead>
  <tbody>
  <c:forEach items="${compete}" var="g">
    <tr>
      <td>${g.competeId}</td>
      <td>${g.competerInfo.nickName}</td>
      <td>${g.competeFee}</td>
      <td>
        <c:choose>
          <c:when test="${g.competeStatus == 0}">
                                             等待选择
          </c:when>
          <c:when test="${g.competeStatus == 1}">
                                          被选中
          </c:when>
          <c:when test="${g.competeStatus == 2}">
                                             已完成
          </c:when>
          <c:when test="${g.competeStatus == 3}">
                                             已支付
          </c:when>
          <c:when test="${g.competeStatus == 4}">
                     已评价                   
          </c:when>
          <c:when test="${g.competeStatus == 9}">
                                             未完成
          </c:when>
            <c:otherwise>
             <span style="color:red">未选中</span>
            </c:otherwise>
        </c:choose>
      </td>
       <td>
      <fmt:formatDate
  value="${g.competeTime}"
  pattern="yyyy-MM-dd 
HH:mm"
  />
       </td>
      <!-- <td>
      <a class="layui-btn layui-btn-mini" lay-event="edittask">选他</a>
      <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="deltask">删除</a>
      </td> -->
    </tr>
  </c:forEach>  
  </tbody>
</table>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
</body>
</html>