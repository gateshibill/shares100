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
		<form class="layui-form layui-form-pane" action="">
			<div class="layui-form-item">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">应用状态</label>
					<div class="layui-input-block ">
						<select id="applicationStatus" name="applicationStatus"
								class="search-select" lay-search="">
								<option value="">全部</option>
								<option value="1"
									<c:choose>
										<c:when test="${app.applicationStatus==1}">selected="selected"</c:when>
									</c:choose>>试用中
								</option>
								<option value="2"
									<c:choose>
										<c:when test="${app.applicationStatus==2}">selected="selected"</c:when>
									</c:choose>>正常服务中
								</option>
								<option value="3"
									<c:choose>
										<c:when test="${app.applicationStatus==3}">selected="selected"</c:when>
									</c:choose>>停止服务中
								</option>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">应用分类</label>
					<div class="layui-input-block">
						<select id="groupType" class="search-select" lay-search="">
								<option value="">全部</option>
								<c:forEach items="${typeList}" var="g">
									<option value="${g.typeId}">${g.typeName}</option>
								</c:forEach>
							</select>
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">应用类型</label>
					<div class="layui-input-block">
						<select id="appType" class="search-select" lay-search="">
								<option value="">全部</option>
								<option value="2"
								<c:choose>
								<c:when test="${app.applicationType==2}">selected="selected"</c:when>
								</c:choose>>社区</option>
								<option value="3"<c:choose>
								<c:when test="${app.applicationType==3}">selected="selected"</c:when>
								</c:choose>>企业</option>
								</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">是否推荐</label>
					<div class="layui-input-block">
						<select id="isRecommend" class="search-select" lay-search="">
								<option value="">全部</option>
								<option value="0"
								<c:choose>
								<c:when test="${app.isRecommend==0}">selected="selected"</c:when>
								</c:choose>>否</option>
								<option value="1"<c:choose>
								<c:when test="${app.isRecommend==1}">selected="selected"</c:when>
								</c:choose>>是</option>
								</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">应用名称</label>
					<div class="layui-input-block">
						<input type="text" name="appName" id="appName"
								placeholder="请输入应用名称" class="layui-input" value="${app.applicationName}">
					</div>
				</div>
				
	
				
				<div class="layui-inline">
					<label class="layui-form-label">发布者名称</label>
					<div class="layui-input-block">
						<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入发布者名称" class="layui-input" value="${userKeyWords}">
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
<!-- 	<!-- 后台暂时不做添加 --> 
<!-- 	<div class="layui-btn-group demoTable"> -->
<%-- 		<a href="${ctx}/application/goaddapp.do" --%>
<!-- 					class="layui-btn layui-btn-small" id="add"> <i -->
<!-- 					class="layui-icon">&#xe608;</i> 添加应用 -->
<!-- 		</a> -->
<!-- 	</div> -->
	<!-- <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="deleteDescovery">删除</a> -->
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
<a href="${ctx}/application/appDetails.do?appId={{d.applicationId}}"
	class="layui-btn layui-btn-normal layui-btn-mini">编辑</a>
	{{# var destype=d.applicationStatus}}
	{{# if (destype=='2'){}}
		<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="updateStatus({{d.applicationId}},{{destype}})">
             <span class="icon-trash-o"></span>停止</a>
    {{# } else if(destype=='3'){ }}
			<a class="layui-btn layui-btn-mini" onclick="updateStatus({{d.applicationId}},{{destype}})">
            <span class="icon-check-circle-o"></span>开通</a>
    {{# } }}
  
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteAppId({{d.applicationId}})">删除</a>
</script>
	<!-- 应用状态模板 -->
	<script type="text/html" id="applicationStatusTpl">
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
<script type="text/html" id="groupTypeTpl">
   {{d.apptype.typeName}}
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
              {{d.appOwner.nickName?d.appOwner.nickName:'<span style="color:red">未设置</span>'}}
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
       {{d.appOwner.email?d.appOwner.email:' <span style="color:red">未设置</span>'}}
        {{# }else{}}
        <span style="color:red">未设置 </span>   
       {{#}}}
</script>
<!-- isRecommendTpl onclick="updateisRecommend({{d.applicationId}},{{d.isRecommend}})" -->
<script type="text/html" id="isRecommendTpl">
  {{# var tj=d.isRecommend}}
    {{# if (tj=='0'){}}
         <a href="#" style="color:#009688" lay-event="updateIsRecomm">否</a>
    {{# } else if(tj=='1'){ }}
   		 <a href="#" style="color:red" lay-event="updateIsRecomm">是</span></a>
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
			 status = $("#applicationStatus").val();
			 appname = $("#appName").val();
			 key = $("#userKeyWords").val();
			 appid = $("#appType").val();
			//--------------方法渲染TABLE----------------
			getlist(status, '', appname, key, appid);
		});
		function doSearch() {
			var status = $("#applicationStatus").val();
			var userid = $("#userId").val();
			var appname = $("#appName").val();
			var key = $("#userKeyWords").val();
			var appid = $("#appType").val();
			var isr=$('#isRecommend').val();
			var type=$('#groupType').val();
			getlist(status, userid, appname, key, appid,isr,type);
		}

		function getlist(status, userid, appname, key, appid,isRecommend,groupType) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'applicationStatus' : status,
					'userId' : userid,
					'appName' : appname,
					'userKeyWords' : key,
					'applicationType' : appid,
					'isRecommend':isRecommend,
					'groupType':groupType,
					'type':3
				},
				url : '${ctx}/application/showAppList.do',
				cols : [ [ 
				/* {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				}, */
				{
					field : 'applicationId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'applicationName',
					title : '应用名称',
					width : 180,
					align : 'center',
				}, 
				{
					field : 'applicationStatus',
					title : '应用状态',
					width : 120,
					align : 'center',
					templet : "#applicationStatusTpl"
				},
				{
					field : 'groupType',
					title : '应用分类',
					width : 120,
					align : 'center',
					templet : "#groupTypeTpl"
				},
				{
					field : 'isRecommend',
					title : '是否推荐',
					width : 120,
					align : 'center',
					templet : "#isRecommendTpl"
				},
				{
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
				}, {
					field : 'appCreateTime',
					title : '创建时间',
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
			if (obj.event === 'updateIsRecomm') {
				if(data.isRecommend==1){
					$.post('${ctx}/application/updateAppliction.do', {
						'applicationId' : data.applicationId,
						'isRecommend' : 0,
					}, function(data) {
						if (data.code==0) {
							layer.msg('取消推荐应用或社区成功');
							//同步更新缓存对应的值
							 obj.update({
								isRecommend : 0
							});
						} else {
							layer.msg(data.msg, {
								time : 2500
							});
						}
					});
				} else{
					$.post('${ctx}/application/updateAppliction.do', {
						'applicationId' : data.applicationId,
						'isRecommend' : 1,
					}, function(data) {
						if (data.code==0) {
							layer.msg('推荐应用或社区成功');
							//同步更新缓存对应的值
							 obj.update({
								isRecommend : 1
							});
						} else {
							layer.msg(data.msg, {
								time : 2500
							});
						}
					});
				}
			} 
// 			else if(obj.event === 'updateStatus'){
// 				if(data.applicationStatus==2){
// 					$.post('${ctx}/application/updateAppliction.do', {
// 						'applicationId' : data.applicationId,
// 						'applicationStatus' : 3,
// 					}, function(data) {
// 						if (data.code==0) {
// 							layer.msg('停止应用或社区成功');
// 							//同步更新缓存对应的值
// 							 obj.update({
// 								 applicationStatus : 3
// 							});
// 						} else {
// 							layer.msg(data.msg, {
// 								time : 2500
// 							});
// 						}
// 					});
// 				} else if(data.applicationStatus==3){
// 					$.post('${ctx}/application/updateAppliction.do', {
// 						'applicationId' : data.applicationId,
// 						'applicationStatus' : 2,
// 					}, function(data) {
// 						if (data.code==0) {
// 							layer.msg('开启应用或社区成功');
// 							//同步更新缓存对应的值
// 							 obj.update({
// 								 applicationStatus : 2
// 							});
// 						} else {
// 							layer.msg(data.msg, {
// 								time : 2500
// 							});
// 						}
// 					});
// 				}
// 			} 
			else {//后面操作
				
			}
		
		});

		
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		
		function updateStatus(aid,type){
			if(type==2){
				layer.confirm('您确认停止该应用么?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/application/updateAppliction.do', {
						'applicationId' : aid,
						'applicationStatus' : 3,
					}, function(data) {
						if (data.code==0) {
							layer.msg('停止应用成功');
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
				layer.confirm('您确认开启该应用么?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/application/updateAppliction.do', {
						'applicationId' : aid,
						'applicationStatus' : 2,
					}, function(data) {
						if (data.code==0) {
							layer.msg('开启成功');
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
		
		function deleteAppId(aid){
			layer.confirm('您确认删除该 社区和应用么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/application/deleteAppId.do', {
					'applicationId' : aid
				}, function(data) {
					if (data.code==0) {
						layer.msg("删除成功!");
						setTimeout(function(){
							window.location.href="${ctx}/application/goapplicationList.do?applicationStatus="+$('#applicationStatus').val()+"&applicationName="+$('#appName').val()
							   +"&userKeyWords="+$('#userKeyWords').val()+"&applicationType="+$('#appType').val();
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
	</script>
</body>
</html>