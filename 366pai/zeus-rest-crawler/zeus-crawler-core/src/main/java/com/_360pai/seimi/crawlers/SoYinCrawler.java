package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.SoYinDao;
import com._360pai.seimi.model.SoYinContract;
import com._360pai.seimi.service.SoYinService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tools.ant.taskdefs.Execute;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.*;

@Component
@Crawler(name = "soyin", useUnrepeated = false)
public class SoYinCrawler extends BaseSeimiCrawler {

    private static String baseUrl = "http://www.soyin.cn/contract/index.html";

    private static String rootUrl = "http://www.soyin.cn";

    private static String downloadUrl = "http://www.soyin.cn/contract/Download";
    @Autowired
    private SoYinDao soYinDao;

    @Autowired
    private SoYinService soYinService;

    private CloseableHttpClient httpClient = null;

    int loginFlag = 1;

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();

        List<JXNode> jxNodes = doc.selN("//div[@class='channel-category']");
        for (JXNode node:jxNodes) {
            List<JXNode> liNodes = node.sel("/ul/li");
            for (JXNode liNode:liNodes) {
                //大类名称
                String bigCategory = getStringValue(liNode.sel("/h2/a/text()").get(0));
                List<JXNode> pNodes = liNode.sel("/p");
                for (JXNode pNode : pNodes) {
                    //小类名称
                    String smallCategory = getStringValue(pNode.sel("/a/text()").get(0));
                    //小类路径
                    String pageUrl = getStringValue(pNode.sel("/a/@href").get(0));
                    //类别id
                    String cid = pageUrl.substring(pageUrl.lastIndexOf("_") + 1, pageUrl.lastIndexOf("."));

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("bigCategory", bigCategory);
                    params.put("smallCategory", smallCategory);
                    params.put("cid", cid);

                    push(Request.build(rootUrl + pageUrl,SoYinCrawler::getPageHtml).setMeta(params));

                }
            }
        }
    }

    public void getPageHtml(Response response){
        JXDocument doc = response.document();

        try {
            List<JXNode> jxNodes = doc.selN("//div[@class='list-article']/ul/li");

            for (JXNode jxNode: jxNodes) {
                System.out.println(jxNode);
                String name = getStringValue(jxNode.sel("/a[1]/text()").get(0));
                String detailUrl = getStringValue(jxNode.sel("/a[1]/@href").get(0));

                String id = detailUrl.substring(detailUrl.lastIndexOf("_") + 1, detailUrl.lastIndexOf("."));

                Map<String, Object> params = response.getMeta();
                params.put("name", name);
                params.put("id", id);

                push(Request.build(rootUrl + detailUrl,SoYinCrawler::getDetailHtml).setMeta(params));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理分页数据异常{}", e.getMessage());
        }
    }


    public void getDetailHtml(Response response){
        Map<String, Object> params = response.getMeta();

        String cid = (String) params.get("cid");
        String id = (String) params.get("id");
        String smallCategory = (String) params.get("smallCategory");
        String bigCategory = (String) params.get("bigCategory");
        String name = (String) params.get("name");

        try {
            if(httpClient == null) {
                httpClient  = soYinService.getLoginType();
            }
            //获取下载地址
            String downloadUrl = getDownloadUrl(httpClient, cid, id, smallCategory, name);
            //进行下载操作
            SoYinContract soYinContract = new SoYinContract();
            soYinContract.setFileUrl(downloadUrl);
            soYinContract.setBigCategory(bigCategory);
            soYinContract.setSmallCategory(smallCategory);
            soYinContract.setModelHtml(null);
            soYinContract.setCreateTime(new Date());

            soYinDao.save(soYinContract);

//            downloadContract(downloadUrl, httpClient, name);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理详情数据异常{}", e.getMessage());
        }
    }

    /**
     * 获取下载路径
     * @param httpClient
     * @param cid
     * @param id
     * @param smallCategory
     * @param name
     * @throws Exception
     */
    private String getDownloadUrl(CloseableHttpClient httpClient,
                                String cid, String id, String smallCategory, String name) throws Exception{

        HttpPost httpPost = new HttpPost(downloadUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id", id));   //自己用户名
        nameValuePairs.add(new BasicNameValuePair("cid", cid));//
        nameValuePairs.add(new BasicNameValuePair("title", name));//
        nameValuePairs.add(new BasicNameValuePair("ctitle", smallCategory));//
        nameValuePairs.add(new BasicNameValuePair("userid", "156489"));//


        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        String fileUrl = EntityUtils.toString(httpResponse.getEntity());
        return fileUrl;
    }

    /**
     * 下载图片
     */
    public void downloadContract(String fileUrl, CloseableHttpClient httpClient,
                                 String fileName) throws Exception{
        InputStream in = null;
        FileOutputStream out = null;
        File file = null;

        //特殊字符转换
        System.out.println(fileUrl);
        URL url1 = new URL(fileUrl.replaceAll("\"",""));
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

        try {
            HttpGet getCityPage = new HttpGet(uri);
            CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);

            in = fileExecute.getEntity().getContent();
            file = new File("D:/soyin/contract");
            if (!file.exists()) {
                file.mkdirs();
            }
            file  = new File("D:/soyin/contract/" + fileName + ".doc");
            out = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
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

    //测试下载是否成功
    public static void main(String[] args) throws Exception{

        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        String s1 = "\"http://upload.soyin.cn//Upload/20180509/2018050917311467945302.doc\"";

        URL url1 = new URL(s1.replaceAll("\"",""));
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

        HttpGet getCityPage = new HttpGet(uri);
        CloseableHttpResponse httpResponse = httpClient.execute(getCityPage);

        InputStream in = null;
        FileOutputStream out = null;
        File file = null;

        in = httpResponse.getEntity().getContent();
        file = new File("D:/soyin/contract");
        if (!file.exists()) {
            file.mkdirs();
        }
        file  = new File("D:/soyin/contract/" + "测试能成功吗" + ".doc");
        out = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }

        if(in != null) {
            in.close();
        }
        if(out != null) {
            out.close();
        }

    }


    private String getStringValue(Object object) {
        if(object == null) {
            return "";
        }else {
            return object.toString();
        }
    }
}
