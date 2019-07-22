package com._360pai.core.service.comprador.impl;

import com._360pai.core.common.constants.CompradorEnum;
import com._360pai.core.condition.comprador.TCompradorRecommendCondition;
import com._360pai.core.dao.comprador.TCompradorRecommendDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.comprador.req.MyRecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendReq;
import com._360pai.core.facade.comprador.resp.RecommendDetailForAdminResp;
import com._360pai.core.facade.comprador.resp.RecommendDetailResp;
import com._360pai.core.model.comprador.TCompradorRecommend;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.comprador.CompradorRecommendService;
import com._360pai.core.utils.ServiceMessageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 15:29
 */
@Service
public class CompradorRecommendServiceImpl implements CompradorRecommendService {
    @Autowired
    private TCompradorRecommendDao tCompradorRecommendDao;
    @Autowired
    private AccountService         accountService;
    @Autowired
    private ServiceMessageUtils    serviceMessageUtils;

    @Override
    public int recommendAdd(TCompradorRecommend recommend) {
        int count = tCompradorRecommendDao.insert(recommend);

        // 发送邮件
        if (count > 0) {
            serviceMessageUtils.compradorRecommendAdd(recommend.getId());
        }

        return recommend.getId();
    }

    @Override
    public int recommendMatchSuccess(RecommendReq req) {
        TCompradorRecommend recommend = tCompradorRecommendDao.selectById(req.getRecommendId());
        int                 count     = 0;
        if (recommend != null) {
            recommend.setRecommendStatus(CompradorEnum.RecommendStatus.SUCCESS.getValue() + "");
            recommend.setOperatorId(req.getOperatorId().toString());
            recommend.setUpdateTime(new Date());
            count = tCompradorRecommendDao.updateById(recommend);
        }
        return count;
    }


    @Override
    public int recommendFinished(RecommendReq req) {
        TCompradorRecommend recommend = tCompradorRecommendDao.selectById(req.getRecommendId());
        int                 count     = 0;
        if (recommend != null) {
            recommend.setRecommendStatus(CompradorEnum.RecommendStatus.FINISH.getValue() + "");
            recommend.setOperatorId(req.getOperatorId().toString());
            recommend.setUpdateTime(new Date());
            count = tCompradorRecommendDao.updateById(recommend);
        }
        return count;
    }

    @Override
    public List<RecommendDetailResp> recommendList(TCompradorRecommendCondition condition) {
        List<TCompradorRecommend> list   = tCompradorRecommendDao.selectList(condition);
        List<RecommendDetailResp> result = new ArrayList<>(list.size());
        RecommendDetailResp       resp;
        for (TCompradorRecommend model : list) {
            resp = new RecommendDetailResp();
            BeanUtils.copyProperties(model, resp);
            result.add(resp);
        }
        return result;
    }

    @Override
    public List<RecommendDetailForAdminResp> recommendListForAdmin(TCompradorRecommendCondition condition) {
        List<TCompradorRecommend>         list   = tCompradorRecommendDao.selectList(condition);
        List<RecommendDetailForAdminResp> result = new ArrayList<>(list.size());
        RecommendDetailForAdminResp       resp;
        for (TCompradorRecommend model : list) {
            resp = new RecommendDetailForAdminResp();
            BeanUtils.copyProperties(model, resp);
            // 后台人员需要知道数据源头人的手机
            setSourceMobile(model, resp);
            result.add(resp);
        }
        return result;
    }

    @Override
    public List<RecommendDetailResp> myRecommendList(MyRecommendListReq req) {
        TCompradorRecommendCondition condition = new TCompradorRecommendCondition();
        condition.setPartyId(req.getPartyId());
        List<RecommendDetailResp> result = new ArrayList<>();
        RecommendDetailResp       resp;
        List<TCompradorRecommend> list   = tCompradorRecommendDao.selectList(condition);
        for (TCompradorRecommend model : list) {
            resp = new RecommendDetailResp();
            BeanUtils.copyProperties(model, resp);
            result.add(resp);
        }
        return result;
    }

    private void setSourceMobile(TCompradorRecommend model, RecommendDetailForAdminResp resp) {
        AccountBaseDto tAccount = accountService.getAccountBaseByPartyId(model.getPartyId());
        resp.setSourceMobile(tAccount == null ? "" : tAccount.getMobile());
        resp.setSourceName(tAccount == null ? "" : tAccount.getName());
    }

}
