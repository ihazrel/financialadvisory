/*
 * import jakarta.servlet.*; import jakarta.servlet.annotation.WebFilter; import
 * jakarta.servlet.http.*;
 * 
 * import java.io.IOException;
 * 
 * @WebFilter("/*") public class AuthFilter implements Filter {
 * 
 * @Override public void doFilter(ServletRequest request, ServletResponse
 * response, FilterChain chain) throws IOException, ServletException {
 * 
 * HttpServletRequest req = (HttpServletRequest) request; HttpServletResponse
 * res = (HttpServletResponse) response;
 * 
 * String path = req.getRequestURI(); String contextPath = req.getContextPath();
 * String url = path.substring(contextPath.length());
 * 
 * HttpSession session = req.getSession(false);
 * 
 * // allow public resources if (url.startsWith("/login.jsp") ||
 * url.startsWith("/UserController") || url.startsWith("/css/") ||
 * url.startsWith("/js/") || url.startsWith("/images/") ||
 * url.startsWith("/bootstrap/")) {
 * 
 * chain.doFilter(request, response); return; }
 * 
 * // check login if (session == null || session.getAttribute("user") == null) {
 * 
 * // FLASH MESSAGE (ONE TIME) res.sendRedirect(contextPath +
 * "/login.jsp?error=loginRequired"); return; }
 * 
 * chain.doFilter(request, response); } }
 */