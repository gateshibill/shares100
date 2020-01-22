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
					<label class="layui-form-label">订单状态</label>
					<div class="layui-input-block">
						<select id="orderStatus" class="search-input" name="orderStatus"
								lay-verify="required" lay-search="">
								<option value="" selected="selected">全部</option>
								<option value="5">已付款</option>
								<option value="7">待发货</option>
								<option value="10">已发货</option>
								<option value="14">确认收货</option>
								<option value="16">待评价</option>
								<option value="20">交易完成</option>
								<option value="-2">退款中</option>
								<option value="-3">退货中</option>
								<option value="-4">已删除</option>
							</select>
					</div>
				</div>
				
					<div class="layui-inline">
					<label class="layui-form-label">收货人</label>
					<div class="layui-input-block">
						<input type="text" name="consignee" id="consignee"
								placeholder="请输入收货人姓名" class="layui-input">
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
   <!-- -->
    <div class="layui-btn-group demoTable">
		<button class="layui-btn" data-type="addAllSeleted"> 
		<i class="layui-icon">&#xe608;</i>批量处理</button>
	</div>
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="descoveryDetail" >查看</a> 
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteOrder({{d.orderID}})">删除</a>
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="ispassTpl">
    {{# var ispass=d.isPassSell}}
    {{# if (ispass=='0'){}}
    	<span style="color:red;">未审核</span>
    {{# } else{ }}
   		已审核
    {{# } }}
</script>	
<!-- 是否热销模板 -->
	<script type="text/html" id="orderType">
    {{# var tj=d.orderType}}
    {{# if (tj=='0'){}}
    	商品
    {{# } else if(tj=='1'){ }}
   		任务
    {{# } }}
</script>
<!--  -->
<script type="text/html" id="loginPlatTpl">
   {{d.app.applicationName}}
</script>
	<script type="text/html" id="orderStatusTpl">
     {{# var destype=d.orderStatus}}
    {{# if (destype=='2'){}}
    	退款中
    {{# } else if(destype=='-3'){ }}
   		 退货中
 	{{# } else if(destype=='3'){ }}
   		 确定订单
 	{{# } else if(destype=='-1'){ }}
   		交易关闭
 	{{# } else if(destype=='-4'){ }}
   		已删除
 	{{# } else if(destype=='5'){ }}
   		 已付款
 	{{# } else if(destype=='7'){ }}
   		 待发货
 	{{# } else if(destype=='10'){ }}
   		已发货
    {{# } else if(destype=='14'){ }}
   		确认收货
    {{# } else if(destype=='16'){ }}
   		待评价
    {{# } else if(destype=='20'){ }}
   		 交易完成
    {{# } }}
</script>
<script type="text/html" id="totalFee">
    {{# var fee=d.totalFee}}
          ￥ {{fee}}元
</script>
<script type="text/html" id="realFee">
    {{# var fee=d.realFee}}
          ￥ {{fee}}元
</script>
<script type="text/html" id="payStatusTpl">
{{# var payStatus=d.payStatus}}
   {{# if(payStatus == 1){}}
     <span style="color:green">是</span>
   {{#}else{}}
        <span style="color:red">是</span>
   {{#}}}
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
			var plat = $("#loginPlat").val();
			var status = $("#orderStatus").val();
			var name = $("#consignee").val();
			var dtr = $("#dateRange").val();
			getlist(plat,status,name,dtr);
		}

		function getlist(plat,status,name,dtr) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat':plat,
					'consignee':name,
					'dateRange':dtr,
					'orderStatus':status
				},
				url : '${ctx}/userorder1/showtraderecord.do', 
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				}, {
					field : 'orderID',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'orderType',
					title : '订单类型',
					width : 140,
					align : 'center',
					 templet:'#orderType'			
				},
				{
					field : 'loginPlat',
					title : '订单应用',
					width : 140,
					align : 'center',
					templet:'#loginPlatTpl'			
				},
				{
					field : 'consignee',
					title : '收货人',
					width : 100,
					align : 'center'
				}, {
					field : 'phone',
					title : '用户电话',
					width : 150,
					align : 'center',
				}, {
					field : 'address',
					title : '用户地址',
					width : 100,
					align : 'center',
				}, {
					field : 'orderStatus',
					title : '订单状态',
					width : 100,
					align : 'center',
					templet:'#orderStatusTpl'	
				},{
					field : 'payStatus',
					title : '是否支付',
					width : 100,
					align : 'center',
					templet:'#payStatusTpl'	
				}, {
					field : 'totalFee',
					title : '总金额',
					width : 120,
					sort : true,
					align : 'center',
					templet:'#totalFee'	
				},{
					field : 'realFee',
					title : '实付金额',
					width : 120,
					sort : true,
					align : 'center',
					templet:'#realFee'	
				}, {
					field : 'number',
					title : '订单数量',
					width : 100,
					align : 'center',
				}, {
					field : 'createTime',
					title : '下单时间',
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
				window.location.href="${ctx}/userorder1/userOrderDateils.do?orderID="+data.orderID;
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

		  
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
	    
		
		function deleteOrder(oid){
			layer.confirm('您确认删除该条订单么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/userorder1/deleteUserOrder.do', {
					'orderID' : oid
				}, function(data) {
					if (data) {
						layer.msg('删除订单成功');
						setTimeout(function() {
							window.location.reload();
						}, 400);
					} else {
						layer.msg('删除订单失败', {
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