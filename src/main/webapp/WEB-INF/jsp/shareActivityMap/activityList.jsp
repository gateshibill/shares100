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
</head>
<body>
<blockquote class="layui-elem-quote news_search">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
		<div class="layui-inline">
			<label class="layui-form-label">发布者编号</label>	
		    <div class="layui-input-inline">
		    	<input type="text" value="" id="keyword" placeholder="请输入发布者编号" class="layui-input search_input" style="height:38px;line-height:38px">
		    </div>
		    <a class="layui-btn search_btn" onclick="doSearch()">查询</a>
		</div>
	</form>
</blockquote>
	<div class="layui-btn-group demoTable">
			<button class="layui-btn layui-btn-danger batchDel" data-type="dealAllSeleted"> 
			<i class="layui-icon">&#xe640;</i>批量删除</button>
		</div>
	<table class="layui-table" id="customer" lay-filter="customer"></table>
	<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-mini" href="${ctx}/shareActivity/goSeeActivity.do?activityId={{d.activityId}}">查看</a>

    <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteActivity({{d.activityId}})">删除</a>
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTpl">
    {{# var status=d.isPublic}}
    {{# if (status=='1'){}}
    	<a href="#" class="" onclick="updateIspublic(1,{{d.activityId}})"><span style="color:#1E9FFF;">是</span></a>
    {{# } else{ }}
   		<a href="#" class="" onclick="updateIspublic(0,{{d.activityId}})"><span style="color:red;">否</span></a>
    {{# } }}
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp3">
    {{# var status=d.application.applicationName}}
            {{status}}
</script>
	<!-- 处理状态模板 -->
	<script type="text/html" id="statusTp4">
    {{# var destype=d.activityStatus}}
    {{# if (destype=='1'){}}
    	进行中
    {{# } else if(destype=='2'){ }}
   		 <span style='color:red'>已结束</span>
 	{{# }
     }}
</script>
<script type="text/html" id="publisher">
  {{# if(d.user){}} 
     {{d.user.realName?d.user.realName:d.user.nickName}}
  {{#}else{}}
       <span style='color:red'>未知</span>
  {{#}}}
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
			var publisherId = $("#keyword").val();
			getlist(publisherId);
		}
		
		function getlist(publisherId) {
			tableIns = table.render({
				elem : '#customer',
				id : 'customer',
				method :'post',
				where:{
					'publisherId':publisherId
					
				},
				url : '${ctx}/shareActivity/showActivityList.do',
				cols : [ [ {
					checkbox : true,
					LAY_CHECKED : false,
					fixed : true
				} //其它参数在此省略
				, {
					field : 'activityId',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				},{
					field : 'activityDesc',
					title : '活动标题',
					width : 200,
					align : 'center',				
				},{
					field : 'publisherId',
					title : '发布者编号',
					width : 100,
					align : 'center',				
				},{
					field : 'publisherName',
					title : '发布者昵称',
					width : 180,
					align : 'center',
                    templet:'#publisher'					
				},{
					field : 'loginPlat',
					title : '应用',
					width : 180,
					align : 'center',
					templet : "#statusTp3"
				},
				 {
					field : 'activityStatus',
					title : '活动状态',
					width : 100,
					align : 'center',
 					templet : "#statusTp4"
				},{
					field : 'isPublic',
					title : '是否公开',
					width : 100,
					align : 'center',
					sort : true,
					templet : "#statusTpl"
				},{
					field : 'createTime',
					title : '发布时间',
					width : 180,
					align : 'center',
					sort : true,
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
				height :'auto',
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
		
		table.on('tool(customer)', function(obj) {
			var data = obj.data;
			
		});

		var $ = layui.$, active = {
				dealAllSeleted : function() { //验证是否全选
					var checkStatus = table.checkStatus('customer'), data = checkStatus.data;
					var goodsIds = "";
					for (var i = 0; i < data.length; i++) {
						goodsIds += data[i].activityId + ",";
					}
					;
					if (goodsIds == "") {
						layer.msg("请选择数据!");
					} else {
						//layer.alert(JSON.stringify(data));
						$.ajax({
							data : 'json',
							url : '${ctx}/shareActivity/batchDelActivity.do',
							type : "post",
							data : {
								activityIds : goodsIds
							},
							success : function(result) {
								if (result.code == '0') {
									layer.msg('批量删除商品成功!');
// 									layer.alert(result.msg);
									doSearch();
								} else {
									layer.msg(result.msg);
									doSearch();
								}
							}
						});
					}
				}
			};
		
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		
		
		$(window).one("resize",function(){
			$(".newsAdd_btn").click(function(){
				var index = layui.layer.open({
					title : "添加活动",
					type : 2,
					content : "${ctx}/shareActivity/addActivity.do", 
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
		
		//结束活动
		function updateStatus(id){
			layer.confirm('您确认结束该活动么?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
			}, function() {
				$.post('${ctx}/shareActivity/updateStatus.do', {
					'activityId' : id,
					'activityStatus' : 2,
				}, function(data) {
					if (data.code ==0) {
						layer.msg("结束该活动成功!");
						 setTimeout(function(){
				    		  window.location.reload();	  
				      },2000);
					} else {
						layer.msg('结束该活动已结束', {
							time : 2500,
							icon : 0
						});
					}
				});
			})
		}
		function updateIspublic(isPublic,id){
			if(isPublic == 1){
				layer.confirm('您确认取消公开活动么?', {
					btn : [ '取消', '确认' ]
				}, function() {
					layer.closeAll();
				}, function() {
					$.post('${ctx}/shareActivity/updateActivity.do', {
						'activityId' : id,
						'isPublic' : 0,
					}, function(data) {
						if (data.code ==0) {
							layer.msg("取消成功!");
							 setTimeout(function(){
					    		  window.location.reload();	  
					      },2000);
						} else {
							layer.msg('取消失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				})
			} else{
				layer.confirm('您确认公开该活动么?', {
					btn : [ '取消', '确认' ]
				}, function() {
					layer.closeAll();
				}, function() {
					$.post('${ctx}/shareActivity/updateActivity.do', {
						'activityId' : id,
						'isPublic' : 1,
					}, function(data) {
						if (data.code ==0) {
							layer.msg("公活动成功!");
							 setTimeout(function(){
					    		  window.location.reload();	  
					      },2000);
						} else {
							layer.msg('公活动失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				})
			}
		}
		//删除活动
		function deleteActivity(id){
			layer.confirm('您确认删除该活动么?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
			}, function() {
				$.post('${ctx}/shareActivity/deleteActivity.do', {
					'activityId' : id
				}, function(data) {
					if (data) {
						layer.msg("删除活动成功!");
						window.location.reload();
					} else {
						layer.msg('删除活动失败', {
							time : 2500,
							icon : 0
						});
					}
				});
			})
		}
	</script>
</body>
</html>