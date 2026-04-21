<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <!-- Google Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                    rel="stylesheet">

                <link href="css/styleGlobal.css" rel="stylesheet" type="text/css" />
                <link href="css/style_proDetail.css" rel="stylesheet" type="text/css" />
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
                <title>${product.productName} - Emart</title>
            </head>

            <body>
                <%@include file="privates/menu.jspf" %>

                    <div class="container detail-wrapper">
                        <!-- Breadcrumbs -->
                        <div class="breadcrumb-glass">
                            <a href="listProduct">Home</a> &nbsp; / &nbsp;
                            <a href="productCate?cate=${product.typeId.typeId}">${product.typeId.categoryName}</a>
                            &nbsp; / &nbsp;
                            <span class="current-item">${product.productName}</span>
                        </div>

                        <div class="detail-card glass-card">
                            <div class="detail-left">
                                <div class="main-image-container">
                                    <img src="${pageContext.request.contextPath}${product.productImage}"
                                        class="detail-img">
                                    <c:if test="${product.discount > 0}">
                                        <div class="discount-badge">-${product.discount}% OFF</div>
                                    </c:if>
                                </div>
                            </div>

                            <div class="detail-right">
                                <div class="category-tag">${product.typeId.categoryName}</div>
                                <h1 class="detail-name">${product.productName}</h1>

                                <div class="detail-price-box">
                                    <span class="price">
                                        <fmt:formatNumber
                                            value="${product.price - (product.price * product.discount / 100)}"
                                            pattern="#,##0" /> đ
                                    </span>
                                    <c:if test="${product.discount > 0}">
                                        <span class="old-price">
                                            <fmt:formatNumber value="${product.price}" pattern="#,##0" /> đ
                                        </span>
                                    </c:if>
                                </div>

                                <div class="desc-box">
                                    <h3>Product Description</h3>
                                    <p class="detail-desc">
                                        ${product.brief}
                                    </p>
                                </div>

                                <div class="action-buttons">
                                    <a href="buyOrder?productId=${product.productId}"
                                        class="btn btn-buy-now">
                                        <span class="glyphicon glyphicon-flash"></span> Buy Now
                                    </a>
                                    <a href="addToCart?id=${product.productId}" class="btn-primary-add">
                                        <span class="glyphicon glyphicon-shopping-cart"></span> Add To Cart
                                    </a>
                                </div>

                                <c:if test="${session != null || sessionScope.role == 1 || sessionScope.role == 2}">
                                    <div class="admin-actions">
                                        <a href="updateProduct?productId=${product.productId}"
                                            class="btn-action edit-btn">
                                            <span class="glyphicon glyphicon-edit"></span> Update Product
                                        </a>
                                        <a href="deleteProduct?productId=${product.productId}"
                                            class="btn-action delete-btn"
                                            onclick="return confirm('Bạn có chắc muốn xóa?')">
                                            <span class="glyphicon glyphicon-trash"></span> Delete Product
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
            </body>

            </html>