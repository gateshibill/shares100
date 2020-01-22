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
	.layui-form-item{
		margin-bottom:0px;
	}
</style>
</head>
<body>
	<blockquote class="layui-elem-quote">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
			<div class="layui-form-item">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">应用名称</label>
					<div class="layui-input-block ">
						<select id="loginPlat" name="loginPlat"
							class="layui-input search-input" lay-search="">
							<c:forEach var="app" items="${appList}">
								<option value="${app.applicationId}">${app.applicationName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
				
				
				<div class="layui-inline">
					<label class="layui-form-label">是否轮播</label>
					<div class="layui-input-block">
						<select id="isUsing" name="isUsing"
							class="layui-input search-select">
							<option value="" selected="selected">全部</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">图片编号</label>
					<div class="layui-input-block">
						<input type="text" name="pictureId" id="pictureId"
							placeholder="请输入图片编号" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">图片名称</label>
					<div class="layui-input-block">
						<input type="text" name="pictureName" id="pictureName"
							placeholder="请输入图片名称" class="layui-input">
					</div>
				</div>
				
				<!-- 
				<div class="layui-inline">
					<label class="layui-form-label">发布者编号</label>
					<div class="layui-input-block">
						<input type="text" name="userId" id="userId"
							placeholder="请输入发布者编号" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布者名称</label>
					<div class="layui-input-block">
						<input type="text" name="userKeyWords" id="userKeyWords"
							placeholder="请输入发布者名称" class="layui-input">
					</div>
				</div> -->

				<div class="layui-inline">
					<a class="layui-btn search_btn" onclick="doSearch()"> <i
						class="layui-icon">&#xe615;</i>查询
					</a>
				</div>
			</div>
	</form>
</blockquote>
	<div class="layui-btn-group demoTable">
		<button class="layui-btn newsAdd_btn" style="margin-left: 10px;"
			data-type="addPicture">
			<i class="layui-icon">&#xe608;</i>发布轮播图
		</button>
	</div>
	<table class="layui-table" id="picture" lay-filter="picture" lay-data="{id: 'picture'}"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-mini" lay-event="editPicture">编辑</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delPicture">删除</a>

</script>
	<!-- 上架状态模板 -->
	<script type="text/html" id="isUsingTpl">
    {{# var issj=d.isUsing}}
    {{# if (issj== 1){}}
    	<span style="color:green;">是</span>
    {{# } else{ }}
   		<span style="color:red">否</span>
    {{# } }}
</script>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
layui.use([ 'layer', 'table', 'laydate' ], function() {
	table = layui.table;
	layer = layui.layer;
	var laydate = layui.laydate;
	//日期范围
	laydate.render({
		elem : '#dateRange',
		range : true
	});
	//--------------方法渲染TABLE----------------
	var isUsing = $("#isUsing").val();
	var loginPlat = $("#loginPlat").val();
	var pictureId = $("#pictureId").val();
	var picName = $("#pictureName").val();
	var userId = $("#userId").val();
	var userKeyWords = $("#userKeyWords").val();
	getlist(isUsing, loginPlat, pictureId, picName, userId, userKeyWords);
});
function doSearch() {
	var isUsing = $("#isUsing").val();
	var loginPlat = $("#loginPlat").val();
	var pictureId = $("#pictureId").val();
	var picName = $("#pictureName").val();
	var userId = $("#userId").val();
	var userKeyWords = $("#userKeyWords").val();
	getlist(isUsing, loginPlat, pictureId, picName, userId, userKeyWords);
}

function getlist(isUsing, loginPlat, pictureId, picName, userId, userKeyWords) {
	tableIns = table.render({
		elem : '#picture',
		id : 'picture',
		method : 'post',
		where : {
			"isUsing" : isUsing,
			"loginPlat" : loginPlat,
			"pictureId" : pictureId,
			"picName" : picName,
			"userId" : userId,
			"userKeyWords" : userKeyWords,
		},
		url : '${ctx}/carousel/carouselList.do',
		cols : [ [ 
//			{
//				checkbox : true,
//				LAY_CHECKED : false,
//				fixed : true
//			}, 
		{
			field : 'pictureId',
			title : 'ID',
			width : 80,
			align : 'center',
			fixed : true,
			sort : true
		}, {
			field : 'pictureName',
			title : '图片名称',
			width : 200,
			align : 'center',
		}, {
			field : 'loginPlat',
			title : '应用ID',
			width : 120,
			align : 'center',
		},
		/* {
			field : 'uploadUser',
			title : '发布者ID',
			width : 200,
			align : 'center'
		}, {
			field : 'pictureUser',
			title : '发布者名称',
			width : 120,
			align : 'center',
		},  */
		{
			field : 'isUsing',
			title : '是否轮播',
			width : 120,
			align : 'center',
			templet : "#isUsingTpl"
		}, {
			field : 'orderId',
			title : '轮播顺序',
			width : 140,
			align : 'center',
				sort : true,
		}, {
			field : 'uploadTime',
			title : '发布时间',
			width : 180,
			align : 'center',
			sort : true,
		}, {
			title : '操作',
			width : 210,
			align : 'center',
			fixed : 'right',
			toolbar : '#toolBar'
		} ] ],
		page : true,
		limits : [ 1, 10, 20, 30, 50, 100 ],
		limit : 20, //默认采用20
		width : '100%',
		height : 'auto',
		loading : true,
		even : true,
		response : {
			statusName : 'code' //数据状态的字段名称，默认：code
			,
			statusCode : 0 //成功的状态码，默认：0
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
/* table.on('tool("picture")', function(obj) {
	var data = obj.data;
	if (obj.event === 'editPicture') {
		alert(123);
		//${ctx}/carousel/showDetails.do?pictureId={{value.pictureId}}
		var index = layui.layer
		.open({
			title : "编辑轮播图",
			type : 2,
			content : "${ctx}/carousel/showDetails.do?pictureId="+data.pictureId,
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
	} else if (obj.event === 'delPicture') {
		$.ajax({
			data : 'json',
			url : '${ctx}/withdraw/dealWithdraw.do',
			type : "post",
			data : {
				withdrawId : data.withdrawId
			},
			success : function(result) {
				if (result.code == '0') {
					layer.msg(result.msg);
					//同步更新缓存对应的值
					obj.update({
						withdrawState : 2
					});
				} else {
					layer.msg(result.msg);
					return;
				}
			}
		});
	} 
}); */


table
.on(
		'tool(picture)',
		function(obj) {
			var data = obj.data;
			if (obj.event === 'editPicture') {
				var index = layui.layer
				.open({
					title : "编辑轮播图",
					type : 2,
					content : "${ctx}/carousel/showDetails.do?pictureId="+data.pictureId,
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
			} else if (obj.event === 'delPicture') {
				layer.confirm('您确定将该轮播图出除轮播?', {
					btn : [ '取消', '确认' ]
				}, function() {
					layer.closeAll();
				}, function() {
					$.post('${ctx}/carousel/delCarousel.do', {
						'pictureId' : data.pictureId
					}, function(data) {
						if (data.code == 0) {
							 layer.msg("操作成功");
							 obj.del();
						} else {
							layer.msg('图片取出轮播序列失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				})
			}	
		});

 
$(window)
.one(
		"resize",
		function() {
			$(".newsAdd_btn")
					.click(
							function() {
								var loginPlat = $("#loginPlat").val();
								if(!loginPlat){
									layer.msg("请选择应用");
									return;
								}
								var index = layui.layer
										.open({
											title : "添加轮播图",
											type : 2,
											content : "${ctx}/carousel/goAddCarousel.do?id="+loginPlat,
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
</script>
</body>
</html>