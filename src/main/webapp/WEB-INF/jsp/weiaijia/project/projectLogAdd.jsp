<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加施工日志</title>
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
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">标题：</label>
				<div class="layui-input-inline">
					<input type="text" name="title"  autocomplete="off" class="layui-input" lay-verify="title">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">备注：</label>
				<div class="layui-input-inline">
					<input type="text" name="remark"  autocomplete="off" class="layui-input" lay-verify="remark">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">图片</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="uploadNewImg"  accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="Images"></ul>
			</div>
				<div class="layui-form-item">
				<label class="layui-form-label">状态：</label>
				<div class="layui-input-inline">
					<select name="status">
						<option value="1">选择顾问</option>
						<option value="2">预约量房</option>
						<option value="3">选择风格</option>
						<option value="4">预交订金</option>
						<option value="5">确定设计</option>
						<option value="6">确定报价</option>
						<option value="7">装修施工</option>
						<option value="8">竣工验收</option>
						<option value="9">客户评价</option>
						<option value="10">项目结束</option>
						
					</select>
				</div>
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
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
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
layui.use('upload', function(){
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#uploadNewImg' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#Images').append('<li><img src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImages();
		    }
		  });
});

layui.use([ 'form', 'layer'],
		function() {
	 	form = layui.form(), layer = layui.layer;
	 	
	 	//自定义验证规则
		form.verify({	
			title : function(value) {
				if (value.length == "") {
					return "请输入标题";
				}
			},
			remark : function(value) {
				if (value.length == "") {
					return "请输入备注";
				}
			},
			status : function(value) {
				if (value.length == "") {
					return "请输入状态";
				}
			},
			detail : function(item) {
				if ($("#Images li").length == 0) {
					return "请上传图片";
				}
			},
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
				url : "${ctx}/project/doAddProjectLog.do",
				dataType:"json",
				data : {
					/* "userId":"${userId}", */
					"projectId":"${projectId}",
					"title":data.field.title,
					"remark":data.field.remark,
					"status":data.field.status,
					"detail":images
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