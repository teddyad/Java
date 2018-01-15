<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link href="/static/css/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login/Registration</title>

</head>
<body>
	<div class="wrapper">
	
	<h2 class="header">Please Login or Register</h2>

		<div class="formWrapper">

			<h2 class="formTitle"><span>Login</span></h2>

            <p class="error">
				<c:if test="${logoutMessage != null}">
					<c:out value="${logoutMessage}"></c:out>
				</c:if>
				<span><c:if test="${created != null && errorMessage == null}">
					<c:out value="${created}" />
				</c:if></span>
	
				<c:if test="${errorMessage != null}">
					<c:out value="${errorMessage}"></c:out>
				</c:if>
            </p>

			<form method="POST" action="/login">

				<label for="username">Email</label><br>
                <input type="text" name="username" /> <br>
                
                <label for="username">Password</label>
				<br> <input type="password" name="password" /> 
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
				<br> <input class="button" type="submit" value="Login!" /><br>

			</form>
		</div>


		<div class="formWrapper">
			<h2 class="formTitle"><span>Register</span></h2>

			<p class="error">
				<form:errors path="user.*" />
			</p>


			<form:form method="POST" action="/registration" modelAttribute="user">

				<form:label path="firstName">First Name:</form:label>
				<br>
				<form:input path="firstName" />
				<br>

				<form:label path="lastName">Last Name:</form:label>
				<br>
				<form:input path="lastName" />
				<br>

				<form:label path="email">Email:</form:label>
				<br>
				<form:input path="email" />
				<br>

				<form:label path="password">Password:</form:label>
				<br>
				<form:password path="password" />
				<br>

				<form:label path="confirmPassword">Confirm Password:</form:label>
				<br>
				<form:password path="confirmPassword" />

				<br>
				<input class="button" type="submit" value="Register">
			</form:form>
		</div>
	</div>
</body>
</html>