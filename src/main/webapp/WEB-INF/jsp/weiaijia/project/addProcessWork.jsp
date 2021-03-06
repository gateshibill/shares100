<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加施工流程</title>
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
				<label class="layui-form-label">标题:</label>
					<div class="layui-input-block">
						<input type="text" name="workTitle" autocomplete="off" class="layui-input" lay-verify="workTitle" maxlength="255">
					</div>
			</div>
			
			<div class="layui-from-item">
				<label class="layui-form-label">状态:</label>
				<div class="layui-input-block">
					<select name="workType" lay-verify="workType" style="width:200px;height: 30px">
						<option value="1">施工流程</option>
						<option value="2">工艺标准</option>
					</select>
				</div> 
			</div>
			
			<div class="layui-form-item" style="margin-top:10px;">
				<label class="layui-form-label">排序:</label>
					<div class="layui-input-block">
						<input type="number" name="orderStatus" autocomplete="off" class="layui-input" lay-verify="statusOrder">
					</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" >描述：</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入施工描述" id="workDesc" lay-verify="workDesc" class="layui-textarea" name="workDesc" maxlength="450" id="workDesc"></textarea>
				</div>
			</div>
			<div class="work-images">
				<div class="layui-form-item">
					<label class="layui-form-label" >介绍：</label>
					<div class="layui-input-block">
						<textarea placeholder="请输入施工介绍" id="workDetail" lay-verify="workDetail" class="layui-textarea" name="workDetail" maxlength="450" id="workDetail"></textarea>
					</div>
				</div> 
				 <div class="layui-form-item">
					<label class="layui-form-label">图片：</label>
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
				    施工项：
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
//保存图片
var designArr = [];
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
			workType : function(value) {
				if (value.length == "") {
					return "请输入施工流程/工艺标准";
				}
			},
			workTitle : function(value) {
				if (value.length == "") {
					return "请输入标题";
				}
			},
			workDesc : function(value) {
				if (value.length == "") {
					return "请输入描述";
				}
			},
			orderStatus : function(value) {
				if (value.length == "") {
					return "请输入排序";
				}
			}
		});
	 	
		//监听提交
		form.on('submit(demo1)', function(data) {
			if(designArr.length < 1){
				layer.msg("请添加施工项");
			}else{
				$.ajax({ 
					type : "post",
					url : "${ctx}/project/doAddProjectWorkProcess.do",
					dataType:"json",
					data : {
						"workType":data.field.workType,
						"workTitle":data.field.workTitle,
						"workDesc":data.field.workDesc,
						"orderStatus":data.field.orderStatus,
						"workImagesStr":JSON.stringify(designArr)
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
		console.log(images);
		if ($("#workDetail").val() == "") {
			layer.msg("请输入施工介绍");
		}
		else if($("#Images li").length < 1){
			layer.msg("请上传图片");
		}
		else{
			//组装
			var imagesObj = {name:$("#workDetail").val(),url:images}
			//添加项
			$('#design-xiang').append('<ul class="layer-photos-demo flex-box" id="design-Images" lay-verify=""><p style="padding:10px;">介绍：'+imagesObj.name+'</p></ul>');
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