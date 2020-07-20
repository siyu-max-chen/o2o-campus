/*
 * 逻辑相对简单，是动态地渲染
 */

$(function() {
	getlist();
	function getlist(e) {
		$.ajax({
			url : "/o2o/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.shopList);
					handleUser(data.user);
				}
			}
		});
	}
	
	function handleUser(data) {
		$('#user-name').text(data.name);
	}
	
	function handleList(data) {
		var html = '';
		data.map(function(item, index) {			
			html += '<div class="ui one cards"><div class="ui card"><div class="content" id="card-title"><h3 class="ui left floated header" id="card-title-text">' + item.shopName +
			'</h3></div><div class="content">' + goShop(item.enableStatus, item.shopId) + '<p>State: ' + '<span class="' + shopStatusClass(item.enableStatus) +'">' + shopStatus(item.enableStatus) +'</span></p><p>Description: ' 
			+ item.shopDesc + '</p><p>Address: ' + item.shopAddr + '</p></div><div class="extra content">' + getHomepageLink(item.enableStatus, item.shopId) + '</div></div></div>';
		});
		$('.shop-wrap').html(html);
	}
	
	function shopStatusClass(status) {
		if (status == 0) {
			return 'text-processing';
		} else if (status == -1) {
			return 'text-invalid';
		} else if (status == 1) {
			return 'text-valid';
		}
	}

	function shopStatus(status) {
		if (status == 0) {
			return 'Processing';
		} else if (status == -1) {
			return 'Not Applicable';
		} else if (status == 1) {
			return 'Valid';
		}
	}

	function goShop(status, id) {
		if (status == 1) {
			return '<a class="button-link-gray right floated" href="/o2o/shopadmin/shopmanagement?shopId=' + id
					+ '"><i class="edit icon"></i> Edit</a>';
		} else {
			return '';
		}
	}
	
	function getHomepageLink(status, id) {
		if (status == 1) {
			return '<a id="link-shop-homepage" href="/o2o/frontend/shopdetail?shopId=' + id + '"><i class="home icon"></i>Shop Homepage</a>';
		} else {
			return '<p><i class="ban icon"></i>Shop Homepage</p>';
		}
	}
	
	$('.ui.dropdown')
	  .dropdown();

});




