/**
 * Created by 王少旗 on 2017/12/3.
 */
$(function () {
    $(".seacherspan").click(function (e) { 
        if($(".searchboxinput").val()==''){
        	$(".prompt").html("");
        	if($(".prompt").html()==""){
        		$(".prompt").css("border-bottom-color","white");
        	}
            $(".searchboxinput").stop().animate({width:'toggle'},200);
            $(".prompt").stop().toggle();
            e.preventDefault();
            e.stopPropagation();
        }
        $(".searchboxinput").val('');
       
    })
})