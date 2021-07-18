package lesson02;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Testselect {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = jdbcUtils.getConnection();
            st = conn.createStatement();
            //写SQL语句
            String sql ="SELECT * FROM users";
            rs = st.executeQuery(sql);//查询完毕会返回一个结果集
            while (rs.next()){
                System.out.println("id="+rs.getObject("id"));
                System.out.println("name="+rs.getObject("name"));
                System.out.println("password="+rs.getObject("password"));
                System.out.println("email="+rs.getObject("email"));
                System.out.println("birth="+rs.getObject("birthday"));
                System.out.println("==================================");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            jdbcUtils.release(conn,st,rs);
        }
    }
}
