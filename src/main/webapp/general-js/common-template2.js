/**
 * Created by junying li on 2016/4/20.
 */

$(function () {
    $("#FloatDIV").hide();
    $("#info").hide();
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $("#unquieKey").html(uniqueKey);
        $.get("../../../generalQrCodeQueryServletVersion2"+"?uniqueKey="+uniqueKey,function(result){
            if(result) {
                var dataObj=eval("("+result+")");
                for(var o in dataObj){
                    switch(dataObj[o].key){
                        case "productName" : $("#productName").html(dataObj[o].result);break;
                        case "productFactory" : $("#productFactory").html(dataObj[o].result);break;
                        case "productOriginalAddress" : $("#productOriginalAddress").html(dataObj[o].result);break;
                        case "telNo" : $("#telNo").html(dataObj[o].result);break;
                        case "productAddress" :     $("#productAddress").attr("href", dataObj[o].result);break;
                        case "queryTimes" : $("#queryTimes").html(dataObj[o].result); break;
                    }
                }
            }
        })
    } catch (e) {
        alert("Error");
    }
})