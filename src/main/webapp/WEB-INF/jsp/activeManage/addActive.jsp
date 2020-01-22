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
.input-w30 {
	width: 30% !important;
}

.button {
	padding: 7px !important
}

.button-padding-lr-10 {
	padding: 8px 16px !important;
	margin-right: 10px;
}

.tip img {
	width: 200px !important;
	height: 150px !important;
}

.textareaWH {
	width: 800px;
	height: 400px;
}

.body-content {
	padding-bottom: 250px !important;
}

.margin-T150 {
	margin-top: 150px;
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
			<strong><span class="icon-pencil-square-o"></span>添加活动</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x"
				action="${ctx}/active/addActive.do" id="submitForm">
				<div class="form-group">
					<div class="label">
						<label>活动名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50 input-w30 input-10" value=""
							name="goodsName" data-validate="required:请输入商品名称" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>活动图片：</label>
					</div>
					<div class="field">
						<input type="text" id="sImage" name="goodsImage"
							class="input tips w50 input-w30 input-10"
							style="width: 25%; float: left; margin-right: 10px;" value=""
							data-place="right" data-image="" /> <a
							class="button bg-main input-file" href="javascript:void(0);">+
							浏览上传<input size="100" accept=".jpg,.png,.gif" type="file"
							id="upImage" />
						</a>
						<div class="tips" style="color: red; margin-top: 6px;">商品图片建议采用600*400的尺寸</div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>活动描述：</label>
					</div>
					<div class="field">
						<textarea id="editor" name="activeDetails"></textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button
							class="button bg-main icon-check-square-o button-padding-lr-10"
							type="submit">提交</button>
						<button
							class="button bg-main icon-check-square-o button-padding-lr-10"
							onclick="window.location.href='${ctx}/active/activeList.do'">
							返回</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	var ue = UE.getEditor('editor');
</script>
<script src="${ctx}/js/imageUpload.js"></script>
</html>