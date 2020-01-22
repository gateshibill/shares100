<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>设置金额</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<style type="text/css">
#Images li{
	float: left;
	width: 300px;
	height: 300px;
	margin-bottom: 20px;
}
#Images img{
	width: 300px;
	height: 300px;
}
.img_del{
	cursor: pointer;
}
.flex-box::before, .flex-box::after {
	display: block;
	clear: both;
	height: 0;
	content: "";
}
</style>
</head>
<body>

	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">设置金额：</label>
				<div class="layui-input-inline">
					<input type="text" name="depositMoney"  autocomplete="off" class="layui-input" value="${project.depositMoney}">
				</div>
			</div>
			<div class="layui-form-item">
			<label class="layui-form-label"></label>
			<div class="layui-input-block">
			<input type="hidden" value="${project.id}" name="id">
				<button class="layui-btn" lay-submit="" lay-filter="demo1">确定</button>
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
	 
		//自定义验证规则
		form.verify({	
			depositMoney : function(value) {
				if (value.length == "") {
					return "请输入金额";
				}
			},
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			var uploadStr = "";
			$.ajax({
				type : "post",
				url : "${ctx}/project/uploadProjectList.do",
				dataType:"json", 
				data : {
					"id":data.field.id,
					"depositMoney":data.field.depositMoney
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
					layer.msg("确认失败");
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