function zdajax(t,e,n,i){$.ajax({url:t,type:e,success:function(t){$(n).html(t)},error:function(){$(n).html(i)}})}function zdscroll(t,e){$("html, body").animate({scrollTop:$(t).offset().top-e},250)}function zdscajax(t,e,n,i){$(n).scrollspy({animation:null,repeat:!1}).on("inview.scrollspy.amui",function(){zdajax(t,e,n,i)})}function zdheighttoggle(t,e,n){var i=$(t),o=i.height(),a=i.find("nav");0==a.length&&(a=i.next("nav")),o>e?(i.height(n),a.click(function(){i.height()==n?(a.text("Less ↑"),i.height(o)):i.height()==o&&(a.text("More ↓"),i.height(n))})):a.hide()}function zdhidtoggle(t){var e=$(t),n=e.find("nav");0==n.length&&(n=e.next("nav")),n.attr("st","0"),n.click(function(){"0"==n.attr("st")?(e.find(".zd-hidden").addClass("zd-show"),n.attr("st","1").text("Less ↑")):(e.find(".zd-hidden").removeClass("zd-show"),n.attr("st","0").text("More ↓"))})}function zdmodal(t,e,n,i,o){$.ajax({url:t,type:"get",success:function(t){$(e).html(t),$(n).modal({width:i,height:o})}})}function zdcommentinit(){var t=$(".detail-comments").find("#comments");t.find("li.am-comment").nextAll().find("li.am-comment").addClass("replay_padding").nextAll().find("li.am-comment").addClass("replay_padding2").nextAll().find("li.am-comment").addClass("replay_padding3"),t.find(".reply").click(function(){t.find(".comment-reply-form").hide(),$(this).parent().siblings(".comment-reply-form").css("display","block"),$("body, html").animate({scrollTop:$(this).offset().top-$(window).height()/2},400)}),t.find(".cancel_comment").click(function(){t.find(".comment-reply-form").hide()}),t.find("form").submit(function(){var t=$(this).find("#id_comment").val(),e=$("<p>"+t+"</p>").text().replace(/^\s\s*/,"").replace(/\s\s*$/,"");return 0==t.length?!1:(e.length<1||e.length>500)&&-1==t.indexOf("<img ")?($("#zd-alert1").modal({width:300}),!1):void 0}),t.find(".comment-ding-link").click(function(t){var e=(parseInt($(this).offset().left)+10,parseInt($(this).offset().top)-10,$(this)),n=$(this)[0].id,i=/support-/g;return n=n.replace(i,"").trim(),$.getJSON("/comment/support/"+n,null,function(t){t.status&&(e.parent().parent().append('<div class="zhans" style="float:right"><b>+1</b></div>'),$(".zhans").css({position:"relative","z-index":"1",color:"#dd514c",top:"-5px",left:"+30px"}).animate({top:"-25px",left:"+30px"},"slow",function(){$(this).fadeIn("fast").remove();var t=parseInt(e.find("em").html().replace("(","").replace(")",""));e.html(e.html().replace(gtext16,gtext17).replace(t,t+1)),e.unbind("click")}))}),!1})}function shaixuaninit(){for(var t=window.location.href,e=$("#shaixuantab").find(".am-parent"),n=[],i=0;i<e.size();i++){var o=e.eq(i).attr("id");o&&n.push(o)}for(var a=0;a<n.length;a++)if(-1!=t.indexOf(n[a])){var s=new RegExp("^.*"+n[a]+"=([^&]*).*$","g"),l=decodeURIComponent(t.replace(s,"$1")),c=$("#"+n[a]);c.find("a:first").text(c.find("[val='"+l+"']").text())}}function isWeiXin(){var t=window.navigator.userAgent.toLowerCase();return"micromessenger"==t.match(/MicroMessenger/i)}function weixinjsinit(){if(isWeiXin()){var t=document.createElement("script");t.src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js";var e=document.getElementsByTagName("script")[0];e.parentNode.insertBefore(t,e),$.ajax({url:"/zddata/wechat_js/",type:"POST",dataType:"json",data:{the_url:window.location.href},success:function(t){wx.config({debug:!1,appId:"wxc01f0b7c52f905b5",timestamp:t.timestamp,nonceStr:t.noncestr,signature:t.signature,jsApiList:["onMenuShareTimeline","onMenuShareAppMessage"]}),wx.ready(function(){var t=document.getElementsByTagName("meta"),e="";for(zi in t)"undefined"!=typeof t[zi].name&&"description"==t[zi].name.toLowerCase()&&(e=t[zi].content);var n=$("#bdzjdetail .main-text").find("img:first").attr("src"),i={title:document.title.replace(" | 让产业合作更简单",""),desc:e,link:window.location.href,imgUrl:n};wx.onMenuShareTimeline(i),wx.onMenuShareAppMessage(i)})},error:function(){}})}}function zdajax(t,e,n,i){$.ajax({url:t,type:e,success:function(t){$(n).html(t)},error:function(){$(n).html(i)}})}function followinfo(t){$.ajax({url:t+"/followed_info/",type:"post",success:function(t){var e=jQuery.parseJSON(t),n=$("#follow-btn");n.attr("val","1"),e.is_followed&&n.text("已关注").attr("val","2").hover(function(){$(this).text("取消")},function(){$(this).text("已关注")}),$("#follow-num").text(e.f_user_count)}})}function followbtn(t){$("#follow-btn").click(function(){"1"==$(this).attr("val")&&"0"==$(this).attr("doing")?($(this).attr("doing","1"),$("#follow-num").removeClass("am-animation-scale-down"),$("#follow-btn").removeClass("am-animation-scale-down"),$.ajax({url:t+"/followed_info/?method=follow",type:"post",success:function(e){var n=$("#follow-btn"),i=$("#follow-num");n.attr("doing","0");var o=/^[0-9]+.?[0-9]*$/;o.test(e)?(i.text(e).addClass("am-animation-scale-down"),n.text("已关注").addClass("am-animation-scale-down").attr("val","2").hover(function(){$(this).text("取消")},function(){$(this).text("已关注")})):"login"==e&&(window.location.href="/accounts/login/?next="+t)}})):"2"==$(this).attr("val")&&"0"==$(this).attr("doing")&&($(this).attr("doing","1"),$("#zd-alert-guanzhu").modal({width:280,onConfirm:function(){$.ajax({url:t+"/followed_info/?method=unfollow",type:"post",success:function(e){var n=$("#follow-btn"),i=$("#follow-num");n.attr("doing","0");var o=/^[0-9]+.?[0-9]*$/;o.test(e)?(i.text(e),n.text("+ 关注").attr("val","1").hover(function(){$(this).text("+ 关注")},function(){$(this).text("+ 关注")})):"login"==e&&(window.location.href="/accounts/login/?next="+t)}})}}).on("close.modal.amui",function(){$("#follow-btn").attr("doing","0")}))})}$(document).ready(function(){var t=window.location.href;if($("#zdpost_list_li").length>0){var e=t.replace(/(\/\?[^\/]*)$/,"/post/list$1");e==t&&(e=t.replace(/#/,"")+"post/list/"),zdajax(e,"get","#zdpost_list_li","暂无数据")}zdcommentinit(),shaixuaninit(),weixinjsinit()}),$(".am-comment textarea").each(function(){this.style.height=this.scrollHeight+"px"}),$(".am-comment textarea").bind({input:function(){this.style.height=this.scrollHeight+"px"},propertychange:function(){this.style.height=this.scrollHeight+"px"}}),$(".footer-prospect-button").click(function(){$(".footer-prospect").hide()}),$(".am-footer-button").click(function(){$(".am-footer-miscs").hide()}),$(".footer-prospect-share").click(function(){$(".am-footer-miscs").show()}),$(function(){var t=$(".detail_describle").height();t>143&&($(".detail_describle").addClass("detail_hideline"),$("#morebuttons").show())}),$("#morebuttons").on("click",function(){$(this).find("span").hasClass("briefcontent")?($(".detail_describle.detail_hideline").removeClass("detail_hideline"),$(this).find(".am-icon-align-left").removeClass("am-icon-align-left").addClass("am-icon-arrow-up"),$(this).find("em").text("收起详情"),$(this).find("span").removeClass("briefcontent").addClass("allcontent")):($(".detail_describle").addClass("detail_hideline"),$(this).find(".am-icon-arrow-up").removeClass("am-icon-arrow-up").addClass("am-icon-align-left"),$(this).find("em").text("显示全部"),$(this).find("span").removeClass("allcontent").addClass("briefcontent"))}),$(function(){function t(t){$("#qrcodebox").empty().qrcode({text:t,width:320,height:320})}function e(t){var e=(new Image,t.toDataURL("image/png"));$("#qrcodeimg").attr("src",e)}$("#qrcode").on("click",function(){t(location.href);var n=$("#qrcodebox canvas").get(0);e(n)})}),$(function(){isWeiXin()?$("#wechatshare").css("display","block"):$("#normalshare").css("display","block")});