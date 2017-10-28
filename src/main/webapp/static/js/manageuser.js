/**
 * 
 */
	var E = window.wangEditor
	var editor = new E('#div1', '#introEditor') // 两个参数也可以传入 elem 对象，class 选择器
	editor.create()
	
	function show(obj) {
		$("#introEditor").show();
		editor.txt.html(obj.name);
	}
