<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Service" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="dao.ServiceDAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đặt lịch khám</title>
        <link href="./css_k/style.css" rel="stylesheet"/>
        <style>
            .bn {
                flex-wrap: wrap;
            }
            .error-message {
                color: red;
                text-align: center;
                margin-top: 10px;
                font-weight: bold;
                white-space: pre-line;
            }
        </style>
    </head>
    <body>
        <%
            UserDAO userDao = new UserDAO();
            ServiceDAO serviceDao = new ServiceDAO();
            List<User> doctors = userDao.getUsersByDefaultOrder("Bác sĩ");
            List<User> customers = userDao.getUsersByDefaultOrder("Khách hàng");
            List<Service> services = serviceDao.getAllServices();
            String error = (String) request.getAttribute("error");
        %>
        <div class="banner bn">
            <img src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474089BGn/mau-logo-rang-vang-tach-nen_045001529.png" alt="Logo"/>
            <a href="#">Trang chủ</a>
        </div>

        <div class="content">
            <div class="form-container">
                <div class="header">Đặt lịch khám</div>
                <div class="form-body">
                    <% if (error != null) { %>
                    <div class="error-message"><%= error %></div>
                    <% } %>
                    <form action="CreateBookingServlet" method="post">
                        <label for="customerId">Bệnh nhân:</label>
                        <select name="customerId" required>
                            <% for(User u : customers) { %>
                            <option value="<%= u.getUserId() %>"><%= u.getFullName() %></option>
                            <% } %>
                        </select>

                        <label for="doctorId">Bác sĩ:</label>
                        <select name="doctorId" required>
                            <% for(User u : doctors) { %>
                            <option value="<%= u.getUserId() %>"><%= u.getFullName() %></option>
                            <% } %>
                        </select>

                        <label for="serviceId">Dịch vụ:</label>
                        <select name="serviceId" required>
                            <% for(Service s : services) { %>
                            <option value="<%= s.getServiceId() %>"><%= s.getServiceName() %></option>
                            <% } %>
                        </select>

                        <label for="appointmentDate">Ngày khám:</label>
                        <input type="datetime-local" name="appointmentDate" required>

                        <label for="note">Ghi chú:</label>
                        <textarea name="note" rows="4"></textarea>

                        <div class="buttons">
                            <button type="submit" class="btn">Xác nhận</button>
                            <button type="reset" class="btn-r">Xoá tất cả</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>         
        <footer>
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </footer>
    </body>
</html>
