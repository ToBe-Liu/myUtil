package com.lxh.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
	/**
	 * JDBCUtil������
	 */
	static Connection conn =null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static final String USER = ConfigManager.getInstance().getString("jdbc.user");
	static final String PASSWORD = ConfigManager.getInstance().getString("jdbc.password");
	static final String JDBCURL = ConfigManager.getInstance().getString("jdbc.jdbcUrl");
	static final String DRIVERCLASS = ConfigManager.getInstance().getString("jdbc.driverClass");
	//��ȡ����
	public static Connection getConnection(){
			try {
				Class.forName(DRIVERCLASS);
				conn = DriverManager.getConnection(JDBCURL, USER, PASSWORD);
			} catch (SQLException e) {
					e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return conn;
	}
	//��ѯ
	public static ResultSet query(String sql,Object[] objs){
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return rs;
	}
	//���� �޸� ɾ��
	public static int updata(String sql,Object[] objs){
		int rs = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			rs = ps.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return rs;
	}
	//�ر���Դ
	public static void close(){
		try {
			if(conn!=null){
			conn.close();
			System.out.println("�ر�JDBC���ӳɹ���");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
}