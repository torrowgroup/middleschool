/**
 * ajax验证该用户邮箱是否被使用
 */
	var usEmail;
	function test(){
		usEmail = $('#usEmail').prop("value");
	}
	var testEmail = function() {
		var email = $('#usEmail').prop("value");
		if(email!=usEmail&&email.length!=0){
			$.ajax({
				type : 'post',
				url : '/middleschool/user/testEmail',
				data : {email:email},
				contentType:'application/x-www-form-urlencoded', //contentType很重要
				success : function(data) {
					if(data.msg==1){
						$("#ok").show();
						$("#wrong").hide();
						$("#add").attr("disabled", false);
					} else {
						$("#wrong").show();
						$("#ok").hide();
						$("#add").attr("disabled", true);
					}
				}
			});
		} else {
			$("#ok").hide();
			$("#wrong").hide();
		}
	};
	
	