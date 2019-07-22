package com.winback.web.controller._case;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade._case.req.CaseAssetReq;
import com.winback.core.facade._case.req.CaseCommReq;
import com.winback.web.controller.AbstractController;
import com.winback.web.vo.LoginInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RuQ
 * @Title: CaseController
 * @ProjectName winback
 * @Description:
 * @date 2019/2/21 10:16
 */
@RestController
public class CaseController extends AbstractController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CaseController.class);

    @Reference(version = "1.0.1")
    private CaseFacade caseFacade;


    /**
     * 发布案件接口
     */
    @PostMapping(value = "/confined/case/publishCase")
    public ResponseModel publishCase(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 publishCase,参数:{}", JSON.toJSONString(req));
        if (req.getCaseAmount() == null || StringUtils.isEmpty(req.getPlaintiff())
                || StringUtils.isEmpty(req.getDefendant())
                || StringUtils.isEmpty(req.getCaseDesc())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if (req.getPlaintiff().length() > 200) {
            return ResponseModel.fail("原告最多输入200个字符");
        }
        if (req.getDefendant().length() > 200) {
            return ResponseModel.fail("被告最多输入200个字符");
        }
        req.setCaseFlag(false);
        if(req.getCaseTypeId() == null ||req.getCaseTypeId() == 0){
            req.setCaseTypeId(1);
            req.setCaseFlag(true);//为了区分风险投 和案件发布的推送

        }
        req.setMainSource(CaseEnum.MainSource.ONLINE.getKey());
        req.setSubSource(CaseEnum.SubSource.APP.getKey());
        req.setAccountId(loadCurLoginId());
        return caseFacade.publishCase(req);
    }


    /**
     * 发布债权收购接口
     */
    @PostMapping(value = "/confined/case/publishCaseAsset")
    public ResponseModel publishCaseAsset(@RequestBody CaseAssetReq req) {
        LOGGER.info("开始调用 publishCaseAsset,参数:{}", JSON.toJSONString(req));
        if (req.getAssetAmount() == null
                || StringUtils.isEmpty(req.getAssetDesc())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setAccountId(loadCurLoginId());
        return caseFacade.publishCaseAsset(req);
    }


    /**
     * 我发布的案件列表接口
     */
    @PostMapping(value = "/confined/case/getMyPublishCaseList")
    public ResponseModel getMyPublishCaseList(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 getMyPublishCaseList,参数:{}", JSON.toJSONString(req));
        req.setAccountId(loadCurLoginId());
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.PARTY.getType());
        return caseFacade.getMyPublishCaseList(req);
    }

    /**
     * 我承接的案件列表接口
     */
    @PostMapping(value = "/confined/case/getMyAcceptCaseList")
    public ResponseModel getMyAcceptCaseList(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 getMyAcceptCaseList,参数:{}", JSON.toJSONString(req));
        req.setAccountId(loadCurLoginId());
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.LAWYER.getType());
        return caseFacade.getMyAcceptCaseList(req);
    }

    /**
     * 律师承接案件
     */
    @PostMapping(value = "/confined/case/acceptCase")
    public ResponseModel acceptCase(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 acceptCase,参数:{}", JSON.toJSONString(req));
        req.setAccountId(loadCurLoginId());
        return caseFacade.acceptCase(req);
    }

    /**
     * 案件库列表接口
     */
    @PostMapping(value = "/confined/case/getCaseList")
    public ResponseModel getCaseList(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 getCaseList,参数:{}", JSON.toJSONString(req));
        //req.setAccountId(loadCurLoginId());
        return caseFacade.getCaseList(req);
    }

    /**
     * 已完结案件列表接口
     */
    @PostMapping(value = "/confined/case/getHasEndCaseList")
    public ResponseModel getHasEndCaseList(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 getHasEndCaseList,参数:{}", JSON.toJSONString(req));
        req.setAccountId(loadCurLoginId());
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.PARTY.getType());

        req.setMainStatus(CaseEnum.MainStatus.SUCCESS.getKey());
        return caseFacade.getEndCaseList(req);
    }

    /**
     * 案件详情接口
     */
    @PostMapping(value = "/confined/case/getCaseDetail")
    public ResponseModel getCaseDetail(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 getCaseDetail,参数:{}", JSON.toJSONString(req));
        req.setAccountId(loadCurLoginId());
        return caseFacade.getCaseDetail(req);
    }

    /**
     * 更新案件进展
     */
    @PostMapping(value = "/confined/case/updateCaseStatus")
    public ResponseModel updateCaseStatus(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 updateCaseStatus,参数:{}", JSON.toJSONString(req));
        req.setLawyerAccountId(loadCurLoginId());
        return caseFacade.updateCaseStatus(req);
    }

    /**
     * 获取新发布的案件列表
     */
    @PostMapping(value = "/open/case/getPublishedCaseList")
    public ResponseModel getPublishedCaseList(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 getPublishedCaseList,参数:{}", JSON.toJSONString(req));
        //eq.setLawyerAccountId(loadCurLoginId());
        //req.setPerPage(5);
        return caseFacade.getPublishedCaseList(req);
    }


    /**
     * 案件搜索
     */
    @PostMapping(value = "/confined/case/searchCase")
    public ResponseModel searchCase(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 searchCase,参数:{}", JSON.toJSONString(req));
        if(!StringUtils.isEmpty(req.getSearchType()) && "1".equals(req.getSearchType())){
            req.setAccountId(loadCurLoginId());
            return caseFacade.searchSelfCaseByName(req);
        }else{
            return caseFacade.searchCaseByName(req);
        }
    }


    /**
     * 案件搜索
     */
    @PostMapping(value = "/confined/case/searchSelfCase")
    public ResponseModel searchSelfCase(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 searchSelfCase,参数:{}", JSON.toJSONString(req));
        req.setAccountId(loadCurLoginId());
        return caseFacade.searchSelfCaseByName(req);
    }

    /**
     * 我管理的案件列表接口
     */
    @PostMapping(value = "/confined/case/getMyManageCaseList")
    public ResponseModel getMyManageCaseList(@RequestBody CaseCommReq req) {
        LOGGER.info("开始调用 getMyPublishCaseList,参数:{}", JSON.toJSONString(req));
        req.setAccountId(loadCurLoginId());
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType());
        return caseFacade.getMyManageCaseList(req);
    }

}
