<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>公告详情</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet"
	href="${ctx}/plugins/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
</head>

<body>
	<div style="margin: 15px;">
		
		<div class="layui-form">
		    <input type="hidden" name="afficheId" id="afficheId" value="${affc.afficheId}">
			<input type="hidden" name="loginPlat" id="loginPlat" value="${affc.loginPlat}"> 
			<div class="layui-form-item">
				<label class="layui-form-label">发布者id：</label>
				<div class="layui-input-block" style="width: 300px;">
					<input type="number"
						style="display: inline !important; width: 200px;"
						name="publisherId" lay-verify="publisherId"
						autocomplete="off" value="${affc.publisherId}" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公告标题：</label>
				<div class="layui-input-block" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="afficheTitle" lay-verify="afficheTitle"
						autocomplete="off" value="${affc.afficheTitle}" class="layui-input">
				</div>
			</div>
			<c:if test="${affc.afficheType == 4}">
				<div class="layui-form-item">
						<label class="layui-form-label">题目类型</label>
						<div class="layui-input-block">
							 <input type="number"
							style="display: inline !important; width: 200px;"
							name="subjectType" lay-verify="subjectType"
							autocomplete="off" value="${affc.subjectType}" class="layui-input">
						</div>
						<p style="color:red">答题活动该项必填</p>
					</div>
			</c:if>	
			<div class="layui-form-item">
				<label class="layui-form-label">公告背景：</label>
				<div class="layui-input-block">
					 <input
						type="file" name="file" class="layui-upload-file" id="test"
						accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin-left:10px;"></label>
			   <img id="goosdImage" src="${affc.afficheImage}"
						style="max-width: 740px; max-height: 480px;">
			</div>
			
			<c:if test="${affc.afficheType == 6}">
			<div class="layui-form-item">
				<label class="layui-form-label">祝福背景图：</label>
				<div class="layui-input-block">
					 <input
						type="file" name="file" class="layui-upload-file" id="zhufu"
						accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="margin-left:10px;"></label>
			   <img id="backgroundUrl" src="${affc.backgroundUrl}"
						style="max-width: 740px; max-height: 480px;">
			</div>
			</c:if>
			<div class="layui-form-item">
				<label class="layui-form-label">公告内容：</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" name="afficheDetail" lay-verify="afficheDetail" id="afficheDetail" style="width: 400px;height:160px;" maxlength="300" class="layui-textarea">
					${affc.afficheDetails}
					</textarea>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script>
	var pageno = 1, pagesize = 60, ulistLoad;
	var afficheImage='';
	var backgroundUrl = '';
	layui.use('upload', function() {
		layui.upload({
		    url: '${ctx}/layfileUpload/upload.do'
		    ,elem: '#test' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post' //上传接口的http类型
		    ,success: function(res){
		    	afficheImage = res.data.src;
		    	$("#goosdImage").attr("src",afficheImage);
		    }
		  });
	});
	layui.use('upload', function() {
		layui.upload({
		    url: '${ctx}/layfileUpload/upload.do'
		    ,elem: '#zhufu' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post' //上传接口的http类型
		    ,success: function(res){
		    	backgroundUrl = res.data.src;
		    	$("#backgroundUrl").attr("src",backgroundUrl);
		    }
		  });
	});
	layui.use([ 'form', 'layedit', 'laypage', 'laydate' ],function() {
						form = layui.form(), laypage = layui.laypage,
								layer = layui.layer, layedit = layui.layedit,
								laydate = layui.laydate;
						layedit.set({
							uploadImage : {
								url : '${ctx}/layfileUpload/upload.do', //接口url
								type : 'post' //默认post
							}
						});
						//创建一个编辑器
						 editIndex = layedit.build('afficheDetail');
						//自定义验证规则
						form.verify({
							publisherId : function(value){
								if (value.trim() == '') {
									return '发布者id不能为空！';
								}
							},
							afficheTitle : function(value) {
								if (value.trim() == '') {
									return '公告标题不能为空！';
								}
							}
							
						});
	

						//监听提交
						form.on('submit(demo1)',function(data) {
							var loginPlat = $("#loginPlat").val();
							var afficheId = $("#afficheId").val();
							if(loginPlat == null && !loginPlat){
					    		  layer.msg('应用id不能为空',{time:1500});
					    		  return;
							}
							if(!afficheId){
								 layer.msg('公告id不能为空',{time:1500});
					    		  return;
							}
							if(!afficheImage){
								afficheImage = $("#goosdImage").attr("src");
							}
							if(!backgroundUrl){
								backgroundUrl = $("#backgroundUrl").attr("src");
							}
						 	$.ajax({
								url:'${ctx}/affiche/updateaffiche.do',
								type:'post',
								dataType:'json',
								data:{
									'publisherId':data.field.publisherId,
									'afficheTitle':data.field.afficheTitle,
									'afficheImage':afficheImage,
									'afficheDetails':layedit.getContent(editIndex),
									'afficheId':afficheId,
									'isRemove':0,
									'backgroundUrl':backgroundUrl,
									'subjectType' : data.field.subjectType?data.field.subjectType:'',
									
								},
								success:function(data){
									if(data.code == 0){
										layer.msg("编辑公告成功");
										setTimeout(function () {
											window.location.href="${ctx}/affiche/goAfficheList.do";
										  }, 400);
									}else{
										layer.msg(data.msg);
									}
								},
								error:function(data){
									alert(JSON.stringify(data));
									layer.msg("error：编辑公告失败");
								}
							}) 
							
						});
					});
	
	
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>