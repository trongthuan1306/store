<%-- 
    Document   : classifyAccount
    Created on : Mar 15, 2026, 6:20:09 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link href="css/styleClassifyAccount.css" rel="stylesheet" type="text/css"/>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    
    <body class="admin-box"> 
 
        
        <%@include file="menu.jspf" %>

        <h2>Account Segments</h2>
    <table class="table">
        <tr>
            <td>Account</td>
            <td>AvgPrice</td>
            <td>Segment</td>

        </tr>    
        <c:forEach var="i" items="${requestScope.segments}">
            <tr>
                <td>${i.account.account}</td>
                <td>
            <fmt:formatNumber 
                value="${i.avgPrice}" 
                type="number" 
                groupingUsed="true" 
                maxFractionDigits="0"
                /> đ
        </td>
        <td>${i.segment}</td>

    </tr>  

</c:forEach>
</table>
</body>
</html>
