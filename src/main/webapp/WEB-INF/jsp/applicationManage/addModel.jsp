<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加商品表单</title>
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
#demo2 img{
width:200px !important;
height:150px !important;
margin-right:20px;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label"><span style="color:red">*&nbsp;</span>模板名称：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="modelName" lay-verify="modelName" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label"><span style="color:red">*&nbsp;</span>模板连接：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="modelUrl" lay-verify="modelUrl" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			  <div class="layui-upload">
                  <label class="layui-form-label" style="width: 100px;"><span style="color:red">*&nbsp;</span>模板图片：</label>
                     <button type="button" class="layui-btn" id="test2">上传图片</button> 
<!--                      <span style="color:red;">上传图片之后点击图片即可删除图片</span> -->
                  <div class="layui-upload-list" id="demo2" style="cursor: pointer;"></div>
                 </div>
			<br>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<%-- <script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script> --%>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
var uploadStr = '',num=0;
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#test2'
	    ,url: '${ctx}/layfileUpload/upload.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  num ++;
	    	  if(num > 3){
	    		  layer.msg('最多上传3张图片!',{time:1500});
	    	  } else{
	    		  $('#demo2').append('<img src="'+ result +'" id="'+index+'" onclick="delImg('+index+')" alt="'+ file.name +'" class="layui-upload-img">')
	    	  }
	      });
	    }
	    ,done: function(res){
	       uploadStr += res.data.src+',';
	    }
	  });
	  
	});
	
layui.use([ 'form', 'layedit', 'laypage', 'laydate' ],
		function() {
	form = layui.form,layer=layui.layer, layedit = layui.layedit,laypage = layui.laypage;
			
		//创建一个编辑器
		var editIndex = layedit.build('goodsDetails_edit');
		//自定义验证规则
		form.verify({
			modelName: function(value) {
				if(value.trim() == '') {
					return '模板名称不能为空';
				}
			},
			modelUrl: function(value) {
				if(value.trim() == '') {
					return '模板连接不能为空';
				}
			},
		});
		
		//监听提交
		form.on('submit(demo1)', function(data) {
				uploadStr = uploadStr.substr(0,uploadStr.length-1);
			$.ajax({
				type : "post",
				url : "${ctx}/application/addappModel.do",
				dataType:"json",
				data : {
					"modelName":data.field.modelName,
					"modelCarousel" : uploadStr,
					"modelUrl":data.field.modelUrl
				},
				success : function(result) {
					if(result.code==0){
						layer.closeAll("iframe");
						layer.msg("添加成功");
						setTimeout(function () {
							window.location.href="${ctx}/application/goChooseModel.do";
						  }, 1000);
// 						setTimeout(location.reload(),1000);
					} else{
						alert('模板图片不能为空');
					}
				},error:function(){
					layer.msg("添加失败");
				}
			});
			return false;
		});
	});

function delImg(index){
	$("#"+index+'-0').remove();
}

function goBack(){
	window.history.go(-1);
}
</script>
</html>