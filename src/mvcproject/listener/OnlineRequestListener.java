package mvcproject.listener;

import java.util.Date;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mvcproject.model.Online;
import mvcproject.service.FactoryService;
import mvcproject.service.OnlineService;

/**
 * 基本思路：
 * 1.当请求进来的时候，记录访问者的信息，判断访问者在数据库中的在线记录是否已经存在，如存在则更新该用户的访问时间，访问页面，如不存在，则插入一条记录，到数据库
 * 2.记录在线用户的时候，记录访问过来的时间，规定10分钟的时间，如果用户在10分钟内没有任何操作，可认为用户已经离线，需在数据表中删除在线记录
 * 3.在访问者登录成功后，将online数据表里的username，从游客改为真正的用户名username
 * ServletContextListener，web应用启动的时候，每隔5秒钟检查过期的用户并且执行删除操作
 * @author Administrator
 *
 */

@WebListener
public class OnlineRequestListener implements ServletRequestListener {
    
	//Service对象，请求操作数据库
	OnlineService onlineService = FactoryService.getOnlineService();
	
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		//1.通过请求得到访问者的基本信息
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		HttpSession session =  request.getSession();
		//获取sessionid
		String ssid =  session.getId();
		//获取访问者的ip地址
		String ip = request.getRemoteAddr();
		//获取访问者正在访问的页面
		String page = request.getRequestURI();
		//获取用户的名称
		String username = (String)session.getAttribute("user");
		username = username==null?"游客":username;
		
		//操作数据库之前，把拿到的信息封装在一个online对象
		Online ol = new Online();
		ol.setSsid(ssid);
		ol.setPage(page);
		ol.setIp(ip);
		ol.setUsername(username);
		ol.setTime(new Date());
		
		//连接数据库，把信息放到数据库，有：更新time，没有：插入一条记录
		//1.根据ssid，查询数据库，有没有记录
		Online online =  onlineService.getOnlineBySsid(ssid);
		if (online!=null) {
			//2.更新这条记录里的time
			online.setTime(new Date());
			online.setPage(page);
			onlineService.updateOnline(online);
		}else {
			onlineService.insertOnline(ol);
		}
				
	}

    
}
