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
</head>
<body>
<blockquote class="layui-elem-quote news_search">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
		<div class="layui-inline">
			<label class="layui-form-label">关键字</label>	
		    <div class="layui-input-inline">
		    	<input type="text" id="keyword" placeholder="请输入关键字" class="layui-input search_input" style="height:38px;line-height:38px">
		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
		<div class="layui-inline">
			<a class="layui-btn layui-btn-danger batchDel">批量删除</a>
		</div>	
	</form>
</blockquote>
	<table class="layui-table" id="customer" lay-filter="customer"
		lay-data="{id: 'customer'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="edit">查看</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteRecord({{d.recordId}})">删除</a>

</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp1">
    {{# var status=d.user.nickName}}
            {{status}}
</script>
<!-- 处理状态模板 -->
	<script type="text/html" id="statusTpl">
    {{# var status=d.payStatus}}
    {{# if (status=='0'){}}
    	未支付
    {{# } else if(status=='1'){ }}
   		已支付
 	{{# } else if(status=='2'){ }}
   		取消订单
 	{{# } }}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table','laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			var laydate = layui.laydate;
			//--------------方法渲染TABLE----------------
			pid = $("#loginPlat").val();
			getlist(pid,'','','','','','');
		});
		function doSearch() {
			var recordId=$("#keyword").val();
			getlist(recordId);
		}
		
		function getlist(recordId) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method :'post',
				where:{
					'recordId':recordId
					
				},
				url : '${ctx}/shareContract/showRecordList.do',
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				} //其它参数在此省略
				, {
					field : 'recordId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'contractId',
					title : '合约编号',
					width : 100,
					align : 'center'
				}, {
					field : 'rewardUser',
					title : '打赏人编号',
					width : 120,
					align : 'center',
					sort : true
				}, 
				{
					field : 'totalFee',
					title : '打赏金额(元)',
					width : 120,
					align : 'center',
					sort : true
				},
				{
					field : 'payStatus',
					title : '打赏状态',
					width : 120,
					align : 'center',
					sort : true,
					templet : "#statusTpl"
				},
				{
					field : 'publisherId',
					title : '发布者编号',
					width : 100,
					align : 'center',
				},{
					field : 'outTradeNumber',
					title : '支付识别码',
					width : 120,
					align : 'center'
				}, {
					field : 'createTime',
					title : '创建时间',
					width : 180,
					align : 'center',
					sort : true,
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
		
		layui.use('table',function() {
							var table = layui.table;
							//监听表格复选框选择
							table.on('checkbox(demo)', function(obj) {
								console.log(obj)
							});
							//监听工具条
							table.on('tool(demo)', function(obj) {
								var data = obj.data;
								if (obj.event === 'detail') {
									layer.msg('ID：' + data.id + ' 的查看操作');
								} else if (obj.event === 'del') {
									layer.confirm('真的删除行么', function(index) {
										obj.del();
										layer.close(index);
									});
								} else if (obj.event === 'edit') {
									layer.alert('编辑行：<br>'
											+ JSON.stringify(data))
								}
							});

							var $ = layui.$, active = {
								getCheckData : function() { //获取选中数据
									var checkStatus = table
											.checkStatus('idTest'), data = checkStatus.data;
									layer.alert(JSON.stringify(data));
								},
								getCheckLength : function() { //获取选中数目
									var checkStatus = table
											.checkStatus('idTest'), data = checkStatus.data;
									layer.msg('选中了：' + data.length + ' 个');
								},
								isAll : function() { //验证是否全选
									var checkStatus = table
											.checkStatus('idTest');
									layer.msg(checkStatus.isAll ? '全选' : '未全选')
								}
							};

							$('.demoTable .layui-btn').on('click', function() {
								var type = $(this).data('type');
								active[type] ? active[type].call(this) : '';
							});
						});
		
		function deleteRecord(id){
			layer.confirm('您确认删除该条记录么?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
			}, function() {
				$.post('${ctx}/shareContract/deleteRecord.do', {
					'recordId' : id
				}, function(data) {
					if (data) {
						layer.msg("删除合约成功!");
						setTimeout(function (){
							window.location.reload();
						},3000);
					} else {
						layer.msg('删除合约失败', {
							time : 2500,
							icon : 0
						});
					}
				});
			})
		}
	</script>

</body>
</html>