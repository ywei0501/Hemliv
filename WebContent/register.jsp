<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" name="viewport"
	content="width=device-width, initial-scale=1" />
<title>基于Web的网上家居用品店设计</title>
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/regist.css" />

<style type="text/css">
.error{
	color:red
}
</style>
<script type="text/javascript">
	/*
		$(function(){
			//为输入框绑定事件
			$("#memUserName").blur(function(){
				//1.失去焦点获得输入框的内容
				var memUsernameInput = $(this).val();
				//2.去服务端校验是否存在用户名----------ajax
				$.post(
						"${pageContext.request.contextPath}/checkMemUsername",
						{"memUsername":memUsernameInput},
						function(data){
							var isExist = data.isExist;
							//3、根据返回的isExist动态的显示信息
							var usernameInfo = "";
							if(isExist){
								//该用户存在
								usernameInfo = "该用户名已经存在";
								$("#usernameInfo").css("color","red");
							}else{
								usernameInfo = "该用户可以使用"
								$("#usernameInfo").css("color","green");
							}
							$("#usernameInfo").html(usernameInfo);
							
						},
						"json"
					);
			});
		});
	
	 */

	//自定义校验规则
	$.validator.addMethod(
	//规则的名称
	"checkMemUsername",
	//校验的函数
	function(value, element, params) {

		//定义一个标志
		var flag = false;

		//value:输入的内容
		//element:被校验的元素对象
		//params：规则对应的参数值
		//目的：对输入的username进行ajax校验
		$.ajax({
			"async" : false,
			"url" : "${pageContext.request.contextPath}/user?method=checkMemUserName",
			"data" : {
			"memUserName" : value
			},
			"type" : "POST",
			"dataType" : "json",
			"success" : function(data) {
				flag = data.isExist;
			}
		});

		//返回false代表该校验器不通过
		return !flag;
	}

	);

	$(function() {
		
		$("#myform").validate({
			rules : {
				"memUserName" : {
					"required" : true,
					"checkMemUsername" : true
				},
				"memPassword" : {
					"required" : true,
					"rangelength" : [ 6, 12 ]
				},
				"repassword" : {
					"required" : true,
					"rangelength" : [ 6, 12 ],
					"equalTo" : "#memPassword"
				},
				"memEmail" : {
					"required" : true,
					"email" : true
				},
				"memSex" : {
					"required" : true
				}
			},
			messages : {
				"memUserName" : {
					"required" : "用户名不能为空",
					"checkMemUsername" : "用户名已存在"
				},
				"memPassword" : {
					"required" : "密码不能为空",
					"rangelength" : "密码长度6-12位"
				},
				"repassword" : {
					"required" : "密码不能为空",
					"rangelength" : "密码长度6-12位",
					"equalTo" : "两次密码不一致"
				},
				"memEmail" : {
					"required" : "邮箱不能为空",
					"email" : "邮箱格式不正确"
				}
			}
		});
	});
	
	
	
</script>
</head>
<body>
	<div id="father">
		<div id="top_left">
			<span style="font-size: 40px;">HEMLIV HOME</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
				style="font-size: 35px;">注册</span>
		</div>
		<div id="top_right">
			<span style="font-size: 15px;">已有帐户</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="login.jsp"><span
				style="font-size: 25px;">登录</span></a>
		</div>
		<div id="main">
			<form action="${pageContext.request.contextPath }/user?method=register" method="post" id="myform"
				name="regform" onsubmit="return checkform()">
				<div class="input">
					<input style="height: 35px;color: gray;" type="text" id="memUserName"
						name="memUserName" size="40" placeholder="请输入用户名"><span
						id="usernameInfo"></span>
				</div>
				<div class="input">
					<input style="height: 35px;color: gray;" type="password" id="memPassword"
						name=memPassword size="40" placeholder="请输入密码"></span>
				</div>
				<div class="input">
					<input style="height: 35px;color: gray;" type="password" id="repassword"
						name="repassword" size="40" placeholder="请再次输入密码"><span
						id="repasswordspan"></span>
				</div>
				<div class="input">
					<label><input type="radio" id="memSex" name="memSex"value="male" />男</label> 
					<label><input type="radio" id="memSex"name="memSex" value="female" />女</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label class="error" for="memSex" style="display:none ">请选择性别</label>
				</div>
				<div class="input">
					<input style="height: 35px; color: gray;" type="text" id="memEmail"
						name="memEmail" size="40" placeholder="请输入邮箱")"><span
						id="email"></span>
				</div>
				<div class="input">
					<input style="height: 35px;" type="text" id="memMoblieP"
						name="memMoblieP" size="40" placeholder="请输入手机号")"><span
						id="mobilephone"></span>
				</div>
				<div class="input">
					<input style="height: 40px; width: 100px;" type="submit"
						name="submit" value="注册" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>
