<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>管理员列表</title>
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
</head>
<body>
<blockquote class="layui-elem-quote news_search">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
		<div class="layui-inline">
			<label class="layui-form-label">管理员名称</label>	
		    <div class="layui-input-inline">
		    	<input type="text" name="roleName" id="roleName" placeholder="请输入真实姓名/角色名称" class="layui-input search_input" style="height:38px;line-height:38px;width: 200px;">
		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
		 <div class="layui-inline">
			<a class="layui-btn layui-btn-normal" href="${ctx}/permission/addUser.do">添加</a>
		</div>
	</form>
</blockquote>
	<table class="layui-table" id="customer" lay-filter="customer"
		lay-data="{id: 'customer'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini"
       href="${ctx}/permission/backUserDetails.do?userId={{d.userId}}">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteRoleId({{d.userId}})">删除</a>

</script>
<!-- 角色名称模块 -->
<script type="text/html" id="roleNameTpl">
 {{# var roleName=d.backRole.roleName}}
           {{roleName}}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table','laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			var laydate = layui.laydate;
			//--------------方法渲染TABLE----------------
			getlist('');
		});
		function doSearch() {
			var name = $("#roleName").val();
			getlist(name);
		}
		
		function getlist(name) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method :'post',
				where:{
				'roleName':name
					
				},
				url : '${ctx}/permission/showBackUserList.do',
				cols : [ [ 
// 				 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				} //其它参数在此省略
// 				, 
				{
					field : 'userId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'userName',
					title : '账号',
					width : 200,
					align : 'center'
				},  
				 {
					field : 'realName',
					title : '真实姓名',
					width : 200,
					align : 'center'
				},   
				 {
					field : 'roleName',
					title : '角色名称',
					width : 200,
					align : 'center',
					templet : "#roleNameTpl"
				},{
					field : 'createTime',
					title : '创建时间',
					width : 200,
					align : 'center'
				},{
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
				loading: true,
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
		
	
	//删除管理员
	function deleteRoleId(id){
		layer.confirm('确定删除该管理么？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					 url:"${ctx}/permission/deleteUserId.do",
					 type:'post',
					 dataType:'json',
					 data:{'userId':id},
					 success:function(data){
						 if(data.code == 0){
							 layer.msg("删除管理成功");
							 setTimeout(function(){
								 window.location.reload();
							 },1000);
						 }else{
							 layer.msg(data.msg);
							 return;
						 }
					 },
					 
				});
			})
	}
	</script>
</body>
</html>