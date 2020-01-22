<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p>测试微信登陆</p>
</body>
<script type="text/javascript" src="${ctx}/weixin/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="${ctx}/weixin/js/public.js"></script>
<script type="text/javascript">
$(function(){
	var storageFlag = 'feikan_qiyehao_'+workId;
	var userId = getCookie(storageFlag); 
	if(!userId){ //重新授权
		login(workId,'index.jsp');
	}else{
		alert(userId);
	}
})
</script>
</html>