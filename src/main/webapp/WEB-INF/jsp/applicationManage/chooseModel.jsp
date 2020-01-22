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
</head>
<body>
<blockquote class="layui-elem-quote news_search">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
		<div class="layui-inline">
			<label class="layui-form-label">模型名称</label>
		    <div class="layui-input-inline">
		    	<input type="text" id="keyword" placeholder="请输入模型名称" class="layui-input search_input" style="height:38px;line-height:38px">
		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
	</form>
</blockquote>
	<table class="layui-table" id="customer" lay-filter="customer"
		lay-data="{id: 'customer'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" href="${ctx}/application/modityModel.do?modelId={{d.modelId}}">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteModel({{d.modelId}})">删除</a>
</script>
	
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table','laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			var laydate = layui.laydate;
			//--------------方法渲染TABLE----------------
			getlist('','','','','','');
		});
		function doSearch() {
			var keyword = $("#keyword").val();
			getlist(keyword);
		}
		
		function getlist(keyword) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method :'post',
				where:{
					'modelName':keyword
					
				},
				url : '${ctx}/application/showModelList.do',
				cols : [ [ {
					field : 'modelId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				},{
					field : 'modelName',
					title : '模型名称',
					width : 180,
					align : 'center'
				}, {
					field : 'modelUrl',
					title : '模型连接',
					width : 200,
					align : 'center'
				},
				 {
					field : 'modelSynopsis',
					title : '模型简介',
					width : 300,
					align : 'center'
				},
				{
					field : 'modelCreateTime',
					title : '创建时间',
					width : 180,
					align : 'center',
					sort : true,
				},{
					field : 'modelUsedCount',
					title : '使用次数',
					width : 120,
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
		$(window).one("resize",function(){
			$(".newsAdd_btn").click(function(){
				var index = layui.layer.open({
					title : "添加模板",
					type : 2,
					content : "${ctx}/application/addModel.do",
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
			})
		}).resize();
		
       table.on('tool(customer)', function(obj) {
			var data = obj.data;	
		});
       
       function deleteModel (modelId){
    	   layer.confirm('您确认删除该模板么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/application/deleteModel.do', {
					'modelId' : modelId
				}, function(data) {
					if (data.code==0) {
						layer.msg("删除模板成功!");
						setTimeout(function (){
							window.location.reload();
						},1000);
					} else {
						layer.msg(data.msg, {
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