<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .admin-sidebar {
            background-color: #343a40;
            color: white;
            min-height: 100vh;
            padding-top: 20px;
        }
        .admin-sidebar .nav-link {
            color: rgba(255,255,255,.75);
            padding: 10px 20px;
        }
        .admin-sidebar .nav-link:hover {
            color: white;
            background-color: rgba(255,255,255,.1);
        }
        .admin-sidebar .nav-link.active {
            color: white;
            background-color: #007acc;
        }
        .admin-sidebar .nav-link i {
            margin-right: 10px;
        }
        .admin-content {
            padding: 20px;
        }
        .admin-header {
            background-color: white;
            box-shadow: 0 0 10px rgba(0,0,0,.1);
            padding: 15px 20px;
            margin-bottom: 20px;
        }
        .admin-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,.05);
            padding: 20px;
            margin-bottom: 20px;
            transition: transform 0.3s;
        }
        .admin-card:hover {
            transform: translateY(-5px);
        }
        .stat-card {
            text-align: center;
            padding: 30px 20px;
        }
        .stat-card i {
            font-size: 40px;
            margin-bottom: 15px;
            color: #007acc;
        }
        .stat-card .stat-number {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 5px;
        }
        .stat-card .stat-title {
            color: #6c757d;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .blog-item {
            display: flex;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
        .blog-item:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }
        .blog-item-image {
            width: 80px;
            height: 80px;
            border-radius: 5px;
            overflow: hidden;
            margin-right: 15px;
        }
        .blog-item-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .blog-item-content {
            flex: 1;
        }
        .blog-item-title {
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 5px;
        }
        .blog-item-title a {
            color: #333;
            text-decoration: none;
        }
        .blog-item-title a:hover {
            color: #007acc;
        }
        .blog-item-meta {
            font-size: 12px;
            color: #6c757d;
            margin-bottom: 5px;
        }
        .blog-item-actions a {
            font-size: 13px;
            margin-right: 10px;
            color: #007acc;
            text-decoration: none;
        }
        .blog-item-actions a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 admin-sidebar">
                <h3 class="text-center mb-4">Admin Panel</h3>
                <div class="d-flex flex-column">
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link active">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/blogs" class="nav-link">
                        <i class="bi bi-file-earmark-text"></i> Quản lý Blog
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="nav-link">
                        <i class="bi bi-folder"></i> Danh mục Blog
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/specializations" class="nav-link">
                        <i class="bi bi-heart-pulse"></i> Quản lý Chuyên khoa
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/comments" class="nav-link">
                        <i class="bi bi-chat-dots"></i> Quản lý Bình luận
                    </a>
                    <a href="${pageContext.request.contextPath}/home" class="nav-link mt-5">
                        <i class="bi bi-house"></i> Về trang chủ
                    </a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link text-danger">
                        <i class="bi bi-box-arrow-right"></i> Đăng xuất
                    </a>
                </div>
            </div>
            
            <!-- Main Content -->
            <div class="col-md-9 col-lg-10 ms-sm-auto">
                <div class="admin-header d-flex justify-content-between align-items-center">
                    <h2 class="m-0">Dashboard</h2>
                    <div>
                        <span>Xin chào, <strong>${sessionScope.account.fullname}</strong></span>
                    </div>
                </div>
                
                <div class="admin-content">
                    <!-- Statistics Cards -->
                    <div class="row mb-4">
                        <div class="col-md-3">
                            <div class="admin-card stat-card">
                                <i class="bi bi-file-earmark-text"></i>
                                <div class="stat-number">${totalBlogs}</div>
                                <div class="stat-title">Tổng số bài viết</div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="admin-card stat-card">
                                <i class="bi bi-folder"></i>
                                <div class="stat-number">${categories.size()}</div>
                                <div class="stat-title">Danh mục</div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="admin-card stat-card">
                                <i class="bi bi-heart-pulse"></i>
                                <div class="stat-number">${totalSpecializations}</div>
                                <div class="stat-title">Chuyên khoa</div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="admin-card stat-card">
                                <i class="bi bi-chat-dots"></i>
                                <div class="stat-number">${totalComments}</div>
                                <div class="stat-title">Bình luận</div>
                                <c:if test="${pendingComments > 0}">
                                    <span class="badge bg-warning">${pendingComments} chờ duyệt</span>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Recent Blogs -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="admin-card">
                                <div class="d-flex justify-content-between align-items-center mb-4">
                                    <h4 class="m-0">Bài viết gần đây</h4>
                                    <a href="${pageContext.request.contextPath}/admin/blogs" class="btn btn-sm btn-primary">
                                        <i class="bi bi-plus"></i> Thêm bài viết mới
                                    </a>
                                </div>
                                
                                <c:forEach var="blog" items="${recentBlogs}">
                                    <div class="blog-item">
                                        <div class="blog-item-image">
                                            <img src="${blog.blogImage}" alt="${blog.blogTitle}">
                                        </div>
                                        <div class="blog-item-content">
                                            <div class="blog-item-title">
                                                <a href="${pageContext.request.contextPath}/blog-detail?id=${blog.blogId}">${blog.blogTitle}</a>
                                            </div>
                                            <div class="blog-item-meta">
                                                <span><i class="bi bi-calendar"></i> ${blog.blogCreatedDate}</span>
                                                <span class="ms-2"><i class="bi bi-folder"></i> ${blog.categoryBlogName}</span>
                                            </div>
                                            <div class="blog-item-actions">
                                                <a href="${pageContext.request.contextPath}/admin/blogs/edit?id=${blog.blogId}">
                                                    <i class="bi bi-pencil"></i> Sửa
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/blogs/delete?id=${blog.blogId}" 
                                                   onclick="return confirm('Bạn có chắc chắn muốn xóa bài viết này?');" 
                                                   class="text-danger">
                                                    <i class="bi bi-trash"></i> Xóa
                                                </a>
                                                <a href="${pageContext.request.contextPath}/blog-detail?id=${blog.blogId}" target="_blank">
                                                    <i class="bi bi-eye"></i> Xem
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                
                                <c:if test="${empty recentBlogs}">
                                    <div class="text-center py-4">
                                        <p>Chưa có bài viết nào.</p>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 