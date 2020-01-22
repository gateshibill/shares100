<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="zh-cn">
<head>
<meta charset="UTF-8">
<title>Table</title>
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
	<form class="layui-form" method="post" action="" id="listform">
		<blockquote class="layui-elem-quote">
			<div class="search-area">
				<ul>
					<li><label style="margin-left: 0px">应用名称：</label>
						<div style="float: left;">
							<select id="loginPlat" class="search-input" name="loginPlat"
								lay-verify="required" lay-search="">
								<c:forEach var="app" items="${appList}">
									<option value="${app.applicationId}">${app.applicationName}</option>
								</c:forEach>
							</select>
						</div></li>
					<li><label>用户状态：</label>
						<div style="float: left;">
							<select id="userStatus" name="userStatus" class="search-select">
								<option value="">全部</option>
								<option value="1">正常</option>
								<option value="2">禁用</option>
							</select>
						</div></li>
					<li><label>是否是管理员：</label>
						<div style="float: left;">
							<select id="isManager" name="isManager" class="search-select">
								<option value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div></li>
					<li><label>用户编号：</label>
						<div>
							<input type="text" name="userId" id="userId"
								placeholder="请输入用户编号" class="search-input">
						</div></li>
					<li><label>用户名称：</label>
						<div>
							<input type="text" name="userKeyWords" id="userKeyWords"
								placeholder="请输入用户关键字" class="search-input">
						</div></li>
					<li><label style="margin-left: 0px">注册日期：</label>
						<div>
							<input type="text" name="searchBeginDate" id="sdate"
								lay-verify="date" placeholder="请选择开始日期" autocomplete="off"
								class="search-input" onclick="layui.laydate({elem: this})">&nbsp;-&nbsp;<input
								type="text" name="searchEndDate" id="edate" lay-verify="date"
								placeholder="请选择结束日期" autocomplete="off" class="search-input"
								onclick="layui.laydate({elem: this})">
						</div></li>
				</ul>
				<div style="float: right;">
					<a href="javascript:;" onclick="doSearch();"
						class="layui-btn layui-btn-small" style="margin: 0 10px"
						id="search"> <i class="layui-icon">&#xe615;</i> 搜索
					</a>
				</div>
				<span style="clear: both; display: block;"></span>
			</div>
		</blockquote>
		<fieldset class="layui-elem-field">
			<legend>数据列表</legend>
			<div class="layui-field-box">
				<table class="site-table table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" id="selected-all"></th>
							<th>用户ID</th>
							<th>用户昵称</th>
							<th>用户真实姓名</th>
							<th>联系方式</th>
							<th>用户邮箱</th>
							<th>性别</th>
							<th>是否是管理</th>
							<th>用户状态</th>
							<th>是否认证</th>
							<th>注册时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="ubody">
					</tbody>
				</table>
			</div>
		</fieldset>
		<div class="admin-table-page">
			<div id="pages" class="page"></div>
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>
<script id="uscript" type="text/html">
	{{each objects as value i}}
		<tr>
			<td><input type="checkbox" name="id[]"
				value="{{value.userId}}" /></td>
			<td>{{value.userId}}</td>
			<td>{{value.nickName}}</td>
			<td>{{value.realName}}</td>
			<td>{{value.phone}}</td>
			<td>{{value.email}}</td>
			<td>
				{{if value.userSex==0}}
					未知
				{{/if}}
				{{if value.userSex==1}}
					<span style="color: green">男</span>
				{{/if}}
				{{if value.userSex==2}}
					<span style="color: red">女</span>
				{{/if}}
			</td>
			<td>{{if value.isCreater!=null}}
					{{if value.isCreater==1}}
						<span style="color: green">是</span>
					{{else}}
						<span style="color: red">否</span>
					{{/if}}</td>}}
				{{else}}
					{{if value.isManager==1}}
					<span style="color: green">是</span>
					{{else}}
						<span style="color: red">否</span>
					{{/if}}
				{{/if}}</td>
			<td>{{if value.userStatus==1}}
					<span style="color: green">正常</span>
				{{else}}
					<span style="color: red">禁用</span>
				{{/if}}</td>
			<td>{{if value.isRenzheng==1}}
					<span style="color: green">已认证</span>
				{{else}}
					<span style="color: red">未认证</span>
				{{/if}}</td>
			<td>{{value.createTime}}</td>
			<td><div class="button-group">
				<a
					href="${ctx}/user/userDetails.do?userId={{value.userId}}"
					class="layui-btn layui-btn-normal layui-btn-mini"> 详情</a>
				{{if value.userStatus==1}}
					<a class="layui-btn layui-btn-danger layui-btn-mini"
						href="javascript:void(0)"
							onclick="deleteone({{value.userId}})"><span
								class="icon-trash-o"></span>禁用</a>
				{{else}}
					<a class="layui-btn layui-btn-mini"
						href="javascript:void(0)"
							onclick="recoveryOne({{value.userId}})"><span
								class="icon-check-circle-o"></span>恢复</a>
				{{/if}}
				</div>
			</td>
		</tr>
	{{/each}}
</script>
<script type="text/javascript">
	var pageno = 1;
	var ulistLoad;

	layui.config({
		base : '${ctx}/plugins/layui/modules/'
	});
	layui
			.use(
					[ 'form', 'layedit', 'laydate' ],
					function() {
						var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
					});
	layui
			.use(
					[ 'icheck', 'laypage', 'layer' ],
					function() {
						var $ = layui.jquery, laypage = layui.laypage, layer = parent.layer === undefined ? layui.layer
								: parent.layer;
						$('input').iCheck({
							checkboxClass : 'icheckbox_flat-green'
						});
						findDataByPage(pageno);

						function findDataByPage(pageno) {
							$
									.ajax({
										beforeSend : function() {
											ulistLoad = layer.load(1);
										},
										timeout : 20000,
										type : "post",
										url : "${ctx}/user/showUserList.do",
										dataType : "json",
										data : {
											"pageNo" : pageno,
											"userStatus" : $("#userStatus")
													.val(),
											"loginPlat" : $("#loginPlat").val(),
											"isManager" : $("#isManager").val(),
											"userId" : $("#userId").val(),
											"nickName" : $("#userKeyWords")
													.val(),
											"searBeginDate" : $("#sdate").val(),
											"searEndDate" : $("#edate").val()
										},
										success : function(result) {
											layer.close(ulistLoad);
											if (result.code == 0) {
												var html = template("uscript",
														result);
												$('#ubody').html(html);
												if (result.count >= 1) {
													laypage({
														cont : 'pages',
														pages : result.totalPages,
														curr : pageno,//当前页
														groups : 5,
														jump : function(obj,
																first) {
															//触发分页后的回调
															if (!first) {
																//点击跳页触发函数自身，并传递当前页：obj.curr
																findDataByPage(obj.curr);
															}
														}
													});
												}
											} else {
												$('#ubody').html("");
												laypage({
													cont : 'pages',
													pages : 0,
													curr : pageno,//当前页
													groups : 5,
													jump : function(obj, first) {
														//触发分页后的回调
														if (!first) {
															//点击跳页触发函数自身，并传递当前页：obj.curr
															findDataByPage(obj.curr);
														}
													}
												});
												layer.msg("该列表没有数据!");
											}
										},
										error : function() {
											layer.close(ulistLoad);
											layer.msg("数据加载失败...");
										}
									});
						}
						$("#search").on('click', function() {
							findDataByPage(pageno);
						});
						$('#import').on(
								'click',
								function() {
									var that = this;
									var index = layer.tips('只想提示地精准些', that, {
										tips : [ 1, 'white' ]
									});
									$('#layui-layer' + index).children(
											'div.layui-layer-content').css(
											'color', '#000000');
								});
						$('.site-table tbody tr').on(
								'click',
								function(event) {
									var $this = $(this);
									var $input = $this.children('td').eq(0)
											.find('input');
									$input.on('ifChecked', function(e) {
										$this
												.css('background-color',
														'#EEEEEE');
									});
									$input.on('ifUnchecked', function(e) {
										$this.removeAttr('style');
									});
									$input.iCheck('toggle');
								}).find('input').each(
								function() {
									var $this = $(this);
									$this.on('ifChecked', function(e) {
										$this.parents('tr').css(
												'background-color', '#EEEEEE');
									});
									$this.on('ifUnchecked',
											function(e) {
												$this.parents('tr').removeAttr(
														'style');
											});
								});
						$('#selected-all')
								.on(
										'ifChanged',
										function(event) {
											var $input = $(
													'.site-table tbody tr td')
													.find('input');
											$input
													.iCheck(event.currentTarget.checked ? 'check'
															: 'uncheck');
										});
					});
	//批量删除
	function DelSelect() {
		var Checkbox = false;
		$("input[name='id[]']").each(function() {
			if (this.checked == true) {
				Checkbox = true;
			}
		});
		if (Checkbox) {
			layer.confirm('您确认禁用选中的用户?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
				$('#checkall').attr("checked", false);
				$("[name ='id[]']:checkbox").attr("checked", false);
			}, function() {
				var checkedList = new Array();
				$("input[name='id[]']:checked").each(function() {
					checkedList.push($(this).val());
				});
				//执行ajax
				$.ajax({
					type : "POST",
					url : "${ctx}/user/deleteUser.do",
					data : {
						'userIds' : checkedList.toString()
					},
					success : function(result) {
						window.location.reload();
					},
					error : function(result) {
						alert('批量禁用失败');
					}
				});
			})
		} else {
			layer.msg('请选择您要禁用的用户!', {
				time : 3000,
				icon : 0
			});
			return false;
		}
	}
	function deleteone(cid) {
		layer.confirm('您确认要禁用该用户?', {
			btn : [ '取消', '确认' ]
		}, function() {
			layer.closeAll();
		}, function() {
			$.post('${ctx}/user/deleteUser.do', {
				'userIds' : cid
			}, function(data) {
				if (data) {
					setTimeout(function() {
						window.location.reload();
					}, 400);
				} else {
					layer.msg('禁用用户失败', {
						time : 2500,
						icon : 0
					});
				}
			});
		})
	}
	//批量恢复
	function recovery() {
		var Checkbox = false;
		$("input[name='id[]']").each(function() {
			if (this.checked == true) {
				Checkbox = true;
			}
		});
		if (Checkbox) {
			layer.confirm('您确认恢复选中的用户?', {
				btn : [ '取消', '确认' ]
			}, function() {
				layer.closeAll();
				$('#checkall').attr("checked", false);
				$("[name ='id[]']:checkbox").attr("checked", false);
			}, function() {
				var checkedList = new Array();
				$("input[name='id[]']:checked").each(function() {
					checkedList.push($(this).val());
				});
				//执行ajax
				$.ajax({
					type : "POST",
					url : "${ctx}/user/recoveryUser.do",
					data : {
						'userIds' : checkedList.toString()
					},
					success : function(result) {
						window.location.reload();
					},
					error : function(result) {
						alert('批量恢复失败');
					}
				});
			})
		} else {
			layer.msg('请选择您要恢复的用户!', {
				time : 3000,
				icon : 0
			});
			return false;
		}
	}
	function recoveryOne(cid) {
		layer.confirm('您确认要恢复该用户?', {
			btn : [ '取消', '确认' ]
		}, function() {
			layer.closeAll();
		}, function() {
			$.post('${ctx}/user/recoveryUser.do', {
				'userIds' : cid
			}, function(data) {
				if (data) {
					setTimeout(function() {
						window.location.reload();
					}, 400);
				} else {
					layer.msg('恢复用户失败', {
						time : 2500,
						icon : 0
					});
				}
			});
		})
	}
</script>

</html>