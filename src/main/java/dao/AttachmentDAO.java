package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connection.DBConnection;
import model.AttachmentModel;
import util.ErrorUtil;

public class AttachmentDAO {
	public ArrayList<AttachmentModel> getAttachmentsByTransactionId(int transactionId) {
		ArrayList<AttachmentModel> items = new ArrayList<>();
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM attachment WHERE transactionId = ? ORDER BY attachmentId")) {
			pstmt.setInt(1, transactionId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				AttachmentModel item = new AttachmentModel(rs.getInt("attachmentId"),
						rs.getInt("transactionId"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getString("fileType"),
						rs.getString("filePath"));

				
				items.add(item); // Add the item to the list
			}
			
		} catch (Exception e) {
			ErrorUtil.log("AttachmentDAO.java", "getAttachmentsByTransactionId", e);
		}
		
		return items; // Return the list of items
	}

	public AttachmentModel getAttachmentById(int attachmentId) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM attachment WHERE attachmentId = ?")) {
			pstmt.setInt(1, attachmentId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new AttachmentModel(rs.getInt("attachmentId"),
						rs.getInt("transactionId"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getString("fileType"),
						rs.getString("filePath"));
			}
		} catch (Exception e) {
			ErrorUtil.log("AttachmentDAO.java", "getAttachmentById", e);
		}

		return null;
	}

	public boolean addAttachment(AttachmentModel attachment) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO attachment (transactionId, name, description, fileType, filePath) VALUES (?, ?, ?, ?, ?)")) {
			pstmt.setInt(1, attachment.getTransactionId());
			pstmt.setString(2, attachment.getName());
			pstmt.setString(3, attachment.getDescription());
			pstmt.setString(4, attachment.getFileType());
			pstmt.setString(5, attachment.getFilePath());

			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			ErrorUtil.log("AttachmentDAO.java", "addAttachment", e);
			return false;
		}
	}

	public boolean deleteAttachment(int attachmentId) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM attachment WHERE attachmentId = ?")) {
			pstmt.setInt(1, attachmentId);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			ErrorUtil.log("AttachmentDAO.java", "deleteAttachment", e);
			return false;
		}
	}

	public boolean deleteAttachmentsByTransactionId(Integer transactionId) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM attachment WHERE transactionId = ?")) {
			pstmt.setInt(1, transactionId);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			ErrorUtil.log("AttachmentDAO.java", "deleteAttachmentsByTransactionId", e);
			return false;
		}
	}
}
