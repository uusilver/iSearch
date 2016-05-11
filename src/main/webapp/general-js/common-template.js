/**
 * Created by junying li on 2016/4/20.
 */

$(function () {
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../generalQrCodeQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result){
                var obj = eval('('+result+')');
                    $("#queryResult").append(obj.queryResult);
                    $("#productResult").append(obj.productResult);
                    var lotterFlag = obj.winLottery;

                if(obj.productAddress){
                    $('#purchaseLink').attr('href',obj.productAddress);
                }else{
                    $('#purchaseLink').attr('href','shop.htm?'+uniqueKey);
                }

                //
                if(lotterFlag.length>0){
                    $("#FloatDIV").click(function(){
                        window.location.href = "../../../choujiang/choujiang.html?"+lotterFlag;
                    })
                }else{
                    $("#FloatDIV").click(function(){
                        window.location.href = "../../../choujiang/choujiang.html?∫‹“≈∫∂£¨Œ¥÷–Ω±!";
                    })
                }
                }
        })
    } catch (e) {
        alert("Error");
    }
})