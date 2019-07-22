package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.TPlatformArticleTypeFacade;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.core.model.assistant.TPlatformArticleType;
import com._360pai.core.service.assistant.TPlatformArticleTypeService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: TPlatformArticleTypeProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 15:55
 */
@Component
@Service(version = "1.0.0")
public class TPlatformArticleTypeProvider implements TPlatformArticleTypeFacade {
    @Autowired
    private TPlatformArticleTypeService tPlatformArticleTypeService;

    @Override
    public PageInfo getTypes(TPlatformArticleTypeReq req) {
        return tPlatformArticleTypeService.getTypes(req);
    }

    @Override
    public int addType(TPlatformArticleTypeReq req) {
        return tPlatformArticleTypeService.addType(Copy(req));
    }

    @Override
    public int editType(TPlatformArticleTypeReq req) {
        return tPlatformArticleTypeService.editType(Copy(req));
    }

    @Override
    public int deleteType(TPlatformArticleTypeReq req) {
        return tPlatformArticleTypeService.deleteType(Copy(req));
    }

    @Override
    public PageInfo getArticles(TPlatformArticleReq req) {
        return tPlatformArticleTypeService.getArticles(req);
    }

    @Override
    public int addArticle(TPlatformArticleReq req) {
        return tPlatformArticleTypeService.addArticle(CopyArticle(req));
    }

    @Override
    public int editArticle(TPlatformArticleReq req) {
        return tPlatformArticleTypeService.editArticle(CopyArticle(req));
    }

    @Override
    public int deleteArticle(TPlatformArticleReq req) {
        return tPlatformArticleTypeService.deleteArticle(CopyArticle(req));
    }

    @Override
    public Object detailArticle(TPlatformArticleReq req) {
        return tPlatformArticleTypeService.detailArticle(CopyArticle(req));
    }

    @Override
    public Object detailType(TPlatformArticleTypeReq req) {
        return tPlatformArticleTypeService.detailType(req.getId());
    }

    private TPlatformArticleType Copy(TPlatformArticleTypeReq req) {
        TPlatformArticleType type = new TPlatformArticleType();
        BeanUtils.copyProperties(req, type);
        return type;
    }

    private TPlatformArticle CopyArticle(TPlatformArticleReq req) {
        TPlatformArticle type = new TPlatformArticle();
        BeanUtils.copyProperties(req, type);
        return type;
    }
}
