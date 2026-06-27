<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	String role = request.getParameter("role");
	if (role == null || role.trim().isEmpty()) {
		role = "staff";
	}

	String roleName = "Staff";
	String sidebarTitle = "Financial Advisory";
	String sidebarIcon = "bi-wallet2";
	String sidebarColor = "#0D6EFD";
	String activeStyle = "background-color: #084298;";
	String dashboardHref = "dashboard.jsp?role=staff";
	String profileName = "Siti Aminah";
	String profileSubtitle = "Department Staff";
	String profileBadge = "Staff Profile";
	String email = "siti@example.com";
	String department = "Operations";
	String avatarIcon = "bi-person";
	String buttonClass = "btn-primary";

	if ("admin".equals(role)) {
		roleName = "System Admin";
		sidebarTitle = "System Admin";
		sidebarIcon = "bi-shield-lock";
		sidebarColor = "#950606";
		activeStyle = "background-color: #ff2c2c;";
		dashboardHref = "admin-user-list.jsp";
		profileName = "System Admin";
		profileSubtitle = "Administration Account";
		profileBadge = "Admin Profile";
		email = "admin@example.com";
		department = "Not Applicable";
		avatarIcon = "bi-person-gear";
		buttonClass = "btn-danger";
	} else if ("departmentmanager".equals(role)) {
		roleName = "Department Manager";
		sidebarTitle = "Department Manager";
		sidebarIcon = "bi-person-badge";
		sidebarColor = "#4338CA";
		activeStyle = "background-color: #312E81;";
		dashboardHref = "dashboard.jsp?role=departmentmanager";
		profileName = "Sarah Ahmad";
		profileSubtitle = "Sales Department Manager";
		profileBadge = "Department Profile";
		email = "sarah@example.com";
		department = "Sales";
		avatarIcon = "bi-person-badge";
	} else if ("financialmanager".equals(role)) {
		roleName = "Financial Manager";
		sidebarTitle = "Financial Manager";
		sidebarIcon = "bi-briefcase";
		sidebarColor = "#0F766E";
		activeStyle = "background-color: #198754;";
		dashboardHref = "dashboard.jsp?role=financialmanager";
		profileName = "Abu Bakar";
		profileSubtitle = "Financial Manager";
		profileBadge = "Finance Profile";
		email = "abu@example.com";
		department = "Finance";
		avatarIcon = "bi-person-check";
		buttonClass = "btn-success";
	} else {
		role = "staff";
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>AI Financial Advisory System</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="css/chatbot-widget.css?v=2">
</head>

<body class="bg-light">
	<div class="container-fluid">
		<div class="row min-vh-100">
			<aside class="col-12 col-lg-2 text-white p-4"
				style="background-color: <%= sidebarColor %>;">
				<h4 class="fw-bold mb-4">
					<i class="bi <%= sidebarIcon %> me-2"></i> <%= sidebarTitle %>
				</h4>

				<div class="nav flex-column nav-pills gap-2">
					<% if (!"admin".equals(role)) { %>
					<a class="nav-link text-white rounded-3" href="<%= dashboardHref %>">
						<i class="bi bi-speedometer2 me-2"></i> Dashboard
					</a>
					<% } %>

					<% if ("admin".equals(role)) { %>
					<a class="nav-link text-white rounded-3" href="admin-user-list.jsp">
						<i class="bi bi-people me-2"></i> User List
					</a>
					<a class="nav-link text-white rounded-3" href="admin-department-list.jsp">
						<i class="bi bi-building me-2"></i> Department List
					</a>
					<% } %>

					<% if ("staff".equals(role)) { %>
					<a class="nav-link text-white rounded-3" href="staff-transaction.jsp">
						<i class="bi bi-cash-coin me-2"></i> Transactions
					</a>
					<a class="nav-link text-white rounded-3" href="aiadvisory.jsp?role=staff">
						<i class="bi bi-robot me-2"></i> AI Advisory
					</a>
					<% } %>

					<% if ("departmentmanager".equals(role)) { %>
					<a class="nav-link text-white rounded-3" href="departmentmanager-budget.jsp">
						<i class="bi bi-wallet2 me-2"></i> Budget Management
					</a>
					<a class="nav-link text-white rounded-3" href="departmentmanager-history.jsp">
						<i class="bi bi-receipt me-2"></i> Transactions
					</a>
					<a class="nav-link text-white rounded-3" href="aiadvisory.jsp?role=departmentmanager">
						<i class="bi bi-robot me-2"></i> AI Advisory
					</a>
					<% } %>

					<% if ("financialmanager".equals(role)) { %>
					<a class="nav-link text-white rounded-3" href="aiadvisory.jsp?role=financialmanager">
						<i class="bi bi-robot me-2"></i> AI Advisory
					</a>
					<% } %>

					<a class="nav-link active text-white rounded-3"
						style="<%= activeStyle %>" href="account-settings.jsp?role=<%= role %>">
						<i class="bi bi-gear me-2"></i> Account Settings
					</a>

					<a class="nav-link text-white bg-danger rounded-3 mt-4 shadow-sm fw-bold" href="index.jsp">
						<i class="bi bi-box-arrow-right me-2"></i> <b>Logout</b>
					</a>
				</div>
			</aside>

			<main class="col-12 col-lg-10 p-4">
				<div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
					<div>
						<h1 class="fw-bold mb-1">Account Settings</h1>
						<p class="text-secondary mb-0">
							Manage your profile, contact details, password, and notification preferences.
						</p>
					</div>

					<div class="card border-0 shadow-sm rounded-4 mt-3 mt-md-0 role-welcome-card">
						<div class="card-body py-2 px-3">
							<span class="text-secondary">Welcome, </span>
							<strong><%= roleName %></strong>
						</div>
					</div>
				</div>

				<section class="row g-4">
					<div class="col-lg-4">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4 text-center">
								<div class="text-white rounded-circle d-inline-flex align-items-center justify-content-center mb-3"
									style="width: 92px; height: 92px; background-color: <%= sidebarColor %>;">
									<i class="bi <%= avatarIcon %> fs-1"></i>
								</div>
								<h5 class="fw-bold mb-1"><%= profileName %></h5>
								<p class="text-secondary mb-3"><%= profileSubtitle %></p>
							</div>
						</div>
					</div>

					<div class="col-lg-8">
						<form action="#" method="post">
							<div class="card border-0 shadow-sm rounded-4 mb-4">
								<div class="card-body p-4">
									<div class="d-flex justify-content-between align-items-center mb-3">
										<h5 class="fw-bold mb-0">
											<i class="bi bi-person-lines-fill me-2"></i> Profile Details
										</h5>
									</div>

									<div class="row g-3">
										<div class="col-md-6">
											<label class="form-label">Full Name</label>
											<input type="text" class="form-control rounded-3"
												name="fullName" value="<%= profileName %>">
										</div>
										<div class="col-md-6">
											<label class="form-label">Email Address</label>
											<input type="email" class="form-control rounded-3"
												name="email" value="<%= email %>">
										</div>
										<div class="col-md-6">
											<label class="form-label">Role</label>
											<input type="text" class="form-control rounded-3"
												value="<%= roleName %>" readonly>
										</div>
										<div class="col-md-6">
											<label class="form-label">Department</label>
											<input type="text" class="form-control rounded-3"
												value="<%= department %>" readonly>
										</div>
										<div class="col-md-6">
											<label class="form-label">Phone Number</label>
											<input type="text" class="form-control rounded-3"
												name="phone" placeholder="Example: 012-3456789">
										</div>
										<div class="col-md-6">
											<label class="form-label">Account Status</label>
											<input type="text" class="form-control rounded-3"
												value="Active" readonly>
										</div>
									</div>
								</div>
							</div>

							<div class="card border-0 shadow-sm rounded-4">
								<div class="card-body p-4">
									<h5 class="fw-bold mb-3">
										<i class="bi bi-shield-lock me-2"></i> Security and Preferences
									</h5>
									<div class="row g-3">
										<div class="col-md-6">
											<label class="form-label">New Password</label>
											<input type="password" class="form-control rounded-3"
												name="newPassword" placeholder="Enter new password">
										</div>
										<div class="col-md-6">
											<label class="form-label">Confirm Password</label>
											<input type="password" class="form-control rounded-3"
												name="confirmPassword" placeholder="Confirm new password">
										</div>
										<div class="col-12">
											<div class="form-check form-switch">
												<input class="form-check-input" type="checkbox"
													name="notifications" id="notifications" checked>
												<label class="form-check-label" for="notifications">
													Receive system, transaction, and AI advisory notifications
												</label>
											</div>
										</div>
										<div class="col-12 d-flex justify-content-end gap-2">
											<button type="reset" class="btn btn-outline-secondary rounded-pill px-4">
												Reset
											</button>
											<button type="submit" class="btn <%= buttonClass %> rounded-pill px-4">
												<i class="bi bi-save me-2"></i> Save Settings
											</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</section>
			</main>
		</div>
	</div>

	<jsp:include page="/notification-widget.jsp" />
	<jsp:include page="/chatbot-widget.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
	<script src="js/chatbot-widget.js?v=2"></script>
</body>
</html>
