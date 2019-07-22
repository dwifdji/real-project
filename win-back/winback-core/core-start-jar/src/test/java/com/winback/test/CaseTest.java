package com.winback.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.HttpUtils;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.dao._case.TCaseDao;
import com.winback.core.dto._case.QNFileInfoDto;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade._case.req.*;
import com.winback.core.facade._case.resp.CaseAdminVo;
import com.winback.core.facade._case.resp.CasePublishVo;
import com.winback.core.facade._case.resp.CaseVo;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.model._case.TCase;
import com.winback.core.service._case.CaseService;
import com.winback.core.service.account.AccountService;
import com.winback.core.service.account.FranchiseeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author RuQ
 * @Title: CaseTest
 * @ProjectName winback
 * @Description:
 * @date 2019/2/21 11:04
 */
public class CaseTest extends  TestBase {
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

    @Autowired
    private CaseService caseService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FranchiseeService franchiseeService;

    @Autowired
    private TCaseDao caseDao;


    /**
     * app发布案件
     */
    @Test
    public void testPublishCase(){
        CaseCommReq req = new CaseCommReq();
        req.setAccountId(3);
        req.setDefendant("汝晴555");
        req.setPlaintiff("吕布555");
        req.setCaseAmount("555");
        req.setCaseDesc("如果没有你，没有过去，我不会有伤心");
        req.setCaseTypeId(1);
        req.setMainSource(CaseEnum.MainSource.ONLINE.getKey());
        req.setSubSource(CaseEnum.SubSource.APP.getKey());
        ResponseModel responseModel = caseFacade.publishCase(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testUserPublistCaseList(){
        AdminCaseReq.QueryReq req = new AdminCaseReq.QueryReq();
        req.setAccountId(74);
        PageInfoResp<Case> pageInfoResp = caseService.getListByAccountId(req);
        System.out.println(JSON.toJSONString(pageInfoResp));
    }


    @Test
    public void testJMSPublistCaseList(){
        AdminAccountReq.FranchiseeQueryReq  req = new AdminAccountReq.FranchiseeQueryReq();
        req.setFranchiseeId(19);
        PageInfoResp<Case> pageInfoResp = franchiseeService.getInviteCaseListByPage(req);
        System.out.println(JSON.toJSONString(pageInfoResp));
    }

    /**
     * app案件库列表
     */
    @Test
    public void testGetCaseList(){
        CaseCommReq req = new CaseCommReq();
//        CaseCommReq req = null;
        req.setCooperateWay("2");
        ResponseModel responseModel = caseFacade.getCaseList(req);
        System.out.println(JSON.toJSONString(responseModel));
    }



    /**
     * 后台预检上传资料
     */
    @Test
    public void testUploadFile(){
        AttachmentReq req = new AttachmentReq();
        req.setCaseId("201902211126234989");
        req.setAttachName("授权文书测试1");
        req.setAttachUrl("https://img2.woyaogexing.com/2019/02/12/2a554e0234704a65b0d82db1ea08949d!400x400.jpeg");
        req.setCaseStatus(CaseEnum.CaseAttachmentMainStatus.SIGN_CONTRACT.getKey());
        req.setAttachType(CaseEnum.CaseAttachmentSubStatus.EMPOWER_DOC.getKey());
        ResponseModel responseModel = caseFacade.uploadFile(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    /**
     * 后台预检审核通过
     */
    @Test
    public void testPreCheckSuccess(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseId("201902211126234989");
        req.setOperatorId(1);
        req.setMainStatus(CaseEnum.MainStatus.PRE_CHECK_SUCCESS.getKey());
        req.setRemark("OK，没问题");
        ResponseModel responseModel = caseFacade.preCheck(req);
        System.out.println(JSON.toJSONString(responseModel));
    }


    /**
     * 后台风控审核通过
     */
    @Test
    public void testRiskCheckSuccess(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseId("201902211126234989");
        req.setOperatorId(3);
        //req.setMainStatus(CaseEnum.MainStatus.LAWSUIT_RISK_CHECK_SUCCESS.getKey());
        req.setMainStatus(CaseEnum.MainStatus.EXECUTE_RISK_CHECK_SUCCESS.getKey());
        req.setRemark("传说中你为爱甘心被搁浅");
        ResponseModel responseModel = caseFacade.riskCheck(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    /** 安排承办律师 **/
    @Test
    public void testSetLawyerForCase(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseId("201902211126234989");
        req.setLawyerAccountId(4);
        req.setOperatorId(4);
        ResponseModel responseModel = caseFacade.setCaseLawyer(req);
        System.out.println(JSON.toJSONString(responseModel));
    }


    /** 合同线下签约 **/
    @Test
    public void testSignContract(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseId("201903011611440862");
        req.setOperatorId(5);
        req.setMainStatus(CaseEnum.MainStatus.HAS_SIGN_CONTRACT.getKey());
        ResponseModel responseModel = caseFacade.signContract(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testGetCaseDetail(){
//        CaseCommReq req = new CaseCommReq();
//        req.setCaseId("201903271101335633");
//        ResponseModel caseVo = caseFacade.getCaseDetail(req);
//        System.out.println(JSON.toJSONString(caseVo));

        CaseVo caseVo = caseService.getCaseDetailByCaseId("201905071908182872",13);
        System.out.println(JSON.toJSONString(caseVo));
//        TCase tCase = caseDao.getCaseByCaseId("201903131804533083");
//        System.out.println(JSON.toJSONString(tCase));
    }

    @Test
    public void testGetCaseDetailAdmin(){
        CaseAdminVo caseVo = caseService.getCaseDetailByCaseIdAdmin("201903251806334494");
        System.out.println(JSON.toJSONString(caseVo));

//        TCase tCase = caseDao.getCaseByCaseId("201903131804533083");
//        System.out.println(JSON.toJSONString(tCase));
    }

    @Test
    public void testGetSelfCase(){
        CaseCommReq req = new CaseCommReq();
        req.setAccountId(34);
        PageInfo<TCase> pageInfo = caseService.searchSelfCaseByName(req);
        System.out.println(JSON.toJSONString(pageInfo));
    }

    @Test
    public void testGetCaseByName(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseBrieName("刑事");
        ResponseModel responseModel = caseFacade.searchCaseByName(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testGetAcceptedCaseList(){
        CaseCommReq req = new CaseCommReq();
        //req.setMainStatus(CaseEnum.MainStatus.SUCCESS.getKey());
        req.setAccountId(19);
        ResponseModel responseModel = caseFacade.getMyAcceptCaseList(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testGetCaseAdminDetail(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseId("201903091733404471");
        ResponseModel responseModel = caseFacade.getAdminCaseBaseInfo(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testSearchLawyerOrder(){
        CaseLawyerOrderReq req = new CaseLawyerOrderReq();
        req.setPlaintiff("吕布");
        ResponseModel responseModel = caseFacade.searchLawyerOrder(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testSearchCaseByName(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseBrieName("其");
        ResponseModel responseModel = caseFacade.searchCaseByName(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testPublishAsset(){
        CaseAssetReq req = new CaseAssetReq();
        req.setAccountId(8);
        req.setAssetAmount("30000");
        req.setAssetDesc("继续跳舞");
        ResponseModel responseModel = caseFacade.publishCaseAsset(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testGetQNFileInfo(){
        String url = "https://cdn-static.360yhl.com/FgO6moA8pBmkhy7ep4OrwHBZpKcQ?attname=【360拍】—个人中心产看平台资金账户明细，无筛选功能.png";
        String result = HttpUtils.sendGet(url.split("\\?attname")[0]+"?stat");
        System.out.println(result);
        QNFileInfoDto dto = JSON.parseObject(result, QNFileInfoDto.class);
        System.out.println(JSON.toJSONString(dto));
    }

    @Test
    public void testSetLawyer(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseId("201903011648285332");
        req.setOperatorId(1);
        req.setLawyerAccountId(5);
        ResponseModel responseModel = caseFacade.setCaseLawyer(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testGetAssetList(){
        CaseAssetReq req = new CaseAssetReq();
        req.setBeginTime("2019-03-11");
        req.setEndTime("2019-03-12");
        ResponseModel responseModel = caseFacade.searchCaseAsset(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testSearchList(){
        CaseCommReq req = new CaseCommReq();
        ResponseModel responseModel = caseFacade.searchCase(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void  testGetNewPublishedCaseList(){
        CaseCommReq req = new CaseCommReq();
        req.setCaseTypeId(5);
        PageInfo<CasePublishVo> voPageInfo = caseService.getPublishedCaseList(req);
        System.out.println(voPageInfo);
    }

}
