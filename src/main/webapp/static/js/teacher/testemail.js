/**
 * ajax验证该用户邮箱是否被使用
 */
	var usEmail = $('#usEmail').prop("value");//获得初始邮箱
	var testEmail = function() {
		var email = $('#usEmail').prop("value");
		if(email!=usEmail&&email.length!=0){
			$.ajax({
				type : 'post',
				url : projectPath+'education/testEmail',
				data : {email:email},
				contentType:'application/x-www-form-urlencoded', //contentType很重要
				success : function(data) {
					if(data.msg==1){
						$("#ok").show();
						$("#wrong").hide();
						$("#sure").attr("disabled", false);
					} else {
						$("#wrong").show();
						$("#ok").hide();
						$("#sure").attr("disabled", true);
					}
				}
			});
		} else {
			$("#ok").hide();
			$("#wrong").hide();
			$("#sure").attr("disabled", false);
		}
	};
	
	