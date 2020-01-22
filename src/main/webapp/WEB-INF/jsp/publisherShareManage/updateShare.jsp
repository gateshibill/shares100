<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>表单</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
</head>

<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
		</fieldset>
		<form class="layui-form" action="">
			<div class="layui-form-item">
			<div class="layui-inline">		
				<label class="layui-form-label">共享内容</label>
				<div class="layui-input-inline">
					<textarea  id="content" class="layui-input newsAuthor" style="width:300px;height:120px;"
					lay-verify="shareContent" autocomplete="off" name="shareContent" placeholder="请输入活动内容">${share.shareContent}</textarea>
				</div>
			</div>		
		</div>
					<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">商品外观：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<img id="shareImage" src="${share.shareImage}" style="max-width:200px;max-height:150px;" /></label>
						<input type="file" name="file" class="layui-upload-file" id="test" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">发布时间：</label>
				<div class="layui-input-block">
					<label style="line-height: 38px;"><fmt:formatDate
							value="${share.shareTime}" type="both" /></label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
layui.use('upload', function(){
	  layui.upload({
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#test' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post' //上传接口的http类型
	    ,success: function(res){
	    	console.log(res);
	    	shareImage.src = res.data.src;
	    }
	  });
	});
	layui.use([ 'form', 'layedit', 'laydate' ],
					function() {
						var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
						//自定义验证规则
						form.verify({
							
							shareContent: function(value) {
								if(value.trim() == '') {
									return '共享内容不能为空';
								}
							}
						});
						//监听提交
						form.on('submit(demo1)', function(data) {
							$.ajax({
								type : "post",
								url : "${ctx}/share/updateShare.do",
								dataType : "json",
								data : {
									"shareId" : '${share.shareId}',
									"shareImage":shareImage.src,
									"shareContent" : data.field.shareContent
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改成功");
										setTimeout(function() {
											location.reload();
										}, 1000);
										//setTimeout(location.reload(),1000);
									}
								},
								error : function() {
									layer.msg("修改失败");
								}
							});
							return false;
						});
					});
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>