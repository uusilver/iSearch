$(function () {
    try {
        var uniqueKey = window.location.href.split("?")[1];
        $.get("../../../commonTemplateQueryServlet"+"?uniqueKey="+uniqueKey,function(result){
            if(result){
                var obj = eval('('+result+')');
                $("#queryResult").append(obj.queryResult);
            }
        })
    } catch (e) {
        alert("Error");
    }
})