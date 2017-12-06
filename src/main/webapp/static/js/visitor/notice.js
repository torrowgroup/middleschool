/**
 * Created by 王少旗 on 2017/10/16.
 */
$(function(){
    var num = 0
    var noticeUl = $('.noticeUl')
    var notice = $('.notice')
    function gotoLeft() {

        if (num<=-750) {
            num = 0
        }
        num -=1;
        noticeUl.css({
            left: num
        })
    }
    var timer = setInterval(gotoLeft,40)
    notice.hover(function () {
        clearInterval(timer)
    },function () {
        timer = setInterval(gotoLeft,40)
    })
    $(".navLi").hover(function () {
        $(this).children('ul').stop().slideDown('500');

    },function () {
        $(this).children('ul').stop().slideUp('fast');
    })
})