package mvcproject.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import mvcproject.utils.JdbcUtils;

public class JdbcUtilsTest {

	@Test
	public void testGetConnection() {
		Connection conn = JdbcUtils.getConnection();
		System.out.println(conn);
		
	}

}
