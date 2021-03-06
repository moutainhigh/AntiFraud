<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>登录</title>
<!-- 绝对路径 -->
<link rel="icon" href="./riskManagement/login/images/login.jpg" type="image/x-icon" />
<link type="text/css" href="./riskManagement/lib/layui/css/layui.css" rel="stylesheet">
<link type="text/css" href="./riskManagement/login/login.css" rel="stylesheet">
<script src="./riskManagement/lib/layui/layui.js"></script>
<script src="./riskManagement/lib/jquery.min.js"></script>
<script src="./riskManagement/login/login.js"></script>
<script src="./riskManagement/login/Syunew3.js"></script>
<script src="./riskManagement/lib/layui/lay/modules/layer.js"></script>
</head>

<body onload="load()">
	<div class="login-main">
		<form class="layui-form login-form" lay-filter="login-form" name="frmlogin">
			<header class="login-elip">WELCOME</header>
			<div class="layui-input-inline login-input-inline">
				<i class="layui-icon layui-icon-username"></i>
				<input type="text" name="username" id="UserName" lay-verify="required" placeholder="请输入账号" class="layui-input login-input" lay-verType="tips"> 
			</div>
			
			<div class="layui-input-inline login-input-inline">
				<i class="layui-icon layui-icon-password"></i>
				<input type="password" name="password" id="Password" lay-verify="required" placeholder="请输入密码" maxlength="16" autocomplete="new-password" class="layui-input login-input" lay-verType="tips">
			</div>
			<div class="layui-input-inline login-input-inline">
				<!-- <input type="button" class="login-button layui-bg-blue" value="登录" id="loginBtn" lay-submit lay-filter="sub" > -->
				<input type="button" class="login-button layui-bg-blue" value="登录" id="loginBtn" lay-submit lay-filter="sub" > 
			</div>
			<input type="hidden" name="KeyID" id="KeyID" value="">
			<input type="hidden" name="rnd" id="rnd" value="${rnd}">
			<input type="hidden" name="return_EncData" id="return_EncData" value="">
		</form>
	</div>
</body>

</html>
