<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
hello world+++${name}
<a href="hello?name=578">hello1</a>
	<a href="hello">hello2</a>
<a href="${rootPath}hello?name=578239">hello4</a>
<form action="${pageContext.request.contextPath }/login.action">
		<table border="1">
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="usEmail"></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="usPassword"></td>
			</tr>
			<tr>
				<td><input type="submit" value="登录">
					<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>
</body>
${msg}
</html>