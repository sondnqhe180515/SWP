<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${specialization eq null ? 'Thêm chuyên khoa mới' : 'Sửa chuyên khoa'} - Admin Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #0d6efd;
            --secondary-color: #6c757d;
            --success-color: #198754;
            --info-color: #0dcaf0;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
            --light-color: #f8f9fa;
            --dark-color: #212529;
        }
        
        body {
            background-color: #f5f8fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .admin-sidebar {
            background-color: #343a40;
            color: white;
            min-height: 100vh;
            padding-top: 20px;
            position: sticky;
            top: 0;
        }
        
        .admin-sidebar .nav-link {
            color: rgba(255,255,255,.75);
            padding: 12px 20px;
            border-radius: 5px;
            margin: 4px 8px;
            transition: all 0.3s ease;
        }
        
        .admin-sidebar .nav-link:hover {
            color: white;
            background-color: rgba(255,255,255,.1);
        }
        
        .admin-sidebar .nav-link.active {
            color: white;
            background-color: var(--primary-color);
            box-shadow: 0 2px 5px rgba(0,0,0,.2);
        }
        
        .admin-sidebar .nav-link i {
            margin-right: 10px;
            font-size: 1.1rem;
        }
        
        .admin-content {
            padding: 20px;
        }
        
        .admin-header {
            background-color: white;
            box-shadow: 0 2px 10px rgba(0,0,0,.1);
            padding: 15px 25px;
            margin-bottom: 25px;
            border-radius: 10px;
        }
        
        .admin-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,.05);
            padding: 25px;
            margin-bottom: 25px;
        }
        
        .form-preview-image {
            max-width: 200px;
            max-height: 200px;
            border-radius: 8px;
            margin-top: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,.1);
            object-fit: cover;
        }
        
        .form-label {
            font-weight: 500;
            margin-bottom: 8px;
            color: #495057;
        }
        
        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }
        
        .form-text {
            font-size: 0.85rem;
        }
        
        .required-field::after {
            content: "*";
            color: var(--danger-color);
            margin-left: 4px;
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }
        
        .btn-primary:hover {
            background-color: #0b5ed7;
            border-color: #0a58ca;
        }
        
        .btn-secondary {
            background-color: var(--secondary-color);
            border-color: var(--secondary-color);
        }
        
        .btn-secondary:hover {
            background-color: #5c636a;
            border-color: #565e64;
        }
        
        .image-preview-container {
            position: relative;
            display: inline-block;
            margin-top: 15px;
        }
        
        .image-preview-actions {
            position: absolute;
            top: 5px;
            right: 5px;
            display: flex;
            gap: 5px;
        }
        
        .image-preview-actions .btn {
            width: 30px;
            height: 30px;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.8);
            border: none;
            box-shadow: 0 2px 5px rgba(0,0,0,.2);
        }
        
        .image-preview-actions .btn:hover {
            background-color: white;
        }
        
        .form-switch .form-check-input {
            width: 3em;
            height: 1.5em;
            margin-top: 0.25em;
        }
        
        .form-switch .form-check-input:checked {
            background-color: var(--success-color);
            border-color: var(--success-color);
        }
        
        .status-label {
            font-size: 0.9rem;
            font-weight: 500;
            margin-left: 10px;
        }
        
        .active-status {
            color: var(--success-color);
        }
        
        .inactive-status {
            color: var(--danger-color);
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
                    <a href="${pageContext.request.contextPath}/admin/blogs" class="nav-link">
                        <i class="bi bi-file-earmark-text"></i> Quản lý Blog
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="nav-link">
                        <i class="bi bi-folder"></i> Danh mục Blog
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/specializations" class="nav-link active">
                        <i class="bi bi-heart-pulse"></i> Quản lý Chuyên khoa
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/doctors" class="nav-link">
                        <i class="bi bi-person-badge"></i> Quản lý Bác sĩ
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
                    <div>
                        <h2 class="m-0">${specialization eq null ? 'Thêm chuyên khoa mới' : 'Sửa chuyên khoa'}</h2>
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb mb-0 mt-1">
                                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/specializations">Chuyên khoa</a></li>
                                <li class="breadcrumb-item active" aria-current="page">${specialization eq null ? 'Thêm mới' : 'Chỉnh sửa'}</li>
                            </ol>
                        </nav>
                    </div>
                    <div>
                        <span>Xin chào, <strong>${sessionScope.account.fullname}</strong></span>
                    </div>
                </div>
                
                <div class="admin-content">
                    <!-- Error Messages -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i> ${errorMessage}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    
                    <!-- Specialization Form -->
                    <div class="admin-card">
                        <form action="${pageContext.request.contextPath}${specialization eq null ? '/admin/specializations/add' : '/admin/specializations/edit'}" 
                              method="post" class="needs-validation" novalidate>
                            
                            <c:if test="${specialization ne null}">
                                <input type="hidden" name="id" value="${specialization.id}">
                            </c:if>
                            
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="mb-4">
                                        <label for="name" class="form-label required-field">Tên chuyên khoa</label>
                                        <input type="text" class="form-control form-control-lg" id="name" name="name" 
                                               value="${specialization.name}" required placeholder="Nhập tên chuyên khoa">
                                        <div class="invalid-feedback">
                                            Vui lòng nhập tên chuyên khoa.
                                        </div>
                                    </div>
                                    
                                    <div class="mb-4">
                                        <label class="form-label d-block">Trạng thái</label>
                                        <div class="form-check form-switch">
                                            <input class="form-check-input" type="checkbox" id="statusSwitch" 
                                                   ${specialization.status eq 'Deactive' ? '' : 'checked'}>
                                            <label class="form-check-label" for="statusSwitch">
                                                <span id="statusText" class="${specialization.status eq 'Deactive' ? 'inactive-status' : 'active-status'}">
                                                    ${specialization.status eq 'Deactive' ? 'Không hoạt động' : 'Hoạt động'}
                                                </span>
                                            </label>
                                            <input type="hidden" name="status" id="statusInput" 
                                                   value="${specialization.status eq 'Deactive' ? 'Deactive' : 'Active'}">
                                        </div>
                                        <div class="form-text">
                                            Chỉ những chuyên khoa có trạng thái "Hoạt động" mới hiển thị trên trang chủ.
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-md-4">
                                    <div class="mb-4">
                                        <label for="image" class="form-label">Hình ảnh đại diện</label>
                                        <input type="text" class="form-control" id="image" name="image" 
                                               value="${specialization.image}" placeholder="Nhập URL hình ảnh" onchange="previewImage()">
                                        <div class="form-text mb-3">
                                            Nhập đường dẫn URL đến hình ảnh đại diện cho chuyên khoa.
                                        </div>
                                        
                                        <div id="imagePreview" class="text-center mt-3">
                                            <c:if test="${not empty specialization.image}">
                                                <div class="image-preview-container">
                                                    <img src="${specialization.image}" class="form-preview-image" alt="Preview">
                                                    <div class="image-preview-actions">
                                                        <button type="button" class="btn btn-light" onclick="removeImage()">
                                                            <i class="bi bi-x"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <hr class="my-4">
                            
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/admin/specializations" class="btn btn-secondary">
                                    <i class="bi bi-arrow-left"></i> Quay lại
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> ${specialization eq null ? 'Thêm mới' : 'Cập nhật'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script>
        // Form validation
        (function() {
            'use strict';
            var forms = document.querySelectorAll('.needs-validation');
            Array.prototype.slice.call(forms).forEach(function(form) {
                form.addEventListener('submit', function(event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        })();
        
        // Status switch
        const statusSwitch = document.getElementById('statusSwitch');
        const statusText = document.getElementById('statusText');
        const statusInput = document.getElementById('statusInput');
        
        statusSwitch.addEventListener('change', function() {
            if (this.checked) {
                statusText.textContent = 'Hoạt động';
                statusText.className = 'status-label active-status';
                statusInput.value = 'Active';
            } else {
                statusText.textContent = 'Không hoạt động';
                statusText.className = 'status-label inactive-status';
                statusInput.value = 'Deactive';
            }
        });
        
        // Image preview for URL input
        const imageInput = document.getElementById('image');
        const imagePreview = document.getElementById('imagePreview');
        
        function previewImage() {
            const imageUrl = imageInput.value;
            
            if (imageUrl) {
                imagePreview.innerHTML = `
                    <div class="image-preview-container">
                        <img src="${imageUrl}" class="form-preview-image" alt="Preview">
                        <div class="image-preview-actions">
                            <button type="button" class="btn btn-light" onclick="removeImage()">
                                <i class="bi bi-x"></i>
                            </button>
                        </div>
                    </div>
                `;
            } else {
                imagePreview.innerHTML = '';
            }
        }
        
        function removeImage() {
            imageInput.value = '';
            imagePreview.innerHTML = '';
        }
        
        // Auto-close alerts after 5 seconds
        setTimeout(function() {
            $('.alert').alert('close');
        }, 5000);
    </script>
</body>
</html> 