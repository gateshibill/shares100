<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>搭配详情</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/dapei_detail.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
</head>
<body>
	<div class="page">
		<!-- 公共头部 -->
	  	<jsp:include page="/vij/publich/header.do"/>
		<div class="mulu">
			<div class="mulu-center">
				<p class="mulu-center-p" align="left">
					<span>首页 ></span>
					<span>主流搭配 ></span>
					<span>${coll.collTitle}</span>
				</p> 
			</div>
		</div>
		<div class="image-max">
			<div class="max-center">
				<div class="max-center-image">
					<ul>
						<c:forEach var="image" items="${coll.image}">
							<li align="center"><img src="${image}"></li>
						</c:forEach>
					</ul>
				</div>
				<div class="max-center-text">
					<c:forEach var="image" items="${coll.image}">
						<p>${coll.collTitle}</p>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="iamge-min">
			<div class="min-center">
				<ul class="min-center-image flex-box">
					<c:forEach var="image" items="${coll.image}">
						<li><img src="${ctx}/vij/images/lazy-image.png" class="lazy" data-original="${image}"></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" />
	</div>
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/image-lazy.js"></script>	
	<script type="text/javascript">
		$(document).ready(function(){
	    	$("img.lazy").lazyload();
			//设置样式
			$(".min-center-image li:eq(0)").addClass("min-image-li");
			$(".max-center-image ul li:eq(0)").addClass("image-li");
			$(".max-center-text p:eq(0)").addClass("text-p");

			//tab栏交互
			var lis = $(".min-center-image li");
			for(var i=0;i<lis.length;i++){
				$(".min-center-image li").get(i).index=i;
				$(".min-center-image li:eq("+i+")").click(function(){
					var i=this.index;
					//移除类名
					$(".min-center-image li").removeClass("min-image-li");
					$(".max-center-image ul li").removeClass("image-li");
					$(".max-center-text p").removeClass("text-p");

					//添加类名
					$(".min-center-image li:eq("+i+")").addClass("min-image-li");
					$(".max-center-image ul li:eq("+i+")").addClass("image-li");
					$(".max-center-text p:eq("+i+")").addClass("text-p");
				})
			}
		})
	</script>
</body>
</html>
