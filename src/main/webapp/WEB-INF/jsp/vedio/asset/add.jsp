<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加视频</title>
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
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/ueditor/themes/default/css/ueditor.css">
<style type="text/css">
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: -3px;
	cursor: pointer;
	color: #35CE8D;
}
img{
	width:200px;
	height:200px;
	margin-right:20px
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">	  
		      <div class="layui-form-item">
				    <label class="layui-form-label">所属应用</label>
				    <div class="layui-input-block" style="width:420px;">
				    <select name="appId" id="appId" lay-verify="appId" autocomplete="off"
						class="layui-input" lay-search="" style="width:420px;">
								<option value="0">默认应用</option>
						</select>
				    </div>
				  </div>
			<div class="layui-form-item">
				<label class="layui-form-label">电影名称：</label>
				<div class="layui-input-inline">
					<input type="text" style="width: 400px;"
						name="name"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">导演：</label>
				<div class="layui-input-inline" >
					<input type="text" style="width: 400px;"
						name="director"  autocomplete="off"
						class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">演员：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="actor"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				    <label class="layui-form-label">剧集类型：</label>
				    <div class="layui-input-block" style="width:420px;">
				    <select name="sitcom" id="sitcom" lay-verify="sitcom" autocomplete="off"
						class="layui-input" lay-search="" style="width:420px;">
								<option value="0">电影</option>
								<option value="1">连续剧</option>
						</select>
				    </div>
			</div>
			<div class="layui-form-item">
				    <label class="layui-form-label">视频类型：</label>
				    <div class="layui-input-block" style="width:420px;">
				    <select name="type" id="type" lay-verify="type" autocomplete="off"
						class="layui-input" lay-search="" style="width:420px;">
								<option value="1">儿童</option>
								<option value="2">体育</option>
								<option value="3">纪录片</option>
						</select>
				    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">时长：</label>
				<div class="layui-input-inline">
					<input type="number" style=" width: 400px;"
						name="duration"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">标签集：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="lableIds"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">地区：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="area"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">语言：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="language"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">发布时间：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="publishTime"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">来源：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="source"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">格式：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="format"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">评分：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="score"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
		 <div class="layui-form-item">        
	           <div class="layui-upload">
	                  <label class="layui-form-label" style="width: 100px;">视频海报：</label>
	                     <button type="button" class="layui-btn" id="logoupload">上传图片</button> 
	                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
	                  <div class="layui-upload-list" id="logolist" style="cursor: pointer;"></div>
	          </div>
          </div>
		<div class="layui-form-item">
				<label class="layui-form-label">介绍：</label>
				<div class="layui-input-inline"  style="width:420px">
					<textarea class="layui-textarea" name="desc"  autocomplete="off"
						class="layui-input"></textarea>
				</div>
			</div>	
          <div class="layui-form-item">
				<div class="layui-inline" style="width:100%">
					<label class="layui-form-label">视频</label>				
					<div class="layui-input-block">
						<script id="playUrl" type="text/plain" style="width:92%;height:500px;"></script>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>	
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var ue = UE.getEditor("playUrl",{
	toolbars: [[
	            'source','insertvideo'
	        ]]
});
var logolist = '';
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#logoupload'
	    ,url: '${ctx}/aida/upload/uploadWebsite.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	 
	    		  $('#logolist').html('<img src="'+ result +'"   class="layui-upload-img">')
	    	 
	      });
	    }
	    ,done: function(res){
	       logolist = res.msg;
	    }
	  });
	  
});


layui.use([ 'form', 'layer'],
		function() {
	 form = layui.form, layer = layui.layer;
		//监听提交
		form.on('submit(demo1)', function(data) {
			var content = ue.getContent();
			//匹配视频播放地址
			var playUrl = matchVedio(content);
			if(!playUrl){
				layer.msg("请上传视频");
				return;
			}
			$.ajax({
				type : "post",
				url : "${ctx}/asset/doAddAsset.do",
				dataType:"json",
				data : {
					"name":data.field.name,
					/* "appId":data.field.appId,
					"director":data.field.director,
					"actor":data.field.actor,
					"sitcom":data.field.sitcom,
					"type":data.field.type,
					"duration":data.field.duration,
					"lableIds":data.field.lableIds,
					"area":data.field.area,
					"language":data.field.language,
					"source":data.field.source,
					"format":data.field.format,
					"score":data.field.score,
					"desc" :data.field.desc,
					'logo':logolist,
					'playUrl':playUrl,
					'publishTime':data.field.publishTime , */
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							 layer.closeAll("iframe");
		      				//刷新父页面
		      				 parent.location.reload();
						  }, 1000);
					} else{
						layer.msg(result.msg,{time:2000});
					}
				},error:function(){
					layer.msg("添加失败");
				}
			});
			return false;
		});
	});
	
function matchVedio(content){
	//匹配图片（g表示匹配所有结果i表示区分大小写）
	var vedioReg = /<video.*?(?:>|\/>)/gi;
	//匹配src属性 
	var srcReg = /src=[\'\"]?([^\'\"]*)[\'\"]?/i;
	var arr = content.match(vedioReg);
	var vedioUrl = "";
	for (var i = 0; i < arr.length; i++) {
	  var src = arr[i].match(srcReg);
	  //获取图片地址
	  if(src[1]){
	     vedioUrl = src[1];
	  }
	}
	return vedioUrl;
}
</script>
</html>