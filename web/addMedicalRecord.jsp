<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Nhập hồ sơ bệnh án</title>
  <link rel="stylesheet" href="css/addMedicalRecord.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
  <h2><i class="fas fa-user-md"></i> Nhập hồ sơ bệnh án</h2>
  <form action="addMedicalRecord" method="post">
    <label><i class="fas fa-calendar-check"></i> Mã cuộc hẹn:</label>
    <input type="number" name="appointmentID" required>

    <label><i class="fas fa-stethoscope"></i> Chẩn đoán:</label>
    <textarea name="diagnosis" required></textarea>

    <label><i class="fas fa-pills"></i> Điều trị:</label>
    <textarea name="treatment" required></textarea>

    <label><i class="fas fa-notes-medical"></i> Ghi chú:</label>
    <textarea name="description"></textarea>

    <label><i class="fas fa-tooth"></i> Ngày tái khám:</label>
    <input type="date" name="reExamDate">

    <button type="submit"><i class="fas fa-save"></i> Lưu hồ sơ</button>
  </form>
  <a href="viewMedicalRecords" class="back-button"><i class="fas fa-arrow-left"></i> Quay lại danh sách</a>
</body>
</html>
