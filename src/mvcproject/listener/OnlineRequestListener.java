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
 * ����˼·��
 * 1.�����������ʱ�򣬼�¼�����ߵ���Ϣ���жϷ����������ݿ��е����߼�¼�Ƿ��Ѿ����ڣ����������¸��û��ķ���ʱ�䣬����ҳ�棬�粻���ڣ������һ����¼�������ݿ�
 * 2.��¼�����û���ʱ�򣬼�¼���ʹ�����ʱ�䣬�涨10���ӵ�ʱ�䣬����û���10������û���κβ���������Ϊ�û��Ѿ����ߣ��������ݱ���ɾ�����߼�¼
 * 3.�ڷ����ߵ�¼�ɹ��󣬽�online���ݱ����username�����ο͸�Ϊ�������û���username
 * ServletContextListener��webӦ��������ʱ��ÿ��5���Ӽ����ڵ��û�����ִ��ɾ������
 * @author Administrator
 *
 */

@WebListener
public class OnlineRequestListener implements ServletRequestListener {
    
	//Service��������������ݿ�
	OnlineService onlineService = FactoryService.getOnlineService();
	
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		//1.ͨ������õ������ߵĻ�����Ϣ
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		HttpSession session =  request.getSession();
		//��ȡsessionid
		String ssid =  session.getId();
		//��ȡ�����ߵ�ip��ַ
		String ip = request.getRemoteAddr();
		//��ȡ���������ڷ��ʵ�ҳ��
		String page = request.getRequestURI();
		//��ȡ�û�������
		String username = (String)session.getAttribute("user");
		username = username==null?"�ο�":username;
		
		//�������ݿ�֮ǰ�����õ�����Ϣ��װ��һ��online����
		Online ol = new Online();
		ol.setSsid(ssid);
		ol.setPage(page);
		ol.setIp(ip);
		ol.setUsername(username);
		ol.setTime(new Date());
		
		//�������ݿ⣬����Ϣ�ŵ����ݿ⣬�У�����time��û�У�����һ����¼
		//1.����ssid����ѯ���ݿ⣬��û�м�¼
		Online online =  onlineService.getOnlineBySsid(ssid);
		if (online!=null) {
			//2.����������¼���time
			online.setTime(new Date());
			online.setPage(page);
			onlineService.updateOnline(online);
		}else {
			onlineService.insertOnline(ol);
		}
				
	}

    
}
