package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.BuriedPointReq;
import com._360pai.core.facade.assistant.req.FrontErrorReq;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/8 12:36
 */
public interface TBuriedPointFacade {
    int insert(BuriedPointReq req);

    int insertFrontError(FrontErrorReq req);
}
