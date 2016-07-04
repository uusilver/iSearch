function checkSel(name) {
    var objs=$(".tableBody").find("[@name="+name+"]");
    var bchecked = false;
    var s="";
	for (var i = 0; i < objs.length; i++) {
		if (objs.get(i).checked == true) {
			bchecked = true;
			s=s+objs.get(i).value+",";
		}
	}
	if (bchecked == false) {
	  alert("请至少选择一条记录!");
		return ;
	}
	return s.substr(0,s.length-1);
}

function checkSelOne(name) {
    var objs=$(".tableBody").find("[@name="+name+"]");
    var bchecked = false;
	for (var i = 0; i < objs.length; i++) {
		if (objs.get(i).checked == true) {
			bchecked = true;
		}
	}
	return bchecked;
}

function checkAll(id) {

    var objs=document.getElementsByName(id.value);
    
    for (var i = 0; i < objs.length; i++) {

        if(objs[i].value!='disabled')
          objs[i].checked=id.checked;        
    }

}


function resetform(formname) { 
    
    $("#"+formname).find("input[type=text],input[type=password],textarea,select,checkbox").each(function(){

      	 $(this).val("");

    }); 
}
