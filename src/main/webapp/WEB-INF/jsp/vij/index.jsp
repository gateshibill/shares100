<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <title>唯爱家</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/index.css">
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
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
</head>

<body>
    <div class="page">
        <c:if test="${fn:length(topadvlist)>0}">
            <c:forEach var="c" items="${topadvlist}" varStatus="index">
                <c:if test="${index.index == 0}">
                    <div class="top-banner">
                        <div class="top-banner-image">
                            <a href="${c.advJumpUrl}"><img src="${c.advUrl}" alt="${c.advName}"></a>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
        <!-- 公共头部 -->
        <jsp:include page="/vij/publich/header.do" />
        <div class="banner" id="banner">
            <div class="banner-center" id="banner-center">
                <div class="banner-center-slider" id="banner-center-slider">
                    <ul class="banner-center-ul" id="banner-center-ul">
                    	<c:forEach var="c" items="${lbtlist}">
                    		<c:if test="${not empty c.pcPictureUrl}">
                        		<li><a href="${c.hrefUrl}" target="_blank"><img src="${c.pcPictureUrl}" alt="" /></a></li>
                        	</c:if>
                        </c:forEach>
                    </ul>
                </div>
                <div class="banner-center-navigation">
                    <div class="banner-center-navigation-title">
                        <p align="center" class="banner-center-navigation-title-p"><a href=""><span align="center">家装城分类</span></a></p>
                    </div>
                    <ul class="banner-center-navigation-type">
                        <c:forEach var="c" items="${typelist}">
                            <li class="banner-center-navigation-type-li">
                                <a class="banner-center-navigation-type-a" href="${ctx}/vij/index/list.do?goodsType=${c.typeId}&typeName=${c.typeName}">
                                    <p align="center">${c.typeName}&nbsp;&nbsp;&nbsp;&nbsp;></p>
                                </a>
                                <div class="banner-center-navigation-jiancai">
                                    <c:choose>
                                        <c:when test="${fn:length(c.childList) > 0 && fn:length(c.childList) <= 8}">
                                            <ul class="banner-center-navigation-jiancai-ul">
                                                <c:forEach var="s" items="${c.childList}" varStatus="index">
                                                    <li>
                                                        <c:choose>
                                                        	<c:when test="${not empty s.typeIcon}">
                                                        		<img src="${s.typeIcon}">
                                                        	</c:when>
                                                        	<c:otherwise>
                                                        		<img src="${ctx}/vij/images/cizhuan_30.png">
                                                        	</c:otherwise>
                                                        </c:choose>
                                                        <p align="left"><a href="${ctx}/vij/index/list.do?goodChildType=${s.typeId}" class="jiancai-ul-a">${s.typeName}</a></p>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </c:when>
                                        <c:when test="${fn:length(c.childList) > 0 && fn:length(c.childList) <= 16}">
                                            <ul class="banner-center-navigation-jiancai-ul">
                                                <c:forEach var="s" items="${c.childList}" varStatus="index">
                                                    <c:if test="${index.index >= 0 && index.index < 8}">
                                                        <li>
															<c:choose>
	                                                        	<c:when test="${not empty s.typeIcon}">
	                                                        		<img src="${s.typeIcon}">
	                                                        	</c:when>
	                                                        	<c:otherwise>
	                                                        		<img src="${ctx}/vij/images/cizhuan_30.png">
	                                                        	</c:otherwise>
	                                                        </c:choose>                                                            <p align="left"><a href="${ctx}/vij/index/list.do?goodChildType=${s.typeId}" class="jiancai-ul-a">${s.typeName}</a></p>
                                                        </li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                            <ul class="banner-center-navigation-jiancai-ul">
                                                <c:forEach var="s" items="${c.childList}" varStatus="index">
                                                    <c:if test="${index.index >= 8 && index.index < 16}">
                                                        <li>
															<c:choose>
	                                                        	<c:when test="${not empty s.typeIcon}">
	                                                        		<img src="${s.typeIcon}">
	                                                        	</c:when>
	                                                        	<c:otherwise>
	                                                        		<img src="${ctx}/vij/images/cizhuan_30.png">
	                                                        	</c:otherwise>
	                                                        </c:choose>                                                                 <p align="left"><a href="${ctx}/vij/index/list.do?goodChildType=${s.typeId}" class="jiancai-ul-a">${s.typeName}</a></p>
                                                        </li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </c:when>
                                        <c:otherwise>
                                            <ul class="banner-center-navigation-jiancai-ul">
                                                <c:forEach var="s" items="${c.childList}" varStatus="index">
                                                    <c:if test="${index.index >= 0 && index.index < 8}">
                                                        <li>
															<c:choose>
	                                                        	<c:when test="${not empty s.typeIcon}">
	                                                        		<img src="${s.typeIcon}">
	                                                        	</c:when>
	                                                        	<c:otherwise>
	                                                        		<img src="${ctx}/vij/images/cizhuan_30.png">
	                                                        	</c:otherwise>
	                                                        </c:choose>                                                                 <p align="left"><a href="${ctx}/vij/index/list.do?goodChildType=${s.typeId}" class="jiancai-ul-a">${s.typeName}</a></p>
                                                        </li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                            <ul class="banner-center-navigation-jiancai-ul">
                                                <c:forEach var="s" items="${c.childList}" varStatus="index">
                                                    <c:if test="${index.index >= 8 && index.index < 16}">
                                                        <li>
															<c:choose>
	                                                        	<c:when test="${not empty s.typeIcon}">
	                                                        		<img src="${s.typeIcon}">
	                                                        	</c:when>
	                                                        	<c:otherwise>
	                                                        		<img src="${ctx}/vij/images/cizhuan_30.png">
	                                                        	</c:otherwise>
	                                                        </c:choose>                                                                 <p align="left"><a href="${ctx}/vij/index/list.do?goodChildType=${s.typeId}" class="jiancai-ul-a">${s.typeName}</a></p>
                                                        </li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                            <ul class="banner-center-navigation-jiancai-ul">
                                                <c:forEach var="s" items="${c.childList}" varStatus="index">
                                                    <c:if test="${index.index >= 16 && index.index < 24}">
                                                        <li>
															<c:choose>
	                                                        	<c:when test="${not empty s.typeIcon}">
	                                                        		<img src="${s.typeIcon}">
	                                                        	</c:when>
	                                                        	<c:otherwise>
	                                                        		<img src="${ctx}/vij/images/cizhuan_30.png">
	                                                        	</c:otherwise>
	                                                        </c:choose>                                                                 <p align="left"><a href="${ctx}/vij/index/list.do?goodChildType=${s.typeId}" class="jiancai-ul-a">${s.typeName}</a></p>
                                                        </li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="banner-bottom">
            <ul class="banner-bottom-center">
                <li class="banner-bottom-center-li1">
                    <div class="banner-bottom-center-baojia">
	                    <a href="${ctx}/vij/index/freePrice.do">
	                        <div class="banner-bottom-center-bao">
	                            <p class="banner-bottom-center-p1">免费报价</p>
	                            <p class="banner-bottom-center-p2">算算装修要花多少钱</p>
	                        </div>
	                        <div class="banner-bottom-center-img">
	                            <img src="${ctx}/vij/images/jisuanqi_26.png">
	                        </div>
	                    </a>
                    </div>
                </li>
                <li class="banner-bottom-center-li2">
                    <div class="banner-bottom-center-baojia">
	                    <a href="${ctx}/vij/index/homeSetup.do">
	                        <div class="banner-bottom-center-bao">
	                            <p class="banner-bottom-center-p1">家居设计</p>
	                            <p class="banner-bottom-center-p2">让你的家与众不同</p>
	                        </div>
	                        <div class="banner-bottom-center-img">
	                            <img src="${ctx}/vij/images/jiajusheji_26.png">
	                        </div>
	                    </a>
                    </div>
                </li>
                <li class="banner-bottom-center-li3">
                    <div class="banner-bottom-center-baojia">
	                    <a href="">
	                        <div class="banner-bottom-center-bao">
	                            <p class="banner-bottom-center-p1">装修工具</p>
	                            <p class="banner-bottom-center-p2">这不是工具·这是艺术品</p>
	                        </div>
	                        <div class="banner-bottom-center-img">
	                            <img src="${ctx}/vij/images/zhuangxiugongju_26.png">
	                        </div>
	                    </a>
                    </div>
                </li>
                <li class="banner-bottom-center-li4">
                    <div class="banner-bottom-center-baojia">
	                    <a href="">
	                        <div class="banner-bottom-center-bao">
	                            <p class="banner-bottom-center-p1">装修攻略</p>
	                            <p class="banner-bottom-center-p2">装修就上唯爱家</p>
	                        </div>
	                        <div class="banner-bottom-center-img">
	                            <img src="${ctx}/vij/images/zhuangxiugonglue_26.png">
	                        </div>
	                    </a>
                    </div>
                </li>
            </ul>
        </div>
        <div class="xianshi-tehui">
            <div class="xianshi-tehui-center">
                <div class="xianshi-tehui-text">
                    <div class="xianshi-tehui-text-t">
                        <div class="xianshi-tehui-text-tehui">
                            <p><a href="">限时特惠</a></p>
                        </div>
                        <div class="xianshi-tehui-text-more">
                            <button class="xianshi-tehui-text-bu"><a href=""></a>&lt;</button>
                            <button><a href=""></a>&gt;</button>
                        </div>
                    </div>
                </div>
                <ul class="xianshi-tehui-image flex-box">
                    <c:forEach var="c" items="${goodslist}" varStatus="index">
                            <a href="${ctx}/vij/index/gooddetail.do?goodsId=${c.goodsId}">
                                <li>
                                    <div class="xianshi-tehui-goods">
                                        <img src="${c.goodOneImage}">
                                    </div>
                                    <div class="xianshi-tehui-biaoti">
                                        <p class="xianshi-tehui-biaoti-p"> ${c.goodsName} </p>
                                    </div>
                                    <div class="xianshi-tehui-jiage">
                                        <p class="xianshi-tehui-jiage-p1" align="center">￥${c.sellPrice}</p>
                                        <p class="xianshi-tehui-jiage-p2" align="center">￥${c.firstCost}</p>
                                    </div>
                                </li>
                            </a>
                    </c:forEach>
                </ul>
            </div>
        </div>
         <c:if test="${fn:length(msadvlist)>0}">
            <c:forEach var="c" items="${msadvlist}" varStatus="index">
                <c:if test="${index.index == 0}">
                    <div class="xianshi-buttom-banner">
                        <div class="xianshi-buttom-banner-cen">
                            <div class="xianshi-buttom-banner-image">
                                <a href="${c.advJumpUrl}"><img src="${c.advUrl}" alt="${c.advName}"></a>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
        <div class="xinpin-tuichu flex-box">
            <div class="xinpin-tuichu-cen">
                <div class="xinpin-tuichu-text">
                    <div class="xinpin-tuichu-text-t">
                        <div class="xinpin-tuichu-text-tehui">
                            <p><a href="">新品推出</a></p>
                        </div>
                        <div class="xinpin-tuichu-text-more">
                            <p class="xinpin-tuichu-text-bu"><a href="${ctx}/vij/index/good.do">更多</a></p>
                        </div>
                    </div>
                </div>
                <div class="xinpin-tuichu-image flex-box">
                    <ul class="xinpin-tuichu-image-goods">
                        <c:forEach var="c" items="${xpgoodslist}" varStatus="index">
                                <a href="${ctx}/vij/index/gooddetail.do?goodsId=${c.goodsId}">
                                    <li class="xinpin-tuichu-image-li">
                                        <div class="xinpin-tuichu-image-div">
                                            <div class="xinpin-tuichu-goods">
                                                <img src="${c.goodOneImage}">
                                            </div>
                                            <div class="xinpin-tuichu-jiage">
                                                <p align="left">￥${c.sellPrice}</p><button>包邮</button>
                                            </div>
                                            <div class="xinpin-tuichu-biaoti">
                                                <p>
                                                    <a href="">${c.goodsName}</a>
                                                </p>
                                            </div>
                                            <!-- <div class="xinpin-tuichu-num">
                                                <p align="right">23人付款</p>
                                            </div> -->
                                        </div>
                                    </li>
                                </a>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <c:if test="${fn:length(xpadvlist)>0}">
            <c:forEach var="c" items="${xpadvlist}" varStatus="index">
                <c:if test="${index.index == 0}">
                    <div class="xianshi-buttom-banner" style="background-color: #f5f5f5;">
                        <div class="xianshi-buttom-banner-cen">
                            <div class="xianshi-buttom-banner-image">
                                <a href="${c.advJumpUrl}"><img src="${c.advUrl}" alt="${c.advName}"></a>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
        <div class="zhuliu-dapei">
            <div class="xinpin-tuichu-cen">
                <div class="xinpin-tuichu-text">
                    <div class="xinpin-tuichu-text-t">
                        <div class="xinpin-tuichu-text-tehui">
                            <p><a href="">推荐</a></p>
                        </div>
                        <div class="xinpin-tuichu-text-more">
                            <button class="xinpin-tuichu-text-bu"><a href="${ctx}/vij/index/coll.do">更多</a></button>
                        </div>
                    </div>
                </div>
                <div class="zhuliu-dapei-image">
                    <ul class="zhuliu-dapei-image-s flex-box">
                        <c:forEach var="coll" items="${collList2}">
                                <li class="dapei-image-s-li1">
                                    <div class="dapei-image-s-div1">
                                        <a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" title="${coll.collTitle}">
                                        	<img src="${ctx}/vij/images/lazy-image.png" class="lazy" data-original="${coll.collCover}">
                                        </a>
                                    </div>
                                    <%-- <div class="dapei-image-s-div2">
                                        <p class="dapei-image-s-div2-p1" align="center"><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}">${coll.collTitle}</a></p>
                                        <p class="dapei-image-s-div2-p2" align="center">${coll.collDesc}</p>
                                    </div> --%>
                                    <p class="beauty_info"><em>${coll.imagesCount}张</em><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" title="${coll.collTitle}">${coll.collTitle}</a></p>
                                    
                                </li>
                        </c:forEach>
                    </ul>
                    <div class="xinpin-tuichu-text">
                    <div class="xinpin-tuichu-text-t">
	                        <div class="xinpin-tuichu-text-tehui">
	                            <p><a href="">主流搭配</a></p>
	                        </div>
	                        <div class="xinpin-tuichu-text-more">
                            	<button class="xinpin-tuichu-text-bu"><a href="${ctx}/vij/index/coll.do?collType=1">更多</a></button>
                        	</div>
	                    </div>
	                </div>
                    <ul class="zhuliu-dapei-image-x flex-box">
                        <c:forEach var="coll" items="${collList}">
                                <li>
                                    <div class="dapei-image-x-image">
                                        <div class="dapei-image-x-image-image">
                                            <a href="${ctx}/vij/index/collDetail.do?id=${coll.id}">
                                                <img src="${ctx}/vij/images/lazy-image.png" class="lazy" data-original="${coll.collCover}">
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
                                    <div class="dapei-image-x-biao"><a href=""></a>
                                        <p align="center"><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}">${coll.collTitle}&nbsp;&nbsp;&nbsp;&nbsp;${coll.imagesCount}张</a></p>
                                    </div>
                                </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="zhuangxiu-fengge">
            <div class="zhuangxiu-fengge-center">
                <div class="zhuangxiu-fengge-text">
                    <div class="zhuangxiu-fengge-text-t">
                        <div class="zhuangxiu-fengge-text-tehui">
                            <p><a href="">装修风格</a></p>
                        </div>
                        <div class="xinpin-tuichu-text-more">
                            <button class="xinpin-tuichu-text-bu"><a href="${ctx}/vij/index/coll.do?collType=2">更多</a></button>
                        </div>
                    </div>
                </div>
                <ul class="zhuangxiu-fengge-image flex-box">
                    <c:forEach var="coll" items="${collList1}">
                            <li class="dapei-image-s-li1">
                                <div class="zhuangxiu-fengge-image-image">
                                    <a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" title="${coll.collTitle}">
                                    	<img src="${ctx}/vij/images/lazy-image.png" class="lazy" data-original="${coll.collCover}">
                                    </a>
                                </div>
                                <%-- <div class="zhuangxiu-fengge-image-text">
                                    <p align="center"><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}">${coll.collTitle}</a></p>
                                </div> --%>
                                <p class="beauty_info"><em>${coll.imagesCount}张</em><a href="${ctx}/vij/index/collDetail.do?id=${coll.id}" target="_blank" title="${coll.collTitle}">${coll.collTitle}</a></p>
                                
                            </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="guwen-fengcai">
            <div class="guwen-fengcai-center">
                <div class="guwen-fengcai-text">
                    <div class="guwen-fengcai-text-t">
                        <div class="guwen-fengcai-text-tehui">
                            <p><a href="">顾问风采</a></p>
                        </div>
                    </div>
                </div>
                <ul class="guwen-fengcai-image flex-box">
                    <c:forEach var="adviser" items="${adviserList}" varStatus="status">
                        <c:if test="${status.count%2==1}">
                           <%--  <li class="guwen-fengcai-image-li0">
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
                   <%--  <li class="guwen-fengcai-image-li3">
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
     <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/image-lazy.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
    	$("img.lazy").lazyload();
        var userId = "${vijMallUserSession.userId}";
        if (userId) {
            addUserAction( userId, 0, 7091, 1);
        }
        //设置top_banner的高度
        var top_banner_image_img = $(".top-banner-image img").height();
        $(".top-banner-image").height(top_banner_image_img);
        $(".top-banner").height(top_banner_image_img);
        //轮播图交互样式 begin
        $("#banner-center").append("<div></div>");
        $("#banner-center div:last").addClass("circle");
        //存储标签   定时器   动画偏移量   动画方向  li标签宽   运动到第几个标签
        var lunbo_lis = null,
            lunbo_spans = null;
        var lunbo_timer = null;
        var lunbo_num = 0;
        var lunbo_left = true;
        var li_width = $("#banner-center-ul li:eq(0)").width();
        var lunbo_f = 1;
        //浏览窗口宽度改变，重新设置li宽度，动画偏移量，动画方向
        $(window).resize(function() {
            clearInterval(lunbo_timer);
            li_width = $("#banner-center-ul li:eq(0)").width();
            lunbo_num = (lunbo_f - 1) * -li_width;
            lunbo_fun();
            lunbo_timer = setInterval(lunbo_fun, 4000);
        });
        //获取li          
        lunbo_lis = $("#banner-center-ul li");
        //设置span
        for (var i = 0; i < lunbo_lis.length; i++) {
            $(".circle").append("<span></span>");
            $(".circle span").addClass("span_circle");
            if (i == 0) {
                $(".circle span").addClass("current");
            }
        }
        //获取span
        lunbo_spans = $(".circle span");
        //设置span样式
        var span_fun = function(j) {
            for (var i = 0; i < lunbo_spans.length; i++) {
                if (i != j) {
                    $(".circle span:eq(" + i + ")").removeClass("current");
                } else {
                    $(".circle span:eq(" + i + ")").addClass("current");
                }
            }
        }
        var lunbo_fun = function() {
            if (lunbo_left) {
                lunbo_num = lunbo_num - li_width;
            } else {
                lunbo_num = lunbo_num + li_width;
            }
            if (lunbo_num == 0 || (lunbo_num < 100 && lunbo_num > -100)) {
                lunbo_left = true;
                lunbo_num = 0;
            }
            if (lunbo_num == -li_width * (lunbo_lis.length - 1) || (lunbo_num < -li_width * (lunbo_lis.length - 1) + 100 && lunbo_num > -li_width * (lunbo_lis.length - 1) - 100)) {
                lunbo_left = false;
                lunbo_num = -li_width * (lunbo_lis.length - 1);
            }
            $("#banner-center-slider").css({ "left": lunbo_num + "px" });
            console.log(Math.round(lunbo_num / li_width));
            if (lunbo_num == 0) {
                lunbo_f = Math.round(lunbo_num / li_width) + 1;
                span_fun(Math.round(lunbo_num / li_width));
            } else {
                lunbo_f = Math.round(lunbo_num / -li_width) + 1;
                span_fun(Math.round(lunbo_num / -li_width));
            }
        }
        //定时器
        lunbo_timer = setInterval(lunbo_fun, 4000);
        //设置鼠标悬停和离开
        for (var i = 0; i < lunbo_spans.length; i++) {
            $(".circle span:eq(" + i + ")").get(0).index = i;
            $(".circle span:eq(" + i + ")").mouseenter(function() {
                clearInterval(lunbo_timer);
                lunbo_num = this.index * -li_width + li_width;
                lunbo_left = true;
                lunbo_fun();
            })
            $(".circle span:eq(" + i + ")").mouseleave(function() {
                lunbo_timer = setInterval(lunbo_fun, 4000);
            })
        }
        //轮播图交互样式 end
    })
    </script>
</body>

</html>