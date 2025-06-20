package model;

import java.sql.Date;

public class Comment {
    private int commentId;
    private int blogId;
    private String commentContent;
    private String commentStatus;
    private Date commentCreatedDate;
    private int commentCreatedBy;
    private String blogTitle;
    
    // Default constructor
    public Comment() {
    }
    
    // Full constructor
    public Comment(int commentId, int blogId, String commentContent, String commentStatus, 
                  Date commentCreatedDate, int commentCreatedBy) {
        this.commentId = commentId;
        this.blogId = blogId;
        this.commentContent = commentContent;
        this.commentStatus = commentStatus;
        this.commentCreatedDate = commentCreatedDate;
        this.commentCreatedBy = commentCreatedBy;
    }
    
    // Getters and Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Date getCommentCreatedDate() {
        return commentCreatedDate;
    }

    public void setCommentCreatedDate(Date commentCreatedDate) {
        this.commentCreatedDate = commentCreatedDate;
    }

    public int getCommentCreatedBy() {
        return commentCreatedBy;
    }

    public void setCommentCreatedBy(int commentCreatedBy) {
        this.commentCreatedBy = commentCreatedBy;
    }
    
    public String getBlogTitle() {
        return blogTitle;
    }
    
    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }
} 