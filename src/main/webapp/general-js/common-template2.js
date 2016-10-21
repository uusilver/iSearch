/**
 * Created by junying li on 2016/4/20.
 */
//global variable to record screen size
var screenSize;
var visitUrl;
var browser;
$(function () {
	screenSize =  screen.width+"*"+screen.height;
    visitUrl = window.location.href;
    browser= myBrowser();
	
    $("#FloatDIV").hide();
    $("#info").hide();
    $("#warn").hide();
    try {
        var uniqueKey = window.location.href.split("?")[1];
        if (uniqueKey.length>20) {
            $("#warn").show();
            $("#green").hide();
        } else {
            $("#unquieKey").html(uniqueKey);
            $.get("../../../generalQrCodeQueryServletVersion2" + "?uniqueKey=" + uniqueKey, function (result) {
                if (result) {
                    var dataObj = eval("(" + result + ")");
                    for (var o in dataObj) {
                        switch (dataObj[o].key) {
                            case "productName" :
                                $("#productName").html(dataObj[o].result);
                                break;
                            case "productFactory" :
                                $("#productFactory").html(dataObj[o].result);
                                break;
                            case "productOriginalAddress" :
                                $("#productOriginalAddress").html(dataObj[o].result);
                                break;
                            case "telNo" :
                                $("#telNo").html(dataObj[o].result);
                                break;
                            case "productAddress" :
                                $("#productAddress").attr("href", dataObj[o].result);
                                break;
                            case "queryTimes" :
                                if (parseInt(dataObj[o].result) >= 3) {
                                    $("#warn").show();
                                    $("#green").hide();
                                }
                                $("#queryTimes").html(dataObj[o].result);
                                break;
                        }
                    }
                }

            })
        }
    } catch (e) {
        alert("Error");
    }
})
// hot trace
/**
 * Created by junying Li on 10/21/2016.
 * Minize capture mouse position
 */


function getPointPosition(ev)
{
    var data = {};

    ev = ev || window.event;

    var record = "Record:"+ev.x +":" +ev.y;

    data.screenSize = screenSize;
    data.visitUrl = visitUrl;
    data.x = ev.x;
    data.y = ev.y;
    data.visitDate = new Date().toLocaleString();
    data.browser = browser;
	$.get("../../../hotTraceServlet" + "?t=" + JSON.stringify(data), function (result) {});
    console.log(data);
}

function myBrowser(){
    var userAgent = navigator.userAgent; //È¡µÃä¯ÀÀÆ÷µÄuserAgent×Ö·û´®
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; //ÅĞ¶ÏÊÇ·ñOperaä¯ÀÀÆ÷
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //ÅĞ¶ÏÊÇ·ñFirefoxä¯ÀÀÆ÷
    if (userAgent.indexOf("Chrome") > -1){
        return "Chrome";
    }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //ÅĞ¶ÏÊÇ·ñSafariä¯ÀÀÆ÷
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; //ÅĞ¶ÏÊÇ·ñIEä¯ÀÀÆ÷
}

document.onmousedown  = getPointPosition;

