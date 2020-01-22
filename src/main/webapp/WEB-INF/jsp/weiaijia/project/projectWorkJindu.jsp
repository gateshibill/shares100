<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>施工进度</title>
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
.projectName{
	margin: 0 auto;
	width: 800px;
	margin-bottom:50px;
}
.workStyle{
	width:190px;
} 
.projectTable{
	margin: 0 auto;
	width: 800px;
	border-left: 2px solid #1e8e66;
	margin-bottom:50px;
}
.projectTable tr{	
	height: 40px;
}
.projectTable th{
	width:40%;
	height: 40px;
	text-align: left;
} 
.big-plan{
	height: 40px;
	color: #333;
	font-size: 20px;
	font-weight: 400;
	line-height: 40px;
}
.min-plan{
	height: 40px;
	color: #333;
	font-size: 16px;
	font-weight: 400;
	line-height: 40px;
}
.big-plan-s{
	float:left;
	display: inline-block;
	height: 40px;
	width: 40px;
	border-radius: 20px;
	margin-left: -21px;
	background-color: #1e8e66;
}
.min-plan-s{
	float:left;
	display: inline-block;
	height: 8px;
	width: 8px;
	border-radius: 4px;
	margin-left: -5px;
	margin-top: 15px;
	background-color: #1e8e66;
}
.work-name{
	float:left;
	display: inline-block;
	height: 40px;
	margin-left: 30px;
	line-height: 40px;
}  
.projectTable td{
	text-align: center;
	width:60%;
	min-height: 40px;
	text-align: right; 
	padding: 10px;
	color: #ccc;
	font-size: 18px;
	font-weight: 400;
} 
</style>
</head>
<body>
	<div style="margin: 15px;border:1px solid #ccc;margin: 0 auto;width: 900px;">
		<div class="projectName" style="margin-top: 15px;">
			<p></p>
			<p></p>
		</div>
		<table class="projectTable">
			<c:forEach var="bigp" items="${tList}">
			<tr> 
				<th class="big-plan">
					<span class="big-plan-s"></span><span class="work-name">${bigp.bigPlanName}</span>
				</th>
				<td>${bigp.bigRealEndTimeS}</td>
			</tr>
			<c:forEach var="minp" items="${bigp.planList}">
				<tr> 
					<th class="min-plan">
						<span class="min-plan-s"></span><span class="work-name">${minp.planName}</span>
					</th>
					<td>${minp.realEndTimeS}</td>
				</tr>
			</c:forEach>
			</c:forEach>
		</table>
		
	</div>
</body>

<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
layui.use('laydate', function(){
	  var laydate = layui.laydate;
	  
	  //执行一个laydate实例
	  laydate.render({
	    elem: '#test1' //指定元素
	    ,type: 'datetime'
	  });
	  
	  laydate.render({
	    elem: '#test2' //指定元素
	    ,type: 'datetime'
	  });
	});
layui.use([ 'form', 'layer'],
		function() {
	 	var form = layui.form, layer = layui.layer;
	 
		//自定义验证规则
		form.verify({	
			planName : function(value) {
				if (value.length == 0) {
					return "请输入名称";
				}
			},
			
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			console.log(data.field.planEndTime);
			$.ajax({
				type : "post",
				url : "${ctx}/newProject/addWorkPlan.do",
				dataType:"json", 
				data : {
					"planEndTime1":data.field.planEndTime,
					"planStartTime1":data.field.planStartTime,
					"planTypeId":data.field.planTypeId,
					"planName":data.field.planName,
					"projectId":"${project.id}"
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
		      				//刷新页面
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
	setTimeout(function () {
		 layer.closeAll("iframe");
		 //刷新父页面
		 parent.location.reload();
	  }, 100);
}
</script>
</html>