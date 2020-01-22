<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>系统信息设置</title>
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
	height:100px;
	margin-bottom:20px;
}
#Images img{
	height:100px;
}
#Images-collCover li{
	float:left;
	height:150px;
	margin-bottom:20px;
}
#Images-collCover img{
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
				<label class="layui-form-label">公司名称：</label>
				<div class="layui-input-block">
					<input type="text" name="companyName"  autocomplete="off" class="layui-input" lay-verify="companyName" value="${systemUpset.companyName}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公司地址：</label>
				<div class="layui-input-block">
					<input type="text" name="companyAddres"  autocomplete="off" class="layui-input" lay-verify="companyAddres" value="${systemUpset.companyAddres}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">客服QQ：</label>
				<div class="layui-input-inline" >
					<input type="text" name="qq" lay-verify="qq" autocomplete="off" class="layui-input" lay-verify="qq" value="${systemUpset.qq}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">顶部欢迎语：</label>
				<div class="layui-input-inline" >
					<input type="text" name="welcomText" lay-verify="welcomText" autocomplete="off" class="layui-input" lay-verify="welcomText" value="${systemUpset.welcomText}" maxlength="20">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">客服电话：</label>
				<div class="layui-input-inline" >
					<input type="text" name="phone" lay-verify="phone" autocomplete="off" class="layui-input" lay-verify="phone" value="${systemUpset.phone}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">系统电话：</label>
				<div class="layui-input-inline" >
					<input type="text" name="sysPhone" lay-verify="sysPhone" autocomplete="off" class="layui-input" lay-verify="sysPhone" value="${systemUpset.sysPhone}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">网站logo：</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="images" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="Images" lay-verify="images">
					
				</ul>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">小程序二维码：</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="small-images" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="small-Images" lay-verify="small_images">
					
				</ul>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公众号二维码：</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="common-images" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="common-Images" lay-verify="common_images">
					
				</ul>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" value="${systemUpset.id}" name="id"/>
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

//logo回显
var imageArr="${systemUpset.companyLogo}".split(",");
if(imageArr != ""){
	for(var i=0;i<imageArr.length;i++){
		$('#Images').append('<li><img layer-src="'+ imageArr[i] +'" src="'+ imageArr[i]  +'" alt="'+ imageArr[i]  +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	}
}
//logo删除
function delImages(){
	var ic_s=$('#Images .img_del');
	for(var i=0;i<ic_s.length;i++){
		$("#Images .img_del").get(i).index=i;
		$("#Images .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#Images li:eq("+i+")").remove();
		})
	}
}
delImages();
//小程序回显
var imageArrSmall="${systemUpset.weChatSmall}".split(",");
if(imageArrSmall != ""){
	for(var i=0;i<imageArrSmall.length;i++){
		$('#small-Images').append('<li><img layer-src="'+ imageArrSmall[i] +'" src="'+ imageArrSmall[i]  +'" alt="'+ imageArrSmall[i]  +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	}
}
//小程序删除
function delImagesSmall(){
	var ic_s=$('#small-Images .img_del');
	for(var i=0;i<ic_s.length;i++){
		$("#small-Images .img_del").get(i).index=i;
		$("#small-Images .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#small-Images li:eq("+i+")").remove();
		})
	}
}
delImagesSmall();
//公众号回显
var imageArrCommon="${systemUpset.weChatCommon}".split(",");
if(imageArrCommon != ""){
	for(var i=0;i<imageArrCommon.length;i++){
		$('#common-Images').append('<li><img layer-src="'+ imageArrCommon[i] +'" src="'+ imageArrCommon[i]  +'" alt="'+ imageArrCommon[i]  +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	}
}
//公众号删除
function delImagesCommon(){
	var ic_s=$('#common-Images .img_del');
	for(var i=0;i<ic_s.length;i++){
		$("#common-Images .img_del").get(i).index=i;
		$("#common-Images .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#common-Images li:eq("+i+")").remove();
		})
	}
}
delImagesCommon();

 layui.use('upload', function(){
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#images' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImages();
		    }
		  });
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#small-images' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#small-Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImagesSmall();
		    }
		  });
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#common-images' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#common-Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImagesCommon();
		    }
		  });
}); 
layui.use([ 'form', 'layer'],
		function() {
	  	form = layui.form(), layer = layui.layer;
	 
		//自定义验证规则
		form.verify({	
			companyName : function(value) {
				if (value.length == "") {
					return "请输入公司名称";
				}
			},
			phone : function(value) {
				if (value.length == "") {
					return "请输入客服电话";
				}
			},
			companyAddres : function(value) {
				if (value.length == "") {
					return "请输入公司地址";
				}
			},
			qq : function(value) {
				if (value.length == "") {
					return "请输入qq";
				}
			},
			welcomText : function(value) {
				if (value.length == "") {
					return "请输入顶部欢迎语";
				}
			},
			sysPhone : function(value) {
				if (value.length == "") {
					return "请输入系统电话";
				}
			},
			images : function(item) {
				if ($("#Images li").length == 0) {
					return "请上传logo";
				}
			},
			small_images : function(item) {
				if ($("#small-Images li").length == 0) {
					return "请上传小程序二维码";
				}
			},
			common_images : function(item) {
				if ($("#common-Images li").length == 0) {
					return "请上传公众号二维码";
				}
			}
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			 var uploadStr = "";
			 var uploadStrSmall = "";
			 var uploadStrCommon = "";
			
			$("#Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 uploadStr += ","+imgSrc;
			})
			$("#small-Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 uploadStrSmall += ","+imgSrc;
			})
			$("#common-Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 uploadStrCommon += ","+imgSrc;
			})
			
			var images = uploadStr.substr(1,uploadStr.length-1);
			var imagesSmall = uploadStrSmall.substr(1,uploadStrSmall.length-1);
			var imagesCommon = uploadStrCommon.substr(1,uploadStrCommon.length-1);
			$.ajax({
				type : "post",
				url : "${ctx}/system/addSystemUpset.do",
				dataType:"json", 
				data : {
					"companyName":data.field.companyName,
					"phone":data.field.phone,
					"companyAddres":data.field.companyAddres,
					"qq":data.field.qq,
					"welcomText":data.field.welcomText,
					"sysPhone":data.field.sysPhone,
					"companyLogo":images,
					"weChatSmall":imagesSmall,
					"weChatCommon":imagesCommon,
					"appId":"${systemUpset.appId}",
					"id":data.field.id
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
					layer.msg("设置失败");
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