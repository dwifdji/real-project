package com._360pai.core.service.assistant;

import com._360pai.core.common.constants.BackstageOperationEnum;
import com._360pai.core.model.assistant.TBackstageOperationRecord;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/15 10:13
 */
public interface TBackstageOperationRecodService {
    /**
     * @param type
     * @param refId
     * @param content
     * @param operatorId
     * @return 数据id
     */
    Integer insertRecode(BackstageOperationEnum.Type type, Long refId, String content, Integer operatorId);

    /**
     * 描述 获取时间最近的一条记录
     *
     * @author : whisky_vip
     * @date : 2018/11/15 10:32
     */
    TBackstageOperationRecord selectLastRecode(BackstageOperationEnum.Type type, Long refId, String content);

    /**
     * 获取 最后支付时间
     *
     * @param type
     * @param refId
     * @param content
     * @return
     */
    String getPayDeadlineTimestamp(BackstageOperationEnum.Type type, Long refId, String content);
}
