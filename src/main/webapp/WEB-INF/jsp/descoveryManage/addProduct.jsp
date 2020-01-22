<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加发现表单</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
</head>

<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>发布产品到【${common.applicationName}】应用</legend>
		</fieldset>
		<div class="layui-form">
		<input type="hidden" name="loginPlat" value="${id}" >
		<input type="hidden" name="descoveryType" value="${descoveryType}" >
			<div class="layui-form-item">
					<label class="layui-form-label">产品：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="productType" lay-verify="productType" autocomplete="off"
						class="layui-input">
							<option value="">请选择类型</option>
							<c:forEach var="tag" items="${tagList}">
								<option value="${tag.tagId}">${tag.tagName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			<div class="layui-form-item">
				<label class="layui-form-label">产品名称：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="descoveryTitle" lay-verify="descoveryTitle" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">价格：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="productPrice" lay-verify="firstCost" autocomplete="off"
						class="layui-input" value=""><span>元</span>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否包邮：</label>
				<div class="layui-input-block">
				<input type="radio" name="freePost" checked="" value="0" title="否">
				
					<input type="radio" name="freePost"  value="1" title="是">
				</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">竞争方式：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="bidWay" lay-verify="bidWay" autocomplete="off"
						class="layui-input">
							<option value="">请选择类型</option>
							<c:forEach var="tag" items="${productTags}">
								<option value="${tag.childtagId}">${tag.childtagName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			<div class="layui-form-item">
				<label class="layui-form-label">标题图：</label>
				<div class="layui-input-block">
  					<img id="descoveryImage" src="" style="max-width:200px;max-height:150px;" lay-verify="">
    				<input type="file" name="file" class="layui-upload-file" id="test" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">详情图：</label>
				<div class="layui-input-block">
  					<img id="photosBook" src="" style="max-width:200px;max-height:150px;" lay-verify="">
    				<input type="file" name="file" class="layui-upload-file" id="test2" accept=".jpg,.gif,.png,.jpeg">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">产品介绍：</label>
				<div class="layui-input-block">
					<span style="color:red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="content" id="descoveryDetails_edit" ></textarea>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
	layui.use('upload', function() {
		layui.upload({
			url : '${ctx}/layfileUpload/upload.do',
			elem : '#test2' //指定原始元素，默认直接查找class="layui-upload-file"
			,
			method : 'post' //上传接口的http类型
			,
			success : function(res) {
				console.log(res);
				photosBook.src = res.data.src;
			}
		});
	});
	layui.use('upload', function() {
		layui.upload({
			url : '${ctx}/layfileUpload/upload.do',
			elem : '#test' //指定原始元素，默认直接查找class="layui-upload-file"
			,
			method : 'post' //上传接口的http类型
			,
			success : function(res) {
				console.log(res);
				descoveryImage.src = res.data.src;
			}
		});
	});
	layui.use([ 'form', 'layedit', 'laydate' ],
					function() {
						var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
						layedit.set({
							uploadImage : {
								url : '${ctx}/layfileUpload/upload.do', //接口url
								type : 'post' //默认post
							}
						});
						//创建一个编辑器
						var editIndex = layedit.build('descoveryDetails_edit');
						//自定义验证规则
						form.verify({
							descoveryTitle : function(value) {
								if (value.trim() == '') {
									return '项目名称不能为空！';
								}
							},
							projectType : function(value) {
								if (value.trim() == '') {
									return '请选择产品类型！';
								}
							},
							photosBook : function(value) {
								if (value.trim() == '') {
									return '详情图不能为空';
								}
							},
							descoveryImage : function(value) {
								if (value.trim() == '') {
									return '标题图不能为空';
								}
							},
						/* ,content: function(value){
							if(value.trim() == ''){
								return '请填写文章描述！';
							}
						} */
						goodsStock: [/^[0-9]*[1-9][0-9]*$/,'商品库存必须为大于零的正整数' ],
						firstCost : [/^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/,'请输入正确的价格!' ],
						sellPrice : [/^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/,'请输入正确的售价!' ]
						});
						//监听提交
						form.on('submit(demo1)',function(data) {
											$.ajax({
														type : "post",
														url : "${ctx}/descovery/addNewDescovery.do",
														dataType : "json",
														data : {
															"descoveryTitle" : data.field.descoveryTitle,
															"loginPlat" : data.field.loginPlat,
															"descoveryType": data.field.descoveryType,
															"projectType": data.field.projectType,
															"projectStatus":data.field.projectStatus,
															"descoveryImage":descoveryImage.src,
															"photosBook":photosBook.src,
															"bidWay":data.field.bidWay,
															"freePost":data.field.freePost,
															"productPrice":data.field.productPrice,
															"descoveryDetails" : layedit.getContent(editIndex)
														},
														success : function(
																result) {
															//alert("result.code="+result.code);
															if (result.code == 0) {
																layer.msg("添加成功");
																setTimeout(
																		function() {
																			window.location.href = "${ctx}/descovery/goList.do?loginPlat="
																					+ data.field.id;
																		}, 1000);
																// 						setTimeout(location.reload(),1000);
															}
														},
														error : function() {
															layer.msg("添加失败");
														}
													});
											return false;
										});
					});

	function goBack() {
		window.history.go(-1);
	}
</script>
</html>