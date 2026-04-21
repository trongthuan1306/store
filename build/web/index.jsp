<%-- 
    Document   : index
    Created on : Feb 24, 2026, 11:05:39 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body >
        <%@include file="privates/menu.jspf" %>
        <c:if test="${session !=null}">
        <h1>Welcome to <b style="color: purple">${sessionScope.user.firstName}</b></h1>
        </c:if>
        <jsp:forward page="listProduct" />
    </body>
</html>
