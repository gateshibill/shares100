<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
.tred{
	color: red;
}
.layui-btn{
	height:30px;
	line-height:30px;
	font-size:12px;
	padding:0 10px
}
</style>
</head>
<body>
<div class="content">
	<table class="layui-table">
  <thead>
    <tr>
      <th colspan="2" style="text-align:center;font-weight: bold;">
      	 <h2>项目信息</h2>
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
      <td>客户户型:${room.houseDoorModel} 
      <div class="btnname"><button class="layui-btn layui-btn-xs getDecorateForecast">上传户型图</button></div>
      </td>
    </tr>
    <tr>
      <td colspan="2">客户地址:${room.homeNumber}</td>
    </tr>
    <tr>
    	<td colspan="2">
    	    <div>1.为业主派遣指定的客服管家:</div>
    		<div class="name">
    			<c:choose>
    				<c:when test="${not empty guwen}">
    					${guwen.realName} / ${guwen.phone}
    				</c:when>
    				<c:otherwise>
    					<span class="red">未选择</span>
    				</c:otherwise>
    			</c:choose>
    		</div>
    		<div class="btnname">
    		<button class="layui-btn layui-btn-xs selectAdviser" onclick="selectAdviser(1);">查看</button>   		
    		</div>
    	</td>
    </tr>
    <tr>
    	<td colspan="2">
	    	<div>2.查看业主房产信息</div>
	    	<div class="name">
	    		<c:choose>
    				<c:when test="${room.isDeal == 1}">
    					已确认
    				</c:when>
    				<c:otherwise>
    					<span class="red">未确认</span>
    				</c:otherwise>
    			</c:choose>
	    	</div>
	    	<div class="btnname"><button class="layui-btn layui-btn-xs" id="lookHouseInfo">点击查看</button></div>
    	</td>
    </tr>
    <tr>
    	<td colspan="2">
	    	<div>3.查看业主意向装修风格</div>
		    <div class="name">
		    	<c:choose>
    				<c:when test="${style.isDeal == 1}">
    					已确认
    				</c:when>
    				<c:otherwise>
    					<span class="red">未确认</span>
    				</c:otherwise>
    			</c:choose>
		    </div>
		    <div class="btnname"><button class="layui-btn layui-btn-xs" id="lookChooseStyle">点击查看</button></div>
    	</td>
    </tr>
    <tr>
    	<td colspan="2">
	    	<div>4.查看业主是否提交定金</div>
	    	<div class="name">
	    		<c:choose>
    				<c:when test="${pOrder.isDeal == 1}">
    					已提交
    				</c:when>
    				<c:otherwise>
    					<span class="red">未提交</span>
    				</c:otherwise>
    			</c:choose>
	    	</div>
	    	<div class="btnname">
		    	<button class="layui-btn layui-btn-xs" id="projectOrder">查看定金</button>
		    	<button class="layui-btn layui-btn-xs" id="projectOrderSetup">设置定金</button>
	    	</div>	    	
    	</td>
    </tr>
     <tr>
    	<td colspan="2">
	    	<div>5.分配设计师</div>
	    	<div class="name">
	    		<c:choose>
    				<c:when test="${not empty design}">
    					${design.realName} / ${design.phone}
    				</c:when>
    				<c:otherwise>
    					<span class="red">未分配</span>
    				</c:otherwise>
    			</c:choose>
	    	</div>
	    	<div class="btnname"><button class="layui-btn layui-btn-xs" onclick="selectAdviser(2);">点击查看</button></div>
    	</td>
    </tr>
    <tr>
    	<td colspan="2">
	    	<div>6.上传设计稿</div>
	    	<div class="name">
	    		<c:choose>
    				<c:when test="${fn.length(projectDesign)}">
    					已上传设计稿
    				</c:when>
    				<c:otherwise>
    					<span class="red">未上传设计稿</span>
    				</c:otherwise>
    			</c:choose>
	    	</div>
	    	<div class="btnname">
	    		<button class="layui-btn layui-btn-xs upDesignImg" data-type="1">上传平面图</button>  
	    		<button class="layui-btn layui-btn-xs upDesignImg" data-type="2">上传原型图</button> 
	    		<button class="layui-btn layui-btn-xs" onclick="updateDesign(1);">查看调整设计
	    			<c:if test="${projectViewC > 0}"><span class="layui-badge layui-bg-red">${projectViewC}</span></c:if>
	    		</button>
	    	</div>
    	</td>
    </tr>
    <tr>
    	<td colspan="2">
	    	<div>7.上传报价</div>
	    	<div class="name">
	    		<c:choose>
    				<c:when test="${fn.length(offers)}">
    					已上传报价
    				</c:when>
    				<c:otherwise>
    					<span class="red">未上传报价</span>
    				</c:otherwise>
    			</c:choose>
    		</div>	
	    	<div class="btnname">		    
	    		<button class="layui-btn layui-btn-xs upOfferImg" data-type="1">上传硬装报价</button>  
	    		<button class="layui-btn layui-btn-xs upOfferImg" data-type="2">上传软装报价</button> 
	    		<button class="layui-btn layui-btn-xs" onclick="updateDesign(2);">查看调整报价
	    			<c:if test="${projectViewB > 0}"><span class="layui-badge layui-bg-red">${projectViewB}</span></c:if>
	    		</button>
	    	</div>
    </tr>
    <tr>
    	<td colspan="2">
	    	<div>8.施工</div>
	    	<div class="name">
	    		<c:forEach items="${list}" var="pc">
	    			<c:choose>
	    				<c:when test="${pc.isFinish == 0}">
	    					未进行
	    				</c:when>
	    				<c:when test="${pc.isFinish == 1}">
	    					已完成
	    				</c:when>
	    				<c:when test="${pc.isFinish == 2}">
	    					进行中
	    				</c:when>
	    				<c:otherwise>
	    					<span class="red">未完成</span>
	    				</c:otherwise>
    				</c:choose>
	    		
	    		</c:forEach>
	    	</div>
	    	<div class="btnname"><button class="layui-btn layui-btn-xs" id="" onclick="goProjectWork();">点击查看</button></div>
    	</td>
    </tr>
    <tr>
    <td colspan="2">
	    	<div>9.验收</div>
	    	<div class="btnname">	    	
	    		<button class="layui-btn layui-btn-xs" id="construction">点击查看</button>
		    	<c:choose>
		    		<c:when test="${project.status < 8}">
		    			<button  class="layui-btn layui-btn-xs layui-btn-disabled">未开始</button>    			
		    		</c:when>
		    		<c:when test="${project.status == 8}">
		    			<button  class="layui-btn layui-btn-xs" onclick="checkAll();">验收</button>
		    		</c:when>
		    		<c:otherwise>
		    		<button  class="layui-btn layui-btn-xs layui-btn-disabled">已验收</button>    			
		    		</c:otherwise>
		    	</c:choose>
	    	</div>
	    	
    	</td>
    </tr>
    <tr>
    	<td colspan="2">
	    	<div>10.查看业主评价</div>
	    	<div class="name">
	    	<c:choose>
	    		<c:when test="${not empty pComment}">
	    			<c:choose>
    				<c:when test="${pComment.isDeal == 1}">
    					已确认
    				</c:when>
    				<c:otherwise>
    					<span class="red">未确认</span>
    				</c:otherwise>
    			</c:choose>
	    		</c:when>
	    		<c:otherwise><span class="red">暂无评论</span></c:otherwise>
	    	</c:choose>
	    		
	    	</div>
	    	<div class="btnname"><button class="layui-btn layui-btn-xs" id="commont">点击查看</button></div>
    	</td>
    </tr>
    
    <tr>
    	<td colspan="2">
	    	<div>11.施工日志</div>
	    	<div class="name">
	    		<c:if test="${logCount <= 0}"><span class="red">暂无施工日志</span></c:if>
	    	</div>
	    	<div class="btnname">
	    	<button class="layui-btn layui-btn-xs" id="" onclick="goProjectLog();">点击查看
	    		<c:if test="${logCount > 0}"><span class="layui-badge layui-bg-red">${logCount}</span></c:if>
	    	</button>
	    	</div>
    	</td>
    </tr>
    
     <tr>
    	<td colspan="2">
	    	<div>12.我要装修施工记录详情</div>
	    	<div class="name">
	    	</div>
	    	<div class="btnname"><button class="layui-btn layui-btn-xs" id="ProjectCount">点击查看</button></div>
    	</td>
    </tr>
  </tbody>
</table>
</div>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
var projectId = "${project.id}";
layui.use([ 'layer', 'table'], function() {
	table = layui.table;
	layer = layui.layer;
});
//选择顾问/设计师
function selectAdviser(advType){
	var index = layui.layer
	.open({
		title : "选择顾问",
		type : 2,
		area:['80%','80%'],
		content : "${ctx}/newProject/goSelectAdviser.do?projectId="
				+ "${project.id}"+"&advType="+advType,
		success : function(layero, index) {
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
}

$(window).one(
		"resize",
		function() {
			$("#lookHouseInfo")
					.click(
							function() {
								
								var index = layui.layer
										.open({
											title : "查看房产信息",
											type : 2,
											area:['600px','400px'],
											content : "${ctx}/project/decorateForecast.do?projectId="
													+projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
			})
}).resize();

//上传户型图
$(".getDecorateForecast").click(function(){
	var index = layui.layer
	.open({
		title : "查看上传户型图",
		type : 2,
		content : "${ctx}/project/goUploadDecorateForecast.do?projectId="
				+ "${project.id}",
		area:['100%','100%'], 
		success : function(layero, index) {
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
})

$(window).one(
		"resize",
		function() {
			$("#lookChooseStyle")
					.click(
							function() {
								
								var index = layui.layer
										.open({
											title : "查看装修风格",
											type : 2,
											area:['100%','100%'],
											content : "${ctx}/project/getChooseStyle.do?projectId="
													+projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
})
}).resize();
			
			
$(window).one(
		"resize",
		function() {
			$("#construction").click(
							function() {
								
								var index = layui.layer
										.open({
											title : "验收",
											type : 2,
											 area:['100%','100%'], 
											content : "${ctx}/project/getCheckList.do?projectId="
													+ projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
			})
}).resize();
$(window).one(
		"resize",
		function() {
			$("#commont")
					.click(
							function() {
								
								var index = layui.layer
										.open({
											title : "评论",
											type : 2,
											 area:['100%','100%'], 
											content : "${ctx}/project/getProjectComment.do?projectId="
													+ projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
			})
}).resize();

$(window).one(
		"resize",
		function() {
			$("#projectOrder")
					.click(
							function() {
								
								var index = layui.layer
										.open({
											title : "定金",
											type : 2,
											 area:['100%','100%'], 
											content : "${ctx}/project/getProjectOrderByid.do?projectId="
													+ projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
			})
}).resize();

//设置定金
$(window).one(
		"resize",
		function() {
			$("#projectOrderSetup")
					.click(
							function() {
								
								var index = layui.layer
										.open({
											title : "设置定金",
											type : 2,
											 area:['100%','100%'], 
											content : "${ctx}/project/AddProjectSetUp.do?id="
													+ projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
			})
}).resize();

//上传设计图
$(".upDesignImg").click(function(){
	var designType = $(this).data("type");
	var  title = "上传平面设计图";
	if(designType == 2){
		title = "上传原型效果图";
	}
	var index = layui.layer
	.open({
		title :title,
		type : 2,
		content : "${ctx}/newProject/goUploadDesign.do?projectId="
				+ "${project.id}"+"&designType="+designType,
		success : function(layero, index) {
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
})

//上传报价
$(".upOfferImg").click(function(){
	var offerType = $(this).data("type");
	var  title = "客户新家硬装报价方案";
	if(offerType == 2){
		title = "上传客户新家软装报价方案";
	}
	var index = layui.layer
	.open({
		title : title,
		type : 2,
		content : "${ctx}/newProject/goUploadOffer.do?projectId="
				+ "${project.id}"+"&offerType="+offerType,
		success : function(layero, index) {
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
})
//查看设计稿
$(".projectDesign").click(function(){
	var index = layui.layer
	.open({
		title : "查看设计图",
		type : 2,
		content : "${ctx}/newProject/goProjectDesign.do?projectId="
				+ "${project.id}",
		area:['100%','100%'], 
		success : function(layero, index) {
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
})
//查看是否有调整设计图/报价
function updateDesign(viewType){
	var title = "调整设计";
	if(viewType == 2){
		title = "调整报价";
	}
	var index = layui.layer
	.open({
		title : title,
		type : 2,
		content : "${ctx}/newProject/findProjectView.do?projectId="
				+ "${project.id}&viewType="+viewType,
		success : function(layero, index) {
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
}
//项目报价
function goOffer(){
	var index = layui.layer
	.open({
		title : "项目报价",
		type : 2,
		content : "${ctx}/newProject/goProjectOffer.do?projectId="
				+ "${project.id}",
		success : function(layero, index) {
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
}
//施工
function goProjectWork(){
	var index = layui.layer
	.open({
		title : "大类施工计划",
		type : 2,
		content : "${ctx}/newProject/goProjectWork.do?projectId="
				+ "${project.id}",
		success : function(layero, index){
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
}

function goProjectLog(){
	var index = layui.layer
	.open({
		title : "施工日志",
		type : 2,
		content : "${ctx}/project/getLogList.do?projectId="
				+ "${project.id}",
		success : function(layero, index){
			setTimeout(
				function() {
					layui.layer.tips(
						'点击此处返回',
						'.layui-layer-setwin .layui-layer-close',
						{
							tips : 3
						});
				}, 500)
		}
	})
	layui.layer.full(index);
}

$(window).one(
		"resize",
		function() {
			$("#ProjectCount")
					.click(
							function() {
								
								var index = layui.layer
										.open({
											title : "项目总览",
											type : 2,
											area:['100%','100%'], 
											content : "${ctx}/project/queryAllPrint.do?projectId="
													+ projectId,
											success : function(
													layero,
													index) {
												setTimeout(
														function() {
															layui.layer
															.tips(
										'点击此处返回',
										'.layui-layer-setwin .layui-layer-close',
									{
								tips : 3
							});
						}, 500)
					}
				})
			})
}).resize();

//完成验收后
function checkAll(){
	layer.confirm(
			'确认验收完成,进入客户评价阶段?',
			{
				btn : [ '确认', '取消' ]
			},
			function() {
				$.post('${ctx}/project/doAcceptance.do',{'projectId' : projectId },
					function(data) {
						if(data.code == 0){
							layer.msg("操作成功");
							setTimeout(function(){
								window.location.reload(true);
							}, 1000)
						}else{
							layer.msg(data.msg)
						}
				})
			})
}
</script>	
</body>
</html>