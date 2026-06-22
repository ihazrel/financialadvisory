package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TransactionModel;
import util.RequestUtil;

import java.io.IOException;
import java.sql.Date;

import dao.TransactionDAO;

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
		case "save":
				// Call the method to create a transaction
				if (request.getParameter("transactionId") == null || request.getParameter("transactionId").isEmpty()) {
					// If transactionId is null or empty, create a new transaction
					createTransaction(request, response);
				} else {
					// If transactionId is provided, update the existing transaction
					//updateTransaction(request, response);
				}
				break;
				
			case "update":
				// Call the method to update a transaction
				//updateTransaction(request, response);
				break;
			case "delete":
				// Call the method to delete a transaction
				//deleteTransaction(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
		}
	}
	
	private void createTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Implement the logic to create a transaction
		// For example, you can retrieve parameters from the request and save them to the database
		String name =  RequestUtil.getString(request, "title");
		String description = RequestUtil.getString(request, "description");
		String invoiceNo = RequestUtil.getString(request, "invoiceNo");
		String payer = RequestUtil.getString(request, "payer");
		String payee = RequestUtil.getString(request, "payee");
		
		int categoryId = RequestUtil.getInt(request, "categoryId");
		int departmentId = RequestUtil.getInt(request, "departmentId");
		
		String transactionType = RequestUtil.getString(request, "transactionType");
		String paymentMethod = RequestUtil.getString(request, "paymentMethod");
		
		double totalAmount = RequestUtil.getDouble(request, "totalAmount");
		
		String currency = RequestUtil.getString(request, "currency");
		String dateTransactionStr = RequestUtil.getString(request, "transactionDate");
		String status = RequestUtil.getString(request, "status");
		
		TransactionModel transaction = new TransactionModel(
				0, // transactionId will be auto-generated
				name,
				description,
				invoiceNo,
				payer,
				payee,
				categoryId,
				departmentId,
				transactionType,
				paymentMethod,
				totalAmount,
				currency,
				Date.valueOf(dateTransactionStr),
				status,
				0, // createdBy (you can set this based on the logged-in user)
				0  // verifiedBy (you can set this based on the logged-in user)
		);
		transaction.setName(name);
		transaction.setDescription(description);
		transaction.setInvoiceNo(invoiceNo);
		transaction.setPayer(payer);
		transaction.setPayee(payee);
		transaction.setCategoryId(categoryId);
		transaction.setDepartmentId(3);
		transaction.setTransactionType(transactionType);
		transaction.setPaymentMethod(paymentMethod);
		transaction.setTotalAmount(totalAmount);
		transaction.setCurrency(currency);
		transaction.setDateTransaction(Date.valueOf(dateTransactionStr));
		transaction.setStatus(status);
		transaction.setCreatedBy(3); // Set the createdBy field (you can replace 0 with the actual user ID)
		transaction.setVerifiedBy(4); // Set the verifiedBy field (you can replace 0 with the actual user ID)
		

		// Save the transaction to the database (this is just a placeholder)
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.createTransaction(transaction);

		response.sendRedirect("staff-transaction.jsp"); // Redirect to a success page after creation
	} 

}
