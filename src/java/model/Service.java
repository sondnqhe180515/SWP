package model;

/**
 * Represents a dental service in the system
 */
public class Service {
    private int id;
    private String name;
    private String description;
    private String image;
    private String status;
    private double price;
    
    public Service() {
    }
    
    public Service(int id, String name, String description, String image, String status, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.status = status;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", name=" + name + ", status=" + status + '}';
    }
}        