<%-- 
    Document   : usercreate
    Created on : Jan 17, 2016, 5:38:23 PM
    Author     : sp1d
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new user</title>
    </head>
    <body>
        <sf:form method="POST" commandName="user">
            <sf:label path="username">Username</sf:label>
            <sf:input path="username"/><br>
            <sf:label path="email">E-mail</sf:label>
            <sf:input path="email"/><br>
            <sf:label path="password">Password</sf:label>
            <sf:password path="password"/><br>
            <input type="submit"/>
        </sf:form>

        
    </body>
</html>
