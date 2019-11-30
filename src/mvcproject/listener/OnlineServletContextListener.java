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
     * 10���ӣ������߳������õ�ʱ����룬û�в�������Ϊ����
     */
	public final int MAX_MILLIS = 10*60*1000; 
	OnlineService onlineService = FactoryService.getOnlineService();
	
	
    public void contextDestroyed(ServletContextEvent arg0)  { 
         
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
        //����Ǵ�ų�ʱ��������Ϣ�ļ���
    	List<Online> expiresUserList= new ArrayList<Online>();
    	//������������ʱ��ͱ�ִ��
    	//��ʱ��,ÿ��5*1000����ִ��һ�κ����listener
    	new Timer(5*1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				List<Online> list =  onlineService.getAllOnline();
				if (list!=null && list.size()>0) {
					for (Online ol:list) {
						//��Date���͵�ʱ��timeǿ��ת����ʱ���
						if ((System.currentTimeMillis()-Long.parseLong(ol.getTime().toString()))>MAX_MILLIS) {
							expiresUserList.add(ol);
						}						
					}					
					//�����ݿ���ɾ���������ķ�������Ϣ
					onlineService.deleteExpiresOnline(expiresUserList);
					//�����ݿ⵱��ɾ����ʱ�ķ�������Ϣ
					expiresUserList.clear();
				}
			}
		}).start();
    	
    }
	
}
