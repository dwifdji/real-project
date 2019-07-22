package com._360pai;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


public class FddTest extends BaseJunit {



    @Autowired
    private FddSignatureFacade fddSignatureFacade;


    @Autowired
    private GatewayProperties gatewayProperties;


    @Test
    public void generateServiceDueDiligenceTest() {

        GenerateContractComReq req= new GenerateContractComReq();

        req.setType(FddEnums.SING_TYPE.BANK_OFFLINE_DELEGATION.getType());
        req.setPartyId("11211122");
        req.setFddId("1212a112");
        req.setActivityId("3121");

        GenerateOfflineDelegationReq greq = new GenerateOfflineDelegationReq();

        greq.setActivityCode("12");


        GenerateContractResp resp = fddSignatureFacade.generateContract(req,greq);





        System.out.println(JSON.toJSONString(resp));
    }


    /**
     *
     * 法大大开通用户接口
     * @param
     * @param
     */
    @Test
    public void querySignQuartz() {


        fddSignatureFacade.querySignQuartz();

    }








    /**
     *
     * 法大大开通用户接口
     * @param
     * @param
     */
    @Test
    public void openMember() {

        FOpenMemberReq req = new FOpenMemberReq();

        req.setCustomer_name("个人1");
        req.setEmail("1376717124@qq.com");
        req.setId_card("110101199003077336");
        req.setMobile("15651617585");
        req.setParty_id("112");
        req.setCustomer_type("1");
        FOpenMemberResp resp = fddSignatureFacade.openMember(req);

        System.out.println(JSON.toJSONString(resp));
    }



    /**
     *
      * 法大大修改接口
     * @param
     * @param
     */
    @Test
    public void updateMemInfo() {

        UpdateMemInfoReq req = new UpdateMemInfoReq();

         req.setEmail("1772112287@qq.com");
         req.setMobile("15651617585");
        req.setCustomer_id("031556AB7DFB64A3A2603BB2795AE378");

        FCommResp resp = fddSignatureFacade.updateMemInfo(req);

        System.out.println(JSON.toJSONString(resp));
    }





    /**
     *
     * 上传合同模板接口
     * @param
     * @param
     */
    @Test
    public void uploadTemplate() {

        FUploadTemplateReq req = new FUploadTemplateReq();

        req.setTemplate_id("1asb122a11210d18121w122c113");
        req.setFile(new File("C:\\Users\\15651\\Desktop\\生产文件\\生产模板\\成交确认书（租赁权拍卖）0528.pdf"));

        //req.setDoc_url("https://cdn-files.360pai.com/authorization_aggreement_bank_v1.pdf");


        FCommResp resp = fddSignatureFacade.uploadTemplate(req);

        System.out.println(JSON.toJSONString(resp));
    }




    /**
     *
     * 直接生成合同
     * @param
     * @param
     */
    @Test
    public void invokeGenerateContract() {


        FddSignInfo signInfo =  JSON.parseObject("{\"fdd_id\":\"D210ACA2145521FDA70519731F018F45\",\"is_auto\":\"2\",\"mem_role\":\"1\",\"party_id\":\"111\",\"sign_role\":\"1\"}",FddSignInfo.class);



        /*JSONObject map = new JSONObject();
        map.put("use_name","测试的名称");
        map.put("name2","上海百昌网络有限公司");


        String responseStr = FddSignatureUtils.invokeGenerateContract(gatewayProperties,"111111111111","2222","模板制作",map.toString(),null);
*/

        System.out.println(JSON.toJSONString(signInfo));
    }



    //生成租赁权 成交确认书


    @Test
    public void generateLeaseTest() {

        GenerateContractComReq req= new GenerateContractComReq();

        req.setType(FddEnums.SING_TYPE.LEASE_DEAL.getType());
        req.setPartyId("11211122");
        req.setFddId("122");
        req.setActivityId("3121");

        LeaseGenerateDealReq greq = new LeaseGenerateDealReq();

        greq.setActivityCode("ActivityCode");
        greq.setAuctionFirm("AuctionFirm");
        greq.setAuctionPeriod("AuctionPeriod");
        greq.setBuyer("Buyer");
        ///greq.setCommission("Commission");
        greq.setSeller("Seller");
        greq.setLotName("LotName");
        greq.setHammerPrice("HammerPrice");
        greq.setLotCode("LotCode");
        greq.setDeposit("Deposit");
        greq.setDealAmount("DealAmount");
        greq.setTotalAmount("TotalAmount");
        greq.setTotalAmountChn("TotalAmountChn");
        GenerateContractResp resp = fddSignatureFacade.generateContract(req,greq);





        System.out.println(JSON.toJSONString(resp));
    }








    /**
     *
     * 自动签章
     * @param
     * @param
     */
    @Test
    public void invokeExtSignAuto() {




        System.out.println(DateUtil.formatStrDate("2018-02-10",Calendar.YEAR));
    }


    @Test
    public void newgenerateContract() {

        GenerateContractComReq req= new GenerateContractComReq();

        req.setType(FddEnums.SING_TYPE.SERVICE_ADVISORY.getType());
        req.setPartyId("12");
        req.setFddId("121212");
        req.setActivityId("312121");

        GenerateServiceAdvisoryReq greq = new GenerateServiceAdvisoryReq();
        greq.setAmount("100");
        greq.setAmountUpper("一百大洋");
        greq.setUser("测试账户");
        greq.setUserAddress("测试的地址");
        greq.setUserPhone("测试的电话");

        GenerateContractResp resp = fddSignatureFacade.generateContract(req,greq);

        System.out.println(JSON.toJSONString(resp));
    }




    /**
     *
     * 签章参数测试
     * @param
     * @param
     */
    //{"code":"1000","download_url":"https:\/\/testapi.fadada.com:8443\/api\/\/getdocs.action?app_id=400929&timestamp=20180813185250&v=2.0&msg_digest=QTM3NUM2MjE4MDExN0I0Q0ZEQTE3MzZFNTkxN0Y3QzFEQTEwMzI1Ng==&send_app_id=null&transaction_id=transaction_id_001","msg":"文档签署成功","result":"success","viewpdf_url":"https:\/\/testapi.fadada.com:8443\/api\/\/viewdocs.action?app_id=400929&timestamp=20180813185250&v=2.0&msg_digest=QTM3NUM2MjE4MDExN0I0Q0ZEQTE3MzZFNTkxN0Y3QzFEQTEwMzI1Ng==&send_app_id=null&transaction_id=transaction_id_001"}
    @Test
    public void extSignAuto() {

        ExtSignContractReq req = new ExtSignContractReq();

        req.setActivity_id("11111");
        req.setContract_id("num201812171330477569");
        req.setType(FddEnums.SING_TYPE.SERVICE_ADVISORY.getType());
        List<FddSignInfo> list = new ArrayList<>();

        FddSignInfo info1 = new FddSignInfo();
        info1.setIs_auto(FddEnums.SING_AUTO.NOT_AUTO.getType());
        info1.setFdd_id("D210ACA2145521FDA70519731F018F45");
        info1.setParty_id("111");
        info1.setMem_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
        info1.setSign_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
        list.add(info1);



        req.setSign_list(list);

        ExtSignContractResp resp = fddSignatureFacade.extSignContract(req);

        System.out.println(JSON.toJSONString(resp));
    }


    /**
     *
     * 签章异步回调处理
     * @param
     * @param
     */
     @Test
    public void extSignNotify() {

         FddCallBackReq req = new FddCallBackReq();

         req.setContract_id("num201809161445442645");
         req.setResult_code("3000");
         req.setTransaction_id("seller201809161446532591");
         req.setDownload_url("https://testapi.fadada.com:8443/api//downLoadContract.action?app_id=400929&v=2.0&timestamp=20180907132329&contract_id=f441ed5b9a8a4bc6b4f8b3dd8c78d153&msg_digest=NUVBNDI2MzNFNDNDMDA4QUU3RTZFODQwMUY0RDVFMzZGMkEwRjMwRg==");
         req.setResult_desc("签章成功");
         req.setViewpdf_url("https://testapi.fadada.com:8443/api//viewContract.action?app_id=400929&v=2.0&timestamp=20180907132329&contract_id=f441ed5b9a8a4bc6b4f8b3dd8c78d153&msg_digest=NUVBNDI2MzNFNDNDMDA4QUU3RTZFODQwMUY0RDVFMzZGMkEwRjMwRg==");
         GenerateContractResp resp = fddSignatureFacade.extSignNotify(req);

        System.out.println(JSON.toJSONString(resp));
    }



    /**
     *
     * 签章异步回调处理
     * @param
     * @param
     */
    @Test
    public void formatStrDate() {


        System.out.println(DateUtil.getTodayString());


        System.out.println(DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
     }



    @Test
    public void listRemove() {


        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("44");
        list.add("55");



        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String x = it.next();
            if(x.equals("55")){
                it.remove();
            }
            if(x.equals("22")){
                it.remove();
            }
        }


        System.out.print(JSON.toJSONString(list));


     }

    @Test
    public void split() {


        System.out.println(DateUtil.nextMinute(24*60));






    }





}
