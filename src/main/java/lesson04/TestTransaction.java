package lesson04;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestTransaction {
    public static void main(String[] args) throws SQLException {
        PreparedStatement pst= null;

        Connection conn = null;
        try {
            conn = jdbcUtils.getConnection();
            //关闭自动提交，自动会开启事务
            conn.setAutoCommit(false);  //开启事务
            String sql1 = "update account set money=money-100 where name='A'";
            pst = conn.prepareStatement(sql1);
            pst.executeUpdate();

            String sql2 = "update account set money=money+100 where name='B'";
            pst = conn.prepareStatement(sql2);
            pst.executeUpdate();
            //业务完毕，提交事务
            conn.commit();
            System.out.println("转账成功！");
        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }finally {
            jdbcUtils.release(conn,pst,null);
        }
    }
}
