<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>查看是否调整设计/报价</title>
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
#Images li{
	float:left;
	width:300px;
	height:300px;
	margin-bottom:30px;
	margin-right:10px;
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
.projectTable{
	margin: 0 auto;
	width: 800px;
	border-top: 1px solid #666;
}
.projectTable tr{	
	border-bottom: 1px solid #666;
}
.projectTable th{
	width:100%;
	min-height: 40px;
	border-left: 1px solid #666;
	border-right: 1px solid #666;
	text-align: left; 
	padding: 10px;
	color: #666;
	font-size: 20px;
	font-weight: 400;
} 

.buttonbtn{
	padding-top:20px;
	padding-right:20px;
	text-align: right;
}
</style>
</head> 
<body> 
	<div style="margin: 15px;"> 
			<c:forEach var="p" items="${projectViewList}" varStatus="status">
				  <blockquote class="layui-elem-quote">${status.index+1}.${p.content}</blockquote>
			</c:forEach> 
		<div class="buttonbtn">
			<button class="layui-btn" lay-submit="" lay-filter="demo1">确定</button>
		</div>
		
	</div> 
</body>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">

layui.use([ 'form', 'layer'],
	function() {
 	var form = layui.form, layer = layui.layer;
	
	//监听提交
	form.on('submit(demo1)', function(data) {
		$.ajax({
			type : "post",
			url : "${ctx}/newProject/updateProjectView.do",
			dataType:"json", 
			data : {
				"projectId":"${project.id}",
				"viewType":"${viewType}"
			},
			success : function(result) {
				if(result.code==0){
					layer.msg("处理成功");
					setTimeout(function () {
						 layer.closeAll("iframe");
	      				//刷新父页面
	      				 parent.location.reload();
					  }, 1000);
				} else{
					layer.msg(result.msg,{time:2000});
				}
			},error:function(){
				layer.msg("处理失败");
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