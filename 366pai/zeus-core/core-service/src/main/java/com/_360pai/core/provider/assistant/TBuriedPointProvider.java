package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.TBuriedPointFacade;
import com._360pai.core.facade.assistant.req.BuriedPointReq;
import com._360pai.core.facade.assistant.req.FrontErrorReq;
import com._360pai.core.model.assistant.TBuriedPoint;
import com._360pai.core.model.assistant.TFrontError;
import com._360pai.core.service.assistant.TBuriedPointService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/8 12:38
 */
@Component
@Service(version = "1.0.0")
public class TBuriedPointProvider implements TBuriedPointFacade {
    @Autowired
    private TBuriedPointService tBuriedPointService;

    @Override
    public int insert(BuriedPointReq req) {
        TBuriedPoint tBuriedPoint = new TBuriedPoint();
        BeanUtils.copyProperties(req, tBuriedPoint);
        tBuriedPointService.insert(tBuriedPoint);
        return tBuriedPoint.getId();
    }

    @Override
    public int insertFrontError(FrontErrorReq req) {
        TFrontError model = new TFrontError();
        BeanUtils.copyProperties(req, model);
        tBuriedPointService.insertFrontError(model);
        return model.getId();
    }
}
