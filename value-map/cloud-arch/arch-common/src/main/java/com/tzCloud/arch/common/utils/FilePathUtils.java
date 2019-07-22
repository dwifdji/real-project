package com.tzCloud.arch.common.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/20 16:05
 */
@Slf4j
public class FilePathUtils {

    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String  osName      = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    public static String getInputPath() {
        String path;
        if (isWindowsOS()) {
            path = "d://file//input//";
        } else {
            path = "/data/file/input/";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();//创建父级文件路径
        }
        return path;
    }

    public static String getTempPath() {
        String path;
        if (isWindowsOS()) {
            path = "d://file//temp//";
        } else {
            path = "/data/file/temp/";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();//创建父级文件路径
        }
        return path;
    }
    public static String getOutPutPath() {
        String path;
        if (isWindowsOS()) {
            path = "d://file//output//";
        } else {
            path = "/data/file/output/";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();//创建父级文件路径
        }
        return path;
    }

}
