<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta charset="utf-8">
	<title>编辑商品类型</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
<%-- 	<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" /> --%>
	<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css"
	media="all" />
</head>
<body class="childrenBody">
	<div style="padding-left:50px;padding-top:30px;">	
	<div class="layui-form">
		<div class="layui-form-item">
			<div class="layui-inline">		
				<label class="layui-form-label">类型名称</label>
				<div class="layui-input-inline" style="width: 200px;">
					<input type="text" id="typeName" value="${type.typeName}" class="layui-input newsAuthor" lay-verify="required" placeholder="请输入商品类型">
				    <input type="hidden" id="typeId" value="${type.typeId}"/>
				    <input type="hidden" id="loginPlat" value="${type.loginPlat}"/>
				</div>
			</div>				
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">		
				<label class="layui-form-label">类型英文名称</label>
				<div class="layui-input-inline" style="width: 200px;">
					<input type="text" id="enTypeName" value="${type.enTypeName}" class="layui-input newsAuthor" lay-verify="required" placeholder="请输入商品类型英文名称">
				    <input type="hidden" id="typeId" value="${type.typeId}"/>
				    <input type="hidden" id="loginPlat" value="${type.loginPlat}"/>
				</div>
			</div>				
		</div>
		<div class="layui-form-item">
					<label class="layui-form-label">排序：</label>
					<div class="layui-input-inline" style="width: 200px;">
						<input type="number"
							name="orderNo" id="orderNo" lay-verify="orderNo" autocomplete="off"
							class="layui-input" value="${type.orderNo}">
					</div>
		</div>
		
		<%--  <c:if test="${not empty type.parentId}"> --%>
		 <div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">类型图标：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					</label>
						<input type="file" name="file" class="layui-upload-file" id="test" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
		<%-- </c:if> --%>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 100px;"></label>	
			<img id="headImage" src="${type.typeIcon}" style="max-width:100px;max-height:100px;" />
		</div> 
		<div class="layui-form-item" style="padding-left:50px">
			<div class="layui-input-block">
				<button class="layui-btn" id="send" >立即提交</button>
		    </div>
		</div>
		</div>
	</div>	
	
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>

<script type="text/javascript">
layui.use(['layer','form'],function(){
	layer = layui.layer;
	form = layui.form;
})


var typeIcon = "";
layui.use('upload', function(){
layui.upload({
  url: '${ctx}/layfileUpload/upload.do'
  ,elem: '#test' //指定原始元素，默认直接查找class="layui-upload-file"
  ,method: 'post' //上传接口的http类型
  ,success: function(res){
  	console.log(res);
  	typeIcon = res.data.src;
  	$("#headImage").attr("src",typeIcon);
  		}
	});
});

    $("#send").on('click',function(){
      var typeName = $("#typeName").val();
      var typeId = $("#typeId").val();
      //var loginPlat = $("#loginPlat").val();
      var enTypeName = $("#enTypeName").val();
      //var parentId = $('#parentId').val();
      var orderNo = $('#orderNo').val();
      if(!typeName){
    	  layer.msg("类型名称不能为空");
    	  return;
    	  
      }
    	$.ajax({
    		 url:'${ctx}/goods/updateTypeName.do',
    		 type:'post',
    		 dataType:'json',
    		 data:{
    			 'typeName':typeName,
    			 'typeId':typeId,
    			 //'loginPlat':loginPlat,
    			 'typeIcon':typeIcon,
    			 'enTypeName':enTypeName,
    			 //'parentId':parentId,
    			 'orderNo':orderNo,
    			 'typeIcon':typeIcon
    			 },
    		 success:function(data){
    			 if(data.code==0){
    				  layer.msg(data.msg);
    				  setTimeout(function(){
    					  layer.closeAll("iframe");
    					//刷新父页面
    				 		parent.location.reload();
    					  //window.location.href = '${ctx}/sharetask/gotaskcontentlist.do';
    				  },1000);
    			 }else{
    				 layer.msg(data.msg);
    				 return;
    			 }
    		 }
    	});
    })
    
   function goBack() {
		window.history.go(-1);
	}
</script>
</body>
</html>