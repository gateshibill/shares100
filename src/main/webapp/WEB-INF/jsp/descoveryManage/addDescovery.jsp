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
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/ueditor/themes/default/css/ueditor.css">
<style type="text/css">
   #deletemoren .layui-form-checkbox{display:none !important}
   #deletemoren input{display:inline-block !important}
</style>
</head>
<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>发布资讯到【${common.applicationName}】应用</legend>
		</fieldset>
		<div class="layui-form">
		
		<input type="hidden" name="loginPlat" value="${id}" >
		<input type="hidden" name="groupType" id="groupType" value="${common.groupType}" >
	    <input type="hidden" name="descoveryType" value="${descoveryType}" >
			<div class="layui-form-item">
				<label class="layui-form-label">资讯标题：</label>
				<div class="layui-input-inline" style="width: 300px;">
					<input type="text"
						style="display: inline !important; width: 200px;"
						name="descoveryTitle" lay-verify="descoveryTitle" autocomplete="off"
						class="layui-input">
				</div>
				
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">是否推荐：</label>
				<div class="layui-input-block">
				<input type="radio" name="isRecommend" checked="" value="0" title="否">
				<input type="radio" name="isRecommend" lay-verify="radio" value="1" title="是">
				<!-- lay-filter="exam_radio" -->
                    <input hidden="" type="text" name="recommendWay" id="recommendWay" >
				</div>
			</div>		
			
			<!-- 添加封面图片，只能上传一张 -->
				<!-- <div class="layui-form-item">
					<label class="layui-form-label">商品外观</label>
					<div class="layui-input-block">
						<input type="file" name="file" class="layui-upload-file" id="uploadNewImg-collCover" accept=".jpg,.gif,.png,.jpeg">
						<p style="color:red">唯爱生活最佳长宽比:1.9:1(比例小于1.9:1可能有样式问题<br>
										唯爱装修最佳长宽比:1:1(比例小于1:1可能有样式问题</p>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"></label>
					<ul class="layue-photos-demo" id="Images-collCover" lay-verify="uploadNewImg_collCover"></ul>
				</div> -->
			<!-- 多规格 结束 -->
			<!-- <div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">商品外观</label>
					<div class="layui-input-inline">
						<button type="button" class="layui-btn" id="test2">上传图片</button> 
                     	<p style="color:red">上传图片之后点击图片即可删除图片</p>
                     	<input type="file" name="file" class="layui-upload-file" id="uploadNewImg"  accept=".jpg,.gif,.png,.jpeg">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-input-label"></label>
				 <div class="layui-upload-list" id="demo2">
				      	<ul class="layer-photos-demo" id="Images" lay-verify="uploadNewImg"></ul>			      
				 </div>
			</div> -->
									
			<!-- <div class="layui-form-item">
            	<div class="layui-upload-list" id="demo2"></div>
            </div> -->
			
			<div class="layui-form-item">
				<label class="layui-form-label">资讯描述：</label>
				<!-- <div class="layui-input-block">
					<span style="color:red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="content" id="descoveryDetails_edit" ></textarea>
				</div> -->
				<div class="layui-input-block">
						<script id="content" type="text/plain" style="width:92%;height:500px;"></script>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
					<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
				</div>
			</div>
					<!-- 模态框开始  -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				      aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width:40%;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">应用推荐方式</h4>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">推荐方式：</label>
							<div class="layui-input-block">
								<input type="radio" name="recommendWay" id="recommendWay" checked="" value="1"
									title="所有"> <input type="radio" name="recommendWay"
									id="recommendWay" value="2" title="同类型">
							</div>
						</div>
						<div class="modal-footer">
						<a class="btn btn-default" onclick="confirm_update()" 
						style="height: 30px; line-height: 30px; padding: 0 18px;">确定</a>
						</div>
					</div>
				</div>
			</div>
			<!--模态框结束-->
		</div>
	</div>
<script type="text/html" id="show_loginPlat">
		{{each objects as value i}}
		<tr>
           <td><input type="checkbox" name="checkbox" lay-skin="primary" value="{{value.applicationId}}"></td>
            <td>{{value.applicationId}}</td>
            <td>{{value.applicationName}}</td>
            <td>
               {{if value.applicationType==1}} 
                                                小程序 
                {{/if}}
                {{if value.applicationType==2}} 
                                               社区
               {{/if}}
                {{if value.applicationType==3}} 
                                              应用
               {{/if}}
              {{if value.applicationType==4}} 
                                             社区应用
               {{/if}}
                    </td>
            <td>{{value.types.typeName}}</td>
            <td>{{value.appCreateTime}}</td>
		</tr>
		{{/each}}
</script>
</body>
<script type="text/javascript" src="${ctx}/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>	
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.min.js"></script>
<script>
var ue = UE.getEditor("content");
//封面删除
function delImages_collCover(){
	var ic_s=$('#Images-collCover .img_del');
	for(var i=0;i<ic_s.length;i++){
		$("#Images-collCover .img_del").get(i).index=i;
		$("#Images-collCover .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#Images-collCover li:eq("+i+")").remove();
		})
	}
}
/* //图片集合删除
function delImages(){
	var i_s=$("#Images .img_del");
	for(var i=0;i<i_s.length;i++){
		$("#Images .img_del").get(i).index=i;
		$("#Images .img_del:eq("+i+")").click(function(){
			var i=this.index;
			$("#Images li:eq("+i+")").remove();
		})
	}
}
 */
var pageno=1,pagesize=10,ulistLoad;;
layui.use('upload', function(){
	 layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#uploadNewImg-collCover' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#Images-collCover').html('<li><img src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
		    	delImages_collCover();
		    }
		 });
	
	  /* layui.upload({
		//url: 'http://www.ailefeigou.com/cofC3/layfileUpload/upload.do'
	    url: '${ctx}/layfileUpload/upload.do'
	    ,elem: '#test2' //指定原始元素，默认直接查找class="layui-upload-file"
	    ,method: 'post', //上传接口的http类型
	    dataType:"json"
	    ,success: function(res){
	    	console.log(res);
	      	$("#descoveryImage").attr("src",res.data.src);
	    }
	  }); */
	 layui.upload({
		    url: '${ctx}/vij/upload/upload.do'
		    ,elem: '#uploadNewImg' //指定原始元素，默认直接查找class="layui-upload-file"
		    ,method: 'post', //上传接口的http类型
		    dataType:"json"
		    ,success: function(res){
		    	$('#Images').append('<li><img src="'+ res.msg +'" alt="'+ res.msg +'" class="layui-upload-img"><div class="operate"><i class="layui-icon img_del">&#xe640;</i></div></li>');
				delImages();
		    }
		  });
	});
layui.use([ 'form','layer' ],
		function() {
	      form = layui.form(),layer = layui.layer;
			
		//自定义验证规则
		form.verify({
			descoveryTitle: function(value) {
				if(value.trim() == '') {
					return '文章标题不能为空！';
				}
			},
			/* uploadNewImg : function(item) {
				if ($("#Images li").length == 0) {
					return "请上传图片";
				}
			}, */
			/* uploadNewImg_collCover : function(item) {
				if ($("#Images-collCover li").length == 0) {
					return "请上传封面";
				}
			}, */
// 			 ,content: function(value){
// 				if(value.trim() == ''){
// 					return '请填写质询描述！';
// 				}
// 			} 
		});
		
		form.on('radio(exam_radio)', function(data) {
			$('#myModal').modal('show');
// 			getLoginPlat(pageno);
	    });
		
		function getLoginPlat(pageno) {
			$.getJSON("${ctx}/goods/loginPlatList.do", {
				pageNo : pageno,
				pageSize: pagesize
			}, function(res) {
				if (res.code == 0) {
					$('#myModal').modal('show');
					var html = template('show_loginPlat', res);
					$('#tbody11').html(html);
					if (res.count > 1) {
						laypage({
							cont : 'pageId',
							pages : res.count,
							skin : 'molv',
							curr : pageno || 1,//当前页
							groups : 5,
							jump : function(obj, first) {
								//触发分页后的回调
								if (!first) {
									//点击跳页触发函数自身，并传递当前页：obj.curr
									getLoginPlat(obj.curr);
								}
							}
						});
					}
				} else {
// 						layer.msg(res.msg);
				}
			})
		}
		
		//监听提交
		form.on('submit(demo1)', function(data) {
			var tags = new Array();
			var uploadStr = "",upload_collCover="";
			$("#Images li").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 uploadStr += ","+imgSrc;
			})
			$("#Images-collCover li").find("img").each(function(i){
				 var imgSrc_c = $(this).attr("src");
				 upload_collCover = imgSrc_c;
			})
			var images = uploadStr.substr(1,uploadStr.length-1);
			var images_collCover = upload_collCover.substr(1,upload_collCover.length-1);
			
			$.ajax({
				type : "post",
				url : "${ctx}/descovery/addNewDescovery.do",
				dataType:"json",
				 data : {
					"descoveryTitle":data.field.descoveryTitle,
					"loginPlat":data.field.loginPlat,
// 					"manyloginPlat":data.field.manyloginPlat,
					"recommendWay":data.field.recommendWay,
					"isRecommend":data.field.isRecommend,
					"descoveryType":data.field.descoveryType,
					//"coverImage":images_collCover,
					/* "descoveryImage":images, */
					"descoveryDetails":ue.getContent(),
				}, 
				success : function(result) {
					if(result.code==0){
						layer.msg("添加成功");
						setTimeout(function () {
							window.location.href="${ctx}/descovery/goList.do?loginPlat="+data.field.loginPlat;
						  }, 1000);
					}
				},error:function(){
					layer.msg("添加失败");
				}
			});
			return false;
		});
		
		//全选
		/* form.on('checkbox(allChoose)', function(data) {
			var child = $(data.elem).parents('table').find(
					'tbody input[name="checkbox"]');
			child.each(function(index, item) {
				item.checked = data.elem.checked;
			});
			form.render('checkbox');
		}); */
		
	});
	


function confirm_update(){
	var way =$('#recommendWay').val();
	$('#recommendWay').val(way);
	$('#myModal').modal('hide');
}

	//所有应用
	function AllLoginPalt(type) {
		$('#platType').val(type);
		$('#myModal').modal('hide');
	}
	//指定应用
	function AppointLoginPalt(type) {
		var Checkbox = false;
		$("input[name='checkbox']").each(function() {
			if (this.checked == true) {
				Checkbox = true;
			}
		});
		if (Checkbox) {
			layer.confirm('您确认只推选中的应用?', {
				btn : [ '确认', '取消' ]
			}, function() {
				var checkedList = new Array();
				$("input[name='checkbox']:checked").each(function() {
					checkedList.push($(this).val());
				});
				var id =checkedList.toString();
				$('#manyloginPlat').val(id);
				$('#platType').val(type);
				$('#myModal').modal('hide');
				layer.closeAll();
			}, function() {
				layer.closeAll();
				$('#checkall').attr("checked", false);
				$("[name ='checkbox']:checkbox").attr("checked", false);
			});
		} else {
			layer.msg('请选择要指定的应用!', {
				time : 3000,
				icon : 0
			});
			return false;
		}
	}
	//相同应用
	function identicalLoginPalt(type) {
		var groupType = $('#groupType').val();
		if(groupType == 1){
			layer.msg("您当前选中的应用为小程序，不能推同类型应用!");
			return false;
		}else{
			$('#platType').val(type);
			$('#myModal').modal('hide');
		}
	}
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>