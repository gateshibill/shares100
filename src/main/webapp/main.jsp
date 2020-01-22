<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all">
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" media="all">
<style>
.info-box {
	height: 85px;
	background-color: white;
	background-color: #ecf0f5;
}

.info-box .info-box-icon {
	border-top-left-radius: 2px;
	border-top-right-radius: 0;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 2px;
	display: block;
	float: left;
	height: 85px;
	width: 85px;
	text-align: center;
	font-size: 45px;
	line-height: 85px;
	background: rgba(0, 0, 0, 0.2);
}

.info-box .info-box-content {
	padding: 5px 10px;
	margin-left: 85px;
}

.info-box .info-box-content .info-box-text {
	display: block;
	font-size: 16px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	text-transform: uppercase;
}

.info-box .info-box-content .info-box-number {
	display: block;
	font-weight: bold;
	font-size: 25px;
}

.major {
	font-weight: 10px;
	color: #01AAED;
}



.main .layui-row {
	margin: 10px 0;
}

.info-box i {
	font-size: 1em;
	color: #fff;
}

.tmiddle {
	text-align: center;
	padding-top: 15px !important;
	cursor: pointer
}
.layui-fluid{
	padding:0px;
}
</style>
</head>
<body>
	<div class="layui-fluid main">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md2">
				<div class="info-box">
					<span class="info-box-icon"
						style="background-color: #54ade8 !important; color: white;">
						<i class="layui-icon">&#xe611;</i>
					</span>
					<div class="info-box-content tmiddle">
						<span class="info-box-number">${messageCount>0?messageCount:0}</span> <span class="info-box-text">咨询总数</span>
					</div>
				</div>
			</div>
			<div class="layui-col-md2">
				<div class="info-box">
					<span class="info-box-icon"
						style="background-color: #FF5722 !important; color: white;">
						<i class="layui-icon">&#xe612;</i>
					</span>
					<div class="info-box-content tmiddle">
						<span class="info-box-number">${goodsCount>0?goodsCount:0}</span> <span
							class="info-box-text">商品总数</span>
					</div>
				</div>
			</div>
			<div class="layui-col-md2">
				<div class="info-box">
					<span class="info-box-icon"
						style="background-color: #009688 !important; color: white;">
						<i class="layui-icon">&#xe613;</i>
					</span>
					<div class="info-box-content tmiddle">
						<span class="info-box-number">${userCount>0?userCount:0}</span> <span
							class="info-box-text">用户总数</span>
					</div>
				</div>
			</div>
			<c:if test="${loginPlat == null || loginPlat == ''}">
			<div class="layui-col-md2">
				<div class="info-box">
					<span class="info-box-icon"
						style="background-color: #5FB878 !important; color: white;">
						<i class="layui-icon">&#xe630;</i>
					</span>
					<div class="info-box-content tmiddle">
						<span class="info-box-number">${appCount}</span> <span
							class="info-box-text">应用总数</span>
					</div>
				</div>
			</div>
			</c:if>
			<div class="layui-col-md2">
				<div class="info-box">
					<span class="info-box-icon"
						style="background-color: #F7B824 !important; color: white;">
						<i class="layui-icon">&#xe637;</i>
					</span>
					<div class="info-box-content tmiddle">
						<span class="info-box-number">${descoveryCount>0?descoveryCount:0}</span> <span
							class="info-box-text">文章总数</span>
					</div>
				</div>
			</div>
			<div class="layui-col-md2">
				<div class="info-box">
					<span class="info-box-icon"
						style="background-color: #2F4056 !important; color: white;">
						<i class="layui-icon">&#xe62a;</i>
					</span>
					<div class="info-box-content tmiddle">
						<span class="info-box-number">${orderCount>0?orderCount:0}</span> <span
							class="info-box-text">订单总数</span>
					</div>
				</div>
			</div>
			<c:if test="${loginPlat != null && loginPlat != ''}">
			<div class="layui-col-md2">
				<div class="info-box">
					<span class="info-box-icon"
						style="background-color: #5FB878 !important; color: white;">
						<i class="layui-icon">&#xe630;</i>
					</span>
					<div class="info-box-content tmiddle">
						<span class="info-box-number">${moneyCount!=''?moneyCount:0}</span> <span
							class="info-box-text">总金额</span>
					</div>
				</div>
			</div>
			</c:if>
		</div>
	</div>

<c:if test="${loginPlat == null || loginPlat == ''}">
	<div class="layui-fluid main">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md6">
				<div class="sysNotice col">
					<blockquote class="layui-elem-quote title">基础统计</blockquote>
					<div class="layui-elem-quote layui-quote-nm"
						style="text-align: center">图形统计区域</div>
				</div>
			</div>
			<div class="layui-col-md6">
				<blockquote class="layui-elem-quote title">应用活跃度排名</blockquote>
				<div class="layui-elem-quote layui-quote-nm">
					<table class="layui-table">
						<tbody>
							<tr>
								<td class="version1">1.好事360</td>
							</tr>
							<tr>
								<td class="version1">2.爱乐飞狗</td>
							</tr>
							<tr>
								<td class="version1">3.铜仁商会</td>
							</tr>
							<tr>
								<td class="version1">4.深圳商会</td>
							</tr>
							<tr>
								<td class="version1">5.浩爷社区</td>
							</tr>
							<tr>
								<td class="version1">6.讯哥java讲堂</td>
							</tr>
							<tr>
								<td class="version1">7.辉哥漫画社</td>
							</tr>
							<tr>
								<td class="version1">8.广州商会</td>
							</tr>
							<tr>
								<td class="version1">9.潮汕商会</td>
							</tr>
							<tr>
								<td class="version1">10.惠州商会</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</c:if>
</body>
</html>