package lesson03;

import lesson02.utils.jdbcUtils;

import java.sql.*;

public class SqlInjection {
    public static void main(String[] args) throws SQLException {
        login("xiaoming");
        //login("'or '1=1","123456");

    }

    //登录业务
    public static void login(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = jdbcUtils.getConnection();
            //写SQL语句
            //SELECT * FROM users WHERE NAME='xiaoming'AND PASSWORD=123456
            //SELECT * FROM users WHERE NAME=''or '1=1'AND PASSWORD=123456
            //PreparedStatement防止SQL注入的本质，把传进来的参数当做字符
            //假设其中存在转义字符就直接忽略，‘会被直接转义
            String sql = "SELECT * FROM users where name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,username);
            rs = pst.executeQuery(sql);//查询完毕会返回一个结果集
            while (rs.next()){
                System.out.println("name="+rs.getObject("name"));
                System.out.println("password="+rs.getObject("password"));
                System.out.println("==================================");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
                jdbcUtils.release(conn,pst,rs);
        }
    }
}
