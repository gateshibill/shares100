<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>商品购物车列表</title>
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
		<form class="layui-form layui-form-pane" action="">
			<div class="layui-form-item">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">发布者名称</label>
					<div class="layui-input-block ">
						<input type="text" name="userName" id="userName"
								placeholder="请输入发布者名称" class="layui-input">
					</div>
				</div>
			
				
				
				
				<div class="layui-inline">
					<label class="layui-form-label">下单日期</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="dateRange"
								id="dateRange" placeholder=" - ">
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
	<div class="layui-btn-group demoTable">
		<button class="layui-btn layui-btn-danger batchDel" data-type="dealAllSeleted"> 
		<i class="layui-icon">&#xe640;</i>批量删除</button>
	</div>
	

	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="descoveryDetail" >查看</a> 
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteOrder({{d.carId}})">删除</a>
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="userNameTpl">
    {{# var name=d.user.nickName}}
    {{name}}
</script>	
<!-- 是否热销模板 -->
	<script type="text/html" id="goodsNameTpl">
    {{# var gname=d.carGoods.goodsName}}
    {{gname}}
</script>
<script type="text/html" id="sellPriceTpl">
    {{# var price=d.carGoods.sellPrice}}
   ￥{{price}}元
</script>
<!--  -->
	<script type="text/html" id="orderStatusTpl">
     {{# var destype=d.status}}
    {{# if (destype=='0'){}}
    	正常
    {{# } else if(destype=='1'){ }}
   		已转移为订单
 	{{# } else if(destype=='2'){ }}
   		移出购物车
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
			getlist('', '', '');
		});
		function doSearch() {
			var name = $("#userName").val();
			var dtr = $("#dateRange").val();
			getlist(name,dtr);
		}

		function getlist(name,dtr) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'userName':name,
					'dateRange':dtr
				},
				url : '${ctx}/shoppingCar/showCarList.do', 
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				}, {
					field : 'carId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'userId',
					title : '用户编号',
					width : 120,
					align : 'center',			
				},
				{
					field : 'userName',
					title : '用户昵称',
					width : 150,
					align : 'center',
					templet:'#userNameTpl'	
				}, 
				{
					field : 'goodsId',
					title : '商品编号',
					width : 120,
					align : 'center',			
				},
				{
					field : 'goodsName',
					title : '商品名称',
					width : 200,
					align : 'center',
					templet:'#goodsNameTpl'	
				}, {
					field : 'sellPrice',
					title : '商品价格',
					width : 120,
					align : 'center',
					templet:'#sellPriceTpl'	
				}, {
					field : 'number',
					title : '数量',
					width : 100,
					align : 'center',
				}, {
					field : 'status',
					title : '订单状态',
					width : 120,
					align : 'center',
					templet:'#orderStatusTpl'	
				}, {
					field : 'createTime',
					title : '加入时间',
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
			if (obj.event === 'descoveryDetail') {
				//window.location.href="${ctx}/goods/goodsDetails.do?goodsId="+data.goodsId;
				var index = layui.layer
				.open({
					title : "购物车详情",
					type : 2,
					content : "${ctx}/goods/goodsDetails.do?goodsId="+data.goodsId,
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
			} else if (obj.event === 'deleteDescovery') {
				$.ajax({
					data : 'json',
					url : '${ctx}/goods/deleteGoods.do',
					type : "post",
					data : {
						'goodsIds' : data.goodsId
					},
					success : function(result) {
						if (result.code == '0') {
							layer.msg(result.msg);
							window.location.reload();
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
		});

		var $ = layui.$, active = {
				dealAllSeleted : function() { //验证是否全选
					var checkStatus = table.checkStatus('descovery'), data = checkStatus.data;
					var carIds = "";
					for (var i = 0; i < data.length; i++) {
						carIds += data[i].carId + ",";
					}
					;
					if (carIds == "") {
						layer.msg("请选择数据!");
					} else {
						//layer.alert(JSON.stringify(data));
						$.ajax({
							data : 'json',
							url : '${ctx}/shoppingCar/batchDelGoods.do',
							type : "post",
							data : {
								carIds : carIds
							},
							success : function(result) {
								if (result.code == '0') {
									layer.msg('批量删除商品成功!');
									doSearch();
								} else {
									layer.msg(result.msg);
									doSearch();
								}
							}
						});
					}
				}
			};
		  
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
	    
		
		function deleteOrder(oid){
			layer.confirm('您确认删除该商品么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/shoppingCar/deleteOrder.do', {
					'carId' : oid
				}, function(data) {
					if (data) {
						layer.msg('删除商品成功');
						setTimeout(function() {
							window.location.reload();
						}, 400);
					} else {
						layer.msg('删除商品失败', {
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