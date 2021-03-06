<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>打印合同</title>

<%@ include file="/riskManagement/templates/headerMenu.jsp"%>
<div style="padding: 15px;">
	<form class="layui-form uploadContract" action="" lay-filter="example">
		<div class="layui-form-item">
			<div class="layui-input-inline">
				<select name="p_id" required lay-verify="required" lay-filter="upload">
					<option value="all">全部</option>
					<option value="yes">已上传</option>
					<option value="no">未上传</option>
				</select>
			</div>
		</div>
	</form>
	<div style="text-align: center;" class="margin-top-15">
		<table id="realEstateMortgage" lay-filter="realEstateMortgage"></table>
	</div>
</div>

<script src="uploadContract.js"></script>

<script>
	$(document).ready(function() {
		uploadContract();
	})
</script>
<script type="text/html" id="operation">
    <a class="layui-btn layui-btn-xs" lay-event="see">查看</a>
</script>
<%@ include file="/riskManagement/templates/footer.jsp"%>
