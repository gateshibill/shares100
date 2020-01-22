<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>企业官网列表</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${ctx}/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/global.css" media="all">
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${ctx}/css/table.css?a=<%=new java.util.Random().nextInt()%>" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>" />

<style type="text/css">
.layui-form-select .layui-input {
	padding-right: 30px;
	cursor: pointer;
	width: 155px;
	height: 38px;
}
</style>
</head>
<body>
	<form class="layui-form" method="post" action="" id="listform">
		<blockquote class="layui-elem-quote news_search">	
				<div class="layui-inline pane-input">
					<label class="layui-form-label">应用名称</label>
					<div class="layui-input-block ">
						<select id="loginPlat" name="loginPlat"
							class="layui-input search-input" lay-search="">
							<option value="">全部</option>
							<c:forEach var="app" items="${appList}">
								<option value="${app.applicationId}">${app.applicationName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
			<div class="layui-inline">
				<a class="layui-btn search_btn" onclick="doSearch()">查询</a>
			</div>
		</blockquote>
	</form>
	<table class="layui-table" id="token" lay-filter="token"
		lay-data="{id: 'token'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="editSaler">编辑</a>
  <a class="layui-btn layui-btn-mini" lay-event="setSaler">设置</a>
</script>
<script type="text/html" id="isdefaultTpl">
    {{# var issj=d.isdefault}}
    {{# if (issj== 1){}}
    	<span style="color:green;">是</span>
    {{# } else{ }}
   		<span style="color:red">否</span>
    {{# } }}
</script>
	
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table'], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			getlist();
		});
		function doSearch() {
			var appId = $("#appId").val();
			getlist(appId);
		}

		function getlist(appId) {
			tableIns = table.render({
				elem : '#token',
				id : 'token',
				method : 'post',
				where : {			
					'appId':appId,
				},
				url : '${ctx}/saler/getSalerList.do',
				cols : [ [ {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'userName',
					title : '姓名',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'appId',
					title : '所属应用',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'isdefault',
					title : '是否默认',
					width : 100,
					align : 'center',
					sort : true,
					templet:"#isdefaultTpl"
				}, {
					field : 'visitedCount',
					title : '名片访问',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'orderCount',
					title : '订单数量',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'customerCount',
					title : '客户数量',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'interactCount',
					title : '互动频次',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'createTime',
					title : '创建时间',
					width : 200,
					align : 'center',
					sort : true
				}, {
					title : '操作',
					width : 200,
					align : 'center',
					fixed : 'right',
					toolbar : '#barDemo'
				} ] ],
				page : true,
				limits : [ 1, 10, 20, 30, 50, 100 ],
				limit : 20, //默认采用20
				width : '100%',
				height : 'auto',
				loading : true,
				response : {
					statusName : 'code' //数据状态的字段名称，默认：code
					,
					statusCode : 0 //成功的状态码，默认：0
					,
					msgName : 'msg' //状态信息的字段名称，默认：msg
					,
					countName : 'count' //数据总数的字段名称，默认：count
					,
					dataName : 'data' //数据列表的字段名称，默认：data
				},
				done : function(res, curr, count) {
					//如果是异步请求数据方式，res即为你接口返回的信息。
					//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
					//console.log(res);
					//得到当前页码
					//console.log(curr);
					//$("#curPageIndex").val(curr);
					//得到数据总量
					//console.log(count);
				}
			});

		}

	

		table
				.on(
						'tool(token)',
						function(obj) {
							var data = obj.data;
							if (obj.event === 'editSaler') {
								var index = layui.layer
								.open({
									title : "编辑销售",
									type : 2,
									content : "${ctx}/saler/editSaler.do?id="
											+ data.id,
									success : function(layero, index) {
										setTimeout(
												function() {
													layui.layer
															.tips(
																	'点击此处返回',
																	'.layui-layer-setwin .layui-layer-close',
																	{
																		tips : 3
																	});
												}, 500)
									}
								})
								layui.layer.full(index);
							}else if(obj.event === 'setSaler'){
								var isdefault = data.isdefault == 0?1:0;
								var title = "";
								if(isdefault == 1){
									title = "是否把该用户设置为默认销售?";	
								}else{
									title = "是否取消该用户为默认销售?";
								}
								layer
								.confirm(
										title,
										{
											btn : [ '确认', '取消' ]
										},
										function() {
											$
													.post(
															'${ctx}/saler/updateSaler.do',
															{
																'id' : data.id,
																'isdefault':isdefault
															},
															function(data) {
																if (data.code == 0) {
																	layer.msg('操作成功');
																	obj.update({
																		isdefault:isdefault,
																	})
																} else {
																	
																	layer.msg(data.msg, {
																		time : 2000
																	});
																}
															});
										}, function() {
											layer.closeAll();
										})
							}
						});

	
	</script>
</body>
</html>