<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>修改商品</title>
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

#Images img {
	width: 200px;
	height: 200px;
}

.img_del {
	cursor: pointer;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			

			<div class="layui-form-item">
				<label class="layui-form-label">类型：</label>
				<div class="layui-input-inline">
					<select name="goodTypeId" id="goodTypeId" lay-verify="goodTypeId"
						autocomplete="off" class="layui-input" lay-search="">
						<c:forEach var="c" items="${lists}">
							<option value="${c.typeId}" <c:if test="${c.typeId == banner.goodTypeId}"></c:if>>${c.typeName}</option>
						</c:forEach>
					</select>
				</div>
			</div>


			<div class="layui-form-item">
				<label class="layui-form-label">图片名：</label>
				<div class="layui-input-block">
					<input type="text" name="imgTitle" autocomplete="off"
						class="layui-input" lay-verify="imgTitle"
						value="${banner.imgTitle}">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">排序：</label>
				<div class="layui-input-block">
					<input type="text" name="imgOrder" autocomplete="off"
						class="layui-input" lay-verify="imgOrder"
						value="${banner.imgOrder}">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">是否显示：</label>
				<div class="layui-input-inline">
					<select name="isStatus" lay-verify="isStatus">
						<c:if test="${banner.isStatus==1}">
							<option value="2">否</option>
							<option value="1" selected="selected">是</option>
						</c:if>
						<c:if test="${banner.isStatus== 2}">
							<option value="1">是</option>
							<option value="2" selected="selected">否</option>
						</c:if>
					</select>
				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">图片</label>
				<div class="layui-input-block">
					<input type="file" name="file" class="layui-upload-file"
						id="imgUrl" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<ul class="layer-photos-demo" id="Images" lay-verify="imgUrl">
					<c:if test="${not empty  banner.imgUrl}">
						<li><img layer-src="${banner.imgUrl}" src="${banner.imgUrl}"  class="layui-upload-img"></li>
					</c:if>
				</ul>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<input type="hidden" value="${banner.id}" name="id" />
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
    var imgUrl = "";
	layui
			.use(
					'upload',
					function() {
				
						layui
								.upload({
									url : '${ctx}/vij/upload/upload.do',
									elem : '#imgUrl' //指定原始元素，默认直接查找class="layui-upload-file"
									,
									method : 'post', //上传接口的http类型
									dataType : "json",
									success : function(res) {
										imgUrl = res.msg;
										$('#Images')
												.html(
														'<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
									}
								});
					});

	layui.use([ 'form', 'layer' ], function() {
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
	//监听提交
		form.on('submit(demo1)', function(data) {

			$.ajax({
				type : "post",
				url : "${ctx}/goodTypeBanner/doupGoodTypeBanner.do",
				dataType : "json",
				data : {
					"id" : data.field.id,
					"imgTitle" : data.field.imgTitle,
					"isStatus" : data.field.isStatus,
					"imgOrder" : data.field.imgOrder,
					"imgUrl" : imgUrl
				},
				success : function(result) {
					if (result.code == 0) {
						layer.msg("修改成功");
						setTimeout(function() {
							layer.closeAll("iframe");
							//刷新父页面
							parent.location.reload();
						}, 1000);
					} else {
						layer.msg(result.msg, {
							time : 2000
						});
					}
				},
				error : function() {
					layer.msg("修改失败");
				}
			});
			return false;
		});
	});

	function goBack() {
		window.history.go(-1);
	}
</script>
</html>