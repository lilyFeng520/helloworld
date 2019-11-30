package mvcproject.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvcproject.utils.CookieUtils;

public class AutoLoginFilter extends HttpFilter {
      
	 @Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//具体的拦截功能在这里编写
		//实现自动登录
		//基本思路：判断请求有无cookie，userKey，ssid，有的话判断userKey和ssid是否正确，正确的话就
		//实现自动登录，直接跳转到main.jsp页面，同时把userKey值放到session里，实现了自动登录的效果			 
		//没有cookie，没有关键cookie，直接转发它的请求，让页面正常执行登录操作
		Cookie[] cookies =  req.getCookies();
		if (cookies != null && cookies.length > 0) {
			String username = null;
			String ssid = null;
			for(Cookie c : cookies ) {
				if(c.getName().equals("userKey")) {
					username = c.getValue();
				}
				if(c.getName().equals("ssid")) {
					ssid = c.getValue();
				}
			}
			
			if (username != null && ssid != null && ssid.equals(CookieUtils.md5Encrypt(username))) {  //true:证明登录过，而且，选择了记住我，我们就要实现自动登录				
				HttpSession session  = req.getSession();
				session.setAttribute("user", username);
				resp.sendRedirect(req.getContextPath() + "/main.jsp");  //到这里，实现了自动登录
			}else {
				chain.doFilter(req, resp);   //放行，正常显示login.jsp页面
			}
						
			
		}else {
			chain.doFilter(req, resp);   //放行，正常显示login.jsp页面
		}
				
	}
	
}
