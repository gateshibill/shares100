<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>公共头部</title>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/js/element-ui.js"></script> <!-- 饿了么UI -->
	<link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css"><!-- 饿了么UI -->
	<link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/header.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/kefu.css">
    <style type="text/css">
    	.navigation-center-ri-sousuo .search{
    		padding:0rem 1rem;
    		font-size:1.0rem;
    	}
    	.search::-webkit-input-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
		
		.search:-moz-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
		
		.search::-moz-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
		
		.search:-ms-input-placeholder {
		    color: #999;
		    font-size:1.0rem;
		}
    </style>
</head>
<body>
    <div class="top"> 
        <div class="top-center">
            <div class="top-center-le">
                <p>${systemUpset.welcomText}</p>
            </div>
            <div class="top-center-ri">
                <ul class="flex-box">
                    <li class="top-center-ri-li1"><a href="/">唯爱家商城</a></li>
                    <c:if test="${empty vijMallUserSession}">
                        <li class="top-center-ri-li2"><a href="${ctx}/vij/account/login.do">登录</a></li>
                        <li class="top-center-ri-li3"><a href="${ctx}/vij/account/register.do">注册</a></li>
                    </c:if>
                    <c:if test="${not empty vijMallUserSession}">
                        <li class="top-center-ri-li6" onmouseenter="showMe()" onmouseleave="hideMe()">
                            <a href="${ctx}/vij/person/index.do" class="ri-li6-a">
                            	<c:choose>
                            		<c:when test="${not empty vijMallUserSession.userName}">
                            			${vijMallUserSession.userName}
                            		</c:when>
                            		<c:otherwise>
                            			 <c:choose>
                            			 	<c:when test="${not empty vijMallUserSession.nickName}">
                            			 	     ${vijMallUserSession.nickName}
                            			 	</c:when>
                            			 	<c:otherwise>
                            			 		 ${vijMallUserSession.realName}
                            			 	</c:otherwise>
                            			 </c:choose>
                            		</c:otherwise>
                            	</c:choose>
                            </a>
                            <div class="top-center-ri-li6-div" style="display: none">
                                <p align="center"><a href="javascript:void(0)" onclick="user_center();">个人中心</a></p>
                                <p align="center"><a href="javascript:void(0)" onclick="quit();">退出登录</a></p>
                            </div>
                        </li>
                    </c:if>
                    <li class="top-center-ri-li4"><a href="${ctx}/vij/person/message.do"><img src="${ctx}/vij/images/weiaijai_xiaoxi.png" class="top-center-ri-msg"></a></li>
                    <li class="top-center-ri-li5"><a href="${ctx}/vij/order/cart.do" style="display:flex;justify-content: space-around;item-align:center;padding:0.1rem"><img style="float:left;" src="${ctx}/vij/images/weiaijai_gouwuche.png">
                            <p style="float:left;line-height:1.1rem;margin-left:0.4rem">购物车（${carCount}）</p>
                        </a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="navigation">
        <div class="navigation-center">
            <div class="navigation-center-le">
                <div class="navigation-center-logo">
                    <a href="${ctx}/vij/index/index.do" title="唯爱家商城首页"><img src="${systemUpset.companyLogo}"></a>
                </div>
            </div>
            <div class="navigation-center-ri">
                <div class="navigation-center-ri-na">
                    <ul>
                        <li class="navigation-center-ri-li1"><a href="/">首页</a></li>
                        <li class="navigation-center-ri-li2"><a href="${ctx}/vij/index/good.do">新品</a></li>
                        <li class="navigation-center-ri-li3"><a href="${ctx}/vij/index/coll.do">搭配</a></li>
                        <li class="navigation-center-ri-li3"><a href="${ctx}/vij/index/adviser.do">顾问</a></li>
                        <li class="navigation-center-ri-li5"><a href="${ctx}/vij/index/welcome.do">我们</a></li>
                    </ul>
                </div>
                <div class="navigation-center-ri-so">
                    <div class="navigation-center-ri-sousuo">
                        <input class="search" type="text" name="" id="keyWord" placeholder="请输入搜索关键字">
                        <button onclick="search();"><img src="${ctx}/vij/images/sousuo_18.png"></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="suspension" v-cloak>
	<div class="suspension-box">
		<a href="#" class="a a-service"><i class="i"></i></a>
		<a href="javascript:;" class="a a-service-phone "><i class="i"></i></a>
		<a href="javascript:;" class="a a-qrcode" title="算算装修需要花多少钱？"><i class="i"></i></a>
		<a href="${ctx}/vij/order/cart.do" class="a a-cart"><i class="i"></i></a>
		<a href="javascript:;" class="a a-top"><i class="i"></i></a>
		<div class="d d-service">
			<i class="arrow"></i>
			<div class="inner-box">
				<div class="d-service-item clearfix">
					<a href="http://wpa.qq.com/msgrd?v=3&uin=${systemUpset.qq}&site=qq&menu=yes" target="_blank" class="clearfix"><span class="qqcircle"><i class="i-qq"></i></span><h3>咨询在线客服</h3></a>
				</div>
			</div>
		</div>
		<div class="d d-service-phone">
			<i class="arrow"></i>
			<div class="inner-box">
				<div class="d-service-item clearfix">
					<span class="qqcircle"><i class="i-tel"></i></span>
					<div class="text">
						<p>服务热线</p>
						<p class="red number">${systemUpset.phone}</p>
					</div>
				</div>
				<div class="d-service-intro clearfix">
					<p><i></i>功能和特性</p>
					<p><i></i>价格和优惠</p>
					<p><i></i>获取内部资料</p>
				</div>
			</div>
		</div>
        <div class="price_wai">
            <div class="price">
                <div class="price-image">
                     <img alt="" src="${ctx}/vij/images/tankuangbeijing01.png">
                </div>
                <div class="price-content">
                    <div class="price-center">
                        <div class="center-cen">
                            <div class="center-content">
                                <div class="content-counter">
                                    <el-form label-width="6rem" size="mini" :model="counterInfo" :rules="counterRule" ref="counter">
                                        <h2 align="left">装修计算器</h2>

                                        <el-form-item label="所在城市：" prop="region">
                                            <el-cascader :options="provinceList" v-model="counterInfo.region" @change="handleChangeArea" change-on-select filterable :props="props" placeholder="所在城市">
                                            </el-cascader>
                                        </el-form-item>

                                        <el-form-item label="房屋面积：" prop="houseArea">
                                            <el-input v-model="counterInfo.houseArea" placeholder="请输入您的房屋面积" maxlength="10"></el-input>
                                            <span class="span-danwei">m<sup>2</sup></span>
                                        </el-form-item>

                                        <el-form-item prop="housetype" label="房屋类型：">
                                            <template>
                                                <el-radio v-model="counterInfo.housetype" label="新房">新房</el-radio>
                                                <el-radio v-model="counterInfo.housetype" label="二手房">二手房</el-radio>
                                            </template>
                                        </el-form-item>

                                        <el-form-item label="房屋户型：">
                                            <div class="counter-input housetype-input">
                                                <div class="div-span">
                                                    <span>{{typeShi}}</span>
                                                    <span>{{typeTing}}</span>
                                                    <span>{{typeChu}}</span>
                                                    <span>{{typeWei}}</span>
                                                    <span>{{typeYang}}</span>
                                                    <span><img src="${ctx}/vij/images/free_priceima.png" @click="showUl"></span>
                                                </div>
                                                <ul class="ul-li" :style="ulStyleObj">
                                                    <li>
                                                        <span align="center" @click="setType(1,1)" :class="{span: istypeShi[0]}">1室</span>
                                                        <span align="center" @click="setType(1,2)" :class="{span: istypeShi[1]}">2室</span>
                                                        <span align="center" @click="setType(1,3)" :class="{span: istypeShi[2]}">3室</span>
                                                        <span align="center" @click="setType(1,4)" :class="{span: istypeShi[3]}">4室</span>
                                                        <span align="center" @click="setType(1,5)" :class="{span: istypeShi[4]}">5室</span>
                                                        <span align="center" @click="setType(1,6)" :class="{span: istypeShi[5]}">6室</span>
                                                    </li>
                                                    <li>
                                                        <span align="center" @click="setType(2,1)" :class="{span: istypeTing[0]}">1厅</span>
                                                        <span align="center" @click="setType(2,2)" :class="{span: istypeTing[1]}">2厅</span>
                                                        <span align="center" @click="setType(2,3)" :class="{span: istypeTing[2]}">3厅</span>
                                                        <span align="center" @click="setType(2,4)" :class="{span: istypeTing[3]}">4厅</span>
                                                        <span align="center" @click="setType(2,5)" :class="{span: istypeTing[4]}">5厅</span>
                                                        <span align="center" @click="setType(2,6)" :class="{span: istypeTing[5]}">6厅</span>
                                                    </li>
                                                    <li>
                                                        <span align="center" @click="setType(3,1)" :class="{span: istypeChu[0]}">1厨</span>
                                                        <span align="center" @click="setType(3,2)" :class="{span: istypeChu[1]}">2厨</span>
                                                        <span align="center" @click="setType(3,3)" :class="{span: istypeChu[2]}">3厨</span>
                                                        <span align="center" @click="setType(3,4)" :class="{span: istypeChu[3]}">4厨</span>
                                                        <span align="center" @click="setType(3,5)" :class="{span: istypeChu[4]}">5厨</span>
                                                        <span align="center" @click="setType(3,6)" :class="{span: istypeChu[5]}">6厨</span>
                                                    </li>
                                                    <li>
                                                        <span align="center" @click="setType(4,1)" :class="{span: istypeWei[0]}">1卫</span>
                                                        <span align="center" @click="setType(4,2)" :class="{span: istypeWei[1]}">2卫</span>
                                                        <span align="center" @click="setType(4,3)" :class="{span: istypeWei[2]}">3卫</span>
                                                        <span align="center" @click="setType(4,4)" :class="{span: istypeWei[3]}">4卫</span>
                                                        <span align="center" @click="setType(4,5)" :class="{span: istypeWei[4]}">5卫</span>
                                                        <span align="center" @click="setType(4,6)" :class="{span: istypeWei[5]}">6卫</span>
                                                    </li>
                                                    <li>
                                                        <span align="center" @click="setType(5,1)" :class="{span: istypeYang[0]}">1阳台</span>
                                                        <span align="center" @click="setType(5,2)" :class="{span: istypeYang[1]}">2阳台</span>
                                                        <span align="center" @click="setType(5,3)" :class="{span: istypeYang[2]}">3阳台</span>
                                                        <span align="center" @click="setType(5,4)" :class="{span: istypeYang[3]}">4阳台</span>
                                                        <span align="center" @click="setType(5,5)" :class="{span: istypeYang[4]}">5阳台</span>
                                                        <span align="center" @click="setType(5,6)" :class="{span: istypeYang[5]}">6阳台</span>
                                                    </li>
                                                </ul>
                                            </div> 
                                        </el-form-item>
                                        
                                        <el-form-item label="手机号码：" prop="counterPhone">
                                            <el-input v-model="counterInfo.counterPhone" placeholder="报价结果会发送至您的手机" maxlength="11"></el-input>
                                        </el-form-item>
                                    </el-form>
                                </div>
                                <div class="content-count">
                                    <div class="count-image">
                                        <img src="${ctx}/vij/images/count_1.png" @click="handleCounter('counter')">
                                    </div>
                                </div>
                                <div class="content-result">
                                    <p class="result-re">您家的装修预算<span>{{priceInfo.zongPrice}}</span>元</p>
                                    <div class="result-type">
                                        <p>材料费：{{priceInfo.cailiaoPrice}}元</p>
                                        <p>人工费：{{priceInfo.rengongPrice}}元</p>
                                        <p>设计费：{{priceInfo.shejiPrice}}元</p>
                                        <p>质检费：{{priceInfo.zhijianPrice}}元</p>
                                        <div class="result-type-ty">
                                            <input type="radio" name="" checked="checked">
                                            <span>基装</span>
                                            <input type="radio" name="" checked="checked">
                                            <span>主材</span>
                                            <input type="radio" name="" checked="checked">
                                            <span>软包</span>
                                            <input type="radio" name="" checked="checked">
                                            <span>家电</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="hiden-price">
                        <p>X</p>        
                    </div>
                </div>
            </div> 
        </div>
	</div>
</div>
	<script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/vij/distpicker/distpicker.data.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	/* ----- 侧边悬浮 ---- */
    	 $(document).on("mouseenter", ".suspension .a", function(){
    		var _this = $(this);
    		var s = $(".suspension");
    		var isService = _this.hasClass("a-service");
    		var isServicePhone = _this.hasClass("a-service-phone");
    		if(isService){ s.find(".d-service").show().siblings(".d").hide();}
    		if(isServicePhone){ s.find(".d-service-phone").show().siblings(".d").hide();}
    	});
    	 $(document).on("mouseleave", ".a-service-phone", function(){
     		$(".suspension").find(".d-service-phone").hide();
     	});
    	$(document).on("mouseleave", ".suspension, .suspension .a-top", function(){
    		$(".suspension").find(".d").hide();
    	});
    	$(document).on("mouseenter", ".suspension .a-top", function(){
    		$(".suspension").find(".d").hide(); 
    	});
    	$(document).on("click", ".suspension .a-top", function(){
    		$("html,body").animate({scrollTop: 0});
    	});
    	$(window).scroll(function(){
    		var st = $(document).scrollTop();
    		var $top = $(".suspension .a-top");
    		if(st > 400){
    			$top.css({display: 'block'});
    		}else{
    			if ($top.is(":visible")) {
    				$top.hide();
    			}
    		}
    	});
        //设置宽度
        $(".price_wai").width($(".top").width());
        //判断是否弹出框
        var showPrice = "${showPrice}";
        if(showPrice=="t"){
        	$(".price_wai").css("display", "block");
        }
        //弹出弹框
        $(".a-qrcode").click(function(){
        	var hiden = "${hiden}";
        	if(hiden != "f"){
        		$(".price_wai").css("display", "block");
        	}
        })
        //关闭弹框
        $(".hiden-price p").click(function(){
        	$(".price_wai").css("display", "none");
        })
    });	

    function quit() {
        $.ajax({
            url: '${ctx}/vij/account/outlogin.do',
            type: 'post',
            dataType: 'json',
            data: {},
            success: function(data) {
                window.location.reload();
            },
            error: function(data) {}
        })
    }
    function search(){
    	var keyWord = $('#keyWord').val();
    	if(keyWord){
    		window.location.href = "${ctx}/vij/index/list.do?goodsName="+keyWord;
    	}
    }
    function user_center() {
    	window.location.href='${ctx}/vij/person/index.do';  
    }
    function showMe() {
        $(".top-center-ri-li6-div").fadeIn();
    }

    function hideMe() {
        $(".top-center-ri-li6-div").fadeOut();
    }
    var vm = new Vue({
        el: ".suspension",
        data: {
        	typeShi : '1室',
			typeTing : '1厅',
			typeChu : '1厨',
			typeWei : '1卫',
			typeYang : '1阳台',
			istypeShi : [],
			istypeTing : [],
			istypeChu : [],
			istypeWei : [],
			istypeYang : [],
         	counterInfo : '',
         	priceInfo : '',
         	ulStyleObj : {
         		display: 'none'
         	},
         	props : {
                value: 'name',
                children: 'children',
                label: 'name'
            },
        },
        created:function(){
        	this.provinceList = PROVINCE_LIST;
            for (var i = 0; i < this.provinceList.length; i++) {
                var list = this.provinceList[i].children;
                for (var j = 0; j < list.length; j++) {
                    delete list[j].children; 
                }
            }
         	this.counterInfo = {
               houseArea : '',
               housetype : '新房',
               houseHuType : '',
               counterPhone : '',
               province : '',
               city : '',
               region : [],
            };
            this.priceInfo = {
            	zongPrice : '510000',
            	cailiaoPrice : '90000',
            	rengongPrice : '80000',
            	shejiPrice : '70000',
            	zhijianPrice : '8000',
            }
            this.istypeShi = [true,false,false,false,false,false];
			this.istypeTing = [true,false,false,false,false,false];
			this.istypeChu = [true,false,false,false,false,false];
			this.istypeWei = [true,false,false,false,false,false];
			this.istypeYang = [true,false,false,false,false,false]; 
            this.setCounterRule();
            this.setPrice();
        },
        methods: {
        	handleChangeArea: function(val) {
                this.counterInfo.province = val[0];
                this.counterInfo.city = val[1];
            },
            showUl: function(val) {
            	if (this.ulStyleObj.display=='none') {
            		this.ulStyleObj.display='block';
            	}
                else{
                	this.ulStyleObj.display='none';
                }
            },
            setType: function(hangType,lieType){
            	var _this=this;
            	if(hangType==1){
            		_this.typeShi=lieType+"室";
            		for(var i=0; i<6; i++){
            			if(lieType-1==i){
            				_this.istypeShi[i]=true;
            			}
            			else{
            				_this.istypeShi[i]=false;
            			}
            		}
            	}
            	if(hangType==2){
            		_this.typeTing=lieType+"厅";
            		for(var i=0; i<6; i++){
            			if(lieType-1==i){
            				_this.istypeTing[i]=true;
            			}
            			else{
            				_this.istypeTing[i]=false;
            			}
            		}
            	}
            	if(hangType==3){
            		_this.typeChu=lieType+"厨";
            		for(var i=0; i<6; i++){
            			if(lieType-1==i){
            				_this.istypeChu[i]=true;
            			}
            			else{
            				_this.istypeChu[i]=false;
            			}
            		}
            	}
            	if(hangType==4){
            		_this.typeWei=lieType+"卫";
            		for(var i=0; i<6; i++){
            			if(lieType-1==i){
            				_this.istypeWei[i]=true;
            			}
            			else{
            				_this.istypeWei[i]=false;
            			}
            		}
            	}
            	if(hangType==5){
            		_this.typeYang=lieType+"阳台";
            		for(var i=0; i<6; i++){
            			if(lieType-1==i){
            				_this.istypeYang[i]=true;
            			}
            			else{
            				_this.istypeYang[i]=false;
            			}
            		}
            	}

            },
        	handleCounter: function(name){
            	var _this = this;
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        _this.addCounter();
                    }
                });
            },
            addCounter: function(){
            	var co=this.counterInfo;
            	var houseDoorModel=this.typeShi+this.typeTing+this.typeChu
            	+this.typeWei+this.typeYang;
            	window.location.href="${ctx}/vij/budget/budget.do?cityCode="
					+co.province+co.city+"&buildingArea="+co.houseArea+
					"&mobilePhoneNo="+co.counterPhone+"&housingTypes="+co.housetype+"&houseDoorModel="+houseDoorModel;
            },
            setPrice : function(){
            	var po=this.priceInfo;
            	function zongPriceFun(){
            		po.zongPrice-=93756;
            		po.cailiaoPrice-=7895;
            		po.rengongPrice-=7895;
            		po.shejiPrice-=7895;
            		po.zhijianPrice-=895;

            		if (po.zongPrice>510000) {
            			po.zongPrice=210000;
            		}
            		if (po.zongPrice<110000) {
            			po.zongPrice=510000;
            		}
            		if (po.cailiaoPrice>90000) {
            			po.cailiaoPrice=90000;
            		}
            		if (po.cailiaoPrice<17000) {
            			po.cailiaoPrice=90000;
            		}
            		if (po.rengongPrice>80000) {
            			po.rengongPrice=80000;
            		}
            		if (po.rengongPrice<17000) {
            			po.rengongPrice=80000;
            		}
            		if (po.shejiPrice>70000) {
            			po.shejiPrice=70000;
            		}
            		if (po.shejiPrice<17000) {
            			po.shejiPrice=70000;
            		}
            		if (po.zhijianPrice>8000) {
            			po.zhijianPrice=80000;
            		}
            		if (po.zhijianPrice<1700) {
            			po.zhijianPrice=8000;
            		}
            	}
            	setInterval(zongPriceFun,200);
            },
            setCounterRule: function() {
                var validateRegion = (rule, value, callback) => {
                    if (value.length==0) {
                        return callback(new Error('请选择所在城市'));
                    }
                    return callback();
                };
                var validatehouseArea = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入您的房屋面积'));
                    }
                    if (!/^[1-9]+[0-9]*]*$/.test(value)) {
                        return callback(new Error('房屋面积只能输入数字'));
                    }
                    return callback();
                };
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
                this.counterRule = {
                    region: [
                        { required: true, validator: validateRegion, trigger: 'change' }
                    ],
                    houseArea:[
                        { required: true, validator: validatehouseArea, trigger: 'blur' }
                    ],
                    counterPhone: [
                        { required: true, validator: validatePhone, trigger: 'blur' }
                    ]
                }
            },
        }
    })				
    </script>
</body>

</html>