
$(function () {
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../commonTemplateQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result){
                var obj = eval('('+result+')');
                if(parseInt(obj.queryTimes)>=1){
                    var txt=  "�˶�ά���ѱ���ѯ��!";
                    window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
                }
                $("#queryResult").append(obj.queryResult);
                var lotterFlag = obj.winLottery;
                ////�н�
                //if(lotterFlag.length>0){
                //    var txt=  "�����ȡ���";
                //    var option = {
                //        title: "�������",
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