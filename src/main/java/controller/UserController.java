package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.UserDAO;
import model.UserModel;
import util.ErrorUtil;

import dao.RoleDAO;
import model.RoleModel;
/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        UserModel user = dao.login(email, password);

        //RoleDAO roleDAO = new RoleDAO();
        
        //List<Role> roleList = roleDAO.getAllRoles();
        
        
        
        if(user != null){
            HttpSession session = request.getSession();

            session.setAttribute("user",user);
            
            //session.setAttribute("roles", RoleDAO.getAllRoles());
            
            response.sendRedirect("dashboard.jsp");
            
        }else{
            request.getSession().setAttribute("error",
                    ErrorUtil.format("UserController.java", "doPost", "Invalid email or password"));
        	response.sendRedirect( "login.jsp?error=1" );
        }
        
	}
}
