<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>U盾管理</title>

<%@ include file="/riskManagement/templates/headerMenu.jsp"%>
<script src="/riskManagement/login/Syunew3.js"></script>
<script src="ushieldAdministration.js"></script>
<body onload="load()">
<div style="padding: 15px;">
	<div class="breadcrumb">
		<img src="/riskManagement/images/accessControl.png">
		<div class="word">
			<span class="primary">U盾管理</span> <span class="triangle"></span> <span class="secondary">添加</span>
		</div>
	</div>

	<form class="layui-form form-main form-main-4" action="" style="margin-top: 20px;" lay-filter="example">
		<input type="hidden" name="id">
		<div class="layui-form-item" style="width:470px">
			<label class="layui-form-label">锁编号</label>
			<div class="layui-input-inline">
				<input type="text" name="ukey_sn" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">例如：SN.0001</div>
		</div>
		<div class="layui-form-item" style="width:470px">
			<label class="layui-form-label">锁ID</label>
			<div class="layui-input-inline">
				<input type="text" id="ukey_id" name="ukey_id" class="layui-input" lay-verify="required">
			</div>
			<div class="layui-form-mid layui-word-aux" style="padding:0 !important;">
				<input type="button" name="name" class="layui-input" lay-verify="required" onclick="login_onclick()" value="获取" style="background: #1E9FFF !important;">
			</div>
		</div>
		<div class="layui-form-item btn-item margin-top-50">
			<div class="layui-input-block page-but margin-left-150">
				<button type="button" class="layui-btn confirm-but layui-btn-normal" lay-submit lay-filter="accessAdd">添加</button>
				<button type="button" class="layui-btn layui-btn-primary cancel-but" onclick="back()">取消</button>
			</div>
		</div>
	</form>
</div>
</body>


<script>
	$(document).ready(function() {
		accessMangementAdd();
	})
</script>

<%@ include file="/riskManagement/templates/footer.jsp"%>