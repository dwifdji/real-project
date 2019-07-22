package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.SoYinBigCategoryDao;
import com._360pai.seimi.dao.SoYinDao;
import com._360pai.seimi.dao.SoYinSmallCategoryDao;
import com._360pai.seimi.model.SoYinBigCategory;
import com._360pai.seimi.model.SoYinContract;
import com._360pai.seimi.model.SoYinSmallCategory;
import com._360pai.seimi.service.SoYinService;
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
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.*;

@Service
public class SoYinServiceImpl implements SoYinService {

    private static String imageUrl = "http://www.soyin.cn/contract/ChangeCode?date";

    private static String msgCodeUrl = "http://www.soyin.cn/contract/Code?phone=15538068782";

    private static String baseUrl = "http://www.soyin.cn/contract/index.html";

    private static String rootUrl = "http://www.soyin.cn";

    private static String downloadUrl = "http://www.soyin.cn/contract/Download";
    @Autowired
    private SoYinDao soYinDao;
    @Autowired
    private SoYinBigCategoryDao soYinBigCategoryDao;
    @Autowired
    private SoYinSmallCategoryDao soYinSmallCategoryDao;


    @Override
    public void getSoYinContract() {

        try{
            CloseableHttpClient httpClient = getLoginType();

            HttpGet getCityPage = new HttpGet(baseUrl);
            CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);
            String html = EntityUtils.toString(fileExecute.getEntity());

            JXDocument doc = JXDocument.create(html);

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

                        getListData(httpClient,rootUrl + pageUrl, params);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendDataToWin() {
        List<SoYinContract> allCategory = soYinDao.findAll();

        Set<String> bigCategorySet = new HashSet<String>();
        for (SoYinContract soYinContract :allCategory) {
            bigCategorySet.add(soYinContract.getBigCategory());
        }

        List<String> bigCateggoryList = new ArrayList<>();
        bigCateggoryList.addAll(bigCategorySet);
        for(int i = 0; i< bigCateggoryList.size(); i++) {
            SoYinBigCategory soYinBigCategory = new SoYinBigCategory();
            soYinBigCategory.setName(bigCateggoryList.get(i));
            soYinBigCategory.setDisplay(true);
            soYinBigCategory.setDeleteFlag(false);
            soYinBigCategory.setCreateTime(new Date());
            soYinBigCategory.setUpdateTime(new Date());
            soYinBigCategory.setOrderNum(i);

            SoYinBigCategory save = soYinBigCategoryDao.save(soYinBigCategory);

            List<String> soYinSmallCategories = new ArrayList<>();
            Set<String> smallCategorys = new HashSet<>();
            List<SoYinContract> soYinContracts = soYinDao.getSmallCategoryListByName(bigCateggoryList.get(i));
            for (int j = 0; j<soYinContracts.size(); j++) {
                smallCategorys.add(soYinContracts.get(j).getSmallCategory());
            }


            soYinSmallCategories.addAll(smallCategorys);

            for (int z = 0; z <soYinSmallCategories.size();z++) {
                SoYinSmallCategory soYinSmallCategory = new SoYinSmallCategory();

                soYinSmallCategory.setName(soYinSmallCategories.get(z));
                soYinSmallCategory.setOrderNum(z);
                soYinSmallCategory.setDeleteFlag(false);
                soYinSmallCategory.setDisplay(true);
                soYinSmallCategory.setCreateTime(new Date());
                soYinSmallCategory.setUpdateTime(new Date());
                soYinSmallCategory.setBigTypeId(save.getId());

                soYinSmallCategoryDao.save(soYinSmallCategory);
            }

        }
    }


    private void getListData(CloseableHttpClient httpClient, String listUrl, Map<String, Object> params) throws Exception{

        HttpGet getCityPage = new HttpGet(listUrl);
        CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);
        String html = EntityUtils.toString(fileExecute.getEntity());

        JXDocument doc = JXDocument.create(html);

        List<JXNode> jxNodes = doc.selN("//div[@class='list-article']/ul/li");

        for (JXNode jxNode: jxNodes) {
//            System.out.println(jxNode);
            if(jxNode.sel("/a[1]/text()").size() > 0) {
                String name = getStringValue(jxNode.sel("/a[1]/text()").get(0));
                String detailUrl = getStringValue(jxNode.sel("/a[1]/@href").get(0));

                String id = detailUrl.substring(detailUrl.lastIndexOf("_") + 1, detailUrl.lastIndexOf("."));

                params.put("name", name);
                params.put("id", id);

                getDetailHtml(httpClient,rootUrl + detailUrl, params);
            }

        }
    }

    private void getDetailHtml(CloseableHttpClient httpClient, String detailUrl, Map<String, Object> params) throws Exception {
        HttpGet getCityPage = new HttpGet(detailUrl);
        CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);
        String html = EntityUtils.toString(fileExecute.getEntity());

        String cid = (String) params.get("cid");
        String id = (String) params.get("id");
        String smallCategory = (String) params.get("smallCategory");
        String bigCategory = (String) params.get("bigCategory");
        String name = (String) params.get("name");

        try {
            //获取下载地址
            SoYinContract soYinContract = soYinDao.getOneByName(name, smallCategory);
            //进行下载操作
            if(soYinContract == null) {
                String downloadUrl = getDownloadUrl(httpClient, cid, id, smallCategory, name);
                soYinContract = new SoYinContract();
                soYinContract.setFileUrl(downloadUrl);
                soYinContract.setBigCategory(bigCategory);
                soYinContract.setSmallCategory(smallCategory);
                soYinContract.setModelHtml(null);
                soYinContract.setCreateTime(new Date());
                soYinContract.setName(name);

                soYinDao.save(soYinContract);
                System.out.println("开始创建新的文件" + name);
                downloadContract(downloadUrl, httpClient, name, smallCategory, bigCategory);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
                                 String fileName, String smallCategory, String bigCategory) throws Exception{
        InputStream in = null;
        FileOutputStream out = null;
        File file = null;

        //特殊字符转换
        URL url1 = new URL(fileUrl.replaceAll("\"",""));
        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

        try {
            HttpGet getCityPage = new HttpGet(uri);
            CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);

            in = fileExecute.getEntity().getContent();
            file = new File("D:/soyin/contract/" + bigCategory + "/" + smallCategory);
            if (!file.exists()) {
                file.mkdirs();
            }
            file  = new File("D:/soyin/contract/"+ bigCategory + "/" +smallCategory + "/" + fileName + ".doc");
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


    @Override
    public CloseableHttpClient getLoginType() {

        try{
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            //模拟讼赢登陆
            downloadImage(imageUrl, httpClient);

            Scanner sc = new Scanner(System.in);
            System.out.println("请输入图片验证码");
            String pic = sc.nextLine();

            getMsgCode("15538068782", httpClient);
            Scanner sc2 = new Scanner(System.in);
            System.out.println("请输入短信验证码");
            String code = sc2.nextLine();

            HttpPost httpPost = new HttpPost("http://www.soyin.cn/login/login");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("phone", "15538068782"));   //自己用户名
            nameValuePairs.add(new BasicNameValuePair("pic", pic));
            nameValuePairs.add(new BasicNameValuePair("code", code));//短信验证码

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

            String html = EntityUtils.toString(httpResponse.getEntity());

            System.out.println(html);

            return httpClient;
        }catch (Exception e){
            e.printStackTrace();
        }
            return null;
    }



    private void getMsgCode(String s, CloseableHttpClient httpClient) throws Exception{

        HttpGet codeGet = new HttpGet(msgCodeUrl);
        CloseableHttpResponse execute = httpClient.execute(codeGet);
        String codeRsponse = EntityUtils.toString(execute.getEntity());

        System.out.println(codeRsponse);
    }


    /**
     * 下载图片
     */
    public void downloadImage(String imageUrl, CloseableHttpClient httpClient) {
        InputStream in = null;
        FileOutputStream out = null;
        File file = null;

        try {
            HttpPost httpPost = new HttpPost( imageUrl + System.currentTimeMillis());
            CloseableHttpResponse imageExecute = httpClient.execute(httpPost);

            in = imageExecute.getEntity().getContent();
            file = new File("D:/douban/images");
            if (!file.exists()) {
                file.mkdirs();
            }
            file  = new File("D:/douban/images/1212.jpg");
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


    private String getStringValue(Object object) {
        if(object == null) {
            return "";
        }else {
            return object.toString();
        }
    }
}
