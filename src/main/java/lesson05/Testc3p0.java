package lesson05;

import lesson05.utils.jdbcUtils_c3p0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Testc3p0 {
    public static void main(String[] args) throws SQLException {



        Connection conn = jdbcUtils_c3p0.getConnection();
        //区别
        //使用？占位符代替参数
        String sql ="INSERT INTO users(id,NAME,PASSWORD,email,birthday) VALUES(?,?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);//预编译SQL,先写SQL，然后不执行

        //手动参数赋值
        pst.setInt(1,6);
        pst.setString(2,"xiaolin");
        pst.setInt(3,123456);
        pst.setString(4,"xiaolin@163.com");

        //注意点 sql.date     数据库    java.sql.Date()
        //      util.date    java       new Date().getTime()
        pst.setDate(5, new java.sql.Date(new Date().getTime())); //获得时间戳

        //执行
        int i = pst.executeUpdate();
        if(i>0) System.out.println("插入成功！");
        //释放连接
        jdbcUtils_c3p0.release(conn,pst,null);

    }
}
