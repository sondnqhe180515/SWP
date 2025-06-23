<%@ page import="java.util.List" %>
<%@ page import="model.Appointments" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doctor Dashboard</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/doctor-sidebar.css">
    <style>
        body { 
            margin: 0; 
            padding: 0; 
            font-family: Arial, sans-serif; 
        }        .main-content { 
            margin-left: 250px;
            padding: 20px; 
            background: #f5f5f5; 
            min-height: 100vh; 
        }
        .card { 
            background: white; 
            padding: 20px; 
            margin: 10px 0; 
            border-radius: 8px; 
            box-shadow: 0 2px 4px rgba(0,0,0,0.1); 
        }
        .stats { 
            display: flex; 
            gap: 20px; 
            margin: 20px 0; 
        }        .stat { 
            background: white; 
            padding: 20px; 
            border-radius: 12px; 
            text-align: center; 
            flex: 1; 
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            transition: transform 0.2s;
            position: relative;
        }
        .stat:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0,0,0,0.15);
        }
        .stat-circle {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            margin: 0 auto 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            font-weight: bold;
            color: white;
        }
        .stat-pending .stat-circle { background: linear-gradient(135deg, #FFA726, #FF7043); }
        .stat-confirmed .stat-circle { background: linear-gradient(135deg, #66BB6A, #43A047); }
        .stat-completed .stat-circle { background: linear-gradient(135deg, #42A5F5, #1E88E5); }
        .stat-cancelled .stat-circle { background: linear-gradient(135deg, #EF5350, #E53935); }        .stat-label {
            font-size: 14px;
            color: #666;
            font-weight: 500;
        }
        .chart-container {
            background: white;
            padding: 20px;
            margin: 10px 0;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .bar-chart {
            display: flex;
            align-items: end;
            gap: 20px;
            height: 200px;
            margin: 20px 0;
        }
        .bar {
            flex: 1;
            background: linear-gradient(to top, #0078B4, #00A8CC);
            border-radius: 4px 4px 0 0;
            position: relative;
            min-height: 20px;
            transition: all 0.3s ease;
        }
        .bar:hover {
            transform: scale(1.05);
        }
        .bar-pending { background: linear-gradient(to top, #FFA726, #FF7043); }
        .bar-confirmed { background: linear-gradient(to top, #66BB6A, #43A047); }
        .bar-completed { background: linear-gradient(to top, #42A5F5, #1E88E5); }
        .bar-cancelled { background: linear-gradient(to top, #EF5350, #E53935); }
        .bar-label {
            position: absolute;
            bottom: -25px;
            left: 50%;
            transform: translateX(-50%);
            font-size: 12px;
            color: #666;
            text-align: center;
            width: 100%;
        }
        .bar-value {
            position: absolute;
            top: -25px;
            left: 50%;
            transform: translateX(-50%);
            font-weight: bold;
            color: #333;
            font-size: 14px;
        }
        .progress-ring {
            width: 80px;
            height: 80px;
            margin: 0 auto 10px;
        }
        .progress-ring-circle {
            stroke: #e6e6e6;
            stroke-width: 4;
            fill: transparent;
            r: 36;
            cx: 40;
            cy: 40;
        }
        .progress-ring-progress {
            stroke-width: 4;
            fill: transparent;
            r: 36;
            cx: 40;
            cy: 40;
            stroke-dasharray: 226.19;
            stroke-dashoffset: 226.19;
            transition: stroke-dashoffset 0.3s;
        }
        .progress-pending { stroke: #FFA726; }
        .progress-confirmed { stroke: #66BB6A; }
        .progress-completed { stroke: #42A5F5; }
        .progress-cancelled { stroke: #EF5350; }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }        th {
            background-color: #f8f9fa;
        }
        @media (max-width: 768px) { 
            .main-content { margin-left: 0; } 
            .stats { flex-direction: column; }
        }
    </style>
</head>
<body>
    <%@ include file="../includes/doctor-sidebar.jsp" %>
    
    <div class="main-content">
        <h1 style="color: #0078B4;">Doctor Dashboard</h1>
          <%
            User doctorUser = (User) request.getAttribute("user");
            List<Appointments> appointments = (List<Appointments>) request.getAttribute("appointments");
            
            int pendingCount = 0;
            int confirmedCount = 0;
            int completedCount = 0;
            int cancelledCount = 0;
              if (appointments != null) {
                for (Appointments appt : appointments) {
                    String status = appt.getStatus();
                    if ("Đang chờ".equals(status)) {
                        pendingCount++;
                    } else if ("Xác nhận".equals(status)) {
                        confirmedCount++;
                    } else if ("Hoàn thành".equals(status)) {
                        completedCount++;
                    } else if ("Đã hủy".equals(status)) {
                        cancelledCount++;
                    }
                }
            }
        %>
          <div class="card">
            <h3>Doctor Information</h3>
            <% if (doctorUser != null) { %>
                <p><strong>Name:</strong> <%= doctorUser.getFullName() %></p>
                <p><strong>ID:</strong> <%= doctorUser.getUserID() %></p>
                <p><strong>Email:</strong> <%= doctorUser.getEmail() %></p>
            <% } %>
            <p><strong>Date:</strong> <%= new java.util.Date() %></p>
        </div>
          <div class="stats">
            <div class="stat stat-pending">
                <svg class="progress-ring">
                    <circle class="progress-ring-circle"></circle>
                    <circle class="progress-ring-progress progress-pending" 
                            style="stroke-dashoffset: <%= 226.19 - (pendingCount * 226.19 / Math.max(1, pendingCount + confirmedCount + completedCount + cancelledCount)) %>"></circle>
                </svg>
                <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size: 20px; font-weight: bold; color: #FFA726;">
                    <%= pendingCount %>
                </div>
                <div class="stat-label">Đang chờ</div>
            </div>
            <div class="stat stat-confirmed">
                <svg class="progress-ring">
                    <circle class="progress-ring-circle"></circle>
                    <circle class="progress-ring-progress progress-confirmed" 
                            style="stroke-dashoffset: <%= 226.19 - (confirmedCount * 226.19 / Math.max(1, pendingCount + confirmedCount + completedCount + cancelledCount)) %>"></circle>
                </svg>
                <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size: 20px; font-weight: bold; color: #66BB6A;">
                    <%= confirmedCount %>
                </div>
                <div class="stat-label">Đã xác nhận</div>
            </div>
            <div class="stat stat-completed">
                <svg class="progress-ring">
                    <circle class="progress-ring-circle"></circle>
                    <circle class="progress-ring-progress progress-completed" 
                            style="stroke-dashoffset: <%= 226.19 - (completedCount * 226.19 / Math.max(1, pendingCount + confirmedCount + completedCount + cancelledCount)) %>"></circle>
                </svg>
                <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size: 20px; font-weight: bold; color: #42A5F5;">
                    <%= completedCount %>
                </div>
                <div class="stat-label">Hoàn thành</div>
            </div>
            <div class="stat stat-cancelled">
                <svg class="progress-ring">
                    <circle class="progress-ring-circle"></circle>
                    <circle class="progress-ring-progress progress-cancelled" 
                            style="stroke-dashoffset: <%= 226.19 - (cancelledCount * 226.19 / Math.max(1, pendingCount + confirmedCount + completedCount + cancelledCount)) %>"></circle>
                </svg>
                <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size: 20px; font-weight: bold; color: #EF5350;">
                    <%= cancelledCount %>
                </div>
                <div class="stat-label">Đã hủy</div>
            </div>
        </div>
        
        <div class="chart-container">
            <h3>Appointment Statistics - Bar Chart</h3>
            <div class="bar-chart">
                <%
                    int maxCount = Math.max(Math.max(pendingCount, confirmedCount), Math.max(completedCount, cancelledCount));
                    if (maxCount == 0) maxCount = 1; // Prevent division by zero
                %>
                <div class="bar bar-pending" style="height: <%= (pendingCount * 150 / maxCount) + 20 %>px;">
                    <div class="bar-value"><%= pendingCount %></div>
                    <div class="bar-label">Đang chờ</div>
                </div>
                <div class="bar bar-confirmed" style="height: <%= (confirmedCount * 150 / maxCount) + 20 %>px;">
                    <div class="bar-value"><%= confirmedCount %></div>
                    <div class="bar-label">Đã xác nhận</div>
                </div>
                <div class="bar bar-completed" style="height: <%= (completedCount * 150 / maxCount) + 20 %>px;">
                    <div class="bar-value"><%= completedCount %></div>
                    <div class="bar-label">Hoàn thành</div>
                </div>
                <div class="bar bar-cancelled" style="height: <%= (cancelledCount * 150 / maxCount) + 20 %>px;">
                    <div class="bar-value"><%= cancelledCount %></div>
                    <div class="bar-label">Đã hủy</div>
                </div>
            </div>
        </div>
        
        <div class="card">
            <h3>Recent Appointments</h3>
            <% if (appointments != null && !appointments.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer ID</th>
                        <th>Service ID</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Note</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int count = Math.min(10, appointments.size());
                        for (int i = 0; i < count; i++) {
                            Appointments appt = appointments.get(i);
                    %>
                    <tr>
                        <td><%= appt.getAppointmentID() %></td>
                        <td><%= appt.getCustomerID() %></td>
                        <td><%= appt.getServiceID() %></td>
                        <td><%= appt.getAppointmentDate() %></td>
                        <td><%= appt.getStatus() %></td>
                        <td><%= appt.getNote() != null ? appt.getNote() : "" %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } else { %>
                <p>No appointments found</p>
            <% } %>
        </div>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="card" style="color: red;">
                <h3>Error</h3>
                <p><%= request.getAttribute("error") %></p>
            </div>
        <% } %>
    </div>
</body>
</html>