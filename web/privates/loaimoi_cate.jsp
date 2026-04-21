<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Categories</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link href="css/styleCate.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="style.css">

</head>

<body>

<%@include file="menu.jspf" %>

<div class="form-container">

    <div class="form-box">

        <h2>New Categories</h2>

        <form method="POST" action="newCategories">

            <div class="form-group">
                <label>Category Name</label>
                <input class="form-control" type="text" name="name" placeholder="Nhóm hàng mới">
            </div>

            <div class="form-group">
                <label>Memo</label>
                <input class="form-control" type="text" name="memo" placeholder="Những sản phẩm dùng cho du lịch, thám hiểm">
            </div>

            <button class="btn btn-primary">Save</button>

        </form>

    </div>

</div>

</body>
</html>