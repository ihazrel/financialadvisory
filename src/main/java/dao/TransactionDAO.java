package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import connection.DBConnection;
import model.TransactionModel;

public class TransactionDAO {

	public TransactionModel getTransactionById(int transactionId) {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM transaction WHERE transactionId = " + transactionId);
			
			while (rs.next()) {
				TransactionModel transaction = new TransactionModel(rs.getInt("transactionId"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getString("invoiceNo"),
						rs.getString("payer"),
						rs.getString("payee"),
						rs.getInt("categoryId"),
						rs.getInt("departmentId"),
						rs.getString("transactionType"),
						rs.getString("paymentMethod"),
						rs.getDouble("totalAmount"),
						rs.getString("currency"),
						rs.getDate("dateTransaction"),
						rs.getString("status"),
						rs.getInt("createdBy"),
						rs.getInt("verifiedBy"));
				
				return transaction; // Return the transaction object

			}
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null; // Placeholder return statement
	}

	public ArrayList<TransactionModel> getAllTransactions() {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM transaction");
			
			ArrayList<TransactionModel> transactions = new ArrayList<>();
			
			while (rs.next()) {
				TransactionModel transaction = new TransactionModel(rs.getInt("transactionId"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getString("invoiceNo"),
						rs.getString("payer"),
						rs.getString("payee"),
						rs.getInt("categoryId"),
						rs.getInt("departmentId"),
						rs.getString("transactionType"),
						rs.getString("paymentMethod"),
						rs.getDouble("totalAmount"),
						rs.getString("currency"),
						rs.getDate("dateTransaction"),
						rs.getString("status"),
						rs.getInt("createdBy"),
						rs.getInt("verifiedBy"));
				
				transactions.add(transaction);
			}
			
			conn.close();
			return transactions;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return new ArrayList<>(); // Placeholder return statement
	}
	
	public Integer createTransaction(TransactionModel transaction) {
		// Code to create a new transaction in the database
		
		try {
			Connection conn = DBConnection.getConnection();
			
			String sql = "INSERT INTO transaction (name, description, invoiceNo, payer, payee, categoryId, departmentId, transactionType, paymentMethod, totalAmount, currency, dateTransaction, status, createdBy, verifiedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, transaction.getName());
			pstmt.setString(2, transaction.getDescription());
			pstmt.setString(3, transaction.getInvoiceNo());
			pstmt.setString(4, transaction.getPayer());
			pstmt.setString(5, transaction.getPayee());
			pstmt.setInt(6, transaction.getCategoryId());
			pstmt.setInt(7, transaction.getDepartmentId());
			pstmt.setString(8, transaction.getTransactionType());
			pstmt.setString(9, transaction.getPaymentMethod());
			pstmt.setDouble(10, transaction.getTotalAmount());
			pstmt.setString(11, transaction.getCurrency());
			pstmt.setDate(12, (Date) transaction.getDateTransaction());
			pstmt.setString(13, transaction.getStatus());
			pstmt.setInt(14, transaction.getCreatedBy());
			pstmt.setInt(15, transaction.getVerifiedBy());

			boolean success = pstmt.executeUpdate() > 0;
			
			if (success) {
				sql = "SELECT transactionId from transaction ORDER BY transactionId DESC FETCH FIRST 1 row only";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					return rs.getInt("transactionId");
				}
			} else {
				return null; // Return null if the insert was not successful
			}

			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null; // Return null for now, you can modify this to return the generated transaction ID if needed
	}
	
	public Integer updateTransaction(TransactionModel transaction) {
		// Code to update the transaction in the database
		 
		 try {
				Connection conn = DBConnection.getConnection();
				
				TransactionModel existingTransaction = getTransactionById(transaction.getTransactionId());
				
				if (existingTransaction == null) {
					System.out.println("Transaction with ID " + transaction.getTransactionId() + " does not exist.");
					return null;
				}
				
				transaction.setTransactionId(existingTransaction.getTransactionId()); // Ensure the transaction ID is set correctly
				
				String sql = "UPDATE transaction SET name=?, description=?, invoiceNo=?, payer=?, payee=?, categoryId=?, departmentId=?, transactionType=?, paymentMethod=?, totalAmount=?, currency=?, dateTransaction=?, status=?, createdBy=?, verifiedBy=? WHERE transactionId=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, transaction.getName());
				pstmt.setString(2, transaction.getDescription());
				pstmt.setString(3, transaction.getInvoiceNo());
				pstmt.setString(4, transaction.getPayer());
				pstmt.setString(5, transaction.getPayee());
				pstmt.setInt(6, transaction.getCategoryId());
				pstmt.setInt(7, transaction.getDepartmentId());
				pstmt.setString(8, transaction.getTransactionType());
				pstmt.setString(9, transaction.getPaymentMethod());
				pstmt.setDouble(10, transaction.getTotalAmount());
				pstmt.setString(11, transaction.getCurrency());
				pstmt.setDate(12, (Date) transaction.getDateTransaction());
				pstmt.setString(13, transaction.getStatus());
				pstmt.setInt(14, transaction.getCreatedBy());
				pstmt.setInt(15, transaction.getVerifiedBy());
				pstmt.setInt(16, transaction.getTransactionId());
				
				if (pstmt.executeUpdate() > 0) {
					// Transaction updated successfully
					return existingTransaction.getTransactionId(); // Return the transaction ID
				} else {
					return null; // Return null if the update was not successful
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		 return null; // Return null if an error occurs
	}
	
	public boolean deleteTransaction(Integer transactionId) {
		// Code to delete a transaction from the database by its ID
		
		try {
				Connection conn = DBConnection.getConnection();
				
				TransactionModel existingTransaction = getTransactionById(transactionId);
				
				if (existingTransaction == null) {
					return false; // Transaction does not exist
				}
				
				TransactionItemDAO transactionItemDAO = new TransactionItemDAO();
				transactionItemDAO.deleteTransactionItemsByTransactionId(transactionId);

				String sql = "DELETE FROM transaction WHERE transactionId=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, transactionId);
				pstmt.executeUpdate();
				return true; // Return true to indicate successful deletion
				
			} catch (Exception e) {
				System.out.println(e);
			}
		return false; // Return false if an error occurs
	}
}
