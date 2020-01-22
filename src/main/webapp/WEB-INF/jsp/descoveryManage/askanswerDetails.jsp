<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>表单</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="${ctx}/text/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<style type="text/css">
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: 5px;
	cursor: pointer;
	color: #35CE8D;
}
#demo2 img{
width:200px !important;
height:150px !important;
margin-right:20px;
}
</style>
</head>

<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>问答详情</legend>
		</fieldset>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">问答编号：</label>
				<div class="layui-input-inline" style="margin-top: 8px;">
					${common.descoveryId}
				</div>
			</div>
			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">阅读数：</label>
					<div class="layui-input-block">
						<label class="detail-label-value"> <input type="text"
							style="display: inline !important; width: 200px;"
							name="readCount" id="readCount" value="${common.readCount}"
							lay-verify="readCount" autocomplete="off" class="layui-input" />次</label>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">点赞数：</label>
					<div class="layui-input-block">
						<label class="detail-label-value"> <input type="text"
							style="display: inline !important; width: 200px;"
							name="pariseCount" id="pariseCount" value="${common.pariseCount}"
							lay-verify="pariseCount" autocomplete="off" class="layui-input" />次</label>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">收藏数：</label>
					<div class="layui-input-block">
						<label class="detail-label-value"> <input type="text"
							style="display: inline !important; width: 200px;"
							name="collectCount" id="collectCount"
							value="${common.collectCount}"
							lay-verify="pariseCount" autocomplete="off" class="layui-input" />次</label>
					</div>
				</div>
			
			 <div class="layui-upload">
                  <label class="layui-form-label" style="width: 100px;">问答图片：</label>
                     <button type="button" class="layui-btn" id="test2">上传图片</button>
                     <span style="color:red">点击图片即可以删除图片</span> 
                  <div class="layui-upload-list" id="demo2">
                   <c:forEach items="${myImage}" var="m" varStatus="status">
                      <c:if test="${m!=null and m!=''}">
                       <img onclick="delthisImg(${status.index+1},${goods.descoveryId})" data-index="${ status.index}" src="<c:out value="${m}" />"> 
                      </c:if>
                     </c:forEach>
                 </div></div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">发布时间：</label>
				<div class="layui-input-inline" style="margin-top: 8px;">
					<fmt:formatDate
							value="${common.publishTime}" type="both" />
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">问答详情：</label>
				<div class="layui-input-block">
					<span style="color: red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="content" id="goodsDetails_edit">${common.descoveryDetails}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
			
	</div>
</body>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript">
var pageno=1,pagesize=10,ulistLoad,type;
var uploadStr = '',did='${common.descoveryId}',length='${length}';
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#test2'
	    ,url: '${ctx}/layfileUpload/upload.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  length ++;
	    	  if(length > 3){
	    		  layer.msg('最多上传3张图片');
	    	  }else{
	    		  $('#demo2').append('<img src="'+ result +'" id="'+index+'" onclick="delImg('+index+')" alt="'+ file.name +'" class="layui-upload-img">')
	    	  }
	      });
	    }
	    ,done: function(res){
	       uploadStr += res.data.src+',';
	    }
	  });
	  
	});

	layui.use(['form','layer','layedit'],function(){
	       form = layui.form,layer=layui.layer, layedit = layui.layedit;
	          layedit.set({
			      uploadImage: {
				        url: '${ctx}/layfileUpload/upload.do', //接口url
				        type: 'post' //默认post
			                 }
	                   	});
						//创建一个编辑器
						var editIndex = layedit.build('goodsDetails_edit');
						//自定义验证规则
						form.verify({
							goodsName: function(value) {
								if(value.trim() == '') {
									return '商品名称不能为空';
								}
							},
							pariseCount: function(value) {
								if(value.trim() == '') {
									return '点赞数不能为空';
								}
							},
							readCount: function(value) {
								if(value.trim() == '') {
									return '阅读数不能为空';
								}
							},
							
						});
					
						
						//监听提交
						form.on('submit(demo1)', function(data) {
							uploadStr = uploadStr.substr(0,uploadStr.length-1);
							$.ajax({
								type : "post",
								url : "${ctx}/descovery/updateDescovery.do",
								dataType : "json",
								data : {
									"descoveryId" : did,
									"readCount" : $('#readCount').val(),
									"pariseCount" : $('#pariseCount').val(),
									"collectCount" : $('#collectCount').val(),
									'descoveryImage':uploadStr,
									"descoveryDetails" : layedit.getContent(editIndex)
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改商品成功");
										setTimeout(function() {
											location.reload();
										}, 1000);
										//setTimeout(location.reload(),1000);
									}else{
										layer.msg("修改商品失败");
									}
								},
								error : function() {
									layer.msg("修改失败");
								}
							});
							return false;
						});
					});

	function delImg(index) {
		$("#" + index + '-0').remove();
	}

	function goBack() {
		window.history.go(-1);
	}
	function delthisImg(index,id){
		length--;
		$.ajax({
			url:'${ctx}/descovery/updateImage.do',
			type:'post',
			dataType:'json',
			data:{
				'descoveryId':id,
				'index':index
			},
			success : function(data) {
				if (data.code == 0) {
					layer.msg('删除图片成功');
					window.location.reload();
				} else {
					layer.msg(res.msg);
				}
			}
		});
	}
</script>
</html>