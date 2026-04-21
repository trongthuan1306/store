<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Emart</title>
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    
    <link href="css/styleGlobal.css" rel="stylesheet" type="text/css"/>
    <link href="css/styleLogin.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<div class="login-page">
    <div class="shape shape-1"></div>
    <div class="shape shape-2"></div>
    <div class="shape shape-3"></div>

    <div class="login-container glass-card">
        <form action="dangnhap" method="post">
            <c:if test="${sessionScope.sls >=3}">
                <jsp:forward page="privates/access_deniend.jsp"></jsp:forward>
            </c:if>
            
            <div class="login-header">
                <h2>Welcome Back</h2>
                <p>Please enter your details to sign in</p>
            </div>

            <div class="input-group">
                <label>Username</label>
                <input type="text" name="acc" placeholder="Enter username" required>
            </div>
            
            <div class="input-group">
                <label>Password</label>
                <div class="password-box">
                    <input type="password" name="psw" id="password" placeholder="Enter password" required>
                    <span class="toggle" onclick="togglePassword()">👁</span>
                </div>
            </div>

            <button type="submit" class="btn-login">Sign In</button>

            <c:if test="${not empty requestScope.msg}">
                <div class="error-msg">
                    <p>${requestScope.msg}</p>
                    <c:if test="${not empty sessionScope.sls}">
                        <p class="error-detail">Sai ${sessionScope.sls} lần rồi nha</p>
                    </c:if>
                </div>
            </c:if>

            <p class="register-text">
                Don't have an account?
                <a href="newAccount">Register here</a>
            </p>
        </form>
    </div>
</div>

<script>
function togglePassword() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}
</script>

</body>
</html>