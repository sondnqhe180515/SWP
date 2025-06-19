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
        <link href="./css_k/style.css" rel="stylesheet"/>

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
                            <th>STT</th>
                            <th>Họ và tên</th>
                            <th>Email</th>
                            <th>SĐT</th>
                            <th>Địa chỉ</th>
                            <th>Vai trò</th>
                            <th>Tùy chỉnh</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% int stt = (currentPage - 1) * 10 + 1;
                            for (User u : userList) { 
                        %>
                        <tr>
                            <td><%= stt++ %></td>
                            <td><%= u.getFullName() %></td>
                            <td><%= u.getEmail() %></td>
                            <td><%= u.getPhoneNumber() != null ? u.getPhoneNumber() : "" %></td>
                            <td><%= u.getAddress() != null ? u.getAddress() : "" %></td>
                            <td><%= u.getRoleName() %></td>
                            <td class="buttons">
                                <a href="#"><button class="btn">Xem chi tiết</button></a>
                                <a href="#"><button class="btn">Sửa</button></a>
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