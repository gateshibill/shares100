<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>修改广告图</title>
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
#Images li{
	float:left;
	width:300px;
	height:300px;
	margin-bottom:20px;
}
#Images img{
	width:300px;
	height:300px;
}
.img_del{
    cursor: pointer;
}
.layui-form-select .layui-edge{
	right:5%;
}
.layui-form-select dl, .layui-nav-child {
    min-width: 95%;
    margin-left: 20px;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">广告名：</label>
				<div class="layui-input-block">
					<input type="text" name="advName"  autocomplete="off" class="layui-input" lay-verify="advName" value="${adv.advName}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">广告跳转链接：</label>
				<div class="layui-input-block">
					<input type="text" name="advJumpUrl"  autocomplete="off" lay-verify="advJumpUrl" class="layui-input" value="${adv.advJumpUrl}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">排序：</label>
				<div class="layui-input-block">
					<input type="number" name="advOrder" autocomplete="off"
						class="layui-input" lay-verify="advOrder"
						value="${adv.advOrder}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">广告位置：</label>
				<div class="layui-input-block">
					<select name="advLocation">
							<option value="1" <c:if test="${adv.advLocation==1}">selected="selected"</c:if>>首页顶部广告</option>
							<option value="3" <c:if test="${adv.advLocation==3}">selected="selected"</c:if>>首页秒杀广告</option>
							<option value="4" <c:if test="${adv.advLocation==4}">selected="selected"</c:if>>首页新品广告</option>
					</select>
				</div>
			</div>
			
			
			 <div class="layui-form-item">
				    <label class="layui-form-label">页面显示：</label>
				    <div class="layui-input-block">
				      <select name="source">
				       		<option value="1" <c:if test="${adv.source==1}">selected="selected"</c:if>>首页</option>
							<option value="2" <c:if test="${adv.source==2}">selected="selected"</c:if>>新品页面</option>
							<option value="3" <c:if test="${adv.source==3}">selected="selected"</c:if>>搭配</option>
							<option value="4" <c:if test="${adv.source==4}">selected="selected"</c:if>>顾问</option>
							<option value="5" <c:if test="${adv.source==5}">selected="selected"</c:if>>我们</option>					
				      </select>
				    </div>
			</div> 
		      
		      
		      
			  <div class="layui-form-item">
				    <label class="layui-form-label">是否开启：</label>
				    <div class="layui-input-block">
				      <select name="isEffect" lay-verify="isEffect">
				       		<option value="0" <c:if test="${adv.isEffect==0}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${adv.isEffect==1}">selected="selected"</c:if>>是</option>	
				      </select>
				    </div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">图片</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="uploadNewImg" accept=".jpg,.gif,.png,.jpeg">
    					 <h3>(1:首页顶部广告3:首页秒杀广告，4：首页新品广告)上传这三个选项时，只能上传一张图片</h3> 
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<c:choose>
					<c:when test="${not empty adv.advUrl}">
						<ul class="layer-photos-demo" id="Images" lay-verify="uploadNewImg">
							<li><img layer-src="${adv.advUrl}" src="${adv.advUrl}" alt="" class="layui-upload-img"></li>
						</ul>						
					</c:when>
					<c:otherwise>
						<ul class="layer-photos-demo" id="Images" lay-verify="uploadNewImg"></ul>
					</c:otherwise>
				</c:choose>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" value="${adv.advId}" name="advId"/>
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

layui.use('upload', function(){
	  /* layui.upload({
	    url: '${ctx}/vij/upload/upload.do'
	    ,elem: '#uploadNewImg-collCover' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	$('#Images-collCover').append('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	    	//$('#Images-collCover').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	    	delImages_collCover();
	    }
	  }); */
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#uploadNewImg' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"></li>');
		    	//$('#Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImages();
		    }
		  });
});
layui.use([ 'form', 'layer'],
		function() {
	 	form = layui.form(), layer = layui.layer;
	 
		//自定义验证规则
		form.verify({	
			advName : function(value) {
				if (value.length == "") {
					return "请输入广告名";
				}
			},
			advJumpUrl : function(value) {
				if (value.length == "") {
					return "请输入广告跳转链接";
				}
			},
			advOrder : function(value) {
				if (value.length == "") {
					return "请输入排序";
				}
			},

			uploadNewImg : function(item) {
				if ($("#Images li").length == 0) {
					return "请上传图片";
				}
			},
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			var uploadStr = "",upload_collCover="";
			
			$("#Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 uploadStr += ","+imgSrc;
			})
			$("#Images-collCover li").find("img").each(function(i){
				 var imgSrc_c = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc_c);
				 upload_collCover += ","+imgSrc_c;
			})
			
			var images = uploadStr.substr(1,uploadStr.length-1);
			var images_collCover = upload_collCover.substr(1,upload_collCover.length-1);
			$.ajax({
				type : "post",
				url : "${ctx}/adv/doupdateAdv.do",
				dataType:"json", 
				data : {
					"advName":data.field.advName,
					"advJumpUrl":data.field.advJumpUrl,
					"advOrder":data.field.advOrder,
					"advLocation":data.field.advLocation,
					"source":data.field.source,
					"isEffect":data.field.isEffect,
					/* "collCover":images_collCover, */
					"advUrl":images,
					"advId":data.field.advId
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