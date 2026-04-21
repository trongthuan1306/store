<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Category - Emart</title>
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/css/styleGlobal.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/styleForm.css" rel="stylesheet" type="text/css"/>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
    <%@include file="menu.jspf" %>

    <div class="form-wrapper glass-card">
        <div class="form-header">
            <h2>Update Category</h2>
        </div>

        <form method="POST" action="updateCategories" class="modern-form">
            <!-- Hidden ID -->
            <input type="hidden" name="id" value="${requestScope.ds.typeId}">

            <div class="form-group">
                <label>Category ID</label>
                <input type="text" class="form-control" value="${requestScope.ds.typeId}" disabled>
            </div>

            <div class="form-group">
                <label>Category Name</label>
                <input type="text" name="name" class="form-control" value="${requestScope.ds.categoryName}" placeholder="Food, Electronics, etc." required>
            </div>

            <div class="form-group">
                <label>Memo</label>
                <textarea name="memo" class="form-control" placeholder="Description of this category...">${requestScope.ds.memo}</textarea>
            </div>

            <button type="submit" class="btn-submit">
                <span class="glyphicon glyphicon-floppy-disk"></span> Save Category
            </button>
            <a href="listCategories" class="cancel-link">Cancel</a>
        </form>
    </div>
</body>
</html>
