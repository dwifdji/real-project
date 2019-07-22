package com.winback.core.service.assistant;

import com.winback.core.model.assistant.TBuriedPoint;

/**
 * @author xdrodger
 * @Title: BuriedPointService
 * @ProjectName winback
 * @Description:
 * @date 2019/2/15 09:28
 */
public interface BuriedPointService {
    Integer insert(TBuriedPoint model);

    Integer update(TBuriedPoint model);
}
