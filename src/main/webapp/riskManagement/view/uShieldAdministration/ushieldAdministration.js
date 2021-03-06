//激活垂直导航栏
$(document).ready(function() {
	navActive(11);
	secondNavActive('#accessMangements dd', 0)
});
function creditEvaluation() {
	layui.use([ 'table' ], function() {
		var table = layui.table;

		// 第一个实例
		table.render({
			id : 'testReload',
			page : {theme : '#405467'},
			even : true,
			skin : 'line', // 行边框风格
			response : { statusCode : 1 },// 规定成功的状态码，默认：0
			elem : '#realEstateMortgage',
			url : '/Ukey/selectUkeyAll.action',
			cols : [ [ // 表头
			{
				field : 'ukey_sn',
				title : '编号'
			}, {
				field : 'ukey_user',
				title : '姓名'
			}, {
				field : 'ukey_id',
				title : '加密锁ID'
			}, {
				field : 'ukey_ckey',
				title : '密钥'
			}, {
				field : 'ukey_stat',
				title : '状态',
				templet : function(d) {
					console.log(d)
					if (d.ukey_stat == '0') {
						return "<span class='failed'>正常</span>"
					} else if (d.ukey_stat == '1') {
						return "<span class='pass'>锁定</span>"
					} 
				}
			}, {
				field : 'operation',
				title : '操作',
				toolbar : '#operation'
				
			}, ] ]
		});

		// 搜索

		var active = {
			reload : function() {
				// 执行重载
				table.reload('testReload', {
					url : '/Ukey/selectByUkeySn.action',
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						ukeySn : $('#demoReload').val(),
						
					},
					done : function() {
					}
				});
			}
		};
	
		$('#demoReload').on('input', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

		// 监听行工具事件
		table.on('tool(realEstateMortgage)', function(obj) {
			var data = obj.data;
			var tr = obj.tr;
			console.log("tr::"+tr.find("a").html());
			if (obj.event == 'edit') {
				$.ajax({
					url : '/Ukey/updateUkeyStat.action',
					type : 'post',
					dataType : 'json',
					data : {"id": data.id, "ukey_stat":data.ukey_stat},
					success : function(obj) {
						var data = obj.data;
						console.log("success:"+JSON.stringify(obj));
						
						// 墨绿深蓝风
						layerMsgPath(obj.msg, 'ushieldAdministration.jsp', '');
					}
				})
			} else if (obj.event == 'modify') {
				window.location.href = 'ushieldAdministrationUppdate.jsp?id=' + data.id;
			} 
		});

	});

}
// 添加U盾
function accessMangementAdd() {
	layui.use([ 'form' ], function() {
		var form = layui.form;

		// 监听提交
		form.on('submit(accessAdd)', function(data) {
				$.ajax({
					url : '/Ukey/insertUkey.action',
					type : 'post',
					dataType : 'json',
					data : data.field,
					success : function(obj) {
						// 墨绿深蓝风
						layerMsgPath(obj.msg, 'ushieldAdministration.jsp', '')
					}
				})
		})
	})
}
// 修改U盾执行后台方法
function submitAjax(data) {
	$.ajax({
		url : '/Ukey/updateUkey.action',
		type : 'post',
		dataType : 'json',
		data : data.field,
		success : function(obj) {
			layerMsgPath(obj.msg, 'ushieldAdministration.jsp', '')
		}
	})
}
// 设置U盾增强密钥
function accessMangementRevamp() {
	onLoadPageRevamp('id');
	layui.use([ 'form' ], function() {
		var form = layui.form;
		// 监听提交

		form.on('submit(accessRevamp)', function(data) {
			set_ckey_user(data);

			return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
		})

	})
}
//页面加载执行
function onLoadPageRevamp(name) {
	var id = getHrefParam(name);
	$.ajax({
		type : 'POST',
		url : '/Ukey/selectUkeyById.action?id=' + id,
		dataType : 'json',
		success : function(result) {
			var res = result.data;
			layui.use([ 'form' ], function() {
				var form = layui.form;
				// 表单初始赋值
				form.val('ukeyInfo', {
					"id": res.id,
					"ukey_sn" : res.ukey_sn,
					"ukey_ckey" : res.ukey_ckey,
					"ukey_user" : res.ukey_user
				})
			})
		}
	});
}

// 以下为U盾操作
var bConnect = 0;
function load() {

	// 如果是IE10及以下浏览器，则跳过不处理
	if (navigator.userAgent.indexOf("MSIE") > 0 && !navigator.userAgent.indexOf("opera") > -1)
		return;
	try {
		var s_pnp = new SoftKey3W();
		s_pnp.Socket_UK.onopen = function() {
			bConnect = 1;// 代表已经连接，用于判断是否安装了客户端服务
		}

		// 在使用事件插拨时，注意，一定不要关掉Sockey，否则无法监测事件插拨
		s_pnp.Socket_UK.onmessage = function got_packet(Msg) {
			var PnpData = JSON.parse(Msg.data);

			if (PnpData.type == "PnpEvent")// 如果是插拨事件处理消息
			{
				if (PnpData.IsIn) {
					layer.msg("UKEY已被插入，被插入的锁的路径是：" + PnpData.DevicePath, function() {
						console.log("插入" + PnpData.IsIn);
					})
				} else {
					layer.msg("UKEY已被拨出，被拨出的锁的路径是：" + PnpData.DevicePath, function() {
						console.log(PnpData.IsIn);
					})
				}
			}
		}

		s_pnp.Socket_UK.onclose = function() {

		}
	} catch (e) {
		layer.msg(e.name + ": " + e.message, function() {
		})
		return false;
	}
}

var digitArray = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');

function toHex(n) {

	var result = ''
	var start = true;

	for (var i = 32; i > 0;) {
		i -= 4;
		var digit = (n >> i) & 0xf;

		if (!start || digit != 0) {
			start = false;
			result += digitArray[digit];
		}
	}

	return (result == '' ? '0' : result);
}

function login_onclick() {

	// 如果是IE10及以下浏览器，则使用AVCTIVEX控件的方式
	if (navigator.userAgent.indexOf("MSIE") > 0 && !navigator.userAgent.indexOf("opera") > -1)
		return Handle_IE10();

	// 判断是否安装了服务程序，如果没有安装提示用户安装
	if (bConnect == 0) {
		layer.alert("请下载安装安全云盾控件<a href='/riskManagement/lib/SetUp.exe'> [下载控件]</a>", {
			skin : 'layui-layer-lan' // 样式类名
			,
			closeBtn : 0
		});
		return false;
	}

	var DevicePath, ret, n, mylen, ID_1, ID_2, addr;
	try {

		// 由于是使用事件消息的方式与服务程序进行通讯，
		// 好处是不用安装插件，不分系统及版本，控件也不会被拦截，同时安装服务程序后，可以立即使用，不用重启浏览器
		// 不好的地方，就是但写代码会复杂一些
		var s_simnew1 = new SoftKey3W(); // 创建UK类

		s_simnew1.Socket_UK.onopen = function() {
			s_simnew1.ResetOrder();// 这里调用ResetOrder将计数清零，这样，消息处理处就会收到0序号的消息，通过计数及序号的方式，从而生产流程
		}

		// 写代码时一定要注意，每调用我们的一个UKEY函数，就会生产一个计数，即增加一个序号，较好的逻辑是一个序号的消息处理中，只调用我们一个UKEY的函数
		s_simnew1.Socket_UK.onmessage = function got_packet(Msg) {
			var UK_Data = JSON.parse(Msg.data);
			if (UK_Data.type != "Process")
				return;// 如果不是流程处理消息，则跳过
			switch (UK_Data.order) {
				case 0: {
					s_simnew1.FindPort(0);// 发送命令取UK的路径
				}
					break;// !!!!!重要提示，如果在调试中，发现代码不对，一定要注意，是不是少了break,这个少了是很常见的错误
				case 1: {
					if (UK_Data.LastError != 0) {
						layer.msg('未发现加密锁，请插入加密锁', function() {
						});
						s_simnew1.Socket_UK.close();
						return false;
					}
					DevicePath = UK_Data.return_value;// 获得返回的UK的路径
					s_simnew1.GetID_1(DevicePath); // 发送命令取ID_1
				}
					break;
				case 2: {
					if (UK_Data.LastError != 0) {
						layer.msg("返回ID号错误，错误码为：" + UK_Data.LastError.toString(), function() {
						});
						s_simnew1.Socket_UK.close();
						return false;
					}
					ID_1 = UK_Data.return_value;// 获得返回的UK的ID_1
					s_simnew1.GetID_2(DevicePath); // 发送命令取ID_2
				}
					break;
				case 3: {
					if (UK_Data.LastError != 0) {
						layer.msg("取得ID错误，错误码为：" + UK_Data.LastError.toString(), function() {
						});
						s_simnew1.Socket_UK.close();
						return false;
					}
					ID_2 = UK_Data.return_value;// 获得返回的UK的ID_2
	
					//frmlogin.ukey_id.value = toHex(ID_1) + toHex(ID_2);
					$("#ukey_id").val(toHex(ID_1) + toHex(ID_2));
					s_simnew1.ContinueOrder();// 为了方便阅读，这里调用了一句继续下一行的计算的命令，因为在这个消息中没有调用我们的函数，所以要调用这个
				}
					break;
			
			}
		}
		s_simnew1.Socket_UK.onclose = function() {

		}
		return true;
	} catch (e) {
		layer.msg(e.name + ": " + e.message, function() {
		});
	}

}

function Handle_IE10() {
	try {
		var DevicePath, mylen, ret;
		var s_simnew1;
		// 创建控件

		s_simnew1 = new ActiveXObject("Syunew3A.s_simnew3");

		DevicePath = s_simnew1.FindPort(0);// '来查找加密锁，0是指查找默认端口的锁
		if (s_simnew1.LastError != 0) {
			layer.msg("未发现加密锁，请插入加密锁", function() {
			});
			return false;
		} else {
			// '读取锁的ID
			frmlogin.KeyID.value = toHex(s_simnew1.GetID_1(DevicePath)) + toHex(s_simnew1.GetID_2(DevicePath));
			if (s_simnew1.LastError != 0) {
				layer.msg("获取用户名错误,错误码是" + s_simnew1.LastError.toString(), function() {
				});
				return false;
			}
			// 获取设置在锁中的用户名
			// 先从地址0读取字符串的长度,使用默认的读密码"FFFFFFFF","FFFFFFFF"
			ret = s_simnew1.YReadEx(0, 1, "ffffffff", "ffffffff", DevicePath);
			mylen = s_simnew1.GetBuf(0);
			// 再从地址1读取相应的长度的字符串，,使用默认的读密码"FFFFFFFF","FFFFFFFF"
			frmlogin.ukey_user.value = s_simnew1.YReadString(1, mylen, "ffffffff", "ffffffff", DevicePath);
			if (s_simnew1.LastError != 0) {
				layer.msg("Err to GetUserName,ErrCode is:" + s_simnew1.LastError.toString(), function() {
				});
				return false;
			}

			// 获到设置在锁中的用户密码,
			// 先从地址20读取字符串的长度,使用默认的读密码"FFFFFFFF","FFFFFFFF"
			ret = s_simnew1.YReadEx(20, 1, "ffffffff", "ffffffff", DevicePath);
			mylen = s_simnew1.GetBuf(0);
			// 再从地址21读取相应的长度的字符串，,使用默认的读密码"FFFFFFFF","FFFFFFFF"
			// frmlogin.Password.value = s_simnew1.YReadString(21, mylen,
			// "ffffffff", "ffffffff", DevicePath);
			if (s_simnew1.LastError != 0) {
				layer.msg("获取用户密码错误,错误码是:" + s_simnew1.LastError.toString(), function() {
				});
				return false;
			}

			// frmlogin.submit();
			submit();
			return true;
		}
	}

	catch (e) {
		layer.msg(e.name + ": " + e.message + "。可能是没有安装相应的控件或插件", function() {
		});
		return false;
	}

}
//向U盾设置增强密钥和用户名
function set_ckey_user(data) {
	
	//如果是IE10及以下浏览器，则使用AVCTIVEX控件的方式
    if (navigator.userAgent.indexOf("MSIE")>0 && !navigator.userAgent.indexOf("opera") > -1) return Handle_IE10_1();
	
	//判断是否安装了服务程序，如果没有安装提示用户安装
	if (bConnect==0) {
		layer.alert("请下载安装安全云盾控件<a href='/riskManagement/lib/SetUp.exe'> [下载控件]</a>", {
			skin : 'layui-layer-lan' // 样式类名
			,
			closeBtn : 0
		});
		return false;
	}
  
  	/*if (frmlogin.ukey_user.value == "" || frmlogin.ukey_ckey.value == "") {
  		window.alert ( "请输入用户名密钥后才进行操作。");return 0;
  	}*/
	
  	var DevicePath,ret,n,mylen,addr, keyid;
  	
  	try {
  		
  		//由于是使用事件消息的方式与服务程序进行通讯，
	    //好处是不用安装插件，不分系统及版本，控件也不会被拦截，同时安装服务程序后，可以立即使用，不用重启浏览器
	    //不好的地方，就是但写代码会复杂一些
		var s_simnew1=new SoftKey3W(); //创建UK类
		
	    s_simnew1.Socket_UK.onopen = function() {
	   	   s_simnew1.ResetOrder();//这里调用ResetOrder将计数清零，这样，消息处理处就会收到0序号的消息，通过计数及序号的方式，从而生产流程
	    }
  		
		//写代码时一定要注意，每调用我们的一个UKEY函数，就会生产一个计数，即增加一个序号，较好的逻辑是一个序号的消息处理中，只调用我们一个UKEY的函数
		 s_simnew1.Socket_UK.onmessage =function got_packet(Msg) {
			 var UK_Data = JSON.parse(Msg.data);
		     // alert(Msg.data);
		     if(UK_Data.type!="Process")return ;//如果不是流程处理消息，则跳过
		     
		     switch(UK_Data.order) {
		     case 0: {
                    s_simnew1.FindPort(0);//发送命令取UK的路径
                }
             	break;//!!!!!重要提示，如果在调试中，发现代码不对，一定要注意，是不是少了break,这个少了是很常见的错误
	         case 1:
                {
                    if( UK_Data.LastError!=0){window.alert ( "未发现加密锁，请插入加密锁");s_simnew1.Socket_UK.close();return false;} 
                    DevicePath=UK_Data.return_value;//获得返回的UK的路径
                    
			         s_simnew1.GetID_1(DevicePath); //'读取锁的ID
			    }
			    break;
			case 2:
			    {	
			        if( UK_Data.LastError!=0){window.alert("读取锁的ID时错误，错误码为："+UK_Data.LastError.toString());s_simnew1.Socket_UK.close();return false;}
			        keyid=toHex(UK_Data.return_value);
			        s_simnew1.GetID_2(DevicePath); //'读取锁的ID
			    }
			    break;
			case 3:
			    {	
			        if( UK_Data.LastError!=0){window.alert("读取锁的ID时错误，错误码为："+UK_Data.LastError.toString());s_simnew1.Socket_UK.close();return false;}
			        keyid=keyid+toHex(UK_Data.return_value);
			        //$('#ukeyId').val(keyid);
			      //写用户名
	                   //写入用户名到地址1，使用默认的写密码"FFFFFFFF","FFFFFFFF"
	                   addr=1;
	                   s_simnew1.YWriteString(frmlogin.ukey_user.value, addr, "FFFFFFFF", "FFFFFFFF", DevicePath);
			    }
			    break;	
                
                
             case 4: {
                if( UK_Data.LastError!=0){ window.alert("写入用户名失败。错误码为："+UK_Data.LastError.toString());s_simnew1.Socket_UK.close();return false;} 
                mylen = UK_Data.return_value;
                
                s_simnew1.SetBuf( mylen, 0);//设置要字符串的长度到缓冲区中，
                }
                break;
             case 5: {
                if( UK_Data.LastError!=0){ window.alert("调用SetBuf时错误，错误码为："+UK_Data.LastError.toString());s_simnew1.Socket_UK.close();return false;} 
                
                //写入缓冲区的数据即用户名的字符串长度到地址0，使用默认的写密码"FFFFFFFF","FFFFFFFF"
                addr=0;
                s_simnew1.YWriteEx(addr, 1, "FFFFFFFF", "FFFFFFFF",DevicePath);
                }
                break;
             case 6: {
            	 
                if( UK_Data.LastError!=0){ window.alert("写入用户名长度失败。错误码为："+UK_Data.LastError.toString());s_simnew1.Socket_UK.close();return false;} 
                //设置增强算法一密钥
                //注意：密钥为不超过32个的0-F字符，例如：1234567890ABCDEF1234567890ABCDEF,不足32个字符的，系统会自动在后面补0
                 s_simnew1.SetCal_2(frmlogin.ukey_ckey.value, DevicePath);        
                }
                break;
			case 7: {
			     if( UK_Data.LastError!=0){ window.alert("设置增强算法密钥错误："+UK_Data.LastError.toString());s_simnew1.Socket_UK.close();return false;} 
			     alert("已成功设置了增强算法密钥和用户名");
			     submitAjax(data);
			 }
			 break;
			 
		     }
		     
		}
		
		 s_simnew1.Socket_UK.onclose = function(){

	     }
		 return true;

  	} catch(e) {
  		alert(e.name + ": " + e.message);
		return false;
  	}
  	
}
