package com.mybatisPlus.demo.controller;


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybatisPlus.demo.model.LjTransactionData;
import com.mybatisPlus.demo.service.LjTransactionDataService;
import com.mybatisPlus.demo.vo.LjTransactionDataVO;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaolei
 * @since 2019-06-12
 */
@RestController
@RequestMapping("/ljTransactionData")
public class LjTransactionDataController {
	
	@Autowired
	private LjTransactionDataService ljTransactionDataService;
	
	@GetMapping("/getLjTransactionData")
	public String getLjTransactionData() {
		List<LjTransactionDataVO>  ljTransactionDataVOs = 
				ljTransactionDataService.getLjTransactionDataVOS();
		
//		int pageSize = 100;
//		
//		for(int j = 1; j < 8602; j++) {
//			 List<LjTransactionData> ljTransactionDatas = 
//					 ljTransactionDataService.getLjTransactionData(100*(j - 1), pageSize, ljTransactionDataVOs);
//			
//			if(ljTransactionDatas != null && ljTransactionDatas.size() > 0) {
//				ljTransactionDataService.insertOrUpdateBatch(ljTransactionDatas);
//			}
//		}
		
		
		int pageSize = 100;
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for(int j = 1; j < 2; j++) {
		 
			Integer flag = j;
			Callable<String> run = new Callable<String>(){
    	    	
				@Override
				public String call() throws Exception{
					List<LjTransactionData> ljTransactionDatas = 
							 ljTransactionDataService.getLjTransactionData(100*(flag - 1), pageSize, ljTransactionDataVOs);
					
	        	    System.out.println(Thread.currentThread().getName() +  "已经处理了" + flag + "数据");
					if(ljTransactionDatas != null && ljTransactionDatas.size() > 0) {
						ljTransactionDataService.insertOrUpdateBatch(ljTransactionDatas);
					}
					
					Thread.sleep(300);
					return "success";
				}
			};
			pool.submit(run);
			
		}
		
		pool.shutdown();
		
		return "success";
	}
	
	
    /**
     * 链家二手房交易市场
     * @return
     */
    @RequestMapping(value = "getLianJiaSellData")
    public String getLianJiaSellData(){
        Request request = new Request();
       
        request.setCrawlerName("lianjiaTransactionData");
        request.setUrl("https://sh.lianjia.com/ershoufang/");
        request.setCallBack("start");
        CrawlerCache.consumeRequest(request);
        return "successful";
    }
}

