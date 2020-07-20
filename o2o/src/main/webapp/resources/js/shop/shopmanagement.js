function getCorrectTarget(target) {
	var className = target.className;
	if(target.className === "button-icon-index" || target.className === "text-icon-desc") {
		var prefixLength = className === "button-icon-index" ? "button-icon-index".length: "text-icon-desc".length;
		var partialId = target.id.substring(prefixLength);
		var containerIdQuery = "#id-manage-div" + partialId;
		
		var targetNew = document.querySelector(containerIdQuery);
		if(targetNew) {
			target = targetNew;
		}
	}
	return target;
}

var addRippleEffect = function(e) {
	var touch = e.touches[0]; //获取第一个触点
	var x = Number(touch.pageX); //页面触点X坐标
	var y = Number(touch.pageY); //页面触点Y坐标

	var target = getCorrectTarget(e.target);

	var rect = target.getBoundingClientRect();

	var ripple = target.querySelector('.ripple');
	if (!ripple) {
		ripple = document.createElement('span');
		ripple.className = 'ripple'
		ripple.style.height = ripple.style.width = Math.max(rect.width * 2, rect.height * 2) + 'px';	
		ripple.style.cssText += "border-radius:100%!imporant;";
		target.appendChild(ripple);
	}
	ripple.classList.remove('show');
	var top = y - rect.top - ripple.offsetHeight / 2 - document.documentElement.scrollTop;
	var left = x - rect.left - ripple.offsetWidth / 2 - document.documentElement.scrollLeft;

	ripple.style.top = top + 'px'
	ripple.style.left = left + 'px'
	ripple.classList.add('show');
	return false;
};

function getShopInfo(shopId){
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	$.getJSON(shopInfoUrl, function(data) {
		if(data.success){
			var shop = data.shop;
			var htmlLine = "";
			var htmlImgLine = "";
			
			htmlImgLine += '<img class="class-shop-image" src="' + "../" + shop.shopImg + '" />';
	
			htmlLine += '<div class="content class-shop-info-name"><div id="id-shop-info-name">' + shop.shopName +
			'</div></div><div class="content">' + '<div id="id-shop-info-category" class="class-shop-info-regular">' +
			'<span class="right floated" id="id-shop-info-create-time"> ' + dateToString(shop.createTime)
			+ '</span>' + '<span class="class-shop-info-subtitle">Category: </span>' + 
			shop.shopCategory.shopCategoryName + '</div>' + 
			'<div id="id-shop-info-area" class="class-shop-info-regular">' + '<span class="class-shop-info-subtitle">Area: </span>'
			+ shop.area.areaName + '</div>' + 
			'<div id="id-shop-info-address" class="class-shop-info-regular">' + 
			'<span class="class-shop-info-subtitle">Address: </span>' + 
			shop.shopAddr + '</div>' +
			'<div id="id-shop-info-tel" class="class-shop-info-regular">' + 
			'<span class="class-shop-info-subtitle">Tel: </span>' + shop.phone + '</div>' + 
			'<div id="id-shop-info-description" class="class-shop-info-regular">' +
			'<span class="class-shop-info-subtitle">Description: </span>' + shop.shopDesc + '</div>' + 
			'</div><div class="extra content"><button class="ui button">Join Project</button></div>';
			
			$('#id-shop-info-card').html(htmlLine);
			$('.class-image-container').html(htmlImgLine);
		}
	});
}

$(function() {
	var shopId = getQueryString('shopId');
	var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
	// Add touch effect for Categories Button!!!
	

//	var theRouterArray = ["/o2o/shopadmin/shopoperation", "/o2o/shopadmin/productmanagement", "/o2o/shopadmin/productcategorymanagement", 
//		"/o2o/shopadmin/awardmanagement", "/o2o/shopadmin/productbuycheck", "/o2o/shopadmin/awarddelivercheck", 
//		"/o2o/shopadmin/usershopcheck", "/o2o/shopadmin/shopauthmanagement"];
//	
//	for(var i = 0; i < manageSize; i++) {
//		var url = theRouterArray[i] + "?shopId=" + shopId;
//		console.log(url);
//		$("#id-manage-div" + i).click(function() {
//			window.location.href = url;
//		});
//    }

	$.getJSON(shopInfoUrl, function(data) {
		if (data.redirect) {
			window.location.href = data.url;
		} else {
			var shopId = "0";
			if(data.shopId != undefined && data.shopId != "") {
				shopId = data.shopId;
			}
			console.log(shopId);
			 $('#id-manage-button0').attr('href', '/o2o/shopadmin/shopoperation?shopId=' + shopId);
			 getShopInfo(shopId);
		}
	});

});

window.onload = function () {
	var manageSize = 8;
	for(var i = 0; i < manageSize; i++) {
    	document.querySelector("#id-manage-div" + i).addEventListener("touchstart", addRippleEffect, false);
    }
}
