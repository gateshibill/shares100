<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加项目</title>
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
				<label class="layui-form-label">验收详情子类：</label>
				<div class="layui-input-block">
					<input type="text" name="itemName"  autocomplete="off" class="layui-input" lay-verify="itemName">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">详情：</label>
				<div class="layui-input-block">
					<input type="text" name="itemDetail"  autocomplete="off"
						class="layui-input" lay-verify="itemDetail">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">验收子类状态</label>
				<div class="layui-input-inline">
					<select name="itemStatus" lay-verify="itemStatus">
						<option value="0">未验收</option>
						<option value="1">验收通过</option>
						<option value="2">正在验收</option>
						<option value="3">验收不通过</option>
					</select>
				</div>
			</div>
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
var checkId = "${checkId}";
layui.use([ 'form', 'layer'],
		function() {
			 form = layui.form(), 
			 layer = layui.layer;
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/pCheckItem/doaddProjectCheckItem.do",
				dataType:"json",
				data : {
					'checkId':checkId,
					"itemName":data.field.itemName,
					"itemDetail":data.field.itemDetail,
					"itemStatus":data.field.itemStatus,
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
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