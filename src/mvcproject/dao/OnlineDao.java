package mvcproject.dao;

import java.util.List;

import mvcproject.model.Online;

public interface OnlineDao {
    /**
     * ȡ���������߷����ߵ���Ϣ 
     * @return
     */
	public List<Online> getAllOnline();
	
	/**
	  * ����һ��online��Ϣ
	 * @param online
	 */
	public void insertOnline(Online online);
    
	/**
	 * ���±����online��Ϣ
	 * @param online
	 */
	public void updateOnline(Online online);
	
	/**
	 * ����ssidɾ�������û���online��Ϣ
	 * @param ssid
	 */
	public void deleteExpiresOnline(String ssid);
	
	/**
	 * ����ssid��ȡһ��online������Ϣ
	 * @param ssid
	 * @return
	 */
	public Online getOnlineBySsid(String ssid);
	
}
