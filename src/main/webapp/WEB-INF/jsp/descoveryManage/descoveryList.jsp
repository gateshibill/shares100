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
	.layui-form-item{
		margin-bottom:0px;
	}
</style>
</head>
<body>
	<blockquote class="layui-elem-quote">
	<form class="layui-form layui-form-pane" method="post" action="" id="listform">
			<div class="layui-form-item">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">应用名称</label>
					<div class="layui-input-block ">
						<select id="loginPlat" class="search-input" name="loginPlat"
								lay-verify="required" lay-search="">
								<option value="" selected="selected">全部</option>
								<c:forEach var="app" items="${appList}">
									<c:choose>
										<c:when test="${desc.loginPlat==app.applicationId}">
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
				
				
				
				<div class="layui-inline">
					<label class="layui-form-label">应用编号</label>
					<div class="layui-input-block">
						<input type="text" name="loginPlat1" id="loginPlat1"
								placeholder="请输入应用编号" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发现类型</label>
					<div class="layui-input-block">
						<select id="descoveryType" name="descoveryType"
								class="search-input">
								<option value="" selected="selected">全部</option>
								<c:forEach var="type" items="${typeList}">
									<c:choose>
										<c:when test="${desc.descoveryType==type.typeId}">
											<option selected="selected" value="${type.typeId}">${type.typeName}</option>
										</c:when>
										<c:otherwise>
											<option value="${type.typeId}">${type.typeName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">是否显示</label>
					<div class="layui-input-block">
						<select id="isShangJia" name="isShangJia" class="search-select">
								<option value="" selected="selected">全部</option>
								<option value="0"
									<c:choose>
										<c:when test="${desc.isShangJia==0}">selected="selected"</c:when>
									</c:choose>>否
								</option>
								<option value="1"
									<c:choose>
										<c:when test="${desc.isShangJia==1}">selected="selected"</c:when>
									</c:choose>>是
								</option>
							</select>
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">发现编号</label>
					<div class="layui-input-block">
						<input type="text" name="descoveryId" id="descoveryId"
								placeholder="请输入发现编号" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发现标题</label>
					<div class="layui-input-block">
						<input type="text" name="descoveryTitle" id="descoveryTitle"
								placeholder="请输入发现标题" class="layui-input" value="${desc.descoveryTitle}">
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">发布者名称</label>
					<div class="layui-input-block">
						<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入发布者名称" class="layui-input">
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
	<div class="layui-btn-group demoTable">
		<button class="layui-btn" style="margin-left: 10px;"
			data-type="addDescovery">
			<i class="layui-icon">&#xe608;</i>发布&nbsp;发现
		</button>
	</div>
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
	<a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="descoveryDetail" >查看</a>
	{{# var ispass=d.isPass}}
    {{# if (ispass=='0'){}}
    	
    {{# } else{ }}
    {{# } }}
	{{# if (ispass=='1'){}}
    	
    {{# } else{ }}
    {{# } }}
	
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="deleteDescovery">删除</a>

</script>
	<!-- 上架状态模板 -->
	<script type="text/html" id="isshangjiaTpl">
    {{# var issj=d.isShangJia}}
    {{# if (issj=='0'){}}
    	<a href="#" lay-event="zxisshangjia" ><span style="color:red;">下架</span></a>
    {{# } else{ }}
   		<a href="#" style="color:#009688" lay-event="zxisshangjia">上架</a>
    {{# } }}
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="ispassTpl">
    {{# var ispass=d.isPass}}
    {{# if (ispass=='0'){}}
    	<span style="color:red;">未审核</span>
    {{# } else{ }}
   		已审核
    {{# } }}
</script>
	<!-- 发现类型模板 -->
	<script type="text/html" id="dtypeTpl">
    {{# var destype=d.descoveryType}}
    {{# if (destype=='1'){}}
    	项目
    {{# } else if(destype=='2'){ }}
   		产品
 	{{# } else if(destype=='3'){ }}
   		技能
 	{{# } else if(destype=='4'){ }}
   		招聘
 	{{# } else if(destype=='5'){ }}
   		资讯
    {{# } else if(destype=='8'){ }}
   		指南
    {{# } else if(destype=='9'){ }}
   		问答
    {{# } }}
</script>	<!-- 是否推荐模板 -->
	<script type="text/html" id="isRecommendTpl">
    {{# var tj=d.isRecommend}}
    {{# if (tj=='0'){}}
    	未推荐
    {{# } else if(tj=='1'){ }}
   		已推荐
    {{# } }}
</script>
	<script type="text/html" id="nicknameTpl">
       {{#if(d.dscvPublisher){}}
              {{d.dscvPublisher.nickName}}
       {{# }else{}}
        <span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
<script type="text/html" id="appTypeTpl">
 {{# var tj=d.app.applicationName}}
    {{# if (tj!='null'){}}
        {{tj}}
    {{# } else{ }}
   		 没有找到
    {{# } }}
</script>
	<script type="text/html" id="realnameTpl">
{{#if(d.dscvPublisher){}}
       {{d.dscvPublisher.realName?d.dscvPublisher.realName:'未设置'}}
{{# }else{}}
        <span style="color:red"> 用户未找到   </span>   
       {{#}}}
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
			dst = $("#descoveryType").val();
			isp = $("#isPass").val();
			isj = $("#isShangJia").val();
			ditit = $("#descoveryTitle").val();
			getlist(pid, '', dst, isp, isj, '', ditit,'');
		});
		function doSearch() {
			var pid = $("#loginPlat").val();
			var lp2 = $("#loginPlat1").val();
			var dst = $("#descoveryType").val();
			var isp = $("#isPass").val();
			var userId = $("#publisherId").val();
			var ukw = $("#userKeyWords").val();
			var isj = $("#isShangJia").val();
			var dsid = $("#descoveryId").val();
			var ditit = $("#descoveryTitle").val();
			var dtr = $("#dateRange").val();
			getlist(pid, lp2, dst, isp, userId, ukw, isj, dsid, ditit, dtr);
		}

		function getlist(pid, loginPlat2, dstype, ispass, userId, userKeyWords,
				isShangjia, descoveryid, dititle, DateRange) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'loginPlat' : pid,
					'loginPlat1' : loginPlat2,
					'descoveryType' : dstype,
					'isPass' : ispass,
					'publisherId' : userId,
					'userKeyWords' : userKeyWords,
					'isShangJia' : isShangjia,
					'descoveryId' : descoveryid,
					'descoveryTitle' : dititle,
					'dateRange' : DateRange
				},
				url : '${ctx}/descovery/showDataList.do',
				cols : [ [ 
// 				{
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'descoveryId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'appType',
					title : '所在应用',
					width : 100,
					align : 'center',
					templet : "#appTypeTpl"
				},{
					field : 'descoveryTitle',
					title : '发现标题',
					width : 200,
					align : 'center'
				}, {
					field : 'isShangJia',
					title : '是否上架',
					width : 120,
					align : 'center',
					templet : "#isshangjiaTpl"
				}, {
					field : 'publisherId',
					title : '发布者ID',
					width : 120,
					align : 'center'
				}, {
					field : 'nickName',
					title : '发布者昵称',
					width : 140,
					align : 'center',
// 					sort : true,
					templet : "#nicknameTpl"
				}, {
					field : 'realName',
					title : '发布者姓名',
					width : 120,
					align : 'center',
					templet : "#realnameTpl"
				}, {
					field : 'typeName',
					title : '发现类型',
					width : 120,
					align : 'center',
				}, {
					field : 'isPass',
					title : '是否通过审核',
					width : 150,
					align : 'center',
					templet : "#ispassTpl"
				}, {
					field : 'isRecommend',
					title : '是否推荐',
					width : 120,
					align : 'center',
					templet : "#isRecommendTpl"
				}, {
					field : 'readCount',
					title : '访客总数',
					width : 120,
					sort : true,
					align : 'center'
				}, {
					field : 'collectCount',
					title : '点赞总数',
					width : 120,
					sort : true,
					align : 'center',
				}, {
					field : 'publishTime',
					title : '发布时间',
					width : 180,
					align : 'center',
					sort : true,
				}, {
					title : '操作',
					width : 210,
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
				var index = layui.layer.open({
					title : "编辑发现",
					type : 2,
					content : "${ctx}/descovery/descoveryDetails.do?descoveryId="+data.descoveryId,
					success : function(layero, index){
						setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
								tips: 3
							});
						},500)
					}
				})			
				layui.layer.full(index);
			} else if (obj.event === 'passDescovery') {
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
				var descoveryId = data.descoveryId;
				layer.confirm('确定删除该条数据', {
					  btn: ['确定','取消'] //按钮
					}, function(){
						$.ajax({
							 url:"${ctx}/descovery/dealDescovery.do",
							 type:'post',
							 dataType:'json',
							 data:{'descoveryId':descoveryId},
							 success:function(data){
								 if(data.code == 0){
									 layer.msg("删除成功");
									 setTimeout(function(){
										 table.reload("descovery");
									 },1000);
								 }else{
									 layer.msg(data.msg);
									 return;
								 }
							 },
							 error:function(data){
								 layer.msg('系统错误,请联系管理员');
								 return;
							 }
						})
					}, function(){
					 
					});
				
			} else if(obj.event === 'zxisshangjia'){
				  if(data.isShangJia==1){
					  $.post('${ctx}/descovery/updateDescovery.do', {
							'descoveryId' : data.descoveryId,
							'isShangJia' : 0,
						}, function(data) {
							if (data.code==0) {
								layer.msg("下架资讯成功!");
								//同步更新缓存对应的值
								 obj.update({
									 isShangJia : 0
								});
							} 
						});
				  } else{
					  $.post('${ctx}/descovery/updateDescovery.do', {
							'descoveryId' : data.descoveryId,
							'isShangJia' : 1,
						}, function(data) {
							if (data.code==0) {
								layer.msg("上架资讯成功!");
								//同步更新缓存对应的值
								 obj.update({
									 isShangJia : 1
								});
							} 
						});
				  }
			} else{
				
			}
		});

		 var $ = layui.$, active = {
			addDescovery : function() { //验证是否全选
				if($("#descoveryType").val()==""){
					layer.msg("请选择要发布的发现类型");
				}else if($("#loginPlat").val()==""){
					layer.msg("请选择要发布的应用");
				} else if($("#descoveryType").val()==9){
					layer.msg("暂不支持发布问答");
				}else if($("#descoveryType").val()==10){
					layer.msg("后台暂不支持发布唯爱秀家");
				}else{			
					//window.location.href='${ctx}/descovery/goAddDescovery.do?descoveryType='+$("#descoveryType").val()+'&id='+$("#loginPlat").val()+'';
					var index = layui.layer.open({
						title : "添加发现",
						type : 2,
						content : '${ctx}/descovery/goAddDescovery.do?descoveryType='+$("#descoveryType").val()+'&id='+$("#loginPlat").val()+'',
						success : function(layero, index){
							setTimeout(function(){
								layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
									tips: 3
								});
							},500)
						}
					})			
					layui.layer.full(index);
				}
			}
		}; 
		$('.demoTable .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		
	</script>
</body>
</html>