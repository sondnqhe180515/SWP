<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Nha Khoa Smile - Chăm sóc nụ cười của bạn</title>
    
    <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
        <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css"/>
        <!-- Swiper CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
        <!-- Responsive CSS -->
        <link href="<c:url value='/css/responsive.css'/>" rel="stylesheet" type="text/css"/>
        
        <style>
            :root {
                --primary-color: #007acc;
                --secondary-color: #0056b3;
                --accent-color: #4CAF50;
                --light-color: #e6f3ff;
                --dark-color: #333;
                --text-color: #444;
                --white-color: #fff;
            }
            
            body {
                font-family: 'Roboto', sans-serif;
                color: var(--text-color);
                background-color: #f9fcff;
            }
            
            h1, h2, h3, h4, h5, h6 {
                font-family: 'Poppins', sans-serif;
            }
            
            .hero-section {
                position: relative;
                background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)), url('<c:url value="/assets/images/bg/01.jpg"/>') no-repeat center center;
                background-size: cover;
                padding: 150px 0;
                color: var(--white-color);
            }
            
            .hero-content {
                max-width: 800px;
                margin: 0 auto;
                text-align: center;
            }
            
            .hero-title {
                font-size: 3.5rem;
                font-weight: 700;
                margin-bottom: 20px;
                text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            }
            
            .hero-subtitle {
                font-size: 1.5rem;
                margin-bottom: 30px;
                text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
            }
            
            .btn-primary {
                background-color: var(--primary-color);
                border-color: var(--primary-color);
                padding: 10px 25px;
                font-weight: 500;
                transition: all 0.3s ease;
            }
            
            .btn-primary:hover {
                background-color: var(--secondary-color);
                border-color: var(--secondary-color);
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            
            .section-title {
                position: relative;
                margin-bottom: 60px;
                text-align: center;
            }
            
            .section-title h2 {
                font-size: 2.5rem;
                font-weight: 700;
                color: var(--primary-color);
                margin-bottom: 20px;
            }
            
            .section-title p {
                max-width: 700px;
                margin: 0 auto;
                color: var(--text-color);
            }
            
            .section-title::after {
                content: '';
                position: absolute;
                bottom: -15px;
                left: 50%;
                transform: translateX(-50%);
                width: 80px;
                height: 3px;
                background-color: var(--accent-color);
            }
            
            .specialization-card {
                background-color: var(--white-color);
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                height: 100%;
            }
            
            .specialization-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
            }
            
            .specialization-icon {
                width: 80px;
                height: 80px;
                margin: 0 auto 20px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: var(--light-color);
                border-radius: 50%;
                color: var(--primary-color);
                font-size: 2rem;
            }
            
            .specialization-icon img {
                max-width: 50px;
                max-height: 50px;
            }
            
            .specialization-card h3 {
                font-size: 1.5rem;
                margin-bottom: 15px;
                color: var(--primary-color);
            }
            
            .doctor-card {
                background-color: var(--white-color);
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                height: 100%;
            }
            
            .doctor-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
            }
            
            .doctor-image {
                position: relative;
                overflow: hidden;
                height: 250px;
            }
            
            .doctor-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform 0.5s ease;
            }
            
            .doctor-card:hover .doctor-image img {
                transform: scale(1.05);
            }
            
            .doctor-info {
                padding: 20px;
                text-align: center;
            }
            
            .doctor-name {
                font-size: 1.3rem;
                font-weight: 600;
                margin-bottom: 5px;
                color: var(--dark-color);
            }
            
            .doctor-specialty {
                color: var(--primary-color);
                font-weight: 500;
                margin-bottom: 15px;
            }
            
            .doctor-social {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-top: 15px;
            }
            
            .doctor-social a {
                width: 35px;
                height: 35px;
                border-radius: 50%;
                background-color: var(--light-color);
                color: var(--primary-color);
                display: flex;
                align-items: center;
                justify-content: center;
                transition: all 0.3s ease;
            }
            
            .doctor-social a:hover {
                background-color: var(--primary-color);
                color: var(--white-color);
            }
            
            .blog-card {
                background-color: var(--white-color);
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                height: 100%;
            }
            
            .blog-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
            }
            
            .blog-image {
                position: relative;
                height: 200px;
                overflow: hidden;
            }
            
            .blog-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform 0.5s ease;
            }
            
            .blog-card:hover .blog-image img {
                transform: scale(1.05);
            }
            
            .blog-date {
                position: absolute;
                top: 15px;
                left: 15px;
                background-color: var(--primary-color);
                color: var(--white-color);
                padding: 5px 15px;
                border-radius: 5px;
                font-size: 0.8rem;
            }
            
            .blog-content {
                padding: 20px;
            }
            
            .blog-title {
                font-size: 1.3rem;
                font-weight: 600;
                margin-bottom: 10px;
                color: var(--dark-color);
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            
            .blog-description {
                color: var(--text-color);
                margin-bottom: 15px;
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            
            .swiper-pagination-bullet-active {
                background-color: var(--primary-color);
            }
            
            .swiper-button-next, .swiper-button-prev {
                color: var(--primary-color);
                background-color: rgba(255, 255, 255, 0.8);
                width: 40px;
                height: 40px;
                border-radius: 50%;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
            
            .swiper-button-next:after, .swiper-button-prev:after {
                font-size: 1.2rem;
            }
            
            .section-bg-light {
                background-color: var(--light-color);
            }
            
            .section-padding {
                padding: 80px 0;
            }
            
            .counter-box {
                background-color: var(--white-color);
                padding: 30px;
                border-radius: 10px;
                text-align: center;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
            }
            
            .counter-box:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
            }
            
            .counter-icon {
                font-size: 2.5rem;
                color: var(--primary-color);
                margin-bottom: 15px;
            }
            
            .counter-number {
                font-size: 2.5rem;
                font-weight: 700;
                color: var(--dark-color);
                margin-bottom: 5px;
            }
            
            .counter-text {
                color: var(--text-color);
                font-weight: 500;
            }
        </style>
</head>
<body>
        <!-- Include Header -->
        <jsp:include page="include/header.jsp"/>
        
        <!-- Hero Section -->
        <section class="hero-section">
            <div class="container">
                <div class="hero-content">
                    <h1 class="hero-title">Chăm sóc nha khoa chuyên nghiệp</h1>
                    <p class="hero-subtitle">Đội ngũ bác sĩ giàu kinh nghiệm, trang thiết bị hiện đại cùng dịch vụ tận tâm</p>
                    <a href="<c:url value='/booking.jsp'/>" class="btn btn-primary btn-lg">Đặt lịch ngay <i class="fas fa-arrow-right ms-2"></i></a>
                </div>
            </div>
        </section>
        
        <!-- Stats Section -->
        <section class="py-5">
            <div class="container">
                <!-- Desktop view -->
                <div class="row g-4 d-none d-md-flex">
                    <div class="col-md-3 col-6">
                        <div class="counter-box">
                            <div class="counter-icon">
                                <i class="fas fa-user-md"></i>
                            </div>
                            <div class="counter-number">20+</div>
                            <div class="counter-text">Bác sĩ chuyên môn</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="counter-box">
                            <div class="counter-icon">
                                <i class="fas fa-smile"></i>
                            </div>
                            <div class="counter-number">5000+</div>
                            <div class="counter-text">Khách hàng hài lòng</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="counter-box">
                            <div class="counter-icon">
                                <i class="fas fa-certificate"></i>
                            </div>
                            <div class="counter-number">15+</div>
                            <div class="counter-text">Năm kinh nghiệm</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="counter-box">
                            <div class="counter-icon">
                                <i class="fas fa-clinic-medical"></i>
                            </div>
                            <div class="counter-number">8+</div>
                            <div class="counter-text">Chuyên khoa</div>
                        </div>
                    </div>
                </div>
                
                <!-- Mobile view with Swiper -->
                <div class="d-md-none">
                    <div class="swiper statsSwiper">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide">
                                <div class="counter-box">
                                    <div class="counter-icon">
                                        <i class="fas fa-user-md"></i>
                                    </div>
                                    <div class="counter-number">20+</div>
                                    <div class="counter-text">Bác sĩ chuyên môn</div>
                                </div>
                            </div>
                            <div class="swiper-slide">
                                <div class="counter-box">
                                    <div class="counter-icon">
                                        <i class="fas fa-smile"></i>
                                    </div>
                                    <div class="counter-number">5000+</div>
                                    <div class="counter-text">Khách hàng hài lòng</div>
                                </div>
                            </div>
                            <div class="swiper-slide">
                                <div class="counter-box">
                                    <div class="counter-icon">
                                        <i class="fas fa-certificate"></i>
                                    </div>
                                    <div class="counter-number">15+</div>
                                    <div class="counter-text">Năm kinh nghiệm</div>
                                </div>
                            </div>
                            <div class="swiper-slide">
                                <div class="counter-box">
                                    <div class="counter-icon">
                                        <i class="fas fa-clinic-medical"></i>
                                    </div>
                                    <div class="counter-number">8+</div>
                                    <div class="counter-text">Chuyên khoa</div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-pagination"></div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Specialization Section -->
        <section class="section-padding">
            <div class="container">
                <div class="section-title">
                    <h2>Chuyên khoa</h2>
                    <p>Các chuyên khoa nha khoa với đội ngũ bác sĩ chuyên môn cao và trang thiết bị hiện đại</p>
                </div>

                <!-- Desktop view -->
                <div class="row g-4 d-none d-md-flex">
                    <c:forEach items="${specializations}" var="spec">
                        <div class="col-lg-3 col-md-6">
                            <div class="specialization-card text-center p-4">
                                <div class="specialization-icon">
                                    <c:choose>
                                        <c:when test="${not empty spec.image}">
                                            <img src="<c:url value='${spec.image}'/>" alt="${spec.name}" />
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-tooth"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <h3>${spec.name}</h3>
                                <p>Đội ngũ bác sĩ chuyên môn cao, trang thiết bị hiện đại.</p>
                                <a href="#" class="btn btn-outline-primary mt-3">Xem thêm</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <!-- Mobile view with Swiper -->
                <div class="d-md-none">
                    <div class="swiper specializationSwiper">
                        <div class="swiper-wrapper">
                            <c:forEach items="${specializations}" var="spec">
                                <div class="swiper-slide">
                                    <div class="specialization-card text-center p-4">
                                        <div class="specialization-icon">
                                            <c:choose>
                                                <c:when test="${not empty spec.image}">
                                                    <img src="<c:url value='${spec.image}'/>" alt="${spec.name}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-tooth"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <h3>${spec.name}</h3>
                                        <p>Đội ngũ bác sĩ chuyên môn cao, trang thiết bị hiện đại.</p>
                                        <a href="#" class="btn btn-outline-primary mt-3">Xem thêm</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-pagination"></div>
                    </div>
                </div>
            </div>
        </section>
        
        <!-- Doctors Section -->
        <section class="section-padding section-bg-light">
            <div class="container">
                <div class="section-title">
                    <h2>Đội ngũ bác sĩ</h2>
                    <p>Đội ngũ bác sĩ giàu kinh nghiệm, tận tâm với nghề</p>
                </div>

                <!-- Desktop view -->
                <div class="d-none d-md-block">
                    <div class="swiper doctorsSwiper">
                        <div class="swiper-wrapper pb-5">
                            <c:forEach items="${doctors}" var="doc">
                                <div class="swiper-slide">
                                    <div class="doctor-card">
                                        <div class="doctor-image">
                                            <c:choose>
                                                <c:when test="${not empty doc.avatar}">
                                                    <img src="<c:url value='${doc.avatar}'/>" alt="${doc.fullName}">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<c:url value='/assets/images/doctors/default-doctor.jpg'/>" alt="${doc.fullName}">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="doctor-info">
                                            <h3 class="doctor-name">${doc.fullName}</h3>
                                            <p class="doctor-specialty">${doc.specialization}</p>
                                            <div class="doctor-social">
                                                <a href="#"><i class="fab fa-facebook-f"></i></a>
                                                <a href="#"><i class="fab fa-twitter"></i></a>
                                                <a href="#"><i class="fab fa-linkedin-in"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-pagination"></div>
                        <div class="swiper-button-next"></div>
                        <div class="swiper-button-prev"></div>
                    </div>
                </div>
                
                <!-- Mobile view -->
                <div class="d-md-none">
                    <div class="swiper doctorsMobileSwiper">
                        <div class="swiper-wrapper">
                            <c:forEach items="${doctors}" var="doc">
                                <div class="swiper-slide">
                                    <div class="doctor-card">
                                        <div class="doctor-image">
                                            <c:choose>
                                                <c:when test="${not empty doc.avatar}">
                                                    <img src="<c:url value='${doc.avatar}'/>" alt="${doc.fullName}">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<c:url value='/assets/images/doctors/default-doctor.jpg'/>" alt="${doc.fullName}">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="doctor-info">
                                            <h3 class="doctor-name">${doc.fullName}</h3>
                                            <p class="doctor-specialty">${doc.specialization}</p>
                                            <div class="doctor-social">
                                                <a href="#"><i class="fab fa-facebook-f"></i></a>
                                                <a href="#"><i class="fab fa-twitter"></i></a>
                                                <a href="#"><i class="fab fa-linkedin-in"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-pagination"></div>
                    </div>
                </div>
            </div>
        </section>
        
        <!-- Blog Section -->
        <section class="section-padding">
            <div class="container">
                <div class="section-title">
                    <h2>Bài viết mới nhất</h2>
                    <p>Cập nhật những tin tức, kiến thức mới nhất về nha khoa</p>
                </div>

                <!-- Desktop view -->
                <div class="row g-4 d-none d-md-flex">
                    <c:forEach items="${recentBlogs}" var="blog">
                        <div class="col-lg-4 col-md-6">
                            <div class="blog-card">
                                <div class="blog-image">
                                    <c:choose>
                                        <c:when test="${not empty blog.blogImage}">
                                            <img src="<c:url value='${blog.blogImage}'/>" alt="${blog.blogTitle}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value='/assets/images/blog/default-blog.jpg'/>" alt="${blog.blogTitle}">
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="blog-date">${blog.blogCreatedDate}</div>
                                </div>
                                <div class="blog-content">
                                    <h3 class="blog-title">
                                        <a href="<c:url value='/blog-detail?id=${blog.blogId}'/>" class="text-decoration-none text-dark">${blog.blogTitle}</a>
                                    </h3>
                                    <p class="blog-description">${blog.blogDescription}</p>
                                    <a href="<c:url value='/blog-detail?id=${blog.blogId}'/>" class="btn btn-outline-primary">Đọc thêm</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <!-- Mobile view with Swiper -->
                <div class="d-md-none">
                    <div class="swiper blogsSwiper">
                        <div class="swiper-wrapper">
                            <c:forEach items="${recentBlogs}" var="blog">
                                <div class="swiper-slide">
                                    <div class="blog-card">
                                        <div class="blog-image">
                                            <c:choose>
                                                <c:when test="${not empty blog.blogImage}">
                                                    <img src="<c:url value='${blog.blogImage}'/>" alt="${blog.blogTitle}">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="<c:url value='/assets/images/blog/default-blog.jpg'/>" alt="${blog.blogTitle}">
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="blog-date">${blog.blogCreatedDate}</div>
                                        </div>
                                        <div class="blog-content">
                                            <h3 class="blog-title">
                                                <a href="<c:url value='/blog-detail?id=${blog.blogId}'/>" class="text-decoration-none text-dark">${blog.blogTitle}</a>
                                            </h3>
                                            <p class="blog-description">${blog.blogDescription}</p>
                                            <a href="<c:url value='/blog-detail?id=${blog.blogId}'/>" class="btn btn-outline-primary">Đọc thêm</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-pagination"></div>
                    </div>
                </div>
                
                <div class="text-center mt-5">
                    <a href="<c:url value='/blogs'/>" class="btn btn-primary">Xem tất cả bài viết</a>
                </div>
            </div>
        </section>
        
        <!-- Call to Action Section -->
        <section class="py-5" style="background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('<c:url value="/assets/images/bg/cta-bg.jpg"/>') no-repeat center center; background-size: cover;">
            <div class="container py-5">
                <div class="row justify-content-center">
                    <div class="col-lg-8 text-center text-white">
                        <h2 class="mb-4">Đặt lịch khám ngay hôm nay</h2>
                        <p class="mb-5">Hãy để chúng tôi chăm sóc nụ cười của bạn. Đội ngũ bác sĩ chuyên nghiệp sẽ tư vấn và điều trị tốt nhất cho bạn.</p>
                        <a href="<c:url value='/booking.jsp'/>" class="btn btn-primary btn-lg">Đặt lịch ngay</a>
                    </div>
                </div>
            </div>
        </section>

    <!-- Include Footer -->
        <jsp:include page="include/footer.jsp"/>
        
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Swiper JS -->
        <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
        
        <!-- Initialize Swiper -->
        <script>
            // Doctors swiper (desktop)
            var doctorsSwiper = new Swiper(".doctorsSwiper", {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                },
                navigation: {
                    nextEl: ".swiper-button-next",
                    prevEl: ".swiper-button-prev",
                },
                breakpoints: {
                    640: {
                        slidesPerView: 2,
                        spaceBetween: 20,
                    },
                    768: {
                        slidesPerView: 3,
                        spaceBetween: 30,
                    },
                    1024: {
                        slidesPerView: 4,
                        spaceBetween: 30,
                    },
                },
            });
            
            // Doctors swiper (mobile)
            var doctorsMobileSwiper = new Swiper(".doctorsMobileSwiper", {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                }
            });
            
            // Stats swiper (mobile only)
            var statsSwiper = new Swiper(".statsSwiper", {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                }
            });
            
            // Specialization swiper (mobile only)
            var specializationSwiper = new Swiper(".specializationSwiper", {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                }
            });
            
            // Blogs swiper (mobile only)
            var blogsSwiper = new Swiper(".blogsSwiper", {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                }
            });
        </script>
</body>
</html>