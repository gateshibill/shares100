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
						<input type="text" name="loginPlat1" id="loginPlat1"
								placeholder="请输入应用编号" class="layui-input">
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">活动类型</label>
					<div class="layui-input-block">
						<select id="type" name=type class="search-input">
								<option value="" selected="selected">全部</option>
								<option value="1">普通转盘抽奖</option>
								<option value="2">有奖竞猜</option>
								<option value="2">红包转盘抽奖</option>
							</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">活动编号</label>
					<div class="layui-input-block">
						<input type="text" name="activityId" id="activityId"
								placeholder="请输入活动编号" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">活动标题</label>
					<div class="layui-input-block">
						<input type="text" name="activityTitle" id="activityTitle"
								placeholder="请输入活动标题" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布者编号</label>
					<div class="layui-input-block">
						<input type="text" name="publisherId" id="publisherId"
								placeholder="请输入发布者编号" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布者名称</label>
					<div class="layui-input-block">
						<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入发布者关键字" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布日期</label>
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
	<div class="layui-btn-group demoTable">
		<button class="layui-btn" style="margin-left: 10px;"
			data-type="addActivity">
			<i class="layui-icon">&#xe608;</i>发布活动
		</button>
	</div>
	<table class="layui-table" id="affiche" lay-filter="affiche"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="activityDetail" >查看</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteActivity({{d.activityId}})">删除</a>

</script>
	<!-- 发现类型模板 -->
	<script type="text/html" id="activityTypeTpl">
    {{# var acttype=d.type}}
    {{# if (acttype=='1'){}}
    	普通转盘抽奖
    {{# } else if(acttype=='2'){ }}
   		有奖竞猜
 	{{# } else if(acttype=='3'){ }}
   		红包转盘抽奖
    {{# } }}
</script>
	<script type="text/html" id="nicknameTpl">
       {{#if(d.activityUser){}}
              {{d.activityUser.nickName}}
       {{# }else{}}
        	<span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
	<script type="text/html" id="realnameTpl">
	{{#if(d.activityUser){}}
    	{{d.activityUser.realName?d.activityUser.realName:'未设置'}}
	{{# }else{}}
    	<span style="color:red"> 用户未找到   </span>   
    {{#}}}
</script>
	<script type="text/html" id="activityStatusTpl">
	{{#var actstatus = d.status }}
    {{# if (actstatus=='1'){}}
    	开启
    {{# } else if(actstatus=='2'){ }}
   		临时关闭
 	{{# } else if(actstatus=='3'){ }}
   		活动结束
	{{# } else if(actstatus=='4'){ }}
   		删除
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
			pid = $("#loginPlat").val();
			getlist(pid, '', '', '', '', '', '', '');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var lp2 = $("#loginPlat1").val();
			var dst = $("#afficheType").val();
			var userId = $("#publisherId").val();
			var ukw = $("#userKeyWords").val();
			var dsid = $("#afficheId").val();
			var ditit = $("#afficheTitle").val();
			var dtr = $("#dateRange").val();
			getlist(pid, lp2, dst, userId, ukw, dsid, ditit, dtr);
		}

		function getlist(pid, loginPlat2, dstype, userId, userKeyWords,
				afficheid, dititle, DateRange) {
			tableIns = table.render({
				elem : '#affiche',
				id : 'affiche',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'loginPlat1' : loginPlat2,
					'afficheType' : dstype,
					'publisherId' : userId,
					'userKeyWords' : userKeyWords,
					'afficheId' : afficheid,
					'afficheTitle' : dititle,
					'dateRange' : DateRange,
				},
				url : '${ctx}/activity/showActivityList.do',
				cols : [ [
// 			    {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'activityId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'activityName',
					title : '活动标题',
					width : 180,
					align : 'center'
				}, {
					field : 'type',
					title : '活动类型',
					width : 130,
					align : 'center',
					templet : "#activityTypeTpl"
				}, {
					field : 'createUser',
					title : '发布者ID',
					width : 140,
					align : 'center'
				}, {
					field : 'nickName',
					title : '发布者昵称',
					width : 140,
					align : 'center',
					sort : true,
					templet : "#nicknameTpl"
				}, {
					field : 'realName',
					title : '发布者姓名',
					width : 200,
					align : 'center',
					templet : "#realnameTpl"
				}, {
					field : 'count',
					title : '可免费参与次数',
					width : 120,
					sort : true,
					align : 'center'
				}, {
					field : 'status',
					title : '活动状态',
					width : 120,
					sort : true,
					align : 'center',
					templet : "#activityStatusTpl"
				}, {
					field : 'createTime',
					title : '发布时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					field : 'updateTime',
					title : '操作时间',
					width : 180,
					sort : true,
					align : 'center',
				}, {
					title : '操作',
					width : 170,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				} ] ],
				page : true,
				limits : [ 10, 15, 20, 30, 50, 100 ],
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
		table
				.on(
						'tool(affiche)',
						function(obj) {
							var data = obj.data;
							if (obj.event === 'activityDetail') {
								window.location.href = "${ctx}/activity/activityDetails.do?activityId="
										+ data.activityId;
							} else if (obj.event === 'passaffiche') {
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
							} else if (obj.event === 'deleteActivity') {
								window.location.href = "${ctx}/activity/activityDetails.do?activityId="
									+ data.activityId;
							}
						});

		var $ = layui.$, active = {
			addActivity : function() { //验证是否全选
				if ($("#type").val() == "") {
					layer.msg("请选择要发布的活动类型");
				} else {
					//window.location.href = '${ctx}/affiche/goAddaffiche.do?afficheType='
					//		+ $("#type").val()+ '&loginPlat='+ $("#loginPlat").val() + '';
				}
			}
		};
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		function deleteActivity(aid){
			layer.confirm('您确认删除该活动么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/activity/deleteActivity.do', {
					'activityId' : aid
				}, function(data) {
					if (data) {
						layer.msg('删除活动成功');
						setTimeout(function() {
							window.location.reload();
						}, 400);
					} else {
						layer.msg('删除活动失败', {
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