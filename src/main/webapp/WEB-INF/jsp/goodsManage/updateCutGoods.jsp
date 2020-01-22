<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑砍价商品</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />

</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">商品价格：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 210px;"
						name="price" id="price" value="${cutGoods.price}" lay-verify="job" autocomplete="off"
						class="layui-input" placeholder="商品价格">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">砍价人数：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 210px;"
						name="num" id="num" value="${cutGoods.num}" lay-verify="job" autocomplete="off"
						class="layui-input" placeholder="砍价人数">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">活动有效：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" style="display: inline !important; width: 210px;"
						name="validate" id="validate" value="${cutGoods.validate}" lay-verify="job" autocomplete="off"
						class="layui-input" placeholder="活动有效">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">商品状态：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<c:if test="${cutGoods.status=='1'}">
					</span><input title="有效" name="status" id="status" type="radio" value="1" checked="checked">
					<input title="无效" name="status" id="status" type="radio" value="0">
					</c:if>
					<c:if test="${cutGoods.status=='0'}">
					</span><input title="有效" name="status" id="status" type="radio" value="1">
					<input title="无效" name="status" id="status" type="radio" value="0" checked="checked">
					</c:if>
				</div>
			</div>	
				
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript">

	layui.use([ 'form', 'layer', 'laydate' ], function() {
		form = layui.form, layer = layui.layer, laydate = layui.laydate;

		//自定义验证规则
		form.verify({
			
			price : function(value) {
				if (value.length == "") {
					return "请输入商品价格";
				}
			},
			num : function(value) {
				if (value.length == "") {
					return "请输入人数";
				}
			},
			validate : function(value) {
				if (value.length == "") {
					return "请输入活动有效";
				}
			},
			
		});

		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/wx/activity/updateCutGoodsById.do",
				dataType : "text",
				data : {
					"id" : "${cutGoods.id}",
					"goodsId" : "${cutGoods.goodsId}",
					"price" : $("#price").val(),
					"num" : $("#num").val(),
					"validate" : $("#validate").val(),
					"status" : data.field.status,
				},
				success : function(result) {
					layer.msg("编辑成功！");
				},
				error : function() {
					layer.msg("编辑失败");
				}
			});
			return false;
		});
	});
</script>
</html>