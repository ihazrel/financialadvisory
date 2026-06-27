<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.UserModel" %> <%-- session to store user role --%>

<%
	UserModel user = (UserModel) session.getAttribute("user");
	
	if(user == null){
		response.sendRedirect("login.jsp");
	    return;
	}
	
	int roleId = user.getRoleId();
	String name = user.getName();
%>

<%
	String roleName = "";
	String sidebarTitle = "";
	String sidebarIcon = "";
	String sidebarColor = "";
	String activeStyle = "";
	String settingsHref = "";
	String advisoryHref = "";
	String subtitle = "";
	String role = "";
	

if (roleId == 4) {

    roleName = "Staff";

    sidebarTitle = "Financial Advisory";
    sidebarIcon = "bi-wallet2";
    sidebarColor = "#0D6EFD";
    activeStyle = "background-color: #084298;";

    settingsHref = "account-settings.jsp?role=staff";
    advisoryHref = "aiadvisory.jsp?role=staff";

    subtitle = "View dashboard, manage transactions, and initiate AI advisory.";

} else if (roleId == 3) {

    roleName = "Department Manager";

    sidebarTitle = "Department Manager";
    sidebarIcon = "bi-person-badge";
    sidebarColor = "#4338CA";
    activeStyle = "background-color: #312E81;";

    settingsHref = "account-settings.jsp?role=departmentmanager";
    advisoryHref = "aiadvisory.jsp?role=departmentmanager";

    subtitle = "Review department transactions, monitor budget usage, and view analytics.";

} else if (roleId == 2) {

    roleName = "Financial Manager";

    sidebarTitle = "Financial Manager";
    sidebarIcon = "bi-briefcase";
    sidebarColor = "#0F766E";
    activeStyle = "background-color: #198754;";

    settingsHref = "account-settings.jsp?role=financialmanager";
    advisoryHref = "aiadvisory.jsp?role=financialmanager";

    subtitle = "Analyze company performance and generate financial reports.";
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
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style>
	html {
		scroll-behavior: smooth;
	}

	section {
		scroll-margin-top: 20px;
	}
</style>
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
					<a class="nav-link active text-white rounded-3"
						style="<%= activeStyle %>" href="dashboard.jsp?role=<%= roleId %>">
						<i class="bi bi-speedometer2 me-2"></i> Dashboard
					</a>

					<% if (roleId == 1) { %>
					<a class="nav-link text-white rounded-3" href="staff-transaction.jsp">
						<i class="bi bi-cash-coin me-2"></i> Transactions
					</a>
					<% } %>

					<% if (roleId == 3) { %>
					<a class="nav-link text-white rounded-3" href="departmentmanager-budget.jsp">
						<i class="bi bi-wallet2 me-2"></i> Budget Management
					</a>
					<a class="nav-link text-white rounded-3" href="departmentmanager-category-list.jsp">
						<i class="bi bi-tags me-2"></i> Category
					</a>
					<a class="nav-link text-white rounded-3" href="departmentmanager-history.jsp">
						<i class="bi bi-receipt me-2"></i> Transactions
					</a>
					<% } %>

					<a class="nav-link text-white rounded-3" href="<%= advisoryHref %>">
						<i class="bi bi-robot me-2"></i> AI Advisory
					</a>

					<a class="nav-link text-white rounded-3" href="<%= settingsHref %>">
						<i class="bi bi-gear me-2"></i> Account Settings
					</a>

					<a class="nav-link text-white bg-danger rounded-3 mt-4 shadow-sm fw-bold" href="logout">
						<i class="bi bi-box-arrow-right me-2"></i> <b>Logout</b>
					</a>
				</div>
			</aside>

			<main class="col-12 col-lg-10 p-4">
				<div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
					<div>
						<h1 class="fw-bold mb-1"><%= sidebarTitle  %> Dashboard</h1>
						<p class="text-secondary mb-0"><%= subtitle %></p>
					</div>

					<div class="card border-0 shadow-sm rounded-4 mt-3 mt-md-0 role-welcome-card">
						<div class="card-body py-2 px-3">
							<span class="text-secondary">Welcome, </span>
							<strong><%= roleName %></strong>
						</div>
					</div>
				</div>

				<% if (roleId == 1) { %>
				<section class="row g-4 mb-4">
					<div class="col-md-6 col-xl-3">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Total Revenue</p>
									<i class="bi bi-graph-up-arrow fs-3 text-success"></i>
								</div>
								<h3 class="fw-bold mb-2">RM 24,500.00</h3>
								<small class="text-success">+67% from last month</small>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-xl-3">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Total Expenses</p>
									<i class="bi bi-wallet2 fs-3 text-danger"></i>
								</div>
								<h3 class="fw-bold mb-2">RM 15,200.00</h3>
								<small class="text-danger">+6.7% from last month</small>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-xl-3">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Net Profit</p>
									<i class="bi bi-cash-stack fs-3 text-success"></i>
								</div>
								<h3 class="fw-bold mb-2">RM 9,300.00</h3>
								<small class="text-success">Positive cashflow</small>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-xl-3">
						<div class="card border-0 shadow-sm rounded-4 h-100 border-start border-primary border-5">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Risk Status</p>
									<i class="bi bi-shield-check fs-3 text-primary"></i>
								</div>
								<h3 class="fw-bold mb-2">Low Risk</h3>
								<small class="text-primary">Spending is under control</small>
							</div>
						</div>
					</div>
				</section>

				<section class="row g-4 mb-4">
					<div class="col-lg-8">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center mb-3">
									<h5 class="fw-bold mb-0">Cashflow Trend</h5>
								</div>
								<canvas id="cashflowChart" height="120"></canvas>
							</div>
						</div>
					</div>
					<div class="col-lg-4">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center mb-3">
									<h5 class="fw-bold mb-0">Expense Categories</h5>
								</div>
								<div class="d-flex justify-content-between py-3 border-bottom">
									<span>Rent</span><strong>RM 4,000</strong>
								</div>
								<div class="d-flex justify-content-between py-3 border-bottom">
									<span>Utilities</span><strong>RM 1,200</strong>
								</div>
								<div class="d-flex justify-content-between py-3 border-bottom">
									<span>Salary</span><strong>RM 7,500</strong>
								</div>
								<div class="d-flex justify-content-between py-3">
									<span>Marketing</span><strong>RM 2,500</strong>
								</div>
							</div>
						</div>
					</div>
				</section>

				<section class="card border-0 shadow-sm rounded-4 mb-4">
					<div class="card-body p-4">
						<h5 class="fw-bold mb-3">
							<i class="bi bi-pie-chart me-2"></i> Expense Focus Area
						</h5>
						<div class="row g-3">
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Rent</p><strong class="text-danger">High Fixed Cost</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Salary</p><strong class="text-warning">Monitor</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Utilities</p><strong class="text-success">Acceptable</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Marketing</p><strong class="text-primary">Review ROI</strong></div></div>
						</div>
					</div>
				</section>

				<section class="row g-4">
					<div class="col-lg-8">
						<div class="card border-0 shadow-sm rounded-4">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center mb-3">
									<h5 class="fw-bold mb-0">Recent Transactions</h5>
									<a href="staff-transaction.jsp" class="text-decoration-none">View All</a>
								</div>
								<div class="table-responsive">
									<table class="table table-hover align-middle">
										<thead><tr><th>Title</th><th>Type</th><th>Category</th><th class="text-end">Amount</th></tr></thead>
										<tbody>
											<tr><td>Product Sales</td><td><span class="badge rounded-pill text-bg-success">Income</span></td><td>Sales</td><td class="text-end">RM 8,500</td></tr>
											<tr><td>Office Rent</td><td><span class="badge rounded-pill text-bg-danger">Expense</span></td><td>Rent</td><td class="text-end">RM 4,000</td></tr>
											<tr><td>Internet Bill</td><td><span class="badge rounded-pill text-bg-danger">Expense</span></td><td>Utilities</td><td class="text-end">RM 200</td></tr>
											<tr><td>ABC Supplier Sdn Bhd - INV-0001</td><td><span class="badge rounded-pill text-bg-danger">Expense</span></td><td>Supplies</td><td class="text-end">RM 477</td></tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-4">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center mb-3">
									<h5 class="fw-bold mb-0">AI Financial Advisor</h5>
								</div>
								<p class="text-secondary">
									Your company currently has a positive cashflow. Consider reviewing fixed costs to improve future profitability.
								</p>
								<a href="<%= advisoryHref %>" class="btn btn-primary w-100 rounded-pill">Open Advisory Chatbot</a>
							</div>
						</div>
					</div>
				</section>
				<% } %>

				<% if (roleId == 3) { %>
				<section class="row g-4 mb-4">
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100"><div class="card-body p-4"><p class="text-secondary mb-1">Department Budget</p><h3 class="fw-bold mb-2">RM 50,000.00</h3><small class="text-secondary">Monthly budget allocation</small></div></div></div>
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100"><div class="card-body p-4"><p class="text-secondary mb-1">Used Budget</p><h3 class="fw-bold mb-2">RM 31,250.00</h3><small class="text-danger">62.5% used this month</small></div></div></div>
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100"><div class="card-body p-4"><p class="text-secondary mb-1">Remaining Budget</p><h3 class="fw-bold mb-2">RM 18,750.00</h3><small class="text-success">Available balance</small></div></div></div>
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100 border-start border-warning border-5"><div class="card-body p-4"><p class="text-secondary mb-1">Pending Verification</p><h3 class="fw-bold mb-2">5</h3><small class="text-warning">Staff transactions pending</small></div></div></div>
				</section>

				<section class="row g-4 mb-4">
					<div class="col-lg-8">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center mb-3">
									<h5 class="fw-bold mb-0"><i class="bi bi-graph-up me-2"></i> Department Budget Usage</h5>
								</div>
								<canvas id="departmentBudgetChart" height="120"></canvas>
							</div>
						</div>
					</div>
					<div class="col-lg-4">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<h5 class="fw-bold mb-3"><i class="bi bi-pie-chart me-2"></i> Spending Categories</h5>
								<div class="d-flex justify-content-between py-3 border-bottom"><span>Marketing</span><strong>RM 12,000</strong></div>
								<div class="d-flex justify-content-between py-3 border-bottom"><span>Travel</span><strong>RM 6,500</strong></div>
								<div class="d-flex justify-content-between py-3 border-bottom"><span>Supplies</span><strong>RM 4,750</strong></div>
								<div class="d-flex justify-content-between py-3"><span>Training</span><strong>RM 8,000</strong></div>
							</div>
						</div>
					</div>
				</section>

				<section class="card border-0 shadow-sm rounded-4 mb-4">
					<div class="card-body p-4">
						<h5 class="fw-bold mb-3"><i class="bi bi-pie-chart me-2"></i> Expense Focus Area</h5>
						<div class="row g-3">
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Marketing</p><strong class="text-danger">High Usage</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Training</p><strong class="text-warning">Monitor</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Supplies</p><strong class="text-primary">Review</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Utilities</p><strong class="text-success">Acceptable</strong></div></div>
						</div>
					</div>
				</section>

				<section class="card border-0 shadow-sm rounded-4 mb-4" id="pending-verification">
					<div class="card-body p-4">
						<div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
							<div>
								<h5 class="fw-bold mb-1"><i class="bi bi-hourglass-split me-2"></i> Pending Verification</h5>
								<p class="text-secondary mb-0">Review transactions submitted by department staff.</p>
							</div>
						</div>
						<div class="table-responsive">
							<table class="table table-hover align-middle">
								<thead><tr><th>Date</th><th>Submitted By</th><th>Transaction</th><th>Category</th><th class="text-end">Amount</th><th>Status</th><th class="text-center">Action</th></tr></thead>
								<tbody>
									<tr><td>2026-05-14</td><td>Aiman Hakim</td><td>Client Meeting Travel</td><td>Travel</td><td class="text-end text-danger fw-bold">RM 850.00</td><td><span class="badge text-bg-warning rounded-pill">Pending</span></td><td class="text-center"><a class="btn btn-sm btn-outline-primary rounded-pill" href="/department/departmentmanager-transaction-details.jsp?id=travel-001"><i class="bi bi-eye me-1"></i>View Details</a></td></tr>
									<tr><td>2026-05-13</td><td>Nur Sofia</td><td>Marketing Material Printing</td><td>Marketing</td><td class="text-end text-danger fw-bold">RM 1,250.00</td><td><span class="badge text-bg-warning rounded-pill">Pending</span></td><td class="text-center"><a class="btn btn-sm btn-outline-primary rounded-pill" href="/department/departmentmanager-transaction-details.jsp?id=marketing-001"><i class="bi bi-eye me-1"></i>View Details</a></td></tr>
									<tr><td>2026-05-12</td><td>Farhan Zaki</td><td>Department Training Fee</td><td>Training</td><td class="text-end text-danger fw-bold">RM 3,000.00</td><td><span class="badge text-bg-warning rounded-pill">Pending</span></td><td class="text-center"><a class="btn btn-sm btn-outline-primary rounded-pill" href="<%= request.getContextPath() %>/department/departmentmanager-transaction-details.jsp?id=training-002"><i class="bi bi-eye me-1"></i>View Details</a></td></tr>
								</tbody>
							</table>
						</div>
					</div>
				</section>
				<% } %>

				<% if (roleId == 2) { %>
				<section class="row g-4 mb-4">
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100"><div class="card-body p-4"><p class="text-secondary mb-1">Total Revenue</p><h3 class="fw-bold mb-2">RM 67,676,767.00</h3><small class="text-success">Company income summary</small></div></div></div>
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100"><div class="card-body p-4"><p class="text-secondary mb-1">Total Expenses</p><h3 class="fw-bold mb-2">RM 676,767.00</h3><small class="text-danger">Approved expenses</small></div></div></div>
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100"><div class="card-body p-4"><p class="text-secondary mb-1">Net Profit</p><h3 class="fw-bold text-success mb-2">RM 67,000,000.00</h3><small class="text-success">Positive cashflow</small></div></div></div>
					<div class="col-md-6 col-xl-3"><div class="card border-0 shadow-sm rounded-4 h-100 border-start border-primary border-5"><div class="card-body p-4"><p class="text-secondary mb-1">Company Status</p><h3 class="fw-bold mb-2">Healthy</h3><small class="text-primary">Based on current period</small></div></div></div>
				</section>

				<section class="card border-0 shadow-sm rounded-4 mb-4">
					<div class="card-body p-4">
						<div class="d-flex justify-content-between align-items-center mb-3">
							<h5 class="fw-bold mb-0"><i class="bi bi-graph-up me-2"></i> Company Financial Analytics</h5>
						</div>
						<canvas id="financialManagerChart" height="110"></canvas>
					</div>
				</section>

				<section class="card border-0 shadow-sm rounded-4 mb-4">
					<div class="card-body p-4">
						<h5 class="fw-bold mb-3"><i class="bi bi-pie-chart me-2"></i> Expense Focus Area</h5>
						<div class="row g-3">
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Rent</p><strong class="text-danger">High Fixed Cost</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Salary</p><strong class="text-warning">Monitor</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Utilities</p><strong class="text-success">Acceptable</strong></div></div>
							<div class="col-md-6 col-xl-3"><div class="border rounded-4 p-3 h-100"><p class="text-secondary mb-1">Marketing</p><strong class="text-primary">Review ROI</strong></div></div>
						</div>
					</div>
				</section>

				<section class="card border-0 shadow-sm rounded-4 mb-4" id="company-statement">
					<div class="card-body p-4">
						<h5 class="fw-bold mb-3"><i class="bi bi-funnel me-2"></i> Statement Period</h5>
						<form action="#" method="post">
							<div class="row g-3 align-items-end">
								<div class="col-md-3"><label class="form-label">Statement Month</label><select class="form-select rounded-3" name="statementMonth"><option selected>June</option><option>May</option><option>April</option><option>March</option></select></div>
								<div class="col-md-3"><label class="form-label">Statement Year</label><select class="form-select rounded-3" name="statementYear"><option selected>2026</option><option>2025</option><option>2024</option></select></div>
								<div class="col-md-3"><label class="form-label">Statement Type</label><select class="form-select rounded-3" name="statementType"><option selected>Monthly Statement</option><option>Quarterly Statement</option><option>Yearly Statement</option></select></div>
								<div class="col-md-3"><button class="btn btn-primary rounded-pill w-100" type="submit"><i class="bi bi-file-earmark-text me-2"></i> Generate Preview</button></div>
							</div>
						</form>
					</div>
				</section>

				<section class="card border-0 shadow-sm rounded-4 mb-4">
					<div class="card-body p-4">
						<div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
							<div>
								<h5 class="fw-bold mb-1"><i class="bi bi-file-earmark-bar-graph me-2"></i> Company Statement Preview</h5>
								<p class="text-secondary mb-0">Statement for June 2026 generated from verified company transactions.</p>
							</div>
						</div>
						<div class="table-responsive mb-4">
							<table class="table table-hover align-middle">
								<thead><tr><th>Statement Item</th><th>Description</th><th class="text-end">Amount</th></tr></thead>
								<tbody>
									<tr><td>Total Revenue</td><td>All verified income transactions for the selected period.</td><td class="text-end text-success">RM 67,676,767.00</td></tr>
									<tr><td>Total Expenses</td><td>All verified expense transactions for the selected period.</td><td class="text-end text-danger">RM 676,767.00</td></tr>
									<tr><td class="fw-bold">Net Profit</td><td class="fw-bold">Total revenue minus total expenses.</td><td class="text-end fw-bold text-success">RM 67,000,000.00</td></tr>
								</tbody>
							</table>
						</div>
						<div class="alert alert-success rounded-4 mb-0">
							<h6 class="fw-bold mb-1">Statement Conclusion</h6>
							<p class="mb-0">The company is in a healthy financial position for this period because revenue is higher than expenses.</p>
						</div>
					</div>
				</section>

				<section class="row g-4">
					<div class="col-lg-6">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<h5 class="fw-bold mb-3"><i class="bi bi-list-ul me-2"></i> Statement Transaction Summary</h5>
								<div class="table-responsive">
									<table class="table table-hover align-middle mb-0">
										<thead><tr><th>Category</th><th>Type</th><th class="text-end">Amount</th></tr></thead>
										<tbody>
											<tr><td>Sales</td><td><span class="badge rounded-pill text-bg-success">Income</span></td><td class="text-end">RM 67,676,767.00</td></tr>
											<tr><td>Rent</td><td><span class="badge rounded-pill text-bg-danger">Expense</span></td><td class="text-end">RM 400,000.00</td></tr>
											<tr><td>Utilities</td><td><span class="badge rounded-pill text-bg-danger">Expense</span></td><td class="text-end">RM 76,767.00</td></tr>
											<tr><td>Supplies</td><td><span class="badge rounded-pill text-bg-danger">Expense</span></td><td class="text-end">RM 200,000.00</td></tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<h5 class="fw-bold mb-1">Export Statement</h5>
								<p class="text-secondary mb-4">Download or print the generated company statement for reporting.</p>
								<div class="d-grid gap-3">
									<button class="btn btn-outline-primary rounded-pill px-4" type="button"><i class="bi bi-printer me-2"></i> Print</button>
									<button class="btn btn-outline-success rounded-pill px-4" type="button"><i class="bi bi-file-earmark-spreadsheet me-2"></i> Export Excel</button>
									<button class="btn btn-primary rounded-pill px-4" type="button"><i class="bi bi-file-earmark-pdf me-2"></i> Download PDF</button>
								</div>
							</div>
						</div>
					</div>
				</section>
				<% } %>
			</main>
		</div>
	</div>

	<jsp:include page="notification-widget.jsp" />
	<jsp:include page="chatbot-widget.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
	<script src="js/staff.js"></script>
	<script src="js/financialmanager.js"></script>
	<script src="js/chatbot-widget.js?v=2"></script>
	<script>
		const departmentBudgetChart = document.getElementById("departmentBudgetChart");
		if (departmentBudgetChart) {
			new Chart(departmentBudgetChart, {
				type: "line",
				data: {
					labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
					datasets: [
						{ label: "Department Budget", data: [50000, 50000, 50000, 50000, 50000, 50000], borderWidth: 3, tension: 0.4, borderColor: "#36A2EB", backgroundColor: "#9BD0F5" },
						{ label: "Used Budget", data: [12000, 18000, 22000, 26000, 28500, 31250], borderWidth: 3, tension: 0.4, borderColor: "#FF6384", backgroundColor: "#FFB1C1" }
					]
				},
				options: { responsive: true, plugins: { legend: { position: "bottom" } }, scales: { y: { beginAtZero: true } } }
			});
		}
	</script>
</body>
</html>
