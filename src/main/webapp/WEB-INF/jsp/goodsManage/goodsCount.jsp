<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>商品浏览情况列表</title>
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
	.layui-btn {
    display: inline-block;
    height: 28px;
    line-height: 28px;
    padding: 0 18px;
/*     background-color: #009688; */
    color: #fff;
    white-space: nowrap;
    text-align: center;
    font-size: 14px;
    border: none;
    border-radius: 2px;
    cursor: pointer;
    opacity: .9;
    filter: alpha(opacity = 90);
}
.layui-btn-primary {
    border: 1px solid #C9C9C9;
    background-color: #fff;
    color: #555;
}

</style>
</head>
<body>
	<form class="layui-form" method="post" action="" id="listform">
		<blockquote class="layui-elem-quote">
			<div class="search-area">
				<ul>
					<li><label>商品名称：</label>
						<div>
							<input type="text" name="goodsName" id="goodsName"
								placeholder="请输入商品名称" class="search-input" >
						<input hidden="" name="goodsId" id="goodsId" value="${goodsId}">
						</div></li>
					<li><label>用户名称：</label>
						<div>
							<input type="text" name="userName" id="userName"
								placeholder="请输入用户名称" class="search-input">
						</div></li>
					<li><label>浏览日期：</label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" name="dateRange"
								id="dateRange" placeholder=" - ">
						</div></li>
						
				</ul>
					<div class="layui-inline">
					  &nbsp;&nbsp;&nbsp;&nbsp;
					  <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
			           <button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
		            </div>
				<span style="clear: both; display: block;"></span>
			</div>
		</blockquote>
	</form>
	
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteGoods({{d.id}})">删除</a>

</script>
	<script type="text/html" id="nicknameTpl">
       {{#if(d.user){}}
              {{d.user.nickName}}
       {{# }else{}}
        <span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
<script type="text/html" id="goodsNameTpl">
     {{d.goods.goodsName}}
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
			
			getlist('', '','');
		});
		function doSearch() {
			var goodsname = $("#goodsName").val();
			var userName = $("#userName").val();
			var dtr = $("#dateRange").val();
			getlist(goodsname, userName, dtr);
		}

		function getlist(gname, uname,DateRange) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'goodsId' : $('#goodsId').val(),
					'goodsName' : gname,
					'userName' : uname,
					'dateRange' : DateRange
				},
				url : '${ctx}/goods/showBrowseList.do',
				cols : [ [ 
// 			 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'userId',
					title : '用户ID',
					width : 150,
					align : 'center',
				},{
					field : 'goodsId',
					title : '商品ID',
					width : 150,
					align : 'center',
				},{
					field : 'nickname',
					title : '用户名称',
					width : 140,
					align : 'center',
					templet : "#nicknameTpl"
				},{
					field : 'goodsName',
					title : '商品名称',
					width : 140,
					align : 'center',
					templet : "#goodsNameTpl"
				},
				{
					field : 'browseCount',
					title : '浏览次数',
					width : 150,
					align : 'center',
				}, {
					field : 'browseTime',
					title : '浏览时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					title : '操作',
					width : 150,
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
		table.on('tool(descovery)', function(obj) {
			var data = obj.data;

		});

		  
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

		function deleteGoods(id){
			layer.msg('该功能暂未开放!');
		}
		
		function goBack(){
			window.history.go(-1);
		}
	</script>
</body>
</html>