<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>生成二维码</title>
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
<style type="text/css">
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: -3px;
	cursor: pointer;
	color: #35CE8D;
}
.layui-form-select .layui-edge {
  right:30px !important
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">应用ID：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 400px;"
						name="loginPlat" lay-verify="loginPlat" autocomplete="off"
						class="layui-input" value="${loginPlat}" readonly="readonly">
				</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">消息推送类型：</label>
					<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 400px;"
						name="noticeType" lay-verify="noticeType" autocomplete="off"
						class="layui-input" value="${noticeType}" readonly="readonly">
				</div>
		   </div>
		   <c:if test="${noticeType == 1}">
			<div class="layui-form-item">
				<label class="layui-form-label">用户id：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 400px;"
						name="userId" lay-verify="userId" autocomplete="off"
						class="layui-input">
						
				</div>
			</div>
			</c:if>
			<c:if test="${noticeType == 2}">
			<div class="layui-form-item">
				<label class="layui-form-label">openId：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 400px;"
						name="openId" lay-verify="openId" autocomplete="off"
						class="layui-input">
				</div>
			</div>
		</c:if>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
layui.use([ 'form', 'layer'],
		function() {
	 form = layui.form(), layer = layui.layer;
		//自定义验证规则
		form.verify({
			loginPlat :function(value) {
				if(value.length == "") {
					 return "请选择应用";
				}
			},
			noticeType :function(value) {
				if(value.length == "") {
				     return "消息类型不能为空";
				}
			},
		});
		
		
		//监听提交
		form.on('submit(demo1)', function(data) {
	        var index = layer.load(1); //换了种风格
	        if(data.field.noticeType == 1){
	        	if(!data.field.userId){
	        		layer.msg("用户id不能为空");
	        		return;
	        	}
	        	var data = {
	    				"loginPlat":data.field.loginPlat,
	    				"userId":data.field.userId,
	    				"noticeType":data.field.noticeType
	    			};
	        } else{
	        	if(!data.field.openId){
	        		layer.msg("openId不能为空");
	        		return;
	        	}
	        	var data = {
	    				"loginPlat":data.field.loginPlat,
	    				"openId":data.field.openId,
	    				"noticeType":data.field.noticeType
	    			};
	        }
	        
			$.ajax({
				type : "post",
				url : "${ctx}/pushnotice/doaddnotice.do",
				dataType:"json",
				data : data,
				success : function(result) {
					layer.close(index);
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						  }, 200);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.close(index);
					layer.msg("添加失败");
				}
			});
			return false;
		});
	});
	



function goBack(){
	window.history.go(-1);
}
</script>
</html>