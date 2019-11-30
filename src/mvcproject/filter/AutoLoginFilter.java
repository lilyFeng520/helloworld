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
		//��������ع����������д
		//ʵ���Զ���¼
		//����˼·���ж���������cookie��userKey��ssid���еĻ��ж�userKey��ssid�Ƿ���ȷ����ȷ�Ļ���
		//ʵ���Զ���¼��ֱ����ת��main.jspҳ�棬ͬʱ��userKeyֵ�ŵ�session�ʵ�����Զ���¼��Ч��			 
		//û��cookie��û�йؼ�cookie��ֱ��ת������������ҳ������ִ�е�¼����
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
			
			if (username != null && ssid != null && ssid.equals(CookieUtils.md5Encrypt(username))) {  //true:֤����¼�������ң�ѡ���˼�ס�ң����Ǿ�Ҫʵ���Զ���¼				
				HttpSession session  = req.getSession();
				session.setAttribute("user", username);
				resp.sendRedirect(req.getContextPath() + "/main.jsp");  //�����ʵ�����Զ���¼
			}else {
				chain.doFilter(req, resp);   //���У�������ʾlogin.jspҳ��
			}
						
			
		}else {
			chain.doFilter(req, resp);   //���У�������ʾlogin.jspҳ��
		}
				
	}
	
}
