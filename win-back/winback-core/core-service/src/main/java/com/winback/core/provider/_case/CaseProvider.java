package com.winback.core.provider._case;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.NumberFormatUtils;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade._case.req.*;
import com.winback.core.facade._case.resp.CaseAssetVo;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.CaseBigBrief;
import com.winback.core.facade._case.vo.CaseBrief;
import com.winback.core.facade._case.vo.HomePageCaseVO;
import com.winback.core.facade.operate.req.OperationReq;
import com.winback.core.model._case.TCase;
import com.winback.core.service._case.CaseBriefService;
import com.winback.core.service._case.CaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RuQ
 * @Title: CaseProvider
 * @ProjectName winback
 * @Description:
 * @date 2019/1/18 15:47
 */
@Component
@Service(version = "1.0.0")
public class CaseProvider implements CaseFacade {

    public static final Logger LOGGER = LoggerFactory.getLogger(CaseProvider.class);

    @Autowired
    private CaseService caseService;
    @Autowired
    private CaseBriefService caseBriefService;


    @Override
    public ResponseModel publishCaseAsset(CaseAssetReq req) {
        LOGGER.info("开始调用 publishCaseAsset，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.publishCaseAsset(req));
    }

    @Override
    public ResponseModel publishCase(CaseCommReq req) {
        LOGGER.info("开始调用 publishCase，参数:{}", JSON.toJSONString(req));
        req.setMainStatus(CaseEnum.MainStatus.INIT.getKey());
        return ResponseModel.succ(caseService.saveCase(req));
    }

    @Override
    public ResponseModel searchCaseByName(CaseCommReq req) {
        LOGGER.info("开始调用 searchCaseByName，参数:{}", JSON.toJSONString(req));
        req.setFrontFlag(true);
        if(StringUtils.isEmpty(req.getCaseBrieName())){
            return ResponseModel.succ(caseService.searchCase(req));
        }else{
            return ResponseModel.succ(caseService.searchCaseByName(req));
        }
    }

    @Override
    public ResponseModel searchSelfCaseByName(CaseCommReq req) {
        LOGGER.info("开始调用 searchCaseByName，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.searchSelfCaseByName(req));
    }

    @Override
    public ResponseModel getMyPublishCaseList(CaseCommReq req) {
        LOGGER.info("开始调用 getMyPublishCaseList，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.searchCase(req));
    }

    @Override
    public ResponseModel getCaseList(CaseCommReq req) {
        LOGGER.info("开始调用 getCaseList，参数:{}", JSON.toJSONString(req));
        //req.setMainStatus(CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey());
        req.setFrontFlag(true);
        return ResponseModel.succ(caseService.searchCase(req));
    }

    @Override
    public ResponseModel getEndCaseList(CaseCommReq req) {
        LOGGER.info("开始调用 getEndCaseList，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.searchCase(req));
    }

    @Override
    public ResponseModel getMyAcceptCaseList(CaseCommReq req) {
        LOGGER.info("开始调用 getMyAcceptCaseList，参数:{}", JSON.toJSONString(req));
        if(!StringUtils.isEmpty(req.getCooperateWay())){
            req.setCaseTypeId(Integer.parseInt(req.getCooperateWay()));
        }
        return ResponseModel.succ(caseService.getHasApplyedCaseList(req));
    }

    @Override
    public ResponseModel getCaseDetail(CaseCommReq req) {
        LOGGER.info("开始调用 getCaseDetail，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.getCaseDetailByCaseId(req.getCaseId(),req.getAccountId()));
    }

    @Override
    public ResponseModel acceptCase(CaseCommReq req) {
        LOGGER.info("开始调用 acceptCase，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.acceptCase(req.getAccountId(),req.getCaseId()));
    }

    @Override
    public ResponseModel updateCaseStatus(CaseCommReq req) {
        LOGGER.info("开始调用 updateCaseStatus，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.updateCaseStatus(req.getLawyerAccountId(),req.getCaseId(),req.getSubStatus(),req.getStatusDesc()));
    }

    @Override
    public ResponseModel getPublishedCaseList(CaseCommReq req) {
        LOGGER.info("开始调用 getPublishedCaseList，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.getPublishedCaseList(req));
    }

    @Override
    public ResponseModel uploadFile(AttachmentReq req) {
        LOGGER.info("开始调用 uploadFile，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.uploadFile(req));
    }

    @Override
    public ResponseModel searchCase(CaseCommReq req) {
        LOGGER.info("开始调用 searchCase，参数:{}", JSON.toJSONString(req));
        PageInfo<TCase> pageInfo = caseService.searchCase(req);
        if(pageInfo != null && pageInfo.getList() != null){
            for(TCase tCase : pageInfo.getList()){
                tCase.setCaseStepList(caseService.transStatus2Step(tCase.getCaseId(),tCase.getMainStatus()));
            }
        }
        return ResponseModel.succ(pageInfo);
    }



    @Override
    public ResponseModel updateCaseBaseInfo(CaseCommReq req) {
        LOGGER.info("开始调用 updateCaseBaseInfo，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.updateCaseInfo(req));
    }

    @Override
    public ResponseModel preCheck(CaseCommReq req) {
        LOGGER.info("开始调用 preCheck，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.verifyCase(req));
    }

    @Override
    public ResponseModel riskCheck(CaseCommReq req) {
        LOGGER.info("开始调用 riskCheck，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.verifyCase(req));
    }

    @Override
    public ResponseModel signContract(CaseCommReq req) {
        LOGGER.info("开始调用 signContract，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.verifyCase(req));
    }

    @Override
    public ResponseModel endLawsuit(CaseCommReq req) {
        LOGGER.info("开始调用 endLawsuit，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.endLawsuit(req));
    }

    @Override
    public ResponseModel endExcute(CaseCommReq req) {
        LOGGER.info("开始调用 endExcute，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.endExcute(req));
    }

    @Override
    public ResponseModel endSuccess(CaseCommReq req) {
        LOGGER.info("开始调用 endSuccess，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.endSuccess(req));
    }

    @Override
    public ResponseModel verifyPayMoney(CaseCommReq req) {
        LOGGER.info("开始调用 verifyPayMoney，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel aiSearchFirm(CaseCommReq req) {
        return null;
    }

    @Override
    public ResponseModel getAcceptedLawyers(CaseCommReq req) {
        LOGGER.info("开始调用 getAcceptedLawyers，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.getApplyAcceptLawyers(req));
    }

    @Override
    public ResponseModel searchLawyer(CaseCommReq req) {
        LOGGER.info("开始调用 searchLawyer，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.searchLawyer(req));
    }

    @Override
    public ResponseModel setCaseLawyer(CaseCommReq req) {
        LOGGER.info("开始调用 setCaseLawyer，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.setLawyerForCase(req));
    }

    @Override
    public PageInfoResp<Case> getPublishCaseList(AdminCaseReq.QueryReq req) {
        LOGGER.info("开始调用 getPublishCaseList，参数:{}", JSON.toJSONString(req));
        return caseService.getListByAccountId(req);
    }

    @Override
    public ListResp<CaseBrief> getCaseBriefList(AdminCaseReq.QueryReq req) {
        return caseBriefService.getCaseBriefList(req);
    }

    @Override
    public PageInfoResp<CaseBrief> getCaseBriefListByPage(AdminCaseReq.QueryReq req) {
        return caseBriefService.getCaseBriefListByPage(req);
    }

    @Override
    public PageInfoResp<CaseBrief> getCaseBriefList(AppReq req) {
        return caseBriefService.getCaseBriefList(req);
    }

    @Override
    public Integer addCaseBrief(AdminCaseReq.AddCaseBriefReq req) {
        return caseBriefService.addCaseBrief(req);
    }

    @Override
    public Integer editCaseBrief(AdminCaseReq.EditCaseBriefReq req) {
        return caseBriefService.editCaseBrief(req);
    }

    @Override
    public Integer deleteCaseBrief(AdminCaseReq.DeleteCaseBriefReq req) {
        return caseBriefService.deleteCaseBrief(req);
    }

    @Override
    public ResponseModel searchCaseAsset(CaseAssetReq req) {
        LOGGER.info("开始调用 searchCaseAsset，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.searchCaseAsset(req));
    }

    @Override
    public List<CaseAssetVo> exportCaseList(CaseAssetReq req) {
        LOGGER.info("开始调用 exportCaseList，参数:{}", JSON.toJSONString(req));
        return caseService.exportCaseList(req);
    }

    @Override
    public ResponseModel getRecommendedCaseList(OperationReq.CaseSizeReq caseSizeReq) {
        Integer caseSize = caseSizeReq.getCaseSize() == null?20:caseSizeReq.getCaseSize();
        List<HomePageCaseVO> homePageCaseVOS = caseService.getRecommendedCaseList(caseSize);
        if("0".equals(caseSizeReq.getLawyerFlag())) {
            setHomePageCases(homePageCaseVOS);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", homePageCaseVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ListResp<CaseBigBrief> getCaseBigBriefList() {
        return caseBriefService.getCaseBigBriefList();
    }

    @Override
    public ListResp<CaseBigBrief> getBackgroundCaseBigBriefList(Boolean all) {
        return caseBriefService.getBackgroundCaseBigBriefList(all);
    }

    @Override
    public PageInfoResp<Case> getProjectManagerAllocatedCaseList(CaseCommReq req) {
        return caseService.getProjectManagerAllocatedCaseList(req);
    }

    @Override
    public Integer allocatedCaseToProjectManager(CaseCommReq req) {
        return caseService.allocatedCaseToProjectManager(req);
    }

    @Override
    public Integer cancelAllocatedCaseToProjectManager(CaseCommReq req) {
        return caseService.cancelAllocatedCaseToProjectManager(req);
    }

    @Override
    public ResponseModel getMyManageCaseList(CaseCommReq req) {
        return ResponseModel.succ(caseService.getMyManageCaseList(req));
    }

    @Override
    public Integer getProjectManagerByCaseId(String caseId) {
        return caseService.getProjectManagerByCaseId(caseId);
    }

    private void setHomePageCases(List<HomePageCaseVO> homePageCaseVOS) {
        if(homePageCaseVOS != null && homePageCaseVOS.size() > 0) {
            for (HomePageCaseVO homePageCaseVO: homePageCaseVOS) {
                homePageCaseVO.setCaseCourt("xxxxxxxxxx");
                homePageCaseVO.setDefendant(formatDefendant(homePageCaseVO.getDefendant()));
            }
        }
    }


    /**
     *
     *格式化被告
     */
    private static  String formatDefendant(String defendant) {

        String formatStr = StringUtils.isBlank(defendant)?
                "xxxxxxxxxx" :  defendant.substring(0,1) + "xxxxxxxxx";

        //看下是否有多个被告
        Integer num = defendant.split("、").length;

        if(num>1){

            formatStr = formatStr +"等"+ NumberFormatUtils.NumberToChn(num)+"名被告";

        }
        return formatStr;
    }


    @Override
    public ResponseModel getAdminCaseBaseInfo(CaseCommReq req) {
        LOGGER.info("开始调用 getAdminCaseBaseInfo，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.getCaseDetailByCaseIdAdmin(req.getCaseId()));
    }

    @Override
    public ResponseModel getAdminCaseCheckInfo(CaseCommReq req) {
        LOGGER.info("开始调用 getAdminCaseCheckInfo，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.getAdminCaseCheckInfo(req.getCaseId()));
    }

    @Override
    public ResponseModel getAdminCaseRiskInfo(CaseCommReq req) {
        LOGGER.info("开始调用 getAdminCaseCheckInfo，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.getAdminCaseRiskInfo(req.getCaseId()));
    }

    @Override
    public ResponseModel getAdminCaseSignInfo(CaseCommReq req) {
        LOGGER.info("开始调用 getAdminCaseSignInfo，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.getAdminCaseSignInfo(req.getCaseId()));
    }

    @Override
    public ResponseModel deleteCaseAttachment(CaseCommReq req) {
        LOGGER.info("开始调用 deleteCaseAttachment，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.deleteCaseAttachment(req.getAttachmentId()));
    }

    @Override
    public ResponseModel getCaseStatusList() {
        List<TCase> list = new ArrayList<>();
        list.add(new TCase(null,"全部"));
        list.add(new TCase("INIT","待资料预检"));
        list.add(new TCase("PRE_CHECK_FAIL","资料预检失败"));
        list.add(new TCase("PRE_CHECK_SUCCESS","待风控审核"));
//        list.add(new TCase("LAWSUIT_RISK_CHECK_SUCCESS","诉讼风控审核通过"));
//        list.add(new TCase("LAWSUIT_RISK_CHECK_FAIL","诉讼风控审核失败"));
        //list.add(new TCase("RISK_CHECK_FAIL","风控审核拒绝"));
        list.add(new TCase("RISK_CHECK_SUCCESS","合同待签约"));
        list.add(new TCase("HAS_SIGN_CONTRACT","待放款审核"));
        list.add(new TCase("BEING_LAWSUIT","案件进入诉讼阶段"));
        list.add(new TCase("BEING_EXECUTE","案件进入执行阶段"));
        list.add(new TCase("WAIT_RECEIVED_PAY","等待回款"));
        list.add(new TCase("SUCCESS","已完成"));
        return ResponseModel.succ(list);
    }

    @Override
    public ResponseModel searchLawyerOrder(CaseLawyerOrderReq req) {
        return ResponseModel.succ(caseService.searchLawyerOrder(req));
    }


    @Override
    public ResponseModel getCaseStatusDescList() {
        return ResponseModel.succ(caseService.getCaseStatusDescList());
    }

    @Override
    public ResponseModel updateCaseStatusDesc(CaseCommReq req) {
        LOGGER.info("开始调用 updateCaseStatusDesc，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(caseService.updateCaseStatusDesc(req.getCaseStatusDescId(),req.getStatusDesc()));
    }
}
