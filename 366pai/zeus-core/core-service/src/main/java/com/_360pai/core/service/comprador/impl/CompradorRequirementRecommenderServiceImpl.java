package com._360pai.core.service.comprador.impl;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.CompradorEnum;
import com._360pai.core.condition.comprador.TCompradorRequirementCondition;
import com._360pai.core.condition.comprador.TCompradorRequirementRecommenderCondition;
import com._360pai.core.dao.comprador.TCompradorRequirementDao;
import com._360pai.core.dao.comprador.TCompradorRequirementRecommenderDao;
import com._360pai.core.facade.comprador.req.MyRequirementRecommenderListReq;
import com._360pai.core.facade.comprador.req.RequiementRecommenderRemarkReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.comprador.resp.CompradorDetailResp;
import com._360pai.core.facade.comprador.resp.MyRequirementRecommenderResp;
import com._360pai.core.facade.comprador.resp.RequirementRecommenderDetailResp;
import com._360pai.core.model.account.Account;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.model.comprador.TCompradorRequirementRecommender;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.comprador.CompradorRequirementRecommenderService;
import com._360pai.core.service.comprador.CompradorRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CompradorRequirementRecommenderServiceImpl implements CompradorRequirementRecommenderService {
    @Autowired
    private TCompradorRequirementRecommenderDao tCompradorRequirementRecommenderDao;
    @Autowired
    private AccountService                      accountService;
    @Autowired
    private TCompradorRequirementDao            tCompradorRequirementDao;
    @Autowired
    private CompradorRequirementService         compradorRequirementService;
    @Autowired
    private ServiceMessageUtils                 serviceMessageUtils;

    @Override
    public int requirementRecommenderAdd(TCompradorRequirementRecommender tCompradorRequirementRecommender) {
        TCompradorRequirementRecommenderCondition condition = new TCompradorRequirementRecommenderCondition();

        condition.setAccountId(tCompradorRequirementRecommender.getAccountId());
        condition.setPartyId(tCompradorRequirementRecommender.getPartyId());
        condition.setRequirementId(tCompradorRequirementRecommender.getRequirementId());

        TCompradorRequirementRecommender model = tCompradorRequirementRecommenderDao.selectFirst(condition);
        if (model != null) {
            return -1;
        }
        int count = tCompradorRequirementRecommenderDao.insert(tCompradorRequirementRecommender);
        // 发送邮件
        if (count > 0) {
            serviceMessageUtils.compradorRecommenderAdd(tCompradorRequirementRecommender.getRequirementId());
        }
        return count;
    }


    @Override
    public List<RequirementRecommenderDetailResp> requirementRecommenderList(TCompradorRequirementRecommenderCondition condition) {
        List<TCompradorRequirementRecommender> list   = tCompradorRequirementRecommenderDao.selectList(condition);
        List<RequirementRecommenderDetailResp> result = new ArrayList<>(list.size());
        RequirementRecommenderDetailResp       detailResp;
        for (TCompradorRequirementRecommender model : list) {
            detailResp = new RequirementRecommenderDetailResp();
            BeanUtils.copyProperties(model, detailResp);
            AccountBaseDto accountInfo = accountService.getAccountBaseByPartyId(model.getPartyId());
            detailResp.setContactName(accountInfo.getName());
            detailResp.setContactPhone(accountInfo.getMobile());
            result.add(detailResp);
        }
        return result;
    }

    @Override
    public int requirementRecommenderRemark(RequiementRecommenderRemarkReq req) {
        TCompradorRequirementRecommenderCondition condition = new TCompradorRequirementRecommenderCondition();
        condition.setId(req.getRequirementRecommenderId());
        TCompradorRequirementRecommender tCompradorRequirementRecommender = tCompradorRequirementRecommenderDao.selectOneResult(condition);
        int                              count                            = 0;
        if (tCompradorRequirementRecommender != null) {
            tCompradorRequirementRecommender.setRemark(req.getRemark());
            tCompradorRequirementRecommender.setOperatorId(req.getOperatorId().toString());
            tCompradorRequirementRecommender.setUpdateTime(new Date());
            count = tCompradorRequirementRecommenderDao.updateById(tCompradorRequirementRecommender);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int requirementRecommenderMatchSuccess(RequiementRecommenderRemarkReq req) {
        TCompradorRequirementRecommenderCondition condition = new TCompradorRequirementRecommenderCondition();
        condition.setId(req.getRequirementRecommenderId());
        TCompradorRequirementRecommender tCompradorRequirementRecommender = tCompradorRequirementRecommenderDao.selectOneResult(condition);
        int                              count                            = 0;
        if (tCompradorRequirementRecommender != null) {
            // 更新该记录状态，改成撮合成功
            tCompradorRequirementRecommender.setRecommenderStatus(CompradorEnum.RecommenderStatus.SUCCESS.getValue() + "");
            tCompradorRequirementRecommender.setUpdateTime(new Date());
            tCompradorRequirementRecommender.setOperatorId(req.getOperatorId().toString());
            count += tCompradorRequirementRecommenderDao.updateById(tCompradorRequirementRecommender);

            //更新其他记录的状态，改成已完成
            TCompradorRequirementRecommenderCondition condition2 = new TCompradorRequirementRecommenderCondition();
            condition2.setRequirementId(tCompradorRequirementRecommender.getRequirementId());
            List<TCompradorRequirementRecommender> list = tCompradorRequirementRecommenderDao.selectList(condition2);
            for (TCompradorRequirementRecommender requirementRecommender : list) {
                // 排除上面本身那条记录
                if (requirementRecommender.getId().equals(tCompradorRequirementRecommender.getId())) {
                    continue;
                }
                requirementRecommender.setRecommenderStatus(CompradorEnum.RecommenderStatus.FINISH.getValue() + "");
                requirementRecommender.setOperatorId(req.getOperatorId().toString());
                requirementRecommender.setUpdateTime(new Date());
                count += tCompradorRequirementRecommenderDao.updateById(requirementRecommender);
            }

            //更新主表状态 改成撮合成功
            TCompradorRequirementCondition condition3 = new TCompradorRequirementCondition();
            condition3.setId(tCompradorRequirementRecommender.getRequirementId());
            TCompradorRequirement tCompradorRequirement = tCompradorRequirementDao.selectFirst(condition3);
            if (tCompradorRequirement != null) {
                tCompradorRequirement.setRequirementStatus(CompradorEnum.RequirementStatus.SUCCESS.getValue() + "");
                tCompradorRequirement.setOperatorId(req.getOperatorId());
                tCompradorRequirement.setUpdateTime(new Date());
                count += tCompradorRequirementDao.updateById(tCompradorRequirement);
            }
        }
        return count;
    }

    @Override
    public PageUtils.Page myRequirementRecommenderList(MyRequirementRecommenderListReq req) {
        TCompradorRequirementRecommenderCondition condition = new TCompradorRequirementRecommenderCondition();
        condition.setPartyId(req.getPartyId());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TCompradorRequirementRecommender>     list       = tCompradorRequirementRecommenderDao.selectList(condition);
        PageInfo<TCompradorRequirementRecommender> pageInfo   = new PageInfo<>(list);
        List<TCompradorRequirementRecommender>     pageList   = pageInfo.getList();
        List<MyRequirementRecommenderResp>         resultList = new ArrayList<>();
        MyRequirementRecommenderResp               resp;
        for (TCompradorRequirementRecommender model : pageList) {
            resp = new MyRequirementRecommenderResp();
            BeanUtils.copyProperties(model, resp);
            TCompradorRequirementCondition condition1 = new TCompradorRequirementCondition();
            condition1.setId(model.getRequirementId());
            CompradorDetailResp requirementDetail = compradorRequirementService.getTCompradorRequirementDetail(model.getRequirementId(), null, true);
            resp.setRequirestDetail(requirementDetail);
            resultList.add(resp);
        }
        PageUtils.Page<MyRequirementRecommenderResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(resultList);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        return page;
    }

    @Override
    public boolean hasRecommend(Integer partyId, Integer requirementId) {
        try {
            if (null == partyId) {
                return false;
            }
            TCompradorRequirementRecommenderCondition condition = new TCompradorRequirementRecommenderCondition();
            condition.setPartyId(partyId);
            condition.setRequirementId(requirementId);
            condition.setIsDelete(false);
            List<TCompradorRequirementRecommender> recommenders
                    = tCompradorRequirementRecommenderDao.selectList(condition);
            return CollectionUtils.isNotEmpty(recommenders);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
