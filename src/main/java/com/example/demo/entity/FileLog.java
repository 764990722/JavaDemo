package com.example.demo.entity;

/**
 * Created by: mi.
 * Created date: 2021/4/12.
 * Description: log存储模型
 */
public class FileLog {

    public Integer id;
    public String log_time;
    public String log_file;
    public String mrchntNo;
    public String trmNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLog_file() {
        return log_file;
    }

    public void setLog_file(String log_file) {
        this.log_file = log_file;
    }

    public String getLog_time() {
        return log_time;
    }

    public void setLog_time(String log_time) {
        this.log_time = log_time;
    }

    public String getMrchntNo() {
        return mrchntNo;
    }

    public void setMrchntNo(String mrchntNo) {
        this.mrchntNo = mrchntNo;
    }

    public String getTrmNo() {
        return trmNo;
    }

    public void setTrmNo(String trmNo) {
        this.trmNo = trmNo;
    }
}
