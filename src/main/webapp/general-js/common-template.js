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
                //�н�
                if(lotterFlag.length>0){
                    var txt=  "�����ȡ���";
                    var option = {
                        title: "�������",
                        btn: parseInt("0011",2),
                        onOk: function(){
                            window.location.href = "../../../choujiang/choujiang.html?"+lotterFlag
                        },
                        onCancel:function(){

                        },
                        onClose:function(){

                        }
                    }
                    window.wxc.xcConfirm(txt, "custom", option);
                }
                }
        })
    } catch (e) {
        alert("Error");
    }
})