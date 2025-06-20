<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<footer>
    <div class="footer-content">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-6 mb-4 mb-md-0">
                    <div class="footer-logo">
                        <img src="<c:url value='/image/Logo.png'/>" alt="logo"/>
                    </div>
                    <p class="mt-3">Nha Khoa Smile - Nơi mang đến nụ cười tự tin cho bạn với đội ngũ bác sĩ giàu kinh nghiệm và trang thiết bị hiện đại.</p>
                    <div class="footer-social mt-3">
                        <a href="#"><i class="fab fa-facebook-f"></i></a>
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="#"><i class="fab fa-instagram"></i></a>
                        <a href="#"><i class="fab fa-youtube"></i></a>
                    </div>
                </div>
                <div class="col-lg-2 col-md-6 mb-4 mb-md-0">
                    <h5 class="footer-heading">Liên kết</h5>
                    <ul class="footer-links">
                        <li><a href="<c:url value='/home'/>">Trang chủ</a></li>
                        <li><a href="#">Giới thiệu</a></li>
                        <li><a href="#">Dịch vụ</a></li>
                        <li><a href="<c:url value='/blogs'/>">Tin tức</a></li>
                        <li><a href="<c:url value='/booking.jsp'/>">Đặt lịch</a></li>
                    </ul>
                </div>
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="footer-heading">Dịch vụ</h5>
                    <ul class="footer-links">
                        <li><a href="#">Niềng răng</a></li>
                        <li><a href="#">Implant</a></li>
                        <li><a href="#">Tẩy trắng răng</a></li>
                        <li><a href="#">Điều trị nha chu</a></li>
                        <li><a href="#">Phục hình răng</a></li>
                    </ul>
                </div>
                <div class="col-lg-3 col-md-6">
                    <h5 class="footer-heading">Liên hệ</h5>
                    <ul class="footer-contact">
                        <li><i class="fas fa-map-marker-alt"></i> 123 Đường ABC, Quận XYZ, TP. Hồ Chí Minh</li>
                        <li><i class="fas fa-phone-alt"></i> 0987 654 321</li>
                        <li><i class="fas fa-envelope"></i> info@nhakhoasmile.com</li>
                        <li><i class="fas fa-clock"></i> T2-T7: 8:00 - 19:00</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="container">
            <div class="copyright text-center">
                Nụ cười của bạn – Sứ mệnh của chúng tôi! © 2023 Nha Khoa Smile
            </div>
        </div>
    </div>
</footer> 