/**
 * 修改用户和修改个人资料
 */
	// 隐藏显示简介和成就富文本
	$(function() {
		$("#introShow").click(function() {
			$("#intro").show();
			$("#achieve").hide();
		})
		$("#introHide").click(function() {
			$("#intro").hide();
		})
		$("#achieveShow").click(function() {
			$("#achieve").show();
			$("#intro").hide();
		})
		$("#achieveHide").click(function() {
			$("#achieve").hide();
		})
	});

	var E = window.wangEditor
	var introdEditor = new E('introdEditor')
	introdEditor.config.uploadImgUrl = 'uploadImg'
	introdEditor.config.uploadImgFileName = 'file'
	introdEditor.create()

	var achieveEditor = new E('achieveEditor')
	achieveEditor.config.uploadImgUrl = 'uploadImg'
	achieveEditor.config.uploadImgFileName = 'file'
	achieveEditor.create()

	function modifyContent() {// 将两个富文本的值赋值给textarea
		var usIntroduction = document.getElementById("usIntroduction");
		var usAchievements = document.getElementById("usAchievements");
		usIntroduction.value = introdEditor.$txt.html();
		usAchievements.value = achieveEditor.$txt.html();
	}
	
