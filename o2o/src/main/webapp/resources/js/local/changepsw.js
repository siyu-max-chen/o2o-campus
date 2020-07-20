$(function() {
	// 修改平台密码的controller url
	var url = '/o2o/local/changelocalpwd';
	// 从地址栏的URL里获取usertype
	// usertype=1则为customer,其余为shopowner
	var usertype = getQueryString('usertype');
	var msgDuration = 1000;
	
	// 登录验证的controller url
	var statusUrl = '/o2o/local/checkuserstatus';
	// **************	用户状态登出系统	*****************
	// 如果用户已登录, 则无法进入该界面!
	$.getJSON(statusUrl, function(data) {
		if (data.success && data.user) {
			// User have been logged in
			console.log(data.user);
		} else {
			Toast('You should sign in your account!', msgDuration);
			window.history.back(-1); 
			return;
		}
	});
	
	// 根据 profile 来获取 localauth 的信息
	var localAuthUrl = '/o2o/local/checklocalauth';
	$.getJSON(localAuthUrl, function(data) {
		if (data.success && data.localAuth) {
			// User have been logged in
			var username = data.localAuth.username;
			$("#userName").val(username);
			
			console.log(data.localAuth);
		} else {
			Toast('Something went wrong!', msgDuration);
			window.history.back(-1); 
			return;
		}
	});
	
	
	$('#submit').click(function() {
		// 获取帐号
		var userName = $('#userName').val();
		// 获取原密码
		var password = $('#password').val();
		// 获取新密码
		var newPassword = $('#newPassword').val();
		var confirmPassword = $('#confirmPassword').val();
		if (newPassword != confirmPassword) {
			$.toast('两次输入的新密码不一致！');
			return;
		}
		// 添加表单数据
		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		// 获取验证码
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		// 将参数post到后台去修改密码
		console.log(formData.get("password"));
		console.log(formData.get("confirmPassword"));
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					if (usertype == 1) {
						// 若用户在前端展示系统页面则自动退回到前端展示系统首页
						 window.location.href = '/o2o/frontend/index';
					} else {
						// 若用户是在店家管理系统页面则自动回退到店铺列表页中
						 window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast('提交失败！' + data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/o2o/shopadmin/shoplist';
	});
});