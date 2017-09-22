package com.scm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
/**
 * ���ݿ����ӹ���
 * @author wu
 *
 */
public class DBUtils {
	private static String driverClass;
	private static String url;
	private static String username;
	private static String password;
	static{
		ResourceBundle bundle = ResourceBundle.getBundle("dbinfo");
		 driverClass = bundle.getString("driverClass");
		 url=bundle.getString("url");
		 username=bundle.getString("username");
		 password=bundle.getString("password");
		try {
			//��������
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//�õ�����
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//�ر���Դ
	public static void closeAll(ResultSet rs,Statement stmt,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
