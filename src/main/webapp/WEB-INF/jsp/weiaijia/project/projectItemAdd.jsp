<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>验收大项</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet"
	href="${ctx}/plugins/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<style type="text/css">
#Images li {
	float: left;
	width: 200px;
	height: 200px;
	margin-bottom: 20px;
}

.name {
	padding: 5px 10px 0 10px;
	color: #22A95D;
	float: left
}

#Images img {
	width: 200px;
	height: 200px;
}

.img_del {
	cursor: pointer;
}

.btnname {
	float: right;
}

.red {
	color: #ff0000;
}
</style>
</head>
<body>
	
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">验收名称：</label>
				<div class="layui-input-block">
					<input type="text" name="itemName"  autocomplete="off" class="layui-input" lay-verify="itemName">
				</div>
			</div>
			<c:forEach items="${list}" var="pc">
				<c:choose>
					<c:when test="${pc.itemStatus ==0}">
						未进行
					</c:when>
					<c:when test="${pc.itemStatus ==1}">
						验收通过
					</c:when>
					<c:when test="${pc.itemStatus ==1}">
						验收中
					</c:when>
					<c:when test="${pc.itemStatus ==1}">
						验收不通过
					</c:when>
				</c:choose>
			</c:forEach>
			
			
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
	    ,elem: '#images' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	//$('#Images').prepend('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
	    	$('#Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"></li>');
	    	delImages();
	    }
	  });
});
layui.use([ 'form', 'layer'],
		function() {
	 	form = layui.form(), layer = layui.layer;
	 	//自定义验证规则
		form.verify({	
			"itemName" : function(value) {
				if (value.length == "") {
					return "请输入验收名称";
				}
			},
		}); 
		//监听提交
		form.on('submit(demo1)', function(data) {
			var uploadStr = "";
			$("#Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 uploadStr += ","+imgSrc;
			})
			var images = uploadStr.substr(1,uploadStr.length-1);
			$.ajax({
				type : "post",
				url : "${ctx}/project/doAddprojectCheckItem.do",
				dataType:"json",
				data : {
					itemStatus:0,
					"itemName":data.field.itemName,
					"checkId":"${checkId}"
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