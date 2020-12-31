package com.example.demo.utils;

import org.springframework.lang.Nullable;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 公共方法
 */
public class TextUtils {

    //不等于null 并且内容大于0
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    //把Null转为空字符串
    public static String nullString(@Nullable String str) {
        return str == null ? "" : str;
    }

}
