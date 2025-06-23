<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt lịch khám</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;700&family=Roboto:wght@400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/bookingStyle.css"/>
</head>
<body>
    <div class="wrapper">

        <div class="section1">
            <div class="logo">
                <img src="image/Logo.png" alt="logo"/>
            </div>
            <input type="text" placeholder="Tìm kiếm..." class="search-box">
            <div class="actions">
                <button>Hotline: 0987654321</button>
                <button>Hỗ trợ khách hàng</button>
                <button>Đăng nhập / Đăng ký</button>
            </div>
        </div>

        <div class="section2">
            <a href="index.jsp"><button>Trang chủ</button></a>
            <button>Giới thiệu</button>
            <button>Dịch vụ</button>
            <button>Chuyên gia</button>
            <button>Hướng dẫn</button>
            <button>Đặt lịch</button>
        </div>

        <div class="body-content">
            <h1>Đặt lịch khám</h1>
            <form action="#" method="post" class="booking-form">
                <div class="form-group inline-radio">
                    <label>Bạn muốn đặt cho:</label>
                    <input type="radio" id="self" name="for" value="self" required onclick="toggleRelative(false)">
                    <label for="self">Bản thân</label>
                    <input type="radio" id="relative" name="for" value="relative" onclick="toggleRelative(true)">
                    <label for="relative">Người thân</label>
                </div>

                <div class="form-group" id="relativeNameGroup" style="display:none;">
                    <label>Tên người thân:</label>
                    <input type="text" name="relativeName">
                </div>

                <div class="form-group">
                    <label>Họ và tên:</label>
                    <input type="text" name="fullname" required>
                </div>

                <div class="form-group">
                    <label>Số điện thoại:</label>
                    <input type="tel" name="phone" required>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label>Ngày khám:</label>
                        <input type="date" name="date" required>
                    </div>
                    <div class="form-group half-width">
                        <label>Giờ khám:</label>
                        <input type="time" name="time" required>
                    </div>
                </div>

                <div class="form-group">
                    <label>Dịch vụ:</label>
                    <select name="service" required>
                        <option value="">-- Chọn dịch vụ --</option>
                        <option value="chinh-nha">Chỉnh nha</option>
                        <option value="implant">Cấy ghép Implant</option>
                        <option value="rang-su">Làm răng sứ</option>
                    </select>
                </div>

                <div class="form-group" style="text-align:center;">
                    <button type="submit">Xác nhận đặt lịch</button>
                </div>
            </form>
        </div>

        <div class="footer">
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </div>
    </div>

    <script>
        function toggleRelative(show) {
            document.getElementById("relativeNameGroup").style.display = show ? "block" : "none";
        }
    </script>
</body>
</html>
