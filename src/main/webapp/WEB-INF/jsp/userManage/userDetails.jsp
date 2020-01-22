<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>

<head>
<meta charset="utf-8">
<title>表单</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/plugins/layui/lay/modules/layer.js"></script>
</head>
<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>用户【${currUser.nickName}】的详情</legend>
		</fieldset>
		<div class="layui-form-item">
			<label class="layui-form-label">用户编号：</label>
			<div class="layui-input-block">
				<label class="layui-form-label">${currUser.userId}</label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">用户昵称：</label>
			<div class="layui-input-block">
				<label class="detail-form-label">${currUser.nickName}</label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">真实姓名：</label>
			<div class="layui-input-inline">
				<label class="layui-form-label">${currUser.realName}</label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">用户头像：</label>
			<div class="layui-input-inline">
				<label class="layui-form-label"><img
					src="${currUser.headImage}"
					style="width: 80px; height: 80px;" /></label>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">联系方式：</label>
				<div class="layui-input-inline">
					<label class="layui-form-label">${currUser.phone}</label>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">电子邮箱：</label>
			<div class="layui-input-block">
				<label class="layui-form-label">${currUser.email}</label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">用户性别：</label>
			<div class="layui-input-inline">
				<label class="layui-form-label"><c:choose>
						<c:when test="${currUser.userSex==2}">
							女
						</c:when>
						<c:when test="${currUser.userSex==1}">
							男
						</c:when>
						<c:otherwise>
							未知
						</c:otherwise>
					</c:choose> </label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">用户积分：</label>
			<div class="layui-input-inline">
				<label class="layui-form-label">${currUser.integral}</label>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">公司及职位：</label>
				<div class="layui-input-inline">
					<label class="detail-form-label" style="width:600px;">${currUser.compony}</label>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">公司简介：</label>
				<div class="layui-input-inline" style="width: 80%;">
					<label class="detail-form-label">${currUser.componyIntroduce}</label>
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">名片图片：</label>
				<div class="layui-input-inline" style="width: 100px;">
					<label class="layui-form-label">${imgHtml}</label>
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<c:if test="${currUser.isRenzheng==0}"><button class="layui-btn" lay-submit="" lay-filter="demo1"
					onclick="passRenzheng(${currUser.userId});">通过认证</button></c:if>
				<c:if test="${currUser.isRenzheng==1}"><button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="demo1"
					onclick="notPassRenzheng(${currUser.userId});">撤销认证</button></c:if>
				<c:if test="${currUser.isManager==0}"><button class="layui-btn" lay-submit="" lay-filter="demo1"
					onclick="setManager(${currUser.userId});">设为管理</button></c:if>
				<c:if test="${currUser.isManager==1}"><button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="demo1"
					onclick="setNotManager(${currUser.userId});">撤销管理</button></c:if>
				<button onclick="goBack();" class="layui-btn layui-btn-primary">返回上页</button>
			</div>
		</div>
		
	</div>
	
	<script>
		function passRenzheng(currUserId){
			setTimeout(function() {
				window.location.href="${ctx}/user/renzhenUser.do?userId="+currUserId+"&isRenzheng="+1;
			}, 400,parent.layer.alert('认证成功!'));
			
		}
		function notPassRenzheng(currUserId){
			setTimeout(function() {
				window.location.href="${ctx}/user/renzhenUser.do?userId="+currUserId+"&isRenzheng="+0;
			}, 400,parent.layer.alert('设置用户未认证成功!'));
		}
		function goBack(){
			window.history.go(-1);
		}
		function setManager(currUserId){
			setTimeout(function() {
				window.location.href="${ctx}/user/setManager.do?userId="+currUserId+"&isManager="+1;
			}, 400,parent.layer.alert('设置用户成为管理成功!'));
			
		}
		function setNotManager(currUserId){
			
			setTimeout(function() {
				window.location.href="${ctx}/user/setManager.do?userId="+currUserId+"&isManager="+0;
			}, 400,parent.layer.alert('设置用户为非管理成功!'));
			
		}
	</script>
</body>

</html>