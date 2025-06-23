<%@ page import="java.util.List" %>
<%@ page import="model.Appointments" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý lịch hẹn</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/appointment-style.css">
        <style>
            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
            }

            .wrapper {
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }

            .content {
                flex: 1;
                padding: 20px;
            }

            .btn-add {
                background-color: #64ccff;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                font-size: 14px;
                cursor: pointer;
                margin-bottom: 20px;
            }

            .btn-add:hover {
                background-color: #52bde8;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <div class="content">
                <h2 style="text-align:center; color: #0078B4;">Danh sách lịch hẹn</h2>

                <!-- Nút Thêm lịch hẹn -->
                <div style="text-align: right;">
                    <form action="addAppointment" method="get">
                        <button class="btn-add" type="submit">+ Thêm lịch hẹn</button>
                    </form>
                </div>

                <div class="table-container">
                    <table class="table" border="1" cellpadding="10">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Customer ID</th>
                                <th>Doctor ID</th>
                                <th>Service ID</th>
                                <th>Date</th>
                                <th>Status</th>
                                <th>Note</th> 
                                <th>Created At</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Appointments> list = (List<Appointments>) request.getAttribute("list");
                                if (list != null && !list.isEmpty()) {
                                    for (Appointments appt : list) {
                            %>
                            <tr>
                                <td><%= appt.getAppointmentID() %></td>
                                <td><%= appt.getCustomerID() %></td>
                                <td><%= appt.getDoctorID() %></td>
                                <td><%= appt.getServiceID() %></td>
                                <td><%= appt.getAppointmentDate() %></td>
                                <td><%= appt.getStatus() %></td>
                                <td><%= appt.getNote() %></td>
                                <td><%= appt.getCreatedAt() %></td>
                                <td>
                                    <form action="manageAppointment" method="post" style="display:inline;">
                                        <input type="hidden" name="appointmentID" value="<%= appt.getAppointmentID() %>"/>
                                        <button class="btn-r" type="submit" name="action" value="confirm">Xác nhận</button>
                                        <button class="btn-r" type="submit" name="action" value="cancel">Hủy</button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr><td colspan="9">Không có lịch hẹn nào</td></tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>

            </div>

            <div class="btn-bottom-left">
                <a href="home" class="btn-back-home">← Quay lại trang chủ</a>
            </div>

            <footer>
                © Phòng khám đa khoa - Quản lý lịch hẹn
            </footer>
        </div>
    </body>
</html>
