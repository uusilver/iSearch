/**
 * Created by vali on 2016/4/20.
 */

$(function () {
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../commonTemplateQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result){
                var obj = eval('('+result+')');
                    $("#queryResult").append(obj.queryResult);
                    $("#productResult").append(obj.productResult);
                    var lotterFlag = obj.winLottery;
                    //中奖
                    if(lotterFlag.length>0){
                        window.open( "../../../choujiang/choujiang.html?"+lotterFlag);
                    }
                }
        })
    } catch (e) {
        alert("Error");
    }
})