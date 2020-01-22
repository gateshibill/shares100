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

<style type="text/css">
#demo2 img {
	width: 200px !important;
	height: 150px !important;
	margin-right: 20px;
	margin-top: 15px;
}
.layui-input, .layui-select, .layui-textarea {
    height: 38px;
    line-height: 30px;
/*     line-height: 36px\9; */
    border: 1px solid #e6e6e6;
    background-color: #fff;
    border-radius: 2px;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>模板【${model.modelName}】的详情</legend>
		</fieldset>
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">模板编号：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${model.modelId}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">模板名称：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text"
						style="display: inline !important; width: 200px;" name="modelName"
						lay-verify="goodsName" autocomplete="off" class="layui-input"
						value="${model.modelName}">
					</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">模板连接：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text"
						style="display: inline !important; width: 200px;" name="modelUrl"
						lay-verify="modelUrl" autocomplete="off" class="layui-input"
						value="${model.modelUrl}">
					</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">模板简介：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<textarea  class="layui-input" style="width:500px;height:160px;"
					lay-verify="modelSynopsis" autocomplete="off" name="modelSynopsis"
						   class="layui-input" maxlength="800" >${model.modelSynopsis}</textarea>
					</label>
				</div>
			</div>

			<div class="layui-upload">
				<label class="layui-form-label" style="width: 100px;">模板图片：</label>
				<button type="button" class="layui-btn" id="test2">上传图片</button>
				<span style="color:red;margin-left:-260px;">点击图片即可以删除图片</span>
				<div class="layui-upload-list" id="demo2">
					<c:if test="${myImage != null and myImage !=''}">
						<c:forEach items="${myImage}" var="m" varStatus="status">
							<c:if test="${m!=null and m!=''}">
								<img onclick="delthisImg(${status.index+1},${model.modelId},1)"
									data-index="${ status.index}" src="<c:out value="${m}" />">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<br>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<%-- <script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script> --%>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
var length='${length}',uploadStr='',myImage='${model.modelCarousel}';
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#test2'
	    ,url: '${ctx}/carouselUpload/upload.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  length ++;
	    	  if(length > 3){
	    		  layer.msg('最多上传3张图片',{time:1500});
	    	  } else{
	    		  $('#demo2').append('<img src="'+ result +'" id="'+index+'" onclick="delImg('+index+')" alt="'+ file.name +'" class="layui-upload-img">')
	    	  }
	      });
	    }
	    ,done: function(res){
// 	    	alert(JSON.stringify(res));
	       uploadStr += res.msg+',';
	    }
	  });
	});
	
	layui.use([ 'form', 'layedit', 'laydate' ],
					function() {
		form = layui.form,layer=layui.layer, layedit = layui.layedit;
						layedit.set({
							uploadImage : {
								url : '${ctx}/layfileUpload/upload.do', //接口url
								type : 'post' //默认post
							}
						});
						//创建一个编辑器
						var editIndex = layedit.build('goodsDetails_edit');
						//自定义验证规则
						form.verify({
							goodsName: function(value) {
								if(value.trim() == '') {
									return '模板名称不能为空';
								}
							},
							modelUrl: function(value) {
								if(value.trim() == '') {
									return '模板连接不能为空';
								}
							},
							modelSynopsis: function(value) {
								if(value.trim() == '') {
									return '模板简介不能为空';
								}
							},
							
						});
						//监听提交
						form.on('submit(demo1)', function(data) {
							if(myImage != null && myImage !=''){
								uploadStr += myImage;
							} else {
								uploadStr = uploadStr.substr(0,uploadStr.length-1);
							}
							$.ajax({
								type : "post",
								url : "${ctx}/application/updateModel.do",
								dataType : "json",
								data : {
									"modelId" : '${model.modelId}',
									"modelName" : data.field.modelName,
									"modelCarousel" : uploadStr,
									'modelSynopsis': data.field.modelSynopsis,
									"modelUrl" : data.field.modelUrl
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改成功");
										setTimeout(function() {
											location.reload();
										}, 1000);
										// setTimeout(location.reload(),1000);
									}
								},
								error : function() {
									layer.msg("修改失败");
								}
							});
							return false;
						});
					});
	
	function delImg(index) {
		$("#" + index + '-0').remove();
	}
	
	function delthisImg(intdex,modelId){
		length--;
		$.ajax({
			url:'${ctx}/application/updateModelImage.do',
			type:'post',
			dataType:'json',
			data:{
				'modelId':modelId,
				'index':intdex
			},
			success : function(data) {
				if (data.code == 0) {
					layer.msg('删除图片成功');
					window.location.reload();
				} else {
					layer.msg(res.msg);
				}
			}
		});
	}
	
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>