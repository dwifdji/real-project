package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.assistant.TPlatformArticleCondition;
import com._360pai.core.condition.assistant.TPlatformArticleTypeCondition;
import com._360pai.core.dao.assistant.TPlatformArticleDao;
import com._360pai.core.dao.assistant.TPlatformArticleTypeDao;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.core.model.assistant.TPlatformArticleType;
import com._360pai.core.service.assistant.TPlatformArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxiao
 * @Title: TPlatformArticleServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/25 10:37
 */
@Service
public class TPlatformArticleServiceImpl implements TPlatformArticleService {

    @Autowired
    private TPlatformArticleDao tPlatformArticleDao;
    @Autowired
    private TPlatformArticleTypeDao tPlatformArticleTypeDao;

    @Override
    public Object headline(TPlatformArticleTypeReq params, TPlatformArticleTypeCondition condition) {
        condition.setDelFlag(0);

        List<TPlatformArticleType> tPlatformArticleTypes = tPlatformArticleTypeDao.selectList(condition);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (TPlatformArticleType type : tPlatformArticleTypes) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("name", type.getArticleTitle());
            map.put("image", type.getImage());
            TPlatformArticleCondition condition1 = new TPlatformArticleCondition();
            condition1.setStatus(TPlatformArticle.online);
            condition1.setArticleTypeId(type.getId());
            condition1.setDelFlag(0);
            PageHelper.startPage(1, 3);
            PageHelper.orderBy("order_num asc");
            List<TPlatformArticle> tPlatformArticles = tPlatformArticleDao.selectList(condition1);
            map.put("article", tPlatformArticles);
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public Object category(TPlatformArticleTypeReq req) {
        TPlatformArticleTypeCondition condition = new TPlatformArticleTypeCondition();
        condition.setStatus(TPlatformArticleType.online);
        condition.setDelFlag(0);
        condition.setShowNews(req.getShowNews());
        PageHelper.orderBy("order_num asc");
        return tPlatformArticleTypeDao.selectList(condition);
    }

    @Override
    public PageInfo categoryList(int page, int perPage, TPlatformArticleCondition condition) {
        PageHelper.startPage(page, perPage);
        PageHelper.orderBy("order_num asc, public_at desc");
        condition.setDelFlag(0);
        List<TPlatformArticle> tPlatformArticles = tPlatformArticleDao.selectList(condition);
        return new PageInfo<>(tPlatformArticles);
    }

    @Override
    public Object platformArticleDetail(Integer id) {
        return tPlatformArticleDao.selectById(id);
    }

    @Override
    public ResponseModel view(TPlatformArticleReq req) {
        if (req.getId() != null) {
            tPlatformArticleDao.addViewCount(req.getId());
        }
        return ResponseModel.succ();
    }
}
