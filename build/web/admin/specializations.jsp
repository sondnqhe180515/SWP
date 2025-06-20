<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Chuyên khoa - Admin Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css">
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
        
        .table-responsive {
            overflow-x: auto;
        }
        
        .table {
            vertical-align: middle;
        }
        
        .table th {
            background-color: #f8f9fa;
            font-weight: 600;
            border-bottom-width: 1px;
        }
        
        .table-image {
            width: 60px;
            height: 60px;
            border-radius: 8px;
            object-fit: cover;
            box-shadow: 0 2px 5px rgba(0,0,0,.1);
        }
        
        .status-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            display: inline-block;
        }
        
        .status-active {
            background-color: #d1e7dd;
            color: #0f5132;
        }
        
        .status-inactive {
            background-color: #f8d7da;
            color: #842029;
        }
        
        .alert {
            border-radius: 8px;
            margin-bottom: 25px;
        }
        
        .btn-group .btn {
            border-radius: 5px;
            margin: 0 2px;
        }
        
        .search-box {
            position: relative;
            margin-bottom: 20px;
        }
        
        .search-box input {
            padding-left: 40px;
            border-radius: 8px;
        }
        
        .search-box i {
            position: absolute;
            left: 15px;
            top: 12px;
            color: #6c757d;
        }
        
        .dataTables_wrapper .dataTables_length, 
        .dataTables_wrapper .dataTables_filter {
            margin-bottom: 15px;
        }
        
        .dataTables_wrapper .dataTables_info, 
        .dataTables_wrapper .dataTables_paginate {
            margin-top: 15px;
        }
        
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            border-radius: 5px;
            margin: 0 2px;
        }
        
        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
            background: var(--primary-color);
            border-color: var(--primary-color);
            color: white !important;
        }
        
        .action-btn {
            width: 32px;
            height: 32px;
            padding: 0;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 6px;
            margin: 0 2px;
        }
        
        .stats-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,.05);
            padding: 20px;
            margin-bottom: 25px;
            transition: all 0.3s ease;
            border-left: 5px solid var(--primary-color);
        }
        
        .stats-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,.1);
        }
        
        .stats-card .stats-icon {
            width: 50px;
            height: 50px;
            border-radius: 10px;
            background-color: rgba(13, 110, 253, 0.1);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            color: var(--primary-color);
            margin-bottom: 15px;
        }
        
        .stats-card .stats-number {
            font-size: 1.8rem;
            font-weight: 600;
            color: #212529;
            margin-bottom: 5px;
        }
        
        .stats-card .stats-text {
            font-size: 0.9rem;
            color: #6c757d;
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
                        <h2 class="m-0">Quản lý Chuyên khoa</h2>
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb mb-0 mt-1">
                                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Chuyên khoa</li>
                            </ol>
                        </nav>
                    </div>
                    <div>
                        <span>Xin chào, <strong>${sessionScope.account.fullname}</strong></span>
                    </div>
                </div>
                
                <div class="admin-content">
                    <!-- Success/Error Messages -->
                    <c:if test="${param.success != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <c:choose>
                                <c:when test="${param.success eq 'added'}">
                                    <strong>Thành công!</strong> Đã thêm chuyên khoa mới.
                                </c:when>
                                <c:when test="${param.success eq 'updated'}">
                                    <strong>Thành công!</strong> Đã cập nhật chuyên khoa.
                                </c:when>
                                <c:when test="${param.success eq 'deleted'}">
                                    <strong>Thành công!</strong> Đã xóa chuyên khoa.
                                </c:when>
                                <c:when test="${param.success eq 'status_changed'}">
                                    <strong>Thành công!</strong> Đã thay đổi trạng thái chuyên khoa.
                                </c:when>
                            </c:choose>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <c:choose>
                                <c:when test="${param.error eq 'not_found'}">
                                    <strong>Lỗi!</strong> Không tìm thấy chuyên khoa.
                                </c:when>
                                <c:when test="${param.error eq 'update_failed'}">
                                    <strong>Lỗi!</strong> Không thể cập nhật chuyên khoa.
                                </c:when>
                                <c:when test="${param.error eq 'delete_failed'}">
                                    <strong>Lỗi!</strong> Không thể xóa chuyên khoa. ${param.message}
                                </c:when>
                                <c:when test="${param.error eq 'status_change_failed'}">
                                    <strong>Lỗi!</strong> Không thể thay đổi trạng thái chuyên khoa.
                                </c:when>
                            </c:choose>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    
                    <!-- Stats Cards -->
                    <div class="row mb-4">
                        <div class="col-md-4">
                            <div class="stats-card">
                                <div class="stats-icon">
                                    <i class="bi bi-heart-pulse"></i>
                                </div>
                                <div class="stats-number">${totalSpecializations}</div>
                                <div class="stats-text">Tổng số chuyên khoa</div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="stats-card" style="border-left-color: var(--success-color);">
                                <div class="stats-icon" style="background-color: rgba(25, 135, 84, 0.1); color: var(--success-color);">
                                    <i class="bi bi-check-circle"></i>
                                </div>
                                <div class="stats-number">${activeSpecializations}</div>
                                <div class="stats-text">Chuyên khoa đang hoạt động</div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="stats-card" style="border-left-color: var(--danger-color);">
                                <div class="stats-icon" style="background-color: rgba(220, 53, 69, 0.1); color: var(--danger-color);">
                                    <i class="bi bi-x-circle"></i>
                                </div>
                                <div class="stats-number">${inactiveSpecializations}</div>
                                <div class="stats-text">Chuyên khoa không hoạt động</div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Specialization List -->
                    <div class="admin-card">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h4 class="m-0">Danh sách Chuyên khoa</h4>
                            <a href="${pageContext.request.contextPath}/admin/specializations/add" class="btn btn-primary">
                                <i class="bi bi-plus-lg"></i> Thêm Chuyên khoa mới
                            </a>
                        </div>
                        
                        <div class="table-responsive">
                            <table id="specializationsTable" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Hình ảnh</th>
                                        <th>Tên chuyên khoa</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="specialization" items="${specializations}">
                                        <tr>
                                            <td>${specialization.id}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty specialization.image}">
                                                        <img src="<c:url value='${specialization.image}'/>" alt="${specialization.name}" class="table-image">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="text-center text-muted">
                                                            <i class="bi bi-image" style="font-size: 24px;"></i>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${specialization.name}</td>
                                            <td>
                                                <span class="status-badge ${specialization.status eq 'Active' ? 'status-active' : 'status-inactive'}">
                                                    ${specialization.status eq 'Active' ? 'Hoạt động' : 'Không hoạt động'}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/admin/specializations/edit?id=${specialization.id}" 
                                                       class="btn btn-sm btn-outline-primary action-btn" title="Sửa">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    
                                                    <c:if test="${specialization.status eq 'Active'}">
                                                        <form action="${pageContext.request.contextPath}/admin/specializations/status" method="post" style="display:inline;">
                                                            <input type="hidden" name="id" value="${specialization.id}">
                                                            <input type="hidden" name="status" value="Deactive">
                                                            <button type="submit" class="btn btn-sm btn-outline-warning action-btn" 
                                                                    onclick="return confirm('Bạn có chắc chắn muốn vô hiệu hóa chuyên khoa này?');"
                                                                    title="Vô hiệu hóa">
                                                                <i class="bi bi-x-circle"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                    
                                                    <c:if test="${specialization.status eq 'Deactive'}">
                                                        <form action="${pageContext.request.contextPath}/admin/specializations/status" method="post" style="display:inline;">
                                                            <input type="hidden" name="id" value="${specialization.id}">
                                                            <input type="hidden" name="status" value="Active">
                                                            <button type="submit" class="btn btn-sm btn-outline-success action-btn" 
                                                                    onclick="return confirm('Bạn có chắc chắn muốn kích hoạt chuyên khoa này?');"
                                                                    title="Kích hoạt">
                                                                <i class="bi bi-check-circle"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                    
                                                    <form action="${pageContext.request.contextPath}/admin/specializations/delete" method="post" style="display:inline;">
                                                        <input type="hidden" name="id" value="${specialization.id}">
                                                        <button type="submit" class="btn btn-sm btn-outline-danger action-btn" 
                                                                onclick="return confirm('Bạn có chắc chắn muốn xóa chuyên khoa này? Hành động này không thể hoàn tác.');"
                                                                title="Xóa">
                                                            <i class="bi bi-trash"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
    
    <script>
        $(document).ready(function() {
            $('#specializationsTable').DataTable({
                language: {
                    search: "Tìm kiếm:",
                    lengthMenu: "Hiển thị _MENU_ mục",
                    info: "Hiển thị _START_ đến _END_ của _TOTAL_ mục",
                    infoEmpty: "Hiển thị 0 đến 0 của 0 mục",
                    infoFiltered: "(lọc từ _MAX_ mục)",
                    paginate: {
                        first: "Đầu",
                        last: "Cuối",
                        next: "Sau",
                        previous: "Trước"
                    }
                },
                columnDefs: [
                    { orderable: false, targets: [1, 4] },
                    { width: "80px", targets: 0 },
                    { width: "100px", targets: 1 },
                    { width: "150px", targets: 3 },
                    { width: "150px", targets: 4 }
                ],
                orderj
                : [[0, 'desc']]
            });
            
            // Auto-close alerts after 5 seconds
            setTimeout(function() {
                $('.alert').alert('close');
            }, 5000);
        });
    </script>
</body>
</html> 