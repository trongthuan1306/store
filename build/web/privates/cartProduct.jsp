<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <link href="css/styleCart.css" rel="stylesheet" type="text/css"/>

    </head>

    <body >

        <%@include file="menu.jspf" %>

        <div class="container" style="margin-top:40px;">

            <h2 class="text-center">
                <span class="glyphicon glyphicon-shopping-cart"></span>
                Your Shopping Cart
            </h2>

            <c:if test="${empty requestScope.ds}">
                <div class="alert alert-warning text-center" style="margin-top:20px;">
                    Your cart is empty!
                </div>
            </c:if>

            <c:if test="${not empty requestScope.ds}">

                <table class="table table-bordered table-hover" style="margin-top:20px;">

                    <thead style="background-color:#343a40; color:white;">
                        <tr>
                            <th>Name</th>
                            <th class="text-center">Price</th>
                            <th class="text-center">Quantity</th>
                            <th class="text-center">Total</th>
                            <th class="text-center">Action</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach var="item" items="${requestScope.ds}">
                            <tr>

                                <td>${item.productId.productName}</td>

                                <td class="text-center text-danger">
                                    <fmt:formatNumber value="${item.productId.price}" groupingUsed="true"/> đ
                                </td>

                                <td class="text-center">

                                    <a href="updateCart?id=${item.productId.productId}&action=minus"
                                       class="btn btn-default btn-xs">-</a>

                                    <strong style="margin:0 10px;">
                                        ${item.quantity}
                                    </strong>

                                    <a href="updateCart?id=${item.productId.productId}&action=plus"
                                       class="btn btn-default btn-xs">+</a>

                                </td>

                                <td class="text-center text-danger">
                                    <fmt:formatNumber value="${item.quantity * item.productId.price}" groupingUsed="true"/> đ
                                </td>

                                <td class="text-center">

                                    <a href="removeCart?id=${item.productId.productId}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Are you sure?')">

                                        <span class="glyphicon glyphicon-trash"></span>
                                        Remove

                                    </a>

                                </td>

                            </tr>
                        </c:forEach>

                    </tbody>
                </table>

                <c:set var="total" value="0"/>

                <c:forEach var="item" items="${requestScope.ds}">
                    <c:set var="total" value="${total + (item.quantity * item.productId.price)}"/>
                </c:forEach>

                <div class="text-right" style="font-size:20px; font-weight:bold;">
                    Total:
                    <span class="text-danger">
                        <fmt:formatNumber value="${total}" groupingUsed="true"/> đ
                    </span>
                </div>

                <div class="text-right" style="margin-top:20px;">

                    <a href="listProduct" class="btn btn-default">
                        Continue Shopping
                    </a>
 
                    <a href="buyOrder" class="btn btn-success">
                     Buy Now   
                    </a>

                </div>

            </c:if>

        </div>

    </body>
</html>