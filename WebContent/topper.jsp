<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="css/topper.css" />
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<!-- 完成异步搜索 -->
<script type="text/javascript">
			function overFn(obj) {
				$(obj).css("background", "#DBEAF9");		
			}
			function outFn(obj) {
				$(obj).css("background", "#fff");
			}
		
			function clickFn(obj) {
				$("#search").val($(obj).html());
				$("#showDiv").css("display", "none");
			}
		
			function searchWord(obj) {
				//1、获得输入框的输入的内容
				var word = $(obj).val();
				//2、根据输入框的内容去数据库中模糊查询---List<Product>
				var content = "";
				$
						.post(
								"${pageContext.request.contextPath}/product?method=searchProduct",
								{
									"word" : word
								},
								function(data) {
									//3、将返回的商品的名称 现在showDiv中		
									if (data.length > 0) {
										for (var i = 0; i < data.length; i++) {
											content += "<div style='padding:5px;cursor:pointer' onclick='clickFn(this)' onmouseover='overFn(this)' onmouseout='outFn(this)'>"
													+ data[i] + "</div>";
										}
										$("#showDiv").html(content);
										$("#showDiv").css("display", "block");
									}
		
								}, "json");
		
			}
		</script>
</head>
<body>
	<div id="topper">
		<div id="top_1">
			<span style="color: gold; font-size: 40px;"> 基于WEB的网上家居用品店设计 </span>
		</div>
		<div id="top_2">
			<ul>
				<c:if test="${empty memberinfo }">
					<li style="color: gray; font-size: 20px;"><a href="login.jsp">login</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</c:if>
				<c:if test="${!empty memberinfo }">
					<li style="color: red">欢迎您，${memberinfo.memUserName }</li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<li style="color: red"><a href="${pageContext.request.contextPath }/user?method=logout">退出</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<li style="color: gray; font-size: 20px;"><a href="cart.jsp">mall</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<li><a href="${pageContext.request.contextPath }/product?method=myOrders">订单</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<li><a href="admin/login.jsp">后台登录</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</ul>
		</div>
		<div id="top_3">
			<div style="float: left;">
				<li style="font-size: 25px; color: white;"><a href="index.jsp">主页</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div style="float: left;">
				<ul id="sort" style="font-size: 25px; color: white;"></ul>
			</div>
			
			<form class="navbar-form navbar-right" role="search" >
				<div class="form-group"
					style="position: relative;" >
					<input id="search" type="text" class="form-control"
						placeholder="Search" onkeyup="searchWord(this)">
					<div id="showDiv"
						style="display: none; position: absolute; z-index: 1000; background: #fff; width: 179px; border: 1px solid #ccc;">

					</div>
				</div>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
			//header.jsp加载完毕后 去服务器端获得所有的category数据
			$(function(){
				var content = "";
				$.post(
					"${pageContext.request.contextPath}/product?method=productSortList",
					function(data){
						//[{"cid":"xxx","cname":"xxxx"},{},{}]
						//动态创建<li><a href="#">${category.cname }</a></li>
						for(var i=0;i<data.length;i++){
							content+="<li><a href='${pageContext.request.contextPath}/product?method=productList&proSid="+data[i].proSid+"'>"+data[i].proSName+"</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						
						//将拼接好的li放置到ul中
						$("#sort").html(content);
					},
					"json"
				);
			});
		</script>
</body>
</html>
