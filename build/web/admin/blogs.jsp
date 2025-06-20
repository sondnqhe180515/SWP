<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Blog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
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
        }
        .blog-table img {
            width: 80px;
            height: 60px;
            object-fit: cover;
            border-radius: 4px;
        }
        .blog-table .title {
            font-weight: 500;
            color: #333;
        }
        .blog-table .category {
            font-size: 13px;
            color: #6c757d;
        }
        .blog-table .date {
            font-size: 13px;
            color: #6c757d;
        }
        .blog-table .status {
            font-size: 12px;
            padding: 3px 8px;
            border-radius: 20px;
            display: inline-block;
        }
        .blog-table .status.active {
            background-color: #d1e7dd;
            color: #0f5132;
        }
        .blog-table .status.inactive {
            background-color: #f8d7da;
            color: #842029;
        }
        .blog-table .status.draft {
            background-color: #fff3cd;
            color: #664d03;
        }
        .blog-form label {
            font-weight: 500;
            margin-bottom: 5px;
        }
        .blog-form .form-control {
            margin-bottom: 15px;
        }
        .note-editor {
            margin-bottom: 15px;
        }
        .action-buttons .btn {
            padding: 0.25rem 0.5rem;
            width: 32px;
            height: 32px;
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
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/blogs" class="nav-link active">
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
                    <h2 class="m-0">Quản lý Blog</h2>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addBlogModal">
                        <i class="bi bi-plus"></i> Thêm bài viết mới
                    </button>
                </div>
                
                <div class="admin-content">
                    <!-- Success/Error Messages -->
                    <c:if test="${param.success eq 'add'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>Thành công!</strong> Bài viết mới đã được thêm.
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${param.success eq 'edit'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>Thành công!</strong> Bài viết đã được cập nhật.
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${param.success eq 'delete'}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>Thành công!</strong> Bài viết đã được xóa.
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${param.error eq 'true'}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>Lỗi!</strong> Đã xảy ra lỗi trong quá trình xử lý.
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    
                    <!-- Blog List -->
                    <div class="admin-card">
                        <div class="table-responsive blog-table">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th width="80">Hình ảnh</th>
                                        <th>Tiêu đề</th>
                                        <th>Danh mục</th>
                                        <th>Ngày tạo</th>
                                        <th>Trạng thái</th>
                                        <th width="150">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="blog" items="${blogs}">
                                        <tr>
                                            <td>
                                                <img src="${blog.blogImage}" alt="${blog.blogTitle}">
                                            </td>
                                            <td>
                                                <div class="title">${blog.blogTitle}</div>
                                            </td>
                                            <td>
                                                <div class="category">${blog.categoryBlogName}</div>
                                            </td>
                                            <td>
                                                <div class="date">${blog.blogCreatedDate}</div>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${blog.blogStatus eq 'Active'}">
                                                        <span class="status active">Hoạt động</span>
                                                    </c:when>
                                                    <c:when test="${blog.blogStatus eq 'Inactive'}">
                                                        <span class="status inactive">Vô hiệu</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status draft">Bản nháp</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="action-buttons">
                                                <button class="btn btn-sm btn-primary edit-blog" 
                                                        data-bs-toggle="modal" 
                                                        data-bs-target="#editBlogModal"
                                                        data-id="${blog.blogId}">
                                                    <i class="bi bi-pencil"></i>
                                                </button>
                                                <button class="btn btn-sm btn-danger delete-blog"
                                                        data-bs-toggle="modal"
                                                        data-bs-target="#deleteBlogModal"
                                                        data-id="${blog.blogId}"
                                                        data-title="${blog.blogTitle}">
                                                    <i class="bi bi-trash"></i>
                                                </button>
                                                <a href="${pageContext.request.contextPath}/blog-detail?id=${blog.blogId}" 
                                                   class="btn btn-sm btn-info" 
                                                   target="_blank">
                                                    <i class="bi bi-eye"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            
                            <c:if test="${empty blogs}">
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
    
    <!-- Add Blog Modal -->
    <div class="modal fade" id="addBlogModal" tabindex="-1" aria-labelledby="addBlogModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addBlogModalLabel">Thêm bài viết mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/admin/blogs" method="post" class="blog-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="status" value="Active">
                        
                        <div class="mb-3">
                            <label for="title" class="form-label">Tiêu đề</label>
                            <input type="text" class="form-control" id="title" name="title" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="categoryId" class="form-label">Danh mục</label>
                            <select class="form-select" id="categoryId" name="categoryId" required>
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.categoryBlogId}">${category.categoryBlogName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label for="image" class="form-label">Đường dẫn hình ảnh</label>
                            <input type="text" class="form-control" id="image" name="image" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="description" class="form-label">Mô tả</label>
                            <textarea class="form-control summernote" id="description" name="description" rows="10" required></textarea>
                        </div>
                        
                        <div class="text-end">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-primary">Lưu bài viết</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Edit Blog Modal -->
    <div class="modal fade" id="editBlogModal" tabindex="-1" aria-labelledby="editBlogModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editBlogModalLabel">Chỉnh sửa bài viết</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/admin/blogs" method="post" class="blog-form">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="blogId" id="edit-blogId">
                        
                        <div class="mb-3">
                            <label for="edit-title" class="form-label">Tiêu đề</label>
                            <input type="text" class="form-control" id="edit-title" name="title" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="edit-categoryId" class="form-label">Danh mục</label>
                            <select class="form-select" id="edit-categoryId" name="categoryId" required>
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.categoryBlogId}">${category.categoryBlogName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label for="edit-image" class="form-label">Đường dẫn hình ảnh</label>
                            <input type="text" class="form-control" id="edit-image" name="image" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="edit-status" class="form-label">Trạng thái</label>
                            <select class="form-select" id="edit-status" name="status">
                                <option value="Active">Hoạt động</option>
                                <option value="Inactive">Vô hiệu</option>
                                <option value="Draft">Bản nháp</option>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label for="edit-description" class="form-label">Mô tả</label>
                            <textarea class="form-control summernote" id="edit-description" name="description" rows="10" required></textarea>
                        </div>
                        
                        <div class="text-end">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Delete Blog Modal -->
    <div class="modal fade" id="deleteBlogModal" tabindex="-1" aria-labelledby="deleteBlogModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteBlogModalLabel">Xác nhận xóa</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Bạn có chắc chắn muốn xóa bài viết "<span id="delete-blog-title"></span>"?</p>
                    <p class="text-danger">Lưu ý: Hành động này không thể hoàn tác và sẽ xóa tất cả bình luận liên quan.</p>
                </div>
                <div class="modal-footer">
                    <form action="${pageContext.request.contextPath}/admin/blogs" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="blogId" id="delete-blogId">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-danger">Xóa</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
    <script>
        $(document).ready(function() {
            // Initialize Summernote editor
            $('.summernote').summernote({
                height: 300,
                toolbar: [
                    ['style', ['style']],
                    ['font', ['bold', 'underline', 'clear']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['table', ['table']],
                    ['insert', ['link', 'picture']],
                    ['view', ['fullscreen', 'codeview', 'help']]
                ]
            });
            
            // Handle edit blog modal
            $('.edit-blog').click(function() {
                var blogId = $(this).data('id');
                
                // Clear form first
                $('#edit-blogId').val('');
                $('#edit-title').val('');
                $('#edit-image').val('');
                $('#edit-categoryId').val('');
                $('#edit-status').val('');
                $('#edit-description').summernote('code', '');
                
                // Get blog data via AJAX
                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/blogs',
                    type: 'GET',
                    data: {
                        action: 'getBlog',
                        blogId: blogId
                    },
                    dataType: 'json',
                    success: function(blog) {
                        $('#edit-blogId').val(blog.blogId);
                        $('#edit-title').val(blog.blogTitle);
                        $('#edit-image').val(blog.blogImage);
                        $('#edit-categoryId').val(blog.categoryBlogId);
                        $('#edit-status').val(blog.blogStatus);
                        
                        // Set description to Summernote
                        $('#edit-description').summernote('code', blog.blogDescription);
                    },
                    error: function(xhr, status, error) {
                        alert('Đã xảy ra lỗi khi lấy dữ liệu bài viết: ' + error);
                    }
                });
            });
            
            // Handle delete blog modal
            $('.delete-blog').click(function() {
                var id = $(this).data('id');
                var title = $(this).data('title');
                
                $('#delete-blogId').val(id);
                $('#delete-blog-title').text(title);
            });
        });
    </script>
</body>
</html> 