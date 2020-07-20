var theUserType = 0;
var userManipulateCount = 0;
var isSideBarShow = false;

$(function() {
	function getUserSidebarAvatar(type) {
		if(type === 1) { 
			return '<a class="item" id="id-sidebar-user"><img src="../upload/cat.png" id="id-sidebar-user-icon-none" class="right-floated" />';
		} else {
			return '<a class="item" id="id-sidebar-user"><img src="../upload/lion.png" id="id-sidebar-user-icon-none" class="right-floated" />';
		}
	}
	
	function getUserSidebarMember(type) {
		if(type === 1) {
			return '<a class="item" id="id-sidebar-member"><i class="trophy icon class-color-gold"></i> Become <span class="class-color-gold">Shop Holder For Free</span>(Recommended)</a>';
		} else {
			return '<a href="/o2o/shopadmin/shoplist" class="item" id="id-sidebar-member"><i class="trophy icon class-color-gold"></i><span class="class-color-gold">Manage Your Shop</span></a>';
		}
	}
	
	// 检查用户 的登陆状态，并且根据用户的类型来决定 sider-bar 的内容
	url = '/o2o/local/checkuserstatus';
	$.getJSON(url, function(data) {
		var htmlContent = '<p class="item">Welcome to My Campus O2O!<p>';
		if (data.success && data.user) {
			// User have been logged in
			var personInfo = data.user;
			theUserType = personInfo.userType;
			htmlContent += getUserSidebarAvatar(personInfo.userType) + 
			'<span>Hi, ' +  personInfo.name  + '</span></a>' + getUserSidebarMember(personInfo.userType);
			var commonPart = '<a class="item" id="id-sidebar-password" href="/o2o/local/changepsw?usertype=1"> <i class="key icon"></i> Change Password</a>' + 
			'<a class="item" id="id-sidebar-password" href="/o2o/local/accountbind?usertype=1"> <i class="handshake icon"></i> Bind Account</a>' + 
			'<a class="item" id="id-sidebar-point" href="/o2o/frontend/mypoint"> <i class="star icon"></i> Reward Point</a>' + 
			'<a class="item" id="id-sidebar-buy" href="/o2o/frontend/myrecord"> <i class="shopping cart icon"></i> Consume History</a>' + 
			'<a class="item" id="id-sidebar-redeem" href="/o2o/frontend/pointrecord"> <i class="gift icon"></i> Redeem History</a>' + 
			'<a class="item" id="id-sidebar-aboutus"> <i class="search icon"></i> Learn More</a>';
			
			$('#id-sidebar-container').html(htmlContent + commonPart);
			// console.log(personInfo);
		} else {
			// No user Logged in....
		}
	});
	
	// Side bar part: two callback functions
	$('#id-footer-more').click(function(e) {
		$('.ui.sidebar')
		.sidebar('show', function() {
			if(!isSideBarShow) {
				userManipulateCount++;
			}
			isSideBarShow = true;
			$('#id-footer-more').attr("class", "item active");
			$('#id-footer-home').attr("class", "item");
			console.log("Done!");
		});
	});
	
	
	$('.ui.sidebar')
	  .sidebar({
		  onHidden: function() {
			  console.log('on hidden');
			  isSideBarShow = false;
			  userManipulateCount--;
			  $('#id-footer-more').attr("class", "item");
			  $('#id-footer-home').attr("class", "item active");
	    } 
	  }
	);
});