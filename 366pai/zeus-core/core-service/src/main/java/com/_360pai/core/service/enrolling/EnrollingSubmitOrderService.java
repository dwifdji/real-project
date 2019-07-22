package com._360pai.core.service.enrolling;

import com._360pai.core.condition.enrolling.EnrollingSubmitOrderCondition;
import com._360pai.core.model.enrolling.EnrollingSubmitOrder;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/18 16:54
 */
public interface EnrollingSubmitOrderService {

    int saveSubmitOrder(EnrollingSubmitOrder order);


    EnrollingSubmitOrder getSubmitOrder(EnrollingSubmitOrderCondition order);

    /**
     *
     *更新订单以及活动状态
     */
    void updateSubmitOrderAndActivity(EnrollingSubmitOrder order);

}
