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
	
	public void createTransaction(TransactionModel transaction) {
		// Code to create a new transaction in the database
		
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
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

			pstmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void updateTransaction(TransactionModel transaction) {
		// Code to update the transaction in the database
		 
		 try {
				Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				
				TransactionModel existingTransaction = getTransactionById(transaction.getTransactionId());
				
				if (existingTransaction == null) {
					System.out.println("Transaction with ID " + transaction.getTransactionId() + " does not exist.");
					return;
				}
				
				transaction.setTransactionId(existingTransaction.getTransactionId()); // Ensure the transaction ID is set correctly
				
				String sql = "UPDATE transaction SET name=?, description=?, invoiceNo=?, payer=?, payee=?, categoryId=?, departmentId=?, transactionType=?, paymentMethod=?, totalAmount=?, currency=?, dateTransaction=?, status=?, createdBy=?, verifiedBy=? WHERE transactionId=?";
				sql = sql.replaceFirst("\\?", "'" + transaction.getName() + "'");
				stmt.executeUpdate(sql);
				conn.close();
			} catch (Exception e) {
				System.out.println(e);
			}
	}
	
	public void deleteTransaction(int transactionId) {
		// Code to delete a transaction from the database by its ID
	}
}
