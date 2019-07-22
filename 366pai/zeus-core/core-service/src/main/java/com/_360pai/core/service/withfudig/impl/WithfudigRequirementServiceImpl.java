package com._360pai.core.service.withfudig.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.assistant.TServiceFollowCondition;
import com._360pai.core.condition.withfudig.TWithfudigRequirementCondition;
import com._360pai.core.condition.withfudig.TWithfudigRequirementInvestCondition;
import com._360pai.core.dao.assistant.TServiceFollowDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementInvestDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.facade.withfudig.resp.MyRequirementDetailResp;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailForAdminResp;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com._360pai.core.model.assistant.TServiceFollow;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.core.model.withfudig.TWithfudigRequirementInvest;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.TBackstageOperationRecodService;
import com._360pai.core.service.assistant.TServiceFollowService;
import com._360pai.core.service.withfudig.WithfudigRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com._360pai.core.utils.ServicePayUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 16:23
 */
@Service
public class WithfudigRequirementServiceImpl implements WithfudigRequirementService {

    private final static String REQUIREMENT_AUDIT_PASS   = "配资乐审核通过";
    private final static String REQUIREMENT_AUDIT_NOPASS = "配资乐审核拒绝";

    @Autowired
    private TWithfudigRequirementDao        tWithfudigRequirementDao;
    @Autowired
    private TWithfudigRequirementInvestDao  tWithfudigRequirementInvestDao;
    @Autowired
    private TServiceFollowDao               tServiceFollowDao;
    @Autowired
    private TServiceFollowService           tServiceFollowService;
    @Autowired
    private AssetService                    assetService;
    @Autowired
    private ServiceMessageUtils             serviceMessageUtils;
    @Autowired
    private TBackstageOperationRecodService tBackstageOperationRecodService;
    @Autowired
    private ServicePayUtils                 servicePayUtils;
    @Autowired
    private AccountService                  accountService;
    @Autowired
    private GatewayMqSender                 mqSender;

    @Override
    public int requirementAdd(TWithfudigRequirement tWithfudigRequirement) {
        tWithfudigRequirementDao.insert(tWithfudigRequirement);
        return tWithfudigRequirement.getId();
    }

    @Override
    public PageUtils.Page selectListForPlatform(WithfudigRequirementReq.RequirementListForPlatform req) {
        TWithfudigRequirementCondition condition = new TWithfudigRequirementCondition();
        condition.setRequirementAmountFrom(req.getRequirementAmountFrom());
        condition.setRequirementAmountTo(req.getRequirementAmountTo());
        condition.setRequirementTermFrom(req.getRequirementTermFrom());
        condition.setRequirementTermTo(req.getRequirementTermTo());

        if ("1".equals(req.getTodayFlag())) {
            condition.setCreatedTime(new Date());
        }

        condition.setRequirementName(req.getQuery());

        // 处理排序字段
        if (StringUtils.isNotBlank(req.getOrderByInterestPercent())) {
            if (SystemConstant.DESC.equals(req.getOrderByInterestPercent())) {
                condition.setOrderBy("orderByInterestPercent_desc");
            } else if (SystemConstant.ASC.equals(req.getOrderByInterestPercent())) {
                condition.setOrderBy("orderByInterestPercent_asc");
            }
        }
        if (StringUtils.isNotBlank(req.getOrderByRequirementAmount())) {
            if (SystemConstant.DESC.equals(req.getOrderByRequirementAmount())) {
                condition.setOrderBy("orderByRequirementAmount_desc");
            } else if (SystemConstant.ASC.equals(req.getOrderByRequirementAmount())) {
                condition.setOrderBy("orderByRequirementAmount_asc");
            }
        }

        PageHelper.startPage(req.getPage(), req.getPerPage());
        PageInfo<TWithfudigRequirement> pageInfo  = tWithfudigRequirementDao.selectListForPlatform(req.getPage(), req.getPerPage(), condition);
        if ("1".equals(req.getTodayFlag()) && pageInfo.getList().size() == 0) {
            condition.setCreatedTime(null);
            pageInfo  = tWithfudigRequirementDao.selectListForPlatform(1, 10, condition);
            pageInfo.setHasNextPage(false);
            pageInfo.setTotal(pageInfo.getList().size());
        }
        List<TWithfudigRequirement> requirementList = pageInfo.getList();

        List<WithfudigRequirementDetailResp> detailRespList = new ArrayList<>();
        WithfudigRequirementDetailResp       resp;
        for (TWithfudigRequirement model : requirementList) {
            resp = new WithfudigRequirementDetailResp();
            resp = convert(model, resp);
            resp.setRequirementStatus(WithfudigEnum.RequirementStatus.WITHFUDIG_ING.getValue().toString());
            resp.setRequirementStatusDesc(WithfudigEnum.RequirementStatus.WITHFUDIG_ING.getKey());
            // 返回asset图片
            List<String> assetImages = new ArrayList<>();
            if (model.getAssetId() != null) {
                AssetVo assetById = assetService.getAssetById(model.getAssetId());
                if (assetById.getExtra() != null) {
                    assetImages = (List<String>) assetById.getExtra().get("images");
                }
            }
            if (resp.getAssetImages() == null)
                resp.setAssetImages(assetImages);
            detailRespList.add(resp);
        }
        PageUtils.Page<WithfudigRequirementDetailResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(detailRespList);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        page.setTotalPage(pageInfo.getPages());
        return page;
    }

    @Override
    public PageUtils.Page requirementFollowList(WithfudigRequirementReq.RequirementListForPlatform req) {
        tServiceFollowService.partyIdBind(req.getAccountId(), req.getPartyId());
        TWithfudigRequirementCondition condition = new TWithfudigRequirementCondition();
        condition.setRequirementAmountFrom(req.getRequirementAmountFrom());
        condition.setRequirementAmountTo(req.getRequirementAmountTo());
        condition.setRequirementTermFrom(req.getRequirementTermFrom());
        condition.setRequirementTermTo(req.getRequirementTermTo());

        // 处理排序字段
        if (StringUtils.isNotBlank(req.getOrderByInterestPercent())) {
            if (SystemConstant.DESC.equals(req.getOrderByInterestPercent())) {
                condition.setOrderByInterestPercentDesc("desc");
            }
            if (SystemConstant.ASC.equals(req.getOrderByInterestPercent())) {
                condition.setOrderByInterestPercentAsc("asc");
            }
        }
        if (StringUtils.isNotBlank(req.getOrderByRequirementAmount())) {
            condition.setOrderByRequirementAmount(req.getOrderByRequirementAmount());
        }
        condition.setPartyId(req.getPartyId());
        condition.setAccountId(req.getAccountId());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TWithfudigRequirement>     list     = tWithfudigRequirementDao.selectFollowListForPlatform(condition);
        PageInfo<TWithfudigRequirement> pageInfo = new PageInfo<>(list);

        List<TWithfudigRequirement> requirementList = pageInfo.getList();

        List<WithfudigRequirementDetailResp> detailRespList = new ArrayList<>();
        WithfudigRequirementDetailResp       resp;
        for (TWithfudigRequirement model : requirementList) {
            resp = new WithfudigRequirementDetailResp();
            resp = convert(model, resp);
            // 返回asset图片
            List<String> assetImages = new ArrayList<>();
            if (model.getAssetId() != null) {
                AssetVo assetById = assetService.getAssetById(model.getAssetId());
                if (assetById.getExtra() != null) {
                    assetImages = (List<String>) assetById.getExtra().get("images");
                }
            }
            if (resp.getAssetImages() == null)
                resp.setAssetImages(assetImages);
            resp.setRequirementStatusDesc(WithfudigEnum.RequirementStatus.getKeyByValue(Integer.valueOf(model.getRequirementStatus())));
            detailRespList.add(resp);
        }
        PageUtils.Page<WithfudigRequirementDetailResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(detailRespList);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        page.setTotalPage(pageInfo.getPages());
        return page;
    }

    @Override
    public PageUtils.Page requirementListForAdmin(WithfudigRequirementReq.RequirementListForAdmin req) {
        TWithfudigRequirementCondition condition = new TWithfudigRequirementCondition();

        condition.setRequirementStatus(req.getRequirementStatus());
        condition.setRequirementName(req.getRequirementName());

        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TWithfudigRequirement>     list            = tWithfudigRequirementDao.selectList(condition);
        PageInfo<TWithfudigRequirement> pageInfo        = new PageInfo<>(list);
        List<TWithfudigRequirement>     requirementList = pageInfo.getList();

        List<WithfudigRequirementDetailForAdminResp> items = new ArrayList<>(req.getPerPage());

        WithfudigRequirementDetailForAdminResp resp;
        for (TWithfudigRequirement model : requirementList) {
            resp = new WithfudigRequirementDetailForAdminResp();
            items.add(convert(model, resp));
        }
        PageUtils.Page<WithfudigRequirementDetailForAdminResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(items);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        page.setTotalPage(pageInfo.getPages());
        return page;
    }

    @Override
    public WithfudigRequirementDetailResp requirementDetail(WithfudigRequirementReq.RequirementDetailReq req, Boolean isAdmin) {
        tServiceFollowService.partyIdBind(req.getAccountId(), req.getPartyId());
        TWithfudigRequirementCondition condition = new TWithfudigRequirementCondition();
        condition.setId(req.getRequirementId());
        TWithfudigRequirement          model = tWithfudigRequirementDao.selectFirst(condition);
        WithfudigRequirementDetailResp resp  = new WithfudigRequirementDetailResp();
        if (!isAdmin) {
            model.setViewNum(model.getViewNum() + 1);
        }
        tWithfudigRequirementDao.updateById(model);
        convertNotFormat(model, resp);
        resp.setInvestFlag(isInvest(req.getAccountId(), req.getPartyId(), req.getRequirementId()));
        resp.setFollowFlag(tServiceFollowService.followFlag(req.getRequirementId(), ServiceFollowEnum.RelativeType.WITHFUDIG.getKey(), req.getPartyId(), req.getAccountId()));
        if (model.getExtra() != null)
            resp.setAssetImages(model.getExtra().getJSONArray("image").toJavaList(String.class));
        // 返回asset图片
        List<String> assetImages = new ArrayList<>();
        if (model.getAssetId() != null) {
            AssetVo assetById = assetService.getAssetById(model.getAssetId());
            if (assetById.getExtra() != null) {
                assetImages = (List<String>) assetById.getExtra().get("images");
            }
        }
        if (resp.getAssetImages() == null) resp.setAssetImages(assetImages);
        return resp;
    }

    @Override
    public int updateRequirementStatus(WithfudigRequirementReq.RequirementStatusUpdate req) {
        // 先取出operatorId 查询出数据，然后再赋值给需要更新的方法
        TWithfudigRequirementCondition condition = new TWithfudigRequirementCondition();
        condition.setId(req.getRequirementId());
        TWithfudigRequirement model = tWithfudigRequirementDao.selectOneResult(condition);
        if (model != null) {
            model.setRequirementStatus(req.getStatus());
            model.setUpdateTime(new Date());
            model.setOperatorId(req.getOperatorId() + "");
            model.setRemark(req.getRemark());
            model.setUpdateTime(new Date());
            if (WithfudigEnum.RequirementStatus.PASS_FOR_PAY.getValue().toString().equals(req.getStatus())) {
                model.setRemark(" ");
                Boolean noNeedPay = servicePayUtils.noNeedPay(model.getPartyId(), ServiceConfigEnum.WITHFUDIG_REQUIREMENT_PRICE, ServiceOrderEnum.OrderType.WITHFUDIG_REQUIREMENT);
                if (noNeedPay) {
                    if (model.getIsPlatform()) {
                        model.setRequirementStatus(WithfudigEnum.RequirementStatus.WITHFUDIG_ING.getValue().toString());
                    } else {
                        model.setRequirementStatus(WithfudigEnum.RequirementStatus.BEGINNING.getValue().toString());
                    }
                } else {
                    setWithfudigPayExpired(model.getId());
                }
                int count = tWithfudigRequirementDao.updateById(model);
                // 发送消息
                if (count > 0) {
                    tBackstageOperationRecodService.insertRecode(BackstageOperationEnum.Type.WITHFUDIG_AUDIT, model.getId().longValue(), REQUIREMENT_AUDIT_PASS, req.getOperatorId());
                    serviceMessageUtils.withfudigRequirementAudit(model.getId());
                }
            }
            // 发送补充资料审核通过 ，修改为配资中 消息 非平台单才有
            if (WithfudigEnum.RequirementStatus.WITHFUDIG_ING.getValue().toString().equals(req.getStatus()) && !model.getIsPlatform()) {
                serviceMessageUtils.withfudigSupplementalInformationAudit(model.getId());
            }
            return tWithfudigRequirementDao.updateById(model);
        }
        return 0;
    }

    @Override
    public int requirementFinished(WithfudigRequirementReq.RequirementId req) {
        TWithfudigRequirementCondition condition = new TWithfudigRequirementCondition();
        condition.setId(req.getRequirementId());
        TWithfudigRequirement model = tWithfudigRequirementDao.selectFirst(condition);

        int count = 0;
        if (model != null) {
            model.setRequirementStatus(WithfudigEnum.RequirementStatus.FINISH.getValue() + "");
            model.setOperatorId(req.getOperatorId() + "");
            model.setUpdateTime(new Date());
            count += tWithfudigRequirementDao.updateById(model);

            //更新其他记录的状态，改成已完成
            TWithfudigRequirementInvestCondition condition2 = new TWithfudigRequirementInvestCondition();
            condition2.setRequirementId(model.getId());
            List<TWithfudigRequirementInvest> list = tWithfudigRequirementInvestDao.selectList(condition2);
            for (TWithfudigRequirementInvest childModel : list) {
                childModel.setInvestStatus(WithfudigEnum.InvestStatus.FINISH.getValue() + "");
                childModel.setOperatorId(req.getOperatorId().toString());
                childModel.setUpdateTime(new Date());
                count += tWithfudigRequirementInvestDao.updateById(childModel);
            }
        }
        return count;
    }

    @Override
    public List<MyRequirementDetailResp> myRequirementList(WithfudigRequirementReq.MyRequirementList req) {
        TWithfudigRequirementCondition condition = new TWithfudigRequirementCondition();
        condition.setPartyId(req.getPartyId());
        List<MyRequirementDetailResp> result = new ArrayList<>();
        MyRequirementDetailResp       resp;
        List<TWithfudigRequirement>   list   = tWithfudigRequirementDao.myRequirementList(condition);
        for (TWithfudigRequirement model : list) {
            resp = new MyRequirementDetailResp();
            resp.setPayDeadlineTimestamp(tBackstageOperationRecodService.getPayDeadlineTimestamp(BackstageOperationEnum.Type.WITHFUDIG_AUDIT, model.getId().longValue(), REQUIREMENT_AUDIT_PASS));
            result.add(convert(model, resp));
        }
        return result;
    }

    @Override
    public WithfudigRequirementDetailResp getRequirementDetail(Integer requirementId) {
        TWithfudigRequirement model = tWithfudigRequirementDao.selectById(requirementId);
        // 返回asset图片
        WithfudigRequirementDetailResp resp = new WithfudigRequirementDetailResp();
        resp = convert(model, resp);
        List<String> assetImages = new ArrayList<>();
        if (model.getAssetId() != null) {
            AssetVo assetById = assetService.getAssetById(model.getAssetId());
            if (assetById.getExtra() != null) {
                assetImages = (List<String>) assetById.getExtra().get("images");
            }
        }
        if (resp.getAssetImages() == null)
            resp.setAssetImages(assetImages);
        return resp;
    }

    @Override
    public int requirementRelateAssertId(WithfudigRequirementReq.RequirementRelateAssertId req) {

        TWithfudigRequirement model = tWithfudigRequirementDao.selectById(req.getRequirementId());
        int                   count = 0;
        if (model != null) {
            model.setAssetId(req.getAssetId());
            model.setUpdateTime(new Date());
            model.setRequirementStatus(WithfudigEnum.RequirementStatus.WAITING_PASS_TWICE.getValue() + "");
            count = tWithfudigRequirementDao.updateById(model);
        }
        return count;
    }

    @Override
    public int requirementUpdate(WithfudigRequirementReq.RequirementUpdate req) {
        TWithfudigRequirement model = new TWithfudigRequirement();
        BeanUtils.copyProperties(req, model);
        model.setId(req.getRequirementId());
        model.setCreatedTime(new Date());
        model.setRequirementStatus(WithfudigEnum.RequirementStatus.WAITING_PASS.getValue() + "");
        return tWithfudigRequirementDao.updateById(model);
    }

    @Override
    public int updateFollowCount(String requirementId) {
        TServiceFollowCondition condition = new TServiceFollowCondition();
        condition.setDelFlag(false);
        condition.setRelativeType(ServiceFollowEnum.RelativeType.WITHFUDIG.getKey());
        condition.setRelativeId(Integer.valueOf(requirementId));

        List<TServiceFollow> list = tServiceFollowDao.selectList(condition);

        TWithfudigRequirement model = tWithfudigRequirementDao.selectById(requirementId);

        int count = 0;
        if (model != null) {
            model.setFollowNum(list.size());
            count += tWithfudigRequirementDao.updateById(model);
        }
        return count;
    }

    @Override
    public int specialNoticeUpdate(WithfudigRequirementReq.SpecialNoticeUpdate req) {
        TWithfudigRequirement model = tWithfudigRequirementDao.selectById(req.getRequirementId());
        model.setSpecialNotice(req.getSpecialNotice());
        model.setOperatorId(req.getOperatorId().toString());
        return tWithfudigRequirementDao.updateById(model);
    }

    private void setWithfudigPayExpired(Integer id) {
        mqSender.withfudigPayExpiredEnqueue(id + "", servicePayUtils.getServicePayExpiredTime() * 60);
    }


    private Integer getInvestCount(Integer requirementId) {
        return tWithfudigRequirementInvestDao.getCountByRequirementId(requirementId);
    }

    private WithfudigRequirementDetailResp convert(TWithfudigRequirement tWithfudigRequirement, WithfudigRequirementDetailResp resp) {
        BeanUtils.copyProperties(tWithfudigRequirement, resp);
        resp.setRequirementStatusDesc(WithfudigEnum.RequirementStatus.getKeyByValue(Integer.valueOf(resp.getRequirementStatus())));
        resp.setInvestNum(getInvestCount(tWithfudigRequirement.getId()));
        resp.setAssetPrice(tWithfudigRequirement.getAssetPrice() == null? null: tWithfudigRequirement.getAssetPrice().toString());
        resp.setRequirementAmount(tWithfudigRequirement.getRequirementAmount() == null? null: tWithfudigRequirement.getRequirementAmount().toString());
        if (tWithfudigRequirement.getExtra() != null)
            resp.setAssetImages(tWithfudigRequirement.getExtra().getJSONArray("image").toJavaList(String.class));
        return resp;
    }

    private WithfudigRequirementDetailForAdminResp convert(TWithfudigRequirement tWithfudigRequirement, WithfudigRequirementDetailForAdminResp resp) {
        BeanUtils.copyProperties(tWithfudigRequirement, resp);
        resp.setRequirementStatusDesc(WithfudigEnum.RequirementStatus.getKeyByValue(Integer.valueOf(resp.getRequirementStatus())));
        resp.setInvestNum(getInvestCount(tWithfudigRequirement.getId()));
        resp.setAssetPrice(tWithfudigRequirement.getAssetPrice() == null ? null: tWithfudigRequirement.getAssetPrice().toString());
        resp.setRequirementAmount(tWithfudigRequirement.getRequirementAmount() == null? null:tWithfudigRequirement.getRequirementAmount().toString());

        AccountBaseDto tAccount = accountService.getAccountBaseByPartyId(tWithfudigRequirement.getPartyId());
        resp.setSourceMobile(tAccount == null ? "" : tAccount.getMobile());
        resp.setSourceName(tAccount == null ? "" : tAccount.getName());
        return resp;
    }

    private WithfudigRequirementDetailResp convertNotFormat(TWithfudigRequirement tWithfudigRequirement, WithfudigRequirementDetailResp resp) {
        BeanUtils.copyProperties(tWithfudigRequirement, resp);
        resp.setRequirementStatusDesc(WithfudigEnum.RequirementStatus.getKeyByValue(Integer.valueOf(resp.getRequirementStatus())));
        resp.setInvestNum(getInvestCount(tWithfudigRequirement.getId()));
        resp.setAssetPrice(tWithfudigRequirement.getAssetPrice().toPlainString());
        resp.setAssetPriceShow(tWithfudigRequirement.getAssetPrice() == null? null: tWithfudigRequirement.getAssetPrice().toString());
        resp.setRequirementAmount(tWithfudigRequirement.getRequirementAmount() == null ? "0" : tWithfudigRequirement.getRequirementAmount().toPlainString());
        resp.setRequirementAmountShow(tWithfudigRequirement.getRequirementAmount() == null ? null:tWithfudigRequirement.getRequirementAmount().toString());
        return resp;
    }

    private MyRequirementDetailResp convert(TWithfudigRequirement tWithfudigRequirement, MyRequirementDetailResp resp) {
        BeanUtils.copyProperties(tWithfudigRequirement, resp);
        resp.setRequirementStatusDesc(WithfudigEnum.RequirementStatus.getKeyByValue(Integer.valueOf(resp.getRequirementStatus())));
        resp.setInvestNum(getInvestCount(tWithfudigRequirement.getId()));
        resp.setAssetPrice(tWithfudigRequirement.getAssetPrice() == null ? null:tWithfudigRequirement.getAssetPrice().toString());
        resp.setRequirementAmount(tWithfudigRequirement.getRequirementAmount() == null ? null : tWithfudigRequirement.getRequirementAmount().toString());
        return resp;
    }

    private boolean isInvest(Integer accountId, Integer partyId, Integer requirementId) {
        TWithfudigRequirementInvestCondition condition = new TWithfudigRequirementInvestCondition();
        condition.setAccountId(accountId);
        condition.setPartyId(partyId);
        condition.setRequirementId(requirementId);
        TWithfudigRequirementInvest model = tWithfudigRequirementInvestDao.selectFirst(condition);
        return model != null;
    }

}
