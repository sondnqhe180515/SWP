<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thông tin tài khoản</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f4f9ff;
                margin: 0;
                padding: 0;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            .banner {
                background-color: #d0f0fd;
                padding: 15px 30px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
                gap: 12px;
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
                flex-shrink: 0;
            }

            .banner img {
                width: 60px;
                height: 60px;
                object-fit: contain;
            }

            .banner a {
                font-weight: bold;
                color: #0078B4;
                text-decoration: none;
                border: 1px solid #64ccff;
                padding: 8px 16px;
                border-radius: 8px;
                background-color: white;
                transition: all 0.3s ease;
            }

            .banner a:hover {
                background-color: #64ccff;
                color: white;
            }

            .content {
                flex: 1 0 auto;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                padding: 20px;
            }

            .info-container {
                width: 100%;
                max-width: 800px;
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            }

            .header {
                background-color: #64ccff;
                color: white;
                padding: 15px;
                text-align: center;
                font-weight: bold;
                font-size: 20px;
            }

            .info-body {
                padding: 20px;
            }

            .info-body p {
                display: flex;
                align-items: center;
                padding: 10px 0;
                font-size: 16px;
                margin: 0;
            }

            .info-body p:hover {
                background-color: #e0f7ff;
            }

            .info-body p strong {
                width: 150px;
                color: #0078B4;
                font-weight: 600;
                flex-shrink: 0;
                text-align: right;
                padding-right: 20px;
            }

            .info-body p span {
                color: #333;
                flex: 1;
                text-align: left;
            }

            .message, .error {
                padding: 10px;
                margin: 10px 0;
                border-radius: 6px;
                text-align: center;
                font-weight: 500;
                font-size: 15px;
            }

            .message {
                background-color: #e7f5ff;
                color: #0078B4;
            }

            .error {
                background-color: #ffe0e0;
                color: #dc3545;
            }

            .no-user {
                text-align: center;
                padding: 20px;
                color: #333;
                font-size: 16px;
            }

            .no-user code {
                background-color: #e7f5ff;
                padding: 2px 8px;
                border-radius: 4px;
                font-family: 'Courier New', Courier, monospace;
            }

            footer {
                background-color: #d0f0fd;
                text-align: center;
                padding: 15px;
                font-weight: bold;
                color: #0078B4;
                border-top: 1px solid #ccc;
                flex-shrink: 0;
            }

            @media (max-width: 600px) {
                .banner {
                    flex-direction: column;
                    gap: 16px;
                    padding: 12px;
                }

                .info-body {
                    padding: 15px;
                }

                .info-body p {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 8px;
                }

                .info-body p strong {
                    width: auto;
                    text-align: left;
                    padding-right: 0;
                }

                .info-body p span {
                    text-align: left;
                }

                .header {
                    font-size: 18px;
                    padding: 12px;
                }
            }
        </style>
    </head>
    <body>
        <div class="banner">
            <img src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474089BGn/mau-logo-rang-vang-tach-nen_045001529.png" alt="Logo"/>
            <a href="#">Trang chủ</a>
        </div>
        <div class="content">
            <div class="info-container">
                <div class="header">Thông tin tài khoản</div>
                <div class="info-body">
                    <c:if test="${not empty error}">
                        <p class="error">${error}</p>
                    </c:if>
                    <c:if test="${not empty message}">
                        <p class="message">${message}</p>
                    </c:if>

                    <c:if test="${not empty user}">
                        <p><strong>Họ tên:</strong> <span>${user.fullName}</span></p>
                        <p><strong>Email:</strong> <span>${user.email}</span></p>
                        <p><strong>Số điện thoại:</strong> <span>${user.phoneNumber}</span></p>
                        <p><strong>Địa chỉ:</strong> <span>${user.address}</span></p>
                        <p><strong>Giới tính:</strong> <span><c:out value="${user.gender != null ? user.gender : '-'}" /></span></p>
                        <p><strong>Ngày sinh:</strong> <span>
                                <c:choose>
                                    <c:when test="${not empty user.dateOfBirth}">
                                        <fmt:formatDate value="${user.dateOfBirth}" pattern="yyyy-MM-dd" />
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </span></p>
                        <p><strong>Vai trò:</strong> <span>${user.roleName}</span></p>
                        <p><strong>Trạng thái:</strong> <span>${user.status ? "Hoạt động" : "Ngừng hoạt động"}</span></p>
                        <p><strong>Ngày tạo:</strong> <span><fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/></span></p>
                    </c:if>

                    <c:if test="${empty user}">
                        <div class="no-user">
                            <p>Vui lòng nhập UserID trong URL để xem thông tin cá nhân.</p>
                            <p>Ví dụ: <code>?userId=1</code></p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <footer>
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </footer>
    </body>
</html>