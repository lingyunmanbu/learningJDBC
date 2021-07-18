package lesson03;
import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeatDelete {
    public static void main(String[] args) throws SQLException {
        Connection conn = jdbcUtils.getConnection();
        //区别
        //使用？占位符代替参数
        String sql = "DELETE FROM users WHERE id=?";
        PreparedStatement pst = conn.prepareStatement(sql);//预编译SQL,先写SQL，然后不执行
        //手动参数赋值
        pst.setInt(1,6);
        //执行
        int i = pst.executeUpdate();
        if(i>0) System.out.println("删除成功！");
        //释放连接
        jdbcUtils.release(conn,pst,null);
    }
}
