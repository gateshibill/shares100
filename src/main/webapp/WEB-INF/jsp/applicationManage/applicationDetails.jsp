<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>应用详情</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

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
	
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />

<style type="text/css">
.layui-form-select .layui-input {
	padding-right: 30px;
	cursor: pointer;
	width: 200px;
	height: 38px;
	margin-left: 24px;
}
.layui-form-select .layui-edge {
    position: absolute;
    right: -25px;
    top: 50%;
    margin-top: -3px;
    cursor: pointer;
    border-width: 6px;
    border-top-color: #c2c2c2;
    border-top-style: solid;
    transition: all .3s;
    -webkit-transition: all .3s;
}
#demo2 img{
width:200px !important;
height:150px !important;
margin-right:20px;
}
#demo3 img{
width:200px !important;
height:150px !important;
margin-right:20px;
}
#demo4 img{
width:200px !important;
height:150px !important;
margin-right:20px;
}
</style>
</head>
<body>
<body>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>应用【${app.applicationName}】的详情</legend>
		</fieldset>
		<div class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">应用编号：</label>
				<div class="layui-input-block">
					<label class="layui-form-label">${app.applicationId}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">应用名称：</label>
				<div class="layui-input-block">
					<label class="layui-form-label"> <input type="text"
						style="display: inline !important; width:200px;height:38px;margin-top:-8px;" name="name"
						id="applicationName" lay-verify="applicationName"
						autocomplete="off" class="layui-input"
						value="${app.applicationName}">
					</label>
				</div>
			</div>
			
				<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">应用状态 :</label>
				<div class="layui-input-inline">
					<select name="applicationStatus" id="applicationStatus"
						lay-verify="appStatus">
						<option value="1"
							<c:choose>
							<c:when test="${app.applicationStatus==1}">selected="selected"</c:when>
						</c:choose>>试用中</option>
						<option value="2"
							<c:choose>
							<c:when test="${app.applicationStatus==2}">selected="selected"</c:when>
						</c:choose>>正常服务</option>
						<option value="3"
							<c:choose>
							<c:when test="${app.applicationStatus==3}">selected="selected"</c:when>
						</c:choose>>停止服务</option>
					</select>
				</div>
			</div>
			
			<c:if test="${app.applicationType !=1}">
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">应用类型 :</label>
					<div class="layui-input-inline">
						<select name="groupType" id="groupType" lay-verify="appStatus" lay-search="">
							<c:forEach items="${typeList}" var="t">
								<c:choose>
									<c:when test="${app.groupType==t.typeId}">
										<option selected="selected" value="${t.typeId}">${t.typeName}</option>
									</c:when>
									<c:otherwise>
										<option value="${t.typeId}">${t.typeName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">销售状态：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <textarea rows="5"
								cols="35" id="saleStatus">${app.saleStatus}</textarea>
						</label>
						<span style="color:red"><br>修改销售状态的时候，请不要把删除之前的数据，<br>在之前数据后面填写，以逗号隔开。</span>
					</div>
				</div>
				
			</c:if>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">访客数：</label>
				<div class="layui-input-block" style="width:200px;">
					<label class="layui-form-label"> <input type="text"
						style="display: inline !important; width:200px;height:38px;margin-top:-8px;" 
						name="visitorCount" id="visitorCount" lay-verify="visitorCount"
						autocomplete="off" class="layui-input" value="${app.visitorCount}">
					</label>
				</div>
			</div>
			
			<c:if test="${app.applicationType ==1}">
			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">应用ID：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							name="appId" id="appId" lay-verify="appId"
							autocomplete="off" class="layui-input"
							value="${app.appId}">
						</label>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">应用密钥：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							name="appSecret" id="appSecret" lay-verify="appSecret"
							autocomplete="off" class="layui-input"
							value="${app.appSecret}">
						</label>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">商户号：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							name="mchid" id="mchid" lay-verify="mchid"
							autocomplete="off" class="layui-input"
							value="${app.mchid}">
						</label>
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">支付密钥：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							name="apiKey" id="apiKey" lay-verify="apiKey"
							autocomplete="off" class="layui-input"
							value="${app.apiKey}">
						</label>
					</div>
				</div>
		
			
			<div class="layui-form-item">
           <div class="layui-upload">
                  <label class="layui-form-label">应用logo：</label>
                     <button type="button" class="layui-btn" id="applogoUpload">上传图片</button> 
                     
                  <div class="layui-upload-list" id="pintrolist" style="cursor: pointer;">
                  	<img id="applogo" src="${app.appLogo}" style="max-width:100px;max-height:100px;">
                  </div>
          </div>
          </div>
			
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">模板名称：</label>
					<div class="layui-input-inline">
								<select name="modelId" id="modelId">
									<c:forEach var="model" items="${midelList}">
										<c:choose>
											<c:when test="${app.modelId==model.modelId}">
												<option selected="selected" value="${model.modelId}">${model.modelName}</option>
											</c:when>
											<c:otherwise>
												<option value="${model.modelId}">${model.modelName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
						<!-- 					<a class="layui-btn" -->
						<!-- 						onclick="window.location.href='http://www.baidu.com'">查看模板</a> -->
					</div>
				</div>
			</c:if>

				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">联系电话：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							name="createrPhone" id="createrPhone" lay-verify="phone11"
							autocomplete="off" class="layui-input"
							value="${app.createrPhone}">
						</label>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">QQ号：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							name="createQQ" id="createQQ" lay-verify="createQQ"
							autocomplete="off" class="layui-input"
							value="${app.createQQ}">
						</label>
					</div>
				</div>
				<c:if test="${app.applicationType==2}">
				<!-- 社区logo图 -->
				<div class="layui-upload">
					<label class="layui-form-label" style="width: 100px;">&nbsp;标题图：</label>
					<button type="button" class="layui-btn" id="test4">上传图片</button>
					<div class="layui-upload-list">
						<img src="${app.groupCard}" class="layui-upload-img" id="demo4"
							style="width: 200px; height: 200px;">
						<p id="demoText"></p>
					</div>
				</div>
				<!-- 没有生成二维码就不显示出来 -->
				<div class="layui-upload">
					<label class="layui-form-label" style="width: 100px;">&nbsp;二维码：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <c:choose>
								<c:when test="${code.downloadUrl != null}">
									<img src="${code.downloadUrl}"
										style="width: 260px; height: 260px;">
								</c:when>
								<c:otherwise>
									<span style="color: red;">该应用还未生成相应的二维码，请在手机点击生成</span>
								</c:otherwise>
							</c:choose>
						</label>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">应用链接：</label>
					<div class="layui-input-block">
						<label class="layui-form-label">${code.path}</label>
					</div>
				</div>
			</c:if>


			<!-- 应用信息 -->
			<c:if test="${abstract1 !=null}">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">简介标题：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							id="abstractTitle" name="abstractTitle"
							lay-verify="abstractTitle" autocomplete="off" class="layui-input"
							value="${abstract1.abstractTitle}">
						</label>
					</div>
				</div>
<%-- 
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">企业电话：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 200px; height: 38px; margin-top: -8px;"
							name="companyPhone" id="companyPhone" lay-verify="phone"
							autocomplete="off" class="layui-input"
							value="${abstract1.companyPhone}">
						</label>
					</div>
				</div> --%>


				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">企业地址：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <input type="text"
							style="display: inline !important; width: 300px; height: 38px; margin-top: -8px;"
							name="companyAddress" id="companyAddress"
							lay-verify="applicationName" autocomplete="off"
							class="layui-input" value="${abstract1.companyAddress}">
						</label>
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">企业信息：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <textarea rows="8"
								cols="60" id="abstractContent">${abstract1.abstractContent}</textarea>
						</label>
					</div>
				</div>

				<div class="layui-upload">
					<label class="layui-form-label" style="width: 100px;">&nbsp;标题图：</label>
					<button type="button" class="layui-btn" id="test">上传图片</button>
					<div class="layui-upload-list">
						<img src="${abstract1.titleImage}" class="layui-upload-img"
							id="demo2" style="width: 200px; height: 200px;">
						<!-- 						<p id="demoText"></p> -->
					</div>
				</div>

				<div class="layui-upload">
					<label class="layui-form-label" style="width: 100px;">详情图：</label>
					<button type="button" class="layui-btn" id="test3">上传图片</button>
					<span style="color: red">点击图片即可以删除图片</span>
					<div class="layui-upload-list" id="demo3">
						<c:if test="${myImage != null and myImage !=''}">
							<c:forEach items="${myImage}" var="m" varStatus="status">
								<c:if test="${m!=null and m!=''}">
									<img
										onclick="delthisImg(${status.index+1},${abstract1.abstractId})"
										data-index="${ status.index}" src="<c:out value="${m}" />">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>


				<div class="layui-upload">
					<label class="layui-form-label" style="width: 100px;">&nbsp;二维码：</label>
					<div class="layui-input-block">
						<label class="layui-form-label"> <c:choose>
								<c:when test="${code.downloadUrl != null}">
									<img src="${code.downloadUrl}"
										style="width: 260px; height: 260px;">
								</c:when>
								<c:otherwise>
									<span style="color:red;">该应用还未生成相应的二维码，请在手机点击生成</span>
								</c:otherwise>
							</c:choose>
						</label>
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px;">应用链接：</label>
					<div class="layui-input-block" style="margin-top: 8px;">
						${code.path}</div>
				</div>

			</c:if>



			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">用户编号：</label>
				<div class="layui-input-block">
					<label class="layui-form-label">${app.appOwner.userId}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">用户昵称：</label>
				<div class="layui-input-block">
					<label class="layui-form-label">${app.appOwner.nickName}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">用户姓名：</label>
				<div class="layui-input-block">
					<label class="layui-form-label">${app.appOwner.realName}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">用户邮箱：</label>
				<div class="layui-input-block">
					<label class="layui-form-label">${app.appOwner.email}</label>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">用户电话：</label>
				<div class="layui-input-block">
					<label class="layui-form-label">${app.appOwner.phone}</label>
				</div>
			</div>
		
		</div>
		
		<a class="layui-btn layui-btn-small"> 
			<i class="layui-icon">&#xe608;</i> 添加管理</a>
       <!--  拿到应用管理者列表-->
		<table class="layui-table" id="descovery" style="margin-top: 0px;" lay-filter="descovery"></table>
        <script type="text/html" id="toolBar">
      	  <a class="layui-btn layui-btn-danger layui-btn-mini" onclick="deleteAppId({{d.manageId}},1)">取消</a>
          <a class="layui-btn layui-btn-mini"  onclick="deleteAppId({{d.manageId}},2)">设置</a>
        </script>
        
 	<!-- 模态框开始  -->
	<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
				      aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width:60%;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">添加应用管理</h4>
						</div>
						<div class="layui-form-item" style="margin-top: 10px;">
							 <div class="layui-inline">
								<label class="layui-form-label"
									style="width: 100px; font-size: 14px;">应用ID</label>
								<div class="layui-input-inline" style="width:180px;">
									<input type="text" name="appid" id="appid"
									  placeholder="请输入应用id" class="layui-input">
								</div>
								</div>
								<div class="layui-inline">
								<label class="layui-form-label"
									style="width: 100px; font-size: 14px;">用户昵称</label>
								<div class="layui-input-inline" style="width:180px;">
									<input type="text" name="nickName" id="userName"
									  placeholder="请输入用户昵称" class="layui-input">
								</div>
								</div>
						<span class="layui-btn layui-btn-normal" onclick="seeNickName()"
								style="height: 30px; line-height:30px;margin-top:-15px; padding: 0 18px;">搜索</span>
						</div>
						<div class="modal-body" style="margin-top:-25px;">
							<table class="layui-table" style="width: 98%; margin:0 auto" id="deletemoren">
								<thead>
									<tr>
										<th>平台ID</th>						
										<th>用户编号</th>
										<th>用户昵称</th>
										<th>注册时间</th>
									    <th>操作</th>
									</tr>
								</thead>
								<tbody id="goodsty">
									<!--应用内容  -->
								</tbody>
							</table>
							<div id="pageType" style="text-align: center;"></div>
						</div>
						<div class="modal-footer">

						</div>
					</div>
				</div>
			</div>
			<!--模态框结束-->
			
<script type="text/html" id="show_goodsType">
{{each data as value i}}
<tr>
<td>{{value.loginPlat}}</td>
<td>{{value.userId}}</td>
<td>{{value.nickName}}</td>
<td>{{value.createTime}}</td>
<td><a class="layui-btn layui-btn-normal" onclick="addAppuser({{value.userId}})"
	   style="height:30px;line-height:30px;padding:0 18px;">添加</a></td>
</tr>
{{/each}}
</script>
<script type="text/html" id="nicknameTpl">
       {{#if(d.user){}}
              {{d.user.nickName}}
       {{# }else{}}
        <span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
<script type="text/html" id="sexTpl">
{{#if(d.user){}}
      {{# if (d.user.userSex=='1'){}}
    	男
    {{# } else if(d.user.userSex=='2'){ }}
     	女
 	{{# } }}
       {{# }else{}}
        <span style="color:red"> 用户未找到   </span>   
       {{#}}}
</script>
		<script type="text/html" id="statusTpl">
      {{# var destype=d.manageStatus}}
    {{# if (destype=='1'){}}
    	管理者
    {{# } else if (destype=='3'){}}
    	<span style="color:red">归属者(所有权)</span>
    {{# } }}
</script>
		<br /> <br />
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="demo1">提交</button>
				<button onclick="goBack();" class="layui-btn layui-btn-primary">返回</button>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/artTemplate-simple-3.0.js"></script>
<script type="text/javascript">
	var appId = '${app.applicationId}',pageno=1,pagesize=10,uploadStr='',upeatils='',length='${length}',group_card='',myy='${abstract1.abstractPictures}',abId='${abstract1.abstractId}';
	var laypage,nickName;
	layui.use('upload', function(){
		  var $ = layui.jquery
		  ,upload = layui.upload;
		  //多图片上传
		  upload.render({
		    elem: '#applogoUpload'
		    ,url: '${ctx}/layfileUpload/upload.do'
		    ,multiple: true
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		      obj.preview(function(index, file, result){
		    	 
		    		  //$('#logolist').html('<img src="'+ result +'"   class="layui-upload-img">')
		    	 
		      });
		    }
		    ,done: function(res){
		       $("#applogo").attr("src",res.data.src)
		    }
		  });
		  
	});
	layui.use([ 'layer', 'table', 'laydate' ], function() {
		table = layui.table;
		layer = layui.layer;
		var laydate = layui.laydate;
		//--------------方法渲染TABLE----------------
		getlist(appId);
	});
	function getlist(loginPlat) {
		tableIns = table.render({
			elem : '#descovery',
			id : 'descovery',
			method : 'post',
			where : {
				'loginPlat' : loginPlat
			},
			url : '${ctx}/application/appManageList.do',
			cols : [ [
// 			 {
// 				checkbox : true,
// 				LAY_CHECKED : false,
// 				fixed : true
// 			}, 
			{
				field : 'manageId',
				title : 'ID',
				width : 80,
				align : 'center',
				fixed : true,
				sort : true
			}, {
				field : 'loginPlat',
				title : '应用编号',
				width : 120,
				align : 'center',
			},{
				field : 'status',
				title : '管理状态',
				width : 180,
				align : 'center',
				templet : "#statusTpl"
			},{
				field : 'manageUser',
				title : '管理用户ID',
				width : 120,
				align : 'center',
			},{
				field : 'nickname',
				title : '用户昵称',
				width : 150,
				align : 'center',
				templet : "#nicknameTpl"
			},{
				field : 'sex',
				title : '用户性别',
				width : 120,
				align : 'center',
				templet : "#sexTpl"
			},{
				field : 'createTime',
				title : '创建时间',
				width : 180,
				align : 'center',
				sort : true,
			}, {
				title : '操作',
				width : 180,
				align : 'center',
				fixed : 'right',
				toolbar : '#toolBar'
			} ] ],
			page : true,
			limits : [ 1, 10, 20, 30, 50, 100 ],
			limit : 20, //默认采用20
			width : '100%',
			height : 'auto',
			loading : true,
			even : true,
			response : {
				statusName : 'code' //数据状态的字段名称，默认：code
				,
				statusCode : 0 //成功的状态码，默认：0
				,
				countName : 'count' //数据总数的字段名称，默认：count
				,
				dataName : 'data' //数据列表的字段名称，默认：data
			},
			done : function(res, curr, count) {
				//如果是异步请求数据方式，res即为你接口返回的信息。
				//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
				//console.log(res);
				//得到当前页码
				//console.log(curr);
				//$("#curPageIndex").val(curr);
				//得到数据总量
				//console.log(count);
			}
		});
	}
	

layui.use('upload', function(){
  var $ = layui.jquery
  ,upload = layui.upload;
  
  //普通图片上传
  var uploadInst = upload.render({
    elem: '#test'
    ,url: '${ctx}/layfileUpload/upload.do'
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
        $('#demo2').attr('src', result); //图片链接（base64）
      });
    }
    ,done: function(res){
      //如果上传失败
      if(res.code > 0){
        return layer.msg('上传失败');
      }
      //上传成功
      uploadStr += res.data.src;
    }
    ,error: function(){
    	//演示失败状态，并实现重传
        var demoText = $('#demoText');
        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
        demoText.find('.demo-reload').on('click', function(){
          uploadInst.upload();
        });
    }
  });
})

layui.use('upload', function(){
  var $ = layui.jquery
  ,upload = layui.upload;
  
  //普通图片上传
  var uploadInst = upload.render({
    elem: '#test4'
    ,url: '${ctx}/layfileUpload/upload.do'
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
        $('#demo4').attr('src', result); //图片链接（base64）
      });
    }
    ,done: function(res){
      //如果上传失败
      if(res.code > 0){
        return layer.msg('上传失败');
      }
      //上传成功
      group_card += res.data.src;
    }
    ,error: function(){
    	//演示失败状态，并实现重传
        var demoText = $('#demoText');
        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
        demoText.find('.demo-reload').on('click', function(){
          uploadInst.upload();
        });
    }
  });
})

layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //多图片上传
	  upload.render({
	    elem: '#test3'
	    ,url: '${ctx}/layfileUpload/upload.do'
	    ,multiple: true
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  length ++;
	    	  if(length > 3){
	    		  layer.msg('最多上传3张图片',{time:1500});
	    	  } else{
	    		  $('#demo3').append('<img src="'+ result +'" id="'+index+'" onclick="delImg('+index+')" alt="'+ file.name +'" class="layui-upload-img">')
	    	  }
	      });
	    }
	    ,done: function(res){
	    	upeatils += res.data.src+',';
	    }
	  });
	  
	});
	
	    layui.use(['form','layedit','laypage'],function(){
	          form = layui.form,laypage = layui.laypage;
						//自定义验证规则
						form.verify({
							applicationName : function(value) {
								if (value.trim() == '') {
									return '应用名称不能为空';
								}
							},
							phone11: [/^1\d{10}$/,'请输入正确的号码!'],
							visitorCount : [/^\d+(\.\d+)?$/,'请输入正确的数字!']
						});
						
						//添加商品类型
						$('.layui-btn-small').on('click', function() {
								$('#myModal1').modal('show');
								addManageUser(pageno);
						});
						
				
						
						
						//监听提交
						form.on('submit(demo1)', function(data) {
							var name = $('#applicationName').val();
							var status = $('#applicationStatus').val();
							var modelId = $('#modelId').val();				
							if(myy != null && myy !='' && upeatils != null){
								upeatils += myy;
							} else {
								upeatils = upeatils.substr(0,upeatils.length-1);
							}
							$.ajax({
								type : "post",
								url : "${ctx}/application/updateAppliction.do",
								dataType : "json",
								data : {
									"applicationId" : appId,
									"abstractId" : abId,
									"applicationStatus" : status,
									"applicationName" : name,
									"groupType" : $('#groupType').val(),
									'saleStatus':$('#saleStatus').val(),
									"titleImage" : uploadStr,
									"abstractPictures" : upeatils,
									"groupCard":group_card,
									"abstractTitle":$('#abstractTitle').val(),
									"abstractContent":$('#abstractContent').val(),
									"createrPhone":$('#createrPhone').val(),
									"createQQ":$("#createQQ").val(),
									"companyPhone":$('#companyPhone').val(),
									"companyAddress":$('#companyAddress').val(),
									"visitorCount":$('#visitorCount').val(),
									"modelId" : modelId,
									"appId":$("#appId").val(),
									"appSecret":$("#appSecret").val(),
									"mchid":$("#mchid").val(),
									"apiKey":$("#apiKey").val(),
									"appLogo":applogo.src,
								},
								success : function(result) {
									if (result.code == 0) {
										layer.msg("修改成功");
										setTimeout(function() {
											location.reload();
										}, 1000);
										//setTimeout(location.reload(),1000);
									} else {
										layer.msg(result.msg);
									}
								},
								error : function() {
									layer.msg("修改失败");
								}
							});
							return false;
						});
					});
	
	
	//添加用户
	function addAppuser(uid){
		layer.confirm('您确认添加该用户么?', {
			btn : [ '确认', '取消' ]
		}, function() {
			$.post('${ctx}/application/addApplicationManage.do', {
				'loginPlat' : appId,
				'manageUser' : uid,
				'type':1
			}, function(data) {
				if (data.code==0) {
					layer.msg('添加用户成功');
					setTimeout(function() {
						window.location.href="${ctx}/application/appDetails.do?appId="+appId;
					}, 400);
				} else {
					layer.msg(data.msg, {
						time : 2500
					});
				}
			});
		}, function() {
			layer.closeAll();
		})
	}
	
	function deleteAppId(mid,type){
		if(type ==1){
			layer.confirm('您确认取消该应用管理者么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/application/deleteManage.do', {
					'manageId' : mid,
					'manageStatus':2,
					'type':1
				}, function(data) {
					if (data.code==0) {
						layer.msg('取消管理用户成功');
						setTimeout(function() {
							window.location.href="${ctx}/application/appDetails.do?appId="+appId;
						}, 400);
					} else {
						layer.msg(data.msg, {
							time : 2500
						});
					}
				});
			}, function() {
				layer.closeAll();
			})
		} else{
			layer.confirm('您确认设置该用户为该应用的所有者么?', {
				btn : [ '确认', '取消' ]
			}, function() {
				$.post('${ctx}/application/deleteManage.do', {
					'manageId' : mid,
					'manageStatus':3,
					'type':2
				}, function(data) {
					if (data.code==0) {
						layer.msg('取消应用所有者成功');
						setTimeout(function() {
							window.location.href="${ctx}/application/appDetails.do?appId="+appId;
						}, 400);
					} else {
						layer.msg(data.msg, {
							time : 2500
						});
					}
				});
			}, function() {
				layer.closeAll();
			})
		}
	}
	
	function seeNickName(){
		nickName=$('#userName').val();
		appid = $('#appid').val();
		addManageUser(pageno,nickName,appid);
	}
	function addManageUser(pageno,nickName,appid) {
		$.getJSON("${ctx}/application/addManageUser.do", {
			page : pageno,
			limit : pagesize,
			nickName:nickName,
			loginPlat:appid
		}, function(res) {
			if (res.code == 0) {
				var html = template('show_goodsType', res);
				$('#goodsty').html(html);
				if (res.count > 0) {
					//不显示首页尾页
					laypage.render({
						elem : 'pageType',
						count : res.count,
						curr : pageno || 1,//当前页
						layout : [ 'count', 'prev', 'page', 'next', 'limit' ],
						jump : function(obj,first) {
							//触发分页后的回调
							if (!first) {
								//点击跳页触发函数自身，并传递当前页：obj.curr
								addManageUser(obj.curr,nickName,appid);
							}
						}
					});
				}
			} else {
				var html = template('show_goodsType', res);
				$('#goodsty').html(html);
				layer.msg('没有更多数据了!');
			}
		})
	}
	
	function delthisImg(index,abid){
		length--;
		$.ajax({
			url:'${ctx}/application/updateImage.do',
			type:'post',
			dataType:'json',
			data:{
				'abstractId':abid,
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
	
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>