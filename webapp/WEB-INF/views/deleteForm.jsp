<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="/guestbook5/delete" method="post">
		<input type="hidden" name="no" value="${param.no }"> 비밀번호<input
			type="password" name="password" value="">
		<button type="submit">확인</button>
	</form>

	<a href="/guestbook5/list">메인으로 돌아가기</a>
</body>
</html>