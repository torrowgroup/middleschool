/**
 * 
 */
	//添加用户，如果用户密码和再次输入密码一致则可以添加;修改密码时也调用此处
	function pswAgain(){
		var password = $("#password").val();
		var again = $("#again").val();
		if(password==again){
			$("#sure").attr("disabled", false); 
			$("#okAgain").show();
			$("#wrongAgain").hide();
		} else {
			$("#sure").attr("disabled", true);
			$("#okAgain").hide();
			$("#wrongAgain").show();
		}
	}
