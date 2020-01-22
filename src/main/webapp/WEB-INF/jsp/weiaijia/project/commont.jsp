<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>查看业主评价</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet"
	href="${ctx}/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<style type="text/css">
#Images li {
	float: left;
	width: 200px;
	height: 200px;
	margin-bottom: 20px;
}

#Images img {
	width: 200px;
	height: 200px;
}

.img_del {
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="layui-form">
			<div class="layui-form-item">
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 30px;">
		<legend>整体满意度:${pComment.commentScore}</legend>
	</fieldset>

	<div class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">选择服务标签：</label>
			<div class="layui-input-block selectAdviser">
			 <button class="layui-btn layui-btn-primary layui-btn-radius">${pComment.tagStr}</button>
			</div>
		</div>
	</div>
	<div class="layui-form-item">
				<label class="layui-form-label">顾客说：</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入介绍" name="commentContent" class="layui-textarea">${pComment.commentContent}</textarea>
				</div>
		</div>	
		<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" name="commentId" value="${pComment.commentId}" />
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
				</div>
			</div>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
layui.use([ 'form', 'layer'],
			function() {
		 var form = layui.form, layer = layui.layer;
//监听提交
			form.on('submit(demo1)', function(data) {
				$.ajax({
					type : "post",
					url : "${ctx}/project/getProjectCommentByid.do",
					dataType:"json",
					data : {
						isDeal:1,
						commentId:data.field.commentId
					},
					success : function(result) {
						if(result.code==0){
							layer.msg("确认成功");
							setTimeout(function () {
								 layer.closeAll("iframe");
			      				//刷新父页面
			      				 parent.location.reload();
							  }, 1000);
						} else{
							layer.msg(result.msg,{time:2000});
						}
					},error:function(){
						layer.msg("失败");
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