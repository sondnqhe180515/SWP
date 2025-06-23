<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.text.NumberFormat" %>
<%
    Integer totalUsers = (Integer) request.getAttribute("totalUsers");
    Integer staffCount = (Integer) request.getAttribute("staffCount");
    Integer customerCount = (Integer) request.getAttribute("customerCount");
    Integer doctorCount = (Integer) request.getAttribute("doctorCount");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="../css/dashboard.css">
</head>
<body>
    <h2>Thống kê hệ thống</h2>

    <div class="card-container">
        <div class="card blue">
            <h3>Tổng người dùng</h3>
            <p><%= totalUsers != null ? totalUsers : "N/A" %></p>
        </div>
        <div class="card green">
            <h3>Nhân viên</h3>
            <p><%= staffCount != null ? staffCount : "N/A" %></p>
        </div>
        <div class="card cyan">
            <h3>Bệnh nhân</h3>
            <p><%= customerCount != null ? customerCount : "N/A" %></p>
        </div>
        <div class="card orange">
            <h3>Bác sĩ</h3>
            <p><%= doctorCount != null ? doctorCount : "N/A" %></p>
        </div>
    </div>
        
</body>
</html>
