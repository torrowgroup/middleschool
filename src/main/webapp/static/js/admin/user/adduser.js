/**
 * 
 */
	//添加用户，如果用户密码和再次输入密码一致则可以添加
	function pswAgain(){
		var password = $("#password").val();
		var again = $("#again").val();
		if(password==again){
			$("#add").attr("disabled", false); 
			$("#okAgain").show();
			$("#wrongAgain").hide();
		} else {
			$("#add").attr("disabled", true);
			$("#okAgain").hide();
			$("#wrongAgain").show();
		}
	}
	function checkPicture(obj){
		var p = obj.picture;
		if(p.value == ''){
			alert("未选择图片");
			return false;
		}
		return true;
	}
