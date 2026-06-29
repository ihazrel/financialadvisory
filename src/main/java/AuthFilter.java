import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import model.UserModel;
import util.ErrorUtil;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String url = req.getRequestURI().substring(contextPath.length());

        HttpSession session = req.getSession(false);

        // PUBLIC PAGES
        // User can access without the need to login first
        if (url.startsWith("/login.jsp") ||
            url.startsWith("/index.jsp") ||
            url.startsWith("/admin/admin-user-details.jsp") ||
            url.startsWith("/LoginController") || // allow it because the LoginController need to process the info first, still consider as user == null
            url.startsWith("/css/") ||
            url.startsWith("/js/") ||
            url.startsWith("/images/") ||
            url.startsWith("/bootstrap/")) {

            chain.doFilter(request, response);
            return; // is needed because the same request is still running inside the filter, even though a redirect has already been sent to the browser.
        }

        // LOGIN CHECK
        if (session == null || session.getAttribute("user") == null) {

            session = req.getSession(true);
            session.setAttribute("error", ErrorUtil.format("AuthFilter.java", "doFilter", "Please login first"));

            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // GET USER INFO
        UserModel user = (UserModel) session.getAttribute("user");
        int roleId = user.getRoleId();

        // ROLE PROTECTION
        
        // SYSTEM ADMIN ONLY PAGES
        if (url.contains("/admin/") && roleId != 1) {

            session.setAttribute("error", ErrorUtil.format("AuthFilter.java", "doFilter", "Access denied"));

            res.sendRedirect(contextPath + "/admin/admin-user-list.jsp");
            return;
        }

            //res.sendRedirect(contextPath + "/admin/admin-user-list.jsp");
            //return;
        //}

        // FINANCIAL MANAGER ONLY PAGES
        if (url.contains("financialmanager-") && roleId != 2) {

            session.setAttribute("error", "Access denied");
            
            res.sendRedirect(contextPath + "/dashboard.jsp");
            return;
        }

        // DEPARTMENT MANAGER ONLY PAGES
        if (url.contains("/department/") && roleId != 3) {

            session.setAttribute("error", ErrorUtil.format("AuthFilter.java", "doFilter", "Access denied"));

            res.sendRedirect(contextPath + "/dashboard.jsp");
            return;
        }

        // STAFF ONLY PAGES
        if (url.contains("staff-") && roleId != 4) {

            session.setAttribute("error", ErrorUtil.format("AuthFilter.java", "doFilter", "Access denied"));

            res.sendRedirect(contextPath + "/dashboard.jsp");
            return;
        }

        // ALLOW ACCESS
        chain.doFilter(request, response);
    }
}
