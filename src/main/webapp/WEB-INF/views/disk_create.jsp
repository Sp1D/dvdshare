
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new disk</title>
    </head>
    <body>
        <p>
            <c:out value="${user.id}"/> : <c:out value="${user.username}"/>
        </p>
        <sf:form method="POST" commandName="disk">
            <sf:label path="name">Name</sf:label><br>
            <sf:input path="name"/>            
            <input type="submit"/>
        </sf:form>

        <c:forEach var="disk" items="${user.holdenDisks}">
            <c:out value="${disk.name}"/><br>
        </c:forEach>
    </body>
</html>
