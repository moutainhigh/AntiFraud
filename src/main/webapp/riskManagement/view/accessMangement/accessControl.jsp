<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>权限分配</title>

<%@ include file="/riskManagement/templates/headerMenu.jsp"%>
<div style="padding: 15px; width: 666px;" class="access-control">
	<div class="breadcrumb">
		<img src="/riskManagement/images/accessControl.png">
		<div class="word">
			<span class="primary">权限分配</span>
		</div>
	</div>
	<form class="layui-form form-main" action="" lay-filter="formTest">
		<div class="layui-form-item role-view">
			<label class="layui-form-label">角色查看：</label>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">业务报单：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1002" title="房屋抵押" lay-skin="primary"> <input type="checkbox" name="pid1003" title="车辆抵押" lay-skin="primary"> <input type="checkbox" name="pid1004" title="信用贷款" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">资产评估：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1006" title="房屋抵押" lay-skin="primary"> <input type="checkbox" name="pid1007" title="车辆抵押" lay-skin="primary"> <input type="checkbox" name="pid1008" title="信用贷款" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">业务反馈：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1019" title="房屋抵押" lay-skin="primary"> <input type="checkbox" name="pid1020" title="车辆抵押" lay-skin="primary"> <input type="checkbox" name="pid1021" title="信用贷款" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">补充手续：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1032" title="房屋抵押" lay-skin="primary"> <input type="checkbox" name="pid1033" title="车辆抵押" lay-skin="primary"> <input type="checkbox" name="pid1034" title="信用贷款" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">面审实地：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1035" title="房屋抵押" lay-skin="primary"> <input type="checkbox" name="pid1036" title="车辆抵押" lay-skin="primary"> <input type="checkbox" name="pid1037" title="信用贷款" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">打印合同：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1025" title="房屋抵押" lay-skin="primary"> <input type="checkbox" name="pid1026" title="车辆抵押" lay-skin="primary"> <input type="checkbox" name="pid1027" title="信用抵押" lay-skin="primary"> <input type="checkbox" name="pid1028" title="上传合同" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">档案管理：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1024" title="合同审核" lay-skin="primary"> <input type="checkbox" name="pid1022" title="档案归档" lay-skin="primary"> <input type="checkbox" name="pid1023" title="档案借阅" lay-skin="primary">
			</div>
		</div>

		<div class="layui-form-item access-form">
			<label class="layui-form-label">上标管理：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1015" title="查看列表" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">贷后管理：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1016" title="查看列表" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">清收管理：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1029" title="房屋抵押" lay-skin="primary"> <input type="checkbox" name="pid1030" title="车辆抵押" lay-skin="primary"> <input type="checkbox" name="pid1031" title="信用抵押" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">权限管理：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1018" title="查看列表" lay-skin="primary">
			</div>
		</div>
		<div class="layui-form-item access-form">
			<label class="layui-form-label">U盾管理：</label>
			<div class="layui-input-block">
				<input type="checkbox" name="pid1038" title="U盾管理" lay-skin="primary">
			</div>
		</div>

		<div class="layui-form-item btn-item margin-top-50">
			<div class="layui-input-block page-but margin-left-150">
				<button type="button" class="layui-btn confirm-but layui-btn-normal" lay-submit lay-filter="control">确认</button>
				<button type="button" class="layui-btn layui-btn-primary cancel-but" onclick="back()">取消</button>
			</div>
		</div>
	</form>
</div>


<script src="accessMangement.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		accessControl();
	})
</script>

<%@ include file="/riskManagement/templates/footer.jsp"%>
