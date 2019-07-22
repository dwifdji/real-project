package com._360pai.core.facade.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com.github.pagehelper.PageInfo;

public interface PlatformArticleFacade {
    Object headline(TPlatformArticleTypeReq req);

    Object category(TPlatformArticleTypeReq req);

    PageInfo categoryList(TPlatformArticleReq req);

    Object detail(TPlatformArticleReq req);

    ResponseModel view(TPlatformArticleReq req);
}
