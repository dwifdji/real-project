package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.TInvestmentAgency;
import com._360pai.seimi.model.TInvestmentNews;
import com._360pai.seimi.model.TInvestmentOffice;
import com._360pai.seimi.model.TInvestmentRecruitment;
import com._360pai.seimi.service.*;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by liuhaolei on 2018/11/16
 */
@Component
@Crawler(name = "investmentAgency", useCookie = true, useUnrepeated = false )
public class InvestmentAgencyCrawler extends BaseSeimiCrawler {

    private final String baseUrl = "http://www.azichan.com";

    @Autowired
    private InvestmentAssetService assetService;
    @Autowired
    private InvestmentOfficeService investmentofficeService;
    @Autowired
    private InvestmentNewsService investmentNewsService;
    @Autowired
    private InvestmentRecruitmentService investmentRecruitmentService;
    @Autowired
    private InvestmentAgencyService investmentAgencyService;

    @Override
    public String[] startUrls() {
        return new String[]{"http://www.azichan.com/org/list.html?laType=1"};
    }

    @Override
    public void start(Response response) {

        String url = response.getUrl();
        try {
            for (int i = 1; i < 253; i++) {
                push(Request.build(url + "&page=" + i,InvestmentAgencyCrawler::getDetailHtml));
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用分页爬取已成", e.getMessage());
        }
    }

    public void getDetailHtml(Response response) {

        JXDocument doc = response.document();
        try {
            List<JXNode> nodes = doc.selN("//div[@class='orgsList']");
            for (JXNode jxNode : nodes) {
                //处理详情页
                List<JXNode> urlSt = jxNode.sel("/@onclick");
                String detailUrl = urlSt.get(0).toString();
                String url = baseUrl + detailUrl.substring(13, detailUrl.length() - 3);

                //处理头像
                List<JXNode> avatar = jxNode.sel("/div/div/img/@src");
                String avatarSt = "";
                if (avatar != null & avatar.size() > 0) {
                    avatarSt = avatar.get(0).toString();
                }
                //封装数据并传参数
                Map<String, Object> params = response.getMeta();
                params.put("avatar", avatarSt);

                push(Request.build(url, InvestmentAgencyCrawler::saveDetail).setMeta(params));

                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取详情页数据异常{}", e.getMessage());
        }
    }

    public void saveDetail(Response response) {
        String content = response.getContent();
        JXDocument doc = response.document();
        //头部信息
        String name = getStringValue(doc.selOne("//h2[@class='orgsName']/text()"));
        String officialWebsite = getStringValue(doc.selOne("//p[@class='orgsHome']/a/@href"));
        String city = getStringValue(doc.selOne("//p[@class='orgsAds']/span/text()"));


        //基本信息 机构类型
        String agencyType = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[1]/text()"));
        //公司类型
        String companyType = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[2]/text()"));
        //法定代表
        String legalRepresentative = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[3]/text()"));
        //组织编码
        String organizationCode = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[4]/text()"));
        //成立时间
        String establishedAt = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[5]/text()"));
        //发照时间
        String photoAt = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[6]/text()"));
        //注册资本
        String registeredCapital = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[7]/text()"));
        //机构规模
        String officeSize = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[8]/text()"));
        //机构地址
        String agencyAddress = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[9]/text()"));
        //经营范围
        String businessScope = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[10]/div/text()"));

        //公司简介
        String companyDesc = getStringValue(doc.selOne("//div[@class='mainLeft']/div[2]/div[2]/div/p/text()"));
        //公司图片
        String companyImage = getStringValue(doc.selOne("//div[@class='mainLeft']/div[2]/div[2]/img/@src"));
        //联系人
        String contactPeople = getStringValue(doc.selOne("//div[@class='mainRight']/div[2]/div[2]/div[1]/p/text()"));
        //联系电话
        String contactPhone = getStringValue(doc.selOne("//div[@class='mainRight']/div[2]/div[2]/div[2]/p/text()"));

        TInvestmentAgency tInvestmentAgency = new TInvestmentAgency();
        tInvestmentAgency.setAgencyAddress(agencyAddress);
        tInvestmentAgency.setOfficialWebsite(officialWebsite);
        tInvestmentAgency.setAgencyType(agencyType);
        tInvestmentAgency.setBusinessScope(businessScope);
        tInvestmentAgency.setCompanyDesc(companyDesc);
        tInvestmentAgency.setCompanyImage(companyImage);
        tInvestmentAgency.setCity(city);
        tInvestmentAgency.setName(name);
        tInvestmentAgency.setContactPeople(contactPeople);
        tInvestmentAgency.setContactPhone(contactPhone);
        tInvestmentAgency.setEstablishedAt(establishedAt);
        tInvestmentAgency.setPhotoAt(photoAt);
        tInvestmentAgency.setLegalRepresentative(legalRepresentative);
        tInvestmentAgency.setOfficeSize(officeSize);
        tInvestmentAgency.setOrganizationCode(organizationCode);
        tInvestmentAgency.setCompanyType(companyType);
        tInvestmentAgency.setRegisteredCapital(registeredCapital);
        //机构成员后续加上id
        String agencyMembers = getAgencyMembers(doc);
        tInvestmentAgency.setAgencyMembers(agencyMembers);
        //服务内容
        String serviceDeTail = getStringValue(doc.selOne("//div[@class='mainLeft']/div[4]/div[2]/p/allText()"));
        tInvestmentAgency.setServiceDeTail(serviceDeTail);

        Integer investmentId = investmentAgencyService.saveTInvestmentAgency(tInvestmentAgency);
        //处理不良资产
        String assetsTotal = getStringValue(doc.selOne("//div[@class='mainLeft']/div[3]/div[1]/em/text()"));
        assetsTotal = "".equals(assetsTotal) ? "":assetsTotal.substring(2, assetsTotal.length() - 2);
        String assetsPageUrl = getStringValue(doc.selOne("//div[@class='mainLeft']/div[3]/div[1]/a/@href"));
        //调用登陆查看数据
        if(!"0".equals(assetsTotal)) {
            assetService.savaAssetList(baseUrl + assetsPageUrl, assetsTotal, investmentId);
        }
        //发展历程(因为为script的加载后渲染，所以暂时没法处理)

        //分支机构
        dealWinthOffice(doc, investmentId);
        //新闻动态
        dealWithNews(doc, investmentId);
        //招聘信息
        dealWithRecruitment(doc, investmentId);
    }

    /**
     * 处理招聘信息
     * @param doc
     */
    private void dealWithRecruitment(JXDocument doc, Integer investmentId) {
        String pageUrl = getStringValue(doc.selOne("//div[@class='mainLeft']/div[9]/div/a/@href"));
        if("".equals(pageUrl)) {
            List<JXNode> jxNodes = doc.selN("//div[@class='mainLeft']/div[9]/div[2]/table/tbody/tr");
            savePageRecruitment(jxNodes, investmentId);
        }else {
            Map<String, Object> params = new HashMap<>();
            params.put("investmentId", investmentId);
            push(Request.build(baseUrl + pageUrl, InvestmentAgencyCrawler::getRecruitments).setMeta(params));
        }

    }

    public void getRecruitments(Response response) {
        JXDocument doc = response.document();
        Map<String, Object> meta = response.getMeta();
        Integer investmentId = (Integer) meta.get("investmentId");
        List<JXNode> jxNodes = doc.selN("//table[@class='rentInfo']/tbody/tr");
        savePageRecruitment(jxNodes, investmentId);
    }

    private void savePageRecruitment(List<JXNode> jxNodes, Integer investmentId) {
        List<TInvestmentRecruitment> tInvestmentRecruitments = new ArrayList<>();
        if(jxNodes != null && jxNodes.size() > 0) {
            for (JXNode jxNode: jxNodes) {
                String position = jxNode.sel("/td[1]/text()").get(0).toString();
                String salary = jxNode.sel("/td[2]/text()").get(0).toString();
                String publishDate = jxNode.sel("/td[3]/text()").get(0).toString();

                TInvestmentRecruitment tInvestmentRecruitment = new TInvestmentRecruitment();
                tInvestmentRecruitment.setPosition(position);
                tInvestmentRecruitment.setSalary(salary);
                tInvestmentRecruitment.setPublishDate(publishDate);
                tInvestmentRecruitment.setInvestmentId(String.valueOf(investmentId));

                tInvestmentRecruitments.add(tInvestmentRecruitment);
            }

            investmentRecruitmentService.saveBatchRecruitments(tInvestmentRecruitments);
        }
    }

    /**
     * 处理新闻
     * @param doc
     */
    private void dealWithNews(JXDocument doc, Integer investmentId) {
        String pageUrl = getStringValue(doc.selOne("//div[@class='mainLeft']/div[8]/div[1]/a/@href"));
//        String pageTotal = getStringValue(doc.selOne("//div[@class='mainLeft']/div[8]/div[1]/em/text()"));
        if("".equals(pageUrl)) {
            savePageNews(doc, investmentId);
        }else {
            Map<String, Object> params = new HashMap<>();
            params.put("investmentId", investmentId);
            push(Request.build(baseUrl + pageUrl, InvestmentAgencyCrawler::getPageNews).setMeta(params));
        }
    }

    private void savePageNews(JXDocument doc, Integer investmentId) {
        List<JXNode> jxNodes = doc.selN("//div[@class='plr50 v2_a_news newsDetails_list']/ul/li");
        List<TInvestmentNews> tInvestmentNewsList = new ArrayList<>();
        if(jxNodes != null && jxNodes.size() > 0) {
            for (JXNode jxNode :jxNodes) {
                String newsTitle = jxNode.sel("/a/text()").get(0).toString();
                String newsUrl = jxNode.sel("/a/@href").get(0).toString();
                String newsNet = jxNode.sel("/a/small/text()").get(0).toString();
                String newsDate = jxNode.sel("/span/text()").get(0).toString();

                TInvestmentNews  tInvestmentNews = new TInvestmentNews();
                tInvestmentNews.setNewsDate(newsDate);
                tInvestmentNews.setNewsNet(newsNet);
                tInvestmentNews.setNewsTitle(newsTitle);
                tInvestmentNews.setNewsUrl(newsUrl);
                tInvestmentNews.setInvestmentId(String.valueOf(investmentId));

                tInvestmentNewsList.add(tInvestmentNews);
            }
        }
        investmentNewsService.saveBatchNewsList(tInvestmentNewsList);
    }

    public void getPageNews(Response response) {
        JXDocument doc = response.document();
        Map<String, Object> meta = response.getMeta();
        Integer investmentId = (Integer) meta.get("investmentId");
        savePageNews(doc, investmentId);
    }

    /**
     * 处理子机构
     * @param doc
     */
    private void dealWinthOffice(JXDocument doc, Integer investmentId) {
        List<JXNode> agencyNodes = doc.selN("//ul[@class='organizations clearfix']/li");
        String branchOfficeUrl = getStringValue(doc.selNOne("//div[@class='borderBg v2_child_orgs mb20']/div/a/@href"));

        if("".equals(branchOfficeUrl)) {
            getBranchOffices(agencyNodes, investmentId);
        }else{
            String totalBranch = getStringValue(doc.selOne("//div[@class='borderBg v2_child_orgs mb20']/div/em/text()"));
            totalBranch = "".equals(totalBranch) ? "":totalBranch.substring(2, totalBranch.length() - 2);
            int totalPage = Integer.parseInt(totalBranch);

            HashMap<String, Object> params = new HashMap<>();
            params.put("total", totalBranch);
            params.put("investmentId", investmentId);
            for(int i = 1; i < totalPage / 16 + 2; i++) {
                push(Request.build(baseUrl + branchOfficeUrl + "&page=" + i, InvestmentAgencyCrawler::getPageBranchOffice).setMeta(params));
            }
        }
    }

    public void getPageBranchOffice(Response response) {
        Map<String, Object> meta = response.getMeta();
        Integer investmentId  = (Integer) meta.get("investmentId");
        JXDocument doc = response.document();
        List<JXNode> agencyNodes = doc.selN("//ul[@class='organizations clearfix']/li");

        if(agencyNodes != null && agencyNodes.size() > 0) {
            getBranchOffices(agencyNodes, investmentId);
        }
    }

    private void getBranchOffices(List<JXNode> agencyNodes, Integer investmentId) {
        List<TInvestmentOffice> tInvestmentOffices = new ArrayList<>();
        if(agencyNodes != null && agencyNodes.size() > 0) {
            for (JXNode jxNode:agencyNodes) {
                String branchOfficeName = getStringValue(jxNode.sel("/div/div[2]/h3/span/text()").get(0));
                String branchOfficeType = getStringValue(jxNode.sel("/div/div[2]/h6/i[1]/text()").get(0));
                String branchOfficeCity = getStringValue(jxNode.sel("/div/div[2]/h6/i[2]/text()").get(0));
                String branchOfficeBegin = getStringValue(jxNode.sel("/div/div[2]/h6/text()").get(0));

                TInvestmentOffice tInvestmentOffice = new TInvestmentOffice();
                tInvestmentOffice.setBeginAt(branchOfficeBegin);
                tInvestmentOffice.setOfficeCity(branchOfficeCity);
                tInvestmentOffice.setOfficeType(branchOfficeType);
                tInvestmentOffice.setOfficeName(branchOfficeName);
                tInvestmentOffice.setInvestmentId(String.valueOf(investmentId));

                tInvestmentOffices.add(tInvestmentOffice);
            }

            investmentofficeService.saveBatchOffices(tInvestmentOffices);
        }

    }

    private String getAgencyMembers(JXDocument doc) {
        List<JXNode> jxNodes = doc.selN("//div[@class='person_list personDetails_list']/ul/li");
        if(jxNodes != null && jxNodes.size() > 0) {
            List<String> agencyList = new ArrayList<>();
            for (JXNode jxNode: jxNodes) {
                String memberName = getStringValue(jxNode.sel("/div/div/div[2]/h4/text()").get(0));
                String positionName = getStringValue(jxNode.sel("/div/div/div[2]/h4/em/text()").get(0));
                agencyList.add(memberName + "_" + positionName);
            }
            String agencyMembers = StringUtils.join(agencyList, ",");
            return agencyMembers;
        }
       return "";
    }


    private String getStringValue(Object object) {
        if(object == null) {
            return "";
        }else {
            return object.toString();
        }
    }
}
