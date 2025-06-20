
package database;

public class TestConnection {
    public static void main(String[] args) {
        try {
            DBContext db = new DBContext();
            if (db.getConnection() != null) {
                System.out.println("Kết nối database thành công!");
                System.out.println("Database name: SWP391_G5");
            }
        } catch (Exception e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 