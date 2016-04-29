/**
 * Created by Cindy on 16/4/29.
 */
var colors = [];
var textColors = [];
var prizes = [];

$(function() {
    $("#inner").click(function() {
        lottery();
    });

    $.ajax({
        type : 'GET',
        url : '../LuckyDrawServlet',
        data: {cust:$("#cust").val(), type:"init"},
        dataType : 'json',
        cache : false,
        error : function() {
            alert('出错了！');
            return false;
        },
        success : function(json) {
            var nameArr = json.nameArr.split(",");
            prizes = nameArr;
            for(var i=0; i<nameArr.length; i++){
                if(i % 2 == 0){
                    colors[i] = "#F36000";
                    textColors[i] = "white";
                }else{
                    colors[i] = "#FEF8A2";
                    textColors[i] = "#F36000";
                }
            }
            draw();
        }
    });
});

var startAngle = -Math.PI/2;
var arc = 0;
var ctx;
function draw() {
    arc = prizes.length>0?(Math.PI * 2 / prizes.length):0;
    startAngle = -Math.PI/2 - arc/2;
    var canvas = document.getElementById("wheelcanvas");
    if (canvas.getContext) {
        var textRadius = 120;
        //圆盘内径
        var insideRadius = 0;
        //圆盘外径
        var outsideRadius = 150;
        ctx = canvas.getContext("2d");
        ctx.clearRect(0,0,400,400);
        //画转盘边框
        ctx.fillStyle = "#DCC88F";
        ctx.beginPath();
        //arc:（圆心X坐标，圆心Y坐标，半径，开始角度（弧度），结束角度弧度，是否按照顺时针画）
        ctx.arc(200, 200, outsideRadius+15, 0, Math.PI * 2, false);
        ctx.fill();
        ctx.save();
        //画转盘内圈
        ctx.lineWidth = 2;
        ctx.font = 'bold 16px sans-serif';
        for(var i = 0; i < prizes.length; i++) {
            var angle = startAngle + i * arc;
            ctx.fillStyle = colors[i];
            ctx.beginPath();
            //arc:（圆心X坐标，圆心Y坐标，半径，开始角度（弧度），结束角度弧度，是否按照顺时针画）
            ctx.arc(200, 200, outsideRadius, angle, angle + arc, false);
            ctx.arc(200, 200, insideRadius, angle + arc, angle, true);
            ctx.fill();
            ctx.save();
            ctx.shadowOffsetX = -1;
            ctx.shadowOffsetY = -1;
            ctx.shadowBlur    = 0;
            ctx.shadowColor   = "rgb(220,220,220)";
            ctx.fillStyle = "black";
            ctx.fillStyle = textColors[i];
            ctx.translate(200 + Math.cos(angle + arc / 2) * textRadius, 200 + Math.sin(angle + arc / 2) * textRadius);
            ctx.rotate(angle + arc / 2 + Math.PI / 2);
            var text = prizes[i];
            ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
            ctx.restore();
        }
    }
}

function lottery() {
    document.getElementById("result").innerHTML = "正在抽奖......";
    $.ajax({
        type : 'GET',
        url : '../LuckyDrawServlet',
        data: {cust:$("#cust").val(), type:"spin"},
        dataType : 'json',
        cache : false,
        error : function() {
            alert('出错了！');
            return false;
        },
        success : function(json) {
            //转动的时候不允许点击抽奖按钮
            $("#inner").unbind('click').css("cursor", "default");
            var angle = parseInt(json.angle); //角度
            var msg = json.msg; //提示信息
            $("#wheelcanvas").rotate({ //inner内部指针转动，outer外部转盘转动
                duration : 5000, //转动时间
                angle : 0, //开始角度
                animateTo : 3600 + (360-angle), //转动角度
                easing : $.easing.easeOutSine, //动画扩展
                callback : function() {
                    document.getElementById("result").innerHTML = msg;
                    if(json.angle >=0) {
                        $("#inner").bind('click').css("cursor", "pointer");
                        $("#inner").click(function() {
                            lottery();
                        });
                    }
                    return false;
					////For test
					//if(json.angle >=0) {
					//	lottery();
					//}else{
					//	return false;
					//}
                }
            });
        }
    });
}