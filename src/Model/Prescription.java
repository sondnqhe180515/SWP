package Model;

public class Prescription {
    private int prescriptionID;
    private int recordID;
    private String medicineName;
    private int quantity;
    private String instructions;

    // ✅ Constructor mặc định (rỗng)
    public Prescription() {
    }

    // ✅ Constructor đầy đủ tham số
    public Prescription(int prescriptionID, int recordID, String medicineName, int quantity, String instructions) {
        this.prescriptionID = prescriptionID;
        this.recordID = recordID;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.instructions = instructions;
    }

    // ✅ Getter và Setter
    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
