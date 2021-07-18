package lesson01;

import java.sql.*;

public class JDBCFirstDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        // 1、加载驱动
        Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动

        // 2、用户信息和url
        String url ="jdbc:mysql://localhost:3306/jdbcstudy?"
                + "useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username ="root";
        String password = "root";

        // 3、连接成功，数据库对象    Connection 代表数据库
        //Eclipse自动生成对象来接收方法的返回值的快捷键:在你要自动生成返回值对象的那一行的末尾
        //（注意一定要将光标点到最后），按Alt+Shift+L
        Connection connection = DriverManager.getConnection(url, username, password);

        // 4、执行SQL对象 statement 执行SQL的对象
        Statement statement = connection.createStatement();

        // 5、用执行SQL的对象 去 执行SQL ，可能存在结果，查看返回结果
        String sql = "SELECT * FROM users";
        ResultSet resultSet = statement.executeQuery(sql); //返回的结果集,结果集中封装了我们全部的查询结果
        while (resultSet.next()) {
            System.out.println("id=" + resultSet.getObject("id"));
            System.out.println("name=" + resultSet.getObject("name"));
            System.out.println("pwd=" + resultSet.getObject("password"));
            System.out.println("email=" + resultSet.getObject("email"));
            System.out.println("birth=" + resultSet.getObject("birthday"));
            System.out.println("==================================");
        }

        // 6、释放连接
        resultSet.close();
        statement.close();
        connection.close();
    }
}
