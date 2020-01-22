<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>业主房产信息</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet"
	href="${ctx}/plugins/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
			<table class="layui-table">
			<colgroup>
			    <col width="150">
			    <col>
			  </colgroup>
				<tbody>
				 <tr>
						<td>姓名</td>
						<td>${room.lfUserName}</td>
					</tr>

					<tr>
						<td>电话</td>
						<td>${room.mobilePhoneNo}</td>
					</tr>
					<tr>
						<td>城市</td>
						<td>${room.cityAreas}</td>
					</tr>
					<tr>
						<td>地址</td>
						<td>${room.homeNumber}</td>
					</tr>
					<tr>
						<td>户型</td>
						<td>${room.houseDoorModel}</td>
					</tr>
					<tr>
						<td>面积</td>
						<td>${room.buildingArea}</td>
					</tr>
					<tr>
						<td>房型</td>
						<td>${room.housingTypes}</td>
					</tr>
				</tbody>
			</table>
			<div class="layui-form-item" style="text-align:center">
				<div class="layui-input-block" style="margin-left:0px">
				<input type="hidden" name="id" value="${room.id}" />
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
				</div>
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
					url : "${ctx}/project/doDecorateForecast.do",
					dataType:"json",
					data : {
						isDeal:1,
						id:data.field.id
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