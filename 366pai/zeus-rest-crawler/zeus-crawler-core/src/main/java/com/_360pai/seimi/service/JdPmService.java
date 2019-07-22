package com._360pai.seimi.service;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.AliPm;
import com._360pai.seimi.model.JdPm;

/**
 * @author zxiao
 * @Title: AliPmService
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/8 13:48
 */
public interface JdPmService {
    JdPm saveItem(JdPm content);

    JdPm findByCode(String code);

    JdPm updateContent(Response content);

    void updateNum(Response response);

    void updateRemind(Response response);
}
