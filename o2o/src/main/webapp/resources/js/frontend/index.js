var categoriesCount = 0;
var categoriesSize = 0;
var headLineSize = 0;
var currHeadLineIndex = 0;
var headLineIdPrefix = "id-headlines-container-card";
var headLineImgIdPrefix = "id-headlines-img-id";
var headLineTextIdPrefix = "id-headlines-text-id";
var headLineTitleIdPrefix = "id-headlines-text-title";
var headLineSubtitleIdPrefix = "id-headlines-text-subtitle";

var bestShopListIdPrefix = "id-best-stores-card-";

var imgUrlArray = new Array();
var headLineTitleArray = new Array();
var headLineSubtitleArray = new Array();
var headLineLinkArray = new Array();
var boolValid = true;

var currShopListIndex = 0;
var bestShopListSize = 0;

function getCorrectTarget(target) {
	var className = target.className;
	
	if(target.className === "button-icon-index" || target.className === "text-icon-desc") {
		var partialId = target.id.substring(target.id.length - 1);
		var containerIdQuery = "#id-cate-button-div" + partialId;
		var targetNew = document.querySelector(containerIdQuery);
		if(targetNew) {
			target = targetNew;
		}
	}
	
	if(className === "image" || className === "class-headlines-text-title" || className === "class-headlines-text-subtitle" || className === "content") {
		var prefixLength = className === "image" ? headLineImgIdPrefix.length: (className === "class-headlines-text-title" ? headLineTitleIdPrefix.length: headLineSubtitleIdPrefix.length);
		if(className === "content") {
			prefixLength = headLineTextIdPrefix.length;
		}
		var partialId = target.id.substring(prefixLength);
		var containerIdQuery = "#" + headLineIdPrefix + partialId;
		var targetNew = document.querySelector(containerIdQuery);
		console.log(targetNew);
		if(targetNew) {
			target = targetNew;
		}
	}
	
	return target;
}

var addRippleEffect = function(e) {
	if(userManipulateCount > 0) return;
	
	console.log(e);
	// var target = e.target;
	var touch = e.touches[0]; //获取第一个触点
	var x = Number(touch.pageX); //页面触点X坐标
	var y = Number(touch.pageY); //页面触点Y坐标

	// var target = document.querySelector('.four.wide.column');
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

function getStyleDisplay(e) {
	var result = "display:none;";
	if(e == 0) {
		result = "display:blocked;";
	}
	return result;
}

function getStrInput(e) {
	return '"#id-best-stores-card-' + e + '"';
}

function renderBestShopList(bestShopList) {
	var shopPageUrl = '/o2o/frontend/shopdetail?shopId=';
	var htmlUrl = '';
	var prefixId = 'id-best-stores-card-';
	var count = 0;
	var shopIdArray = new Array();
	
	console.log(bestShopList);
	
	bestShopList.map(function(item, index) {
		// var url = shopPageUrl + item;		
		
		console.log(item + " " + count);
		
		htmlUrl += '<div class="ui card class-best-stores-card" id="' + prefixId + count  + '" style=' + getStyleDisplay(count) + '>' + 
		'<div class="image">' + '<img src="' +  getContextPath() + item.shopImg + '" class="class-headlines-image"></div>' + 
		'<div class="content" id=""><div class="class-headlines-text-title" id="">' + item.shopName + '</div>' + 
		'<div class="description" style="font-weight: 600; font-size:90%;">' +  item.shopCategory.shopCategoryName + ' · ' + getDollarString(item.dollar) + '</div>' + 
		'<div class="class-headlines-text-subtitle" id="">' + getSubstringWithLimit(item.shopDesc, 85) + '</div></div>' + 
		'<div class="extra content"><span class="left floated"><i class="like icon"></i>' +  item.likes  + ' Like</span> <span class="right floated"><i class="star icon"></i>' +
		'Favorite</span></div></div>';
		
		htmlUrl += '<script>' +  '$(' +  getStrInput(count) + ').click(function(){ window.location.href = "/o2o/frontend/shopdetail?shopId=' + item.shopId  +'"; }); </script>'
		
		if(count == bestShopList.length - 1) {
			htmlUrl += '<div><div id="id-headlines-button-set" class="class-headlines-button-set">' + 
			'<button class="circular ui large icon button class-headlines-button class-headlines-button" id="id-best-shop-list-button-prev">' + 
			'<i class="icon angle left"></i></button>' + 
			'<button class="circular ui large icon button class-headlines-button class-headlines-button right floated" id="id-best-shop-list-button-next">' + 
			'<i class="icon angle right"></i></button></div></div>';
			
			$('#id-best-shop-list-container').html(htmlUrl);
			
			var theNextButton = $('#id-best-shop-list-button-next'), thePrevButton = $('#id-best-shop-list-button-prev');
			
			theNextButton.click(function(e) {
				if(!boolValid || userManipulateCount > 0) return;
				var prevId = currShopListIndex;
				var nextId = (currShopListIndex + 1) >= bestShopList.length? 0: (currShopListIndex + 1);
				slideHelper2(prevId, nextId, 1);
			});

			thePrevButton.click(function(e) {
				if(!boolValid || userManipulateCount > 0) return;
				var prevId = currShopListIndex;
				var nextId = (currShopListIndex - 1) < 0? (bestShopList.length - 1): (currShopListIndex - 1);
				slideHelper2(prevId, nextId, 1);
			});
			
		}
		count++;		
		bestShopListSize++;
	});
	
}

$(function() {
	// 定义访问后台，获取头条列表以及一级类别列表的URL
	var url = '/o2o/frontend/listmainpageinfo';
	
	console.log("Starting to load file from SQL...");
	// 访问后台，获取头条列表以及一级类别列表
	$.getJSON(url, function(data) {
		if (data.success) {
			// 获取后台传递过来的大类列表
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml = '';
			// 遍历大类列表，拼接出俩俩 (col-50) 一行的类别
			var idx = 0;

			shopCategoryList.map(function(item, index) {
				categoryHtml += '<div class="four wide column index-catagory-button-container shop-classify" id = "id-cate-button-div' + idx +  '"' + 'data-category=' + item.shopCategoryId + '>' +
				'<div class="button-icon-index" id="id-button-icon-index' + idx + '">' + '<img src="' + getContextPath() + item.shopCategoryImg + '" class="index-category-icon">' +
				'<div class="text-icon-desc" id="id-text-icon-desc' + idx + '">' + item.shopCategoryName + '</div></div></div>';
				categoriesSize++;
				idx++;
			});

			// 将拼接好的类别赋值给前端HTML控件进行展示
			$('.row').html(categoryHtml);

			// Add touch effect for Categories Button!!!
			for(var i = 0; i < categoriesSize; i++) {
				document.querySelector("#id-cate-button-div" + i).addEventListener("touchstart", addRippleEffect, false);
			}

			// 获取后台传递过来的头条列表
			var headLineList = data.headLineList;
			var swiperHtml = '';
			var counter = 0;
			// 遍历头条列表，并拼接出轮播图组
			
			headLineList.map(function(item, index) {
				var imgUrl = getContextPath() + item.lineImg;
				// if(counter == 0) {
				swiperHtml += '<div class="ui card class-headlines-card" id="id-headlines-container-card' + counter + '" style=' + getStyleDisplay(counter) + '>' + 
				'<div class="image" id="' + (headLineImgIdPrefix + counter) + '" style="border-radius:0px!important;" >' +
				'<img src="' + getContextPath() + item.lineImg +'" id="id-headlines-image" class="class-headlines-image"></div>' + 
				'<div class="content class-headlines-content" id="' + (headLineTextIdPrefix + counter) + '" style="border-radius:0px!important;" >' + 
				'<div class="class-headlines-text-title" id="id-headlines-text-title' + counter + '">' + item.lineName + '</div>' + 
				'<div class="class-headlines-text-subtitle" id="id-headlines-text-subtitle' + counter + '">Hello World</div></div></div>';
				// }

				imgUrlArray.push(getContextPath() + item.lineImg);
				headLineTitleArray.push(item.lineName);
				headLineSubtitleArray.push(item.lineName);				
				headLineLinkArray.push("#");
				headLineSize += 1;
				counter++;
			});

			swiperHtml += 
				'<div><div id="id-headlines-button-set" class="class-headlines-button-set">' + 
				'<button class="circular ui large icon button class-headlines-button" id="id-headlines-prev-button"><i class="icon angle left"></i></button>' +
				'<button class="circular ui large icon button class-headlines-button right floated" id="id-headlines-next-button"><i class="icon angle right"></i></button>' + 
				'</div></div>';

			$('#id-headlines-container-outter').html(swiperHtml);

			console.log("Data should be uploaded...");

			var theNextButton = $('#id-headlines-next-button'), thePrevButton = $('#id-headlines-prev-button');

			theNextButton.click(function(e) {
				if(!boolValid || userManipulateCount > 0) return;
				var prevId = currHeadLineIndex;
				var nextId = (currHeadLineIndex + 1) >= headLineSize? 0: (currHeadLineIndex + 1);
				slideHelper2(prevId, nextId, 0);
			});

			thePrevButton.click(function(e) {
				if(!boolValid || userManipulateCount > 0) return;
				var prevId = currHeadLineIndex;
				var nextId = (currHeadLineIndex - 1) < 0? (headLineSize - 1): (currHeadLineIndex - 1);
				slideHelper2(prevId, nextId, 0);
			});

			// Add touch effect for Headlines Button
			console.log(headLineSize);
			for(var i = 0; i < headLineSize; i++) {
				document.querySelector("#" + headLineIdPrefix + i).addEventListener("touchstart", addRippleEffect, false);
			}
			
			renderBestShopList(data.bestShopList);
		}
	});
	
	
	$('.row').on('click', '.shop-classify', function(e) {
		if(userManipulateCount > 0) return;
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
		window.location.href = newUrl;
	});
	
});

function slideHelper2(prevId, nextId, type) {
	var prefix = '';
	if(type == 0) {
		prefix = headLineIdPrefix;
	} else if(type == 1) {
		prefix = bestShopListIdPrefix;
	}
	
	var prevDiv = $("#" + prefix + prevId);
	var nextDiv = $("#" + prefix + nextId);
	prevDiv.hide();
	nextDiv.show();
	boolValid = false;
	console.log(boolValid);
	
	nextDiv
	  .transition({
	    animation  : 'pulse',
	    duration   : '0.3s',
	    onComplete : function() {
	      boolValid = true;
		  console.log(boolValid);
	    }
	  })
	;
	if(type == 0) {
		currHeadLineIndex = nextId;
	} else if(type == 1) {
		currShopListIndex = nextId;
	}
}


window.onload = function () {
    var box = document.querySelector('.button');
        
    // document.querySelector("#id-footer-o2o").addEventListener("touchstart", addRippleEffect, false);
    
//    box.addEventListener("touchstart", function (e) {
//        console.log('start');
//        console.log(e);
//        box.style.backgroundColor="rgba(180, 180, 180, 0.5)";
//    });
    
//    if(box && false) {
//    	box.addEventListener("touchstart", addRippleEffect, false);
//    	
//	    box.addEventListener("touchmove", function (e) {
//	        // console.log('move');
//	        // console.log(e);
//	    });
//	    
//	    box.addEventListener("touchend", function (e) {
//	        console.log('end');
//	        console.log(e);
//	        box.style.backgroundColor="";
//	    });
//	    
//	    box.onclick = function (e) {
//	        console.log(e);
//	        console.log('click');
//	    }
//    }
}