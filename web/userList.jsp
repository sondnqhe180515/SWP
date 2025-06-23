<%-- 
    Document   : userList
    Created on : May 29, 2025, 12:14:44 AM
    Author     : An
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách người dùng Quản trị viên</title> <%-- Thay đổi tiêu đề --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userList.css">
</head>
<body>
    <div class="header">
        <div class="header-left">
            <img src="https://placehold.co/50x50/007bff/FFFFFF?text=Logo" alt="Dental Clinic Logo"> <%-- Placeholder logo --%>
        </div>
        <div class="header-center">
            <h1>Hệ thống Quản lý Phòng khám Nha khoa</h1>
        </div>
        <div class="header-right">
            <div class="menu-icon" id="menuIcon">&#9776;</div> <%-- Menu --%>
        </div>
    </div>

    <div class="side-menu" id="sideMenu">
        <ul>
            <li><a href="#">Trang chủ</a></li>
            <li><a href="#">Quản lý người dùng</a></li>
            <li><a href="#">Quản lý lịch hẹn</a></li>
            <li><a href="#">Quản lý dịch vụ</a></li>
            <li><a href="#">Cài đặt</a></li>
            <li><a href="#">Đăng xuất</a></li>
        </ul>
    </div>

    <div class="overlay" id="overlay"></div>

    <h1 class="page-title">Danh sách người dùng Quản trị viên</h1> 

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Họ và tên</th>
                <th>Email</th>
                <th>Số điện thoại</th>
                <th>Địa chỉ</th>
                <th>Vai trò</th>
                <th>Trạng thái</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="o" items="${listP}"> 
                <tr>
                    <td><c:out value="${o.userId}"/></td>
                    <td><c:out value="${o.fullName}"/></td>
                    <td><c:out value="${o.email}"/></td>
                    <td><c:out value="${o.phoneNumber}"/></td>
                    <td><c:out value="${o.address}"/></td>
                    <td><c:out value="${o.roleId}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${o.status}">
                                <span class="status-active">Hoạt động</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-suspended">Đã khóa</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                     <td>
                        <a href="${pageContext.request.contextPath}/userDetail?userId=<c:out value="${o.userId}"/>" class="action-link">Xem chi tiết</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty listP}">
                <tr>
                    <td colspan="7" class="no-data">Không có người dùng !</td> 
                </tr>
            </c:if>
        </tbody>
    </table>

    <div class="back-link">
        <p><a href="${pageContext.request.contextPath}/">Quay lại trang chủ</a></p>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const menuIcon = document.getElementById('menuIcon');
            const sideMenu = document.getElementById('sideMenu');
            const overlay = document.getElementById('overlay');

            menuIcon.addEventListener('click', function() {
                sideMenu.classList.toggle('open');
                overlay.classList.toggle('active');
            });

            overlay.addEventListener('click', function() {
                sideMenu.classList.remove('open');
                overlay.classList.remove('active');
            });
        });
    </script>
</body>
</html>
