<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/static/css/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Super Admin</title>
</head>
<body>
	<div class="userWrapper">

		<h2 class="userHeader">
            <span>SUPER ADMINISTRATOR:  <c:out
                    value="${user.firstName }" /></span>
        </h2>
        
        <div class="content">
            <table>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Assign Role</th>
                    <th>Remove User</th>
                </tr>

                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.firstName}" /> <c:out
                                value="${user.lastName}" /></td>
                        <td><c:out value="${user.email}" /></td>
                        
                        <c:choose>
                            <c:when test="${user.isSuperAdmin()}">
                                <td>SuperAdmin</td>
                                   <td>
                                    <a href="/superAdmin/promote/${user.id}">Super Admin</a>&nbsp | &nbsp
                                    <a href="/admin/promote/${user.id}">Admin</a>
                                </td>
                                    <td><a class="delete" href="/superAdmin/delete/${user.id}">Delete</a>
                                </td>
                            </c:when>
                            
                             <c:when test="${user.isAdmin()}">
                                <td>Admin</td>
                                <td>
	                                <a href="/superAdmin/promote/${user.id}">Super Admin</a>&nbsp | &nbsp
	                                <a href="/admin/promote/${user.id}">Admin</a>
	                            </td>
	                                <td><a class="delete" href="/superAdmin/delete/${user.id}">Delete</a>
                                </td>
                            </c:when>

                            <c:otherwise>
                                 <td>User</td>
                                      <td>
                                       <a href="/superAdmin/promote/${user.id}">Super Admin</a>&nbsp | &nbsp
                                    <a href="/admin/promote/${user.id}">Admin</a>
                                </td>
                                    <td><a class="delete" href="/superAdmin/delete/${user.id}">Delete</a>
                                </td>
                              
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    </c:forEach>
          
            </table>

        </div>
		<form method="POST" action="/logout">
			<input type="hidden" name="${_csrf.parameterName }"
				value="${_csrf.token }" /> <input class="button" type="submit"
				value="Logout!" />
		</form>
	</div>

</body>
</html>