<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/person_common.css">
<title>个人中心公共部分</title>
</head>
<body>
		<div class="navigation">
			<div class="navigation-center">
				<div class="navigation-center-le">
					<div class="navigation-center-logo">
						<a href="/" title="唯爱家商城首页"><img src="${systemUpset.companyLogo}"></a>
					</div>
				</div>
				<div class="navigation-center-ri">
					<div class="navigation-center-ri-na">
						<ul>
							<li class="navigation-center-ri-li1"><a href="${ctx}/vij/person/index.do">个人中心</a></li>
							<li class="navigation-center-ri-li2">
								<a href="${ctx}/vij/person/account.do" class="navigation-center-ri-li2-a">账户设置</a>
							</li>
							<li class="navigation-center-ri-li3"><a href="${ctx}/vij/person/message.do">消息</a></li>
						</ul>
					</div>
					 <div class="navigation-center-ri-so">
						<div class="navigation-center-ri-sousuo">
							<input type="text" name="" class="sousuo-input1" id="keyWord" placeholder="请输入搜索关键字">
							<input type="button" name="" onclick="search();" class="sousuo-input2"  value="搜索">
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
		<script type="text/javascript">
		 function search(){
		    	var keyWord = $('#keyWord').val();
		    	if(keyWord){
		    		window.location.href = "${ctx}/vij/index/list.do?goodsName="+keyWord;
		    	}
		    }
		</script>
</body>
</html>