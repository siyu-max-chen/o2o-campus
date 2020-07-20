$(function() {
	var loading = false;
	// 分页允许返回的最大条数，超过此数则禁止访问后台
	var maxItems = 20;
	// 默认一页返回的商品数
	var pageSize = 3;
	var prevTotal = 0;
	
	// 列出商品列表的URL
	var listUrl = '/o2o/frontend/listproductsbyshop';
	// 默认的页码
	var pageNum = 1;
	// 从地址栏里获取ShopId
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';
	// 获取本店铺信息以及商品类别信息列表的URL
	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
	
	// 变量初始化的部分：
	var isAllLoaded = false;
	var isLoadingLikeIcon = false;
	
	// 用户登录验证
	var statusUrl = '/o2o/local/checkuserstatus';
	var getLikeUrl = '/o2o/frontend/checkuserlikeshop'
	var user = null;
	var shop = null;
	var likeMap = null;
	var prevLikes = 0;
	
	// **************	用户状态登出系统	*****************
	// 如果用户已登录, 则无法进入该界面!
	$.getJSON(statusUrl, 
			function(data) {
		if (data.success && data.user) {
			// User have been logged in
			user = data.user;
		}
		
		// 渲染出店铺基本信息以及商品类别列表以供搜索
		getSearchDivData();
		// 预先加载10条商品信息
		addItems(pageSize, pageNum);
		
		//给兑换礼品的a标签赋值兑换礼品的URL，2.0讲解
		 $('#global-id-gift-icon').click(function() {
			 if(userManipulateCount > 0) return;
			 window.location.href = '/o2o/frontend/awardlist?shopId=' + shopId
		 });
		
	});
	
	// 获取用户是否【点赞】过这个商店
	function getUserLikeShop() {
		console.log(user);
		console.log(shop);
		if(user != null && shop != null) {
			var url = getLikeUrl + '?' + 'userId=' + user.userId + '&shopId=' + shop.shopId;
			isLoadingLikeIcon = true;
			$.getJSON(url, function(data) {
				if(data.success && data.existed) {
					// 如果存在 like 关系, 则重新渲染 like button 为 红色
					likeMap = data.likeMap;
					$('#id-like-icon').addClass('active');
				}
				isLoadingLikeIcon = false;
				// console.log(data);
				// console.log(likeMap);
			});
		}
	}
	
	// 获取本店铺信息以及商品类别信息列表
	function getSearchDivData() {
		var url = searchDivUrl;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								shop = data.shop;
								var html = '';
								
								html += '<div class="ui card global-class-rect-card" style="margin-bottom:5vh;">' + 
								'<div class="image" style="border-radius:0px!important;">' + '<img src="' + getContextPath() + shop.shopImg + 
								'" class="global-class-img-card"></div>' + '<div class="content">' + 
								'<div class="header">' + shop.shopName + '</div>' + 
								'<div class="description" style="font-weight:600;">' + shop.shopCategory.shopCategoryName +  
								' · ' + getDollarString(shop.dollar) + '</div>' + 
								'<div class="description"> ' + shop.shopDesc + '</div>' + 
								'<div class="meta right floated">' + new Date(shop.lastEditTime).Format("yyyy-MM-dd") + '</div></div>' + 
								'<div class="extra content"><p>Address: ' + shop.shopAddr + '</p>' + 
								'<p>Tel: ' + shop.phone + '</p>' + '</div>' + 
								'<div class="extra content"><span class="left floated" id="id-like-icon-container"><i class="like icon" id="id-like-icon"></i><span id="id-like-icon-text">' + getLikesString(shop.likes) + '</span></span>' + 
								'<span class="right floated"><i class="star icon"></i> Favorite</span>' + 
								'</div></div>';
								
								$('#id-shop-container').html(html);
								
								// 更新点赞的内容
								prevLikes = shop.likes;
								getUserLikeShop();
								
								// 获取后台返回的该店铺的商品类别列表
								var productCategoryList = data.productCategoryList;
								html = '';
								// 遍历商品列表，生成可以点击搜索相应商品类别下的商品的a标签
								
								
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="global-class-select-button" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								// 将商品类别a标签绑定到相应的HTML组件中
								$('#shopdetail-button-div').html(html);
								
								// 点击 like icon 来进行调节 toggle
								$("#id-like-icon").click(function(e){
									if(isLoadingLikeIcon || userManipulateCount > 0) {
										return;
									}
									if(user == null) {
										Toast("Please log in to do the operation!", 1000);
										return;
									}
									var url = '/o2o/frontend/toggleuserlikeshop?userId=' + user.userId + "&shopId=" + shop.shopId;
									// 如果没有关注, 则进行关注的操作
									if(likeMap == null) {
										url += '&liked=false';
									} else {
										url += '&liked=true';
									}
									
									isLoadingLikeIcon = true;
									$.getJSON(url, function(data) {
										if(data.success) {
											// 如果存在 like 关系, 则重新渲染 like button 为 红色
											likeMap = data.likeMap;
											var likeCount = prevLikes;
											
											if(data.liked) {
												$('#id-like-icon').addClass('active');
											} else {
												$('#id-like-icon').removeClass('active');
											}
											
											var newCount = getLikesString(data.likeCount);
											$('#id-like-icon-text').html(newCount);
											
											isLoadingLikeIcon = false;
										} else {
											Toast(data.errMsg, 1000);
											isLoadingLikeIcon = false;
										}
									});
									
								});
							}
						});
	}
	/**
	 * 获取分页展示的商品列表信息
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
		// 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		
		// 开始 loading 内容:
		if(!isAllLoaded) {
			$("#id-scroll-down-segment-loading").addClass("active");
			$("#id-scroll-down-segment").css("height", "200px");
		}
		
		// 访问后台获取相应查询条件下的商品列表
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取当前查询条件下商品的总数
				maxItems = data.count;
				var html = '';
				// 遍历商品列表，拼接出卡片集合
				data.productList.map(function(item, index) {
					
					html += '<div class="ui grid class-product-grid-container" data-product-id=' + item.productId + '>' + 
					'<div class="four wide column"><div class="ui one cards" id="">' + 
					'<div class="ui card global-class-rect-card"><div class="image" style="border-radius: 0px !important;">' + 
					'<img src="' + getContextPath() + item.imgAddr + '" class="" style="max-height:6rem;">' + '</div></div></div></div>' + 
					'<div class="twelve wide column"><div class="ui one cards" id=""><div class="ui card global-class-rect-card">' + 					
					'<div class="content"><div class="description" style="font-weight: 600;">' + item.productName + '</div>' + 
					'<div class="description">' + item.productDesc + '</div>' + 
					'<div class="meta right floated">' + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '</div>' + 
					'</div></div></div></div></div>';
					
					
				});
				// 将卡片集合添加到目标HTML组件里
				$('.list-div').append(html);
				// 获取目前为止已显示的卡片总数，包含之前已经加载的
				var total = $('.list-div .card').length;
				// 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
				
				// 加载结束，可以再次加载了
				loading = false;
				if(!isAllLoaded) {
					$("#id-scroll-down-segment-loading").removeClass("active");
					$("#id-scroll-down-segment").css("height", "0px");
				}
				
				if (total > maxItems || total === prevTotal) {
					// 隐藏提示符
					// $('.infinite-scroll-preloader').hide();
					isAllLoaded = true;
					$("#id-scroll-down-segment").hide();
				} else {
					// $('.infinite-scroll-preloader').show();
				}
				// 否则页码加1，继续load出新的店铺
				pageNum += 1;
				prevTotal = total;
				
				// 刷新页面，显示新加载的店铺
				
				// $.refreshScroller();
			}
		});
	}

	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	
	// 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
	$('#shopdetail-button-div').on(
			'click',
			'.global-class-select-button',
			function(e) {
				if(userManipulateCount > 0) return;
				isAllLoaded = false; prevTotal = 0; $("#id-scroll-down-segment").show();
				// 获取商品类别Id
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					// 若之前已选定了别的category,则移除其选定效果，改成选定新的
					if ($(e.target).hasClass('global-class-select-button-selected')) {
						$(e.target).removeClass('global-class-select-button-selected');
						productCategoryId = '';
					} else {
						$(e.target).addClass('global-class-select-button-selected').siblings()
								.removeClass('global-class-select-button-selected');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});
	
	// 点击商品的卡片进入该商品的详情页
	$('.list-div').on(
			'click',
			'.ui.grid',
			function(e) {
				var productId = e.currentTarget.dataset.productId;
				window.location.href = '/o2o/frontend/productdetail?productId='
						+ productId;
			});
	
	// 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		if(userManipulateCount > 0) return;
		isAllLoaded = false; prevTotal = 0; $("#id-scroll-down-segment").show();
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	
	// 下滑屏幕
	$(window).scroll(function(){ 
		if(isAllLoaded) return;
		console.log("scrolling");
		$('#id-scroll-down-segment').visibility({
			// update size when new content loads
			// load content on bottom edge visible
			onTopVisible: function(thisElement) {
				console.log("123");
				if (loading) {
					return;
				}
				addItems(pageSize, pageNum);
				// top of element passed
				// $('#id-scroll-down-segment').attr("id", "");
				
			}
		});
	});
	
	
	// $.init();
});