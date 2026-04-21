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
        <title>Manage Categories - Emart</title>
    </head>
    <body >
        <%@include file="privates/menu.jspf" %>

        <div class="admin-container">
            <div class="admin-header">
                <h1 class="admin-title">Manage Categories</h1>
            </div>
            
            <div class="admin-controls">
                <form action="searchCategories" method="get" class="form-inline admin-search">
                    <div class="form-group">
                        <input type="text" name="keyword" class="form-control" placeholder="Search categories..." required="">
                    </div>
                    <button type="submit" class="btn-search">
                        <span class="glyphicon glyphicon-search"></span> Search
                    </button>
                    <c:if test="${not empty requestScope.errorCate}">
                        <span style="color: red; margin-left: 15px; font-weight: 500;">${requestScope.errorCate}</span>
                    </c:if>
                </form>

                <a href="newCategories" class="btn-add-new">
                    <span class="glyphicon glyphicon-plus"></span> Add Category
                </a>
            </div>

            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Categories Name</th>
                        <th>Memo</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" items="${requestScope.ds}">
                        <tr>
                            <td><strong>${i.typeId}</strong></td>
                            <td>${i.categoryName}</td>
                            <td>${i.memo}</td>
                            <td>
                                <div class="action-cell">
                                    <a href="updateCategories?id=${i.typeId}" class="btn-sm-action btn-update">
                                        <span class="glyphicon glyphicon-edit"></span> Update
                                    </a>
                                    <a href="deleteCategories?id=${i.typeId}" class="btn-sm-action btn-delete" onclick="return confirm('Xóa danh mục này?')">
                                        <span class="glyphicon glyphicon-trash"></span> Delete
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
