<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="helper.RoleHelper" %>
<%@ page import="model.UserModel" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	UserModel user = (UserModel) session.getAttribute("user");

	boolean isAdmin = RoleHelper.isAdmin(user);
	boolean isDepartmentManager = RoleHelper.isDepartmentManager(user);
	boolean isFinancialManager = RoleHelper.isFinancialManager(user);
	boolean isStaff = RoleHelper.isStaff(user);

	String sidebarRole = request.getParameter("sidebarRole");
	String activeMenu = request.getParameter("activeMenu");
	if (sidebarRole == null) sidebarRole = "staff";
	if (activeMenu == null) activeMenu = "";

	String sidebarTitle = "Financial Advisory";
	String sidebarIcon = "bi-wallet2";
	String sidebarColor = "#0D6EFD";
	String sidebarActiveColor = "#084298";

	if (isAdmin) {
		sidebarTitle = "System Admin";
		sidebarIcon = "bi-shield-lock";
		sidebarColor = "#950606";
		sidebarActiveColor = "#ff2c2c";
	} else if (isDepartmentManager) {
		sidebarTitle = "Department Manager";
		sidebarIcon = "bi-person-badge";
		sidebarColor = "#4338CA";
		sidebarActiveColor = "#312E81";
	} else if (isFinancialManager) {
		sidebarTitle = "Financial Manager";
		sidebarIcon = "bi-briefcase";
		sidebarColor = "#0F766E";
		sidebarActiveColor = "#198754";
	} else {
		sidebarRole = "Staff";
	}

	String contextPath = request.getContextPath();
	String activeClass = "nav-link active text-white rounded-3";
	String normalClass = "nav-link text-white rounded-3";
%>

<aside class="col-12 col-lg-2 text-white p-4" style="background-color: <%= sidebarColor %>;">
	<h4 class="fw-bold mb-4">
		<i class="bi <%= sidebarIcon %> me-2"></i> <%= sidebarTitle %>
	</h4>
	<div class="nav flex-column nav-pills gap-2">
		
		<c:if test="${isAdmin}">
			<a class="<%= "users".equals(activeMenu) ? activeClass : normalClass %>"
			   <%= "users".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
			   href="<%= contextPath %>/admin-user-list.jsp">
			    <i class="bi bi-people me-2"></i> User List
			</a>
		
			<a class="<%= "departments".equals(activeMenu) ? activeClass : normalClass %>"
			   <%= "departments".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
			   href="<%= contextPath %>/admin-department-list.jsp">
			    <i class="bi bi-building me-2"></i> Department List
			</a>
			
			<a class="<%= "budget".equals(activeMenu) ? activeClass : normalClass %>"
			   <%= "budget".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
			   href="<%= contextPath %>/departmentmanager-budget.jsp">
			    <i class="bi bi-wallet2 me-2"></i> Budget Management
			</a>
		</c:if>
		
		<c:if test="${!isAdmin}">
			<a class="<%= "dashboard".equals(activeMenu) ? activeClass : normalClass %>"
			   <%= "dashboard".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
			   href="<%= contextPath %>/dashboard.jsp">
			    <i class="bi bi-speedometer2 me-2"></i> Dashboard
			</a>
			
			<a class="<%= "transactions".equals(activeMenu) ? activeClass : normalClass %>"
			   <%= "transactions".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
			   href="<%= contextPath %>/TransactionController?action=list">
			    <i class="bi bi-cash-coin me-2"></i> Transactions
			</a>
			
			<c:if test="${isFinancialManager || isDepartmentManager }">
				<a class="<%= "categories".equals(activeMenu) ? activeClass : normalClass %>"
				   <%= "categories".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
				   href="<%= contextPath %>/departmentmanager-category-list.jsp">
				    <i class="bi bi-tags me-2"></i> Category
				</a>
				
				<a class="<%= "history".equals(activeMenu) ? activeClass : normalClass %>"
				   <%= "history".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
				   href="<%= contextPath %>/departmentmanager-history.jsp">
				    <i class="bi bi-receipt me-2"></i> History
				</a>
			</c:if>
			
			<a class="<%= "advisory".equals(activeMenu) ? activeClass : normalClass %>"
			   <%= "advisory".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
			   href="<%= contextPath %>/aiadvisory.jsp">
			    <i class="bi bi-robot me-2"></i> AI Advisory
			</a>
			
			<a class="<%= "settings".equals(activeMenu) ? activeClass : normalClass %>"
			   <%= "settings".equals(activeMenu) ? "style=\"background-color: " + sidebarActiveColor + ";\"" : "" %>
			   href="<%= contextPath %>/account-settings.jsp">
			    <i class="bi bi-gear me-2"></i> Account Settings
			</a>
		</c:if>
		
			<a class="nav-link text-white bg-danger rounded-3 mt-4 shadow-sm fw-bold"
			   href="<%= contextPath %>/login.jsp"
			   onclick="localStorage.removeItem('demoRole')">
			    <i class="bi bi-box-arrow-right me-2"></i> Logout
			</a>
	</div>
</aside>