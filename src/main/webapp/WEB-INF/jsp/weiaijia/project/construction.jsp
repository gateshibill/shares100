<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>施工</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${ctx}/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/global.css" media="all">
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${ctx}/css/table.css?a=<%=new java.util.Random().nextInt()%>" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>" />

<style type="text/css">
.content{
	width:80%;
	margin:0 auto;
}
.top{
	text-align:center
}
.layui-table-cell{
   height:auto;
   overflow:visible;
  text-overflow:inherit;
 white-space:normal;
}
.layui-table td, .layui-table th{
	padding:15px;
}
.name{
	padding:5px 10px 0 10px;
	color:#22A95D;
}
.btnbutton{
	float:right;
}
</style>
</head>
<body>
	<div class="content">
		<table class="layui-btn">
			<tbody>
				<tr>
					<td>客户新房施工计划
					<button class="layui-btn layui-btn-radius" id="constructionPlan">点击进入</button>
					</td>
				</tr>
				<tr>
					<td>客户新房施工进度
						<button class="layui-btn layui-btn-radius">点击进入</button>						
					</td>
				</tr>
				<tr>
					<td>客户新房施工管控
						<button class="layui-btn layui-btn-radius">点击进入</button>
					</td>
				</tr>
				
			</tbody>
		</table>
		<button class="layui-btn layui-btn-radius">确定施工</button>
	</div>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>	
	<script type="text/javascript">
		var projectId = "${project.id}";
		layui.use([ 'layer', 'table'], function() {
			table = layui.table;
			layer = layui.layer;
		});

$(window).one(
		"resize",
		function() {
			$("#constructionPlan")
					.click(
							function() {
								
								var index = layui.layer
										.open({
											title : "施工计划",
											type : 2,
											area:['400px','600px'],
											content : "${ctx}/project/decorateForecast.do?projectId="
													+projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
			})
}).resize();

</script>
</body>
</html>