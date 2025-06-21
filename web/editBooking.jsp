<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Appointment, model.User, model.Service" %>
<%
    Appointment appointment = (Appointment) request.getAttribute("appointment");
    List<User> customers = (List<User>) request.getAttribute("customers");
    List<User> doctors = (List<User>) request.getAttribute("doctors");
    List<Service> services = (List<Service>) request.getAttribute("services");
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
                color: red;
                text-align: center;
                font-weight: bold;
                margin: 10px 0;
                white-space: pre-line;
            }
        </style>
    </head>
    <body>
        <div class="content">
            <div class="form-container">
                <div class="header">Chỉnh sửa lịch khám</div>
                <div class="form-body">
                    <% if (error != null) { %>
                    <div class="error-message"><%= error.replaceAll("\\n", "<br>") %></div>
                    <% } %>

                    <form action="EditBookingServlet" method="post" onsubmit="return validateNote();">
                        <input type="hidden" name="id" value="<%= appointment.getAppointmentId() %>"/>

                        <label>Bệnh nhân:</label>
                        <select name="customerId" required>
                            <% for(User u : customers) { %>
                            <option value="<%= u.getUserId() %>" <%= u.getUserId() == appointment.getCustomerId() ? "selected" : "" %>><%= u.getFullName() %></option>
                            <% } %>
                        </select>

                        <label>Bác sĩ:</label>
                        <select name="doctorId" required>
                            <% for(User u : doctors) { %>
                            <option value="<%= u.getUserId() %>" <%= u.getUserId() == appointment.getDoctorId() ? "selected" : "" %>><%= u.getFullName() %></option>
                            <% } %>
                        </select>

                        <label>Dịch vụ:</label>
                        <select name="serviceId" required>
                            <% for(Service s : services) { %>
                            <option value="<%= s.getServiceId() %>" <%= s.getServiceId() == appointment.getServiceId() ? "selected" : "" %>><%= s.getServiceName() %></option>
                            <% } %>
                        </select>

                        <label>Ngày khám:</label>
                        <%
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDate = sdf.format(appointment.getAppointmentDate());
                        %>
                        <input type="datetime-local" name="appointmentDate" value="<%= formattedDate %>" required>

                        <label>Trạng thái:</label>
                        <select name="status" required>
                            <option value="Đang xử lý" <%= "Đang xử lý".equals(appointment.getStatus()) ? "selected" : "" %>>Đang xử lý</option>
                            <option value="Hoàn tất" <%= "Hoàn tất".equals(appointment.getStatus()) ? "selected" : "" %>>Hoàn tất</option>
                        </select>

                        <label>Ghi chú:</label>
                        <textarea name="note" rows="4" maxlength="255"><%= appointment.getNote() != null ? appointment.getNote() : "" %></textarea>

                        <div class="buttons">
                            <button type="submit" class="btn">Cập nhật</button>
                            <button type="reset" class="btn-r">Xoá</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

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
                if (/(.)\1\1/.test(note)) {
                    alert("Không được nhập 3 ký tự giống nhau liên tiếp trong ghi chú.");
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
