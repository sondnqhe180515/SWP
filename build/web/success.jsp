<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng Ký Thành Công</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f4f9ff;
                margin: 0;
                padding: 0;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }

            .content {
                flex: 1 0 auto;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 20px;
            }

            .container {
                width: 100%;
                max-width: 500px;
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
                text-align: center;
            }

            .header {
                background-color: #64ccff;
                color: white;
                padding: 15px;
                text-align: center;
                font-weight: bold;
                font-size: 20px;
                margin: 0;
            }

            .message-body {
                padding: 20px;
            }

            .message-body p {
                font-size: 16px;
                color: #333;
                margin: 10px 0;
            }

            .message-body a {
                font-weight: bold;
                color: #0078B4;
                text-decoration: none;
                border: 1px solid #64ccff;
                padding: 8px 16px;
                border-radius: 8px;
                background-color: white;
                transition: all 0.3s ease;
                display: inline-block;
            }

            .message-body a:hover {
                background-color: #64ccff;
                color: white;
            }

            @media (max-width: 600px) {
                .content {
                    padding: 15px;
                }

                .container {
                    max-width: 100%;
                }

                .header {
                    font-size: 18px;
                    padding: 12px;
                }

                .message-body {
                    padding: 15px;
                }

                .message-body p {
                    font-size: 14px;
                }
            }
        </style>
    </head>
    <body>
        <div class="content">
            <div class="container">
                <div class="header">Đăng Ký Thành Công!</div>
                <div class="message-body">
                    <p>Chúc mừng bạn đã tạo tài khoản thành công.</p>
                    <p><a href="register.jsp">Quay lại</a></p>
                </div>
            </div>
        </div>
    </body>
</html>