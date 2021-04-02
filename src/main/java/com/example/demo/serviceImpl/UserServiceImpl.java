package com.example.demo.serviceImpl;

import com.example.demo.mysql.UserMapper;
import com.example.demo.Service.UserService;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 数据库接口实现
 */
@Service
public class UserServiceImpl implements UserService {

    final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserInfo() {
        User user = new User();
        user.setUsername("PeaceJay");
        user.setPassword("a123456");
        return user;
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public List<User> queryByUsername(String username) {
        return userMapper.queryByUsername(username);
    }

    @Override
    public int deleteUser(String id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public List<User> queryUser() {
        return userMapper.queryUser();
    }


    @Override
    public int updateUser(User user) {
        int count = 0;
        try {
            count = userMapper.updateUser(user);
        }catch (Exception err){
            System.out.println(err);
        }
        return count;
    }

    @Override
    public int updateHerd(User user) {
        int count = 0;
        try {
            count = userMapper.updateHerd(user);
        }catch (Exception err){
            System.out.println(err);
        }
        return count;
    }

    @Override
    public List<User> getUserPageList(String username) {
        return userMapper.getUserPageList(username);
    }

    @Override
    public List<User> queryImage() {
        return userMapper.queryImage();
    }



}

