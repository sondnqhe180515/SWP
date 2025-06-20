package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Slot;

/**
 * Data Access Object for Slot entity
 */
public class SlotDAO extends DBContext {
    
    /**
     * Get all available time slots
     * @return List of Slot objects
     * @throws SQLException If a database error occurs
     */
    public List<Slot> getAllSlots() throws SQLException {
        List<Slot> slots = new ArrayList<>();
        String sql = "SELECT Slot_Id, Start_Time, End_Time FROM Slot ORDER BY Start_Time";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Slot slot = new Slot();
                slot.setSlotId(rs.getInt("Slot_Id"));
                slot.setStartTime(rs.getTime("Start_Time"));
                slot.setEndTime(rs.getTime("End_Time"));
                slots.add(slot);
            }
        }
        
        return slots;
    }
    
    /**
     * Get a slot by its ID
     * @param slotId The ID of the slot to retrieve
     * @return Slot object or null if not found
     * @throws SQLException If a database error occurs
     */
    public Slot getSlotById(int slotId) throws SQLException {
        String sql = "SELECT Slot_Id, Start_Time, End_Time FROM Slot WHERE Slot_Id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, slotId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Slot slot = new Slot();
                    slot.setSlotId(rs.getInt("Slot_Id"));
                    slot.setStartTime(rs.getTime("Start_Time"));
                    slot.setEndTime(rs.getTime("End_Time"));
                    return slot;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Get available slots for a specific date and doctor
     * @param doctorId The ID of the doctor
     * @param date The date in SQL Date format
     * @return List of available Slot objects
     * @throws SQLException If a database error occurs
     */
    public List<Slot> getAvailableSlots(int doctorId, java.sql.Date date) throws SQLException {
        List<Slot> availableSlots = new ArrayList<>();
        
        String sql = "SELECT s.Slot_Id, s.Start_Time, s.End_Time FROM Slot s " +
                     "WHERE s.Slot_Id NOT IN (" +
                     "  SELECT ds.Slot_Id FROM Doctor_schedule ds " +
                     "  WHERE ds.Doctor_Id = ? AND ds.Schedule_WorkDate = ? AND ds.Schedule_Status = 'Booked'" +
                     ") ORDER BY s.Start_Time";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            stmt.setDate(2, date);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Slot slot = new Slot();
                    slot.setSlotId(rs.getInt("Slot_Id"));
                    slot.setStartTime(rs.getTime("Start_Time"));
                    slot.setEndTime(rs.getTime("End_Time"));
                    availableSlots.add(slot);
                }
            }
        }
        
        return availableSlots;
    }
    
    /**
     * Find the closest slot to a requested time
     * @param requestedTime The requested time in SQL Time format
     * @return The closest available Slot or null if none found
     * @throws SQLException If a database error occurs
     */
    public Slot findClosestSlot(java.sql.Time requestedTime) throws SQLException {
        String sql = "SELECT TOP 1 Slot_Id, Start_Time, End_Time FROM Slot " +
                     "WHERE (CAST(Start_Time AS TIME) <= CAST(? AS TIME) AND CAST(End_Time AS TIME) >= CAST(? AS TIME)) OR " +
                     "ABS(DATEDIFF(MINUTE, CAST(Start_Time AS TIME), CAST(? AS TIME))) <= 30 " +
                     "ORDER BY ABS(DATEDIFF(MINUTE, CAST(Start_Time AS TIME), CAST(? AS TIME)))";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTime(1, requestedTime);
            stmt.setTime(2, requestedTime);
            stmt.setTime(3, requestedTime);
            stmt.setTime(4, requestedTime);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Slot slot = new Slot();
                    slot.setSlotId(rs.getInt("Slot_Id"));
                    slot.setStartTime(rs.getTime("Start_Time"));
                    slot.setEndTime(rs.getTime("End_Time"));
                    return slot;
                }
            }
        }
        
        return null;
    }
} 