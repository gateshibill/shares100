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
	<form class="layui-form" method="post" action="" id="listform">
		<blockquote class="layui-elem-quote news_search">
		<div class="layui-inline">
		    <div class="layui-input-inline">
		    	<input type="text" value="" id="keyword" placeholder="请输入发布者编号" class="layui-input search_input" style="height:38px;line-height:38px">
		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
		<div class="layui-inline">
			<a class="layui-btn layui-btn-normal" href="${ctx}/share/goAddShare.do">添加</a>
		</div>

	</blockquote>
	</form>
	<table class="layui-table" id="customer" lay-filter="customer"></table>
	<!--   <a class="layui-btn layui-btn-mini" onclick="updateStatus({{d.activityId}})" >结束</a>-->
	<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-mini" href="${ctx}/share/goSeeShare.do?shareId={{d.shareId}}">查看</a>
   
    <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteShare({{d.shareId}})">删除</a>
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp3">
    {{# var status=d.app.applicationName}}
            {{status}}
</script>
<script type="text/html" id="publisher">
  {{# if(d.user){}} 
     {{d.user.realName?d.user.realName:d.user.nickName}}
  {{#}else{}}
       <span style='color:red'>未知</span>
  {{#}}}

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
			var userId = $("#keyword").val();
			getlist(userId);
		}
		
		function getlist(userId) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method :'post',
				where:{
					'shareUserId':userId
					
				},
				url : '${ctx}/share/showShareList.do',
				cols : [ [
// 				{
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				} //其它参数在此省略
// 				,
				{
					field : 'shareId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				},{
					field : 'shareContent',
					title : '共享内容',
					width : 200,
					align : 'center',				
				},{
					field : 'shareUserId',
					title : '共享用户ID',
					width : 100,
					align : 'center',				
				},{
					field : 'publisherName',
					title : '共享者昵称',
					width : 180,
					align : 'center',
                    templet:'#publisher'					
				},{
					field : 'loginPlat',
					title : '应用',
					width : 180,
					align : 'center',
					templet : "#statusTp3"
				},{
					field : 'praiseCount',
					title : '点赞数(次)',
					width : 120,
					align : 'center',
				},{
					field : 'shareTime',
					title : '发布时间',
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
				height :'auto',
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
		
		
// 		$(window).one("resize",function(){
// 			$(".newsAdd_btn").click(function(){
// 				var index = layui.layer.open({
// 					title : "发布共享",
// 					type : 2,
// 					content : "", 
// 					success : function(layero, index){
// 						setTimeout(function(){
// 							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
// 								tips: 3
// 							});
// 						},500)
// 					}
// 				})			
// 				layui.layer.full(index);
// 			})
// 		}).resize();
		
		
		//删除活动
		function deleteShare(id){
			layer.confirm('您确认删除该共享么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/share/deleteShare.do', {
					'shareId' : id
				}, function(data) {
					if (data) {
						layer.msg("删除共享成功!");
						window.location.reload();
					} else {
						layer.msg('删除共享失败', {
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