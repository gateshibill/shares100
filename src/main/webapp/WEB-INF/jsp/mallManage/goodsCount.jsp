<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>商品统计</title>
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
									<option value="${app.applicationId}">${app.applicationName}</option>
								</c:forEach>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">商品类型</label>
					<div class="layui-input-block">
						<select id="goodsType" name="goodsType" class="search-select">
								<option value="">全部</option>
								<c:forEach var="app" items="${typeList}">
									<option value="${app.typeId}">${app.typeName}</option>
								</c:forEach>
							</select>
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">商品名称</label>
					<div class="layui-input-block">
						<input type="text" name="goodsName" id="goodsName"
								placeholder="请输入商品名称" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布日期</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="dateRange"
								id="dateRange" placeholder=" - ">
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
	<span style="height: 40px;padding: 0 18px; color: #919191; margin-top: 0;border-radius:0; font-size: 14px;margin-left:0">商品销量总计：<span id="goodsCount"></span>件</span>
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="descoveryDetail" >查看</a>
</script>
	<!-- 上架状态模板 -->
	<script type="text/html" id="isshangjiaTpl">
    {{# var issj=d.isSelled}}
    {{# if (issj=='0'){}}
    	<a href="#" style="color:#009688" class="" onclick="recoveryOne({{d.goodsId}},{{d.isSelled}})">下架</a>
    {{# } else{ }}
        <a href="#" class="" onclick="recoveryOne({{d.goodsId}},{{d.isSelled}})"><span style="color:red;">上架</span></a>
    {{# } }}
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="ispassTpl">
    {{# var ispass=d.isPassSell}}
    {{# if (ispass=='0'){}}
    	<span style="color:red;">未审核</span>
    {{# } else{ }}
   		已审核
    {{# } }}
</script>	
<!-- 是否热销模板 -->
	<script type="text/html" id="isRecommendTpl">
    {{# var tj=d.isHot}}
    {{# if (tj=='0'){}}
<a href="#" style="color:#009688" class="" onclick="updateIsHot({{d.goodsId}},{{d.isHot}})">否</a>
    	<span style="color:red;"></span>
    {{# } else if(tj=='1'){ }}
   		 <a href="#" class="" onclick="updateIsHot({{d.goodsId}},{{d.isHot}})"><span style="color:red;">是</span></a>
    {{# } }}
</script>
<!-- 是否热销模板 -->
	<script type="text/html" id="isHotTpl">
    {{# var tj=d.isRecommend}}
    {{# if (tj=='0'){}}
<a href="#" style="color:#009688" class="" onclick="updateisRecommend({{d.goodsId}},{{d.isRecommend}})">否</a>
    	<span style="color:red;"></span>
    {{# } else if(tj=='1'){ }}
   		 <a href="#" class="" onclick="updateisRecommend({{d.goodsId}},{{d.isRecommend}})"><span style="color:red;">是</span></a>
    {{# } }}
</script>
	<script type="text/html" id="nicknameTpl">
       {{#if(d.goodsUser){}}
              {{d.goodsUser.nickName}}
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
			pid = $("#loginPlat").val();
			getlist(pid, '', '', '', '', '', '');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var lp2 = $("#loginPlat1").val();
			var type = $("#goodsType").val();
			var isreco = $("#isRecommend11").val();
			var ishot = $("#isHot11").val();
			var name = $("#goodsName").val();
			var dtr = $("#dateRange").val();
			var ukw=$("#userKeyWords").val();
			getlist(pid, lp2, type, isreco,ishot, name, dtr,ukw);
		}

		function getlist(pid, loginPlat2, goodsType, isRecommend, isHot,goodsName,DateRange,userKeyWords) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'loginPlat1' : loginPlat2,
					'goodsType' : goodsType,
					'isHot' : isHot,
					'isRecommend' : isRecommend,
					'goodsName' : goodsName,
					'dateRange' : DateRange,
					'userName' : userKeyWords
				},
				url : '${ctx}/mallcount/showGoodsList.do',
				cols : [ [
// 				{
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'goodsId',
					title : 'ID',
					width : 80,
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
					width : 140,
					align : 'center'
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
					templet : "#isRecommendTpl"
				}, {
					field : 'isRecommend',
					title : '是否推荐',
					width : 100,
					align : 'center',
					templet : "#isHotTpl"
				},{
					field : 'publisherId',
					title : '发布者ID',
					width : 100,
					align : 'center'
				}, {
					field : 'realName',
					title : '发布者姓名',
					width : 100,
					align : 'center',
					templet : "#nicknameTpl"
				}, {
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
					width : 120,
					align : 'center',
					fixed : 'right',
					toolbar : '#toolBar'
				} ] ],
				page : true,
				limits : [ 1, 10, 20, 30, 50, 100 ],
				limit : 20, //默认采用20
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
					$('#goodsCount').text(res.goodsCount);
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
			} else if (obj.event === 'recommend') {
				$.ajax({
					data : 'json',
					url : '${ctx}/descovery/tjtoSame.do',
					type : "post",
					data : {
						descoveryId : data.descoveryId
					},
					success : function(result) {
						if (result.code == '0') {
							layer.msg(result.msg);
							//同步更新缓存对应的值
							obj.update({
								isRecommend : 1
							});
						} else {
							layer.msg(result.msg);
							return;
						}
					}
				});
			} else if (obj.event === 'deleteDescovery') {
				$.ajax({
					data : 'json',
					url : '${ctx}/goods/deleteGoods.do',
					type : "post",
					data : {
						'goodsIds' : data.goodsId
					},
					success : function(result) {
						if (result.code == '0') {
							layer.msg(result.msg);
							window.location.reload();
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
							window.location.reload();
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
							window.location.reload();
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
						setTimeout(function() {
							var html = template("gscript", data);
							$('#gbody').html(html);
						}, 400);
					} else {
						layer.msg('删除商品失败', {
							time : 2500,
							icon : 0
						});
					}
				});
			})
		}
		
		//是否推荐
		function updateisRecommend(cid,type){
			if(type==1){
				layer.confirm('您确认取消推荐该商品?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isRecommend' : 0,
					}, function(data) {
						if (data) {
							layer.msg('取消推荐商品成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg('取消推荐商品失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			}else{
				layer.confirm('您确认推荐该商品?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isRecommend' : 1,
					}, function(data) {
						if (data) {
							layer.msg('推荐商品成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg('推荐商品失败', {
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
		//是否热销
		function updateIsHot(cid,type){
			if(type==1){
				layer.confirm('您确认取消热销该商品?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isHot' : 0,
					}, function(data) {
						if (data) {
							layer.msg('取消热销商品成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg('取消热销商品失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			}else{
				layer.confirm('您确认热销该商品?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isHot' : 1,
					}, function(data) {
						if (data) {
							layer.msg('热销商品成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg('热销商品失败', {
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
		function recoveryOne(cid,type) {
			if(type==1){
				layer.confirm('您确认下架该商品?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isSelled' : 0,
					}, function(data) {
						if (data) {
							layer.msg('下架商品成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg('下架商品失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				}, function() {
					layer.closeAll();
				})
			}else{
				layer.confirm('您确认上架该商品?', {
					btn : [ '确认', '取消' ]
				}, function() {
					$.post('${ctx}/goods/updateGoods.do', {
						'goodsId' : cid,
						'isSelled' : 1,
					}, function(data) {
						if (data) {
							layer.msg('上架商品成功');
							setTimeout(function() {
								window.location.reload();
							}, 400);
						} else {
							layer.msg('恢复商品失败', {
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
							window.location.reload();
						}, 400);
					} else {
						layer.msg('删除商品失败', {
							time : 2500,
							icon : 0
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