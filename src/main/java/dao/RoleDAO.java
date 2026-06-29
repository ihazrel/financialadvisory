package dao;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.RoleModel;
import model.UserModel;
import util.ErrorUtil;
import connection.DBConnection;

public class RoleDAO {
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql = null;
	
	public static List<RoleModel> getAllRoles() {
		
        List<RoleModel> list = new ArrayList<RoleModel>();

        try {
        	
        	conn = DBConnection.getConnection();
        	sql = "SELECT roleid, name FROM role";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
            	RoleModel r = new RoleModel();
                r.setRoleId(rs.getInt("roleid"));
                r.setName(rs.getString("name"));
                //r.setDescription(rs.getString("description"));
                list.add(r);
            }

        } catch(Exception e) {
            ErrorUtil.log("RoleDAO.java", "getAllRoles", e);
        }

        return list;
    }
	
    // this is for dashboard, that will get the user name and role

}
