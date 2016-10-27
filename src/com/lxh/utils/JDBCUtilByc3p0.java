package com.lxh.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtilByc3p0 {
	/**
	 * JDBCUtil工具类
	 */
	static Connection conn =null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static DataSource dataSource = new  ComboPooledDataSource("c3p0"); 
	//获取链接
	public static Connection getConnection() throws SQLException{
		conn = dataSource.getConnection();
		return conn;
	}
	//查询
	public static ResultSet query(String sql,Object[] objs){
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return rs;
	}
	//增加 修改 删除
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
	//关闭资源
	public static void close(){
		try {
			if(conn!=null){
			conn.close();
			System.out.println("关闭JDBC链接成功！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
}
