package model;

import java.sql.Date;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogDescription;
    private String blogImage;
    private Date blogCreatedDate;
    private int categoryBlogId;
    private int userId;  // Maps to Blog_CreatedBy in DB
    private String categoryBlogName;
    private String blogContent;
    private String blogStatus;
    
    // Default constructor
    public Blog() {
    }
    
    // Full constructor
    public Blog(int blogId, String blogTitle, String blogDescription, String blogImage, Date blogCreatedDate, int categoryBlogId, int userId, String categoryBlogName) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogImage = blogImage;
        this.blogCreatedDate = blogCreatedDate;
        this.categoryBlogId = categoryBlogId;
        this.userId = userId;
        this.categoryBlogName = categoryBlogName;
    }
    
    // Getters and Setters
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public Date getBlogCreatedDate() {
        return blogCreatedDate;
    }

    public void setBlogCreatedDate(Date blogCreatedDate) {
        this.blogCreatedDate = blogCreatedDate;
    }

    public int getCategoryBlogId() {
        return categoryBlogId;
    }

    public void setCategoryBlogId(int categoryBlogId) {
        this.categoryBlogId = categoryBlogId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategoryBlogName() {
        return categoryBlogName;
    }

    public void setCategoryBlogName(String categoryBlogName) {
        this.categoryBlogName = categoryBlogName;
    }
    
    public String getBlogContent() {
        return blogContent;
    }
    
    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }
    
    public String getBlogStatus() {
        return blogStatus;
    }
    
    public void setBlogStatus(String blogStatus) {
        this.blogStatus = blogStatus;
    }
    
    // For compatibility with admin functions
    public int getBlogCreatedBy() {
        return userId;
    }
    
    public void setBlogCreatedBy(int blogCreatedBy) {
        this.userId = blogCreatedBy;
    }
} 