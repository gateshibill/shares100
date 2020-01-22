<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加管理员</title>
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
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
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
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>添加管理</legend>
		</fieldset>
		<form class="layui-form" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">登录账号：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 200px;"
						name="userName" lay-verify="userName" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">登录密码：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 200px;"
						name="password" lay-verify="password" autocomplete="off"
						class="layui-input" onfocus="this.type='password'">
				</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">角色类型：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="roleId" lay-verify="roleId" autocomplete="off"
						class="layui-input">
							<option value="">请选择类型</option>
							<c:forEach var="role" items="${roleList}">
								<option value="${role.roleId}">${role.roleName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">状态：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="isEffect" lay-verify="isEffect" autocomplete="off"
						class="layui-input">
							<option value="">请选择状态</option>
							<option value="1" selected="selected">开启</option>
							<option value="0">禁用</option>						
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">管理平台：</label>
					<%-- <div class="layui-input-inline" style="width: 210px;">
						<select name="loginPlat" lay-verify="loginPlat" autocomplete="off"
						class="layui-input">
							<option value="">请选择管理的平台</option>
							<c:forEach var="role" items="${roleList}">
								<option value="${role.roleId}">${role.roleName}</option>
							</c:forEach>
						</select>
					</div> --%>
					<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 200px;"
						name="loginPlat" lay-verify="loginPlat" autocomplete="off"
						class="layui-input"> 
						<p style="color:red">不填,默认管理所有,多个平台id用,号分隔[独立应用的id]</p>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">真实姓名：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 200px;"
						name="realName" lay-verify="realName" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">用户头像：</label>
				<div class="layui-input-block">
  					<img id="headImage" src="" style="max-width:200px;max-height:150px;">
    				<input type="file" name="file" class="layui-upload-file" id="test" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>
<script type="text/javascript">
var pageno=1,pagesize=50,ulistLoad;
var headImage="";
layui.use('upload', function(){
	  layui.upload({
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#test' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post' //上传接口的http类型
	    ,success: function(res){
	    	headImage = res.data.src;
	    	if(headImage != ""){
	    		$("#headImage").attr("src",headImage);
	    	}
	    }
	  });
	});
	
layui.use([ 'form', 'layer'],
		function() {
	 form = layui.form(),layer = layui.layer;
			
		//自定义验证规则
		form.verify({
			userName : [ /^[a-zA-Z]\w{3,15}$/,
							'账户由字母、数字、下划线组成，字母开头，在4-16位之内' ],
					//password : [ /(.+){6,12}$/, '密码必须6到12位' ],
			password : [ /^[a-zA-Z]\w{5,15}$/,
							'密码需由字母，数字，下划线组成，字母开头，在6-16位之内' ],
			roleId :function(value) {
				if(value.length == "") {
									return "请选择角色";
								}
							},
			realName :function(value) {
					if(value.length == "") {
				           return "请输入真实姓名";
					}
				},
			loginPlat:function(value){
				if(value.length == "") {
			           return "请输入应用id";
				}
			}	
			
		});
		
		
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/permission/addbackUser.do",
				dataType:"json",
				data : {
					"userName":data.field.userName,
					"password":data.field.password,
					"realName":data.field.realName,
					"roleId":data.field.roleId,
					"isEffect":data.field.isEffect,
					"loginPlat":data.field.loginPlat,
					"headImage":headImage
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							window.location.href="${ctx}/permission/goManagerList.do";
						  }, 1000);
					}
				},error:function(result){
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