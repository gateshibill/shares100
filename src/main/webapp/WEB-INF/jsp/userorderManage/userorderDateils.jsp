<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>表单</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
</head>

<body>
	<div style="margin: 15px;">
		<!-- <fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>订单详情</legend>
		</fieldset> -->
		<form class="layui-form" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">订单编号：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${order.orderID}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">商品名称：</label>
				<div class="layui-input-block">
					<label class="detail-label-value" id="goodsName"></label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">订单状态：</label>
				<div class="layui-input-inline" style="width: 210px;">
					<select name="orderStatus" id="orderStatus">
					<option value="5"
						<c:choose>
									<c:when test="${order.orderStatus==5}">selected="selected"</c:when>
									</c:choose>>已付款</option>
						<option value="7"
							<c:choose>
									<c:when test="${order.orderStatus==7}">selected="selected"</c:when>
									</c:choose>>待发货</option>
						<option value="10"
							<c:choose>
									<c:when test="${order.orderStatus==10}">selected="selected"</c:when>
									</c:choose>>已发货</option>
						<option value="14"
							<c:choose>
									<c:when test="${order.orderStatus==14}">selected="selected"</c:when>
									</c:choose>>确认收货</option>
						<option value="16"
							<c:choose>
									<c:when test="${order.orderStatus==16}">selected="selected"</c:when>
									</c:choose>>待评价</option>
					    <option value="20"
							<c:choose>
									<c:when test="${order.orderStatus==20}">selected="selected"</c:when>
									</c:choose>>交易完成</option>
						<option value="-2"
							<c:choose>
									<c:when test="${order.orderStatus==-2}">selected="selected"</c:when>
									</c:choose>>退款中</option>
									<option value="-3"
							<c:choose>
									<c:when test="${order.orderStatus==-3}">selected="selected"</c:when>
									</c:choose>>退货中</option>
									<option value="-4"
							<c:choose>
									<c:when test="${order.orderStatus==-4}">selected="selected"</c:when>
									</c:choose>>已删除</option>
					</select>
				</div>
			</div>
			
				<div class="layui-form-item">
				<label class="layui-form-label">是否支付：</label>
				<div class="layui-input-inline" style="width: 210px;">
					<select name="payStatus" id="payStatus">
					<c:if test="${order.payStatus == 1}">
					 <option value="1" selected="selected">是</option>
					 <option value="0">否</option>
					</c:if>
					<c:if test="${order.payStatus != 1}">
					 <option value="1">是</option>
					 <option value="0" selected="selected">否</option>
					</c:if>	
					</select>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">物流类型：</label>
				<div class="layui-input-inline" style="width: 210px;">
					<select name="logisticsTypes" id="logisticsTypes">
					<%-- <option value="1"
						<c:choose>
									<c:when test="${order.logisticsTypes==1}">selected="selected"</c:when>
									</c:choose>>韵达</option>
						<option value="2"
							<c:choose>
									<c:when test="${order.logisticsTypes==2}">selected="selected"</c:when>
									</c:choose>>顺丰</option>
						<option value="3"
							<c:choose>
									<c:when test="${order.logisticsTypes==3}">selected="selected"</c:when>
									</c:choose>>申通</option>
						<option value="4"
							<c:choose>
									<c:when test="${order.logisticsTypes==4}">selected="selected"</c:when>
									</c:choose>>圆通</option> --%>
						<c:forEach var="c" items="${expresslist}">
							 <option value="${c.expressId}" <c:if test="${order.logisticsTypes==c.expressId}">selected="selected"</c:if> >${c.expressName}</option>
						</c:forEach>			
					</select>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">收货人名称：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text"
						style="display: inline !important; width: 200px;" name="consignee"
						lay-verify="goodsName" autocomplete="off" class="layui-input"
						value="${order.consignee}">
					</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话号码：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text"
						style="display: inline !important; width: 200px;" name="phone"
						lay-verify="phone" autocomplete="off" class="layui-input"
						value="${order.phone}">
					</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">物流单号：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text"
						style="display: inline !important; width: 200px;" name="logisticsOrder"
						lay-verify="" autocomplete="off" class="layui-input"
						value="${order.logisticsOrder}">
					</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">订单备注：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text"
						style="display: inline !important; width: 200px;" name="remarks"
						lay-verify="" autocomplete="off" class="layui-input"
						value="${order.remarks}">
					</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">收货地址：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text"
						style="display: inline !important; width:300px;" name="address"
						lay-verify="goodsName" autocomplete="off" class="layui-input"
						value="${order.address}">
					</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">价格：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" min="0"
						style="display: inline !important; width: 200px;" name="realFee"
						lay-verify="firstCost" autocomplete="off" class="layui-input"
						value="${order.realFee}">&nbsp;<span>元</span>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">数量：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number" min="0"
						style="display: inline !important; width: 200px;" name="number"
						lay-verify="number" autocomplete="off" class="layui-input"
						value="${order.number}">&nbsp;<span>件</span>
				</div>
			</div>	
			<div class="layui-form-item">
				<label class="layui-form-label">下单时间：</label>
				<div class="layui-input-block">
					<label style="line-height: 38px;"><fmt:formatDate
							value="${order.createTime}" type="both" /></label>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
<!-- 					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
 -->				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
var orderID='${order.orderID}';
var orderObjects = '${order.objects}';
var reg=/<[^>]+>/gim;
orderObjects = orderObjects.replace(reg,""); 
orderObjects = JSON.parse(orderObjects);

var strobject = "";
for(var i = 0;i<orderObjects.length;i++){
	strobject += ' + '+orderObjects[i].goodsName;
}
 strobject = strobject.substr(2,strobject.length-1);
 $("#goodsName").text(strobject); 
  layui.use([ 'form', 'layedit', 'laydate' ],
					function() {
						var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
						layedit.set({
							uploadImage : {
								url : '${ctx}/layfileUpload/upload.do', //接口url
								type : 'post' //默认post
							}
						});
						//创建一个编辑器
						var editIndex = layedit.build('goodsDetails_edit');
						//自定义验证规则
						form.verify({
							goodsName: function(value) {
								if(value.trim() == '') {
									return '收货人不能为空';
								}
							},
							
							address: function(value) {
								if(value.trim() == '') {
									return '收货地址不能为空';
								}
							},
							phone : [/^1[3|4|5|8][0-9]\d{4,8}$/,'请输入正确的手机号码'],
							
							firstCost : [ /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/,
									'请输入正确的价格!' ],
							sellPrice : [ /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/,
									'请输入正确的售价!' ]
						});
						//监听提交
						form.on('submit(demo1)', function(data) {
							$.ajax({
								type : "post",
								url : "${ctx}/userorder1/updateUserOrder.do",
								dataType : "json",
								data : {
									"orderID" : orderID,
									"phone" : data.field.phone,
									"address" : data.field.address,
									"consignee" : data.field.consignee,
									"number" : data.field.number,
									"logisticsOrder" : data.field.logisticsOrder,
									"logisticsTypes" : data.field.logisticsTypes,
									"remarks" : data.field.remarks,
									"realFee" : data.field.realFee,
									"orderStatus" : data.field.orderStatus,
									"payStatus":data.field.payStatus
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改订单成功");
										setTimeout(function() {
											layer.closeAll("iframe");
											//刷新父页面
											parent.location.reload();
												
										}, 1000);
									}else{
										layer.msg(result.msg,{time:2000});
									}
									return false;
								},
								error : function() {
									layer.msg("修改失败");
									return false;
								}
							});
							return false;
						});
					});
	/* function goBack() {
		window.location.href = history.back(-1);
	} */
</script>
</html>