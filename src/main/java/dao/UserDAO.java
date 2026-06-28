package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.UserModel;
import connection.DBConnection;
import util.ErrorUtil;

public class UserDAO {
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql = null;

	
	// this is for user login() function
    public UserModel login(String email, String password) {

        UserModel user = null;

        try {

            conn = DBConnection.getConnection();

            sql = "SELECT * FROM USERS WHERE EMAIL=? AND PASSWORD=?";

            ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if(rs.next()) {

                user = new UserModel();

                user.setUserId(rs.getInt("USERID"));

                user.setEmail(rs.getString("EMAIL"));

                user.setName(rs.getString("NAME"));

                user.setDepartmentId(rs.getInt("DEPARTMENTID"));

                user.setRoleId(rs.getInt("ROLEID"));
            }

            conn.close();

        } catch(Exception e) {
            ErrorUtil.log("UserDAO.java", "login", e);
        }

        return user; //return Object
    }
	
	// this is for user update() information
}
