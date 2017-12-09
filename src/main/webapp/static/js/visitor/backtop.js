/**
 * Created by 王少旗 on 2017/12/3.
 */
$(function() {
    $(window).scroll(function() {
        if ($(window).scrollTop() > 800)
            $('.backtop').fadeIn();
        else
            $('.backtop').fadeOut();
    });
    $('.backtop').click(function() {
        $('html, body').animate({scrollTop: 0}, 1000);
    });
});