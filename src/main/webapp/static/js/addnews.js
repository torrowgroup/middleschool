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
	}
