<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
.lineHeit32 {
	line-height: 32px;
}

.marginTop5 {
	margin-top: 5px;
}
</style>
<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/pintuer.js?v=3333"></script>
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
			<strong><span class="icon-pencil-square-o"></span>活动详情</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x"
				action="${ctx}/active/updateActive.do?activeId=${ac.activeId}&pageNo=${page}"
				id="submitForm">
				<div class="form-group">
					<div class="label">
						<label>活动编号：</label>
					</div>
					<div class="field">
						<label class="lineHeit32">${ac.activeId}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>发布用户：</label>
					</div>
					<div class="field">
						<label class="lineHeit32">${ac.activeUser.realName}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>活动名称：</label>
					</div>
					<div class="field">
						<label class="lineHeit32">${ac.activeTitle}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>活动图片：</label>
					</div>
					<div class="field">
						<img src="${ac.activeImage}"
							style="width: 640px; height: 320px;" />
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>阅读总数：</label>
					</div>
					<div class="field">
						<label class="lineHeit32">${ac.readCount}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>点赞总数：</label>
					</div>
					<div class="field">
						<label class="lineHeit32">${ac.praiseCount}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>发布时间：</label>
					</div>
					<div class="field">
						<label class="lineHeit32"><fmt:formatDate value="${ac.createTime}"
								type="both" /></label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>开始时间：</label>
					</div>
					<div class="field">
						<label class="lineHeit32"><fmt:formatDate value="${ac.activeBeginTime}"
								type="both" /></label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>结束时间：</label>
					</div>
					<div class="field">
						<label class="lineHeit32"><fmt:formatDate value="${ac.activeEndTime}"
								type="both" /></label>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>活动详情：</label>
					</div>
					<div class="field">
						<textarea id="activeEditor" name="activeDetails">${ac.activeDetails}</textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>是否删除：</label>
					</div>
					<div class="field">
						<select class="marginTop5" name="isRemove">
							<option value="1"
								<c:choose>
									<c:when test="${currUser.userStatus==1}">selected="selected"</c:when>
									</c:choose>>正常</option>
							<option value="0"
								<c:choose>
								<c:when test="${currUser.userStatus==0}">selected="selected"</c:when></c:choose>>已删除
							</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button
							class="button bg-main icon-check-square-o button-padding-lr-10"
							type="submit">确定</button>
						<button
							class="button bg-main icon-check-square-o button-padding-lr-10"
							onclick="javascript:;goBack(${page});">返回</button>

					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
var ue = UE.getEditor('activeEditor');
function goBack(page){
	window.location.href="${ctx}/active/activeList.do?pageNo="+page;
}
</script>
<script src="${ctx}/js/imageUpload.js"></script>
</html>