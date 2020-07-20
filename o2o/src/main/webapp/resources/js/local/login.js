$(function() {
	// 登录验证的controller url
	var loginUrl = '/o2o/local/logincheck';
	
	// 从地址栏的URL里获取usertype
	// usertype=1则为customer,其余为shopowner
	var usertype = getQueryString('usertype');
	// 登录次数，累积登录三次失败之后自动弹出验证码要求输入
	var loginCount = 0;
	
	var msgDuration = 1000;
	
	function Toast(msg, duration) {
	    duration = isNaN(duration) ? 500 : duration;
	    var m = document.createElement('div');
	    m.innerHTML = msg; //width:34%
	    m.style.cssText = "width: 50%; font-weight: 800; min-width: 90px;height: auto;color: #fff; opacity:0.8; line-height: auto;text-align: center;border-radius: 5px;padding:10px;position: fixed;top: 40%;left: 25%;z-index: 999999;background: #696969;font-size: 14px;";
	    document.body.appendChild(m);
	    setTimeout(function () {
	        var d = 0.5;
	        m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
	        m.style.opacity = '0';
	        setTimeout(function () {
	            document.body.removeChild(m)
	        }, d * 1000);
	    }, duration);
	}
	
	$('#submit').click(function() {				
		// 获取输入的帐号
		var userName = $('#username').val();
		// 获取输入的密码
		var password = $('#psw').val();
		// 获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认为false,即不需要
		var needVerify = false;
		// 如果登录三次都失败
		if (loginCount >= 3) {
			// 那么就需要验证码校验了
			if (!verifyCodeActual) {
				Toast("Please input verification code!", msgDuration);	// $.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		if(emailModeState == 1) {
			loginUrl = '/o2o/local/logincheckemail';
		}
		
		// 访问后台进行登录验证
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				//是否需要做验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					Toast("Success to log in!", msgDuration);	// $.toast('登录成功！');
					if (usertype == 1) {
						// 若用户在前端展示系统页面则自动链接到前端展示系统首页
						window.location.href = '/o2o/frontend/index';
					} else {
						// 若用户是在店家管理系统页面则自动链接到店铺列表页中
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					Toast("Fail to log in! " + data.errMsg, msgDuration);	// $.toast('Fail to Log In!' + data.errMsg);
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


// ***********************	是否开启邮箱登陆模式	*********************
var emailModeState = 0;
$('#global-id-emailmode-button').click(function(e) {
	var thisButton = $('#global-id-emailmode-button');
	
	var showIcon = $('#global-id-emailmode-icon-show');
	var hideIcon = $('#global-id-emailmode-icon-hide');
	var formName = $('#username');
	var formInput = $('#psw');
	
	if(emailModeState === 0) {
		showIcon.hide();
		hideIcon.show();
		
		var nextUrl = '<i class="user circle icon"></i> Email';
		$('#id-username-title').html(nextUrl);
		formName.attr("type", "email");
		formName.attr("placeholder", "Email Address");
		
		thisButton.css({"color":"rgb(255, 255, 255)", "background-color":"rgba(5, 5, 5, .9)"});
		emailModeState = 1;
	} else {
		showIcon.show();
		hideIcon.hide();
		
		var nextUrl = '<i class="user circle icon"></i> User Name';
		$('#id-username-title').html(nextUrl);
		formName.attr("type", "text");
		formName.attr("placeholder", "User Name");
		
		thisButton.css({"color":"rgba(0, 0, 0, .6)", "background-color":"#e0e1e2"});
		emailModeState = 0;
	}
	
	e.preventDefault();
});

