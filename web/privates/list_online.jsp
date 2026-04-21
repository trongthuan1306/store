<%-- 
    Document   : list_online
    Created on : Mar 12, 2026, 10:05:35 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

        <%@include file="menu.jspf" %>
        <div class="container">

            <h1>List Online</h1>

            <ul>
                <c:forEach var="s" items="${applicationScope.online.values()}">
                    <c:set var="i" value="${s.getAttribute('user')}"></c:set>

                        <li>
                        ${i.lastName} ${i.firstName} 
                        <b style="color:red">
                            ${i.roleInSystem == 1 ? 'Sep' : i.roleInSystem == 2 ? 'Quan Ly' : 'User'}
                        </b>
                    </li>

                </c:forEach>
            </ul>

        </div>
    </body>
</html>
