<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>家居设计</title>
	
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/vue.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/element-ui.js"></script> <!-- 饿了么UI -->
	<link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css"><!-- 饿了么UI -->
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/home_setup.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
	<link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
	
</head>
<body>
	<!-- 公共头部 -->
    <jsp:include page="/vij/publich/header.do" />
	<div class="page">
		<div class="shenqing" v-bind:style="{ 'z-index': zindex, 'display': none }">
			<div class="shenqing-cen">
				<div class="shenqing-content">
					<div class="shenqing-title">
						<p class="quxiao" align="right" @click="hideApply">X</p>
						<p class="shenqing-maxtitle" align="center">感谢您的申请</p>
						<p class="shenqing-mintitle" align="left">30分钟内唯爱家将会有客服与您联系，确认您的具体需求。
						</p>
					</div>
					<div class="shenqing-form">
						<el-form label-width="6rem" size="mini" :model="applyInfo" :rules="applyRule" ref="apply">
							<p class="form-title">由于申请的人数比较多，完善资料可以更快的获得审核服务</p>
							<el-form-item label="您的房型" prop="houseType">
							    <el-select v-model="applyInfo.houseType" placeholder="请选择您的房屋类型">
							       	<el-option label="新房" value="新房"></el-option>
							      	<el-option label="二手房" value="二手房"></el-option>
							      	<el-option label="办公室" value="办公室"></el-option>
							    </el-select>
							</el-form-item>

							<el-form-item prop="houseMianji" label="房屋面积">
                        <el-input v-model="applyInfo.houseMianji" placeholder="请输入您的房屋面积" maxlength="10"></el-input>
                        <span class="span-danwei">m<sup>2</sup></span>
                 		</el-form-item>

                    	 <el-form-item label="装修时间" prop="houseTime">
							   <el-select v-model="applyInfo.houseTime" placeholder="请选择您要装修的时间">
							      <el-option label="1个月内" value="1个月内"></el-option>
							     	<el-option label="2个月内" value="2个月内"></el-option>
							      <el-option label="3个月内" value="3个月内"></el-option>
							   </el-select>
						</el-form-item>

              			<el-form-item prop="houseAddress" label="楼盘地址">
                  		<el-input v-model="applyInfo.houseAddress" placeholder="请输入您的楼盘地址" maxlength="60"></el-input>
              			</el-form-item>
              			<el-button type="primary" @click="handleApply('apply')">立即提交</el-button>
          			</el-form>
					</div>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="banner">
				<div class="banner-image">
					<img src="${ctx}/vij/images/jiajubanner_02.png">
				</div>
				<div class="banner-content">
					<div class="banner-center">
						<div class="banner-form">
							<div class="form-f">
								<el-form item-with="19rem" size="medium" :model="predictInfo" :rules="predictRule" ref="predict">
									<h2 align="center">预约设计师</h2>
									<el-form-item prop="deliveryName">
		                        <el-input v-model="predictInfo.deliveryName" placeholder="请输入您的称呼" maxlength="10"></el-input>
		                    	</el-form-item>
		                    	<el-form-item prop="region">
		                        <el-cascader :options="provinceList" v-model="predictInfo.region" @change="handleChangeArea" change-on-select filterable :props="props" placeholder="所在省市">
		                        </el-cascader>
		                    	</el-form-item>
		                    	<el-form-item prop="deliveryPhone">
		                        <el-input v-model="predictInfo.deliveryPhone" placeholder="请输入您的手机号码" maxlength="11"></el-input>
		                    	</el-form-item>
		                    	<p class="f-mintitle">为了您的权益，您的信息将被严格保密</p>
		                    	<p align="center">
		                    		<el-button type="primary" @click="showApply('predict')">立即预约</el-button>
		                    	</p>
		                </el-form>

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="serve">
				<div class="serve-center">
					<div class="serve-top">
						<p align="center">服务流程</p>
					</div>
					<div class="serve-buttom">
						<div class="buttom-left">
							<div class="buttom-title">
								<p align="center">电话沟通量房时间</p>
							</div>
							<div class="buttom-yuyin">
								<img src="${ctx}/vij/images/1_05.png">
							</div>
						</div>
						<div class="buttom-right">
							<div class="buttom-title">
								<p align="center">专业设计师上门量房</p>
							</div>
							<div class="buttom-yuyin">
								<img src="${ctx}/vij/images/1_06.png">
							</div>
						</div>
						<div class="buttom-left">
							<div class="buttom-title">
								<p align="center">三个工作日出设计图</p>
							</div>
							<div class="buttom-yuyin">
								<img src="${ctx}/vij/images/1_05.png">
							</div>
						</div>
						<div class="buttom-right">
							<div class="buttom-title">
								<p align="center">帮您制定预算规划</p>
							</div>
							<div class="buttom-yuyin">
								<img src="${ctx}/vij/images/1_06.png">
							</div>
						</div>

					</div>
				</div>
			</div>
			<div class="item">
				<div class="item-cen">
					<div class="item-title">
						<p align="center">设计师项目</p>
					</div>
					<div class="item-content">
						<div class="content-left">
							<div class="item-image">
								<img src="${ctx}/vij/images/guwen-91.png">
								<p class="item-neme" align="center">张某某</p>
								<p class="item-jingyan" align="center">五年设计经验</p>
								<p class="item-zhiwei" align="center">唯爱家设计主管</p>
							</div>
							<div class="item-imageone">
								<img src="${ctx}/vij/images/2_05.png">
							</div>
							<div class="item-imagetwo">
								<div class="imagetwo-s">
									<img src="${ctx}/vij/images/3_07.png">
								</div>
								<div class="imagetwo-x">
									<img src="${ctx}/vij/images/4_10.png">
								</div>
							</div>
						</div>
						<div class="content-right">
							<div class="item-image">
								<img src="${ctx}/vij/images/guwen-91.png">
								<p class="item-neme" align="center">张某某</p>
								<p class="item-jingyan" align="center">五年设计经验</p>
								<p class="item-zhiwei" align="center">唯爱家设计主管</p>
							</div>
							<div class="item-imagetwo">
								<div class="imagetwo-s">
									<img src="${ctx}/vij/images/3_07.png">
								</div>
								<div class="imagetwo-x">
									<img src="${ctx}/vij/images/4_10.png">
								</div>
							</div>
							<div class="item-imageone">
								<img src="${ctx}/vij/images/2_05.png">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="all-set">
				<div class="set-cen">
					<div class="cen-title">
						<p class="set-title" align="left">全部设计师</p>
						<a href="">
							<p class="set-more" align="left">查看全部</p>
						</a>
						<p class="set-biao" align="center">></p>
					</div>
					<div class="cen-content">
						<ul class="flex-box">
							<li>
								<img src="${ctx}/vij/images/guwen-91.png">
								<p class="set-neme" align="center">张某某</p>
								<p class="set-jingyan" align="center">五年设计经验</p>
								<p class="set-zhiwei" align="center">唯爱家设计主管</p>
							</li>
							<li>
								<img src="${ctx}/vij/images/guwen-91.png">
								<p class="set-neme" align="center">张某某</p>
								<p class="set-jingyan" align="center">五年设计经验</p>
								<p class="set-zhiwei" align="center">唯爱家设计主管</p>
							</li>
							<li>
								<img src="${ctx}/vij/images/guwen-91.png">
								<p class="set-neme" align="center">张某某</p>
								<p class="set-jingyan" align="center">五年设计经验</p>
								<p class="set-zhiwei" align="center">唯爱家设计主管</p>
							</li>
							<li>
								<img src="${ctx}/vij/images/guwen-91.png">
								<p class="set-neme" align="center">张某某</p>
								<p class="set-jingyan" align="center">五年设计经验</p>
								<p class="set-zhiwei" align="center">唯爱家设计主管</p>
							</li>
						</ul>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
	<!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
	<script type="text/javascript" src="${ctx}/vij/distpicker/distpicker.data.js"></script>
	<script type="text/javascript">
		var vm = new Vue({
         el: ".page",
         data: {
         	predictInfo: '',
            addressList: [],
            provinceList: [],
            addressRule: '',
            applyInfo:'',
            props: {
                value: 'name',
                children: 'children',
                label: 'name'
            },
            zindex: '10',
            none:'none'
         },
         created:function(){
         	this.provinceList = PROVINCE_LIST;
            for (var i = 0; i < this.provinceList.length; i++) {
                var list = this.provinceList[i].children;
                for (var j = 0; j < list.length; j++) {
                    delete list[j].children; 
                }
            }
			this.predictInfo = {
               deliveryName: '',
               deliveryPhone: '',
               province: '',
               city: '',
               region: [],
           };
           this.applyInfo = {
                houseType: '',
	            houseMianji:'',
	            houseTime:'',
	            houseAddress:'',
           };
           this.setPredictRule();
           this.setApplyRule();
         },
         methods: {
      		showApply: function(name){
      			var _this = this;
               this.$refs[name].validate((valid) => {
                    if (valid) {
                        _this.zindex=10;
                        _this.none='block';
                        $(document.body).css({
                       	   "overflow-x":"hidden",
                       	   "overflow-y":"hidden"
                       	 });
                    }
               });
      		},
      		hideApply: function(){
      			this.zindex=-10;
      			this.none='none';
      			$(document.body).css({
      			   "overflow-x":"auto",
      			   "overflow-y":"auto"
      			 });
      		},
            handleChangeArea: function(val) {
                this.predictInfo.province = val[0];
                this.predictInfo.city = val[1];
            },
            handleApply: function(name){
            	var _this = this;
               this.$refs[name].validate((valid) => {
                    if (valid) {
                        _this.addApply();
                    }
               });
            },
            addApply: function(){
            	var pre = this.predictInfo;
            	var app = this.applyInfo;
            	var _this=this;
            	$.ajax({
                    url: "${ctx}/vij/homeSetup/addHomeSetup.do",
                    data: {
                    	city: pre.province+pre.city,
                    	phone: pre.deliveryPhone,
                    	callName: pre.deliveryName,
                    	homeType: app.houseType,
                    	homeArea: app.houseMianji,
                    	homeTime: app.houseTime,
                    	homeAddress: app.houseAddress
                    },
                    type: "POST",
                    datatype: "json",
                    success: function(data){
                    	if(data.code==0){
                    		_this.$message({
                                message: data.msg,
                                type: 'success'
                            });
                    		function updatePage(){
						    	window.location.reload();
						    }
							setTimeout(updatePage,1500);
                    	}
                    },
                    error: function(data) {
                        //alert(JSON.stringify(data));
                    }
                });
            },
            setPredictRule: function() {
                
                var validatePhone = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入手机号码'));
                    }
                    var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
                    var isPhoneNumber = reg.test(value);
                    if (!isPhoneNumber) {
                        return callback(new Error('请输入正确的手机号码'));
                    }
                    return callback();
                };

                this.predictRule = {
                    deliveryName: [
                        { required: true, message: '请输入您的称呼', trigger: 'blur' }
                    ],
                    region: [
                        { required: true, message: '请选择所在地区', trigger: 'change' }
                    ],
                    deliveryPhone: [
                        { required: true, validator: validatePhone, trigger: 'blur' }
                    ]
                }
            },
            setApplyRule:function(){
            	
            	var validateMianji = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入您的房屋面积'));
                    }
                    if(!/^[1-9]+[0-9]*]*$/.test(value)){
                    	return callback(new Error('房屋面积只能为数字'));
                    }
                    return callback();
               };
            	
            	this.applyRule = {
                    houseType: [
                        { required: true, message: '请选择您的房屋类型', trigger: 'change' }
                    ],
                    houseMianji: [
                        { required: true, validator: validateMianji, trigger: 'blur' }
                    ],
                    houseTime: [
                        { required: true, message: '请选择您的装修时间', trigger: 'change' }
                    ],
                    houseAddress: [
                        { required: true, message: '请输入您的楼盘地址', trigger: 'blur' }
                    ]
                }
            }
        }
      })
	</script>
</body>
</html>
