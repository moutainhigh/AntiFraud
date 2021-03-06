<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>风控反欺诈系统</title>

<%@ include file="/riskManagement/templates/headerMenu.jsp"%>
<script src="/riskManagement/lib/echarts.simple.min.js"></script>
<div style="padding: 15px;">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>
			数据更新时间：<span id="date">Loading……</span>
		</legend>
	</fieldset>
	<div class="layui-fluid">
		<div class="layui-row">
			<div class="layui-col-sm3">
				<div>
					<span class="housing">Loading……</span>
				</div>
				<div>统计房贷总数</div>
			</div>
			<div class="layui-col-sm3">
				<div>
					<span class="car">Loading……</span>
				</div>
				<div>统计车贷总数</div>
			</div>
			<div class="layui-col-sm3">
				<div>
					<span class="credit">Loading……</span>
				</div>
				<div>统计信贷总数</div>
			</div>
			<div class="layui-col-sm3">
				<div>
					<span class="all">Loading……</span>
				</div>
				<div>总数量</div>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-col-sm3">
				<div>
					<span class="housing_money">Loading……</span>
				</div>
				<div>统计房贷金额</div>
			</div>
			<div class="layui-col-sm3">
				<div>
					<span class="car_money">Loading……</span>
				</div>
				<div>统计车贷金额</div>
			</div>
			<div class="layui-col-sm3">
				<div>
					<span class="credit_money">Loading……</span>
				</div>
				<div>统计信贷金额</div>
			</div>
			<div class="layui-col-sm3">
				<div>
					<span class="all_money">Loading……</span>
				</div>
				<div>总金额</div>
			</div>
		</div>
		<div class="layui-btn-group">
			<button class="layui-btn" id="test1">2019</button>
			<button class="layui-btn" id="test2">2019-1</button>
			<button class="layui-btn" id="test3">2019-1-23</button>
		</div>
		<div class="layui-row" style="margin-top: 50px">
			<div class="layui-col-lg6">
				<div id="count" style="width: 600px; height: 400px;"></div>
			</div>
			<div class="layui-col-lg6">
				<div id="money" style="width: 600px; height: 400px;"></div>
			</div>
		</div>
	</div>
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend> 操作日志 </legend>
	</fieldset>
	<div style="text-align: center;" class="margin-top-15">
		<table id="realEstateMortgage" lay-filter="realEstateMortgage"></table>
	</div>
</div>

<script src="index.js"></script>

<%@ include file="/riskManagement/templates/footer.jsp"%>
