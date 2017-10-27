/**
 * Created by 王少旗 on 2017/10/17.
 */
$(function () {
    var sBox = $('.sBox')
    var sBox1 = $('.sBox1')[0]
    var scrollUl1 = $('.scrollUl1')[0]
    var scrollUl2 = $('.scrollUl2')[0]
    scrollUl2.innerHTML = scrollUl1.innerHTML
    function gotol() {
        if(sBox1.scrollLeft>=scrollUl1.offsetWidth){
            sBox1.scrollLeft = 0
        }else{
            sBox1.scrollLeft++
        }
    }
    var timer = setInterval(gotol,20)
    sBox.hover(function () {
        clearInterval(timer)
    },function () {
        timer = setInterval(gotol,20)
    })
})