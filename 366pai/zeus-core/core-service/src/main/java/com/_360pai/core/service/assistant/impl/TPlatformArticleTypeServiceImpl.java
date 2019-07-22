package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.TPlatformArticleCondition;
import com._360pai.core.condition.assistant.TPlatformArticleTypeCondition;
import com._360pai.core.dao.assistant.TPlatformArticleDao;
import com._360pai.core.dao.assistant.TPlatformArticleTypeDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.core.model.assistant.TPlatformArticleType;
import com._360pai.core.service.assistant.TPlatformArticleTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zxiao
 * @Title: TPlatformArticleTypeServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 15:57
 */
@Service
public class TPlatformArticleTypeServiceImpl implements TPlatformArticleTypeService {

    @Autowired
    private TPlatformArticleTypeDao tPlatformArticleTypeDao;
    @Autowired
    private TPlatformArticleDao tPlatformArticleDao;

    @Override
    public PageInfo getTypes(TPlatformArticleTypeReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        PageHelper.orderBy("order_num asc");
        TPlatformArticleTypeCondition condition = new TPlatformArticleTypeCondition();
        condition.setDelFlag(0);
        List<TPlatformArticleType> tPlatformArticleTypes = tPlatformArticleTypeDao.selectList(condition);
        return new PageInfo<>(tPlatformArticleTypes);
    }

    @Override
    public int addType(TPlatformArticleType params) {
        findByTitleAndType(params);
        return tPlatformArticleTypeDao.insert(params);
    }

    @Override
    public int editType(TPlatformArticleType params) {
        TPlatformArticleType type = tPlatformArticleTypeDao.selectById(params.getId());
        if (type == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据已存在");
        }
        if (type.getArticleTitle().equals(params.getArticleTitle())) {
            if (!type.getType().equals(params.getType())) {
                findByTitleAndType(params);
            }
        } else {
            findByTitleAndType(params);
        }
        return tPlatformArticleTypeDao.updateById(params);
    }

    @Override
    public int deleteType(TPlatformArticleType params) {
        TPlatformArticleType type = tPlatformArticleTypeDao.selectById(params.getId());
        if (type == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据已存在");
        }
        type.setDelFlag(1);
        return tPlatformArticleTypeDao.updateById(type);
    }

    @Override
    public PageInfo getArticles(TPlatformArticleReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        PageHelper.orderBy("order_num asc");
        TPlatformArticleCondition condition = new TPlatformArticleCondition();
        condition.setArticleTypeId(req.getArticleTypeId());
        condition.setDelFlag(0);
        List<TPlatformArticle> tPlatformArticles = tPlatformArticleDao.selectList(condition);
        for (TPlatformArticle article : tPlatformArticles) {
            TPlatformArticleType type = tPlatformArticleTypeDao.selectById(article.getArticleTypeId());
            article.setArticleTypeName(type.getArticleTitle());
        }
        return new PageInfo<>(tPlatformArticles);
    }

    @Override
    public int addArticle(TPlatformArticle params) {
        findByNameAndType(params);
        return tPlatformArticleDao.insert(params);
    }

    @Override
    public int editArticle(TPlatformArticle params) {
        TPlatformArticle article = tPlatformArticleDao.selectById(params.getId());
        if (article == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        if (article.getArticleName().equals(params.getArticleName())) {
            if (!article.getArticleTypeId().equals(params.getArticleTypeId())) {
                findByNameAndType(params);
            }
        } else {
            findByNameAndType(params);
        }
        return tPlatformArticleDao.updateById(params);
    }

    @Override
    public int deleteArticle(TPlatformArticle params) {
        TPlatformArticle article = tPlatformArticleDao.selectById(params.getId());
        if (article == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        article.setDelFlag(1);
        return tPlatformArticleDao.updateById(article);
    }

    @Override
    public Object detailArticle(TPlatformArticle params) {
        TPlatformArticle article = tPlatformArticleDao.selectById(params.getId());
        if (article == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        return article;
    }

    @Override
    public Object detailType(Integer id) {
        return tPlatformArticleTypeDao.selectById(id);
    }

    private void findByTitleAndType(TPlatformArticleType params) {
        TPlatformArticleTypeCondition condition = new TPlatformArticleTypeCondition();
        condition.setArticleTitle(params.getArticleTitle());
        condition.setType(params.getType());
        condition.setDelFlag(0);
        List<TPlatformArticleType> tPlatformArticleTypes = tPlatformArticleTypeDao.selectList(condition);
        if (!tPlatformArticleTypes.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据已存在");
        }
    }

    private void findByNameAndType(TPlatformArticle params) {
        TPlatformArticleCondition condition = new TPlatformArticleCondition();
        condition.setArticleTypeId(params.getArticleTypeId());
        condition.setArticleName(params.getArticleName());
        List<TPlatformArticle> tPlatformArticles = tPlatformArticleDao.selectList(condition);
        if (!tPlatformArticles.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据已存在");
        }
    }
}
