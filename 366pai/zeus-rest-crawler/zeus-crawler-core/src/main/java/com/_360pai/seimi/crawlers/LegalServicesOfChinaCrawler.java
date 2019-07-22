package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.LegalServicesOfChinaLawyer;
import com._360pai.seimi.model.LegalServicesOfChinaLawyerDep;
import com._360pai.seimi.service.LegalServicesOfChinaService;
import com._360pai.seimi.util.HttpUtilNew;
import com._360pai.seimi.util.HttpUtilNewModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 中国法律服务网
 */
@Crawler(name = "legalServicesOfChina", delay = 5)
@Component
public class LegalServicesOfChinaCrawler extends BaseSeimiCrawler {


    private static final String DEP_LIST_URL = "http://www.12348.gov.cn/lawerdeptlist/getlawerdeptlist";

    private static final String DEP_INFO_URL = "http://www.12348.gov.cn/lawdeptinfo/getlawdeptinfo";

    private static final String LAWYER_LIST_URL = "http://www.12348.gov.cn/lawdeptinfo/getlawerlist";

    @Autowired
    private LegalServicesOfChinaService legalServicesOfChinaService;

    @Override
    public String[] startUrls() {
        return new String[] {"http://www.12348.gov.cn/#/publicies/lawdept/lawdept"};
    }

    //@Override
    //public List<Request> startRequests() {
    //    List<Request> requests = new ArrayList<>();
    //    requests.add(Request.build("http://www.12348.gov.cn/lawerdeptlist/getlawerdeptlist", LegalServicesOfChinaCrawler::start).setParams());
    //    return requests;
    //}

    @Override
    public void start(Response response) {
        getDepList();
        syncLawyerDepAndLawyers();
    }

    private void getDepList() {
        int pageNum = 1;
        while (true) {
            try {
                logger.info("当前页码={}", pageNum);
                List<JSONObject> dataList = new ArrayList<>();

                JSONObject json = new JSONObject();
                json.put("pageSize", "500");
                json.put("pageNum", pageNum + "");
                json.put("pzslsj", "0");
                json.put("nums", "0");
                json.put("yw", "");
                json.put("xzqh", "");
                HttpUtilNewModel res = HttpUtilNew.post(DEP_LIST_URL, json);
                if (res == null || 200 != res.getStatusCode()) {
                    break;
                }
                if (StringUtils.isEmpty(res.getHtml())) {
                    break;
                }
                JSONObject result = JSON.parseObject(res.getHtml());
                if (!result.containsKey("list")) {
                    break;
                }
                JSONArray list = result.getJSONArray("list");
                if (list.isEmpty()) {
                    break;
                }
                for (Object o : list) {
                    try {
                        JSONObject dep = (JSONObject) o;
                        String lsswsbs = dep.getString("lsswsbs");
                        LegalServicesOfChinaLawyerDep lawyerDep = legalServicesOfChinaService.findByLsswsbs(lsswsbs);
                        if (lawyerDep == null) {
                            lawyerDep = new LegalServicesOfChinaLawyerDep();
                            lawyerDep.setCreateTime(new Date());
                        }
                        lawyerDep.setLsswsbs(lsswsbs);
                        lawyerDep.setZsdh(dep.getString("ZSDH"));
                        String xzqh = dep.getString("xzqh");
                        lawyerDep.setXzqh(xzqh);
                        if (StringUtils.isNotEmpty(xzqh)) {
                            if (xzqh.length() >= 2) {
                                lawyerDep.setProvcode(StringUtils.isEmpty(xzqh) ? "" : xzqh.substring(0,2));
                            }
                            if (xzqh.length() >= 4) {
                                lawyerDep.setCitycode(StringUtils.isEmpty(xzqh) ? "" : xzqh.substring(0,4));
                            }
                        }
                        lawyerDep.setZt(dep.getString("zt"));
                        lawyerDep.setNums(dep.getString("nums"));
                        lawyerDep.setUpdateTime(new Date());
                        legalServicesOfChinaService.saveLawyerDep(lawyerDep);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pageNum ++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }


    private void syncLawyerDepAndLawyers() {
        int page = 0;
        while (true) {
            logger.info("当前页码={}", page);
            Page<LegalServicesOfChinaLawyerDep> pageList = legalServicesOfChinaService.findNeedToUpdate(page, 50);
            if (!pageList.hasContent()) {
                break;
            }
            for (LegalServicesOfChinaLawyerDep lawyerDep : pageList.getContent()) {
                try {
                    getDepInfo(lawyerDep.getLsswsbs());
                    getLawyerList(lawyerDep.getLsswsbs());
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                //try {
                //    Thread.sleep(RandomUtils.nextInt(800, 1500));
                //} catch (InterruptedException e2) {
                //    e2.printStackTrace();
                //}
            }
            //try {
            //    Thread.sleep(RandomUtils.nextInt(2000, 5000));
            //} catch (InterruptedException e2) {
            //    e2.printStackTrace();
            //}
            page ++;
        }

    }

    private void getDepInfo(String lsswsbs) {
        //try {
        //    Thread.sleep(500);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        logger.info("请求律所信息，lsswsbs={}", lsswsbs);
        NameValuePair[] params = new NameValuePair[]{
                new BasicNameValuePair("lsswsbs", lsswsbs)
        };
        JSONObject json = new JSONObject();
        json.put("lsswsbs", lsswsbs);
        List<JSONObject> dataList = new ArrayList<>();
        HttpUtilNewModel res = HttpUtilNew.post(DEP_INFO_URL, json);
        if (res == null || 200 != res.getStatusCode()) {
            return;
        }
        if (StringUtils.isEmpty(res.getHtml())) {
            return;
        }
        LegalServicesOfChinaLawyerDep lawyerDep = legalServicesOfChinaService.findByLsswsbs(lsswsbs);
        if (lawyerDep == null) {
            lawyerDep = new LegalServicesOfChinaLawyerDep();
            lawyerDep.setCreateTime(new Date());
        }
        JSONObject result = JSON.parseObject(res.getHtml());

        lawyerDep.setLsswsbs(lsswsbs);
        lawyerDep.setPzslsj(result.getString("pzslsj"));
        lawyerDep.setLsswsmc(result.getString("lsswsmc"));
        lawyerDep.setJd(result.getString("jd"));
        lawyerDep.setZsd(result.getString("zsd"));
        lawyerDep.setZyzh(result.getString("zyzh"));
        lawyerDep.setDistrict(result.getString("district"));
        lawyerDep.setImgurl("http://www.12348.gov.cn/resources/images/liz/lsjg.png");

        lawyerDep.setWd(result.getString("wd"));
        lawyerDep.setJj(result.getString("jj"));
        lawyerDep.setTyshxydm(result.getString("tyshxydm"));
        lawyerDep.setYezc(result.getString("yezc"));
        lawyerDep.setYwzclist(result.getJSONArray("ywzclist").toJSONString());
        lawyerDep.setUpdateTime(new Date());
        legalServicesOfChinaService.saveLawyerDep(lawyerDep);
    }

    private void getDepInfo(JSONObject dep, String lsswsbs) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("请求律所信息，lsswsbs={}", lsswsbs);
        NameValuePair[] params = new NameValuePair[]{
                new BasicNameValuePair("lsswsbs", lsswsbs)
        };
        JSONObject json = new JSONObject();
        json.put("lsswsbs", lsswsbs);
        List<JSONObject> dataList = new ArrayList<>();
        HttpUtilNewModel res = HttpUtilNew.post(DEP_INFO_URL, json);
        if (res == null || 200 != res.getStatusCode()) {
            return;
        }
        if (StringUtils.isEmpty(res.getHtml())) {
            return;
        }
        LegalServicesOfChinaLawyerDep lawyerDep = legalServicesOfChinaService.findByLsswsbs(lsswsbs);
        if (lawyerDep == null) {
            lawyerDep = new LegalServicesOfChinaLawyerDep();
            lawyerDep.setCreateTime(new Date());
        }
        JSONObject result = JSON.parseObject(res.getHtml());
        lawyerDep.setZsdh(dep.getString("ZSDH"));
        String xzqh = dep.getString("xzqh");
        lawyerDep.setXzqh(xzqh);
        if (StringUtils.isNotEmpty(xzqh)) {
            if (xzqh.length() >= 2) {
                lawyerDep.setProvcode(StringUtils.isEmpty(xzqh) ? "" : xzqh.substring(0,2));
            }
            if (xzqh.length() >= 4) {
                lawyerDep.setCitycode(StringUtils.isEmpty(xzqh) ? "" : xzqh.substring(0,4));
            }

        }

        lawyerDep.setZt(dep.getString("zt"));
        lawyerDep.setNums(dep.getString("nums"));

        lawyerDep.setLsswsbs(lsswsbs);
        lawyerDep.setPzslsj(result.getString("pzslsj"));
        lawyerDep.setLsswsmc(result.getString("lsswsmc"));
        lawyerDep.setJd(result.getString("jd"));
        lawyerDep.setZsd(result.getString("zsd"));
        lawyerDep.setZyzh(result.getString("zyzh"));
        lawyerDep.setDistrict(result.getString("district"));
        lawyerDep.setImgurl("http://www.12348.gov.cn/resources/images/liz/lsjg.png");

        lawyerDep.setWd(result.getString("wd"));
        lawyerDep.setJj(result.getString("jj"));
        lawyerDep.setTyshxydm(result.getString("tyshxydm"));
        lawyerDep.setYezc(result.getString("yezc"));
        lawyerDep.setYwzclist(result.getJSONArray("ywzclist").toJSONString());
        lawyerDep.setUpdateTime(new Date());
        legalServicesOfChinaService.saveLawyerDep(lawyerDep);
    }

    private void getLawyerList(String lsswsbs) {
        //try {
        //    Thread.sleep(2000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        logger.info("请求律师信息，lsswsbs={}", lsswsbs);
        NameValuePair[] params = new NameValuePair[]{
                new BasicNameValuePair("pageSize", "100000"),
                new BasicNameValuePair("pageNum", "1"),
                new BasicNameValuePair("pkid", lsswsbs)
        };
        JSONObject json = new JSONObject();
        json.put("pageSize", "100000");
        json.put("pageNum", "1");
        json.put("pkid", lsswsbs);
        List<JSONObject> dataList = new ArrayList<>();
        HttpUtilNewModel res = HttpUtilNew.post(LAWYER_LIST_URL, json);
        if (res == null || 200 != res.getStatusCode()) {
            return;
        }
        if (StringUtils.isEmpty(res.getHtml())) {
            return;
        }
        JSONObject result = JSON.parseObject(res.getHtml());
        if (!result.containsKey("list")) {
            return;
        }
        logger.info("请求律师信息，lsswsbs={}, list={}", lsswsbs, result.getJSONArray("list").toJSONString());
        JSONArray list = result.getJSONArray("list");
        for (Object o : list) {
            try {
                JSONObject data = (JSONObject) o;
                logger.info("处理律师信息，lsswsbs={}，lsbs={}", lsswsbs, data.getString("lsbs"));
                LegalServicesOfChinaLawyer lawyer = legalServicesOfChinaService.findByLsbs(data.getString("lsbs"));
                if (lawyer == null) {
                    lawyer = new LegalServicesOfChinaLawyer();
                    lawyer.setCreateTime(new Date());
                }
                lawyer.setLsswsbs(lsswsbs);
                lawyer.setLsbs(data.getString("lsbs"));
                lawyer.setImgUrl(data.getString("PIC"));
                lawyer.setXm(data.getString("xm"));
                lawyer.setYears(data.getString("years"));
                lawyer.setLsswsmc(data.getString("lsswsmc"));
                lawyer.setYwzc(data.getJSONArray("ywzc").toJSONString());
                lawyer.setUpdateTime(new Date());
                legalServicesOfChinaService.saveLawyer(lawyer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
