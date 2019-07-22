package com.tzCloud.core.service;

import com.tzCloud.core.constant.AssistantEnum;

/**
 * @author xdrodger
 * @Title: AccountSearchRecord
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/25 09:14
 */
public interface AccountSearchRecordService {
    void saveSearchRecord(AssistantEnum.SearchType type, Integer accountId, String content);
}
