<%-- 
    Document   : buy_pro
    Created on : Mar 15, 2026, 3:31:03 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styleBuy.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body>

    <body >
        <%@include file="menu.jspf" %>
        <div class="container" style="margin-top:20px;">
            <div style="margin-left: 14px; margin-bottom: 20px">
        <a href="Cart" >
            <button >Back</button>
        </a>
                </div>
            <div class="col-md-7">

            <!-- LEFT SIDE -->

                <div class="checkout-box">

                    <h4>Thông tin thanh toán</h4>

                    <form action="createOrder" method="post">

                        <div class="form-group">
                            <label>Họ và tên</label>
                            <input type="text" name="name" class="form-control" required="" >
                        </div>

                        <div class="form-group">
                            <label>Email</label>
                            <input type="text" name="email" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label>Điện thoại</label>
                            <input type="text" name="phone" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label>Địa chỉ</label>
                            <input type="text" name="address" class="form-control"required>
                        </div>

                        <h4>Phương thức thanh toán</h4>

                        <div class="radio">
                            <label>
                                <input type="radio" name="payment" checked>
                                Thanh toán khi giao hàng (COD)
                            </label>
                        </div>

                        <br>

                        <button class="btn btn-primary btn-lg">
                            Đặt hàng
                        </button>

                    </form>

                </div>

            </div>


            <!-- RIGHT SIDE -->
            <div class="col-md-5">

                <div class="product-box">

                    <h4>Đơn hàng của bạn</h4>

                    <c:set var="total" value="0"/>

                    <c:forEach var="item" items="${requestScope.ds}">

                        <div class="product-item row">

                            <div class="col-md-8">
                                <b>${item.productId.productName}</b>
                                <br>
                                x ${item.quantity}
                            </div>

                            <div class="col-md-4 text-right text-danger">

                                <fmt:formatNumber 
                                    value="${item.quantity * item.productId.price}" 
                                    groupingUsed="true"/> đ

                            </div>

                        </div>



                    </c:forEach>



                    <div class="form-group" style="margin-top:15px;">
                        <input type="text" class="form-control"
                               placeholder="Mã giảm giá">
                    </div>

                    <div class="row total-box">

                        <div class="col-md-6">
                            Tổng tiền hàng 
                        </div>

                        <div class="col-md-6 text-right">

                            <fmt:formatNumber value="${requestScope.totalO}" groupingUsed="true"/> đ

                        </div>

                    </div>

                    <div class="row total-box">

                        <div class="col-md-6">
                            Giảm giá
                        </div>

                        <div class="col-md-6 text-right">

                            <fmt:formatNumber value="${requestScope.totalD}" groupingUsed="true"/> đ

                        </div>

                    </div>



                    <div class="row total-box">

                        <div class="col-md-6">
                            <b>Thanh toán </b>
                        </div>

                        <div class="col-md-6 text-right text-danger">

                            <b>
                                <fmt:formatNumber value="${requestScope.totalAfter}" groupingUsed="true"/> đ
                            </b>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    </body>
</html>
