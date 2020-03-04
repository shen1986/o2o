$(function(){
    var initUrl = '/o2o/shopadmin/getshopadmininfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    function getShopInitInfo(){
        $.getJSON(initUrl, function(data){
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function(item, index) {

                })
            }
        })
    }
});