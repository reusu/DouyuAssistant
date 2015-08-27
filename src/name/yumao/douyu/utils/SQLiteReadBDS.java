package name.yumao.douyu.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class SQLiteReadBDS{
    private static final int INITIAL = 2;
    private static final int MAX_ACTIVE = 64;
    private static final int MAX_IDLE = 16;
    private static final long MAX_WAIT = 100;
    private static final String DRIVER_NAME = "org.sqlite.JDBC";
    private static BasicDataSource bds;
    static{
        if(bds == null){
            bds = new BasicDataSource();
        }
        bds.setDriverClassName(DRIVER_NAME);
        bds.setInitialSize(INITIAL);
        bds.setMaxActive(MAX_ACTIVE);
        bds.setMaxIdle(MAX_IDLE);
        bds.setMaxWait(MAX_WAIT);
    }
    
    public static void setSqliteUrl(String path){
    	bds.setUrl("jdbc:sqlite:" + path);
    }
    public static Connection getSqliteConnection() throws SQLException{
        return bds.getConnection();
    }
}