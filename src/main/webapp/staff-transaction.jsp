<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ page import="dao.TransactionDAO,model.TransactionModel" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>AI Financial Advisory System</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Bootstrap Icons -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

<!-- Chatbot Widget CSS -->
<link rel="stylesheet" href="css/chatbot-widget.css?v=2">

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

			<!-- Sidebar -->
			<aside class="col-12 col-lg-2 text-white p-4"
				style="background-color: #0D6EFD;">
				<h4 class="fw-bold mb-4">
					<i class="bi bi-wallet2 me-2"></i> Financial Advisory
				</h4>

				<div class="nav flex-column nav-pills gap-2">
					<a class="nav-link text-white rounded-3" href="dashboard.jsp?role=staff">
						<i class="bi bi-speedometer2 me-2"></i> Dashboard
					</a>

					<a class="nav-link active text-white rounded-3" style="background-color: #084298;"
						href="staff-transaction.jsp">
						<i class="bi bi-cash-coin me-2"></i> Transactions
					</a>

					<a class="nav-link text-white rounded-3" href="aiadvisory.jsp?role=staff">
						<i class="bi bi-robot me-2"></i> AI Advisory
					</a>

					<a class="nav-link text-white rounded-3" href="account-settings.jsp?role=staff">
						<i class="bi bi-gear me-2"></i> Account Settings
					</a>

					<a class="nav-link text-white bg-danger rounded-3 mt-4 shadow-sm fw-bold" href="index.jsp">
						<i class="bi bi-box-arrow-right me-2"></i> <b>Logout</b>
					</a>
				</div>
			</aside>

			<!-- Main Content -->
			<main class="col-12 col-lg-10 p-4">

				<!-- Header -->
				<div
					class="d-flex flex-wrap justify-content-between align-items-center mb-4">
					<div>
						<h1 class="fw-bold mb-1">Transaction Management</h1>
						<p class="text-secondary mb-0">
							Record, upload invoice, and submit company transactions for approval.
						</p>
					</div>

					<div class="card border-0 shadow-sm rounded-4 mt-3 mt-md-0 role-welcome-card">
						<div class="card-body py-2 px-3">
							<span class="text-secondary">Welcome, </span>
							<strong>Staff</strong>
						</div>
					</div>

				</div>

				<!-- Summary Cards -->
				<section class="row g-4 mb-4">
					<div class="col-md-3">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Total Income</p>
									<i class="bi bi-arrow-down-circle fs-2 text-success"></i>
								</div>

								<h3 class="fw-bold mb-0">RM 67,676,767.00</h3>
								<small class="text-success">Approved income records</small>
							</div>
						</div>
					</div>

					<div class="col-md-3">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Total Expenses</p>
									<i class="bi bi-arrow-up-circle fs-2 text-danger"></i>
								</div>

								<h3 class="fw-bold mb-0">RM 676,767.00</h3>
								<small class="text-danger">Approved expense records</small>
							</div>
						</div>
					</div>

					<div class="col-md-3">
						<div class="card border-0 shadow-sm rounded-4 h-100">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Net Balance</p>
									<i class="bi bi-piggy-bank fs-2 text-primary"></i>
								</div>

								<h3 class="fw-bold mb-0">RM 67,000,000.00</h3>
								<small class="text-primary">Based on approved transactions</small>
							</div>
						</div>
					</div>

					<div class="col-md-3">
						<div
							class="card border-0 shadow-sm rounded-4 h-100 border-start border-warning border-5">
							<div class="card-body p-4">
								<div class="d-flex justify-content-between align-items-center">
									<p class="text-secondary mb-1">Pending Verification</p>
									<i class="bi bi-hourglass-split fs-2 text-warning"></i>
								</div>

								<h3 class="fw-bold mb-0">3</h3>
								<small class="text-warning">Waiting for Department Manager verification</small>
							</div>
						</div>
					</div>
				</section>

					<!-- Transaction Table -->
				<section class="card border-0 shadow-sm rounded-4">
					<div class="card-body p-4">
						<div class="d-flex justify-content-between align-items-center mb-3">
							<h5 class="fw-bold mb-0">
								<i class="bi bi-list-ul me-2"></i> Transaction List
							</h5>
							<div class="d-flex gap-2">
									<a href="staff-transaction-details.jsp?id=0" class="btn btn-primary rounded-pill px-4">
										<i class="bi bi-plus-circle me-2"></i>Create New
									</a>
									<a href="#" class="btn btn-outline-primary rounded-pill px-4">
										<i class="bi bi-download me-2"></i>Export
									</a>
								</div>
						</div>

						<div class="table-responsive">
							<table class="table table-hover align-middle">
								<thead>
									<tr>
										<th>Date</th>
										<th>Title</th>
										<th>Type</th>
										<th>Category</th>
										<th>Payment</th>
										<th class="text-end">Amount</th>
										<th>Approval Status</th>
										<th class="text-center">Action</th>
									</tr>
								</thead>

									<tbody>
										<%
										TransactionDAO transactionDOA = new TransactionDAO();
																			ArrayList<TransactionModel> transactions = transactionDOA.getAllTransactions(); // Fetch transactions from database
																		
																			for (TransactionModel transaction : transactions) {
										%>
										<tr>
											<td><%= transaction.getDateTransaction() %></td>
											<td><%= transaction.getName() %></td>
											<td><span class="badge rounded-pill text-bg-success">Income</span></td>
											<td><%= transaction.getCategoryId() %></td>
											<td><%= transaction.getPaymentMethod() %></td>
											<td class="text-end text-success fw-bold">RM <%= transaction.getTotalAmount() %></td>
											<%if ("Approved".equalsIgnoreCase(transaction.getStatus())) { %>
											<td><span class="badge rounded-pill text-bg-success">Approved</span></td>
											<% } else if ("Rejected".equalsIgnoreCase(transaction.getStatus())) { %>
											<td><span class="badge rounded-pill text-bg-danger">Rejected</span></td>
											<% } else { %>
											<td><span class="badge rounded-pill text-bg-warning">Pending Verification</span></td>
											<% } %>
											<td class="text-center">
												<a class="btn btn-sm btn-outline-secondary rounded-pill" href="staff-transaction-details.jsp?id=sales-001"><i class="bi bi-eye"></i></a>
												<a href="staff-transaction-details.jsp?action=edit&id=sales-001" class="btn btn-sm btn-outline-primary rounded-pill"><i class="bi bi-pencil-square"></i></a>
												<a href="#" class="btn btn-sm btn-outline-danger rounded-pill"><i class="bi bi-trash"></i></a>
											</td>
										</tr>
										<%
											}
										%>
										<!-- <tr>
											<td>2026-01-08</td>
											<td>Office Rent</td>
											<td><span class="badge rounded-pill text-bg-danger">Expense</span></td>
											<td>Rent</td>
											<td>Bank Transfer</td>
											<td class="text-end text-danger fw-bold">RM 32,471.00</td>
											<td><span class="badge rounded-pill text-bg-warning">Pending Verification</span></td>
											<td class="text-center">
												<a class="btn btn-sm btn-outline-secondary rounded-pill" href="staff-transaction-details.jsp?id=rent-001"><i class="bi bi-eye"></i></a>
												<a href="staff-transaction-details.jsp?action=edit&id=rent-001" class="btn btn-sm btn-outline-primary rounded-pill"><i class="bi bi-pencil-square"></i></a>
												<a href="#" class="btn btn-sm btn-outline-danger rounded-pill"><i class="bi bi-trash"></i></a>
											</td>
										</tr>
										<tr>
											<td>2026-01-10</td>
											<td>Internet Bill</td>
					s						<td><span class="badge rounded-pill text-bg-danger">Expense</span></td>
											<td>Utilities</td>
											<td>Online Payment</td>
											<td class="text-end text-danger fw-bold">RM 76,432.00</td>
											<td><span class="badge rounded-pill text-bg-danger">Rejected</span></td>
											<td class="text-center">
												<a class="btn btn-sm btn-outline-secondary rounded-pill" href="staff-transaction-details.jsp?id=internet-001"><i class="bi bi-eye"></i></a>
												<a href="staff-transaction-details.jsp?action=edit&id=internet-001" class="btn btn-sm btn-outline-primary rounded-pill"><i class="bi bi-pencil-square"></i></a>
												<a href="#" class="btn btn-sm btn-outline-danger rounded-pill"><i class="bi bi-trash"></i></a>
											</td>
										</tr>
										<tr>
											<td>2026-01-14</td>
											<td>Marketing Campaign</td>
											<td><span class="badge rounded-pill text-bg-danger">Expense</span></td>
											<td>Marketing</td>
											<td>Credit Card</td>
											<td class="text-end text-danger fw-bold">RM 46,832.00</td>
											<td><span class="badge rounded-pill text-bg-warning">Pending Verification</span></td>
											<td class="text-center">
												<a class="btn btn-sm btn-outline-secondary rounded-pill" href="staff-transaction-details.jsp?id=marketing-001"><i class="bi bi-eye"></i></a>
												<a href="staff-transaction-details.jsp?action=edit&id=marketing-001" class="btn btn-sm btn-outline-primary rounded-pill"><i class="bi bi-pencil-square"></i></a>
												<a href="#" class="btn btn-sm btn-outline-danger rounded-pill"><i class="bi bi-trash"></i></a>
											</td>
										</tr>
										<tr>
											<td>2026-01-20</td>
											<td>ABC Supplier Sdn Bhd - INV-0001</td>
											<td><span class="badge rounded-pill text-bg-danger">Expense</span></td>
											<td>Supplies</td>
											<td>Invoice</td>
											<td class="text-end text-danger fw-bold">RM 477.00</td>
											<td><span class="badge rounded-pill text-bg-warning">Pending Verification</span></td>
											<td class="text-center">
												<a class="btn btn-sm btn-outline-secondary rounded-pill" href="staff-transaction-details.jsp?id=invoice-001"><i class="bi bi-eye"></i></a>
												<a href="staff-transaction-details.jsp?action=edit&id=invoice-001" class="btn btn-sm btn-outline-primary rounded-pill"><i class="bi bi-pencil-square"></i></a>
												<a href="#" class="btn btn-sm btn-outline-danger rounded-pill"><i class="bi bi-trash"></i></a>
											</td>
										</tr> -->
									</tbody>
							</table>
						</div>

						<nav class="mt-3">
							<ul class="pagination justify-content-end mb-0">
								<li class="page-item disabled">
									<a class="page-link">Previous</a>
								</li>
								<li class="page-item active">
									<a class="page-link" href="#">1</a>
								</li>
								<li class="page-item">
									<a class="page-link" href="#">2</a>
								</li>
								<li class="page-item">
									<a class="page-link" href="#">Next</a>
								</li>
							</ul>
						</nav>
					</div>
				</section>

			</main>
		</div>
	</div>

	<jsp:include page="notification-widget.jsp" />

	<jsp:include page="chatbot-widget.jsp" />

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

	<!-- Chatbot Widget JS -->
	<script src="js/chatbot-widget.js?v=2"></script>

</body>
</html>
