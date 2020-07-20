/**
 * 1. 从后台获取到 店铺分类 和 区域等信息 填充到前端表格中
 * 2. 从表格中获取 到 提交信息 
 */

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


$(function(){
	var shopId = getQueryString("shopId");
	var isEdit = shopId? true: false;
	
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	var editShopUrl = '/o2o/shopadmin/modifyshop';
	
	if(!isEdit){
		getShopInitInfo();
	} else {
		getShopInfo();
	}
	
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl, function(data) {
			if(data.success){
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				
				// 给店铺类别选定原先的店铺类别值
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				
				// 让 dollar 进行选择
				var dollarId = shop.dollar;
				$("#prices option[data-id='" + dollarId + "']").attr(
						"selected", "selected");
				
				// 初始化区域列表
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				
				// 不允许选择店铺类别
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				
				// 给店铺选定原先的所属的区域
				$("#area option[data-id='" + shop.area.areaId + "']").attr(
						"selected", "selected");
			}
		});
	}
	
	// 从数据库中获取 一些 信息，例如 商铺分类的选择  和 区域的选择 并 填充到 下拉列表中
	function getShopInitInfo(){
		$.getJSON(initUrl, function(data){
			if(data.success){
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}
	
	$('#id-go-back-button').click(function(){
		window.location.href="/o2o/shopadmin/shopmanagement?shopId=" + shopId;
	});
	
	$('#submit').click(function(){
		// 创建 shop 对象
		var shop = {};
		if(isEdit){
			shop.shopId = shopId;
		}
		// 获取表单里的数据并填充进对应的店铺属性中
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// 选择选定好的区域信息
		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		
		shop.dollar = $('#prices').find('option').not(function() {
					return !this.selected;
				}).data('id');
		
		console.log(shop.dollar);

		var shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			Toast("Please Enter the Verification Code!");
			// $.toast('请输入验证码！');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		
		$.ajax({
			url: (isEdit? editShopUrl :registerShopUrl),
			type:'POST',
			data:formData,
			contentType: false,
			processData: false,
			cache: false,
			success: function(data){
				if(data.success){
					Toast("Successed to Submit!");
					// $.toast('提交成功!');
				} else {
					Toast("Failed to Submit! Reason is: " + data.errMsg);
					// $.alert('提交失败!' + data.errMsg);
				}
				$('#captcha_img').click();
			}
		});
	});
})
