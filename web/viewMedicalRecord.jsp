<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.MedicalRecords" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách hồ sơ bệnh án</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/viewMedicalRecord.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/doctor-sidebar.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>
    <body>
        <!-- Include Sidebar -->
        <%@ include file="includes/doctor-sidebar.jsp" %>
        
        <div class="main-content">
            <div style="padding: 20px;">

        <h2><i class="fas fa-notes-medical"></i> Danh sách hồ sơ bệnh án</h2>

        <form action="viewMedicalRecords" method="get" style="margin-bottom:20px; text-align:left;">
            <input type="text" name="searchName" placeholder="Tìm theo tên khách hàng..." value="${searchName}" style="padding:15px; width:950px; border-radius:4px; border:1px solid #ccc;">
            <button type="submit" style="padding:15px 30px; background:#64ccff; border:none; border-radius:4px; color:#fff;">Tìm kiếm</button>
        </form>

        <div class="top-buttons">
            <a href="addMedicalRecord.jsp" class="btn-add"><i class="fas fa-plus" ></i> Thêm hồ sơ mới</a>
        </div>

        <c:if test="${not empty records}">
            <table>
                <thead>
                    <tr>
                        <th>Mã hồ sơ</th>
                        <th>Mã cuộc hẹn</th>
                        <th>Tên bệnh nhân</th>
                        <th>Chẩn đoán</th>
                        <th>Điều trị</th>
                        <th>Ghi chú</th>
                        <th>Ngày tái khám</th>
                        <th>Ghi chú tái khám</th>
                        <th>Ngày tạo</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${records}">
                        <tr>
                            <td>${r.recordID}</td>
                            <td>${r.appointmentID}</td>
                            <td>${r.customerName}</td>
                            <td>${r.diagnosis}</td>
                            <td>${r.treatment}</td>
                            <td>${r.description}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty r.reExamDate}">
                                        ${r.reExamDate}
                                    </c:when>
                                    <c:otherwise>Chưa đặt</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty r.reExamNote}">
                                        ${r.reExamNote}
                                    </c:when>
                                    <c:otherwise><i>Chưa có</i></c:otherwise>
                                </c:choose>
                            </td>
                            <td>${r.createdAt}</td>
                            <td>
                                <a href="reexamNote?recordId=${r.recordID}" class="btn-update">
                                    <i class="fas fa-edit"></i> Ghi chú tái khám
                                </a>
                            </td>
                            
                            <td>
                                <a href="DeleteMedicalRecord?recordId=${r.recordID}"
                                   class="btn-delete"
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa hồ sơ này?');">
                                    <i class="fas fa-trash-alt"></i> Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty records}">
            <p style="text-align:center; color: gray;">Không có hồ sơ bệnh án nào.</p>
        </c:if>

            </div>
        </div>
    </body>
</html>
</html>
