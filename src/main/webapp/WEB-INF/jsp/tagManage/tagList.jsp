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
		<form class="layui-form layui-form-pane" action="">
			<div class="layui-form-item">
			   <div class="layui-inline pane-input">
					<label class="layui-form-label">标签类型</label>
					<div class="layui-input-block ">
						<select id="tagType" name="tagType" class="search-select">
								<option value="">全部</option>
								<option value="1">项目标签</option>
								<option value="2">产品标签</option>
								<option value="3">技能标签</option>
								<option value="4">招聘标签</option>
							</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标签级别</label>
					<div class="layui-input-block">
						<select id="isChildTag" name="isChildTag" class="search-select">
								<option value="">全部</option>
								<option value="0">一级标签</option>
								<option value="1">二级标签</option>
							</select>
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">标签编号</label>
					<div class="layui-input-block">
						<input type="text" name="tagId" id="tagId" placeholder="请输入标签编号"
								class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">标签名称</label>
					<div class="layui-input-block">
						<input type="text" name="tagName" id="tagName"
								placeholder="请输入标签名称" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">添加者编号</label>
					<div class="layui-input-block">
						<input type="text" name="tagUserId" id="tagUserId"
								placeholder="添加者编号(0是系统)" class="layui-input">
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">添加者名称</label>
					<div class="layui-input-block">
						<input type="text" name="tagUserWords" id="tagUserWords"
								placeholder="请输入添加者关键字" class="layui-input">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">发布日期</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="dataRange" id="test6"
								placeholder=" - ">
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
		<a class="layui-btn layui-btn-small newsAdd_btn" id="add"> <i
					class="layui-icon">&#xe608;</i> 添加标签
				</a>
	</div>
	
	<table class="layui-table" id="descovery" lay-filter="descovery"></table>
	<script type="text/html" id="toolBar">
  	<a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteTag({{d.tagId}})">删除</a>
</script>
	<!-- 上架状态模板 -->
	<script type="text/html" id="usersexTpl">
    {{# var issj=d.userSex}}
    {{# if (issj=='1'){}}
    	男
    {{# } else{ }}
   		女
    {{# } }}
</script>
	<!-- 审核状态模板 -->
	<script type="text/html" id="isManagerTpl">
    {{# var ispass=d.isManager}}
    {{# if (ispass=='1'){}}
    	<span style="color:red;">是</span>
    {{# } else{ }}
   		否
    {{# } }}
</script>
	<script type="text/html" id="userStatusTpl">
    {{# var ispass=d.tagStatus}}
    {{# if (ispass=='1'){}}
    	<a href="javascript:;" onclick="changeTagStatus({{d.tagId}},{{d.tagStatus}})"><span style="color:green">启用</span></a>
    {{# } else{ }}
       <a href="javascript:;" onclick="changeTagStatus({{d.tagId}},{{d.tagStatus}})"><span style="color:red">停用</span></a>
    {{# } }}
</script>
	<script type="text/html" id="nicknameTpl">
     {{#if(d.tagUser){}}
              {{d.tagUser.nickName}}
       {{# }else{}}
                        系统 
       {{#}}}
</script>
	<script type="text/html" id="phoneTpl">
{{# var destype=d.tagType}}
    {{# if (destype=='1'){}}
    	项目标签
    {{# } else if(destype=='2'){ }}
   		产品标签
 	{{# } else if(destype=='3'){ }}
   		技能标签
 	{{# } else if(destype=='4'){ }}
   		招聘标签
 	{{# } else if(destype=='5'){ }}
   		资讯标签
    {{# } }}
</script>
	<script type="text/html" id="e_mailTpl">
 {{# var ispass=d.isChildTag}}
    {{# if (ispass=='0'){}}
    	一级
    {{# } else{ }}
   		二级
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
				elem : '#test6',
				range : true
			});
			//--------------方法渲染TABLE----------------
			pid = $("#loginPlat").val();
			getlist('', '', '', '', '', '');
		});
		function doSearch() {
			var pid = $("#tagType").val();
			var lp2 = $("#isChildTag").val();
			var tagId = $("#tagId").val();
			var tagName = $("#tagName").val();
			var userId = $("#tagUserId").val();
			var nickName = $("#userKeyWords").val();
			var dtr = $("#test6").val();
			getlist(pid, lp2,tagId,tagName,userId,nickName,dtr);
		}

		function getlist(pid, lp2, tagId,tagName,userId, nickName, DateRange) {
			tableIns = table.render({
				elem : '#descovery',
				id : 'descovery',
				method : 'post',
				where : {
					'tagType' : pid,
					'isChildTag' : lp2,
					'isChildTag' : tagId,
					'tagName' : tagName,
					'userId' : userId, 
					'nickName' : nickName,
					'dataRange' : DateRange
				},
				url : '${ctx}/tag/showTagList.do',
				cols : [ [
// 				{
// 					checkbox : true,
// 					LAY_CHECKED : false,
// 					fixed : true
// 				}, 
				{
					field : 'tagId',
					title : 'ID',
					width : 80,
					align : 'center',
					fixed : true,
					sort : true
				}, {
					field : 'tagName',
					title : '标签名称',
					width : 140,
					align : 'center',
				}, {
					field : 'tagType',
					title : '标签类型',
					width : 140,
					align : 'center',
					templet : "#phoneTpl"
				}, {
					field : 'email',
					title : '标签级别',
					width : 140,
					align : 'center',
					sort : true,
					templet : "#e_mailTpl"
					
				}, {
					field : 'createUser',
					title : '添加者ID',
					width : 100,
					align : 'center',
					
				}, {
					field : 'isCreater',
					title : '添加者昵称',
					width : 100,
					align : 'center',
					templet : "#nicknameTpl"
					
				}, {
					field : 'userStatus',
					title : '是否启用',
					width : 100,
					align : 'center',
					templet : "#userStatusTpl"
					
				},{
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
				$.ajax({
					data : 'json',
					url : '${ctx}//.do',
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
			} else if (obj.event === 'nopassDescovery') {
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
		
		
		function changeTagStatus(tagId,tagStatus){
			if (tagStatus == 1) {
				layer.confirm('您确认停用该标签?', {
					btn : [ '取消', '确认' ]
				}, function() {
					layer.closeAll();
				}, function() {
					$.post('${ctx}/tag/updateTagInfo.do', {
						'tagId' : tagId,
						'tagStatus' : 0
					}, function(data) {
						if (data) {
							setTimeout(function() {
								window.location.reload();
								var html = template("gscript", data);
								$('#gbody').html(html);
							}, 400);
						} else {
							layer.msg('停用失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				})
			} else {
				layer.confirm('您确认启用该标签?', {
					btn : [ '取消', '确认' ]
				}, function() {
					layer.closeAll();
				}, function() {
					$.post('${ctx}/tag/updateTagInfo.do', {
						'tagId' : tagId,
						'tagStatus' : 1
					}, function(data) {
						if (data) {
							setTimeout(function() {
								window.location.reload();
								var html = template("gscript", data);
								$('#gbody').html(html);
							}, 400);
						} else {
							layer.msg('启用失败', {
								time : 2500,
								icon : 0
							});
						}
					});
				})
			}
		}
		//删除标签
		function deleteTag(tagId){
			layer.confirm('您确认删除该活动么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/tag/deleteTag.do', {
					'tagId' : tagId
				}, function(data) {
					if (data) {
						layer.msg('删除标签成功');
						setTimeout(function() {
							window.location.reload();
						}, 400);
					} else {
						layer.msg('删除标签失败', {
							time : 2500,
							icon : 0
						});
					}
				});
			}, function() {
				layer.closeAll();
			})
		}
		
		function addTag() {
			//自定页
			layer.open({
						title : '新增标签',
						type : 1,
						skin : 'layui-layer-demo', //样式类名
						closeBtn : 1, //不显示关闭按钮
						anim : 2,
						area : [ '460px', '240px' ], //宽高
						shadeClose : false, //开启遮罩关闭
						content : '<div class="layui-form-item" style="margin-top:20px;">'
								+ '<label class="layui-form-label" style="margin-left:20px;">标签名称：</label>'
								+ '<div class="layui-input-inline" style="width: 300px;">'
								+ '<input type="text"'
								+ 'style="display: inline !important; width: 200px;" name="sellPrice"'
								+ 'lay-verify="sellPrice" autocomplete="off" class="layui-input"'
								+ 'value="${goods.sellPrice}">'
								+ '</div>'
								+ '</div>'
								+ ' <div class="layui-form-item">'
								+ ' <div class="layui-input-block">'
								+ ' <button class="layui-btn" lay-submit="" lay-filter="demo1">提交修改</button>'
								+ ' <button onclick="" class="layui-btn layui-btn-primary">取消</button>'
								+ ' </div>' + ' </div>',
					});
		}
		
		$(window).one("resize",function(){
			$(".newsAdd_btn").click(function(){
				var index = layui.layer.open({
					title : "添加标签",
					type : 2,
					content : "${ctx}/tag/addTage.do",
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