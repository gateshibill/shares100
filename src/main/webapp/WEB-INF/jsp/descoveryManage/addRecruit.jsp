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
			<legend>发布招聘到【${common.applicationName}】应用</legend>
		</fieldset>
		<div class="layui-form">
		
		<input type="hidden" name="loginPlat" value="${id}" >
		<input type="hidden" name="descoveryType" value="${descoveryType}" >
			<div class="layui-form-item">
					<label class="layui-form-label">招聘：</label>
					<div class="layui-input-inline" style="width: 210px;">
						<select name="wantedType" lay-verify="wantedType" autocomplete="off"
						class="layui-input">
							<option value="">请选择类型</option>
							<c:forEach var="tag" items="${tagList}">
								<option value="${tag.tagId}">${tag.tagName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			<div class="layui-form-item">
				<label class="layui-form-label">职位名称：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="descoveryTitle" lay-verify="descoveryTitle" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">发布城市：</label>
				<div class="layui-input-inline" style="width: 210px;">
					<div id="distpicker1" style="width:200px;">
						<select class="layui-input" name="startProvince" id="province"></select>
						<select class="layui-input" name="startCity" id="city"></select> 
						<select class="layui-input" name="district" id="district"></select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">薪资范围：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 150px;" name="minSalary"
						lay-verify="firstCost" autocomplete="off" class="layui-input"
						value="">&nbsp;&nbsp;-
				</div>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 150px; margin-left: -135px;"
						name="maxSalary" lay-verify="firstCost" autocomplete="off"
						class="layui-input" value=""> <span>元</span>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">项目资源：</label>
				<div class="layui-input-block">
				<c:forEach items="${wantedTags}" var="tags">
						<input type="checkbox" id="hasResourceTag" name="hasResourceTag" lay-skin="primary"
							title="${tags.childtagName}" value="${tags.childtagId}"
							class="layui-input" lay-verify="" placeholder="请选择项目资源">
						
					</c:forEach>
				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">岗位描述：</label>
				<div class="layui-input-block">
					<span style="color:red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="" id="descoveryDetails_edit" ></textarea>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">技能要求：</label>
				<div class="layui-input-block">
					<span style="color:red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content1"
						lay-verify="" id="descoveryDetails_edit1" ></textarea>
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
<script type="text/javascript" src="${ctx}/js/distpicker.data.js"></script>
<script type="text/javascript" src="${ctx}/js/distpicker.js"></script>
<script>
layui.use('upload', function(){
	  layui.upload({
		//url: 'http://www.ailefeigou.com/cofC3/layfileUpload/upload.do'
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#test' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	console.log(res);
	      	$("#descoveryImage").attr("src",res.data.src);
	    }
	  });
	});
layui.use(
	[ 'form', 'layedit', 'laydate' ],
		function() {
			var form = layui.form(), layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
			layedit.set({
			uploadImage: {
				url: '${ctx}/layfileUpload/upload.do', //接口url
				type: 'post' //默认post
			 }
		});
		//创建一个编辑器
		var editIndex = layedit.build('descoveryDetails_edit');
		//创建一个编辑器
		var editIndex1 = layedit.build('descoveryDetails_edit1');
		//自定义验证规则
		form.verify({
			descoveryTitle: function(value) {
				if(value.trim() == '') {
					return '文章标题不能为空！';
				}
			}
			 ,content: function(value){
				if(value.trim() == ''){
					return '请填写岗位描述！';
				}
			} ,
			 content1: function(value){
					if(value.trim() == ''){
						return '请填写技能要求！';
					}
				} ,
				wantedType: function(value){
					if(value.trim() == ''){
						return '请选择招聘类型！';
					}
				} ,
				firstCost : [/^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/,'请输入正确的价格!' ],
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			var province = $('#province').val();
			var city = $('#city').val();
			var district = $('#district').val();
			if(!district){
				var work_city = province+","+city+","+district;
			}else{
				var work_city = province+","+city;
			}
			alert(work_city);
			$.ajax({
				type : "post",
				url : "${ctx}/descovery/addNewDescovery.do",
				dataType:"json",
				 data : {
					"descoveryTitle":data.field.descoveryTitle,
					"loginPlat":data.field.loginPlat,
					"wantedType":data.field.wantedType,
					"descoveryType":data.field.descoveryType,
					"minSalary":data.field.minSalary,
					"maxSalary":data.field.maxSalary,
					"workCity":work_city,
					"wantedSkill":layedit.getContent(editIndex1),
					"workDesc":layedit.getContent(editIndex)
				}, 
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							window.location.href="${ctx}/descovery/goList.do?loginPlat="+data.field.id;
						  }, 1000);
// 						setTimeout(location.reload(),1000);
					}
				},error:function(){
					layer.msg("添加失败");
				}
			});
			return false;
		});
	});
$('#distpicker1').distpicker({
    province: '广东省',
    city: '广州市',
    district: '天河区'
  });
function goBack(){
	window.history.go(-1);
}
</script>
</html>