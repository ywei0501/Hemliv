<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>基于Web的网上家居用品店设计</title>
<link rel="stylesheet" href="css/index.css" />
<script>
			/*加载函数*/
			function init(){
				setInterval("changeImg()",3000);
			}
			/*切换图片函数*/
			var i=0;
			function changeImg(){
				i++; 
				document.getElementById("index_bg1").src="image/index_bg"+i+".jpg";
				if (i==3) {
					i=0;
				}
			}
</script>
</head>
<body>
	<div id="father">
		<!-- 引入topper.jsp文件 -->
		<jsp:include page="/topper.jsp"></jsp:include>
		<!--轮播图-->
		<div id="">
			<img src="image/index_bg1 .jpg" width="100%" id="index_bg1" />
		</div>
		<div id="category_top">
			<span style="font-size: 30px; color:gray;">MORE CATEGORY</span>
		</div>
		<div id="category1">
				<!--嵌入一大四小-->
				<div id="category1_right">
					<img src="image/index1.png" width="100%" height="100%" />
				</div>
				<div class="category1_left">
					<img src="image/index2.jpg" width="100%" height="100%" />
				</div>
				<div class="category1_left">
					<img src="image/index3.jpg" width="100%" height="100%" />
				</div>
				<div class="category1_left">
					<img src="image/index4.jpg" width="100%" height="100%" />
				</div>
				<div class="category1_left">
					<img src="image/index5.jpg" width="100%" height="100%" />
				</div>
			</div>
			<div id="category2">
				<img src="image/index6.jpg" width="100%" height="100%"/>
			</div>
			<div id="empty">
				<span style="color: grey; font-size: 20px;">I LIKE THE SIMPLE LIFE</span><br />
				<span style="color: grey; font-size: 15px;">THERE'S ALWAYS SOME INSPIRATION TO CHANGE YOUR LIFE.YOUR STORY TOLD IN HEMLIV</span> <br />
				<span style="color: grey; font-size: 15px;">总有一些灵感，会改变你一成不变的生活.</span>	
			</div>
			<div id="category3">
				<div id="category3_left">
					<img src="image/index7.jpg" width="100%" height="100%"/>
				</div>
				<div id="category3_right">
					<img src="image/index8.jpg" width="100%" height="100%"/>
				</div>	
			</div>
			<div id="category4">
				<div class="category4_small">
					<img src="image/index9.jpg" width="100%" height="100%" />
				</div>
				<div class="category4_small">
					<img src="image/index10.jpg" width="100%" height="100%" />
				</div>
				<div class="category4_small">
					<img src="image/index11.jpg" width="100%" height="100%" />
				</div>
			</div>
			<div id="category5">
				<img src="image/index12.jpg" width="100%" height="100%"/>
			</div>
			
	</div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>