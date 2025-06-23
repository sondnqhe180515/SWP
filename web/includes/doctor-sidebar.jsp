<%@ page import="model.User" %>
<%
    User currentUser = (User) session.getAttribute("user");
    String currentPage = request.getRequestURI();
%>

<!-- Sidebar -->
<div class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <h3>Doctor Panel</h3>
        <% if (currentUser != null) { %>
            <p style="font-size: 14px; margin: 5px 0 0 0; opacity: 0.8;">
                <%= currentUser.getFullName() %>
            </p>
        <% } %>
    </div>
    
    <ul class="sidebar-nav">
        <li>
            <a href="<%= request.getContextPath() %>/doctor/dashboard" 
               class="<%= currentPage.contains("dashboard") ? "active" : "" %>">
                <i></i> Dashboard
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/viewMedicalRecords" 
               class="<%= currentPage.contains("viewMedicalRecord") ? "active" : "" %>">
                <i></i> Medical Records
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/viewFeedback" 
               class="<%= currentPage.contains("viewFeedback") ? "active" : "" %>">
                <i></i> Feedback
            </a>
        </li>
    </ul>
    
    <div class="sidebar-footer">
        <a href="<%= request.getContextPath() %>/logout" class="btn-logout">
            <i></i> Logout
        </a>
    </div>
</div>

<!-- Toggle button for mobile -->
<button class="sidebar-toggle" onclick="toggleSidebar()">☰</button>

<script>
function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('show');
}

// Close sidebar when clicking outside on mobile
window.addEventListener('click', function(e) {
    const sidebar = document.getElementById('sidebar');
    const toggle = document.querySelector('.sidebar-toggle');
    
    if (window.innerWidth <= 768 && !sidebar.contains(e.target) && !toggle.contains(e.target)) {
        sidebar.classList.remove('show');
    }
});
</script>
