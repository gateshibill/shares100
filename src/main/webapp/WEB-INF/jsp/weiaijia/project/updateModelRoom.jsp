<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑样板间</title>
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
.work-images{
	border:1px solid #999;
	margin-top:20px;
	padding:20px;
}
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
#softImages li{
	float:left;
	width:300px;
	height:300px;
	margin-bottom:20px;
}
#softImages img{
	width:300px;
	height:300px;
}
#hardImages li{
	float:left;
	width:300px;
	height:300px;
	margin-bottom:20px;
}
#hardImages img{ 
	width:300px;
	height:300px;
}
.img_del{
    cursor: pointer;
}
.flex-box::before, .flex-box::after {
    display: block;
    clear: both;
    height: 0;
    content: "";
}
#design-Images{
	border:1px solid #999;
	margin-top: 10px;
}
#design-Images li{
	width: 300px;
	height: 300px;
	float: left;
	margin-left: 10px;
}
#design-Images li img{
	width: 300px;
	height: 300px;
}
.xiang_del{
	cursor: pointer;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">板间标题:</label>
					<div class="layui-input-block">
						<input type="text" name="modelTitle" autocomplete="off" class="layui-input" lay-verify="modelTitle" maxlength="255" placeholder="请输入标题" value="${room.modelTitle}">
					</div>
			</div>
			<div class="layui-from-item">
				<label class="layui-form-label">板间类型:</label>
				<div class="layui-input-block">
					<select name="typeName" lay-verify="typeName" style="width:200px;height: 30px">
						
						<option value="中式" <c:if test="${room.typeName == '中式'}">selected="selected"</c:if>>中式</option>
						<option value="欧式" <c:if test="${room.typeName == '欧式'}">selected="selected"</c:if>>欧式</option>
						<option value="新古典" <c:if test="${room.typeName == '新古典'}">selected="selected"</c:if>>新古典</option>
						<option value="美式" <c:if test="${room.typeName == '美式'}">selected="selected"</c:if>>美式</option>
						<option value="现代简约" <c:if test="${room.typeName == '现代简约'}">selected="selected"</c:if>>现代简约</option>
						<option value="北欧" <c:if test="${room.typeName == '北欧'}">selected="selected"</c:if>>北欧</option>
						<option value="法式" <c:if test="${room.typeName == '法式'}">selected="selected"</c:if>>法式</option>
						<option value="其他" <c:if test="${room.typeName == '其他'}">selected="selected"</c:if>>其他</option>
					</select>
				</div>  
			</div>
			<div class="layui-from-item" style="margin-top:15px;">
				<label class="layui-form-label">是否有效:</label>
				<div class="layui-input-block">
					<select name="isEffect" lay-verify="isEffect" style="width:200px;height: 30px">
						<option value="0" <c:if test="${room.isEffect == 0}">selected="selected"</c:if>>否</option>
						<option value="1" <c:if test="${room.isEffect == 1}">selected="selected"</c:if>>是</option>
					</select>
				</div>  
			</div>
			<div class="layui-form-item" style="margin-top:15px;">
				<label class="layui-form-label">关键词:</label>
				<div class="layui-input-block allKeyWord">
					<input type="text" name="keyWord" autocomplete="off" class="layui-input keyWord1" lay-verify="keyWord" maxlength="255" placeholder="请输入关键词1">
					<input type="text" name="keyWord" autocomplete="off" class="layui-input keyWord2" lay-verify="keyWord" maxlength="255" placeholder="请输入关键词2">
					<input type="text" name="keyWord" autocomplete="off" class="layui-input keyWord3" lay-verify="keyWord" maxlength="255" placeholder="请输入关键词3">
					<input type="text" name="keyWord" autocomplete="off" class="layui-input keyWord4" lay-verify="keyWord" maxlength="255" placeholder="请输入关键词4">
				</div>
			</div>
			<div class="layui-form-item" style="margin-top:15px;">
				<label class="layui-form-label">排序:</label>
					<div class="layui-input-block">
						<input type="number" name="orderStatus" autocomplete="off" class="layui-input" lay-verify="orderStatus" placeholder="请输入排序" value="${room.orderStatus}">
					</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">描述：</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入施工描述" id="modelDesc" lay-verify="modelDesc" class="layui-textarea" name="modelDesc" maxlength="450">${room.modelDesc}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">软装图片：</label>
				<div class="layui-input-block">
    				<button type="button" class="layui-btn" id="uploadSoftImg">图片上传</button> 
				</div>
			</div> 
			 <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
				    预览图：
				    <div class="layui-upload-list" id="demo2">
				      	<ul class="layer-photos-demo flex-box" id="softImages" lay-verify="uploadSoftImg"></ul>			      
				    </div>
			 </blockquote>
			 <div class="layui-form-item">
				<label class="layui-form-label">硬装图片：</label>
				<div class="layui-input-block">
    				<button type="button" class="layui-btn" id="uploadHardImg">图片上传</button> 
				</div>
			</div> 
			 <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
				    预览图：
				    <div class="layui-upload-list" id="demo2">
				      	<ul class="layer-photos-demo flex-box" id="hardImages" lay-verify="uploadHardImg"></ul>			      
				    </div>
			 </blockquote>
			<div class="work-images">
				<div class="layui-form-item">
					<label class="layui-form-label">实际装修图片名称:</label>
						<div class="layui-input-block">
							<input type="text" name="imageName" id="imageName" autocomplete="off" class="layui-input" lay-verify="imageName" maxlength="255">
						</div>
				</div> 
				 <div class="layui-form-item">
					<label class="layui-form-label">实际装修图片：</label>
					<div class="layui-input-block">
	    					<button type="button" class="layui-btn" id="uploadNewImg">图片上传</button> 
					</div>
				</div> 
				 <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
					    预览图：
					    <div class="layui-upload-list" id="demo2">
					      	<ul class="layer-photos-demo flex-box" id="Images" lay-verify="uploadNewImg"></ul>			      
					    </div>
				 </blockquote>
				 <div class="layui-form-item">
					<label class="layui-form-label"></label>
					<div class="layui-input-block">
						<button class="layui-btn addWork">继续添加</button>
					</div>
				</div>
			</div>
			<div>
				<blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
				    实际装修项：
				    <div class="layui-upload-list" id="design-xiang">
				      				      
				    </div>
			 	</blockquote>
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

<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
//保存图片/项回显
var designArr = [];
var designArr_1 = '${room.homeImages}';
if(designArr_1){
	 designArr_1 = JSON.parse(designArr_1);
	 designArr = designArr_1;
	 if(designArr.length>0){
		for(var i = 0; i < designArr.length; i++){
			//添加项
			$('#design-xiang').append('<ul class="layer-photos-demo flex-box" id="design-Images" lay-verify=""><p style="padding:10px;">实际装修图片名称：'+designArr[i].name+'</p></ul>');
			//遍历 
			var imagesArr=designArr[i].url.split(",");
			for (var j = 0; j < imagesArr.length; j++) {
				$('#design-xiang ul:last').append('<li><img  src="'+ imagesArr[j] +'" alt="'+ imagesArr[j] +'" class="layui-upload-img"></li>');
			}
			$('#design-xiang ul:last').append('<div class="operate"><i class="layui-icon xiang_del">&#xe640;</i></div>');
		}
	}
}
//软装回显
var softImagesArr="${room.softImages}".split(",");
if(softImagesArr != ""){
	for(var i=0;i<softImagesArr.length;i++){
		$('#softImages').append('<li><img layer-src="'+ softImagesArr[i] +'" src="'+ softImagesArr[i]  +'" alt="'+ softImagesArr[i]  +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	}
}

//硬装回显
var hardImagesArr="${room.hardImages}".split(",");
if(hardImagesArr != ""){
	for(var i=0;i<hardImagesArr.length;i++){
		$('#hardImages').append('<li><img layer-src="'+ hardImagesArr[i] +'" src="'+ hardImagesArr[i]  +'" alt="'+ hardImagesArr[i]  +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	}
}
//关键字回显
var keyWordArr="${room.keyWord}".split(",");
if(keyWordArr != ""){
	for(var i=0;i<keyWordArr.length;i++){
		$(".allKeyWord input:eq("+i+")").val(keyWordArr[i]);
	}
} 
//软装图片集合删除
function delsoftImages(){
	var i_s=$("#softImages .img_del");
	for(var i=0;i<i_s.length;i++){
		$("#softImages .img_del").get(i).index=i;
		$("#softImages .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#softImages li:eq("+i+")").remove();
		})
	}
}
delsoftImages();
//硬装图片集合删除
function delhardImages(){
	var i_s=$("#hardImages .img_del");
	for(var i=0;i<i_s.length;i++){
		$("#hardImages .img_del").get(i).index=i;
		$("#hardImages .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#hardImages li:eq("+i+")").remove();
		})
	}
}
delhardImages();
//实际装修图片集合删除
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
//项删除
function xiang_del(){
	var i_s=$("#design-xiang .xiang_del");
	for(var i=0;i<i_s.length;i++){
		$("#design-xiang .xiang_del").get(i).index=i;
		$("#design-xiang .xiang_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#design-xiang ul:eq("+i+")").remove();
			designArr.splice(i,1);
		})
	}
}
xiang_del();
layui.use('upload', function(){
	  var upload = layui.upload;
	  upload.render({
		    url: '${ctx}/vij/uploadProject/uploadProject.do'
		    ,multiple: true
		    ,accept:'images'
		    ,elem: '#uploadSoftImg' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,before: function(obj){}
		    ,done: function(res){
		    	$('#softImages').append('<li><img  src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
		    	delsoftImages();
		    }
		});
		upload.render({
		    url: '${ctx}/vij/uploadProject/uploadProject.do'
		    ,multiple: true
		    ,accept:'images'
		    ,elem: '#uploadHardImg' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,before: function(obj){}
		    ,done: function(res){
		    	$('#hardImages').append('<li><img  src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
		    	delhardImages();
		    }
		});
		upload.render({
		    url: '${ctx}/vij/uploadProject/uploadProject.do'
		    ,multiple: true
		    ,accept:'images'
		    ,elem: '#uploadNewImg' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,before: function(obj){}
		    ,done: function(res){
		    	$('#Images').append('<li><img  src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImages();
		    }
		});  
});

layui.use([ 'form', 'layer'],
		function() {
	var form = layui.form, layer = layui.layer;
	 	//自定义验证规则
		form.verify({	
			modelTitle : function(value) {
				if (value.length == "") {
					return "请输入样板间标题";
				}
			},
			keyWord : function(value) {
				if (value.length == "") {
					return "请输入关键词";
				}
			},
			modelDesc : function(value) {
				if (value.length == "") {
					return "请输入描述";
				}
			},
			orderStatus : function(value) {
				if (value.length == "") {
					return "请输入排序";
				}
			},
			uploadSoftImg : function(value) {
				if ($('#softImages li').length < 1) {
					return "请上传软装图片";
				}
			},
			uploadHardImg : function(value) {
				if ($('#hardImages li').length < 1) {
					return "请上传硬装图片";
				}
			}
			
		});
	 	
		//监听提交
		form.on('submit(demo1)', function(data) {
			var softUploadStr = "";
			var hardUploadStr = "";
			$("#softImages li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 softUploadStr += ","+imgSrc;
			})
			$("#hardImages li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 hardUploadStr += ","+imgSrc;
			})
			var softImages = softUploadStr.substr(1,softUploadStr.length-1);
			var hardImages = hardUploadStr.substr(1,hardUploadStr.length-1);

			//关键词
			var keyWordStr = $(".keyWord1").val()+","+$(".keyWord2").val()+","+$(".keyWord3").val()+","+$(".keyWord4").val(); 
			if(designArr.length < 1){
				layer.msg("请添加实际装修项");
			}else{
				$.ajax({
					type : "post",
					url : "${ctx}/newProject/updateModelRoom.do",
					dataType:"json",
					data : { 
						"id":"${room.id}",
						"modelTitle":data.field.modelTitle,
						"isEffect":data.field.isEffect,
						"typeName":data.field.typeName,
						"modelDesc":data.field.modelDesc,
						"orderStatus":data.field.orderStatus,
						"homeImages":JSON.stringify(designArr),
						"softImages":softImages,
						"hardImages":hardImages,
						"keyWord":keyWordStr
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
						layer.msg("添加失败");
					}
				});
			}
			
			return false;
		});
	});

	$(".addWork").click(function(){
		var uploadStr = "";
		$("#Images li").find("img").each(function(i){
			 var imgSrc = $(this).attr("src");
			 console.log("imgSrc-"+i+":"+imgSrc);
			 uploadStr += ","+imgSrc;
		})
		var images = uploadStr.substr(1,uploadStr.length-1);
		if ($("#imageName").val() == "") {
			layer.msg("请输入实际装修图片名称");
		}
		else if($("#Images li").length < 1){
			layer.msg("请上传实际装修图片");
		}
		else{
			//组装
			var imagesObj = {name:$("#imageName").val(),url:images}
			//添加项
			$('#design-xiang').append('<ul class="layer-photos-demo flex-box" id="design-Images" lay-verify=""><p style="padding:10px;">实际装修图片名称:'+imagesObj.name+'</p></ul>');
			//遍历
			var imagesArr=imagesObj.url.split(",");
			for (var i = 0; i < imagesArr.length; i++) {
				$('#design-xiang ul:last').append('<li><img  src="'+ imagesArr[i] +'" alt="'+ imagesArr[i] +'" class="layui-upload-img"></li>');
			}
			$('#design-xiang ul:last').append('<div class="operate"><i class="layui-icon xiang_del">&#xe640;</i></div>');
			//加入数组
			designArr.push(imagesObj);
			//清空数据
			$("#workDetail").val("");
			$("#Images li").remove();
			//调用删除
			xiang_del();
		}		
	}) 


function goBack(){
	window.history.go(-1);
}
</script>
</html>