<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>轮播图列表</title>
<link rel="stylesheet"
	href="${ctx}/plugins/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/global.css" media="all">
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/table.css" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>" />
	<style type="text/css">
			.layui-elem-field {
			    margin-bottom: 10px;
			    padding: 0;
			    border: 1px solid #fff;
			}
	</style>
</head>
<body>
	<form method="post" action="" id="listform">
		<blockquote class="layui-elem-quote">
			<div class="search-area">
				<ul>
					<li><label style="margin-left: 0px">应用名称：</label>
						<div style="float: left;">
							<select id="loginPlat" class="search-input" name="loginPlat"
								lay-verify="required" lay-search="">
								<option value=" ">全部</option>
								<c:forEach var="app" items="${appList}">
									<option value="${app.applicationId}">${app.applicationName}</option>
								</c:forEach>
							</select>
						</div></li>
				</ul>
				<div style="float: right;">
					<!-- <a href="javascript:;" onclick="doSearch();"
						class="layui-btn layui-btn-small" style="margin: 0 10px"
						id="search"> <i class="layui-icon">&#xe615;</i> 搜索
					</a> -->
					<input type="submit" value="搜索">
				</div>
				<span style="clear: both; display: block;"></span>
			</div>
		</blockquote>
		<fieldset class="layui-elem-field">
			<legend>订单统计</legend>
			
			<div class="layui-field-box">
			<table class="layui-table">
    <colgroup>
      <col width="150">
      <col width="150">
      <col width="200">
      <col>
    </colgroup>
    <thead>
      <tr>
        <th>已付款订单数量（单）</th>
        <th>未付款订单数量（单）</th>
        <th>订单总数量（单）</th>
      </tr> 
    </thead>
    <tbody>
      <tr>
        <td>${payordercount>0?payordercount:0}</td>
        <td>${nopayordercount>0?nopayordercount:0}</td>
        <td>${totalorder>0?totalorder:0}</td>
      </tr>
      
      
 
    </tbody>
  </table>
			</div>
		</fieldset>
		
		<fieldset class="layui-elem-field">
			<legend>资金统计</legend>
			
			<div class="layui-field-box">
			<table class="layui-table">
    <colgroup>
      <col width="150">
      <col width="150">
      <col width="200">
      <col>
    </colgroup>
    <thead>
      <tr>
        <th>商品收入(元)</th>
        <th>其他收入(元)</th>
        <th>总金额(元)</th>
      </tr> 
    </thead>
    <tbody>
      
      <tr>
        <td>${smoney!=null?smoney:0}</td>
        <td>${othermoney!=null?othermoney:0}</td>
        <td>${totalmoney!=null?totalmoney:0}</td>
      </tr>
    </tbody>
  </table>
			</div>
		</fieldset>
		<div class="admin-table-page">
			<div id="pages" class="page"></div>
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>

</html>