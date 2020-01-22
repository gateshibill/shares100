<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>修改密码</title>
	<link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css"><!-- 饿了么UI -->
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/safe_setup.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">	
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/vue.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/element-ui.js"></script> <!-- 饿了么UI -->
    
</head>
<body>
	<jsp:include page="/vij/person/common.do" />
	<div class="page" v-cloak>
		<div class="centre">
			<div class="centre-center flex-box">
				<div class="centre-center-left">
					<a href="${ctx}/vij/person/account.do"><p class="left-setup">修改密码</p></a>
					<a href="${ctx}/vij/person/myaddress.do"><p class="left-address">收货地址</p></a>
					<a href="${ctx}/vij/person/myinfo.do"><p class="left-user">修改资料</p></a>
				</div>
				<div class="centre-center-right">
					<div class="right-li li-1">
						<div class="right-li-text">
							<p class="li-text-title">修改登录密码</p>
							<div class="li-text-text flex-box">
								<p class="text-text-pass">互联网账号存在被盗风险，建议您定期修改登录密码，以保护账户安全</p>
							</div>
							<div class="li-text-pass">
								<div class="li-pass-div">
									<div class="pass-div-password flex-box">
										<input class="oldPwd" type="password" v-model="oldPwd" placeholder="当前密码" maxlength="20" @focusout="judgeOldPwd">
										<img src="${ctx}/vij/images/denglu_password_03.png">
									</div>
									<span class="password-msg msg">{{password_msg}}</span>
									
									<div class="pass-div-passwords flex-box">
										<input class="newPwd" type="password" v-model="newPwd" placeholder="新密码" maxlength="20" @focusout="judgeNewPwd">
										<img src="${ctx}/vij/images/denglu_password_03.png">
									</div>
									<span class="passwordx-msg msg">{{passwordx_msg}}</span>
	
									<div class="pass-div-passwords flex-box">
										<input class="confirmPwd" type="password" v-model="confirmPwd" placeholder="确认密码" maxlength="20" @focusout="judgeConfirmPwd">
										<img src="${ctx}/vij/images/denglu_password_03.png">
									</div>
									<span class="passwordq-msg msg">{{passwordq_msg}}</span>
									<span class="passwords-msg">密码由6-20位英文字母、数字或符号组成</span>
									<button class="pass-div-button" @click="querenUpdate">确认修改</button>
								</div>
							</div>
						</div>
					</div>
				</div> 
			</div>
		</div>
		<div class="user-bottom"></div>
	</div>
	<!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
	<script type="text/javascript">
	var vm = new Vue({
		el: ".page",
        data: {
        	oldPwd:'',
        	newPwd:'',
        	confirmPwd:'',
        	password_msg:'',
        	passwordx_msg:'',
        	passwordq_msg:''
        },
        filiters: {},
        methods: {
        	judgeOldPwd:function(){
            	//判断原始密码
        		if(this.oldPwd==""){
        			this.password_msg ='原始密码不能为空'; 
				}
				else{
					this.password_msg ='';
				}
            },
            judgeNewPwd:function(){
            	//判断新密码
            	if(this.newPwd==""){
            		this.passwordx_msg='新密码不能为空';
				}
				else{
					this.passwordx_msg='';
				}
            },
            judgeConfirmPwd:function(){
            	//判断确认密码
            	if(this.confirmPwd==""){
            		this.passwordq_msg='确认密码不能为空';
				}
				else{
					this.passwordq_msg='';
				}
            },
            querenUpdate:function(){
            	var _this=this;
            	if(this.oldPwd==""){
            		this.password_msg='原始密码不能为空';
				}
				else if(this.newPwd==""){
            		this.passwordx_msg='新密码不能为空';
				}
				else if(this.confirmPwd==""){
            		this.passwordq_msg='确认密码不能为空';
				}
				else{
					if(this.newPwd!=this.confirmPwd){
						this.passwordq_msg='新密码和确认密码不一致';
					}
					else{
						if(this.newPwd.length<6){
							this.passwordx_msg='新密码不能小于6位数';
						}
						else{
							if(!/^\w+$/.test(this.newPwd)){
								this.passwordx_msg='新密码只能为数字、字母、下划线';
							}
							else{
								$.ajax({url:"${ctx}/vij/account/updatePwd.do",
									   data:{"oldPwd":this.oldPwd,"newPwd":this.newPwd,"confirmPwd":this.confirmPwd},
									   type:"POST",
									   success:function(data){
										   if(data.code=="0"){
											    _this.$message({
				      	                            message: data.msg,
				      	                            type: 'success'
				      	                        });
											    function updatePage(){
											    	window.location.reload();
											    }
												setTimeout(updatePage,1500);											    
											}
											if(data.code=="1"){
												if(data.msg=="原始密码不能为空"||data.msg=="原始密码不正确"){
													console.log(data.msg);
													_this.password_msg=data.msg;
												}
												else{
													_this.passwordx_msg=data.msg;
												}
												
											}
										},
									   datatype:"text"
								});
							}
						}
						
					}
				}
            }
            
        }
	})
	
	</script>
</body>
</html>