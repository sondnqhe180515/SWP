<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    String roleFilter = (String) request.getAttribute("roleFilter");
    String sortBy = (String) request.getAttribute("sortBy");
    Integer totalDoctors = (Integer) request.getAttribute("totalDoctors");
    Integer totalStaff = (Integer) request.getAttribute("totalStaff");
    Integer totalCustomers = (Integer) request.getAttribute("totalCustomers");
    Integer totalAllUsers = (Integer) request.getAttribute("totalAllUsers");

    if (userList == null || currentPage == null || totalPages == null || 
        totalDoctors == null || totalStaff == null || totalCustomers == null || totalAllUsers == null) {
        response.sendRedirect("UserStatisticsServlet");
        return;
    }
    if (roleFilter == null) roleFilter = "";
    if (sortBy == null) sortBy = "id";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thống kê tài khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f4f9ff;
                margin: 0;
                padding: 0;
                min-height: 100vh;
                display: flex; /* Sử dụng flexbox theo chiều dọc */
                flex-direction: column; /* Căn chỉnh theo cột */
            }

            .banner {
                background-color: #d0f0fd;
                padding: 15px 30px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
                gap: 12px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                flex-shrink: 0; /* Ngăn banner co lại */
            }

            .banner img {
                width: 60px;
                height: 60px;
                object-fit: contain;
            }

            .banner button {
                padding: 8px 16px;
                border: 1px solid #64ccff;
                background-color: #ffffff;
                color: #0078B4;
                border-radius: 8px;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .banner button:hover {
                background-color: #64ccff;
                color: white;
            }

            .banner button.active {
                background-color: #ffeb3b;
                border: 2px solid #fbc02d;
                color: #333;
            }

            .banner select {
                padding: 6px 12px;
                border-radius: 6px;
                border: 1px solid #ccc;
            }

            .banner a {
                font-weight: bold;
                color: #0078B4;
                text-decoration: none;
                border: 1px solid #64ccff;
                padding: 8px 16px;
                border-radius: 8px;
                background-color: white;
                transition: 0.3s;
            }

            .banner a:hover {
                background-color: #64ccff;
                color: white;
            }

            .content {
                flex: 1 0 auto; /* Cho phép content mở rộng và chiếm phần còn lại */
                display: flex;
                flex-direction: column;
                justify-content: center; /* Căn giữa nội dung theo chiều dọc */
                align-items: center; /* Căn giữa theo chiều ngang */
                padding: 20px; /* Thêm padding để tránh sát mép */
            }

            .table-container {
                width: 100%; /* Đảm bảo bảng chiếm toàn bộ chiều rộng container */
                max-width: 1200px; /* Giới hạn chiều rộng tối đa để bảng không quá rộng */
                overflow-x: auto; /* Cho phép cuộn ngang nếu bảng quá rộng */
            }

            .table {
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 12px rgba(0,0,0,0.05);
                width: 100%; /* Đảm bảo bảng chiếm toàn bộ chiều rộng container */
            }

            .table thead th {
                background-color: #64ccff;
                color: white;
                text-align: center;
            }

            .table td, .table th {
                vertical-align: middle !important;
                text-align: center;
            }

            .table tbody tr:hover {
                background-color: #e0f7ff;
            }

            .pagination {
                justify-content: center;
                margin: 20px 0; /* Thêm margin trên và dưới */
                flex-shrink: 0; /* Ngăn pagination co lại */
            }

            .pagination .page-item.active .page-link {
                background-color: #64ccff;
                border-color: #64ccff;
                color: white;
            }

            .pagination .page-link {
                color: #0078B4;
            }

            footer {
                background-color: #d0f0fd;
                text-align: center;
                padding: 15px;
                font-weight: bold;
                color: #0078B4;
                border-top: 1px solid #ccc;
                flex-shrink: 0; /* Ngăn footer co lại */
            }
        </style>
    </head>
    <body>
        <div class="banner">
            <img src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474089BGn/mau-logo-rang-vang-tach-nen_045001529.png" alt="Logo"/>
            <button class="<%= roleFilter.equals("Bác sĩ") ? "active" : "" %>" onclick="window.location.href = 'UserStatisticsServlet?roleFilter=Bác sĩ&sortBy=<%= sortBy %>&page=1'">Thống kê số lượng bác sĩ (<%= totalDoctors %>)</button>
            <button class="<%= roleFilter.equals("Nhân viên") ? "active" : "" %>" onclick="window.location.href = 'UserStatisticsServlet?roleFilter=Nhân viên&sortBy=<%= sortBy %>&page=1'">Thống kê số lượng nhân viên (<%= totalStaff %>)</button>
            <button class="<%= roleFilter.equals("Khách hàng") ? "active" : "" %>" onclick="window.location.href = 'UserStatisticsServlet?roleFilter=Khách hàng&sortBy=<%= sortBy %>&page=1'">Thống kê số lượng khách hàng (<%= totalCustomers %>)</button>
            <button class="<%= roleFilter.equals("") ? "active" : "" %>" onclick="window.location.href = 'UserStatisticsServlet?sortBy=<%= sortBy %>&page=1'">Tất cả người dùng (<%= totalAllUsers %>)</button>
            <div>
                <select class="form-select form-select-sm" style="width: auto; display: inline-block; padding-right: 40px;" onchange="window.location.href = 'UserStatisticsServlet?roleFilter=<%= roleFilter %>&sortBy=' + this.value + '&page=1'">
                    <option value="id" <%= sortBy.equals("id") ? "selected" : "" %>>Xếp tự động</option>
                    <option value="name" <%= sortBy.equals("name") ? "selected" : "" %>>Xếp từ A - Z</option>
                </select>
            </div>
            <a href="#">Trang chủ</a>
        </div>

        <div class="content">
            <div class="table-container">
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Họ và tên</th>
                            <th>Email</th>
                            <th>SĐT</th>
                            <th>Địa chỉ</th>
                            <th>Vai trò</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (User u : userList) { %>
                        <tr>
                            <td><%= u.getUserId() %></td>
                            <td><%= u.getFullName() %></td>
                            <td><%= u.getEmail() %></td>
                            <td><%= u.getPhoneNumber() != null ? u.getPhoneNumber() : "" %></td>
                            <td><%= u.getAddress() != null ? u.getAddress() : "" %></td>
                            <td><%= u.getRoleName() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="UserStatisticsServlet?page=<%= i %>&roleFilter=<%= roleFilter %>&sortBy=<%= sortBy %>"><%= i %></a>
                    </li>
                    <% } %>
                </ul>
            </nav>
        </div>

        <footer>
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </footer>
    </body>
</html>