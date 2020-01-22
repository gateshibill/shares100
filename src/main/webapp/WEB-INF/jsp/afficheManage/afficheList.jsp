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
					<label class="layui-form-label">公告类型</label>
					<div class="layui-input-block">
						<select id="afficheType" name="afficheType" class="search-input">
								<option value="" selected="selected">全部</option>
								<option value="1">普通公告</option>
								<option value="2">活动公告</option>
								<option value="2">游戏公告</option>
								<!-- 超级管理员才有的权限 -->
								<c:if test="${loginer.userId == 1}">
								<option value="4">答题游戏</option>
								<option value="5">转盘游戏</option>
								<option value="6">祝福广告</option>
								</c:if>
						</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">公告编号</label>
					<div class="layui-input-block">
						<input type="text" name="afficheId" id="afficheId"
								placeholder="请输入公告编号" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">公告标题</label>
					<div class="layui-input-block">
						<input type="text" name="afficheTitle" id="afficheTitle"
								placeholder="请输入公告标题" class="layui-input">
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
			data-type="addaffiche">
			<i class="layui-icon">&#xe608;</i>发布公告
		</button>
	</div>
	<table class="layui-table" id="affiche" lay-filter="affiche"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="afficheDetail" >查看</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteAffiche({{d.afficheId}})">删除</a>

</script>
	<!-- 发现类型模板 -->
	<script type="text/html" id="affctypeTpl">
    {{# var affctype=d.afficheType}}
    {{# if (affctype=='1'){}}
    	普通公告
    {{# } else if(affctype=='2'){ }}
   		活动公告
 	{{# } else if(affctype=='3'){ }}
   		游戏公告
    {{# } }}
</script>
	<script type="text/html" id="nicknameTpl">
       {{#if(d.afficheComUser){}}
              {{d.afficheComUser.nickName}}
       {{# }else{}}
        	<span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
	<script type="text/html" id="realnameTpl">
	{{#if(d.afficheComUser){}}
    	{{d.afficheComUser.realName?d.afficheComUser.realName:'未设置'}}
	{{# }else{}}
    	<span style="color:red"> 用户未找到   </span>   
    {{#}}}
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
				url : '${ctx}/affiche/afficheList.do',
				cols : [ [ 
// 				 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'afficheId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'afficheTitle',
					title : '公告标题',
					width : 180,
					align : 'center'
				}, {
					field : 'afficheType',
					title : '公告类型',
					width : 130,
					align : 'center',
					templet : "#affctypeTpl"
				}, {
					field : 'publisherId',
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
					field : 'readCount',
					title : '访客总数',
					width : 120,
					sort : true,
					align : 'center'
				}, {
					field : 'collectCount',
					title : '点赞总数',
					width : 120,
					sort : true,
					align : 'center',
				}, {
					field : 'joinCount',
					title : '加入人数',
					width : 120,
					sort : true,
					align : 'center',
				}, {
					field : 'createTime',
					title : '发布时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					title : '操作',
					width : 170,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				} ] ],
				page : true,
				limits : [ 10, 15, 20, 30, 50, 100 ],
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
		table.on('tool(affiche)',function(obj) {
							var data = obj.data;
							if (obj.event === 'afficheDetail') {
								window.location.href = "${ctx}/affiche/afficheDetails.do?afficheId="
										+ data.afficheId;
							} 
						});

		var $ = layui.$, active = {
			addaffiche : function() { //验证是否全选
				if ($("#afficheType").val() == "") {
					layer.msg("请选择要发布的公告类型");
				} else {
					window.location.href = '${ctx}/affiche/goAddaffiche.do?afficheType='
						+ $("#afficheType").val()+ '&loginPlat='+ $("#loginPlat").val();
		/* 					var index = layui.layer.open({
								title : "添加公告",
								type : 2,
								content : '${ctx}/affiche/goAddaffiche.do?afficheType='
									+ $("#afficheType").val()+ '&loginPlat='+ $("#loginPlat").val(),
								success : function(layero, index){
									setTimeout(function(){
										layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
											tips: 3
										});
									},500)
								}
							})			
							layui.layer.full(index); */

				}

			}
		};
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		
		function deleteAffiche(aid){
			layer.confirm('您确认删除该公告么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/affiche/deleteAffiche.do', {
					'afficheId' : aid
				}, function(data) {
					if (data) {
						layer.msg('删除公告成功');
						setTimeout(function() {
							window.location.reload();
						}, 400);
					} else {
						layer.msg('删除公告失败', {
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