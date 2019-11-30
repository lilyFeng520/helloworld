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
		 //在这一个方法里能处理增删改查所有功能
		 //设置字符集
		 req.setCharacterEncoding("UTF-8");
		 resp.setCharacterEncoding("UTF-8");
		 String mn = req.getServletPath();
		 mn =  mn.substring(1);
		 mn = mn.substring(0, mn.length()-4);
		 try {
			//获取UserController这个类中的方法，mn是方法的名字，后面两个是方法中的参数,实现了从class对象中获取到method对象
			Method method = this.getClass().getDeclaredMethod(mn, HttpServletRequest.class,HttpServletResponse.class);
			//表示执行反射调用，调用当前Method对象所表示的方法
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
	 * 实现首页的模糊查询
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username =  req.getParameter("username");
		String address =  req.getParameter("address");
		String phoneNo =  req.getParameter("phoneNo");
		username = username==null?"":username.replaceAll("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~!@#￥……&*（）&mdash;——|{}【】‘；：“”’。，、？]", "");
		address = address==null?"":address.replaceAll("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~!@#￥……&*（）&mdash;——|{}【】‘；：“”’。，、？]", "");
		phoneNo = phoneNo==null?"":phoneNo.replaceAll("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~!@#￥……&*（）&mdash;——|{}【】‘；：“”’。，、？]", "");		
		List<User> list =  userService.query(username,address,phoneNo);
		req.setAttribute("userList", list);    //把结果集放到req的属性空间里
		req.getRequestDispatcher("/main.jsp").forward(req, resp);    //把整个req，resp注入到jsp页面
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
		//通过id把数据库中原来的user信息拿到
		User user = userService.get(id);
		
		String yUsername = user.getUsername();    //旧名字
		String xUsername = req.getParameter("username");     //新名字
		
		long count =  userService.getCountByName(xUsername);
		if(!xUsername.equals(yUsername) && count>0) {  //首先新名字和旧名字不一样，还能在数据库中找到同名记录，证明名字有冲突
			req.setAttribute("note", xUsername + ",这个名字已被使用，请更换！");
			req.getRequestDispatcher("/update.udo?id="+id).forward(req, resp);
			return;   //表示不用再去执行下面的代码
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
	 * 用户登录方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,UnsupportedEncodingException {
		//第一个步骤，拿到三个参数
		String username  = req.getParameter("username");
		String pasword = req.getParameter("pasword");
		String expiredays  = req.getParameter("expiredays");
		
		Cookie[] cookies = req.getCookies();
		
		boolean login = false;  //是否登录的标记：true，已经登录，false，还没有登录
		String account = null;  //登录账号
		String ssid = null;  //这是一个标记，通过cookie拿到的一个判断用户该不该成功登录的标志
		
		
		
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
		
		if (!login) {   //login:false 取反true:用户还没登录呢
			//用户第一次访问过来
			User user =  userService.login(username,pasword);  //通过访问数据库，来判断用户的用户名和密码对不对
			//userService里的login方法，判断用户名和密码对不对，对：返回User对象，不对：返回null
			if (user != null) {   //对的：返回登录成功
				expiredays = expiredays==null?"":expiredays;
				switch (expiredays) {
				case "7": //选择了记住我一周
					//创建一个cookie对象，设置cookie对象的失效时间为7天
					CookieUtils.createCookie(username, req, resp, 7*24*60*60);
					break;
				case "30": //选择了记住我一个月
					//创建一个cookie对象，设置cookie对象的失效时间为30天
					CookieUtils.createCookie(username, req, resp, 30*24*60*60);
					break;
				case "100": //选择了记住我到永远
					//创建一个cookie对象，设置cookie对象的失效时间为Integer.Max...	
					CookieUtils.createCookie(username, req, resp, Integer.MAX_VALUE);
					break;
				default:
					//创建一个cookie对象，设置cookie对象的失效时间为-1,关闭浏览器，cookie就失效
					CookieUtils.createCookie(username, req, resp, -1);
					break;
				}
				
				req.getSession().setAttribute("user", user.getUsername());
				//把在线状态online表里的username从游客改为真正的username
				HttpSession session = req.getSession();
				Online ol  = onlineService.getOnlineBySsid(session.getId());
				if (ol!=null) {
					ol.setUsername(username);
					onlineService.updateOnline(ol);
				}							
				//登录成功了，准许客户进入main.jsp页面
				resp.sendRedirect(req.getContextPath() + "/main.jsp");
			}else {
				req.setAttribute("note", "用户名或密码是错误的！");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		}else {
			req.getSession().setAttribute("user", username);
			//把在线状态online表里的username从游客改为真正的username
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
		//把记录登录状态的cookie删除
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
		
		//把记录登录状态的session删除
		 HttpSession session = req.getSession();
		 if (session!=null ) {
			session.removeAttribute("user");
		}
		
		 //退出登录以后，跳转到login.jsp
		 resp.sendRedirect(req.getContextPath() +"/login.jsp");
	}
	
	/**
	 * 显示所有在线用户的信息
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
