package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.TServiceElite;
import com._360pai.seimi.service.ServiceEliteService;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by liuaholei on 2018/11/14
 */
@Component
@Crawler(name = "serviceElite", useUnrepeated = false)
public class ServiceEliteCrawler extends BaseSeimiCrawler {
	
	@Autowired
	private ServiceEliteService serviceEliteService;
	
	private final String baseUrl = "http://www.azichan.com";

	
    @Override
    public String[] startUrls() {
        return new String[]{"http://www.azichan.com/lawyer/list.html?laType=2"};
    }

    @Override
    public void start(Response response) {
        String url = response.getUrl();
        
        try {
        	for (int i = 1; i < 471; i++) {
        	logger.info("已经开始爬取第{}", i + " 页");
			push(Request.build(url + "&page=" + i,ServiceEliteCrawler::getDetailHtml));
			Thread.sleep(1000);
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
			logger.error("调用分页数据异常{}", e.getMessage());
		}
    }
    
    
    public void getDetailHtml(Response response){
        JXDocument doc = response.document();
       
        try {
        	List<JXNode> nodes = doc.selN("//div[@class='orgsList']");
        	
        	for (JXNode jxNode : nodes) {
        		
        		//服务类型
        		List<JXNode> spanList = jxNode.sel("/div/span/text()");
        		
        		//服务类型
               	String serverType = "";
               	if(spanList != null && spanList.size() > 0) {
               		serverType =  StringUtils.join(spanList, ",");
               	}
               	 
        		List<JXNode> urlSt = jxNode.sel("/@onclick");
        		String detailUrl = urlSt.get(0).toString();
        		String realUrl = detailUrl.substring(13, detailUrl.length()-2);
        		String url = baseUrl + realUrl;
        		
        		List<JXNode> avatar = jxNode.sel("/div/div/img/@src");
        		String avatarSt = "";
        		if(avatar != null & avatar.size() > 0) {
        			avatarSt = avatar.get(0).toString();
        		}
        		 
        		Map<String, Object> params = new HashMap<String, Object>();
        		params.put("serverType", serverType);
        		params.put("avatar", avatarSt);
        		push(Request.build(url,ServiceEliteCrawler::saveDetail).setMeta(params));
        		
        		Thread.sleep(2000);
			}
        	 
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理分页详情数据异常{}", e.getMessage());
        }
    }
    
    /**
     * 获取详情数据并进行封装
     * @param response
     */
    public void saveDetail(Response response) {
    	 JXDocument doc = response.document();
    	 
    	 TServiceElite tServiceElite = new TServiceElite();
    	 Map<String, Object> meta = response.getMeta();
    	 
    	 //服务类型
    	 String serverType = (String) meta.get("serverType");
    	 //头像路径
    	 String avatar = (String) meta.get("avatar");
    	 
    	 //名称
    	 String name = getStringValue(doc.selOne("//h2[@class='orgsName mt20']/text()"));
    	 //律所
    	 String lawFirm = getStringValue(doc.selOne("//p[@class='small']/text()"));
    	 if("".equals(lawFirm)) {
    		 lawFirm = getStringValue(doc.selOne("//p[@class='small']/a/text()"));
    	 }
    	 //电话
    	 String phone = getStringValue(doc.selOne("//div[@class='contacts']/div[1]/p/text()"));
    	 //email
    	 String email = getStringValue(doc.selOne("//div[@class='contacts']/div[2]/p/text()"));
    	 //擅长服务
    	 String goodServer = getStringValue(doc.selOne("//div[@class='wraper clearfix relative']/div/div[2]/div/b/text()"));
    	 
    	 //职位
    	 String position = getStringValue(doc.selOne("//p[@class='small']/i/text()"));
    	 
    	 //城市
    	 String city = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[1]/text()"));
    	 //职业
    	 String career = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[2]/text()"));
    	 //从业年限
    	 String workYears = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[3]/text()"));
    	 //学历
    	 String education = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[4]/text()"));
    	 //执业证号
    	 String licenseNumber = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[5]/text()"));
    	 //社会职务
    	 String socialDuties = getStringValue(doc.selOne("//div[@class='plr50 basicInfos clearfix']/ul/li[6]/text()"));
    	 
    	 //职位描述
    	 String personalDesc= getStringValue(doc.selOne("//div[@class='contion']/p/allText()"));
    	 //个人图片
    	 String persionImage = getStringValue(doc.selOne("//div[@class='plr50 copyProfile']/img/@src"));
    	 //个人详情
    	 String personalDetail= getStringValue(doc.selOne("//div[@class='plr50 copyProfile']/div/p/allText()"));
    	 
    	 tServiceElite.setName(name);
    	 tServiceElite.setCity(city);
    	 tServiceElite.setLawFirm(lawFirm);
    	 tServiceElite.setEducation(education);
    	 tServiceElite.setLicenseNumber(licenseNumber);
    	 tServiceElite.setPersonalDesc(personalDesc);
    	 tServiceElite.setCareer(career);
    	 tServiceElite.setPersonalDetail(personalDetail);
    	 tServiceElite.setWorkYears(workYears);
    	 tServiceElite.setPosition(position);
    	 tServiceElite.setSocialDuties(socialDuties);
    	 tServiceElite.setPersionImage(persionImage);
    	 tServiceElite.setAvatar(avatar);
    	 tServiceElite.setPhone(phone);
    	 tServiceElite.setEmail(email);
    	 tServiceElite.setGoodServer(goodServer);
    	 tServiceElite.setServerType(serverType);
    	 
    	 Integer count = serviceEliteService.saveEntity(tServiceElite);
    	 logger.info("开始插入数据{}", tServiceElite);
    	 
    	 try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
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
