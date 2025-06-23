package DAO;

import Model.Prescription;
import Model.prescriptionUser;
import dal.DBContext;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {
    private final Connection conn;

    public PrescriptionDAO(Connection conn) {
        this.conn = conn;
    }

    public void createPrescription(Prescription prescription) throws SQLException {
        String sql = "INSERT INTO Prescriptions (RecordID, MedicineName, Quantity, Instructions) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, prescription.getRecordID());
        ps.setString(2, prescription.getMedicineName());
        ps.setString(3, prescription.getDose());
        ps.setString(4, prescription.getNote());
        ps.executeUpdate();
    }

    public List<Prescription> getPrescriptionsByRecordID(int recordID) throws SQLException {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM Prescriptions WHERE RecordID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, recordID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Prescription p = new Prescription();
            p.setPrescriptionID(rs.getInt("PrescriptionID"));
            p.setRecordID(rs.getInt("RecordID"));
            p.setMedicineName(rs.getString("MedicineName"));
            p.setNote(rs.getString("Instructions"));
            list.add(p);
        }
        return list;
    }

    public void updatePrescription(Prescription p) throws SQLException {
        String sql = "UPDATE Prescriptions SET MedicineName=?, Instructions=? WHERE PrescriptionID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, p.getMedicineName());
        ps.setString(2, p.getNote());
        ps.setInt(3, p.getPrescriptionID());
        ps.executeUpdate();
    }

    public void deletePrescription(int id) throws SQLException {
        String sql = "DELETE FROM Prescriptions WHERE PrescriptionID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Prescription getPrescriptionByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Prescription> getAllPrescriptions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
