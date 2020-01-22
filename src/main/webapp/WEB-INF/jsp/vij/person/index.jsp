<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/element-ui-2.4.9.css"><!-- 饿了么UI -->
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/user_daifukuan.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js" charset="utf-8"></script>
</head>

<body>
    <jsp:include page="/vij/person/common.do" />
    <div class="page" v-cloak>
        <div class="centre">
            <div class="centre-center flex-box">
                <div class="centre-center-left">
                    <ul>
                        <li><a href="#" class="centre-center-left-a centre-center-left-a1">全部功能</a></li>
                        <li><a href="${ctx}/vij/order/cart.do" class="centre-center-left-a">我的购物车</a></li>
                        <li><a href="" class="centre-center-left-a">我的收藏</a></li>
                    </ul>
                </div>
                <div class="centre-center-right">
                    <div class="centre-center-right-user">
                        <div class="centre-center-right-user-s flex-box">
                            <div class="center-right-user-s-image">
                                <img src=" ${ctx}/vij/images/weiaijia_touxiang_03.png">
                            </div>
                            <ul class="center-right-user-s-ul flex-box">
                                <li class="center-right-user-s-li1"><a href="#">用户名（${not empty user.userName?user.userName:user.nickName}）</a></li>
                                <li><a href="${ctx}/vij/person/myaddress.do">我的收货地址</a></li>
                            </ul>
                        </div>
                        <div class="centre-center-right-user-x">
                            <ul class="flex-box">
                            	<li :class="status==1?'user-x-li':''" @click="changTab(1)">
                                    <div class="center-right-user-x-image">
                                        <img src="${ctx}/vij/images/all_order_03.png">
                                    </div>
                                    <p align="center"><span>所有订单</span>(<span class="allCount"></span>)</p>
                                </li>
                                <li :class="status==3?'user-x-li':''" @click="changTab(3)">
                                    <div class="center-right-user-x-image">
                                        <img src="${ctx}/vij/images/daifukuan_10.png">
                                    </div>
                                    <p align="center"><span>待付款</span>(<span class="dfkCount"></span>)</p>
                                </li>
                                <li :class="status==5?'user-x-li':''" @click="changTab(5)">
                                    <div class="center-right-user-x-image">
                                        <img src="${ctx}/vij/images/daifahuo_12.png">
                                    </div>
                                    <p align="center"><span>待发货</span>(<span class="dfhCount"></span>)</p>
                                </li>
                                <li :class="status==10?'user-x-li':''" @click="changTab(10)">
                                    <div class="center-right-user-x-image">
                                        <img src="${ctx}/vij/images/daishouhuo_14.png">
                                    </div>
                                    <p align="center"><span>待收货</span>(<span class="dshCount"></span>)</p>
                                </li>
                                <li :class="status==14?'user-x-li':''" @click="changTab(14)">
                                    <div class="center-right-user-x-image">
                                        <img src="${ctx}/vij/images/daipinjia_16.png">
                                    </div>
                                    <p align="center"><span>已收货</span>(<span class="dpjCount"></span>)</p>
                                </li>
                                <li :class="status==-1?'user-x-li':''" @click="changTab(-1)">
                                    <div class="center-right-user-x-image">
                                        <img src="${ctx}/vij/images/tuikuan_19.png">
                                    </div>
                                    <p align="center"><span>退款/售后</span>(<span class="tkCount"></span>)</p>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="centre-center-right-goods">
                        <div class="centre-center-right-goods-like flex-box">
                            <div class="goods-like-item long">宝贝</div>
                            <div class="goods-like-item">单价</div>
                            <div class="goods-like-item">数量</div>
                            <div class="goods-like-item">实付款</div>
                            <div class="goods-like-item">交易状态</div>
                            <div class="goods-like-item">付款</div>
                        </div>
                        <div class="center-right-orders">
                            <div class="order-items" v-for="order in orderList">
                                <div class="order-goods-list">
                                    <div class="goods-item" v-for="goods in order.objects">
                                        <img class="goods-image" src="${ctx}/vij/images/banchuang_49.png">
                                        <div class="goods-name-box">
                                            <a :href="'${ctx}/vij/index/gooddetail.do?goodsId='+goods.goodsId" class="centre-center-left-a">
                                            <p class="goods-name">{{goods.goodsName}}</p>
                                            </a>
                                            <p class="goods-type">{{goods.specTypeString}}</p>
                                        </div>
                                        <div class="goods-price">{{goods.sellPrice}}</div>
                                        <div class="goods-number">{{goods.buyNumber}}</div>
                                    </div>
                                </div>
                                <div class="order-status-box">
                                    <div class="order-subtatol">{{order.totalFee}}</div>
                                    <div class="order-status">{{order.orderStatus|statusFormat}}</div>
                                    <div class="order-event" v-if="order.orderStatus===3">
                                        <el-button size="mini" type="primary" @click="goPay(order.orderID)">去付款</el-button>
                                        <br />
                                        <el-button size="mini" type="text" @click="quitOrder(order.orderID)">
                                            <span style="color: #999;">取消订单</span>
                                        </el-button>
                                    </div>
                                    <div class="order-event" v-if="order.orderStatus===5">
                                        <el-button size="mini" type="primary">查看订单</el-button>
                                    </div>
                                    <div class="order-event" v-if="order.orderStatus===10">
                                        <el-button size="mini" type="primary" @click="takeGoods(order.orderID)">确认收货</el-button>
                                    </div>
                                    <div class="order-event" v-if="order.orderStatus===14">
                                        <el-button size="mini" type="primary">去评价</el-button>
                                    </div>
                                    <div class="order-event" v-if="order.orderStatus===-1">
                                        <el-button size="mini" type="primary">申请售后</el-button>
                                    </div>
                                    <div class="order-event" v-else></div>
                                </div>
                            </div>
                            <div class="pagination" v-if="pageTotal>0">
                                <el-pagination background layout="prev, pager, next" :total="pageTotal" @current-change="changePage">
                                </el-pagination>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="index-bottom"></div>
    <script type="text/javascript">
    USER_ID = "${vijMallUserSession.userId}";
    var vm = new Vue({
        el: '.page',
        data: {
            orderList: [],
            status: '',
            pageTotal: 0,
            pageNo: 1,
        },
        created: function() {
            this.status = 1;
            this.pageNo = 1;
            this.getOrderList();
        },
        filters: {
            statusFormat: function(status) {
                switch (status) {
	                case 1:
	                    return "全部订单";
	                    break;
                    case 3:
                        return "待付款";
                        break;
                    case 5:
                        return "待发货";
                        break;
                    case 10:
                        return "待收货";
                        break;
                    case 14:
                        return "已收货";
                        break;
                    case -1:
                        return "退款售后";
                        break;
                }
            }
        },
        methods: {
            getOrderList: function() {
                var _this = this;
                $.ajax({
                    url: '${ctx}/wx/goodsOrder/vijbusinessOrdersList.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        'pageNo': this.pageNo,
                        'pageSize': 10,
                        'userId': USER_ID,
                        'loginPlat': LOGIN_PLAT,
                        'orderStatus': this.status,
                    },
                }).done(function(res) {
                    console.log(res);
                    if (res.code == 0) {
                        console.log(res);
                        for (var i = 0; i < res.data.length; i++) {
                            res.data[i].objects = JSON.parse(res.data[i].objects);
                        }
                        _this.orderList = res.data;
                        _this.pageTotal = res.count;
                    } else if (res.code = 1) {
                        _this.orderList = [];
                        _this.pageTotal = 0;
                    }
                });
            },
            changTab: function(status) {
                this.status = status;
                this.pageNo = 1;
                this.getOrderList();
            },
            changePage: function(pageNo) {
                this.pageNo = pageNo;
                this.getOrderList();
            },
            goPay: function(orderId) {
                location.href = "${ctx}/vij/order/pay.do?orderId=" + orderId;
            },
            quitOrder: function(orderId) {
                var _this = this;
                $.ajax({
                    url: '${ctx}/wx/goodsOrder/quitgoodorder.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        userId: USER_ID,
                        orderId: orderId,
                    },
                }).done(function(res) {
                    if (res.code === "0") {
                        _this.$message({
                            message: res.msg,
                            type: 'success',
                        });
                        _this.getOrderList()
                    }
                });
            },
            takeGoods: function(orderId) {
                var _this = this;
                $.ajax({
                    url: '${ctx}///wx/goodsOrder/takeGoods.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        userId: USER_ID,
                        orderId: orderId,
                    },
                }).done(function(res) {
                    if (res.code === "0") {
                        _this.$message({
                            message: res.msg,
                            type: 'success',
                        });
                        _this.getOrderList();
                    }
                });
            },
        }
    })
    //订单数量
    function ordercount(){
    	$.ajax({url:"${ctx}/vij/order/getOrderCount.do",
			   data:{"loginPlat":LOGIN_PLAT},
			   type:"POST",
               datatype: "json",
			   success:function(data){
				   if(data.code==0){
					   $(".allCount").text(data.objects.allCount);
					   $(".dfkCount").text(data.objects.dfkCount);
					   $(".dfhCount").text(data.objects.dfhCount);
					   $(".dshCount").text(data.objects.dshCount);
					   $(".dpjCount").text(data.objects.dpjCount);
					   $(".tkCount").text(data.objects.tkCount);
				   }
				   if(data.code==1){
					   console.log(data.msg);
				   }
				   if(data.code==2){
					   console.log(data.msg);
				   }
				},
				error:function(data){
					
				}
			});
    }
    ordercount();
    </script>
    <!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
</body>

</html>