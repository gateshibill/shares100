<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑搭配</title>
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
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">标题：</label>
				<div class="layui-input-block">
					<input type="text" name="collTitle"  autocomplete="off" class="layui-input" lay-verify="collTitle" value="${coll.collTitle}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">描述：</label>
				<div class="layui-input-block">
					<input type="text" name="collDesc"  autocomplete="off" lay-verify="collTitle" class="layui-input" value="${coll.collDesc}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">排序：</label>
				<div class="layui-input-block">
					<input type="text" name="orderStatus"  autocomplete="off"
						class="layui-input" lay-verify="orderStatus" value="${coll.orderStatus}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">类型：</label>
				<div class="layui-input-inline">
					<select name="collType" lay-verify="collType">
						<c:if test="${coll.collType=='1'}">
							<option value="1">主流搭配</option>
							<option value="2">装修风格</option>
						</c:if>
						<c:if test="${coll.collType=='2'}">
							<option value="2">装修风格</option>
							<option value="1">主流搭配</option>
						</c:if>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否推荐：</label>
				<div class="layui-input-inline">
					<select name="isRecommend" lay-verify="isRecommend">
						<c:if test="${coll.isRecommend=='0'}">
							<option value="0">否</option>
							<option value="1">是</option>
						</c:if>
						<c:if test="${coll.isRecommend=='1'}">
							<option value="1">是</option>
							<option value="0">否</option>
						</c:if>
					</select>
				</div>
			</div>	
			<div class="layui-form-item">
				<label class="layui-form-label">封面</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="uploadNewImg-collCover" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="Images-collCover" lay-verify="uploadNewImg_collCover"></ul>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">图片</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="uploadNewImg" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			 <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
				    预览图：
				    <div class="layui-upload-list" id="demo2">
				      	<ul class="layer-photos-demo" id="Images" lay-verify="uploadNewImg"></ul>			      
				    </div>
			 </blockquote>
			<div class="layui-form-item">
				<label class="layui-form-label">详情：</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入介绍" name="collDetail" class="layui-textarea">${coll.collDetail}</textarea>
				</div>
			</div>	
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" value="${coll.id}" name="id"/>
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
//封面回显
var collCoverArr="${coll.collCover}".split(",");
for(var i=0;i<collCoverArr.length;i++){
	$('#Images-collCover').append('<li><img layer-src="'+ collCoverArr[i] +'" src="'+ collCoverArr[i]  +'" alt="'+ collCoverArr[i]  +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
}
//图片集合回显
var imagesArr="${coll.collImages}".split(",");
for(var i=0;i<imagesArr.length;i++){
	console.log(imagesArr[i]);
	$('#Images').append('<li><img layer-src="'+ imagesArr[i] +'" src="'+ imagesArr[i]  +'" alt="'+ imagesArr[i]  +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
}
//封面删除
function delImages_collCover(){
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

layui.use('upload', function(){
	  layui.upload({
	    url: '${ctx}/vij/upload/upload.do'
	    ,elem: '#uploadNewImg-collCover' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	$('#Images-collCover').append('<li><img src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	    	delImages_collCover();
	    }
	  });
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,multiple: true
		    ,elem: '#uploadNewImg' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#Images').append('<li><img  src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImages();
		    }
		  });
});
layui.use([ 'form', 'layer'],
		function() {
	 	form = layui.form(), layer = layui.layer;
	 
		//自定义验证规则
		form.verify({	
			collTitle : function(value) {
				if (value.length == "") {
					return "请输入标题";
				}
			},
			collDesc : function(value) {
				if (value.length == "") {
					return "请输入描述";
				}
			},
			orderStatus : function(value) {
				if (value.length == "") {
					return "请输入排序";
				}
			},
			collDetail : function(value) {
				if (value.length == "") {
					return "请输入详情";
				}
			},
			uploadNewImg_collCover : function(item) {
				if ($("#Images-collCover li").length == 0) {
					return "请上传封面";
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
				url : "${ctx}/coll/doUpdateColl.do",
				dataType:"json", 
				data : {
					"collTitle":data.field.collTitle,
					"collDesc":data.field.collDesc,
					"orderStatus":data.field.orderStatus,
					"collType":data.field.collType,
					"isRecommend":data.field.isRecommend,
					"collDetail":data.field.collDetail,
					"collCover":images_collCover,
					"collImages":images,
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