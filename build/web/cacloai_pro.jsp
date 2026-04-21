<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Google Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        
        <!-- CSS -->
        <link href="css/styleGlobal.css" rel="stylesheet" type="text/css"/>
        <link href="css/styleProduct.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        
        <!-- JS -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>Shop - Emart</title>
    </head>
    <body>

        <%@include file="privates/menu.jspf" %>
        
        <div class="galaxy-layout">
            <!-- LEFT HOLOGRAM SIDEBAR -->
            <aside class="hologram-sidebar left">
                <div class="hologram-stage">
                    <div class="prism-container">
                        <div class="prism">
                            <div class="face front"></div>
                            <div class="face back"></div>
                            <div class="face right"></div>
                            <div class="face left"></div>
                            <div class="face top"></div>
                            <div class="face bottom"></div>
                            <div class="internal-glow"></div>
                        </div>
                        <div class="rays"></div>
                    </div>
                </div>
            </aside>

            <!-- MAIN CENTER CONTENT -->
            <main class="container-wrapper">
                <!-- CATEGORY BAR -->
                <div class="category-bar">
                    <a href="listProduct" class="cate-item ${(empty param.cate) ? 'active' : ''}">
                        All Products
                    </a>
                <c:forEach var="c" items="${requestScope.dscate}">
                    <a href="productCate?cate=${c.typeId}" class="cate-item ${(param.cate == c.typeId) ? 'active' : ''}">
                        ${c.categoryName}
                    </a>
                </c:forEach>
            </div>

            <div class="top-controls">
                <!-- FILTER BAR -->
                <form action="listProduct" class="filter-bar">
                    <div class="filter-group">
                        <label>Min price</label>
                        <input type="number" name="minPrice" placeholder="0">
                    </div>
                    <div class="filter-group">
                        <label>Max price</label>
                        <input type="number" name="maxPrice" placeholder="100000">
                    </div>
                    <div class="filter-group">
                        <label>Discount</label>
                        <select name="discount">
                            <option value="">All</option>
                            <option value="1">Have discount</option>
                            <option value="0">No discount</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label>Sort price</label>
                        <select name="sort">
                            <option value="">Default</option>
                            <option value="asc">Price Low → High</option>
                            <option value="desc">Price High → Low</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-filter">Filter</button>
                </form>

                <!-- RIGHT ACTIONS -->
                <div class="right-actions">
                    <a href="Cart" class="btn-cart-top" style="text-decoration:none;">
                        <span class="glyphicon glyphicon-shopping-cart"></span> Cart
                    </a>
                    
                    <c:if test="${session!=null || sessionScope.role==1 || sessionScope.role==2}">
                        <a href="newProduct" class="btn-add-product" style="text-decoration:none;">
                            <span class="glyphicon glyphicon-plus"></span> Add Product
                        </a>
                    </c:if>
                </div>
            </div>

            <!-- PRODUCT GRID -->
            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger" style="border-radius: 10px;">${requestScope.error}</div> 
            </c:if>
            
            <div class="product-grid">
                <c:forEach items="${requestScope.ds}" var="p">
                    <div class="product-card">
                        <a href="detailProduct?id=${p.productId}">
                            <div class="image-wrapper">
                                <img src="${pageContext.request.contextPath}${p.productImage}" 
                                     class="imageProduct" alt="${p.productName}">
                                <c:if test="${p.discount > 0}">
                                    <div class="discount-badge">-${p.discount}%</div>
                                </c:if>
                            </div>
                            <div class="product-info">
                                <h4 class="product-title">${p.productName}</h4>
                                <div class="price-section">
                                    <span class="new-price">
                                        <fmt:formatNumber 
                                            value="${p.price - (p.price * p.discount / 100)}" 
                                            type="number" groupingUsed="true" maxFractionDigits="0"/> đ
                                    </span>
                                    <c:if test="${p.discount > 0}">
                                        <span class="old-price">
                                            <fmt:formatNumber 
                                                value="${p.price}" 
                                                type="number" groupingUsed="true" maxFractionDigits="0"/> đ
                                        </span>
                                    </c:if>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <!-- VIEWED SECTION -->
            <c:if test="${not empty requestScope.dx}">
                <div class="viewed-section">
                    <h3 class="viewed-title">Recently Viewed</h3>
                    <div class="viewed-product">
                        <c:forEach items="${requestScope.dx}" var="p" varStatus="st">
                            <c:if test="${requestScope.showAll || st.index < 5}">
                                <div class="product-card">
                                    <a href="detailProduct?id=${p.productId}">
                                        <div class="image-wrapper">
                                            <img src="${pageContext.request.contextPath}${p.productImage}" 
                                                 class="imageProduct" alt="${p.productName}">
                                            <c:if test="${p.discount > 0}">
                                                <div class="discount-badge">-${p.discount}%</div>
                                            </c:if>
                                        </div>
                                        <div class="product-info">
                                            <h4 class="product-title">${p.productName}</h4>
                                            <div class="price-section">
                                                <span class="new-price">
                                                    <fmt:formatNumber 
                                                        value="${p.price - (p.price * p.discount / 100)}" 
                                                        type="number" groupingUsed="true" maxFractionDigits="0"/> đ
                                                </span>
                                                <c:if test="${p.discount > 0}">
                                                    <span class="old-price">
                                                        <fmt:formatNumber 
                                                            value="${p.price}" 
                                                            type="number" groupingUsed="true" maxFractionDigits="0"/> đ
                                                    </span>
                                                </c:if>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            
            <c:if test="${not requestScope.showAll && requestScope.dx.size() > 5}">
                <div class="see-more">
                    <a href="listProduct?showAll=true" class="btn-see-more">See All Viewed Products</a>
                </div>
            </c:if>
            
            </main>

            <!-- RIGHT HOLOGRAM SIDEBAR -->
            <aside class="hologram-sidebar right">
                <div class="hologram-stage">
                    <div class="prism-container mirror">
                        <div class="prism">
                            <div class="face front"></div>
                            <div class="face back"></div>
                            <div class="face right"></div>
                            <div class="face left"></div>
                            <div class="face top"></div>
                            <div class="face bottom"></div>
                            <div class="internal-glow"></div>
                        </div>
                        <div class="rays"></div>
                    </div>
                </div>
            </aside>
        </div> <!-- End galaxy-layout -->
    </body>
</html>
