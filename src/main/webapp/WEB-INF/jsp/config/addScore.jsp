<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加积分</title>
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
				<label class="layui-form-label">应用id：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="loginPlat" value="${loginPlat}"  readonly="readonly" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">注册积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="registerScore" value="0"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">登陆积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="dayLoginScore" value="0"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">签到积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="signScore" value="0"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">分享积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="shareScore" value="0"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">一级邀请积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="visitScore" value="0" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">二级邀请积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="twoVisitScore" value="0" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">完善资料积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="myInfoScore" value="0" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">购买积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="buyScore" value="0" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">充值积分：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" style="display: inline !important; width: 200px;"
						name="rechargeScore" value="0" autocomplete="off"
						class="layui-input">
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
layui.use([ 'form', 'layer'],
		function() {
	 form = layui.form(), layer = layui.layer;
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/config/addConfig.do",
				dataType:"json",
				data : {
					"loginPlat":data.field.loginPlat,
					"registerScore":data.field.registerScore,
					"dayLoginScore":data.field.dayLoginScore,
					"buyScore":data.field.buyScore,
					"signScore":data.field.signScore,
					"shareScore":data.field.shareScore,
					"visitScore":data.field.visitScore,
					"twoVisitScore":data.field.twoVisitScore, 
					"rechargeScore":data.field.rechargeScore,
					"myInfoScore":data.field.myInfoScore,
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