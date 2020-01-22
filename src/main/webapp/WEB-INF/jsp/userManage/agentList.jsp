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
	.layui-form-select .layui-input {
    padding-right: 30px;
    cursor: pointer;
    width: 155px;
    height: 38px;
}
</style>
</head>
<body>
	<blockquote class="layui-elem-quote news_search">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">应用名称</label>
					<div class="layui-input-block">
						 <select id="loginPlat" class="search-input" name="loginPlat"
								lay-verify="required" lay-search="">					  
								 <option value="">全部</option>
							<c:forEach var="app" items="${appList}">
								<c:choose>
									<c:when test="${loginPlat==app.applicationId}">
										<option selected="selected" value="${app.applicationId}">${app.applicationName}</option>
									</c:when>
									<c:otherwise>
										<option value="${app.applicationId}">${app.applicationName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="layui-inline pane-select">
					<label class="layui-form-label">用户名称</label>
					<div class="layui-input-block ">
						<input type="text" name="userKeyWords" id="userKeyWords" placeholder="请输入用户关键字" class="layui-input search-input" style="height:38px;line-height:38px;width:220px;">
					</div>
				</div>
		<div class="layui-inline">
	        <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
	</form>
	</blockquote>
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn layui-btn-mini" lay-event="visitlist" >邀请列表</a>
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="consumelist" >消费列表</a>

</script>
<script type="text/html" id="usersexTpl">
    {{# var issj=d.userSex}}
    {{# if (issj=='1'){}}
    	男
    {{# } else{ }}
   		女
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
			getlist(pid, '');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var nickName = $("#userKeyWords").val();
			getlist(pid,nickName);
		}

		function getlist(pid,nickName) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'nickName' : nickName,
					
				},
				url : '${ctx}/user/getagentlist.do',
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
				},{
					field : 'realName',
					title : '真实姓名',
					width : 140,
					align : 'center',
				},{
					field : 'nickName',
					title : '用户昵称',
					width : 140,
					align : 'center',
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
					
				},{
					field : 'createTime',
					title : '注册时间',
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
			var title = '';
			var url = '';
			if (obj.event === 'visitlist') {
				title = '邀请列表';
				url ='/user/govisitlist.do?introducer='+data.userId+"&loginPlat="+data.loginPlat;
			} else if (obj.event === 'consumelist') {
				title = '消费列表';
				url = '/user/govisitconsumelist.do?agentId='+data.userId+"&loginPlat="+data.loginPlat;
			} 
			var index = layui.layer.open({
				title : title,
				type : 2,
				content : "${ctx}"+url,
				success : function(layero, index){
					setTimeout(function(){
						layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
							tips: 3
						});
					},500)
				}
			})			
			layui.layer.full(index);
		});

		 var $ = layui.$, active = {
			addDescovery : function() { //验证是否全选
				if($("#descoveryType").val()==""){
					layer.msg("请选择要发布的发现类型");
				}else{
					window.location.href='${ctx}/descovery/goAddDescovery.do?descoveryType='+$("#descoveryType").val()+'&id='+$("#loginPlat").val()+'';
				}
			}
		}; 
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
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