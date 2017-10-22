/**
 * 
 */
	//添加用户，如果用户密码和再次输入密码一致则可以添加
	function pswAgain(){
		var password = $("#password").val();
		var again = $("#again").val();
		if(password==again){
			$("#add").attr("disabled", false); 
			$("#testPassword").html("1");
		} else {
			$("#add").attr("disabled", true);
			$("#testPassword").html("0");
		}
	}