<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>

<head>
    <meta charset="utf-8" />
    <title>商品列表</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/element-ui-2.4.9.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/common.css" />
    <link type="text/css" rel="stylesheet" href="${ctx}/vij/css/product.css" />
    <link rel="icon" type="image/x-icon" href="${ctx}/vij/images/title_12.png">
</head>

<body>
    <jsp:include page="/vij/publich/header.do" />
    <div class="page-product-list" v-cloak>
        <div>
            <div class="list-title">首页 > 全部结果</div>
            <div class="product-list-content">
                <div v-if="isProductListNull" class="tips" style="text-align:center;padding:100px 0 120px 0">对不起，没有找到对应的商品</div>
                <div v-if="!isProductListNull && !productList" class="tips">努力加载中...</div>
                <div class="list-product-list">
                    <div class="list-product-item" v-for="(product,index) in productList">
                        <a :href="'${ctx}/vij/index/gooddetail.do?goodsId='+product.goodsId">
                            <div class="list-product-item-image">
                                <img :src="product.goodOneImage">
                            </div>
                            <p class="list-product-item-name">{{product.goodsName}}</p>
                            <p class="list-product-item-price">￥{{product.sellPrice}}</p>
                            <div class="list-product-item-image-list">
                                <div class="list-product-item-image-item">
                                    <img :src="product.goodOneImage">
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="pagination" v-if="pageTotal>0">
                    <el-pagination background layout="prev, pager, next" :total="pageTotal" @current-change="changePage" :page-size="limit">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
    <!-- 公共尾部 -->
    <jsp:include page="/vij/publich/footer.do" />
    <script type="text/javascript" src="${ctx}/vij/js/jquery-3.2.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/vue.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/includeHTML.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/element-ui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/vij/js/common.js" charset="utf-8"></script>
    <script type="text/javascript">
    var USER_ID = "${vijMallUserSession.userId}";
    var vm = new Vue({
        el: '.page-product-list',
        data: {
            tab: '',
            enterStatus: 0, // 1大类，2小类， 3搜索
            requestText: '',
            page: 1,
            productList: [],
            isProductListNull: false,
            pageTotal:'', // 产品个数，用于翻页
            limit: 20, // 每页条目数
        },
        created: function() {
            this.addUserAction(); /* 事件上报，页面的其他方法在此方法后面写 */
            this.initEnterStatus();
            this.getProductList();
        },
        mounted: function() {

        },
        methods: {
            addUserAction: function() {
                let objectId = getUrlFooter().goodChildType || 1;
                addUserAction(USER_ID, 0, 7096, objectId);
            },
            initEnterStatus: function() {
                var param = getUrlFooter();
                if (param.goodsType) {
                    this.enterStatus = 1;
                    this.requestText = param.goodsType;
                } else if (param.goodChildType) {
                    this.enterStatus = 2;
                    this.requestText = param.goodChildType;
                } else if (param.goodsName) {
                    this.enterStatus = 0;
                    this.requestText = param.goodsName;
                }
            },
            getProductList: function() {
                var statusKey = ['goodsName', 'goodsType', 'goodChildType'];
                var param = {
                    page: this.page,
                    limit: this.limit
                }
                param[statusKey[this.enterStatus]] = this.requestText;
                $.ajax({
                    url: '${ctx}/vij/index/getGoodList.do ',
                    type: 'POST',
                    dataType: 'json',
                    data: param,
                }).done((res) => {
                    if (res.code === "0") {
                        this.productList = res.data;
                        this.isProductListNull = false;
                    } else {
                        this.isProductListNull = true;
                    }
                    this.pageTotal = res.count; 
                });
            },
            changePage: function(index) {
                this.page = index;
                this.getProductList();
            }
        },
    });
    </script>
</body>

</html>