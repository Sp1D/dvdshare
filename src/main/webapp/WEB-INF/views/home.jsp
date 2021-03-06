<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <li class="active"><a href="<c:url value='/user/self'/>">Home</a></li>
                        <li><a href="<c:url value='/users'/>">Other users</a></li>                            
                        <li><a href="<c:url value='/user/self/requests/in'/>">Requests <span class="badge">${incomingRequestsCount}</span></a></li>                        
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="<c:url value='/logout'/>">Logout</a></li>                            
                    </ul>
                </div>
            </nav>
            <div class="page-header">
                <h1>Hello,<br>${user.username}!</h1>
                <p>It's, like, all yours! Well, not all of them exactly.</p>
            </div>            
            <ul id="tabs" class="nav nav-tabs">
                <li id="tab-own" role="presentation"><a href="<c:url value="/user/self"/>">My own disks</a></li>
                <li id="tab-hold" role="presentation"><a href="<c:url value="/user/self/hold"/>">Disks on hands</a></li>
                <li id="tab-taken" role="presentation"><a href="<c:url value="/user/self/taken"/>">Taken disks</a></li>
                <li id="tab-given" role="presentation"><a href="<c:url value='/user/self/given'/>">Given disks</a></li>                                
            </ul>
            <table class="table tbl-mydisks">
                <thead>
                    <tr>
                        <th class="id">#</th>
                        <th>Title</th>                            
                        <th class="return"></th>
                        <th class="owner">Owner</th>
                        <th class="holder">Holder</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="disk" items="${disks}">
                        <c:if test="${disk.id != null}">
                            <tr>
                                <td class="id">${disk.id}</td>
                                <td>${disk.title}</td>
                                <td class="return">
                                    <c:if test="${selection == 'TAKEN'}">
                                        <a class="btn-return" href="#">return</a>
                                    </c:if>
                                </td>
                                <td class="owner">${disk.owner.username}</td>
                                <td class="holder">${disk.holder.username}</td>
                            </tr>
                        </c:if>
                    </c:forEach>                        
                </tbody>    
            </table>

            <c:if test="${selection == 'OWN'}">
                <button class="btn btn-success btn-sm" data-toggle="modal" data-target="#modal-adddisk">Add new disk</button>

                <div id="modal-adddisk" class="modal" tabindex="-1" role="dialog">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="gridSystemModalLabel">Add new disk</h4>
                            </div>
                            <div class="modal-body">
                                <p>Please be aware about autogenerated identifier. It is assigned to all disks in <strong>dvdshare</strong> system</p>
                                <form id="form-adddisk" action="">
                                    <div class="form-group">
                                        Disk title<br>
                                        <input id="newdisk-title" type="text" class="form-control" name="title">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="btn-add-disk" class="btn btn-success">Add</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>                                
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
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
