package lesson03;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUpdate {
    public static void main(String[] args) throws SQLException {
        Connection conn = jdbcUtils.getConnection();
        //区别
        //使用？占位符代替参数
        String sql = "UPDATE users set name=? where id=?";
        PreparedStatement pst = conn.prepareStatement(sql);//预编译SQL,先写SQL，然后不执行
        //手动参数赋值
        pst.setString(1,"张伟");
        pst.setInt(2,4);

        //执行
        int i = pst.executeUpdate();
        if(i>0) System.out.println("修改成功！");
        //释放连接
        jdbcUtils.release(conn,pst,null);
    }
}
