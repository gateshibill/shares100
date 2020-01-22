<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>添加企业应用</title>
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
		    <%-- <div class="layui-form-item">
				<label class="layui-form-label">所属应用：</label>
				<div class="layui-input-inline" style="width: 420px;">
				
						<select name="appId" id="appId" lay-verify="appId" autocomplete="off"
						class="layui-input" lay-search="" style="width: 420px;">
							<c:forEach var="type" items="${appList}">
								<option value="${type.applicationId}">${type.applicationName}</option>
							</c:forEach>
						</select>
				</div>
			</div>
		     --%>
		      <div class="layui-form-item">
				    <label class="layui-form-label">所属应用</label>
				    <div class="layui-input-block" style="width:420px;">
				    <select name="appId" id="appId" lay-verify="appId" autocomplete="off"
						class="layui-input" lay-search="" style="width:420px;">
							<c:forEach var="c" items="${appList}">
								<option value="${c.applicationId}">${c.applicationName}</option>
							</c:forEach>
						</select>
				    </div>
				  </div>
			<div class="layui-form-item">
				<label class="layui-form-label">企业名称：</label>
				<div class="layui-input-inline">
					<input type="text" style="width: 400px;"
						name="cname"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">联系方式：</label>
				<div class="layui-input-inline" >
					<input type="text" style="width: 400px;"
						name="cphone"  autocomplete="off"
						class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">企业地址：</label>
				<div class="layui-input-inline">
					<input type="text" style=" width: 400px;"
						name="caddress"  autocomplete="off"
						class="layui-input">
				</div>
			</div>
			
			
		 <div class="layui-form-item">        
	           <div class="layui-upload">
	                  <label class="layui-form-label" style="width: 100px;">公司logo：</label>
	                     <button type="button" class="layui-btn" id="logoupload">上传图片</button> 
	                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
	                  <div class="layui-upload-list" id="logolist" style="cursor: pointer;"></div>
	          </div>
          </div>
			<!-- <div class="layui-form-item">
			   <div class="layui-upload">
	                  <label class="layui-form-label" style="width: 100px;">官网轮播图：</label>
	                     <button type="button" class="layui-btn" id="bannerupload" >上传图片</button> 
	                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
	                  <div class="layui-upload-list" id="bannerlist" style="cursor: pointer;"></div>
	          </div>
           </div> -->
         <!--  <div class="layui-form-item">        
	           <div class="layui-upload">
	                  <label class="layui-form-label" style="width: 100px;">公司介绍：</label>
	                     <button type="button" class="layui-btn" id="cintroupload">上传图片</button> 
	                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
	                  <div class="layui-upload-list" id="cintrolist" style="cursor: pointer;"></div>
	          </div>
          </div>
          <div class="layui-form-item">
           <div class="layui-upload">
                  <label class="layui-form-label" style="width: 100px;">产品介绍：</label>
                     <button type="button" class="layui-btn" id="pintroupload">上传图片</button> 
                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
                  <div class="layui-upload-list" id="pintrolist" style="cursor: pointer;"></div>
          </div>
          </div>
          <div class="layui-form-item">        
           <div class="layui-upload">
                  <label class="layui-form-label" style="width: 100px;">团队成员：</label>
                     <button type="button" class="layui-btn" id="teamupload">上传图片</button> 
                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
                  <div class="layui-upload-list" id="teamlist" style="cursor: pointer;"></div>
          </div>
          </div>
          <div class="layui-form-item">    
           <div class="layui-upload">
                  <label class="layui-form-label" style="width: 100px;">人才需求：</label>
                     <button type="button" class="layui-btn" id="cfindupload">上传图片</button> 
                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
                  <div class="layui-upload-list" id="cfindlist" style="cursor: pointer;"></div>
          </div>
          </div>
          <div class="layui-form-item">     
           <div class="layui-upload">
                  <label class="layui-form-label" style="width: 100px;">客户案例：</label>
                     <button type="button" class="layui-btn" id="kehuupload">上传图片</button> 
                     <span style="color:red">上传图片之后点击图片即可删除图片</span>
                  <div class="layui-upload-list" id="kehulist" style="cursor: pointer;"></div>
          </div>
          </div> -->
          <div class="layui-form-item">
				<div class="layui-inline" style="width:100%">
					<label class="layui-form-label">公司详情</label>				
					<div class="layui-input-block">
						<script id="companyDetail" type="text/plain" style="width:92%;height:500px;"></script>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
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
var ue = UE.getEditor("companyDetail");
var logolist = '',bannerlist = '',cintrolist = '',pintrolist = '',cfindlist='',kehulist='',teamlist='',num=0;
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
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#bannerupload'
	    ,url: '${ctx}/aida/upload/uploadWebsite.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  if(num > 5){
	    		  layer.msg('最多上传5张图片!',{time:1500});
	    	  } else{
	    		  $('#bannerlist').append('<img src="'+ result +'" id="banner-'+index+'" onclick="delImg('+index+','+num+')"  class="layui-upload-img">')
	    	  }
	    	  num ++;
	      });
	    }
	    ,done: function(res){
	       bannerlist += res.msg+',';
	    }
	  });
	  
});
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#cintroupload'
	    ,url: '${ctx}/aida/upload/uploadWebsite.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	 
	    		  $('#cintrolist').html('<img src="'+ result +'"   class="layui-upload-img">')
	    	 
	      });
	    }
	    ,done: function(res){
	       cintrolist = res.msg;
	    }
	  });
	  
});

layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#pintroupload'
	    ,url: '${ctx}/aida/upload/uploadWebsite.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	
	    	 $('#pintrolist').html('<img src="'+ result +'"   class="layui-upload-img">')
	    	  
	      });
	    }
	    ,done: function(res){
	       pintrolist = res.msg;
	    }
	  });
	  
});

layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#cfindupload'
	    ,url: '${ctx}/aida/upload/uploadWebsite.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	   
	    	$('#cfindlist').html('<img src="'+ result +'"  class="layui-upload-img">')

	      });
	    }
	    ,done: function(res){
	    	cfindlist = res.msg;
	    }
	  });
	  
});

layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#teamupload'
	    ,url: '${ctx}/aida/upload/uploadWebsite.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  
	    		  $('#teamlist').html('<img src="'+ result +'"  class="layui-upload-img">')
	    	 
	      });
	    }
	    ,done: function(res){
	       teamlist = res.msg;
	    }
	  });
	  
});

layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#kehuupload'
	    ,url: '${ctx}/aida/upload/uploadWebsite.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	 
	    		  $('#kehulist').html('<img src="'+ result +'"  class="layui-upload-img">')
	    	 
	      });
	    }
	    ,done: function(res){
	      kehulist = res.msg;
	    }
	  });
	  
});
layui.use([ 'form', 'layer'],
		function() {
	 form = layui.form, layer = layui.layer;
		//监听提交
		form.on('submit(demo1)', function(data) {
			
			$.ajax({
				type : "post",
				url : "${ctx}/website/doadd.do",
				dataType:"json",
				data : {
					"cname":data.field.cname,
					"appId":data.field.appId,
					"cphone":data.field.cphone,
					"caddress":data.field.caddress,
					//"banner":bannerlist,
					"cintro":cintrolist,
					"pintro":pintrolist,
					"cfind":cfindlist,
					"kehu":kehulist,
					"team":teamlist,
					'logo':logolist,
					'companyDetail':ue.getContent(),
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
	


function delImg(index,num){
	console.log(index);
	var bannerArr = bannerlist.split(",");
	var str = bannerArr[num];
	bannerlist=bannerlist.replace(str+",","");
	$("#banner-"+index+'-0').remove();
	
}
/* function delImg1(index){
	$("#cintro-"+index+'-0').remove();
}
function delImg2(index){
	$("#pintro"+index+'-0').remove();
}
function delImg3(index){
	$("#cfind-"+index+'-0').remove();
}
function delImg4(index){
	$("#team-"+index+'-0').remove();
}
function delImg5(index){
	$("#kehu-"+index+'-0').remove();
} */
function goBack(){
	window.history.go(-1);
}
 
</script>
</html>