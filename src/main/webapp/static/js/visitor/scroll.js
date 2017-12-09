$(function () {
    var sBox = $('.sBox')
    var sBox1 = $('.sBox1')[0]
    var sBox1 = $('.sBox1')[0]
    var scrollUl1 = $('.scrollUl1')[0]
    var scrollUl2 = $('.scrollUl2')[0]
    var scrollUl1_1 = $('.scrollUl1')
    var scrollUl2_1 = $('.scrollUl2')
//    scrollUl2.innerHTML = scrollUl1.innerHTML
    scrollUl2_1.html(scrollUl1_1.html());
    function gotol() {
        if(sBox1.scrollLeft>=scrollUl1.offsetWidth){
            sBox1.scrollLeft = 0
        }else{
            sBox1.scrollLeft++
        }
    }
    var timer = setInterval(gotol,50)
    sBox.hover(function () {
        clearInterval(timer)
    },function () {
        timer = setInterval(gotol,50)
    })
})



if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE6.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0"){
    var time,time1;
    var s=1;
    $(".scrollUl1").children('li').hover(function(){
        s=1;
        $(this).siblings().find("img").css("transform","scale(1)");
        clearInterval(time1);
        time = setInterval( _transition1($(this)),20);
    },function () {
        clearInterval(time);
        time1 = setInterval( _transitionhide1($(this)),20);
    });
    function _transition1(obj) {
        return function(){
            _transition(obj);
        }
    }
    function _transitionhide1(obj){
        return function(){
            _transitionhide(obj);
        }
    }
    function _transition(obj) {
        s+=0.005;
        if(s>1.2) s=1.2;
        obj.find('img').css("transform","scale("+s+")");
    }
    function _transitionhide(obj) {
        s-=0.01;
        if(s<1) s=1;
        obj.find("img").css("transform","scale("+s+")");
    }
}