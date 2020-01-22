<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>项目管理列表</title>
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
				<label class="layui-form-label">装修状态</label>
				<div class="layui-input-inline">
					<div style="float: left;">
						<select id="status" class="layui-input search_input"
							name="status" lay-search="">
							<option value="">全部</option>
							<option  value="1" <c:if test="${project.status == 1}">selected="selected"</c:if> >选择顾问</option>
							<option  value="2" <c:if test="${project.status == 2}">selected="selected"</c:if>>预约量房</option>		
							<option  value="3" <c:if test="${project.status == 3}">selected="selected"</c:if>>选择风格</option>		
							<option  value="4" <c:if test="${project.status == 4}">selected="selected"</c:if>>预交定金</option>	
							<option  value="5" <c:if test="${project.status == 5}">selected="selected"</c:if>>确定设计</option>		
							<option  value="6" <c:if test="${project.status == 6}">selected="selected"</c:if>>确定报价</option>		
							<option  value="7" <c:if test="${project.status == 7}">selected="selected"</c:if>>装修施工</option>		
							<option  value="8" <c:if test="${project.status == 8}">selected="selected"</c:if>>竣工验收</option>		
							<option  value="9" <c:if test="${project.status == 9}">selected="selected"</c:if>>客户评价</option>		
							<option  value="10" <c:if test="${project.status == 10}">selected="selected"</c:if>>项目结束</option>								
						</select>
					</div>
				</div>
			</div>
			
			<div class="layui-inline">
					<label class="layui-form-label">项目名称</label>
					<div class="layui-input-block">
						<input type="text" name="name" id="name" value="${project.name}"
								placeholder="请输入项目名称" class="layui-input">
					</div>
			</div>
			<div class="layui-inline">
				<a class="layui-btn search_btn" onclick="doSearch()">查询</a> 
				<a class="layui-btn layui-btn-normal newsAdd_btn">添加</a>
			</div>
		</blockquote>
	</form>
	
	<table class="layui-table" id="token" lay-filter="token"
		lay-data="{id: 'token'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="indexToken">项目明细</a>
  <a class="layui-btn layui-btn-mini" lay-event="editToken">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delToken">删除</a>
</script>
<script type="text/html" id="statusTpl">
    {{# var status=d.status}}
    {{# if (status==1){}}
   		<span style="color:green;">选择顾问</span>
    {{# } else if(status == 2){ }}
    	<span style="color:green;">预约量房</span>
	{{# } else if(status == 3){ }}
    	<span style="color:green;">选择风格</span>
	{{# } else if(status == 4){ }}
    	<span style="color:green;">预交定金</span>
    {{# }else if(status == 5){ }}
		<span style="color:green;">确定设计</span>
	{{# }else if(status == 6){ }}
		<span style="color:green;">确定报价</span>
	{{# }else if(status == 7){ }}
		<span style="color:green;">装修施工</span>
	{{# }else if(status == 8){ }}
	    <span style="color:green;">竣工验收</span>
	{{# }else if(status == 9){ }}
		<span style="color:green;">客户评价</span>
	{{# }else { }}
		<span style="color:green;">已结束</span>
	{{# } }}
</script>	
	
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
	var userId ="${userId}";
		layui.use([ 'layer', 'table'], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			//plat = $("#loginPlat11").val();
			getlist(userId,null,null);
		});
		function doSearch() {
			var statued = $("#status").val();
			var bname = $("#name").val();
			getlist(userId,statued,bname);
		}
		
		function getlist(userId,statued,bname) {
			tableIns = table.render({
				elem : '#token',
				id : 'token',
				method : 'post',
				where : {				
					'status':statued,
					'name':bname,
					'userId':userId,
				},
				url : '${ctx}/project/queryProject.do',
				cols : [ [ {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'name',
					title : '工程名称',
					width : 250,
					align : 'center',
					sort : true
				}, {
					field : 'lfUserName',
					title : '用户名称',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'mobilePhoneNo',
					title : '联系方式',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'status',
					title : '状态',
					width : 100,
					align : 'center',
					sort : true,
					templet: "#statusTpl"
				}, {
					field : 'createTime',
					title : '时间',
					width :200,
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

		$(window).one("resize",
			function() {
				$(".newsAdd_btn").click(
			function() {
			var index = layui.layer.open({
					title : "添加项目",
					type : 2,
					content : "${ctx}/project/addProject.do",
					success : function(
					layero,
					index) {
					setTimeout(
			function() {
					layui.layer.tips(
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

		table.on(
				'tool(token)',
						function(obj) {
							var data = obj.data;
							 if (obj.event === 'indexToken') {
								var index = layui.layer.open({
											title : "项目明细",
											type : 2,
											content : "${ctx}/project/godetail.do?projectId="
													+ data.id,
											success : function(layero, index) {
												setTimeout(
														function() {
															layui.layer.tips(
																'点击此处返回',
																'.layui-layer-setwin .layui-layer-close',
																{
																	tips : 3
																});
														}, 500)
											}
										})
								layui.layer.full(index);
							}else if (obj.event === 'editToken') {
								var index = layui.layer.open({
											title : "编辑项目",
											type : 2,
											content : "${ctx}/project/updateProject.do?id="
													+ data.id,
											success : function(layero, index) {
												setTimeout(
														function() {
															layui.layer.tips(
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
															'${ctx}/project/delProiect.do',
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