package com.isi.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
@Data
public class JdbcUtils {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public Connection getConnection() {
        Connection connection = null;
        try {
            //1、加载驱动  固定写法
            //MySQL8中com.mysql.cj.jdbc.Driver
            Class.forName(driverClassName);

            //3、连接成功，数据库对象Connection代表数据库
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 执行插入操作
     */
    public boolean insert(String sql){
        Statement statement = null;
        Integer resultSet = null;
        Connection connection=null;
        //4、执行sql的对象
        try {
            connection=this.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeUpdate(sql);
            if (resultSet > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 执行查询操作
     * @param tableName
     * @return
     */
    public List<List<String>> queryTable(String tableName,List<String> filedName){
        PreparedStatement statement = null;
        Connection connection=null;
        String sql="select * from "+ tableName;
        //二维数组用来存放表数据，第一行放表头的数据，第二行之后放实际数据
        List<List<String>> array = new ArrayList<>();
        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            //循环每一行
            while (resultSet.next()){
                List<String> rowData= new ArrayList<>();
                for (String filed : filedName) {
                    String result = resultSet.getString(filed);
                    //result.isEmpty()只能处理数据库里面值为空的情况，不能处理（null）这种情况，用result==可以处理这两种情况
                    //isEmpty() 用于判断List内容是否为空,必须在 list 本身不是空的引用的情况下才行;
                    //null 用于判断有没有这个集合对象;
                    if (result==null){
                        result="";
                    }
                    rowData.add(result);
                }
                array.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }
}
