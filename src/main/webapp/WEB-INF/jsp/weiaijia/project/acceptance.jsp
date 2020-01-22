<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>小项验收</title>
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
	<form class="layui-form" method="post" action="" id="listform">
		<blockquote class="layui-elem-quote news_search">
			<div class="layui-inline">
				<a class="layui-btn search_btn" onclick="doSearch()">查询</a>
			</div>
		</blockquote>
	</form>
	<table class="layui-table" id="token" lay-filter="token"
		lay-data="{id: 'token'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="projectWorkBigPlay">未通过原因</a>
  <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delToken">删除</a>
</script>
<script type="text/html" id="isFinishTpl">
    {{# var isFinish=d.isFinish}}
    {{# if(isFinish==1){}}
   		<span style="color:green;">已完成</span>
    {{# } else { }}
    	<span style="color:red;">未完成</span>
    {{# } }}
</script>
<script type="text/html" id="checkStatusTpl">
    {{# var checkStatus=d.checkStatus}}
    {{# if(checkStatus==3){}}
   		<span style="color:green;">通过</span>
    {{# } else if(checkStatus==4){ }}
    	<span style="color:red;">未通过</span>
   	{{# } else if(checkStatus==2){ }}
    	<span>待验收</span>
	{{# } else if(checkStatus==1){ }}
    	<span>施工中</span>
	{{# }else{}}
    	<span>未开始</span>
    {{# } }}
</script>	
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>

layui.use([ 'form', 'layer'],
		function() {
	 	var form = layui.form, layer = layui.layer;
		//监听提交
		form.on('submit(demo1)', function(data) {
			
			$.ajax({
				success : function(result) {
					if(result.code==0){
						layer.msg("确认成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						  }, 1000);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.msg("确认失败");
				}
			});
			return false;
		});
	});

		layui.use([ 'layer', 'table'], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			//plat = $("#loginPlat11").val();
			getlist();
		});
		function doSearch() {
			//getlist(isRecommend,collType);
		}

		function getlist(isFinish,collType) {
			tableIns = table.render({
				elem : '#token',
				id : 'token',
				method : 'post',
				where : {				
					'planTypeId':'${projectWorkPlan.planTypeId}',
				},
				url : '${ctx}/newProject/getProjectWorkPlan.do',
				cols : [ [ {
					field : 'planId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'planName',
					title : '施工名称',
					width : 150,
					align : 'center',
					sort : true
				}, {
					field : 'isFinish',
					title : '是否完成',
					width : 150,
					align : 'center',
					sort : true,
					templet:"#isFinishTpl"
				},{
					field : 'checkStatus',
					title : '验收',
					width : 150,
					align : 'center',
					sort : true,
					templet:"#checkStatusTpl"
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
table.on(
		'tool(token)',
		function(obj) {
			var data = obj.data;
			/* console.log(smallPlanId); */
			if (obj.event === 'projectWorkBigPlay') {
				var index = layui.layer
						.open({
							title : "验收不通过原因",
							type : 2,
							content : "${ctx}/newProject/projectCheckView.do?smallPlanId="
									+ data.planId,
							success : function(layero, index) {
								setTimeout(
										function() {
											layui.layer.tips(
												'点击此处返回',
												'.layui-layer-setwin .layui-layer-close',
												{
													tips : 3
												});
										}, 500)
							}
						})
				layui.layer.full(index);

			}else if(obj.event === 'delToken'){
				layer.confirm(
						'您确认删除该条数据?',
						{
							btn : [ '确认', '取消' ]
						},
						function() {
							$
									.post(
											'${ctx}/newProject/delProjectCheckView.do',
											{
												'planId' : data.planId
											},
											function(data) {
												if (data.code == 0) {
													layer.msg('删除成功');
													obj.del();
												} else {
													
													layer.msg(data.msg, {
														time : 2000
													});
												}
											});
						}, function() {
							layer.closeAll();
						})
			}
		});
		/* table.on(
						'tool(token)',
						function(obj) {
							var data = obj.data; */
						/* }else if (obj.event === 'editToken') {
								var index = layui.layer
										.open({
											title : "编辑小类施工计划",
											type : 2,
											content : "${ctx}/newProject/goUpdateWorkPlan.do?planId="
													+ data.planId,
											success : function(layero, index) {
												setTimeout(
														function() {
															layui.layer.tips(
																'点击此处返回',
																'.layui-layer-setwin .layui-layer-close',
																{
																	tips : 3
																});
														}, 500)
											}
										})
								layui.layer.full(index);
							}else if(obj.event === 'delToken'){
								layer.confirm(
										'您确认删除该条数据?',
										{
											btn : [ '确认', '取消' ]
										},
										function() {
											$
													.post(
															'${ctx}/newProject/deleteWorkPlan.do',
															{
																'planId' : data.planId
															},
															function(data) {
																if (data.code == 0) {
																	layer.msg('删除成功');
																	obj.del();
																} else {
																	
																	layer.msg(data.msg, {
																		time : 2000
																	});
																}
															});
										}, function() {
											layer.closeAll();
										})
							}
						});
 */
	
	</script>
</body>
</html>