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
				<label class="layui-form-label">是否推荐</label>
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="isRecommend" class="layui-input search_input"
							name="isRecommend" lay-search="">
							<option value="">全部</option>
							<option  value="0" <c:if test="${coll.isRecommend == 0}">selected=""</c:if> >否</option>
							<option  value="1" <c:if test="${coll.isRecommend == 1}">selected=""</c:if>>是</option>		
						</select>
					</div>
				</div>
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label">类型</label>
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="collType" class="layui-input search_input"
							name="collType" lay-search="">
							<option value="">全部</option>
							<option  value="1" <c:if test="${coll.collType == 1}">selected=""</c:if> >主流搭配</option>
							<option  value="2" <c:if test="${coll.collType == 2}">selected=""</c:if>>装修风格</option>		
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
   {{# if(d.collType == 1){}}
    <a class="layui-btn layui-btn-mini" lay-event="goodcoll">关联商品</a>
  {{#}}}
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delToken">删除</a>
</script>
<script type="text/html" id="isRecommentTpl">
    {{# var isRecommend=d.isRecommend}}
    {{# if (isRecommend==1){}}
   		<span style="color:green;">是</span>
    {{# } else{ }}
    	<span style="color:red;">否</span>
    {{# } }}
</script>	
<script type="text/html" id="collTypeTpl">
{{# var collType =d.collType}}
{{# if (collType==1){}}
		<span style="color:green;">主流搭配</span>
{{# } else if(collType==2){ }}
	<span style="color:red;">装修风格</span>
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
			var isRecommend = $("#isRecommend").val();
			var collType = $("#collType").val();
			getlist(isRecommend,collType);
		}

		function getlist(isRecommend,collType) {
			tableIns = table.render({
				elem : '#token',
				id : 'token',
				method : 'post',
				where : {				
					'isRecommend':isRecommend,
					'collType':collType
				},
				url : '${ctx}/coll/getCollList.do',
				cols : [ [ {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'collTitle',
					title : '标题',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'collDesc',
					title : '描述',
					width : 200,
					align : 'center',
					sort : true
				}, {
					field : 'isRecommend',
					title : '是否推荐',
					width : 100,
					align : 'center',
					sort : true,
					templet:"#isRecommentTpl"
				}, {
					field : 'collDetail',
					title : '详情',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'collType',
					title : '类型',
					width : 150,
					align : 'center',
					sort : true,
					templet:"#collTypeTpl"
				}, {
					field : 'orderStatus',
					title : '排序',
					width : 70,
					align : 'center',
					sort : true
				}, {
					field : 'createTime',
					title : '时间',
					width : 180,
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
															title : "添加搭配",
															type : 2,
															content : "${ctx}/coll/addColl.do",
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
											title : "编辑搭配",
											type : 2,
											content : "${ctx}/coll/updateColl.do?id="
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
							}else if(obj.event === 'goodcoll'){
								var index = layui.layer
								.open({
									title : data.collTitle+"--关联商品",
									type : 2,
									content : "${ctx}/coll/goodColl.do?collId="
											+ data.id+"&loginPlat="+data.loginPlat,
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
															'${ctx}/coll/delColl.do',
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