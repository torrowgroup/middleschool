	var ifr = document.getElementById('ifr');
    function aa(a){
    	ifr.src = a + '.html';
  }
  function getdates()
{
var w_array=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
var d=new Date();
var year=d.getFullYear();
var month=d.getMonth()+1;
var day=d.getDate();
var week=d.getDay();
var h=d.getHours();
var mins=d.getMinutes();
var s=d.getSeconds();

if(month<10) month="0" + month
if(day<10) month="0" + day
if(h<10) h="0" + h
if(mins<10) mins="0" + mins
if(s<10) s="0" + s

var shows=" <span>"  + h + ":" + mins + ":" + s + " " + w_array[week] + "</span>";
document.getElementById("date").innerHTML=shows;
setTimeout("getdates()",1000);
}
getdates();



    