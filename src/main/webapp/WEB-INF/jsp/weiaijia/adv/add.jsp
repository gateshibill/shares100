<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加图片</title>
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
				    <label class="layui-form-label">所属应用</label>
				    <div class="layui-input-block">
				    <select name="appId" id="appId" lay-verify="appId" autocomplete="off"
						class="layui-input" lay-search="">
							<c:forEach var="c" items="${appList}">
								<option value="${c.applicationId}">${c.applicationName}</option>
							</c:forEach>
						</select>
				    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">广告名：</label>
				<div class="layui-input-block">
					<input type="text" name="advName"  autocomplete="off" class="layui-input" lay-verify="advName">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">广告跳转链接：</label>
				<div class="layui-input-block">
					<input type="text" name="advJumpUrl"  autocomplete="off" lay-verify="advJumpUrl" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">排序：</label>
				<div class="layui-input-block">
					<input type="number" name="advOrder"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">广告位置：</label>
				<div class="layui-input-block">
					<select name="advLocation" lay-verify="advLocation">
						<option value="1">首页顶部广告</option>
<!-- 						<option value="2">首页轮播图</option>
 -->						<option value="3">首页秒杀广告</option>
						<option value="4">首页新品广告</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">页面位置：</label>
				<div class="layui-input-block">
					<select name="source" lay-verify="source">
						<option value="1">首页</option>
						<option value="2">新品页面</option>
						<option value="3">搭配</option>
						<option value="4">顾问</option>
						<option value="5">我们</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否开启：</label>
				<div class="layui-input-block">
					<select name="isEffect" lay-verify="isEffect">
						<option value="0">不开启</option>
						<option value="1">开启</option>
					</select>
				</div>
			</div>	
			<div class="layui-form-item">
				<label class="layui-form-label">图片</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="advUrl"  accept=".jpg,.gif,.png,.jpeg">
						<h3>(1:首页顶部广告3:首页秒杀广告，4：首页新品广告)上传这三个选项时，只能上传一张图片</h3>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="Images" lay-verify="advUrl"></ul>
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
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">

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
		    ,elem: '#advUrl' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
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
					return "请输入图片名";
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
			advLocation : function(value) {
				if (value.length == "") {
					return "请输入广告位置";
				}
			},
			source : function(value) {
				if (value.length == "") {
					return "请输入页面位置";
				}
			},
			isEffect : function(value) {
				if (value.length == "") {
					return "请输入是否开启";
				}
			},
			
			advUrl : function(item) {
				if ($("#Images li").length == 0) {
					return "请上传图片";
				}
			}
		});
	 	
		//监听提交
		form.on('submit(demo1)', function(data) {
			var uploadStr = "";
			
			$("#Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 uploadStr += ","+imgSrc;
			})
			var images = uploadStr.substr(1,uploadStr.length-1);
			$.ajax({
				type : "post",
				url : "${ctx}/adv/doaddAdv.do",
				dataType:"json",
				data : {
					"advName":data.field.advName,
					"advJumpUrl":data.field.advJumpUrl,
					"advOrder":data.field.advOrder,
					"advLocation":data.field.advLocation,
					"source":data.field.source,
					"isEffect":data.field.isEffect,
					"advUrl":images,
					"appId":data.field.appId
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