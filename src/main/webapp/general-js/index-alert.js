
$(function () {
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../generalQrCodeQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result){
                var obj = eval("("+result+")");
                if(parseInt(obj.queryTimes)>=1){
                    var txt=  "此二维码已被查询过!";
                    window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
                }
                $("#queryResult").append(obj.queryResult);
            }
        })
    } catch (e) {
        alert("Error");
    }
})