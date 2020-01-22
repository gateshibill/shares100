<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>顾问</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/guwen.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
	        var userId = "${vijMallUserSession.userId}";
	        if (userId) {
	        	addUserAction(userId, 0, 7094, 1); /*事件上报，页面其他方法在此方法后面写*/
	        }

		})
	</script>
</head>
<body>
	<div class="page">
		<!-- 公共头部 -->
	  	<jsp:include page="/vij/publich/header.do"/>
		<div class="mulu">
			<div class="mulu-center">
				<p class="mulu-center-p" align="left">
				<span>首页  > </span>
				<span>顾问风采</span>
				</p>
			</div>
		</div>	
		<div class="guwen-fengcai">
			<div class="guwen-fengcai-center">
				<ul class="guwen-fengcai-image">
				<c:forEach var="adviser" items="${adviserList}" varStatus="status">
				<c:if test="${status.count%2==1}">
					<%-- <li class="guwen-fengcai-image-li0">
						<a href="">
							<img src="${ctx}/vij/images/guwen_banner_123.png">
						</a>
					</li> --%>
					<li class="guwen-fengcai-image-li1">
						<div class="guwen-fengcai-image-image">
							<a href="">
								<img src="${adviser.images}">
							</a>
						</div>
						<div class="guwen-fengcai-image-text">
							<div class="fengcai-image-text-image">
								<img src="${ctx}/vij/images/guwen_diwen_131.png">
							</div>
							<div class="fengcai-image-text-text">
								<div class="fengcai-image-text-name">
									<div class="image-text-name-center">
										<img src="${ctx}/vij/images/guwenbeijing.png">
										<div class="image-text-name-name flex-box">
											<p class="image-text-name-name-p1">${adviser.city}</p>
											<p class="image-text-name-name-p2">${adviser.realName}</p>
										</div>
									</div>
								</div>
								<div class="fengcai-image-text-zhiwei">
									<p align="center">${adviser.position}</p>
								</div>
								<div class="fengcai-image-text-content">
									<p align="center">${adviser.introduce}</p>
								</div>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${status.count%2==0}">
					<%-- <li class="guwen-fengcai-image-li0">
						<a href="">
							<img src="${ctx}/vij/images/guwen_banmner_129.png">
						</a>
					</li> --%>
					<li class="guwen-fengcai-image-li2">
						<div class="guwen-fengcai-image-image">
							<a href="">
								<img src="${adviser.images}">
							</a>
						</div>
						<div class="guwen-fengcai-image-text">
							<div class="fengcai-image-text-image">
								<img src="${ctx}/vij/images/guwen_diwen_130.png">
							</div>
							<div class="fengcai-image-text-text">
								<div class="fengcai-image-text-name">
									<div class="image-text-name-center">
										<img src="${ctx}/vij/images/guwenbeijing.png">
										<div class="image-text-name-name flex-box">
											<p class="image-text-name-name-p1">${adviser.city}</p>
											<p class="image-text-name-name-p2">${adviser.realName}</p>
										</div>
									</div>
								</div>
								<div class="fengcai-image-text-zhiwei">
									<p align="center">${adviser.position}</p>
								</div>
								<div class="fengcai-image-text-content">
									<p align="center">${adviser.introduce}</p>
								</div>
							</div>
						</div>
					</li>
				</c:if>
				</c:forEach>
					<%-- <li class="guwen-fengcai-image-li3">
						<div class="fengcai-image-li3-image">
							<img src="${ctx}/vij/images/guwen_banner_132.png">
						</div>
						<div class="fengcai-image-li3-text">
							<div class="fengcai-li3-text-text">
								<p class="fengcai-li3-text-p1" align="center"><a href="">选择唯爱家</a></p>
								<p class="fengcai-li3-text-p2" align="center"><a href="">让家具更艺术，让生活更美好</a></p>
								
							</div>
						</div>
						
					</li> --%>
				</ul>
			</div>
		</div>


		<!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" />
	</div>
</body>
</html>