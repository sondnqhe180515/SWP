<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lịch thành công</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;700&family=Roboto:wght@400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            line-height: 1.6;
            color: #333;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        
        .wrapper {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .success-container {
            background-color: #fff;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            margin-top: 50px;
        }
        
        .success-icon {
            width: 80px;
            height: 80px;
            background-color: #4CAF50;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
        }
        
        .success-icon svg {
            width: 40px;
            height: 40px;
            fill: white;
        }
        
        h1 {
            color: #4CAF50;
            font-family: 'Poppins', sans-serif;
            font-weight: 700;
            margin-bottom: 20px;
        }
        
        .message {
            font-size: 18px;
            margin-bottom: 30px;
        }
        
        .booking-details {
            background-color: #f9f9f9;
            border-radius: 5px;
            padding: 20px;
            margin-bottom: 30px;
            text-align: left;
        }
        
        .booking-details p {
            margin: 10px 0;
        }
        
        .btn {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 12px 24px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            transition: background-color 0.3s;
        }
        
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <%@ include file="include/header.jsp" %>
    
    <div class="wrapper">
        <div class="success-container">
            <div class="success-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/>
                </svg>
            </div>
            
            <h1>Đặt lịch thành công!</h1>
            
            <div class="message">
                <%
                String successMessage = (String) session.getAttribute("successMessage");
                if (successMessage != null) {
                    out.println(successMessage);
                    session.removeAttribute("successMessage"); // Clear the message after displaying
                } else {
                    out.println("Cảm ơn bạn đã đặt lịch khám tại phòng khám nha khoa của chúng tôi.");
                }
                %>
            </div>
            
            <div class="booking-details">
                <p><strong>Lưu ý:</strong></p>
                <p>- Vui lòng đến trước giờ hẹn 15 phút để làm thủ tục.</p>
                <p>- Mang theo giấy tờ tùy thân và thẻ bảo hiểm y tế (nếu có).</p>
                <p>- Nếu bạn cần thay đổi lịch hẹn, vui lòng liên hệ với chúng tôi trước ít nhất 24 giờ.</p>
                <p>- Nhân viên của chúng tôi sẽ gọi điện xác nhận lịch hẹn của bạn trong vòng 24 giờ.</p>
            </div>
            
            <a href="index.jsp" class="btn">Trở về trang chủ</a>
        </div>
    </div>
    
    <%@ include file="include/footer.jsp" %>
</body>
</html>
