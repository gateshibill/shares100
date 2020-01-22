<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>报价结果</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/price_result.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	
</head>
<body>
	<div class="page">
		<!-- 公共头部 -->
	  	<jsp:include page="/vij/publich/header.do">
	  		<jsp:param value="f" name="hiden"/>
	  	</jsp:include>
		<div class="price">
			<div class="price-image">
				<img src="${ctx}/vij/images/pricebeijing_02.png">
			</div>
			<div class="price-content">
				<div class="price-center">
					<div class="center-cen">
						<div class="center-content">
							<div class="content-counter">
								<p class="counter-title" align="center">扫描下方二维码了解更多</p>
								<p class="counter-mintitle" align="center">30分钟内会有专门人员致电联系，请注意电话畅通！会有专门人员致电联系</p>
								<div class="counter-content">
									<div class="content-div">
										<div class="div-image">
											<img src="${ctx}/vij/images/weiaijiaguwen.png">
										</div>
										<p align="center">唯爱家·高级顾问（5年经验）</p>
									</div>
									<div class="content-div">
										<div class="div-image erweima">
											<img src="${ctx}/vij/images/weixin_22.gif">
										</div>
										<p align="center">扫描关注小程序！</p>
									</div>
								</div>
								<p class="counter-xiatitle" align="center">
									24小时咨询任何装修疑问，还有更多装修案例
								</p>
								
							</div>
							
							<div class="content-result">
								<p class="result-re">您家的装修预算<span>${grossPrice}</span>元</p>
								<div class="result-type">
									<p>材料费：${clmoney}元</p>
									<p>人工费：${rgmoney}元</p>
									<p>设计费：${sjmoney}元</p>
									<p>质检费：${zjmoney}元</p>
									<div class="result-type-ty">
										<input type="radio" name="" checked="checked" class="input">
										<span>基装</span>
										<input type="radio" name="" checked="checked" class="input">
										<span>主材</span>
										<input type="radio" name="" checked="checked" class="input">
										<span>软包</span>
										<input type="radio" name="" checked="checked" class="input">
										<span>家电</span>
									</div>
									<input type="button" name="" value="提交" class="input-button">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" />

	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			
		})
	</script>
</body>
</html>