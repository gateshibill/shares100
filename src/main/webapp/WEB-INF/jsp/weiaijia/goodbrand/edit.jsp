<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加品牌</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<style type="text/css">
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: -3px;
	cursor: pointer;
	color: #35CE8D;
}
img{
	width:200px;
	height:200px;
	margin-right:20px
}
.layui-form-select .layui-input{
	width:400px;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">	  
		     <div class="layui-form-item">
				    <label class="layui-form-label">所属应用：</label>
				    <div class="layui-input-block" style="width:400px;;margin-left:100px">
				    <select name="appId" id="appId" lay-verify="appId" autocomplete="off"
						class="layui-input" lay-search="" style="width:400px;" required="required">
							<c:forEach var="c" items="${commList}">
								<option value="${c.applicationId}" <c:if test="${c.applicationId == brand.loginPlat}">selected="selected"</c:if>>${c.applicationName}</option>
							</c:forEach>
						</select>
				    </div>
				  </div>
		    
			<div class="layui-form-item">
				<label class="layui-form-label">品牌名称：</label>
				<div class="layui-input-inline">
					<input type="text" style="width: 400px;"
						name="cnBrandName" value="${brand.cnBrandName}" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			

			<div class="layui-form-item">
				<label class="layui-form-label">品牌排序：</label>
				<div class="layui-input-inline">
					<input type="number" style=" width:400px;"
						name="brandOrder" value="${brand.brandOrder}"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			
		 <div class="layui-form-item">        
	           <div class="layui-upload">
	                  <label class="layui-form-label" style="width: 100px;">品牌logo：</label>
	                     <button type="button" class="layui-btn" id="logoupload">上传图片</button> 
	                  <div class="layui-upload-list" id="logolist" style="cursor: pointer;padding-left:100px">
	                  		<c:if test="${not empty brand.brandLogo}">
	                  			<img src="${brand.brandLogo}"  class="layui-upload-img">
	                  		</c:if>
	                  </div>
	          </div>
          </div>
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<input type="hidden" value="${brand.brandId}" name="brandId" />
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
var logolist = '';
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#logoupload'
	    ,url: '${ctx}/layfileUpload/upload.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	    obj.preview(function(index, file, result){	    	 
	    	$('#logolist').html('<img src="'+ result +'"  class="layui-upload-img">')
	    })
	    }
	    ,done: function(res,index, upload){
	    	logolist = res.data.src;
	    }
	  });
	  
	});
layui.use([ 'form', 'layer'],
		function() {
	 form = layui.form, layer = layui.layer;
		//监听提交
		form.on('submit(demo1)', function(data) {
			
			$.ajax({
				type : "post",
				url : "${ctx}/goodbrand/doEditBrand.do",
				dataType:"json",
				data : {
					"loginPlat":data.field.appId,
					"cnBrandName":data.field.cnBrandName,
					"brandOrder":data.field.brandOrder,				
					'brandLogo':logolist,
					"brandId":data.field.brandId,
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
 
</script>
</html>