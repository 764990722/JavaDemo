package com.example.demo.mysql;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 数据库接口类
 */
@Repository
@Mapper
public interface UserMapper {

    int addUser(User user);

    List<User> queryByUsername(String id);

    int deleteUser(String id);

    List<User> queryUser();

    int updateUser(@Param("user") User user);

    int updateHerd(@Param("user") User user);

    List<User> getUserPageList(String username);

    List<User> queryImage();

}
