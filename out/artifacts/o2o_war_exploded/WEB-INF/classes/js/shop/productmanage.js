$(function() {
	var shopId = 7;
	var addUrl='/o2o/shopadmin/addproductcategorys'
	var listUrl = '/o2o/shopadmin/getproductcategorylist?shopId='
			+ shopId;
	var deleteUrl = '/o2o/shopadmin/removeproductcategory';
	getList();
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				console.log(productList)
				$(".category-wrap").html('');
				var tempHtml = '';
				let productList1 = Array.from(productList);
				console.log(productList1)
				productList1.map(function(item, index) {
					tempHtml += ''
							+ '<div class="row row-product-category now">'
							+ '<div class="col-33 product-category-name">'
							+ item.productCategoryName
							+ '</div>'
							+ '<div class="col-33">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-33"><a href="#" class="button delete" data-id=" '
							+ item.productCategoryId
							+ '">删除</a></div>'
							+'</div>'+'<br>'
				});
				$('.category-wrap').append(tempHtml);
			}
		});
	}


	$('.category-wrap').on('click','.row-product-category.temp .delete',
		function (e) {
			console.log($(this).parent().parent());
			$(this).parent().parent().remove();

	})
	$('.category-wrap').on('click','.row-product-category.now .delete',
		function (e) {
		var target=e.currentTarget;
		console.log(e);
			$.confirm('确定么?', function() {
				$.ajax({
					url : deleteUrl,
					type : 'POST',
					data : {
						productCategoryId : target.dataset.id
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$.toast('操作成功！');
							getList();
						} else {
							$.toast('操作失败！');
						}
					}
				});
			});


	})
	function deleteItem(id, enableStatus) {
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			$.ajax({
				url : deleteUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}

	$('.product-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = '/myo2o/shop/productedit?productId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('delete')) {
							deleteItem(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							window.location.href = '/myo2o/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});

	$('#new').click(function() {
		var tempHtml = '<div class="row row-product-category temp">'
			+ '<div class="col-33 product-category-name"><input class="category-input category" type="text" placeholder="分类名"></div>'
			+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
			+ '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
			+ '</div>';
		$('.category-wrap').append(tempHtml);
	});
	$('#submit').click(function() {
		var tempArr = $('.temp');
		console.log(tempArr)
		var productCategoryList = [];
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.productCategoryName && tempObj.priority) {
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addUrl,
			type : 'POST',
			data : JSON.stringify(productCategoryList),
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					getList();
				} else {
					$.toast('提交失败！');
				}
			}
		});
	});
});