<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lỗi</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f2fbff;
            margin: 0;
            padding: 20px;
            text-align: center;
        }

        .container {
            max-width: 600px;
            margin: 100px auto;
            padding: 30px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid #ffdede;
        }

        h2 {
            color: #e74c3c;
            margin-bottom: 20px;
        }

        p {
            font-size: 16px;
            color: #555;
            margin-bottom: 30px;
        }

        .btn {
            display: inline-block;
            margin: 0 10px;
            padding: 12px 24px;
            background-color: #e74c3c;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            font-size: 15px;
            transition: background-color 0.3s ease-in-out;
        }

        .btn:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>❌ Đã xảy ra lỗi khi lưu hồ sơ!</h2>
        <p>Vui lòng kiểm tra lại thông tin hoặc thử lại sau.</p>
        <a href="addMedicalRecord.jsp" class="btn">🔄 Thử lại</a>
        <a href="doctorDashboard.jsp" class="btn">🏠 Về trang bác sĩ</a>
    </div>
</body>
</html>
