<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加顾问</title>
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
#Images-collCover li{
	float:left;
	width:300px;
	height:300px;
	margin-bottom:20px;
}
#Images-collCover img{
	width:300px;
	height:300px;
}
.img_del{
    cursor: pointer;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">姓名：</label>
				<div class="layui-input-inline">
					<input type="text" name="realName"  autocomplete="off" class="layui-input" lay-verify="realName">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话号码：</label>
				<div class="layui-input-inline" >
					<input type="text" name="phone" lay-verify="phone" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">职位：</label>
				<div class="layui-input-inline" >
					<input type="text" name="position" lay-verify="position" autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">所在城市：</label>
				<div class="layui-input-inline">
					<input type="text" name="city" lay-verify="city"  autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">排序：</label>
				<div class="layui-input-block">
					<input type="number" name="orderStatus" autocomplete="off"
						class="layui-input" lay-verify="orderStatus">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">负责人：</label>
				<div class="layui-input-inline">
					<select name="advType" lay-verify="advType">
						<option value="1">顾问</option>
						<option value="2">设计师</option>
						<option value="3">工长</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否推荐到首页：</label>
				<div class="layui-input-inline">
					<select name="isRecommend" lay-verify="isRecommend">
						<option value="0" selected="selected">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">设计师图片:</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="imagesUrl" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="ImagesUrl" lay-verify="imagesUrl"></ul>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">PC图片:</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="uploadNewImg-collCover" accept=".jpg,.gif,.png,.jpeg" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="Images-collCover" lay-verify="uploadNewImg_collCover"></ul>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">图片:</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="images" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="Images" lay-verify="images"></ul>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">介绍：</label>
				<div class="layui-input-block">
					<textarea  name="introduce"  autocomplete="off"  class="layui-textarea" lay-verify="introduce" ></textarea>	
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
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
<script type="text/javascript">
function delImages_collCover(){
	//PC封面删除
	var ic_s=$('#Images-collCover .img_del');
	for(var i=0;i<ic_s.length;i++){
		$("#Images-collCover .img_del").get(i).index=i;
		$("#Images-collCover .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#Images-collCover li:eq("+i+")").remove();
		})
	}
}
delImages_collCover();
//图片集合删除
function delImages(){
	var i_s=$("#Images .img_del");
	for(var i=0;i<i_s.length;i++){
		$("#Images .img_del").get(i).index=i;
		$("#Images .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#Images li:eq("+i+")").remove();
		})
	}
}
delImages();

//设计师图片删除
function delImagesUrl(){
	var i_s=$("#ImagesUrl .img_del");
	for(var i=0;i<i_s.length;i++){
		$("#ImagesUrl .img_del").get(i).index=i;
		$("#ImagesUrl .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#ImagesUrl li:eq("+i+")").remove();
		})
	}
}
delImagesUrl();
layui.use('upload', function(){
	layui.upload({
	    url: '${ctx}/vij/upload/upload.do'
	    ,elem: '#uploadNewImg-collCover' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	$('#Images-collCover').html('<li><img src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	    	 delImages_collCover(); 
	    }
	});
	  layui.upload({
	    url: '${ctx}/vij/upload/upload.do'
	    ,elem: '#images' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	//$('#Images').prepend('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	    	$('#Images').append('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	    	delImages();
	    }
	  });
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#imagesUrl' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	//$('#Images').prepend('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
		    	$('#ImagesUrl').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
		    	delImagesUrl();
		   }
		});
});
layui.use([ 'form', 'layer'],
		function() {
	 	form = layui.form(), layer = layui.layer;
	 
	 	//自定义验证规则
		form.verify({	
			realName : function(value) {
				if (value.length == "") {
					return "请输入名字";
				}
			},
			phone : function(value) {
				if (value.length == "") {
					return "请输入电话";
				}
			},
			orderStatus : function(value) {
				if (value.length == "") {
					return "请输入排序";
				}
			},
			city : function(value) {
				if (value.length == "") {
					return "请输入城市";
				}
			},
			position : function(value) {
				if (value.length == "") {
					return "请输入职位";
				}
			},
			introduce : function(value) {
				if (value.length == "") {
					return "请输入介绍";
				}
			},
			uploadNewImg_collCover : function(item) {
				if ($("#Images-collCover li").length == 0) {
					return "请上传PC图片";
				}
			},
			images : function(item) {
				if ($("#Images li").length == 0) {
					return "请上传图片";
				}
			},
			imagesUrl : function(item) {
				if ($("#ImagesUrl li").length == 0) {
					return "请上传设计师图片";
				}
			},
		});
	 	
		//监听提交
		form.on('submit(demo1)', function(data) {
			var uploadStr = "", upload_collCover="",upload_str = "";
			$("#Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 uploadStr += ","+imgSrc;
			})
			$("#ImagesUrl li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 upload_str += ","+imgSrc;
			})
			$("#Images-collCover li").find("img").each(function(i){
				 var imgSrc_c = $(this).attr("src");
				  console.log("imgSrc-"+i+":"+imgSrc_c);  
				  upload_collCover+=","+imgSrc_c; 
			})
			var images = uploadStr.substr(1,uploadStr.length-1);
			var imagesUrl = upload_str.substr(1,upload_str.length-1);
			var images_collCover = upload_collCover.substr(1,upload_collCover.length-1);
			$.ajax({
				type : "post",
				url : "${ctx}/adviser/doAddAdviser.do",
				dataType:"json",
				data : {
					"realName":data.field.realName,
					"position":data.field.position,
					"phone":data.field.phone,
					"city":data.field.city,
					"introduce":data.field.introduce,
					"advType":data.field.advType,
					"isRecommend":data.field.isRecommend,
					"orderStatus":data.field.orderStatus,
					"appImageUrl":images_collCover,
					"advImages":imagesUrl,
					"images":images
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
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
function goBack(){
	window.history.go(-1);
}
</script>
</html>