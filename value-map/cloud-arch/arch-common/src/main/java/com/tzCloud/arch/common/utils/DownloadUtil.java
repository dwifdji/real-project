package com.tzCloud.arch.common.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: DownloadUtil
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/12 18:24
 */
public class DownloadUtil {

    public static void downloadExcel(HttpServletRequest request, HttpServletResponse response, String fileName, String sheetName, List<Map<String, Object>> list, String[] keys, String[] columnNames) {
        try {
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0
                    || userAgent.indexOf("Safari") >= 0) {
                fileName = new String((fileName).getBytes(), "ISO8859-1") + ".xls";
            } else {
                fileName = URLEncoder.encode(fileName, "UTF8") + ".xls"; //其他浏览器
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ExcelUtil.createWorkBook(list, keys, columnNames, sheetName).write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadExcel(HttpServletRequest request, HttpServletResponse response, Map<String, Object> params) {
        try {
            String fileName = (String) params.get("fileName");
            String sheetName = (String) params.get("sheetName");
            List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
            String[] keys = (String[]) params.get("keys");
            String[] columnNames = (String[]) params.get("columnNames");
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0
                    || userAgent.indexOf("Safari") >= 0) {
                fileName = new String((fileName).getBytes(), "ISO8859-1") + ".xls";
            } else {
                fileName = URLEncoder.encode(fileName, "UTF8") + ".xls"; //其他浏览器
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ExcelUtil.createWorkBook(list, keys, columnNames, sheetName).write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
