$(function () {
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../commonTemplateQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result){
                var obj = eval('('+result+')');
                $("#queryResult").append(obj.queryResult);
                var lotterFlag = obj.winLottery;
                //ÖÐ½±
                if(lotterFlag.length>0){
                    window.open( "../../../choujiang/choujiang.html?"+lotterFlag);
                }
            }
        })
    } catch (e) {
        alert("Error");
    }
})