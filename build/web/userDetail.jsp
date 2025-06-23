<%-- 
    Document   : userDetail
    Created on : May 29, 2025, 12:14:44 AM
    Author     : An
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thông tin chi tiết mẫu</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userDetail.css"> 
    </head>
    <body>
        <div class="header">
            <div class="header-left">
                <img src="https://placehold.co/50x50/007bff/FFFFFF?text=Logo" alt="Dental Clinic Logo">
            </div>
            <div class="header-center">
                <h1>Hệ thống Quản lý Phòng khám Nha khoa</h1>
            </div>
            <div class="header-right">
                <div class="menu-icon" id="menuIcon">&#9776;</div>
            </div>
        </div>

        <div class="side-menu" id="sideMenu">
            <ul>
                <li><a href="#">Trang chủ</a></li>
                <li><a href="${pageContext.request.contextPath}/adminUsers">Quản lý người dùng</a></li>
                <li><a href="#">Quản lý lịch hẹn</a></li>
                <li><a href="#">Quản lý dịch vụ</a></li>
                <li><a href="#">Cài đặt</a></li>
                <li><a href="#">Đăng xuất</a></li>
            </ul>
        </div>

        <div class="overlay" id="overlay"></div>

        <div class="container">
            <h1 class="page-title">Thông tin người dùng</h1>
            <c:if test="${not empty user}">
                <div class="detail-card">
                    <img src="https://placehold.co/120x120/007bff/FFFFFF?text=User" alt="Ảnh cá nhân" class="user-image">
                    <div class="user-info">
                        <p><strong>ID:</strong> ${user.userId}</p>
                        <p><strong>Tên:</strong> ${user.fullName}</p>
                        <p><strong>Email:</strong> ${user.email}</p>
                        <p><strong>Số điện thoại:</strong> ${user.phoneNumber}</p>
                        <p><strong>Địa chỉ:</strong> ${user.address}</p>
                        <p><strong>Vai trò:</strong> ${user.roleId}</p>
                        <p><strong>Trạng thái:</strong> 
                            <span class="status-active">Hoạt động</span> 
                        </p>
                        <p><strong>Ngày tạo:</strong> 2024-01-15</p>
                        <p><strong>Ghi chú:</strong> example!!!</p>
                    </div>
                </div>
            </c:if>
            <div class="action-buttons">
                <a href="#" class="edit-button">Chỉnh sửa</a> <%-- Nút Chỉnh sửa --%>
                <a href="#" class="delete-button">Xóa</a> <%-- Nút Xóa --%>
            </div>

            <div class="back-link">
                <p><a href="${pageContext.request.contextPath}/AccControll">Quay lại danh sách người dùng</a></p>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const menuIcon = document.getElementById('menuIcon');
                const sideMenu = document.getElementById('sideMenu');
                const overlay = document.getElementById('overlay');

                menuIcon.addEventListener('click', function () {
                    sideMenu.classList.toggle('open');
                    overlay.classList.toggle('active');
                });

                overlay.addEventListener('click', function () {
                    sideMenu.classList.remove('open');
                    overlay.classList.remove('active');
                });
            });
        </script>
    </body>
</html>
