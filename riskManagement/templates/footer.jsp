<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</div>
</div>
<div class="layui-footer">
	<!-- 底部固定区域 -->
	©2018 讴业普惠 All rights resered <span style="padding-left: 20px;">石家庄云阙网络科技有限公司</span>
</div>
</div>

<script>
	//JavaScript代码区域
	 layui.use([ 'element', 'layer' ], function() {
		var element = layui.element, layer = layui.layer;

		//抖动layer
		var name = $($('.layui-layout-right a')[0]).text();
		$($('.layui-layout-right .layui-nav-item')[0]).click(function() {
			layer.msg('欢迎您！ ' + name, function() {
			})
		})

	}); 
</script>
</body>
</html>
<div class="imgBox">
	<img alt="" src="">
</div>