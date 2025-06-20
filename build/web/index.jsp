<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Child Care System</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Icons -->
    <link href="https://cdn.jsdelivr.net/npm/@mdi/font@6.5.95/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="assets/css/style.css" rel="stylesheet">
</head>
<body>
    <%@ include file="include/header.jsp" %>

    <!-- Start Hero -->
    <section class="bg-half-170 d-table w-100" style="background: url('https://via.placeholder.com/1920x1080') center center;">
        <div class="bg-overlay"></div>
        <div class="container">
            <div class="row mt-5 justify-content-center">
                <div class="col-lg-12 text-center">
                    <div class="title-heading mt-4">
                        <h1 class="display-4 fw-bold text-white mb-3">Professional Child Care Services</h1>
                        <p class="para-desc text-white-50 mx-auto">Providing quality child care with love and dedication.</p>
                        <div class="mt-4 pt-2">
                            <a href="booking-form" class="btn btn-primary">Book Appointment</a>
                        </div>
                    </div>
            </div>
            </div>
        </div>
    </section>
    <!-- End Hero -->

    <!-- Start Services -->
    <section class="section">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12">
                    <div class="section-title text-center mb-4 pb-2">
                        <h4 class="title mb-4">Our Services</h4>
                        <p class="text-muted para-desc mx-auto mb-0">We provide comprehensive child care services</p>
                </div>
                </div>
            </div>

            <div class="row">
                <c:if test="${not empty services}">
                    <c:forEach items="${services}" var="service">
                        <div class="col-lg-4 col-md-6 mt-4 pt-2">
                            <div class="card service-wrapper rounded border-0 shadow p-4">
                                <div class="icon text-center text-primary">
                                    <i class="uil uil-heart-medical h3 mb-0"></i>
                                </div>
                                <div class="content mt-4">
                                    <h5 class="title">${service.name}</h5>
                                    <p class="text-muted mt-3 mb-0">${service.description}</p>
                                </div>
                                <div class="mt-3">
                                    <a href="booking-form" class="text-primary">Book Now <i class="mdi mdi-chevron-right"></i></a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </section>
    <!-- End Services -->

    <!-- Include Footer -->
    <%@ include file="include/footer.jsp" %>

    <!-- Back to top -->
    <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top">
        <i data-feather="arrow-up" class="icons"></i>
    </a>

    <!-- JavaScript -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://unpkg.com/feather-icons"></script>
    <script src="assets/js/app.js"></script>
</body>
</html>