package com.hemliv.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 使用C3P0连接�?
 * @author Administrator
 *
 */
public class C3P0Utils {
	private static ComboPooledDataSource dataSource= new ComboPooledDataSource();
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
}
