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

            String selectedCustomer = String.valueOf(request.getAttribute("customerId"));
            String selectedDoctor = String.valueOf(request.getAttribute("doctorId"));
            String selectedService = String.valueOf(request.getAttribute("serviceId"));
            String noteValue = request.getAttribute("note") != null ? request.getAttribute("note").toString() : "";
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
                    <form action="CreateBookingServlet" method="post" onsubmit="return validateNote();">
                        <label for="customerId">Bệnh nhân:</label>
                        <select name="customerId" required>
                            <% for(User u : customers) { %>
                            <option value="<%= u.getUserId() %>" <%= String.valueOf(u.getUserId()).equals(selectedCustomer) ? "selected" : "" %>>
                                <%= u.getFullName() %>
                            </option>
                            <% } %>
                        </select>

                        <label for="doctorId">Bác sĩ:</label>
                        <select name="doctorId" required>
                            <% for(User u : doctors) { %>
                            <option value="<%= u.getUserId() %>" <%= String.valueOf(u.getUserId()).equals(selectedDoctor) ? "selected" : "" %>>
                                <%= u.getFullName() %>
                            </option>
                            <% } %>
                        </select>

                        <label for="serviceId">Dịch vụ:</label>
                        <select name="serviceId" required>
                            <% for(Service s : services) { %>
                            <option value="<%= s.getServiceId() %>" <%= String.valueOf(s.getServiceId()).equals(selectedService) ? "selected" : "" %>>
                                <%= s.getServiceName() %>
                            </option>
                            <% } %>
                        </select>

                        <label for="appointmentDate">Ngày khám:</label>
                        <input type="datetime-local" name="appointmentDate" required>

                        <label for="note">Ghi chú:</label>
                        <textarea name="note" rows="4" maxlength="255"><%= noteValue %></textarea>

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

        <script>
            function validateNote() {
                let note = document.querySelector('textarea[name="note"]').value.trim();
                if (note === '')
                    return true;

                if (note.length > 255) {
                    alert("Ghi chú không được vượt quá 255 ký tự.");
                    return false;
                }
                if (/^[^a-zA-Z0-9]/.test(note)) {
                    alert("Ghi chú không được bắt đầu bằng ký tự đặc biệt.");
                    return false;
                }
                if (/<[^>]*>/.test(note)) {
                    alert("Không được nhập thẻ HTML vào ghi chú.");
                    return false;
                }
                if (!/[a-zA-Z]/.test(note.charAt(0))) {
                    alert("Ghi chú phải bắt đầu bằng chữ cái.");
                    return false;
                }
                if (/(.)\\1\\1/.test(note)) {
                    alert("Không được nhập 3 ký tự giống nhau liên tiếp trong ghi chú.");
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>
