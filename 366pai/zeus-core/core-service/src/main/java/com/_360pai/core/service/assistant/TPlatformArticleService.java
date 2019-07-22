package com._360pai.core.service.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.assistant.TPlatformArticleCondition;
import com._360pai.core.condition.assistant.TPlatformArticleTypeCondition;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com.github.pagehelper.PageInfo;

public interface TPlatformArticleService {
    Object headline(TPlatformArticleTypeReq params, TPlatformArticleTypeCondition condition);

    Object category(TPlatformArticleTypeReq req);

    PageInfo categoryList(int page, int perPage, TPlatformArticleCondition condition);

    Object platformArticleDetail(Integer id);

    ResponseModel view(TPlatformArticleReq req);
}
