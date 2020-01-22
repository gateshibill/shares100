<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title>活动列表</title>
<link rel="stylesheet" href="${ctx}/css/pintuer.css">
<link rel="stylesheet" href="${ctx}/css/admin.css">
<link rel="stylesheet" href="${ctx}/css/skin/default/layer.css">
<style type="text/css">
.w80 {
	width: 80px;
}

.dateInput {
	width: 150px;
	height: 40px;
	line-height: 40px;
	display: inline-block
}

.bp15 {
	height: 40px;
	line-height: 38px;
	margin-left: 10px;
	padding: 0 15px
}

.layer-anim {
	top: 100px !important;
}
</style>
<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/pintuer.js"></script>
<script type="text/javascript"
	src="${ctx}/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/js/layer.js"></script>
</head>
<body>
	<form method="post" action="${ctx}/active/activeList.do" id="listform">
		<div class="panel admin-panel">
			<div class="padding border-bottom">
				<ul class="search" style="padding-left: 10px;">
					<li>活动名称：</li>
					<li><input type="text" placeholder="请输入商品名称"
						value="${goods.goodsName}" name="goodsName"
						class="input dateInput" /></li>
					<li>发布者姓名：</li>
					<li><input type="text" placeholder="请输入商品名称"
						value="${user.realName}" name="activeUser.realName"
						class="input dateInput" /></li>
					<li>发布日期：</li>
					<li><input type="text" placeholder="选择开始日期"
						value="${searchBeginDate}" name="searchBeginDate"
						class="input dateInput"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></li>
					<li>到 <input type="text" placeholder="选择结束日期"
						value="${searchEndDate}" name="searchEndDate"
						class="input dateInput"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></li>
					<li><input type="submit" class="button border-main bp15"
						value="搜索"> <!-- style="height:40px;line-height:38px;margin-left:10px;" --></li>
				</ul>
			</div>
			<div style="text-align: left; padding: 20px 0;">
				<div style="padding:8px 0 0 20px;float:left"><input
					type="checkbox" id="checkall" />全选</div>
				<div style="padding-left:20px;float:left">
					<a href="javascript:void(0)" class="button bg-sub icon-plus"
					style="padding: 5px 15px;" onclick="window.location.href='${ctx}/active/toAddActive.do'">添加活动</a>
				</div>
				<div style="padding-left:20px;float:left">
					<a href="javascript:void(0)" class="button bg-dot icon-trash-o"
					style="padding: 5px 15px;" onclick="DelSelect()">批量取消</a>
				</div>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="6%">选中状态</th>
					<th width="6%">活动ID</th>
					<th width="10%">活动名称</th>
					<th width="10%">发布者名称</th>
					<th width="10%">发布时间</th>
					<th width="12%">开始时间</th>
					<th width="12%">结束时间</th>
					<th width="12%">操作</th>
				</tr>
				<c:forEach var="activeInfo" items="${activeList}">
					<tr>
						<td><input type="checkbox" name="id[]"
							value="${activeInfo.activeId}" /></td>
						<td>${activeInfo.activeId}</td>
						<td>${activeInfo.activeTitle}</td>
						<td>${activeInfo.activeUser.realName}</td>
						<td><fmt:formatDate value="${activeInfo.createTime}"
								type="both" /></td>
						<td><fmt:formatDate value="${activeInfo.activeBeginTime}"
								type="both" /></td>
						<td><fmt:formatDate value="${activeInfo.activeEndTime}"
								type="both" /></td>
						<td><div class="button-group">
								<a class="button border-main"
									href="${ctx}/active/activeDetails.do?activeId=${activeInfo.activeId}&pageNo=${page}"><span
									class="icon-edit"></span>详情</a> <a class="button border-red"
									href="javascript:void(0)"
									onclick="deleteone(${goodsInfo.goodsId})"><span
									class="icon-trash-o"></span>取消</a>
							</div></td>
					</tr>
				</c:forEach>
			</table>
			<div id="pages" style="width: 100%; border-top: 1px solid red">
				<div class="pagelist">
					<a href="javascript:;" onclick="goPageAction(1);">首页</a>
					<c:choose>
						<c:when test="${page == 1}">
							<a href="javascript:;">上一页</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:;" onclick="goPageAction(${page - 1});">上一页
							</a>
						</c:otherwise>
					</c:choose>

					<c:forEach
						begin="${page>2?(page+2>totalPages?(totalPages>5?totalPages-4:1):page-2):1}"
						end="${page>3?page+2>totalPages?totalPages:page+2:totalPages>5?5:totalPages}"
						var="p">
						<c:choose>
							<c:when test="${page == p}">
								<a href="javascript:;" onclick="goPageAction(${p});"
									class="current">${p}</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:;" onclick="goPageAction(${p});">${p}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${page == totalPages||totalPages==0}">
							<a href="javascript:;">下一页</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:;" onclick="goPageAction(${page + 1});">下一页
							</a>
						</c:otherwise>
					</c:choose>
					<a href="javascript:;" onclick="goPageAction(${totalPages});">末页</a>
					&nbsp;总共&nbsp; ${totalPages} &nbsp;页
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;去第<input type="text"
						id="goPageId" style="width: 50px;" value="${page}" />页<a
						href="javascript:;"
						onclick="goMyPage($('#goPageId').val(),${totalPages});">Go</a>
				</div>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
	function goPageAction(page){
		window.location.href="${ctx}/active/activeList.do?pageNo="+page+"&activeTitle=${activeTitle}&searchBeginDate=${searchBeginDate}&searchEndDate=${searchEndDate}";
	}
	function goMyPage(page,totalPage){
		if(page>0 && page<=totalPage){
			window.location.href="${ctx}/active/activeList.do?pageNo="+page+"&activeTitle=${activeTitle}&searchBeginDate=${searchBeginDate}&searchEndDate=${searchEndDate}";
		}else{
			alert("请输入有效的整数");
		}
	}
	//全选
	$("#checkall").click(function(){ 
	  $("input[name='id[]']").each(function(){
		  if (this.checked) {
			  this.checked = false;
		  }
		  else {
			  this.checked = true;
		  }
	  });
	})

	//批量删除
	function DelSelect(){
		var Checkbox=false;
		 $("input[name='id[]']").each(function(){
		  if (this.checked==true) {		
			Checkbox=true;	
		  }
		});
		if (Checkbox){
			 layer.confirm('您确认删除选中的商品信息?',{btn:['取消','确认']},function(){
			       layer.closeAll(); 
			       $('#checkall').attr("checked",false);
				   $("[name ='id[]']:checkbox").attr("checked", false); 
			    },function(){	
			var checkedList = new Array(); 
			$("input[name='id[]']:checked").each(function() { 
			checkedList.push($(this).val()); 
			}); 
			//执行ajax
			$.ajax({ 
				type: "POST", 
				url: "${ctx}/goods/deleteGoods.do", 
				data: {'goodsIds':checkedList.toString()},
				success: function(result) { 		 
				 window.location.reload(); 
				},
				error:function(result){
					alert('批量删除失败');
				}
				}); 
			})
		}else{
			layer.msg('请选择您要删除的商品!',{time:3000,icon:0});
			return false;
		}
	}

	function deleteone(cid){
		 layer.confirm('您确认删除该条商品信息?',{btn:['取消','确认']},function(){
		       layer.closeAll();  
		    },function(){	
			 $.post('${ctx}/goods/deleteGoods.do',{'goodsIds':cid},function(data){
				  if(data){
					      setTimeout(function(){
					    		  window.location.reload();	  
					      },400);
				  }else{
						layer.msg('删除商品失败',{time:2500,icon:0});

				  }
			 });
		})
	}
</script>

</html>