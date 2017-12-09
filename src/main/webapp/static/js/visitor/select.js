/**
 * Created by 王少旗 on 2017/10/31.
 */
	
	$("#anyThing").on('click','.selectBox',function(){
		 $(".selectBoxUl").stop().fadeToggle('fast') ;
		    var h1 = $('.selectBoxUl')[0].clientHeight;
		    console.log(h1);
		    if(h1>260){
		    	$('.selectBoxUl').css({"top":-260,"height":"260px","overflowY":"scroll"});
		    }else{
		    	$('.selectBoxUl').css('top',-h1);
		    }
		});
	
	function turnPageNew (obj){	//新闻界面的跳转页数
		var text = obj.text;//跳转页数
		var name = obj.id;	//类别类id
		var inquiry = obj.name; //查询内容
		window.location.href = "viewNews?currentPage="+text+"&nId="+name+'&inquiry='+inquiry;
	}
	
	function turnPageView (obj){//学校风光界面的跳转页数
		var text = obj.text;
		var name = obj.id;
		window.location.href = "viewGeneral?currentPage="+text+"&gId="+name;
	}
	function turnPageEducation (obj){//教育教研界面的跳转页数
		var text = obj.text;
		var rId = obj.id;
		var inquiry = obj.name;
		window.location.href = "viewEducation?currentPage="+text+"&rId="+rId+"&inquiry="+inquiry;
	}
	function turnPageLiterture (obj){//校园文学公告界面的跳转页数
		var text = obj.text;
		var name = obj.id;
		window.location.href = "viewLiterature?currentPage="+text+"&cId="+name;
	}
	function turnPageNotice (obj){//通知公告界面的跳转页数
		var text = obj.text;
		var name = obj.id;
		window.location.href = "viewNotices?currentPage="+text+"&nId="+name;
	}
	function turnPageUserIntro (obj){//教师介绍界面的跳转页数
		var text = obj.text;
		var name = obj.id;
		window.location.href = "viewTeacherIntroActieve?currentPage="+text+"&ask="+name;
	}
	function turnPageMessage (obj){//查看留言界面的跳转页数
		var text = obj.text;
		var meId = obj.id;
		window.location.href = "viewLeaveWords?currentPage="+text+"&nId="+meId;
	}
	
	