# 1、JDBC学习

## 1.1第一个JDBC程序

>创建测试数据库

```sql
CREATE DATABASE `jdbcStudy` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `jdbcStudy`;

CREATE TABLE `users`(
 `id` INT PRIMARY KEY,
 `NAME` VARCHAR(40),
 `PASSWORD` VARCHAR(40),
 `email` VARCHAR(60),
 birthday DATE
);

 INSERT INTO `users`(`id`,`NAME`,`PASSWORD`,`email`,`birthday`)
VALUES('1','zhangsan','123456','zs@sina.com','1980-12-04'),
('2','lisi','123456','lisi@sina.com','1981-12-04'),
('3','wangwu','123456','wangwu@sina.com','1979-12-04')
```

1.创建一个maven项目

2.导入数据库驱动：从maven仓库中找到合适的版本添加到pom.xml中

![image-20210711180251459](C:\Users\27219\AppData\Roaming\Typora\typora-user-images\image-20210711180251459.png)

3.编写测试代码

```java
package learningjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//我的第一个JDBC程序
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
```

步骤总结：

1、加载驱动

2、连接数据库Drivermanager

3、获得执行SQL的对象statement

4、获得返回的结果集

5、释放连接

>DriverManager

```java
Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动
Connection connection = DriverManager.getConnection(url, username, password);

//connection 代表数据库
//数据库设置自动提交
//事务提交
//事务回滚
connection.commit();
connection.setAutoCommit(false);
connection.rollback();
```

[DriverManager API]: https://www.runoob.com/manual/jdk11api/java.sql/java/sql/DriverManager.html
[Connection API]: https://www.runoob.com/manual/jdk11api/java.sql/java/sql/Connection.html



>URL

```java
 String url ="jdbc:mysql://localhost:3306/jdbcstudy?"
		 		+ "useUnicode=true&characterEncoding=utf8&useSSL=false";
//mysql--3306
//jdbc:mysql://主机地址:端口号/数据库名?参数1&参数2&参数3
//oralce--1521
//jdbc:oracle:thin:@localhost:1521:sid
//协议://主机地址:端口号/数据库名？参数1&参数2&参数3
```

> Statement 执行SQL对象    PrepareStatement执行SQL的对象

```java
String sql = "SELECT * FROM users";//编写SQL语句

statement.executeQuery(sql);// 查询操作返回ResultSet
statement.execute(sql);// 执行任何SQL
statement.executeUpdate(sql);//更新，插入，删除都用这个，返回一个受影响的行数
```

[Statement API]: https://www.runoob.com/manual/jdk11api/java.sql/java/sql/Statement.html



> ResultSet 查询的结果集，封装了所有的查询结果

获得指定的数据类型

```java
resultSet.getObject();//在不知道列类型的情况下使用
resultSet.getInt();
resultSet.getString();
resultSet.getDate();

resultSet.next();
//ResultSet对象维护指向其当前数据行的游标。 最初，光标位于第一行之前。 next方法将光标移动到下一行，并且因为当ResultSet对象中没有更多行时它返回false ，它可以在while循环中用于迭代结果集。
```

[ResultSet API]: https://www.runoob.com/manual/jdk11api/java.sql/java/sql/ResultSet.html

> 释放资源

```java
resultSet.close();
statement.close();
connection.close();//十分耗资源，用完关掉
```

## 1.2 Statement对象

JDBC 中的statement对象用于向数据库发送SQL语句，想完成对数据库的增删改查，只需要通过这个对象向数据库发送增删改查语句即可。

Statement对象的executeUpdate方法，用于向数据库发送增、删、改的SQL语句，executeUpdate执行完后，将会返回一个整数（即增删改语句导致数据库几行数据发生了变化）

Statement.executeQuery方法用于向数据库发送查询语句，executeQuery方法返回代表查询结果的ResultSet对象。

> CRUD操作-create

使用Statement.executeUpdate（sql）方法完成数据库的添加操作，示例操作：

```java
Statement st = connection.createStatement();
String sql1 = "insert into user(...) values(...)";
int num = st.executeUpdate(sql1);
if (num>0) {
	System.out.println("插入成功！");
}
```

> CRUD操作-delete

使用Statement.executeUpdate（sql）方法完成数据库的删除操作，示例操作：

```java 
Statement st = connection.createStatement();
String sql1 = "delete from user where id=1";
int num = st.executeUpdate(sql1);
if (num>0) {
	System.out.println("删除成功！");
}
```

> CRUD操作-update

使用Statement.executeUpdate（sql）方法完成数据库的修改操作，示例操作：

```java
Statement st = connection.createStatement();
String sql1 = "update user set name='' where name=''";
int num = st.executeUpdate(sql1);
if (num>0) {
	System.out.println("修改成功！");
}
```

> CRUD操作-read

使用Statement.executeQuery（sql）方法完成数据库的查询操作，示例操作：

```java
Statement st = connection.createStatement();
String sql1 = "select * from user where id=1";
ResultSet rs = statement.executeQuery(sql);
while(rs.next()){
    //根据获取的列的数据类型，分别调用rs的相应方法映射到java对象中
}
```

> 代码实现

1.提取工具类

```java
package lesson02.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class jdbcUtils {
    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {

        try{
            InputStream in = jdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(in);

            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            //1.驱动只用加载一次
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //获取连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    //释放连接
    public static void release(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if(rs!=null) rs.close();
        if(st!=null) st.close();
        if(conn!=null) conn.close();
    }

}
```

2.编写增删改的方法 st.executeUpdate(sql)

```java
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

```

```java
package lesson02;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestUpdate {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = jdbcUtils.getConnection();//获取数据库连接
            st = conn.createStatement();//获得执行SQL的执行对象
            String sql = "UPDATE users SET  NAME='xiaoming',email='xm@163.conm' WHERE id=1";
            int i = st.executeUpdate(sql);
            if (i > 0) System.out.println("修改成功！");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                jdbcUtils.release(conn, st, rs);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

```

```java
package lesson02;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDelete {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = jdbcUtils.getConnection();//获取数据库连接
            st = conn.createStatement();//获得执行SQL的执行对象
            String sql ="DELETE FROM users WHERE id=4";
            int i = st.executeUpdate(sql);
            if(i>0) System.out.println("删除成功！");

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

```

3.查询 st.executeQuery(sql);

```java
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

```

> SQL注入

SQL存在漏洞会被攻击导致数据泄露   SQL会被拼接

```java
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

```

## 1.3 PreparedStatement

PreparedStatement可以防止SQL注入，效果更好！



1.新增

```java
package lesson03;

import lesson02.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TestInsert {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = jdbcUtils.getConnection();
            //区别
            //使用？占位符代替参数
            String sql ="INSERT INTO users(id,NAME,PASSWORD,email,birthday) VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);//预编译SQL,先写SQL，然后不执行

            //手动参数赋值
            pst.setInt(1,5);
            pst.setString(2,"xiaogang");
            pst.setInt(3,123321);
            pst.setString(4,"xiaoggang@163.com");
            //注意点 sql.date     数据库    java.sql.Date()
            //      util.date    java       new Date().getTime()
            pst.setDate(5, new java.sql.Date(new Date().getTime())); //获得时间戳
            //执行
            int i = pst.executeUpdate();
            if(i>0) System.out.println("插入成功！");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            jdbcUtils.release(conn,pst,null);
        }
    }
}

```

2.删除

```java
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
        pst.setInt(1,5);
        //执行
        int i = pst.executeUpdate();
        if(i>0) System.out.println("删除成功！");
        //释放连接
        jdbcUtils.release(conn,pst,null);
    }
}

```

3.修改

```java
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
```

4.查询

```java
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

```

5.SQL注入

```java
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

```

## 1.4 JDBC 事务

> 代码实现

1.开启事务 conn.setAutoCommit(false);  //开启事务

2.以租业务执行完毕，提交事务

3.在catch中显式的定义 回滚语句，但默认失败就会回滚

```java
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

```

##  1.5 数据库连接池

数据库连接---执行完毕---释放

连接---释放十分浪费系统资源

池化技术：准备一些预先的资源，过来就连接预先准备好的

--------开门-------服务----------关门

-----开门----业务员：等待----服务--

常用连接数10个

最小连接数10个

最大连接数100个 ：业务最高承载上限

排队等待，

等待超时：100ms

编写连接池，实现接口DateSource

>开源数据源实现

DBCP

C3P0

Druid:阿里巴巴

使用这些数据库连接池后，我们在项目开发上就不需要编写连接数据库的代码了

> DBCP

需要的jar包

commons-dbcp-1.4        commons-pool-1.6

> C3P0

需要的jar包

c3P0-0.9.5.5   mchange-commons-java-0.2.20

> 结论

无论使用什么数据源，本质还是一样的，dateSource接口不会变，方法就不会变
