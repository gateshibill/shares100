<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>推送模板列表</title>
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
</head>
<body>
<blockquote class="layui-elem-quote news_search">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
		<div class="layui-inline">
			<label class="layui-form-label">应用名称</label>
		    <div class="layui-input-inline">
		    	<div style="float:left;">
					<select id="loginPlat" class="layui-input search_input" name="loginPlat" lay-search="">
					   <option value="">全部</option>
						<c:forEach var="app" items="${appList}">
						  <option value="${app.applicationId}">${app.applicationName}</option>
						</c:forEach>
					 </select>
				</div>
		    </div>
		</div>
 		<div class="layui-inline"> 
 			<a class="layui-btn search_btn" onclick="doSearch()">查询</a>
 			<a class="layui-btn layui-btn-normal newsAdd_btn" href="javascript:void(0)">添加</a> 
		</div>
	</form>
</blockquote>
	<table class="layui-table" id="affiche" lay-filter="affiche"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn layui-btn-mini" lay-event="editpush" >编辑</a>
	<a  class="layui-btn layui-btn-primary layui-btn-mini" lay-event="delpush">删除</a>
</script>
	
<script type="text/html" id="isEffect">
       {{#if(d.isEffect == 1){}}
             <span style="color:green">是</span>
       {{# }else{}}
        	<span style="color:red">否</span>   
       {{#}}}
</script>
<script type="text/html" id="tempTypeTpl">
       {{#if(d.tempType == 1){}}
                                       订单购买成功通知
	   {{# } else if(d.tempType == 2){}}
			订单购买失败通知
	   {{# } else if(d.tempType == 3){}}
			订单发货通知
	   {{# } else if(d.tempType == 4){}}
			交易提醒通知
	   {{# } else if(d.tempType == 10){}}	
			微信服务号收到新订单通知【餐饮】
	   {{# } else if(d.tempType == 11){}}
			留言通知
	   {{# } else if(d.tempType == 12){}}
			留言回复通知
       {{# }else{}}
        	暂无模板
       {{#}}}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table', 'laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
		
			//--------------方法渲染TABLE----------------
			pid = $("#loginPlat").val();
			getlist(pid, '', '', '', '', '', '', '');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			getlist(pid);
		}

		function getlist(pid) {
			tableIns = table.render({
				elem : '#affiche',
				id : 'affiche',
				method : 'post',
				where : {
					'loginPlat' : pid
				},
				url : '${ctx}/pushmessage/gettemplatelist.do',
				cols : [ [ 
// 				 {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'id',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'loginPlat',
					title : '应用id',
					width : 180,
					align : 'center'
				}, {
					field : 'tempType',
					title : '是否有效',
					width : 200,
					align : 'center',
					templet : "#tempTypeTpl"
				}, {
					field : 'templateId',
					title : '模板id',
					width : 300,
					align : 'center',
				}, {
					field : 'isEffect',
					title : '是否有效',
					width : 200,
					align : 'center',
					templet : "#isEffect"
				}, {
					field : 'createTime',
					title : '创建时间',
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
				limits : [ 10, 15, 20, 30, 50, 100 ],
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
		
		
		$(window).one("resize",function(){
			$(".newsAdd_btn").click(function(){
				var loginPlat = $("#loginPlat").val();
				if(!loginPlat){
					layer.msg("请先选择应用");
					return;
				}
				var index = layui.layer.open({
					title : "添加模板",
					type : 2,
					content : "${ctx}/pushmessage/goaddtemplate.do?loginPlat="+loginPlat,
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
		
		table.on('tool(affiche)',function(obj) {
							var data = obj.data;
							if (obj.event === 'editpush') { //编辑
								var index = layui.layer.open({
									title : "编辑模板",
									type : 2,
									content : "${ctx}/pushmessage/goedittemplate.do?id="+data.id,
									success : function(layero, index){
										setTimeout(function(){
											layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
												tips: 3
											});
										},500)
									}
								})			
								layui.layer.full(index);
							}else if(obj.event === 'delpush'){ //下载
								layer.confirm('您确认删除该条模板信息', {
								    btn: ['确认','取消'], 
								    shade: false //不显示遮罩
								}, function(){
								     $.ajax({
								    	 url:"${ctx}/pushmessage/deltemplate.do",
								    	 type:"post",
								    	 dataType:"json",
								    	 data:{"id":data.id},
								    	 success:function(data){
								    		 if(data.code == 0){
								    			 layer.msg("删除模板成功");
								    			 setTimeout(function(){
								    				 window.location.reload();
								    			 },400);
								    		 }else{
								    			 layer.msg(data.msg);
								    		 }
								    	 },
								    	 error:function(data){
								    		 layer.msg(data.msg);
								    	 }
								     })
								}, function(){
								    //layer.msg('奇葩么么哒', {shift: 6});
								});
							}
	      });

		
	
	</script>
</body>
</html>