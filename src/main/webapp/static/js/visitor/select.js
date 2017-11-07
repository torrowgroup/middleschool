/**
 * Created by 王少旗 on 2017/10/31.
 */
	
//	console.log($('.selectBoxUl')[0].clientHeight)
	$('.selectBox').on('click',function () {
	    $('.selectBoxUl').stop().fadeToggle() ;
	    var h1 = $('.selectBoxUl')[0].clientHeight;
	    if(h1>260){
	    	$('.selectBoxUl').css({"top":260,"height":"260px","overflowY":"scroll"});
	    }else{
	    	$('.selectBoxUl').css('top',-h1);
	    }
	    
	})
	
	function turnPageNew (obj){	//新闻界面的跳转页数
		var text = obj.text;
		var name = obj.id;
		window.location.href = "viewNews?currentPage="+text+"&nId="+name;
	}
	
	function turnPageView (obj){//学校风光界面的跳转页数
		var text = obj.text;
		var name = obj.id;
		window.location.href = "viewGeneral?currentPage="+text+"&gId="+name;
	}
	
	