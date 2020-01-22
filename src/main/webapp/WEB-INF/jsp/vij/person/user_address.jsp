<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>收货地址</title>
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js"></script> <!-- 饿了么UI -->
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css"><!-- 饿了么UI -->
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/user_address.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vij/css/common.css">
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
    <style type="text/css">
    .info select {
        border: 0.05rem solid #bfbfbf;
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
                    <div class="right-li li-1">
                        <div class="right-li-title">
                            <p class="title-name" @click="showAddressBox">地址列表<span class="title-num">共${fn:length(address)}条</span></p>
                            <el-button size="medium" type="primary" @click="showAddressBox" plain>
                                <span>新增</span>
                                <i class="el-icon-plus el-icon--right"></i>
                            </el-button>
                        </div>
                        <el-table :data="addressList" stripe style="width: 53rem;" border>
                            <el-table-column align="center" header-align="center" prop="deliveryName" label="收货人" min-width="1">
                            </el-table-column>
                            <el-table-column align="center" header-align="center" label="所在地区" min-width="1">
                                <template slot-scope="scope">
                                    <span>{{ getRegion(scope.row.province, scope.row.city, scope.row.area)}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column align="center" header-align="center" prop="shoppingAddress" label="详细地址" min-width="1">
                            </el-table-column>
                            <el-table-column align="center" header-align="center" prop="postCode" label="邮编" min-width="1">
                            </el-table-column>
                            <el-table-column align="center" header-align="center" prop="deliveryPhone" label="电话/手机" min-width="1">
                            </el-table-column>
                            <el-table-column align="center" header-align="center" label="操作" min-width="3">
                                <template slot-scope="scope">
                                    <el-button size="mini" @click="showAddressBox(scope.$index)">编辑</el-button>
                                    <el-button size="mini" type="danger" @click="deleteAddress(scope.$index)">删除</el-button>
                                    <el-button v-if="scope.row.isDefault" size="mini" type="primary" @click="setAddressToDefault(scope.$index)" plain>取消默认</el-button>
                                    <el-button v-else size="mini" type="primary" @click="setAddressToDefault(scope.$index)">设为默认</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    
                </div>
            </div>
        </div>
        <el-dialog :title="dialogStatus==1?'新增收货地址':'修改收货地址'" :visible.sync="showDialog" width="55rem" center>
            <div class="right-li-form">
                <el-form label-width="150px" item-with="420px" size="medium" :model="addressEditInfo" :rules="addressRule" ref="address">
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
                <el-button @click="showDialog = false">取 消</el-button>
                <el-button type="primary" @click="handleAddress('address')">{{dialogStatus==1?'新 增':'保 存'}}</el-button>
            </span>
        </el-dialog>
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
            addressEditInfo: '',
            addressList: [],
            showDialog: false,
            provinceList: [],
            area: '',
            dialogStatus: '',
            addressRule: '',
            props: {
                value: 'name',
                children: 'children',
                label: 'name'
            }
        },
        created: function() {
            this.provinceList = PROVINCE_LIST;
            this.setAddressRule();

            this.getAddressList();
        },
        filiters: {},
        methods: {
            getRegion: function(province, city, area) {
                if (city === "县" || city === "市辖区") {
                    city = '';
                }
                return province + city + area;
            },
            getAddressList: function() {
                var _this = this;
                console.log("${ctx}/wx/shoppingAddress/findShoppingAddr.do")
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
                    _this.addressList = res.objects;
                });
            },
            showAddressBox: function(index) {
                if (typeof index === "object") {
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
                this.showDialog = true;
            },
            handleAddress: function(name) {
                var _this = this;
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        if (_this.dialogStatus === 1) {
                            _this.addAddress();
                        } else {
                            _this.editAddress();
                        }
                    }
                });
            },
            deleteAddress: function(index) {
                var _this = this;
                var addressId = this.addressList[index].addressId;
                $.ajax({
                    url: '${ctx}/wx/shoppingAddress/deleteAddress.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        addressId: addressId
                    },
                }).done(function(res) {
                    if (res.code == 0) {
                        _this.addressList = res.objects;
                        _this.getAddressList();
                        _this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                    }
                });
            },
            addAddress: function() {
                var _this = this;
                var ad = this.addressEditInfo;
                $.ajax({
                    url: '${ctx}/wx/shoppingAddress/addAddress.do',
                    type: 'POST',
                    dataType: 'json',
                    data: {
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
                    if (res.code == 0) {
                        console.log(res)
                        _this.getAddressList();
                        _this.showDialog = false;
                        _this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                    }
                });
            },
            setAddressToDefault: function(index) {
                this.addressEditInfo = this.addressList[index];
                this.addressEditInfo.isDefault = !this.addressEditInfo.isDefault;
                this.editAddress();
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
                        _this.showDialog = false;
                        _this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                    }
                });
            },
            handleChangeArea: function(val) {
                this.addressEditInfo.province = val[0];
                this.addressEditInfo.city = val[1];
                this.addressEditInfo.area = val[2];
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
                    region: [
                        { required: true, validator: validateRegion, trigger: 'change' }
                    ],
                    shoppingAddress: [
                        { required: true, message: '请输入详细地址', trigger: 'blur' }
                    ],
                    postCode: [
                        { required: true, validator: validatePostCode, trigger: 'blur' }
                    ],
                    deliveryName: [
                        { required: true, message: '请输入收件人姓名', trigger: 'blur' }
                    ],
                    deliveryPhone: [
                        { required: true, validator: validatePhone, trigger: 'blur' }
                    ]
                }
            },
        }
    });
    </script>
</body>

</html>