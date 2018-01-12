var ifr = document.getElementById('ifr');
function aa(a) {
	ifr.src = a + '.html';
}
function getdates() {
	var w_array = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var week = d.getDay();
	var h = d.getHours();
	var mins = d.getMinutes();
	var s = d.getSeconds();

	if (month < 10)
		month = "0" + month
	if (day < 10)
		month = "0" + day
	if (h < 10)
		h = "0" + h
	if (mins < 10)
		mins = "0" + mins
	if (s < 10)
		s = "0" + s

	var shows = " <span>" + h + ":" + mins + ":" + s + " " + w_array[week]
			+ "</span>";
	document.getElementById("date").innerHTML = shows;
	setTimeout("getdates()", 1000);
}
getdates();
//以下为张金高改
$(document).ready(function(){
    $('.out').click(function(e) {
        $(this).next().stop().slideToggle(); 
        $(this).parent("li.lione").siblings().children("ul").slideUp();
        e.preventDefault();
    })
    
    $('.out1').click(function(){//当点击侧边栏时使头部背景色取消
 		$(this).css("backgroundColor", "white");
 		$(".current").siblings().children("a").css("background-image","none");
	})
	
    $('.out').hover(function(){
	 	$(this).css("backgroundColor", "#f9f9f9");
	})
	$('.out1').hover(function(){
		$(this).css("backgroundColor", "white");
	})
	 $(".current").mouseover(function() {//当背景图片不为点击时图片时修改其为悬浮图
		 if($(this).children("a").css("background-image")=='none'){
		     $(this).children("a").css("background-image", "url(static/images/admin/nav_li_hover.gif)");
	    }
    });
    $(".current").mouseout(function() {//当背景图为悬浮图时，移除悬浮图
    	var bgimg = $(this).children("a").css("background-image");
    	if(bgimg.substr(bgimg.length-18,16)=='nav_li_hover.gif'){
    		$(this).children("a").css("background-image", "none");
    	}
    });
	$(".current").click(function(){
		$(this).children("a").css("background-image", "url(static/images/admin/nav_li_current.gif)");
		$(this).siblings().children("a").css("background-image","none");
	}) 

	var wh =  $(window).height();//浏览器当前窗口可视区域高度
	var sheight = (wh-75)+'px';
	$("#ifr").css("height",sheight);
	$(".box").css("height",sheight);
});
