package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TransactionModel;
import model.TransactionItemModel;
import util.RequestUtil;


import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import dao.TransactionDAO;
import dao.TransactionItemDAO;

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
		case "create":
				// Call the method to create a transaction
				createTransaction(request, response, false);
				break;
				
			case "update":
				// Call the method to update a transaction
				updateTransaction(request, response, false);
				break;
				
			case "delete":
				// Call the method to delete a transaction
				deleteTransaction(request, response);
				break;
			
			case "submit":
				// Call the method to submit a transaction
				submitTransaction(request, response);
				break;	
				
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
		}
	}
	
	private void createTransaction(HttpServletRequest request, HttpServletResponse response, boolean isSubmit) throws ServletException, IOException {
		
		TransactionModel transaction = buildTransaction(request, true);
		ArrayList<TransactionItemModel> items = buildTransactionItems(request);
		
		transaction.setStatus(isSubmit ? "pending" : "draft");
		transaction.setDepartmentId(3);
		transaction.setCreatedBy(3);
		transaction.setVerifiedBy(4);
		
		
		// Save the transaction and its items to the database (this is just a placeholder)
		TransactionDAO transactionDAO = new TransactionDAO();
		Integer updatedTransactionId = transactionDAO.createTransaction(transaction);
		
		TransactionItemDAO transactionItemDAO = new TransactionItemDAO();
		transactionItemDAO.upsertAllTransactionItems(items, updatedTransactionId);

		response.sendRedirect("staff-transaction.jsp"); // Redirect to a success page after creation
	} 
	
	private void updateTransaction(HttpServletRequest request, HttpServletResponse response, boolean isSubmit) throws ServletException, IOException {

		TransactionModel transaction = buildTransaction(request, false);
		ArrayList<TransactionItemModel> items = buildTransactionItems(request);

		transaction.setStatus(isSubmit ? "pending" : "draft");
		transaction.setDepartmentId(3);
		transaction.setCreatedBy(3);
		transaction.setVerifiedBy(4);
		
		
		// Save the transaction and its items to the database (this is just a placeholder)
		TransactionDAO transactionDAO = new TransactionDAO();
		Integer updatedTransactionId = transactionDAO.updateTransaction(transaction);
		
		TransactionItemDAO transactionItemDAO = new TransactionItemDAO();
		transactionItemDAO.upsertAllTransactionItems(items, updatedTransactionId);

		response.sendRedirect("staff-transaction.jsp"); // Redirect to a success page after creation
	} 
	
	private void submitTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		updateTransaction(request, response, true);
	}
	
	private void deleteTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int transactionId = RequestUtil.getInt(request, "transactionId");
	    
	    TransactionDAO transactionDAO = new TransactionDAO();
	    boolean success = transactionDAO.deleteTransaction(transactionId);
	    
	    if (success) {
	    	response.sendRedirect("staff-transaction.jsp"); // Redirect to a success page after creation
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