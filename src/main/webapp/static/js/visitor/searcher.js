/**
 * Created by 王少旗 on 2017/12/3.
 */
$(function () {
    $(".seacherspan").click(function (e) { 
        if($(".searchboxinput").val()==''){
            $(".searchboxinput").stop().animate({width:'toggle'},200);
            e.preventDefault();
            e.stopPropagation();
        }
        $(".searchboxinput").val('');
    })
})