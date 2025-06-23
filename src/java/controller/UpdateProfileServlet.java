package controller;

import dao.UserDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            request.setAttribute("userIdError", "Vui lòng nhập UserID!");
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            UserDAO userDAO = new UserDAO();
            model.User user = userDAO.getUserById(userId);

            if (user == null) {
                request.setAttribute("userIdError", "Không tìm thấy người dùng!");
                request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
                return;
            }

            request.setAttribute("user", user);
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("userIdError", "UserID không hợp lệ!");
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String userIdStr = request.getParameter("userId");
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String confirmDob = request.getParameter("confirmDob");
            
            // Kiểm tra UserID
            if (userIdStr == null || userIdStr.isEmpty()) {
                request.setAttribute("userIdError", "Vui lòng nhập UserID!");
                forwardError(userIdStr, fullName, phoneNumber, address, dateOfBirthStr, gender, request, response);
                return;
            }
            
            int userId;
            try {
                userId = Integer.parseInt(userIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("userIdError", "UserID không hợp lệ!");
                forwardError(userIdStr, fullName, phoneNumber, address, dateOfBirthStr, gender, request, response);
                return;
            }
            
            // Lấy thông tin người dùng hiện tại
            UserDAO userDAO = new UserDAO();
            model.User currentUser = userDAO.getUserById(userId);
            if (currentUser == null) {
                request.setAttribute("userIdError", "Không tìm thấy người dùng!");
                forwardError(userIdStr, fullName, phoneNumber, address, dateOfBirthStr, gender, request, response);
                return;
            }
            
            // Kiểm tra roleId
            if (currentUser.getRoleId() != 1) {
                request.setAttribute("userIdError", "Chỉ tài khoản của khách hàng mới được phép cập nhật hồ sơ!");
                forwardError(userIdStr, fullName, phoneNumber, address, dateOfBirthStr, gender, request, response);
                return;
            }
            
            // Map để lưu lỗi cho từng trường
            Map<String, String> errors = new HashMap<>();
            
            // Validation cho Họ và tên
            if (fullName == null || fullName.trim().isEmpty()) {
                fullName = currentUser.getFullName();
            } else {
                if (!fullName.matches("^[A-Za-zÀ-ỹ][A-Za-zÀ-ỹ\\s]*$") || fullName.length() > 50) {
                    errors.put("fullNameError", "Họ và tên chỉ được chứa chữ cái và khoảng trắng, ký tự đầu tiên phải là chữ cái, tối đa 50 ký tự.");
                } else {
                    boolean hasThreeConsecutive = false;
                    for (int i = 0; i < fullName.length() - 2; i++) {
                        if (fullName.charAt(i) == fullName.charAt(i + 1) && fullName.charAt(i) == fullName.charAt(i + 2)) {
                            hasThreeConsecutive = true;
                            break;
                        }
                    }
                    if (hasThreeConsecutive) {
                        errors.put("fullNameError", "Họ và tên không được có 3 ký tự liên tiếp trùng nhau.");
                    }
                }
            }
            
            // Validation cho Số điện thoại
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                phoneNumber = currentUser.getPhoneNumber();
            } else {
                if (!phoneNumber.matches("^0\\d{9}$")) {
                    errors.put("phoneNumberError", "Số điện thoại phải bắt đầu bằng 0 và có đúng 10 chữ số.");
                }
            }
            
            // Validation cho Địa chỉ
            if (address == null || address.trim().isEmpty()) {
                address = currentUser.getAddress();
            } else {
                if (address.length() < 2) {
                    errors.put("addressError", "Địa chỉ phải có ít nhất 2 ký tự.");
                } else if (!address.matches("^[A-Za-zÀ-ỹ0-9][A-Za-zÀ-ỹ0-9\\s*,.]*$")) {
                    errors.put("addressError", "Địa chỉ chỉ được chứa chữ cái, số, khoảng trắng, dấu phẩy hoặc dấu gạch chéo, và bắt đầu bằng chữ cái hoặc số.");
                }
            }
            
            // Validation cho Ngày sinh
            Date dateOfBirth = currentUser.getDateOfBirth();
            boolean isDobConfirmed = "true".equals(confirmDob);
            if (dateOfBirthStr != null && !dateOfBirthStr.trim().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    dateOfBirth = sdf.parse(dateOfBirthStr);
                    Date currentDate = new Date();
                    if (dateOfBirth.after(currentDate)) {
                        errors.put("dateOfBirthError", "Ngày sinh không được là ngày trong tương lai.");
                    } else {
                        int birthYear = Integer.parseInt(dateOfBirthStr.split("-")[0]);
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        if (birthYear < 1) {
                            errors.put("dateOfBirthError", "Ngày sinh không hợp lệ.");
                        } else if (currentYear - birthYear > 100 && !isDobConfirmed) {
                            errors.put("dateOfBirthError", "Bạn có chắc bạn sinh năm " + birthYear + " không?");
                            request.setAttribute("showConfirmDob", "true");
                        } else if (currentYear - birthYear > 100 && isDobConfirmed) {
                            // Không đặt lỗi nếu đã xác nhận "Có"
                            errors.remove("dateOfBirthError"); // Xóa lỗi nếu đã xác nhận
                        }
                    }
                } catch (ParseException e) {
                    errors.put("dateOfBirthError", "Định dạng ngày sinh không hợp lệ!");
                }
            } else if ("false".equals(confirmDob)) {
                dateOfBirth = null; // Xóa ngày sinh nếu nhấn "Không"
                dateOfBirthStr = ""; // Đặt lại dateOfBirthStr để reset trường ngày sinh trên giao diện
            }
            
            // Không validate Gender
            if (gender == null || gender.trim().isEmpty()) {
                gender = currentUser.getGender();
            }
            
            // Nếu có lỗi, trả về form với lỗi
            if (!errors.isEmpty()) {
                for (Map.Entry<String, String> entry : errors.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                forwardError(userIdStr, fullName, phoneNumber, address, dateOfBirthStr, gender, request, response);
                return;
            }
            
            // Tạo đối tượng User
            model.User updatedUser = new model.User();
            updatedUser.setUserId(userId);
            updatedUser.setFullName(fullName);
            updatedUser.setPhoneNumber(phoneNumber);
            updatedUser.setAddress(address);
            updatedUser.setGender(gender);
            updatedUser.setDateOfBirth(dateOfBirth);
            
            // Cập nhật thông tin người dùng
            boolean success = userDAO.updateProfile(updatedUser);
            
            if (success) {
                request.setAttribute("message", "Cập nhật hồ sơ thành công!");
                model.User updatedUserInfo = userDAO.getUserById(userId);
                request.setAttribute("user", updatedUserInfo);
            } else {
                request.setAttribute("userIdError", "Cập nhật hồ sơ thất bại. Vui lòng thử lại!");
                request.setAttribute("user", updatedUser);
            }
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void forwardError(String userId, String fullName, String phoneNumber, String address, String dateOfBirth, String gender, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        model.User user = new model.User();
        user.setUserId(userId != null && !userId.isEmpty() ? Integer.parseInt(userId) : 0);
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setGender(gender);
        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                user.setDateOfBirth(sdf.parse(dateOfBirth));
            } catch (ParseException e) {
                // Không set dateOfBirth nếu parse lỗi
            }
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
    }
}