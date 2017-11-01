/**
 * Created by 王少旗 on 2017/10/31.
 */

console.log($('.selectBoxUl')[0].clientHeight)
$('.selectBox').on('click',function () {
    $('.selectBoxUl').stop().fadeToggle() ;
    var h1 = $('.selectBoxUl')[0].clientHeight;
    if(h1>260){
    	$('.selectBoxUl').css({"top":260,"height":"260px","overflowY":"scroll"});
    }else{
    	$('.selectBoxUl').css('top',-h1);
    }
    
})


//$(document).ready(function(){
// 
//  console.log(h1)
//});