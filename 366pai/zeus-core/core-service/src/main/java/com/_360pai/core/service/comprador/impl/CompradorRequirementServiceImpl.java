package com._360pai.core.service.comprador.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.comprador.TCompradorRequirementCondition;
import com._360pai.core.condition.comprador.TCompradorRequirementRecommenderCondition;
import com._360pai.core.dao.comprador.TCompradorRequirementDao;
import com._360pai.core.dao.comprador.TCompradorRequirementRecommenderDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.comprador.req.*;
import com._360pai.core.facade.comprador.resp.CompradorDetailForAdminResp;
import com._360pai.core.facade.comprador.resp.CompradorDetailResp;
import com._360pai.core.facade.comprador.resp.CompradorListByBuildingTypeResp;
import com._360pai.core.facade.comprador.resp.MyRequirementDetailResp;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.model.comprador.TCompradorRequirementRecommender;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.TBackstageOperationRecodService;
import com._360pai.core.service.comprador.CompradorRequirementRecommenderService;
import com._360pai.core.service.comprador.CompradorRequirementService;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.core.utils.ServiceMessageUtils;
import com._360pai.core.utils.ServicePayUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述 资产大买办 实现类
 *
 * @author : whisky_vip
 * @date : 2018/8/31 11:24
 */
@Service
public class CompradorRequirementServiceImpl implements CompradorRequirementService {

    private final static String REQUIREMENT_AUDIT_PASS   = "大买办审核通过";
    private final static String REQUIREMENT_AUDIT_NOPASS = "大买办审核拒绝";

    @Autowired
    private TCompradorRequirementDao               tCompradorRequirementDao;
    @Autowired
    private TCompradorRequirementRecommenderDao    tCompradorRequirementRecommenderDao;
    @Autowired
    private ServiceMessageUtils                    serviceMessageUtils;
    @Autowired
    private ServicePayUtils                        servicePayUtils;
    @Autowired
    private TBackstageOperationRecodService        tBackstageOperationRecodService;
    @Autowired
    private AccountService                         accountService;
    @Autowired
    private CompradorRequirementRecommenderService compradorRequirementRecommenderService;
    @Autowired
    private GatewayMqSender                        mqSender;

    @Override
    public int requirementAuditNoPass(CompradorRequirementReq.RequirementStatusUpdate req) {
        // 先取出operatorId 查询出数据，然后再赋值给需要更新的方法
        TCompradorRequirementCondition condition = new TCompradorRequirementCondition();
        condition.setId(req.getRequirementId());
        TCompradorRequirement tCompradorRequirement = tCompradorRequirementDao.selectOneResult(condition);
        if (tCompradorRequirement != null) {
            tCompradorRequirement.setRequirementStatus(req.getStatus());
            tCompradorRequirement.setUpdateTime(new Date());
            tCompradorRequirement.setOperatorId(req.getOperatorId());
            tCompradorRequirement.setRemark(req.getRemark());
            return tCompradorRequirementDao.updateById(tCompradorRequirement);
        }
        return 0;
    }

    @Override
    public int requirementAuditPass(CompradorRequirementReq.RequirementStatusUpdate req) {
        // 先取出operatorId 查询出数据，然后再赋值给需要更新的方法
        TCompradorRequirementCondition condition = new TCompradorRequirementCondition();
        condition.setId(req.getRequirementId());
        TCompradorRequirement tCompradorRequirement = tCompradorRequirementDao.selectOneResult(condition);
        if (tCompradorRequirement != null) {
            tCompradorRequirement.setRequirementStatus(req.getStatus());
            tCompradorRequirement.setUpdateTime(new Date());
            tCompradorRequirement.setOperatorId(req.getOperatorId());
            tCompradorRequirement.setRemark(req.getRemark());
            if (CompradorEnum.RequirementStatus.PASS_FOR_PAY.getValue().toString().equals(req.getStatus())) {
                // 判断是否需要支付，不需要，则直接改成platform展示状态
                Boolean noNeedPay = servicePayUtils.noNeedPay(tCompradorRequirement.getPartyId(), ServiceConfigEnum.COMPRADOR_REQUIREMENT_PRICE, ServiceOrderEnum.OrderType.COMPRADOR_REQUIREMENT);
                if (noNeedPay) {
                    tCompradorRequirement.setRequirementStatus(CompradorEnum.RequirementStatus.PLACEMENT.getValue().toString());
                } else {
                    setCompradorPayExpired(tCompradorRequirement.getId());
                }
                tCompradorRequirement.setRemark(null);
                int count = tCompradorRequirementDao.updateById(tCompradorRequirement);
                // 发送消息
                if (count > 0) {
                    tBackstageOperationRecodService.insertRecode(BackstageOperationEnum.Type.COMPRADOR_AUDIT, tCompradorRequirement.getId().longValue(), REQUIREMENT_AUDIT_PASS, req.getOperatorId());
                    serviceMessageUtils.compradorRequirementAudit(tCompradorRequirement.getId());
                }
            }
            return tCompradorRequirementDao.updateById(tCompradorRequirement);
        }
        return 0;
    }

    @Override
    public int requirementAdd(TCompradorRequirement tCompradorRequirement) {
        tCompradorRequirementDao.insert(tCompradorRequirement);
        return tCompradorRequirement.getId();
    }

    @Override
    public int requirementUpdate(CompradorRequirementReq.RequirementUpdate req) {
        TCompradorRequirement requirement = tCompradorRequirementDao.selectById(req.getRequirementId());
        if (requirement == null) {
            return 0;
        }
        TCompradorRequirement tCompradorRequirement = new TCompradorRequirement();
        BeanUtils.copyProperties(req, tCompradorRequirement);
        tCompradorRequirement.setId(req.getRequirementId());
        tCompradorRequirement.setUpdateTime(new Date());
        // 如果是审核不通过状态，修改之后，改成待审核状态
        if (CompradorEnum.RequirementStatus.NO_PASS.getValue().toString().equals(requirement.getRequirementStatus())
                ||CompradorEnum.RequirementStatus.PASS_FOR_PAY.getValue().toString().equals(requirement.getRequirementStatus())
                ||CompradorEnum.RequirementStatus.PAY_EXPIRED.getValue().toString().equals(requirement.getRequirementStatus())) {
            tCompradorRequirement.setRequirementStatus(CompradorEnum.RequirementStatus.WAITING_PASS.getValue().toString());
        }
        return tCompradorRequirementDao.updateById(tCompradorRequirement);
    }

    @Override
    public CompradorDetailResp getTCompradorRequirementDetail(Integer requirementId, Integer partyId, Boolean isAdmin) {
        TCompradorRequirementCondition condition = new TCompradorRequirementCondition();
        condition.setId(requirementId);
        TCompradorRequirement tCompradorRequirement = tCompradorRequirementDao.selectFirst(condition);
        CompradorDetailResp   resp                  = new CompradorDetailResp();
        if (tCompradorRequirement != null) {
            convertSingle(tCompradorRequirement, resp);
            resp.setRecommendFlag(compradorRequirementRecommenderService.hasRecommend(partyId, tCompradorRequirement.getId()));
            if (!isAdmin) {
                tCompradorRequirement.setViewNum(tCompradorRequirement.getViewNum() + 1);
                tCompradorRequirementDao.updateById(tCompradorRequirement);
            }
        }

        return resp;
    }

    @Override
    public PageUtils.Page requirementListForPlatform(RequirementListForPlatformReq req) {
        TCompradorRequirementCondition condition = new TCompradorRequirementCondition();
        condition.setCityId(req.getCityId());
        condition.setBuildingType(req.getBuildingType());

        condition.setAreaFrom(req.getAreaFrom());
        condition.setAreaTo(req.getAreaTo());

        condition.setPriceFrom(req.getPriceFrom());
        condition.setPriceTo(req.getPriceTo());

        // 排序字段处理
        if (StringUtils.isNotBlank(req.getOrderByArea())) {
            if (SystemConstant.DESC.equals(req.getOrderByArea())) {
                condition.setOrderByAreaDesc("desc");
            }
            if (SystemConstant.ASC.equals(req.getOrderByArea())) {
                condition.setOrderByAreaAsc("asc");
            }
        }
        if (StringUtils.isNotBlank(req.getOrderByPrice())) {
            if (SystemConstant.DESC.equals(req.getOrderByPrice())) {
                condition.setOrderByPriceDesc("desc");
            }
            if (SystemConstant.ASC.equals(req.getOrderByPrice())) {
                condition.setOrderByPriceAsc("asc");
            }
        }


        // 分页查询
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TCompradorRequirement>     list     = tCompradorRequirementDao.selectListForPlatform(condition);
        PageInfo<TCompradorRequirement> pageInfo = new PageInfo<>(list);

        //数据转换成返回resp
        List<CompradorDetailResp> detailRespList = convertList(pageInfo.getList());

        PageUtils.Page<CompradorDetailResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(detailRespList);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        page.setTotalPage(pageInfo.getPages());

        return page;
    }


    @Override
    public PageUtils.Page requirementListForAdmin(RequirementAdminQueryRep req) {
        TCompradorRequirementCondition condition = new TCompradorRequirementCondition();

        condition.setBuildingType(req.getBuildingType());
        condition.setRequirementStatus(req.getRequirementStatus());
        condition.setRequirementName(req.getRequirementName());

        PageHelper.startPage(req.getPage(), req.getPerPage());

        List<TCompradorRequirement>     list            = tCompradorRequirementDao.selectList(condition);
        PageInfo<TCompradorRequirement> pageInfo        = new PageInfo<>(list);
        List<TCompradorRequirement>     requirementList = pageInfo.getList();

        List<CompradorDetailForAdminResp> items = new ArrayList<>(req.getPerPage());

        CompradorDetailForAdminResp resp;
        for (TCompradorRequirement model : requirementList) {
            resp = new CompradorDetailForAdminResp();
            convertSingle(model, resp);
            items.add(resp);
        }
        PageUtils.Page<CompradorDetailForAdminResp> page = new PageUtils.Page<>();
        page.setTotal((int) pageInfo.getTotal());
        page.setList(items);
        page.setPerPage(req.getPerPage());
        page.setPage(req.getPage());
        page.setTotalPage(pageInfo.getPages());
        return page;
    }

    @Override
    public List<CompradorListByBuildingTypeResp> requirementListByBuildingType() {
        TCompradorRequirementCondition        condition = new TCompradorRequirementCondition();
        List<CompradorListByBuildingTypeResp> result    = new ArrayList<>(CompradorEnum.RequirementBuildingType.values().length);
        CompradorListByBuildingTypeResp       byBuildingTypeResp;
        for (CompradorEnum.RequirementBuildingType buildingType : CompradorEnum.RequirementBuildingType.values()) {
            condition.setBuildingType(buildingType.getValue() + "");
            List<TCompradorRequirement>                              list                = tCompradorRequirementDao.selectList(condition);
            List<CompradorListByBuildingTypeResp.ListByBuildingType> listByBuildingTypes = new ArrayList<>();
            byBuildingTypeResp = new CompradorListByBuildingTypeResp();
            byBuildingTypeResp.setBuildingType(buildingType.getValue() + "");
            CompradorListByBuildingTypeResp.ListByBuildingType listByBuildingType;
            if (CollectionUtils.isNotEmpty(list)) {
                listByBuildingTypes = new ArrayList<>(5);
                for (TCompradorRequirement model : list) {
                    listByBuildingType = new CompradorListByBuildingTypeResp.ListByBuildingType();
                    if (listByBuildingTypes.size() == 5) {
                        break;
                    }
                    // 只放 置办中的数据
                    if (CompradorEnum.RequirementStatus.PLACEMENT.getValue().toString().equals(model.getRequirementStatus())) {
                        convert(model, listByBuildingType);
                        listByBuildingType.setStartPrice(NumberValidationUtils.formatPrice(model.getStartPrice()));
                        listByBuildingType.setEndPrice(NumberValidationUtils.formatPrice(model.getEndPrice()));
                        listByBuildingType.setStartArea(NumberValidationUtils.formatPrice(model.getStartArea()));
                        listByBuildingType.setEndArea(NumberValidationUtils.formatPrice(model.getEndArea()));
                        listByBuildingTypes.add(listByBuildingType);
                    }
                }
            }
            byBuildingTypeResp.setList(listByBuildingTypes);
            result.add(byBuildingTypeResp);
        }
        return result;
    }

    @Override
    public List<MyRequirementDetailResp> myRequirementList(MyRequirementListReq req) {
        TCompradorRequirementCondition condition = new TCompradorRequirementCondition();
        condition.setPartyId(req.getPartyId());
        List<MyRequirementDetailResp> result = new ArrayList<>();
        MyRequirementDetailResp       resp;
        List<TCompradorRequirement>   list   = tCompradorRequirementDao.myRequirementList(condition);
        for (TCompradorRequirement model : list) {
            resp = new MyRequirementDetailResp();
            convert(model, resp);
            resp.setRecommenderNum(getRecomenderCount(model.getId()));
            resp.setRequirementStatusDesc(CompradorEnum.RequirementStatus.getKeyByValue(Integer.valueOf(resp.getRequirementStatus())));
            resp.setPayDeadlineTimestamp(tBackstageOperationRecodService.getPayDeadlineTimestamp(BackstageOperationEnum.Type.COMPRADOR_AUDIT, model.getId().longValue(), REQUIREMENT_AUDIT_PASS));
            result.add(resp);
        }
        return result;
    }

    @Override
    public int requirementFinished(CompradorRequirementQueryReq req) {
        TCompradorRequirementCondition condition = new TCompradorRequirementCondition();
        condition.setId(req.getRequirementId());
        TCompradorRequirement tCompradorRequirement = tCompradorRequirementDao.selectFirst(condition);

        int count = 0;
        if (tCompradorRequirement != null) {
            if (Integer.valueOf(tCompradorRequirement.getRequirementStatus()) > CompradorEnum.RequirementStatus.PLACEMENT.getValue()) {
                return -1;
            }
            tCompradorRequirement.setRequirementStatus(CompradorEnum.RequirementStatus.FINISH.getValue() + "");
            tCompradorRequirement.setOperatorId(req.getOperatorId());
            tCompradorRequirement.setUpdateTime(new Date());
            count += tCompradorRequirementDao.updateById(tCompradorRequirement);

            //更新其他记录的状态，改成已完成
            TCompradorRequirementRecommenderCondition condition2 = new TCompradorRequirementRecommenderCondition();
            condition2.setRequirementId(tCompradorRequirement.getId());
            List<TCompradorRequirementRecommender> list = tCompradorRequirementRecommenderDao.selectList(condition2);
            for (TCompradorRequirementRecommender requirementRecommender : list) {
                requirementRecommender.setRecommenderStatus(CompradorEnum.RecommenderStatus.FINISH.getValue() + "");
                requirementRecommender.setOperatorId(req.getOperatorId().toString());
                requirementRecommender.setUpdateTime(new Date());
                count += tCompradorRequirementRecommenderDao.updateById(requirementRecommender);
            }
        }

        return count;
    }

    @Override
    public Integer getRecomenderCount(Integer requirementId) {
        return tCompradorRequirementRecommenderDao.getCountByRequirementId(requirementId);
    }

    private void setCompradorPayExpired(Integer id) {
        mqSender.compradorPayExpiredEnqueue(id + "", servicePayUtils.getServicePayExpiredTime() * 60);
    }


    private <T> T convert(TCompradorRequirement tCompradorRequirement, T obj) {
        BeanUtils.copyProperties(tCompradorRequirement, obj);
        return obj;
    }

    private List<CompradorDetailResp> convertList(List<TCompradorRequirement> requirementList) {
        List<CompradorDetailResp> detailRespList = new ArrayList<>(requirementList.size());
        CompradorDetailResp       resp;
        for (TCompradorRequirement model : requirementList) {
            resp = new CompradorDetailResp();
            convertSingle(model, resp);
            detailRespList.add(resp);
        }
        return detailRespList;
    }

    private void convertSingle(TCompradorRequirement model, CompradorDetailResp resp) {
        resp = RespConvertUtil.convert(model, resp);
        resp.setRequirementStatusDesc(CompradorEnum.RequirementStatus.getKeyByValue(Integer.valueOf(resp.getRequirementStatus())));
        resp.setRecommenderNum(getRecomenderCount(model.getId()));
        resp.setStartPrice(model.getStartPrice().toString());
        resp.setStartPriceShow(NumberValidationUtils.formatPrice(model.getStartPrice()));
        resp.setEndPrice(model.getEndPrice().toString());
        resp.setEndPriceShow(NumberValidationUtils.formatPrice(model.getEndPrice()));
        resp.setStartArea(model.getStartArea().toString());
        resp.setStartAreaShow(NumberValidationUtils.formatPrice(model.getStartArea()));
        resp.setEndArea(model.getEndArea().toString());
        resp.setEndAreaShow(NumberValidationUtils.formatPrice(model.getEndArea()));
    }

    private void convertSingle(TCompradorRequirement model, CompradorDetailForAdminResp resp) {
        resp = RespConvertUtil.convert(model, resp);
        resp.setRequirementStatusDesc(CompradorEnum.RequirementStatus.getKeyByValue(Integer.valueOf(resp.getRequirementStatus())));
        resp.setRecommenderNum(getRecomenderCount(model.getId()));
        resp.setStartPrice(NumberValidationUtils.formatPrice(model.getStartPrice()));
        resp.setEndPrice(NumberValidationUtils.formatPrice(model.getEndPrice()));
        resp.setStartArea(NumberValidationUtils.formatPrice(model.getStartArea()));
        resp.setEndArea(NumberValidationUtils.formatPrice(model.getEndArea()));

        AccountBaseDto tAccount = accountService.getAccountBaseByPartyId(model.getPartyId());
        resp.setSourceMobile(tAccount == null ? "" : tAccount.getMobile());
        resp.setSourceName(tAccount == null ? "" : tAccount.getName());
    }


}
