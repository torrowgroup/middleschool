var n = document.documentElement.clientHeight;
function stopDefault( e )
{ 
   if ( e && e.preventDefault ) 
      e.preventDefault(); 
     else 
        window.event.returnValue = false;  
} 
function showT(e)  //显示隐藏层和弹出层
{
   var hideobj=document.getElementById("hidebg");
   hidebg.style.display="block";  //显示隐藏层
   hidebg.style.height= n + "px";  //设置隐藏层的高度为当前页面高度
   document.getElementById("hidebox").style.display="block";  //显示弹出层
   //e.preventDefault();
   stopDefault(e);
   return false;
}
function hideT(e)  {
   document.getElementById("hidebg").style.display="none";
   document.getElementById("hidebox").style.display="none";	
   stopDefault(e);
}
function show(e)  //显示隐藏层和弹出层
{
   var hideobj=document.getElementById("hidebg");
   hidebg.style.display="block";  //显示隐藏层
   hidebg.style.height= n + "px";  //设置隐藏层的高度为当前页面高度
   document.getElementById("hidebox1").style.display="block";  //显示弹出层
   //e.preventDefault();
   stopDefault(e);
   return false;
}
function hide(e)  {
   document.getElementById("hidebg").style.display="none";
   document.getElementById("hidebox1").style.display="none";	
   stopDefault(e);
}
