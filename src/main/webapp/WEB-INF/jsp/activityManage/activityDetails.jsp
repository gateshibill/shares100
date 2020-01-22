<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>活动详情</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css?v=1"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
</head>

<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>活动【${activity.activityName}】的详情</legend>
		</fieldset>
		<form class="layui-form" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">活动编号：</label>
				<div class="layui-input-block">
					<label class="layui-form-label">${activity.activityId}</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">活动名称：</label>
				<div class="layui-input-block">
					<label class="detail-form-label">${activity.activityName}</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">活动相册：</label>
				<div class="layui-input-inline">
					<label class="layui-form-label"><img
						src="${activity.activityImage}"
						style="width: 120px; height: 120px;" /></label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">活动类型：</label>
				<div class="layui-input-inline">
					<label class="layui-form-label"> <c:if
							test="${activity.type==1}">
						转盘抽奖
					</c:if> <c:if test="${activity.type==2}">
						竞猜
					</c:if>
					</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">限定次数：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${activity.count}次</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">活动状态：</label>
				<div class="layui-input-block">
					<c:choose>
						<c:when test="${activity.status==1}">
							<input type="radio" name="status" checked="" value="1"
								title="活动开启">
							<input type="radio" name="status" value="2" title="临时关闭">
							<input type="radio" name="status" value="3" title="活动结束">
							<input type="radio" name="status" value="4" title="删除活动">
						</c:when>
						<c:when test="${activity.status==2}">
							<input type="radio" name="status" checked="" value="2"
								title="临时关闭">
							<input type="radio" name="status" value="1" title="重新开启">
							<input type="radio" name="status" value="3" title="活动结束">
							<input type="radio" name="status" value="4" title="删除活动">
						</c:when>
						<c:when test="${activity.status==3}">
							<input type="radio" name="status" checked="" value="3"
								title="活动结束">
							<input type="radio" name="status" value="4" title="删除活动">
						</c:when>
						<c:otherwise>
							<span style="line-height:38px;">已删除</span>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">创建用户id：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${activity.createUser}</label>
					<%-- <a class="layui-btn"
						onclick="window.location.href='${ctx}/user/userDetails.do?userId=${activity.createUser}'">查看用户</a> --%>
					<a class="layui-btn"
						onclick="window.location.href='${ctx}/user/gogogo.do?userId=${activity.createUser}'">查看用户</a>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">发布时间：</label>
				<div class="layui-input-block">
					<label style="line-height: 38px;"><fmt:formatDate
							value="${activity.createTime}" type="both" /></label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">修改时间：</label>
				<div class="layui-input-block">
					<label style="line-height: 38px;"><fmt:formatDate
							value="${activity.updateTime}" type="both" /></label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">活动详情：</label>
				<div class="layui-input-block">
					<textarea name="activityDesc" placeholder="请输入活动详情" class="layui-textarea">${activity.activityDesc}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交修改</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回上页</button>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
	layui
			.use(
					[ 'form', 'layedit', 'laydate' ],
					function() {
						var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
						
						//监听提交
						form.on('submit(demo1)', function(data) {
							$.ajax({
								type : "post",
								url : "${ctx}/activity/updateInfo.do",
								dataType : "json",
								data : {
									"activityId" : '${activity.activityId}',
									"status" : data.field.status,
									"activityDesc" : data.field.activityDesc,
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改成功");
										setTimeout(function() {
											window.location.href="${ctx}/activity/goActivityList.do";
										}, 1000);
										// 						setTimeout(location.reload(),1000);
									}
								},
								error : function() {
									layer.msg("修改失败");
								}
							});
							return false;
						});
					});
	function goBack() {
		// 	window.location.href = '${ctx}/goods/goGoodsList.do';
		window.history.go(-1);
	}
</script>
</html>