// JavaScript Document
function wordcount(comid , spanid)
	{
		maxLength = document.getElementById(comid).getAttribute("maxlength");
		curLength = document.getElementById(comid).value.length;
		//document.getElementById(spanid).innerHTML = "140";
		de = "";
		
		if(curLength > maxLength)
		{
			//window.alert('����̫���ˣ�');
			document.getElementById(comid).textContent = document.getElementById(comid).textContent.substr(0,140);
			return;
		}
		else
		{
		    document.getElementById(spanid).innerHTML = maxLength - curLength;
		   // window.alert(maxLength - curLength);
			return;
		}
	}
function checkInput(comid)
{
	if(document.getElementById(comid).value.length > 140)
	{
		window.alert('����̫���ˣ�');
		return false;
	}
	else
		return true;
}	

function generateCommonForm(nth)
{
	//var commonForm = "<form id = 'common-form'method='post' action='replyTrans.jsp?username=<%=p1.getUsername()%>&headImageUrl=<%=p1.getHeadSourceUrl()%>&commentId=<%=comment.get(i).getId()%>'><textarea name = 'buffer'></textarea><input name = 'checkbox' type='checkbox' /><div class='tip'>ͬʱת�����ҵ�΢��</div><input class='sys-pic comment-but' type='image' src='site/img/transparent.png' /><div class='clear'></div></form>";
	$('.comment-box').eq(nth).toggleClass('disP');
}


function password_check(password_id, password_id_check)
{
	var p1 = document.getElementById(password_id);
	var p2 = document.getElementById(password_id_check);
	
	if(p1.value != p2.value)
	{
		p2.setCustomValidity("两次密码必须输入一致！");  
	}
	else
		p2.setCustomValidity("");  
}


function passPic()
{
var pic = document.createElement("input");
pic.setAttribute("type", "file");
pic.setAttribute("name", "picture");
pic.setAttribute("id", "picture");
pic.setAttribute("style", "display:none");
document.getElementById("pic").appendChild(pic);
$("#picture").click();
}

function passHeadImage()
{
var head = document.createElement("input");
head.setAttribute("type", "file");
head.setAttribute("name", "headImage");
head.setAttribute("id", "headImage");
head.setAttribute("style", "display:none");

var sub = document.createElement("input");
sub.setAttribute("type", "submit");
sub.setAttribute("style", "display:none");
sub.setAttribute("id", "headImageSub");

var form1 = document.createElement("form");
form1.action = "headImageChange.jsp";
form1.method = "post";
form1.setAttribute("encType","multipart/form-data");
form1.appendChild(head);
form1.appendChild(sub);
document.getElementById("link-area").appendChild(form1);
$("#headImage").click();
//encType=multipart/form-data
}




//初始化：是否开启DIV弹出窗口功能
//0 表示开启; 1 表示不开启;
var popupStatus = 0; 

//使用Jquery加载弹窗  
function loadPopup(id1, id2){    
//仅在开启标志popupStatus为0的情况下加载   

if(popupStatus==0){    
$("#" + id1).css({    
"opacity": "0.7"   
});    
$("#" + id1).fadeIn("slow");    
$("#" + id2).fadeIn("slow");    
popupStatus = 1;    
}    
}   

//使用Jquery去除弹窗效果  
function disablePopup(id1, id2){    
//仅在开启标志popupStatus为1的情况下去除 

if(popupStatus==1){    
$("#" + id1).fadeOut("slow");    
$("#" + id2).fadeOut("slow");    
popupStatus = 0;    
}    
}   

function centerPopup(id1, id2){   

	//获取系统变量

	var windowWidth = document.documentElement.clientWidth;   

	var windowHeight = document.documentElement.clientHeight;   

	var popupHeight = $("#"+id2).height();   

	var popupWidth = $("#"+id2).width();   

	//居中设置   

	$("#"+id2).css({   

	"position": "absolute",   

	"top": windowHeight/2-popupHeight/2,   

	"left": windowWidth/2-popupWidth/2   

	});
	
	//以下代码仅在IE6下有效  
	
	$("#" + id1).css({   

	"height": windowHeight   

	});   

	}

$(document).ready(function(){   

	//执行触发事件的代码区域  

	});

	//当按下id为#button的按钮时触发弹出窗口的事件：

	//打开弹出窗口   

	//按钮点击事件!

	function pppop(id1, id2){   

	//调用函数居中窗口

	centerPopup(id1, id2);   

	//调用函数加载窗口

	loadPopup(id1, id2);   

	}

	//关闭弹出窗口   

	//点击"X"所触发的事件

	function dis(id1, id2){   

	disablePopup(id1, id2);   

	}   

	//点击窗口以外背景所触发的关闭窗口事件!

	$("#backgroundPopup").click(function(){   

	disablePopup(id1, id2);   

	});   

	//键盘按下ESC时关闭窗口!

	$(document).keypress(function(e){   

	if(e.keyCode==27 & popupStatus==1){   

	disablePopup(id1, id2);   

	}   

	});  




