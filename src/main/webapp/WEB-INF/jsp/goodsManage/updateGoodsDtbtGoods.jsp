<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>设置秒杀数据</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />
<style type="text/css">
.layui-form-select .layui-input{
	width:190px;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">持续时间/小时：</label>
				<div class="layui-input-inline">
					<input type="text"
						name="msTime" id="msTime" value="${good.msTime}"  autocomplete="off"
						class="layui-input" placeholder="请输入秒杀商品持续的时间/小时">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否开启秒杀：</label>
				<div class="layui-input-inline" style="width:190px">
					<select name="isDtbt" style="width:190px">
							<option <c:if test="${good.isDtbt == 0}">selected="selected"</c:if>  value="0">否</option>
							<option <c:if test="${good.isDtbt == 1}">selected="selected"</c:if> value="1">是</option>
					</select>
				</div>
			</div>
			<p style="color:red;padding-bottom:20px;">备注：秒杀开始时间为当前设置时间</p>
			<div class="layui-form-item">
				<div class="layui-input-block">
				    <input type="hidden" value="${good.goodsId}" name="goodsId"/> 
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript">

	layui.use([ 'form', 'layer', 'laydate' ], function() {
		form = layui.form, layer = layui.layer, laydate = layui.laydate;

		
		//监听提交
		form.on('submit(demo1)', function(data) {
			$.ajax({
				type : "post",
				url : "${ctx}/goods/updateDtbtGoods.do",
				dataType : "text",
				data : {
					goodsId : data.field.goodsId,
					msTime : data.field.msTime,
					isDtbt:data.field.isDtbt,
				},
				success : function(result) {
					layer.msg("编辑成功！");
					setTimeout(function(){
				 		parent.location.reload();
					},1000);
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