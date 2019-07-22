package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.Lawyer345Dao;
import com._360pai.seimi.model.Lawyer345Contract;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Component
@Crawler(name = "lawyer345", useUnrepeated = false)
public class Lawyer345Crawler extends BaseSeimiCrawler{

    private static String rootUrl = "http://www.64365.com";
    @Autowired
    private Lawyer345Dao lawyer345Dao;
    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try{
            List<JXNode> jxNodes = doc.selN("//div[@class='other-ht r-3']");
            for (JXNode jxNode:jxNodes) {
                String bigCaName = getStringByList(jxNode.sel("/div/div[1]/a/text()"));
//                String[] bigCas = {"转让合同"};
//
//                List<String> asList = Arrays.asList(bigCas);
//
//                if (!asList.contains(bigCaName)){
//                    continue;
//                };

                List<JXNode> liNodes = jxNode.sel("/div/div[2]/ul/li");

                for (JXNode liNode:liNodes) {
                    String smallCaUrl = getStringByList(liNode.sel("/p/a/@href"));
                    String smallCaName = getStringByList(liNode.sel("/p/a/text()"));

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("bigCategory", bigCaName);
                    params.put("smallCategory", smallCaName);

                    Thread.sleep(2000);
                    push(Request.build(rootUrl + smallCaUrl,Lawyer345Crawler::getSmallCaHtml).setMeta(params));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getSmallCaHtml(Response response){

        try {
            JXDocument doc = response.document();
            List<JXNode> jxNodes = doc.selN("//div[@class='u-page mt40 pb20 tc']");
            String maxPage = getStringByList(jxNodes.get(0).sel("/div/div/a[last()-1]/text()"));
            for (int i = 1; i<Integer.parseInt(maxPage) + 1; i++) {
                String pageUrl = response.getUrl();
                Thread.sleep(2000);
                push(Request.build(pageUrl + "/" + i, Lawyer345Crawler::getPageHtml).setMeta(response.getMeta()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理小类数据异常{}", e.getMessage());
        }
    }

    public void getPageHtml(Response response){

        try {
            JXDocument doc = response.document();
            List<JXNode> jxNodes = doc.selN("//ul[@class='hetong-list']/li");

            for (JXNode detailNode:jxNodes ) {
                String downLoadUrl = getStringByList(detailNode.sel("/div/a/@href"));
                String name = getStringByList(detailNode.sel("/div/h3/a/text()"));

                Map<String, Object> meta = response.getMeta();
                String bigCategory = (String) meta.get("bigCategory");
                String smallCategory = (String) meta.get("smallCategory");

                System.out.println(downLoadUrl + name + bigCategory + smallCategory);

//                System.getProperties().setProperty("http.proxyHost", "121.31.149.3");
//                System.getProperties().setProperty("http.proxyPort", "8123");

                CookieStore cookieStore = new BasicCookieStore();
                HttpHost proxy = new HttpHost("121.31.149.3", 8123);
                CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

                //            downloadContract("http:" + downLoadUrl, httpClient, name, smallCategory, bigCategory);
                Lawyer345Contract lawyer345Contract = lawyer345Dao.getOneByCategoryName(name, smallCategory);


                //下载路径
                String fileUrl = "http:" + downLoadUrl;


                if(lawyer345Contract == null) {
                    Lawyer345Contract lawyer345Contract1Model = new Lawyer345Contract();
                    lawyer345Contract1Model.setBigCategory(bigCategory);
                    lawyer345Contract1Model.setSmallCategory(smallCategory);
                    lawyer345Contract1Model.setFileUrl("http:" + downLoadUrl);
                    lawyer345Contract1Model.setCreateTime(new Date());

                    lawyer345Contract1Model.setName(name);

                    Thread.sleep(2000);
                    lawyer345Dao.save(lawyer345Contract1Model);
//                    downloadContract(fileUrl, httpClient, lawyer345Contract1Model.getName(), smallCategory, bigCategory);
                    sendGet(fileUrl, httpClient, bigCategory,smallCategory,lawyer345Contract1Model.getName());
                }else if(lawyer345Contract != null){
                    if(!fileUrl.equals(lawyer345Contract.getFileUrl())){
                        String id = fileUrl.substring(fileUrl.indexOf("id=") + 3, fileUrl.indexOf("&&"));
                        Lawyer345Contract lawyer345Contract2 = lawyer345Dao.getOneByCategoryName(name + "_" + id, smallCategory);
                        if(lawyer345Contract2 == null) {

                            Lawyer345Contract lawyer345Contract1Model = new Lawyer345Contract();
                            lawyer345Contract1Model.setBigCategory(bigCategory);
                            lawyer345Contract1Model.setSmallCategory(smallCategory);
                            lawyer345Contract1Model.setFileUrl("http:" + downLoadUrl);
                            lawyer345Contract1Model.setCreateTime(new Date());

                            lawyer345Contract1Model.setName(name + "_" + id );
                            Thread.sleep(2000);
                            lawyer345Dao.save(lawyer345Contract1Model);
//                    downloadContract(fileUrl, httpClient, lawyer345Contract1Model.getName(), smallCategory, bigCategory);
                            sendGet(fileUrl, httpClient, bigCategory,smallCategory,lawyer345Contract1Model.getName());
                        }
                    }else {
                        File file  = new File("D:/lawyer365/contract/"+ bigCategory + "/" +smallCategory + "/" + name + ".doc");
                        Thread.sleep(2000);
                        if(!file.exists()) {
                            sendGet(fileUrl, httpClient, bigCategory,smallCategory,name);
                        }
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理分页数据异常{}", e.getMessage());
        }
    }


    /**
     * 下载合同
     */
    public void downloadContract(String fileUrl, CloseableHttpClient httpClient,
                                 String fileName, String smallCategory, String bigCategory) throws Exception{
        InputStream in = null;
        FileOutputStream out = null;
        File file = null;

        //特殊字符转换
        URL url1 = new URL(fileUrl);
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

        try {
            HttpGet getCityPage = new HttpGet(uri);
            CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);

            in = fileExecute.getEntity().getContent();
            file = new File("D:/lawyer345/contract/" + bigCategory + "/" + smallCategory);
            if (!file.exists()) {
                file.mkdirs();
            }
            file  = new File("D:/lawyer345/contract/"+ bigCategory + "/" +smallCategory + "/" + fileName + ".doc");
            if(!file.exists()) {
                file.createNewFile();
                out = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
            }

            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getStringByList(List<JXNode> noList) {
        return noList.size() > 0 ? noList.get(0).toString() : null;
    }


    public static void main(String[] args) {
//        CookieStore cookieStore = new BasicCookieStore();
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
//
//        InputStream in = null;
//        FileOutputStream out = null;
//        File file = null;
//        try{
//            HttpGet getCityPage = new HttpGet("http://download.64365.com/contact/default.aspx?id=675757&&type=1");
//            CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);
//
//            in = fileExecute.getEntity().getContent();
//            file = new File("D:/lawyer345/contract/"  );
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            file  = new File("D:/lawyer345/contract/" + "test.doc");
//            out = new FileOutputStream(file);
//            byte[] b = new byte[1024];
//            int len = 0;
//            while ((len = in.read(b)) != -1) {
//                out.write(b, 0, len);
//            }
//
//            if(in != null) {
//                in.close();
//            }
//            if(out != null) {
//                out.close();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        121.31.149.35:8123
        // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
//        System.getProperties().setProperty("http.proxyHost", "121.31.149.3");
//        System.getProperties().setProperty("http.proxyPort", "8123");
        // 判断代理是否设置成功
        // 发送 GET 请求
//        System.out.println(sendGet("http://download.64365.com/contact/default.aspx","id=675757&&type=1"));
        // 发送 POST 请求

    }


    public static String sendGet(String url, CloseableHttpClient httpClient, String bigCategory, String smallCategory, String fileName) {
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
