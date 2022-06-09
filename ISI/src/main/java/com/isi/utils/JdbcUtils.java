package com.isi.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
@Data
public class JdbcUtils {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public Boolean connectionSql(String sql) {
        Connection connection = null;
        Statement statement = null;
        Integer resultSet = null;
        try {
            //1、加载驱动  固定写法
            //MySQL8中com.mysql.cj.jdbc.Driver
            Class.forName(driverClassName);

            //3、连接成功，数据库对象Connection代表数据库
            connection = DriverManager.getConnection(url, username, password);
            //4、执行sql的对象
            statement = connection.createStatement();

            resultSet = statement.executeUpdate(sql);
            if (resultSet > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6、释放连接
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
