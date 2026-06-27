<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<<<<<<< HEAD
<%
	String action = request.getParameter("action");
	boolean isCreate = "create".equals(action);
	boolean isEdit = "edit".equals(action);
=======
<%@ page import="model.TransactionModel, dao.TransactionDAO" %>
<%@ page import="model.TransactionItemModel, dao.TransactionItemDAO" %>
<%@ page import="model.AttachmentModel, dao.AttachmentDAO" %>
<%@ page import="dao.CategoryDAO, model.CategoryModel" %>
<%@ page import="java.util.ArrayList" %>

<%
	TransactionDAO transactionDAO = new TransactionDAO();
	TransactionItemDAO transactionItemDAO = new TransactionItemDAO();
	AttachmentDAO attachmentDAO = new AttachmentDAO();

	String action = request.getParameter("action");
	boolean isCreate = "create".equals(action);
	boolean isEdit = "edit".equals(action);
	
	int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : -1;
	
	TransactionModel local_transaction = transactionDAO.getTransactionById(id);
	ArrayList<TransactionItemModel> local_items = transactionItemDAO.getTransactionItemsByTransactionId(id);
	ArrayList<AttachmentModel> local_attachments = attachmentDAO.getAttachmentsByTransactionId(id);
>>>>>>> main
%>

<!DOCTYPE html>
<html lang="en">
<head>
<<<<<<< HEAD
<jsp:include page="/includes/common-head.jsp" />
=======
<meta charset="UTF-8">
<title>AI Financial Advisory System</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="css/chatbot-widget.css?v=2">
>>>>>>> main
<style>
	.transaction-workspace {
		max-width: 1180px;
	}

	.transaction-form-card {
		border-radius: 8px;
	}

	.drop-zone {
		border: 2px dashed #9ec5fe;
		border-radius: 8px;
		background: #f8fbff;
		cursor: pointer;
		min-height: 180px;
		transition: border-color .15s ease, background-color .15s ease;
	}

	.drop-zone.drag-over {
		border-color: #0d6efd;
		background: #edf5ff;
	}

	.line-item-grid {
		display: grid;
<<<<<<< HEAD
		grid-template-columns: 110px 110px 150px minmax(180px, 1fr) 120px 140px 140px 44px;
=======
		grid-template-columns: 150px minmax(180px, 1fr) 120px 140px 140px 44px;
>>>>>>> main
		gap: .75rem;
		align-items: center;
	}

	.attachment-row {
		display: grid;
<<<<<<< HEAD
		grid-template-columns: 110px 110px minmax(180px, 1fr) 180px 120px minmax(180px, 1fr) 44px;
=======
		grid-template-columns: minmax(180px, 1fr) 150px 44px;
>>>>>>> main
		gap: .75rem;
		align-items: center;
	}

	@media (max-width: 767.98px) {
		.line-item-grid,
		.attachment-row {
			grid-template-columns: 1fr;
		}
	}
</style>
</head>
<body class="bg-light">
	<div class="container-fluid">
		<div class="row min-vh-100">
<<<<<<< HEAD
			<jsp:include page="/includes/sidebar.jsp">
				<jsp:param name="sidebarRole" value="staff" />
				<jsp:param name="activeMenu" value="transactions" />
			</jsp:include>
=======
			<aside class="col-12 col-lg-2 text-white p-4"
				style="background-color: #0D6EFD;">
				<h4 class="fw-bold mb-4">
					<i class="bi bi-wallet2 me-2"></i> Financial Advisory
				</h4>
				<div class="nav flex-column nav-pills gap-2">
					<a class="nav-link text-white rounded-3" href="dashboard.jsp?role=staff">
						<i class="bi bi-speedometer2 me-2"></i> Dashboard
					</a>
					<a class="nav-link active text-white rounded-3"
						style="background-color: #084298;" href="staff-transaction.jsp">
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
>>>>>>> main

			<main class="col-12 col-lg-10 p-4">
				<div class="transaction-workspace mx-auto">
					<div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
						<div>
							<h1 class="fw-bold mb-1"><%= isCreate ? "Add New Transaction" : "Transaction Details" %></h1>
							<p class="text-secondary mb-0">Record transaction information, item lines, and supporting attachments.</p>
						</div>
						<a class="btn btn-outline-secondary rounded-pill px-4" href="staff-transaction.jsp">
							<i class="bi bi-arrow-left me-2"></i>Back to List
						</a>
					</div>

					<section class="card border-0 shadow-sm transaction-form-card">
						<div class="card-body p-4">
<<<<<<< HEAD
							<form action="#" method="post" enctype="multipart/form-data">
=======
							<form action="TransactionController" method="post" enctype="multipart/form-data">
>>>>>>> main
								<div class="d-flex flex-wrap justify-content-between align-items-center gap-3 pb-3 mb-4 border-bottom">
									<h5 class="fw-bold mb-0">
										<i class="bi bi-receipt me-2"></i><%= isCreate ? "Add New Transaction" : "Edit Transaction" %>
									</h5>
									<button class="btn btn-outline-primary rounded-pill px-4" type="button"
										data-bs-toggle="modal" data-bs-target="#extractUploadModal">
										<i class="bi bi-file-earmark-arrow-up me-2"></i>Extract from PDF
									</button>
								</div>

								<div class="row g-3 mb-4">
<<<<<<< HEAD
									<div class="col-md-3">
										<label class="form-label">Transaction ID</label>
										<input type="text" class="form-control rounded-3" name="transactionId"
											value="<%= isCreate ? "Auto Generated" : "TXN-0001" %>" readonly>
									</div>
									<div class="col-md-6">
										<label class="form-label">Transaction Title</label>
										<input type="text" class="form-control rounded-3" name="title"
											value="<%= isCreate ? "ABC Supplier Sdn Bhd - INV-0001" : "Office Rent" %>" placeholder="Example: Office Rent">
									</div>
									<div class="col-md-3">
										<label class="form-label">Money Flow</label>
										<select class="form-select rounded-3" name="transactionType">
											<option value="money-in">Money In</option>
											<option value="money-out" selected>Money Out</option>
										</select>
									</div>
									<div class="col-md-3">
										<label class="form-label">Transaction Type</label>
										<select class="form-select rounded-3" name="financialType">
											<option value="revenue">Revenue</option>
											<option value="expenses" selected>Expenses</option>
=======
									<div class="col-md-6">
										<label class="form-label">Transaction Title</label>
										<input type="text" class="form-control rounded-3" name="title"
											value="<%= local_transaction != null ? local_transaction.getName() : "" %>" placeholder="Example: Office Rent">
									</div>
									<div class="col-md-3">
										<label class="form-label">Type</label>
										<select class="form-select rounded-3" name="transactionType">
											<option value="income" <%= (local_transaction != null && "income".equalsIgnoreCase(local_transaction.getTransactionType())) ? "selected" : "" %>>Income</option>
											<option value="expense" <%= (local_transaction != null && "expense".equalsIgnoreCase(local_transaction.getTransactionType())) ? "selected" : "" %>>Expense</option>
>>>>>>> main
										</select>
									</div>
									<div class="col-md-3">
										<label class="form-label">Category</label>
<<<<<<< HEAD
										<select class="form-select rounded-3" name="category">
											<option value="">Select Category</option>
											<option value="sales">Sales</option>
											<option value="rent" <%= isCreate ? "" : "selected" %>>Rent</option>
											<option value="utilities">Utilities</option>
											<option value="salary">Salary</option>
											<option value="marketing">Marketing</option>
											<option value="supplies" <%= isCreate ? "selected" : "" %>>Supplies</option>
											<option value="travel">Travel</option>
											<option value="training">Training</option>
											<option value="others">Others</option>
										</select>
									</div>
									<div class="col-md-3">
										<label class="form-label">Department</label>
										<select class="form-select rounded-3" name="department">
											<option value="1">IT</option>
											<option value="2" selected>Finance</option>
											<option value="3">HR</option>
=======
										<select class="form-select rounded-3" name="categoryId">
												<option value="">Select Category</option>
	                                        <%
	                                        	CategoryDAO categoryDAO = new CategoryDAO();
	                                        	ArrayList<CategoryModel> categories = categoryDAO.getAllCategories();
	                                        	for (CategoryModel category : categories) {
	                                        %>
	                                        	<option value="<%= category.getCategoryId() %>" <%= (local_transaction != null && local_transaction.getCategoryId() == category.getCategoryId()) ? "selected" : "" %>>
	                                        		<%= category.getName() %>
	                                        	</option>
	                                        <%
	                                        	}
	                                        %>
>>>>>>> main
										</select>
									</div>
									<div class="col-md-4">
										<label class="form-label">Amount (RM)</label>
										<input type="number" step="0.01" class="form-control rounded-3"
<<<<<<< HEAD
											name="amount" value="<%= isCreate ? "477.00" : "32471.00" %>" placeholder="0.00">
=======
											name="amount" value="<%= local_transaction != null ? local_transaction.getTotalAmount() : "" %>" placeholder="0.00">
>>>>>>> main
									</div>
									<div class="col-md-4">
										<label class="form-label">Transaction Date</label>
										<input type="date" class="form-control rounded-3"
<<<<<<< HEAD
											name="transactionDate" value="<%= isCreate ? "2026-01-20" : "2026-01-08" %>">
=======
											name="transactionDate" value="<%= local_transaction != null ? local_transaction.getDateTransaction() : "" %>" placeholder="Select transaction date">
>>>>>>> main
									</div>
									<div class="col-md-4">
										<label class="form-label">Payment Method</label>
										<input type="text" class="form-control rounded-3"
<<<<<<< HEAD
											name="paymentMethod" value="<%= isCreate ? "Invoice" : "Bank Transfer" %>" placeholder="e.g. Bank Transfer, Credit Card, Cash">
									</div>
									<div class="col-md-4">
										<label class="form-label">Currency</label>
										<select class="form-select rounded-3" name="currency">
											<option selected>MYR</option>
											<option>USD</option>
											<option>AUD</option>
										</select>
									</div>
									<div class="col-md-4">
										<label class="form-label">Status</label>
										<select class="form-select rounded-3" name="status">
											<option selected>Pending Verification</option>
											<option>Approved</option>
											<option>Rejected</option>
										</select>
=======
											name="paymentMethod" value="<%= local_transaction != null ? local_transaction.getPaymentMethod() : "" %>" placeholder="e.g. Bank Transfer, Credit Card, Cash">
>>>>>>> main
									</div>
									<div class="col-md-6">
										<label class="form-label">Invoice / Reference Number</label>
										<input type="text" class="form-control rounded-3" name="invoiceNo"
<<<<<<< HEAD
											value="<%= isCreate ? "INV-0001" : "RENT-2026-001" %>" placeholder="Invoice or payment reference">
									</div>
									<div class="col-md-6">
										<label class="form-label">Payer</label>
										<input type="text" class="form-control rounded-3" name="payer"
											value="<%= isCreate ? "Finance Department" : "Company Account" %>" placeholder="Who pays / receives money">
=======
											value="<%= local_transaction != null ? local_transaction.getInvoiceNo() : "" %>" placeholder="Invoice or payment reference">
>>>>>>> main
									</div>
									<div class="col-md-6">
										<label class="form-label">Vendor / Payee Name</label>
										<input type="text" class="form-control rounded-3" name="payee"
<<<<<<< HEAD
											value="<%= isCreate ? "ABC Supplier Sdn Bhd" : "City Office Properties" %>" placeholder="Vendor, customer, or payee">
									</div>
									<div class="col-md-3">
										<label class="form-label">Created By</label>
										<input type="text" class="form-control rounded-3" name="createdBy" value="Aina Rahman" placeholder="Staff name">
									</div>
									<div class="col-md-3">
										<label class="form-label">Verified By</label>
										<input type="text" class="form-control rounded-3" name="verifiedBy" value="Farid Hassan" placeholder="Department manager name">
=======
											value="<%= local_transaction != null ? local_transaction.getPayee() : "" %>" placeholder="Vendor, customer, or payee">
>>>>>>> main
									</div>
									<div class="col-12">
										<label class="form-label">Reason / Description</label>
										<textarea class="form-control rounded-3" rows="4" name="description"
<<<<<<< HEAD
											placeholder="Explain transaction purpose"><%= isCreate ? "Office supplies purchased for company operations. Auto extracted from uploaded invoice." : "Monthly office rental payment for January 2026." %></textarea>
=======
											placeholder="Explain transaction purpose"><%= local_transaction != null ? local_transaction.getDescription() : "" %></textarea>
>>>>>>> main
									</div>
								</div>

								<div class="border rounded-3 p-3 mb-4">
									<div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-3">
										<div>
											<h6 class="fw-bold mb-1">
												<i class="bi bi-list-check me-2"></i>Editable Item List
											</h6>
											<p class="text-secondary small mb-0">Review and edit transaction line items before submitting.</p>
										</div>
									</div>

									<div class="line-item-grid text-secondary small fw-semibold mb-2 d-none d-md-grid">
<<<<<<< HEAD
										<span>Item ID</span>
										<span>Transaction ID</span>
=======
>>>>>>> main
										<span>Item</span>
										<span>Description</span>
										<span>Quantity</span>
										<span>Unit Price</span>
										<span>Total</span>
										<span></span>
									</div>

									<div id="lineItems" class="vstack gap-2">
<<<<<<< HEAD
										<div class="line-item-grid line-item-row">
											<input type="text" class="form-control rounded-3" name="transactionItemId" value="ITEM-001">
											<input type="text" class="form-control rounded-3" name="itemTransactionId" value="TXN-0001">
											<input type="text" class="form-control rounded-3" name="itemName" value="Office Supplies">
											<input type="text" class="form-control rounded-3" name="itemDescription" value="Stationery and office consumables">
											<input type="number" class="form-control rounded-3 item-qty" name="itemQuantity" value="3" min="0" step="1">
											<input type="number" class="form-control rounded-3 item-price" name="itemUnitPrice" value="150.00" min="0" step="0.01">
											<input type="number" class="form-control rounded-3 item-total fw-bold" name="itemTotal" value="450.00" min="0" step="0.01">
											<button type="button" class="btn btn-outline-danger rounded-circle remove-row" aria-label="Delete item">
												<i class="bi bi-trash"></i>
											</button>
										</div>
										<div class="line-item-grid line-item-row">
											<input type="text" class="form-control rounded-3" name="transactionItemId" value="ITEM-002">
											<input type="text" class="form-control rounded-3" name="itemTransactionId" value="TXN-0001">
											<input type="text" class="form-control rounded-3" name="itemName" value="Service Tax">
											<input type="text" class="form-control rounded-3" name="itemDescription" value="Tax charged by supplier">
											<input type="number" class="form-control rounded-3 item-qty" name="itemQuantity" value="1" min="0" step="1">
											<input type="number" class="form-control rounded-3 item-price" name="itemUnitPrice" value="27.00" min="0" step="0.01">
											<input type="number" class="form-control rounded-3 item-total fw-bold" name="itemTotal" value="27.00" min="0" step="0.01">
											<button type="button" class="btn btn-outline-danger rounded-circle remove-row" aria-label="Delete item">
												<i class="bi bi-trash"></i>
											</button>
										</div>
=======
										<%
											if (local_items != null && !local_items.isEmpty()) {
                                                for (TransactionItemModel item : local_items) {
										%>
											<div class="line-item-grid line-item-row">
												<input type="text" class="form-control rounded-3" name="itemName" value="<%= item.getName() %>">
												<input type="text" class="form-control rounded-3" name="itemDescription" value="<%= item.getDescription() %>">
												<input type="number" class="form-control rounded-3 item-qty" name="itemQuantity" value="<%= item.getQuantity() %>" min="0" step="1">
												<input type="number" class="form-control rounded-3 item-price" name="itemUnitPrice" value="<%= item.getUnitPrice() %>" min="0" step="0.01">
												<input type="number" class="form-control rounded-3 item-total fw-bold" name="itemTotal" value="<%= item.getQuantity() * item.getUnitPrice() %>" min="0" step="0.01">
												<button type="button" class="btn btn-outline-danger rounded-circle remove-row" aria-label="Delete item">
													<i class="bi bi-trash"></i>
												</button>
											</div>
											<%
                                                }
                                            } else {
										%>
											<div class="no-items-message text-center text-muted py-3">
                                                No items added yet. Click "Add Item" to start.
                                            </div>
                                        	<%
                                            }
										%>
>>>>>>> main
									</div>

									<div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mt-3">
										<button type="button" class="btn btn-outline-primary btn-sm rounded-pill px-3" id="addItemBtn">
											<i class="bi bi-plus-circle me-2"></i>Add Item
										</button>
										<div class="d-flex align-items-center gap-3">
											<span class="fw-bold">Grand Total</span>
											<input type="number" class="form-control rounded-3 fw-bold text-danger" id="totalAmount"
<<<<<<< HEAD
												name="totalAmount" value="<%= isCreate ? "477.00" : "32471.00" %>" readonly style="max-width: 160px;">
=======
												name="totalAmount" value="<%= local_transaction != null ? local_transaction.getTotalAmount() : "" %>" readonly style="max-width: 160px;">
>>>>>>> main
										</div>
									</div>
								</div>

								<div class="border rounded-3 p-3 mb-4">
									<div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-3">
										<h6 class="fw-bold mb-0">
											<i class="bi bi-paperclip me-2"></i>Attachment List
										</h6>
										<button type="button" class="btn btn-outline-primary btn-sm rounded-pill px-3"
											data-bs-toggle="modal" data-bs-target="#attachmentUploadModal">
											<i class="bi bi-upload me-2"></i>Upload Attachment
										</button>
									</div>

									<div id="attachmentList" class="vstack gap-2">
<<<<<<< HEAD
										<div class="attachment-row text-secondary small fw-semibold d-none d-md-grid">
											<span>Attachment ID</span>
											<span>Transaction ID</span>
											<span>Name</span>
											<span>Description</span>
											<span>File Type</span>
											<span>File Path</span>
											<span></span>
										</div>
										<div class="attachment-row border-bottom pb-2">
											<span>ATT-001</span>
											<span>TXN-0001</span>
											<div>
												<i class="bi bi-file-earmark-pdf text-danger me-2"></i>
												<span class="fw-semibold">INV-0001.pdf</span>
											</div>
											<span class="text-secondary small">Invoice PDF</span>
											<span>PDF</span>
											<span class="text-secondary small">uploads/invoices/INV-0001.pdf</span>
											<button type="button" class="btn btn-outline-danger rounded-circle remove-attachment" aria-label="Delete attachment">
												<i class="bi bi-trash"></i>
											</button>
										</div>
										<div class="attachment-row border-bottom pb-2">
											<span>ATT-002</span>
											<span>TXN-0001</span>
											<div>
												<i class="bi bi-file-earmark-image text-primary me-2"></i>
												<span class="fw-semibold">delivery-order.png</span>
											</div>
											<span class="text-secondary small">Supporting File</span>
											<span>PNG</span>
											<span class="text-secondary small">uploads/attachments/delivery-order.png</span>
=======
									
									<% if (local_attachments != null && !local_attachments.isEmpty()) {
									   		for (AttachmentModel attachment : local_attachments) { %>
										<div class="attachment-row border-bottom pb-2">
											<div>
												<i class="bi bi-file-earmark-pdf text-danger me-2"></i>
												<span class="fw-semibold"><%= attachment.getName() %></span>
											</div>
											<span class="text-secondary small"><%= attachment.getDescription() %></span>
>>>>>>> main
											<button type="button" class="btn btn-outline-danger rounded-circle remove-attachment" aria-label="Delete attachment">
												<i class="bi bi-trash"></i>
											</button>
										</div>
<<<<<<< HEAD
=======
										<%
                                                }
                                            } else {
										%>
											<div class="no-items-message text-center text-muted py-3">
                                                No items added yet. Click "Upload Attachment" to start.
                                            </div>
                                        	<%
                                            }
										%>
>>>>>>> main
									</div>
								</div>

								<div class="d-flex flex-wrap justify-content-end gap-2">
									<a class="btn btn-outline-secondary rounded-pill px-4" href="staff-transaction.jsp">Cancel</a>
									
									<button class="btn btn-primary rounded-pill px-4" type="submit" name="action" value="save">
										<i class="bi bi-send-check me-2"></i>Save
									</button>
									
									<button class="btn btn-primary rounded-pill px-4" type="submit" name="action" value="submit">
										<i class="bi bi-send-check me-2"></i>Submit Transaction
									</button>
								</div>
							</form>
						</div>
					</section>
				</div>
			</main>
		</div>
	</div>

	<div class="modal fade" id="extractUploadModal" tabindex="-1" aria-labelledby="extractUploadModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<div class="modal-header">
					<h5 class="modal-title fw-bold" id="extractUploadModalLabel">Extract Transaction from PDF</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<label class="drop-zone d-flex flex-column justify-content-center align-items-center text-center p-4 w-100">
						<i class="bi bi-cloud-arrow-up fs-1 text-primary mb-2"></i>
						<span class="fw-bold">Upload invoice PDF or image</span>
						<span class="text-secondary small">Drag and drop file here, or click to browse</span>
						<span class="selected-file text-primary small mt-2"></span>
						<input class="d-none upload-input" type="file" accept=".pdf,.jpg,.jpeg,.png">
					</label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-secondary rounded-pill px-4" data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary rounded-pill px-4" data-bs-dismiss="modal">Confirm</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="attachmentUploadModal" tabindex="-1" aria-labelledby="attachmentUploadModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<div class="modal-header">
					<h5 class="modal-title fw-bold" id="attachmentUploadModalLabel">Upload Attachment</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<label class="drop-zone d-flex flex-column justify-content-center align-items-center text-center p-4 w-100">
						<i class="bi bi-paperclip fs-1 text-primary mb-2"></i>
						<span class="fw-bold">Upload supporting attachment</span>
						<span class="text-secondary small">Drag and drop file here, or click to browse</span>
						<span class="selected-file text-primary small mt-2"></span>
						<input class="d-none upload-input" type="file" accept=".pdf,.jpg,.jpeg,.png,.doc,.docx,.xls,.xlsx">
					</label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-secondary rounded-pill px-4" data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary rounded-pill px-4" data-bs-dismiss="modal">Confirm</button>
				</div>
			</div>
		</div>
	</div>
<<<<<<< HEAD
<jsp:include page="/includes/common-scripts.jsp" />
	<script>
		const lineItems = document.getElementById("lineItems");
		const grandTotal = document.getElementById("totalAmount");
=======

	<jsp:include page="notification-widget.jsp" />
	<jsp:include page="chatbot-widget.jsp" />
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
	<script src="js/chatbot-widget.js?v=2"></script>
	<script>
		const lineItems = document.getElementById("lineItems");
		const grandTotal = document.getElementById("grandTotal");
>>>>>>> main

		function updateLineTotal(row) {
			const qty = Number(row.querySelector(".item-qty").value) || 0;
			const price = Number(row.querySelector(".item-price").value) || 0;
			row.querySelector(".item-total").value = (qty * price).toFixed(2);
			updateGrandTotal();
		}

		function updateGrandTotal() {
			const total = [...document.querySelectorAll(".item-total")]
				.reduce((sum, input) => sum + (Number(input.value) || 0), 0);
			grandTotal.value = total.toFixed(2);
		}
		
		function removeEmptyMessage(containerId) {
            const noItemsMessage = document.getElementById(containerId).querySelector(".no-items-message");
            if (noItemsMessage) {
                noItemsMessage.classList.add("d-none");
            }
        }
		
		function addEmptyMessage(containerId, message) {
			const noItemsMessage = document.getElementById(containerId).querySelector(".no-items-message");
<<<<<<< HEAD
			if (noItemsMessage) {
				noItemsMessage.classList.remove("d-none");
			}
=======
			noItemsMessage.classList.remove("d-none");
>>>>>>> main
        }

		document.getElementById("addItemBtn").addEventListener("click", () => {
			removeEmptyMessage("lineItems");
			
			const row = document.createElement("div");
			row.className = "line-item-grid line-item-row";
			row.innerHTML = `
<<<<<<< HEAD
				<input type="text" class="form-control rounded-3" name="transactionItemId" placeholder="Item ID">
				<input type="text" class="form-control rounded-3" name="itemTransactionId" placeholder="Transaction ID">
=======
>>>>>>> main
				<input type="text" class="form-control rounded-3" name="itemName" placeholder="Item name">
				<input type="text" class="form-control rounded-3" name="itemDescription" placeholder="Item description">
				<input type="number" class="form-control rounded-3 item-qty" name="itemQuantity" value="1" min="0" step="1">
				<input type="number" class="form-control rounded-3 item-price" name="itemUnitPrice" value="0.00" min="0" step="0.01">
				<input type="number" class="form-control rounded-3 item-total fw-bold" name="itemTotal" value="0.00" min="0" step="0.01">
				<button type="button" class="btn btn-outline-danger rounded-circle remove-row" aria-label="Delete item">
					<i class="bi bi-trash"></i>
				</button>`;
			lineItems.appendChild(row);
		});

		lineItems.addEventListener("input", (event) => {
			const row = event.target.closest(".line-item-row");
			if (!row) {
				return;
			}
			if (event.target.classList.contains("item-qty") || event.target.classList.contains("item-price")) {
				updateLineTotal(row);
			}
			if (event.target.classList.contains("item-total")) {
				updateGrandTotal();
			}
		});

		lineItems.addEventListener("click", (event) => {
			const button = event.target.closest(".remove-row");
			if (!button) {
				return;
			}
			button.closest(".line-item-row").remove();
			
			// If there are no more line items, show the "no items" message
			const remainingRows = lineItems.querySelectorAll(".line-item-row");
			const noItemsMessage = document.getElementById("noItemsMessage");
			
			if (remainingRows.length === 0 && noItemsMessage) {
               noItemsMessage.classList.remove("d-none");
            }
			
			updateGrandTotal();
		});

		document.getElementById("attachmentList").addEventListener("click", (event) => {
			const button = event.target.closest(".remove-attachment");
			if (button) {
				button.closest(".attachment-row").remove();
			}
		});

		document.querySelectorAll(".drop-zone").forEach((zone) => {
			const input = zone.querySelector(".upload-input");
			const selectedFile = zone.querySelector(".selected-file");

			function setSelectedFile(files) {
				selectedFile.textContent = files && files.length ? files[0].name : "";
			}

			zone.addEventListener("dragover", (event) => {
				event.preventDefault();
				zone.classList.add("drag-over");
			});

			zone.addEventListener("dragleave", () => zone.classList.remove("drag-over"));

			zone.addEventListener("drop", (event) => {
				event.preventDefault();
				zone.classList.remove("drag-over");
				input.files = event.dataTransfer.files;
				setSelectedFile(input.files);
			});

			input.addEventListener("change", () => setSelectedFile(input.files));
		});
	</script>
</body>
</html>
