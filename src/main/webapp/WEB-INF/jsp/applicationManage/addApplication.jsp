<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加应用表单</title>
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
	
<style type="text/css">
	.layui-input, .layui-textarea {
    display: block;
    width: 200px;
    padding-left: 10px;
}
.red{
	color:red;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>添加应用</legend>
		</fieldset>
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="red">*</span>应用名称：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="applicationName" lay-verify="applicationName" autocomplete="off"
						class="layui-input" placeholder="请输入小程序应用名称">
				</div>
			</div>
			
			<div class="layui-form-item">
					<label class="layui-form-label"><span class="red">*</span>应用模板：</label>
					<div class="layui-input-inline">
						<select name="modelId">
							<option value="">请选择类型</option>
							<c:forEach var="model" items="${midelList}">
								<option value="${model.modelId}">${model.modelName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="red">*</span>应用ID：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="appId" lay-verify="appId" autocomplete="off"
						class="layui-input" value="" placeholder="请输入应用的app_id">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="red">*</span>应用密钥：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="appSecret" lay-verify="appSecret" autocomplete="off"
						class="layui-input" value="" placeholder="请输入应用的app_secret">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">商户号：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="mchid" lay-verify="mchid" autocomplete="off"
						class="layui-input" value="" placeholder="请输入应用的支付商户号">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">支付密钥：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="apiKey" lay-verify="apiKey" autocomplete="off"
						class="layui-input" value="" placeholder="请输入应用的支付密钥">
				</div>
			</div>
			
			<div class="layui-form-item">
					<label class="layui-form-label">应用logo：</label>
					<div class="layui-input-block">
  						<img id="applogo" src="" style="max-width:100px;max-height:100px;">
    					<input type="file" name="file" class="layui-upload-file" id="applogoUpload" accept=".jpg,.gif,.png,.jpeg">
					</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
layui.use('upload', function(){
	  layui.upload({
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#applogoUpload' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	
	      	$("#applogo").attr("src",res.data.src);
	    }
	  });
});
layui.use(
	[ 'form', 'layedit', 'laydate' ],
		function() {
			var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
			
		//自定义验证规则
		form.verify({
			applicationName: function(value) {
				if(value.trim() == '') {
					return '应用名称不能为空';
				}
			},
			appId: function(value) {
				if(value.trim() == '') {
					return '应用appId不能为空';
				}
			},
			appSecret: function(value) {
				if(value.trim() == '') {
					return '应用appSecret不能为空';
				}
			},
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/application/addApplication.do",
				dataType:"json",
				data : {
					"applicationName":data.field.applicationName,
					"modelId":data.field.modelId,
					"appId":data.field.appId,
					"appSecret":data.field.appSecret,
					"mchid":data.field.mchid,
					"apiKey":data.field.apiKey,
					"appLogo":applogo.src,
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加小程序成功");
						setTimeout(function () {
							window.location.href="${ctx}/application/goAppList.do";
						  }, 1000);
// 						setTimeout(location.reload(),1000);
					} else{
						layer.msg(result.msg);
					}
				},error:function(){
					layer.msg("新建应用失败");
				}
			});
			return false;
		});
	});
function goBack(){
// 	window.location.href = '${ctx}/goods/goGoodsList.do';
	window.history.go(-1);
}
</script>
</html>