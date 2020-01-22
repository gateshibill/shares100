<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>新品推出</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/xinpin_tuichu.css">
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
</head>

<body>
    <div class="page">
        <!-- 公共头部 -->
        <jsp:include page="/vij/publich/header.do" />
        <div class="mulu">
            <div class="mulu-center">
                <p class="mulu-center-p" align="left">
                    <span>首页 > </span>
                    <span>新品推出 > </span>
                    <span>所有分类</span>
                </p>
            </div>
        </div>
        <!-- 一个大类开始 -->
        <c:forEach var="a" items="${typelist}" varStatus="index">
            <div class="jiancai">
                <div class="jiancai-center">
                    <div class="jiancai-center-title">
                        <div class="jiancai-center-title-t">
                            <img src="${ctx}/vij/images/xialatubiao.png">
                            <p>${a.typeName} ${a.enTypeName}</p>
                        </div>
                    </div>
                    <div class="jiancai-center-type">
                        <ul>
                            <c:forEach var="b" items="${a.childList}">
                                <li><a href="${ctx}/vij/index/list.do?goodChildType=${b.typeId}">${b.typeName}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="jiancai-center-goods flex-box">
                        <ul class="jiancai-center-goods-ul flex-box">
                            <c:forEach var="c" items="${goodslist}">
                                <c:if test="${c.goodsType == a.typeId}">
                                    <a href="${ctx}/vij/index/gooddetail.do?goodsId=${c.goodsId}">
                                        <li>
                                            <div class="jiancai-center-goods-ul-image">
                                                <a href="${ctx}/vij/index/gooddetail.do?goodsId=${c.goodsId}">
                                                    <img src="${ctx}/vij/images/lazy-image.png" class="lazy" data-original="${c.goodOneImage}">
                                                </a>
                                            </div>
                                            <p class="jiancai-center-goods-ul-p1" align="center">
                                                ${c.goodsName}
                                            </p>
                                            <p class="jiancai-center-goods-ul-p2" align="center">￥${c.sellPrice}</p>
                                        </li>
                                    </a>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- <div class="jiancai-banner">
			<div class="jiancai-banner-center">
				<div class="jiancai-banner-center-image"></div>
			</div>
		</div> -->
        </c:forEach>
        <div style="height:2.0rem;width:100%;background-color:#eeeded"></div>
        <!-- 一个大类结束 -->
        <!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" />
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/image-lazy.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
    	$("img.lazy").lazyload();
        var userId = "${vijMallUserSession.userId}";
        if (userId) {
        	addUserAction(userId, 0, 7092, 1); /* 事件上报，页的其他方法在此方法后面写 */
        }

    })
    </script>
    </div>
</body>

</html>