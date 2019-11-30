package mvcproject.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * jdbc工具类
 * @author Administrator
 *
 */
public class JdbcUtils {
     
	//数据库连接池，C3P0
	private static ComboPooledDataSource dataSource = null;
	static{ //静态代码块只会被执行一次
		dataSource = new ComboPooledDataSource("mysql");
//		dataSource = new ComboPooledDataSource();
		dataSource.setUser("root");
        dataSource.setPassword("admin");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mvcproject?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8");
        try {
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /*dataSource.setInitialPoolSize();
        dataSource.setAcquireIncrement();
        dataSource.setMinPoolSize();
        dataSource.setMaxPoolSize();
        dataSource.setMaxStatements();
        dataSource.setMaxIdleTime();
        dataSource.setIdleConnectionTestPeriod();
        dataSource.setAcquireRetryAttempts();*/

	}
	
	/**
	 * 获取到数据库mysql的数据连接对象conn
	 * @return
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn= dataSource.getConnection();
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 是通用的关闭数据库连接对象的方法
	 * @param conn
	 */
	public static void closeConn(Connection conn) {
		if(conn!= null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rollbackTransation(Connection conn){
		if(conn!=null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
