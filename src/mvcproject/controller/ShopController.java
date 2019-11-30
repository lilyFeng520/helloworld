package mvcproject.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"*.pdo"})
public class ShopController extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
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
	
	
	private void shopping(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pname = req.getParameter("pname");
		req.setAttribute("p", pname);
		req.getRequestDispatcher("/productdetails.jsp").forward(req, resp);
		
	}
	
	private void addcart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pname = req.getParameter("pname");
		
		//添加到购物车
		HttpSession session = req.getSession(true);
		
		List<String> products  = (List<String>)session.getAttribute("car");
		
		if (products==null) {
			products = new ArrayList<>();
		}
		
		products.add(pname);
		
		session.setAttribute("car", products);
		
		resp.setCharacterEncoding("UTF-8");
		
		resp.sendRedirect(req.getContextPath() + "/shoppingcart.jsp");
		
	}
	
	
}
