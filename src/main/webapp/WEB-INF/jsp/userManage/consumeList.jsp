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
	
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<!--  
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn layui-btn-mini" lay-event="descoveryDetail" >邀请列表</a>
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="setAgent" >消费列表</a>

</script>
-->
<script type="text/html" id="usersexTpl">
    {{# var issj=d.userSex}}
    {{# if (issj=='1'){}}
    	男
    {{# } else{ }}
   		女
    {{# } }}
</script>
	<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script>
	var loginPlat = "${loginPlat}";
	var agentId = "${agentId}";
	layui.use([ 'layer', 'table', 'laydate' ], function() {
		table = layui.table;
		layer = layui.layer;
		var laydate = layui.laydate;
			//日期范围
			laydate.render({
				elem : '#test6',
				range : true
			});
			//--------------方法渲染TABLE----------------
			getlist(loginPlat,agentId);
		});
		
		function getlist(pid,uid) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'agentId' : uid,
					
				},
				url : '${ctx}/user/getvisitconsumelist.do',
				cols : [ [ 
// 				  {
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
                   {
					field : 'orderID',
					title : 'ID',
					width : 100,
					align : 'center',
					fixed : true,
					sort : true
				},{
					field : 'realFee',
					title : '实际支付',
					width : 140,
					align : 'center',
				},{
					field : 'rebateMoney',
					title : '返点利润',
					width : 140,
					align : 'center',
				}, {
					field : 'phone',
					title : '联系方式',
					width : 140,
					align : 'center',
				}, {
					field : 'loginPlat',
					title : '平台id',
					width : 140,
					align : 'center'
				},{
					field : 'payTime',
					title : '支付时间',
					width : 180,
					align : 'center',
					sort : true,
				}
				/* , {
					title : '操作',
					width : 170,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				}  */
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
			if (obj.event === 'descoveryDetail') {
				window.location.href="${ctx}/user/userDetails.do?userId="+data.userId;
			} else if (obj.event === 'passDescovery') {
				if(data.userStatus==1){
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							userStatus:2
							
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('禁用用户成功');
								//同步更新缓存对应的值
								obj.update({
									userStatus : 2
								});
							} 
						}
					});
				} else {
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							userStatus:1
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('恢复用户成功');
								//同步更新缓存对应的值
								obj.update({
									userStatus : 1
								});
							} 
						}
					});
				}
			} else if (obj.event === 'nopassDescovery') {
				if(data.isManager==0){
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							isManager:1
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('设置管理成功');
								//同步更新缓存对应的值
								obj.update({
									isManager : 1
								});
							} 
						}
					});
				} else {
					$.ajax({
						data : 'json',
						url : '${ctx}/user/updateUseer.do',
						type : "post",
						data : {
							userId : data.userId,
							isManager:0
						},
						success : function(data) {
							if (data.code == '0') {
								layer.msg('取消管理成功');
								//同步更新缓存对应的值
								obj.update({
									isManager : 0
								});
							} 
						}
					});
				}
			}else if(obj.event === 'setAgent'){
				 alert("该功能暂未开发");
			} else if (obj.event === 'deleteDescovery') {
				$.ajax({
					data : 'json',
					url : '${ctx}/withdraw/dealWithdraw.do',
					type : "post",
					data : {
						withdrawId : data.withdrawId
					},
					success : function(result) {
						if (result.code == '0') {
							layer.msg(result.msg);
							//同步更新缓存对应的值
							obj.update({
								withdrawState : 2
							});
						} else {
							layer.msg(result.msg);
							return;
						}
					}
				});
			}
		});

		 var $ = layui.$, active = {
			addDescovery : function() { //验证是否全选
				if($("#descoveryType").val()==""){
					layer.msg("请选择要发布的发现类型");
				}else{
					window.location.href='${ctx}/descovery/goAddDescovery.do?descoveryType='+$("#descoveryType").val()+'&id='+$("#loginPlat").val()+'';
				}
			}
		}; 
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		
		//删除用户
		function deleteUser(userId){
			layer.confirm('您确认删除该用户么?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
			}, function() {
				$.post('${ctx}/user/deleteUserId.do', {
					'userId' : userId
				}, function(data) {
					if (data) {
						layer.msg('删除用户成功');
						setTimeout(function() {
							window.location.reload();
						}, 1000);
					} else {
						layer.msg('删除失败', {
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