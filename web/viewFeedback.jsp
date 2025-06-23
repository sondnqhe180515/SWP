<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Feedbacks" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Phản hồi bệnh nhân</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/doctor-sidebar.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            .main-content .feedback-content {
                padding: 20px;
            }
            .main-content body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f2fbff;
                padding: 20px;
            }

            h2 {
                text-align: center;
                color: #0078b4;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
                background-color: white;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                border-radius: 10px;
                overflow: hidden;
            }

            th, td {
                padding: 12px 15px;
                border-bottom: 1px solid #ddd;
                text-align: center;
            }

            th {
                background-color: #64ccff;
                color: white;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            .btn {
                background-color: #64ccff;
                color: white;
                padding: 10px 20px;
                margin: 20px auto;
                display: inline-block;
                border: none;
                border-radius: 8px;
                font-weight: bold;
                text-decoration: none;
                cursor: pointer;
            }

            .btn:hover {
                background-color: #45bde0;
            }

            .heart-icon {
                color: gray;
                cursor: pointer;
                font-size: 18px;
                transition: color 0.3s ease;
            }

            .heart-icon.liked {
                color: red;
            }

            .delete-icon {
                color: red;
                cursor: pointer;
            }

            .actions {
                text-align: center;
            }
            .star {
                color: #FFD700;
            }
        </style>
    </head>
    <body>
        <!-- Include Sidebar -->
        <%@ include file="includes/doctor-sidebar.jsp" %>
        
        <div class="main-content">
            <div class="feedback-content">

        <h2><i class="fas fa-comments"></i> Phản hồi từ bệnh nhân</h2>

        <c:if test="${not empty feedbackList}">
            <table>
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Khách hàng</th>
                        <th>Đánh giá</th>
                        <th>Bình luận</th>
                        <th>Ngày tạo</th>
                        <th>Thích</th>
                        <th>Xóa</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="fb" items="${feedbackList}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${fb.customerName}</td>
                            <td>
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="fa-star ${i <= fb.rating ? 'fas star' : 'far'}"></i>
                                </c:forEach>
                            </td>
                            <td>${fb.comment}</td>
                            <td>${fb.createdAt}</td>
                            <td>
                                <i class="fa-heart far heart-icon" onclick="toggleLike(this)" title="Thả tim"></i>
                            </td>
                            <td>
                                <a href="deleteFeedback?id=${fb.feedbackID}" onclick="return confirm('Bạn có chắc muốn xóa phản hồi này?')">
                                    <i class="fas fa-trash-alt delete-icon"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty feedbackList}">
            <p style="text-align:center; color: gray;">Không có phản hồi nào.</p>
        </c:if>

        <div class="actions">
            <a href="index.jsp" class="btn"><i class="fas fa-arrow-left"></i> Quay lại</a>
        </div>

        <script>
            function toggleLike(element) {
                element.classList.toggle("liked");
                element.classList.toggle("fas");
                element.classList.toggle("far");
            }
        </script>

            </div>
        </div>
    </body>
</html>
