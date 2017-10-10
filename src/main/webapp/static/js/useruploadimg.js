/**
 * 增添用户中使用ajax上传图片
 */
	var upload = function() {
		var formData = new FormData();
        formData.append("picture", document.getElementById("usPicture").files[0]);   
		$.ajax({
			type : 'post',
			url : 'http://localhost:8080/middleschool/user/uploadPicture',
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(data) {
                $("#uploadMsg").html(data.msg);
			},
			error : function(data) {
				 $("#uploadMsg").html(data.msg);
			}
		});
	};
