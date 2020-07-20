

$(function() {
	// loading 变量主要是来在加载的时候防止继续加载的
	var loading = false;
	// 分页允许返回的最大条数，超过此数则禁止访问后台
	var maxItems = 999;
	// 一页返回的最大条数
	var prevTotal = 0;
	
	var pageSize = 3;
	// 获取店铺列表的URL
	var listUrl = '/o2o/frontend/listshops';
	// 获取店铺类别列表以及区域列表的URL
	var searchDivUrl = '/o2o/frontend/listshopspageinfo';
	// 页码
	var pageNum = 1;
	// 从地址栏URL里尝试获取parent shop category id.
	var parentId = getQueryString('parentId');
	// 是否选择了子类
	var selectedParent = false;
	if (parentId){
		selectedParent = true;
	}
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';
	
	
	// 变量初始化的部分：
	var isAllLoaded = false;
	
	// 渲染出店铺类别列表以及区域列表以供搜索
	getSearchDivData();
	// 预先加载10条店铺信息
	addItems(pageSize, pageNum);
	
	function getStringInRange(s, L) {
		L = 100;
		var str = s;
		console.log(s);
		if(s.length > L) {
			str = s.substring(0, L) + "...";
		} //console.log(str);
		return str;
	}
	
	/**
	 * 获取店铺类别列表以及区域列表信息
	 * 
	 * @returns
	 */
	function getSearchDivData() {
		// 如果传入了parentId，则取出此一级类别下面的所有二级类别
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								// 获取后台返回过来的店铺类别列表
								var shopCategoryList = data.shopCategoryList;
								var html = '';
								html += '<a href="#" class="global-class-select-button" data-category-id=""> All Shops  </a>';
								// 遍历店铺类别列表，拼接出a标签集
								shopCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="global-class-select-button" data-category-id='
													+ item.shopCategoryId
													+ '>'
													+ item.shopCategoryName
													+ '</a>';
										});
								// 将拼接好的类别标签嵌入前台的html组件里
								$('#shoplist-search-div').html(html);
								var selectOptions = '<option value="">All Areas</option>';
								// 获取后台返回过来的区域信息列表
								var areaList = data.areaList;
								// 遍历区域信息列表，拼接出option标签集
								areaList.map(function(item, index) {
									selectOptions += '<option value="'
											+ item.areaId + '">'
											+ item.areaName + '</option>';
								});
								// 将标签集添加进area列表里
								$('#area-search').html(selectOptions);
							}
						});
	}
	
	
	/**
	 * 获取分页展示的店铺列表信息
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
				+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		// 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		
		// 开始 loading 内容:
		if(!isAllLoaded) {
			$("#id-scroll-down-segment-loading").addClass("active");
			$("#id-scroll-down-segment").css("height", "200px");
		}
		
		// 访问后台获取相应查询条件下的店铺列表
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取当前查询条件下店铺的总数
				maxItems = data.count;
				var html = '';
				// 遍历店铺列表，拼接出卡片集合
				data.shopList.map(function(item, index) {
					
					html += '<div class="ui one cards">' + 
					'<div class="ui card" data-shop-id="'+ item.shopId + '">' +  
					'<div class="content"><div class="header">' + item.shopName + '</div>' + 
					'<div class="description" style="font-weight:600;">'+ item.shopCategory.shopCategoryName  +  
					' · ' + getDollarString(item.dollar) + '</div>' + 					
					'<div class="description">' + getStringInRange(item.shopDesc, 90) + '</div>' + 
					'<div class="meta right floated">' + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '</div>' + 
					
					'<div><img src="' + getContextPath() + item.shopImg + '" class="global-class-img-card">' + '</div></div>' + 
					'<div class="extra content">' + '<span class="left floated"><i class="like icon" id="id-like-icon-show"></i>'+ item.likes + ' Likes</span>' + 
					'<span class="right floated"><i class="star icon"></i> Favorite</span></div></div></div>';
					
					/*
					html += '' + '<div class="card" data-shop-id="'
							+ item.shopId + '">' + '<div class="card-header">'
							+ item.shopName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getContextPath() + 
							+ item.shopImg + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.shopDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';*/
				});
				// 将卡片集合添加到目标HTML组件里
				$('.list-div').append(html);
				// 获取目前为止已显示的卡片总数，包含之前已经加载的
				var total = $('.list-div .card').length;
				
				// 否则页码加1，继续load出新的店铺
				pageNum += 1;
				// 加载结束，可以再次加载了
				loading = false;
				
				if(!isAllLoaded) {
					$("#id-scroll-down-segment-loading").removeClass("active");
					$("#id-scroll-down-segment").css("height", "0px");
				}
				
				// 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
				if (total > maxItems || total === prevTotal) {
					// 隐藏提示符
					isAllLoaded = true;
					$("#id-scroll-down-segment").hide();
					
				} else {
				}
				
				prevTotal = total;
				// 刷新页面，显示新加载的店铺
				
				/***
				$.refreshScroller(); ***/
			}
		});
	}
	
	// 下滑屏幕
	$(window).scroll(function(){ 
		if(isAllLoaded) return;
		console.log("scrolling");
		$('#id-scroll-down-segment').visibility({
			// update size when new content loads
			// load content on bottom edge visible
			onTopVisible: function(thisElement) {
				if (loading) {
					return;
				}
				addItems(pageSize, pageNum);
				// top of element passed
				// $('#id-scroll-down-segment').attr("id", "");
				console.log("123");
			}
		});
	});

	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	// 点击店铺的卡片进入该店铺的详情页
	$('.shop-list').on('click', '.card', function(e) {
		if(userManipulateCount > 0) return;
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	});

	// 选择新的店铺类别之后，重置页码，清空原先的店铺列表，按照新的类别去查询
	$('#shoplist-search-div').on(
			'click',
			'.global-class-select-button',
			function(e) {
				if(userManipulateCount > 0) return;
				isAllLoaded = false; prevTotal = 0; $("#id-scroll-down-segment").show();
				if (parentId && selectedParent) {// 如果传递过来的是一个父类下的子类
					shopCategoryId = e.target.dataset.categoryId;
					// 若之前已选定了别的category,则移除其选定效果，改成选定新的
					if ($(e.target).hasClass('global-class-select-button-selected')) {
						$(e.target).removeClass('global-class-select-button-selected');
						shopCategoryId = '';
					} else {
						$(e.target).addClass('global-class-select-button-selected').siblings()
								.removeClass('global-class-select-button-selected');
					}
					// 由于查询条件改变，清空店铺列表再进行查询
					$('.list-div').empty();
					// 重置页码
					pageNum = 1;
					addItems(pageSize, pageNum);
				} else {// 如果传递过来的父类为空，则按照父类查询
					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('global-class-select-button-selected')) {
						$(e.target).removeClass('global-class-select-button-selected');
						parentId = '';
					} else {
						$(e.target).addClass('global-class-select-button-selected').siblings()
								.removeClass('global-class-select-button-selected');
					}
					// 由于查询条件改变，清空店铺列表再进行查询
					$('.list-div').empty();
					// 重置页码
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});
	
	
	// 需要查询的店铺名字发生变化后，重置页码，清空原先的店铺列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		if(userManipulateCount > 0) return;
		isAllLoaded = false; prevTotal = 0; $("#id-scroll-down-segment").show();
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// 区域信息发生变化后，重置页码，清空原先的店铺列表，按照新的区域去查询
	$('#area-search').on('change', function() {
		if(userManipulateCount > 0) return;
		isAllLoaded = false; prevTotal = 0; $("#id-scroll-down-segment").show();
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// 返回按钮键的通常运行
	$('#global-id-go-back-icon').click(function(e) {
		if(userManipulateCount > 0) return;
		window.location.href = '/o2o/frontend/index';
	});
	
	$('#global-id-search-icon').click(function(e) {
		console.log("Click!!!!");
	});
	

});
