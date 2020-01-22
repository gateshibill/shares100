<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>查看业主意向装修风格</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${ctx}/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
</head>
<body>	
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
		<table class="layui-table">
		  <colgroup>
			    <col width="50%">
			    <col width="50%">
			    <col>
			  </colgroup>
			<thead>
				<tr>
					<th colspan="12" style="text-align: center; font-weight: bold;" >
						<h2>业主意向装修风格</h2>
					</th>
				</tr>
			</thead>
			<tbody class="table-body">
			
			</tbody>
		</table>
		<div class="layui-form-item" style="text-align:center">
				<div class="layui-input-block" style="margin-left:0">
				<input type="hidden" name="styleId" value="${style.id}" />
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
				</div>
			</div>	
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>	
<script type="text/javascript">
var designArr = '${style.answerList}';
if(designArr){
	 designArr = JSON.parse(designArr);
	 if(designArr.length>0){
		for(var i = 0; i < designArr.length; i++){
			//遍历 
			$('.table-body').append('<tr><td>'+designArr[i].question+'</td><td>'+designArr[i].answer+'</td></tr>');
		}
	}
}	
layui.use([ 'form', 'layer'],
			function() {
	var form = layui.form, layer = layui.layer;
//监听提交
			form.on('submit(demo1)', function(data) {
				$.ajax({
					type : "post",
					url : "${ctx}/project/dogetChooseStyle.do",
					dataType:"json",
					data : {
						isDeal:1,
						id:"${style.id}",
						projectId:'${style.projectId}'
					},
					success : function(result) {
						if(result.code==0){
							layer.msg("确认成功");
							setTimeout(function () {
								 layer.closeAll("iframe");
			      				//刷新父页面
			      				 parent.location.reload();
							  }, 1000);
						} else{
							layer.msg(result.msg,{time:2000});
						}
					},error:function(){
						layer.msg("失败");
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