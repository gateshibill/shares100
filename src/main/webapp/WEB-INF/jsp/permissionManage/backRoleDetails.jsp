<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑角色</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<style type="text/css">
html, body {
	background-color: #fff !important
}

.quanxian li {
	float: left;
	padding-right: 30px;
}

.parent {
	font-size: 16px !important;
	font-weight: 400;
	padding-bottom: 10px;
}

.table {
	padding-left: 10% !important
}

.form-group .table {
	width: 80%;
	margin: 0 auto
}

.pd10 {
	padding: 20px 0 !important
}

.a-btn {
	height: 30px;
	line-height: 30px;
	padding: 0 18px;
	position: absolute;
	right: 25px;
	margin-top: 10px;
	font-size: 10px;
	border-radius: 5px;
	background: #ff4c3b
}

.panel-head {
	width: 100%;
	height: 50px;
	line-height: 50px;
	background: #f2f2f2;
	color: #7e7e7e;
	position: relative;
}
.layui-form input[type=checkbox], .layui-form input[type=radio], .layui-form select {
     display: inline-block !important; 
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>角色【${role.roleName}】的详情</legend>
		</fieldset>
		<div class="layui-form">
			
			<div class="layui-form-item">
				<label class="layui-form-label">角色名称：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text" style="display: inline !important;width:200px;margin-left:0px;float:left;"
						name="roleName" id="roleName" value="${role.roleName}"
						 lay-verify="roleName" autocomplete="off"
						class="layui-input" />
						 <input id="yjy_userId" value="${role.roleId}" type="hidden" />
					</label>
				</div>
			</div>
			<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>分配权限</legend>
		</fieldset>
       <div class="form-group">      
          <table class="table" id="parentMenuList" style="font-size:14px;">	
         
         </table>       
        </div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" onclick="updateRole(${role.roleId})">提交</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>	
layui.use(
		[ 'form', 'layer' ],
		function() {
			 form = layui.form(), layer = layui.layer;
		});	

	var userId = $("#yjy_userId").val();
	//获取数据
	$(document).ready(
			function() {
				$.ajax({
					url : '${ctx}/permission/showHisRole.do?roleId=' + userId,
					type : 'post',
					dataType : 'json',
					success : function(data) {
// 						 alert(JSON.stringify(data));
						var htm = '';
						htm += getMenuItem(data);
						$('#parentMenuList').html(htm);
						//全选反选
						$('.rules_row').on('change',function() {$(this).closest('.rule_check').find('.child_row').find('input').prop('checked', this.checked);
								});

						//子选中父选中：存在缺陷即当所有子节点都不被选中时，父节点仍然被选中
						 $(".child_row").on('change',function() {
									//$(this).parent().parent().parent().addClass('aaa');
									$(this).parent().parent().find(".parent").find('input').prop('checked', true);
								})
					}
				//重新遍历
				})
			});
	//拼接数据
	function getMenuItem(data) {
		if (data) {
			var html = "";
			for (var i = 0; i < data.objects.length; i++) {
				html += '<tr><td class="pd10 rule_check">';
				if (data.objects[i].hasAuthority == 1) {
					html += '<div class="parent"><input type="checkbox" class="rules_row" name="rules[]" checked="checked" value="'+data.objects[i].menuId+'" />'
							+ data.objects[i].title
							+ '</div><ul class="quanxian child_row">';
				} else {
					html += '<div class="parent"><input type="checkbox" class="rules_row" name="rules[]"  value="'+data.objects[i].menuId+'" />'
							+ data.objects[i].title
							+ '</div><ul class="quanxian child_row">';
				}
				var childs = data.objects[i].children;
// 				alert(JSON.stringify(childs));
				if (childs != null) {
					for (var j = 0; j < childs.length; j++) {
						if (childs[j].hasAuthority == 1) {
							html += '<li> <input type="checkbox" checked="checked" class="child_row" name="rules[]" value="'+childs[j].menuId+'"/>'
									+ childs[j].title + '</li>';
						} else {
							html += '<li> <input type="checkbox"  class="child_row" name="rules[]" value="'+childs[j].menuId+'"/>'
									+ childs[j].title + '</li>';

						}
					}
				}
				html += '</ul></td></tr>';
			}
		}
		//勾选
		//getcheck(rules);
		return html;
	}
	
	function updateRole(rid){
		var Checkbox = false;
		var roleName= $("#roleName").val(); 
		if(!roleName){
			layer.msg('角色名称不能为空');
			return;
		}
		$("input[name='rules[]']").each(function() {
			if (this.checked == true) {
				Checkbox = true;
			}
		});
		if(Checkbox == false){
			layer.msg("请勾选权限菜单!");
			return;
		}
		if (Checkbox) {
			var checkedList = new Array();
			$("input[name='rules[]']:checked").each(function() {
				checkedList.push($(this).val());
			});
			$.ajax({
				url:"${ctx}/permission/updateBackUserRole.do",
				type:"post",
				dataType : "json",
				data:{'roleId' : rid,'roleName':roleName,'menuIds' : checkedList.toString()},
			    success:function(data){
// 			    	alert(JSON.stringify(data));
			    	if(data.code == 0){
			    		layer.msg('分配权限成功');
			    		setTimeout(function(){
			    			window.location.href="${ctx}/permission/goRoleList.do";
			    		},1000);
			    	}else{
			    		layer.msg(data.msg);
			    		return;
			    	}
			    },
			    error:function(data){
			    	layer.msg('系统错误');
			    	return false;
			    }
				
			}); 
			
			
		 } else{
			layer.msg('请勾选权限');
			return;
		}
	}
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>