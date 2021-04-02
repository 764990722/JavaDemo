package com.example.demo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by: mi.
 * Created date: 2021/3/30.
 * Description:
 */
public class JDBCTest {

    public static void main(String[] args) throws IOException {

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/peacejay?useUnicode=true&useSSL=false&characterEncoding=utf8";
        String user = "root";
        String pass = "00109624";
        try {
            Class.forName(driver);    //加载驱动
            //连接数据库
            Connection conn = DriverManager.getConnection(url, user, pass);
            if (!conn.isClosed()) {
                System.out.println("Connection Success!");
                //statement 用来执行sql语句
                Statement statement = conn.createStatement();
                //要执行的sql
                String sql = "select * from tb_file";
                //结果集
                ResultSet rs = statement.executeQuery(sql);
                int id = 1;
                while (rs.next()) {
                    id++;
                }
                System.out.println(id);
//                String fname = null;
//                String fcontent = null;
//                while (rs.next()) {
//                    fname = rs.getString("fname");
//                    fcontent = rs.getString("fcontent");        //这个是blob 型
//                    System.out.println(fname + " | " + fcontent);
//                }

                String sqlxx = "insert into tb_file(id,fname,fcontent) values (?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlxx);
                FileInputStream fi = new FileInputStream("D:\\image_dlam.jpg");
                pstmt.setInt(1, id);
                pstmt.setString(2, "多来阿蒙" + id);
                //pstmt.setBinaryStream(2,fi,fi.available());    也行
                pstmt.setBlob(3, fi, fi.available());               //也行
                pstmt.execute();
                conn.close();
            }
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
