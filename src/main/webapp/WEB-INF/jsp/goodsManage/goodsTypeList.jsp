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
				<div class="layui-input-inline">
					<input type="text" id="keyword" placeholder="请输入类型名称"
						class="layui-input search_input" value="${name}"
						style="height: 38px; line-height: 38px; width: 220px;">
				</div>
			</div>
			<%-- 
			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" id="applicationName" placeholder="请输入应用名称"
						class="layui-input search_input" value="${applicationName}"
						style="height: 38px; line-height: 38px; width: 220px;">
				</div>
			</div> --%>
			<div class="layui-inline">
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="isRemove" class="layui-input search_input"
							name="isRemove" lay-search="">
							<option value="">是否显示</option>
							<option value="0">是</option>
							<option value="1">否</option>
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
	<table class="layui-table" id="customer" lay-filter="customer"
		lay-data="{id: 'customer'}"></table>
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-mini" lay-event="childType">子分类</a>
  		<a class="layui-btn layui-btn-mini" lay-event="edittaskcontent">编辑</a>
  		<a class="layui-btn layui-btn-mini" lay-event="goodsTypeOrder">排序</a>
  		<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteGoodsType({{d.typeId}})">删除</a>
	</script>
	<!-- typeNameTpl -->

	<script type="text/html" id="typeNameTpl">
      {{# var typeName=d.typeName}}
     <a href="${ctx}/goods/goodsClassifyList.do?loginPlat={{d.loginPlat}}&goodsType={{d.typeId}}" style="color:#1E9FFF">{{typeName}}</a>  
</script>
	<script type="text/html" id="appNameTpl">
       {{#if(d.application){}}
              {{d.application.applicationName}}
       {{# }else{}}
        <span style="color:red"> 所有应用   </span>   
       {{#}}}
</script>

	<script type="text/html" id="isRemoveTpl">
       {{#if(d.isRemove == 0){}}
             <a style="color:green" lay-event="setType">是</a>
       {{# }else{}}
       		 <a style="color:red" lay-event="setType"> 否 </a>   
       {{#}}}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table', 'laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			var laydate = layui.laydate;
			//--------------方法渲染TABLE----------------
			plat = $("#loginPlat11").val();
			name = $("#keyword").val();
			getlist(name, plat);
		});
		function doSearch() {
			var name = $("#keyword").val();
			var plat = $("#loginPlat11").val();
			var isRemove = $("#isRemove").val();
			getlist(name, plat, isRemove);
		}

		function getlist(name, plat, isRemove) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method : 'post',
				where : {
					'typeName' : name,
					'loginPlat' : plat,
					'isRemove' : isRemove,
				},
				url : '${ctx}/goods/showGoodsTypeList.do',
				cols : [ [ {
					field : 'typeId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'typeName',
					title : '类型名称',
					width : 150,
					align : 'center',
					templet : "#typeNameTpl"
				}, {
					field : 'appName',
					title : '应用名称',
					width : 100,
					align : 'center',
					templet : "#appNameTpl"
				}, {
					field : 'loginPlat',
					title : '应用Id',
					width : 100,
					align : 'center',
				}, 
				/* {
					field : 'nickName',
					title : '发布者昵称',
					width : 200,
					align : 'center',
					templet : "#nickNameTpl"
				},  */
				{
					field : 'isRemove',
					title : '是否显示',
					width : 100,
					align : 'center',
					templet : "#isRemoveTpl"
				}, {
					field : 'orderNo',
					title : '排列顺序',
					width : 100,
					align : 'center',
				}, {
					field : 'createTime',
					title : '创建时间',
					width : 180,
					align : 'center'
				}, {
					title : '操作',
					width : 250,
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
															title : "添加商品类型",
															type : 2,
															content : "${ctx}/goods/addGootype.do",
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
						'tool(customer)',
						function(obj) {
							var data = obj.data;
							if (obj.event === 'edittaskcontent') {
								var index = layui.layer
										.open({
											title : "编辑商品类型",
											type : 2,
											content : "${ctx}/goods/updaGoodsType.do?typeId="
													+ data.typeId,
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
							}else if(obj.event === 'childType'){ //子分类
								var index = layui.layer
								.open({
									title : data.typeName+"--子分类",
									type : 2,
									content : "${ctx}/goods/childGoodsType.do?parentId="+ data.typeId,
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
							}else if (obj.event === 'goodsTypeOrder') {
								layer
										.confirm(
												'<div class="layui-inline">'
														+ '<label class="out-form-label">排列序号：</label>'
														+ '<div class="layui-input-inline">'
														+ '<input type="number" id="orderId" min="1" name="number" lay-verify="number" autocomplete="off" class="layui-input">'
														+ '</div>' + '</div>',
												{
													btn : [ '确认', '取消' ]
												},
												function() {
													$
															.post(
																	'${ctx}/goods/updateGoodsTypeOrder.do',
																	{
																		'typeId' : data.typeId,
																		'orderNo' : $(
																				"#orderId")
																				.val(),
																	},
																	function(
																			data) {
																		if (data.code == 0) {
																			layer
																					.msg('修改排序成功');
																			//同步更新缓存对应的值
																			obj
																					.update({
																						orderNo : $(
																								"#orderId")
																								.val(),
																					});
																		} else {
																			layer
																					.msg(
																							data.msg,
																							{
																								time : 2000
																							});
																		}
																	});
												}, function() {
													layer.closeAll();
												})
							}else if(obj.event === 'setType'){
								 var isRemove = data.isRemove;
								 if(isRemove == 1){
									 isRemove = 0;
								 }else{
									 isRemove = 1;
								 }
								 var typeId = data.typeId;
								 $.ajax({
						    		 url:'${ctx}/goods/updateTypeName.do',
						    		 type:'post',
						    		 dataType:'json',
						    		 data:{
						    			 'typeId':typeId,
						    			 'loginPlat' : data.loginPlat,
						    			 'typeName' : data.typeName,
						    			 'isRemove':isRemove
						    			 },
						    		 success:function(data){
						    			 if(data.code==0){
						    				  layer.msg(data.msg);
						    				//同步更新缓存对应的值
												obj
														.update({
															isRemove : isRemove
														});
						    			 }else{
						    				 layer.msg(data.msg);
						    				 return;
						    			 }
						    		 }
						    	});
							}
						});

		function deleteGoodsType(tid) {
			layer
					.confirm(
							'您确认删除该商品类型?',
							{
								btn : [ '确认', '取消' ]
							},
							function() {
								$
										.post(
												'${ctx}/goods/deleteGoodsType.do',
												{
													'typeId' : tid
												},
												function(data) {
													if (data.code == 0) {
														layer.msg('删除商品类型成功');
														setTimeout(
																function() {
																	// 							window.location.reload();
																	window.location.href = "${ctx}/goods/goodsTypeList.do?loginPlat="
																			+ $(
																					'#loginPlat11')
																					.val()
																			+ "&name="
																			+ $(
																					'#keyword')
																					.val();
																}, 400);
													} else {
														/* icon : 0 */
														layer.msg(data.msg, {
															time : 2000
														});
													}
												});
							}, function() {
								layer.closeAll();
							})
		}
	</script>
</body>
</html>