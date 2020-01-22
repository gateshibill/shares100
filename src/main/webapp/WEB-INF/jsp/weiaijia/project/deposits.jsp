<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>查看业主是否提交定金</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${ctx}/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
		<table class="layui-table">
			<thead>
				<tr>
					<td>对方已提交${pOrder.realMoney}元定金，请确认收款</td>				
				</tr>
			</thead>
		</table>
		<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" name="porderId" value="${pOrder.porderId}" />
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
					url : "${ctx}/project/dogetProjectOrderList.do",
					dataType:"json",
					data : {
						isDeal:1,
						porderId:data.field.porderId,
						projectId:'${pOrder.projectId}'
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