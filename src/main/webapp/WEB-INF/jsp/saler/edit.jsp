<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑销售</title>
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
				<label class="layui-form-label">姓名：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 200px;"
						name="userName" value="${saler.userName}"  readonly="readonly" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">职位：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 200px;"
						name="position" value="${saler.position}"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">部门：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="departmentId" value="${saler.departmentId}"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">访问次数：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="visitedCount" value="${saler.visitedCount}"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">订单数量：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="orderCount" value="${saler.orderCount}"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">客户数量：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="customerCount" value="${saler.customerCount}"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">互动频次：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="interactCount" value="${saler.interactCount}" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
		   <div class="layui-form-item">
				<label class="layui-form-label">每日访问量：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="eVisitedCount" value="${saler.eVisitedCount}" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">成交率：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="dealRatio"  value="${saler.dealRatio}" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
		
			
			
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<input type="hidden" value="${saler.id}" name="id"/>
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
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
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/saler/updateSaler.do",
				dataType:"json",
				data : {
					"userName":data.field.userName,
					"position":data.field.position,
					"departmentId":data.field.departmentId,
					"visitedCount":data.field.visitedCount,
					"orderCount":data.field.orderCount,
					"customerCount":data.field.customerCount,
					"interactCount":data.field.interactCount,
					"dealRatio":data.field.dealRatio,
					"eVisitedCount":data.field.eVisitedCount,
					"id" : data.field.id
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("更新成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						  }, 400);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.msg("更新失败");
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