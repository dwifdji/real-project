package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.facade.THallActivityFacade;
import com._360pai.core.facade.assistant.req.THallActivityReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: THallActivityController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/18 18:40
 */
@RestController
public class THallActivityController {

    @Reference(version = "1.0.0")
    private THallActivityFacade tHallActivityFacade;

    @PostMapping(value = "/open/assetHall/list")
    public ResponseModel getTHallActivityList(@RequestBody THallActivityReq req) {
        Object object = tHallActivityFacade.getTHallActivityList(req);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", object);
        return ResponseModel.succ(jsonObject, PropertyFilterFactory.create(new String[]{"status", "mode", "cityId"}));
    }
    
    
    @GetMapping("/open/assetHall/enrolling_activity_list")
    public ResponseModel getHallEnrollingActivities(THallActivityReq req) {
    	if(req.getHallId() == null) {
    		return ResponseModel.fail(ApiCallResult.EMPTY);
    	}
    	return tHallActivityFacade.getHallEnrollingActivities(req.getHallId());
    }
}
