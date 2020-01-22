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
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/math.min.js" charset="utf-8"></script>
    <title>购物车</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/common.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/product.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/index.css">
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
</head>

<body>
    <jsp:include page="/vij/publich/header.do" />
    <div class="page-product-cart" v-cloak>
        <div class="cart-content">
            <div class="cart-header">
                <p class="cart-product-all-counts">全部商品（{{productList.length}}）</p>
                <div class="choose-address flex-box">
                    <p class="choose-address-text">配送至</p>
                    <div class="choose-address-input">
                        <el-select v-model="address" filterable placeholder="请选择">
                            <el-option v-for="item in addressList" :label="item.shoppingAddress" :value="item.shoppingAddress">
                            </el-option>
                        </el-select>
                    </div>
                </div>
            </div>
            <div class="carts-title flex-box">
                <div class="title-item title-checkbox">
                    <el-checkbox data-indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAll">全选</el-checkbox>
                </div>
                <div class="title-item title-product">产品</div>
                <div class="title-item title-price">单价</div>
                <div class="title-item title-counts">数量</div>
                <div class="title-item title-subtotal">小计</div>
                <div class="title-item title-operate">操作</div>
            </div>
            <div class="product-list">
                <div class="cart-product-item flex-box" :class="product.checked==true?'high-light':''" v-for="(product,index) in productList">
                    <div class="cart-product-checkbox">
                        <el-checkbox-group v-model="checkProuctList" @change="handleCheckedProduct($event,index)">
                            <el-checkbox :label="product" :key="product.goodsId" @chang="handleCheckedProduct(index)">
                            </el-checkbox>
                        </el-checkbox-group>
                    </div>
                    <div class="cart-product-image">
                        <img :src="product.carGoods.goodsImage|getfirstImage">
                    </div>
                    <div class="cart-product-info flex-box">
                        <p class="cart-product-name">{{product.carGoods.goodsName}}</p>
                        <p class="cart-product-type">类型：
                            {{product.goodsSpec ? product.goodsSpec.type1 + "/" + product.goodsSpec.type2 : ''}}</p>
                    </div>
                    <div class="cart-product-price">{{product.carGoods.sellPrice}}</div>
                    <div class="cart-product-counts">
                        <el-input-number v-model="product.number" :min="1" @change="changeNumber($event,index)"></el-input-number>
                    </div>
                    <div class="cart-product-subtotal">{{product.subtotal}}</div>
                    <div class="cart-product-operate">
                        <div class="cart-product-operate-item">移入收藏夹</div>
                        <div class="cart-product-operate-item" @click="deleteProductInCart(index)">删除</div>
                    </div>
                </div>
            </div>
            <div class="bottom-bar flex-box">
                <div class="bottom-bar-left flex-box">
                    <div class="bottom-left-item bottom-checkbox">
                        <el-checkbox v-model="checkAll" @change="handleCheckAll">全选</el-checkbox>
                    </div>
                    <div class="bottom-left-item" @click="deleteChooseCart">删除选中物品</div>
                    <div class="bottom-left-item">清除失效宝贝</div>
                    <div class="bottom-left-item">移到我的关注</div>
                    <div class="bottom-left-item" @click="clearCart">清空购物车</div>
                </div>
                <div class="bottom-bar-right flex-box">
                    <div class="bottom-right-item">已选择{{checkProuctList.length}}件商品</div>
                    <div class="bottom-right-item bottom-bar-price">
                        <p class="bottom-price-sum">总价：{{countsPrice}}元</p>
                        <p class="bottom-save-sum">节省：{{countsSavePrice}}元</p>
                    </div>
                    <div class="go-counter" @click="goCounter">结算</div>
                </div>
            </div>
        </div>
    </div>
    <!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
    <script type="text/javascript" src="${ctx}/vij/js/common.js" charset="utf-8"></script>
    <script type="text/javascript">
    var USER_ID = "${vijMallUserSession.userId}";
    var vm = new Vue({
        el: '.page-product-cart',
        data: {
            address: '',
            addressList: [],
            checkAll: '', //选择全部
            checkProuctList: [],
            countsPrice: 0,
            countsSavePrice: 0,
            isIndeterminate: true,
            pageNo: 1,
            pageSize: 10,
            productList: [],
        },
        created: function() {
            this.addUserAction(); /* 事件上报，页面的其他方法在此方法后面写 */

            this.getData();
            this.getAddressList();
        },
        mounted: function() {},
        watch: {
            checkProuctList: function() {
                this.calculatePrice();
            }
        },
        filters: {
            getfirstImage: function(str) {
                var ret = str.split(',')[0]
                return ret;
            }
        },
        methods: {
            addUserAction: function() {
                addUserAction(USER_ID, 0, 7096, 1);
            },
            getData: function() {
                var _this = this;
                $.ajax({
                    url: '${ctx}/wx/shoppingCar/myShoppingCar.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        pageNo: this.pageNo,
                        pageSize: this.pageSize,
                        userId: USER_ID,
                    },
                }).done(function(res) {
                    console.log(res);
                    _this.productList = _this.productList.concat(res.objects);
                    _this.calculatePrice();
                });
            },
            getAddressList: function() {
                var _this = this;
                $.ajax({
                    url: '${ctx}/wx/shoppingAddress/findShoppingAddr.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        pageNo: 1,
                        pageSize: 10,
                        userId: USER_ID,
                        loginPlat: LOGIN_PLAT,
                    },
                }).done(function(res) {
                    console.log(res);
                    _this.addressList = _this.addressList.concat(res.objects);
                });
            },

            /**
             * [点击全选]
             * @param  {[isChecked]} isChecked [是否全选]
             * 遍历购物车列表，将二级目录(shop)和三级目录(product)全选/反选
             */
            handleCheckAll: function(value) {
                this.checkProuctList = value ? this.productList : [];
                this.isIndeterminate = false;
                for (var i = 0; i < this.productList.length; i++) {
                    this.productList[i].checked = value;
                }
            },

            /**
             * [点击选择产品]
             * @param  {[index]} list    [选中产品的数组]
             * @param  {[index]} productIndex [选中产品下标]
             */
            handleCheckedProduct: function(list, index) {
                console.log(this.checkProuctList)
                var checkedCount = list.length;
                this.checkAll = checkedCount === this.productList.length;
                this.$set(this.productList[index], "checked", !this.productList[index].checked)
            },

            /**
             * [改变商品数量]
             * @param  {[int]} number [改变后的数量]
             * @param  {[int]} index  [数组下标]
             */
            changeNumber: function(number, index) {
                this.calculatePrice(index);
                var product = this.productList[index];
                var _this = this;
                $.ajax({
                    url: '${ctx}/wx/shoppingCar/updateShopCarNumber.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        carId: product.carId,
                        number: number,
                    },
                }).done(function(res) {});
            },

            /**
             * 删除选中商品：与deleteProductInCart方法不一样
             * @return {[type]} [description]
             */
            deleteChooseCart: function() {
                var carIdList = [];
                for (var i = 0; i < this.checkProuctList.length; i++) {
                    var carId = this.checkProuctList[i].carId;
                    carIdList.push(carId);
                }
                var carIdsString = carIdList.join();
                this.deleteCartByString(carIdsString);
            },
            clearCart: function() {
                var carIdList = [];
                for (var i = 0; i < this.productList.length; i++) {
                    var carId = this.productList[i].carId;
                    carIdList.push(carId)
                }
                var carIdsString = carIdList.join();
                this.deleteCartByString(carIdsString);
            },
            deleteCartByString: function(string) {
                $.ajax({
                    url: '${ctx}/vij/order/delChooseCar.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        carIds: string,
                    },
                }).done((res) => {
                    console.log(res)
                    this.productList = [];
                    this.getData();
                });
            },

            /**
             * 删除商品
             * @param  {[int]} index 
             */
            deleteProductInCart: function(index) {
                var product = this.productList[index];
                var _this = this;
                _this.productList.splice(index, 1);
                $.ajax({
                    url: '${ctx}/wx/shoppingCar/removeGoods.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        carId: product.carId,
                    },
                }).done(function(res) {
                    console.log(res)
                    if (res.code == 0) {
                        for (var i = 0; i < _this.checkProuctList.length; i++) {
                            if (_this.checkProuctList[i].carId == product.carId) {
                                console.log(product.cardId)
                                _this.checkProuctList.splice(i, 1)
                            }
                        }
                    }
                });
            },

            /**
             * 计算总价,只有选中的商品才计算总价
             */
            calculatePrice: function(index) {
                for (var i = 0; i < this.productList.length; i++) {
                    var price = this.productList[i].carGoods.sellPrice;
                    var number = this.productList[i].number;
                    var subtotal = math.eval(price + "*" + number);
                    this.$set(this.productList[i], "subtotal", subtotal);
                }
                var countsPrice = 0;
                var countsSave = 0;
                for (var i = 0; i < this.checkProuctList.length; i++) {
                    var price = this.checkProuctList[i].carGoods.sellPrice;
                    var number = this.checkProuctList[i].number;
                    var prime = this.checkProuctList[i].carGoods.firstCost || price;
                    countsPrice = math.eval(countsPrice + "+" + price + "*" + number);
                    countsSave = math.eval(countsSave + "+(" + prime + "-" + price + ")*" + number)
                }
                this.countsPrice = countsPrice;
                this.countsSavePrice = countsSave;
            },
            /**
             * 前往结算
             */
            goCounter: function() {
                if (this.checkProuctList.length) {
                    var tempOrderList = [];
                    for (var i = this.checkProuctList.length - 1; i >= 0; i--) {
                        var orderItem = {
                            carId: this.checkProuctList[i].carId,
                            goodsid: this.checkProuctList[i].goodsId,
                            buyNumber: this.checkProuctList[i].number,
                            goodsName: this.checkProuctList[i].carGoods.goodsName,
                            goodsPrice: this.checkProuctList[i].carGoods.sellPrice,
                            goodsImage: this.checkProuctList[i].carGoods.goodsImage.split(',')[0],
                        }
                        tempOrderList.push(orderItem);
                    }
                    var tempOrderString = JSON.stringify(tempOrderList);
                    window.localStorage.setItem("TEMP_ORDER_LIST", tempOrderString);
                    window.location.href = "${ctx}/vij/order/counter.do";
                } else {
                    this.$message({
                        message: "未选择商品",
                        type: 'error',
                        icon: true,
                    });
                }
            },
        },
    });
    </script>
</body>

</html>