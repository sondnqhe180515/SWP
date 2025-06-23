<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm lịch hẹn</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/addappointment-style.css">

    </head>
    <body>
        <h2>Thêm lịch hẹn mới</h2>

        <form action="addAppointment" method="post">
            <label>Customer ID:</label>
            <input type="number" name="customerID" required><br>

            <label>Doctor ID:</label>
            <input type="number" name="doctorID" required><br>

            <label>Service ID:</label>
            <input type="number" name="serviceID" required><br>

            <label>Ngày hẹn:</label>
            <input type="date" name="appointmentDate" required><br>

            <label>Ghi chú:</label>
            <textarea name="note"></textarea><br>

            <button type="submit">Thêm lịch hẹn</button>
        </form>

        <p style="color:red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>

        <p style="text-align: center;">
            <a href="manageAppointment" class="back-button">← Quay lại danh sách</a>
        </p>

    </body>
</html>
