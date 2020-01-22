<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>layui</title>
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
			<div class="layui-inline">
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="loginPlat11" class="layui-input search_input"
							name="loginPlat" lay-search="">
							<option value="">全部应用</option>
							<c:forEach var="app" items="${appList}">
								<c:choose>
									<c:when test="${loginPlat==app.applicationId}">
										<option selected="selected" value="${app.applicationId}">${app.applicationName}</option>
									</c:when>
									<c:otherwise>
										<option value="${app.applicationId}">${app.applicationName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<a class="layui-btn search_btn" onclick="doSearch()">查询</a> <a
					class="layui-btn layui-btn-normal newsAdd_btn">添加</a>
			</div>
		</blockquote>
	</form>
	<table class="layui-table" id="score" lay-filter="score"
		lay-data="{id: 'score'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="editScore">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delScore">删除</a>
</script>

	
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table'], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			plat = $("#loginPlat11").val();
			getlist(plat);
		});
		function doSearch() {
			var plat = $("#loginPlat11").val();
			getlist(plat);
		}

		function getlist(plat) {
			tableIns = table.render({
				elem : '#score',
				id : 'score',
				method : 'post',
				where : {
					'loginPlat' : plat,
				},
				url : '${ctx}/config/getConfig.do',
				cols : [ [ {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'loginPlat',
					title : '应用id',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'registerScore',
					title : '注册积分',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'dayLoginScore',
					title : '登陆积分',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'signScore',
					title : '签到积分',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'shareScore',
					title : '分享积分',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'visitScore',
					title : '一级邀请积分',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'twoVisitScore',
					title : '二级邀请积分',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'myInfoScore',
					title : '完善资料积分',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'buyScore',
					title : '购买积分',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'rechargeScore',
					title : '充值积分',
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
					field : 'updateTime',
					title : '更新时间',
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

		$(window)
				.one(
						"resize",
						function() {
							$(".newsAdd_btn")
									.click(
											function() {
												var loginPlat = $("#loginPlat11").val();
												if(!loginPlat){
													layer.msg("请选择应用");
													return false;
												}
												var index = layui.layer
														.open({
															title : "设置积分",
															type : 2,
															content : "${ctx}/config/goAddConfig.do?loginPlat="+loginPlat,
															success : function(
																	layero,
																	index) {
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
											})
						}).resize();

		table
				.on(
						'tool(score)',
						function(obj) {
							var data = obj.data;
							if (obj.event === 'editScore') {
								var index = layui.layer
										.open({
											title : "编辑积分",
											type : 2,
											content : "${ctx}/config/goUpdateConfig.do?id="
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
							}else if(obj.event === 'delScore'){
								layer
								.confirm(
										'您确认删除该条数据?',
										{
											btn : [ '确认', '取消' ]
										},
										function() {
											$
													.post(
															'${ctx}/config/delConfig.do',
															{
																'id' : data.id
															},
															function(data) {
																if (data.code == 0) {
																	layer.msg('删除成功');
																	obj.del();
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