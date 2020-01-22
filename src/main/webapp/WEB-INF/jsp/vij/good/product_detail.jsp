<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>

<head>
    <meta charset="utf-8" />
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js" charset="utf-8"></script>
    <title>商品详情</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/common.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/product_detail.css" />
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
</head>

<body>
    <jsp:include page="/vij/publich/header.do" />
    <div class="page-product-detail" v-cloak>
        <div class="product-box flex-box">
            <div class="box-product-name">{{productInfo.goodsName}}</div>
            <div class="product-link flex-box">
                <a class="product-link-item" href="#">概述</a>
                <a class="product-link-item" href="#">参数</a>
                <a class="product-link-item" href="#">用户须知</a>
                <a class="product-link-item" href="#">用户评价</a>
            </div>
        </div>
        <div class="product-detail flex-box">
            <div class="product-show-image">
                <div class="product-image-hull">
                    <img class="product-image" :src="mainImageUrl">
                </div>
                <div class="product-thumb">
                    <div class="thumb-list">
                        <div class="thumb-item-hull" :class="index==thumbIndex?'high-light':''" v-for="(imageUrl,index) in imageList" @click="clickThumb(index)">
                            <img class="thumb-item" :src="imageUrl">
                        </div>
                    </div>
                </div>
            </div>
            <div class="product-info">
                <div class="product-name">{{productInfo.goodsName}}</div>
                <div class="product-slogan"></div>
                <div class="product-seckill" v-if="productInfo.isDtbt==1">
                    <div class="seckill-left">
                        <p class="seckill-price">秒杀价￥{{productInfo.sellPrice}}<span class="seckill-prime">￥{{productInfo.firstCost}}</span></p>
                        <p class="seckill-time">秒杀开始时间：{{seckillTime}}</p>
                    </div>
                    <div class="seckill-right">
                        <img class="seckill-background" src="${ctx}/vij/images/seckill-background.png">
                        <p class="seckill-countdown" v-if="seckillStatus!==101">倒计时：{{countdown}}</p>
                        <p class="seckill-status" v-if="seckillStatus==-1">等待秒杀中</p>
                        <p class="seckill-status" v-if="seckillStatus==101">秒杀已结束</p>
                        <p class="seckill-status" v-if="seckillStatus==1">秒杀进行中</p>
                    </div>
                </div>
                <div class="product-price" v-else>￥{{productInfo.sellPrice}}元</div>
                <div class="shipping-address" v-if="defaultAddress">
                    <img src="${ctx}/vij/images/detail-address-icon.png" />
                    <p>
                        {{defaultAddress.province}}
                        <span>{{defaultAddress.city}}</span>
                        <span>{{defaultAddress.area}}</span>
                        <span>{{defaultAddress.shoppingAddress}}</span>
                        <a href="${ctx}/vij/person/myaddress.do">修改</a>
                    </p>
                </div>
                <div class="product-spec flex-box" v-for="(spec,specIndex) in specTypeList">
                    <p class="spec-title">{{spec.name}}</p>
                    <div class="spec-list flex-box">
                        <div class="spec-item-hull" :class="chosenSpecList[specIndex]==itemIndex?'high-light':''" v-for="(item,itemIndex) in spec.specSet" @click="chooseSpec(specIndex,itemIndex)">
                            <p class="spec-item">{{item}}</p>
                            <img src="${ctx}/vij/images/high-light-pandant.png" class="choose-pandant">
                        </div>
                    </div>
                </div>
                <div class="product-counts flex-box">
                    <div class="counts-title">数量</div>
                    <div class="counts-input">
                        <el-input-number v-model="counts" controls-position="right" @change="changeCounts" :min="1" style="scoped"></el-input-number>
                    </div>
                    <p class="product-stock">库存{{productInfo.goodsStock}}件</p>
                </div>
                <div class="product-shopping flex-box">
                    <div class="button-buy" @click="buyNow">立即购买</div>
                    <div class="button-join-cart" @click="joinCart">加入购物车</div>
                </div>
            </div>
        </div>
        <div class="product-detail-more">
            <div class="more-item" :class="moreStatus==1?'high-light':''" @click="moreStatus=1">宝贝详情</div>
            <div class="more-item" :class="moreStatus==2?'high-light':''" @click="moreStatus=2">用户评价</div>
        </div>
        <div class="product-rich-text" v-show="moreStatus==1">
            <div class="product-rich-text-hull" v-html="productInfo.goodsDetails"></div>
        </div>
        <div class="product-comment" v-show="moreStatus==2">
            <div class="comment-tab">
                <div class="comment-tab-item" :class="commentStatus==0?'high-light':''" @click="changeComent(0)">全部评价</div>
                <div class="comment-tab-item" :class="commentStatus==3?'high-light':''" @click="changeComent(3)">好评</div>
                <div class="comment-tab-item" :class="commentStatus==2?'high-light':''" @click="changeComent(2)">中评</div>
                <div class="comment-tab-item" :class="commentStatus==1?'high-light':''" @click="changeComent(1)">差评</div>
            </div>
            <div class="comment-list">
                <div class="comment-item" v-for='comment in commentList'>
                    <div class="comment-avator">
                        <img :src="comment.headImage">
                    </div>
                    <div class="comment-nickname">{{comment|commentName}}</div>
                    <div class="comment-right">
                        <el-rate v-model="comment.score" disabled :colors="rateColors">
                        </el-rate>
                        <p class="comment-content">{{comment.content}}</p>
                        <div class="comment-item-iamges"></div>
                        <p class="comment-more">
                            <span class="comment-spec">黑色 16</span>
                            <span class="comment-time"></span>
                        </p>
                    </div>
                </div>
                <div class="pagination" v-if="pageTotal>0">
                    <el-pagination background layout="prev, pager, next" :total="pageTotal" @current-change="changePage">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
    <!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
    <script type="text/javascript" src="${ctx}/vij/js/common.js" charset="utf-8"></script>
    <script type="text/javascript">
    var goodsId = "${goodsId}";
    var USER_ID = "${vijMallUserSession.userId}";
    var vm = new Vue({
        el: '.page-product-detail',
        data: {
            goodsId: '',
            counts: 1,
            productInfo: '',
            imageList: [],
            mainImageUrl: '',
            thumbIndex: 0,
            typeIndex: 0,
            specList: [],
            specTypeList: [],
            chosenSpecList: [],
            specChosen: {}, //选中的规格
            interval: '', //计时器，秒杀倒计时
            countdown: 0,
            seckillTime: '', // 秒杀点
            commentList: '',
            commentPage: 1,
            rateColors: ['#d81e07', '#d81e07', '#d81e07'], // 评价星星的颜色
            commentStatus: 0,
            pageTotal: 0, // 评论条数，用于翻页
            moreStatus: 1,
            defaultAddress: '', //默认地址
        },
        created: function() {
            this.goodsId = goodsId;
            this.getData();
            this.getCommentList();
            this.getDefaultAddress();
        },
        mounted: function() {},
        watch: {

        },
        filters: {
            commentName: function(name) {
                if (name.userName) {
                    return name.userName;
                } else if (name.nickName) {
                    return name.nickName;
                } else {
                    return "匿名用户";
                }
            }
        },
        methods: {
            getData: function() {
                $.ajax({
                    url: '${ctx}/wx/goods/goodsDetails.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        goodsid: this.goodsId,
                        userId: USER_ID,
                    },
                }).done((res) => {
                    console.log(res);
                    if (res.code == 0) {
                        this.productInfo = res.object;
                        this.getImageUrl(res.object.goodsImage)
                        if (res.object.isMoreSpec == 1) {
                            this.recombineSpecList(res.object.specList, res.object.spectypeList);
                        }
                        if (res.object.isDtbt == 1) {
                            this.calculateCountdown(res.object.seckillStartTime, res.object.msTime);
                        }
                    }
                });
            },

            getCommentList: function() {
                $.ajax({
                    url: '${ctx}/vij/comment/getCommentList.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        goodsid: this.goodsId,
                        status: this.commentStatus,
                        page: this.commentPage,
                        limit: 10
                    },
                }).done((res) => {
                    console.log(res);
                    if (res.code == 0) {
                        this.commentList = res.data;
                        this.pageTotal = res.count;
                    } else if (res.code == 1) {
                        this.commentList = [];
                        this.pageTotal = 0;
                    }
                });
            },

            getDefaultAddress: function() {
                $.ajax({
                    url: '${ctx}/wx/shoppingAddress/selectOneAddressList.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        userId: USER_ID,
                    },
                }).done((res) => {
                    console.log("默认地址", res);
                    if (res.code == 0) {
                        this.defaultAddress = res.objects[0];
                    }
                });
            },

            changeComent: function(index) {
                this.commentStatus = index;
                this.commentPage = 1;
                this.getCommentList();
            },

            changePage: function(page) {
                this.commentPage = page + 1;
                this.getCommentList();
            },

            getImageUrl: function(imageString) {
                this.imageList = imageString.split(',');
                this.mainImageUrl = this.imageList[0];
            },

            clickThumb: function(index) {
                this.mainImageUrl = this.imageList[index];
                this.thumbIndex = index;
            },

            clickType: function(index) {
                this.typeIndex = index;
            },

            changeCounts: function(value) {
                this.counts = value;
            },

            joinCart: function() {
                if (this.check() === false) {
                    return;
                }
                var _this = this;
                $.ajax({
                    url: '${ctx}/wx/shoppingCar/addGoodsToCar.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        goodsId: this.goodsId,
                        userId: USER_ID,
                        number: this.counts,
                        specId: this.specChosen.id,
                    }
                }).done(function(res) {
                    if (res.code == 0) {
                        _this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                    } else {
                        _this.$message({
                            message: res.msg,
                            type: 'error'
                        });
                    }
                });
            },

            buyNow: function() {
                if (!this.check()) {
                    return;
                }
                var tempOrderList = [{
                    goodsid: this.goodsId,
                    buyNumber: 1,
                    goodsName: this.productInfo.goodsName,
                    goodsPrice: this.productInfo.sellPrice,
                    goodsImage: this.imageList[0],
                    specId: this.specChosen.id,
                    specTypeString: this.specChosen.type1 + '/' + this.specChosen.type2,
                }];
                var tempOrderString = JSON.stringify(tempOrderList)
                window.localStorage.setItem("TEMP_ORDER_LIST", tempOrderString);
                window.location.href = "${ctx}/vij/order/counter.do";
            },

            check: function() {
                if (!USER_ID) {
                    location.href = "${ctx}/vij/account/login.do";
                    return false;
                }
                if (this.seckillStatus === 101) {
                    this.$message({
                        message: "秒杀已结束",
                        type: 'error'
                    });
                    return false;
                }
                if (this.productInfo.isMoreSpec === 1  && (this.specChosen === undefined || this.specChosen.id === undefined)) {
                    this.$message({
                        message: "请选择商品规格~",
                        type: 'error'
                    });
                    return false;
                }
                return true;
            },
            /**
             * [recombineSpecList 重组多规格数组]
             * @param specList 多规格商品:每一种规格的商品为一个数组元素
             * @param specTypeList 多规格类型：每一种规格为一个数组元素
             * 该函数的主要目的是为specTypeList新增specSet属性
             */
            recombineSpecList: function(specList, specTypeList) {
                if (!specList || !specTypeList) {
                    return;
                }
                var price = specList[0].price;
                this.lowestPrice = specList[0].price;
                for (var i = 0; i < specTypeList.length; i++) {
                    var specTypeKey = "type" + specTypeList[i].type;
                    var specSet = new Set();
                    for (var j = 0; j < specList.length; j++) {
                        specSet.add(specList[j][specTypeKey]);
                        if (i == 0 && price > specList[j].price) {
                            // 在商品没有选择完规格的时候，价格要显示最低价
                            this.lowestPrice = specList[j].price;
                        }
                    }
                    specTypeList[i].specSet = Array.from(specSet);
                }
                this.specList = specList;
                this.specTypeList = specTypeList;
            },

            chooseSpec(specIndex, itemIndex) {
                var specList = this.chosenSpecList;
                console.log(specList)
                specList[specIndex] = itemIndex;
                console.log(specList)
                var index = -1;
                if (specList.length == this.specTypeList.length) {
                    for (var i = 0; i < this.specList.length; i++) {
                        if (index >= 0) {
                            break;
                        }
                        for (var j = 0; j < this.specTypeList.length; j++) {
                            var item = this.specList[i]["type" + (j + 1)];
                            if (this.specTypeList[j].specSet[specList[j]] == item) {
                                if (j == this.specTypeList.length - 1) {
                                    index = i;
                                    break;
                                }
                                continue;
                            }
                            break;
                        }
                    }
                }
                /**
                 * XXX--这是一个hack
                 * Vue不能直接赋值chosenSpecList
                 * 在之后会寻找相应方法
                 */
                this.chosenSpecList = [].concat(specList);
                this.specChosen = this.specList[index];
            },

            calculateCountdown: function(timestamp, continuingTime) {
                var timestamp = timestamp * 1000;
                date = new Date(timestamp),
                    nowTimestamp = Date.parse(new Date()),
                    // continuingTime = this.productInfo.msTime,
                    totalSecond = '';
                var dataArray = [date.getFullYear(),
                    Number(date.getMonth()) + 1,
                    date.getDate(),
                    date.getHours(),
                    date.getMinutes(),
                    date.getSeconds()
                ];
                this.seckillTime = getDateFormat(dataArray);

                if (nowTimestamp < timestamp) {
                    this.seckillStatus = -1; //未开始
                    totalSecond = (timestamp - nowTimestamp) / 1000;
                } else if (nowTimestamp > timestamp + continuingTime * 3600 * 1000) {
                    this.seckillStatus = 101; //已结束
                    return;
                } else {
                    this.seckillStatus = 1; //进行中
                    totalSecond = (timestamp - nowTimestamp) / 1000 + continuingTime * 3600;
                }
                this.interval = setInterval(() => {
                    // console.log("hi")
                    // 秒数
                    var second = totalSecond;

                    // 小时位
                    var hour = Math.floor(second / 3600);
                    var hourStr = hour.toString();
                    if (hourStr.length == 1) {
                        hourStr = '0' + hourStr;
                    }

                    // 分钟位
                    var minute = Math.floor((second - hour * 3600) / 60);
                    var minuteStr = minute.toString();
                    if (minuteStr.length == 1) {
                        minuteStr = '0' + minuteStr;
                    }

                    // 秒位
                    var sec = second - hour * 3600 - minute * 60;
                    var secondStr = sec.toString();
                    if (secondStr.length == 1) {
                        secondStr = '0' + secondStr;
                    }

                    var countDown = hourStr + ":" + minuteStr + ":" + secondStr;
                    this.countdown = countDown;

                    totalSecond--;

                    if (totalSecond < 0) {
                        clearInterval(this.interval);
                        this.calculateCountdown(timestamp/1000, continuingTime);
                    }
                }, 1000);

                function getDateFormat(array) {
                    console.log(array)
                    var ret = '';
                    var gap = ['-', '-', ' ', ':', ':', ''];
                    for (var i = 0; i < array.length; i++) {
                        if (array[i] < 10) {
                            ret += "0" + array[i];
                        } else {
                            ret += array[i]
                        }
                        ret += gap[i];
                    }
                    return ret;
                }
            }
        },
    });
    </script>
</body>

</html>