<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thành công</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f2fbff;
            margin: 0;
            padding: 20px;
            text-align: center;
        }

        h2 {
            color: #0078B4;
            margin-bottom: 20px;
        }

        .container {
            max-width: 600px;
            margin: 100px auto;
            padding: 30px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid #ccefff;
        }

        p {
            font-size: 16px;
            color: #333;
            margin-bottom: 30px;
        }

        .btn {
            display: inline-block;
            margin: 0 10px 10px;
            padding: 12px 24px;
            background-color: #64ccff;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            font-size: 15px;
            transition: background-color 0.3s ease-in-out;
        }

        .btn:hover {
            background-color: #45bde0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>✔️ Hồ sơ khám bệnh đã được lưu thành công!</h2>
        <p>Cảm ơn bác sĩ đã nhập thông tin.</p>
        <a href="addMedicalRecord.jsp" class="btn">➕ Nhập hồ sơ khác</a>
        <a href="viewMedicalRecords" class="btn">📄 Xem danh sách</a>
        <a href="doctorDashboard.jsp" class="btn">🏠 Về trang bác sĩ</a>
    </div>
</body>
</html>
