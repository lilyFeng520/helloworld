package mvcproject.dao;

import java.sql.Connection;
import java.util.List;

import mvcproject.model.User;

/**
 *接口制定规则，只定义方法，不去实现，UserDao：定义与User数据表有关系的操作方法
 * @author Administrator
 *
 */
public interface UserDao {
     
	/**
	 * 实现插入一条新用户信息数据
	 * @param user
	 * @return
	 */
	public int save(User user);
	
	/**
	 * 根据用户编号删除对应的用户数据
	 * @param id
	 * @return
	 */
	public int deleteUserById(int id);
	
	/**
	 * 根据用户编号修改对应的用户数据
	 * @param id
	 * @return
	 */
	public int  updateUserById(User user);
	
	
	/**
	 * 根据用户编号来获取一条用户数据，封装成类User的一个对象
	 * @param id
	 * @return
	 */
	public User get(int id);
	
	/**
	 * 支持事务
	 * @param conn
	 * @param id
	 * @return
	 */
	public User get(Connection conn,int id);
	
	
	/**
	 * 获取所有的用户数据
	 * @return
	 */
	public List<User> getListAll();
	
	/**
	 * 查询指定用户名的用户有多少条
	 * @param name
	 * @return
	 */
	public long getCountByName(String username);
    
	
	/**
	 * Dao层里实现模糊查询的方法
	 * @param username
	 * @param address
	 * @param phoneNo
	 * @return
	 */
	public List<User> query(String username, String address, String phoneNo);

	/**
	 * Dao层实现根据用户名和密码查询数据库中用户记录的方法
	 * @param username
	 * @param pasword
	 * @return
	 */
	public User getUserByUp(String username, String pasword);
	
}
