<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>修改资料</title>
    
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js"></script> <!-- 饿了么UI -->
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css"><!-- 饿了么UI -->
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/user_datum.css">
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
    <style type="text/css">
    .info select {
        border: 0.05rem solid #BFBFBF;
        background: #FFFFFF;
        width: 5.5rem;
        height: 1.8rem;
        margin-right: 0.5rem;
        float: left;
    }

    .info {
        margin-bottom: 0.5rem;
        text-align: center;
    }
    </style>
</head>

<body>
	<jsp:include page="/vij/person/common.do" />
    <div class="page">
        <div class="centre">
            <div class="centre-center flex-box">
                <div class="centre-center-left">
                    <a href="${ctx}/vij/person/account.do"><p class="left-setup">修改密码</p></a>
					<a href="${ctx}/vij/person/myaddress.do"><p class="left-address">收货地址</p></a>
					<a href="${ctx}/vij/person/myinfo.do"><p class="left-user">修改资料</p></a>
                </div>
                <div class="centre-center-right">
                    <div class="right-li-te">
                        <p class="li-te-title">个人资料</p>
                    </div>
                    <el-form ref="form" :model="form" label-width="80px">
                        <el-form-item label="头像：">
                            <el-upload
                              class="avatar-uploader"
                              action="${ctx}/vij/upload/upload.do"
                              :show-file-list="false"
                              :on-success="handleAvatarSuccess"
                              :on-error="handleAvatarFail"
                              >
                              <img v-if="form" :src="form.headImage" class="avatar">
                              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                        </el-form-item>  
                        <el-form-item label="昵称：">
                            <el-input v-model="form.realName"></el-input>
                        </el-form-item>                        
                        <el-form-item label="性别：">
                            <el-radio-group v-model="form.userSex">
                              <el-radio value="1" :label="1">男</el-radio>
                              <el-radio value="2" :label="2">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="手机：">
                            <el-input v-model="form.phone"></el-input>
                        </el-form-item>                          
                        <el-form-item label="生日：">
                            <el-date-picker
                              v-model="form.birthday"
                              type="date"
                              placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>  
                        <el-form-item>
                            <el-button type="primary" @click="uploadInfo">保存资料</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
        </div>
    </div>
    <div class="user-bottom"></div>
    <!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
    <script type="text/javascript" src="${ctx}/vij/distpicker/distpicker.data.js"></script>
    <script type="text/javascript">
    var USER_ID = "${vijMallUserSession.userId}";
    var vm = new Vue({
        el: ".page",
        data: {
            headImage:'',
            userSex:'',
            form:{
                headImage:'',
                userSex:'',
                realName:'',
                phone:'',
            }
        },
        created: function(){
            // this.form = {
            //     headImage:'',
            //     userSex:'',
            //     realName:'',
            //     phone:'',
            // }
            this.getUserinfo();
        },
        methods: {
            getUserinfo:function(){
                $.ajax({
                    url: "${ctx}/wx/user/getUserinfo.do",
                    data: {},
                    type: "POST",
                    datatype: "json",
                    success: (res) => {
                        var form = {
                            headImage: res.data.headImage,
                            realName: res.data.realName || res.data.nickName,
                            userSex: res.data.userSex,
                            phone: res.data.phone,
                        };
                        this.form = form;
                    },
                    error: (data) => {
                        this.$message({
                            message: data.msg,
                            type: 'error'
                        });
                    }
                })
            },
            handleAvatarSuccess(res) {
                console.log("handleAvatarSuccess",res)
                this.form.headImage = res.msg;
            },
            handleAvatarFail(res){
                console.log(res)

            },
            uploadInfo: function(){
                var form = this.form;
                form.userId = USER_ID;
                form.loginPlat = LOGIN_PLAT;
                $.ajax({
                    url: '${ctx}//wx/user/updateUserById.do',
                    type: 'POST',
                    dataType: 'json',
                    data: form,
                }).done((res) => {
                    this.$message({
                        message: res.msg,
                        type: 'success'
                    });
                }).fail((res) => {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                });
                
            },
            
        }
    });
    </script>
</body>

</html>