
$(function() {
	var registerUrl = '/o2o/local/createnewuser';
	// 从地址栏的URL里获取usertype
	// usertype=1则为customer,其余为shopowner
	var usertype = getQueryString('usertype');
	// 登录次数，累积登录三次失败之后自动弹出验证码要求输入
	var loginCount = 0;
	var msgDuration = 1000;
	
	
	// 登录验证的controller url
	var statusUrl = '/o2o/local/checkuserstatus';
	// **************	用户状态登出系统	*****************
	// 如果用户已登录, 则无法进入该界面!
	$.getJSON(statusUrl, function(data) {
		if (data.success && data.user) {
			// User have been logged in
			Toast('Cannot sigh up a new account！ You have already logged in!', msgDuration);
			window.history.back(-1); 
			return;
		}
	});
	
	
	$('#submit').click(function() {				
		// 获取输入的帐号
		var userName = $('#username').val();
		// 获取输入的邮箱
		var email = $('#email').val();
		// 获取输入的密码
		var password = $('#psw').val();
		// 验证的密码
		var passwordConfirm = $('#confirmPassword').val();
		// 获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认为false,即不需要
		var needVerify = false;
		// 如果登录三次都失败
		
		if (password != passwordConfirm) {
			Toast('Please input the same password！', msgDuration);
			return;
		}
		
		if (loginCount >= 10) {
			// 那么就需要验证码校验了
			if (!verifyCodeActual) {
				Toast("Please input verification code!", msgDuration);	// $.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}

		// 访问后台进行登录验证
		$.ajax({
			url : registerUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				email: email,
				verifyCodeActual : verifyCodeActual,
				//是否需要做验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					Toast("Success to sign up!", msgDuration);	// $.toast('登录成功！');
					window.location.href = '/o2o/frontend/index';
				} else {
					Toast("Fail to register! " + data.errMsg, msgDuration);	// $.toast('Fail to Log In!' + data.errMsg);
					loginCount++;
					if (loginCount >= 3) {
						// 登录失败三次，需要做验证码校验
						$('#verifyPart').show();
					}
				}
			}
		});
	});
});

var addRippleEffectSubmitUtil = function(e) {
	var touch = e.touches[0]; //获取第一个触点
	var x = Number(touch.pageX); //页面触点X坐标
	var y = Number(touch.pageY); //页面触点Y坐标
	
	console.log(x + ' ' + y);
	var target = e.target;
	var rect = target.getBoundingClientRect();
	var ripple = target.querySelector('.ripple');
	if (!ripple) {
		ripple = document.createElement('span');
		ripple.className = 'ripple'
			ripple.style.height = ripple.style.width = Math.max(rect.width * 2, rect.height * 2) + 'px'
			target.appendChild(ripple);
	}
	ripple.classList.remove('show');
	var top = y - rect.top - ripple.offsetHeight / 2 - document.documentElement.scrollTop;
	var left = x - rect.left - ripple.offsetWidth / 2 - document.documentElement.scrollLeft;
	ripple.style.top = top + 'px';
	ripple.style.left = left + 'px';
	
	console.log(top + " " + left);
	
	ripple.classList.add('show');
	return false;
}

window.onload = function () {
	document.querySelector('#submit').addEventListener("touchstart", addRippleEffectSubmitUtil, false);
};



