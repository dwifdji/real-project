package com.winback.applet.controller.assistant;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.winback.applet.controller.AbstractController;
import com.winback.arch.common.AppletReq;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ToolUtil;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.winback.arch.web.utils.IpUtils;
import com.winback.core.commons.SystemDict;
import com.winback.core.facade.assistant.AssistantFacade;
import com.winback.core.facade.assistant.req.AppletAssistantReq;
import com.winback.core.facade.contract.ContractFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * 获取所有合同大类类型列表接口
     */
    @GetMapping(value = "/getContractBigTypeList")
    public ResponseModel getContractBigTypeList(AppletReq req) {
        return ResponseModel.succ(contractFacade.getContractBigTypeList());
    }

    /**
     * 获取配置信息接口
     */
    @GetMapping(value = "/getConfigInfo")
    public ResponseModel getConfigInfo(AppletReq req) {
        Map<String, Object> data = new HashMap<>();
        data.put("servicePhone", systemProperties.getAppletServicePhone());
        return ResponseModel.succ(data);
    }

    /**
     * 数据埋点 新增接口
     */
    @PostMapping(value = "/buriedPoint/insert")
    public ResponseModel buriedPointInsert(@RequestBody AppletAssistantReq.BuriedPointReq req, HttpServletRequest request) {
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
                req.setDeviceType("3");
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
}
