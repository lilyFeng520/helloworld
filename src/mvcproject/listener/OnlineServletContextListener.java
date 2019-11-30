package mvcproject.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.swing.Timer;

import mvcproject.model.Online;
import mvcproject.service.FactoryService;
import mvcproject.service.OnlineService;

@WebListener
public class OnlineServletContextListener implements ServletContextListener {
    /**
     * 10分钟，访问者超过设置的时间毫秒，没有操作，认为离线
     */
	public final int MAX_MILLIS = 10*60*1000; 
	OnlineService onlineService = FactoryService.getOnlineService();
	
	
    public void contextDestroyed(ServletContextEvent arg0)  { 
         
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
        //这个是存放超时访问者信息的集合
    	List<Online> expiresUserList= new ArrayList<Online>();
    	//服务器启动的时候就被执行
    	//定时器,每隔5*1000毫秒执行一次后面的listener
    	new Timer(5*1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				List<Online> list =  onlineService.getAllOnline();
				if (list!=null && list.size()>0) {
					for (Online ol:list) {
						//把Date类型的时间time强行转换成时间戳
						if ((System.currentTimeMillis()-Long.parseLong(ol.getTime().toString()))>MAX_MILLIS) {
							expiresUserList.add(ol);
						}						
					}					
					//从数据库中删除掉超过的访问者信息
					onlineService.deleteExpiresOnline(expiresUserList);
					//在数据库当中删除超时的访问者信息
					expiresUserList.clear();
				}
			}
		}).start();
    	
    }
	
}
