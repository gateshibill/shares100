<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加商品图片</title>
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
.img_del{
    cursor: pointer;
} 
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">应用：</label>
				<div class="layui-input-inline">
					<select name="loginPlat" id="loginPlat" lay-verify="loginPlat"
							autocomplete="off" class="layui-input" lay-search="" lay-filter="loginPlat">
						<option value="">请选择应用</option>						
						<c:forEach var="app" items="${commList}">
							<option value="${app.applicationId}">${app.applicationName}</option>
									
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">类型：</label>
				<div class="layui-input-inline">
					<select name="goodTypeId" id="goodTypeId"  lay-verify="goodTypeId"
							autocomplete="off" class="layui-input" >
						 <option value="">请选择类型</option>
						
					</select>
				</div>
			</div>	
				<div class="layui-form-item">
				<label class="layui-form-label">图片名：</label>
				<div class="layui-input-block">
					<input type="text" name="imgTitle"  autocomplete="off" class="layui-input" lay-verify="required">
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label">图片</label>
				<div class="layui-input-block">
    					<input type="file" name="file" class="layui-upload-file" id="imgUrl"  accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
				<div class="layui-form-item">
					<label class="layui-form-label"></label>
					<ul class="layer-photos-demo" id="Images" lay-verify="imgUrl"></ul>
				</div>
				
			<div class="layui-form-item">
				<label class="layui-form-label">图片分类排序：</label>
				<div class="layui-input-inline">
					<input type="number" style=" width:400px;"
						name="imgOrder"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否显示：</label>
				<div class="layui-input-inline">
					<select name="isStatus" lay-verify="isStatus">
						<option value="1" selected="selected">是</option>
						<option value="2">否</option>
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
var imgUrl = '';
layui.use('upload', function(){
	 
	  layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#imgUrl' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	imgUrl = res.msg;
		    	$('#Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
		    }
		  });
});

layui.use([ 'form', 'layer'],
		function() {
	 	form = layui.form(), layer = layui.layer;
	 	//自定义验证规则
		form.verify({	
			imgTitle : function(value) {
				if (value.length == "") {
					return "请输入图片名";
				}
			},
			isStatus : function(value) {
				if (value.length == "") {
					return "是否显示";
				}
			},

		});
	 	
		//分类一级联动
		form.on('select(loginPlat)',function(data){
			 var loginPlat = data.value ;
			 if(!loginPlat){
				 layer.msg("请选择应用");
				 return false;
			 }
			 var typehtml = "";
			 $.ajax({
				 url:'${ctx}/goods/getParentType.do',
				 type:'post',
				 dataType:'json',
				 data:{loginPlat:loginPlat},
				 success:function(res){
					 if(res.code == 0){ //获取数据
						 var object = res.objects;
					 	 for(var i=0;i<object.length;i++){
					 		 typehtml+= '<option value="'+object[i].typeId+'">'+object[i].typeName+'</option>';
					 	 }
					 	 $("#goodTypeId").append(typehtml);
					 	 form.render();
					 }else{
						 layer.msg("该应用暂无分类,请先添加商品分类在添加商品");
						 return;
					 }
				 }
				 ,error:function(res){
					 
				 }
			 })
			 
		})
	 	
		//监听提交
		form.on('submit(demo1)', function(data) {
		
			$.ajax({
				type : "post",
				url : "${ctx}/goodTypeBanner/doaddGoodTypeBanner.do",
				dataType:"json",
				data : {
					"appId":data.field.loginPlat,
					"imgTitle":data.field.imgTitle,
					"isStatus":data.field.isStatus,
					"imgOrder":data.field.imgOrder,
					"imgUrl":imgUrl,
					"goodTypeId":data.field.goodTypeId,
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