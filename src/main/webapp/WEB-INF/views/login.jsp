<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="<c:url value='static/img/favicon.png'/>">
        <link rel="stylesheet" href="<c:url value='static/css/bootstrap.min.css'/>">        
        <link rel="stylesheet" href="<c:url value='static/css/dvdshare.css'/>">        
        <title>Login - dvdshare</title>
    </head>
    <body onload='document.form.email.focus();'>
        <div class="container">
            <div class="login">
                <h2>Welcome to dvdshare</h2>
                <form method="POST" action="<c:url value='/login'/>" name="form">
                    <div class="form-group">                        
                        <input class="form-control" type="email" name="email" placeholder="email" autocomplete="on"/>
                    </div>
                    <div class="form-group">                        
                        <input class="form-control" type="password" name="password" placeholder="password" autocomplete="on"/>                        
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="submit" class="btn btn-success btn-login">Login</button>
                </form>
                <p style="padding-top: 5px;">Not registered, dude ?&nbsp;<a href="<c:url value="/register"/>">So sign up, yo!</a></p>
                <p class="form-error"><c:out value="${error}"/></p>
            </div>
        </div>
    </body>
</html>
