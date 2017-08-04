package zxc.mysql.demo.dbbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import zxc.mysql.demo.conf.Context;


public class BaseDao
{
	private static Connection conn;

	public static Connection getOraConnect()
	{
		return getOraConnect(Context.MYSQL_DB_USER,
				Context.MYSQL_DB_PASSWORD);
	}

	public static Connection getOraConnect(String user, String password)
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			if (conn == null)
			{
				conn = DriverManager.getConnection(Context.JDBC_URL,
						user, password);
				conn.setAutoCommit(true);
				System.out.println("Connected to database");
			}

		} catch (SQLException se)
		{
			System.out.println("数据库连接失败！");
			se.printStackTrace();
			
		} catch (Exception e)
		{
			// e.printStackTrace();
		}
		return conn;
	}
}
