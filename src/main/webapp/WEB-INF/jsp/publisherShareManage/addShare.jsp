<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加共享表单</title>
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
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<style type="text/css">
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: 5px;
	cursor: pointer;
	color: #35CE8D;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>发布共享</legend>
		</fieldset>
		<form class="layui-form" action="">
		<div class="layui-form-item">
					<label class="layui-form-label">应用类型：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="loginPlat" id="loginPlat" lay-verify="loginPlat" autocomplete="off"
						class="layui-input">
							<option value="">请选择类型</option>
							<c:forEach var="type" items="${appList}">
								<option value="${type.applicationId}">${type.applicationName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			<div class="layui-form-item">
				<label class="layui-form-label">共享内容：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<textarea placeholder="请输入内容" name="shareContent" style="width: 400px;height:160px;" maxlength="300" 
					   class="layui-textarea" lay-verify="shareContent" autocomplete="off"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">共享图片：</label>
				<div class="layui-input-block">
  					<img id="goosdImage" src="" style="max-width:200px;max-height:150px;">
    				<input type="file" name="file" class="layui-upload-file" id="test" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
		
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
			
			
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>
<script type="text/javascript">
var pageno=1,pagesize=10,ulistLoad,type;
layui.use('upload', function(){
	  layui.upload({
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#test' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post' //上传接口的http类型
	    ,success: function(res){
	    	console.log(res);
	      	goosdImage.src = res.data.src;
	    }
	  });
	});
	
layui.use([ 'form', 'layedit', 'laypage', 'laydate' ],
		function() {
	 form = layui.form(), laypage = layui.laypage, layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
			layedit.set({
			uploadImage: {
				url: '${ctx}/layfileUpload/upload.do', //接口url
				type: 'post' //默认post
			 }
		});
		//创建一个编辑器
		var editIndex = layedit.build('goodsDetails_edit');
		//自定义验证规则
		form.verify({
			shareContent: function(value) {
				if(value.trim() == '') {
					return '共享内容不能为空';
				}
			},
			loginPlat: function(value) {
				if(value.trim() == '') {
					return '请选择应用类型';
				}
			},
			
		});
		

		
		//监听提交
		form.on('submit(demo1)', function(data) {
			var way = $('#recommendWay').val();
			$.ajax({
				type : "post",
				url : "${ctx}/share/addPublisherShare.do",
				dataType:"json",
				data : {
					"shareContent":data.field.shareContent,
					"loginPlat":data.field.loginPlat,
					"shareImage":goosdImage.src
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							window.location.href="${ctx}/share/publisherShareList.do";
						  }, 1000);
// 						setTimeout(location.reload(),1000);
					}
				},error:function(){
					layer.msg("添加失败");
				}
			});
			return false;
		});
	});
	

function goBack(){
	window.history.go(-1);
}
</script>
</html>