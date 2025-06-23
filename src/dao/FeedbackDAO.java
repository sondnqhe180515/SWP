package dao;

import dal.DBContext;
import java.sql.*;
import java.util.*;
import model.Feedbacks;

public class FeedbackDAO extends DBContext {

    public List<Feedbacks> getFeedbacksForDoctor(int doctorId) {
        List<Feedbacks> list = new ArrayList<>();

        String sql = "SELECT f.FeedbackID, f.CustomerID, u.FullName AS CustomerName, "
                + "f.Rating, f.Comment, f.CreatedAt "
                + "FROM Feedbacks f "
                + "JOIN Users u ON f.CustomerID = u.UserID "
                + "WHERE f.TargetType = N'Bác sĩ' AND f.TargetID = ? "
                + "ORDER BY f.CreatedAt DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Feedbacks f = new Feedbacks();
                f.setFeedbackID(rs.getInt("FeedbackID"));
                f.setCustomerID(rs.getInt("CustomerID"));
                f.setCustomerName(rs.getString("CustomerName"));
                f.setRating(rs.getInt("Rating"));
                f.setComment(rs.getString("Comment"));
                f.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteFeedback(int feedbackId) {
        String sql = "DELETE FROM Feedbacks WHERE FeedbackID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedbackId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
