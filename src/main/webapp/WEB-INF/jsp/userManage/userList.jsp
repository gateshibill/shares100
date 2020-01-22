<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>用户列表</title>
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
			
				<div class="layui-inline pane-select">
					<label class="layui-form-label">应用编号</label>
					<div class="layui-input-block ">
						<input type="text" name="loginPlat1" id="loginPlat1"
								placeholder="请输入应用编号" class="layui-input"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">用户状态</label>
					<div class="layui-input-block">
						<select id="userStatus" name="userStatus" class="search-select">
								<option value="">全部</option>
								<option value="1">正常</option>
								<option value="2">禁用</option>
							</select>
					</div>
				</div>
				
				<!-- <div class="layui-inline">
					<label class="layui-form-label">是否管理员</label>
					<div class="layui-input-block">
						<select id="isManager" name="isManager" class="search-select">
								<option value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">是否代理</label>
					<div class="layui-input-block">
						<select id="isAgent" name="isAgent" class="search-select">
								<option value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
					</div>
				</div> -->
				
				<div class="layui-inline pane-select">
					<label class="layui-form-label">用户昵称</label>
					<div class="layui-input-block ">
						<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入用户关键字" class="layui-input">
					</div>
				</div>
				
				<%-- <div class="layui-inline pane-select">
					<label class="layui-form-label">邀请人昵称</label>
					<div class="layui-input-block ">
						<select name="introducer" id="introducer"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<c:forEach var="app" items="${userList}">
									<option value="${app.userId}">${app.nickName}</option>
								</c:forEach>
							</select>
					</div>
				</div> --%>
				
				<div class="layui-inline">
					<label class="layui-form-label">注册时间区间</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="dataRange" id="test6"
								placeholder=" - ">
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
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn layui-btn-mini" lay-event="lookUser" >查看</a>
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="setAgent" >设置代理</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteUser({{d.userId}})">删除</a>

</script>
	<!-- 上架状态模板 -->
<script type="text/html" id="usersexTpl">
    {{# var issj=d.userSex}}
    {{# if (issj=='1'){}}
    	男
    {{# } else{ }}
   		女
    {{# } }}
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="isManagerTpl">
    {{# var ispass=d.isManager}}
    {{# if (ispass=='0'){}}
     <a href="#" lay-event="nopassDescovery">否</a>
    {{# } else{ }}
   		<a href="#" lay-event="nopassDescovery" style="color:green;">是</a>
    {{# } }}
</script>
	<script type="text/html" id="userStatusTpl">
    {{# var ispass=d.userStatus}}
    {{# if (ispass=='1'){}}
     <a href="#" style="color:green" lay-event="passDescovery">正常</a>
    {{# } else{ }}
   	<a href="#" lay-event="passDescovery" style="color:red;">禁用</a>
    {{# } }}
</script>
	<script type="text/html" id="nicknameTpl">
    {{# var destype=d.nickName}}
     {{# if (destype==null){}}
            	用户未找到
    {{# } else{ }}
   		{{destype}}
    {{# } }}
</script>
	<script type="text/html" id="phoneTpl">
       {{#if(d.phone){}}
      	 	{{d.phone?d.phone:'未设置'}}
       {{# }else{}}
        	未设置     
       {{#}}}
</script>
	<script type="text/html" id="e_mailTpl">
       {{#if(d.eMail){}}
       		{{d.eMail?d.eMail:'未设置'}}
		{{# }else{}}
        	未设置    
       {{#}}}
</script>

	<script type="text/html" id="isAgentTpl">
    {{# var isAgent=d.isAgent}}
    {{# if (isAgent == 1){}}
                            是
    {{# } else{ }}
   	             否  
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
				elem : '#test6',
				range : true
			});
			//--------------方法渲染TABLE----------------
			pid = $("#loginPlat").val();
			getlist(pid, '', '', '', '', '', '','');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var lp2 = $("#loginPlat1").val();
			var ism = $("#isManager").val();
			var userId = $("#userId").val();
			var nickName = $("#userKeyWords").val();
			var introducer=$("#introducer").val();
			var status=$("#userStatus").val();
			var dtr = $("#test6").val();
			var isAgent = $("#isAgent").val()
			getlist(pid, lp2,ism,userId,nickName,introducer,status,dtr,isAgent);
		}

		function getlist(pid, lp2, ism, userId, nickName,introducer,status,DateRange,isAgent) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'loginPlat1' : lp2,
					//'isManager' : ism,
					'userId' : userId, 
					'nickName' : nickName,
					'introducer':introducer,
					'userStatus':status,
					'dataRange' : DateRange,
					//'isAgent':isAgent
				},
				url : '${ctx}/user/showUserList.do',
				cols : [ [ 
// 				  {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
                   {
					field : 'userId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'realName',
					title : '用户名称',
					width : 140,
					align : 'center',
					templet : "#nicknameTpl"
				}, {
					field : 'phone',
					title : '联系方式',
					width : 140,
					align : 'center',
					templet : "#phoneTpl"
				}, {
					field : 'loginPlat',
					title : '平台id',
					width : 140,
					align : 'center'
				}, {
					field : 'email',
					title : '邮箱',
					width : 140,
					align : 'center',
					templet : "#e_mailTpl"
					
				}, {
					field : 'userSex',
					title : '性别',
					width : 100,
					align : 'center',
					templet : "#usersexTpl"
					
				}
				/* , {
					field : 'isAgent',
					title : '是否代理',
					width : 100,
					align : 'center',
					templet : "#isAgentTpl"
					
				}, {
					field : 'isManager',
					title : '是否管理员',
					width : 100,
					align : 'center',
					templet : "#isManagerTpl"
					
				} */
				, {
					field : 'userStatus',
					title : '用户状态',
					width : 100,
					align : 'center',
					templet : "#userStatusTpl"
					
				},{
					field : 'accessTime',
					title : '最近登陆时间',
					width : 180,
					align : 'center',
					sort : true,
				},{
					field : 'createTime',
					title : '注册时间',
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
			if (obj.event === 'lookUser') {
				var index = layui.layer
				.open({
					title : "编辑用户",
					type : 2,
					content : "${ctx}/user/userDetails.do?userId="+data.userId,
					success : function(
							layero,
							index) {
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
			} else if (obj.event === 'passDescovery') {
				if(data.userStatus==1){
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							userStatus:2
							
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('禁用用户成功');
								//同步更新缓存对应的值
								obj.update({
									userStatus : 2
								});
							} 
						}
					});
				} else {
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							userStatus:1
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('恢复用户成功');
								//同步更新缓存对应的值
								obj.update({
									userStatus : 1
								});
							} 
						}
					});
				}
			} else if (obj.event === 'nopassDescovery') {
				if(data.isManager==0){
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							isManager:1
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('设置管理成功');
								//同步更新缓存对应的值
								obj.update({
									isManager : 1
								});
							} 
						}
					});
				} else {
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							isManager:0
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('取消管理成功');
								//同步更新缓存对应的值
								obj.update({
									isManager : 0
								});
							} 
						}
					});
				}
			}else if(obj.event === 'setAgent'){
				var isAgent = data.isAgent;
				var msg = "";
				if(isAgent == 1){
					isAgent = 0;
					msg = "取消代理成功";
				}else{
					isAgent = 1;
					msg = "设置代理成功";
				}
					$.ajax({
						data : 'json',
						url : '${ctx}/user/setagentbyuserid.do',
						type : "post",
						data : {
							userId : data.userId,
							isAgent:isAgent
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg(msg);
								//同步更新缓存对应的值
								obj.update({
									isAgent :isAgent
								});
							}else{
								 layer.msg(data.msg);
								 return;
							} 
						}
					});
				
				
			} 
		});

	
		
		//删除用户
		function deleteUser(userId){
			layer.confirm('您确认删除该用户么?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
			}, function() {
				$.post('${ctx}/user/deleteUserId.do', {
					'userId' : userId
				}, function(data) {
					if (data) {
						layer.msg('删除用户成功');
						setTimeout(function() {
							window.location.reload();
						}, 1000);
					} else {
						layer.msg('删除失败', {
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