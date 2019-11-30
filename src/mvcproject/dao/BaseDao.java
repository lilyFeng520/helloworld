package mvcproject.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import mvcproject.utils.JdbcUtils;

/**
 * ����һ����Dao������Ļ����࣬�������ڱ������dao�࣬UserDao��ȥ�̳������ã�����new BaseDao()��ֱ����
 * @author Administrator
 *
 * @param <T>�����Ҫ�����������ݱ�ӳ�䵽java������java�࣬User
 */
public class BaseDao<T> {
     
	 QueryRunner queryRunner = new QueryRunner();
	 
	 private Class<T> clazz;
	 
	 public BaseDao() {
		//��baseDao�Ĺ��췽������ʼ��clazz����,User   User.class
		Type superType =  this.getClass().getGenericSuperclass(); //getGenericSuperclass�������õ������ߵĸ��������
		if (superType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) superType;
			Type[] tarry = pt.getActualTypeArguments(); //����һ���������飬���ݵĵ�һ��Ԫ�ؾ�������Ҫ�ģ�T��User.class
			if (tarry[0] instanceof Class) {
				clazz =  (Class<T>) tarry[0];
			}			
		}
	}
	
	/**
	 * ��ѯ���ݱ�ȡ��sql���Ľ�����ĵ�һ�����ݣ���װ��һ����Ķ��󷵻أ���֧������
	 * �õ�dbUtils������
	 * null��λ�ã�Ӧ�ô���BaseDao<T>��ߵ�T�������õ�ʱ������͵�class
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql,Object... args) {
		Connection conn=null;
		T entity = null;
		try {
			//��conn
			conn=JdbcUtils.getConnection();
			entity = queryRunner.query(conn,sql,new BeanHandler<T>(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.closeConn(conn);
		}
		return entity;
	}
	
	
	/**
	 * ��ѯ���ݱ�ȡ��sql���Ľ�����ĵ�һ�����ݣ���װ��һ����Ķ��󷵻أ�֧������
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(Connection conn,String sql,Object... args) {		
		T entity = null;
		try {
		     entity = queryRunner.query(conn,sql,new BeanHandler<T>(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * ��ȡ������¼��ͨ�÷�����ͨ�ã��÷���ʵ�ֲ���ʵ��ͨ��
	 * @return
	 */
	public List<T> getList(String sql,Object...args){
		Connection conn=null;
		List<T> list = null;
		try {
			//��conn
			conn=JdbcUtils.getConnection();
			list = queryRunner.query(conn,sql,new BeanListHandler<T>(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.closeConn(conn);
		}
		return list;
	}
	
	
	/**
	 * ʵ��insert,update,deleteͨ�õĸ��·���
	 * @param sql
	 * @param args
	 * @return
	 */
	public int update(String sql,Object...args) {
		Connection conn=null;
		int rows = 0;
		try {
			//��conn
			conn=JdbcUtils.getConnection();
			rows = queryRunner.update(conn,sql,args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.closeConn(conn);
		}
		return rows;
	}
	
	/**
	 * ͨ�õķ���sql���Ľ��ֻ��һ����ֵ�����͵Ĳ�ѯ�������û��ĸ�����count(id)
	 * @param sql
	 * @param args
	 * @return
	 */
	public Object getValue(String sql,Object...args) {
		Connection conn=null;
		Object obj = 0;
		try {
			//��conn
			conn=JdbcUtils.getConnection();
			obj = queryRunner.query(conn,sql,new ScalarHandler(),args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.closeConn(conn);
		}
		return obj;
	}
	
	
}
