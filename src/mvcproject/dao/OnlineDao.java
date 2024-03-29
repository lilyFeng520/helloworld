package mvcproject.dao;

import java.util.List;

import mvcproject.model.Online;

public interface OnlineDao {
    /**
     * 取出所有在线访问者的信息 
     * @return
     */
	public List<Online> getAllOnline();
	
	/**
	  * 插入一条online信息
	 * @param online
	 */
	public void insertOnline(Online online);
    
	/**
	 * 更新保存的online信息
	 * @param online
	 */
	public void updateOnline(Online online);
	
	/**
	 * 根据ssid删除下线用户的online信息
	 * @param ssid
	 */
	public void deleteExpiresOnline(String ssid);
	
	/**
	 * 根据ssid获取一条online在线信息
	 * @param ssid
	 * @return
	 */
	public Online getOnlineBySsid(String ssid);
	
}
