<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>交易成功列表</title>
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
						<select id="loginPlat" class="search-input" name="loginPlat"
								lay-verify="required" lay-search="">
								<option value=" ">全部</option>
								<c:forEach var="app" items="${appList}">
									<option value="${app.applicationId}">${app.applicationName}</option>
								</c:forEach>
							</select>
					</div>
				</div>
				
				
				
				<div class="layui-inline">
					<label class="layui-form-label">充值状态</label>
					<div class="layui-input-block">
						<select id="rechargeStatus" class="search-input" name="rechargeStatus"
								lay-verify="required" lay-search="">
								<option value="" selected="selected">全部</option>
								<option value="0">待付款</option>
								<option value="1">已付款</option>
								
							</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<a class="layui-btn search_btn" onclick="doSearch()"> <i
						class="layui-icon">&#xe615;</i>查询
					</a>
				</div>
			</div>
	</form>
</blockquote>
   <!-- -->
    <!-- <div class="layui-btn-group demoTable">
		<button class="layui-btn" data-type="addAllSeleted"> 
		<i class="layui-icon">&#xe608;</i>批量处理</button>
	</div> -->
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="editRecharge" >查看</a> 
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delRecharge">删除</a>
</script>




<script type="text/html" id="rechargeStatus1">
{{# var rechargeStatus=d.rechargeStatus}}
   {{# if(rechargeStatus == 1){}}
     <span style="color:green">已付款</span>
   {{#}else{}}
        <span style="color:red">未付款</span>
   {{#}}}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table', 'laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			getlist('', '', '');
		});
		function doSearch() {
			var plat = $("#loginPlat").val();
			var status = $("#rechargeStatus").val();
		
			getlist(plat,status);
		}

		function getlist(plat,status) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat':plat,
					'rechargeStatus':status
				},
				url : '${ctx}/rechargeorder/getrechargelist.do', 
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				}, {
					field : 'rechargeId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'userId',
					title : '用户id',
					width : 140,
					align : 'center'
				},
				{
					field : 'loginPlat',
					title : '应用id',
					width : 140,
					align : 'center'
				},
				{
					field : 'rechargeMoney',
					title : '充值金额',
					width : 100,
					align : 'center'
				}, {
					field : 'rechargeStatus',
					title : '付款状态',
					width : 100,
					align : 'center',
					templet:'#rechargeStatus1'	

				}, {
					field : 'createTime',
					title : '充值时间',
					width : 200,
					align : 'center',
				}, {
					field : 'payTime',
					title : '付款时间',
					width : 100,
					align : 'center'
				} , {
					title : '操作',
					width : 150,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				} 
				] ],
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
		table.on('tool(descovery)', function(obj) {
			/* var data = obj.data;
			if (obj.event === 'descoveryDetail') {
				
			} else if (obj.event === 'recommend') {
		
			} else if (obj.event === 'deleteDescovery') {
				
			} */
			alert("该功能暂未开放");
		});

		  
	
		
		
	</script>
</body>
</html>