<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="dao.AppointmentDAO, dao.UserDAO, dao.ServiceDAO" %>
<%@ page import="model.Appointment, model.User, model.Service" %>
<%
    AppointmentDAO dao = new AppointmentDAO();
    UserDAO userDao = new UserDAO();
    ServiceDAO serviceDao = new ServiceDAO();

    int currentPageNum = 1;
    int pageSize = 10;

    if (request.getParameter("page") != null) {
        try {
            currentPageNum = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            currentPageNum = 1;
        }
    }

    List<Appointment> fullList = dao.getAllAppointments();
    int totalAppointments = fullList.size();
    int totalPages = (int) Math.ceil((double) totalAppointments / pageSize);
    int start = (currentPageNum - 1) * pageSize;
    int end = Math.min(start + pageSize, totalAppointments);
    List<Appointment> appointments = fullList.subList(start, end);

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lịch khám đã đặt</title>
        <link href="./css_k/style.css" rel="stylesheet"/>
        <style>
            .table-title {
                background-color: white;
                color: #64ccff;
                text-align: center;
                padding: 20px 0;
                font-size: 22px;
                font-weight: bold;
                border-bottom: 2px solid #64ccff;
            }

            thead th {
                background-color: #64ccff;
                color: white;
                padding: 14px 10px;
                text-align: center;
                font-size: 15px;
                border-right: 1px solid #ffffff;
            }

            .pagination {
                margin: 50px 0;
                text-align: center;
            }
            .pagination a {
                margin: 0 5px;
                padding: 8px 12px;
                border: 1px solid #64ccff;
                border-radius: 6px;
                color: #64ccff;
                text-decoration: none;
                font-weight: bold;
            }
            .pagination a.active,
            .pagination a:hover {
                background-color: #64ccff;
                color: white;
            }
            .empty-message {
                text-align: center;
                padding: 20px;
                font-size: 1.1rem;
                color: #666;
            }
        </style>
    </head>
    <body>
        <div class="banner">
            <img src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474089BGn/mau-logo-rang-vang-tach-nen_045001529.png" alt="Logo"/>
            <a href="home.jsp">Trang chủ</a>
        </div>


        <div class="content">
            <div class="table-container">
                <div class="table-title">Lịch khám đã đặt</div>
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Bệnh nhân</th>
                            <th>Bác sĩ</th>
                            <th>Dịch vụ</th>
                            <th>Thời gian</th>
                            <th>Trạng thái</th>
                            <th>Ghi chú</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (appointments != null && !appointments.isEmpty()) {
                                for (Appointment a : appointments) {
                                    User patient = userDao.getUserById(a.getCustomerId());
                                    User doctor = userDao.getUserById(a.getDoctorId());
                                    Service service = serviceDao.getServiceById(a.getServiceId());
                        %>
                        <tr>
                            <td><%= a.getAppointmentId() %></td>
                            <td><%= patient != null ? patient.getFullName() : "?" %></td>
                            <td><%= doctor != null ? doctor.getFullName() : "?" %></td>
                            <td><%= service != null ? service.getServiceName() : "?" %></td>
                            <td><%= sdf.format(a.getAppointmentDate()) %></td>
                            <td><%= a.getStatus() %></td>
                            <td><%= a.getNote() != null ? a.getNote() : "" %></td>

                            <td class="buttons">
                                <a href="EditBookingServlet?id=<%= a.getAppointmentId() %>">
                                    <button class="btn">Sửa</button>
                                </a>
                            </td>
                        </tr>
                        <%   }
                } else { %>
                        <tr>
                            <td colspan="8" class="empty-message">Không có lịch khám nào.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            <% if (totalPages > 1) { %>
            <div class="pagination">
                <% for (int i = 1; i <= totalPages; i++) { %>
                <a href="viewBooking.jsp?page=<%= i %>" class="<%= (i == currentPageNum) ? "active" : "" %>"><%= i %></a>
                <% } %>
            </div>
            <% } %>
        </div>
        <footer>
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </footer>
    </body>
</html>