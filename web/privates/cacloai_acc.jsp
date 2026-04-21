<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link href="${pageContext.request.contextPath}/css/styleGlobal.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/styleAdmin.css" rel="stylesheet" type="text/css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>Manage Accounts - Emart</title>
    </head>
    <body >
        <%@include file="menu.jspf" %>
        
        <div class="admin-container">
            <div class="admin-header">
                <h1 class="admin-title">Manage Accounts</h1>
            </div>

            <div class="admin-controls">
                <form action="searchAccount" method="get" class="form-inline admin-search">
                    <div class="form-group">
                        <input type="text" name="keyword" class="form-control" placeholder="Search account..." required="">
                    </div>
                    <button type="submit" class="btn-search">
                        <span class="glyphicon glyphicon-search"></span> Search
                    </button>
                    <c:if test="${not empty requestScope.errorAcc}">
                        <span style="color: red; margin-left: 15px; font-weight: 500;">${requestScope.errorAcc}</span>
                    </c:if>
                </form>

                <a href="newAccount" class="btn-add-new">
                    <span class="glyphicon glyphicon-plus"></span> Add Account
                </a>
            </div>

            <table class="admin-table">
                <thead>
                    <tr>
                        <th>Account</th>
                        <th>FullName</th>
                        <th>Birthday</th>
                        <th>Gender</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" items="${requestScope.ds}">
                        <tr>
                            <td><strong>${i.account}</strong></td>
                            <td>${i.firstName} ${i.lastName}</td>
                            <td>${i.birthday}</td>
                            <td>
                                <span style="color: ${i.gender == true ? '#0284c7' : '#db2777'}; font-weight: 500;">
                                    ${i.gender == true ? "Male" : "Female"}
                                </span>
                            </td>
                            <td>${i.phone}</td>
                            <td>
                                <span class="label ${i.roleInSystem == 1 ? 'label-danger' : i.roleInSystem == 2 ? 'label-warning' : 'label-success'}">
                                    ${i.roleInSystem == 1 ? "Admin" : i.roleInSystem == 2 ? "Manager" : "User"}
                                </span>
                            </td>                 
                            <td>
                                <div class="action-cell">
                                    <c:if test="${sessionScope.role == 1 || (sessionScope.role == 2 && i.roleInSystem != 1)}">
                                        <a href="updateAccount?acc=${i.account}" class="btn-sm-action btn-update">
                                            <span class="glyphicon glyphicon-edit"></span> Update
                                        </a>
                                        <a href="deleteAccount?acc=${i.account}" class="btn-sm-action btn-delete" onclick="return confirm('Xóa tài khoản này?')">
                                            <span class="glyphicon glyphicon-trash"></span> Delete
                                        </a>
                                    </c:if>
                                    
                                    <c:if test="${sessionScope.role == 1 || (sessionScope.role == 2 && i.roleInSystem != 1)}">
                                        <c:choose>
                                            <c:when test="${i.isUse == true}">
                                                <a href="lockLogin?acc=${i.account}" class="btn-sm-action btn-lock">
                                                    <span class="glyphicon glyphicon-lock"></span> Lock
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="unlockLogin?acc=${i.account}" class="btn-sm-action btn-unlock">
                                                    <span class="glyphicon glyphicon-ok"></span> Unlock
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
