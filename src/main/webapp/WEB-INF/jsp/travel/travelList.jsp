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
	<blockquote class="layui-elem-quote">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
			<div class="layui-inline">
			<label class="layui-form-label">应用名称</label>
		    <div class="layui-input-inline">
		    	<div style="float:left;">
					<select id="loginPlat11" class="layui-input search_input" name="loginPlat" lay-search="">
					   <option value="">全部</option>
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
			<label class="layui-form-label">行程状态</label>
		    <div class="layui-input-inline">
		    	<div style="float:left;">
					<select id="state" class="layui-input search_input" name="state" lay-search="">
					   <option value="">全部</option>
					   <option value="1">可参团</option>
					   <option value="2">进行中</option>
					   <option value="3">已结束</option>
					   <option value="4">已删除</option>		
						</select>
				</div>
		    </div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">行程标题</label>
		    <div class="layui-input-inline">
		    	<input type="text" id="travelTitle" name="travelTitle" placeholder="请输入行程标题" 
		    	class="layui-input search_input" value="${travelTitle}"
		    	style="height:38px;line-height:38px;width:220px;">
		    </div>
		</div>
		<div class="layui-inline">
	        <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
<!-- 			<a class="layui-btn layui-btn-normal newsAdd_btn">添加</a>
 -->		</div>
	</form>
</blockquote>
	<table class="layui-table" id="affiche" lay-filter="affiche" 
		lay-data="{id: 'affiche'}"></table>
		<!--  
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="join">旅客</a>	
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="view">景点</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="comment">留言</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="update">编辑</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delete">删除</a>
    </script>
    -->
    <script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="join">旅客</a>	
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="view">景点</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="comment">留言</a>
    </script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
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
			pid = $("#loginPlat").val();
			getlist(pid, '', '', '', '', '', '', '');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var title = $("#travelTitle").val();
			var state = $("#state").val();
			getlist(pid,title,state);
		}

		function getlist(pid,title,state) {
			tableIns = table.render({
				elem : '#affiche',
				id : 'affiche',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'travelTitle':title,
					'state':state
				},
				url : '${ctx}/travel/gettravellist.do',
				cols : [ [ 
// 				 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'travelId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'travelTitle',
					title : '行程标题',
					width : 180,
					align : 'center'
				},{
					field : 'state',
					title : '行程状态',
					width : 130,
					align : 'center'
				},{
					field : 'loginPlat',
					title : '平台Id',
					width : 130,
					align : 'center'
				}, {
					field : 'travelJoinPeople',
					title : '加入人数',
					width : 200,
					align : 'center'
				}, {
					field : 'travelPublisherId',
					title : '发布者',
					width : 140,
					align : 'center',
					sort : true
				}, {
					field : 'createTime',
					title : '发布时间',
					width : 180,
					align : 'center',
					sort : true,
				},{
					title : '操作',
					width : 300,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				}] ],
				page : true,
				limits : [ 10, 15, 20, 30, 50, 100 ],
				limit : 15, //默认采用20
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

        
		$(window).one("resize",function(){
			$(".newsAdd_btn").click(function(){
				var index = layui.layer.open({
					title : "添加行程",
					type : 2,
					content : "${ctx}/travel/goaddtravel.do",
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
			})
		}).resize();
		
		table.on('tool(affiche)', function(obj) {
			var data = obj.data;
			if (obj.event === 'join') {
				var index = layui.layer.open({
					title : "加入行程旅客列表",
					type : 2,
					content : "${ctx}/travel/gojointravel.do?travelId="+data.travelId,
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
				//window.location.href="${ctx}/sharetask/gotaskdetail.do?taskId="+data.taskId;  //查看操作
			}  else if(obj.event === 'update'){
				
			}else if(obj.event === 'view'){
				var index = layui.layer.open({
					title : "行程景点",
					type : 2,
					content : "${ctx}/travel/gotravelview.do?travelId="+data.travelId,
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
			}else if(obj.event === 'comment'){
				var index = layui.layer.open({
					title : "行程留言列表",
					type : 2,
					content : "${ctx}/travel/gotravelcomment.do?travelId="+data.travelId,
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
			}else if(obj.event === 'delete'){
				
			}
		});
	
		
	
	</script>
</body>
</html>