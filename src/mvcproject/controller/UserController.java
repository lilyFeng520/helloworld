package mvcproject.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.deploy.LoginConfig;

import mvcproject.model.Online;
import mvcproject.model.User;
import mvcproject.service.FactoryService;
import mvcproject.service.OnlineService;
import mvcproject.service.UserService;
import mvcproject.utils.CookieUtils;

@SuppressWarnings("unused")
@WebServlet(urlPatterns = {"*.udo"})
public class UserController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	UserService userService = FactoryService.getUserService();
	OnlineService onlineService  = FactoryService.getOnlineService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 //����һ���������ܴ�����ɾ�Ĳ����й���
		 //�����ַ���
		 req.setCharacterEncoding("UTF-8");
		 resp.setCharacterEncoding("UTF-8");
		 String mn = req.getServletPath();
		 mn =  mn.substring(1);
		 mn = mn.substring(0, mn.length()-4);
		 try {
			//��ȡUserController������еķ�����mn�Ƿ��������֣����������Ƿ����еĲ���,ʵ���˴�class�����л�ȡ��method����
			Method method = this.getClass().getDeclaredMethod(mn, HttpServletRequest.class,HttpServletResponse.class);
			//��ʾִ�з�����ã����õ�ǰMethod��������ʾ�ķ���
			method.invoke(this, req,resp);
		} catch (Exception e) {			
			e.printStackTrace();
		} 
	}
	
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		user.setUsername(req.getParameter("username"));
		user.setPasword(req.getParameter("pasword"));
		user.setAddress(req.getParameter("address"));
		user.setPhoneNo(req.getParameter("phoneNo"));
		user.setRegDate(new Date());
		
		int rows =  userService.save(user);
		if (rows>0) {
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		}else {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}
	
	/**
	 * ʵ����ҳ��ģ����ѯ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username =  req.getParameter("username");
		String address =  req.getParameter("address");
		String phoneNo =  req.getParameter("phoneNo");
		username = username==null?"":username.replaceAll("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~!@#������&*����&mdash;����|{}������������������������]", "");
		address = address==null?"":address.replaceAll("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~!@#������&*����&mdash;����|{}������������������������]", "");
		phoneNo = phoneNo==null?"":phoneNo.replaceAll("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~!@#������&*����&mdash;����|{}������������������������]", "");		
		List<User> list =  userService.query(username,address,phoneNo);
		req.setAttribute("userList", list);    //�ѽ�����ŵ�req�����Կռ���
		req.getRequestDispatcher("/main.jsp").forward(req, resp);    //������req��respע�뵽jspҳ��
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));		
		int rows = userService.deleteUserById(id);
		if (rows>0) {
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		}else {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		User user = userService.get(id);
		req.setAttribute("user", user);
		req.getRequestDispatcher("/update.jsp").forward(req, resp);
	}
	
	private void updatedo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		//ͨ��id�����ݿ���ԭ����user��Ϣ�õ�
		User user = userService.get(id);
		
		String yUsername = user.getUsername();    //������
		String xUsername = req.getParameter("username");     //������
		
		long count =  userService.getCountByName(xUsername);
		if(!xUsername.equals(yUsername) && count>0) {  //���������ֺ;����ֲ�һ�������������ݿ����ҵ�ͬ����¼��֤�������г�ͻ
			req.setAttribute("note", xUsername + ",��������ѱ�ʹ�ã��������");
			req.getRequestDispatcher("/update.udo?id="+id).forward(req, resp);
			return;   //��ʾ������ȥִ������Ĵ���
		}
		
		user.setUsername(xUsername);
		user.setPasword(req.getParameter("pasword"));
		user.setAddress(req.getParameter("address"));
		user.setPhoneNo(req.getParameter("phoneNo"));
		
		int rows =  userService.updateUserById(user);
		if (rows>0) {
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		}else {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}
	
	private void b(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/path/b.jsp").forward(req, resp);
	}
	
	/**
	 * �û���¼����
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,UnsupportedEncodingException {
		//��һ�����裬�õ���������
		String username  = req.getParameter("username");
		String pasword = req.getParameter("pasword");
		String expiredays  = req.getParameter("expiredays");
		
		Cookie[] cookies = req.getCookies();
		
		boolean login = false;  //�Ƿ��¼�ı�ǣ�true���Ѿ���¼��false����û�е�¼
		String account = null;  //��¼�˺�
		String ssid = null;  //����һ����ǣ�ͨ��cookie�õ���һ���ж��û��ò��óɹ���¼�ı�־
		
		
		
		if(cookies!=null && cookies.length >0) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("userKey")) {
					account = cookie.getValue();
				}
				
				if (cookie.getName().equals("ssid")) {
					ssid = cookie.getValue();
				}
				
			}
		}
		
		if (account != null && ssid != null) {
			login  = ssid.equals(CookieUtils.md5Encrypt(username));
		}
		
		System.out.println("login:" + login);
		
		if (!login) {   //login:false ȡ��true:�û���û��¼��
			//�û���һ�η��ʹ���
			User user =  userService.login(username,pasword);  //ͨ���������ݿ⣬���ж��û����û���������Բ���
			//userService���login�������ж��û���������Բ��ԣ��ԣ�����User���󣬲��ԣ�����null
			if (user != null) {   //�Եģ����ص�¼�ɹ�
				expiredays = expiredays==null?"":expiredays;
				switch (expiredays) {
				case "7": //ѡ���˼�ס��һ��
					//����һ��cookie��������cookie�����ʧЧʱ��Ϊ7��
					CookieUtils.createCookie(username, req, resp, 7*24*60*60);
					break;
				case "30": //ѡ���˼�ס��һ����
					//����һ��cookie��������cookie�����ʧЧʱ��Ϊ30��
					CookieUtils.createCookie(username, req, resp, 30*24*60*60);
					break;
				case "100": //ѡ���˼�ס�ҵ���Զ
					//����һ��cookie��������cookie�����ʧЧʱ��ΪInteger.Max...	
					CookieUtils.createCookie(username, req, resp, Integer.MAX_VALUE);
					break;
				default:
					//����һ��cookie��������cookie�����ʧЧʱ��Ϊ-1,�ر��������cookie��ʧЧ
					CookieUtils.createCookie(username, req, resp, -1);
					break;
				}
				
				req.getSession().setAttribute("user", user.getUsername());
				//������״̬online�����username���ο͸�Ϊ������username
				HttpSession session = req.getSession();
				Online ol  = onlineService.getOnlineBySsid(session.getId());
				if (ol!=null) {
					ol.setUsername(username);
					onlineService.updateOnline(ol);
				}							
				//��¼�ɹ��ˣ�׼��ͻ�����main.jspҳ��
				resp.sendRedirect(req.getContextPath() + "/main.jsp");
			}else {
				req.setAttribute("note", "�û����������Ǵ���ģ�");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		}else {
			req.getSession().setAttribute("user", username);
			//������״̬online�����username���ο͸�Ϊ������username
			HttpSession session = req.getSession();
			Online ol  = onlineService.getOnlineBySsid(session.getId());
			if (ol!=null) {
				ol.setUsername(username);
				onlineService.updateOnline(ol);
			}
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		}
				
	 }
	
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�Ѽ�¼��¼״̬��cookieɾ��
		Cookie[] cookies =  req.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("userKey")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
				if (cookie.getName().equals("ssid")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}
		}
		
		//�Ѽ�¼��¼״̬��sessionɾ��
		 HttpSession session = req.getSession();
		 if (session!=null ) {
			session.removeAttribute("user");
		}
		
		 //�˳���¼�Ժ���ת��login.jsp
		 resp.sendRedirect(req.getContextPath() +"/login.jsp");
	}
	
	/**
	 * ��ʾ���������û�����Ϣ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void online(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Online> list = onlineService.getAllOnline();
		req.setAttribute("online", list);
		req.getRequestDispatcher("/online.jsp").forward(req, resp);
	}
}
