package com._360pai.seimi.service;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.AliPm;

/**
 * @author zxiao
 * @Title: AliPmService
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/8 13:48
 */
public interface AliPmService {
    AliPm saveItem(AliPm content);

    AliPm findByCode(String code);

    AliPm updateContent(Response content);
}
