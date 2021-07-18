package lesson03;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestSelect {
    public static void main(String[] args) throws SQLException {
        Connection conn = jdbcUtils.getConnection();
        String sql = "SELECT * FROM users where name=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1,"xiaoming");
        //执行
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            System.out.println("id="+rs.getObject("id"));
            System.out.println("name="+rs.getObject("name"));
            System.out.println("pwd="+rs.getObject("password"));
            System.out.println("email="+rs.getObject("email"));
            System.out.println("birth="+rs.getObject("birthday"));
            System.out.println("============================");
        }
        jdbcUtils.release(conn,pst,rs);

    }
}
