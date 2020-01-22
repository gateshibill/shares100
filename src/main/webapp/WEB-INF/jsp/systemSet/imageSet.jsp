<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加发现表单</title>
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
</head>

<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>图片上传设置</legend>
		</fieldset>
		<form method="post" class="layui-form" 
		action="${ctx}/systemSet/updateImageSet.do" id="submitForm">
		<input type="hidden" name="loginPlat" value="${id}" >
	    <input type="hidden" name="descoveryType" value="${descoveryType}" >
			<div class="layui-form-item">
				<label class="layui-form-label">头像宽度：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;" value="${imageset.headWidth}"
						name="headWidth" lay-verify="headWidth" autocomplete="off"
						class="layui-input" />px
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">头像高度：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;" value="${imageset.headHeight}"
						name="headHeight" lay-verify="headHeight" autocomplete="off"
						class="layui-input" />px
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">其他图片宽度：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;" value="${imageset.uimageWidth}"
						name="uimageWidth" lay-verify="uimageWidth" autocomplete="off"
						class="layui-input" />px
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">其他图片高度：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;" value="${imageset.uimageHeight}"
						name="uimageHeight" lay-verify="uimageHeight" autocomplete="off"
						class="layui-input" />px
				</div>
			</div> 
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" type="submit" onclick="tijiao();">确认</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>

layui.use([ 'form', 'layedit', 'laydate' ],
		function() {
			var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
			
		//创建一个编辑器
		var editIndex = layedit.build('descoveryDetails_edit');
		//自定义验证规则
		form.verify({
			headHeight: function(value) {
				if(value.trim() == '') {
					return '头像宽度不能为空！';
				}
			},
			headHeight: function(value) {
				if(value.trim() == '') {
					return '头像高度不能为空！';
				}
			},
			uimageWidth: function(value) {
				if(value.trim() == '') {
					return '其他头像宽度不能为空！';
				}
			},
			uimageHeight: function(value) {
				if(value.trim() == '') {
					return '其他头像宽度不能为空！';
				}
			}
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			return false;
		});
	});

	function tijiao() {
		layer.msg("修改成功,下次上传生效!");
		setTimeout(function() {
			location.reload()
		}, 5000);
	}
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>