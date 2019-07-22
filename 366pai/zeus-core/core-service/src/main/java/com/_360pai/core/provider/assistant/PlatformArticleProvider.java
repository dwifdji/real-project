package com._360pai.core.provider.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.assistant.TPlatformArticleCondition;
import com._360pai.core.condition.assistant.TPlatformArticleTypeCondition;
import com._360pai.core.facade.assistant.PlatformArticleFacade;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.core.service.assistant.TPlatformArticleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: PlatformArticleProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/25 10:35
 */
@Component
@Service(version = "1.0.0")
public class PlatformArticleProvider implements PlatformArticleFacade {

    @Autowired
    private TPlatformArticleService tPlatformArticleService;

    @Override
    public Object headline(TPlatformArticleTypeReq req) {
        TPlatformArticleTypeCondition condition = new TPlatformArticleTypeCondition();
        condition.setStatus(TPlatformArticleTypeCondition.online); //上线状态
        condition.setType(req.getType());  //今日头条或者资产大买办
        return tPlatformArticleService.headline(req,condition);
    }

    @Override
    public Object category(TPlatformArticleTypeReq req) {
        if (req.getType() == 3) {
            req.setShowNews(0);
        }
        return tPlatformArticleService.category(req);
    }

    @Override
    public PageInfo categoryList(TPlatformArticleReq req) {
        TPlatformArticleCondition condition = new TPlatformArticleCondition();
        condition.setArticleTypeId(req.getArticleTypeId());
        condition.setStatus(TPlatformArticle.online);
        return tPlatformArticleService.categoryList(req.getPage(),req.getPerPage(),condition);
    }

    @Override
    public Object detail(TPlatformArticleReq req) {
        return tPlatformArticleService.platformArticleDetail(req.getId());
    }

    @Override
    public ResponseModel view(TPlatformArticleReq req) {
        return tPlatformArticleService.view(req);
    }
}
