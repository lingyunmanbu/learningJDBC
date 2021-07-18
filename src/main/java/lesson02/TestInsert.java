package lesson02;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsert {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = jdbcUtils.getConnection();//获取数据库连接
            st = conn.createStatement();//获得执行SQL的执行对象
            String sql ="INSERT INTO users(id,NAME,PASSWORD,email,birthday)" +
                    "VALUES(4,'wangwang',123123,'liwang124@163.com','1997-12-04')";
             int i = st.executeUpdate(sql);
             if(i>0) System.out.println("插入成功！");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                jdbcUtils.release(conn,st,rs);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
