<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑项目</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />

</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">项目名称：</label>
				<div class="layui-input-block">
					<input type="text" name="name" autocomplete="off" class="layui-input" lay-verify="name" value="${project.name}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">项目拥有者：</label>
				<div class="layui-input-inline">
					<input type="text" name="createUserId" id="createUserId" autocomplete="off" class="layui-input" lay-verify="createUserId">
				</div>
				<button type="button" onclick="showModal();" class="layui-btn">查找</button>
			</div>
			<%-- <div class="layui-form-item">
				<label class="layui-form-label">创建用户：</label>
				<div class="layui-input-block">
					<input type="text" name="createUserId"  autocomplete="off"
						class="layui-input" lay-verify="createUserId" value="${project.createUserId}">
				</div>
			</div> --%>
			<div class="layui-form-item">
				<label class="layui-form-label">创建用户：</label>
				<div class="layui-input-inline">
					<select name="loginPlat" id="loginPlat" lay-verify="loginPlat"
							autocomplete="off" class="layui-input" lay-search="" lay-filter="loginPlat">
						<option value="">请选择应用</option>						
						<c:forEach var="app" items="${list}">
							<option value="${app.id}">${app.realName}</option>
									
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">管理者：</label>
				<div class="layui-input-block">
					<input type="text" name="waiterId"  autocomplete="off"
						class="layui-input" lay-verify="waiterId" value="${project.waiterId}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">状态：</label>
				<div class="layui-input-inline">
					<select name="status">
							<option value="1" <c:if test="${project.status==1}">selected="selected"</c:if>>顾问</option>
							<option value="2" <c:if test="${project.status==2}">selected="selected"</c:if>>设计师</option>
 							<option value="3" <c:if test="${project.status==3}">selected="selected"</c:if>>工长</option>
							<option value="4" <c:if test="${project.status==4}">selected="selected"</c:if>>预约量房</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" value="${project.id}" name="id"/>
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">

layui.use([ 'form', 'layer'],
		function() {
	 	form = layui.form(), layer = layui.layer;
	 
		//自定义验证规则
		form.verify({	
			name : function(value) {
				if (value.length == "") {
					return "请输入项目名称";
				}
			},
			ownerId : function(value) {
				if (value.length == "") {
					return "请输入项目拥有者";
				}
			},
			createUserId : function(value) {
				if (value.length == "") {
					return "请输入创建用户";
				}
			},
			waiterId : function(value) {
				if (value.length == "") {
					return "请输入顾问名字";
				}
			},
			status : function(value) {
				if (value.length == "") {
					return "请选择当前状态";
				}
			},
			
			detail : function(value) {
				if (value.length == "") {
					return "请输入详情";
				}
			},
		
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			var uploadStr = "",upload_collCover="";
			
			$.ajax({
				type : "post",
				url : "${ctx}/project/subjectProject.do",
				dataType:"json", 
				data : {
					"name":data.field.name,
					"ownerId":data.field.keyWord,
					"createUserId":data.field.loginPlat,
					"waiterId":data.field.waiterId,
					"status":data.field.status,
					
						
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("编辑成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						  }, 1000);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.msg("编辑失败");
				}
			});
			return false;
		});
	});
function goBack(){
	window.history.go(-1);
}
//弹框
function showModal(){
	//打开弹框
	var modalIndex = layer.open({
		  title:'修改项目拥有者',
		  area:['60%','80%'],
		  type:0,
		  content: '<div>'+
			  '<div class="layui-form-item"><label class="layui-form-label" style="width:50px">关键字:</label>'+
			  '<div class="layui-input-inline"><input type="text" name="keyword" id="keyword"  placeholder="请输入姓名称或联系方式" autocomplete="off" class="layui-input">'+
			  '</div><input type="button" class="layui-btn" onclick="searchUser();" value="查询"></div>'+
			  '<div class="showData"></div></div>',
		});
}
//查询方法
function searchUser(){
	var keyWord = $("#keyword").val();
	if(!keyWord){
		$(".showData").html("<span style='color:red;text-align:center;padding-top:10px'>请输入搜索关键字</span>");
		return;
	}else{
		$.ajax({
			url:'${ctx}/user/searchUserProject.do',
			type:'post',
			dataType:'json',
			data:{'keyWord':keyWord},
			success:function(data){
				if(data.code == 0){
					var obj = data.objects;
					var html = "";
					html += '<table class="layui-table">',
					html += '<thead><tr>', 
					html += '<th>id</th>', 
					html += '<th>用户名称</th>',
					html += '<th>联系方式</th>',
					html += '<th>操作</th>',
					html += '</tr></thead>';
					
					html += '<tbody>';
					//tr数据 
					for (var i = 0; i < obj.length; i++) {
						html += '<tr>';
						html += '<th>'+obj[i].userId+'</th>';
						html += '<th>'+obj[i].realName+'</th>';
						html += '<th>'+obj[i].phone+'</th>';
						html += '<th style="text-align:center"><a type="button" class="layui-btn layui-btn-mini" href="javascript:;" onclick="choose('+obj[i].userId+')">选择</a></th>';
						html += '</tr>';
					}
					html += '</tbody>';
					html += '</table>';
					$(".showData").html(html);
				}else{
					layer.msg(data.msg);
					return;
				}
			},
			error:function(data){
				alert();
			}
			
		})
		return;
		
	}
	return;
}

function choose(userId){
	$("#createUserId").val(userId);
	layer.closeAll();
}
</script>
</html>