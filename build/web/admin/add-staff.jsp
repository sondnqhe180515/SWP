<%-- 
    Document   : add-staff
    Created on : May 29, 2025, 8:21:05 AM
    Author     : FPT SHOP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Thêm nhân viên</title>
    <link rel="stylesheet" href="../css/add-staff.css">
    </head>
<body>
<h2>Thêm nhân viên</h2>

<% if (request.getAttribute("error") != null) { %>
    <p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>
<% if (request.getAttribute("success") != null) { %>
    <p style="color:green;"><%= request.getAttribute("success") %></p>
<% } %>


<form action="<%= request.getContextPath() %>/addstaffservlet" method="post">

    <label>Họ và tên:</label><br>
    <input type="text" name="fullName" required><br><br>

    <label>Tên đăng nhập (email):</label><br>
    <input type="email" name="email" required><br><br>

    <label>Mật khẩu:</label><br>
    <input type="text" name="password" required><br><br>

    <label>Địa chỉ:</label><br>
    <input type="text" name="address"><br><br>

    <label>Vai trò:</label><br>
    <select name="roleId" required>
        <option value="2">Nhân viên</option>
        <option value="3">Bác sĩ</option>
        <option value="4">Quản trị viên</option>
    </select><br><br>

    <label>Số điện thoại:</label><br>
    <input type="text" name="phone"><br><br>
    
    <label>Ngày sinh:</label><br>
    <input type="date" name="dob" required><br><br>
    
    
    <label>Giới tính:</label><br>
    <select name="gender" required>
        <option value="Nam">Nam</option>
        <option value="Nữ">Nữ</option>
        <option value="Khác">Khác</option>
    </select><br><br>

    <input type="submit" value="Thêm">
</form>
</body>
</html>