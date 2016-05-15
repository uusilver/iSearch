// JavaScript Document

//---------------------------------------------------------------清单
/*DOM操作
  <1>.hasClass(obj, sClassName);
  <2>.addClass(obj, newClass);
  <3>.removeClass(obj,delClass);
  <4>.getByClass(oParent,sClass,tagName);
  <5>.getStyle(obj,attr);  获取样式
  	  css(obj,option,val); 设置样式
  <6>.css3(obj,attr,val);
  <7>.
*/

/*BOM操作
  <1>.----browerMaster.than2(name,num,MoL);	[ name : msie，firefox，opera，chrome，safari ]  [ num ]  [ MoL : gt,gte,lt,lte,eq ]
      ----browerMaster.desMes()  浏览器的基本信息
  
  <2>.获取元素距离屏幕左侧和顶部的真实距离
      getOffsetPos(obj)
	  
  <3>.scrollH();
      offsetH();
	  scrollT();
	  getMax([viewH(),scrollH(),offsetH()])  获取遮罩层的最大高度
 
  <4>.视窗的宽度，高度
       viewH();
	   viewW();
	   
  <5>.get方式传参
	 GetRequest();
	 例子：whichIndex是参数的名字  
	 URL: http://www.baidu.com?whichIndex=1
	 var Request = new Object();
	 Request = GetRequest();
	 var actionIndex = Request['whichIndex'];
	 if(actionIndex){
		setTimeout(function(){
			aBtns.eq(actionIndex).trigger('click');
		},500);
	 }	
	 
  <6>.PC端判断 返回布尔值
      isPc();   
  	  
*/

/*UI
  <1>.自定义滚动条 scrollNorm.scrollMoveTo(val); 移动到某处
                 scrollNorm.resizeFn();  滚动条resize				 
  		 				   				   
*/

/*基础层
  <1>.getMax(arr);
  <2>.bindEvent(obj,evt,fn);
  <3>.delEvent(obj,events,fn);
  <4>.date_getTime();  获取时间 ms

*/

/*运动
   <0>.scrollToTar(tar,fn); //滑动到某个区域
   <1>.oldStartMove(obj, json, fn);   
   <2>.startMove(obj,json,times,fx,fn,fnMove);   
   <3>.shake(obj,attr,callBack);
   <4>.getClose(obj,target);
*/

/*移动端开发
  
  <1>  function touch( json, toLeftFn, toRightFn, toMoveFn )	
  //json: {  obj:'', dirType:'pageX'/'pageY', moveObj:'', endObj:'' }
  //toLeftFn : 左/上
  //toRightFn : 右/下
  //toMoveFn: 移动中回调 ( 移动的新坐标，移动的距离 )

*/

//--------------------------------------------------------------[ LAB ]
//---------------【 DOM 】
function addClass(obj,newClass){
	var oldClass = obj.className;
	var oldClassArr = [];
	
	if(oldClass=='' || oldClass==null){
		obj.className = newClass;	
	}else{
		oldClassArr = oldClass.split(' ');	
		for(var i=0; i<oldClassArr.length; i++){
			if(oldClassArr[i]!=' '&& oldClassArr[i]==newClass){
				break;	
			}	
		}
		obj.className = oldClass+' '+newClass;
	} 	
}

function removeClass(obj,delClass){
	var oldClass = obj.className;
	var oldClassArr = [];
	var newClassArr = [];
	
	if(oldClass=='' || oldClass==null){
		return;
	}else{
		oldClassArr = oldClass.split(' ');	
		for(var i=0; i<oldClassArr.length; i++){
			if(oldClassArr[i]!=' '&& oldClassArr[i]!=delClass){
				newClassArr.push(oldClassArr[i]);
			}	
		}
		obj.className = newClassArr.join(' ');
	} 	
}

function getByClass(oParent,sClass,tagName){
	var aEle = [],result = [];
	if( tagName && typeof tagName == 'string'){
		aEle = oParent.getElementsByTagName(tagName);	
	}else{
		aEle = oParent.getElementsByTagName('*');
	}
	
	if(!tagName && oParent.getElementsByClassName){
        return oParent.getElementsByClassName(sClass); //性能考虑
    }else{
		var re = new RegExp('(^|\\s)' + sClass + '($|\\s)', 'i');
		for(var i = 0; i < aEle.length; i++){
			if(re.test(aEle[i].className)){
				result.push(aEle[i]);
			}
		}
		return result;
    }
}

function hasClass(obj, sClassName){
    var hasBtn = false;
    var sClass =  obj.className;                  
    if (sClass == '') {							  
        hasBtn = false;
    } else {									  
        var aClass = sClass.split(' ');				
        for (var i=0; i<aClass.length; i++) {	  
            if (aClass[i] == sClassName) {
                hasBtn = true;                          
            }
        }
    }
    return hasBtn;
}

/*function css(obj,attr,val){
    if(!val){
		return obj.currentStyle ? obj.currentStyle[attr] : getComputedStyle(obj,false)[attr];
    }else{
        obj.style[attr] = val;
    }
}*/

function getStyle( obj, attr ){
	function getResult( rAttr ){
		return obj.currentStyle ? obj.currentStyle[rAttr] : getComputedStyle( obj )[rAttr]
	};
	
	var result = getResult( attr );
	if( attr === "opacity" && typeof result === "undefined" ) return 1;
	if( result === "auto" ){
		
		if( attr !== "width" && attr !== "height" && attr.indexOf("margin") === -1 && attr !== "left" && attr !== "right" && attr !== "top" && attr !== "bottom" ) return result;
		var sPosition = getStyle(obj, "position");
		
		if( sPosition !== "absolute" && sPosition !== "fixed" ){
			switch( attr ){
				case "marginLeft"  : return getOffsetPos(obj).l - getOffsetPos(obj.parentNode).t + "px";
				case "marginRight" : return obj.parentNode.offsetWidth - obj.offsetWidth - ( getOffsetPos(obj).l - getOffsetPos(obj.parentNode).l ) + "px";
				case "marginBottom": return obj.parentNode.offsetHeight - obj.offsetHeight - ( getOffsetPos(obj).t - getOffsetPos(obj.parentNode).t ) + "px";
				case "width"       : return obj.parentNode.offsetWidth + "px";
			};
		};
		
		if( attr === "width" || attr === "height" || attr.indexOf("margin") !== -1 || attr === "left" || attr === "right" || attr === "top" || attr === "bottom" && sPosition === "absolute" || sPosition === "fixed" ){
			return 0 + "px";
		};
	};
	return result;
}

function css( obj, option, val ){
	if( isJson( option ) ){
		for( var attr in option ){
			if( attr !== "opacity" ){
				var temp = Number(option[attr]);
				var newVal = typeof option[attr] === "number" && temp === temp ? option[attr] + "px" : option[attr];
				obj.style[attr] = newVal;
			} else {
				obj.style[attr] = option[attr];
				obj.style.filter = 'alpha(opacity='+ option[attr]*100 +')';
			};
		};
	}else{
		if(!val){
			return getStyle(obj,option);
		}else{
			obj.style[option] = val;
		};
	};
}

function css3(obj,attr,val){
	var str = attr.charAt(0).toUpperCase() + attr.substring(1);
	
	obj.style['Webkit'+str] = val;
	obj.style['Moz'+str] = val;
	obj.style['ms'+str] = val;
	obj.style['O'+str] = val;
	obj.style[attr] = val;
} 

function getChildAttr( oParent, tagName, attr ){
	var aEle = oParent.getElementsByTagName(tagName);
	var arr = [];
	attr = attr === "class" && aEle[0].getAttribute("className") !== null ? "className" : attr;
	
	for(var i = 0; i < aEle.length; i++){
		var attrVal = attr === "style" ? aEle[i].style.cssText : aEle[i].getAttribute(attr);
		
		if( attrVal === "" || typeof attrVal !== "string" ){
			continue;
		} else {
			arr.push( attrVal );
		};
	};
	return arr;
}

//JSON类型判断
function isJson(obj){
	var result = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
	return result;
}

//-------------------【 BOM 】
function viewW(){
	return document.documentElement.clientWidth;		
}

function viewH(){
	return document.documentElement.clientHeight;	
}

function scrollH(){
	return document.body.scrollHeight;	
}

function offsetH(){
	return document.body.offsetHeight;	
}

function scrollT(){
	return document.body.scrollTop || document.documentElement.scrollTop;	
}

function getOffsetPos(obj){
	var result = {'l':0,'t':0};
	while(obj){
		result.l += obj.offsetLeft;	
		result.t += obj.offsetTop;	
		obj = obj.offsetParent;
	}
	return result;	
}

//get方式传参
function GetRequest() {
   var url = location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}

//PC端判断
function isPc() {
	var userAgentInfo = navigator.userAgent;
	var agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");
	var flag = true;
	for (var v = 0; v < agents.length; v++) {
		if (userAgentInfo.indexOf(agents[v]) > 0) { flag = false; break; }
	}
	return flag;
}

//浏览器版本
var browerMaster = (function(){
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
	(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
	(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
	(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

	//浏览器版本信息
	//Opera浏览器用的是Chrome的内核
	function fn(){
		if(Sys.ie){
			return 'IE: ' + Sys.ie;
		} 
		if(Sys.firefox){
			return  'Firefox: ' + Sys.firefox;
		} 
		if(Sys.chrome){
			return  'Chrome: ' + Sys.chrome;
		} 
		if(Sys.opera){
			return  'Opera: ' + Sys.opera;
		} 
		if(Sys.safari){
			return  'Safari: ' + Sys.safari;
		}
		if(ua.match(/trident/) && ua.match(/rv/) && ua.match(/11/)){
			return 'IE: ' + 11;
		}	
	}
	
	//浏览器类型( name : msie，firefox，opera，chrome，safari )  //浏览器版本号 (num)  //对比的关系 ( MoL : gt,gte,lt,lte,eq )
	function fn2(name,num,MoL){
		//比对版本
		if(isNaN(Number(num))){
			return false;	
		}else{
			var num = Number(num);	
		}
		//符号
		switch(MoL){
			case 'gt' : MoL = '>';
					  break;
			case 'gte' : MoL = '>=';
					   break;
			case 'lt' : MoL = '<';
					  break;
			case 'lte' : MoL = '<=';
					   break;
			case 'eq' : MoL = '==';	
					  break;		   		  		  		  	
		}
		//浏览器
		switch(name){
			case 'msie' : return eval(Number(parseInt(Sys.ie))+MoL+num) ? true : false;
					      break;
			case 'firefox' : return eval(Number(parseInt(Sys.firefox))+MoL+num) ? true : false;
					       break;
			case 'opera' :  return eval(Number(parseInt(Sys.chrome))+MoL+num) ? true : false;
					     break;
			case 'chrome' : return eval(Number(parseInt(Sys.chrome))+MoL+num) ? true : false;
					      break;
			case 'safari' : return eval(Number(parseInt(Sys.safari))+MoL+num) ? true : false;
					  	  break;		  		  	  	
		}	
	}	
	
	return {
		desMes : fn,
		than2 : fn2
	}	
})();

//----------------------【 Base 】

//绑定事件
function bindEvent(obj,evt,fn){
	if(obj.addEventListener){
	   obj.addEventListener(evt,fn,false);
	}else{
		obj.attachEvent('on'+evt,function(){
			fn.call(obj);	
		})	
	}	
}

//删除事件
function delEvent(obj,events,fn){
	if( obj.removeEventListener ){
		obj.removeEventListener(events,fn,false);
	}
	else{
		obj.detachEvent('on'+events,fn);
	}
}

function getMax(arr){ 
	var iMin = -999999; 	
	for(var i=0; i<arr.length; i++){	
	   if(arr[i]>iMin){		
		  iMin = arr[i];			
	   }			
	}	
	return iMin;	
}

function date_getTime(){
	var time = new Date();
	var now = time.getTime();
	return now;	
}

//----【 数组 】
//原型拓展-删除
Array.prototype.remove = Array.prototype.remove || function (n){
	for(var i=0;i<this.length;i++)
	{
		if(this[i]==n)
		{
			this.splice(i, 1);
			i--;
		}
	}
};

//------------------------【 UI 】

//自定义滚动条
function scrollNorm(obj,fn){
	
	if(!document.getElementById(obj)){return false;}
	var oBox = document.getElementById(obj);
	
	var inColor = '#999';
	var outColor = '#666';
	var iT = 0;	
	var clickT = 0;
	var person = 0;
	var iBtnTime = null;
	var sSpeed = 4;
	var scrollBarW = 15;
	
	//左侧容器
	var oldcontent = oBox.children[0];
	var contentOffsetH = document.createElement('div');
	contentOffsetH.style.cssText = '\
	position:absolute;\
		 top:0px;\
		 left:0px;\
	  z-index:999;\
	   width:100%'; 
	contentOffsetH.innerHTML = oldcontent.innerHTML;	
	oBox.removeChild(oldcontent);   
	
	var contentWindow = document.createElement('div');
	contentWindow.style.cssText = 'position:absolute;\
		  top:0px;\
		 left:0px;\
	  z-index:999;\
	    width:'+(oBox.clientWidth-scrollBarW-5)+'px;\
	   height:100%;\
	   overflow:hidden;';
	oBox.appendChild(contentWindow);
	contentWindow.appendChild(contentOffsetH);
	
	//滚动条按钮
	var scrollBtnBox = document.createElement('div');
	scrollBtnBox.style.cssText = 'position:absolute;\
		 top:20px;\
		 right:0px;\
	  z-index:999;\
	   height:'+(oBox.clientHeight-40)+'px;\
	   width:'+scrollBarW+'px;\
	   background:#222';
	oBox.appendChild(scrollBtnBox);
	
	var scrollBtn = document.createElement('div');
	scrollBtn.style.cssText = 'position:absolute;\
		 top:0;\
		left:0;\
	  z-index:999;\
	   width:'+scrollBarW+'px;\
	   height:0;\
	   background-color:'+outColor+';\
	   font-size:0;\
	   line-height:0;\
	   overflow:hidden;\
	   cursor:pointer;';
	scrollBtnBox.appendChild(scrollBtn);   
	
	//上下按钮
	var topBtn = document.createElement('div');
	topBtn.style.cssText = '\
	position:absolute;\
		 top:0;\
		right:0px;\
	  z-index:999;\
	   width:'+scrollBarW+'px;\
	   height:15px;\
	   background-color:'+outColor+';\
	   font-size:0;\
	   line-height:0;\
	   overflow:hidden;\
	   cursor:pointer;';
	oBox.appendChild(topBtn);
	
	var btmBtn = document.createElement('div');
	btmBtn.style.cssText = '\
	position:absolute;\
	  bottom:0;\
		right:0px;\
	  z-index:999;\
	   width:'+scrollBarW+'px;\
	   height:15px;\
	   background-color:'+outColor+';\
	   font-size:0;\
	   line-height:0;\
	   overflow:hidden;\
	   cursor:pointer;';
	oBox.appendChild(btmBtn);
	
	if(scrollBtn&&scrollBtnBox&&contentWindow&&contentOffsetH){			
		
		//运动比例
		function upAndBtm(){
			if(iT<0){
				iT = 0;	
			}else if(iT>(scrollBtnBox.offsetHeight-scrollBtn.offsetHeight)){
				iT = scrollBtnBox.offsetHeight-scrollBtn.offsetHeight;	
			}
			person = iT/(scrollBtnBox.offsetHeight-scrollBtn.offsetHeight);
					
			if(contentOffsetH.offsetHeight > contentWindow.offsetHeight){
				//contentOffsetH.style.top = -person*(contentOffsetH.offsetHeight - contentWindow.offsetHeight)+"px";	
				var theTop = -person*(contentOffsetH.offsetHeight - contentWindow.offsetHeight);	
				oldStartMove(contentOffsetH,{top:Math.floor(theTop)});
			}
			fn && fn();
			scrollBtn.style.top = iT + 'px';
			//oldStartMove(scrollBtn,{top:iT});
		}
		
		//调整滚动条状态
		function resizeIt(){
			//右侧滚动条高度以及显示与否
			if(contentOffsetH.offsetHeight < contentWindow.offsetHeight){
				//alert('移除滚动条');
				scrollBtnBox.style.display = 'none';
				if(topBtn&&btmBtn){
					 topBtn.style.display = btmBtn.style.display = 'none';
				}
				iT = 0;
				oldStartMove(contentOffsetH,{top:0});
				scrollBtn.style.top = '0';
			}else{
				//alert('添加滚动条');
				scrollBtnBox.style.display = 'block';
				scrollBtnBox.style.visibility = 'visible';
				if(topBtn&&btmBtn){
					 topBtn.style.display = btmBtn.style.display = 'block';
					 topBtn.style.visibility = btmBtn.style.visibility = 'visible';
				}
				
				//scrollBtn's height				
				//scrollBtn.style.height = Math.floor(scrollBtnBox.offsetHeight*contentWindow.offsetHeight/contentOffsetH.offsetHeight)+'px';
				oldStartMove(scrollBtn,{height:(Math.floor(scrollBtnBox.offsetHeight*contentWindow.offsetHeight/contentOffsetH.offsetHeight))});
				if(browerMaster.than2('msie',10,'lt')){
					setTimeout(function(){
						upAndBtm();
					},300)
				}else{
					//addClass(scrollBtn,'ts3');addClass(contentOffsetH,'ts3');
					//setTimeout(function(){
						upAndBtm();
						//setTimeout(function(){
						//	removeClass(scrollBtn,'ts3');removeClass(contentOffsetH,'ts3');
						//},300)
					//},300);
				}
				
			};	
		}
		resizeIt();
		
		//点击滚动条某一位置，动作
		scrollBtnBox.onclick = function(ev){
			var oEv = ev || window.event;	
			var oT = oEv.clientY + scrollT() - getOffsetPos(scrollBtnBox).t - clickT; 
			iT = oT;
			upAndBtm();
			oEv.cancelBubble = true;
		}
		
		//滑到某一位置
		function scrollMoveTo(val){
			//addClass(contentOffsetH,'ts3');addClass(scrollBtn,'ts3');
			//contentOffsetH.style.top = val+'px';
			oldStartMove(contentOffsetH,{top:val});
			person = -val/(contentOffsetH.offsetHeight - contentWindow.offsetHeight);
			iT = Math.floor(person*(scrollBtnBox.offsetHeight-scrollBtn.offsetHeight));
			//scrollBtn.style.top = iT + 'px';
			oldStartMove(scrollBtn,{top:iT});
			fn && fn();
			//setTimeout(function(){
				//removeClass(contentOffsetH,'ts3');removeClass(scrollBtn,'ts3');
			//},300);		
		}
		
		//按钮滑入滑出效果
		if(scrollBtn){
			scrollBtn.onmouseover = function(){ this.style.backgroundColor = inColor;};
			scrollBtn.onmouseout = function(){ this.style.backgroundColor = outColor;};
		}
		if(topBtn){
			topBtn.onmouseover = function(){ this.style.backgroundColor = inColor;};
			topBtn.onmouseout = function(){ this.style.backgroundColor = outColor;};
		}
		if(btmBtn){
			btmBtn.onmouseover = function(){ this.style.backgroundColor = inColor;};
			btmBtn.onmouseout = function(){ this.style.backgroundColor = outColor;};
		}
		
		//上下按钮 函数
		function btnUpDown(obj,num){
			obj.onmousedown = function(){
				clearInterval(iBtnTime);
				iBtnTime = setInterval(function(){
					iT = scrollBtn.offsetTop - num;				
					upAndBtm();	
				},40);
			}
			obj.onmouseup = function(){
				clearInterval(iBtnTime);	
			}	
		}
		if(topBtn){
			btnUpDown(topBtn,sSpeed);
		}
		if(btmBtn){
			btnUpDown(btmBtn,-sSpeed);
		}
		
		//拖拽按钮
		scrollBtn.onmousedown = function(ev){
			//clearTimer
			clearInterval(iBtnTime);	
			var ev = ev || event; 
			var disY = ev.clientY - scrollBtn.offsetTop;
			if(scrollBtn.setCapture){
				scrollBtn.setCapture();	
			}
			document.onmousemove = function(ev){
				var ev = ev || event;
				iT = ev.clientY - disY;
				upAndBtm();	
				clickT = ev.clientY + scrollT() - getOffsetPos(scrollBtn).t;
			}
			document.onmouseup = function(){
				document.onmousemove = null;
				document.onmouseup = null;
				if(scrollBtn.releaseCapture){
					scrollBtn.releaseCapture();	
				}	
			}
			return false;	
		}
		
		//鼠标滚动函数
		var myScrollBtn = '';
		function myScroll(ev){
			var ev = ev || event;
			ev.cancelBubble = true;
			if(ev.detail){
				if(ev.detail>0){
					myScrollBtn = false;
				}else{
					myScrollBtn = true;
				}	
			}
			if(ev.wheelDelta){
				if(ev.wheelDelta>0){
					myScrollBtn = true;
				}else{
					myScrollBtn = false;
				}	
			}	
		
			if(!myScrollBtn){
				iT = scrollBtn.offsetTop + sSpeed;	
			}else{
				iT = scrollBtn.offsetTop - sSpeed;
			}
			
			upAndBtm();
			
			if(ev.preventDefault){
				ev.preventDefault();	
			}else{
				return false;	
			}
		}
		
		//绑定滚动事件
		if(scrollBtnBox.addEventListener){
			scrollBtnBox.removeEventListener("DOMMouseScroll",myScroll,false);
			contentWindow.removeEventListener("DOMMouseScroll",myScroll,false);
			scrollBtnBox.addEventListener("DOMMouseScroll",myScroll,false);	
			contentWindow.addEventListener("DOMMouseScroll",myScroll,false);
		}
		scrollBtnBox.onmousewheel = null;
		contentWindow.onmousewheel = null;
		scrollBtnBox.onmousewheel = myScroll;
		contentWindow.onmousewheel = myScroll;
		
		return {
			resizeFn : resizeIt,
			scrollMoveFn : scrollMoveTo	
		}
		
	}
}

//------------------------------------------运动
//滚动到某个区域
function scrollToTar(tar,fn){
	var canScroll = getMax([viewH(),scrollH(),offsetH()]) - viewH();		
	if(tar>canScroll){ tar = canScroll;}
	clearInterval(document.iTimer);
	var iSpeed = 0;
	var iCur = 0;
	document.iTimer = setInterval(function(){
		var iBtn = true;
		
		iCur = scrollT();
		iSpeed = (tar - iCur)/4;
		iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
		if(iCur != tar){
			iBtn = false;
		}
		document.body.scrollTop = document.documentElement.scrollTop = iCur + iSpeed;
		
		if(iBtn){
			clearInterval(document.iTimer);
			fn && fn();
		}
	}, 30);
}


//不能规定时间的运动
function oldStartMove(obj, json, fn){
    clearInterval(obj.iTimer);
    var iSpeed = 0;
    var iCur = 0;
    obj.iTimer = setInterval(function(){
        var iBtn = true;
        for(var attr in json){
            
            if(attr == 'opacity'){
                iCur = Math.round(parseFloat(getStyle(obj, 'opacity')) * 100);
            }else{
                iCur = parseInt(getStyle(obj, attr));
            }
            
            iSpeed = (json[attr] - iCur)/4;
            iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
            
            if(iCur != json[attr]){
                iBtn = false;
            }
            
            if(attr == 'opacity'){
                obj.style.opacity = (iCur + iSpeed) / 100;
                obj.style.filter = 'alpha(opacity='+ (iCur + iSpeed) +')';
            }else{
                obj.style[attr] = iCur + iSpeed + 'px';
            }
            
        }
        if(iBtn){
            clearInterval(obj.iTimer);
            fn && fn();
        }
    }, 30);
}   

//新的运动
function startMove( obj, json, times, fx, fn, fnMove ){
	var isCallBack = true;
	var iCur = {};
	var type = {};
	var timer = null;
	for(var attr in json){
		if(attr == 'opacity'){
			iCur[attr] = Math.round(getStyle(obj,attr)*100);
		} else {
			iCur[attr] = parseInt(getStyle(obj,attr));
			type[attr] = typeof json[attr] === "string" && json[attr].indexOf("%") !== -1 ? "%" : "px";
			json[attr] = parseInt( json[attr] );
		};
	};
	var startTime = date_getTime();
	clearInterval(timer);
	timer = setInterval(function(){
		var changeTime = date_getTime();
		var scale = 1 - Math.max(0,(startTime - changeTime + times)/times);
		//运动过程中的函数
		if(fnMove){
			fnMove(scale);
		};
		
		for(var attr in json){
			var value = 0;
			fx = typeof fx == "undefined" || fx == "" ? "def" : fx;
			
			value = Tween[fx](scale*times, iCur[attr] , json[attr] - iCur[attr] , times );
			if(attr == 'opacity'){
				obj.style.filter = 'alpha(opacity='+ value +')';
				obj.style.opacity = value/100;
			} else {
				//百分比情况
				obj.style[attr] = value + type[attr];
			};
			
			if(scale == 1){
				clearInterval(timer);
				if(fn && isCallBack){
					fn.call(obj);
					isCallBack = false;
				};
			};
			
		};
		
	},13);
	
	var Tween = {
			def: function (t, b, c, d) {
				return this["easeOutQuad"](t, b, c, d);
			},
			easeInQuad: function (t, b, c, d) {
				return c*(t/=d)*t + b;
			},
			easeOutQuad: function (t, b, c, d) {
				return -c *(t/=d)*(t-2) + b;
			},
			easeInOutQuad: function (t, b, c, d) {
				if ((t/=d/2) < 1) return c/2*t*t + b;
				return -c/2 * ((--t)*(t-2) - 1) + b;
			},
			easeInCubic: function (t, b, c, d) {
				return c*(t/=d)*t*t + b;
			},
			easeOutCubic: function (t, b, c, d) {
				return c*((t=t/d-1)*t*t + 1) + b;
			},
			easeInOutCubic: function (t, b, c, d) {
				if ((t/=d/2) < 1) return c/2*t*t*t + b;
				return c/2*((t-=2)*t*t + 2) + b;
			},
			easeInQuart: function (t, b, c, d) {
				return c*(t/=d)*t*t*t + b;
			},
			easeOutQuart: function (t, b, c, d) {
				return -c * ((t=t/d-1)*t*t*t - 1) + b;
			},
			easeInOutQuart: function (t, b, c, d) {
				if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
				return -c/2 * ((t-=2)*t*t*t - 2) + b;
			},
			easeInQuint: function (t, b, c, d) {
				return c*(t/=d)*t*t*t*t + b;
			},
			easeOutQuint: function (t, b, c, d) {
				return c*((t=t/d-1)*t*t*t*t + 1) + b;
			},
			easeInOutQuint: function (t, b, c, d) {
				if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
				return c/2*((t-=2)*t*t*t*t + 2) + b;
			},
			easeInSine: function (t, b, c, d) {
				return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
			},
			easeOutSine: function (t, b, c, d) {
				return c * Math.sin(t/d * (Math.PI/2)) + b;
			},
			easeInOutSine: function (t, b, c, d) {
				return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
			},
			easeInExpo: function (t, b, c, d) {
				return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
			},
			easeOutExpo: function (t, b, c, d) {
				return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
			},
			easeInOutExpo: function (t, b, c, d) {
				if (t==0) return b;
				if (t==d) return b+c;
				if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
				return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
			},
			easeInCirc: function (t, b, c, d) {
				return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
			},
			easeOutCirc: function (t, b, c, d) {
				return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
			},
			easeInOutCirc: function (t, b, c, d) {
				if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
				return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
			},
			easeInElastic: function (t, b, c, d) {
				var s=1.70158;var p=0;var a=c;
				if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
				if (a < Math.abs(c)) { a=c; var s=p/4; }
				else var s = p/(2*Math.PI) * Math.asin (c/a);
				return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
			},
			easeOutElastic: function (t, b, c, d) {
				var s=1.70158;var p=0;var a=c;
				if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
				if (a < Math.abs(c)) { a=c; var s=p/4; }
				else var s = p/(2*Math.PI) * Math.asin (c/a);
				return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
			},
			easeInOutElastic: function (t, b, c, d) {
				var s=1.70158;var p=0;var a=c;
				if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (!p) p=d*(.3*1.5);
				if (a < Math.abs(c)) { a=c; var s=p/4; }
				else var s = p/(2*Math.PI) * Math.asin (c/a);
				if (t < 1) return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
				return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b;
			},
			easeInBack: function (t, b, c, d, s) {
				if (s == undefined) s = 1.70158;
				return c*(t/=d)*t*((s+1)*t - s) + b;
			},
			easeOutBack: function (t, b, c, d, s) {
				if (s == undefined) s = 1.70158;
				return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
			},
			easeInOutBack: function (t, b, c, d, s) {
				if (s == undefined) s = 1.70158; 
				if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
				return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
			},
			easeInBounce: function (t, b, c, d) {
				return c - this["easeOutBounce"] (d-t, 0, c, d) + b;
			},
			easeOutBounce: function (t, b, c, d) {
				if ((t/=d) < (1/2.75)) {
					return c*(7.5625*t*t) + b;
				} else if (t < (2/2.75)) {
					return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
				} else if (t < (2.5/2.75)) {
					return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
				} else {
					return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
				}
			},
			easeInOutBounce: function (t, b, c, d) {
				if (t < d/2) return this["easeInBounce"] (t*2, 0, c, d) * .5 + b;
				return this["easeOutBounce"] (t*2-d, 0, c, d) * .5 + c*.5 + b;
			}
		}
}

//震荡
function shake( obj, attr, callBack ){
	if ( obj.onOff ) return;
	obj.onOff = true;
	
	var pos = parseInt(getStyle(obj, attr));	
	var arr = [];			// 20, -20, 18, -18 ..... 0
	var num = 0;
	var timer = null;
		
	for ( var i=20; i>0; i-=2 ) {
		arr.push( i, -i );
	}
	arr.push(0);
		
	clearInterval( obj.shake );
	obj.shake = setInterval(function (){
		obj.style[attr] = pos + arr[num] + 'px';
		num++;
		if ( num === arr.length ) {
			clearInterval( obj.shake );
			callBack && callBack();
			obj.onOff = false;
		}
	}, 50);
}

//碰撞
function getClose(obj,target){
	var L1 = obj.offsetLeft;
	var T1 = obj.offsetTop;
	var W1 = obj.offsetWidth;
	var H1 = obj.offsetHeight;
	
	var L2 = target.offsetLeft;
	var T2 = target.offsetTop;
	var W2 = target.offsetWidth;
	var H2 = target.offsetHeight;

	if( (W1+L1)<L2 || L1>(W2+L2) || (H1+T1)<T2 || T1>(H2+T2) ){
		return false;
	}else{
		return true;
	}
}

//移动端开发
//json: {  obj:'', dirType:'pageX'/'pageY', moveObj:'', endObj:'' }
//toLeftFn : 左/上
//toRightFn : 右/下
//toMoveFn: 移动中回调 ( 移动的新坐标，移动的距离 )
function touch( json, toLeftFn, toRightFn, toMoveFn ){
	var obj = null;
	if( typeof json.obj !== "undefined" ){
		obj = json.obj;
	} else {
		return;
	};
	var startObj = typeof json.startObj === "object" ? json.startObj : obj;
	var endObj = typeof json.endObj === "object" ? json.endObj : obj;
	var moveObj = typeof json.moveObj === "object" ? json.moveObj : obj;
	
	var dirStr = json.dirType !== "pageY" ? "pageX" : json.dirType;
	var dirJson = {
		"page"    : dirStr,
		"offsetLT": dirStr === "pageX" ? "offsetLeft" : "offsetTop",
		"offsetWH": dirStr === "pageX" ? "offsetWidth" : "offsetHeight"
	};
	
	var downVal = 0;
	var downLT = 0;
	var downTime = 0;
	var allowMove = false;
	
	function startFn(ev){
		
		var ev = ev || event;
		var touchs = typeof ev.changedTouches === "undefined" ? ev : ev.changedTouches[0];
		
		downVal = touchs[dirJson.page];
		downLT = obj[dirJson.offsetLT];
		downTime = date_getTime();
		allowMove = true;
		
	};
	
	function moveFn(ev){
		
		if( typeof toMoveFn !== "function" || !allowMove )return;
		
		var ev = ev || event;
		var touchs = typeof ev.changedTouches === "undefined" ? ev : ev.changedTouches[0];
		
		var touchDistance = touchs[dirJson.page] - downVal;
		var moveVal = touchDistance + downLT;
		
		//移动中...
		toMoveFn.call( obj, moveVal, touchDistance );
		
	};
	
	function endFn(ev){
		if ( !allowMove ) return false;
		allowMove = false;
		
		var ev = ev || event;
		var touchs = typeof ev.changedTouches === "undefined" ? ev : ev.changedTouches[0];
		
		var endX = touchs[dirJson.page] - downVal;
		if( endX < 0 ){//←
			if( (Math.abs(endX) > obj[dirJson.offsetWH]/3 || (date_getTime() - downTime < 300 && Math.abs(endX) > 30 )) ){
				
				//dirJson.page = "pageX"向左运动； dirJson.page = "pageY"向上运动
				typeof toLeftFn === "function" ? toLeftFn.call( obj ) : "";
				
			}
		} else {//→
			if( (Math.abs(endX) > obj[dirJson.offsetWH]/3 || (date_getTime() - downTime < 300 && Math.abs(endX) > 30 )) ){
				//dirJson.page = "pageX"向右运动； dirJson.page = "pageY"向下运动
				typeof toRightFn === "function" ? toRightFn.call( obj ) : "";
				
			}
		};
	};
	
	//事件绑定
	if(isPc()){

		bindEvent( moveObj, "mousedown", function(ev){
			ev.preventDefault();
		});
		bindEvent( startObj, "mousedown", startFn );
		bindEvent( moveObj, "mousemove", moveFn );
		bindEvent( endObj, "mouseup", endFn );
		
	} else {
		
		bindEvent( moveObj, "touchmove", function(ev){
			ev.preventDefault();
		});
		bindEvent( startObj, "touchstart", startFn );
		bindEvent( moveObj, "touchmove", moveFn );
		bindEvent( endObj, "touchend", endFn );
		
	};
	
}

//手机屏幕方向的检测
function addListenScreen(){
	function listenScreen(){
		switch(window.orientation){
			case 0 :  alert(1);
					   break;
			case 90 : alert(2);
					   break;
			case 180 : alert(3);
					   break;
			case -90 : alert(4);
					   break;		   		   		   	
		}
	};
	listenScreen();
	window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", listenScreen, false);
}

//弧度转换角度
function a2d(n){
	return n*180/Math.PI;
}

