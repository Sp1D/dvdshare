<%@page import="com.sp1d.dvdshare.entities.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="f" uri="functions.tld"  %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${user.username} - dvdshare</title>
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
                <h1>${user.username}</h1>
                <c:if test="${userPrincipal == user}">
                    <p>Oops, it is you!</p>
                </c:if>
                    <p><a href="<c:url value='/users'/>">Want go back to users list?</a></p>
            </div>            
            <ul id="tabs" class="nav nav-tabs">
                <li id="tab-own" role="presentation"><a href="<c:url value="/user/${user.id}/own"/>">His own disks</a></li>
                <li id="tab-hold" role="presentation"><a href="<c:url value="/user/${user.id}/hold"/>">Disks on hands</a></li>                
            </ul>
            <table class="table tbl-mydisks">
                <thead>
                    <tr>
                        <th class="id">#</th>
                        <th class="request">Request</th>                        
                        <th>Title</th>                            
                        <th class="owner">Owner</th>
                        <th class="holder">Holder</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="disk" items="${disks}">
                        <c:if test="${disk.id != null}">                           
                            <tr>
                                <td class="id">${disk.id}</td>
                                <td class="request">
                                    
                                    <c:if test="${disk.owner == disk.holder && userPrincipal != user}">                                        
                                        <c:choose>                                            
                                            <c:when test="${f:colcontains(requests, disk.request) && !f:colcontains(principalRequests, disk.request)}">
                                                <span class="glyphicon glyphicon-ok btn-request btn-disabled"></span>    
                                                <span class="glyphicon glyphicon-remove btn-cancel btn-disabled"></span>
                                            </c:when>
                                            
                                            <c:when test="${f:colcontains(requests, disk.request) && f:colcontains(principalRequests, disk.request)}">
                                                <span class="glyphicon glyphicon-ok btn-request btn-disabled"></span>    
                                                <span class="glyphicon glyphicon-remove btn-cancel"></span>
                                            </c:when>
                                                
                                            <c:otherwise>
                                                <span class="glyphicon glyphicon-ok btn-request"></span>    
                                                <span class="glyphicon glyphicon-remove btn-cancel btn-disabled"></span>
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </c:if>    
                                </td>
                                <td>${disk.title}</td>
                                <td class="owner">${disk.owner.username}</td>
                                <td class="holder">${disk.holder.username}</td>
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
