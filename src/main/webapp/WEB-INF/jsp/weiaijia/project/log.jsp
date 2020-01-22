<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>施工日志</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${ctx}/layui/css/layui.css?a=<%=new java.util.Random().nextInt()%>"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/global.css" media="all">
<link rel="stylesheet"
	href="${ctx}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${ctx}/css/table.css?a=<%=new java.util.Random().nextInt()%>" />
<link rel="stylesheet"
	href="${ctx}/css/layer-search.css?v=<%=new java.util.Random().nextInt()%>" />

<style type="text/css">
.content{
	width:80%;
	margin:0 auto;
}
.top{
	text-align:center
}
.layui-table-cell{
   height:auto;
   overflow:visible;
  text-overflow:inherit;
 white-space:normal;
}
.layui-table td, .layui-table th{
	padding:15px;
}
.name{
	padding:5px 10px 0 10px;
	color:#22A95D;
}
.btnbutton{
	float:right;
}
.btnname {
	float: right;
}
</style>
</head>
<body>	
	<div style="margin: 15px;">
		<div class="layui-form">
		<blockquote>
				<a class="layui-btn layui-btn-normal  newsAdd_btn">添加</a>
		</blockquote>	
		</div>
			<div class="layui-form-item">
		<table class="layui-table">
		
			<thead>
				<tr>
					<th style="text-align: center; font-weight: bold;" colspan="6">
						<h2>施工日志</h2>
					</th>
				</tr>
				<tr>
					<th width="5%" style="text-align: center;">序号</th>
					<th width="30%" style="text-align: center;">标题</th>
					<th width="30%" style="text-align: center;">备注</th>
					<th width="15%" style="text-align: center;">状态</th>
					<th width="20%" style="text-align: center;">时间</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${tlist}" var="pc" varStatus="status">
			<tr style="text-align: center;">
				<td>${status.index+1}</td>
				<td>${pc.title}</td>
				<td>${pc.remark}</td>
			
			<%-- <td>
				<div class="layui-form-item">
					<label class="layui-form-label"></label>
						<div class="layui-input-block">
		    				<input type="file" name="file" class="layui-upload-file" id="detail" accept=".jpg,.gif,.png,.jpeg">
						</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"></label>
						<ul class="layer-photos-demo" id="Images">
							<li><img layer-src="${pc.detail}" src="${pc.detail}" alt="${pc.detail}" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del"></i></div></li>
						</ul>
				</div>
			</td> --%>
			<td>
			<div class="layui-form-item" align="center">
			    <label class="layui-form-label"></label>
			    <div class="layui-input-block">
			      	<c:if test="${pc.status==1}">
						选择顾问
					</c:if>
					<c:if test="${pc.status==2}">
						预约量房
					</c:if>
					<c:if test="${pc.status==3}">
						选择风格
					</c:if>
					<c:if test="${pc.status==4}">
						预交订金
					</c:if>
					<c:if test="${pc.status==5}">
						确认设计
					</c:if>
					<c:if test="${pc.status==6}">
						确定报价
					</c:if>
					<c:if test="${pc.status==7}">
						装修施工
					</c:if>
					<c:if test="${pc.status==8}">
						竣工验收
					</c:if>
					<c:if test="${pc.status==9}">
						客户评论
					</c:if>
					<c:if test="${pc.status==10}">
						项目结束
					</c:if>
			    </div>
			</div>
		</td>
			<td><fmt:formatDate value="${pc.createTime}" pattern="yyyy-MM-dd"/></td>
		</tr>
				</c:forEach>
			</tbody>
		</table>
		</div> 
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<input type="hidden" name="plogId" value="${log.plogId}"/>
			<button class="layui-btn layui-btn-radius" lay-submit="" lay-filter="demo1">确认日志</button>
		</div>
	</div>
</body>
	
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>	
	<script type="text/javascript">
	layui.use('upload', function(){
		var upload = layui.upload;
		upload.render({
			    url: '${ctx}/vij/upload/upload.do'
			    ,elem: '#detail' //指定原始元素，默认直接查找class="layui-upload-file"
			    ,method: 'post', //上传接口的http类型
			    dataType:"json"
			    ,success: function(res){
			    	$('#Images').html('<li><img layer-src="'+ res.msg +'" src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"></li>');
					delImages();
			    }
			  });
	}); 
	 var projectId ="${projectId}"; 
	layui.use([ 'form', 'layer'],
			function() {
		 	var form = layui.form, layer = layui.layer;
			//监听提交
			form.on('submit(demo1)', function(data) {
				 var uploadStr = "";
					$("#Images li").find("img").each(function(i){
						 var imgSrc = $(this).attr("src");
						 console.log("imgSrc-"+i+":"+imgSrc);
						 uploadStr += ","+imgSrc;
					})
					var images = uploadStr.substr(1,uploadStr.length-1);
				$.ajax({
					type : "post",
					url : "${ctx}/project/doLogByid.do",
					dataType:"json",
					data : {
						isDeal:1,
						"projectId":projectId,
						"plogId":data.field.plogId,
						"detail":images
					},
					success : function(result) {
						if(result.code==0){
							layer.msg("确认成功");
							setTimeout(function () {
								 layer.closeAll("iframe");
			      				//刷新父页面
			      				 parent.location.reload();
							  }, 1000);
						} else{
							layer.msg(result.msg,{time:2000});
						}
					},error:function(){
						layer.msg("确认失败");
					}
				});
				return false;
			});
		});
	
	 $(window).one(
				"resize",
				function() {
					$(".newsAdd_btn").click(
									function() {
								var index = layui.layer.open({
										title : "添加施工日志",
										type : 2,
									content : "${ctx}/project/addProjectLog.do?projectId="+projectId,
								success : function(
									layero,
									index) {
								setTimeout(
									function() {
										layui.layer.tips(
									'点击此处返回',
									'.layui-layer-setwin .layui-layer-close',
								{
									tips : 3
								});
							}, 500)
						}
					})
				layui.layer.full(index);
			})
		}).resize();
	
	function goBack(){
		window.history.go(-1);
	}
</script>
</html>