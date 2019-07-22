package com._360pai.arch.core.file;

import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.ResumeUploader;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Component
@DependsOn("gatewayProperties")
public class QiNiuUtil {

    @Autowired
    private GatewayProperties gatewayProperties;

    private FileRecorder recorder = null;
    private Response     response = null;

    public String uploadToPublic(File f, String expectKey) throws IOException {
        recorder = new FileRecorder(f.getParentFile());
        final String token = Auth.create(gatewayProperties.getAccessKey(), gatewayProperties.getSecretKey()).uploadToken(gatewayProperties.getBucket(), expectKey);

        final Up up = new Up(f, expectKey, token);
        try {
            response = up.up(Zone.zone0());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.isOK()) {
            return expectKey;
        } else {
            return "";
        }
    }

    class Up {
        private final File   file;
        private final String key;
        private final String token;
        ResumeUploader uploader = null;

        public Up(File file, String key, String token) {
            this.file = file;
            this.key = key;
            this.token = token;
        }

        public void close() {
            try {
                Method m_close = ResumeUploader.class.getDeclaredMethod("close", new Class[]{});
                m_close.setAccessible(true);
                m_close.invoke(uploader, new Object[]{});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        public Response up(Zone zone) throws Exception {
            try {
                if (recorder == null) {
                    recorder = new FileRecorder(file.getParentFile());
                }
                final Client client = new Client();
                uploader = new ResumeUploader(client, token, key, file,
                        null, Client.DefaultMime, recorder, new Configuration(zone));
                Response res = uploader.upload();
                System.out.println("UP:  " + ", left up");
                return res;
            } catch (Exception e) {
                System.out.println("UP:  " + ", exception up");
                throw e;
            }
        }
    }

    private static JSONObject nativeGet(String url) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String        line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return JSONObject.parseObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {// 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public String getQETag(File file) {
        String result = "";
        QETag  eTag   = new QETag();
        try {
            System.out.println(eTag.calcETag(file.getAbsolutePath()));
            result = eTag.calcETag(file.getAbsolutePath());
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Unsupported algorithm:" + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("IO Error:" + ex.getMessage());
        }
        return result;
    }

    public void syncFile() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
        BucketManager bucketManager = new BucketManager(Auth.create(gatewayProperties.getAccessKey(), gatewayProperties.getSecretKey()), cfg);
//文件名前缀
        String prefix = "";
//每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
//列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(gatewayProperties.getBucket(), prefix, limit, delimiter);
        int                            i                = 0;
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                System.out.println(gatewayProperties.getDomain() + item.key);
                System.out.println(item.hash);
                System.out.println(item.fsize);
                System.out.println(item.mimeType);
                System.out.println(item.putTime / 10000);
                System.out.println(item.endUser);
                System.out.println("==========================");
                i++;
            }
        }
        System.out.println(i);
    }
}