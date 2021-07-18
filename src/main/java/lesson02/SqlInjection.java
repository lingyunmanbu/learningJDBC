package lesson02;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjection {
    public static void main(String[] args) {
        //login("xiaoming","123456");
        login("'or '1=1","123456");

    }


    //登录业务
    public static void login(String username,String password){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = jdbcUtils.getConnection();
            st = conn.createStatement();
            //写SQL语句
            //SELECT * FROM users WHERE NAME='xiaoming'AND PASSWORD=123456
            //SELECT * FROM users WHERE NAME=''or '1=1'AND PASSWORD=123456
            String sql ="SELECT * FROM users WHERE NAME='"+username+"'AND PASSWORD="+password;
            rs = st.executeQuery(sql);//查询完毕会返回一个结果集
            while (rs.next()){
                System.out.println("name="+rs.getObject("name"));
                System.out.println("password="+rs.getObject("password"));
                System.out.println("==================================");
            }
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
