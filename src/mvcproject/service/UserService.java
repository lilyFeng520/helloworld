package mvcproject.service;

import java.sql.Connection;
import java.util.List;

import mvcproject.model.User;

public interface UserService {
       
	/**
	 * ʵ�ֲ���һ�����û���Ϣ����
	 * @param user
	 * @return
	 */
	public int save(User user);
	
	/**
	 * �����û����ɾ����Ӧ���û�����
	 * @param id
	 * @return
	 */
	public int deleteUserById(int id);
	
	/**
	 * �����û�����޸Ķ�Ӧ���û�����
	 * @param id
	 * @return
	 */
	public int  updateUserById(User user);
	
	
	/**
	 * �����û��������ȡһ���û����ݣ���װ����User��һ������
	 * @param id
	 * @return
	 */
	public User get(int id);
	
	/**
	 * ֧������
	 * @param conn
	 * @param id
	 * @return
	 */
	public User getTransation(int id);
	
	
	/**
	 * ��ȡ���е��û�����
	 * @return
	 */
	public List<User> getListAll();
	
	/**
	 * ��ѯָ���û������û��ж�����
	 * @param name
	 * @return
	 */
	public long getCountByName(String username);
    
	/**
	 * �����û�ģ����ѯ�ķ���
	 * @param username
	 * @param address
	 * @param phoneNo
	 * @return
	 */
	public List<User> query(String username, String address, String phoneNo);

	/**
	 * �����ж��û��ĵ�¼��������Բ��Եķ���
	 * @param username
	 * @param pasword
	 * @return
	 */
	public User login(String username, String pasword);
	
	
}
