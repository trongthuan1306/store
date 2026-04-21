<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Account - Emart</title>
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
            <h2>Update Account</h2>
        </div>

        <form method="post" action="updateAccount" class="modern-form">
            <!-- Hidden Fields -->
            <input name="acc" type="hidden" value="${requestScope.ds.account}">
            <input name="pass" type="hidden" value="${requestScope.ds.pass}">

            <div class="row">
                <div class="col-md-6 form-group">
                    <label>First name</label>
                    <input name="fname" type="text" class="form-control" value="${requestScope.ds.firstName}" required>
                </div>
                <div class="col-md-6 form-group">
                    <label>Last name</label>
                    <input name="lname" type="text" class="form-control" value="${requestScope.ds.lastName}" required>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label>Phone number</label>
                    <input name="phone" type="text" class="form-control" value="${requestScope.ds.phone}" required>
                </div>
                <div class="col-md-6 form-group">
                    <label>Birth day</label>
                    <input name="bday" type="date" class="form-control" value="${requestScope.ds.birthday}" required>
                </div>
            </div>

            <div class="form-group">
                <label>Gender</label>
                <div class="radio-group">
                    <div class="radio-item">
                        <input type="radio" name="gender" id="male" value="1" ${requestScope.ds.gender ? "checked" : ""}>
                        <label for="male">Male</label>
                    </div>
                    <div class="radio-item">
                        <input type="radio" name="gender" id="female" value="0" ${!requestScope.ds.gender ? "checked" : ""}>
                        <label for="female">Female</label>
                    </div>
                </div>
            </div>

            <c:if test="${sessionScope.role == 1}">
                <div class="form-group">
                    <label>Role in system</label>
                    <select name="role" class="form-control">
                        <option value="1" ${requestScope.ds.roleInSystem == 1 ? "selected" : ""}>Administrator</option>
                        <option value="2" ${requestScope.ds.roleInSystem == 2 ? "selected" : ""}>Manager</option>
                        <option value="3" ${requestScope.ds.roleInSystem == 3 ? "selected" : ""}>User</option>
                    </select>
                </div>
            </c:if>

            <div class="form-group checkbox-group">
                <input name="isUse" type="checkbox" id="active" value="1" ${requestScope.ds.isUse ? "checked" : ""}>
                <label for="active">Is active (Allow Login)</label>
            </div>

            <button type="submit" class="btn-submit">
                <span class="glyphicon glyphicon-floppy-disk"></span> Save Changes
            </button>
            <a href="listProduct" class="cancel-link">Cancel</a>
        </form>
    </div>
</body>
</html>
