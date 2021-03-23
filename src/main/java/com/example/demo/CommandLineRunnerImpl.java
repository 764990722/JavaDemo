package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by: PeaceJay
 * Created date: 2020/03/23.
 * Description: 自动创建数据库与表
 * 使用前必须存在一个数据库edgecomput
 * 判断exchange是否存在  不存在就会去新建一个
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");//加载注册
        //一开始必须填一个已经存在的数据库
        String url = "jdbc:mysql://127.0.0.1:3306/edgecomput?useUnicode=true&useSSL=false&characterEncoding=utf8";
        Connection conn = DriverManager.getConnection(url, "root", "109624");//建立连接
        Statement stat = conn.createStatement();
        String checkdatabase = "show databases like \"exchange\"";//判断数据库是否存在
        String createdatabase = "create  database  exchange";//创建数据库
        stat = (Statement) conn.createStatement();
        ResultSet resultSet = stat.executeQuery(checkdatabase);
        if (resultSet.next()) {
            //若数据库存在 新建一个表
            System.out.println("exchange exist!");
            url = "jdbc:mysql://127.0.0.1:3306/exchange?useUnicode=true&useSSL=false&characterEncoding=utf8";
            conn = DriverManager.getConnection(url, "root", "109624");
            stat = conn.createStatement();

            stat.executeUpdate("DROP TABLE IF EXISTS `user1`");
            stat.executeUpdate("CREATE TABLE `user1`  (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                    "  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',\n" +
                    "  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',\n" +
                    "  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号'\n" +
                    " ,PRIMARY KEY (`id`)) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;\n");

            stat.close();
            conn.close();
        } else {
            if (stat.executeUpdate(createdatabase) == 0)//若数据库不存在
                //打开创建的数据库
            url = "jdbc:mysql://127.0.0.1:3306/exchange?useUnicode=true&useSSL=false&characterEncoding=utf8";
            conn = DriverManager.getConnection(url, "root", "109624");
            stat = conn.createStatement();

            //创建表 user
            stat.executeUpdate("DROP TABLE IF EXISTS `user`");
            stat.executeUpdate("CREATE TABLE `user`  (\n" +
                    //"  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id自增主键',\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                    "  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',\n" +
                    "  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',\n" +
                    "  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号'\n" +
                    ",PRIMARY KEY (`id`)) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;\n");


            //创建表 angle_difference
            stat.executeUpdate("DROP TABLE IF EXISTS `angle_difference`");
            stat.executeUpdate("CREATE TABLE `angle_difference`  (\n" +
                    "  `station` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变电站',\n" +
                    "  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安装位置',\n" +
                    "  `ab` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'A相和B相的角差',\n" +
                    "  `bc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'B相和C相的角差',\n" +
                    "  `ca` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'C相和A相的角差',\n" +
                    "  `humidity` int(10) NULL DEFAULT NULL COMMENT '湿度',\n" +
                    "  `timestamp` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据采集时间'\n" +
                    ") ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;\n");

            stat.close();
            conn.close();
            System.out.println("create table success!");
        }
    }
}
