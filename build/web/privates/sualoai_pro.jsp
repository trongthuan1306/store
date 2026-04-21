<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Product - Emart</title>
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
            <h2>Update Product</h2>
        </div>

        <form action="updateProduct" method="post" enctype="multipart/form-data" class="modern-form">
            <!-- Hidden ID -->
            <input type="hidden" name="productId" value="${requestScope.ds.productId}">

            <div class="row">
                <div class="col-md-8 form-group">
                    <label>Product Name</label>
                    <input type="text" name="name" class="form-control" value="${requestScope.ds.productName}" required>
                </div>
                <div class="col-md-4 form-group">
                    <label>Category</label>
                    <select name="typeId" class="form-control" required>
                        <c:forEach var="c" items="${requestScope.catelist}">
                            <option value="${c.typeId}" ${c.typeId == requestScope.ds.typeId.typeId ? 'selected' : ''}>
                                ${c.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>Description (Brief)</label>
                <textarea name="brief" class="form-control" rows="4">${requestScope.ds.brief}</textarea>
            </div>

            <div class="row">
                <div class="col-md-4 form-group">
                    <label>Unit</label>
                    <input type="text" name="unit" class="form-control" value="${requestScope.ds.unit}" required>
                </div>
                <div class="col-md-4 form-group">
                    <label>Price (VND)</label>
                    <input type="number" name="price" class="form-control" value="${requestScope.ds.price}" required>
                </div>
                <div class="col-md-4 form-group">
                    <label>Discount (%)</label>
                    <input type="number" name="discount" class="form-control" value="${requestScope.ds.discount}">
                </div>
            </div>

            <div class="form-group">
                <label>Author / Owner Account</label>
                <select name="account" class="form-control" required>
                    <c:forEach var="a" items="${requestScope.acclist}">
                        <option value="${a.account}" ${a.account == requestScope.ds.account.account ? 'selected' : ''}>
                            ${a.account}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Update Product Image (Optional)</label>
                <div style="margin-bottom: 10px;">
                    <img src="${pageContext.request.contextPath}${requestScope.ds.productImage}" alt="${requestScope.ds.productName}" style="height: 100px; border-radius: 8px; border: 1px solid #cbd5e1; padding: 5px; background: white;">
                </div>
                <input type="file" name="productImage" class="form-control" style="padding: 9px 15px;">
                <small style="color: #64748b; margin-top: 5px; display: block;">Leave blank if you don't want to change the image.</small>
            </div>

            <button type="submit" class="btn-submit">
                <span class="glyphicon glyphicon-floppy-disk"></span> Save Product
            </button>
            <a href="listProduct" class="cancel-link">Cancel</a>
        </form>
    </div>
</body>
</html>
