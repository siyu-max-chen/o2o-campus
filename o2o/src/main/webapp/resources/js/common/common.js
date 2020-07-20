Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function changeVerifyCode(img){
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null){
		return decodeURIComponent(r[2]);
	}
	return '';
}

function dateToString(dateValue) {
	var date = new Date(dateValue);
	var Y = date.getFullYear() + '-';
	var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	var D = date.getDate() + ' ';
	return Y + M + D;
}

function getSubstringWithLimit(str, limit) {
	if(str.length <= limit) {
		return str;
	}
	return str.substring(0, limit) + "...";
}

function getDollarString(i) {
	var str = '';
	for(var c = 0; c < i; c++) {
		str += '$';
	}
	return str;
}

function getLikesString(i) {
	var str = '';
	if(i < 2) {
		str = i + ' Like';
	} else {
		str = i + ' Likes';
	}
	return str;
}

// 工具类: Toast 函数
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

/**
 * 获取项目的ContextPath以便修正图片路由让其正常显示
 * @returns
 */
function getContextPath(){
	return "/o2o/";
}

// 返回按钮键的通常运行
$('#global-id-go-back-icon').click(function(e) {
	window.history.go(-1);
});


// *************************  密码隐藏模式修改 	**************************
var passwordShowState = 0;
// 针对于更改单一 password field 的实现
$('#global-id-passwordhide-button').click(function(e) {
	var thisButton = $('#global-id-passwordhide-button');
	
	var showIcon = $('#global-id-passwordhide-icon-show');
	var hideIcon = $('#global-id-passwordhide-icon-hide');
	var formInput = $('#psw');
	
	if(passwordShowState === 0) {
		showIcon.hide();
		hideIcon.show();
		formInput.attr("type", "text");
		passwordShowState = 1;
		thisButton.css({"color":"rgb(255, 255, 255)", "background-color":"rgba(5, 5, 5, .9)"});
	} else {
		showIcon.show();
		hideIcon.hide();
		formInput.attr("type", "password");
		passwordShowState = 0;
		thisButton.css({"color":"rgba(0, 0, 0, .6)", "background-color":"#e0e1e2"});
	}
	e.preventDefault();
});

//针对于更改多个 password field 的实现
$('#global-id-passwordhidegroup-button').click(function(e) {
	var thisButton = $('#global-id-passwordhidegroup-button');
	
	var showIcon = $('#global-id-passwordhide-icon-show');
	var hideIcon = $('#global-id-passwordhide-icon-hide');
	
	var formLines = new Array();
	formLines[0] = $('#psw');
	formLines[1] = $('#confirmPassword');
	formLines[2] = $('#newPassword');
	
	if(passwordShowState === 0) {
		showIcon.hide();
		hideIcon.show();
		for(var count = 0; count < formLines.length; count++) {
			if(formLines[count])
				formLines[count].attr("type", "text");
		}
		passwordShowState = 1;
		thisButton.css({"color":"rgb(255, 255, 255)", "background-color":"rgba(5, 5, 5, .9)"});
	} else {
		showIcon.show();
		hideIcon.hide();
		for(var count = 0; count < formLines.length; count++) {
			if(formLines[count])
				formLines[count].attr("type", "password");
		}
		passwordShowState = 0;
		thisButton.css({"color":"rgba(0, 0, 0, .6)", "background-color":"#e0e1e2"});
	}
	e.preventDefault();
});

