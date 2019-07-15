<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>基于Web的网上家居用品店设计</title>
<link rel="stylesheet" href="css/bedroom.css" />
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<style type="text/css">
	body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}
</style>
</head>
<body>
	<div id="father">
		<!-- 引入topper.jsp文件 -->
		<jsp:include page="/topper.jsp"></jsp:include>
		<div id="top">
			<c:if test="${proSid eq 'b3923d5f-7376-4a86-bc27-2525fd5325a7' }">
				<img src="image/bedroom1.jpg" />
			</c:if>
			<c:if test="${proSid eq '20c3f1a2-1cd9-4dca-b785-08b1fcc1ea9f' }">
				<img src="image/diningroom.jpg" />
			</c:if>
			<c:if test="${proSid eq '4375e724-e8a5-4677-aadd-2a86760c4a9c' }">
				<img src="image/bathroom.jpg" />
			</c:if>
			<c:if test="${proSid eq 'bdc32866-6d5f-47fe-9ef8-e95093c4bb95' }">
				<img src="image/accessories.jpg" />
			</c:if>
			<c:if test="${proSid eq '8da0774f-5a02-4e4d-80f6-a40ede764dea' }">
				<img src="image/livingroom.jpg" />
			</c:if>
		</div>
		<div id="bed1">
			<div class="bed1_small">
				<span style="color: grey;"> SPRING & SUMMER & 2019 </span><br /> <span
					style="color: grey;"> 2019春夏新品 </span>
			</div>
			<div class="bed1_small">
				<span style="color: grey;"> SPRING & SUMMER & 2019 </span><br /> <span
					style="color: grey;"> 2019春夏新品 </span>
			</div>
			<div class="bed1_small">
				<span style="color: grey;"> SPRING & SUMMER & 2019 </span><br /> <span
					style="color: grey;"> 2019春夏新品 </span>
			</div>
			<div class="bed1_small">
				<span style="color: grey;"> SPRING & SUMMER & 2019 </span><br /> <span
					style="color: grey;"> 2019春夏新品 </span>
			</div>
		</div>
		<div class="product">
			<c:forEach items="${pageBean.list }" var="product">
				<div class="smallproduct" style="height: 250px">
					<a href="${pageContext.request.contextPath }/product?method=productInfo&proId=${product.proId}"> <img
						src="${pageContext.request.contextPath }/${product.proImage}"
						width="170" height="170" style="display: inline-block;">
					</a>
					<p>
						<a href="${pageContext.request.contextPath }/product?method=productInfo&proId=${product.proId}" style='color: green'>${product.proName }</a>
					</p>
					<p>
						<font color="#FF0000">商城价：&yen;${product.proPrice }</font>
					</p>
				</div>
			</c:forEach>
		</div>

		<!-- 分页 -->
		<div id="page" style="width: 380px; margin: 0 auto; margin-top: 50px;">
			<ul class="pagination" style="text-align: center; margin-top: 10px;">
				<!-- 上一页 -->
				<!-- 判断当前页是否是第一页 -->
				<c:if test="${pageBean.currentPage==1 }">
					<li class="disabled">
						<a href="javascript:void(0);" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pageBean.currentPage!=1 }">
					<li>
						<a href="${pageContext.request.contextPath }/product?method=productList&proSid=${proSid}&currentPage=${pageBean.currentPage-1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>	
			
				<c:forEach begin="1" end="${pageBean.totalPage }" var="page">
					<!-- 判断当前页 -->
					<c:if test="${pageBean.currentPage==page }">
						<li class="active"><a href="javascript:void(0);">${page }</a></li>
					</c:if>
					<c:if test="${pageBean.currentPage!=page}">
						<li><a href="${pageContext.request.contextPath}/product?method=productList&proSid=${proSid}&currentPage=${page }">${page }</a></li>
					</c:if>
				</c:forEach>
				
				<!-- 判断当前页是否是最后一页 -->
				<c:if test="${pageBean.currentPage==pageBean.totalPage }">
					<li class="disabled"><a href="javascript:void(0);"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
				<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
					<li><a
						href="${pageContext.request.contextPath }/product?method=productList&proSid=${proSid}&currentPage=${pageBean.currentPage+1}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<!-- 引入footer.jsp -->
		<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>