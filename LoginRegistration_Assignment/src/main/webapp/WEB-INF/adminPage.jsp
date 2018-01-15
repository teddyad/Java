<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/static/css/style.css" rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="userWrapper">

		<a class="menu" href="/">Home</a>
		<h2 class="userHeader">
			<span>Welcome to the admin page of <c:out
					value="${currentUser.firstName }" /></span>
		</h2>
		<div class="content">
			<p>Administration content goes here</p>
			<p>*</p>
			<p>*</p>
			<p>*</p>
			<p>*</p>
			<p>*</p>
			<p>*</p>
			<p>*</p>

		</div>

		<form method="POST" action="/logout">
			<input type="hidden" name="${_csrf.parameterName }"
				value="${_csrf.token }" /> <input class="button" type="submit"
				value="Logout!" />
		</form>
	</div>
</body>
</html>