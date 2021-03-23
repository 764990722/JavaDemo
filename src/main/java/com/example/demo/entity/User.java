package com.example.demo.entity;

import javax.persistence.Table;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 用户模型
 */

public class User {
    private int id;
    private String username;
    private String password;
    private String phone;

    public User() {}

    public User(String username, String password, String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "user{name='" + username + "\'," + "password=" + password + "}";
    }
}
