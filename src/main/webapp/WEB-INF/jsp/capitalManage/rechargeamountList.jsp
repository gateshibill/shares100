<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>充值优惠列表</title>
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
	.layui-form-select .layui-input {
	    padding-right: 30px;
	    cursor: pointer;
	    width: 155px;
	    height: 38px;
    }
</style>
</head>
<body>
	<form class="layui-form" method="post" action="" id="listform">
		<blockquote class="layui-elem-quote">
			<div class="search-area">
				<ul>
					<li><label style="margin-left: 0px">应用名称：</label>
						<div style="float: left;">
							<select id="loginPlat" class="search-input" name="loginPlat"
								lay-verify="required" lay-search="">
								<option value=" ">全部</option>
								<c:forEach var="app" items="${appList}">
									<option value="${app.applicationId}">${app.applicationName}</option>
								</c:forEach>
							</select>
						</div></li>
				</ul>
							</div>
				
				<div class="layui-inline">
	        <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
			<a class="layui-btn layui-btn-normal newsAdd_btn">添加</a>
		</div>
		</blockquote>
	</form>
   <!-- -->
    <!-- <div class="layui-btn-group demoTable">
		<button class="layui-btn" data-type="addAllSeleted"> 
		<i class="layui-icon">&#xe608;</i>批量处理</button>
	</div> -->
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn layui-btn-mini" lay-event="editRecharge" >编辑</a> 
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delRecharge">删除</a>
</script>

<script type="text/html" id="isEffecttpl">
{{# var isEffect=d.isEffect}}
   {{# if(isEffect == 1){}}
     <span style="color:green">是</span>
   {{#}else{}}
        <span style="color:red">否</span>
   {{#}}}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table', 'laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------
			getlist('', '', '');
		});
		function doSearch() {
			var plat = $("#loginPlat").val();		
			getlist(plat);
		}

		function getlist(plat) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat':plat
				},
				url : '${ctx}/rechargeamount/getrechargelist.do', 
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				}, {
					field : 'discountId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'loginPlat',
					title : '应用id',
					width : 140,
					align : 'center'
				},
				{
					field : 'rechargeAmount',
					title : '充值金额',
					width : 100,
					align : 'center'
				}, {
					field : 'sendAmount',
					title : '赠送金额',
					width : 100,
					align : 'center'

				}, {
					field : 'isEffect',
					title : '是否有效',
					width : 100,
					align : 'center',
					templet:'#isEffecttpl'	

				}, {
					field : 'createTime',
					title : '添加时间',
					width : 200,
					align : 'center',
				},  {
					title : '操作',
					width : 150,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				} 
				] ],
				page : true,
				limits : [ 1, 10, 20, 30, 50, 100 ],
				limit : 15, //默认采用20
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
			if (obj.event === 'editRecharge') { //修改
				var index = layui.layer.open({
					title : "编辑充值优惠",
					type : 2,
					content : "${ctx}/rechargeamount/goupdatepage.do?discountId="+data.discountId,
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})	
				layui.layer.full(index);
			} else if (obj.event === 'delRecharge') { //删除
				layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index){
					  $.ajax({
						  url:"${ctx}/rechargeamount/dodeleamount.do",
						  type:"post",
						  dataType:"json",
						  data:{"discountId":data.discountId},
						  success:function(data){
							  if(data.code == 0){
								  layer.msg("删除成功");
								  setTimeout(function(){
									  window.location.reload(true);
								  },200);
							  }else{
								  layer.alert(data.msg);
							  }
						  },
						  error:function(data){
							  layer.alert("删除失败");
						  }
					  })
					  
					  layer.close(index);
					});
			} 
		});

		$(window).one("resize",function(){
			$(".newsAdd_btn").click(function(){
				var loginPlat = $("#loginPlat").val();
				if(!loginPlat || loginPlat == "" || loginPlat<=0){
					layer.msg("请选择应用");
					return;
				}
				var index = layui.layer.open({
					title : "添加充值优惠",
					type : 2,
					content : "${ctx}/rechargeamount/goaddpage.do?loginPlat="+loginPlat,
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
			})
		}).resize();  
	
		
		
	</script>
</body>
</html>