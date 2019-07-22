package com.winback.test;


import com.gexin.fastjson.JSON;
import com.winback.core.facade.connect.req.ConnectReq;
import com.winback.core.facade.risk.RiskFacade;
import com.winback.core.facade.risk.req.RiskReq;
import org.junit.Test;


import javax.annotation.Resource;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class RiskTest extends TestBase{

    @Resource
    private RiskFacade riskFacade;




    /**
     * 获取公司信息
     *
     */
    @Test
    public void getComInfo() {
        RiskReq.keyWordReq req = new RiskReq.keyWordReq();
        req.setKeyWord("上海百昌网络拍卖科技有限公司");

        System.out.println("返回参数为....."+JSON.toJSONString(riskFacade.getComInfo(req)));
    }




    /**
     * 获取公司信息
     *
     */
    @Test
    public void getInvestList() {
        RiskReq.keyWordReq req = new RiskReq.keyWordReq();
        req.setKeyWord("阿里巴巴(中国)有限公司");

        System.out.println("返回参数为....."+JSON.toJSONString(riskFacade.getInvestList(req)));
    }


}




