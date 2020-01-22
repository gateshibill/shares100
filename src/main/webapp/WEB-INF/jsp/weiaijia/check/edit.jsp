<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>验收项目</title>
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
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">验收名称：</label>
				<div class="layui-input-block">
					<input type="text" name="checkName"  autocomplete="off" class="layui-input" lay-verify="checkName" value="${pjCheck.checkName}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">验收人员：</label>
				<div class="layui-input-block">
					<input type="text" name="checkUserStr"  autocomplete="off"
						class="layui-input" lay-verify="checkUserStr" value="${pjCheck.checkUserStr}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">验收状态</label>
				<div class="layui-input-inline">
					<select name="checkStatus" lay-verify="checkStatus">
						<option value="0" <c:if test="${pjCheck.checkStatus==0}">selected="selected"</c:if>>未验收</option>
						<option value="1" <c:if test="${pjCheck.checkStatus==1}">selected="selected"</c:if>>验收通过</option>
						<option value="2" <c:if test="${pjCheck.checkStatus==2}">selected="selected"</c:if>>正在验收</option>
						<option value="3" <c:if test="${pjCheck.checkStatus==3}">selected="selected"</c:if>>验收不通过</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" value="${pjCheck.checkId}" name="checkId"/>
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
			checkName : function(value) {
				if (value.length == "") {
					return "请输入验收名称";
				}
			},
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			var uploadStr = "",upload_collCover="";
			$.ajax({
				type : "post",
				url : "${ctx}/pjCheck/doupdateProjectCheck.do",
				dataType:"json", 
				data : {
					"checkId":data.field.checkId,
					"checkName":data.field.checkName,
					"checkUserStr":data.field.checkUserStr,
					"checkStatus":data.field.checkStatus,
						
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("编辑成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						  }, 1000);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.msg("编辑失败");
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