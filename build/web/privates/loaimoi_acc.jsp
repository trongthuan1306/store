<%-- 
    Document   : loai_moi
    Created on : Feb 25, 2026, 3:01:56 PM
    Author     : ASUS
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Add New Account</title>
        <link href="css/styleNewAcc.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>

        <c:if test="${sessionScope.user !=null}" >
            <%@include file="menu.jspf" %>
        </c:if>

        <div class="form-container">

            <div class="form-box">

                <h2>Add new account</h2>
                <h3 style="color:red">${requestScope.error}</h3>

                <form method="post" action="newAccount">

                    <div class="form-group">
                        <label>Account</label>
                        <input class="form-control" name="acc" type="text" placeholder="Enter account">
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input class="form-control" name="pass" type="password" placeholder="Enter password">
                    </div>

                    <div class="form-group">
                        <label>Last name</label>
                        <input class="form-control" name="lname" type="text">
                    </div>

                    <div class="form-group">
                        <label>First name</label>
                        <input class="form-control" name="fname" type="text">
                    </div>

                    <div class="form-group">
                        <label>Phone number</label>
                        <input class="form-control" name="phone" type="text">
                    </div>

                    <div class="form-group">
                        <label>Birth day</label>
                        <input class="form-control" name="bday" type="date">
                    </div>

                    <div class="form-group">
                        <label>Gender</label><br>
                        <input type="radio" name="gender" value="1" checked> Male
                        <input type="radio" name="gender" value="0"> Female
                    </div>

                    <c:if test="${sessionScope.user!=null}">
                        <div class="form-group">
                            <label>Role in system</label>
                            <select class="form-control" name="role">
                                <c:if test="${sessionScope.role==1}">
                                    <option value="1">Administrator</option>
                                </c:if>
                                <option value="2">Manager</option>
                                <option value="3">User</option>
                            </select>
                        </div>

                        <div class="checkbox">
                            <label>
                                <input name="isUse" type="checkbox"> Is active
                            </label>
                        </div>
                    </c:if>

                    <button class="btn btn-primary">Submit</button>

                </form>

            </div>

        </div>

    </body>
</html>
