<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>基于Web的网上家居用品店设计</title>
		<link rel="stylesheet" href="css/Login.css" />
	</head>
	<body>
		<div id="father">
		<div id="top">
			<!-- 引入topper.jsp文件 -->
			<jsp:include page="/topper.jsp"></jsp:include>

		</div>
	
			<div id="lan_top">
				<span style="color: red; font: '楷体'; font-size: 30px;">
					欢迎使用基于Web的网上家居用品店系统 指导老师 易德成
				</span>
			</div>
			<div id="main">
				<form action="${pageContext.request.contextPath }/user?method=login" method="post">
					<div id="main_top">
						<span style="font-size: 25px;">登录(Login)</span>
					</div>
					<div class="input">
						<input type="text"  id="user" name="memusername" size="80px" placeholder="请输入用户名" style="height: 35px; width: 300px;"/>
					</div>
					<div class="input">
						<input type="password" id="password" name="mempassword" size="200px" placeholder="请输入密码" style="height: 30px; width: 300px;"/>
					</div>
					<div class="input" >
						<input type="submit" name="login" id="login" value="登录" style="height: 40px; width: 300px; background-color:hotpink; font-size: 25px ; letter-spacing: 30px;"/>
					</div>
					<div id="main_bottom_left">
						<span style="font-size: 20px;">忘了密码?</span>
					</div>
					<div id="main_bottom_right">
						<a href="register.jsp"><span style="font-size: 20px; color:black ">注册</span></a>
					</div>
				</form>
			</div>
			<div id="lan_bottom">
				<span style="color: red; font: '楷体'; font-size: 30px;">
					湖南工业大学2019计算机本科毕业设计  2019年6月
				</span>
			</div>
		</div>
	</body>
</html>
