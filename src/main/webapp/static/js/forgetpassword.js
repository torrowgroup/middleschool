/**
 * 	获取验证码，找回密码
 */
	
	function getTestCode(){
		var usEmail = $('#usEmail').val();
		if(usEmail!=null&&usEmail!=""){
			var emailPat=/^(.+)@(.+)$/;
			var matchArray=usEmail.match(emailPat);
			if (matchArray==null) {
			        $('#message').html("邮箱格式不正确");
			        $("#sure").attr("disabled", true);
			        return;
			  }
			  $.ajax({
					type : 'post',
					url : '/middleschool/getTestCode',
					data : {email:usEmail},
					contentType:'application/x-www-form-urlencoded', //contentType很重要
					success : function(data) {
						if(data.msg){
							$('#message').html("验证码发送成功");
							$("#sure").attr("disabled", false);
						} else {
							$('#message').html("邮箱不存在或者不合法");
							$("#sure").attr("disabled", true);
						}
					},
					error : function(data) {
						$('#message').html("邮箱不存在或者不合法");
	                }
				});
		} else {
			$('#message').html("邮箱为空");
			$("#sure").attr("disabled", true);
		}
	}
