/**
 * Created by vali on 2016/4/20.
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

                //TODO �滻��������ǩ
                $('#purchaseLink').attr('href','http://www.taobao.com');

                //�н�
                if(lotterFlag.length>0){
                    $("#FloatDIV").click(function(){
                        window.location.href = "../../../choujiang/choujiang.html?"+lotterFlag;
                    })
                }else{
                    $("#FloatDIV").click(function(){
                        window.location.href = "../../../choujiang/choujiang.html?���ź�����δ���н�!";
                    })
                }
                }
        })
    } catch (e) {
        alert("Error");
    }
})