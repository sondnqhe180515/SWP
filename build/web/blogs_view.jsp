<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Blog</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/responsive.css"/>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Swiper CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
    
    <style>
        .blog-list-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .blog-list-header {
            text-align: center;
            margin-bottom: 40px;
        }
        
        .blog-list-header h1 {
            font-size: 32px;
            color: #007acc;
            margin-bottom: 15px;
        }
        
        .blog-list-header p {
            color: #666;
            max-width: 700px;
            margin: 0 auto;
        }
        
        .blog-card {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.07);
            margin-bottom: 30px;
            overflow: hidden;
            height: 100%;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        
        .blog-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        
        .blog-image {
            width: 100%;
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
        
        .blog-content {
            padding: 20px;
            display: flex;
            flex-direction: column;
            flex: 1;
        }
        
        .blog-meta {
            font-size: 13px;
            color: #888;
            margin-bottom: 10px;
        }
        
        .blog-title {
            font-size: 18px;
            color: #007acc;
            font-weight: bold;
            text-decoration: none;
            margin-bottom: 10px;
            display: block;
        }
        
        .blog-description {
            color: #444;
            flex: 1;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 4;
            -webkit-box-orient: vertical;
            margin-bottom: 15px;
        }
        
        .read-more {
            color: #007acc;
            text-decoration: none;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
        }
        
        .read-more i {
            margin-left: 5px;
            transition: transform 0.2s ease;
        }
        
        .read-more:hover i {
            transform: translateX(3px);
        }
        
        /* Swiper styles */
        .swiper-blog {
            padding-bottom: 40px;
        }
        
        .swiper-pagination-bullet-active {
            background-color: #007acc;
        }
        
        @media (max-width: 767px) {
            .blog-list-header h1 {
                font-size: 24px;
            }
            
            .blog-image {
                height: 180px;
            }
            
            .blog-content {
                padding: 15px;
            }
            
            .blog-title {
                font-size: 16px;
            }
        }
    </style>
</head>
<body>
    <%@ include file="include/header.jsp" %>
    
    <div class="blog-list-container">
        <div class="blog-list-header">
            <h1>Tin tức & Blog</h1>
            <p>Cập nhật những tin tức, kiến thức mới nhất về nha khoa</p>
        </div>
        
        <!-- Desktop view -->
        <div class="row g-4 d-none d-md-flex">
            <c:forEach var="blog" items="${blogs}">
                <div class="col-lg-4 col-md-6">
                    <div class="blog-card">
                        <div class="blog-image">
                            <a href="blog-detail?id=${blog.blogId}">
                                <img src="${blog.blogImage}" alt="${blog.blogTitle}">
                            </a>
                        </div>
                        <div class="blog-content">
                            <div class="blog-meta">
                                <span><i class="fas fa-calendar"></i> ${blog.blogCreatedDate}</span> |
                                <span><i class="fas fa-folder"></i> ${blog.categoryBlogName}</span>
                            </div>
                            <a href="blog-detail?id=${blog.blogId}" class="blog-title">${blog.blogTitle}</a>
                            <div class="blog-description">
                                ${blog.blogDescription}
                            </div>
                            <a href="blog-detail?id=${blog.blogId}" class="read-more">Đọc tiếp <i class="fas fa-chevron-right"></i></a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <!-- Mobile view with Swiper -->
        <div class="d-md-none">
            <div class="swiper swiper-blog">
                <div class="swiper-wrapper">
                    <c:forEach var="blog" items="${blogs}">
                        <div class="swiper-slide">
                            <div class="blog-card">
                                <div class="blog-image">
                                    <a href="blog-detail?id=${blog.blogId}">
                                        <img src="${blog.blogImage}" alt="${blog.blogTitle}">
                                    </a>
                                </div>
                                <div class="blog-content">
                                    <div class="blog-meta">
                                        <span><i class="fas fa-calendar"></i> ${blog.blogCreatedDate}</span> |
                                        <span><i class="fas fa-folder"></i> ${blog.categoryBlogName}</span>
                                    </div>
                                    <a href="blog-detail?id=${blog.blogId}" class="blog-title">${blog.blogTitle}</a>
                                    <div class="blog-description">
                                        ${blog.blogDescription}
                                    </div>
                                    <a href="blog-detail?id=${blog.blogId}" class="read-more">Đọc tiếp <i class="fas fa-chevron-right"></i></a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="swiper-pagination"></div>
            </div>
        </div>
    </div>
    
    <%@ include file="include/footer.jsp" %>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Swiper JS -->
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    
    <!-- Initialize Swiper -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Blog list swiper for mobile
            const blogListSwiper = new Swiper('.swiper-blog', {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                }
            });
        });
    </script>
</body>
</html> 