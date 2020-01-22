<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加卡券</title>
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
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: -3px;
	cursor: pointer;
	color: #35CE8D;
}
img{
	width:200px;
	height:200px;
	margin-right:20px
}
.layui-form-select .layui-input{
	width:400px;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">	  
		     
			<div class="layui-form-item">
				<label class="layui-form-label">卡券名称：</label>
				<div class="layui-input-inline">
					<input type="text" style="width: 400px;"
						name="ticketName"  autocomplete="off"
						class="layui-input" value="${ticket.ticketName}">
				</div>
			</div>
			
			<div class="layui-form-item">
				    <label class="layui-form-label">卡券类型：</label>
				    <div class="layui-input-block" style="width:400px;;margin-left:100px">
				    <select name="ticketType" id="ticketType" lay-verify="ticketType" autocomplete="off"
						class="layui-input" lay-search="" style="width:400px;" required="required">
						   <option value="1" <c:if test="${ticket.ticketType == 1}">selected="selected"</c:if>>立减券</option>
						   <option value="2" <c:if test="${ticket.ticketType == 2}">selected="selected"</c:if>>满减券</option>
						</select>
				    </div>
				  </div>

			<div class="layui-form-item">
				<label class="layui-form-label">卡券描述：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width:400px;"
						name="ticketDesc"  autocomplete="off"
						class="layui-input" value="${ticket.ticketDesc}">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">卡券面值：</label>
				<div class="layui-input-inline">
					<input type="number" style=" width:400px;"
						name="ticketMoney"  autocomplete="off"
						class="layui-input" value="${ticket.ticketMoney}">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">卡券数量：</label>
				<div class="layui-input-inline">
					<input type="number" style=" width:400px;"
						name="ticketNumber"  autocomplete="off"
						class="layui-input" value="${ticket.ticketNumber}">
				</div>
			</div>
			
			<div class="layui-form-item">
				    <label class="layui-form-label">是否有效：</label>
				    <div class="layui-input-block" style="width:400px;;margin-left:100px">
				    <select name="isEffect" id="isEffect" lay-verify="isEffect" autocomplete="off"
						class="layui-input" lay-search="" style="width:400px;" required="required">
						   <option value="0" <c:if test="${ticket.isEffect == 0}">selected="selected"</c:if>>否</option>
						   <option value="1" <c:if test="${ticket.isEffect == 1}">selected="selected"</c:if>>是</option>
						</select>
				    </div>
		  </div>
		
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<input type="hidden" name="ticketId" value="${ticket.ticketId}"/>
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
layui.use([ 'form', 'layer'],
		function() {
	 form = layui.form, layer = layui.layer;
		//监听提交
		form.on('submit(demo1)', function(data) {
			
			$.ajax({
				type : "post",
				url : "${ctx}/cardticket/doEditCardTicket.do",
				dataType:"json",
				data : {
					"loginPlat":data.field.loginPlat,
					"ticketName":data.field.ticketName,
					"ticketType":data.field.ticketType,				
					'ticketDesc':data.field.ticketDesc,
					'ticketMoney':data.field.ticketMoney,
					'ticketNumber':data.field.ticketNumber,
					"isEffect":data.field.isEffect,
					"ticketId":data.field.ticketId,
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