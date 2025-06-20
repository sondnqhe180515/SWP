package database;

import dao.BlogDAO;
import model.Blog;
import java.util.List;

public class TestGetBlog {
    public static void main(String[] args) {
        try {
            BlogDAO blogDAO = new BlogDAO();
            List<Blog> blogs = blogDAO.getAllBlogs();
            System.out.println("So blog: " + blogs.size());
            for (Blog blog : blogs) {
                System.out.println(blog.getBlogId() + " | " + blog.getBlogTitle() + " | " + blog.getBlogDescription() + " | " + blog.getBlogImage() + " | " + blog.getBlogCreatedDate() + " | " + blog.getCategoryBlogName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 