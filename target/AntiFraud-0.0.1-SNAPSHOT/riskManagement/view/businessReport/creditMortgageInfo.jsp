<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>业务报单</title>

<%@ include file="/riskManagement/templates/headerMenu.jsp"%>
<div style="padding: 15px;">
	<div class="breadcrumb">
		<img src="/riskManagement/images/businessReportInfo.png">
		<div class="word">
			<span class="primary">业务报单</span> <span class="triangle"></span> <span class="secondary">信用贷款</span>
		</div>
	</div>
	<form class="form-main layui-form form-main-4" lay-filter="example">
		<input type="hidden" name="id">
		<fieldset class="layui-elem-field layui-field-title">
			<legend>借款人身份信息</legend>
		</fieldset>

		<div class="layui-form-item">
			<label class="layui-form-label">报单编号</label>
			<div class="layui-input-inline">
				<input type="text" name="entry_number" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>抵押人姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="name" autocomplete="off" class="layui-input" lay-verify="required" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>身份证号</label>
			<div class="layui-input-inline">
				<input type="text" name="id_number" autocomplete="off" class="layui-input" lay-verify="required" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>抵押人性别</label>
			<div class="layui-input-inline">
				<select name="gender" lay-verify="required" disabled>
					<option value="">请选择</option>
					<option value="1">男</option>
					<option value="2">女</option>
					<option value="0">未知</option>
				</select>
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>抵押人年龄</label>
			<div class="layui-input-inline">
				<input type="text" name="age" autocomplete="off" class="layui-input" disabled lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>婚姻状况</label>
			<div class="layui-input-inline">
				<select name="marital_status" lay-verify="required" disabled>
					<option value="">请选择</option>
					<option value="10">未婚</option>
					<option value="20">已婚</option>
					<option value="30">丧偶</option>
					<option value="40">离婚</option>
					<option value="90">未知</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>最高学历</label>
			<div class="layui-input-inline">
				<select name="education" lay-verify="required" disabled>
					<option value="">请选择</option>
					<option value="10">研究生</option>
					<option value="20">本科</option>
					<option value="30">大专</option>
					<option value="40">中专</option>
					<option value="50">技校</option>
					<option value="60">高中</option>
					<option value="70">初中</option>
					<option value="80">小学</option>
					<option value="90">文盲</option>
					<option value="其它">未知</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>最高学位</label>
			<div class="layui-input-inline">
				<select name="diploma" lay-verify="required" disabled>
					<option value="">请选择</option>
					<option value="0">其它</option>
					<option value="1">名誉博士</option>
					<option value="2">博士</option>
					<option value="3">硕士</option>
					<option value="4">学士</option>
					<option value="9">未知</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号码</label>
			<div class="layui-input-inline">
				<input type="text" name="phone" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">住宅电话</label>
			<div class="layui-input-inline">
				<input type="text" name="home_phone" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">单位电话</label>
			<div class="layui-input-inline">
				<input type="text" name="business_phone_number" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>通讯地址</label>
			<div class="layui-input-inline">
				<input type="text" name="home_address" autocomplete="off" class="layui-input" lay-verify="required" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>通讯地址邮编</label>
			<div class="layui-input-inline">
				<input type="text" name="mailing_address" autocomplete="off" class="layui-input" lay-verify="required" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">户籍地址</label>
			<div class="layui-input-inline">
				<input type="text" name="permanent_residence_address" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">电子邮箱</label>
			<div class="layui-input-inline">
				<input type="text" name="email" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">配偶姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="spouses_name" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">配偶证件号码</label>
			<div class="layui-input-inline">
				<input type="text" name="spouse_identification_number" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">配偶工作单位</label>
			<div class="layui-input-inline">
				<input type="text" name="spousal_work_unit" autocomplete="off" class="layui-input" disabled> 
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">配偶联系电话</label>
			<div class="layui-input-inline">
				<input type="text" name="spouse_telephone" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>亲属联系人姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="relative_contact_name" autocomplete="off" class="layui-input" lay-verify="required" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>亲属联系人关系</label>
			<div class="layui-input-inline">
				<select name="domestic_relation" lay-verify="required" disabled>
					<option value="">请选择</option>
					<option value="0">父子(女)</option>
					<option value="1">母子(女)</option>
					<option value="2">配偶</option>
					<option value="3">子女</option>
					<option value="4">亲属</option>
					<option value="5">同事</option>
					<option value="6">朋友</option>
					<option value="7">兄弟姐妹</option>
					<option value="8">其他</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>亲属联系人电话</label>
			<div class="layui-input-inline">
				<input type="text" name="relative_contact_number" autocomplete="off" class="layui-input" lay-verify="required" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">紧急联系人姓名</label>
			<div class="layui-input-inline">
				<input type="text" name="emergency_name" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">紧急联系人关系</label>
			<div class="layui-input-inline">
				<select name="emergency_relation" disabled>
					<option value="">请选择</option>
					<option value="0">父子(女)</option>
					<option value="1">母子(女)</option>
					<option value="2">配偶</option>
					<option value="3">子女</option>
					<option value="4">亲属</option>
					<option value="5">同事</option>
					<option value="6">朋友</option>
					<option value="7">兄弟姐妹</option>
					<option value="8">其他</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">紧急联系人电话</label>
			<div class="layui-input-inline">
				<input type="text" name="emergency_phone" autocomplete="off" class="layui-input" disabled>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">申请额度</label>
			<div class="layui-input-inline">
				<input type="text" name="apply_for_limit" autocomplete="off" class="layui-input" disabled><span class="unit">万元</span>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">申请期限</label>
			<div class="layui-input-inline">
				<input type="text" name="apply_for_deadline" autocomplete="off" class="layui-input" disabled><span class="unit">个月</span>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="required">*</span>是否本月开户</label>
			<div class="layui-input-inline">
				<select name="account_opening_time" lay-verify="required" disabled>
					<option value="">请选择</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</div>
		<fieldset class="layui-elem-field layui-field-title">
			<legend>图片信息</legend>
		</fieldset>
		<div class="imgList layui-clear layer-photos-demo" id="layer-photos-demo"></div>
		<!-- <div class="layui-form-item btn-item">
			<div class="layui-input-block page-but margin-top-50 margin-left-150">
				<button type="button" class="layui-btn confirm-but" lay-submit lay-filter="save">保存</button>
				<button type="button" class="layui-btn confirm-but layui-btn-normal" lay-submit lay-filter="sub">提交</button>
				<button type="button" class="layui-btn layui-btn-primary cancel-but" onclick="back()">取消</button>
			</div>
		</div> -->
	</form>
</div>
<script src="creditMortgage.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		creditMortgageInfo();
	})
</script>
<%@ include file="/riskManagement/templates/footer.jsp"%>