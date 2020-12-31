package com.example.demo.Service;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 接口类
 */
public interface UserService {
    User getUserInfo();//固定的查询

    int addUser(String username, String password, String phone);//用于注册时，添加用户

    List<User> queryByUsername(String username);//用户名匹配

    int deleteUser(String id);//删除用户

    List<User> queryUser();//查询全部用户

    int updateUser(@Param("user") User user);//修改用户信息

    List<User> getUserPageList(String username);//分页，模糊查询查询用户

}
