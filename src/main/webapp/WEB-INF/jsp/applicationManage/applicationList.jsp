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
	<!-- <form class="layui-form" method="post" action="" id="listform">
		<blockquote class="layui-elem-quote">
			<div class="search-area">
				<ul>
					<li><label style="margin-left: 0px">应用状态：</label>
						<div style="float: left;">
							<select id="applicationStatus" name="applicationStatus"
								class="search-select" lay-search="">
								<option value="">全部</option>
								<option value="1">试用中</option>
								<option value="2">正常服务中</option>
								<option value="3">停止服务中</option>
							</select>
						</div></li>
					<li><label>应用编号：</label>
						<div style="float: left;">
							<input type="text" name="applicationId" id="applicationId"
								placeholder="请输入应用编号" class="search-input">
						</div></li>
					<li><label>应用名称：</label>
						<div>
							<input type="text" name="appName" id="appName"
								placeholder="请输入应用名称" class="search-input">
						</div></li>
					<li><label>用户编号：</label>
						<div>
							<input type="text" name="userId" id="userId"
								placeholder="请输入用户编号" class="search-input">
						</div></li>
					<li><label>用户昵称：</label>
						<div>
							<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入用户昵称" class="search-input">
						</div></li>
					<li><label style="margin-left: 10px">模型名称：</label>
						<div>
							<input type="text" name="modelName" id="modelName"
								placeholder="请输入模型名称" class="search-input">
						</div></li>
				</ul>
				<div style="float: right;">
					<a href="javascript:;" onclick="doSearch();"
						class="layui-btn layui-btn-small" style="margin: 0 10px"
						id="search"> <i class="layui-icon">&#xe615;</i> 搜索
					</a>
				</div>
				<span style="clear: both; display: block;"></span>
			</div>
		</blockquote>
	</form> -->
	
	<blockquote class="layui-elem-quote">
		<form class="layui-form layui-form-pane" action="">
			<div class="layui-form-item">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">应用状态</label>
					<div class="layui-input-block ">
						<select id="applicationStatus" name="applicationStatus"
								class="search-select" lay-search="">
								<option value="">全部</option>
								<option value="1">试用中</option>
								<option value="2">正常服务中</option>
								<option value="3">停止服务中</option>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">应用编号</label>
					<div class="layui-input-block">
						<input type="text" name="applicationId" id="applicationId"
								placeholder="请输入应用编号" class="layui-input">
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">发布者编号</label>
					<div class="layui-input-block">
						<input type="text" name="userId" id="userId"
								placeholder="请输入发布者编号" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布者名称</label>
					<div class="layui-input-block">
						<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入用户名称" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">模型名称</label>
					<div class="layui-input-block">
						<input type="text" name="modelName" id="modelName"
								placeholder="请输入模型名称" class="layui-input">
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
	<!--  后台暂时不做添加-->
	<c:if test="${loginPlat == null || loginPlat==''}">	
	<div class="layui-btn-group demoTable">
		<a href="${ctx}/application/goaddapp.do"
					class="layui-btn layui-btn-small" id="add"> <i
					class="layui-icon">&#xe608;</i> 添加应用
		</a>
	</div>
   </c:if>
	<!-- <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="deleteDescovery">删除</a> -->
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
	<a href="${ctx}/application/appDetails.do?appId={{d.applicationId}}"
					class="layui-btn layui-btn-normal layui-btn-mini">编辑</a>
	{{# var ispass=d.applicationStatus}}
	{{# if (ispass=='2'){}}
		<a class="layui-btn layui-btn-danger layui-btn-mini"
			href="javascript:void(0)" onclick="updateStatus({{d.applicationId}},{{ispass}})">
             <span class="icon-trash-o"></span>停止</a>
    {{# } else if(ispass=='3'){ }}
			<a class="layui-btn layui-btn-mini" href="javascript:void(0)"
				onclick="updateStatus({{d.applicationId}},{{ispass}})">
            <span class="icon-check-circle-o"></span>开通</a>
    {{# } }}
  
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteAppId({{d.applicationId}})">删除</a>
	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="goSystem">PC官网</a>
</script>
	<!-- 应用状态模板 -->
	<script type="text/html" id="isshangjiaTpl">
    {{# var destype=d.applicationStatus}}
    {{# if (destype=='1'){}}
    	试用中
    {{# } else if(destype=='2'){ }}
   		正常服务中
 	{{# } else if(destype=='3'){ }}
   		停止服务
 	{{# } else if(destype=='4'){ }}
   		
 	{{# } else if(destype=='5'){ }}
   		
    {{# } }}
</script>	
<!-- 用户真实姓名模板 -->
<script type="text/html" id="realnameTpl">
 {{#if(d.appOwner){}}
       {{d.appOwner.realName?d.appOwner.realName:'<span style="color:red">未设置</span>'}}
        {{# }else{}}
        <span style="color:red">未设置 </span>   
       {{#}}}
</script>
<!--用户昵称模板  -->
<script type="text/html" id="nicknameTpl">
       {{#if(d.appOwner){}}
              {{d.appOwner.nickName}}
       {{# }else{}}
        <span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
<!--用户电话模板  -->
<script type="text/html" id="phoneTpl">
        {{#if(d.appOwner){}}
       {{d.appOwner.phone?d.appOwner.phone:'<span style="color:red">未设置</span>'}}
        {{# }else{}}
        <span style="color:red">未设置 </span>   
       {{#}}}
</script>
<!--用户邮箱模板  -->
<script type="text/html" id="eMailTpl">
  {{#if(d.appOwner){}}
       {{d.appOwner.email?d.appOwner.email:'<span style="color:red">未设置</span>'}}
        {{# }else{}}
        <span style="color:red">未设置 </span>   
       {{#}}}
</script>
<script type="text/html" id="modelNameTpl">
{{# var parentId = d.modelId}}
{{# if (parentId !=null){}}
     {{d.appModel.modelName}}
{{# } else{ }}
   		<span style="color:red">暂无模板</span>
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
			getlist('', '', '', '', '', '');
		});
		function doSearch() {
			var status = $("#applicationStatus").val();
			var userid = $("#userId").val();
			var appname = $("#appName").val();
			var key = $("#userKeyWords").val();
			var appid = $("#applicationId").val();
			var modelName = $("#modelName").val();
			getlist(status, userid, appname, key, appid, modelName);
		}

		function getlist(status, userid, appname, key, appid, modelName) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'applicationStatus' : status,
					'userId' : userid,
					'appName' : appname,
					'userKeyWords' : key,
					'applicationId' : appid,
					'modelName' : modelName,
					'type':1
				},
				url : '${ctx}/application/showAppList.do',
				cols : [ [ 
// 		        {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'applicationId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				},  {
					field : 'applicationName',
					title : '应用名称',
					width : 180,
					align : 'center',
				}, {
					field : 'modelName',
					title : '模板',
					width : 180,
					align : 'center',
					templet : "#modelNameTpl"
				}, {
					field : 'isShangJia',
					title : '应用状态',
					width : 120,
					align : 'center',
					templet : "#isshangjiaTpl"
				},{
					field : 'nickname',
					title : '用户昵称',
					width : 120,
					align : 'center',
					templet : "#nicknameTpl"
				}, {
					field : 'realName',
					title : '真实姓名',
					width : 120,
					align : 'center',
					templet:'#realnameTpl',
				}, {
					field : 'phone',
					title : '联系方式',
					width : 120,
					align : 'center',
					templet:'#phoneTpl',
				}, {
					field : 'email',
					title : '用户邮箱',
					width : 180,
					align : 'center',
					templet :"#eMailTpl"
				},{
					field : 'appCreateTime',
					title : '创建时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					title : '操作',
					width : 250,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
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
		table.on('tool(descovery)', function(obj) {
			var data = obj.data;
			if (obj.event === 'descoveryDetail') {
				window.location.href="${ctx}/descovery/descoveryDetails.do?descoveryId="+data.descoveryId;
			}
			else if(obj.event === 'goSystem'){
					var index = layui.layer
					.open({
						title : "PC官网",
						type : 2,
						content : "${ctx}/system/goAddSystemUpset.do?appId="
								+ data.applicationId,
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
			}
		});

	
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		

		function deleteAppId(aid){
			layer.confirm('您确认删除该 小程序么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/application/deleteAppId.do', {
					'applicationId' : aid
				}, function(data) {
					if (data.code==0) {
						layer.msg("删除小程序成功!");
						setTimeout(function(){
							window.location.reload();
						},400);
					} else {
						layer.msg(data.msg, {
							time : 2500
						});
					}
				});
			}, function() {
				layer.closeAll();
			})
		}
		function updateStatus(aid,type){
			if(type==2){
				layer.confirm('您确认停止该小程序么?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/application/updateAppliction.do', {
						'applicationId' : aid,
						'applicationStatus' : 3,
					}, function(data) {
						if (data.code==0) {
							layer.msg('停止小程序成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg(data.msg, {
								time : 2500
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			}else{
				layer.confirm('您确认开启该小程序么?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/application/updateAppliction.do', {
						'applicationId' : aid,
						'applicationStatus' : 2,
					}, function(data) {
						if (data.code==0) {
							layer.msg('开启小程序成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg(data.msg, {
								time : 2500
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			}
		}
	</script>
</body>
</html>