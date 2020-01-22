<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta charset="utf-8">
	<title>添加标签</title>
	<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
</head>
<body class="childrenBody">
	<div style="padding-left:50px;;padding-top:30px;">	
		<div class="layui-form-item">
			<div class="layui-inline">		
				<label class="layui-form-label">标签名称</label>
				<div class="layui-input-inline">
					<input type="text" id="content" class="layui-input newsAuthor" lay-verify="required" placeholder="请输入标签名称">
				</div>
			</div>				
		</div>
		<div class="layui-form-item">
					<label class="layui-form-label">标签类型</label>
					<div class="layui-input-inline" style="width:190px;">
						<select name="tagType" id="tagType" lay-verify="tagType" autocomplete="off"
						class="layui-input">
							<option value="">请选择</option>
							<option value="1">项目标签</option>
							<option value="2">产品标签</option>
							<option value="3">技能标签</option>
							<option value="4">招聘标签</option>
							
						</select>
					</div>
				</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" id="send" >立即提交</button>
<!-- 				<button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
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
      var tagType = $("#tagType").val();
      if(!content){
    	  layer.msg("标签名称不能为空");
    	  return;
      }
      if(!tagType){
    	  layer.msg("标签类型不能为空");
    	  return;
      }
    	$.ajax({
    		 url:'${ctx}/tag/doaddTag.do',
    		 type:'post',
    		 dataType:'json',
    		 data:{'tagName':content,'tagType':tagType},
    		 success:function(data){
    			 if(data.code==0){
    				  layer.msg(data.msg);
    				  setTimeout(function(){
    					  layer.closeAll("iframe");
      					//刷新父页面
      				 		parent.location.reload();
    					 // window.location.href = '${ctx}/shareContract/goContentList.do';
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