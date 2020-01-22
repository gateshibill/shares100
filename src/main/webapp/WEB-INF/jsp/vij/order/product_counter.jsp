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
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js"></script> <!-- 饿了么UI -->
    <script type="text/javascript" src="${ctx}/vij/js/math.min.js"></script> <!-- 精确计算 -->
    <title>商品结算</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css"><!-- 饿了么UI -->
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/common.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/product_counter.css" />
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
</head>

<body>
    <jsp:include page="/vij/publich/header.do" />
    <div class="page-product-counter" v-cloak>
        <div class="counter-content flex-box">
            <div class="counter-title">填写并核对订单信息</div>
            <div class="order-info">
                <div class="info-item">
                    <div class="info-item-title flex-box">
                        <p class="title-left">选择收获地址</p>
                        <p class="info-add-address" @click="showDialogBox(1)">新增收货地址</p>
                    </div>
                    <div class="info-item-content address-list flex-box">
                        <div class="address-item" v-for="(address,index) in addressList" @click="clickAddress(index)">
                            <img class="address-border" :src="addressIndex==index?'${ctx}/vij/images/ad-border-1.png':'${ctx}/vij/images/ad-border-0.png'">
                            <p class="receiver">
                                <span class="receiver">{{address.deliveryName}}</span>
                            </p>
                            <p class="address">
                                <span>{{address.province}}{{address.city}}{{address.area}}</span>
                                {{address.shoppingAddress}}
                            </p>
                            <div class="edit-address" @click.stop="showDialogBox(2,index)">修改</div>
                        </div>
                    </div>
                </div>
                <div class="info-item">
                    <div class="info-item-title">支付方式</div>
                    <div class="info-item-content pay-type-list flex-box">
                        <div class="pay-type-item" :class="index==payTypeIndex?'high-light':''" v-for="(type,index) in payTypeList" @click="clickPayType(index)">
                            <img src="${ctx}/vij/images/high-light-pandant.png" class="choose-pandant">
                            <p class="pay-type-text">{{type}}</p>
                        </div>
                    </div>
                </div>
                <div class="info-item">
                    <div class="info-item-title">确认订单信息</div>
                    <div class="info-item-content order-product-list">
                        <div class="order-title">
                            <p class="order-title-item order-title-product-name">店铺宝贝</p>
                            <p class="order-title-item">商品属性</p>
                            <p class="order-title-item">单价</p>
                            <p class="order-title-item">数量</p>
                            <p class="order-title-item">优惠方式</p>
                            <p class="order-title-item">小计</p>
                        </div>
                        <div class="order-product-item" v-for="order in orderList">
                            <div class="order-product-item-content">
                                <div class="order-product-image">
                                    <img :src="order.goodsImage">
                                </div>
                                <p class="order-product-name">{{order.goodsName}}</p>
                                <p class="order-product-type">{{order.specTypeString}}</p>
                                <p class="order-product-price">￥{{order.goodsPrice}}</p>
                                <p class="order-product-counts">{{order.buyNumber}}</p>
                                <p class="order-product-discount">无</p>
                                <p class="order-product-subtotal">￥{{order.goodsPrice}}</p>
                            </div>
                            <div class="order-more flex-box">
                                <div class="order-more-left">
                                    <div class="invoice-box">
                                        <div class="invoice-checkbox">
                                            <el-checkbox v-model="flageInvoice"></el-checkbox>
                                            <span>开具发票</span>
                                        </div>
                                        <div class="invoice-info" v-if="flageInvoiceReady">
                                            <p class="invoice-text">
                                                <span>{{invoiceEditInfo.typeName}}</span>
                                                <span>{{invoiceEditInfo.riseName}}</span>
                                                <span>{{invoiceEditInfo.detailName}}</span>
                                            </p>
                                            <div class="change-invoice" @click="flagShowInvoiceDialog=true">修改</div>
                                        </div>
                                        <div class="invoice-info" v-else>
                                            <p class="invoice-text">
                                                <span>未填写商品信息</span>
                                            </p>
                                            <div class="change-invoice" @click="flagShowInvoiceDialog=true">填写</div>
                                        </div>
                                    </div>
                                    <div class="order-remark flex-box">
                                        <p>商家备注</p>
                                        <input v-model="remarks" type="text" placeholder="选填，填写内容已和商家协定确定">
                                    </div>
                                </div>
                                <div class="order-more-right">
                                    <p class="transport-type">运输方式：普通配送 平邮 免费<span style="float: right;">￥0.00</span></p>
                                    <div class="transport-insuance flex-box">
                                        <p>运费险：</p>
                                        <div class="insuance-checkbox">
                                            <el-checkbox>运费险</el-checkbox>
                                        </div>
                                        <p class="insuance-explain">卖家家送，确认收货前退货可赔</p>
                                        <p class="insuance-price">￥0.00</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="commit-order">
                <div class="commit-order-info">
                    <p class="real-payment">实付款<span>￥{{sumPrice}}</span></p>
                    <p class="commit-address">寄送至：{{address.province}}{{address.city}}{{address.area}}{{address.shoppingAddress}}</p>
                    <p class="commit-receiver">收货人：{{address.deliveryName}}</p>
                </div>
                <div class="commit-buttons flex-box">
                    <a href="${ctx}/vij/order/cart.do">
                        <div class="button-cart">返回购物车</div>
                    </a>
                    <div class="button-commit" @click="commitOrder">提交订单</div>
                </div>
            </div>
        </div>
        <el-dialog :title="dialogStatus==1?'新增收货地址':'修改收货地址'" :visible.sync="flagShowAddressDialog" width="35rem" center>
            <div class="right-li-form">
                <el-form label-width="150px" size="medium" :model="addressEditInfo" :rules="addressRule" ref="address">
                    <el-form-item label="省/市/县：" prop="region">
                        <el-cascader style="width: 20rem;" :options="provinceList" v-model="addressEditInfo.region" @change="handleChangeArea" change-on-select filterable :props="props">
                        </el-cascader>
                    </el-form-item>
                    <el-form-item label="详细地址：" prop="shoppingAddress">
                        <el-input v-model="addressEditInfo.shoppingAddress" placeholder="请输入详细地址，如道路、门牌号、小区、楼栋号、单元等信息"></el-input>
                    </el-form-item>
                    <el-form-item label="邮政编码：" prop="postCode">
                        <el-input v-model="addressEditInfo.postCode" placeholder="请填写邮编"></el-input>
                    </el-form-item>
                    <el-form-item label="收货人姓名：" prop="deliveryName">
                        <el-input v-model="addressEditInfo.deliveryName" placeholder="长度不超过25个字符"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码：" prop="deliveryPhone">
                        <el-input v-model="addressEditInfo.deliveryPhone" placeholder="中国大陆+86"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-checkbox v-model="addressEditInfo.isDefault" label="设置为默认收货地址" name="type"></el-checkbox>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer" style="padding: 0">
                <el-button @click="flagShowAddressDialog = false">取 消</el-button>
                <el-button type="primary" @click="handleAddress('address')">{{dialogStatus==1?'新 增':'保 存'}}</el-button>
            </span>
        </el-dialog>
        <el-dialog title="发票信息" :visible.sync="flagShowInvoiceDialog" width="35rem">
            <el-form label-width="150px" size="medium" :rules="invoiceRule" :model="invoiceEditInfo" ref="invoice">
                <el-form-item label="发票类型：" prop="invioceType">
                    <div class="invoice-type-item" :class="invoiceEditInfo.type==1?'high-light':''" @click="changeInvioce(1,1)">
                        <img src="${ctx}/vij/images/high-light-pandant.png">
                        <span>电子发票</span>
                    </div>
                    <div class="invoice-type-item" :class="invoiceEditInfo.type==2?'high-light':''" @click="changeInvioce(1,2)">
                        <img src="${ctx}/vij/images/high-light-pandant.png">
                        <span>普通发票</span>
                    </div>
                </el-form-item>
                <el-form-item label="发票抬头：" prop="invioceRise">
                    <div class="invoice-rise-item" :class="invoiceEditInfo.rise==1?'high-light':''" @click="changeInvioce(2,1)">
                        <img src="${ctx}/vij/images/high-light-pandant.png">
                        <span>个人</span>
                    </div>
                    <div class="invoice-rise-item" :class="invoiceEditInfo.rise==2?'high-light':''" @click="changeInvioce(2,2)">
                        <img src="${ctx}/vij/images/high-light-pandant.png">
                        <span>公司</span>
                    </div>
                    <el-input v-if="invoiceEditInfo.rise==2" v-model="invoiceEditInfo.riseText" class="invoice-risename" placeholder="请输入发票抬头" spellcheck="false"></el-input>
                </el-form-item>
                <el-form-item label="纳税人识别号：" prop="invioceTaxpayerNumber">
                    <el-input v-model="invoiceEditInfo.taxpayerNumber" class="invoice-risename" placeholder="请输入纳税人识别号" spellcheck="false"></el-input>
                    <p class="invoice-tips">纳税人识别号一般为15至20位。部分为英文字用。为确保发票开具的准确性，请与公司财务确认后填写。</p>
                </el-form-item>
                <el-form-item label="商品明细：" prop="invioceDetail">
                    <div class="invoice-type-item" :class="invoiceEditInfo.detail==1?'high-light':''" @click="changeInvioce(3,1)">
                        <img src="${ctx}/vij/images/high-light-pandant.png">
                        <span>商品明细</span>
                    </div>
                    <p class="invoice-tips">提示：电子发票仅支持某某商品开具，上架商品发票以实际收到为准。</p>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleInvoice">保 存</el-button>
                <el-button @click="flagShowInvoiceDialog = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
    <!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
    <script type="text/javascript" src="${ctx}/vij/js/common.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/distpicker/distpicker.data.js"></script>
    <script type="text/javascript">
    var USER_ID = "${vijMallUserSession.userId}";
    var vm = new Vue({
        el: '.page-product-counter',
        data: {
            address: '',
            addressIndex: 0,
            addressList: [],
            addressEditInfo: '', // 编辑地址时储存地址的临时变量
            invoiceEditInfo: {
                // type: 1,
                // rise: 1,
                // detail: 1,
            },

            orderList: [],
            payTypeIndex: 0,
            payTypeList: ["在线付款"],
            remarks: '',
            flagShowAddressDialog: false,
            flagShowInvoiceDialog: false,
            sumPrice: 0,
            dialogStatus: '',
            addressRule: '',
            invoiceRule: '',
            provinceList: '',
            props: {
                value: 'name',
                children: 'children',
                label: 'name'
            },

            flageInvoice: false, // 是否开发票
            flageInvoiceReady: false, // 是否填写发票信息
        },
        created: function() {
            this.getOrderData();
            this.getAddressList();

            this.setAddressRule();
            this.setInvoiceRule();
            this.provinceList = PROVINCE_LIST;

            this.calculataSumPrice();
        },
        mounted: function() {},
        watch: {
            flageInvoice: function(newVal, oldVal) {
                if (newVal && !this.flageInvoiceReady) {
                    this.flagShowInvoiceDialog = true;
                }
            },

            flagShowInvoiceDialog: function(newVal, oldVal) {
                if (!newVal && !this.flageInvoiceReady) {
                    this.flageInvoice = false;
                }
            }
        },
        methods: {
            changeInvioce: function(index, value) {
                console.log(index, value);
                switch (index) {
                    case 1:
                        this.$set(this.invoiceEditInfo, 'type', value);
                        let typeName = this.invoiceEditInfo.type == 1 ? "电子发票" : "普通发票";
                        this.$set(this.invoiceEditInfo, 'typeName', typeName);
                        break;
                    case 2:
                        this.$set(this.invoiceEditInfo, 'rise', value);
                        let riseName = this.invoiceEditInfo.rise == 1 ? "个人" : "公司";
                        this.$set(this.invoiceEditInfo, 'riseName', riseName);
                        break;
                    case 3:
                        this.$set(this.invoiceEditInfo, 'detail', value);
                        let detailName = this.invoiceEditInfo.detail == 1 ? "商品明细" : "商品明细";
                        this.$set(this.invoiceEditInfo, 'detailName', detailName);
                        break;
                    default:
                        break;
                }
            },

            getOrderData: function() {
                var orderListString = localStorage.getItem("TEMP_ORDER_LIST");
                this.orderList = JSON.parse(orderListString);
                console.log(this.orderList);
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
                    console.log("地址---");
                    if (res.code == 0) {
                        console.log(res);
                        _this.addressList = res.objects;
                        _this.clickAddress(0);
                    }
                });
            },

            showDialogBox: function(type, index) {
                if (type === 1) {
                    console.log("新增");
                    if (this.$refs["address"] !== undefined) {
                        this.$refs["address"].resetFields();
                    }
                    this.dialogStatus = 1;
                    this.addressEditInfo = {
                        shoppingAddress: '',
                        deliveryName: '',
                        deliveryPhone: '',
                        postCode: '',
                        province: '',
                        city: '',
                        area: '',
                        region: [],
                        isDefault: false,
                    };
                } else {
                    console.log("编辑");
                    this.dialogStatus = 2;
                    this.addressEditInfo = this.addressList[index];
                    this.addressEditInfo.isDefault = Boolean(this.addressEditInfo.isDefault);
                    var region = [this.addressEditInfo.province, this.addressEditInfo.city, this.addressEditInfo.area];
                    Vue.set(this.addressEditInfo, "region", region)
                    console.log(this.addressEditInfo)
                }
                this.flagShowAddressDialog = true;
                this.dialogStatus = type;
            },

            handleChangeArea: function(val) {
                this.addressEditInfo.province = val[0];
                this.addressEditInfo.city = val[1];
                this.addressEditInfo.area = val[2];
            },

            handleAddress: function() {
                // var _this = this;
                this.$refs["address"].validate((valid) => {
                    if (valid) {
                        if (this.dialogStatus === 1) {
                            this.addAddress();
                        } else {
                            this.editAddress();
                        }
                    }
                });
            },

            handleInvoice: function() {
                this.$refs["invoice"].validate((valid) => {
                    console.log(valid)
                    if (valid) {
                        this.saveInvoice();
                    }
                });
            },

            saveInvoice: function() {
                this.flageInvoice = true;
                this.flageInvoiceReady = true;
                this.flagShowInvoiceDialog = false;
            },

            addAddress: function() {
                var _this = this;
                var param = {
                    area: this.addressEditInfo.area,
                    city: this.addressEditInfo.city,
                    deliveryName: this.addressEditInfo.deliveryName,
                    deliveryPhone: this.addressEditInfo.deliveryPhone,
                    isDefault: Number(this.addressEditInfo.isDefault),
                    postCode: this.addressEditInfo.postCode,
                    province: this.addressEditInfo.province,
                    shoppingAddress: this.addressEditInfo.shoppingAddress,
                    userId: USER_ID,
                }
                $.ajax({
                    url: '${ctx}/wx/shoppingAddress/addAddress.do',
                    type: 'POST',
                    dataType: 'json',
                    data: param,
                }).done(function(res) {
                    console.log("地址---");
                    if (res.code >= 0) {
                        _this.getAddressList();
                    }
                }).always(function(res) {
                    _this.flagShowAddressDialog = false;
                    _this.$message({
                        message: res.msg,
                        type: 'success'
                    });
                });
            },

            editAddress: function() {
                console.log("sendEdit...")
                var _this = this;
                var ad = this.addressEditInfo;
                $.ajax({
                    url: '${ctx}/wx/shoppingAddress/upShoppingAddr.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        addressId: ad.addressId,
                        area: ad.area,
                        city: ad.city,
                        deliveryName: ad.deliveryName,
                        deliveryPhone: ad.deliveryPhone,
                        isDefault: Number(ad.isDefault),
                        postCode: ad.postCode,
                        province: ad.province,
                        shoppingAddress: ad.shoppingAddress,
                        userId: USER_ID,
                    },

                }).done(function(res) {
                    console.log(this.data)
                    if (res.code == 0) {
                        console.log(res)
                        _this.getAddressList();
                        _this.flagShowAddressDialog = false;
                        _this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                    }
                });
            },

            setAddressRule: function() {
                var validateRegion = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请选择所在地区'));
                    }
                    return callback()
                };
                var validatePostCode = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入邮政编码'));
                    }
                    var reg = /^[0-8][0-7]\d{4}$/;
                    var isPostCode = reg.test(value);
                    if (!isPostCode) {
                        return callback(new Error('请输入正确的邮政编码'));
                    }
                    return callback()
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
                    return callback()
                };

                this.addressRule = {
                    region: [{ required: true, validator: validateRegion, trigger: 'change' }],
                    shoppingAddress: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
                    postCode: [{ required: true, validator: validatePostCode, trigger: 'blur' }],
                    deliveryName: [{ required: true, message: '请输入收件人姓名', trigger: 'blur' }],
                    deliveryPhone: [{ required: true, validator: validatePhone, trigger: 'blur' }]
                };
            },

            setInvoiceRule: function() {

                var validaType = (rule, value, callback) => {
                    if (this.invoiceEditInfo.type === undefined) {
                        return callback(new Error('请选择发票类型'));
                    }
                    return callback();
                }

                var validaRise = (rule, value, callback) => {
                    if (this.invoiceEditInfo.rise === undefined) {
                        return callback(new Error('请选择发票抬头'));
                    } else if (this.invoiceEditInfo.rise === 2 && this.invoiceEditInfo.riseText === undefined) {
                        return callback(new Error('请输入发票抬头'));
                    }
                    return callback();
                }

                var validaTaxpayerNumber = (rule, value, callback) => {
                    if (this.invoiceEditInfo.taxpayerNumber === undefined) {
                        return callback(new Error('请选择发票抬头'));
                    }
                    return callback();
                }
                var validaDetail = (rule, value, callback) => {
                    if (this.invoiceEditInfo.detail === undefined) {
                        return callback(new Error('请选择发票明细'));
                    }
                    return callback();
                }
                this.invoiceRule = {
                    invioceType: [{ required: true, validator: validaType, trigger: 'blur' }],
                    invioceRise: [{ required: true, validator: validaRise, trigger: 'change' }],
                    invioceTaxpayerNumber: [{ required: true, validator: validaTaxpayerNumber, trigger: 'blur' }],
                    invioceDetail: [{ required: true, validator: validaDetail, trigger: 'blur' }],
                };
            },

            clickAddress: function(index) {
                this.address = this.addressList[index];
                this.addressIndex = index;
            },

            clickPayType: function(index) {
                this.payTypeIndex = index;
            },

            calculataSumPrice: function() {
                var sumPrice = 0
                for (var i = 0; i < this.orderList.length; i++) {
                    var price = this.orderList[i].goodsPrice;
                    var buyNumber = this.orderList[i].buyNumber;
                    sumPrice = math.eval(sumPrice + "+" + price + "*" + buyNumber);
                }
                this.sumPrice = sumPrice;
            },

            commitOrder: function() {
                var _this = this;
                var goodsList = [];
                for (var i = 0; i < this.orderList.length; i++) {
                    if (this.orderList[i].carId !== undefined) {
                        goodsList.push({
                            "goodsid": this.orderList[i].goodsid,
                            "buyNumber": this.orderList[i].buyNumber,
                            "carId": this.orderList[i].carId,
                            "specId": this.orderList[i].specId,
                            "specTypeString": this.orderList[i].specTypeString,
                        })
                    } else {
                        goodsList.push({
                            "goodsid": this.orderList[i].goodsid,
                            "buyNumber": this.orderList[i].buyNumber,
                            "specId": this.orderList[i].specId,
                            "specTypeString": this.orderList[i].specTypeString,
                        })
                    }
                }
                var goodsString = JSON.stringify(goodsList);
                var param = {
                    addressId: this.address.addressId,
                    consumeType: 1,
                    goods: goodsString,
                    isOnline: 1,
                    loginPlat: LOGIN_PLAT,
                    remarks: this.remarks,
                    userid: USER_ID,
                }
                if (this.flageInvoice) {
                    param.isInvoice = 1;
                    param.invoiceStr = JSON.stringify(this.invoiceEditInfo);
                }
                console.log(param)
                $.ajax({
                    url: '${ctx}/wx/pay/createUserOrder.do',
                    type: 'POST',
                    dataType: 'json',
                    data: param

                }).done((res) => {
                    if (res.code == 0) {
                        console.log(res.code)
                        window.location.href = "${ctx}/vij/order/pay.do?orderId=" + res.orderID;
                    } else {
                        this.$message({
                            message: res.msg,
                            type: 'error',
                            icon: true,
                        });
                    }
                });
            },

        },
    });
    </script>
</body>

</html>