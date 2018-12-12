// 激活二级导航
$(document).ready(function() {
	$($('#printingContract dd')[3]).addClass('layui-this');
	$($('.layui-side .layui-nav-item')[4]).addClass('layui-nav-itemed');
});

function uploadContract() {
	layui.use([ 'form', 'table' ], function() {
		var form = layui.form, table = layui.table;

		// 第一个实例
		table.render({
			elem : '#realEstateMortgage',
			url : '/Compact/findAllCompact.action',
			page : {
				theme : '#405467'
			},
			id : 'testReload',
			even : true,
			skin : 'line', // 行边框风格
			parseData : function(res) { // res 即为原始返回的数据
				return {
					"code" : res.code, // 解析接口状态
					"msg" : res.msg, // 解析提示文本
					"count" : res.count, // 解析数据长度
					"data" : res.data
				// 解析数据列表
				};
			},
			response : {
				statusCode : 1
			// 规定成功的状态码，默认：0
			},
			cols : [ [ // 表头
			{
				field : 'report_id',
				title : '报单编号'
			},{
				field : 'contract_id',
				title : '合同编号'
			}, {
				field : 'create_time',
				title : '电话'
			},  {
				field : 'operation',
				title : '操作',
				toolbar : '#operation'
			}, ] ]
		});

		// 搜索

		form.on('select(upload)', function(data) {
			// console.log(data.elem); //得到select原始DOM对象
			console.log(data.value); // 得到被选中的值
			// console.log(data.othis); //得到美化后的DOM对象

			if (data.value == 'all') {
				// 执行重载
				table.reload('testReload', {
					url : '/Compact/findAllCompact.action',
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					done : function() {
						console.log('1完成')
					}
				});
			} else if (data.value == 'yes') {
				// 执行重载
				table.reload('testReload', {
					url : '/Compact/findAllCompactImgIsNotNull.action',
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					done : function() {
						console.log('2完成')
					}
				});
			} else if (data.value == 'no') {
				// 执行重载
				table.reload('testReload', {
					url : '/Compact/findAllCompactImgIsNull.action',
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					done : function() {
						console.log('3完成')
					}
				});
			}

		});
		// 监听行工具事件
		table.on('tool(realEstateMortgage)', function(obj) {
			var data = obj.data;
			console.log(obj);
			reportId = data.report_id;
			if (obj.event == 'see') {
				window.location.href = "uploadContractInfo.jsp?report_id=" + data.report_id;

			}
		});

	})
}

var formData = null;

function onLoadPage(name) {
	console.log('aaa');
	var report_id = getHrefParam(name);
	$.ajax({
		url : '/Compact/findCompactByEntry_number.action',
		type : 'post',
		dataType : 'json',
		data : {
			entry_number : report_id
		},
		async : false,
		success : function(result) {
			console.log(result);
			formData = result;
		}
	});
	console.log(formData);
}
// 获取地址栏参数，name:参数名称
function getHrefParam(key) {
	var s = window.location.href;
	var reg = new RegExp(key + "=\\w+");
	var rs = reg.exec(s);
	if (rs === null || rs === undefined) {
		return "";
	} else {
		return rs[0].split("=")[1];
	}
}
function uploadContractInfo() {
	onLoadPage("report_id");
	console.log(formData);
	layui.use([ 'form', 'upload' ], function() {
		var form = layui.form, upload = layui.upload;
		var lastIndex = formData.data.length;
		// 表单初始赋值
		form.val('example', {
			"report_id" : formData.data[0].report_id, // "name": "value"
			'id' : formData.data[0].id,
			'user_id' : formData.data[0].user_id,
			'contract_id' : formData.data[0].contract_id,
			'create_time' : timeStamp2String(formData.data[0].create_time),
			'update_time' : timeStamp2String(formData.data[lastIndex-1].update_time),
			'remark' : formData.data[0].remark
		})


		// 多文件列表示例
		var demoListView = $('#demoList'), uploadListIns = upload.render({
			elem : '#testList',
			url : '/Compact/upLoadCompact.action',
			accept : 'file',
			multiple : true,
			auto : false,
			bindAction : '',
			data : {
				report_id : function() {
					return $("[name='report_id']").val();
				},
				reamker : function() {
					return $("[name='remark']").val();
				}
			},
			choose : function(obj) {
				var files = this.files = obj.pushFile(); // 将每次选择的文件追加到文件队列
				// 读取本地文件
				obj.preview(function(index, file, result) {
					var tr = $([ '<tr id="upload-' + index + '">', '<td>' + '<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">' + '</td>',
							'<td>' + (file.size / 1014).toFixed(1) + 'kb</td>', '<td>等待上传</td>', '<td>',
							'<button type="button" class="layui-btn layui-btn-xs demo-reload btn">上传</button>',
							'<button type="button" class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>', '</td>', '</tr>' ].join(''));

					// 单个重传
					tr.find('.btn').on('click', function() {
						obj.upload(index, file);
						return false;
					});

					// 删除
					tr.find('.demo-delete').on('click', function() {
						delete files[index]; // 删除对应的文件
						tr.remove();
						uploadListIns.config.elem.next()[0].value = ''; // 清空
						// input
						// file
						// 值，以免删除后出现同名文件不可选
					});

					demoListView.append(tr);
				});
			},
			done : function(res, index, upload) {
				console.log(res);
				if (res.code == 1) { // 上传成功
					var tr = demoListView.find('tr#upload-' + index), tds = tr.children();
					tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
					tds.eq(3).html(''); // 清空操作
					return delete this.files[index]; // 删除文件队列已经上传成功的文件
				}
				this.error(index, upload);
			},
			error : function(index, upload) {
				var tr = demoListView.find('tr#upload-' + index), tds = tr.children();
				tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
				//tds.eq(3).find('.demo-reload').removeClass('layui-hide'); // 显示重传
			}
		});

	});

}
//格式化Date日期时间数据(yyyy-MM-dd hh:mm:ss)
function timeStamp2String(time) {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return year + "-" + month + "-" + date;
}