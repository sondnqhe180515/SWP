<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết Blog</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/responsive.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <!-- Add Swiper CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
    <style>
        .blog-detail-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .blog-header {
            background-color: #f5f5f5;
            padding: 40px 0;
            text-align: center;
            margin-bottom: 30px;
        }
        .blog-title {
            font-size: 28px;
            margin-bottom: 15px;
        }
        .blog-meta {
            color: #666;
            font-size: 14px;
        }
        .blog-content {
            display: flex;
            flex-wrap: wrap;
        }
        .main-content {
            flex: 2;
            padding-right: 30px;
        }
        .sidebar {
            flex: 1;
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
        }
        .blog-image {
            width: 100%;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .blog-text {
            line-height: 1.6;
            color: #333;
        }
        .comments-section {
            margin-top: 40px;
        }
        .comment {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 15px;
        }
        .comment-form {
            margin-top: 30px;
        }
        .comment-form textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 10px;
        }
        .comment-form input[type="text"],
        .comment-form input[type="email"] {
            width: 48%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 10px;
        }
        .comment-form button {
            background-color: #007acc;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }
        .related-posts {
            margin-top: 40px;
        }
        .related-post {
            display: flex;
            margin-bottom: 15px;
        }
        .related-post img {
            width: 80px;
            height: 60px;
            object-fit: cover;
            margin-right: 10px;
        }
        .related-post-content {
            font-size: 14px;
        }
        .related-post-content a {
            color: #007acc;
            text-decoration: none;
        }
        .categories-widget {
            margin-bottom: 30px;
        }
        .categories-widget ul {
            list-style: none;
            padding: 0;
        }
        .categories-widget li {
            margin-bottom: 8px;
        }
        .categories-widget a {
            color: #333;
            text-decoration: none;
            display: flex;
            align-items: center;
        }
        .categories-widget a:hover {
            color: #007acc;
        }
        .search-widget {
            margin-bottom: 30px;
        }
        .search-widget input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        /* Updated related blogs styles for Swiper */
        .related-blogs {
            margin-top: 40px;
            position: relative;
        }
        .related-blogs-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .related-blogs-header h3 {
            margin: 0;
        }
        .swiper-blog {
            width: 100%;
            padding-bottom: 40px;
        }
        .related-blog-card {
            height: 100%;
            border: 1px solid #eee;
            border-radius: 8px;
            overflow: hidden;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .related-blog-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .related-blog-card img {
            width: 100%;
            height: 180px;
            object-fit: cover;
        }
        .related-blog-card-content {
            padding: 15px;
        }
        .related-blog-card-content h4 {
            font-size: 16px;
            margin-bottom: 10px;
            line-height: 1.4;
        }
        .related-blog-card-content h4 a {
            color: #333;
            text-decoration: none;
        }
        .related-blog-card-content h4 a:hover {
            color: #007acc;
        }
        .related-blog-card .blog-meta {
            margin-bottom: 10px;
        }
        .read-more {
            color: #007acc;
            text-decoration: none;
            font-size: 14px;
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
        .swiper-pagination {
            bottom: 0;
        }
        .swiper-button-next, .swiper-button-prev {
            color: #007acc;
        }
        .widget {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
            padding: 20px;
            margin-bottom: 30px;
        }
        .widget-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .post-recent {
            display: flex;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border-bottom: 1px solid #f0f0f0;
        }
        .post-recent:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }
        .post-recent-thumb {
            width: 70px;
            height: 70px;
            margin-right: 15px;
            border-radius: 5px;
            overflow: hidden;
        }
        .post-recent-thumb img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .post-recent-content {
            flex: 1;
        }
        .post-recent-content a {
            display: block;
            color: #333;
            font-weight: 500;
            margin-bottom: 5px;
            text-decoration: none;
            font-size: 15px;
            line-height: 1.4;
        }
        .post-recent-content a:hover {
            color: #007acc;
        }
        .post-recent-content span {
            display: block;
            font-size: 12px;
            color: #888;
        }
        .nav-tabs .nav-link {
            color: #333;
            font-weight: 500;
            border: none;
            border-bottom: 2px solid transparent;
            padding: 10px 15px;
        }
        .nav-tabs .nav-link.active {
            color: #007acc;
            border-bottom: 2px solid #007acc;
            background: transparent;
        }
        .tab-content {
            padding-top: 20px;
        }
        
        /* Mobile responsive styles */
        @media (max-width: 767px) {
            .blog-header {
                padding: 30px 0;
            }
            
            .blog-title {
                font-size: 22px;
            }
            
            .blog-content {
                flex-direction: column;
            }
            
            .main-content {
                padding-right: 0;
                margin-bottom: 30px;
            }
            
            .comment-form input[type="text"],
            .comment-form input[type="email"] {
                width: 100%;
                margin-right: 0;
            }
            
            .mobile-order-1 {
                order: 1;
            }
            
            .mobile-order-2 {
                order: 2;
            }
            
            .mobile-order-3 {
                order: 3;
            }
            
            .mobile-order-4 {
                order: 4;
            }
            
            .sidebar {
                margin-top: 30px;
            }
            
            .related-blog-card img {
                height: 160px;
            }
        }
    </style>
</head>
<body>
    <%@ include file="include/header.jsp" %>
    
    <!-- Blog Header - Mobile Order 1 -->
    <div class="blog-header mobile-order-1">
        <div class="blog-detail-container">
            <h1 class="blog-title">${blog.blogTitle}</h1>
            <div class="blog-meta">
                <span><i class="bi bi-calendar"></i> ${blog.blogCreatedDate}</span> |
                <span><i class="bi bi-folder"></i> ${blog.categoryBlogName}</span>
            </div>
        </div>
    </div>
    
    <div class="blog-detail-container">
        <div class="row">
            <!-- Main Content - Mobile Order 2 -->
            <div class="col-lg-8 col-md-7 mobile-order-2">
                <img src="${blog.blogImage}" alt="${blog.blogTitle}" class="blog-image">
                
                <div class="blog-text">
                    ${blog.blogContent}
                </div>
                
                <!-- Comments Section - Mobile Order 3 -->
                <div class="comments-section mobile-order-3">
                    <h3>Bình luận (${comments.size()})</h3>
                    
                    <c:forEach var="comment" items="${comments}">
                        <div class="comment">
                            <div class="comment-meta">
                                <strong>Người dùng #${comment.commentCreatedBy}</strong> - 
                                <span>${comment.commentCreatedDate}</span>
                            </div>
                            <div class="comment-content">
                                ${comment.commentContent}
                            </div>
                        </div>
                    </c:forEach>
                    
                    <div class="comment-form">
                        <h3>Để lại bình luận</h3>
                        <form action="blog-detail" method="post">
                            <input type="hidden" name="blogId" value="${blog.blogId}">
                            <input type="hidden" name="userId" value="1"> <!-- Temporary user ID -->
                            
                            <textarea name="content" rows="5" placeholder="Nội dung bình luận" required></textarea>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <input type="text" name="name" placeholder="Tên của bạn" required class="form-control">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <input type="email" name="email" placeholder="Email của bạn" required class="form-control">
                                </div>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Gửi bình luận</button>
                        </form>
                    </div>
                </div>
            </div>
            
            <!-- Sidebar - Mobile Order 4 -->
            <div class="col-lg-4 col-md-5 mobile-order-4">
                <div class="sidebar">
                    <!-- SEARCH WIDGET -->
                    <div class="widget search-widget">
                        <h5 class="widget-title">Tìm kiếm</h5>
                        <div class="mt-4">
                            <form role="search" method="get" action="blogs" class="searchform">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="keyword" placeholder="Tìm kiếm...">
                                    <button class="btn btn-primary" type="submit"><i class="bi bi-search"></i></button>
                                </div>
                            </form>
                        </div>
                    </div>
                    
                    <!-- RECENT POST WIDGET -->
                    <div class="widget">
                        <h5 class="widget-title">Bài viết gần đây</h5>
                        
                        <ul class="nav nav-tabs" id="recentPostsTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="all-tab" data-bs-toggle="tab" data-bs-target="#all-posts" type="button" role="tab" aria-controls="all-posts" aria-selected="true">Tất cả</button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="category-tab" data-bs-toggle="tab" data-bs-target="#category-posts" type="button" role="tab" aria-controls="category-posts" aria-selected="false">Cùng danh mục</button>
                            </li>
                        </ul>
                        
                        <!-- Desktop view -->
                        <div class="tab-content d-none d-md-block" id="recentPostsTabContent">
                            <!-- All Recent Posts -->
                            <div class="tab-pane fade show active" id="all-posts" role="tabpanel" aria-labelledby="all-tab">
                                <c:forEach var="recentBlog" items="${recentBlogs}">
                                    <div class="clearfix post-recent">
                                        <div class="post-recent-thumb float-start">
                                            <a href="blog-detail?id=${recentBlog.blogId}">
                                                <img alt="${recentBlog.blogTitle}" src="${recentBlog.blogImage}" class="img-fluid rounded">
                                            </a>
                                        </div>
                                        <div class="post-recent-content float-start">
                                            <a href="blog-detail?id=${recentBlog.blogId}">${recentBlog.blogTitle}</a>
                                            <span class="text-muted mt-2">${recentBlog.blogCreatedDate}</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            
                            <!-- Category Recent Posts -->
                            <div class="tab-pane fade" id="category-posts" role="tabpanel" aria-labelledby="category-tab">
                                <c:forEach var="categoryBlog" items="${recentBlogsByCategory}">
                                    <div class="clearfix post-recent">
                                        <div class="post-recent-thumb float-start">
                                            <a href="blog-detail?id=${categoryBlog.blogId}">
                                                <img alt="${categoryBlog.blogTitle}" src="${categoryBlog.blogImage}" class="img-fluid rounded">
                                            </a>
                                        </div>
                                        <div class="post-recent-content float-start">
                                            <a href="blog-detail?id=${categoryBlog.blogId}">${categoryBlog.blogTitle}</a>
                                            <span class="text-muted mt-2">${categoryBlog.blogCreatedDate}</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        
                        <!-- Mobile view with Swiper -->
                        <div class="d-md-none mt-3">
                            <!-- All Recent Posts Swiper -->
                            <div class="tab-pane fade show active" id="all-posts-mobile" role="tabpanel">
                                <div class="swiper recentBlogsSwiper">
                                    <div class="swiper-wrapper">
                                        <c:forEach var="recentBlog" items="${recentBlogs}">
                                            <div class="swiper-slide">
                                                <div class="clearfix post-recent">
                                                    <div class="post-recent-thumb float-start">
                                                        <a href="blog-detail?id=${recentBlog.blogId}">
                                                            <img alt="${recentBlog.blogTitle}" src="${recentBlog.blogImage}" class="img-fluid rounded">
                                                        </a>
                                                    </div>
                                                    <div class="post-recent-content float-start">
                                                        <a href="blog-detail?id=${recentBlog.blogId}">${recentBlog.blogTitle}</a>
                                                        <span class="text-muted mt-2">${recentBlog.blogCreatedDate}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <div class="swiper-pagination"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- CATEGORIES WIDGET -->
                    <div class="widget categories-widget">
                        <h5 class="widget-title">Danh mục</h5>
                        <ul class="list-unstyled mt-4">
                            <c:forEach var="category" items="${categories}">
                                <li class="mb-2">
                                    <a href="blogs?category=${category.categoryBlogId}" class="d-flex align-items-center">
                                        <i class="bi bi-chevron-right me-2"></i> ${category.categoryBlogName}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Updated Related Blogs with Swiper -->
        <div class="related-blogs mt-5">
            <div class="related-blogs-header">
                <h3>Bài viết liên quan</h3>
            </div>
            
            <!-- Desktop view -->
            <div class="d-none d-md-block">
                <div class="swiper swiper-blog">
                    <div class="swiper-wrapper">
                        <c:forEach var="relatedBlog" items="${relatedBlogs}">
                            <div class="swiper-slide">
                                <div class="related-blog-card">
                                    <img src="${relatedBlog.blogImage}" alt="${relatedBlog.blogTitle}">
                                    <div class="related-blog-card-content">
                                        <div class="blog-meta">
                                            <span><i class="bi bi-calendar"></i> ${relatedBlog.blogCreatedDate}</span>
                                        </div>
                                        <h4><a href="blog-detail?id=${relatedBlog.blogId}">${relatedBlog.blogTitle}</a></h4>
                                        <a href="blog-detail?id=${relatedBlog.blogId}" class="read-more">Đọc tiếp <i class="bi bi-chevron-right"></i></a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Add Pagination -->
                    <div class="swiper-pagination"></div>
                    <!-- Add Navigation -->
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                </div>
            </div>
            
            <!-- Mobile view -->
            <div class="d-md-none">
                <div class="swiper swiper-blog-mobile">
                    <div class="swiper-wrapper">
                        <c:forEach var="relatedBlog" items="${relatedBlogs}">
                            <div class="swiper-slide">
                                <div class="related-blog-card">
                                    <img src="${relatedBlog.blogImage}" alt="${relatedBlog.blogTitle}">
                                    <div class="related-blog-card-content">
                                        <div class="blog-meta">
                                            <span><i class="bi bi-calendar"></i> ${relatedBlog.blogCreatedDate}</span>
                                        </div>
                                        <h4><a href="blog-detail?id=${relatedBlog.blogId}">${relatedBlog.blogTitle}</a></h4>
                                        <a href="blog-detail?id=${relatedBlog.blogId}" class="read-more">Đọc tiếp <i class="bi bi-chevron-right"></i></a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Add Pagination -->
                    <div class="swiper-pagination"></div>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="include/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Add Swiper JS -->
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    
    <!-- Initialize Swiper -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Related blogs swiper (desktop)
            const relatedBlogsSwiper = new Swiper('.swiper-blog', {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: '.swiper-blog .swiper-pagination',
                    clickable: true,
                },
                navigation: {
                    nextEl: '.swiper-blog .swiper-button-next',
                    prevEl: '.swiper-blog .swiper-button-prev',
                },
                breakpoints: {
                    640: {
                        slidesPerView: 2,
                        spaceBetween: 20,
                    },
                    992: {
                        slidesPerView: 3,
                        spaceBetween: 30,
                    }
                }
            });
            
            // Related blogs swiper (mobile)
            const relatedBlogsMobileSwiper = new Swiper('.swiper-blog-mobile', {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: '.swiper-blog-mobile .swiper-pagination',
                    clickable: true,
                }
            });
            
            // Recent blogs swiper (mobile)
            const recentBlogsSwiper = new Swiper('.recentBlogsSwiper', {
                slidesPerView: 1,
                spaceBetween: 20,
                pagination: {
                    el: '.recentBlogsSwiper .swiper-pagination',
                    clickable: true,
                }
            });
            
            // Handle tab switching for mobile
            document.querySelectorAll('#recentPostsTabs .nav-link').forEach(function(tab) {
                tab.addEventListener('click', function() {
                    setTimeout(function() {
                        recentBlogsSwiper.update();
                    }, 100);
                });
            });
        });
    </script>
</body>
</html>
