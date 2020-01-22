<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>企业应用列表</title>
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
			<%-- <div class="layui-inline">
					<label class="layui-form-label">企业号名称</label>
					<div class="layui-input-block">
						<input type="text" name="workName" id="workName" value="${work.workName}"
								placeholder="请输入企业号名称" class="layui-input">
					</div>
			</div>
			<div class="layui-inline">
					<label class="layui-form-label">企业ID</label>
					<div class="layui-input-block">
						<input type="text" name="qyId" id="qyId" value="${work.qyId}"
								placeholder="请输入企业ID" class="layui-input">
					</div>
				</div> --%>
				<div class="layui-inline">
					<label class="layui-form-label">应用名称</label>
					<div class="layui-input-block">
						<input type="text" name="appName" id="appName" value="${work.appName}"
								placeholder="请输入应用名称" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">应用ID</label>
					<div class="layui-input-block">
						<input type="text" name="agentId" id="agentId" value="${work.agentId}"
								placeholder="请输入应用ID" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">小程序id</label>
					<div class="layui-input-block">
						<input type="text" name="xcxAppId" id="xcxAppId" value="${work.xcxAppId}"
								placeholder="请输入小程序ID" class="layui-input">
					</div>
				</div>
			<div class="layui-inline">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="isEffect" class="layui-input search_input"
							name="isEffect" lay-search="">
							<option value="">全部</option>
							<option  value="0" <c:if test="${work.isEffect == 0}">selected=""</c:if> >禁用</option>
							<option  value="1" <c:if test="${work.isEffect == 1}">selected=""</c:if>>启用</option>		
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
	<table class="layui-table" id="token" lay-filter="token"
		lay-data="{id: 'token'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="editToken">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delToken">删除</a>
  
	</script>

	
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table'], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			//plat = $("#loginPlat11").val();
			getlist();
		});
		function doSearch() {
			var workName = $("#workName").val();
			var qyId = $("#qyId").val();
			var appName = $("#appName").val();
			var agentId = $("#agentId").val();
			var isEffect = $("#isEffect").val();
			var xcxAppId = $("#xcxAppId").val();
			getlist(workName,qyId,appName,agentId,isEffect,xcxAppId);
		}

		function getlist(workName,qyId,appName,agentId,isEffect,xcxAppId) {
			tableIns = table.render({
				elem : '#token',
				id : 'token',
				method : 'post',
				where : {
					'workName' : workName,
					'qyId':qyId,
					'appName':appName,
					'agentId':agentId,
					'isEffect':isEffect,
					'xcxAppId':xcxAppId
				},
				url : '${ctx}/workweixin/getworklist.do?xcxAppId='+'${loginer.loginPlat}',
				cols : [ [ {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'workName',
					title : '企业号名称',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'qyId',
					title : '企业ID',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'appName',
					title : '应用名称',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'agentId',
					title : '应用id',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'appSecret',
					title : '应用密钥',
					width : 400,
					align : 'center',
					sort : true
				}, {
					field : 'workId',
					title : '应用编号',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'xcxAppId',
					title : '小程序ID',
					width : 150,
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
												
												var index = layui.layer
														.open({
															title : "添加企业应用",
															type : 2,
															content : "${ctx}/workweixin/addWork.do",
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
						'tool(token)',
						function(obj) {
							var data = obj.data;
							if (obj.event === 'editToken') {
								var index = layui.layer
										.open({
											title : "编辑企业应用",
											type : 2,
											content : "${ctx}/workweixin/updateWork.do?id="
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
							}else if(obj.event === 'delToken'){
								layer
								.confirm(
										'您确认删除该条数据?',
										{
											btn : [ '确认', '取消' ]
										},
										function() {
											$
													.post(
															'${ctx}/workweixin/delWork.do',
															{
																'workId' : data.workId
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
							}else if(obj.event === 'goSystem'){
								if(data.xcxAppId == null || data.xcxAppId == ""){
									layer.msg('小程序ID为空，不能进入pc官网');
								}else{
									var index = layui.layer
									.open({
										title : "PC官网",
										type : 2,
										content : "${ctx}/system/goAddSystemUpset.do?appId="
												+ data.xcxAppId,
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
								}
						layui.layer.full(index);
							}
						});

	
	</script>
</body>
</html>