package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.condition.assistant.THallCondition;
import com._360pai.core.dao.applet.TAppletHallActivityDao;
import com._360pai.core.dao.assistant.THallDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.assistant.req.AppletHallActivityReq;
import com._360pai.core.facade.assistant.vo.BackAppletHallActivity;
import com._360pai.core.facade.shop.vo.AppletHallActivityVO;
import com._360pai.core.model.applet.TAppletHallActivity;
import com._360pai.core.model.assistant.THall;
import com._360pai.core.service.assistant.THallService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zxiao
 * @Title: THallServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 13:03
 */
@Service
public class THallServiceImpl implements THallService {

    @Autowired
    private THallDao tHallDao;
    @Autowired
    private TAppletHallActivityDao appletHallActivityDao;

    @Override
    public PageInfo hallList(int page, int perPage, THall req) {
        PageHelper.startPage(page, perPage);
        THallCondition condition = new THallCondition();
        condition.setDelFlag(0);
        condition.setHallType(req.getHallType());
        List<THall> tHalls = tHallDao.selectList(condition);
        return new PageInfo<>(tHalls);
    }

    @Override
    public int addHall(THall params) {
        findByNameAndType(params);
        return tHallDao.insert(params);
    }

    @Override
    public int editHall(THall params) {
        THall hall = tHallDao.selectById(params.getId());
        if (hall == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        if (hall.getHallName().equals(params.getHallName())) {
            if (!hall.getHallType().equals(params.getHallType())) {
                findByNameAndType(params);
            }
        } else {
            findByNameAndType(params);
        }
        hall.setHallName(params.getHallName());
        hall.setHallType(params.getHallType());
        hall.setOrderNum(params.getOrderNum());
        return tHallDao.updateById(hall);
    }

    private void findByNameAndType(THall params) {
        THallCondition condition = new THallCondition();
        condition.setHallName(params.getHallName());
        condition.setHallType(params.getHallType());
        condition.setDelFlag(0);
        List<THall> tHalls = tHallDao.selectList(condition);
        if (!tHalls.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称重复");
        }
    }

    @Override
    public int deleteHall(THall params) {
        THall hall = tHallDao.selectById(params.getId());
        if (hall == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        hall.setDelFlag(1);
        return tHallDao.updateById(hall);
    }

    @Override
    public Object detailHall(Integer id) {
        return tHallDao.selectById(id);
    }

    @Override
    public PageInfoResp<BackAppletHallActivity> getAppletHallActivityList(AppletHallActivityReq.QueryReq req) {
        PageInfoResp<BackAppletHallActivity> resp = new PageInfoResp<>();
        List<BackAppletHallActivity> resultList = new ArrayList<>();
        PageInfo<BackAppletHallActivity> pageInfo = appletHallActivityDao.getBackList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (BackAppletHallActivity item : pageInfo.getList()) {
            resultList.add(item);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public Integer addAppletHallActivity(AppletHallActivityReq.AddReq req) {
        TAppletHallActivity hallActivity = appletHallActivityDao.findBy(req.getType(), req.getActivityId());
        if (hallActivity != null) {
            throw new BusinessException("活动已存在");
        }
        hallActivity = new TAppletHallActivity();
        hallActivity.setType(Integer.parseInt(req.getType()));
        hallActivity.setActivityId(req.getActivityId());
        hallActivity.setOrderNum(req.getOrderNum());
        int result = appletHallActivityDao.insert(hallActivity);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return hallActivity.getId();
    }

    @Override
    public Integer editAppletHallActivity(AppletHallActivityReq.EditReq req) {
        TAppletHallActivity hallActivity = appletHallActivityDao.selectById(req.getId());
        if (hallActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        hallActivity.setOrderNum(req.getOrderNum());
        int result = appletHallActivityDao.updateById(hallActivity);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return hallActivity.getId();
    }

    @Override
    public Integer deleteAppletHallActivity(AppletHallActivityReq.DeleteReq req) {
        TAppletHallActivity hallActivity = appletHallActivityDao.selectById(req.getId());
        if (hallActivity == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        hallActivity.setDeleteFlag(true);
        int result = appletHallActivityDao.updateById(hallActivity);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return hallActivity.getId();
    }
}
