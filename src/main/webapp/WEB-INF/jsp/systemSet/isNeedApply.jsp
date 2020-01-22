<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="${ctx}/css/pintuer.css">
<link rel="stylesheet" href="${ctx}/css/admin.css">
<style>
</style>
<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/pintuer.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/ueditor.all.min.js">
	
</script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<div class="panel admin-panel margin-top" id="add">
		<div class="panel-head">
			<strong><span class="icon-pencil-square-o"></span> 会员入会机制</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x"
				action="${ctx}/systemSet/updateSet.do" id="submitForm">
				<div style="float: left; margin: 0 20px;">
					<label>会员是否需要申请:</label>
				</div>
				<div style="float: left">
					<input type="radio" name="isNeedApply"
						<c:choose><c:when test="${sysSet.isNeedApply==1}">checked</c:when></c:choose>
						value="1">是 <em style="padding: 0 10px;"></em> <input
						type="radio" name="isNeedApply"
						<c:choose><c:when test="${sysSet.isNeedApply==0}">checked</c:when></c:choose>
						value="0">否
				</div>
				<div style="float:left;margin-top:100px;">
					<button
						class="button bg-main icon-check-square-o button-padding-lr-10"
						type="submit" onclick="tijiao();">提交</button>
					<button
						class="button bg-main icon-check-square-o button-padding-lr-10"
						type="reset">重置</button>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function tijiao(){
		alert("修改成功!");
	}
</script>
</html>