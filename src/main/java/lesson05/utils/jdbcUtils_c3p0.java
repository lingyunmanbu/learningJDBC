package lesson05.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcUtils_c3p0 {
    private static ComboPooledDataSource dataSource = null;
    static {
        try{
            //创建数据源 工厂模式-->创建对象
             dataSource = new ComboPooledDataSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); //从数据源获取连接
    }
    //释放连接
    public static void release(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if(rs!=null) rs.close();
        if(st!=null) st.close();
        if(conn!=null) conn.close();
    }
}
