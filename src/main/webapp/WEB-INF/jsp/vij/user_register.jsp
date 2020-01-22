<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>唯爱家·注册</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/user_register.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//保存手机号
			var phone="";
			//判断手机号是否为空
			$(".phone").focusout(function(){
				if($(".phone").val()==""){
					$(".center_cen_bottom_phone_msg").text("手机号码不能为空");
				}
				else{
					$(".center_cen_bottom_phone_msg").text("");
				}
			})
			//点击获取验证码
			$(".yanzheng_center_cen_bottom_ma .button").click(function(){
				if($(".phone").val()==""){
					$(".center_cen_bottom_phone_msg").text("手机号码不能为空");
				}
				else if(!(/^1[34578]\d{9}$/.test($(".phone").val()))){
					$(".center_cen_bottom_phone_msg").text("手机号码格式错误");
				}
				else{
					phone=$(".phone").val();
					$.ajax({url:"${ctx}/vij/account/sendmsg.do",
						   data:{"type":1
							   ,"loginPlat":LOGIN_PLAT
							   ,"phone":$(".phone").val()},
						   type:"POST",
						   success:function(data){
								if(data.code=="0"){
									//设置按钮不可点
									$(".yanzheng_center_cen_bottom_ma .button").attr("disabled",true);
									//设置倒计时
									var nums=180;
									var timer=null;
									function set_botton(){
										nums-=1;
										if (nums>0) {
											$(".yanzheng_center_cen_bottom_ma .button").val("剩余"+nums+"s");
										}
										if(nums==0){
											clearInterval(timer);
											$(".yanzheng_center_cen_bottom_ma .button").attr("disabled",false);
											$(".yanzheng_center_cen_bottom_ma .button").val("重新获取");
										}
									}
									timer=setInterval(set_botton,1000);
									//去除提示信息
									$(".yanzheng_center_cen_bottom_msg").empty();
									//添加提示信息
									$(".yanzheng_center_cen_bottom_msg").append("<img src=\"${ctx}/vij/images/yanzhengma.png\"><p>"+data.msg+"</p>");
								}
								if(data.code=="1"){
									//去除提示信息
									$(".yanzheng_center_cen_bottom_msg").empty();
									$(".yanzheng_center_cen_bottom_msg").append("<p>"+data.msg+"</p>");
									$(".yanzheng_center_cen_bottom_msg p").css("color","red");
								}
							},
							error : function() {},
						   datatype:"json"
					});
				}
				
			})
			//点击下一步
			$(".yanzheng_center_cen_bottom_button").click(function(){
				if($(".phone").val()==""){
					$(".center_cen_bottom_phone_msg").text("手机号码不能为空");
				}
				else if(!(/^1[34578]\d{9}$/.test($(".phone").val()))){
					$(".center_cen_bottom_phone_msg").text("手机号码格式错误");
				}
				else{
					$.ajax({url:"${ctx}/vij/account/checkCode.do",
						   data:{"type":1
							   ,"loginPlat":LOGIN_PLAT
							   ,"phone":$(".phone").val()
							   ,"code":$(".input").val()},
						   type:"POST",
						   success:function(data){
								if(data.code=="0"){
									$(".zhuce_yanzheng").css("display","none");
									$(".zhuce_msg").css("display","block");
								}
								if(data.code=="1"){
									//去除提示信息
									$(".yanzheng_center_cen_bottom_msg").empty();
									$(".yanzheng_center_cen_bottom_msg").append("<p>"+data.msg+"</p>");
									$(".yanzheng_center_cen_bottom_msg p").css("color","red");
								}
							},
							error : function() {},
						   datatype:"json"
					});
						
				}
			})
			//判断用户名是否为空
			$(".center_cen_bottom_name_username").focusout(function(){
				if($(".center_cen_bottom_name_username").val()==""){
					$(".center_cen_bottom_name_msg").text("用户名不能为空");
				}
				else{
					$(".center_cen_bottom_name_msg").text("");
				}
			})
			//判断密码是否为空
			$(".center_cen_bottom_password_password1").focusout(function(){
				if($(".center_cen_bottom_password_password1").val()==""){
					$(".center_cen_bottom_pass1_msg").text("密码不能为空");
				}
				else{
					$(".center_cen_bottom_pass1_msg").text("");
				}
			})
			//判断确认密码是否为空
			$(".center_cen_bottom_password_password2").focusout(function(){
				if($(".center_cen_bottom_password_password2").val()==""){
					$(".center_cen_bottom_pass2_msg").text("确认密码不能为空");
				}
				else{
					$(".center_cen_bottom_pass2_msg").text("");
				}
			})
			//点击立即注册
			$(".msg_center_cen_bottom_button").click(function(){
				if($(".center_cen_bottom_name_username").val()==""){
					$(".center_cen_bottom_name_msg").text("用户名不能为空");
				}
				else if($(".center_cen_bottom_password_password1").val()==""){
					$(".center_cen_bottom_pass1_msg").text("密码不能为空");
				}
				else if($(".center_cen_bottom_password_password2").val()==""){
					$(".center_cen_bottom_pass2_msg").text("确认密码不能为空");
				}
				else{
					if($(".center_cen_bottom_password_password1").val()!=$(".center_cen_bottom_password_password2").val()){
						$(".center_cen_bottom_pass2_msg").text("前后密码不一致");
					}
					else{
						//数字或字母或下划线组成
						var pass_string=$(".center_cen_bottom_password_password1").val();
						var name_string=$(".center_cen_bottom_name_username").val();
						if(!/^\w+$/.test(pass_string)){
							$(".center_cen_bottom_pass2_msg").text("密码必须为数字字母或下划线");
						}
						else if(!/^\w+$/.test(name_string)){
							$(".center_cen_bottom_name_msg").text("用户名必须为数字字母或下划线");
						}
						else{
							if($(".center_cen_bottom_password_password1").val().length<6){
								$(".center_cen_bottom_pass2_msg").text("密码不能小于6位数");
							}
							else if($(".center_cen_bottom_name_username").val().length<6){
								$(".center_cen_bottom_name_msg").text("用户名不能小于6位数");	
					    	}
					    	else{
					    		$.ajax({url:"${ctx}/vij/account/doregister.do",
									   data:{"userName":$(".center_cen_bottom_name_username").val()
										   ,"password":$(".center_cen_bottom_password_password1").val()
										   ,"confirmPwd":$(".center_cen_bottom_password_password2").val()
										   ,"LoginPlat":LOGIN_PLAT
										   ,"phone":phone},
									   type:"POST",
									   success:function(data){
											if(data.code=="0"){
												$(".zhuce_msg").css("display","none");
												$(".zhuce_success").css("display","block");
												$(".success_cen_center_content_zhanghao span").text(data.msg);
												//设置跳转倒计时
												var nums_1=5;
												var timer_1=null;
												function go_login(){
													nums_1-=1;
													if (nums_1>0) {
														$(".success_cen_center_content_nums span").text(nums_1);
													}
													if(nums_1==0){
														//window.location.href="${ctx}/vij/account/login.do";
														<!--注册完默认已登录并跳转到首页-->
														window.location.href = "${ctx}/vij/index/index.do";
													}
												}
												timer_1=setInterval(go_login,1000);
											}else{
												$(".center_cen_bottom_pass2_msg").text(data.msg);
											}
											/* if(data.code=="1"){
												$(".center_cen_bottom_pass2_msg").text(data.msg);
											}
											if(data.code=="2"){
												window.location.href="${ctx}/vij/index/index.do";
											} */
										},
										error : function() {},
									   datatype:"json"
								});
					    	}
						}   	
					}
				}
			})

			
		})
	</script>
</head>
<body>
	<div class="page">
		<div class="navigation">
			<div class="navigation-center">
				<div class="navigation-center-le">
					<div class="navigation-center-logo">
						<a href="/" title="唯爱家商城首页"><img src="${systemUpset.companyLogo}"></a>
					</div>
				</div>
				
			</div>
		</div>
		<div class="zhuce">
			<div class="zhuce_yanzheng">
				<div class="zhuce_yanzheng_center">
					<div class="zhuce_yanzheng_center_cen">
						<div class="yanzheng_center_cen_top">
							<img src="${ctx}/vij/images/zhuce_yanzheng.png">
						</div>
						<div class="yanzheng_center_cen_bottom">
							<div class="yanzheng_center_cen_bottom_phone flex-box">
								<select>
									<option>中国大陆&nbsp;+86</option>
									<option>台湾</option>
									<option>港澳</option>
								</select>
								<input type="text" name="" class="phone" placeholder="请输入手机号" maxlength="11">
							</div>
							<span class="center_cen_bottom_phone_msg">
								
							</span>
							<div class="yanzheng_center_cen_bottom_ma flex-box">
								<input type="button" class="button" value="获取验证码">
								<input class="input" type="text" name="" placeholder="手机验证">
							</div>
							<div class="yanzheng_center_cen_bottom_msg">
								
							</div>
							<button class="yanzheng_center_cen_bottom_button" id="yanzheng_center_cen_bottom_button">下一步</button>
						</div>
					</div>
				</div>
			</div>
			<div class="zhuce_msg">
				<div class="zhuce_msg_center">
					<div class="zhuce_msg_center_cen">
						<div class="msg_center_cen_top">
							<img src="${ctx}/vij/images/zhuce_zhanghao_2.png">
						</div>
						<div class="msg_center_cen_bottom">
							<div class="msg_center_cen_bottom_name">
								<p >用&nbsp;&nbsp;户&nbsp;&nbsp;名</p>
								<input type="text" name="" placeholder="您的账号或登录名" class="center_cen_bottom_name_username" maxlength="20">
							</div>
							<span class="center_cen_bottom_name_msg">
								
							</span>
							<div class="msg_center_cen_bottom_password password1">
								<p >设置密码</p>
								<input type="password" name="" placeholder="建议使用两种或两种以上的组合" class="center_cen_bottom_password_password1" maxlength="20">
							</div>
							<span class="center_cen_bottom_pass1_msg">
								
							</span>
							<div class="msg_center_cen_bottom_password password2">
								<p >确认密码</p>
								<input type="password" name="" placeholder="请再次输入密码" class="center_cen_bottom_password_password2" maxlength="20">
							</div>
							<span class="center_cen_bottom_pass2_msg">
								
							</span>
							<button class="msg_center_cen_bottom_button" id="msg_center_cen_bottom_button">立即注册</button>
						</div>
					</div>
				</div>
			</div>
			<div class="zhuce_success">
				<div class="zhuce_success_cen">
					<div class="zhuce_success_cen_center">
						<div class="success_cen_center_content">
							<img src="${ctx}/vij/images/zhucechenggong-2.png">
							<p class="success_cen_center_content_title" align="left">恭喜你注册成功，你的账号为</p>
							<p class="success_cen_center_content_zhanghao" align="left">唯爱家用户名<span></span></p>
							<p class="success_cen_center_content_nums" align="left">
								<span></span><a href="${ctx}/vij/account/login.do">&nbsp;&nbsp;秒后跳转登录页面,点击直接跳转</a>
							</p>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<div class="zhuce_bottom">
			
		</div>
	</div>
	
</body>
</html>