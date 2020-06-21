$(function() {
	var shopId = 1;
	var listUrl = '/o2o/shopadmin/getproductcategorylist?shopId='
			+ shopId;
	var deleteUrl = '/o2o/shopadmin/modifyproduct';
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
		window.location.href = '/myo2o/shop/productedit';
	});
});