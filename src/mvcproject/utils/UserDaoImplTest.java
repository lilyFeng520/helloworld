package mvcproject.utils;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import mvcproject.dao.UserDao;
import mvcproject.dao.UserDaoImpl;
import mvcproject.model.User;

public class UserDaoImplTest {
    
	UserDao userDao = new UserDaoImpl(); 
	@Test
	public void testGetInt() {
		Connection conn = JdbcUtils.getConnection();
		User user = null; 		
		try {
			conn.setAutoCommit(false);
			user = userDao.get(conn,4);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			JdbcUtils.closeConn(conn);
		}		 
		System.out.println(user);
	}

}
	