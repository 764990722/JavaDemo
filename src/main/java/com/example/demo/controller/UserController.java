package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.utils.TextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 接口实现
 */
@ResponseBody
@RestController
public class UserController {

    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * 固定本地查询
     */
    @RequestMapping(value = "/getUserItem", method = RequestMethod.GET)
    public Response<Object> getUserItem() {
        service.getUserInfo();
        Response<Object> res = new Response<>();
        res.setMsg("成功");
        res.setCode(200);
        res.setData(Collections.emptyMap());
        return res;
    }

    /**
     * 增
     * 动态注册账号，并验证用户名是否重复
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response<Object> register(@RequestBody User body) {
        String username = body.getUsername();
        String password = body.getPassword();
        String phone = body.getPhone();

        //1.判断用户名、密码、手机号是否为空
        if (phone != null && phone.length() == 0) {
            return new Response<>(false, "请输入手机号", -1, Collections.emptyMap());
        }
        if (TextUtils.isEmpty(username)) {
            return new Response<>(false, "请输入用户名", -1, Collections.emptyMap());
        }
        if (TextUtils.isEmpty(password)) {
            return new Response<>(false, "请输入密码", -1, Collections.emptyMap());
        }

        //查询用户名
        List<User> users = service.queryByUsername(username);
        //2.判断是否有重复用户名
        if (users != null && users.size() > 0) {
            return new Response<>(false, "注册失败，用户名重复,请更换", -1, Collections.emptyMap());
        } else {
            int count = service.addUser(username, password, phone);
            User user = new User(username, password, phone);
            if (count > 0) {
                return new Response<>(true, "注册成功", 200, user);
            } else {
                return new Response<>(false, "注册失败", -1, Collections.emptyMap());
            }
        }
    }


    /**
     * 查单条
     * 动态登录账号，并验证用户名是否重复
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<Object> login(@RequestBody Map<String, String> person) {
        String username = person.get("username");
        String password = person.get("password");

        if (TextUtils.isEmpty(username)) {
            return new Response<>(false, "请输入用户名", -1, Collections.emptyMap());
        }
        if (TextUtils.isEmpty(password)) {
            return new Response<>(false, "请输入密码", -1, Collections.emptyMap());
        }
        //查询用户名
        List<User> users = service.queryByUsername(username);
        //2.判断是否有重复用户名
        if (users != null && users.size() > 0) {
            //3. 判断密码是否正确
            if (password.equals(users.get(0).getPassword())) {
                //4. 密码正确，登陆成功
                return new Response<>(true, "登录成功", 200, Collections.emptyMap());
            } else {
                return new Response<>(false, "登陆失败，密码错误", -1, Collections.emptyMap());
            }
        } else {
            return new Response<>(false, "登陆失败，用户名不存在", -1, Collections.emptyMap());
        }
    }


    /**
     * 删
     * 动态删除账号，根据用户名删除账号
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public Response<Object> deleteUser(@RequestBody Map<String, String> person) {
        String id = person.get("id");
        System.out.println(id);

        try {
            Response<Object> response = new Response<>(false, "删除失败", -1, Collections.emptyMap());
            String[] list = id.split(",");
            for (String s : list) {
                int cont = service.deleteUser(s);
                if (cont > 0) {
                    response = new Response<>(true, "删除成功", 200, Collections.emptyMap());
                } else {
                    response = new Response<>(false, "删除失败", -1, Collections.emptyMap());
                }
            }
            return response;
        } catch (Exception e) {
            return new Response<>(false, "参数错误", -1, Collections.emptyMap());
        }
    }


    /**
     * 查全部
     * 查询所有用户
     */
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    public Response<Object> queryUser() {
        List<User> users = service.queryUser();
        return new Response<>(true, "查询成功", 200, users);
    }

    /**
     * 改
     * 修改指定用户账号和密码
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Response<Object> updateUser(@RequestBody User user) {
        int id = user.getId();
        if (id != 0) {
            int count = service.updateUser(user);
            if (count > 0) {
                return new Response<>(true, "修改成功", 200, user);
            } else {
                return new Response<>(false, "修改失败", -1, Collections.emptyMap());
            }
        } else {
            return new Response<>(false, "请传入用户id", -1, Collections.emptyMap());
        }
    }

    /**
     * 查分页
     * 分页查询用户
     */
    @RequestMapping(value = "/getUserPageList", method = RequestMethod.GET)
    public Response<Object> getUserPageList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize,
                                            @Param("username") String username) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = service.getUserPageList(username);

        // 组装分页数据，主要包含分页列表数据及总记录数
        Map<String, Object> map = new HashMap<>();
        map.put("list", users);
        map.put("total", new PageInfo<>(users).getTotal());

        return new Response<>(true, "查询成功", 200, map);
    }

}