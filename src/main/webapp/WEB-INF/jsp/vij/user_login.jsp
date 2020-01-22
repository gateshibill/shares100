<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>唯爱家·登录</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/user_login.css">
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
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
        <div class="denglu">
            <div class="denglu_image">
                <img src="${ctx}/vij/images/login_banner.png">
            </div>
                <div class="denglu_cen">
                    <div class="denglu_center">
                        <div class="denglu_center_content">
                            <div class="denglu_center_content_deng">
                                <div class="center_content_deng_biao flex-box">
                                    <p class="content_deng_biao_p" :class="loginStatus==1?'active':''" @click="loginStatus=1">微信登录</p>
                                    <p class="content_deng_biao_p" :class="loginStatus==2?'active':''" @click="loginStatus=2">账号登录</p>
                                </div>
                                <div class="center_content_deng_span" id="center_content_deng_span">
                                    <span :class="loginStatus==1?'active':''"></span>
                                    <span :class="loginStatus==2?'active':''"></span>
                                </div>
                                <ul>
                                    <li class="center_content_deng_weixin" v-if="loginStatus==1">
                                        <div class="content_deng_weixin_erweima">
                                            <img :src="codeUrl" id="codeUrl"/>
                                        </div>
                                            <p align="center">微信扫描登录</p>
                                    </li>
                                    <li class="center_content_deng_zhanghao" v-if="loginStatus==2">
                                        <div class="content_deng_zhanghao_biaodan">
                                            <el-form class="content_deng_zhanghao_biaodan_form" :model="loginEditInfo" :rules="loginRules" ref="login">
                                                <el-form-item prop="userName" class="flex-box">
                                                    <img class="deng_zhanghao_biaodan_name_ima" src="${ctx}/vij/images/yonghutubiao_1.png">
                                                    <el-input v-model="loginEditInfo.userName" placeholder="邮箱/用户名/手机号"></el-input>
                                                </el-form-item>
                                                <el-form-item prop="password" class="flex-box">
                                                    <img class="deng_zhanghao_biaodan_name_ima" src="${ctx}/vij/images/yonghutubiao.png">
                                                    <el-input type="password" v-model="loginEditInfo.password" placeholder="请输入密码" @keyup.enter.native="checkLoginInfo"></el-input>
                                                </el-form-item>
                                                <div class="flex-box">
                                                    <div class="deng_zhanghao_biaodan_zidong_left">
                                                        <el-checkbox v-model="remenberLoginChecked">下次自动登录</el-checkbox>
                                                    </div>
                                                    <div class="deng_zhanghao_biaodan_zidong_right">
                                                        <p align="right"><a href="${ctx}/vij/account/forgetPassword.do">忘记密码？</a></p>
                                                    </div>
                                                </div>
                                                <button type="button" class="content_deng_zhanghao_biaodan_deng" @click="checkLoginInfo">立即登录</button>
                                            </el-form>
                                        </div>
                                        <div class="content_deng_zhanghao_lianjie">
                                            <ul>
                                                <li class="content_deng_zhanghao_qq"></li>
                                                <li class="content_deng_zhanghao_weibo"></li>
                                                <li class="content_deng_zhanghao_zhuce">
                                                    <p align="right"><a href="${ctx}/vij/account/register.do">免费注册</a></p>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
        var checkLoginTimer;
        var vm = new Vue({
            el: '.page',
            data: {
                loginStatus: 2,
                userName: '',
                remenberLoginChecked: false,
                loginRules: '',
                loginEditInfo: {
                    userName: '',
                    password: '',
                },
                codeUrl:'',
            },
            created: function() {
                this.getRemenberInfo();
                this.setLoginInfo();
                this.codeUrl = "${ctx}/vij/images/weixin_22.gif";
                this.getCode(); //测试微信扫码
            },
            mounted: function() {

            },
            methods: {
                getRemenberInfo: function() {
                    if (this.getCookie("userName") != null) {
                        this.remenberLoginChecked = true;
                        this.loginEditInfo.userName = this.getCookie("userName");
                        this.loginEditInfo.password = this.getCookie("password");
                    }
                },
                loginNow: function() {
                    console.log(this.loginEditInfo)
                    $.ajax({
                        url: "${ctx}/vij/account/dologin.do",
                        data: {
                            account: this.loginEditInfo.userName,
                            password: this.loginEditInfo.password,
                            LoginPlat: LOGIN_PLAT,
                        },
                        type: "POST",
                        datatype: "json",
                        success: (res) => {
                            switch (res.code) {
                                case "0":
                                    this.remenberLogin();
                                    window.location.href = "${ctx}/vij/index/index.do";
                                    break;
                                case "1":
                                    this.$message({
                                        message: res.msg,
                                        type: "error"
                                    })
                                    break;
                                case "2":
                                    window.location.href = "${ctx}/vij/index/index.do";
                                    break;
                                default:
                                break;
                            }
                        },
                        error: function(data) {
                            //alert(JSON.stringify(data));
                        }
                    });
                },
                remenberLogin: function() {
                    if (this.remenberLoginChecked) {
                        this.setCookie("userName", this.loginEditInfo.userName);
                        this.setCookie("password", this.loginEditInfo.password);
                    } else {
                        this.delCookie("userName");
                        this.delCookie("password");
                    }
                },
                setCookie: function(name, value) {
                    var Days = 180;
                    var exp = new Date();
                    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
                    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();

                },
                delCookie: function(name) {
                    var exp = new Date();
                    exp.setTime(exp.getTime() - 1);
                    var cval = this.getCookie(name);
                    if (cval != null) {
                        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
                    }
                },
                getCookie: function(name) {
                    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
                    if (arr = document.cookie.match(reg)) {
                        return unescape(arr[2]);
                    } else {
                        return null;
                    }
                },
                checkLoginInfo: function() {
                    this.$refs["login"].validate((valid) => {
                        if (valid) {
                            this.loginNow();
                            this.remenberLogin();
                        }
                    });
                },
                //请求获取二维码
                getCode: function() {
                    $.ajax({
                        url: '${ctx}/wx/vijcodelogin/makeUserLoginCode.do',
                        type: 'post',
                        data: { loginPlat: LOGIN_PLAT },
                        dataType: 'json',
                        success: (data)=> {
                            if (data.code == 0) {
                                var obj = data.objects;
                                this.codeUrl = obj.codeUrl;
                                var vijuuid = obj.vijuuid;
                                checkLoginTimer = window.setInterval(()=> {
                                    this.checkLogin(vijuuid);
                                }, 1000)
                            }
                        },
                        error: function(data) {
                            console.log(this.data)
                        }
                    })
                },
                //轮询检测登陆
                checkLogin: function(vijuuid) {
                    //console.log(vijuuid);
                    $.ajax({
                        url: '${ctx}/wx/vijcodelogin/checkLogin.do',
                        type: 'post',
                        data: { 'uuid': vijuuid },
                        dataType: 'json',
                        success: function(data) {
                            //console.log("data---"+JSON.stringify(data));
                            if (data.code == 0) {
                                window.clearInterval(checkLoginTimer);
                                window.location.href = "${ctx}/vij/index/index.do";
                            } else {

                            }
                        },
                        error: function(data) {

                        }
                    })
                },
                setLoginInfo: function() {
                    this.loginRules = {
                        userName: [
                            { required: true, message: '邮箱/用户名/手机号', trigger: 'change' }
                        ],
                        password: [
                            { required: true, message: '请输入密码', trigger: 'change' }
                        ]
                    }
                }
            }
        });
        </script>
</body>

</html>