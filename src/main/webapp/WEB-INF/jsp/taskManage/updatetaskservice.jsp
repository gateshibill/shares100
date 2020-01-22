<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta charset="utf-8">
	<title>编辑任务服务</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />
	<style type="text/css">
	  #select-input .layui-form-select .layui-input{width:190px !important}
	</style>
</head>
<body class="childrenBody">
	<div style="padding-left:50px;;padding-top:30px;">	
	   <div class="layui-form">
		<div class="layui-form-item">	
			<div class="layui-inline">		
				<label class="layui-form-label">服务</label>
				<div class="layui-input-inline">
					<input type="text" id="content" value="${service.serviceName}" class="layui-input newsAuthor"  placeholder="请输入服务内容">
				    <input type="hidden" id="serviceId" value="${service.serviceId}"/>
				</div>
			</div>				
		</div>
		
	<div class="layui-form-item">
    <label class="layui-form-label">类型</label>
    <div class="layui-input-block" style="width:200px;" id="select-input">
      <select name="type" id="type"  style="width:200px;">
      <c:if test="${service.serviceType == 1}">
        <option value="1" selected="selected">发布者满意赠送服务</option>
        <option value="2">抢单者提供的服务</option>
      </c:if>
        
          <c:if test="${service.serviceType == 2}">
        <option value="1" >发布者满意赠送服务</option>
        <option value="2" selected="selected">抢单者提供的服务</option>
      </c:if>
    
      </select>
    </div>
  </div>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" id="send" >立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		</div>
	</div>	
	</div>	
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript">
layui.use('layer',function(){
	layer = layui.layer;
})
    $("#send").on('click',function(){
      var content = $("#content").val();
      var type = $("#type").val();
      var serviceId = $("#serviceId").val();
      if(!content){
    	  layer.msg('添加内容不允许为空');
    	  return;
      }
    	$.ajax({
    		 url:'${ctx}/sharetask/doupdatetaskservice.do',
    		 type:'post',
    		 dataType:'json',
    		 data:{'serviceName':content,'serviceType':type,'serviceId':serviceId},
    		 success:function(data){
    			 if(data.code==0){
    				  layer.msg(data.msg);
    				  setTimeout(function(){
    					  layer.closeAll("iframe");
      					//刷新父页面
      				 		parent.location.reload();
    					  //layer.closeAll("iframe");
    					  //window.location.href = '${ctx}/sharetask/gotaskservicelist.do';
    				  },1000);
    			 }else{
    				 layer.msg(data.msg);
    				 return;
    			 }
    		 }
    	});
    })
</script>
</body>
</html>