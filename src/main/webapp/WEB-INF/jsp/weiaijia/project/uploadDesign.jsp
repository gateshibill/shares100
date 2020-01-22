<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>上传设计图效果图</title>
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
<link rel="stylesheet" href="${ctx}/ueditor/themes/default/css/ueditor.css">
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
				<label class="layui-form-label" >设计图：</label>
				<div class="layui-input-block">
				<script id="content" type="text/plain" style="width:92%;height:500px;"></script>
				</div>
			</div>
			
		
		

			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" value="" name="id"/>
					<button class="layui-btn addDesign">立即提交</button>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>	
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var ue = UE.getEditor("content");
var content = '${projectDesign.designIntro}';
ue.addListener("ready", function () {
    ue.setContent(content); //赋值 
});
 
layui.use([ 'form', 'layer'],
		function() {
	 	var form = layui.form, layer = layui.layer;

});

	$(".addDesign").click(function(){
			$.ajax({
				type : "post",
				url : "${ctx}/newProject/addProjectDesign.do",
				dataType:"json", 
				data : {
					"designId":"${projectDesign.designId}",
					"designIntro":ue.getContent(),
					"projectId":"${project.id}",
					"designType": "${designType}"
				}, 
				success : function(result) {
					if(result.code==0){
						layer.msg("操作成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						  }, 1000);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.msg("上传失败");
				}
			});
	})
	

	function goBack(){
		window.history.go(-1);
	}
</script>
</html>