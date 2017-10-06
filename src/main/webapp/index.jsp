<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
<form action="${rootPath}login">
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
${msg}
<a href="${rootPath}user/showAllUser">用户管理</a>
<a href="${rootPath}user/main">用户管理</a>
</body>
</html>