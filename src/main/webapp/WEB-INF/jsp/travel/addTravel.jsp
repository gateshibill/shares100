<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加行程</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet"
	href="${ctx}/text/layui/css/layui.css?v=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<style type="text/css">
.icon-plus {
	font-size: 23px;
	position: absolute;
	left: 0;
	top: 5px;
	cursor: pointer;
	color: #35CE8D;
	cursor: pointer;
}

#demo2 img {
	width: 200px !important;
	height: 150px !important;
	margin-right: 20px;
}

#demo3 img {
	width: 200px !important;
	height: 150px !important;
	margin-right: 20px;
}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<div class="layui-form">
           	<div class="layui-form-item">
<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>选择应用：</label>					<div class="layui-input-inline" style="width: 210px;">
						<select name="loginPlat" id="loginPlat" lay-verify="loginPlat" autocomplete="off"
						class="layui-input">
							<option value="">请选择应用</option>
							<c:forEach var="app" items="${appList}">
								<option value="${app.applicationId}">${app.applicationName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>行程标题：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="travelTitle" lay-verify="travelTitle" id="travelTitle" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<!-- <div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>行程景点：</label>
				<div class="layui-input-inline" style="width: 210px;">
					<span class="icon-plus" style="color: #009688;"></span> <span
						id="nameType" style="margin-left: 35px; margin-top: 20px;"></span>
				</div>
				<input hidden="" id="travelViewList" name="travelViewList"
					lay-verify="travelViewList" autocomplete="off" class="layui-input">
			</div> -->
         <!--  <div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>景点红包数：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="redBagCount" lay-verify="redBagCount" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>红包金额：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="moneyCount" lay-verify="moneyCount" autocomplete="off"
						class="layui-input">
				</div>
			</div> -->
			<div class="layui-upload">
				<label class="layui-form-label" style="width: 100px;">行程图片：</label>
				<button type="button" class="layui-btn" id="test2">上传图片</button>
				<span style="color: red">上传图片之后点击图片即可删除图片</span>
				<div class="layui-upload-list" id="demo2" style="cursor: pointer;"></div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>行程价格：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="number"
						style="display: inline !important; width: 200px;"
						name="travelPrice" lay-verify="travelPrice" autocomplete="off"
						class="layui-input" value="1680" id="travelPrice"><span>元</span>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>出发地：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;" name="startcity"
						lay-verify="startcity" autocomplete="off" id="startcity" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>出发时间：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" id="starttime"
						style="display: inline !important; width: 200px;" name="starttime"
						lay-verify="starttime"  autocomplete="off" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;"><span
					style="color: red; margin-left: -10px;">*&nbsp;</span>结束时间：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text" id="endtime"
						style="display: inline !important; width: 200px;" name="endtime"
						lay-verify="endtime" autocomplete="off" class="layui-input"
						>
				</div>
			</div>
			<!-- <div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">是否推荐：</label>
				<div class="layui-input-block">
					<input type="radio" name="isRecommend" checked="" value="0"
						title="否"> <input type="radio" name="isRecommend"
						value="1" title="是" lay-filter="" lay-verify="radio"> <input
						hidden="" type="text" id="recommendWay" name="recommendWay" />
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">是否热门：</label>
				<div class="layui-input-block">
					<input type="radio" name="ishot" value="0" title="否"> <input
						type="radio" name="ishot" checked="" value="1" title="是">

				</div>
			</div> -->



			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">行程详情：</label>
				<div class="layui-input-block">
					<span style="color: red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="content" id="goodsDetails_edit"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" onclick="addTravel()">确认</button>
				</div>
			</div>



		</div>
	</div>

</body>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>
<script type="text/javascript">
var pageno=1,pagesize=20,ulistLoad,type,num=0,sum=0;
var uploadStr = '',upeatils='';
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
	    	  num ++;
	    	  if(num > 1){
	    		  layer.msg('最多上传1张图片!',{time:1500});
	    	  } else{
	    		  $('#demo2').append('<img src="'+ result +'" id="'+index+'" onclick="delImg('+index+')" alt="'+ file.name +'" class="layui-upload-img">')
	    	  }
	      });
	    }
	    ,done: function(res){
	       uploadStr += res.data.src+',';
	    }
	  });
	  
	});


layui.use(['form','layer','layedit','laypage','laydate'],function(){
	form = layui.form,layer=layui.layer, layedit = layui.layedit,laypage = layui.laypage,laydate = layui.laydate;
	 layedit.set({
			uploadImage: {
				url: '${ctx}/layfileUpload/upload.do', //接口url
				type: 'post' //默认post
			 }
		});
	 laydate.render({
		    elem: '#starttime' //指定元素
		  });	
	 laydate.render({
		    elem: '#endtime' //指定元素
		  });
		//创建一个编辑器
		 editIndex = layedit.build('goodsDetails_edit');
	
		
		});
		

function delImg(index){
	$("#"+index+'-0').remove();
}

//提交
function addTravel(){
	uploadStr = uploadStr.substr(0,uploadStr.length-1);
	var loginPlat = $("#loginPlat").val();
	alert(loginPlat);
	var travelTitle = $("#travelTitle").val();
	var travelPrice = $("#travelPrice").val();
	var startPlace = $("#startcity").val();
	var startTime = $("#starttime").val();
	var endTime = $("#endtime").val();
	var travelImageList = uploadStr;
	var travelContent = layedit.getContent(editIndex);
	if(!loginPlat){
		layer.msg("请选择应用");
		return;
	}
	if(!travelTitle){
		layer.msg("请输入标题");
		return;
	}
	if(!travelPrice){
		layer.msg("请输入价格");
		return;	
	}
	
	if(!travelContent){
		layer.msg("请输入内容");
		return;
	}
	$.ajax({
		url:"${ctx}/travel/doaddtravel.do",
		type:'post',
		dataType:'json',
		data : {
			'loginPlat':loginPlat,
			'travelTitle':travelTitle,
			'travelPrice':travelPrice,
			'startPlace':startPlace,
			//'startTime':startTime,
			//'endTime':endTime,
			'travelImageList':uploadStr,
			'travelContent':layedit.getContent(editIndex)
		},
		success:function(data){
			alert(JSON.stringify(data));
		},
		error:function(data){
			alert(JSON.stringify(data));
		}
	})
	
}
</script>
</html>