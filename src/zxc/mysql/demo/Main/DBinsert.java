package zxc.mysql.demo.Main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import zxc.mysql.demo.conf.Context;
import zxc.mysql.demo.dbbase.BaseDao;

public class DBinsert
{
	/**
	 * 功能：Java读取txt文件的内容 
	 * 步骤：1：先获得文件句柄 
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 *  4：一行一行的输出。readline()。 
	 *  备注：需要考虑的是异常情况
	 */
	public static void main(String[] args)
	{
		try
		{
			BufferedReader bufferedReader = null;
			Connection conn = BaseDao.getOraConnect();
			try
			{
				String encoding = "utf-8";
				File file = new File(Context.FilePath);
				if (file.isFile() && file.exists())
				{ // 判断文件是否存在
					InputStreamReader read = new InputStreamReader(
							new FileInputStream(file), encoding);// 考虑到编码格式
					bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					int num=0;
					while ((lineTxt = bufferedReader.readLine()) != null)
					{
						num++;
						//System.out.println(lineTxt);
						String[] columns = lineTxt.split(",");
						/*if (columns[0].trim().equalsIgnoreCase("序号"))
						{
							continue;
						}*/
						PreparedStatement pstmt = conn
								.prepareStatement("insert into recommend(userid,movieid,weights)values(?,?,?)");
						pstmt.setString(1, columns[0]);
						pstmt.setString(2, columns[1]);
						pstmt.setString(3, columns[2]);
						pstmt.executeUpdate();
						System.out.println(num+"||"+pstmt.toString());
					}
					read.close();
				} else
				{
					System.out.println("找不到指定的文件");
				}
			} catch (Exception e)
			{
				System.out.println("读取文件内容出错");
				e.printStackTrace();
			} finally
			{
				try
				{
					conn.close();
					bufferedReader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception e)
		{
			System.out.println("数据库连接失败！");
			
			e.printStackTrace();
		}

	}

	
}
