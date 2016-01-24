<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${userPrincipal.username} requests - dvdshare</title>
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
                        <li><a href="<c:url value='/users'/>">Other users</a></li>                            
                        <li class="active"><a href="<c:url value='/user/self/requests/in'/>">Requests <span class="badge">${incomingRequestsCount}</span></a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="<c:url value='/logout'/>">Logout</a></li>                            
                    </ul>
                </div>
            </nav>
            <div class="page-header">
                <h1>Hello,<br>${userPrincipal.username}!</h1>
                <p>That is all active requests you made or received</p>
            </div>            
            <ul id="tabs" class="nav nav-tabs">
                <li id="tab-in" role="presentation" class="active"><a href="<c:url value="/user/self/requests/in"/>">Incoming</a></li>
                <li id="tab-out" role="presentation" class="active"><a href="<c:url value="/user/self/requests/out"/>">Outgoing</a></li>                
            </ul>
            <table class="table tbl-requests">
                <thead>
                    <tr>
                        <th class="id">Req. #</th>
                        <th>Title</th>                            
                        <th class="owner">Owner</th>                        
                        <th class="status">Status</th>                        
                        <th class="request">Request</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="request" items="${requests}">
                        <c:if test="${request.id != null}">
                            <tr>
                                <td class="id">${request.id}</td>
                                <td>${request.disk.title}</td>
                                <td class="owner">${request.disk.owner.username}</td>
                                <td class="status">
                                    <c:choose>
                                        <c:when test="${request.status == 'ACCEPTED'}">
                                            <span class="glyphicon glyphicon-ok-sign" style="color:green;"></span>
                                        </c:when>
                                        <c:when test="${request.status == 'REJECTED'}">
                                            <span class="glyphicon glyphicon-remove-sign" style="color:red;"></span>
                                        </c:when>
                                        <c:when test="${request.status == 'REQUESTED'}">
                                            <span class="glyphicon glyphicon-question-sign" style="color:blue;"></span>
                                        </c:when>
                                        <c:otherwise>
                                            ${request.status}
                                        </c:otherwise>                                            
                                    </c:choose>                                
                                </td>
                                <td class="request"> 
                                    <c:choose>
                                        <c:when test="${selection == 'IN'}">
                                            <span id="btn-accept" class="glyphicon glyphicon-thumbs-up"></span>&nbsp;&nbsp;
                                            <span id="btn-reject" class="glyphicon glyphicon-thumbs-down"></span>                                            
                                        </c:when>
                                        <c:when test="${selection == 'OUT'}">                                            
                                            <span id="btn-cancel" class="glyphicon glyphicon-remove"></span>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>                        
                </tbody>    
            </table>


        </div>
        <script>
            var contextPath = '<%= request.getContextPath()%>';
            var csrf = '<c:out value="${_csrf.token}"/>';
            var dataSelection = '<c:out value="${selection}"/>';
        </script> 
        <script src="<c:url value='/static/js/jquery-1.12.0.min.js'/>"></script>
        <script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>        
        <script src="<c:url value='/static/js/dvdshare.js'/>"></script> 
    </body>
</html>
