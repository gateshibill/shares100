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
	.twoChildType{
		display:none;
	}
</style>
</head>
<body style="width:90%;margin:20px auto">
	<blockquote class="layui-elem-quote">
		<form class="layui-form layui-form-pane" action="">
			<div class="layui-form-item">
			  
				<div class="layui-inline pane-select">
					<label class="layui-form-label">一级分类</label>
					<div class="layui-input-block ">
						<select id="goodsType" name="goodsType" class="search-select" lay-search="" lay-filter="goodsType">
								<option value="">全部</option>
								<c:forEach var="app" items="${typeList}">
									<c:choose>
										<c:when test="${goods.goodsType==app.typeId}">
											<option selected="selected" value="${app.typeId}">${app.typeName}</option>
										</c:when>
										<c:otherwise>
											<option value="${app.typeId}">${app.typeName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
					</div>
				</div>			
				<div class="layui-inline pane-select twoChildType" hidden="true">
					<label class="layui-form-label">二级分类</label>
					<div class="layui-input-block">
						<select name="goodChildType" id="goodChildType"
							autocomplete="off" class="layui-input" lay-search="">
							<option value="">请选择类型</option>
							
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
				
		
			
				<div class="layui-inline">
					<a class="layui-btn search_btn" onclick="doSearch()"> <i
						class="layui-icon">&#xe615;</i>查询
					</a>
				</div>
			</div>
	</form>
	</blockquote>
	
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	
	<script type="text/html" id="toolBar">
		<a class="layui-btn layui-btn-normal layui-btn-mini" lay-event="chooseThis">选择</a>
	</script>
<script type="text/html" id="goodsTypeTpl">
     {{# var tj=d.gType.typeName}}
    {{# if (tj!='null'){}}
        {{tj}}
    {{# } else{ }}
   		 没有找到
    {{# } }}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	
	<script>
	var collId = "${collId}";
		layui.use(['form','layer', 'table'], function() {
			form = layui.form,
			table = layui.table;
			layer = layui.layer;
			
			//--------------方法渲染TABLE----------------
			var type = $("#goodsType").val();
			var name = $("#goodsName").val();
			var goodsId = $("#goodsId").val();
			var goodChildType = $("#goodChildType").val();
			getlist(type,goodChildType,name,goodsId);
			//分类二级联动
			form.on('select(goodsType)',function(data){
				$(".twoChildType").hide();
				 var parentId = data.value;
				 if(!parentId){
					  layer.msg("请选择一级分类");
					  return false;
				 }
				 var typehtml = "";
				 typehtml += '<option value="">请选择</option>';
				 $.ajax({
					 url:'${ctx}/goods/getChildType.do',
					 type:'post',
					 dataType:'json',
					 data:{parentId:parentId},
					 success:function(res){
						 if(res.code == 0){ //获取数据
							 $(".twoChildType").show();
							 $(".twoChildType").css("display","inline-block");
							 var object = res.objects;
						 	 for(var i=0;i<object.length;i++){
						 		 typehtml+= '<option value="'+object[i].typeId+'">'+object[i].typeName+'</option>';
						 	 }
						 	 $("#goodChildType").html(typehtml);
						 	 form.render();
						 }else{
							 //layer.msg("该无分类,请先添加商品分类在添加商品");
							 //return;
						 }
					 }
					 ,error:function(res){
						 
					 }
				 })
			})
		});
		function doSearch() {
			var type = $("#goodsType").val();
			var name = $("#goodsName").val();
			var goodsId = $("#goodsId").val();
			var goodChildType = $("#goodChildType").val();
			getlist(type,goodChildType,name,goodsId);
		}

		function getlist(goodsType,goodChildType,goodsName,goodsId) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'goodsType' : goodsType,
					'goodChildType':goodChildType,
					'goodsName' : goodsName,
					'isSelled' : 1,
					'goodsId':goodsId
				},
				url : '${ctx}/goods/goodsList.do',
				cols : [ [ 

				{
					field : 'goodsId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				},{
					field : 'goodsType',
					title : '商品类型',
					width : 150,
					align : 'center',
					templet : "#goodsTypeTpl"
				},{
					field : 'goodsName',
					title : '商品名称',
					width : 140,
					align : 'center'
				},{
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
				limit : 8, //默认采用20
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
				window.location.href="${ctx}/goods/goodsDetails.do?goodsId="+data.goodsId;
			} else if(obj.event === 'chooseThis'){
				$.post('${ctx}/coll/chooseGoodColl.do', {
					'goodId' : data.goodsId,
					'collId' : collId,
				}, function(data) {
					if (data.code==0) {
						layer.msg('关联产品成功');
						setTimeout(function(){
							parent.getlist(collId);
						},1000);	
					}else{
						layer.msg(data.msg);
						return;
					}   
				});
			  } else {//后面需求
				  
			  }
		});

		  
		
	  
	</script>
</body>
</html>