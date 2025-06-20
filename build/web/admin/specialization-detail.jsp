<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết chuyên khoa: ${specialization.name} - Admin Panel</title>
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
        
        .specialization-image {
            max-width: 100%;
            height: auto;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,.1);
        }
        
        .detail-card {
            border-left: 5px solid var(--primary-color);
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,.05);
            padding: 20px;
            margin-bottom: 20px;
            background-color: white;
        }
        
        .detail-label {
            font-weight: 600;
            color: #6c757d;
            margin-bottom: 5px;
        }
        
        .detail-value {
            font-size: 1.1rem;
        }
        
        .doctor-card {
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0,0,0,.05);
            transition: all 0.3s ease;
            height: 100%;
        }
        
        .doctor-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 30px rgba(0,0,0,.1);
        }
        
        .doctor-image {
            position: relative;
            overflow: hidden;
            height: 200px;
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
                        <h2 class="m-0">Chi tiết chuyên khoa</h2>
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb mb-0 mt-1">
                                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/specializations">Chuyên khoa</a></li>
                                <li class="breadcrumb-item active" aria-current="page">${specialization.name}</li>
                            </ol>
                        </nav>
                    </div>
                    <div>
                        <span>Xin chào, <strong>${sessionScope.account.fullname}</strong></span>
                    </div>
                </div>
                
                <div class="admin-content">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="admin-card text-center">
                                <c:choose>
                                    <c:when test="${not empty specialization.image}">
                                        <img src="<c:url value='${specialization.image}'/>" alt="${specialization.name}" class="specialization-image mb-3">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center text-muted p-5 mb-3">
                                            <i class="bi bi-image" style="font-size: 100px;"></i>
                                            <p>Không có hình ảnh</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                
                                <h3 class="mt-3">${specialization.name}</h3>
                                <span class="status-badge ${specialization.status eq 'Active' ? 'status-active' : 'status-inactive'}">
                                    ${specialization.status eq 'Active' ? 'Hoạt động' : 'Không hoạt động'}
                                </span>
                                
                                <div class="mt-4">
                                    <a href="${pageContext.request.contextPath}/admin/specializations/edit?id=${specialization.id}" class="btn btn-primary">
                                        <i class="bi bi-pencil"></i> Sửa thông tin
                                    </a>
                                </div>
                            </div>
                            
                            <div class="detail-card">
                                <div class="detail-label">ID</div>
                                <div class="detail-value">${specialization.id}</div>
                            </div>
                            
                            <div class="detail-card">
                                <div class="detail-label">Ngày tạo</div>
                                <div class="detail-value">${specialization.createdDate}</div>
                            </div>
                            
                            <div class="detail-card">
                                <div class="detail-label">Cập nhật lần cuối</div>
                                <div class="detail-value">${specialization.modifiedDate}</div>
                            </div>
                        </div>
                        
                        <div class="col-md-8">
                            <div class="admin-card">
                                <div class="d-flex justify-content-between align-items-center mb-4">
                                    <h4 class="m-0">Bác sĩ thuộc chuyên khoa</h4>
                                    <a href="${pageContext.request.contextPath}/admin/doctors/add?specializationId=${specialization.id}" class="btn btn-primary">
                                        <i class="bi bi-plus-lg"></i> Thêm bác sĩ
                                    </a>
                                </div>
                                
                                <c:choose>
                                    <c:when test="${not empty doctors && doctors.size() > 0}">
                                        <div class="row g-4">
                                            <c:forEach var="doctor" items="${doctors}">
                                                <div class="col-md-6 col-lg-4">
                                                    <div class="doctor-card">
                                                        <div class="doctor-image">
                                                            <c:choose>
                                                                <c:when test="${not empty doctor.avatar}">
                                                                    <img src="<c:url value='${doctor.avatar}'/>" alt="${doctor.fullName}">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="<c:url value='/assets/images/doctors/default-doctor.jpg'/>" alt="${doctor.fullName}">
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="doctor-info">
                                                            <h3 class="doctor-name">${doctor.fullName}</h3>
                                                            <p class="doctor-specialty">${specialization.name}</p>
                                                            <a href="${pageContext.request.contextPath}/admin/doctors/edit?id=${doctor.id}" class="btn btn-outline-primary">
                                                                <i class="bi bi-pencil"></i> Sửa
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-5">
                                            <i class="bi bi-person-x" style="font-size: 48px; color: #ccc;"></i>
                                            <p class="mt-3">Chưa có bác sĩ nào thuộc chuyên khoa này</p>
                                            <a href="${pageContext.request.contextPath}/admin/doctors/add?specializationId=${specialization.id}" class="btn btn-primary mt-2">
                                                <i class="bi bi-plus-lg"></i> Thêm bác sĩ
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
</html> 