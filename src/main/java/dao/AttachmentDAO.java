package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.DBConnection;
import model.AttachmentModel;

public class AttachmentDAO {
	public ArrayList<AttachmentModel> getAttachmentsByTransactionId(int transactionId) {
		ArrayList<AttachmentModel> items = new ArrayList<>();
		
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM attachment WHERE transactionId = " + transactionId);
			
			while (rs.next()) {
				AttachmentModel item = new AttachmentModel(rs.getInt("attachmentId"),
						rs.getInt("transactionId"),
						rs.getString("fileName"),
						rs.getString("fileDescription"),
						rs.getString("fileType"),
						rs.getString("filePath"));

				
				items.add(item); // Add the item to the list
			}
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return items; // Return the list of items
	}
}
