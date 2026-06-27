package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TransactionModel;
import model.UserModel;
import model.AttachmentModel;
import model.CategoryModel;
import model.TransactionItemModel;
import util.RequestUtil;


import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.AttachmentDAO;
import dao.CategoryDAO;
import dao.TransactionDAO;
import dao.TransactionItemDAO;
import helper.RoleHelper;
import helper.SessionHelper;

/**
 * Servlet implementation class TransactionController
 */
@MultipartConfig
@WebServlet("/TransactionController")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Handle GET requests if needed
    	
    	String action = request.getParameter("action");
    	
    	if (action == null || action.isEmpty()) {
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
    		return;
    	}
    	
    	switch (action) {
    		case "list" -> listTransactions(request, response);
    		
    		case "view-details" -> viewTransactionDetails(request, response);
    		
    		default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
    	}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		
		if (action == null || action.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
			return;
		}
		
		switch (action) {
			case "create" -> createTransaction(request, response, false);
				
			case "update" -> updateTransaction(request, response, false);
				
			case "delete" -> deleteTransaction(request, response);
				
			case "submit" -> submitTransaction(request, response);
			
			case "approve" -> confirmApproval(request, response, true);
			
			case "reject" -> confirmApproval(request, response, false);
				
			default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
		}
	}
	
	private void createTransaction(HttpServletRequest request, HttpServletResponse response, boolean isSubmit) throws ServletException, IOException {
		
		UserModel curr_user = SessionHelper.getCurrentUser(request);
		
		TransactionModel transaction = buildTransaction(request, true);
		ArrayList<TransactionItemModel> items = buildTransactionItems(request);
		
		
		transaction.setStatus(isSubmit ? "pending" : "draft");
		transaction.setDepartmentId(curr_user.getDepartmentId());
		transaction.setCreatedBy(curr_user.getUserId());
		
		
		// Save the transaction and its items to the database (this is just a placeholder)
		TransactionDAO transactionDAO = new TransactionDAO();
		Integer updatedTransactionId = transactionDAO.createTransaction(transaction);
		
		TransactionItemDAO transactionItemDAO = new TransactionItemDAO();
		transactionItemDAO.upsertAllTransactionItems(items, updatedTransactionId);

		listTransactions(request, response);	
	} 
	
	private void updateTransaction(HttpServletRequest request, HttpServletResponse response, boolean isSubmit) throws ServletException, IOException {

		UserModel curr_user = SessionHelper.getCurrentUser(request);
		
		TransactionModel transaction = buildTransaction(request, false);
		ArrayList<TransactionItemModel> items = buildTransactionItems(request);

		transaction.setStatus(isSubmit ? "pending" : "draft");
		transaction.setDepartmentId(curr_user.getDepartmentId());
		transaction.setCreatedBy(curr_user.getUserId());
		
		// Save the transaction and its items to the database (this is just a placeholder)
		TransactionDAO transactionDAO = new TransactionDAO();
		Integer updatedTransactionId = transactionDAO.updateTransaction(transaction);
		
		TransactionItemDAO transactionItemDAO = new TransactionItemDAO();
		transactionItemDAO.upsertAllTransactionItems(items, updatedTransactionId);

		listTransactions(request, response);
	} 
	
	private void deleteTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int transactionId = RequestUtil.getInt(request, "transactionId");
	    
	    TransactionDAO transactionDAO = new TransactionDAO();
	    boolean success = transactionDAO.deleteTransaction(transactionId);
	    
	    if (success) {
	    	response.sendRedirect("staff-transaction.jsp"); // Redirect to a success page after creation
	    }
	}

	private void submitTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		updateTransaction(request, response, true);
	}

	private void confirmApproval(HttpServletRequest request, HttpServletResponse response, boolean isApproved) throws ServletException, IOException {
		UserModel curr_user = SessionHelper.getCurrentUser(request);
		
		if (curr_user == null || (!RoleHelper.isFinancialManager(curr_user) && !RoleHelper.isDepartmentManager(curr_user))) {
	        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to approve or reject transactions.");
	        return;
	    }
		
		int transactionId = RequestUtil.getInt(request, "transactionId");
	    
	    TransactionDAO transactionDAO = new TransactionDAO();
	    TransactionModel transaction = transactionDAO.getTransactionById(transactionId);
	    
	    if (curr_user.getDepartmentId() != transaction.getDepartmentId()) {
	        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to approve or reject this transaction.");
	        return;
	    }
	    
	    if (transaction != null && "pending".equalsIgnoreCase(transaction.getStatus())) {
	    	
	        transaction.setStatus(isApproved ? "approved" : "rejected");
	        transaction.setVerifiedBy(curr_user.getUserId());
	        
	        boolean success = transactionDAO.updateTransaction(transaction) != null;
	        
	        if (success) {
	        	listTransactions(request, response); // Redirect to a success page after approval
	        }
	    } else {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Transaction not found or not in pending status");
	    }
	}
	
	private void listTransactions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Implementation for listing transactions
		
		TransactionDAO transactionDAO = new TransactionDAO();
		UserModel curr_user = SessionHelper.getCurrentUser(request);
		
		ArrayList<TransactionModel> transactions = new ArrayList<>();
		
		if (curr_user != null && RoleHelper.isFinancialManager(curr_user)) {
	        transactions = transactionDAO.getAllTransactions();
	        return;
	    } else if (curr_user != null && (RoleHelper.isDepartmentManager(curr_user) || RoleHelper.isStaff(curr_user))) {
	        transactions = transactionDAO.getTransactionsByDepartmentId(curr_user.getDepartmentId());
	    }
		
		request.setAttribute("transactions_list", transactions);
		
		request.getRequestDispatcher("transaction.jsp").forward(request, response);
	}	
	
	private void viewTransactionDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int transactionId = RequestUtil.getInt(request, "transactionId");
	    
	    TransactionDAO transactionDAO = new TransactionDAO();
	    TransactionModel transaction = transactionDAO.getTransactionById(transactionId);
	    List<CategoryModel> categories = new CategoryDAO().getAllCategories();
	    UserModel curr_user = SessionHelper.getCurrentUser(request);
	    
	    if (transaction != null) {
	        TransactionItemDAO transactionItemDAO = new TransactionItemDAO();
	        List<TransactionItemModel> items = transactionItemDAO.getTransactionItemsByTransactionId(transactionId);
	        List<AttachmentModel> attachments = new AttachmentDAO().getAttachmentsByTransactionId(transactionId);
	        
	        // data list
	        request.setAttribute("transaction", transaction);
	        request.setAttribute("transaction_items", items);
	        request.setAttribute("transaction_attachments", attachments);
	        
	        // dropdown list
	        request.setAttribute("categories_dropdown", categories);
	        
	        boolean isEditable = transaction.getStatus().equalsIgnoreCase("draft") || transaction.getStatus().equalsIgnoreCase("rejected");
	        boolean isApprover = curr_user != null 
	        					&& RoleHelper.isDepartmentManager(curr_user)  // is HoD or Financial Manager
	        					&& curr_user.getDepartmentId() == transaction.getDepartmentId()  // HoD of the same department as transaction
	        					&& transaction.getStatus().equalsIgnoreCase("pending");
	        
	        // helper variable
	        request.setAttribute("isEditable", isEditable);
	        request.setAttribute("isApprover", isApprover);
	        
	        request.getRequestDispatcher("transaction-details.jsp").forward(request, response);
	    } else {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found");
	    }
	}

	private TransactionModel buildTransaction(HttpServletRequest request, boolean isNewRecord) {

	    TransactionModel transaction = new TransactionModel();
	    
	    if (isNewRecord) {
	    	transaction.setTransactionId(null);
	    } else {
	    	transaction.setTransactionId(RequestUtil.getInt(request, "transactionId"));
	    }

	    transaction.setName(RequestUtil.getString(request, "title"));
	    transaction.setDescription(RequestUtil.getString(request, "description"));
	    transaction.setInvoiceNo(RequestUtil.getString(request, "invoiceNo"));
	    transaction.setPayer(RequestUtil.getString(request, "payer"));
	    transaction.setPayee(RequestUtil.getString(request, "payee"));

	    transaction.setCategoryId(RequestUtil.getInt(request, "categoryId"));
	    transaction.setDepartmentId(RequestUtil.getInt(request, "departmentId"));

	    transaction.setTransactionType(
	        RequestUtil.getString(request, "transactionType"));

	    transaction.setPaymentMethod(
	        RequestUtil.getString(request, "paymentMethod"));

	    transaction.setTotalAmount(
	        RequestUtil.getDouble(request, "totalAmount"));

	    transaction.setCurrency(
	        RequestUtil.getString(request, "currency"));

	    transaction.setDateTransaction(
	        Date.valueOf(RequestUtil.getString(request, "transactionDate")));

	    transaction.setStatus(
	        RequestUtil.getString(request, "status"));

	    return transaction;
	}

	private ArrayList<TransactionItemModel> buildTransactionItems(HttpServletRequest request) {
	    ArrayList<TransactionItemModel> items = new ArrayList<>();

	    String[] itemIds = request.getParameterValues("itemId");
	    String[] itemNames = request.getParameterValues("itemName");
	    String[] itemDescriptions = request.getParameterValues("itemDescription");
	    String[] itemUnitPrices = request.getParameterValues("itemUnitPrice");
	    String[] itemQuantities = request.getParameterValues("itemQuantity");

	    if (itemNames != null && itemDescriptions != null && itemUnitPrices != null && itemQuantities != null) {
	        for (int i = 0; i < itemNames.length; i++) {
	            TransactionItemModel item = new TransactionItemModel();
	            item.setTransactionItemId(itemIds != null && itemIds.length > i ? Integer.parseInt(itemIds[i]) : null);
	            item.setName(itemNames[i]);
	            item.setDescription(itemDescriptions[i]);
	            item.setUnitPrice(Double.parseDouble(itemUnitPrices[i]));
	            item.setQuantity(Integer.parseInt(itemQuantities[i]));
	            items.add(item);
	        }
	    }

	    return items;
	}
}