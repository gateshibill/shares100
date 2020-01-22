<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>拼团商品管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
	<style type="text/css">
	.layui-text{height:42px;line-height:42px;padding:0 15px;}
	 form{padding:0 15px;}
	.backBtn{float:right;}
</style>
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
拼团商品列表
<p onclick="goback()" class="backBtn">
<i class="layui-icon" style="font-size:18px;color:#333;font-weight:bold">&#x1006;</i>  
</p>
</blockquote>
	<form class="layui-form layui-form-pane" action="">
			<div class="layui-form-item">
				<div class="layui-inline pane-input">
					<label class="layui-form-label">拼团ID</label>
					<div class="layui-input-block ">
						<input type="text" id="keyWords" name="keyWords"
							placeholder="请输入拼团ID" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<a class="layui-btn search_btn" onclick="doSearch()"> <i
						class="layui-icon">&#xe615;</i>查询
					</a>
				</div>
			</div>
	</form>
	<div>
		<button id="addArticle" style="margin-left:10px;" class="layui-btn layui-btn-primary">添加商品</button>
	</div>
	<table class="layui-table" id="Article" lay-filter="Article"
		lay-data="{id: 'Article'}"></table>
	<script type="text/html" id="toolBar">
		<a class="layui-btn layui-btn-mini" lay-event="editArticle" >编辑</a>
		<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="delAritcle" >删除</a>
	</script>
	<script type="text/html" id="articleTypeTpl">
    
	</script>
	<script type="text/html" id="isEffectTpl">
    {{# if (d.isEffect==1){}}
     	是
    {{# } else { }}
                             否
    {{# } }}
	</script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js""></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.all.js"></script>
	<script>
	 var articleType = '${articleType}';
		layui.use([ 'layer', 'table' ], function() {
			table = layui.table;
			layer = layui.layer;
			//--------------方法渲染TABLE----------------日期范围
			
			getlist(null,articleType);
		});
		function doSearch() {
			var keyWords = $("#keyWords").val();
			getlist(keyWords,articleType);
		}

		function getlist(keyWords) {
			tableIns = table.render({
				elem : '#Article',
				id : 'Article',
				method : 'post',
				where : {
					'articleTitle' : keyWords,
					'articleType':articleType
				},
				url : '${pageContext.request.contextPath}/wx/activity/getAllPinGoodsList.do',
				cols : [ [ {
					field : 'id',
					title : '拼团ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'appId',
					title : '应用ID',
					width : 140,
					align : 'center'
				}, {
					field : 'goodsId',
					title : '商品ID',
					width : 140,
					align : 'center',
					templet : 'center'
				},{
					field : 'price',
					title : '价格',
					width : 140,
					align : 'center'
				},{
					field : 'validate',
					title : '活动有效',
					width : 140,
					align : 'center',
					templet : 'center'
				},{
					field : 'status',
					title : '状态',
					width : 140,
					align : 'center'
				},{
					title : '操作',
					width : 200,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				} ] ],
				page : true,
				limits : [ 12, 15, 18, 20,30,50],				
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
				}
			});
		}
		table.on('tool(Article)', function(obj) {
			var data = obj.data;
			if (obj.event === 'editArticle') {
				var index = layui.layer.open({
					title : "编辑拼团商品",
					type : 2,
					content :'${pageContext.request.contextPath}/wx/activity/goUpdatePinGoodsById.do?id='+data.id,
					success : function(layero, index) {
						setTimeout(function() {
							layui.layer.tips('点击此处返回',
									'.layui-layer-setwin .layui-layer-close', {
										tips : 3
									});
						}, 500)
					}
				})
				layui.layer.full(index);
			}else if(obj.event === 'delAritcle'){
 				layer.confirm('确认删除该拼团商品，该过程不可逆', {icon: 3, title:'提示'}, function(index){
					  $.ajax({
						  url:'${pageContext.request.contextPath}/wx/activity/deletePinGoodsById.do?id='+data.id,						
						  type:'post',
						  dataType:'json',
						  success:function(data){
								window.location.reload();
						  },
						  error:function(data){
							  
						  }
					  })
					  layer.close(index);
 					});
			}
		});
		$(window).one("resize",function(){
			$("#addArticle").click(function(){
				var index = layui.layer.open({
					title : "添加商品",
					type : 2,
					content : "#",
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
		
		function goback(){
			history.go(-1);
		}
	</script>
</body>
</html>