<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>All users - dvdshare</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="<c:url value='/static/img/favicon.png'/>">
        <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css'/>">        
        <link rel="stylesheet" href="<c:url value='/static/css/dvdshare.css'/>">
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-default navbar-static-top">
                <div class="container-fluid">
                    <ul class="nav navbar-nav">
                        <li><a href="<c:url value='/user/self'/>">Home</a></li>
                        <li class="active"><a href="<c:url value='/users'/>">Other users</a></li>
                        <li><a href="<c:url value='/user/self/requests/in'/>">Requests <span class="badge">${incomingRequestsCount}</span></a></li> 
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="<c:url value='/logout'/>">Logout</a></li>                            
                    </ul>
                </div>
            </nav>
            <div class="page-header">
                <h1>Users list</h1>
            </div>            
            <ul id="tabs" class="nav nav-tabs">                
                <li id="tab-allusers" role="presentation" class="active"><a href="<c:url value='/users'/>">All users</a></li>                
                <li id="tab-alldisks" role="presentation"><a href="<c:url value='/users/disks'/>">All disks</a></li>                
            </ul>
            <table class="table tbl-users">
                <thead>
                    <tr>
                        <th class="id">#</th>
                        <th>Username</th>                                                    
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.id != null}">
                            <tr>
                                <td class="id">${user.id}</td>
                                <td><a href="<c:url value='/user/${user.id}'/>">${user.username}</a></td>                                
                            </tr>
                        </c:if>
                    </c:forEach>                        
                </tbody>    
            </table>
               
        </div>
        <script>
            var contextPath = '<%= request.getContextPath()%>';
            var csrf = '<c:out value="${_csrf.token}"/>';  
            var ds = '<c:out value="${selection}"/>';
        </script> 
        <script src="<c:url value='/static/js/jquery-1.12.0.min.js'/>"></script>
        <script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>        
        <script src="<c:url value='/static/js/dvdshare.js'/>"></script> 
    </body>
</html>
