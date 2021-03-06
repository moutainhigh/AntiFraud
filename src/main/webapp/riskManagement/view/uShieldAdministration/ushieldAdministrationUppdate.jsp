<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>U盾管理</title>

<%@ include file="/riskManagement/templates/headerMenu.jsp"%>
<body onload="load()">
<div style="padding: 15px;">
	<div class="breadcrumb">
		<img src="/riskManagement/images/accessControl.png">
		<div class="word">
			<span class="primary">U盾管理</span> <span class="triangle"></span> <span class="secondary">修改</span>
		</div>
	</div>

	<form class="layui-form form-main form-main-4" action="" style="margin-top: 20px;" lay-filter="ukeyInfo" name="frmlogin">
		<input type="hidden" name="id">
		<div class="layui-form-item">
			<label class="layui-form-label">锁编号</label>
			<div class="layui-input-inline">
				<input type="text" name="ukey_sn" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">密钥</label>
			<div class="layui-input-inline">
				<input type="text" name="ukey_ckey" class="layui-input" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item" style="width:470px">
			<label class="layui-form-label">账号</label>
			<div class="layui-input-inline">
				<input type="text" name="ukey_user" lay-verify="required" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-form-mid layui-word-aux">输入2-10位账户</div>
		</div>

		<div class="layui-form-item btn-item margin-top-50">
			<div class="layui-input-block page-but margin-left-150"><!-- lay-filter="accessRevamp" -->
				<button type="button" class="layui-btn confirm-but layui-btn-normal" lay-submit lay-filter="accessRevamp">确认</button>
				<button type="button" class="layui-btn layui-btn-primary cancel-but" onclick="back()">取消</button>
			</div>
		</div>
	</form>
</div>
</body>
<script src="ushieldAdministration.js"></script>

<script>
	$(document).ready(function() {
		accessMangementRevamp();
	})
</script>

<%@ include file="/riskManagement/templates/footer.jsp"%>