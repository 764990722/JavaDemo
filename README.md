
## 实战Java接口学习之路

## 需要用到mysql  8.0.22 winx64安装配置方法图文 教程地址：http://note.youdao.com/noteshare?id=bc87cf96e09dbeaeba6ca094591da455

## 参考学习博客，在此感谢：https://blog.csdn.net/weixin_44135121/category_9285585.html

## 框架说明
## pom.xml 注解框架、数据库框架、分页框架

## 接口说明
## getUserInfo          测试接口，不需要数据库可访问 http://localhost:8080/getUserInfo
## addUser              注册接口
## login                登录接口
## deleteUser           删除单条数据
## queryUser            查询所有用户
## updateUser           修改用户信息
## getUserPageList      分页，模糊查询



## 数据库代码
````
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.demo.mysql.UserMapper">
    <resultMap id="BaseResultMap" type="com.example.demo.entity.User">
        <result column="id" jdbcType="INTEGER" property="id"/>
        <result column="username" jdbcType="VARCHAR" property="username"/>
        <result column="password" jdbcType="VARCHAR" property="password"/>
        <result column="phone" jdbcType="VARCHAR" property="phone"/>
    </resultMap>
    <!-- id sql语句唯一名    parameterType 传入sql语句的参数类型-->
    <insert id="addUserInfo" parameterType="com.example.demo.entity.User">
        INSERT INTO `user` VALUES (#{id},#{username}, #{password}, #{phone})
    </insert>
    
    <!--  插入数据  -->
    <insert id="addUser" parameterType="com.example.demo.entity.User">
        INSERT INTO `user` VALUES (#{id},#{username}, #{password}, #{phone})
    </insert>
    <!--  根据账号查询  -->
    <select id="queryByUsername" resultType="com.example.demo.entity.User">
        SELECT * FROM `user` WHERE username = #{username}
    </select>
    <!--  根据id删除  -->
    <delete id="deleteUser">
        DELETE FROM `user` WHERE id = #{id}
    </delete>
    <!--  查询所有用户  -->
    <select id="queryUser" resultMap="BaseResultMap">
        SELECT * FROM `user`
    </select >
    
    <!--  修改指定用户信息  -->
    <update id="updateUser"  parameterType="com.example.demo.entity.User">
        UPDATE `user`
        <trim prefix="SET" suffixOverrides=",">
            <if test="null != user.username and '' != user.username">
                username = #{user.username},
            </if>
            <if test="null != user.password and '' != user.password">
                password = #{user.password},
            </if>
        </trim>
        WHERE id = #{user.id}
    </update>
    
    <!--  分页查询用户信息,并通过账号模糊查询  -->
    <select id="getUserPageList" resultMap="BaseResultMap">
        select * from `user` WHERE username LIKE CONCAT('%',#{username,jdbcType=VARCHAR},'%')
    </select>
</mapper>
````


![](image/addUser.jpg)

````
    /**
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
````

![](image/login.jpg)
![](image/delete.jpg)
![](image/updateUser.jpg)
![](image/getUserPageList.jpg)
      

  
   



