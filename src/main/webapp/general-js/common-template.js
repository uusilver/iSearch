/**
 * Created by junying li on 2016/4/20.
 */

$(function () {
    $("#FloatDIV").hide();
    $("#info").hide();
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../generalQrCodeQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result) {
                var obj = eval('(' + result + ')');
                $("#queryResult").append(obj.queryResult);
                $("#productResult").append(obj.productResult);
                var lotterFlag = obj.winLottery;

                //判断是否隐藏抽奖按钮
                var showHideLotteryClick = obj.lotteryFlag;
                if (showHideLotteryClick.length > 1) {
                    $("#FloatDIV").show();
                }
                if (obj.productAddress) {
                    $('#purchaseLink').attr('href', obj.productAddress);
                } else {
                    $('#purchaseLink').attr('href', 'shop.htm?' + uniqueKey);
                }

                //是否显示产品信息
                if (obj.productDesc) {
                    if (obj.productDesc.length > 0) {
                        $("#info").show();
                        $("#info").append(obj.productDesc);
                    }
                }

                //
                if(lotterFlag.length>0){
                    $("#FloatDIV").click(function(){
                        window.location.href = "../../../choujiang/choujiang.html?"+lotterFlag;
                    })
                }else{
                    $("#FloatDIV").click(function(){
                        window.location.href = "../../../choujiang/choujiang.html?很遗憾，未中奖!";
                    })
                }
                }
        })
    } catch (e) {
        alert("Error");
    }
})