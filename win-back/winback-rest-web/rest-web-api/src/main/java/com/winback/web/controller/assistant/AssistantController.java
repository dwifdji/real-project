package com.winback.web.controller.assistant;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.AppletReq;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ToolUtil;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.winback.arch.web.utils.IpUtils;
import com.winback.core.commons.SystemDict;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade._case.vo.CaseStatus;
import com.winback.core.facade.assistant.AssistantFacade;
import com.winback.core.facade.assistant.req.AppAssistantReq;
import com.winback.core.facade.assistant.vo.ShareInfo;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.web.controller.AbstractController;
import com.winback.web.vo.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author xdrodger
 * @Title: AssistantController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:37
 */
@Slf4j
@RestController
@RequestMapping(value = "/open/assistant", produces = "application/json;charset=UTF-8")
public class AssistantController extends AbstractController {
    @Reference(version = "1.0.0")
    private AssistantFacade assistantFacade;
    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private IpUtils ipUtils;
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

    /**
     * 数据字典接口
     */
    @GetMapping(value = "/systemDict")
    public ResponseModel systemDict() {
        Map<Object, Map<Object, Object>> map = SystemDict.instance.getSystemDict();
        return ResponseModel.succ(map);
    }

    /**
     * 获取七牛token信息
     */
    @GetMapping(value = "/getQiniuToken")
    public ResponseModel getQiniuToken(@RequestParam(value = "fileType", required = false, defaultValue = "") String fileType) {
        return assistantFacade.getQiNiuToken(fileType);
    }

    /**
     * 获取所有城市接口
     */
    @GetMapping(value = "/getAllCities")
    public ResponseModel getAllCities(@RequestParam(value = "type", required = false, defaultValue = "0") String type) {
        return assistantFacade.getAllCities(type);
    }
    /**
     * 获取所有省份接口
     */
    @GetMapping(value = "/getAllProvinces")
    public ResponseModel getAllProvinces() {
        return ResponseModel.succ(assistantFacade.getAllProvinces());
    }

    /**
     * 获取省份下城市接口
     */
    @GetMapping(value = "/getCitiesByProvince")
    public ResponseModel getCitiesByProvince(String provinceCode) {
        return ResponseModel.succ(assistantFacade.getCitiesByProvinceCode(provinceCode));
    }

    /**
     * 获取城市下区县接口
     */
    @GetMapping(value = "/getAreasByCity")
    public ResponseModel getAreasByCity(String cityCode) {
        return ResponseModel.succ(assistantFacade.getAreasByCityCode(cityCode));
    }

    /**
     * 获取帮助条目列表接口
     */
    @GetMapping(value = "/help/item/list")
    public ResponseModel getHelpItemList(AppReq req) {
        return ResponseModel.succ(assistantFacade.getHelpItemList(req));
    }

    /**
     * 获取帮助条目列表接口
     */
    @GetMapping(value = "/help/item")
    public ResponseModel getHelpItem(AppAssistantReq.GetHelpItemReq req) {
        return ResponseModel.succ(assistantFacade.getHelpItem(req));
    }

    /**
     * 获取所有案由列表接口
     */
    @GetMapping(value = "/getCaseBriefList")
    public ResponseModel getCaseBriefList(AppReq req) {
        return ResponseModel.succ(caseFacade.getCaseBriefList(req));
    }

    /**
     * 获取所有合同大类类型列表接口
     */
    @GetMapping(value = "/getContractBigTypeList")
    public ResponseModel getContractBigTypeList(AppReq req) {
        return ResponseModel.succ(contractFacade.getContractBigTypeList());
    }


    /**
     * 获取配置信息接口
     */
    @GetMapping(value = "/getConfigInfo")
    public ResponseModel getConfigInfo(AppReq req) {
        Map<String, Object> data = new HashMap<>();
        data.put("servicePhone", systemProperties.getAppletServicePhone());
        data.put("appDownloadUrl", systemProperties.getAppDownloadUrl());
        return ResponseModel.succ(data);
    }

    /**
     * 数据埋点 新增接口
     */
    @PostMapping(value = "/buriedPoint/insert")
    public ResponseModel buriedPointInsert(@RequestBody AppAssistantReq.BuriedPointReq req, HttpServletRequest request) {
        // 埋点一直返回成功，不影响后面流程
        int id ;
        try {
            Integer loginId = loadCurLoginId();
            req.setUserId(loginId == null ? null : loginId + "");
            req.setUserType("");
            Date now = new Date();
            req.setCreateTime(now);
            req.setUpdateTime(now);
            if (req.getDevice() != null) {
                req.setDeviceType(req.getDevice().getDeviceType());
                req.setDeviceToken(req.getDevice().getDeviceToken());
            } else {
                req.setDeviceType("0");
            }
            if (loginId != null && !loginId.equals(-1) && req.getDevice() != null) {
                assistantFacade.simpleSaveDevice(loginId, req.getDevice());
            }
            String     clientIp = ToolUtil.getClientIp(request);
            JSONObject map      = ipUtils.getAddressByIPTencent(clientIp);

            Map<String, String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
            req.setDeviceMark(browserMap.get("os") + "-" + browserMap.get("browser"));

            req.setCity("" + map.get("city"));
            req.setProvince("" + map.get("province"));
            req.setIp(clientIp);
            id = assistantFacade.buriedPointInsert(req);
        } catch (Exception e) {
            return ResponseModel.succ();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        return ResponseModel.succ(data);
    }

    /**
     * 数据埋点 更新接口
     */
    @PostMapping(value = "/buriedPoint/update")
    public ResponseModel buriedPointUpdate(@RequestBody AppAssistantReq.BuriedPointUpdateReq req, HttpServletRequest request) {
        try {
            req.setUpdateTime(new Date());
            assistantFacade.buriedPointUpdate(req);
        } catch (Exception e) {
            return ResponseModel.succ();
        }
        return ResponseModel.succ();
    }


    /**
     * 获取分享信息接口
     */
    @GetMapping(value = "/getShareInfo")
    public ResponseModel getShareInfo(@RequestParam(value = "type", required = false, defaultValue = "1") String type) {
        LoginInfo loginInfo = loadCurLoginInfo();
        String mobile = "";
        if (loginInfo != null && StringUtils.isNotBlank(loginInfo.getMobile())) {
            mobile = loginInfo.getMobile();
        }
        return ResponseModel.succ(getShareInfo(type, mobile));
    }

    private ShareInfo getShareInfo(String type, String mobile) {
        ShareInfo shareInfo = new ShareInfo();
        if ("1".equals(type)) {
            shareInfo.setTitle(systemProperties.getShareTitleFranchisee());
            shareInfo.setContent(systemProperties.getShareContentFranchisee());
            shareInfo.setImgUrl(systemProperties.getShareImgUrlFranchisee());
            shareInfo.setUrl(systemProperties.getShareUrlFranchisee() + mobile);
        } else if ("2".equals(type)) {
            shareInfo.setTitle(systemProperties.getShareTitleApp());
            shareInfo.setContent(systemProperties.getShareContentApp());
            shareInfo.setImgUrl(systemProperties.getShareImgUrlApp());
            shareInfo.setUrl(systemProperties.getShareUrlApp() + mobile);
        }
        return shareInfo;
    }

    /**
     * 获取案由大类列表接口
     */
    @GetMapping(value = "/getCaseBigBriefList")
    public ResponseModel getCaseBigBriefList(AppReq req) {
        return ResponseModel.succ(caseFacade.getCaseBigBriefList());
    }

    /**
     * 获取案件筛选项接口
     */
    @GetMapping(value = "/case/nav")
    public ResponseModel getCaseNav(AppReq req) {
        Map<String, Object> data = new HashMap<>();
        data.put("caseBigBriefList", caseFacade.getCaseBigBriefList().getList());
        data.put("caseStatusList", getFrontCaseStatusList());
        return ResponseModel.succ(data);
    }

    private List<CaseStatus> getFrontCaseStatusList() {
        List<CaseStatus> list = new LinkedList<>();
        list.add(new CaseStatus("", "全部"));
        list.add(new CaseStatus(CaseEnum.MainStatus.RISK_CHECK_SUCCESS));
        list.add(new CaseStatus(CaseEnum.MainStatus.HAS_SIGN_CONTRACT));
        list.add(new CaseStatus(CaseEnum.MainStatus.BEING_LAWSUIT));
        list.add(new CaseStatus(CaseEnum.MainStatus.BEING_EXECUTE));
        list.add(new CaseStatus(CaseEnum.MainStatus.WAIT_RECEIVED_PAY));
        list.add(new CaseStatus(CaseEnum.MainStatus.SUCCESS));
        return list;
    }

    /**
     * 检查版本更新接口
     */
    @PostMapping(value = "/check/update")
    public ResponseModel checkUpdate(@RequestBody AppAssistantReq.CheckUpdateReq req) {
        return ResponseModel.succ(assistantFacade.checkUpdate(req));
    }
}
