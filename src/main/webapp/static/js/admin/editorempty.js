/**
 * 
 */
function editorEmpty(){
	var editorContent = editor.$txt.html();
	if(editorContent!="<p><br></p>"){
		cont.value = editorContent;			
	} else {
		alert("必须填写内容");
		var event = event || window.event;
		event.preventDefault(); // 兼容标准浏览器
		window.event.returnValue = false;
		return false;
	}
}