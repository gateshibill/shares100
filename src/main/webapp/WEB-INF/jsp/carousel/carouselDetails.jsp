<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑轮播图</title>
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
			<legend>轮播图【${picture.pictureName}】的详情</legend>
		</fieldset>
		<div class="layui-form">
			
			<div class="layui-form-item">
				<label class="layui-form-label">图片名称：</label>
				<div class="layui-input-block">
					<input type="text" name="pictureName" lay-verify="pictureName" autocomplete="off"
						class="layui-input" value="${picture.pictureName}">
				</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">轮播图[移动端]：</label>
					<div class="layui-input-block">
  						<img id="pictureUrl" src="${picture.pictureUrl}" style="max-width:200px;max-height:150px;">
    					<input type="file" name="file" class="layui-upload-file" id="test" accept=".jpg,.gif,.png,.jpeg">
					</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">轮播图[电脑端]：</label>
					<div class="layui-input-block">
  						<img id="pcPictureUrl" src="${picture.pcPictureUrl}" style="max-width:200px;max-height:150px;">
    					<input type="file" name="file" class="layui-upload-file" id="pctest" accept=".jpg,.gif,.png,.jpeg">
					</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">播放顺序：</label>
				<div class="layui-input-block">	
				<input type="text" name="orderId" lay-verify="orderId" autocomplete="off"
						class="layui-input" placeholder="请输入播放顺序" value="${picture.orderId}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">链接地址：</label>
				<div class="layui-input-inline" >
					<input type="text" name="hrefUrl" lay-verify="hrefUrl" autocomplete="off"
						class="layui-input" value="${picture.hrefUrl}">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">是否启用：</label>
				<div class="layui-input-block">
						<select name="isUsing" lay-verify="isUsing" class="layui-input search-select">
							<option value="0" <c:if test="${picture.isUsing == 0}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${picture.isUsing == 1}">selected="selected"</c:if>>是</option>
						</select>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<input type="hidden" value="${picture.pictureId}" name="pictureId"/>
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
layui.use('upload', function(){
	  layui.upload({
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#test' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	      	$("#pictureUrl").attr("src",res.data.src);
	    }
	  });
	});
layui.use('upload', function(){
	  layui.upload({
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#pctest' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	      	$("#pcPictureUrl").attr("src",res.data.src);
	    }
	  });
	});	
	layui.use([ 'form', 'layedit', 'laydate' ],
					function() {
						var form = layui.form(), layer = layui.layer;
						//自定义验证规则
						form.verify({
							pictureName: function(value) {
								if(value.trim() == '') {
									return '请输入图片名称！';
								}
							},
							pictureUrl: function(value) {
								if(value.trim() == '') {
									return '图片不能为空！';
								}
							}
						});
						
						//监听提交
						form.on('submit(demo1)', function(data) {
							$.ajax({
								type : "post",
								url : "${ctx}/carousel/updateCarousel.do",
								dataType : "json",
								data : {
									"pictureId" : '${picture.pictureId}',
									"pictureName" : data.field.pictureName,
									"isUsing" : data.field.isUsing,
									"pictureUrl":pictureUrl.src,
									"pcPictureUrl":pcPictureUrl.src,
									"hrefUrl":data.field.hrefUrl,
									"orderId":data.field.orderId
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改成功");
										setTimeout(function () {
											 layer.closeAll("iframe");
						      				//刷新父页面
						      				 parent.location.reload();
										}, 1000)
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