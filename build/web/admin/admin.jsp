<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sess = request.getSession(false);
    User user = (sess != null) ? (User) sess.getAttribute("user") : null;

    if (user == null || user.getRole() == null || !"Quản trị viên".equals(user.getRole().getRoleName())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashboard</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                height: 100vh;
                background-color: #f9fafe;
            }

            .sidebar {
                width: 220px;
                background-color: #ffffff;
                padding: 20px;
                border-right: 1px solid #dce0e5;
                box-shadow: 2px 0 6px rgba(0,0,0,0.05);
            }

            .sidebar h2 {
                margin-top: 0;
                color: #007acc;
                font-size: 22px;
                font-weight: bold;
            }

            .sidebar a {
                display: block;
                padding: 12px;
                margin-bottom: 8px;
                color: #333;
                text-decoration: none;
                border-radius: 6px;
                transition: background-color 0.2s ease;
            }

            .sidebar a:hover {
                background-color: #e6f3ff;
                color: #007acc;
                font-weight: 500;
            }

            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            .topbar {
                background-color: #d1ecf7;
                padding: 12px 24px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #c0d9e8;
            }

            .topbar img {
                height: 40px;
            }

            .logout-btn {
                background-color: #007acc;
                color: white;
                padding: 8px 16px;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
                transition: background-color 0.3s ease;
            }

            .logout-btn:hover {
                background-color: #005f9e;
            }

            iframe {
                flex: 1;
                border: none;
                background-color: #f9fafe;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2>Admin</h2>
            <a href="${pageContext.request.contextPath}/admin/dashboard" target="contentFrame">Thống kê</a>
            <a href="add-staff.jsp" target="contentFrame">Thêm Nhân Viên/Bác sĩ</a>
            <a href="employee-list" target="contentFrame">Danh Sách Nhân Viên</a>
            <!-- Có thể thêm Doctors / Users / Appointments ở đây -->
        </div>

        <div class="main">
            <div class="topbar">
                <div><img src="${pageContext.request.contextPath}/image/Logo.png" alt="Logo"></div> 
                <div><a href="${pageContext.request.contextPath}/logout" class="logout-btn">Đăng xuất</a></div>
            </div>

            <iframe name="contentFrame" src="${pageContext.request.contextPath}/admin/dashboard"></iframe>
        </div>
    </body>
</html>
