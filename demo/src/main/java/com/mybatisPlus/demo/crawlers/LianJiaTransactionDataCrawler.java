package com.mybatisPlus.demo.crawlers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mybatisPlus.demo.model.LjTransactionData;
import com.mybatisPlus.demo.service.LjTransactionDataService;
import com.mybatisPlus.demo.util.HttpsUtil;
import com.mybatisPlus.demo.vo.LjTransactionDataVO;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;


@Crawler(name = "lianjiaTransactionData", useUnrepeated = false)
@Component
public class LianJiaTransactionDataCrawler extends BaseSeimiCrawler {

	@Autowired
   private LjTransactionDataService ljTransactionDataService;
	
	@Override
	public String[] startUrls() {
	    return new String[0];
	}

    @Override
    public void start(Response response) { 
    	List<LjTransactionDataVO> ljTransactionDataVOs = ljTransactionDataService.getProblems();
    	
		ExecutorService pool = Executors.newFixedThreadPool(20);

    	if(ljTransactionDataVOs != null && ljTransactionDataVOs.size() > 0) {
    		for (int i = 0, len = ljTransactionDataVOs.size(); i < len; i++) {
        		Map<String, Object> params = new HashMap<String, Object>();
        		LjTransactionDataVO ljTransactionDataVO = ljTransactionDataVOs.get(i);
        		params.put("ljTransactionDataVO", ljTransactionDataVO);
        		
        		String urlCity = "";
        		if(ljTransactionDataVO.getProvince().equals("北京")) {
        			urlCity = "bj";
        		}else if(ljTransactionDataVO.getProvince().equals("上海")) {
        			urlCity = "sh";
        		}else if(ljTransactionDataVO.getProvince().equals("广州")) {
        			urlCity = "gz";
        		} 
        		String url = "http://" + urlCity + ".lianjia.com/chengjiao/" + ljTransactionDataVO.getCode() + ".html";
        		 
        	    
        		Integer flag = i;
        	    Callable<LjTransactionDataVO> run = new Callable<LjTransactionDataVO>(){
        	    	
    				@Override
    				public LjTransactionDataVO call() throws Exception{
    	        		String result = HttpsUtil.get(url, null);
    	        		getDetailData(result, ljTransactionDataVO);
    	        		
    	        	    System.out.println(Thread.currentThread().getName() +  "已经处理了" + flag + "条数据");

    					Thread.sleep(300);
    					return ljTransactionDataVO;
    				}
    			};
    			pool.submit(run);
 
        		
			}
    	}
    	
		pool.shutdown();
    }


    public void getDetailData(String result,  LjTransactionDataVO ljTransactionDataVO) { 
        JXDocument doc = JXDocument.create(result);
        
        JXNode selNOne = doc.selNOne("//div[@class='price']/text()");
       
         
        if(!"暂无价格".equals(selNOne.toString()) && !"暂无数据".equals(ljTransactionDataVO.getConstructionArea())) {
        	String priceSt = doc.selOne("//div[@class='price']/span/i/text()").toString();
        	
        	if(StringUtils.isNotBlank(priceSt)) {
        		String[] split = priceSt.split("-");
        		
        		BigDecimal bigDecimal1 = new BigDecimal(split[0]);
        		BigDecimal bigDecimal2 = new BigDecimal(split[1]);
        		BigDecimal bigDecimal3 = new BigDecimal(2);
        		BigDecimal bigDecimal4 = new BigDecimal(ljTransactionDataVO.getConstructionArea().replace("㎡", ""));
        		BigDecimal bigDecimal5 = new BigDecimal(10000);


        		
        		BigDecimal currentPrice = bigDecimal1.add(bigDecimal2).divide(bigDecimal3, 0, BigDecimal.ROUND_HALF_UP);
        		
        		BigDecimal unitPrice = currentPrice.multiply(bigDecimal5).divide(bigDecimal4, 0, BigDecimal.ROUND_HALF_UP);
        		
            	String listPrice = doc.selOne("//div[@class='msg']/span[1]/label/text()").toString();
            	
            	LjTransactionData ljTransactionData = new LjTransactionData();
				BeanUtils.copyProperties(ljTransactionDataVO, ljTransactionData);
				
				ljTransactionData.setListingPrice(listPrice);
				ljTransactionData.setUnitPrice(unitPrice.toString());
				ljTransactionData.setCurrentPrice(currentPrice.toString());
				
				ljTransactionDataService.insertOrUpdate(ljTransactionData);
         	}
        	
        }
    }

}
