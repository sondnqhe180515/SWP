package model;

public class CategoryBlog {
    private int categoryBlogId;
    private String categoryBlogName;
    private String categoryBlogStatus;
    
    // Default constructor
    public CategoryBlog() {
    }
    
    // Full constructor
    public CategoryBlog(int categoryBlogId, String categoryBlogName, String categoryBlogStatus) {
        this.categoryBlogId = categoryBlogId;
        this.categoryBlogName = categoryBlogName;
        this.categoryBlogStatus = categoryBlogStatus;
    }
    
    // Getters and Setters
    public int getCategoryBlogId() {
        return categoryBlogId;
    }

    public void setCategoryBlogId(int categoryBlogId) {
        this.categoryBlogId = categoryBlogId;
    }

    public String getCategoryBlogName() {
        return categoryBlogName;
    }

    public void setCategoryBlogName(String categoryBlogName) {
        this.categoryBlogName = categoryBlogName;
    }

    public String getCategoryBlogStatus() {
        return categoryBlogStatus;
    }

    public void setCategoryBlogStatus(String categoryBlogStatus) {
        this.categoryBlogStatus = categoryBlogStatus;
    }
} 