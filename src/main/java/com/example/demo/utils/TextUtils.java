package com.example.demo.utils;

import org.springframework.lang.Nullable;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/31.
 * Description: 公共方法
 */
public class TextUtils {

    public static String Format_TIME = "yyyy-MM-dd";
    public static String Format_TIME1 = "yyyyMMddHHmmss";

    /**
     * 不等于null 并且内容大于0
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 把Null转为空字符串
     */
    public static String nullString(@Nullable String str) {
        return str == null ? "" : str;
    }




    /**
     * 上传图片
     * @param inputStream   文件
     * @param fileName      文件名
     * @param filePath      文件路径
     */
    public static void savePic(InputStream inputStream, String fileName, String filePath) {
        OutputStream os = null;
        try {
            String path = filePath;
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  得到当前时间转换成String
     *
     * @param format  yyyy年MM月dd日 HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }


}
