<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑职位</title>
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
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: -3px;
	cursor: pointer;
	color: #35CE8D;
}
img{
	width:200px;
	height:200px;
	margin-right:20px
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">	  
		    <div class="layui-form-item">
				<label class="layui-form-label">所属应用：</label>
				<div class="layui-input-inline">
					<input type="number" style="width: 400px;"
						name="appId"  autocomplete="off"
						class="layui-input" lay-verify="appId" value="${position.appId}" readonly="readonly">
				</div>
			</div>
		    
			<div class="layui-form-item">
				<label class="layui-form-label">职位名称：</label>
				<div class="layui-input-inline">
					<input type="text" style="width: 400px;"
						name="positionName"  autocomplete="off"
						class="layui-input" lay-verify="positionName" value="${position.positionName}">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">薪资范围：</label>
				<div class="layui-input-inline" >
					<input type="text" style="width: 400px;"
						name="money"  autocomplete="off"
						class="layui-input" lay-verify="money" value="${position.money}">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">经验：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="experience"  autocomplete="off"
						class="layui-input" lay-verify="experience" value="${position.experience}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">学历：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="education"  autocomplete="off"
						class="layui-input" lay-verify="education" value="${position.education}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公司简称：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="company"  autocomplete="off"
						class="layui-input" value="${position.company}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">HR联系方式：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="hrPhone"  autocomplete="off"
						class="layui-input" lay-verify="hrPhone" value="${position.hrPhone}">
				</div>
			</div>
		   <div class="layui-form-item">
				<label class="layui-form-label">职位描述：</label>
				<div class="layui-input-block" style="padding-left:20px;">
					<textarea class="layui-textarea layui-hide" name="detail"
						 id="detail">${position.detail}</textarea>
				</div>
		 </div>					         
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
				<input type="hidden" value="${position.positionId}" name="positionId"/>
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
layui.use([ 'form', 'layer','layedit'],
		function() {
	 form = layui.form, layer = layui.layer,layedit = layui.layedit;
	    //创建一个编辑器
		var editIndex = layedit.build('detail', {
			tool: ['strong' //加粗
			       ,'italic' //斜体
			       ,'underline' //下划线
			       ,'del' //删除线		       
			       ,'|' //分割线		       
			       ,'left' //左对齐
			       ,'center' //居中对齐
			       ,'right' //右对齐
			       ,'link' //超链接
			       ,'unlink' //清除链接
			       ,'face' ]
			,height: 400
			});
		form.verify({
			appId: function(value) {
				if(value.trim() == '') {
					return '所属应用不能为空！';
				}
			},
			positionName: function(value) {
				if(value.trim() == '') {
					return '职位名称不能为空！';
				}
			},
			money: function(value) {
				if(value.trim() == '') {
					return '薪资不能为空！';
				}
			},
			experience: function(value) {
				if(value.trim() == '') {
					return '经验不能为空！';
				}
			},
			education: function(value) {
				if(value.trim() == '') {
					return '学历不能为空！';
				}
			},
			hrPhone: function(value) {
				if(value.trim() == '') {
					return 'HR联系方式不能为空！';
				}
			}

		});
		
		//监听提交
		form.on('submit(demo1)', function(data) {
			
			$.ajax({
				type : "post",
				url : "${ctx}/position/doEditPosition.do",
				dataType:"json",
				data : {
					"positionName":data.field.positionName,
					"positionId":data.field.positionId,
					"money":data.field.money,
					"experience":data.field.experience,
					"education":data.field.education,			
					"detail":layedit.getContent(editIndex),			
					"hrPhone":data.field.hrPhone,			
					"company":data.field.company,			
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