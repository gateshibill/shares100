<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>商品列表</title>
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
	.layui-form-item{
		margin-bottom:0px;
	}
</style>
</head>
<body>
	<blockquote class="layui-elem-quote">
		<form class="layui-form layui-form-pane" action="">
			<div class="layui-form-item">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">应用名称</label>
					<div class="layui-input-block ">
						<select id="loginPlat" class="search-input" name="loginPlat"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<c:forEach var="app" items="${appList}">
								<c:choose>
										<c:when test="${goods.loginPlat==app.applicationId}">
											<option selected="selected" value="${app.applicationId}">${app.applicationName}</option>
										</c:when>
										<c:otherwise>
											<option value="${app.applicationId}">${app.applicationName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select>
					</div>
				</div>
			
				<div class="layui-inline pane-select">
					<label class="layui-form-label">商品类型</label>
					<div class="layui-input-block ">
						<select id="goodsType" name="goodsType" class="search-select" lay-search="">
								<option value="">全部</option>
								<c:forEach var="app" items="${typeList}">
								  <c:if test="${empty app.parentId}">
									<c:choose>
										<c:when test="${goods.goodsType==app.typeId}">
											<option selected="selected" value="${app.typeId}">${app.typeName}</option>
										</c:when>
										<c:otherwise>
											<option value="${app.typeId}">${app.typeName}</option>
										</c:otherwise>
									</c:choose>
								  </c:if>	
								</c:forEach>
							</select>
					</div>
				</div>
				<%-- <div class="layui-inline">
					<label class="layui-form-label">是否拼</label>
					<div class="layui-input-block">
						<select id="isPin11" class="search-input" name="isPin"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="0"
							<c:choose>
									<c:when test="${goods.isPin==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							<option value="1"
							<c:choose>
									<c:when test="${goods.isPin==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							</select>
					</div>
				</div> --%>
				<%-- <div class="layui-inline">
					<label class="layui-form-label">是否砍</label>
					<div class="layui-input-block">
						<select id="isCut11" class="search-input" name="isCut"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="0"
							<c:choose>
									<c:when test="${goods.isCut==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							<option value="1"
							<c:choose>
									<c:when test="${goods.isCut==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							</select>
					</div>
				</div> --%>
				<div class="layui-inline">
					<label class="layui-form-label">是否秒</label>
					<div class="layui-input-block">
						<select id="isDtbt11" class="search-input" name="isDtbt"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="0"
							<c:choose>
									<c:when test="${goods.isDtbt==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							<option value="1"
							<c:choose>
									<c:when test="${goods.isDtbt==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">是否热销</label>
					<div class="layui-input-block">
						<select id="isHot11" class="search-input" name="isHot"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="0"
							<c:choose>
									<c:when test="${goods.isHot==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							<option value="1"
							<c:choose>
									<c:when test="${goods.isHot==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">是否推荐</label>
					<div class="layui-input-block">
						<select id="isRecommend11" class="search-input" name="isRecommend"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="0"
							<c:choose>
									<c:when test="${goods.isRecommend==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							<option value="1"
							<c:choose>
									<c:when test="${goods.isRecommend==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">是否上架</label>
					<div class="layui-input-block">
						<select id="isSelled" class="search-input" name="isSelled"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="0"
							<c:choose>
									<c:when test="${goods.isSelled==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							<option value="1"
							<c:choose>
									<c:when test="${goods.isSelled==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">是否积分兑换</label>
					<div class="layui-input-block">
						<select id="isScore" class="search-input" name="isScore"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="1"
							<c:choose>
									<c:when test="${goods.isScore==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							<option value="0"
							<c:choose>
									<c:when test="${goods.isScore==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">是否新品</label>
					<div class="layui-input-block">
						<select id="isNew" class="search-input" name="isScore"
								lay-verify="required" lay-search="">
								<option value="">全部</option>
								<option value="1"
							<c:choose>
									<c:when test="${goods.isNew==1}">selected="selected"</c:when>
									</c:choose>>是</option>
							<option value="0"
							<c:choose>
									<c:when test="${goods.isNew==0}">selected="selected"</c:when>
									</c:choose>>否</option>
							</select>
					</div>
				</div>
				
				<div class="layui-inline pane-select">
					<label class="layui-form-label">商品名称</label>
					<div class="layui-input-block ">
						<input type="text" name="goodsName" id="goodsName"
								placeholder="请输入商品名称" class="layui-input" value="${goods.goodsName}">
					</div>
				</div>
				
				<div class="layui-inline pane-select">
					<label class="layui-form-label">商品ID</label>
					<div class="layui-input-block ">
						<input type="text" name="goodsId" id="goodsId"
								placeholder="请输入商品ID" class="layui-input" value="${goods.goodsId}">
					</div>
				</div>
				
			<%-- 	<div class="layui-inline pane-select">
					<label class="layui-form-label">发布者名称</label>
					<div class="layui-input-block ">
						<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入发布者名称" class="layui-input" value="${userKeyWords}">
					</div>
				</div> --%>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布日期</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="dateRange"
								id="dateRange" placeholder=" - " value="${dateRange}">
					</div>
				</div>
				<div class="layui-inline">
					<a class="layui-btn search_btn" onclick="doSearch()"> <i
						class="layui-icon">&#xe615;</i>查询
					</a>
				</div>
			</div>
	</form>
	</blockquote>
	<div class="layui-btn-group demoTable">
		<a class="layui-btn" style="margin-left:20px;"
			href="${ctx}/goods/goAddGoods.do">
			<i class="layui-icon">&#xe608;</i>添加商品
		</a>
	</div>
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<!--{{# var ispass=d.isPassSell}}
    {{# if (ispass=='0'){}}
          <a class="layui-btn layui-btn-mini" onclick="goodsIsPassSell(0,{{d.goodsId}})" >审核</a>
    {{# } else{ }}
         <a class="layui-btn layui-btn-warm layui-btn-mini" onclick="goodsIsPassSell(1,{{d.goodsId}})" >取消</a>
    {{# } }}
	  -->
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="descoveryDetail" >查看</a>

  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteGoods({{d.goodsId}})">删除</a>

</script>
	<!-- 上架状态模板 -->
	<script type="text/html" id="isshangjiaTpl">
    {{# var issj=d.isSelled}}
    {{# if (issj=='0'){}}
    	<a href="#" style="color:red" lay-event="recoveryOne">下架</a>
    {{# } else{ }}
        <a href="#" lay-event="recoveryOne"><span style="color:green">上架</span></a>
    {{# } }}
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="ispassTpl">
    {{# var ispass=d.isPassSell}}
    {{# if (ispass=='0'){}}
    	<span style="color:red;">未审核</span>
    {{# } else{ }}
   		<span style="color:green;">已审核</span>
    {{# } }}
</script>	
<!-- 是否热销模板 onclick="updateIsHot({{d.goodsId}},{{d.isHot}})"-->
	<script type="text/html" id="isHotTpl">
    {{# var tj=d.isHot}}
    {{# if (tj=='0'){}}
       <a href="#" style="color:red" class="" lay-event="updateIsHot">否</a>
    {{# } else if(tj=='1'){ }}
   		 <a href="#"  lay-event="updateIsHot"><span style="color:green;">是</span></a>
    {{# } }}
</script>
<!-- 是否砍价 -->
<script type="text/html" id="isCutTpl">
    {{# var tj=d.isCut}}
    {{# if (tj=='0'){}}
       <a href="#" style="color:red" class="" lay-event="updateIsCut">否</a>
    {{# } else if(tj=='1'){ }}
   		 <a href="#"  lay-event="updateIsCut"><span style="color:green;">是</span></a>
    {{# } }}
</script>

<!-- 是否拼团 -->
<script type="text/html" id="isPinTpl">
    {{# var tj=d.isPin}}
    {{# if (tj=='0'){}}
       <a href="#" style="color:red" class="" lay-event="updateIsPin">否</a>
    {{# } else if(tj=='1'){ }}
   		 <a href="#"  lay-event="updateIsPin"><span style="color:green;">是</span></a>
    {{# } }}
</script>

<script type="text/html" id="isNewTpl">
    {{# var tj=d.isNew}}
    {{# if (tj=='0'){}}
       <a href="#"  lay-event="updateIsNew"><span style="color:red">否<span></a>
    {{# } else if(tj=='1'){ }}
   		<a href="#"  lay-event="updateIsNew"> <span style="color:green;">是</span></a>
    {{# } }}
</script>

<!-- 是否秒杀 -->
<script type="text/html" id="isDtbtTpl">
    {{# var tj=d.isDtbt}}
    {{# if (tj=='0'){}}
       <a href="#" style="color:red" class="" lay-event="updateIsDtbt">否</a>
    {{# } else if(tj=='1'){ }}
   		 <a href="#"  lay-event="updateIsDtbt"><span style="color:green;">是</span></a>
    {{# } }}
</script>
<!-- 是否热销模板 -->
	<script type="text/html" id="isRecommendTpl">
    {{# var tj=d.isRecommend}}
    {{# if (tj=='0'){}}
<a href="#" style="color:red"  lay-event="updateIsRecommend">否</a>
    {{# } else if(tj=='1'){ }}
   		 <a href="#"  lay-event="updateIsRecommend"><span style="color:green;">是</span></a>
    {{# } }}
</script>
	<script type="text/html" id="nicknameTpl">
       {{#if(d.goodsUser){}}
              {{d.goodsUser.nickName?d.goodsUser.nickName:' <span style="color:red"> 未设置   </span> '}}
       {{# }else{}}
        <span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
<!-- goodsTypeTpl -->
<script type="text/html" id="goodsTypeTpl">
     {{# var tj=d.gType.typeName}}
    {{# if (tj!='null'){}}
        {{tj}}
    {{# } else{ }}
   		 没有找到
    {{# } }}
</script>
<script type="text/html" id="appTypeTpl">
 {{# var tj=d.app.applicationName}}
    {{# if (tj!='null'){}}
        {{tj}}
    {{# } else{ }}
   		 没有找到
    {{# } }}
</script>

<script type="text/html" id="isScoreTpl">
    {{# var isScore=d.isScore}}
    {{# if (isScore== 1){}}
		<a href="#"  style="color:green" lay-event="updateIsScore">是</a>
    {{# } else { }}
   		 <a href="#" style="color:red" lay-event="updateIsScore">否</a>
    {{# } }}
</script>
<script type="text/html" id="goodsNameTpl">
 <a href="${ctx}/goods/goBrowseList.do?goodsId={{d.goodsId}}" style="color:#1E9FFF">{{d.goodsName}}</a>  
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	
	<script>
		layui.use([ 'layer', 'table', 'laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			var laydate = layui.laydate;
			//日期范围
			laydate.render({
				elem : '#dateRange',
				range : true
			});
			//--------------方法渲染TABLE----------------
			var pid = $("#loginPlat").val();
			var lp2 = $("#loginPlat1").val();
			var type = $("#goodsType").val();
			var isreco = $("#isRecommend11").val();
			var iscut = $("#isCut11").val();
			var ispin = $("#isPin11").val();
			var isdtbt = $("#isDtbt11").val();
			var ishot = $("#isHot11").val();
			var name = $("#goodsName").val();
			var goodsId = $("#goodsId").val();
			var ukw=$("#userKeyWords").val();
			var dtr = $("#dateRange").val();
		    var isSelled=$("#isSelled").val();
			var isScore = $('#isScore').val();
			var isNew = $('#isNew').val();
			getlist(pid,lp2, type, isreco,iscut,ispin,isdtbt,ishot, name, dtr,ukw,isSelled,isScore,isNew,goodsId);
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var lp2 = $("#loginPlat1").val();
			var type = $("#goodsType").val();
			var isreco = $("#isRecommend11").val();
			var iscut = $("#isCut11").val();
			var ispin = $("#isPin11").val();
			var isdtbt = $("#isDtbt11").val();
			var ishot = $("#isHot11").val();
			var name = $("#goodsName").val();
			var dtr = $("#dateRange").val();
			var ukw=$("#userKeyWords").val();
		    var isSelled=$("#isSelled").val();
		    var isScore = $('#isScore').val();
			var isNew = $('#isNew').val();
			var goodsId = $("#goodsId").val();
			getlist(pid, lp2, type, isreco,iscut,ispin,isdtbt,ishot, name, dtr,ukw,isSelled,isScore,isNew,goodsId);
		}

		function getlist(pid, loginPlat2, goodsType, isRecommend,isCut,isPin,isDtbt, isHot,goodsName,DateRange,userKeyWords,isSelled,isScore,isNew,goodsId) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'appName' : loginPlat2,
					'goodsType' : goodsType,
					'isCut' : isCut,
					'isPin' : isPin,
					'isDtbt' : isDtbt,
					'isHot' : isHot,
					'isRecommend' : isRecommend,
					'goodsName' : goodsName,
					'dateRange' : DateRange,
					'isSelled' : isSelled,
					'userName' : userKeyWords,
					'isScore': isScore,
					'isNew':isNew,
					'goodsId':goodsId
				},
				url : '${ctx}/goods/goodsList.do',
				cols : [ [ 
// 			 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'goodsId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'appType',
					title : '所在应用',
					width : 150,
					align : 'center',
					templet : "#appTypeTpl"
				},{
					field : 'goodsType',
					title : '商品类型',
					width : 150,
					align : 'center',
					templet : "#goodsTypeTpl"
				},{
					field : 'goodsName',
					title : '商品名称',
					width : 250,
					align : 'center',
					templet : "#goodsNameTpl"
				},{
					field : 'goodSort',
					title : '商品排序',
					width : 100,
					align : 'center',
					sort:true
				},
				{
					field : 'isSelled',
					title : '是否上下架',
					width : 100,
					align : 'center',
					templet : "#isshangjiaTpl"
				}, {
					field : 'isHot',
					title : '是否热销',
					width : 100,
					align : 'center',
					templet : "#isHotTpl"
				}, {
					field : 'isNew',
					title : '是否新品',
					width : 100,
					align : 'center',
					templet : "#isNewTpl"
				}
				/* , {
					field : 'isCut',
					title : '是否砍价',
					width : 100,
					align : 'center',
					templet : "#isCutTpl"
				}
				, {
					field : 'isPin',
					title : '是否拼团',
					width : 100,
					align : 'center',
					templet : "#isPinTpl"
				} */
				, {
					field : 'isDtbt',
					title : '是否秒杀',
					width : 100,
					align : 'center',
					templet : "#isDtbtTpl"
				}, {
					field : 'isRecommend',
					title : '是否推荐',
					width : 100,
					align : 'center',
					templet : "#isRecommendTpl"
				},{
					field : 'isScore',
					title : '积分兑换',
					width : 100,
					align : 'center',
					templet : "#isScoreTpl"
				},{
					field : 'scoreCount',
					title : '所需积分',
					width : 100,
					align : 'center',
					sort :true
				},
				/* {
					field : 'publisherId',
					title : '发布者ID',
					width : 100,
					align : 'center'
				}, {
					field : 'realName',
					title : '发布者昵称',
					width : 200,
					align : 'center',
					templet : "#nicknameTpl"
				},  */
				{
					field : 'firstCost',
					title : '原价(元)',
					width : 100,
					align : 'center',
				}, {
					field : 'sellPrice',
					title : '售价(元)',
					width : 100,
					align : 'center',
				}, {
					field : 'vipPrice',
					title : '售价(元)',
					width : 100,
					align : 'center',
				}, {
					field : 'goodsRebate',
					title : '返点',
					width : 100,
					align : 'center',
				}, {
					field : 'selledCount',
					title : '销量',
					width : 100,
					align : 'center',
				}, {
					field : 'goodsStock',
					title : '库存',
					width : 100,
					align : 'center',
				},  {
					field : 'isPassSell',
					title : '是否审核',
					width : 100,
					sort : true,
					align : 'center',
					templet : "#ispassTpl"
				}, {
					field : 'createTime',
					title : '发布时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					title : '操作',
					width : 170,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				} ] ],
				page : true,
				limits : [ 6, 12, 24, 36, 48, 60 ],
				limit : 12, //默认采用20
				width : '100%',
				height : 'auto',
				loading : true,
				even : true,
				response : {
					statusName : 'code' //数据状态的字段名称，默认：code
					,
					statusCode : 0 //成功的状态码，默认：0
					,
					countName : 'count' //数据总数的字段名称，默认：count
					,
					dataName : 'data' //数据列表的字段名称，默认：data
				},
				done : function(res, curr, count) {
					//如果是异步请求数据方式，res即为你接口返回的信息。
					//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
					//console.log(res);
					//得到当前页码
					//console.log(curr);
					//$("#curPageIndex").val(curr);
					//得到数据总量
					//console.log(count);
				}
			});
		}
		
		
		table.on('tool(descovery)', function(obj) {
			
			var data = obj.data;
			if (obj.event === 'descoveryDetail') {
				//window.location.href="${ctx}/goods/goodsDetails.do?goodsId="+data.goodsId;
				var index = layui.layer.open({
					title : "编辑商品--"+data.goodsName,
					type : 2,
					content : "${ctx}/goods/goodsDetails.do?goodsId="+data.goodsId,
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
			} else if(obj.event === 'updateIsHot'){
						if(data.isHot==1){
							$.post('${ctx}/goods/updateGoods.do', {
								'goodsId' : data.goodsId,
								'isHot' : 0,
							}, function(data) {
								if (data.code==0) {
									layer.msg('取消热销成功');
										//同步更新缓存对应的值
										 obj.update({
											isHot : 0
										});
								}else{
									layer.msg(data.msg);
									return;
								}   
							});
						} else{
							$.post('${ctx}/goods/updateGoods.do', {
								'goodsId' : data.goodsId,
								'isHot' : 1,
							}, function(data) {
								if (data.code==0) {
									layer.msg('设置热销成功');
										//同步更新缓存对应的值
										 obj.update({
											isHot : 1
										});
								}else{
									layer.msg(data.msg);
									return;
								}   
							});
						}
			  }else if(obj.event === 'updateIsNew'){
				  var isNew = data.isNew == 1?0:1;
				  $.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : data.goodsId,
						'isNew' :isNew,
					}, function(data) {
						if (data.code==0) {
							layer.msg('设置成功');
								//同步更新缓存对应的值
								 obj.update({
									isNew : isNew
								});
						}else{
							layer.msg(data.msg);
							return;
						}   
					});
			  }else if(obj.event === 'updateIsCut'){
				  	
					if(data.isCut==0){
						layer.open({
							  type: 2,
							  title:'设置砍价数据',
							  area: ['400px', '300px'],
							  skin: 'layui-layer-demo', //样式类名
							  closeBtn: 0, //不显示关闭按钮
							  anim: 2,
							  shadeClose: true, //开启遮罩关闭
							  content: '${ctx}/wx/activity/getCutByGoodsId.do?id='+data.goodsId,
							  end:function(){
								  window.location.reload();
							  }	
						});
					} 
					else{
						$.post('${ctx}/goods/updateGoods.do', {
							'goodsId' : data.goodsId,
							'isCut' : 0,
						}, function(data) {
							if (data.code==0) {
								layer.msg('取消砍价成功');
									 //同步更新缓存对应的值
									 obj.update({
										isCut : 0
									});
							}else{
								layer.msg(data.msg);
								return;
							}   
						});
					}
		  		}else if(obj.event === 'updateIsPin'){
				  	
					if(data.isPin==0){
						layer.open({
							  type: 2,
							  title:'设置拼团数据',
							  area: ['400px', '250px'],
							  skin: 'layui-layer-demo', //样式类名
							  closeBtn: 0, //不显示关闭按钮
							  anim: 2,
							  shadeClose: true, //开启遮罩关闭
							  content: '${ctx}/wx/activity/getPinByGoodsId.do?id='+data.goodsId,
							  end:function(){
								  window.location.reload();
							  }	
							});
					} 
					else{
						$.post('${ctx}/goods/updateGoods.do', {
							'goodsId' : data.goodsId,
							'isPin' : 0,
						}, function(data) {
							if (data.code==0) {
								layer.msg('取消拼团成功');
									 //同步更新缓存对应的值
									 obj.update({
										isPin : 0
									});
							}else{
								layer.msg(data.msg);
								return;
							}   
						});
					}
		  		}  else if(obj.event === 'updateIsDtbt'){
					// if(data.isDtbt==0){
						layer.open({
							  type: 2,
							  title:'设置秒杀商品',
							  area: ['500px', '300px'],
							  skin: 'layui-layer-demo', //样式类名
							  closeBtn: 1, //不显示关闭按钮
							  anim: 2,
							  shadeClose: true, //开启遮罩关闭
							 // content: '${ctx}/wx/activity/getDtbtByGoodsId.do?id='+data.goodsId,
							  content:'${ctx}/goods/goDtbtGoods.do?goodsId='+data.goodsId,
							  end:function(){
								 /* window.location.reload();*/
							  }	
							});
					/* } 
					else{
						$.post('${ctx}/goods/updateGoods.do', {
							'goodsId' : data.goodsId,
							'isDtbt' : 0,
						}, function(data) {
							if (data.code==0) {
								layer.msg('取消秒杀成功');
									 //同步更新缓存对应的值
									 obj.update({
										isDtbt : 0
									});
							}else{
								layer.msg(data.msg);
								return;
							}   
						});
					}  */
		  		}  else if(obj.event === 'updateIsRecommend'){
				  if(data.isRecommend==1){
						$.post('${ctx}/goods/updateGoods.do', {
							'goodsId' : data.goodsId,
							'isRecommend' : 0,
						}, function(data) {
							if (data.code==0) {
								layer.msg('取消推荐成功');
									//同步更新缓存对应的值
									 obj.update({
										 isRecommend : 0
									});
							}else{
								layer.msg(data.msg);
								return;
							}  
						});
				  } else{
					  $.post('${ctx}/goods/updateGoods.do', {
							'goodsId' : data.goodsId,
							'isRecommend' : 1,
						}, function(data) {
							if (data.code==0) {
								layer.msg('设置推荐成功');
									//同步更新缓存对应的值
									 obj.update({
										 isRecommend : 1
									});
							}else{
								layer.msg(data.msg);
								return;
							}  
						});
				  }
			  }else if(obj.event === 'updateIsScore'){
				  if(data.isScore==1){
						$.post('${ctx}/goods/updateIsScore.do', {
							'goodId' : data.goodsId,
							'isScore' : 0,
						}, function(data) {
							if (data.code==0) {
								layer.msg('操作成功');
									//同步更新缓存对应的值
									 obj.update({
										 isScore : 0
									});
							}else{
								layer.msg(data.msg);
								return;
							} 
						});
				  } else{
					  $.post('${ctx}/goods/updateIsScore.do', {
							'goodId' : data.goodsId,
							'isScore' : 1,
						}, function(data) {
							if (data.code==0) {
								layer.msg('操作成功');
									//同步更新缓存对应的值
									 obj.update({
										 isScore : 1
									});
							}else{
								layer.msg(data.msg);
								return;
							} 
						});
				  }
			  }else if(obj.event === 'recoveryOne'){
                         if(data.isSelled==1){
                     		$.post('${ctx}/goods/updateGoods.do', {
    							'goodsId' : data.goodsId,
    							'isSelled' : 0,
    						}, function(data) {
    							if (data.code==0) {
    								layer.msg('下架商品成功');
    									//同步更新缓存对应的值
    									 obj.update({
    										 isSelled : 0
    									});
    							}else{
    								layer.msg(data.msg);
    								return;
    							}  
    						});
                         } else{
                     		$.post('${ctx}/goods/updateGoods.do', {
    							'goodsId' : data.goodsId,
    							'isSelled' : 1,
    						}, function(data) {
    							if (data.code==0) {
    								layer.msg('上架商品成功');
    									//同步更新缓存对应的值
    									 obj.update({
    										isSelled : 1
    									});
    							}else{
    								layer.msg(data.msg);
    								return;
    							}  
    						});
                         }
			  } else {//后面需求
				  
			  }
		});

		  
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		
		
	    //是否审核该商品
		function goodsIsPassSell(type,cid){
			if(type ==1){
				layer.confirm('您确认取消审核商品么?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isPassSell' : 0,
					}, function(data) {
						if (data) {
							layer.msg("取消审核商品成功!");
							window.location.href="${ctx}/goods/goGoodsList.do?loginPlat="+$('#loginPlat').val()
							+"&goodsName="+$('#goodsName').val()+"&goodsType="+$('#goodsType').val()
							+"&isHot="+$('#isHot11').val()+"&isRecommend="+$('#isRecommend11').val();
						} else {
							layer.msg('审核商品失败', {
								time : 2500,
								icon : 0
							});

						}
					});
				}, function() {
					layer.closeAll();
				})
			}else{
				layer.confirm('您确认审核该商品?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isPassSell' : 1,
					}, function(data) {
						if (data) {
							layer.msg("审核商品成功!");
							window.location.href="${ctx}/goods/goGoodsList.do?loginPlat="+$('#loginPlat').val()
							+"&goodsName="+$('#goodsName').val()+"&goodsType="+$('#goodsType').val()
							+"&isHot="+$('#isHot11').val()+"&isRecommend="+$('#isRecommend11').val();
						} else {
							layer.msg('审核商品失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			}
		}
		function deleteone(cid) {
			layer.confirm('您确认删除该条商品信息?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
			}, function() {
				$.post('${ctx}/goods/deleteGoods.do', {
					'goodsIds' : cid,
				}, function(data) {
					if (data) {
						layer.msg('删除商品成功');
						window.location.href="${ctx}/goods/goGoodsList.do?loginPlat="+$('#loginPlat').val()+"appName="+$('#loginPlat1').val()
						+"&goodsName="+$('#goodsName').val()+"&goodsType="+$('#goodsType').val()
						+"&isHot="+$('#isHot11').val()+"&isRecommend="+$('#isRecommend11').val()+"&userKeyWords="+$('#userKeyWords').val();
					} else {
						layer.msg('删除商品失败', {
							time : 2500,
							icon : 0
						});
					}
				});
			})
		}
		
		function deleteGoods(gid){
			layer.confirm('您确认删除该商品?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/goods/updateGoods.do', {
					'goodsId' : gid,
					'goodsStatus' : 2,
				}, function(data) {
					if (data) {
						layer.msg('删除商品成功');
						setTimeout(function() {
							window.location.href="${ctx}/goods/goGoodsList.do?loginPlat="+$('#loginPlat').val()
							+"&goodsName="+$('#goodsName').val()+"&goodsType="+$('#goodsType').val()
							+"&isHot="+$('#isHot11').val()+"&isRecommend="+$('#isRecommend11').val()
							+"&userKeyWords="+$('#userKeyWords').val()+"&dateRange="+$("#dateRange").val();
						}, 400);
					} else {
						layer.msg('删除商品失败', {
							time : 2500
						});
					}
				});
			}, function() {
				layer.closeAll();
			})
		}
	</script>
</body>
</html>