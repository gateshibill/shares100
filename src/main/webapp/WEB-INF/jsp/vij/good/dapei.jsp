<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>搭配</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/dapei.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	<style type="text/css">
		.beauty_info {
		    font-size: 14px;
		    height: 40px;
		    overflow: hidden;
		    white-space: nowrap;
		    border: 1px solid #e8e8e8;
		    border-top: none;
		    clear:both;
		}
		.beauty_info>em {
		    float: right;
		    padding-right: 12px;
		    margin-top: 10px;
		    font-size: 12px;
		    color: #959595;
		}
		
		.beauty_info>a {
		    width: 210px;
		    text-indent: 11px;
		    line-height: 40px;
		    display: inline-block;
		    white-space: nowrap;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    height: 40px;
		    color:#666;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
        var userId = "${vijMallUserSession.userId}";
        if (userId) {
        	addUserAction(userId, 0, 7093, 1); /* 事件上报，页的其他方法在此方法后面写 */
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
				<span>首页 > </span>
				<c:choose>
					<c:when test="${coll.collType == 1}">
						<span>主流搭配</span>				
					</c:when>
					<c:when test="${coll.collType == 2}">
						<span>装修风格</span>					
					</c:when>
					<c:otherwise>
						<span>搭配</span>		
					</c:otherwise>
				</c:choose>
				</p>
			</div>
		</div>
	    <c:if test="${coll.collType == 0 || empty coll.collType}">
		<div class="xiangce-zhuliu-dapei">
			<div class="xiangce-xinpin-tuichu-cen">	
				<div class="tuichu-cen-title">
					<p>推荐</p>
				</div>
				<div class="xiangce-zhuliu-dapei-image">
					<ul class="zhuliu-dapei-image-s flex-box">
						<c:forEach var="coll" items="${collList}">
						<c:if test="${coll.isRecommend==1}">
							<li class="dapei-image-s-li1">
								<div class="dapei-image-s-div1">
									<a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" ><img src="${coll.collCover}"></a>
								</div>
								<%-- <div class="dapei-image-s-div2">
									<p class="dapei-image-s-div2-p1" align="center"><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}">${coll.collTitle}</a></p>
									<p class="dapei-image-s-div2-p2" align="center">${coll.collDesc}</p>
								</div> --%>
								<p class="beauty_info"><em>${coll.imagesCount}张</em><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" title="${coll.collTitle}">${coll.collTitle}</a></p>
							</li>
						</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	   </c:if>	
	   	<c:if test="${coll.collType == 1 || empty coll.collType}">
		<div class="zhuliu-dapei">
			<div class="xinpin-tuichu-cen">	
				<div class="tuichu-cen-title">
					<p>主流搭配</p>
				</div>
				<div class="zhuliu-dapei-image">
					<ul class="zhuliu-dapei-image-x flex-box">
						<c:forEach var="coll" items="${collList}">
						<c:if test="${coll.collType==1}">
							<li>
								<div class="dapei-image-x-image">
									<div class="dapei-image-x-image-image">
										<a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank">
											<img src="${coll.collCover}">
										</a>
									</div>
									<div class="dapei-image-x-image-text">
										<div class="image-x-image-text">
											<div class="image-x-image-text-div1">
												<p class="image-x-image-text-p1" align="center">
													<a href=""><img src="${ctx}/vij/images/shoucang-2.png">收藏</a>
												</p>
											</div>
											<div class="image-x-image-text-div2">
												<p class="image-x-image-text-p2" align="center">
													<a href="">装修成这样需要花多少钱？</a>
												</p>
											</div>
										</div>
									</div>
								</div>
								<div class="dapei-image-x-biao">
									<p align="center"><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank">${coll.collTitle}&nbsp;&nbsp;&nbsp;&nbsp;${coll.imagesCount}张</a></p>
								</div>
							</li>
						</c:if>
						</c:forEach>	
					</ul>
				</div>
					
			</div>
		</div>
		</c:if>
	    <c:if test="${coll.collType == 2 || empty coll.collType}">
		<div class="zhuangxiu-fengge">
			<div class="zhuangxiu-fengge-center">
				<div class="zhuangxiu-fengge-text">
					<div class="zhuangxiu-fengge-text-t">
						<div class="zhuangxiu-fengge-text-tehui">
							<p><a href="">装修风格</a></p>
						</div>
						
					</div>
				</div>
				<ul class="zhuangxiu-fengge-image flex-box">
					<c:forEach var="coll" items="${collList}">
						<c:if test="${coll.collType==2}">
							<li class="dapei-image-s-li1">
								<div class="zhuangxiu-fengge-image-image">
									<a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" title="${coll.collTitle}"><img src="${coll.collCover}"></a>
								</div>
								<%-- <div class="zhuangxiu-fengge-image-text">
									<p align="center"><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}">${coll.collTitle}</a></p>
								</div> --%>
								<p class="beauty_info"><em>${coll.imagesCount}张</em><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" title="${coll.collTitle}">${coll.collTitle}</a></p>								
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		</c:if>
		<!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" />
	</div>
</body>
</html>
