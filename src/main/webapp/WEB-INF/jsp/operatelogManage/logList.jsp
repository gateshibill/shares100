<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>商品列表</title>
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
<blockquote class="layui-elem-quote">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
			<div class="layui-inline">
			<label class="layui-form-label">操作人名称</label>
		    <div class="layui-input-inline">
		    	<div style="float:left;">
					<input type="text" name="operateUser" id="operateUser"
								placeholder="请输入用户名称" class="layui-input">
				</div>
		    </div>
		</div>
		
		<div class="layui-inline">
			<label class="layui-form-label">操作日期</label>
		    <div class="layui-input-inline">
<input type="text" class="layui-input" name="dateRange"
								id="dateRange" placeholder=" - ">		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
 		<div class="layui-inline"> 
 			<a class="layui-btn layui-btn-normal newsAdd_btn" href="javascript:void(0)">添加</a> 
		</div>
	</form>
</blockquote>	
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteLog({{d.logId}})">删除</a>
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="resultTpl">
    {{# var ispass=d.operateResult}}
    {{# if (ispass=='1'){}}
    	成功
    {{# } else{ }}
   		<span style="color:red;">失败</span>
    {{# } }}
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
			getlist('', '', '', '', '', '');
		});
		function doSearch() {
			var name = $("#operateUser").val();
			var dtr = $("#dateRange").val();
			getlist(name,dtr);
		}

		function getlist(name,drt) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'operateUser' : name,
					'dateRange' : drt
				},
				url : '${ctx}/log/shoeLogList.do',
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				}, {
					field : 'logId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'operateName',
					title : '操作内容',
					width : 320,
					align : 'center'
				},
				{
					field : 'operateUser',
					title : '操作用户',
					width : 150,
					align : 'center',
				}, {
					field : 'operateResult',
					title : '操作结果',
					width : 150,
					align : 'center',
					templet : "#resultTpl"
				}, {
					field : 'operateTime',
					title : '操作时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					title : '操作',
					width : 180,
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
			if (obj.event === 'descoveryDetail') {
				window.location.href="${ctx}/goods/goodsDetails.do?goodsId="+data.goodsId;
			} else if (obj.event === 'recommend') {
				$.ajax({
					data : 'json',
					url : '${ctx}/descovery/tjtoSame.do',
					type : "post",
					data : {
						descoveryId : data.descoveryId
					},
					success : function(result) {
						if (result.code == '0') {
							layer.msg(result.msg);
							//同步更新缓存对应的值
							obj.update({
								isRecommend : 1
							});
						} else {
							layer.msg(result.msg);
							return;
						}
					}
				});
			} 
		});

		  
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

		function deleteLog(lid){
			layer.confirm('您确认删除该条日志么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/log/deleteLog.do', {
					'logId' : lid
				}, function(data) {
					if (data) {
						layer.msg('删除日志成功');
						setTimeout(function() {
							window.location.reload();
						}, 400);
					} else {
						layer.msg('删除日志失败', {
							time : 2500,
							icon : 0
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