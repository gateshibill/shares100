<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加管理表单</title>
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
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		<div class="layui-form-item">
					<label class="layui-form-label">应用类型：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="loginPlat" lay-verify="loginPlat" autocomplete="off"
						class="layui-input">
							<option value="">请选择类型</option>
							<c:forEach var="app" items="${appList}">
								<option value="${app.applicationId}">${app.applicationName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			<div class="layui-form-item">
				<label class="layui-form-label">类型名称：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 200px;"
						name="typeName" lay-verify="typeName" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
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
layui.use([ 'form', 'layedit', 'laypage' ],
		function() {
	 form = layui.form(), laypage = layui.laypage, layer = layui.layer, layedit = layui.layedit;

		//自定义验证规则
		form.verify({
			loginPlat :function(value) {
				if(value.length == "") {
						   return "请选择应用";
								}
							},
		      typeName :function(value) {
					if(value.length == "") {
				           return "请输入类型名称";
					}
				},
		});
		
		
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/goods/addLoginPlatType.do",
				dataType:"json",
				data : {
					"loginPlat":data.field.loginPlat,
					"typeName":data.field.typeName
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加类型成功");
						setTimeout(function () {
							
						  }, 1000);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
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