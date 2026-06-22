package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.DBConnection;
import model.CategoryModel;

public class CategoryDAO {
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
			System.out.println(e);
		}
		
		return null; // Placeholder return statement
	}
}
