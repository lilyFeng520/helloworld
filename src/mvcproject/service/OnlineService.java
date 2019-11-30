package mvcproject.service;

import java.util.List;

import mvcproject.model.Online;

public interface OnlineService {

	/**
	 * ȡ���������߷����ߵ���Ϣ
	 * 
	 * @return
	 */
	public List<Online> getAllOnline();

	/**
	 * ����һ��online��Ϣ
	 * 
	 * @param online
	 */
	public void insertOnline(Online online);

	/**
	 * ���±����online��Ϣ
	 * 
	 * @param online
	 */
	public void updateOnline(Online online);

	/**
	 * ���ݹ�ʱ������Ϣ����ɾ�������û���online��Ϣ
	 * 
	 * @param session
	 */
	public void deleteExpiresOnline(List<Online> list);

	/**
	 * ����ssid��ȡһ��online������Ϣ
	 * 
	 * @param ssid
	 * @return
	 */
	public Online getOnlineBySsid(String ssid);
}
