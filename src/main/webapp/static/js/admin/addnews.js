/**
 * 
 */
	// 隐藏显示简介和成就富文本
	$(function() {
		$("#introShow").click(function() {
			$("#intro").show();
		})
	});
	var E = window.wangEditor
	var introdEditor = new E('introdEditor')
	introdEditor.config.uploadImgUrl = 'uploadImg'
	introdEditor.config.uploadImgFileName = 'file'
	introdEditor.create()
	function modifyContent() {// 将两个富文本的值赋值给textarea
		var usIntroduction = document.getElementById("usIntroduction");
		usIntroduction.value = introdEditor.$txt.html();
		var editorContent = introdEditor.$txt.html();
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
