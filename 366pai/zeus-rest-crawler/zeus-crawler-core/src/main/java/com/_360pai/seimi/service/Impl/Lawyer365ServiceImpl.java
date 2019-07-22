package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.Lawyer345Dao;
import com._360pai.seimi.model.Lawyer345Contract;
import com._360pai.seimi.service.Lawyer365Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class Lawyer365ServiceImpl implements Lawyer365Service {
    @Autowired
    private Lawyer345Dao lawyer345Dao;
    @Override
    public void downLoadUrls() {
//        List<Lawyer345Contract> all = lawyer345Dao.findAll();

        List<Lawyer345Contract> allDo = lawyer345Dao.getLawyer345ContractList();


        Integer flag = 0;
        Set<Integer> idSet = new HashSet<>();
        for (Lawyer345Contract lawyer345Contract: allDo) {

            List<Lawyer345Contract> allContractList =
                    lawyer345Dao.getOneByCategoryListName(lawyer345Contract.getName(), lawyer345Contract.getSmallCategory());

            if(allContractList.size()==2) {
                idSet.add(allContractList.get(1).getId());
                flag ++;
            }

        }

        System.out.println("最终的数量是" + flag);
        System.out.println("最终的数量是" + idSet);

        for (Integer id: idSet) {
            lawyer345Dao.delete(id);
        }
//        List<File> fileList = new ArrayList<>();
//        fileList = getFileList("D:/lawyer365/contract", fileList);
//        Integer fileFlag = 0;
//
//
//        for (File fileNameModel:fileList) {
//
//            for (Lawyer345Contract lawyer345Contract: allDo) {
//
//                String fileName = "D:\\lawyer365\\contract\\" + lawyer345Contract.getBigCategory() + "\\" + lawyer345Contract.getSmallCategory()
//                        + "\\" + lawyer345Contract.getName() + ".doc";
//                if(fileNameModel.getAbsolutePath().equals(fileName)) {
//                    fileFlag = 1;
//                }
//            }
//
//            if(fileFlag == 0) {
//                System.out.println(fileNameModel.getAbsolutePath());
//
//                fileNameModel.delete();
//            }
//
//            fileFlag = 0;
//        }

    }

    public static List<File> getFileList(String strPath, List<File> fileList) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath(), fileList); // 获取文件绝对路径
                } else { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
//                    filelist.add(files[i]);
//                    filelist.add(fileName);
                    fileList.add(files[i]);
                }
            }
        }

        return fileList;
    }


    public static String sendGet(String url, String bigCategory, String smallCategory, String fileName) {
        String result = "";
        InputStream in = null;
        FileOutputStream out = null;
        try {

            URL url1 = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = url1.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = connection.getInputStream();
//            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
//            HttpGet getCityPage = new HttpGet(uri);
//            CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);
//            in = fileExecute.getEntity().getContent();

            File file = new File("D:/lawyer365/contract/" + bigCategory + "/" + smallCategory );
            if (!file.exists()) {
                file.mkdirs();
            }
            file  = new File("D:/lawyer365/contract/"+ bigCategory + "/" +smallCategory + "/" + fileName + ".doc");
            if(!file.exists()) {
                file.createNewFile();
                out = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
