package database;

import dao.BlogDAO;
import java.sql.SQLException;
import java.util.List;
import model.Blog;

/**
 * Test class to fetch recent blog posts
 */
public class TestGetRecentBlogs {
    
    public static void main(String[] args) {
        try {
            System.out.println("Starting recent blog tests...");
            
            // Test getting all recent blogs
            testGetRecentBlogs();
            
            // Test getting recent blogs by category
            testGetRecentBlogsByCategory();
            
            System.out.println("Tests completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error executing tests: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Test fetching recent blogs across all categories
     */
    private static void testGetRecentBlogs() {
        System.out.println("\n===== Testing getRecentBlogs() =====");
        try {
            BlogDAO blogDAO = new BlogDAO();
            List<Blog> recentBlogs = blogDAO.getRecentBlogs(5); // Get 5 most recent blogs
            
            if (recentBlogs == null || recentBlogs.isEmpty()) {
                System.out.println("No recent blogs found!");
                return;
            }
            
            System.out.println("Found " + recentBlogs.size() + " recent blogs:");
            for (Blog blog : recentBlogs) {
                printBlogDetails(blog);
            }
        } catch (Exception e) {
            System.err.println("Error in testGetRecentBlogs: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Test fetching recent blogs from a specific category
     */
    private static void testGetRecentBlogsByCategory() {
        System.out.println("\n===== Testing getRecentBlogsByCategoryId() =====");
        try {
            BlogDAO blogDAO = new BlogDAO();
            
            // Get blogs from all available categories to test
            List<Blog> allBlogs = blogDAO.getAllBlogs();
            if (allBlogs == null || allBlogs.isEmpty()) {
                System.out.println("No blogs found to test categories!");
                return;
            }
            
            // Get a sample category ID from the first blog
            int sampleCategoryId = allBlogs.get(0).getCategoryBlogId();
            System.out.println("Testing with category ID: " + sampleCategoryId);
            
            // Get recent blogs from this category
            List<Blog> categoryBlogs = blogDAO.getRecentBlogsByCategoryId(sampleCategoryId, 3);
            
            if (categoryBlogs == null || categoryBlogs.isEmpty()) {
                System.out.println("No recent blogs found for category ID " + sampleCategoryId);
                return;
            }
            
            System.out.println("Found " + categoryBlogs.size() + " recent blogs in category " + sampleCategoryId + ":");
            for (Blog blog : categoryBlogs) {
                printBlogDetails(blog);
            }
        } catch (Exception e) {
            System.err.println("Error in testGetRecentBlogsByCategory: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Helper method to print blog details
     */
    private static void printBlogDetails(Blog blog) {
        System.out.println("-----------------------------------------");
        System.out.println("Blog ID: " + blog.getBlogId());
        System.out.println("Title: " + blog.getBlogTitle());
        System.out.println("Category: " + blog.getCategoryBlogName() + " (ID: " + blog.getCategoryBlogId() + ")");
        System.out.println("Created Date: " + blog.getBlogCreatedDate());
        System.out.println("Image: " + blog.getBlogImage());
        // Print a short preview of the description
        String description = blog.getBlogDescription();
        if (description != null && description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }
        System.out.println("Description: " + description);
        System.out.println("-----------------------------------------");
    }
} 