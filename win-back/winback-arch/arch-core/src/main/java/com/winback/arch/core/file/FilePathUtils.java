package com.winback.arch.core.file;

import com.winback.arch.common.HttpUtils;
import com.winback.arch.core.file.watermark.ImageMarkUtil;
import com.winback.arch.core.file.watermark.PdfMarkUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @throws IOException
     */
    public static File downLoadFromUrl(String urlStr, String fileName) {
        try {

            URL               url  = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);

            // 文件保存位置
            File saveDir = new File(getInputPath());
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File             file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos  = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            log.trace("info:" + url + " download success");

//            return saveDir + File.separator + fileName;
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[]                buffer = new byte[1024];
        int                   len    = 0;
        ByteArrayOutputStream bos    = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) throws Exception {
//        String url      = "https://cdn-images.360pai.com/hcigy8zosyv.jfif?attname=债权清单.jfif";
//        String url      = "https://cdn-images.360pai.com/lrc6CeSLkpoCA19SN8mYfr5JneFa.rar?attname=lrc6CeSLkpoCA19SN8mYfr5JneFa.rar";
//        String fileName = getFileName(url);
//        System.out.println(fileName);
//        String fileFormat = getFileFormat(url);
//        System.out.println(fileFormat);
//        boolean contains = acceptFileFormat.contains(fileFormat);
//        log.trace("contains:" + contains);
//        if (contains) {
////            String s = downLoadFromUrl(split[0], fileName, getOutPutPath());
//        }

        getInputPath();
        getOutPutPath();

        downLoadWatermarkFileFromUrl("https://cdn-images-test.360pai.com/Folx5YSfGLC9IEFt39Pg295tyFDm", "1.jpeg");

    }

    public static Boolean acceptFile(String fileUrl) {
        String fileFormat = getFileFormat(fileUrl);
        return acceptFileFormat.contains(fileFormat);
    }

    public static String getFileName(String url) {
        String[] split  = url.split("\\?");
        String[] split1 = split[0].split("/");
        return split1[split1.length - 1];
    }

    private static final String       FILE_FORMAT_PDF   = "application/pdf";
    private static final String       FILE_FORMAT_IMAGE = "image/jpeg";
    private static final String       FILE_FORMAT_PNG   = "image/png";
    private static       List<String> acceptFileFormat  = new ArrayList<>();

    static {
        acceptFileFormat.add(FILE_FORMAT_PDF);
        acceptFileFormat.add(FILE_FORMAT_IMAGE);
        acceptFileFormat.add(FILE_FORMAT_PNG);
    }

    private static String getFileFormat(String url) {
        String[]   split      = url.split("\\?");
        String     s          = HttpUtils.sendGet(split[0] + "?stat");
        JSONObject jsonObject = JSONObject.parseObject(s);
        return jsonObject.get("mimeType") + "";
    }

    //    private static String waterMarkByImg =   "/resources/image/水印.png";
    private static String waterMarkByImg;

    static {
        try {
//            File file = ResourceUtils.getFile("classpath:image/水印.png");
//            waterMarkByImg = file.getCanonicalPath();
            waterMarkByImg = getInputPath() + "赢回来水印.png";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File downLoadWatermarkFileFromUrl(String url, String fileName) {
        String fileFormat = getFileFormat(url);
        File   saveDir    = new File(getInputPath());
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        if (file.exists()) {
            if (FILE_FORMAT_IMAGE.equals(fileFormat) || FILE_FORMAT_PNG.equals(fileFormat)) {
                ImageMarkUtil.waterMarkByImg(waterMarkByImg, saveDir + File.separator + fileName, getOutPutPath() + File.separator + fileName, 0);
            } else if (FILE_FORMAT_PDF.equals(fileFormat)) {
                //pdf文字水印
//                PdfMarkUtil.waterMark(file.getAbsolutePath(), getOutPutPath() + File.separator + fileName, "360pai.com");
                //pdf图片水印
                PdfMarkUtil.addImageWatermark(file.getAbsolutePath(), getOutPutPath() + File.separator + fileName, waterMarkByImg);
            }
        }
        return new File(getOutPutPath() + File.separator + fileName);
    }

    public static File watermarkFile(File file) {
        if (file.exists()) {
            String fileFormat = new MimetypesFileTypeMap().getContentType(file);
            if (FILE_FORMAT_IMAGE.equals(fileFormat) || FILE_FORMAT_PNG.equals(fileFormat)) {
                ImageMarkUtil.waterMarkByImg(waterMarkByImg, file.getPath(), file.getParent() + File.separator + "r" + file.getName(), 0);
            } else if (FILE_FORMAT_PDF.equals(fileFormat)) {
                //pdf文字水印
//                PdfMarkUtil.waterMark(file.getAbsolutePath(), getOutPutPath() + File.separator + fileName, "360pai.com");
                //pdf图片水印
                PdfMarkUtil.addImageWatermark(file.getAbsolutePath(), file.getParent() + File.separator + "r" + file.getName(), waterMarkByImg);
            }
        }
        return new File(file.getParent() + File.separator + "r" + file.getName());
    }
}
