<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <div class="section1">
        <div class="logo">
            <a href="<c:url value='/home'/>">
                <img src="<c:url value='/image/Logo.png'/>" alt="logo"/>
            </a>
        </div>
        <input type="text" placeholder="Tìm kiếm..." class="search-box">
        <div class="actions">
            <a href="tel:0987654321" class="phone-link"><button><i class="fas fa-phone-alt me-2"></i> 0987654321</button></a>
            <button><i class="fas fa-headset me-2"></i> Hỗ trợ khách hàng</button>
            <form action="<c:url value='/login'/>" method="get" style="display:inline;">
                <button type="submit"><i class="fas fa-user me-2"></i> Đăng nhập / Đăng ký</button>
            </form>
        </div>
    </div>

    <div class="section2">
        <form action="<c:url value='/home'/>" method="get" style="display:inline;">
            <button type="submit">Trang chủ</button>
        </form>
        <button>Giới thiệu</button>
        <button>Dịch vụ</button>
        <button>Chuyên gia</button>
        <button>Hướng dẫn</button>
        <form action="<c:url value='/blogs'/>" method="get" style="display:inline;">
            <button type="submit">Tin tức</button>
        </form>
        <form action="<c:url value='/booking.jsp'/>" method="get" style="display:inline;">
            <button type="submit" class="booking-btn">Đặt lịch</button>
        </form>
    </div>
</header> 