<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>验收进度</title>
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
/* .content{
	width:80%;
	margin:0 auto;
} */
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
.btnname{
	float:right;
}
#red{
	color:#ff5722;
}
#bname{
	color:#00FF00; 
}
</style>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
  <legend>验收进度</legend>
</fieldset>
  <c:forEach items="${list}" var="pc"> 
<ul class="layui-timeline">
  <li class="layui-timeline-item">
    <i class="layui-icon layui-timeline-axis"></i>
    <div class="layui-timeline-content layui-text">
	    <h3 class="layui-timeline-title">
	    	<fmt:formatDate value="${pc.checkTime}" pattern="yyyy-MM-dd"/>
	    </h3>
      <p>
        ${pc.planName}
        <i class="layui-icon"></i><br>
        <c:choose>
        	<c:when test="${pc.checkStatus ==0}">
        		未开始
        	</c:when>
        	
        	<c:when test="${pc.checkStatus ==1}">
        		<a id="name">施工中</a>
        	</c:when>
        	<c:when test="${pc.checkStatus ==2}">
        		<a id="bname">待验收</a>
        	</c:when>
        	
        	<c:when test="${pc.checkStatus ==3}">
        		<a class="red">通过</a>
        	</c:when>
        	<c:when test="${pc.checkStatus ==4}">
        		<a>未通过</a>
        	</c:when>
        </c:choose>
     	 </p>
     
    </div>
  </li>
</ul>  
 </c:forEach>
</body>
</html>