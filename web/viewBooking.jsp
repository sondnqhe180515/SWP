<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="dao.AppointmentDAO, dao.UserDAO, dao.ServiceDAO" %>
<%@ page import="model.Appointment, model.User, model.Service" %>
<%
    AppointmentDAO dao = new AppointmentDAO();
    UserDAO userDao = new UserDAO();
    ServiceDAO serviceDao = new ServiceDAO();

    String statusFilter = request.getParameter("status");
    String dateFilter = request.getParameter("date");
    String sortBy = request.getParameter("sortBy");
    String keyword = request.getParameter("keyword");

    if (statusFilter == null) statusFilter = "";
    if (dateFilter == null) dateFilter = "";
    if (sortBy == null) sortBy = "";
    if (keyword == null) keyword = "";

    int currentPageNum = 1;
    int pageSize = 10;
    String pageParam = request.getParameter("page");
    if (pageParam != null) {
        try {
            currentPageNum = Integer.parseInt(pageParam);
        } catch (NumberFormatException e) {
            currentPageNum = 1;
        }
    }

    // Lấy danh sách lịch khám
    List<Appointment> fullList;
    if (!keyword.trim().isEmpty()) {
        fullList = dao.searchAppointmentsByCustomerName(keyword, statusFilter, dateFilter, sortBy);
    } else if ("name".equalsIgnoreCase(sortBy)) {
        fullList = dao.sortAppointmentsByCustomerNameAZ(statusFilter, dateFilter);
    } else if ("date".equalsIgnoreCase(sortBy)) {
        fullList = dao.sortAppointmentsByDate(statusFilter, dateFilter);
    } else {
        if (statusFilter.isEmpty() && dateFilter.isEmpty()) {
            fullList = dao.getAllAppointments();
        } else {
            fullList = dao.getFilteredAppointments(statusFilter, dateFilter);
        }
    }
   
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
            .filter-form {
                padding: 10px 20px;
                margin: 10px 20px 0 auto;
                display: flex;
                justify-content: flex-end;
                align-items: center;
                gap: 15px;
                flex-wrap: wrap;
            }
            .filter-form label {
                font-size: 1rem;
                color: #333;
                font-weight: bold;
            }
            .filter-form select,
            .filter-form input[type="date"],
            .filter-form input[type="text"] {
                padding: 8px;
                border: 1px solid #0078B4;
                border-radius: 4px;
                font-size: 0.95rem;
            }
            .filter-form button {
                padding: 8px 12px;
                background-color: #64ccff;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 0.95rem;
            }
            .filter-form button:hover {
                background-color: #0078B4;
            }
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
            <a href="#">Trang chủ</a>
        </div>

        <form method="get" action="ViewBookingServlet" class="filter-form">
            <div>
                <label>Sắp xếp:</label>
                <select name="sortBy" onchange="this.form.submit()">
                    <option value="" <%= sortBy.isEmpty() ? "selected" : "" %>>Mặc định</option>
                    <option value="name" <%= "name".equals(sortBy) ? "selected" : "" %>>Theo tên bệnh nhân</option>
                    <option value="date" <%= "date".equals(sortBy) ? "selected" : "" %>>Theo thời gian đăng ký</option>
                </select>
            </div>
            <div>
                <label>Trạng thái:</label>
                <select name="status" onchange="this.form.submit()">
                    <option value="" <%= statusFilter.isEmpty() ? "selected" : "" %>>Tất cả</option>
                    <option value="Đang xử lý" <%= "Đang xử lý".equals(statusFilter) ? "selected" : "" %>>Đang xử lý</option>
                    <option value="Hoàn tất" <%= "Hoàn tất".equals(statusFilter) ? "selected" : "" %>>Hoàn tất</option>
                </select>
            </div>

            <div>
                <label>Tìm kiếm theo thời gian:</label>
                <input type="date" name="date" value="<%= dateFilter %>" />
                <button type="submit">Tìm kiếm</button>
            </div>

            <div>
                <label>Tìm kiếm theo tên bệnh nhân:</label>
                <input type="text" name="keyword" value="<%= keyword %>" />
                <button type="submit">Tìm kiếm</button>
            </div>
        </form>

        <div class="content">
            <div class="table-container">
                <div class="table-title">Lịch khám đã đặt</div>
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>STT</th>
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
                                int stt = start + 1;
                                for (Appointment a : appointments) {
                                    User patient = userDao.getUserById(a.getCustomerId());
                                    User doctor = userDao.getUserById(a.getDoctorId());
                                    Service service = serviceDao.getServiceById(a.getServiceId());
                        %>
                        <tr>
                            <td><%= stt++ %></td>
                            <td><%= patient != null ? patient.getFullName() : "?" %></td>
                            <td><%= doctor != null ? doctor.getFullName() : "?" %></td>
                            <td><%= service != null ? service.getServiceName() : "?" %></td>
                            <td><%= sdf.format(a.getAppointmentDate()) %></td>
                            <td><%= a.getStatus() %></td>
                            <td><%= a.getNote() != null ? a.getNote() : "" %></td>
                            <td class="buttons">
                                <a href="EditBookingServlet?id=<%= a.getAppointmentId() %>&status=<%= statusFilter %>&date=<%= dateFilter %>&sortBy=<%= sortBy %>&keyword=<%= keyword %>&page=<%= currentPageNum %>">
                                    <button class="btn">Điều chỉnh</button>
                                </a>
                                <a href="AppointmentDetailServlet?id=<%= a.getAppointmentId() %>">
                                    <button class="btn">Xem chi tiết</button>
                                </a>
                            </td>
                        </tr>
                        <% } 
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
                <a href="viewBooking.jsp?page=<%= i %>&status=<%= statusFilter %>&date=<%= dateFilter %>&sortBy=<%= sortBy %>&keyword=<%= keyword %>" 
                   class="<%= (i == currentPageNum) ? "active" : "" %>"><%= i %></a>
                <% } %>
            </div>
            <% } %>
        </div>

        <footer>
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </footer>
    </body>
</html>