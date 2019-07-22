package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.condition.assistant.TServiceFollowCondition;
import com._360pai.core.dao.assistant.TServiceFollowDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;
import com._360pai.core.facade.assistant.resp.ServiceFollowResp;
import com._360pai.core.model.assistant.TServiceFollow;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.TServiceFollowService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.service.withfudig.WithfudigRequirementService;
import com._360pai.core.utils.RespConvertUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/17 10:19
 */
@Service
public class TServiceFollowServiceImpl implements TServiceFollowService {

    @Autowired
    private TServiceFollowDao           tServiceFollowDao;
    @Autowired
    private AccountService              accountService;
    @Autowired
    private DisposalRequirementService  disposalRequirementService;
    @Autowired
    private WithfudigRequirementService withfudigRequirementService;

    @Override
    public int remove(ServiceFollowReq.Remove req) {
        int                     count     = 0;
        TServiceFollowCondition condition = new TServiceFollowCondition();
        condition.setRelativeId(Integer.valueOf(req.getRequirementId()));
        condition.setRelativeType(req.getRelativeType());
        condition.setPartyId(req.getPartyId());
        condition.setAccountId(req.getAccountId());
        TServiceFollow tServiceFollow = tServiceFollowDao.selectOneResult(condition);

        if (tServiceFollow != null) {
            tServiceFollow.setDelFlag(true);
            count += tServiceFollowDao.updateById(tServiceFollow);
        }
        return count;
    }

    @Override
    public int add(ServiceFollowReq.Add req) {
        int count = 0;

        TServiceFollowCondition condition = new TServiceFollowCondition();
        condition.setRelativeId(Integer.valueOf(req.getRequirementId()));
        condition.setRelativeType(req.getRelativeType());
        condition.setPartyId(req.getPartyId());
        condition.setAccountId(req.getAccountId());

        TServiceFollow tServiceFollow = tServiceFollowDao.selectOneResult(condition);
        if (tServiceFollow != null) {
            tServiceFollow.setDelFlag(false);
            count += tServiceFollowDao.updateById(tServiceFollow);
        } else {
            TServiceFollow model = new TServiceFollow();
            model.setAccountId(req.getAccountId());
            model.setPartyId(req.getPartyId());
            model.setRelativeId(Integer.valueOf(req.getRequirementId()));
            model.setRelativeType(req.getRelativeType());
            model.setDelFlag(false);
            model.setCreatedTime(new Date());
            count += tServiceFollowDao.insert(model);
        }
        return count;
    }

    @Override
    public PageUtils.Page list(ServiceFollowReq.List req) {
        TServiceFollowCondition condition = new TServiceFollowCondition();
        condition.setRelativeId(Integer.valueOf(req.getRequirementId()));
        condition.setRelativeType(req.getRelativeType());
        condition.setDelFlag(false);
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TServiceFollow>     list     = tServiceFollowDao.selectList(condition);
        PageInfo<TServiceFollow> pageInfo = new PageInfo<>(list);

        List<ServiceFollowResp> items = convertList(pageInfo.getList());

        PageUtils.Page<ServiceFollowResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(items);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        return page;
    }

    @Override
    public boolean followFlag(Integer relativeId, String relativeType, Integer partyId, Integer accountId) {
        TServiceFollowCondition condition = new TServiceFollowCondition();
        condition.setRelativeId(relativeId);
        condition.setRelativeType(relativeType);
        condition.setPartyId(partyId);
        condition.setAccountId(accountId);
        condition.setDelFlag(false);
        List<TServiceFollow> list = tServiceFollowDao.selectList(condition);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeList(ServiceFollowReq.Remove req) {
        if (req.getRelativeType().equals(ServiceFollowEnum.RelativeType.WITHFUDIG.getKey())) {
            return withfudigFollowRemove(req);
        } else if (req.getRelativeType().equals(ServiceFollowEnum.RelativeType.DIPOSAL.getKey())) {
            return disposalFollowRemove(req);
        }
        return 0;
    }

    @Override
    public Map<String, Integer> getFollowNumByAccountId(Integer accountId, Integer partyId) {
        ServiceFollowEnum.RelativeType[] relativeTypes = ServiceFollowEnum.RelativeType.values();
        Map<String, Integer>             result        = Maps.newHashMap();
        TServiceFollowCondition          condition;
        for (ServiceFollowEnum.RelativeType relativeType : relativeTypes) {
            condition = new TServiceFollowCondition();
            condition.setAccountId(accountId);
            condition.setPartyId(partyId);
            condition.setDelFlag(false);
            condition.setRelativeType(relativeType.getKey());
            List<TServiceFollow> list = tServiceFollowDao.selectList(condition);
            result.put(relativeType.getKey(), CollectionUtils.isEmpty(list) ? 0 : list.size());
        }
        return result;
    }

    @Override
    public int partyIdBind(Integer accountId, Integer partyId) {
        if (partyId != null) {
            TServiceFollowCondition condition = new TServiceFollowCondition();
            condition.setAccountId(accountId);
            condition.setDelFlag(false);
            List<TServiceFollow> tServiceFollows = tServiceFollowDao.selectList(condition);
            int count = 0;
            if (CollectionUtils.isNotEmpty(tServiceFollows)) {
                for (int i = 0, size = tServiceFollows.size(); i < size; i++) {
                    TServiceFollow tServiceFollow = tServiceFollows.get(i);
                    if (tServiceFollow.getPartyId() == null) {
                        tServiceFollow.setPartyId(partyId);
                        count += tServiceFollowDao.updateById(tServiceFollow);
                    }
                }
                return count;
            }
        }
        return 0;
    }

    private int withfudigFollowRemove(ServiceFollowReq.Remove req) {
        int count = 0;
        for (String tmp : req.getRequirementIds()) {
            req.setRequirementId(tmp);
            count += remove(req);
            withfudigRequirementService.updateFollowCount(tmp);
        }
        return count;
    }

    private int disposalFollowRemove(ServiceFollowReq.Remove req) {
        int count = 0;
        for (Integer tmp : req.getDisposalIds()) {
            req.setRequirementId(tmp.toString());
            count += remove(req);
        }
        if (count > 0) {
            disposalRequirementService.updateFollowCountList(req.getDisposalIds());
        }
        return count;
    }


    private List<ServiceFollowResp> convertList(List<TServiceFollow> list) {
        List<ServiceFollowResp> respList = new ArrayList<>(list.size());
        ServiceFollowResp       resp;
        for (TServiceFollow model : list) {
            resp = new ServiceFollowResp();
            convertSingle(model, resp);
            respList.add(resp);
        }
        return respList;
    }

    private void convertSingle(TServiceFollow model, ServiceFollowResp resp) {
        resp = RespConvertUtil.convert(model, resp);
        AccountBaseDto accountInfo = accountService.getAccountBaseByPartyId(model.getAccountId());
        resp.setContactName(accountInfo.getName());
        resp.setContactPhone(accountInfo.getMobile());
    }
}
