<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<%-- <link rel="icon" href="${ctx}/images/titleIcon.png" type="image/x-icon"/> --%>
<meta charset="utf-8">
<title>${app.applicationName}</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="icon" href="${app.appLogo?app.appLogo:'favicon.ico'}">
<link rel="stylesheet"
	href="${ctx}/plugins/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/global.css" media="all">
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<style type="text/css">
	.layui-form-label {
    float: left;
    display: block;
    padding: 9px 15px;
    width: 100px;
    text-align: right;
    /* font-weight: 400; */
    
}
.layui-input, .layui-textarea {
    display: block;
    padding-left: 10px;
    margin-top: 15px;
    width: 95%;
    height: 38px;
}
.layui-tab-content{padding:0px;}
	
	</style>
</head>
<body>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header header header-demo">
			<div class="layui-main">
				<div class="admin-login-box" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;position:static">
					<%-- <a class="logo" style="left: 0;"
						href="http://www.feikantec.com/feikanyun.html"> <span
						style="font-size: 32px;"><img src="${ctx}/images/indexLogo.png"></span>
					</a> --%>
					<a href="#" style="color:#fff !important;text-align: center;line-height:70px;font-size:20px;
					">${appName}管理系统</a>
					<div class="admin-side-toggle">
						<i class="fa fa-bars" aria-hidden="true"></i>
					</div>
				</div>
				<ul class="layui-nav admin-header-item">
					<!-- <li class="layui-nav-item"><a href="javascript:;">清除缓存</a></li>
					<li class="layui-nav-item"><a href="javascript:;">浏览网站</a></li>
					<li class="layui-nav-item" id="video1"><a href="javascript:;">视频</a>
					</li> -->
					<li class="layui-nav-item"><a href="javascript:;"
						class="admin-header-user"> <img src="${loginer.headImage}" /> 
						<%-- <span style="color:#fff !important">角色类型:${loginer.roleName}&nbsp;&nbsp;${loginer.realName}</span> --%>
					<span style="color:#fff !important">${loginer.realName}</span>
					</a>
						<dl class="layui-nav-child">
							<dd>
								<a class="newsAdd_btn"><i class="fa fa-user-circle"
									aria-hidden="true"></i> 个人信息</a>
							</dd>
							<!-- <dd>
								<a href="javascript:;"><i class="fa fa-gear"
									aria-hidden="true"></i> 设置</a>
							</dd> -->
							<dd id="lock">
								<a href="javascript:;" onclick="updatePass();"> <i class="fa fa-lock"
									style="padding-right: 3px; padding-left: 1px;"></i> 修改密码
								</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="logoutSystem();"><i class="fa fa-sign-out"
									aria-hidden="true"></i> 注销</a>
							</dd>
						</dl></li>
				</ul>
				<ul class="layui-nav admin-header-item-mobile">
					<li class="layui-nav-item"><a href="login.html"><i
							class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
				</ul>
			</div>
		</div>
		<div class="layui-side layui-bg-black" id="admin-side">
			<div class="layui-side-scroll" id="admin-navbar-side"
				lay-filter="side"></div>
		</div>
		<div class="layui-body"
			style="bottom: 0; border-left: solid 2px #1AA094;" id="admin-body">
			<div class="layui-tab admin-nav-card layui-tab-brief"
				lay-filter="admin-tab">
				<ul class="layui-tab-title">
					<li class="layui-this"><i class="fa fa-dashboard"
						aria-hidden="true"></i> <cite>首页</cite></li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show">
						<iframe src="${ctx}/systemcount/gomain.do"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-footer footer footer-demo" id="admin-footer">
			<div class="layui-main">
				<p>
					2017 &copy; <a href="http://www.feikantec.com/" target="_blank">${appName}管理系统</a>
				</p>
			</div>
		</div>
		<div class="site-tree-mobile layui-hide">
			<i class="layui-icon">&#xe602;</i>
		</div>
		<div class="site-mobile-shade"></div>
		<!--锁屏模板 start-->
		<script type="text/template" id="lock-temp">
				<div class="admin-header-lock" id="lock-box">
					<div class="admin-header-lock-img">
						<img src="images/0.jpg"/>
					</div>
					<div class="admin-header-lock-name" id="lockUserName">beginner</div>
					<input type="text" class="admin-header-lock-input" value="输入密码解锁.." name="lockPwd" id="lockPwd" />
					<button class="layui-btn layui-btn-small" id="unlock">解锁</button>
				</div>
			</script>
		<!--锁屏模板 end -->
		<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
		<script type="text/javascript" src="${ctx}/datas/nav.js"></script>
		<!-- index.js引入后会报错 -->
<%-- 	<script src="${ctx}/js/index.js"></script>--%>		
        <script>
       var userId='${loginer.userId}';
       
            /* 退出系统 */
			function logoutSystem(){
				window.location.href="${ctx}/logout.do";
			}
			
			$(window).one("resize",function(){
				$(".newsAdd_btn").click(function(){
					var index = layui.layer.open({
						title : "编辑用户信息",
						type : 2,
						content : "${ctx}/permission/backUserDetails.do?userId="+userId,
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
			
			
			//修改密码
			function updatePass(){
				layer.open({
		 			 type: 1,
		 			 skin: '#008bca',
		 			 area: ['430px', '280px'], //宽高
		 			 title:'修改密码',
		 			 btn:['确定','取消'],
		 			 anim: 1,
		 			 content: '<div id="showdiv" class="showpass"><ul class="passul"><li class="layui-inline"><label class="layui-form-label" style="width:120px;margin-top:15px;">原始密码：</label><input class="layui-input" style="width:200px;margin-left:0px;" type="password" id="old" placeholder="请输入原始密码"/></li><li class="layui-inline"><label class="layui-form-label" style="width:120px;margin-top:15px;">新的密码：</label><input class="layui-input" style="width:200px;" type="password" id="new1" placeholder="请输入新的密码" value=""/></li><li class="layui-inline"><label class="layui-form-label" style="width:120px;margin-top:15px;">确认密码：</label><input style="width:200px;" class="layui-input" type="password" id="new2" placeholder="再次确认新密码" value=""/></li></ul></div>',
		 			 yes:function() {
		 				var oldpass = $('#old').val();
		 		 		var newpass = $('#new1').val();
		 		 		var newpass1 = $('#new2').val();
		 		 		var match = /^[\w]{6,16}$/;
		 		 		if(!oldpass) {
		 		 			layer.msg('原始密码不能为空!');
		 		 			return;
		 		 		}
		 		 		if(!newpass) {
		 		 			layer.msg('所设的新密码不能为空!');
		 		 			return;
		 		 		}
		 		 		if(newpass == oldpass) {
		 		 			layer.msg('新密码不能与原始密码相同!');
		 		 			return;
		 		 		}
		 		 		if(!newpass1) {
		 		 			layer.msg('需再次输入新密码!');
		 		 			return;
		 		 		}
		 		 		if(newpass!=newpass1) {
		 		 			layer.msg('两次输入不一样，请重新输入!');
		 		 			return;
		 		 		}
		 		 		if(!match.test(newpass)) {
		 		 			layer.msg('密码须由字母和数字或下划线组,可设置位数6-16!');
		 		 			return;
		 		 		} 
		 		 		$.ajax({
		 		 			url:'${ctx}/updatePassword.do',
		 		 			type:'POST',
		 		 			dataType:'JSON',
		 		 			data:{'password':oldpass,'confirmPassword':newpass,'modifyPassword':newpass1},
		 		 			
		 		 			success:function(data){
		 				    	if(data.code == 0){
		 				    		layer.closeAll();
		 				    		layer.msg('修改密码成功!');
		 				    		setTimeout(function(){
// 		 				    			 window.location.Reload();
			 				    		window.location.href = "${ctx}/login.do";
		 				    		},1000);
		 				    	}else{
		 				    		layer.msg(data.msg);
		 				    		return;
		 				    	}
		 				    },
		 		 			error:function() {
		 		 				layer.msg("修改密码失败!");
		 		 				return;
		 		 			}
		 		 		}); 	
		 			 },
		 			 btn2:(function(index) {
		 				 layer.close(index);
		 			 })
		 		});
			}
		</script>
	</div>
</body>
</html>