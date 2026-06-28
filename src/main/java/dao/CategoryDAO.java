package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.DBConnection;
import model.CategoryModel;
import util.ErrorUtil;

public class CategoryDAO {
	public CategoryModel getCategoryById(int categoryId) {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM category WHERE categoryId = " + categoryId);
			
			while (rs.next()) {
				CategoryModel category = new CategoryModel(rs.getInt("categoryId"),
						rs.getString("name"),
						rs.getInt("isGeneric") == 1, // Assuming isGeneric is stored as an integer (1 for true, 0 for false)
						rs.getInt("parentCategoryId"));
				
				return category; // Return the category object

			}
			conn.close();
			
		} catch (Exception e) {
			ErrorUtil.log("CategoryDAO.java", "getCategoryById", e);
		}
		
		return null; // Placeholder return statement
	}
	
	public static String getCategoryLabelById(int categoryId) {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT name FROM category WHERE categoryId = " + categoryId);
			
			if (rs.next()) {
				return rs.getString("name");
			}
			
			conn.close();
			
		} catch (Exception e) {
			ErrorUtil.log("CategoryDAO.java", "getCategoryLabelById", e);
			
		}
		return null;
	}
	
	public ArrayList<CategoryModel> getAllCategories() {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM category");
			
			ArrayList<CategoryModel> categories = new ArrayList<>();
			
			while (rs.next()) {
				CategoryModel category = new CategoryModel(rs.getInt("categoryId"),
						rs.getString("name"),
						rs.getInt("isGeneric") == 1, // Assuming isGeneric is stored as an integer (1 for true, 0 for false)
						rs.getInt("parentCategoryId"));
				
				categories.add(category);
			}
			
			conn.close();
			return categories;
			
		} catch (Exception e) {
			ErrorUtil.log("CategoryDAO.java", "getAllCategories", e);
		}
		
		return null; // Placeholder return statement
	}
}
