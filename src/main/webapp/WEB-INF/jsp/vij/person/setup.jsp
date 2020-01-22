<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>设置</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/setup.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">	
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	
</head>
<body>
	<div class="page">
		<jsp:include page="/vij/person/common.do" />
		<div class="centre">
			<div class="centre-center flex-box">
				<div class="centre-center-left">
					<p class="left-news"><a href="${ctx}/vij/person/message.do">系统消息</a></p>
					<p class="left-setup"><a href="${ctx}/vij/person/setup.do">设置</a></p>
				</div>
				<div class="centre-center-right">
					<div class="right-li li-1">
						<div class="right-li-title flex-box">
							<p class="title-name">消息设置</p>
						</div>
						<p class="right-li-mintitle" align="left">选择接收的消息类型</p>
						<ul class="right-li-news flex-box">
							<li>
								<input type="checkbox" name="">
								<span>系统通知</span>
							</li>
							
						</ul>
					</div>
				</div> 
			</div>
		</div>
		<div class="user-bottom"></div>
		<!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" />
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			//点击删除消息
			var spans=$(".right-li-news span");
			for(var i=0;i<spans.length;i++){
				$(".right-li-news span").get(i).index=i;
				$(".right-li-news span:eq("+i+")").click(function(){
					//赋值
					var i=this.index;
					//删除
					$(".right-li-news li:eq("+i+")").remove();
				})
			}
		})
	</script>
</body>
</html>