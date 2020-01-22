<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>关于我们</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	<style type="text/css">
		.content{
			width: 100%;
			height: auto;
		}
		.content img{
			width: 100%;
			height: auto;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
            var userId = "${vijMallUserSession.userId}";
            if (userId) {
            	addUserAction(userId, 0, 7095, 1); /* 事件上报，页的其他方法在此方法后面写 */
            }
		})
	</script>
</head>
<body>
	<div class="page">
		<!-- 公共头部 -->
	 	<jsp:include page="/vij/publich/header.do"/>
		<div class="content">
			<img src="${ctx}/vij/images/about_02.jpg">
		</div>
		<!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" />
	</div>

</body>
</html>	