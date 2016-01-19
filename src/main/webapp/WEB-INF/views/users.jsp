<%-- 
    Document   : users
    Created on : Jan 17, 2016, 5:21:42 PM
    Author     : sp1d
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.sp1d.dvdshare.entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <thead>
                <tr><th>ID</th><th>USERNAME</th><th>DISKS</th></tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>
                            <c:out value="${user.id}"/>
                        </td>
                        <td>
                            <c:out value="${user.username}"/>
                        </td>
                        <td>

                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
