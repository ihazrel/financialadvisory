package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import connection.DBConnection;
import model.TransactionItemModel;

public class TransactionItemDAO {
	public ArrayList<TransactionItemModel> getTransactionItemsByTransactionId(int transactionId) {
		ArrayList<TransactionItemModel> items = new ArrayList<>();
		
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM transactionitem WHERE transactionId = " + transactionId);
			
			while (rs.next()) {
				TransactionItemModel item = new TransactionItemModel(rs.getInt("transactionItemId"),
						rs.getInt("transactionId"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getDouble("unitPrice"),
						rs.getInt("quantity"));
				
				items.add(item); // Add the item to the list
			}
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return items; // Return the list of items
	}
}
