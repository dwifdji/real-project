package com.winback.test;

import com.gexin.fastjson.JSON;
import com.winback.arch.common.ComEmailSendReq;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.ComEmailUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.core.facade.finance.FinanceFacade;
import com.winback.core.facade.finance.req.FinanceReq;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class FinanceTest extends TestBase{

    @Resource
    private FinanceFacade financeFacade;

    /**
     * 出款记录
     *
     */
    @Test
    public void getExpendList() {
        FinanceReq.expendListReq req = new FinanceReq.expendListReq();

         System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.getExpendList(req)));
    }




    /**
     * 回款记录列表
     *
     */
    @Test
    public void getReceivableList() {
        FinanceReq.receivableListReq req  = new FinanceReq.receivableListReq();

         System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.getReceivableList(req)));
    }




    /**
     * 发票记录列表
     *
     */
    @Test
    public void getInvoiceList() {
        FinanceReq.invoiceListReq req   = new FinanceReq.invoiceListReq();

         System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.getInvoiceList(req)));
    }






    /**
     * 保存出款记录
     *
     */
    @Test
    public void saveExpend() {
        FinanceReq.saveExpendReq req   = new FinanceReq.saveExpendReq();
        req.setAcctBankName("1200120120121");
        req.setAcctName("吴传奇");
        req.setAcctNo("101012012");
        req.setAmount("1122");
        req.setCaseId("1");
        req.setMsg("描述信息");
         System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.saveExpend(req)));
    }



    /**
     * 保存回款记录
     *
     */
    @Test
    public void saveReceivable() {
        FinanceReq.saveReceivableReq req    = new FinanceReq.saveReceivableReq();
        req.setCaseId("122");
        req.setType("1");
        req.setAmount("1122");
        req.setMsg("描述信息");
        req.setCaseAmount("1");
        req.setCost("111");
         System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.saveReceivable(req)));
    }




    /**
     * 保存发票记录
     *
     */
    @Test
    public void saveInvoice() {
        FinanceReq.saveInvoiceReq  req    = new FinanceReq.saveInvoiceReq ();
        req.setCaseId("122");
        req.setType("1");
        req.setAmount("1122");
        req.setComName("公司");
        req.setDutyNo("121111111111");
        req.setPhone("110110");
        System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.saveInvoice(req)));
    }





    /**
     * 审核接口
     *
     */
    @Test
    public void audit() {
        FinanceReq.auditReq req = new FinanceReq.auditReq();
        req.setStatus("1");
        req.setType("1");
        req.setIds(new String[]{"1"});

        System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.audit(req)));
    }




    /**
     * 获取审核的列表信息
     *
     */
    @Test
    public void getExpendAuditList() {
        FinanceReq.commonReq req = new FinanceReq.commonReq();
        req.setCaseId("1");

        System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.getExpendAuditList(req)));
    }



    /**
     * 获取放款审核列表
     *
     */
    @Test
    public void getReceivableAuditList() {
        FinanceReq.commonReq req = new FinanceReq.commonReq();
        req.setCaseId("1");
        req.setPage(1);
        req.setPerPage(5);
        System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.getReceivableAuditList(req)));
    }



    /**
     * 发票审核类表
     *
     */
    @Test
    public void getInvoiceAuditList() {
        FinanceReq.commonReq req = new FinanceReq.commonReq();
        req.setCaseId("122");

        System.out.println("返回参数为....."+JSON.toJSONString(financeFacade.getInvoiceAuditList(req)));
    }





    /**
     * 发票审核类表
     *
     */
    @Test
    public void email() {
        /*ComEmailSendReq comEmailSendReq = new ComEmailSendReq();

        List<String> email =  new ArrayList<>();
        email.add("wuchaunqi@360pai.com");

        comEmailSendReq.setBusType(EmailEnum.BUS_TYPE.BUSINESS);
        comEmailSendReq.setTitle("赢回来邮件测试");
        comEmailSendReq.setContent("内容");
        comEmailSendReq.setEmailList(email);

        ComEmailUtil.sendEmail(comEmailSendReq);*/

        ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.MINOR,EmailEnum.MODULE_TYPE.PAY,"请求的参数","支付参数校验不成功！","异常的内容",null);

     }

}




