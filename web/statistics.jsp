<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    // Hàm định dạng số lượng lớn
    public String formatNumber(int number) {
        if (number >= 1_000_000) {
            return String.format("%.1fM", number / 1_000_000.0);
        } else if (number >= 1_000) {
            return String.format("%.1fK", number / 1_000.0);
        } else {
            return String.valueOf(number);
        }
    }
%>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    String roleFilter = (String) request.getAttribute("roleFilter");
    String sortBy = (String) request.getAttribute("sortBy");
    String search = (String) request.getAttribute("search");
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
    if (sortBy == null) sortBy = "default";
    if (search == null) search = "";
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
                display: flex;
                flex-direction: column;
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
                flex-shrink: 0;
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

            .search-form {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .search-form input {
                padding: 6px 12px;
                border-radius: 6px;
                border: 1px solid #ccc;
                width: 200px;
            }

            .search-form button {
                padding: 6px 12px;
                border: 1px solid #64ccff;
                background-color: #ffffff;
                color: #0078B4;
                border-radius: 6px;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .search-form button:hover {
                background-color: #64ccff;
                color: white;
            }

            .content {
                flex: 1 0 auto;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                padding: 20px;
            }

            .table-container {
                width: 100%;
                max-width: 1200px;
                overflow-x: auto;
            }

            .table {
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 12px rgba(0,0,0,0.05);
                width: 100%;
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
                margin: 20px 0;
                flex-shrink: 0;
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
                flex-shrink: 0;
            }

            .buttons {
                display: flex;
                justify-content: center;
                padding-top: 25px;
                gap: 15px;
            }

            .btn {
                padding: 5px 10px;
                border: 1px solid #64ccff;
                background-color: #ffffff;
                color: #64ccff;
                border-radius: 10px;
                font-weight: 600;
                transition: all 0.3s ease;
                text-transform: uppercase;
                font-size: 1rem;
            }

            .btn:hover {
                background-color: #64ccff;
                color: #ffffff;
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(30, 144, 255, 0.3);
            }

            .btn-d {
                padding: 5px 10px;
                border: 1px solid #64ccff;
                background-color: #ffffff;
                color: #64ccff;
                border-radius: 10px;
                font-weight: 600;
                transition: all 0.3s ease;
                text-transform: uppercase;
                font-size: 1rem;
            }

            .btn-d:hover {
                background-color: #FF6B6B;
                color: #ffffff;
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(30, 144, 255, 0.3);
            }
        </style>
    </head>
    <body>
        <div class="banner">
            <img src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474089BGn/mau-logo-rang-vang-tach-nen_045001529.png" alt="Logo"/>
            <button class="<%= roleFilter.equals("Bác sĩ") ? "active" : "" %>" 
                    onclick="window.location.href = 'UserStatisticsServlet?roleFilter=Bác sĩ&sortBy=<%= sortBy %>&page=1&search=<%= search %>'">
                Thống Kê Số Lượng Bác Sĩ (<%= formatNumber(totalDoctors) %>)
            </button>
            <button class="<%= roleFilter.equals("Nhân viên") ? "active" : "" %>" 
                    onclick="window.location.href = 'UserStatisticsServlet?roleFilter=Nhân viên&sortBy=<%= sortBy %>&page=1&search=<%= search %>'">
                Thống Kê Số Lượng Nhân Viên (<%= formatNumber(totalStaff) %>)
            </button>
            <button class="<%= roleFilter.equals("Khách hàng") ? "active" : "" %>" 
                    onclick="window.location.href = 'UserStatisticsServlet?roleFilter=Khách hàng&sortBy=<%= sortBy %>&page=1&search=<%= search %>'">
                Thống Kê Số Lượng Khách Hàng (<%= formatNumber(totalCustomers) %>)
            </button>
            <button class="<%= roleFilter.equals("") ? "active" : "" %>" 
                    onclick="window.location.href = 'UserStatisticsServlet?sortBy=<%= sortBy %>&page=1&search=<%= search %>'">
                Tất cả (<%= formatNumber(totalAllUsers) %>)
            </button>
            <div class="search-form">
                <form action="UserStatisticsServlet" method="post">
                    <input type="hidden" name="roleFilter" value="<%= roleFilter %>"/>
                    <input type="hidden" name="sortBy" value="<%= sortBy %>"/>
                    <input type="hidden" name="page" value="1"/>
                    <input type="text" name="search" placeholder="Tìm theo tên hoặc email" value="<%= search %>"/>
                    <button type="submit">Tìm Kiếm</button>
                </form>
            </div>
            <div>
                <select class="form-select form-select-sm" style="width: auto; display: inline-block; padding-right: 40px;" 
                        onchange="window.location.href = 'UserStatisticsServlet?roleFilter=<%= roleFilter %>&sortBy=' + this.value + '&page=1&search=<%= search %>'">
                    <option value="default" <%= sortBy.equals("default") ? "selected" : "" %>>Xếp tự động</option>
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
                            <th>Tùy chỉnh</th>
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
                            <td class="buttons">
                                <a href="#"><button class="btn">Edit</button></a>
                                <a href="#"><button class="btn-d">Delete</button></a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="UserStatisticsServlet?page=<%= i %>&roleFilter=<%= roleFilter %>&sortBy=<%= sortBy %>&search=<%= search %>"><%= i %></a>
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