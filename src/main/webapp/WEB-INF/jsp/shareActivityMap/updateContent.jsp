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
		</fieldset>
		<form class="layui-form" action="">
			<div class="layui-form-item">
			<div class="layui-inline">		
				<label class="layui-form-label">活动内容</label>
				<div class="layui-input-inline">
					<textarea  id="content" class="layui-input newsAuthor" style="width:300px;height:120px;"
					lay-verify="contentDesc" autocomplete="off" name="contentDesc"
						class="layui-input"  maxlength="100" >${content.contentDesc}</textarea>
				</div>
			</div>		
		</div>
			<div class="layui-form-item">
				<label class="layui-form-label">发布时间：</label>
				<div class="layui-input-block">
					<label style="line-height: 38px;"><fmt:formatDate
							value="${content.createTime}" type="both" /></label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
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
						//自定义验证规则
						form.verify({
							contentDesc: function(value) {
								if(value.trim() == '') {
									return '活动内容不能为空';
								}
							}
						});
						//监听提交
						form.on('submit(demo1)', function(data) {
							$.ajax({
								type : "post",
								url : "${ctx}/shareActivity/updateContent.do",
								dataType : "json",
								data : {
									"contentId" : '${content.contentId}',
									"contentDesc" : data.field.contentDesc
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改成功");
										setTimeout(function() {
											window.location.href = '${ctx}/shareActivity/contentList.do';
										}, 1000);
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