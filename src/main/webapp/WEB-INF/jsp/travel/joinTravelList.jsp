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
	<table class="layui-table" id="affiche" lay-filter="affiche"></table>
	<script type="text/html" id="toolBar">
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="#">移除</a>
    </script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
	 var travelId = ${travelId};
		layui.use([ 'layer', 'table', 'laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			getlist();
		});
		

		function getlist(pid,title,state) {
			tableIns = table.render({
				elem : '#affiche',
				id : 'affiche',
				method : 'post',
				where : {
					'travelId' : travelId,
				},
				url : '${ctx}/travel/getjointravel.do',
				cols : [ [ 
// 				 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'joinId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'userId',
					title : '旅客ID',
					width : 180,
					align : 'center'
				},{
					field : 'joinUser',
					title : '旅客昵称',
					width : 130,
					align : 'center'
				},{
					field : 'userPhone',
					title : '联系方式',
					width : 130,
					align : 'center'
				},{
					field : 'state',
					title : '状态',
					width : 130,
					align : 'center'
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



	
		
	
	</script>
</body>
</html>