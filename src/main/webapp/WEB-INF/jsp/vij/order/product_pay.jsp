<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>

<head>
    <meta charset="utf-8" />
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/includeHTML.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/qrcode.min.js" charset="utf-8"></script>
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <title>商品支付</title>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/common.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/product.css" />
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
</head>

<body>
    <jsp:include page="/vij/publich/header.do" />
    <div class="page-product-pay" v-cloak>
        <div class="pay-content">
            <div class="pay-content-header flex-box">
                <div class="pay-header-qcode"></div>
                <div class="pay-header-tip">
                    <p style="font-size: 1.3rem;color: #41A18D;">订单提交成功,请尽快付款!</p>
                    <p>请您在24小时内完成支付,否则订单会被自动取消</p>
                    <div v-if="showOrderInfo">
                        <p>收货信息:{{orderInfo.consignee}} {{orderInfo.phone}} {{orderInfo.address}}</p>
                        <p>交易金额: {{orderInfo.realFee}}元</p>
                        <p>购买时间: {{orderInfo.createTime}}</p>
                        <p>订单号: <span style="color: #f00;">{{orderId}}</span></p>
                    </div>
                </div>
                <div class="pay-header-order">
                    <p class="pay-header-order-price">应付金额<span>{{orderInfo.realFee}}</span>元</p>
                    <div class="pay-header-order-detail" @click="showOrderInfo=!showOrderInfo">
                        <p>订单详情</p>
                        <img :src="showOrderInfo?'${ctx}/vij/images/pay-up.png':'${ctx}/vij/images/pay-down.png'">
                    </div>
                </div>
            </div>
            <div class="pay-list flex-box">
                <div class="pay-item" :class="payTypeIndex==index?'high-light':''" v-for="(type,index) in payTypeList" @click="clickPayType(index)">
                    <img :src="type.typeImage">
                </div>
                <div class="pay-now">
                    <div class="pay-now-button" @click="payNow">立即支付</div>
                </div>
            </div>
        </div>
        <el-dialog title="微信支付" :visible.sync="showWxpay" width="40rem">
            <div class="dialog-content flex-box">
                <div class="dialog-wxcode">
                    <div class="wxcode-image" id="wxcode-image"></div>
                    <p>请使用微信扫一扫</p>
                    <p>扫描二维码支付</p>
                </div>
                <div class="dialog-tip">
                    <img src="${ctx}/vij/images/pay_tip.png">
                </div>
            </div>
            <div class="dialog-footer flex-box">
                <div class="dialog-close dialog-button" @click="showWxpay = false">更换其他支付方式</div>
                <div class="dialog-finish dialog-button">支付成功</div>
            </div>
        </el-dialog>
    </div>
    <!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
    <script type="text/javascript" src="${ctx}/vij/js/common.js" charset="utf-8"></script>
    <script type="text/javascript">
    var USER_ID = "${vijMallUserSession.userId}";
    console.log("userId--" + USER_ID);
    var vm = new Vue({
        el: '.page-product-pay',
        data: {
            sumPrice: 0,
            showOrderInfo: false,
            orderId: '',
            orderInfo: '',
            qrcodeString: '',
            showWxpay: false,
            payTypeIndex: 1,
            payTypeList: [{
                typeId: 101,
                typeName: "支付宝",
                typeImage: '${ctx}/vij/images/pay_alipay.png',
            }, {
                typeId: 102,
                typeName: "微信",
                typeImage: '${ctx}/vij/images/pay_wxpay.png',
            }]
        },
        watch: {
            showWxpay: function(_new, _old) {
                var _this = this;
                if (_new) {
                    this.$nextTick(function() {
                        $('#wxcode-image').empty();
                        var qrcode = new QRCode('wxcode-image', {
                            text: _this.qrcodeString,
                            width: $(".dialog-wxcode").width(),
                            height: $(".dialog-wxcode").width(),
                            colorDark: '#000000',
                            colorLight: '#ffffff',
                            correctLevel: QRCode.CorrectLevel.L
                        });
                    })
                }
            }
        },
        created: function() {
            this.orderId = getUrlFooter().orderId;
            this.getQrcodeString();
            this.getOrderInfo();
        },
        mounted: function() {},
        methods: {
            clickPayType: function(index) {
                this.payTypeIndex = index;
            },
            payNow: function() {
                if (this.payTypeIndex == 1) {
                    this.showWxpay = true;
                }
            },
            getQrcodeString: function() {
                var _this = this;
                console.log(this.orderId)
                $.ajax({
                    url: '${ctx}/vij/pay/unifiedorder.do',
                    type: 'POST',
                    dataType: 'json',
                    data: { orderId: this.orderId, userId: USER_ID },
                }).done(function(res) {
                    if (res.code == 0) {
                        _this.qrcodeString = res.msg;
                    }
                });
            },
            getOrderInfo: function() {
                var _this = this;
                console.log(this.orderId)
                console.log('${ctx}/vij/order/getOrderById.do')
                $.ajax({
                    url: '${ctx}/vij/order/getOrderById.do',
                    type: 'POST',
                    dataType: 'json',
                    data: { orderId: this.orderId },
                }).done(function(res) {
                    console.log("订单信息--")
                    console.log(res)
                    if (res.code == 0) {
                        var orderInfo = res.objects[0];
                        orderInfo.objects = JSON.parse(orderInfo.objects);
                        _this.orderInfo = orderInfo;
                    }
                });
            }
        },
    });
    </script>
</body>

</html>