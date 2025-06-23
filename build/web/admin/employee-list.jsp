<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%
    List<User> employees = (List<User>) request.getAttribute("employees");
    String selectedRole = (String) request.getAttribute("selectedRole");
    String message = request.getParameter("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách nhân viên</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/employee-list.css">
    </head>
    <body>

        <% if ("success".equals(message)) { %>
        <p style="color: green;">✅ Cập nhật trạng thái thành công.</p>
        <% } else if ("fail".equals(message)) { %>
        <p style="color: red;">❌ Cập nhật trạng thái thất bại.</p>
        <% } %>

        <h2>Danh sách nhân viên</h2>

        <form method="get" action="employee-list">
            <select name="role" onchange="this.form.submit()">
                <option value="all" <%= "all".equals(selectedRole) || selectedRole == null ? "selected" : "" %>>Tất cả</option>
                <option value="staff" <%= "staff".equals(selectedRole) ? "selected" : "" %>>Nhân viên</option>
                <option value="doctor" <%= "doctor".equals(selectedRole) ? "selected" : "" %>>Bác sĩ</option>
            </select>
        </form>

        <table>
            <tr>
                <th>STT</th>
                <th>Tên</th>
                <th>Địa chỉ</th>
                <th>Email</th>
                <th>SĐT</th>
                <th>Thao tác</th>
            </tr>
            <%
                int i = 1;
                for (User u : employees) {
            %>
            <tr>
                <td><%= i++ %></td>
                <td><%= u.getFullName() %></td>
                <td><%= u.getAddress() %></td>
                <td><%= u.getEmail() %></td>
                <td><%= u.getPhoneNumber() %></td>
                
                <td>
                    <form action="<%= request.getContextPath() %>/update-status" method="post" style="display:inline;">
                        <input type="hidden" name="userId" value="<%= u.getUserId() %>" />
                        <input type="hidden" name="status" value="true" />
                        <input type="submit" value="Mở khóa" class="unlock-btn" />
                    </form>
                    <form action="<%= request.getContextPath() %>/update-status" method="post" style="display:inline;">
                        <input type="hidden" name="userId" value="<%= u.getUserId() %>" />
                        <input type="hidden" name="status" value="false" />
                        <input type="submit" value="Khóa" class="lock-btn" />
                    </form>
                </td>

            </tr>
            <% } %>
        </table>

    </body>
</html>
