<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>忘记密码</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/forget_password.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
</head>
<body>
	<div class="page">	
		<!-- 公共头部 -->
        <jsp:include page="/vij/publich/header.do" />
		<div class="forget">
			<div class="forget-cen">
				<div class="phone">
					<div class="phone-iamge">
						<div class="phone-iamge-cen">
							<img src="${ctx}/vij/images/yanzheng_jindu.png">
						</div>
					</div>
					<div class="phone-title">
						<img src="${ctx}/vij/images/gantanhao_12.png">
						<p>请输入你需要找回登录密码的手机号</p>
					</div>
					<div class="phone-yanzheng">
						<div class="yanzheng-cen">
							<div class="yanzheng-phone">
								<span class="span-title" align="right">手机号：</span>
								<input type="text" name="" class="fill-phone" placeholder="请输入手机号" maxlength="11">
							</div>
							<span class="phone-msg msg"></span>

							<div class="yanzheng-ma">
								<span class="span-title" align="right">验证码：</span>
								<input type="text" name="" class="fill-ma" placeholder="请输入验证码">
								<input type="button" name="" value="点击免费获取" class="ma-button">
							</div>
							<span class="ma-msg msg"></span>
							<div class="yanzheng_center_cen_bottom_msg">
								
							</div>
							<input class="chongzhi" type="button" name="" value="立即重置">
						</div>
					</div>
				</div>
				<div class="update">
					<div class="update-iamge">
						<div class="update-iamge-cen">
							<img src="${ctx}/vij/images/set_jindu.png">
						</div>
					</div>
					<div class="update-title">
						<p>用户名&nbsp;&nbsp;1780*****65</p>
					</div>
					<div class="update-password">
						<div class="password-cen">
							<div class="password-xin">
								<span class="password-title" align="right">新的登录密码</span>
								<input type="password" name="" class="fill-password-xin" placeholder="请输入新的登录密码" maxlength="20">
							</div>
							<span class="password-tishi">必须是6-20位数字、字母、下划线</span>
							<span class="password-xin-msg p-msg"></span>

							<div class="password-xin">
								<span class="password-title" align="right">确认登录密码</span>
								<input type="password" name="" class="fill-password-que" placeholder="请确认登录密码" maxlength="20">
							</div>
							<span class="password-que-msg p-msg"></span>

							<input class="queren" type="button" name="" value="确认">
						</div>
					</div>
				</div>
				<div class="success">
					<div class="success-title">
						<img src="${ctx}/vij/images/gantanhao_12.png">
						<p>重置完成</p>
					</div>
					<div class="success-content">
						<img src="${ctx}/vij/images/chongzhichenggong_06.gif">
						<p>重置成功</p>
					</div>
				</div>
			</div>
		</div>
		<!-- 公共尾部 -->
        <jsp:include page="/vij/publich/footer.do" /> 
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			//保存手机号
			var phone="";
			//判断手机号是否为空
			$(".fill-phone").focusout(function(){
				if($(".fill-phone").val()==""){
					$(".phone-msg").text("手机号码不能为空");
				}
				else{
					$(".phone-msg").text("");
				}
			})
			//点击获取验证码
			$(".yanzheng-ma .ma-button").click(function(){
				if($(".fill-phone").val()==""){
					$(".phone-msg").text("手机号码不能为空");
				}
				else if(!(/^1[34578]\d{9}$/.test($(".fill-phone").val()))){
					$(".phone-msg").text("手机号码格式错误");
				}
				else{
					phone=$(".fill-phone").val();
					$.ajax({url:"${ctx}/vij/account/sendmsg.do",
						   data:{"type":2
							   ,"loginPlat":LOGIN_PLAT
							   ,"phone":$(".fill-phone").val()},
						   type:"POST",
						   success:function(data){
								if(data.code=="0"){
									//设置按钮不可点
									$(".yanzheng-ma .ma-button").attr("disabled",true);
									//设置倒计时
									var nums=180;
									var timer=null;
									function set_botton(){
										nums-=1;
										if (nums>0) {
											$(".yanzheng-ma .ma-button").val("剩余"+nums+"s");
										}
										if(nums==0){
											clearInterval(timer);
											$(".yanzheng-ma .ma-button").attr("disabled",false);
											$(".yanzheng-ma .ma-button").val("重新获取");
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
								}
							},
							error : function() {},
						   datatype:"json"
					});
				}
				
			})
			//点击立即重置
			$(".chongzhi").click(function(){
				if($(".fill-phone").val()==""){
					$(".phone-msg").text("手机号码不能为空");
				}
				else if(!(/^1[34578]\d{9}$/.test($(".fill-phone").val()))){
					$(".phone-msg").text("手机号码格式错误");
				}
				else{
					$.ajax({url:"${ctx}/vij/account/checkCode.do",
						   data:{"type":2
							   ,"loginPlat":LOGIN_PLAT
							   ,"phone":$(".fill-phone").val()
							   ,"code":$(".fill-ma").val()},
						   type:"POST",
						   success:function(data){
								if(data.code=="0"){
									$(".phone").css("display","none");
									$(".update").css("display","block");
									$(".update-title p").text("用户名      "+data.msg);
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

			//判断新密码
			$(".fill-password-xin").focusout(function(){
				if($(".fill-password-xin").val()==""){
					$(".password-xin-msg").text("密码不能为空");
				}
				else{
					$(".password-xin-msg").text("");
				}
			})
			//判断确认密码
			$(".fill-password-que").focusout(function(){
				if($(".fill-password-que").val()==""){
					$(".password-que-msg").text("确认密码不能为空");
				}
				else{
					$(".password-que-msg").text("");
				}
			})
			//点击确认
			$(".queren").click(function(){
				if($(".fill-password-xin").val()==""){
					$(".password-xin-msg").text("密码不能为空");
				}
				else if($(".fill-password-que").val()==""){
					$(".password-que-msg").text("确认密码不能为空");
				}
				else{
					if($(".fill-password-xin").val()!=$(".fill-password-que").val()){
						$(".password-que-msg").text("前后密码不一致");
					}
					else{
						//数字或字母或下划线组成
						var pass_string=$(".fill-password-xin").val();
						if(!/^\w+$/.test(pass_string)){
							$(".password-xin-msg").text("密码必须为数字字母或下划线");
						}
						else{
							if($(".fill-password-xin").val().length<6){
								$(".password-xin-msg").text("密码不能小于6位数");
							}
					    	else{
					    		$.ajax({url:"${ctx}/vij/account/findPwd.do",
									   data:{"newPwd":$(".fill-password-xin").val(),"phone":phone,"loginPlat":"709"},
									   type:"POST",
									   success:function(data){
											if(data.code=="0"){
												$(".update").css("display","none");
												$(".success").css("display","block");
									    		function go_login(){
									    			window.location.href="${ctx}/vij/account/login.do";
									    		}
									    		setTimeout(go_login,2000);			
											}
											if(data.code=="1"){
												$(".password-que-msg").text(data.msg);
											}
										},
										error : function() {
											//alert("重置失败");
										},
									   datatype:"json"
								});
					    		
					    	}
						}   	
					}
				}
			})
		})
	</script>
</body>
</html>	