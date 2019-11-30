package mvcproject.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsLoginFilter extends HttpFilter{
    
	@Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
    		throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String path = req.getServletPath().substring(1);
		String autho = getFilterConfig().getInitParameter("authority");
		String noautho = getFilterConfig().getInitParameter("noautho"); 
		String[] strArr = autho.split(",");
		String[] noautoArr = noautho.split(",");
		for (String str : noautoArr) {
			if (path.equals(str)) {
				chain.doFilter(req, resp);
			}
		}
				
		HttpSession session  = req.getSession();
		for (String str : strArr) {
			if (str.equals(path)) {
				String username =  (String) session.getAttribute("user");
				if (username != null) {
					chain.doFilter(req, resp);
				}else {
					resp.sendRedirect(req.getContextPath() + "/login.jsp");
				}
			}
		}
		
    }
}
