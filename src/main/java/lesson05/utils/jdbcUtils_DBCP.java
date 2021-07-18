package lesson05.utils;

import lesson02.utils.jdbcUtils;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class jdbcUtils_DBCP {
    private static DataSource dataSource = null;
    static {
        try{
            InputStream in = jdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            Properties properties = new Properties();
            properties.load(in);

            //创建数据源 工厂模式-->创建对象
            dataSource = BasicDataSourceFactory.createDataSource(properties);
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
