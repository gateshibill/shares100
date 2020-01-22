<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>组装规格</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />
<link rel="stylesheet" href="${ctx}/css/table.css"/>
<link rel="stylesheet" href="${ctx}/css/layer-search.css"/>
<style type="text/css">
.xh-list li{
	float:left;
	background: #f4f4f4;
	padding:10px 20px;
	margin-right:20px;
}
.xinghao,.guige{
	padding:30px 20px;
}
.one,.two{
	display:none;
}
.gg-list li{
	float:left;
	background: #f4f4f4;
	padding:10px 20px;
	margin-right:20px;
}
</style>
</head>
<body>
<div class="content">
	<div class="xinghao">
	 <h2>型号</h2>
	     <ul class="xh-list">
	     		
	     </ul>
	     <input type="text" id="xh"/><button class="addxh" onclick="addxh()">添加</button>
	</div>	
	<div class="guige">
	<h2>规格</h2>
		 <ul class="gg-list">
	     		
	     </ul>
	     <input type="text" id="gg"/><button class="addgg" onclick="addgg()">添加</button>
	</div>
	<div class="table">
<table class="layui-table">
  <thead>
    <tr class="topth">
      <th class="one">型号</th>
      <th class="two">规格</th>
      <th>库存</th>
      <th>团购价</th>
      <th>单买价</th>
      <th>sku编码</th>
      <th>商品编码</th>
    </tr> 
  </thead>
  <tbody class="tbodydata">
    
  </tbody>
</table>
	</div>
</div>
<script type="text/javascript" src="${ctx}/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
	function addxh(){
		var xh = $('#xh').val();
		if(!xh){
			layer.msg("请输入型号");
			return;
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
		zzTable(xhArr,ggArr);
	}
	
	function addgg(){
		var gg = $('#gg').val();
		if(!gg){
			layer.msg("请输入规格");
			return;
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
			             html1 += "<td  rowspan='"+ggcount+"'>"+xhArr[i]+"</td>";
			             if(ggcount > 0){
			                    html1 += "<td>"+ggArr[j]+"</td>";

			             }else{
			                    html1 += "<td class='two'>"+ggArr[j]+"</td>";
			             }
			                html1 += "<td><input type='text' /></td>";
			                html1 += "<td><input type='text' /></td>";
			                html1 += "<td><input type='text' /></td>"
			                html1 += "<td><input type='text' /></td>";
			                html1 += "<td><input type='text' /></td>";
			                html1 += "</tr>";    
					 }else{
						 html1 += "<tr>";
			                if(ggcount > 0){
			                    html1 += "<td>"+ggArr[j]+"</td>";

			                }else{
			                    html1 += "<td class='two'>"+ggArr[j]+"</td>";
			                }
			                html1 += "<td><input type='text' /></td>";
			                html1 += "<td><input type='text' /></td>";
			                html1 += "<td><input type='text' /></td>"
			                html1 += "<td><input type='text' /></td>";
			                html1 += "<td><input type='text' /></td>";
			                html1 += "</tr>";
					 }
				}
			}else{
				 html1 += "<tr>";
	             html1 += "<td>"+xhArr[i]+"</td>";
	             if(ggcount > 0){
	                    html1 += "<td></td>";

	             }else{
	                    html1 += "<td class='two'></td>";
	             }
	                html1 += "<td><input type='text' /></td>";
	                html1 += "<td><input type='text' /></td>";
	                html1 += "<td><input type='text' /></td>"
	                html1 += "<td><input type='text' /></td>";
	                html1 += "<td><input type='text' /></td>";
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
</script>
</body>
</html>