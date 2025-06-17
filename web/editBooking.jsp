<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Appointment" %>
<%@ page import="model.User" %>
<%@ page import="model.Service" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="dao.ServiceDAO" %>
<%
    Appointment appointment = (Appointment) request.getAttribute("appointment");
    UserDAO userDao = new UserDAO();
    ServiceDAO serviceDao = new ServiceDAO();
    List<User> doctors = userDao.getUsersByDefaultOrder("Bác sĩ");
    List<User> customers = userDao.getUsersByDefaultOrder("Khách hàng");
    List<Service> services = serviceDao.getAllServices();
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa lịch khám</title>
        <link href="./css_k/style.css" rel="stylesheet"/>
        <style>

            .error-message {
                color: #ff4d4f;
                font-weight: 600;
                font-size: 0.9rem;
                margin-top: 5px;
                text-align: center;
            }
            .is-invalid {
                border-color: #ff4d4f;
                box-shadow: 0 0 4px rgba(255, 77, 79, 0.5);
            }
        </style>
    </head>
    <body>
        <div class="banner">
            <img src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474089BGn/mau-logo-rang-vang-tach-nen_045001529.png" alt="Logo"/>
            <a href="#">Trang chủ</a>
        </div>

        <div class="content">
            <div class="form-container">
                <div class="header">Chỉnh sửa lịch khám</div>
                <div class="form-body">
                    <% if (error != null) { %>
                    <div class="error-message"><%= error.replaceAll("\n", "<br>") %></div>
                    <% } %>

                    <form action="EditBookingServlet" method="post" id="bookingForm">
                        <input type="hidden" name="id" value="<%= appointment.getAppointmentId() %>">

                        <label for="customerId">Bệnh nhân:</label>
                        <select name="customerId" id="customerId" required>
                            <% for(User u : customers) { %>
                            <option value="<%= u.getUserId() %>" <%= u.getUserId() == appointment.getCustomerId() ? "selected" : "" %>><%= u.getFullName() %></option>
                            <% } %>
                        </select>

                        <label for="doctorId">Bác sĩ:</label>
                        <select name="doctorId" id="doctorId" required>
                            <% for(User u : doctors) { %>
                            <option value="<%= u.getUserId() %>" <%= u.getUserId() == appointment.getDoctorId() ? "selected" : "" %>><%= u.getFullName() %></option>
                            <% } %>
                        </select>

                        <label for="serviceId">Dịch vụ:</label>
                        <select name="serviceId" id="serviceId" required>
                            <% for(Service s : services) { %>
                            <option value="<%= s.getServiceId() %>" <%= s.getServiceId() == appointment.getServiceId() ? "selected" : "" %>><%= s.getServiceName() %></option>
                            <% } %>
                        </select>

                        <label for="appointmentDate">Ngày khám:</label>
                        <%
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDate = sdf.format(appointment.getAppointmentDate());
                        %>
                        <input type="datetime-local" name="appointmentDate" id="appointmentDate" required value="<%= formattedDate %>">
                        <small style="color:gray;">Chỉ trong khung 07:00–11:30 hoặc 14:00–17:00</small>

                        <label for="status">Trạng thái:</label>
                        <select name="status" id="status" required>
                            <option value="Đang xử lý" <%= "Đang xử lý".equals(appointment.getStatus()) ? "selected" : "" %>>Đang xử lý</option>
                            <option value="Hoàn tất" <%= "Hoàn tất".equals(appointment.getStatus()) ? "selected" : "" %>>Hoàn tất</option>
                        </select>

                        <label for="note">Ghi chú:</label>
                        <textarea name="note" id="note" rows="4"><%= appointment.getNote() != null ? appointment.getNote() : "" %></textarea>

                        <div class="buttons">
                            <button type="submit" class="btn">Cập nhật</button>
                            <button type="reset" class="btn btn-r">Xoá tất cả</button>
                            <button type="button" class="btn btn-r" onclick="window.location.href = 'viewBooking.jsp'">Thoát</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <footer>
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </footer>

        <script>
            const appointmentInput = document.getElementById("appointmentDate");
            const timeError = document.createElement("div");
            timeError.className = "error-message";
            appointmentInput.parentNode.appendChild(timeError);

            appointmentInput.addEventListener("input", function () {
                const selectedDate = new Date(this.value);
                const now = new Date();

                appointmentInput.classList.remove("is-invalid");
                timeError.textContent = "";

                if (selectedDate < now) {
                    timeError.textContent = "❌ Không thể đặt lịch trong quá khứ.";
                    appointmentInput.classList.add("is-invalid");
                    return;
                }

                const hour = selectedDate.getHours();
                const minute = selectedDate.getMinutes();
                const totalMin = hour * 60 + minute;

                const isValidTime =
                        (totalMin >= 420 && totalMin <= 690) || // 07:00 – 11:30
                        (totalMin >= 840 && totalMin <= 1020);  // 14:00 – 17:00

                if (!isValidTime) {
                    timeError.textContent = "Chỉ được chỉnh trong khung 07:00–11:30 hoặc 14:00–17:00.";
                    appointmentInput.classList.add("is-invalid");
                }
            });
        </script>
    </body>
</html>
