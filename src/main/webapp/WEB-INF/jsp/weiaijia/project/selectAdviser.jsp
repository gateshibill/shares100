<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>选择顾问</title>
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
.adviserbtn{
	width: 100px;
	height: 38px;
}
.layui-form-item .activebtn{
	background-color: #2ba260;
	color: #fff;
} 
</style>
</head>
<body>
	<div style="margin: 15px;"> 
	
		<div class="layui-form">
			<div class="layui-form-item">
				<c:choose>
					<c:when test="${advType == 2}">
						<label class="layui-form-label">设计师列表：</label>		
					</c:when>
					<c:otherwise>
						<label class="layui-form-label">顾问列表：</label>			
					</c:otherwise>
				</c:choose>
				<div class="layui-input-block selectAdviser">
				<c:if test="${not empty guwen}">
					<c:forEach var="adviser" items="${guwenList}">
						 <button class="layui-btn layui-btn-primary layui-btn-radius adviserbtn <c:if test="${guwen.id==adviser.id}">activebtn</c:if>" value="${adviser.id}">${adviser.realName}</button>
					</c:forEach>
				</c:if>
				<c:if test="${empty guwen}">
					<c:forEach var="adviser" items="${guwenList}">
						 <button class="layui-btn layui-btn-primary layui-btn-radius adviserbtn" value="${adviser.id}">${adviser.realName}</button>
					</c:forEach>
				</c:if>
				</div>  
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
	var id;
	var adviserbtns = $(".selectAdviser button");
	for(var i=0;i<adviserbtns.length;i++){
		$(".selectAdviser button").get(i).index=i;
		$(".selectAdviser button:eq("+i+")").click(function(){
			var i=this.index; 
			//移除类名
			$(".selectAdviser button").removeClass("activebtn");
			//添加类名
			$(".selectAdviser button:eq("+i+")").addClass("activebtn");
			id = $(".selectAdviser button:eq("+i+")").val();
		})
	}
	layui.use([ 'form', 'layer'],
			function() {
		 	form = layui.form(), layer = layui.layer;
			//监听提交
			form.on('submit(demo1)', function(data) {
				$.ajax({
					type : "post",
					url : "${ctx}/newProject/selectAdviser.do",
					dataType:"json",
					data : {
						"adviserId" : id,
						"id" : "${project.id}",
						"advType" : "${advType}"
					},
					success : function(result) {
						if(result.code==0){
							layer.msg("选择成功");
							setTimeout(function () {
								 layer.closeAll("iframe");
			      				//刷新父页面
			      				 parent.location.reload();
							  }, 1000);
						} else{
							layer.msg(result.msg,{time:2000});
						}
					},error:function(){
						layer.msg("选择失败");
					}
				});
				return false;
			});
		});
	 
</script>
</html>