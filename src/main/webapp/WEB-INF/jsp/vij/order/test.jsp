<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公共头部</title>
<style type="text/css">
	#result-qrcode{
		width:200px;
		height:200px
	}
</style>
</head>
<body>
<div id="result-qrcode"></div>
<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/vij/js/qrcode.min.js"></script>
<script type="text/javascript">
var checkTimer;
$(function(){
	// 设置参数方式
	$.ajax({
		 url:'${ctx}/vij/pay/unifiedorder.do',
		 type:'post',
		 data:{orderId:793},
		 dataType:'json',
		 success:function(ret){
			 alert(JSON.stringify(ret));
			 var qrcode = new QRCode(document.getElementById("result-qrcode"), {
				    text: ret.msg,
				    width: $("#result-qrcode").width(),
				    height: $("#result-qrcode").height(),
				    colorDark: '#000',
				    colorLight: '#fff',
				    correctLevel: QRCode.CorrectLevel.H,
				});
		 },
		 error:function(ret){
			 
		 }
	})
    checkTimer = window.setInterval(function(){
    	getPayState()
    },1500); 
})
function getPayState(){
    var url = '${ctx}/vij/pay/checkOrderPay.do';
    //轮询是否已经付费
    $.ajax({
        type:'post',
         url:url,
         data:{orderId:793},
         cache:false,
         async:true,
         success:function(json){
             if(json.code == 0){
            	 window.clearInterval(checkTimer);

                 window.location.href = '${ctx}/vij/account/login.do';
             }else{
            	 console.log("失败---")
             }
         },
         error:function(){
             layer.msg("执行错误！", 8);
         }
     });
}
</script>		
</body>
</html>