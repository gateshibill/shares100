<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="utf-8">
<title>项目明细</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${ctx}/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/global.css" media="all">
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${ctx}/css/table.css?a=<%=new java.util.Random().nextInt()%>" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>" />

<style type="text/css">
.content{
	width:80%;
	margin:0 auto;
}
.top{
	text-align:center
}
.layui-table-cell{
   height:auto;
   overflow:visible;
  text-overflow:inherit;
 white-space:normal;
}
.layui-table td, .layui-table th{
	padding:15px;
}
.name{
	padding:5px 10px 0 10px;
	color:#22A95D;
	float:left
}
.btnbutton{
	float:right;
}
.btnname{
	float:right;
}
.red{
	color:#ff0000;
}
.projectTable .p1{
	color: #666;
	font-size: 23px;
	font-weight: 400;
	
}
.projectTable .p2{
	color: #339d5b;
	font-size: 20px;
	font-weight: 400;
	padding-left: 20px;
}
.projectTable .p3{
	color: red;
	font-size: 20px;
	font-weight: 400;
	padding-left: 20px;
}  
</style>
</head>
<body>
<div class="content">
	<table class="layui-table">
  <thead>
    <tr>
      <th colspan="2" style="text-align:center;font-weight: bold;">
      	 <h2>客户信息</h2>
      </th>
    </tr> 
  </thead>
  <tbody>
    <tr>
      <td>客户姓名:${room.lfUserName}</td>
      <td>联系方式:${room.mobilePhoneNo}</td>
    </tr>
    <tr>
      <td>房屋面积:${room.buildingArea}</td>
      <td>客户户型:${room.houseDoorModel}</td>
    </tr>
    <tr>
      <td colspan="2">客户地址:${room.homeNumber}</td>
    </tr>
    <tr>
    	<td>
    	    <div>1.为业主派遣指定的客服管家:</div></td>
    		<td><div class="name">
    			<c:choose>
    				<c:when test="${not empty guwen}">
    					${guwen.realName} / ${guwen.phone}
    				</c:when>
    				<c:otherwise>
    					<span class="red">未选择</span>
    				</c:otherwise>
    			</c:choose>
    		</div>
    	</td>
    </tr>
    <tr>
    	<td>
	    	<div>2.分配设计师</div></td>
	    	<td><div class="name">
	    		<c:choose>
    				<c:when test="${not empty design}">
    					${design.realName} / ${design.phone}
    				</c:when>
    				<c:otherwise>
    					<span class="red">未分配</span>
    				</c:otherwise>
    			</c:choose>
	    	</div>
    	</td>
    </tr>
     <tr>
    	<td>
	    	<div>3.查看业主房产信息</div></td>
	    	<td><div class="name">
	    		<c:choose>
    				<c:when test="${room.isDeal == 1}">
    					已确认
    				</c:when>
    				<c:otherwise>
    					<span class="red">未确认</span>
    				</c:otherwise>
    			</c:choose>
	    	</div>
    	</td>
    </tr>
   	<tbody>
	 	<tr>
			<td>姓名:</td>
			<td>${room.lfUserName}</td>
		</tr>

		<tr>
			<td>电话:</td>
			<td>${room.mobilePhoneNo}</td>
		</tr>
		<tr>
			<td>城市:</td>
			<td>${room.cityAreas}</td>
		</tr>
		<tr>
			<td>地址:</td>
			<td>${room.homeNumber}</td>
		</tr>
		<tr>
			<td>户型:</td>
			<td>${room.houseDoorModel}</td>
		</tr>
		<tr>
			<td>面积:</td>
			<td>${room.buildingArea}</td>
		</tr>
		<tr>
			<td>房型:</td>
			<td>${room.housingTypes}</td>
		</tr>
		</tbody>
		
		 <tr>
	    	<td>
		    	<div>4.查看业主意向装修风格</div>
		    </td>
			<td><div class="name">
			    	<c:choose>
	    				<c:when test="${style.isDeal == 1}">
	    					已确认
	    				</c:when>
	    				<c:otherwise>
	    					<span class="red">未确认</span>
	    				</c:otherwise>
	    			</c:choose>
			    </div>
	    	</td>
	    </tr>
    	<tbody class="style-detail"></tbody>
    	<tbody>
				<tr>
			    	<td>5.查看业主是否提交定金</td>
				    <td>
				    	<c:if test="${project.isPay == '1'}">
							<p style="color:green">已交定金：${projectOrder.realMoney}</p>
						</c:if>
						<c:if test="${project.isPay == '0'}">
							<p style="color:red">未交定金</p>
						</c:if>
				    </td>	
			    </tr>
			    <tr>
		   			<td><div>6.查看业主是否有(调整设计或确定设计稿)</div></td>
			   		<td>
						<c:if test="${pView_d == 't'}">
							<p class="p2" style="color:green">有</p>
						</c:if>
						<c:if test="${pView_d == 'f'}">
							<p class="p3" style="color:red">没有</p>
						</c:if>
					</td>  
		   		</tr>
		   		<c:forEach var="design" items="${designList}">
		   			<tr>
		   				<td>调整设计意见</td>
			   			<td>${design.content}</td>
			   		</tr>
		   		</c:forEach>
		   		
		     <tr>
		    	<td>7.查看业主是否有(调整报价或确定报价)</td>
		    	<th>
					<c:if test="${pView_o == 't'}">
						<p class="p2" style="color:green">有</p>
					</c:if>
					<c:if test="${pView_o == 'f'}">
						<p class="p3" style="color:red">没有</p>
					</c:if>
				</th>  
		    </tr>
		    <c:forEach var="offer" items="${offerList}">
	   			<tr>
	   				<td>调整报价意见</td>
		   			<td>${offer.content}</td>
		   		</tr>
	   		</c:forEach>
    <tr>
    	<td colspan="2" style="text-align: center">8.施工</td>
    </tr>
   	<c:forEach items="${pPlanList}" var="pls">
   	 <c:if test="${fn.length(pls.planList) > 0}">
	   <c:forEach items="${pls.planList}" var="pl">
	   	<tr>
	   		<td>${pl.planName}(${pls.bigPlanName})</td>
	   		<td>
	   			<c:choose>
	   				<c:when test="${pl.isFinish==0}">
	   					施工完成
	   				</c:when>
	   				<c:when test="${pl.isFinish==1}">
	   					施工没有完成
	   				</c:when>
	   			</c:choose>
	   		</td>
	   	</tr>
	   	</c:forEach>
	   </c:if>
   	</c:forEach>
  	<tr>
    	<td colspan="2" style="text-align: center;">9.验收</td>
    </tr>
    <tr>
	   <c:forEach items="${pCheck}" var="pc">
			<tr>
			<td> ${pc.checkName}</td>
			<td>
				 	<c:choose>
						<c:when test="${pc.checkStatus == 0}">
								未验收
							</c:when>
							<c:when test="${pc.checkStatus == 1}">
								验收通过
							</c:when>
							<c:when test="${pc.checkStatus == 2}">
								正在验收
							</c:when>
							
							<c:when test="${pc.checkStatus == 3}">
						<span class="red">验收不通过</span>
						</c:when>
				</c:choose>
			</td>
		</tr>
	</c:forEach> 
    </tr>
    
    <tr>
		<th style="text-align: center; font-weight: bold;" colspan="2">
			<h2>施工日志</h2>
		</th>
	</tr>
			<tr>
				<th>标题</th>
				<th>备注</th>
			</tr>
    	<c:forEach items="${log}" var="pc" varStatus="status">
			<tr>
				<td>${pc.title}</td>
				<td>${pc.remark}</td>
			</tr>
		</c:forEach>
   
    <tr>
    <td colspan ="2">
    <fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 30px;">
		<legend>整体满意度:${pComment.commentScore}</legend>
	</fieldset>

	<div class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">选择服务标签：</label>
			<div class="layui-input-block selectAdviser">
			 <button class="layui-btn layui-btn-primary layui-btn-radius">${pComment.tagStr}</button>
			</div>
		</div>
	</div>
	<div class="layui-form-item">
				<label class="layui-form-label">顾客说：</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入介绍" name="commentContent" class="layui-textarea">${pComment.commentContent}</textarea>
				</div>
		</div>
		</td>
		</tr>	
    </tbody>
    </table>
    </div>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script>
var designArr = '${style.answerList}';
if(designArr){
	 designArr = JSON.parse(designArr);
	 if(designArr.length>0){
		for(var i = 0; i < designArr.length; i++){
			//遍历 
			$('.style-detail').append('<tr><td>'+designArr[i].question+'</td><td>'+designArr[i].answer+'</td></tr>');
		}
	}
}
</script>
</body>
</html>