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
			<label class="layui-form-label">发布者编号</label>
		    <div class="layui-input-inline">
		    	<input type="text" value="" id="keyword" placeholder="请输入发布者编号" class="layui-input search_input" style="height:38px;line-height:38px">
		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
		<div class="layui-inline">
			<a class="layui-btn layui-btn-danger batchDel">批量删除</a>
		</div>	
	</form>
</blockquote>
	<table class="layui-table" id="customer" lay-filter="customer"></table>
	<!--  <a class="layui-btn layui-btn-mini" lay-event="edit">查看</a> -->
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteContract({{d.contractId}})">删除</a>
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTpl">
    {{# var status=d.isPublic}}
    {{# if (status=='1'){}}
    	<span style="color:red;">是</span>
    {{# } else{ }}
   		否
    {{# } }}
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp2">
    {{# var status=d.isClose}}
    {{# if (status=='1'){}}
    	<span style="color:red;">是</span>
    {{# } else{ }}
   		否
    {{# } }}
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp3">
    {{# var status=d.reason.contractTitle}}
            {{status}}
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp4">
    {{# var status=d.content.contentTitle}}
            {{status}}
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp5">
    {{# var status=d.punishment.punishmentContent}}
            {{status}}
</script>
<script type="text/html" id="publisher">
  {{# if(d.usercommon){}} 
     {{d.usercommon.realName?d.usercommon.realName:d.usercommon.nickName}}
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
			var sharedUserId = $("#keyword").val();
			getlist(sharedUserId);
		}
		
		function getlist(sharedUserId) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method :'post',
				where:{
					'sharedUserId':sharedUserId
					
				},
				url : '${ctx}/shareContract/showSharetList.do',
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				} //其它参数在此省略
				, {
					field : 'contractId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				},{
					field : 'publisher',
					title : '发布者',
					width : 200,
					align : 'center',
                    templet:'#publisher'					
				},{
					field : 'reasonId',
					title : '合约原因',
					width : 200,
					align : 'center',
					templet : "#statusTp3"
				},
				 {
					field : 'contentId',
					title : '合约内容',
					width : 200,
					align : 'center',
 					templet : "#statusTp4"
				},
				{
					field : 'punishmentId',
					title : '合约惩罚',
					width : 200,
					align : 'center',
 					templet : "#statusTp5"
				}, {
					field : 'sharedDeposit',
					title : '金额(元)',
					width : 100,
					sort  : true,
					align : 'center'
				},
				{
					field : 'isPublic',
					title : '是否公开',
					width : 100,
					align : 'center',
					sort : true,
					templet : "#statusTpl"
				},
				{
					field : 'isClose',
					title : '是否关闭',
					width : 100,
					align : 'center',
					sort : true,
					templet : "#statusTp2"
				},{
					field : 'createTime',
					title : '合约生成时间',
					width : 170,
					align : 'center',
					sort : true,
				},{
					title : '操作',
					width : 100,
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
		
		//删除合约
		function deleteContract(id){
			layer.confirm('您确认删除该合约么?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
			}, function() {
				$.post('${ctx}/shareContract/deleteContract.do', {
					'contractId' : id
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