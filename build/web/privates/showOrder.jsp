<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order History | Emart</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- Bootstrap 3 (Project Default) -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <style>
        body {
            perspective: var(--perspective);
            overflow-x: hidden;
        }

        .history-container {
            padding: 60px 15px;
            max-width: 1300px;
            margin: 0 auto;
            animation: fadeInUp 0.8s var(--transition-smooth) forwards;
            transform-style: preserve-3d;
        }

        .page-header-box {
            margin-bottom: 50px;
            text-align: center;
            transform: translateZ(30px);
        }

        .page-title {
            font-weight: 900;
            font-size: 5rem;
            background: linear-gradient(135deg, var(--text-main), var(--primary));
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 15px;
            letter-spacing: -2px;
        }

        .glass-card {
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(20px);
            border: 1px solid var(--glass-border);
            border-radius: 35px;
            box-shadow: var(--shadow-3d);
            padding: 40px;
            transform-style: preserve-3d;
            transition: var(--transition-smooth);
        }

        .glass-card:hover {
            transform: rotateX(1deg) translateY(-5px);
        }

        .custom-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0 15px;
            transform: translateZ(20px);
        }

        .custom-table thead th {
            border: none;
            color: var(--text-muted);
            font-weight: 800;
            text-transform: uppercase;
            font-size: 1.2rem;
            letter-spacing: 0.1em;
            padding: 15px 25px;
        }

        .custom-table tbody tr {
            background: rgba(255, 255, 255, 0.5);
            transition: var(--transition-smooth);
            border-radius: 20px;
            transform-style: preserve-3d;
        }

        .custom-table tbody tr:hover {
            background: white;
            transform: translateY(-8px) scale(1.01) translateZ(15px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.06);
        }

        .custom-table tbody td {
            padding: 25px;
            border: none;
            vertical-align: middle;
        }

        .custom-table tbody tr td:first-child { border-radius: 20px 0 0 20px; }
        .custom-table tbody tr td:last-child { border-radius: 0 20px 20px 0; }

        .order-id {
            font-weight: 800;
            color: var(--primary);
            font-size: 1.6rem;
            background: rgba(99, 102, 241, 0.08);
            padding: 6px 14px;
            border-radius: 100px;
        }

        .cust-name { font-weight: 800; font-size: 1.7rem; color: var(--text-main); }
        .cust-meta { font-size: 1.3rem; color: var(--text-muted); font-weight: 500; }

        .status-badge {
            padding: 8px 18px;
            border-radius: 100px;
            font-size: 1.2rem;
            font-weight: 800;
            display: inline-block;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            transform: translateZ(5px);
        }

        .status-processing { background: #fef3c7; color: #92400e; }
        .status-shipping { background: #dbeafe; color: #1e40af; }
        .status-delivered { background: #dcfce7; color: #166534; }

        .price-tag {
            font-weight: 900;
            font-size: 1.8rem;
            color: var(--text-main);
            transform: translateZ(10px);
        }

        .empty-state {
            text-align: center;
            padding: 80px 0;
            transform: translateZ(40px);
        }
    </style>
</head>
<body>
    <%@include file="menu.jspf" %>

    <div class="history-container">
        <div class="page-header-box">
            <h1 class="page-title">Order History</h1>
            <p class="text-muted">Track and manage your premium purchases</p>
        </div>

        <div class="glass-card">
            <c:if test="${empty requestScope.orders}">
                <div class="empty-state">
                    <span class="glyphicon glyphicon-shopping-cart empty-icon"></span>
                    <h3>No orders found</h3>
                    <p class="text-muted">You haven't placed any orders yet.</p>
                    <a href="${pageContext.request.contextPath}/listProduct" class="btn btn-primary" style="margin-top: 20px; border-radius: 10px; padding: 10px 25px; background: var(--primary-gradient); border: none;">Start Shopping</a>
                </div>
            </c:if>

            <c:if test="${not empty requestScope.orders}">
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th class="text-center">No.</th>
                                <th>Order ID</th>
                                <th>Shipment Info</th>
                                <th class="text-center">Date Created</th>
                                <th class="text-center">Total Amount</th>
                                <th class="text-center">Status</th>
                                <c:if test="${sessionScope.role == 1 || sessionScope.role == 2}">
                                    <th class="text-center">Account</th>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${requestScope.orders}" varStatus="st">
                                <tr>
                                    <td class="text-center" style="font-weight: 600; color: var(--text-muted); font-size: 1.2rem;">
                                        ${st.index + 1}
                                    </td>
                                    <td>
                                        <span class="order-id">#${o.orderId}</span>
                                    </td>
                                    <td>
                                        <div class="customer-info">
                                            <span class="cust-name">${o.custName}</span>
                                            <span class="cust-meta">${o.custPhone}</span>
                                            <span class="cust-meta" style="font-size: 1.1rem;">${o.custAddr}</span>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <div style="font-weight: 500;">
                                            <fmt:formatDate value="${o.createdDate}" pattern="MMM dd, yyyy"/>
                                        </div>
                                        <div style="font-size: 1.1rem; color: var(--text-muted);">
                                            <fmt:formatDate value="${o.createdDate}" pattern="HH:mm"/>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <span class="price-tag">
                                            <fmt:formatNumber value="${o.totalValue}" groupingUsed="true"/> đ
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${o.ordState == 0}">
                                                <span class="status-badge status-processing">Processing</span>
                                            </c:when>
                                            <c:when test="${o.ordState == 1}">
                                                <span class="status-badge status-shipping">Shipping</span>
                                            </c:when>
                                            <c:when test="${o.ordState == 2}">
                                                <span class="status-badge status-delivered">Delivered</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-unknown">Unknown</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <c:if test="${sessionScope.role == 1 || sessionScope.role == 2}">
                                        <td class="text-center">
                                            <span class="label label-default" style="border-radius: 4px; font-weight: 500;">${o.account.account}</span>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</body>
</html>