package com.winback.test;

import com.gexin.fastjson.JSON;
import com.winback.core.facade.finance.FinanceFacade;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.gateway.facade.QiChaChaFacade;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class QiChaChaTest extends TestBase{

    @Resource
    private QiChaChaFacade qiChaChaFacade;

    /**
     * 出款记录
     *
     */
    @Test
    public void getExpendList() {

        String keyWord = "小米信息";


        System.out.println("返回参数为....."+JSON.toJSONString(qiChaChaFacade.getRiskComInfo(keyWord)));
    }






    /**
     * 出款记录
     *
     */
    @Test
    public void searchInvestment() {

        String keyWord = "阿里巴巴(中国)有限公司";


        System.out.println("返回参数为....."+JSON.toJSONString(qiChaChaFacade.searchInvestment(keyWord,"1","2")));
    }



}




