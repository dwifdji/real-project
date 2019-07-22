package com._360pai.core.service.withfudig.impl;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.WithfudigEnum;
import com._360pai.core.condition.withfudig.TWithfudigRequirementCondition;
import com._360pai.core.condition.withfudig.TWithfudigRequirementInvestCondition;
import com._360pai.core.dao.account.TFundProviderDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementInvestDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementInvestReq;
import com._360pai.core.facade.withfudig.resp.MyRequirementInvestResp;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementInvestDetailResp;
import com._360pai.core.model.account.TFundProvider;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.core.model.withfudig.TWithfudigRequirementInvest;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.withfudig.WithfudigRequirementInvestService;
import com._360pai.core.service.withfudig.WithfudigRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * @date : 2018/9/6 16:24
 */
@Service
public class WithfudigRequirementInvestServiceImpl implements WithfudigRequirementInvestService {

    @Autowired
    private TWithfudigRequirementInvestDao tWithfudigRequirementInvestDao;
    @Autowired
    private TWithfudigRequirementDao       tWithfudigRequirementDao;
    @Autowired
    private WithfudigRequirementService    withfudigRequirementService;
    @Autowired
    private AccountService                 accountService;
    @Autowired
    private ServiceMessageUtils            serviceMessageUtils;
    @Autowired
    private TFundProviderDao               tFundProviderDao;

    @Override
    public List<WithfudigRequirementInvestDetailResp> requirementInvestList(TWithfudigRequirementInvestCondition condition) {

        List<TWithfudigRequirementInvest>          list   = tWithfudigRequirementInvestDao.selectList(condition);
        List<WithfudigRequirementInvestDetailResp> result = new ArrayList<>(list.size());
        WithfudigRequirementInvestDetailResp       detailResp;
        for (TWithfudigRequirementInvest model : list) {
            detailResp = new WithfudigRequirementInvestDetailResp();
            BeanUtils.copyProperties(model, detailResp);
            AccountBaseDto accountInfo = accountService.getAccountBaseByPartyId(model.getPartyId());
            TFundProvider byPartyId = tFundProviderDao.getByPartyId(model.getPartyId());
            detailResp.setContactName(byPartyId.getCompanyName());
            detailResp.setContactPhone(accountInfo.getMobile());
            result.add(detailResp);
        }
        return result;
    }

    @Override
    public PageUtils.Page myRequirementInvestList(WithfudigRequirementInvestReq.MyRequirementInvestList req) {
        TWithfudigRequirementInvestCondition condition = new TWithfudigRequirementInvestCondition();
        condition.setPartyId(req.getPartyId());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TWithfudigRequirementInvest>     list     = tWithfudigRequirementInvestDao.selectList(condition);
        PageInfo<TWithfudigRequirementInvest> pageInfo = new PageInfo<>(list);
        List<TWithfudigRequirementInvest>     pageList = pageInfo.getList();

        List<MyRequirementInvestResp> resultList = new ArrayList<>();
        MyRequirementInvestResp       resp;
        for (TWithfudigRequirementInvest model : pageList) {
            resp = new MyRequirementInvestResp();
            BeanUtils.copyProperties(model, resp);
            WithfudigRequirementDetailResp requirementDetail = withfudigRequirementService.getRequirementDetail(model.getRequirementId());
            resp.setRequirementDetail(requirementDetail);
            resultList.add(resp);
        }
        PageUtils.Page<MyRequirementInvestResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(resultList);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        page.setTotalPage(pageInfo.getPages());
        return page;
    }

    @Override
    public int requirementInvestMatchSuccess(WithfudigRequirementInvestReq.RequirementInvestRemark req) {
        TWithfudigRequirementInvestCondition condition = new TWithfudigRequirementInvestCondition();
        condition.setId(req.getRequirementInvestId());
        TWithfudigRequirementInvest tWithfudigRequirementInvest = tWithfudigRequirementInvestDao.selectOneResult(condition);
        int                         count                       = 0;
        if (tWithfudigRequirementInvest != null) {
            // 更新该记录状态，改成撮合成功
            tWithfudigRequirementInvest.setInvestStatus(WithfudigEnum.InvestStatus.SUCCESS.getValue() + "");
            tWithfudigRequirementInvest.setUpdateTime(new Date());
            tWithfudigRequirementInvest.setOperatorId(req.getOperatorId().toString());
            count += tWithfudigRequirementInvestDao.updateById(tWithfudigRequirementInvest);

            //更新其他记录的状态，改成已完成
            TWithfudigRequirementInvestCondition condition2 = new TWithfudigRequirementInvestCondition();
            condition2.setRequirementId(tWithfudigRequirementInvest.getRequirementId());
            List<TWithfudigRequirementInvest> list = tWithfudigRequirementInvestDao.selectList(condition2);
            for (TWithfudigRequirementInvest withfudigRequirementInvest : list) {
                // 排除上面本身那条记录
                if (withfudigRequirementInvest.getId().equals(tWithfudigRequirementInvest.getId())) {
                    continue;
                }
                withfudigRequirementInvest.setInvestStatus(WithfudigEnum.InvestStatus.FINISH.getValue() + "");
                withfudigRequirementInvest.setOperatorId(req.getOperatorId().toString());
                withfudigRequirementInvest.setUpdateTime(new Date());
                count += tWithfudigRequirementInvestDao.updateById(withfudigRequirementInvest);
            }

            //更新主表状态 改成撮合成功
            TWithfudigRequirementCondition condition3 = new TWithfudigRequirementCondition();
            condition3.setId(tWithfudigRequirementInvest.getRequirementId());
            TWithfudigRequirement tWithfudigRequirement = tWithfudigRequirementDao.selectFirst(condition3);
            if (tWithfudigRequirement != null) {
                tWithfudigRequirement.setRequirementStatus(WithfudigEnum.RequirementStatus.SUCCESS.getValue() + "");
                tWithfudigRequirement.setOperatorId(req.getOperatorId() + "");
                tWithfudigRequirement.setUpdateTime(new Date());
                count += tWithfudigRequirementDao.updateById(tWithfudigRequirement);
            }
        }
        return count;
    }

    @Override
    public int requirementInvestRemark(WithfudigRequirementInvestReq.RequirementInvestRemark req) {
        TWithfudigRequirementInvestCondition condition = new TWithfudigRequirementInvestCondition();
        condition.setId(req.getRequirementInvestId());
        TWithfudigRequirementInvest tWithfudigRequirementInvest = tWithfudigRequirementInvestDao.selectOneResult(condition);
        int                         count                       = 0;
        if (tWithfudigRequirementInvest != null) {
            tWithfudigRequirementInvest.setOperatorId(req.getOperatorId().toString());
            tWithfudigRequirementInvest.setUpdateTime(new Date());
            tWithfudigRequirementInvest.setRemark(req.getRemark());
            count = tWithfudigRequirementInvestDao.updateById(tWithfudigRequirementInvest);
        }
        return count;
    }

    @Override
    public int requirementInvestAdd(TWithfudigRequirementInvest tWithfudigRequirementInvest) {
        TWithfudigRequirementInvestCondition condition = new TWithfudigRequirementInvestCondition();

        condition.setAccountId(tWithfudigRequirementInvest.getAccountId());
        condition.setPartyId(tWithfudigRequirementInvest.getPartyId());
        condition.setRequirementId(tWithfudigRequirementInvest.getRequirementId());

        TWithfudigRequirementInvest model = tWithfudigRequirementInvestDao.selectFirst(condition);
        if (model != null) {
            return -1;
        }
        // 如果非平台的单子，初冾中，则改成补充资料
        TWithfudigRequirement tWithfudigRequirement = tWithfudigRequirementDao.selectById(tWithfudigRequirementInvest.getRequirementId());
        if (!tWithfudigRequirement.getIsPlatform()) {
            if (Integer.valueOf(tWithfudigRequirement.getRequirementStatus()).equals(WithfudigEnum.RequirementStatus.BEGINNING.getValue())) {
                tWithfudigRequirement.setRequirementStatus(WithfudigEnum.RequirementStatus.ADDITIONAL_MATERIALS.getValue() + "");
                tWithfudigRequirementDao.updateById(tWithfudigRequirement);
                //发送通知
                serviceMessageUtils.withfudigNotplatformInvest(tWithfudigRequirement.getId());
            }
            serviceMessageUtils.withfudigNotplatformInvestToOperator(tWithfudigRequirement.getId());
        }
        return tWithfudigRequirementInvestDao.insert(tWithfudigRequirementInvest);
    }


}
