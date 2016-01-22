<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="<c:url value='static/img/favicon.png'/>">
        <link rel="stylesheet" href="<c:url value='static/css/bootstrap.min.css'/>">        
        <link rel="stylesheet" href="<c:url value='static/css/dvdshare.css'/>">        
        <title>Register - dvdshare</title>
    </head>
    <body onload='document.form.email.focus();'>
        <div class="container">
            <div class="login">
                <h2>Show us yourself</h2>
                <sf:form modelAttribute="user" name="form">                    
                    <div class="form-group">                
                        <sf:input class="form-control" type="email" placeholder="email" path="email"/>                        
                    </div>
                    <div class="form-group">                
                        <sf:input class="form-control" type="text" placeholder="username" path="username"/>
                    </div>
                    <div class="form-group">                
                        <sf:input class="form-control" type="password" placeholder="password" path="plainPassword"/>
                    </div>
                    <div class="form-group">                
                        <sf:input class="form-control" type="password" placeholder="re-enter password" path="plainPasswordCheck"/>
                    </div>                    
                    <button class="btn btn-success btn-login" type="submit">Register</button>                
                    <p style="padding-top: 5px;"><a href="<c:url value="/login"/>">Back to login page</a></p>
                    <sf:errors path="*" cssClass="form-error"/>
                </sf:form>


            </div>
        </div>
    </body>
</html>
