/**
 * ajax验证该用户邮箱是否被使用
 */
	var testEmail = function() {
		var email = $('#usEmail').prop("value");
		$.ajax({
			type : 'post',
			url : '/middleschool/user/testEmail',
			data : {email:email},
			contentType:'application/x-www-form-urlencoded', //contentType很重要
			success : function(data) {
                $("#testEmail").html(data.msg);
			},
			error : function(data) {
				 $("#testEmail").html(data.msg);
			}
		});
	};