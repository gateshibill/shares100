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
								<option value="">全部</option>							
								<c:forEach var="app" items="${appList}">
									<option value="${app.applicationId}">${app.applicationName}</option>
								</c:forEach>
							</select>
					</div>
				</div>
				
				
				
				<div class="layui-inline">
					<label class="layui-form-label">应用编号</label>
					<div class="layui-input-block">
						<input type="text" name="loginPlat2" id="loginPlat2"
								placeholder="请输入应用编号" class="layui-input">
					</div>
				</div>
				
					<div class="layui-inline">
					<label class="layui-form-label">处理状态</label>
					<div class="layui-input-block">
						<select id="withdrawState" name="withdrawState"
								class="search-select">
								<option value="">全部</option>
								<option value="1">未处理</option>
								<option value="2">已处理</option>
							</select>
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">用户编号</label>
					<div class="layui-input-block">
						<input type="text" name="userId" id="userId"
								placeholder="请输入用户编号" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">用户名称</label>
					<div class="layui-input-block">
						<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入用户关键字" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">申请日期区间</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="applyDateRange"
								id="applyDateRange" placeholder=" - ">
					</div>
				</div>
				
					<div class="layui-inline">
					<label class="layui-form-label">处理日期区间：</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="dealDateRange"
								id="dealDateRange" placeholder=" - ">
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
		<button class="layui-btn" data-type="dealAllSeleted">处理选中</button>
	</div>
	<table class="layui-table" id="customer" lay-filter="customer"></table>
	<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="detail">查看</a>
  <a class="layui-btn layui-btn-mini" lay-event="deal" >处理</a>

</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTpl">
    {{# var status=d.withdrawState}}
    {{# if (status=='1'){}}
    	<span style="color:red;">申请中</span>
    {{# } else{ }}
   		已处理
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
				elem : '#applyDateRange',
				range : true
			});
			//日期范围
			laydate.render({
				elem : '#dealDateRange',
				range : true
			});
			//--------------方法渲染TABLE----------------
			pid = $("#loginPlat").val();
			getlist(pid, '', '', '', '', '', '');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var lp2 = $("#loginPlat2").val();
			var userId = $("#userId").val();
			var ukw = $("#userKeyWords").val();
			var wdw = $("#withdrawState").val();
			var appdb = $("#applyDateRange").val();
			var ddr = $("#dealDateRange").val();
			getlist(pid, lp2, userId, ukw, wdw, appdb, ddr);
		}

		function getlist(pid, loginPlat2, userId, userKeyWords, withdrawState,
				applyDateRange, dealDateRange) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'loginPlat2' : loginPlat2,
					'userId' : userId,
					'userKeyWords' : userKeyWords,
					'withdrawState' : withdrawState,
					'applyDateRange' : applyDateRange,
					'dealDateRange' : dealDateRange
				},
				url : '${ctx}/withdraw/withdrawList.do',
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				} //其它参数在此省略
				, {
					field : 'withdrawId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'userId',
					title : '提现用户编号',
					width : 140,
					align : 'center'
				}, {
					field : 'withdrawRealName',
					title : '提现用户姓名',
					width : 140,
					align : 'center'
				}, {
					field : 'withdrawFee',
					title : '提现金额(元)',
					width : 140,
					align : 'center',
					sort : true,
				}, {
					field : 'outTradeNo',
					title : '订单号',
					width : 200,
					align : 'center'
				}, {
					field : 'withdrawIp',
					title : '提现IP',
					width : 130,
					align : 'center'
				}, {
					field : 'createTime',
					title : '申请时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					field : 'withdrawState',
					title : '处理状态',
					width : 120,
					sort : true,
					align : 'center',
					templet : "#statusTpl"
				}, {
					field : 'dealTime',
					title : '处理时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					title : '操作',
					width : 170,
					align : 'center',
					fixed : 'right',
					toolbar : '#barDemo'
				} ] ],
				page : true,
				limits : [ 1, 10, 20, 30, 50, 100 ],
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
		table.on('tool(customer)', function(obj) {
			var data = obj.data;
			if (obj.event === 'detail') {
				layer.msg(JSON.stringify(data));
			} else if (obj.event === 'deal') {
				$.ajax({
					data : 'json',
					url : '${ctx}/withdraw/dealWithdraw.do',
					type : "post",
					data : {
						withdrawId : data.withdrawId
					},
					success : function(result) {
						if (result.code == '0') {
							layer.msg(result.msg);
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
				var checkStatus = table.checkStatus('customer'), data = checkStatus.data;
				var withdrawIds = "";
				for (var i = 0; i < data.length; i++) {
					withdrawIds += data[i].withdrawId + ",";
				}
				;
				if (withdrawIds == "") {
					layer.msg("请选择数据!");
				} else {
					//layer.alert(JSON.stringify(data));
					$.ajax({
						data : 'json',
						url : '${ctx}/withdraw/dealBatch.do',
						type : "post",
						data : {
							withdrawIds : withdrawIds
						},
						success : function(result) {
							if (result.code == '0') {
								layer.alert(result.msg);
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
	</script>

</body>
</html>