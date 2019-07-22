package com._360pai.core.service.assistant;

import com._360pai.core.model.assistant.TBuriedPoint;
import com._360pai.core.model.assistant.TFrontError;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/8 12:31
 */
public interface TBuriedPointService {
    /**
     * 描述 插入日志记录
     *
     * @author : whisky_vip
     * @date : 2018/10/8 12:32
     */
    void insert(TBuriedPoint tBuriedPoint);

    void insertFrontError(TFrontError model);
}
