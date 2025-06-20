<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đã xảy ra lỗi</title>
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
        
        .error-container {
            background-color: #fff;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            margin-top: 50px;
            margin-bottom: 50px;
        }
        
        .error-icon {
            width: 80px;
            height: 80px;
            background-color: #f44336;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
        }
        
        .error-icon svg {
            width: 40px;
            height: 40px;
            fill: white;
        }
        
        h1 {
            color: #f44336;
            font-family: 'Poppins', sans-serif;
            font-weight: 700;
            margin-bottom: 20px;
        }
        
        .message {
            font-size: 18px;
            margin-bottom: 30px;
        }
        
        .error-details {
            background-color: #f9f9f9;
            border-radius: 5px;
            padding: 20px;
            margin-bottom: 30px;
            text-align: left;
            color: #f44336;
            font-family: monospace;
            max-height: 200px;
            overflow-y: auto;
        }
        
        .btn-group {
            margin-top: 20px;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 24px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            transition: background-color 0.3s;
            margin: 0 10px;
        }
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-secondary {
            background-color: #007acc;
            color: white;
        }
        
        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <%@ include file="include/header.jsp" %>
    
    <div class="body-content">
        <div class="error-container">
            <div class="error-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"/>
                </svg>
            </div>
            
            <h1>Đã xảy ra lỗi</h1>
            
            <div class="message">
                Rất tiếc, đã xảy ra lỗi khi xử lý yêu cầu của bạn.
            </div>
            
            <% 
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
            <div class="error-details">
                <%= errorMessage %>
            </div>
            <% } %>
            
            <div class="btn-group">
                <a href="javascript:history.back()" class="btn btn-primary">Quay lại</a>
                <a href="home" class="btn btn-secondary">Về trang chủ</a>
            </div>
            
            <p style="margin-top: 30px;">
                Nếu vấn đề vẫn tiếp tục xảy ra, vui lòng liên hệ với chúng tôi qua số điện thoại: <strong>0123 456 789</strong>
            </p>
        </div>
    </div>
    
    <%@ include file="include/footer.jsp" %>
</body>
</html>
