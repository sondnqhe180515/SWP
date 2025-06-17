package DAO;

import Model.Prescription;
import dal.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Lấy danh sách tất cả đơn thuốc
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> list = new ArrayList<>();
        String query = "SELECT * FROM Prescriptions";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Prescription(
                        rs.getInt("PrescriptionID"),
                        rs.getInt("RecordID"),
                        rs.getString("MedicineName"),
                        rs.getInt("Quantity"),
                        rs.getString("Instructions")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy 1 đơn thuốc theo PrescriptionID
    public Prescription getPrescriptionByID(int prescriptionID) {
        String query = "SELECT * FROM Prescriptions WHERE PrescriptionID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, prescriptionID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Prescription(
                        rs.getInt("PrescriptionID"),
                        rs.getInt("RecordID"),
                        rs.getString("MedicineName"),
                        rs.getInt("Quantity"),
                        rs.getString("Instructions")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm đơn thuốc mới
    public void addPrescription(Prescription p) {
        String sql = "INSERT INTO Prescriptions (RecordID, MedicineName, Quantity, Instructions) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getRecordID());
            ps.setString(2, p.getMedicineName());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getInstructions());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật đơn thuốc
    public void updatePrescription(Prescription p) {
        String sql = "UPDATE Prescriptions SET RecordID=?, MedicineName=?, Quantity=?, Instructions=? WHERE PrescriptionID=?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getRecordID());
            ps.setString(2, p.getMedicineName());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getInstructions());
            ps.setInt(5, p.getPrescriptionID());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá đơn thuốc
    public void deletePrescription(int prescriptionID) {
        String sql = "DELETE FROM Prescriptions WHERE PrescriptionID=?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prescriptionID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
