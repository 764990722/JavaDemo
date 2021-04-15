package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.FileApk;
import com.example.demo.entity.FileLog;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.utils.TextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
@RequestMapping("user")
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
        String username = body.getUsername() == null ? "" : body.getUsername();
        String password = body.getPassword() == null ? "" : body.getPassword();
        String phone = body.getPhone() == null ? "" : body.getPhone();
        String herdImage = body.getHerdImage() == null ? "" : body.getHerdImage();

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
            System.out.println("注册失败，用户名重复,请更换");
            return new Response<>(false, "注册失败，用户名重复,请更换", -1, Collections.emptyMap());
        } else {
            User bean = new User(username, password, phone, herdImage);
            int count = service.addUser(bean);
            if (count > 0) {
                System.out.println("注册成功" + username);
                return new Response<>(true, "注册成功", 200, bean);
            } else {
                System.out.println("注册失败" + password);
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
        Map<String, Object> map = new HashMap<>();
        map.put("list", users);
        return new Response<>(true, "查询成功", 200, map);
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
        List<User> users = service.getUserPageList(username == null ? "" : username);

        // 组装分页数据，主要包含分页列表数据及总记录数
        Map<String, Object> map = new HashMap<>();
        map.put("list", users);
        map.put("total", new PageInfo<>(users).getTotal());

        return new Response<>(true, "查询成功", 200, map);
    }


    /**
     * 查全部
     * 查询所有图片
     */
    @RequestMapping(value = "/queryImage", method = RequestMethod.GET)
    public Response<Object> queryImage() {
        List<User> users = service.queryImage();
        Map<String, Object> map = new HashMap<>();
        map.put("list", users);
        return new Response<>(true, "查询成功", 200, map);
    }


    /**
     * 修改用户资料
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Response<Object> updateUser(@RequestBody User user) {
        try {
            User body = new User();
            body.setId(user.getId());
            body.setUsername(user.getUsername() == null ? "" : user.getUsername());
            body.setPassword(user.getPassword() == null ? "" : user.getPassword());
            body.setPhone(user.getPhone() == null ? "" : user.getPhone());

            //返回数据 并保存值到数据库
            int count = service.updateUser(body);
            if (count > 0) {
                return new Response<>(true, "修改成功", 200, body);
            } else {
                return new Response<>(true, "修改失败", -102, Collections.emptyMap());
            }
        } catch (Exception e) {
            return new Response<>(true, "修改失败", -103, Collections.emptyMap());
        }
    }


    /**
     * 修改用户资料 - 带表单头像
     * + @Param + @RequestParamz组合  就不能用 @RequestBody
     */
    @RequestMapping(value = "/updateHerd", method = RequestMethod.POST)
    public Response<Object> updateHerd(@Param("id") String id,
                                       @Param("username") String username,
                                       @Param("password") String password,
                                       @Param("phone") String phone,
                                       @RequestParam("file") MultipartFile file_data) {

        try {
            User body = new User();
            body.setId(Integer.parseInt(id == null ? "-1" : id));
            body.setUsername(username == null ? "" : username);
            body.setPassword(password == null ? "" : password);
            body.setPhone(phone == null ? "" : phone);
            body.setHerdImage("");

            if (file_data != null) {
                // 获取文件名
                String timeName = TextUtils.getCurrentTime(TextUtils.Format_TIME1);
                String fileName = String.format("%s_%s", timeName, file_data.getOriginalFilename());
                System.out.println("新的文件名为：" + fileName);

                // 新增当前日期文件名
                String timeStr = TextUtils.getCurrentTime(TextUtils.Format_TIME);

                // 文件上传后的路径
                String filePath = String.format("%s%s/", "/usr/local/include/img/", timeStr);

                // 检测是否存在目录
                File dest = new File(filePath);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }

                try {
                    TextUtils.savePic(file_data.getInputStream(), fileName, filePath);
                    //映射后可访问得路径
                    String imageUrl = String.format("%s%s/%s", "http://8.136.210.1:8080/pic/img/", timeStr, fileName);
                    System.out.println(imageUrl);
                    body.setHerdImage(imageUrl);

                } catch (IOException e) {
                    e.printStackTrace();
                    return new Response<>(true, "修改失败", -101, Collections.emptyMap());
                }
            }

            //返回数据 并保存值到数据库
            int count = service.updateHerd(body);
            System.out.println(count);
            if (count > 0) {
                return new Response<>(true, "修改成功", 200, body);
            } else {
                return new Response<>(true, "修改失败", -102, Collections.emptyMap());
            }
        } catch (Exception e) {
            return new Response<>(true, "修改失败", -103, Collections.emptyMap());
        }

    }


    /**
     * 添加下载地址 - 带文件
     * + @Param + @RequestParamz组合  就不能用 @RequestBody
     */
    @RequestMapping(value = "/addApk", method = RequestMethod.POST)
    public Response<Object> addApk(@Param("apk_version") String apk_version,
                                   @RequestParam("apk_file") MultipartFile file_data) {

        try {
            FileApk body = new FileApk();
            body.setApk_version(apk_version == null ? "" : apk_version);
            body.setApk_file("");

            if (file_data != null) {
                // 获取文件名
                String timeName = TextUtils.getCurrentTime(TextUtils.Format_TIME1);
//                String fileName = String.format("%s_%s", timeName, file_data.getOriginalFilename());
                String fileName = String.format("%s", file_data.getOriginalFilename());
                System.out.println("新的文件名为：" + fileName);

                // 新增当前日期文件名
                String timeStr = TextUtils.getCurrentTime(TextUtils.Format_TIME);

                // 文件上传后的路径
                String filePath = String.format("%s", "/usr/local/include/apk/");

                // 检测是否存在目录
                File dest = new File(filePath);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }

                try {
                    TextUtils.savePic(file_data.getInputStream(), fileName, filePath);
                    //映射后可访问得路径
                    String imageUrl = String.format("%s%s", "http://8.136.210.1:8080/pic/apk/", fileName);
                    System.out.println(imageUrl);
                    body.setApk_file(imageUrl);

                } catch (IOException e) {
                    e.printStackTrace();
                    return new Response<>(true, "上传失败", -101, Collections.emptyMap());
                }
            }

            //返回数据 并保存值到数据库
            int count = service.addApk(body);
            System.out.println(count);
            if (count > 0) {
                return new Response<>(true, "上传成功", 200, body);
            } else {
                return new Response<>(true, "上传失败", -102, Collections.emptyMap());
            }
        } catch (Exception e) {
            return new Response<>(true, "上传失败", -103, Collections.emptyMap());
        }

    }


    /**
     * 上传log文件 并生成地址
     * + @Param + @RequestParamz组合  就不能用 @RequestBody
     */
    @RequestMapping(value = "/addLog", method = RequestMethod.POST)
    public Response<Object> addLog(@Param("log_time") String log_time,
                                   @Param("mrchntNo") String mrchntNo,
                                   @Param("trmNo") String trmNo,
                                   @RequestParam("log_file") MultipartFile log_file) {

//        try {
        FileLog body = new FileLog();
        body.setLog_time(log_time == null ? "" : log_time);
        body.setMrchntNo(mrchntNo == null ? "" : mrchntNo);
        body.setTrmNo(trmNo == null ? "" : trmNo);
        body.setLog_file("");
        System.out.println(mrchntNo == null ? "mrchntNo" : mrchntNo);
        System.out.println(trmNo == null ? "trmNo" : trmNo);

        if (log_file != null) {
            // 获取文件名
            String timeName = TextUtils.getCurrentTime(TextUtils.Format_TIME1);
//                String fileName = String.format("%s_%s", timeName, file_data.getOriginalFilename());
            String fileName = String.format("%s", log_file.getOriginalFilename());
            System.out.println("新的文件名为：" + fileName);

            // 新增当前日期文件名
            String timeStr = TextUtils.getCurrentTime(TextUtils.Format_TIME);

            // 文件上传后的路径
            String filePath = String.format("%s%s/", "/usr/local/include/log/", timeStr);

            // 检测是否存在目录
            File dest = new File(filePath);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            try {
                TextUtils.savePic(log_file.getInputStream(), fileName, filePath);
                //映射后可访问得路径
                String imageUrl = String.format("%s%s", "http://8.136.210.1:8080/pic/log/", fileName);
                System.out.println(imageUrl);
                body.setLog_file(imageUrl);

            } catch (IOException e) {
                e.printStackTrace();
                return new Response<>(true, "上传失败", -101, Collections.emptyMap());
            }
        }

        //返回数据 并保存值到数据库
        int count = service.addLog(body);
        System.out.println("count:" + count);
        if (count > 0) {
            return new Response<>(true, "上传成功", 200, body);
        } else {
            return new Response<>(true, "上传失败", -102, Collections.emptyMap());
        }
//        } catch (Exception e) {
//            return new Response<>(true, "上传失败", -103, Collections.emptyMap());
//        }
    }


}