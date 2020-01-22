<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>公共版权页脚</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/copyright.css">
    <style type="text/css">
    	.navigation-center-ri-sousuo input{
    		padding:0 0.25rem;
    		font-size:1.0rem;
    	}
    	input::-webkit-input-placeholder, textarea::-webkit-input-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
		
		input:-moz-placeholder, textarea:-moz-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
		
		input::-moz-placeholder, textarea::-moz-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
		
		input:-ms-input-placeholder, textarea:-ms-input-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
    </style>
</head>
<body>
    <div class="copyright">
			<div class="copyright-sha"></div>
			<div class="copyright-xia">
				<div class="xia-title">
					<ul class="title-cen flex-box">
						<li class="title-cen-li1">
							<img src="${ctx}/vij/images/she_03.gif">
							<p align="center">专业设计</p>
						</li>
						<li>
							<img src="${ctx}/vij/images/fu_05.gif">
							<p align="center">贴心服务</p>
						</li>
						<li>
							<img src="${ctx}/vij/images/zhi_07.gif">
							<p align="center">质量保障</p>
						</li>
						<li>
							<img src="${ctx}/vij/images/jia_09.gif">
							<p align="center">超低价格</p>
						</li>
						<li>
							<img src="${ctx}/vij/images/bao_11.gif">
							<p align="center">绿色环保</p>
						</li>
						<li>
							<img src="${ctx}/vij/images/shou_13.gif">
							<p align="center">售后保障</p>
						</li>
						
					</ul>
				</div>
				
				<div class="xia-content">
					<div class="content-cen">
						<div class="content-cen-left">
							<div class="cen-left-l">
								<img src="${systemUpset.weChatSmall}">
								<p align="center">小程序</p>
							</div>
							<div class="cen-left-r">
								<img src="${systemUpset.weChatCommon}">
								<p align="center">微信公众号</p>
							</div>
						</div>
						<div class="content-cen-right">
							<div class="cen-right-content">
								<p align="left">地址：${systemUpset.companyAddres}</p>
								<p align="left">全国统一服务与热线：${systemUpset.phone}</p>
								<p align="left">${systemUpset.companyName}&nbsp;版权所有黔ICP备18009041号</p>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>

</html>