<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加商品子分类类型表单</title>
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
<style type="text/css">

</style>
</head>
<body>
	<div style="margin: 15px;">
		<from class="layui-form">
				 
			
				<div class="layui-form-item">
					<label class="layui-form-label">类型名称：</label>
					<div class="layui-input-inline" style="width: 300px;">
						<input type="text" style="display: inline !important; width: 200px;"
							name="typeName" lay-verify="typeName" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">类型英文名称：</label>
					<div class="layui-input-inline" style="width: 300px;">
						<input type="text" style="display: inline !important; width: 200px;"
							name="enTypeName" lay-verify="enTypeName" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				
					<div class="layui-form-item">
					<label class="layui-form-label">排序：</label>
					<div class="layui-input-inline" style="width: 300px;">
						<input type="number" style="display: inline !important; width: 200px;"
							name="orderNo" lay-verify="orderNo" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				
				 <div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">类型图标：</label>
					<div class="layui-input-block">
						<label class="detail-label-value">
						<img id="typeicon" src="${type.typeIcon}" style="max-width:50px;max-height:30px;" /></label>
						<button type="button" class="layui-btn" id="uploadIcon">上传图片</button> 
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label"></label>
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					</div>
				</div>
			</from>
		</div>
	
</body>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
var parentId = '${parentId}';
var loginPlat = '${loginPlat}';
var uploadIcon = "";
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#uploadIcon'
	    ,url: '${ctx}/layfileUpload/upload.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	    obj.preview(function(index, file, result){	    	 
	    	$('#typeicon').attr('src',result);
	    })
	    }
	    ,done: function(res,index, upload){
	        uploadIcon = res.data.src;
	    }
	  });
	  
	});
layui.use([ 'form', 'layedit', 'laypage', 'laydate' ],
		function() {
	 form = layui.form, laypage = layui.laypage, layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
			
		//创建一个编辑器
		var editIndex = layedit.build('goodsDetails_edit');
		//自定义验证规则
		form.verify({
			loginPlat :function(value) {
				if(value.length == "") {
						   return "请选择应用";
								}
							},
		      typeName :function(value) {
					if(value.length == "") {
				           return "请输入商品类型名称";
					}
				},
		});
		
		
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/goods/saveGoodsType.do",
				dataType:"json",
				data : {
					"loginPlat":loginPlat,
					"typeName":data.field.typeName,
					"enTypeName":data.field.enTypeName,
					"parentId":parentId,
					"typeIcon":uploadIcon,
					"orderNo":data.field.orderNo
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加商品类型成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
// 							window.location.href="${ctx}/goods/goodsTypeList.do";
						  }, 1000);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.msg("添加失败");
				}
			});
			return false;
		});
	});
	
</script>
</html>