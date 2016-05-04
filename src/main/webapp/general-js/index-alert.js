
$(function () {
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../commonTemplateQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result){
                var obj = eval('('+result+')');
                if(parseInt(obj.queryTimes)>=1){
                    var txt=  "此二维码已被查询过!";
                    window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
                }
                $("#queryResult").append(obj.queryResult);
                var lotterFlag = obj.winLottery;
                ////中奖
                //if(lotterFlag.length>0){
                //    var txt=  "点击领取红包";
                //    var option = {
                //        title: "红包到！",
                //        btn: parseInt("0011",2),
                //        onOk: function(){
                //            window.location.href = "../../../choujiang/choujiang.html?"+lotterFlag
                //        },
                //        onCancel:function(){
                //
                //        },
                //        onClose:function(){
                //
                //        }
                //    }
                //    window.wxc.xcConfirm(txt, "custom", option);
                //}
            }
        })
    } catch (e) {
        alert("Error");
    }
})