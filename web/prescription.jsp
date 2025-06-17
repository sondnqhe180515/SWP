<%-- 
    Document   : prescription
    Created on : Jun 10, 2025, 7:44:40 AM
    Author     : An
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Kê Toa Thuốc - Phòng Khám Răng</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/prescription.css">
    </head>
    <body>
        <div class="container">
            <h2>Tạo Toa Thuốc - Phòng Khám Răng</h2>
            <form method="post" action="#">
                <div>
                    <h3>Thông Tin Bệnh Nhân</h3>
                    <table border="0">
                        <tbody>
                            <tr>
                                <td><label>Họ tên bệnh nhân:</label>
                                    <input type="text" name="name" required></td>
                                <td> <label>Mã bệnh nhân:</label>
                                    <input type="text" name="patientId"></td>
                            </tr>
                            <tr>
                                <td> <label>Ngày khám:</label>
                                    <input type="date" name="visitDate"></td>
                                <td>  <label>Chẩn đoán:</label>
                                    <select name="diagnosis">
                                        <option value="Viêm nha chu">Viêm nha chu</option>
                                        <option value="Sâu răng">Sâu răng</option>
                                        <option value="Viêm lợi">Viêm lợi</option>
                                        <option value="Khác">Khác</option>
                                    </select></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div>
                    <h3>Danh Sách Thuốc</h3>
                    <table>
                        <tr>
                            <th>Tên thuốc</th>
                            <th>Liều dùng</th>
                            <th>Số lần/ngày</th>
                            <th>Số ngày</th>
                            <th>Ghi chú</th>
                        </tr>
                        <tr>
                            <td><input type="text" name="medName1"></td>
                            <td><input type="text" name="dose1"></td>
                            <td><input type="text" name="freq1"></td>
                            <td><input type="text" name="days1"></td>
                            <td><input type="text" name="note1"></td>
                        </tr>
                    </table>
                </div>

                <div >
                    <h3>Hướng Dẫn Dùng Thuốc</h3>
                    <textarea name="instructions" rows="4" placeholder="Ví dụ: uống sau bữa ăn..."></textarea>
                </div>

                <div>
                    <h3>Thông Tin Bác Sĩ</h3>
                    <label>Bác sĩ kê đơn:</label>
                    <input type="text" name="doctorName">
                </div>

                <input class="btn-submit" type="submit" value="Lưu Toa Thuốc">
            </form>
        </div>
    </body>
</html>
