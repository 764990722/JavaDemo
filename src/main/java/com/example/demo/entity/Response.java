package com.example.demo.entity;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 公共模型
 */
public class Response<T> {

    private int code;
    private Boolean success;
    private String msg;
    private T data;// 数据

    public Response() {
    }

    public Response(Boolean success, String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
