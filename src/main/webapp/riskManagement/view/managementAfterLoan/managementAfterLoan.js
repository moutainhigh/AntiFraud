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
			url : '/AcceptController/findnewInfo.action',
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
			}, {
				field : 'operation',
				title : '操作',
				toolbar : '#operation'
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
						mobile : $('#demoReload').val()
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
					url : '/AcceptController/findnewInfo.action',
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

		// 监听行工具事件
		table.on('tool(realEstateMortgage)', function(obj) {
			var data = obj.data;
			if (obj.event == 'see') {
				window.location.href = "managementInfo.jsp?entry_number=" + data.entry_number;
			}
		});

	})

}
function managementInfo() {

	layui.use([ 'table' ], function() {
		var table = layui.table;
		// 监听单元格编辑
		table.on('edit(realEstateMortgage)', function(obj) {
			$.ajax({
				url : '/AcceptController/updateRemaker.action',
				type : 'post',
				dataType : 'json',
				data:{
					id: obj.data.id,
					remaker: obj.value
				},
				success:function(res){
					if(res.code==1){
						layerClose('修改成功')
					}else {
						layerClose('修改失败')
					}
				}
			})
		});
		var entry_number = getHrefParam('entry_number');
		$.ajax({
			url : '/AcceptController/SelectHistory.action',
			type : 'post',
			dataType : 'json',
			data : {
				entry_number : entry_number
			},
			success : function(result) {
				console.log(result)
				var tableIns = table.render({
					id : 'testReload',
					data : result.data, // 行边框风格
					elem : '#realEstateMortgage',
					response : {
						statusCode : 1
					// 规定成功的状态码，默认：0
					},
					even : true,
					skin : 'line', // 行边框风格
					cols : [ [ // 表头
					{
						field : 'period',
						title : '期数'
					}, {
						field : 'repayment_time',
						title : '还款时间',
						templet : function(d) {
							return timeStamp2String(d.repayment_time)
						}
						
					}, {
						field : 'remaker',
						title : '备注',
						edit : 'text'
					} ] ]
				})
			}
		});
	})

}