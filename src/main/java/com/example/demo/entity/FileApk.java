package com.example.demo.entity;

/**
 * Created by: mi.
 * Created date: 2021/4/12.
 * Description: apk存储模型
 */
public class FileApk {

    public Integer id;
    public String apk_file;
    public String apk_version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApk_file() {
        return apk_file;
    }

    public void setApk_file(String apk_file) {
        this.apk_file = apk_file;
    }

    public String getApk_version() {
        return apk_version;
    }

    public void setApk_version(String apk_version) {
        this.apk_version = apk_version;
    }
}
