<%-- 
    Document   : loaimoi_pro
    Created on : Feb 26, 2026, 10:46:12 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/styleNewPro.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body >
        <%@include file="menu.jspf" %>
        <div class="form-container">

            <div class="form-box">
                <h2>Add New Product </h2>
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger">${requestScope.error}</div>
                </c:if>
                <form action="newProduct" method="post" enctype="multipart/form-data">

                    <table class="product-table">
                        <tr class="form-group">
                            <td>Product ID:</td>
                            <td><input type="text" name="productId" required></td>
                        </tr>
                        <tr>
                            <td>Product Name:</td>
                            <td><input type="text" name="name" required></td>
                        </tr>

                        <tr>
                            <td>Description:</td>
                            <td><textarea name="brief" rows="3" cols="30"></textarea></td>
                        </tr>

                        <tr>
                            <td>Category:</td>
                            <td>
                                <select name="typeId" required>
                                    <c:forEach var="c" items="${requestScope.catelist}">
                                        <option value="${c.typeId}">
                                            ${c.categoryName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <td>Account:</td>
                            <td>
                                <select name="account" required>
                                    <c:forEach var="a" items="${requestScope.acclist}">
                                        <option value="${a.account}">
                                            ${a.account}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <td>Unit:</td>
                            <td><input type="text" name="unit" required></td>
                        </tr>

                        <tr>
                            <td>Price:</td>
                            <td><input type="number" name="price" required></td>
                        </tr>

                        <tr>
                            <td>Discount:</td>
                            <td><input type="number" name="discount" value="0"></td>
                        </tr>

                        <tr>
                            <td>Product Image:</td>
                            <td><input type="file" name="productImage" required></td>
                        </tr>

                        <tr>
                            <td></td>
                            <td>
                                <input type="submit" value="Add Product">
                            </td>
                        </tr>

                    </table>

                </form>
            </div>
        </div>
    </body>
</html>
