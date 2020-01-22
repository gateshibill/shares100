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
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="#">编辑</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="#">删除</a>
    </script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
	    var travelId = ${travelId};
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
			getlist();
		});
	

		function getlist(pid,title,state) {
			tableIns = table.render({
				elem : '#affiche',
				id : 'affiche',
				method : 'post',
				where : {
					'travelId':travelId,
				},
				url : '${ctx}/travel/gettravelviewlist.do',
				cols : [ [ 
// 				 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'viewId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'qqmapViewId',
					title : '腾讯地图景点Id',
					width : 180,
					align : 'center'
				},{
					field : 'location',
					title : '景点名称',
					width : 130,
					align : 'center'
				},{
					field : 'latitude',
					title : '经度',
					width : 130,
					align : 'center'
				}, {
					field : 'longitude',
					title : '纬度',
					width : 200,
					align : 'center'
				}, {
					field : 'redbagCount',
					title : '红包个数',
					width : 140,
					align : 'center',
					sort : true
				}, {
					field : 'isOrder',
					title : '是否游过',
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