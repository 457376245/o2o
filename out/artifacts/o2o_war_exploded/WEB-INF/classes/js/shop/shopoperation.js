$(function () {
    var shopId=getQueryString("shopId");
    var isEdit=shopId?true:false
    var initUrl='/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl='/o2o/shopadmin/registershop';
    var shopInfoUrl="/o2o/shopadmin/getshopbyid?shopId="+shopId
    var editShopUrl="/o2o/shopadmin/modifyshop"
    if (!isEdit){
        getShopInitInfo();
    }
    else {
        getShopInfo(shopId);
    }


    function getShopInfo(shopId) {
        console.log("getShopInfo")
        $.getJSON(shopInfoUrl,function (data) {
            if (data.success){
                var shop=data.shop;
                $("#shop-name").val(shop.shopName);
                $("#shop-addr").val(shop.shopAddr);
                $("#shop-phone").val(shop.Phone);
                $("#shop-desc").val(shop.shopDesc);
                var shopCategory='<option data-id="'+shop.shopCategory.shopCategoryId+'"selected>'+shop.shopCategory.shopCategoryName+'</option>';
                var tempAreaHtml='';
                data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'+item.areaName+'</option>';
                })
                $("#shop-category").html(shopCategory);
                $("#shop-category").attr('disabled','disabled');
                $('#area').html(tempAreaHtml);
                $("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected")
            }
        })
    }
    function getShopInitInfo() {
        console.log("getShopInitInfo")
        $.getJSON(initUrl,function (data) {
            if (data.success){
                var tempHtml='';
                var tempAreaHtml='';
                data.categoryList.map(function (item,index) {
                    tempHtml+='<option data-id="'+item.shopCategoryId+'">'+item.shopCategoryName+'</option>';
                });
                data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'+item.areaName+'</option>';
                })
                $("#shop-category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
        })

    }
    $("#submit").click(function () {
        alert(11)
        var shop={};
        if (isEdit){
            shop.shopId=shopId;
        }
        shop.shopName=$("#shop-name").val();
        shop.shopAddr=$("#shop-addr").val();
        shop.Phone=$("#shop-phone").val();
        shop.shopDesc=$("#shop-desc").val();
        shop.shopCategory={shopCategoryId:$('#shop-categoryategory').find('option').not(function () {
                return!this.selected
            }).data('id')}
        shop.area={areaId:$('#area').find('option').not(function () {
                return!this.selected
            }).data('id')}
        var shopImg=$('#shop-img')[0].files[0];
        var formData=new FormData();
        formData.append('shopImg',shopImg);
        formData.append('shopStr',JSON.stringify(shop));
        var verifyCodeActual=$("#j_captcha").val();
        if (!verifyCodeActual){
            alert("请输入验证码！")
            return
        }
        formData.append("verifyCodeActual",verifyCodeActual);
        $.ajax({
            url:(isEdit?editShopUrl:registerShopUrl),
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if (data.success){
                    alert('提交成功');
                }
                else {
                    alert('提交失败'+data.errMsg);
                }

            }
        })
    })

})
function changeVerifyCode() {
    $('#captcha_img').attr("src",'../Kaptcha?'+Math.floor(Math.random()*100))
}
