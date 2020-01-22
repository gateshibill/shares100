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
.dateInput {
	width: 150px;
	height: 40px;
	line-height: 40px;
	display: inline-block;
	float: left;
}
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
			<strong><span class="icon-pencil-square-o"></span> 商品类型自定义</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x"
				action="${ctx}/systemSet/addGoodsType.do" id="submitForm">
				<c:forEach var="goodsType" items="${gtList}">
					<div style="margin: 10px 20px;">
						<label style="margin-right: 10px;">${goodsType.typeId}.${goodsType.typeName}</label>
					</div>
				</c:forEach>
				<div class="form-group">
					<div class="field" style="margin: 10px 30px; display: none"
						id="addGTdiv">
						<input type="text" name="typeName" class="input dateInput"
							placeholder="请输入商品类型" data-validate="required:请输入商品类型" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="button bg-main icon-plus button-padding-lr-10"
					style="cursor: pointer" onclick="showAddDiv();">增加</div>
				<div style="float: left; margin: 100px 100px 0px 100px;">
					<button
						class="button bg-main icon-check-square-o button-padding-lr-10"
						type="submit">提交</button>
					<button class="button bg-main icon-refresh button-padding-lr-10"
						type="reset" onclick="hiddenGTDiv();">重置</button>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function showAddDiv() {
		$("#addGTdiv").show();
	}
	function hiddenGTDiv() {
		$("#addGTdiv").hide();
	}
</script>
</html>