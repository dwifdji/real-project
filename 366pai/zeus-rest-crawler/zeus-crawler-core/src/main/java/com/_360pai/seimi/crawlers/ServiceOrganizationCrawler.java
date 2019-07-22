package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.TAgencyNews;
import com._360pai.seimi.model.TAgencyRecruitment;
import com._360pai.seimi.model.TServiceOrganization;
import com._360pai.seimi.service.AgencyNewsService;
import com._360pai.seimi.service.AgencyRecruitmentService;
import com._360pai.seimi.service.ServiceOrganizationService;
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
 *  create by liuhaolei on 2018/11/13
 */
@Component
@Crawler(name = "serviceOrganization", useUnrepeated = false)
public class ServiceOrganizationCrawler extends BaseSeimiCrawler {
	
	private final String baseUrl = "http://www.azichan.com";

	@Autowired
	private ServiceOrganizationService serviceOrganizationService;
	@Autowired
	private AgencyNewsService agencyNewsService;
	@Autowired
	private AgencyRecruitmentService AgencyRecruitmentService;


    @Override
    public String[] startUrls() {
        return new String[]{"http://www.azichan.com/org/list.html?laType=2"};
    }

    @Override
    public void start(Response response) {
        String url = response.getUrl();

        try {
        	for (int i = 1; i < 172; i++) {
	        	logger.info("已经开始爬取第{}", i + " 页");
				push(Request.build(url + "&page=" + i, ServiceOrganizationCrawler::getDetailHtml));
				Thread.sleep(1000);
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void getDetailHtml(Response response) {

		JXDocument doc = response.document();
		try {
			List<JXNode> nodes = doc.selN("//div[@class='orgsList']");
			for (JXNode jxNode : nodes) {
				//处理类型
				List<JXNode> spanList = jxNode.sel("/div/span/text()");
				String serverType = "";
				if (spanList != null && spanList.size() > 0) {
					serverType = StringUtils.join(spanList, ",");
				}

				//处理详情页
				List<JXNode> urlSt = jxNode.sel("/@onclick");
				String detailUrl = urlSt.get(0).toString();
				String url = baseUrl + detailUrl.substring(13, detailUrl.length()-3);

				//处理头像
				List<JXNode> avatar = jxNode.sel("/div/div/img/@src");
				String avatarSt = "";
				if(avatar != null & avatar.size() > 0) {
					avatarSt = avatar.get(0).toString();
				}
				//封装数据并传参数
				Map<String, Object> params = response.getMeta();
				params.put("serverType", serverType);
				params.put("avatar", avatarSt);

				push(Request.build(url,ServiceOrganizationCrawler::saveDetail).setMeta(params));

				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取分页数据异常{}", e.getMessage());
		}

	}

	/**
     * 获取详情数据并进行封装
     * @param response
     */
    public void saveDetail(Response response) {
		JXDocument doc = response.document();

		Map<String, Object> meta = response.getMeta();
		//头像
		String avatar = (String) meta.get("avatar");
		//服务类型
		String serverType  = (String) meta.get("serverType");

		//名称
		String name = getStringValue(doc.selOne("//h2[@class='orgsName']/text()"));
		//机构主站地址
		String agencyUrl = getStringValue(doc.selOne("//p[@class='orgsHome']/a/@href"));
		//城市
		String city = getStringValue(doc.selOne("//p[@class='orgsAds']/span/text()"));
		//成立日期
		String establishedDate = getStringValue(doc.selOne("//p[@class='orgsAds']/text()"));

		//基本信息
		String agencyType = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[1]/text()"));
		//组织代码
		String organizationCode = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[2]/text()"));
		//规模
		String scale = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[4]/text()"));
		//详细地址
		String address = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[5]/text()"));
		//联系电话
		String phone = getStringValue(doc.selOne("//div[@class='contacts']/div[2]/p/text()"));
		//联系邮箱
		String email = getStringValue(doc.selOne("//div[@class='contacts']/div[3]/p/text()"));
		//简介图片
		String descImage = getStringValue(doc.selOne("//div[@class='plr50 copyProfile']/img/@src"));
		//公司简介text
		String desc = getStringValue(doc.selOne("//div[@class='plr50 copyProfile']/div/p/text()"));
		//业务范围text
		JXNode selNOne = doc.selNOne("//div[@class='contion']");
		String businessScope = getStringValue(selNOne);

		//机构成员
		List<JXNode> JXNodes = doc.selN("//div[@class='person_list personDetails_list']/ul/li");

		String members = "";
		if(JXNodes != null) {
			List<String> memberList = new ArrayList<String>();
			for (JXNode jxNode : JXNodes) {
				List<JXNode> memberNode = jxNode.sel("/div/div/div/h4/text()");
				memberList.add(memberNode.get(0).toString());
			}

			members = StringUtils.join(memberList, ",");
		}

		//开始新增数据
		TServiceOrganization tServiceOrganization = new TServiceOrganization();
		tServiceOrganization.setAddress(address);
		tServiceOrganization.setAgencyType(agencyType);
		tServiceOrganization.setAgencyUrl(agencyUrl);
		tServiceOrganization.setAvatar(avatar);
		tServiceOrganization.setBusinessScope(businessScope);
		tServiceOrganization.setCity(city);
		tServiceOrganization.setCompanyDesc(desc);
		tServiceOrganization.setDescImage(descImage);
		tServiceOrganization.setEmail(email);
		tServiceOrganization.setName(name);
		tServiceOrganization.setPhone(phone);
		tServiceOrganization.setScale(scale);
		tServiceOrganization.setOrganizationCode(organizationCode);
		tServiceOrganization.setEstablishedDate(establishedDate);
		tServiceOrganization.setServerType(serverType);
		tServiceOrganization.setMembers(members);

		Integer agencyId = serviceOrganizationService.saveEntity(tServiceOrganization);

		//招聘信息
		List<JXNode> jsobOffers = doc.selN("//table[@class='rentInfo']/tbody/tr");
		saveAgencyRecruitmentList(jsobOffers, agencyId);

		//获取新闻动态路径
		String newsDetailUrl = getStringValue(doc.selOne("//div[@class='mainLeft']/div[@class='borderBg mb20'][6]/div/a/@href"));

		if(!"".equals(newsDetailUrl)) {
			Map<String, Object> params = new HashMap<>();
			params.put("agencyId", agencyId);
			newsDetailUrl = baseUrl + newsDetailUrl;
			push(Request.build(newsDetailUrl,ServiceOrganizationCrawler::saveNews).setMeta(params));
		}else {
			List<JXNode> newsNodes = doc.selN("//ul[@class='clearfix']/li");
			saveAgencyNewsList(newsNodes, agencyId);
		}


	}

	public void saveNews(Response response) {
		JXDocument doc = response.document();
		Map<String, Object> meta = response.getMeta();
		Integer agencyId = (Integer) meta.get("agencyId");
		List<JXNode> newsNodes = doc.selN("//ul[@class='clearfix']/li");
		saveAgencyNewsList(newsNodes, agencyId);
	}

	private void saveAgencyRecruitmentList(List<JXNode> jsobOffers, Integer agencyId) {
		List<TAgencyRecruitment> tAgencyRecruitments = new ArrayList<>();
		if(jsobOffers != null && jsobOffers.size() > 0) {
			for (JXNode jxNode : jsobOffers) {
				//职位
				List<JXNode> positionNode = jxNode.sel("/td[1]/text()");
				String position = positionNode.get(0).toString();

				List<JXNode> salaryNode = jxNode.sel("/td[2]/text()");
				String salary = salaryNode.get(0).toString();

				List<JXNode> releaseDateNode = jxNode.sel("/td[3]/text()");
				String releaseDate = releaseDateNode.get(0).toString();

				TAgencyRecruitment tAgencyRecruitment = new TAgencyRecruitment();
				tAgencyRecruitment.setPosition(position);
				tAgencyRecruitment.setReleaseDate(releaseDate);
				tAgencyRecruitment.setSalary(salary);
				String s = agencyId == null ? "" : String.valueOf(agencyId);
				tAgencyRecruitment.setAgencyId(s);

				tAgencyRecruitments.add(tAgencyRecruitment);
				//创建新的实体进行数据插入
			}
			Integer newCount = AgencyRecruitmentService.savaBatchList(tAgencyRecruitments);
		}
	}

	private void saveAgencyNewsList(List<JXNode> newsNodes, Integer agencyId) {
		ArrayList<TAgencyNews> tAgencyNewsList = new ArrayList<>();
    	if(newsNodes != null && newsNodes.size() > 0) {
			for (JXNode jxNode : newsNodes) {

				//职位
				List<JXNode> newsUrlNode = jxNode.sel("/a/@href");
				String newsUrl = newsUrlNode.get(0).toString();

				List<JXNode> releaseDateNode = jxNode.sel("/span/text()");
				String releaseDate = releaseDateNode.get(0).toString();

				List<JXNode> newsTitleNode = jxNode.sel("/a/text()");
				String newsTitle = newsTitleNode.get(0).toString();

				List<JXNode> newsNetNode = jxNode.sel("/a/small/text()");
				String newsNet = newsNetNode.get(0).toString();

				TAgencyNews tAgencyNews = new TAgencyNews();
				tAgencyNews.setNewsNet(newsNet);
				tAgencyNews.setNewsTitle(newsTitle);
				tAgencyNews.setNewsUrl(newsUrl);
				tAgencyNews.setReleaseDate(releaseDate);
				String s = agencyId == null ? "" : String.valueOf(agencyId);
				tAgencyNews.setAgencyId(s);

				tAgencyNewsList.add(tAgencyNews);
			}
			Integer count = agencyNewsService.saveBatchNews(tAgencyNewsList);
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
