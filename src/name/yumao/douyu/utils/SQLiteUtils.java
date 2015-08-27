package name.yumao.douyu.utils;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;


public class SQLiteUtils {
	private static String lastSelectID = null;
	private static Logger logger = Logger.getLogger(SQLiteUtils.class);
	public static void initDB(){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = SQLiteWriteBDS.getSqliteConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select count(*) from sqlite_master where type='table' and name='DouyuDB'");
			resultSet.next();
			if(resultSet.getString("count(*)").equals("1")){
			}else{
				//建表
				statement.executeUpdate("create table DouyuDB (id integer primary key autoincrement, snick string default null,content string default null,donate integer default '0',ctime datetime default '2000-1-1')");
			}
			freeConnection(resultSet, statement, connection);
		} catch (SQLException e) {
			logger.info(e.toString());
		} finally{
			freeConnection(resultSet, statement, connection);
		}
	}
	public static void addContent(String snick,String content){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = SQLiteWriteBDS.getSqliteConnection();
			statement = connection.prepareStatement("insert into DouyuDB(snick,content,ctime) values(?,?,strftime('%Y-%m-%d %H:%M:%f','now','localtime'))");
			statement.setString(1, snick);
			statement.setString(2, content);
			statement.executeUpdate();
			freeConnection(resultSet, statement, connection);
		} catch (SQLException e) {
			logger.info(e.toString());
		}finally{
			freeConnection(resultSet, statement, connection);
		}
	}
	public static void addDonateres(String snick,String ms){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = SQLiteWriteBDS.getSqliteConnection();
			statement = connection.createStatement();
			statement.executeUpdate("insert into DouyuDB(snick,donate,ctime) values('"+snick+"','"+ms+"',strftime('%Y-%m-%d %H:%M:%f','now','localtime'))");
			freeConnection(resultSet, statement, connection);
		} catch (SQLException e) {
			logger.info(e.toString());
		}finally{
			freeConnection(resultSet, statement, connection);
		}
	}
	//----------------------------------最好重写下------------------------------------------//
	public static JTable queryDonateList(JTable jTable){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String columnNames[] = {"排名","昵称","鱼丸数量"};
		String[][] data = new String[15][3];
		//获取数据进行date二元数组组合
		try {
			connection = SQLiteReadBDS.getSqliteConnection();
			int i = 0;
			statement = connection.createStatement();
			//初始化数组
			resultSet = statement.executeQuery("select count(*) from(select snick,sum(donate) as donarers from DouyuDB where donate <> 0 group by snick order by donarers  desc limit 15)");
			resultSet.next();
			data = new String[Integer.parseInt(resultSet.getString(1))][3];
			resultSet = statement.executeQuery("select snick,sum(donate) as donarers from DouyuDB where donate <> 0 group by snick order by donarers  desc limit 15");
			while(resultSet.next()){
				data[i][0] = (i+1)+"";
				data[i][1] = resultSet.getString("snick");
				data[i++][2] = resultSet.getString("donarers");
			}
			freeConnection(resultSet, statement, connection);
			i = 0;
			jTable = new JTable(data, columnNames);
			jTable.setBounds(0, 0, 290, 270);
			jTable.setBackground(new Color(0,0,0,0));
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
			jTable.setDefaultRenderer(Object.class, tcr);
			jTable.setFont(new Font("微软雅黑",Font.BOLD,15));
		}catch (SQLException e) {
			logger.info(e.toString());
		}finally{
			freeConnection(resultSet, statement, connection);
		}
		return jTable;
	}
	//----------------------------------------鱼丸答谢-------------------------------------------//
	public static HashMap<String,String> selectDonateresForLast5Sec(String waitTime){
		HashMap<String,String> donateresForLast5Sec = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = SQLiteReadBDS.getSqliteConnection();
			donateresForLast5Sec = new HashMap<String, String>();
			statement = connection.createStatement();
			resultSet = null;
			if(lastSelectID==null){
				resultSet = statement.executeQuery("select max(id) as id,snick,sum(donate) as donate_sum from DouyuDB where donate <> 0 and ctime > strftime('%Y-%m-%d %H:%M:%f','now','localtime','" + waitTime + " seconds') group by snick order by id");
			}else{
				resultSet = statement.executeQuery("select max(id) as id,snick,sum(donate) as donate_sum from DouyuDB where donate <> 0 and id > '" + lastSelectID + "' group by snick order by id");
			}
			//进行数据转储
			while(resultSet.next()){
				lastSelectID = resultSet.getString("id");
				donateresForLast5Sec.put(resultSet.getString("snick"), resultSet.getString("donate_sum"));
			}
			freeConnection(resultSet, statement, connection);
//			logger.info(lastSelectID==null?"null":lastSelectID);
		} catch (SQLException e) {
			logger.info(e.toString());
		}finally{
			freeConnection(resultSet, statement, connection);
		}
		return donateresForLast5Sec;
	}
	
    public static void freeConnection(ResultSet resultSet, Statement statement,
            Connection connection) {
        try {
            if (resultSet != null)
            	resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                	statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    try {
                    	connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
