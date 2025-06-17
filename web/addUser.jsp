<%-- 
    Document   : addUser
    Created on : Jun 5, 2025, 7:50:57 AM
    Author     : An
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm người dùng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addUser.css">
</head>
<body>
    <h2>Thêm người dùng mới</h2>
    
    <form action="${pageContext.request.contextPath}/addUserServlet" method="post">
        <label for="fullName">Họ và tên:</label><br>
        <input type="text" id="fullName" name="fullName" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="phoneNumber">Số điện thoại:</label><br>
        <input type="text" id="phoneNumber" name="phoneNumber" required><br><br>

        <label for="address">Địa chỉ:</label><br>
        <input type="text" id="address" name="address"><br><br>

        <label for="roleId">Vai trò:</label><br>
        <select name="roleId" id="roleId" required>
            <option value="1">Admin</option>
            <option value="2">Bác sĩ</option>
            <option value="3">Lễ tân</option>
        </select><br><br>

        <input type="submit" value="Thêm người dùng">
        <a href="AccControll">Hủy</a>
    </form>
</body>
</html>

