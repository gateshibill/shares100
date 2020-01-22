<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>视频列表</title>
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
					<label class="layui-form-label">应用ID</label>
					<div class="layui-input-block">
						<input type="text" name="appId" id="appId" value="${asset.appId}"
								placeholder="请输入appId" class="layui-input">
					</div>
			</div>	
			<div class="layui-inline">
					<label class="layui-form-label">视频名称</label>
					<div class="layui-input-block">
						<input type="text" name="name" id="name" value="${asset.name}"
								placeholder="请输入视频名称" class="layui-input">
					</div>
			</div>
				
				
			<div class="layui-inline">
				<a class="layui-btn search_btn" onclick="doSearch()">查询</a> <a
					class="layui-btn layui-btn-normal newsAdd_btn">添加</a>
			</div>
		</blockquote>
	</form>
	<table class="layui-table" id="vedio" lay-filter="vedio"
		lay-data="{id: 'vedio'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="editVedio">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="editVedio">删除</a>
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
			var name = $("#name").val();
			var appId = $("#appId").val();
			getlist(name,appId);
		}

		function getlist(name,appId) {
			tableIns = table.render({
				elem : '#vedio',
				id : 'vedio',
				method : 'post',
				where : {			
					'name':name,
					'appId':appId,
				},
				url : '${ctx}/asset/getAssetList.do',
				cols : [ [ {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'appId',
					title : '所属应用',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'name',
					title : '视频名称',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'director',
					title : '导演名称',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'actor',
					title : '演员',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'type',
					title : '类型',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'duration',
					title : '时长',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'area',
					title : '地区',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'language',
					title : '语言',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'source',
					title : '来源',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'lableIds',
					title : '标签集',
					width : 200,
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

		$(window)
				.one(
						"resize",
						function() {
							$(".newsAdd_btn")
									.click(
											function() {
												
												var index = layui.layer
														.open({
															title : "添加视频",
															type : 2,
															content : "${ctx}/asset/add.do",
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
							if (obj.event === 'editVedio') {
								var index = layui.layer
										.open({
											title : "编辑企业应用",
											type : 2,
											content : "${ctx}/website/edit.do?id="
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
							}else if(obj.event === 'delVedio'){
								layer
								.confirm(
										'您确认删除该条数据?',
										{
											btn : [ '确认', '取消' ]
										},
										function() {
											$
													.post(
															'${ctx}/website/delWebSite.do',
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