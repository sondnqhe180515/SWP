<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.MedicalRecords" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ghi chú tái khám</title>
    <link rel="stylesheet" href="css/reexamNote.css"> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

    <form action="reexamNote" method="post">
        <h2><i class="fas fa-notes-medical"></i> Ghi chú tái khám</h2>

        <c:if test="${not empty error}">
            <p class="error"><i class="fas fa-exclamation-circle"></i> ${error}</p>
        </c:if>

        <label for="recordId"><i class="fas fa-id-card"></i> Mã hồ sơ </label>
        <input type="hidden" name="recordId" value="${recordId}" />

        <label for="reExamNote"><i class="fas fa-pen"></i> Ghi chú tái khám:</label>
         <textarea name="reExamNote" required>${reExamNote}</textarea>

        <button type="submit"><i class="fas fa-save"></i> Lưu ghi chú</button>
    </form>

    <a href="viewMedicalRecords" class="back-button"><i class="fas fa-arrow-left"></i> Quay lại danh sách hồ sơ</a>

</body>
</html>
