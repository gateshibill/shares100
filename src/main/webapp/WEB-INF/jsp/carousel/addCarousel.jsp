<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加轮播图表单</title>
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
	<style type="text/css">
		.layui-form-label{
			width:130px;
		}
		.layui-input, .layui-textarea{
			width:100%;
		}
		.layui-input-block{
			margin-left:160px;
		}
	</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">图片名称：</label>
				<div class="layui-input-block">
					<input type="text" name="pictureName" lay-verify="pictureName" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">轮播图[移动端]：</label>
					<div class="layui-input-block">
  						<img id="pictureUrl" src="" style="max-width:200px;max-height:150px;">
    					<input type="file" name="file" class="layui-upload-file" id="test" accept=".jpg,.gif,.png,.jpeg">
					</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">轮播图[电脑端]：</label>
					<div class="layui-input-block">
  						<img id="pcPictureUrl" src="" style="max-width:200px;max-height:150px;">
    					<input type="file" name="file" class="layui-upload-file" id="pctest" accept=".jpg,.gif,.png,.jpeg">
					</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">播放顺序：</label>
				<div class="layui-input-block">	
				<input type="text" name="orderId" lay-verify="orderId" autocomplete="off"
						class="layui-input" placeholder="请输入播放顺序">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">链接地址：</label>
				<div class="layui-input-block" >
					<input type="text" name="hrefUrl" lay-verify="hrefUrl" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">是否启用：</label>
				<div class="layui-input-block">
						<select name="isUsing" lay-verify="isUsing" class="layui-input search-select">
							<option value="0">否</option>
							<option value="1" selected="selected">是</option>
						</select>
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
layui.use(
	[ 'form', 'layedit', 'laydate' ],
		function() {
			var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
			layedit.set({
			uploadImage: {
				url: '${ctx}/layfileUpload/upload.do1', //接口url
				type: 'post' //默认post
			 }
		});
		//创建一个编辑器
		var editIndex = layedit.build('goodsDetails_edit');
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
				url : "${ctx}/carousel/addNewCarouselPic.do",
				dataType:"json",
				 data : {
					"pictureName":data.field.pictureName,
					"pictureUrl":pictureUrl.src,
					"orderId":data.field.orderId,
					"isUsing":data.field.isUsing,
					"pcPictureUrl":pcPictureUrl.src,
					"hrefUrl":data.field.hrefUrl,
					"loginPlat":'${id}',
				}, 
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						}, 1000)
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