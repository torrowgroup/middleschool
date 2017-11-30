/**
 * 
 */
	//留言创建富文本
	var E = window.wangEditor
	var editor = new E('editor')
	editor.config.uploadImgUrl = 'uploadImg'
	editor.config.uploadImgFileName = 'file'
	editor.create()
	
	//将富文本内容赋值传到后台
	function checkForm(e){
		var cont = document.getElementById('content');
		var editorContent = editor.$txt.html();
		if(editorContent!="<p><br></p>"){
			cont.value = editorContent;			
		} else {
			alert("必须填写留言内容");
			var event = event || window.event;
			event.preventDefault(); // 兼容标准浏览器
			window.event.returnValue = false;
			return false;
		}
	}
