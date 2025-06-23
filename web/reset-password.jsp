<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Phòng khám nha khoa SmileCare - Đặt lại mật khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
    <!-- Dental clinic background elements -->
    <div class="dental-background">
        <!-- Medical cross -->
        <div class="dental-cross"></div>
        
        <!-- Floating dental icons -->
        <i class="fas fa-tooth floating-dental-icon" style="top: 15%; left: 8%; font-size: 40px; animation-duration: 18s;"></i>
        <i class="fas fa-user-md floating-dental-icon" style="top: 25%; right: 12%; font-size: 35px; animation-duration: 22s;"></i>
        <i class="fas fa-heartbeat floating-dental-icon" style="bottom: 20%; left: 10%; font-size: 32px; animation-duration: 20s;"></i>
        <i class="fas fa-shield-alt floating-dental-icon" style="bottom: 35%; right: 15%; font-size: 30px; animation-duration: 16s;"></i>
        <i class="fas fa-stethoscope floating-dental-icon" style="top: 60%; left: 5%; font-size: 28px; animation-duration: 24s;"></i>
        <i class="fas fa-tooth floating-dental-icon" style="top: 45%; right: 8%; font-size: 38px; animation-duration: 14s;"></i>
    </div>

    <div class="container-fluid d-flex align-items-center justify-content-center h-100">
        <div class="login-container">
            <div class="clinic-logo">
                <div class="logo-container">
                    <i class="fas fa-sync-alt"></i>
                </div>
                <h1 class="clinic-name">Đặt lại mật khẩu</h1>
                <p class="clinic-tagline">Bảo mật SmileCare</p>
            </div>
            
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="error-message">
                <i class="fas fa-exclamation-triangle"></i>
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>
            
            <form method="post" action="${pageContext.request.contextPath}/forgot-password">
                <input type="hidden" name="action" value="resetPassword">
                
                <div class="mb-3">
                    <label for="newPassword" class="form-label">
                        <i class="fas fa-lock"></i>Mật khẩu mới
                    </label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-key"></i></span>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" 
                               placeholder="Enter new secure password"
                               minlength="6" required>
                    </div>
                    <div class="form-text">Mật khẩu phải dài ít nhất 6 ký tự</div>
                </div>
                
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">
                        <i class="fas fa-lock"></i>Xác nhận mật khẩu mới
                    </label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-shield-check"></i></span>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                               placeholder="Confirm your new password"
                               minlength="6" required>
                    </div>
                </div>
                
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Đặt lại mật khẩu
                    </button>
                </div>
            </form>
            
            <div class="signup-link">
                Ghi nhớ mật khẩu của bạn? <a href="${pageContext.request.contextPath}/login">Quay lại Đăng nhập</a>
            </div>
        </div>
    </div>
    
    <script>
        // Password confirmation validation
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const password = document.getElementById('newPassword').value;
            const confirmPassword = this.value;
            
            if (password !== confirmPassword) {
                this.setCustomValidity('Passwords do not match');
            } else {
                this.setCustomValidity('');
            }
        });
    </script>
</body>
</html>
