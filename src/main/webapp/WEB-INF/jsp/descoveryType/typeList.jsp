<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>layui</title>
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
			<label class="layui-form-label">类型名称</label>
		    <div class="layui-input-inline">
		    	<input type="text" id="keyword" placeholder="请输入类型名称" class="layui-input search_input" style="height:38px;line-height:38px;width:170px;">
		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
<!-- 		<div class="layui-inline"> -->
<%-- 			<a class="layui-btn layui-btn-normal" href="${ctx}/descovery/addtype.do">添加</a> --%>
<!-- 		</div> -->
	</form>
</blockquote>
	<!-- <a class="layui-btn layui-btn-mini" lay-event="edittaskcontent">编辑</a> -->
	<table class="layui-table" id="customer" lay-filter="customer"
		lay-data="{id: 'customer'}"></table>
	<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-mini" lay-event="goodsTypeOrder">排序</a>
  {{# var ispass=d.typeStatus}}
  {{# if (ispass=='1'){}}
          <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteGoodsType({{d.id}},1)">停用</a>
    {{# } else{ }}
        <a class="layui-btn layui-btn-mini" onclick="deleteGoodsType({{d.id}},2)" >启用</a>
    {{# } }}
 
</script>
<!-- typeNameTpl -->
<script type="text/html" id="typeNameTpl">
      {{# var typeName=d.typeName}}
     <a href="${ctx}/goods/goodsClassifyList.do?loginPlat={{d.loginPlat}}&goodsType={{d.typeId}}" style="color:#1E9FFF">{{typeName}}</a>  
</script>
	<script type="text/html" id="appNameTpl">
       {{#if(d.app){}}
              {{d.app.applicationName}}
       {{# }else{}}
        <span style="color:red"> 所有应用   </span>   
       {{#}}}
</script>
	<script type="text/html" id="nickNameTpl">
       {{# var destype=d.typeStatus}}
    {{# if (destype=='1'){}}
    	使用中
    {{# } else if(destype=='0'){ }}
   		<span style="color:red">已停用</span>
 	{{# }  }}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
		layui.use([ 'layer', 'table','laydate' ], function() {
			table = layui.table;
			layer = layui.layer;
			var laydate = layui.laydate;
			//--------------方法渲染TABLE----------------
			getlist('','','','','','');
		});
		function doSearch() {
			var name = $("#keyword").val();
			var plat =$("#loginPlat").val();
			getlist(name,plat);
		}
		
		function getlist(name,plat) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method :'post',
				where:{
					'typeName':name,
					'loginPlat':plat
				},
				url : '${ctx}/descovery/descoveryTypeList.do',
				cols : [ [  {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'typeId',
					title : '类型编号',
					width : 120,
					align : 'center',
				}, {
					field : 'typeName',
					title : '类型名称',
					width : 200,
					align : 'center',
				}, 
				 {
					field : 'appName',
					title : '应用名称',
					width : 200,
					align : 'center',
					templet : "#appNameTpl"
				},
				{
					field : 'nickName',
					title : '发布状态',
					width : 200,
					align : 'center',
					templet : "#nickNameTpl"
				},{
					field : 'orderNo',
					title : '排列顺序',
					width : 150,
					align : 'center',
				},{
					field : 'createTime',
					title : '创建时间',
					width : 200,
					align : 'center'
				},{
					title : '操作',
					width : 200,
					align : 'center',
					fixed : 'right',
					toolbar : '#barDemo'
				} ] ],
				page : true,
				limits : [ 1, 10, 20, 30, 50, 100 ],
				limit : 20, //默认采用20
				width : '100%',
				height : 'auto',
				loading: true,
				response : {
					statusName : 'code' //数据状态的字段名称，默认：code
					,
					statusCode : 0 //成功的状态码，默认：0
					,
					msgName : 'msg' //状态信息的字段名称，默认：msg
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
		
		layui.use('table',function() {
							var table = layui.table;
							//监听表格复选框选择
							table.on('checkbox(demo)', function(obj) {
								console.log(obj)
							});
							//监听工具条
							table.on('tool(demo)', function(obj) {
								var data = obj.data;
								if (obj.event === 'detail') {
									layer.msg('ID：' + data.id + ' 的查看操作');
								} else if (obj.event === 'del') {
									layer.confirm('真的删除行么', function(index) {
										obj.del();
										layer.close(index);
									});
								} else if (obj.event === 'edit') {
									layer.alert('编辑行：<br>'
											+ JSON.stringify(data))
								}
							});

							var $ = layui.$, active = {
								getCheckData : function() { //获取选中数据
									var checkStatus = table
											.checkStatus('idTest'), data = checkStatus.data;
									layer.alert(JSON.stringify(data));
								},
								getCheckLength : function() { //获取选中数目
									var checkStatus = table
											.checkStatus('idTest'), data = checkStatus.data;
									layer.msg('选中了：' + data.length + ' 个');
								},
								isAll : function() { //验证是否全选
									var checkStatus = table
											.checkStatus('idTest');
									layer.msg(checkStatus.isAll ? '全选' : '未全选')
								}
							};

							$('.demoTable .layui-btn').on('click', function() {
								var type = $(this).data('type');
								active[type] ? active[type].call(this) : '';
							});
						});

		table.on('tool(customer)', function(obj) {
			var data = obj.data;
			if (obj.event === 'edittaskcontent') {
				var index = layui.layer.open({
					title : "编辑商品类型",
					type : 2,
					content : "${ctx}/goods/updaGoodsType.do?typeId="+data.typeId,/* +"&contentText="+data.contentText */
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
				//window.location.href="${ctx}/sharetask/gotaskdetail.do?taskId="+data.taskId;  //查看操作
			} else if(obj.event === 'goodsTypeOrder'){
				layer.confirm(
						'<div class="layui-inline">'
								+ '<label class="out-form-label">排列序号：</label>'
								+ '<div class="layui-input-inline">'
								+ '<input type="number" id="orderId" min="1" name="number" lay-verify="number" autocomplete="off" class="layui-input">'
								+ '</div>' + '</div>', {
							btn : [ '确认', '取消' ]
						}, function() {
							$.post('${ctx}/descovery/updateTypeOrder.do', {
								'id' : data.id,
								'orderNo' : $("#orderId").val(),
							}, function(data) {
								if (data.code==0) {
									layer.msg('修改排序成功');
									//同步更新缓存对应的值
									 obj.update({
									 orderNo : $("#orderId").val(),
									});
								} else {
									layer.msg(data.msg,{time:2000});
								}
							});
						}, function() {
							layer.closeAll();
						})
			}
		});
		
		function deleteGoodsType(tid,type){
			if(type ==1){
				layer.confirm('您确认停用该发布类型么?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/descovery/updateTypeOrder.do', {
						'id' : tid,
						'typeStatus':0
					}, function(data) {
						if (data.code==0) {
							layer.msg('停用发布类型成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg(data.msg, {
								time : 400
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			} else{
				layer.confirm('您确认启用该发布类型?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/descovery/updateTypeOrder.do', {
						'id' : tid,
						'typeStatus':1
					}, function(data) {
						if (data.code==0) {
							layer.msg('启用发布类型成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg(data.msg, {
								time : 400
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			}
		}
	</script>
</body>
</html>