package auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.DBConnection;
import helper.RoleHelper;

@WebServlet(name = "LoginAsUserServlet", urlPatterns = {"/loginAsUser"})
public class LoginAsUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int roleParam = Integer.parseInt(request.getParameter("role"));
        if (id == 0) {
            response.sendRedirect(request.getContextPath() + "/user-list.jsp");
            return;
        }

        // Fetch user details from DB and set session attributes for impersonation
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT userId, name, email, roleId, departmentId FROM users WHERE userId = ?";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                    	
                        int userId = rs.getInt("userId");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        int roleId = rs.getInt("roleId");
                        int departmentId = rs.getInt("departmentId");
                        
                        UserModel user = new UserModel(userId, name, email, email, roleId, departmentId);
                        
                        request.getSession().setAttribute("user", user);
                        request.getSession().setAttribute("theme", RoleHelper.getTheme(user.getRoleId()));

                        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                        return;
                    } else {
                        // user not found
                        response.sendRedirect(request.getContextPath() + "/user-list.jsp?error=user_not_found");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
