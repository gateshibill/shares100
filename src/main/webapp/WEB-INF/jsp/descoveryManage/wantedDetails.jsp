<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="utf-8">
<title>招聘详情表单</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
</head>

<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>招聘【${common.descoveryTitle}】的详情</legend>
		</fieldset>
		<div class="layui-form">
		
			<div class="layui-form-item">
				<label class="layui-form-label">招聘编号：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${common.descoveryId}</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">招聘名称：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<input type="text" style="display: inline !important; width: 200px;"
						name="descoveryTitle" value="${common.descoveryTitle}"
						 lay-verify="descoveryTitle" autocomplete="off"
						class="layui-input" />
				</label>
				</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label">阅读数：</label>
					<div class="layui-input-block">
						<label class="detail-label-value"> <input type="text"
							style="display: inline !important; width: 200px;"
							name="readCount" value="${common.readCount}"
							lay-verify="descoveryTitle" autocomplete="off" class="layui-input" />次</label>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label">点赞数：</label>
					<div class="layui-input-block">
						<label class="detail-label-value"> <input type="text"
							style="display: inline !important; width: 200px;"
							name="pariseCount" value="${common.pariseCount}"
							lay-verify="descoveryTitle" autocomplete="off" class="layui-input" />次</label>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label">收藏数：</label>
					<div class="layui-input-block">
						<label class="detail-label-value"> <input type="text"
							style="display: inline !important; width: 200px;"
							name="collectCount" value="${common.collectCount}"
							lay-verify="descoveryTitle" autocomplete="off" class="layui-input" />次</label>
					</div>
				</div>
				
			<div class="layui-form-item">
				<label class="layui-form-label">招聘图片：</label>
				<div class="layui-input-block">
					<label class="detail-label-value"><img src="${common.descoveryImage}"/></label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">招聘相册：</label>
				<div class="layui-input-block">
					<label class="detail-label-value"><img src="${common.photosBook}"/></label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">发布者编号：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${common.dscvPublisher.userId}</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">发布者昵称：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${common.dscvPublisher.nickName}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">加入总数：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${common.joinedCount}&nbsp;次</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">发布时间：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">
					<fmt:formatDate
							value="${common.publishTime}" type="both" /></label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">薪资：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${common.minSalary}-${common.maxSalary}元</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">工作城市：</label>
				<div class="layui-input-block">
					<label class="detail-label-value">${common.workCity}</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">通过审核：</label>
				<div class="layui-input-inline">
					<c:choose>
						<c:when test="${common.isPass==1}">
							<input type="radio" name="isHot" checked value="1" title="是">
							<input type="radio" name="isHot" value="0" title="否">
						</c:when>
						<c:otherwise>
							<input type="radio" name="isHot" value="1" title="是">
							<input type="radio" name="isHot" checked value="0" title="否">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否上架：</label>
				<div class="layui-input-block">
					<c:choose>
						<c:when test="${common.isShangJia==1}">
							<input type="radio" name="isSelled" checked="" value="1"
								title="是">
							<input type="radio" name="isSelled" value="0" title="否">
						</c:when>
						<c:otherwise>
							<input type="radio" name="isSelled" value="1" title="是">
							<input type="radio" name="isSelled" checked="" value="0"
								title="否">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">福利标签：</label>
				<div class="layui-input-block">
					<label class="detail-label-value"> 
						<c:forEach items="${wantedTags}" var="tags">
								<c:choose>
									<c:when
										test="${fn:contains(common.getWelfare(),tags.getChildtagId())}">
										<input type="checkbox" name="welfare"
											id="welfare" value="${tags.getChildtagId()}"
											lay-verify="welfare" title="${tags.getChildtagName()}"
											checked="checked"> 
								</c:when>
									<c:otherwise>
										<input type="checkbox" name="welfare"
											id="welfare" value="${tags.getChildtagId()}"
											lay-verify="welfare" title="${tags.getChildtagName()}">
									</c:otherwise>
								</c:choose>
						</c:forEach>
					</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">岗位描述：</label>
				<div class="layui-input-block">
					<span style="color: red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="content" id="workDesc_edit">${common.wantedDesc}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">技能要求：</label>
				<div class="layui-input-block">
					<span style="color: red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="content" id="descoveryDetails_edit">${common.wantedSkill}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script>
	layui
			.use(
					[ 'form', 'layedit', 'laydate' ],
					function() {
						var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
						layedit.set({
							uploadImage : {
								url : '${ctx}/layfileUpload/upload.do', //接口url
								type : 'post' //默认post
							}
						});
						//创建一个编辑器
						var editWorkDesc = layedit.build('workDesc_edit');
						var editdesciDetails = layedit.build('descoveryDetails_edit');
						//自定义验证规则
						form.verify({
							descoveryTitle: function(value) {
								if(value.trim() == '') {
									return '名称不能为空!';
								}
							},
							firstCost : [ /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/,
									'请输入正确的成本价!' ],
							sellPrice : [ /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/,
									'请输入正确的售价!' ]
						});
						//监听提交
						form.on('submit(demo1)', function(data) {
							$.ajax({
								type : "post",
								url : '${ctx}/descovery/updateDescovery.do',
// 								url : "${ctx}/goods/updateGoods.do",
								dataType : "json",
								data : {
                                    "descoveryId":'${common.descoveryId}',
									"firstCost" : data.field.firstCost,
									"sellPrice" : data.field.sellPrice,
									"isSelled" : data.field.isSelled,
									"isHot" : data.field.isHot,
									"collectCount" : data.field.collectCount,
									"readCount" : data.field.readCount,
									"pariseCount" : data.field.pariseCount,
									"descoveryTitle" : data.field.descoveryTitle,
									"wantedDesc" : layedit.getContent(editWorkDesc),
									"wantedSkill" : layedit.getContent(editdesciDetails)
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改成功");
										setTimeout(function() {
											location.reload();
										}, 1000);
										//setTimeout(location.reload(),1000);
									}
								},
								error : function() {
									layer.msg("修改失败");
								}
							});
							return false;
						});
					});
	function goBack() {
		// 	window.location.href = '${ctx}/goods/goGoodsList.do';
		window.history.go(-1);
	}
</script>
</html>