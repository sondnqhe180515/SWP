<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thông tin khách hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="./css_k/style.css" rel="stylesheet"/>
        <style>
            .banner {
                justify-content: space-between;
                align-items: center;
                padding: 15px 30px;
                flex-wrap: wrap;
                gap: 10px;
            }
            

            .banner .search-sort-container {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-left: auto; /* Đẩy về bên phải */
            }

            .banner .search-form, .banner .sort-form {
                display: flex;
                align-items: center;
            }

            .banner .search-form {
                order: 2;
            }

            .banner .sort-form {
                order: 3;
            }

            .banner a {
                order: 4;
                margin-left: 10px;
            }

            .banner .search-form input {
                flex: 1;
                min-width: 200px;
                border-radius: 6px;
                border: 1px solid #ccc;
                padding: 8px;
            }

            .banner .search-form button {
                padding: 8px 16px;
                border-radius: 6px;
                font-weight: 600;
            }

            .banner .sort-form select {
                padding: 8px 16px;
                border-radius: 6px;
                border: 1px solid #ccc;
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <% 
            // Kiểm tra nếu không có dữ liệu, chuyển hướng đến Servlet
            if (request.getAttribute("customerList") == null) {
                response.sendRedirect(request.getContextPath() + "/ViewCustomerAccountServlet?page=1");
                return; // Dừng xử lý JSP
            }
        %>

        <div class="banner">
            <img src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474089BGn/mau-logo-rang-vang-tach-nen_045001529.png" alt="Logo" class="logo-img"/>

                <div class="search-form">
                    <form action="ViewCustomerAccountServlet" method="post">
                        <input type="hidden" name="page" value="1"/>
                        <input type="text" name="search" placeholder="Tìm kiếm theo tên hoặc email" value="<%= request.getAttribute("search") != null ? request.getAttribute("search") : "" %>"/>
                        <button type="submit">Tìm kiếm</button>
                    </form>
                </div>

                <div class="sort-form">
                    <form action="ViewCustomerAccountServlet" method="post">
                        <input type="hidden" name="page" value="1"/>
                        <input type="hidden" name="search" value="<%= request.getAttribute("search") != null ? request.getAttribute("search") : "" %>"/>
                        <select name="sortBy" onchange="this.form.submit()">
                            <option value="default" <%= "default".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Xếp tự động</option>
                            <option value="name" <%= "name".equals(request.getAttribute("sortBy")) ? "selected" : "" %>>Xếp từ A-Z</option>
                        </select>
                    </form>
                </div>

                <a href="#">Trang chủ</a>
            </div>


        <div class="content">
            <div class="table-container">
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên Khách hàng</th>
                            <th>Giới tính</th>
                            <th>Ngày sinh</th>
                            <th>Email</th>
                            <th>SĐT</th>
                            <th>Địa chỉ</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<User> customerList = (List<User>) request.getAttribute("customerList");
                            Integer currentPage = (Integer) request.getAttribute("currentPage");
                            Integer totalPages = (Integer) request.getAttribute("totalPages");
                            Integer pageSize = (Integer) request.getAttribute("pageSize");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            if (customerList == null || customerList.isEmpty()) { 
                        %>
                        <tr>
                            <td colspan="7" style="text-align: center;">Không có dữ liệu khách hàng.</td>
                        </tr>
                        <% 
                            } else {
                                int stt = (currentPage != null && pageSize != null) ? (currentPage - 1) * pageSize + 1 : 1;
                                for (User u : customerList) { 
                        %>
                        <tr>
                            <td><%= stt++ %></td>
                            <td><%= u.getFullName() != null ? u.getFullName() : "" %></td>
                            <td><%= u.getGender() != null ? u.getGender() : "" %></td>
                            <td><%= u.getDateOfBirth() != null ? dateFormat.format(u.getDateOfBirth()) : "" %></td>
                            <td><%= u.getEmail() != null ? u.getEmail() : "" %></td>
                            <td><%= u.getPhoneNumber() != null ? u.getPhoneNumber() : "" %></td>
                            <td><%= u.getAddress() != null ? u.getAddress() : "" %></td>
                        </tr>
                        <% 
                                }
                            } 
                        %>
                    </tbody>
                </table>
            </div>

            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <% 
                        Integer currentPageVal = (Integer) request.getAttribute("currentPage");
                        Integer totalPagesVal = (Integer) request.getAttribute("totalPages");
                        String searchVal = (String) request.getAttribute("search");
                        if (totalPagesVal != null && totalPagesVal > 0) {
                            for (int i = 1; i <= totalPagesVal; i++) { 
                    %>
                    <li class="page-item <%= (i == currentPageVal) ? "active" : "" %>">
                        <a class="page-link" href="ViewCustomerAccountServlet?page=<%= i %>&search=<%= searchVal != null ? searchVal : "" %>"><%= i %></a>
                    </li>
                    <% 
                            }
                        } else { 
                    %>
                    <li class="page-item disabled"><a class="page-link" href="#">Không có trang</a></li>
                        <% 
                            } 
                        %>
                </ul>
            </nav>
        </div>

        <footer>
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </footer>

    </body>
</html>