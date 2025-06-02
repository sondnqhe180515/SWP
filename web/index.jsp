<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Phòng khám nha khoa SWP</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;700&family=Roboto:wght@400;500&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
    </head>
    <body>
        <div class="wrapper">

            <div class="section1">
                <div class="logo">
                    <img src="image/Logo.png" alt="logo"/>
                </div>
                <input type="text" placeholder="Tìm kiếm..." class="search-box">
                <button>Hotline: 0987654321</button>
                <button>Hỗ trợ khách hàng</button>
                <button>Đăng nhập / Đăng ký</button>
            </div>

            <div class="section2">
                <button>Trang chủ</button>
                <button>Giới thiệu</button>
                <button>Dịch vụ</button>
                <button>Chuyên gia</button>
                <button>Hướng dẫn</button>
                <form action="booking.jsp" method="get" style="display:inline;">
                    <button type="submit">Đặt lịch</button>
                </form>
            </div>

            <div class="body-content">
                <div class="body-flex">
                    <div class="image">
                        <img src="image/home_swp.jpg" alt="Hình ảnh nha khoa">
                    </div>
                    <div class="text">
                        <h1>Nha khoa SWP</h1>
                        <p>Chúng tôi mang đến giải pháp chăm sóc răng miệng toàn diện:</p>
                        <ul>
                            <li>Bác sĩ chuyên môn cao</li>
                            <li>Công nghệ hiện đại</li>
                            <li>Dịch vụ đa dạng: chỉnh nha, Implant, răng sứ</li>
                            <li>Không gian thân thiện, vô trùng</li>
                            <li>Chăm sóc tận tâm</li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="footer">
                Nụ cười của bạn – Sứ mệnh của chúng tôi!
            </div>
        </div>
    </body>
</html>
