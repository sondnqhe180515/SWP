package Model;

/**
 *
 * @author An
 */
public class Account {
    private int userId;
    private String fullName;
    private String email;
    private String passwordHash; // Thêm trường này để khớp với CSDL
    private String phoneNumber;
    private String address;
    private int roleId; // Thêm trường RoleID để khớp với CSDL
    private boolean status;
    private String roleName;

    // Constructor mặc định
    public Account() {
    }

    // Constructor đầy đủ (có thể không cần passwordHash khi hiển thị)
    public Account(int userId, String fullName, String email, String passwordHash, String phoneNumber, String address, int roleId, boolean status, String roleName) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roleId = roleId;
        this.status = status;
        this.roleName = roleName;
    }

    // Constructor cho việc lấy dữ liệu hiển thị (không cần passwordHash)
    public Account(int userId, String fullName, String email, String phoneNumber, String address, int roleId, boolean status, String roleName) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roleId = roleId;
        this.status = status;
        this.roleName = roleName;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    @Override
    public String toString() {
        return "Account{" + "userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address + ", status=" + status + ", roleName=" + roleName + '}';
    }
    
}
