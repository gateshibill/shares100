<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑管理员</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
</head>

<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>管理员【${user.userName}】的详情</legend>
		</fieldset>
		<div class="layui-form-item">
			<label class="layui-form-label">用户编号：</label>
			<div class="layui-input-block">
				<label class="detail-label-value">${user.userId}</label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">用户账号：</label>
			<div class="layui-input-block">
				<label class="detail-label-value"> <input type="text"
					style="display: inline !important; width: 200px;" name="userName"
					id="userName" lay-verify="userName" autocomplete="off"
					class="layui-input" value="${user.userName}">
				</label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">真实姓名：</label>
			<div class="layui-input-block">
				<label class="detail-label-value"> <input type="text"
					style="display: inline !important; width: 200px;" name="realName"
					id="realName" lay-verify="realName" autocomplete="off"
					class="layui-input" value="${user.realName}">
				</label>
			</div>
		</div>
		<c:choose>
			<c:when test="${roleId ==1}">
				<div class="layui-form-item">
					<label class="layui-form-label">角色类型：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="roleId" id="roleId" lay-verify="roleId"
							autocomplete="off" class="layui-input">
							<c:forEach var="role" items="${roleList}">
								<c:choose>
									<c:when test="${urole.roleId==role.roleId}">
										<option selected="selected" value="${role.roleId}">${role.roleName}</option>
									</c:when>
									<c:otherwise>
										<option value="${role.roleId}">${role.roleName}</option>
									</c:otherwise>
								</c:choose>

							</c:forEach>
						</select>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="layui-form-item">
					<label class="layui-form-label">角色类型：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="roleId" id="roleId" lay-verify="roleId"
							autocomplete="off" class="layui-input">
							<c:forEach var="role" items="${roleList}">
								<c:choose>
									<c:when test="${urole.roleId==role.roleId}">
										<option selected="selected" value="${role.roleId}">${role.roleName}</option>
									</c:when>
								</c:choose>

							</c:forEach>
						</select>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="${loginPlat == null || loginPlat==''}">
			<div class="layui-form-item">
				<label class="layui-form-label">状态：</label>
				<div class="layui-input-inline" style="width: 210px;">
					<select name="isEffect" lay-verify="isEffect" autocomplete="off"
						class="layui-input" id="isEffect">
						<option value="">请选择状态</option>
						<c:if test="${user.isEffect == 1}">
							<option value="1" selected="selected">开启</option>
							<option value="0">禁用</option>
						</c:if>
						<c:if test="${user.isEffect != 1}">
							<option value="1">开启</option>
							<option value="0" selected="selected">禁用</option>
						</c:if>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">管理平台：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;" name="loginPlat"
						lay-verify="loginPlat" autocomplete="off" class="layui-input"
						value="${user.loginPlat}" id="loginPlatList">
					<p style="color: red">不填,默认管理所有,多个平台id用,号分隔[独立应用的id]</p>
				</div>
			</div>
		</c:if>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 100px;">用户头像：</label>
			<div class="layui-input-block">
				<label class="detail-label-value"> <img id="headImage"
					src="${user.headImage}"
					style="max-width: 200px; max-height: 150px;" /></label> <input type="file"
					name="file" class="layui-upload-file" id="test"
					accept=".jpg,.gif,.png,.jpeg">
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">创建时间：</label>
			<div class="layui-input-block">
				<label style="line-height: 38px;"><fmt:formatDate
						value="${user.createTime}" type="both" /></label>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
				<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>

	var userId = '${user.userId}';
	var headImage = '';
	layui.use('upload', function() {
		layui.upload({
			url : '${ctx}/layfileUpload/upload.do',
			elem : '#test' //指定原始元素，默认直接查找class="layui-upload-file"
			,
			method : 'post' //上传接口的http类型
			,
			success : function(res) {
				headImage = res.data.src;
				if(headImage != ""){
		    		$("#headImage").attr("src",headImage);
		    	}
			}
		});
	});

	layui.use([ 'form', 'layer'], function() {
		var form = layui.form(), layer = layui.layer;
		//自定义验证规则
		form.verify({
			userName : function(value) {
				if (value.trim() == '') {
					return '账号不能为空';
				}
			},
			realName : function(value) {
				if (value.trim() == '') {
					return '真实姓名不能为空';
				}
			}

		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			var Image = $("#headImage").attr("src");
			if(Image != ""){
				headImage = Image;
			}
			$.ajax({
				type : "post",
				url : "${ctx}/permission/updateBackUser.do",
				dataType : "json",
				data : {
					"userId" : userId,
					"realName" : $('#realName').val(),
					"roleId" : $('#roleId').val(),
					'headImage' : headImage,
					"userName" : $('#userName').val(),
					"isEffect" : $("#isEffect").val(),
					"loginPlat" : $("#loginPlatList").val()
				},
				success : function(result) {
					if (result.code == 0) {
						layer.msg("修改成功");
						setTimeout(function() {
							location.reload();
						}, 1000);
						// setTimeout(location.reload(),1000);
					} else {
						layer.msg(result.msg);
					}
				},
				error : function() {
					layer.msg("修改失败");
				}
			});
			return false;
		});
	});
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>