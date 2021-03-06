// 激活二级导航
$(document).ready(function() {
	navActive(8);
	secondNavActive('#managementAfterLoan dd', 0)
});

function managementAfterLoan() {
	layui.use([ 'table', 'form' ], function() {
		var table = layui.table, form = layui.form;

		// 第一个实例
		table.render({
			id : 'testReload',
			elem : '#realEstateMortgage',
			url : '/AcceptController/Accept.action',
			page : {
				theme : '#405467'
			},
			even : true,
			skin : 'line', // 行边框风格
			response : {
				statusCode : 1
			// 规定成功的状态码，默认：0
			},
			cols : [ [ // 表头

			{
				field : 'bidId',
				title : '标号 bisiid'
			}, {
				field : 'entry_number',
				title : '报单编号'
			}, {
				field : 'realName',
				title : '姓名'
			}, {
				field : 'mobile',
				title : '电话'
			}, {
				field : 'period',
				title : '当前期数'
			}, {
				field : 'total_period',
				title : '总期数'
			}, {
				field : 'amount',
				title : '还款金额'
			}, {
				field : 'status',
				title : '是否逾期',
				templet : function(d) {
					if (d.is_overdue == '0') {
						return "<span class='pass'>未逾期</span>"
					} else if (d.is_overdue == '1') {
						return "<span class='failed'>已逾期</span>"
					}
				}
			}, {
				field : 'repayment_time',
				title : '还款时间',
				templet : function(d) {
					return timeStamp2String(d.repayment_time);
				}
			} ] ]
		});

		// 电话搜索
		var active = {
			reload : function() {
				// 执行重载
				table.reload('testReload', {
					url : '/AcceptController/findByPhone.action',
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						phone : $('#demoReload').val()
					}
				});
			}
		};
		// 搜索

		form.on('select(upload)', function(data) {

			if (data.value == '0') {
				// 执行重载
				table.reload('testReload', {
					url : '/AcceptController/findLoanOverdue.action?is_overdue=' + data.value,
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					done : function() {
					}
				});
			} else if (data.value == '1') {
				// 执行重载
				table.reload('testReload', {
					url : '/AcceptController/findLoanOverdue.action?is_overdue=' + data.value,
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					done : function() {
					}
				});
			} else if (data.value == '2') {
				// 执行重载
				table.reload('testReload', {
					url : '/AcceptController/Accept.action',
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					done : function() {
					}
				});
			}

		});

		$('#demoReload').on('input', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
	})

}
