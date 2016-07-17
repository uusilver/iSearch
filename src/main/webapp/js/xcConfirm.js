/*
 * ʹ��˵��:
 * window.wxc.Pop(popHtml, [type], [options])
 * popHtml:html�ַ���
 * type:window.wxc.xcConfirm.typeEnum�����е�Ԫ��
 * options:��չ����
 * �÷�:
 * 1. window.wxc.xcConfirm("���ǵ���<span>lalala</span>");
 * 2. window.wxc.xcConfirm("�ɹ�","success");
 * 3. window.wxc.xcConfirm("������","input",{onOk:function(){}})
 * 4. window.wxc.xcConfirm("�Զ���",{title:"�Զ���"})
 */
(function($){
	window.wxc = window.wxc || {};
	window.wxc.xcConfirm = function(popHtml, type, options) {
	    var btnType = window.wxc.xcConfirm.btnEnum;
		var eventType = window.wxc.xcConfirm.eventEnum;
		var popType = {
			info: {
				title: "��Ϣ",
				icon: "0 0",//��ɫi
				btn: btnType.ok
			},
			success: {
				title: "�ɹ�",
				icon: "0 -48px",//��ɫ�Թ�
				btn: btnType.ok
			},
			error: {
				title: "����",
				icon: "-48px -48px",//��ɫ��
				btn: btnType.ok
			},
			confirm: {
				title: "��ʾ",
				icon: "-48px 0",//��ɫ�ʺ�
				btn: btnType.okcancel
			},
			warning: {
				title: "����",
				icon: "0 -96px",//��ɫ̾��
				btn: btnType.okcancel
			},
			input: {
				title: "����",
				icon: "",
				btn: btnType.ok
			},
			custom: {
				title: "",
				icon: "",
				btn: btnType.ok
			}
		};
		var itype = type ? type instanceof Object ? type : popType[type] || {} : {};//��ʽ������Ĳ���:��������
		var config = $.extend(true, {
			//����
			title: "", //�Զ���ı���
			icon: "", //ͼ��
			btn: btnType.ok, //��ť,Ĭ�ϵ���ť
			//�¼�
			onOk: $.noop,//���ȷ���İ�ť�ص�
			onCancel: $.noop,//���ȡ���İ�ť�ص�
			onClose: $.noop//�����رյĻص�,���ش����¼�
		}, itype, options);
		
		var $txt = $("<p>").html(popHtml);//�����ı�dom
		var $tt = $("<span>").addClass("tt").text(config.title);//����
		var icon = config.icon;
		var $icon = icon ? $("<div>").addClass("bigIcon").css("backgroundPosition",icon) : "";
		var btn = config.btn;//��ť�����ɲ���
		
		var popId = creatPopId();//��������
		
		var $box = $("<div>").addClass("xcConfirm");//�����������
		var $layer = $("<div>").addClass("xc_layer");//���ֲ�
		var $popBox = $("<div>").addClass("popBox");//��������
		var $ttBox = $("<div>").addClass("ttBox");//������������
		var $txtBox = $("<div>").addClass("txtBox");//��������������
		var $btnArea = $("<div>").addClass("btnArea");//��ť����
		
		var $ok = $("<a>").addClass("sgBtn").addClass("ok").text("ȷ��");//ȷ����ť
		var $cancel = $("<a>").addClass("sgBtn").addClass("cancel").text("ȡ��");//ȡ����ť
		var $input = $("<input>").addClass("inputBox");//�����
		var $clsBtn = $("<a>").addClass("clsBtn");//�رհ�ť
		
		//������ťӳ���ϵ
		var btns = {
			ok: $ok,
			cancel: $cancel
		};
		
		init();
		
		function init(){
			//������������input
			if(popType["input"] === itype){
				$txt.append($input);
			}
			
			creatDom();
			bind();
		}
		
		function creatDom(){
			$popBox.append(
				$ttBox.append(
					$clsBtn
				).append(
					$tt
				)
			).append(
				$txtBox.append($icon).append($txt)
			).append(
				$btnArea.append(creatBtnGroup(btn))
			);
			$box.attr("id", popId).append($layer).append($popBox);
			$("body").append($box);
		}
		
		function bind(){
			//���ȷ�ϰ�ť
			$ok.click(doOk);
			
			//�س�������ȷ�ϰ�ť�¼�
			$(window).bind("keydown", function(e){
				if(e.keyCode == 13) {
					if($("#" + popId).length == 1){
						doOk();
					}
				}
			});
			
			//���ȡ����ť
			$cancel.click(doCancel);
			
			//����رհ�ť
			$clsBtn.click(doClose);
		}

		//ȷ�ϰ�ť�¼�
		function doOk(){
			var $o = $(this);
			var v = $.trim($input.val());
			if ($input.is(":visible"))
		        config.onOk(v);
		    else
		        config.onOk();
			$("#" + popId).remove(); 
			config.onClose(eventType.ok);
		}
		
		//ȡ����ť�¼�
		function doCancel(){
			var $o = $(this);
			config.onCancel();
			$("#" + popId).remove(); 
			config.onClose(eventType.cancel);
		}
		
		//�رհ�ť�¼�
		function doClose(){
			$("#" + popId).remove();
			config.onClose(eventType.close);
			$(window).unbind("keydown");
		}
		
		//���ɰ�ť��
		function creatBtnGroup(tp){
			var $bgp = $("<div>").addClass("btnGroup");
			$.each(btns, function(i, n){
				if( btnType[i] == (tp & btnType[i]) ){
					$bgp.append(n);
				}
			});
			return $bgp;
		}

		//����popId,��ֹid�ظ�
		function creatPopId(){
			var i = "pop_" + (new Date()).getTime()+parseInt(Math.random()*100000);//��������
			if($("#" + i).length > 0){
				return creatPopId();
			}else{
				return i;
			}
		}
	};
	
	//��ť����
	window.wxc.xcConfirm.btnEnum = {
		ok: parseInt("0001",2), //ȷ����ť
		cancel: parseInt("0010",2), //ȡ����ť
		okcancel: parseInt("0011",2) //ȷ��&&ȡ��
	};
	
	//�����¼�����
	window.wxc.xcConfirm.eventEnum = {
		ok: 1,
		cancel: 2,
		close: 3
	};
	
	//��������
	window.wxc.xcConfirm.typeEnum = {
		info: "info",
		success: "success",
		error:"error",
		confirm: "confirm",
		warning: "warning",
		input: "input",
		custom: "custom"
	};

})(jQuery);