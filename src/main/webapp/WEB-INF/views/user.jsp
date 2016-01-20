<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>[Username] - dvdshare</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css'/>">        
        <link rel="stylesheet" href="<c:url value='/static/css/dvdshare.css'/>">               

    </head>
    <body>
        <div class="container">
            <div>
                <c:if test="${pageContext.request.userPrincipal != null}">
                    <h1>Hello,<br>
                        ${user.username}
                        !</h1>
                    </c:if>
                <table class="table tbl-mydisks">
                    <thead>
                        <tr>
                            <th class="id">#/Available</th>
                            <th>Title</th>                            
                            <th class="holder">Holder</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="disk" items="${disks}">
                            <tr>
                                <td class="id">${disk.id}</td>
                                <td>${disk.title}</td>
                                <td class="holder">${disk.holder.username}</td>
                            </tr>
                        </c:forEach>                        
                    </tbody>    
                </table>
                <button class="btn btn-success" data-toggle="modal" data-target="#modal-adddisk">Add new disk</button>

                <div id="modal-adddisk" class="modal" tabindex="-1" role="dialog">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="gridSystemModalLabel">Add new disk</h4>
                            </div>
                            <div class="modal-body">
                                <form id="form-adddisk">
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
            </div>
        </div>
        <script>
            var contextPath = '<%= request.getContextPath()%>';
            var csrf = '<c:url value="${_csrf.token}"/>';
        </script> 
        <script src="<c:url value='/static/js/jquery-1.12.0.min.js'/>"></script>
        <script src="<c:url value='/static/js/bootstrap.min.js'/>"></script>        
        <script src="<c:url value='/static/js/dvdshare.js'/>"></script> 
    </body>
</html>
