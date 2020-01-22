<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>搭配列表</title>
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
				<label class="layui-form-label">广告位置</label>
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="advLocation" class="layui-input search_input"
							name="advLocation" lay-search="">
							<option value="">全部</option>
							<option value="1"
								<c:if test="${adv.advLocation == 1}">selected=""</c:if>>首页顶部广告</option>
							<option value="2"
								<c:if test="${adv.advLocation == 2}">selected=""</c:if>>首页轮播图</option>
							<option value="3"
								<c:if test="${adv.advLocation == 3}">selected=""</c:if>>首页秒杀广告</option>
							<option value="4"
								<c:if test="${adv.advLocation == 4}">selected=""</c:if>>首页新品广告</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">页面位置</label>
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="source" class="layui-input search_input"
							name="source" lay-search="">
							<option value="">全部</option>
							<option value="1"
								<c:if test="${adv.source == 1}">selected=""</c:if>>首页</option>
							<option value="2"
								<c:if test="${adv.source == 2}">selected=""</c:if>>新品页面</option>
							<option value="3"
								<c:if test="${adv.source == 3}">selected=""</c:if>>搭配</option>
							<option value="4"
								<c:if test="${adv.source == 4}">selected=""</c:if>>顾问</option>
							<option value="5"
								<c:if test="${adv.source == 5}">selected=""</c:if>>我们</option>
						</select>
					</div>
				</div>
			</div>

			<div class="layui-inline">
				<label class="layui-form-label">是否开启</label>
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="isEffect" class="layui-input search_input"
							name="isEffect" lay-search="">
							<option value="">全部</option>
							<option value="0"
								<c:if test="${adv.isEffect == 0}">selected=""</c:if>>不开启</option>
							<option value="1"
								<c:if test="${adv.isEffect == 1}">selected=""</c:if>>开启</option>
						</select>
					</div>
				</div>
			</div>

			<%-- <div class="layui-inline">
					<label class="layui-form-label">广告名称</label>
					<div class="layui-input-block">
						<input type="text" name="cnBrandName" id="cnBrandName" value="${brand.cnBrandName}"
								placeholder="请输入品牌名称" class="layui-input">
					</div>
				</div> --%>
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
	<script type="text/html" id="advLocationTpl">
    {{# var advLocation=d.advLocation}}
    {{# if (advLocation==1){}}
   		首页顶部广告
    {{# } else if(advLocation==2){}}
    	首页轮播图
 	{{# } else if(advLocation==3){}}
    	首页秒杀广告
 	{{# } else if(advLocation==4){}}
    	首页新品广告
    {{# } }}
</script>
<script type="text/html" id="sourceTpl">
    {{# var source=d.source}}
    {{# if (source==1){}}
   		首页
    {{# } else if(source==2){}}
    	新品页面
 	{{# } else if(source==3){}}
    	搭配
 	{{# } else if(source==4){}}
    	顾问
 	{{# } else if(source==5){}}
    	我们
    {{# } }}
</script>
<script type="text/html" id="isEffectTpl">
	{{# var isEffect =d.isEffect}}
	{{# if (isEffect==0){}}
		<span style="color:red;">不开启</span>
	{{# }else if(isEffect==1){ }}
	<span style="color:green;">开启</span>
	{{# } }}
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
			var isRecommend = $("#isEffect").val();
			var collType = $("#advLocation").val(); 
			var source = $("#source").val();
			getlist(isRecommend,collType,source);
		}

		function getlist(isRecommend,collType,source) {
			tableIns = table.render({
				elem : '#token',
				id : 'token',
				method : 'post',
				where : {				
					'isEffect':isRecommend,
					'advLocation':collType,
					 'source':source, 
				},
				url : '${ctx}/adv/getAdvList.do',
				cols : [ [ {
					field : 'advId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'advName',
					title : '广告名称',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'appId',
					title : '应用ID',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'isEffect',
					title : '是否开启',
					width : 150,
					align : 'center',
					sort : true,
					templet:"#isEffectTpl"
				}, {
					field : 'advJumpUrl',
					title : '广告跳转链接',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'advLocation',
					title : '广告位置',
					width : 150,
					align : 'center',
					sort : true,
					templet:"#advLocationTpl"
				}, {
					field : 'advOrder',
					title : '排序',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'source',
					title : '页面位置',
					width : 150,
					align : 'center',
					sort : true,
					templet:"#sourceTpl"
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
															title : "添加图片",
															type : 2,
															content : "${ctx}/adv/addAdv.do",
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
											title : "编辑图片",
											type : 2,
											content : "${ctx}/adv/updateAdv.do?advId="
													+ data.advId,
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
								layer.confirm(
										'您确认删除该条数据?',
										{
											btn : [ '确认', '取消' ]
										},
										function() {
											$
													.post(
															'${ctx}/adv/delAdv.do',
															{
																'advId' : data.advId
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