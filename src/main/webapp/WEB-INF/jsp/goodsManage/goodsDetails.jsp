<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>编辑商品表单</title>
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
<link rel="stylesheet" href="${ctx}/ueditor/themes/default/css/ueditor.css">
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
#demo2,#activitydemo2{
	width:100%;
	cursor: pointer;
	padding-left:20px;
}
#demo2 img,#activitydemo2 img {
	width: 200px !important;
	height: 150px !important;
	margin-right: 20px;
	float:left;
}

#demo3 img {
	width: 200px !important;
	height: 150px !important;
	margin-right: 20px;
}

.layui-form-label {
	width: 100px;
}

.red {
	color: red;
}
#moregg{
	width:100%;
	display:none;
}

.xh-list li,.gg-list li{
	float:left;
	background: #f4f4f4;
	padding:8px 30px;
	margin-right:20px;
	cursor: pointer;
}
.xinghao,.guige{
	padding:20px 20px;
}
.one,.two{
	display:none;
}

.ggcontent{
	background-color:#fff;
	border:1px solid #ddd;
	margin:20px;
	padding:10px
}
#type1send,#type2send,#piliang{
	display:none;
}
/*清除浮动*/
.box::before, .box::after {
    display: block;
    clear: both;
    height: 0;
    content: "";
}
.twoChildType{
	/*display:none*/
	display:inline-block;
}

</style>
</head>
<body>
	<div style="margin: 15px;">
		<form class="layui-form" action="#">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label"><span class="red">*</span>商品名称</label>
					<div class="layui-input-inline">
						<input type="text" name="goodsName" lay-verify="goodsName"
							autocomplete="off" class="layui-input" value="${goods.goodsName}">
					</div>
				</div>
		        
		        <div class="layui-inline">
					<label class="layui-form-label">商品品牌</label>
					<div class="layui-input-inline">
						<select name="brandId" id="brandId" 
							autocomplete="off" class="layui-input" lay-search="">
							<c:forEach var="c" items="${brands}">
								<option value="${c.brandId}" <c:if test="${c.brandId == goods.brandId}">selected="selected"</c:if>>${c.cnBrandName}</option>
							</c:forEach>
						</select>
					</div>	
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"><span class="red">*</span>一级类型</label>
					<div class="layui-input-inline">
						<select name="goodsType" id="goodsType" lay-verify="goodsType"
							autocomplete="off" class="layui-input" lay-search="" lay-filter="goodsType">
							<option value="">请选择类型</option>
							<c:forEach var="c" items="${plists}">
								<option value="${c.typeId}" <c:if test="${goods.goodsType == c.typeId}">selected="selected"</c:if>>${c.typeName}</option>							
							</c:forEach>
						</select>
					</div>	
				</div>
				
				<div class="layui-inline twoChildType box" hidden="true">
					<label class="layui-form-label">二级类型</label>
					<div class="layui-input-inline">
						<select name="goodChildType" id="goodChildType"
							autocomplete="off" class="layui-input" lay-search="">
							
							
						</select>
					</div>	
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"><span class="red">*</span>商品售价</label>
					<div class="layui-input-inline">
						<input type="number" name="sellPrice" lay-verify="sellPrice"
							autocomplete="off" class="layui-input" value="${goods.sellPrice}">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">商品原价</label>
					<div class="layui-input-inline">
						<input type="number" name="firstCost" lay-verify="firstCost"
							autocomplete="off" class="layui-input" value="${goods.firstCost}">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label">会员售价</label>
					<div class="layui-input-inline">
						<input type="number" name="vipPrice" lay-verify="vipPrice"
							autocomplete="off" class="layui-input" value="${goods.vipPrice}">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">一级返点</label>
					<div class="layui-input-inline">
						<input type="text" name="goodsRebate" lay-verify="goodsRebate"
							autocomplete="off" class="layui-input" value="${goods.goodsRebate}">
					</div>
				</div>
				<!-- <div class="layui-inline">
					<label class="layui-form-label">二级返点</label>
					<div class="layui-input-inline">
						<input type="text" name="twogoodsRebate" lay-verify="twogoodsRebate"
							autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">三级返点</label>
					<div class="layui-input-inline">
						<input type="text" name="threegoodsRebate" lay-verify="threegoodsRebate"
							autocomplete="off" class="layui-input">
					</div>
				</div> -->

				<div class="layui-inline">
					<label class="layui-form-label"><span class="red">*</span>商品库存</label>
					<div class="layui-input-inline">
						<input type="text" name="goodsStock" lay-verify="goodsStock"
							autocomplete="off" class="layui-input"  value="${goods.goodsStock}">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">商品排序</label>
					<div class="layui-input-inline">
						<input type="number" name="goodSort" lay-verify="goodSort"
							autocomplete="off" class="layui-input" value="${goods.goodSort}">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">积分兑换</label>
					<div class="layui-input-inline">
						<select name="isScore">
							<option <c:if test="${goods.isScore == 0}">selected="selected"</c:if> value="0">否</option>
							<option value="1" <c:if test="${goods.isScore == 1}">selected="selected"</c:if>>是</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">所需积分</label>
					<div class="layui-input-inline">
						<input type="text" name="scoreCount" lay-verify="scoreCount"
							autocomplete="off" class="layui-input" value="${goods.scoreCount}">
					</div>
				</div>

				<div class="layui-inline">
					<label class="layui-form-label">是否推荐</label>
					<div class="layui-input-inline">
						<select name="isRecommend">
							<option <c:if test="${goods.isRecommend == 0}">selected="selected"</c:if> value="0">否</option>
							<option value="1" <c:if test="${goods.isRecommend == 1}">selected="selected"</c:if>>是</option>
						</select>
					</div>
				</div>

				<div class="layui-inline">
					<label class="layui-form-label">是否审核</label>
					<div class="layui-input-inline">
						<select name="isPassSell">
							<option value="0" <c:if test="${goods.isPassSell == 0}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${goods.isPassSell == 1}">selected="selected"</c:if>>是</option>
						</select>
					</div>
				</div>



				<div class="layui-inline">
					<label class="layui-form-label">是否上架</label>
					<div class="layui-input-inline">
						<select name="isSelled">
							<option value="0" <c:if test="${goods.isSelled == 0}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${goods.isSelled == 1}">selected="selected"</c:if>>是</option>
						</select>
					</div>
				</div>
				
				
				<div class="layui-inline">
					<label class="layui-form-label">是否新品</label>
					<div class="layui-input-inline">
						<select name="isNew">
							<option value="0" <c:if test="${goods.isNew == 0}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${goods.isNew == 1}">selected="selected"</c:if>>是</option>
						</select>
					</div>
				</div>		


				<div class="layui-inline">
					<label class="layui-form-label">出租出售</label>
					<div class="layui-input-inline">
						<select name="sellOrRent">
							<option  value="0" <c:if test="${goods.sellOrRent == 0}">selected="selected"</c:if>>出租</option>
							<option <c:if test="${goods.sellOrRent == 1}">selected="selected"</c:if> value="1">出售</option>
						</select>
					</div>
				</div>
			</div>
			
			<%-- <div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">是否秒杀</label>
					<div class="layui-input-inline">
						<select name="isDtbt" lay-filter="isDtbt">
							<option <c:if test="${goods.isDtbt == 0}">selected="selected"</c:if>  value="0">否</option>
							<option <c:if test="${goods.isDtbt == 1}">selected="selected"</c:if>  value="1">是</option>
						</select>
					</div>
				</div>	
				<c:choose>
					<c:when test="${goods.isDtbt == 1}">
						<div class="layui-inline msTime">
							<label class="layui-form-label">有效时间</label>
							<div class="layui-input-inline">
								<input type="number" name="msTime"
									autocomplete="off" class="layui-input" value="${goods.msTime}" placeholder="请输入秒杀商品的有效时间，单位为小时"> 
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="layui-inline msTime" hidden="false">
							<label class="layui-form-label">有效时间</label>
							<div class="layui-input-inline">
								<input type="number" name="msTime"
									autocomplete="off" class="layui-input" value="${goods.msTime}" placeholder="请输入秒杀商品的有效时间，单位为小时"> 
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				
			</div> --%>
			
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">多规格</label>
					<div class="layui-input-inline">
						<select name="isMoreSpec" lay-filter="isMoreSpec">
							<option <c:if test="${goods.isMoreSpec == 0}">selected="selected"</c:if> value="0">否</option>
							<option <c:if test="${goods.isMoreSpec == 1}">selected="selected"</c:if> value="1">是</option>
						</select>
					</div>
				</div>
			</div>
            
            <!-- 多规格编辑开始 -->
              
         <%--      <div class="layui-form-item moregg" id="moregg">
				<div class="ggcontent">
					<div class="xinghao">
						<p>商品规格最多添加两个商品规格</p>
						<div class="layui-inline">	
							<div class="layui-input-inline">
						<select name="type1" lay-filter="type1" id="type1" lay-search="">
							<option value="0">请选择规格</option>
							<c:forEach var="c" items="${lists}">
								<option value="${c.id}" title="${c.specName}">${c.specName}</option>
							</c:forEach> 
						</select>
						</div>
						</div>
						<div class="box" style="padding-top:20px">						
							<ul class="xh-list box" style="float:left">
	
							</ul>
							<div class="layui-inline" id="type1send" style="float:left">				
								<div class="layui-inline">	
									<div class="layui-input-inline">								
										<input type="text" autocomplete="off" class="layui-input" id="xh" />
									</div>
								</div>
								<div class="layui-inline">	
									<div class="layui-input-inline">	
										<button type="button" class="addxh layui-btn layui-btn-primary" onclick="addxh()">添加</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="guige">
					<div class="layui-inline">	
							<div class="layui-input-inline">
							<select name="type2" lay-filter="type2" id="type2" lay-search="">
								<option value="0">请选择规格</option>
									<c:forEach var="c" items="${lists}">
										<option value="${c.id}" title="${c.specName}">${c.specName}</option>
									</c:forEach> 
									
									</select>
					</div>
					</div>	
						<div class="box" style="padding-top:20px">
							<ul class="gg-list box" style="float:left">

							</ul>
							<div class="layui-inline" id="type2send" style="float:left">										
								<div class="layui-inline">	
									<div class="layui-input-inline">
								<input type="text" id="gg" autocomplete="off" class="layui-input"/>
								</div>
								</div>
								<div class="layui-inline">	
									<div class="layui-input-inline">
								<button type="button" class="addgg layui-btn layui-btn-primary" onclick="addgg()">添加</button>
								</div>
								</div>
							</div>	
						
						</div>
					</div>
					<div class="table" style="padding:0 20px">
						<p>价格以及库存</p>
						<div class="piliang" id="piliang">
						  <div class="layui-inline">
								<label class="layui-form-label">批量设置</label>
						  </div>		
							<div class="layui-inline">
								<label class="layui-form-label">价格</label>
								<div class="layui-input-inline">
									<input type="number" name="ppprice" id="ppprice"
										autocomplete="off" class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">库存</label>
								<div class="layui-input-inline">
									<input type="number" name="ppstock" id="ppstock" 
										autocomplete="off" class="layui-input">
								</div>
							</div>
							<div class="layui-inline">	
								<div class="layui-input-inline">
							<button type="button" class="addpp layui-btn layui-btn-primary" onclick="addPP()">确定</button>
							</div>
							</div>
						</div>
						<table class="layui-table">
							<thead>
								<tr class="topth">
									<th class="one">型号</th>
									<th class="two">规格</th>
									<th>价格</th>
									<th>库存</th>
									
								</tr>
							</thead>
							<tbody class="tbodydata">

							</tbody>
						</table>
					</div>
				</div>	   	
			</div>
               --%>
            <!-- 多规格编辑结束 -->
             
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">商品外观</label>
					<div class="layui-input-inline">
						<button type="button" class="layui-btn" id="test2">上传图片</button> 
                     	<p style="color:red">上传图片之后点击图片即可删除图片</p>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
            	<div class="layui-upload-list" id="demo2">
            	   <c:if test="${fn:length(myImage)>0}">
                      <c:forEach items="${myImage}" var="m" varStatus="status">
            			<img src="${m}" id="pic_${status.index}" onclick="delImg(${status.index})"  class="layui-upload-img">
            		  </c:forEach>
            		</c:if>
            	</div>
            </div>
            
            <div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">商品活动图片</label>
					<div class="layui-input-inline">
						<button type="button" class="layui-btn" id="activityUpload">上传图片</button> 
                     	<p style="color:red">上传图片之后点击图片即可删除图片</p>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
            	<div class="layui-upload-list" id="activitydemo2">
            	   <c:if test="${not empty goods.goodRecommendPic}">
            			<img src="${goods.goodRecommendPic}" id="pic_100" onclick="delImg(100)"  class="layui-upload-img">
            		</c:if>
            	</div>
            </div>
            
			<div class="layui-form-item">
				<div class="layui-inline" style="width:100%">
					<label class="layui-form-label">商品详情</label>
					<%-- <div class="layui-input-block">
						<span style="color:red;">上传图片后,如果要追加图片,请再次点击文本框,避免覆盖上一张图片,如果需要替换之前的图片,请点击图片再点上传</span>
					<textarea class="layui-textarea layui-hide" name="content"
						lay-verify="content" id="goodsDetails_edit" >${goods.goodsDetails}</textarea>
					</div> --%>
					<div class="layui-input-block">
						<script id="content" type="text/plain" style="width:92%;height:500px;"></script>
					</div>
				</div>
			</div>
		<div class="layui-form-item">
				<div class="layui-input-block">
					<button  class="layui-btn" lay-submit="" lay-filter="demo1">确认</button>
<!-- 					<button type="button" onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
 -->				</div>
		</div>
			
</form>
</div>
</body>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/text/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>	
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var goodsId='${goods.goodsId}';
var ue = UE.getEditor("content");
var content = '${goods.goodsDetails}';
ue.addListener("ready", function () {
    ue.setContent(content); //赋值 
 });
var type1name = '',type2name = '',num=0;
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
	    	  if(num > 4){
	    		  layer.msg('最多上传5张图片!',{time:1500});
	    	  } else{
	    		 // $('#demo2').append('<img src="'+ result +'" id="pic_'+index+'" onclick="delImg('+index+')" alt="'+ file.name +'" class="layui-upload-img">')
	    	  }
	    	  num++;
	      });
	    }
	    ,done: function(res,index, upload){
	    	console.log("index--"+index);
	    	if(res.code == 0){
  		  		$('#demo2').append('<img src="'+ res.data.src +'" id="pic_'+index+'" onclick="delImg('+index+')"  class="layui-upload-img">')
	    	}else{
	    		alert(res.msg);
	    	}	
	    }
	  });
	  
	});
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#activityUpload'
	    ,url: '${ctx}/layfileUpload/upload.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){	    	 
	    	 
	      });
	    }
	    ,done: function(res,index, upload){
	    	console.log("index--"+index);
	    	if(res.code == 0){
	  		  $('#activitydemo2').html('<img src="'+ res.data.src +'" id="pic_'+index+'" onclick="delImg('+index+')"  class="layui-upload-img">')
	    	}else{
	    		alert(res.msg);
	    	}
	    }
	  });
	  
	});	


layui.use(['form','layer'],function(){
	form = layui.form,layer=layui.layer;	
		//自定义验证规则
		form.verify({
			goodsName: function(value) {
				if(value.trim() == '') {
					return '商品名称不能为空';
				}
			},
			loginPlat: function(value) {
				if(value.trim() == '') {
					return '请选择所属应用';
				}
			},
			goodsType: function(value) {
				if(value.trim() == '') {
					return '请选择商品类型';
				}
			},
			goodsStock: [/^[0-9]*[1-9][0-9]*$/,'商品库存必须为大于零的正整数' ],
			/*firstCost : [/^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/,'请输入正确的成本价!' ],*/
			sellPrice : [/^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/,'请输入正确的售价!' ]
		});
		
		

		
		var parentId = "${goods.goodsType}"; //一级分类
		var childType = "${goods.goodChildType}";//二级分类
		getChildType(parentId);
		
	
		//分类二级联动
		form.on('select(goodsType)',function(data){
			 var parentId = data.value;
			 getChildType(parentId);	
		})
		
		
		function getChildType(parentId){
			$(".twoChildType").hide();
			 if(!parentId){
				  layer.msg("请选择一级分类");
				  return false;
			 }
			 var typehtml = "";
			 typehtml += '<option value="">请选择</option>';
			 $.ajax({
				 url:'${ctx}/goods/getChildType.do',
				 type:'post',
				 dataType:'json',
				 data:{parentId:parentId},
				 success:function(res){
					 if(res.code == 0){ //获取数据
						 $(".twoChildType").show();
						 var object = res.objects;
						 typehtml='<option value="">请选择类型</option>';
					 	 for(var i=0;i<object.length;i++){
					 		 if(childType == object[i].typeId){
						 		 typehtml+= '<option selected="selected" value="'+object[i].typeId+'">'+object[i].typeName+'</option>';
					 		 }else{
						 		 typehtml+= '<option value="'+object[i].typeId+'">'+object[i].typeName+'</option>';
					 		 }
					 	 }
					 	 $("#goodChildType").html(typehtml);
					 	 form.render();
					 }else{
						 //layer.msg("该无分类,请先添加商品分类在添加商品");
						 //return;
					 }
				 }
				 ,error:function(res){
					 
				 }
			 })
		}
		
		form.on('select(isDtbt)',function(data){
			  if(data.value == 1){
				 $(".msTime").show(); 
			  }else{
				  $(".msTime").hide();
			  }
			  $(".msTime").css("display","inline-block");
			  form.render();

		})
		//多规格显示隐藏
		form.on('select(isMoreSpec)',function(data){
			var guige = data.value;
			if(guige == 1){
				$("#moregg").show();
			}else{
				$("#moregg").hide();
			}
		})
		
		//规格1
		form.on('select(type1)',function(data){
			type1name = $(this).text();
			$(".one").text(type1name);
			type1 = data.value;
			if(type1 >0){
				$("#type1send").show();
			}else{
				$("#type1send").hide();
			}
		})
		
		//规格2
		form.on('select(type2)',function(data){
			type2name = $(this).text();
			$(".two").text(type2name);
			type2 = data.value;
			if(type2 >0){
				$("#type2send").show();
			}else{
				$("#type2send").hide();
			}
		})
	
		
		//监听提交
		form.on('submit(demo1)', function(data) {
			//var  newuploadStr = uploadStr.substr(0,uploadStr.length-1);
			//console.log(newuploadStr);
			//return false;
			//alert(123);
			//return false;
			
			var uploadStr = "";
			//遍历获取图片
			$("#demo2").find("img").each(function(i){
				 var imgSrc = $(this).attr("src");
				 console.log("imgSrc-"+i+":"+imgSrc);
				 uploadStr += ","+imgSrc;
			})
			var newuploadStr = uploadStr.substr(1,uploadStr.length-1);
			var activityPic = "";
			var pic = $("#activitydemo2").find("img").attr("src");
			if(pic){
				activityPic = pic;
			}
			var way = $('#recommendWay').val();
			var vipPrice =data.field.vipPrice;
			if(vipPrice== ""){
				vipPrice = 0;
			}
			var goodsRebate = data.field.goodsRebate;
			if(goodsRebate == ""){
				goodsRebate = 0;
			}
			var moreGGStr = "";
			var ggStr = "";
			if(data.field.isMoreSpec == 1){
				//遍历价格和库存
				var zzArr = [];
				$(".tbodydata tr").each(function(i){
					var type1name = $(this).find(".typeone").val();
					var type2name = $(this).find(".typetwo").text();
					var price = $(this).find(".ggprice").val();
					var stock = $(this).find(".ggstock").val();
					var zz = {'type1':type1name,'type2':type2name,'price':price,'kucun':stock};
					zzArr.push(zz)
				})
				
				moreGGStr = JSON.stringify(zzArr);
			    var ggArr = [{'id':1,'name':type1name},{'id':2,'name':type2name}];
			    ggStr = JSON.stringify(ggArr);
			}   
			$.ajax({
				type : "post",
				url : "${ctx}/goods/updateGoods.do",
				dataType:"json",
				data : {
					"goodsId" : goodsId,
					"goodsName":data.field.goodsName,
					"goodsType":data.field.goodsType,
					"goodChildType":data.field.goodChildType,
					"loginPlat":data.field.loginPlat,
					"isPassSell":data.field.isPassSell,
					"recommendWay":way,
					"goodsImage":newuploadStr,
					"detailsImage":newuploadStr,
					"isRecommend":data.field.isRecommend,
					"firstCost":data.field.firstCost,
					"sellPrice":data.field.sellPrice,
					"vipPrice":vipPrice,
					"goodsRebate":goodsRebate,
					"goodsStock":data.field.goodsStock,
					"isSelled":data.field.isSelled,
					"sellOrRent":data.field.sellOrRent,
					"goodsDetails":ue.getContent(),
					"isScore" : data.field.isScore,
					"scoreCount" : data.field.scoreCount,
					"isMoreSpec":data.field.isMoreSpec,
					"moreGGStr":moreGGStr,
					"ggStr":ggStr,
					"goodSort":data.field.goodSort,
					"isNew":data.field.isNew,
					//"msTime":data.field.msTime,
					//"isDtbt":data.field.isDtbt,
					"goodRecommendPic":activityPic,
					"brandId":data.field.brandId,
				},
				success : function(result) {
					if(result.code==0){
						layer.msg("编辑成功");
						setTimeout(function(){
							window.location.reload(true);
						},1000);
					}else{
						layer.msg(result.msg);
						return;
					}
				},error:function(data){
					layer.msg("编辑失败");
				}
			});
			return false;
		});
		
		//console.log("${type1List}")
		//var xh = 
		//zzTable("${type1List}","${type2List}");
		
	});
	
function sucess_totemplate(res) {
	var html = template('show_loginPlat', res);
	$('#tag_list1').html(html);
    form.render();
}
$('#confirm_update').on('click',function(){
	$('#myModal').modal('hide');
	var id=$('#tagId').val();
	$('#manyloginPlat').val(id);
});

function confirm_update(){
	var way =$('#recommendWay').val();
	$('#recommendWay').val(way);
	$('#myModal').modal('hide');
}

function addType(typeId,name){
	$('#nameType').text(name);
	$('#goodsType').val(typeId);
	$('#myModal1').modal('hide');
}

function addGoodsType(){
	var name = $('#typeName').val();
	if(!name || name == ""){
		layer.msg("商品类型不能为空",{time:1000});
		return false;
	}
	var loginPlat = $('#loginPlat').val();
	$.ajax({
		url:'${ctx}/goods/addGoodsType.do',
		type:'post',
		dataType:'json',
		data:{
			'typeName':name,
			'orderNo':1,
			'loginPlat':loginPlat
		},
		success:function(data){
			if(data.code == 0){
// 				alert(JSON.stringify(data));
				var type = data.msg;
				$('#myModal1').modal('hide');
				$('#nameType').text(name);
				$('#goodsType').val(type);
			}else{
				layer.msg(data.msg,{time:1000});
				return false;
			}
		}
	})
}


function delImg(index){
	num--;
	$("#pic_"+index).remove();
}
function goBack(){
	window.history.go(-1);
}

/**多规格方法**/
 function addxh(){
		var xh = $('#xh').val();
		if(!xh){
			layer.msg("请输入规格");
			return false;
		}
		
		//先遍历对比判断是否重复
		//遍历型号
		var isCheck = 0;
		$(".xh-list").find("li").each(function(i){
			 var checkname = $(this).text();
			 if(checkname == xh){
				isCheck = 1;
			 }
		})	
		if(isCheck == 1){
			 layer.msg("规格名称不能重复");
			 return false;
		}
		var html = "<li onclick='removeli(this)'>"+xh+"</li>";
		$(".xh-list").append(html);
		//遍历型号
		var xhArr = [];
		$(".xh-list").find("li").each(function(i){
			 var xhname = $(this).text();
			 xhArr.push(xhname);
		})	
		
		//遍历规格
		var ggArr = [];
		$(".gg-list").find("li").each(function(i){
			 var ggname = $(this).text();
			 ggArr.push(ggname);
		})	
		$('#xh').val('');
		$("#piliang").show();
		zzTable(xhArr,ggArr);
	}
	
	function addgg(){
		var gg = $('#gg').val();
		if(!gg){
			layer.msg("请输入规格");
			return;
		}
		var isCheck = 0;
		$(".gg-list").find("li").each(function(i){
			 var checkname = $(this).text();
			 if(checkname == gg){
				isCheck = 1;
			 }
		})	
		if(isCheck == 1){
			 layer.msg("规格名称不能重复");
			 return false;
		}
		var html = "<li onclick='removeli(this)'>"+gg+"</li>";
		$(".gg-list").append(html);
		//遍历型号
		var xhArr = [];
		$(".xh-list").find("li").each(function(i){
			 var xhname = $(this).text();
			 xhArr.push(xhname);
		})	
		
		//遍历规格
		var ggArr = [];
		$(".gg-list").find("li").each(function(i){
			 var ggname = $(this).text();
			 ggArr.push(ggname);
		})	
		$('#gg').val('');
		zzTable(xhArr,ggArr);
	}
	
	function zzTable(xhArr,ggArr){
		var xhcount = xhArr.length; //型号长度
		var ggcount = ggArr.length; //规格长度
		if(xhcount > 0){
			$(".one").show();
		}
		if(ggcount > 0){
			$(".two").show();
		}
		var html1 = "";
		for(var i =0;i<xhcount;i++){
			if(ggcount > 0){
				for(var j =0;j<ggcount;j++){
					 if(j == 0){
						 html1 += "<tr>";
			             html1 += "<td  rowspan='"+ggcount+"'>"+xhArr[i]+"<input type='hidden' class='typeone' value='"+xhArr[i]+"'></td>";
			             if(ggcount > 0){
			                    html1 += "<td class='typetwo'>"+ggArr[j]+"</td>";

			             }else{
			                    html1 += "<td class='two typetwo'>"+ggArr[j]+"</td>";
			             }
			                html1 += "<td><input type='number' class='layui-input ggprice'/></td>";
			                html1 += "<td><input type='number' class='layui-input ggstock'/></td>";			              
			                html1 += "</tr>";    
					 }else{
						 html1 += "<tr> <input type='hidden' class='typeone' value='"+xhArr[i]+"'>";
						 
			                if(ggcount > 0){
			                    html1 += "<td class='typetwo'>"+ggArr[j]+"</td>";

			                }else{
			                    html1 += "<td class='two typetwo'>"+ggArr[j]+"</td>";
			                }
			                html1 += "<td><input type='number' class='layui-input ggprice'/></td>";
			                html1 += "<td><input type='number' class='layui-input ggstock' /></td>";
			                html1 += "</tr>";
					 }
				}
			}else{
				 html1 += "<tr><input type='hidden' class='typeone' value='"+xhArr[i]+"'>";
	             html1 += "<td>"+xhArr[i]+"</td>";
	             if(ggcount > 0){
	                    html1 += "<td class='typetwo'></td>";

	             }else{
	                    html1 += "<td class='two typetwo'></td>";
	             }
	                html1 += "<td><input type='number' class='layui-input ggprice'/></td>";
	                html1 += "<td><input type='number' class='layui-input ggstock'/></td>";
	                html1 += "</tr>";   
			}
			
		}
		
	    $(".tbodydata").html(html1);
	}
	
	function removeli(e){
		$(e).remove();
		//遍历型号
		var xhArr = [];
		$(".xh-list").find("li").each(function(i){
			 var xhname = $(this).text();
			 xhArr.push(xhname);
		})	
		
		//遍历规格
		var ggArr = [];
		$(".gg-list").find("li").each(function(i){
			 var ggname = $(this).text();
			 ggArr.push(ggname);
		})	
		zzTable(xhArr,ggArr);
	}
   
	//批量增加
	function addPP(){
		var price = $("#ppprice").val();
		var stock = $("#ppstock").val();
		if(price){
			$(".ggprice").val(price);
		}
		if(stock){
			$(".ggstock").val(stock);
		}
		
	}
	
	
</script>
</html>