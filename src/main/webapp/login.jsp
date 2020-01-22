<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
<%--     <link rel="icon" href="${ctx}/images/titleIcon.png" type="image/x-icon"/> --%>
    <title>登录</title>  
    <!-- <link rel="icon" href="favicon.ico"> -->
    <link rel="stylesheet" href="${ctx}/css/login1.css">
    <link rel="stylesheet" href="${ctx}/css/login2.css">
<%--     <link rel="stylesheet" href="${ctx}/css/skin/default/layer.css">
 --%>    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>  
<%--     <script src="${ctx}/js/layer.js"></script>
 --%></head>
<body>
<div class="bg"></div>
<div class="container">
    <div class="line bouncein">
        <div class="xs6 xm4 xs3-move xm4-move">
            <div style="height:100px;"></div>
            <div class="media media-y margin-big-bottom">           
            </div>         
            <form action="${ctx}/login.do" method="post" id="loginform">
            <div class="panel loginbox">
                <div class="text-center margin-big padding-big-top"><h1>后台管理中心</h1></div>
                <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="text" class="input input-big" name="userName" id="userName" placeholder="登录账号" data-validate="required:请输入账号" />
                            <span class="icon icon-user margin-small"></span>
                        </div>  
                           <span style="color:#e33;margin-left:30px;line-height:30px"></span>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input input-big" name="password" id="passWord" placeholder="登录密码" data-validate="required:请输入密码" />
                            <span class="icon icon-key margin-small"></span>
                        </div>
                         <span style="color:#e33;margin-left:30px;line-height:30px">${errorMsg}</span>
                    </div>
<%--                     <div class="form-group">
                        <div class="field">
                            <input type="text" id ="codeImage" class="input input-big" name="imageCode" placeholder="填写右侧的验证码" data-validate="required:请输入验证码" />
                           <img id="imageCode" src="imageAction" alt="验证码" width="100" height="32" class="passcode" style="height:44px;cursor:pointer;" onclick="this.src=this.src+'?'">                   
                        </div>
                        <span style="color:#e33;margin-left:30px;line-height:30px">${imageMsg}</span>
                    </div> --%>
                </div>
                <div style="padding:10px 30px 30px 30px;"><input type="submit" class="button button-block bg-main text-big input-big" value="登录"/>
            </div>
           </div> 
            </form>          
        </div>
    </div>
</div>
<script type="text/javascript">
window.history.forward(1);
</script>
</body>
</html>