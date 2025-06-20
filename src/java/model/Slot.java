package model;

import java.sql.Time;

/**
 * Represents a time slot for appointments
 */
public class Slot {
    private int slotId;
    private Time startTime;
    private Time endTime;
    
    public Slot() {
    }
    
    public Slot(int slotId, Time startTime, Time endTime) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public int getSlotId() {
        return slotId;
    }
    
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    public Time getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
    
    /**
     * Format the time slot for display
     * @return Formatted time string (e.g., "08:00 - 09:00")
     */
    public String getFormattedTimeSlot() {
        String start = startTime.toString().substring(0, 5);
        String end = endTime.toString().substring(0, 5);
        return start + " - " + end;
    }
} 