<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
// Kiểm tra nếu specializations là null (truy cập trực tiếp)
if (request.getAttribute("specializations") == null) {
    response.sendRedirect("booking-form");
    return;
}
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt lịch khám</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/bookingStyle.css"/>
    <style>
        /* Additional styles for doctor selection */
        .doctor-option {
            padding: 8px;
            margin-bottom: 5px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .doctor-option:hover {
            background-color: #f0f0f0;
        }
        .no-doctors-message {
            color: #f44336;
            font-style: italic;
            padding: 10px 0;
            display: none;
        }
        .hidden {
            display: none !important;
        }
        .loading {
            display: inline-block;
            width: 20px;
            height: 20px;
            border: 3px solid rgba(0, 0, 0, 0.1);
            border-radius: 50%;
            border-top-color: #3498db;
            animation: spin 1s ease-in-out infinite;
            margin-left: 10px;
            vertical-align: middle;
        }
        @keyframes spin {
            to { transform: rotate(360deg); }
        }
    </style>
</head>
<body>
    <%@ include file="include/header.jsp" %>
    <div class="booking-wrapper">
       
        <div class="booking-container">
            <h1>Đặt lịch khám</h1>
            <form action="BookingServlet" method="post" class="booking-form">
                <div class="form-group inline-radio">
                    <label>Bạn muốn đặt cho:</label>
                    <input type="radio" id="self" name="for" value="self" required onclick="toggleRelative(false)" checked>
                    <label for="self">Bản thân</label>
                    <input type="radio" id="relative" name="for" value="relative" onclick="toggleRelative(true)">
                    <label for="relative">Người thân</label>
                </div>

                <!-- Thông tin người thân -->
                <div class="relative-info" id="relativeInfo" style="display:none;">
                    <div class="form-group">
                        <label>Tên người thân:</label>
                        <input type="text" name="relativeName">
                    </div>
                    <div class="form-group">
                        <label>Số điện thoại người thân:</label>
                        <input type="tel" name="relativePhone">
                    </div>
                    <div class="form-group">
                        <label>Email người thân:</label>
                        <input type="email" name="relativeEmail">
                    </div>
                    <div class="form-group">
                        <label>Giới tính:</label>
                        <select name="relativeGender">
                            <option value="">-- Chọn giới tính --</option>
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                            <option value="Khác">Khác</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label>Họ và tên:</label>
                    <input type="text" name="fullname" required>
                </div>

                <div class="form-group">
                    <label>Số điện thoại:</label>
                    <input type="tel" name="phone" required>
                </div>

                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email">
                </div>

                <div class="form-group">
                    <label>Địa chỉ:</label>
                    <input type="text" name="address">
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label>Ngày khám:</label>
                        <input type="date" name="date" required min="<%= java.time.LocalDate.now() %>" 
                               max="<%= java.time.LocalDate.now().plusMonths(3) %>">
                    </div>
                    <div class="form-group half-width">
                        <label>Giờ khám:</label>
                        <select name="time" required>
                            <option value="">-- Chọn giờ khám --</option>
                            <option value="08:00">08:00 - 09:00</option>
                            <option value="09:00">09:00 - 10:00</option>
                            <option value="10:00">10:00 - 11:00</option>
                            <option value="13:30">13:30 - 14:30</option>
                            <option value="14:30">14:30 - 15:30</option>
                            <option value="15:30">15:30 - 16:30</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label>Chuyên khoa:</label>
                        <select name="specialization" required id="specializationSelect">
                            <option value="">-- Chọn chuyên khoa --</option>
                            <!-- Debug info -->
                            
                            
                            <c:choose>
                                <c:when test="${not empty specializations}">
                                    <c:forEach var="specialization" items="${specializations}">
                                        <option value="${specialization.key}">${specialization.value}</option>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <!-- Fallback options if no specializations loaded -->
                                    <option value="1">Nha khoa tổng quát</option>
                                    <option value="2">Chỉnh nha</option>
                                    <option value="3">Phục hình răng</option>
                                    <option value="4">Nha khoa thẩm mỹ</option>
                                    <option value="5">Nha khoa trẻ em</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                        <span id="loadingSpecialization" class="loading" style="display: none;"></span>
                    </div>
                    <div class="form-group half-width">
                        <label>Chọn bác sĩ:</label>
                        <select name="doctorId" id="doctorSelect">
                            <option value="">-- Không chỉ định --</option>
                            <c:choose>
                                <c:when test="${not empty doctors}">
                                    <c:forEach var="doctor" items="${doctors}">
                                        <option value="${doctor.doctorId}" data-specialization="${doctor.specializationId}" class="doctor-option">${doctor.fullName} - ${doctor.specialization}</option>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <!-- Fallback options if no doctors loaded -->
                                    <option value="1">BS. Nguyễn Văn A</option>
                                    <option value="2">BS. Trần Thị B</option>
                                    <option value="3">BS. Lê Văn C</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                        <div id="noDoctorsMessage" class="no-doctors-message">
                            Không có bác sĩ nào cho chuyên khoa này. Vui lòng chọn chuyên khoa khác hoặc để trống để chúng tôi sắp xếp bác sĩ phù hợp.
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label>Ghi chú:</label>
                    <textarea name="note" rows="4" placeholder="Nhập triệu chứng hoặc yêu cầu đặc biệt (nếu có)"></textarea>
                </div>

                <div class="form-group" style="text-align:center;">
                    <button type="submit">Xác nhận đặt lịch</button>
                </div>
            </form>
        </div>

        <div class="booking-footer">
            Nụ cười của bạn – Sứ mệnh của chúng tôi!
        </div>
    </div>

    <script>
        function toggleRelative(show) {
            document.getElementById("relativeInfo").style.display = show ? "block" : "none";
        }

        window.onload = function () {
            toggleRelative(false); // Mặc định chọn bản thân
            
            // Filter doctors based on selected specialization
            const specializationSelect = document.getElementById('specializationSelect');
            const doctorSelect = document.getElementById('doctorSelect');
            const noDoctorsMessage = document.getElementById('noDoctorsMessage');
            const loadingIndicator = document.getElementById('loadingSpecialization');
            
            specializationSelect.addEventListener('change', function() {
                const selectedSpecialization = this.value;
                
                // Reset doctor selection
                doctorSelect.selectedIndex = 0;
                
                if (!selectedSpecialization) {
                    // If no specialization selected, show all doctors
                    showAllDoctors();
                    noDoctorsMessage.style.display = 'none';
                    return;
                }
                
                // Show loading indicator
                loadingIndicator.style.display = 'inline-block';
                
                console.log("Fetching doctors for specialization ID: " + selectedSpecialization);
                
                // Fetch doctors for this specialization using AJAX
                fetch('get-doctors-by-specialization?specializationId=' + selectedSpecialization)
                    .then(response => {
                        console.log("Response status:", response.status);
                        if (!response.ok) {
                            return response.text().then(text => {
                                console.log("Error response:", text);
                                throw new Error('Network response was not ok: ' + response.status + ' ' + text);
                            });
                        }
                        return response.json();
                    })
                    .then(doctors => {
                        console.log("Received doctors:", doctors);
                        
                        // Clear existing doctor options except the default one
                        while (doctorSelect.options.length > 1) {
                            doctorSelect.remove(1);
                        }
                        
                        // Add new doctor options
                        if (doctors && doctors.length > 0) {
                            doctors.forEach(doctor => {
                                const option = document.createElement('option');
                                option.value = doctor.doctorId;
                                option.setAttribute('data-specialization', doctor.specializationId);
                                option.className = 'doctor-option';
                                option.textContent = doctor.fullName + ' - ' + doctor.specialization;
                                doctorSelect.appendChild(option);
                            });
                            noDoctorsMessage.style.display = 'none';
                        } else {
                            noDoctorsMessage.style.display = 'block';
                        }
                        
                        // Hide loading indicator
                        loadingIndicator.style.display = 'none';
                    })
                    .catch(error => {
                        console.error('Error fetching doctors:', error);
                        noDoctorsMessage.style.display = 'block';
                        loadingIndicator.style.display = 'none';
                    });
            });
            
            function showAllDoctors() {
                const doctorOptions = document.querySelectorAll('.doctor-option');
                doctorOptions.forEach(option => {
                    option.style.display = 'block';
                });
            }
        };
    </script>
</body>
</html>
