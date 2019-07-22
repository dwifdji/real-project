package com._360pai.core.service.disposal.impl;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.condition.account.TDisposeProviderCondition;
import com._360pai.core.condition.disposal.TDisposeLevelCondition;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.TActivityServiceProviderDao;
import com._360pai.core.dao.disposal.TDisposeLevelDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.service.disposal.DisposeLevelService;
import com._360pai.core.utils.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author xiaolei
 * @create 2018-10-29 15:05
 */
@Service
public class DisposeLevelServiceImpl implements DisposeLevelService {

    @Autowired
    private TDisposeLevelDao disposeLevelDao;
    @Autowired
    private TDisposeProviderDao disposeProviderDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private TActivityServiceProviderDao activityServiceProviderDao;

    @Override
    public boolean isFirstLevel(Integer providerId, String cityId) {
        TDisposeLevelCondition condition = new TDisposeLevelCondition();
        condition.setProviderId(providerId);
        condition.setCityId(cityId != null ? Integer.valueOf(cityId) : null);
        condition.setLevelFlag(Constant.DisposalCons.FIRST_LEVEL);
        condition.setReplaceTime("0");
        List<TDisposeLevel> tDisposeLevels = disposeLevelDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tDisposeLevels);
    }

    @Override
    public boolean isFirstLevel(Integer partyId) {

        if (partyId == null) {
            return false;
        }

        TDisposeProviderCondition condition = new TDisposeProviderCondition();
        condition.setPartyId(partyId);
        List<TDisposeProvider> tDisposeProviders = disposeProviderDao.selectList(condition);

        for (TDisposeProvider tmp : tDisposeProviders) {
            if (isFirstLevel(tmp.getId(), null)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Integer getFirstLevelLowOfficeByCityId(String cityId) {

        if (cityId == null) {
            return null;
        }
        String[] split = cityId.split(",");
        cityId         = split[0];
        if (split.length > 1) {
            return null;
        }
        Map<String, Object> query = new HashMap<>(3);
        query.put("disposeType",DisposalEnum.DisposeType.LAW_OFFICE.getKey());
        query.put("cityId",cityId);
        query.put("levelFlag",Constant.DisposalCons.FIRST_LEVEL);
        TDisposeLevel level = disposeLevelDao.getRegionLevelProvider(query);
        return level == null ? null : level.getProviderId();
    }

    @Override
    public Integer addFirstLevelLowOffice(Integer providerId, String cityId, Integer operatorId) {
        TDisposeLevel level = new TDisposeLevel();
        level.setProviderId(providerId);
        level.setCityId(Integer.valueOf(cityId));
        level.setLevelFlag(Constant.DisposalCons.FIRST_LEVEL);
        level.setOperatorId(operatorId);
        level.setSurveyRefuseNum(0);
        int insert = disposeLevelDao.insert(level);
        return insert;
    }

    @Override
    public PageInfo getFirstLevelCityPartnerList(DisposeProviderReq.GetProviderList req) {
        Map<String, Object> query = queryLevelParam(req);
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TDisposeLevel> list = disposeLevelDao.getFirstLevelCityPartnerByParam(query);
        PageInfo<TDisposeLevel> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo getCityPartnerList(DisposeProviderReq.GetProviderList req) {
        Map<String, Object> query = queryParam(req);
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TDisposeLevel> list = disposeLevelDao.getCityPartnerByParamWithoutFirstLevel(query);
        PageInfo<TDisposeLevel> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrReplaceFirstPartner(Integer levelId, Integer providerId, Integer operatorId) {
        if (levelId == null && providerId != null) {
            return addFirstPartner(providerId, operatorId);
        } else if (levelId != null && providerId != null) {
            return replaceFirstPartner(levelId, providerId, operatorId);
        }
        return 0;
    }

    @Override
    public TDisposeLevel getById(Integer levelId) {
        Assert.notNull(levelId, "levelId 不能为空");
        TDisposeLevel level = new TDisposeLevel();
        level.setId(levelId);
        return disposeLevelDao.selectById(levelId);
    }

    @Override
    public int removeFirstPartner(Integer levelId, Integer operatorId) {
        TDisposeLevel byId = getById(levelId);
        if (null != byId) {
            byId.setReplaceTime(DateUtil.formatDate2Str(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT));
            byId.setOperatorId(operatorId);
            disposeLevelDao.updateById(byId);
            activityServiceProviderDao.removeFirstLevelAuctionDisposeProviderByProviderId(byId.getProviderId());
        }
        return 0;
    }

    @Override
    public int updateContractInfo(Integer levelId, Integer operatorId, Date contractDate, String contractNo) {
        Optional.ofNullable(levelId).orElseThrow(() -> new BusinessException("levelId 不能为空"));
        TDisposeLevel byId = getById(levelId);
        byId.setContractDate(contractDate);
        byId.setContractNo(contractNo);
        byId.setOperatorId(operatorId);
        int i = disposeLevelDao.updateById(byId);
        return i;
    }

    @Override
    public TDisposeLevel getByProviderIdAndCityId(Integer provider, Integer cityId) {
        TDisposeLevelCondition condition = new TDisposeLevelCondition();
        condition.setProviderId(provider);
        condition.setCityId(cityId);
        condition.setReplaceTime("0");
        List<TDisposeLevel> tDisposeLevels = disposeLevelDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tDisposeLevels) ? tDisposeLevels.get(0) : null;
    }

    @Override
    public int updateById(TDisposeLevel level) {

        return disposeLevelDao.updateById(level);
    }

    private Map<String, Object> queryLevelParam(DisposeProviderReq.GetProviderList req) {
        Map<String, Object> query = new HashMap<>(10);
        query.put("cityId", req.getCityId());
        query.put("provinceId", req.getProvinceId());
        query.put("areaId", req.getAreaId());
        query.put("companyName", req.getCompanyName());
        query.put("disposeType", req.getDisposeType());
        query.put("providerId",  req.getProviderId());
        return query;
    }

    private Map<String, Object> queryParam(DisposeProviderReq.GetProviderList req) {
        Map<String, Object> query = new HashMap<>(10);
        if (req.getCityId() != null) {
            if (req.getCityId().intValue() < 0) {
                query.put("provinceId", Math.abs(req.getCityId()));
            } else {
                query.put("cityId", req.getCityId());
            }
        }
        query.put("companyName", req.getCompanyName());
        query.put("levelId",     req.getLevelId());
        query.put("disposeType", req.getDisposeType());
        return query;
    }

    private int addFirstPartner(Integer providerId,  Integer operatorId) {
        // 新增，
        TDisposeProvider disposeProvider = disposeProviderDao.selectById(providerId);
        if (null == disposeProvider) {
            throw new BusinessException("参数异常");
        }
        if (StringUtils.isBlank(disposeProvider.getRegion())) {
            throw new BusinessException("请补充服务商所在区域");
        }
        //判断该服务商是否是一级合伙人
        boolean firstLevel = isFirstLevel(disposeProvider.getId(), disposeProvider.getRegion());
        if (firstLevel) {
            return 1;
        }
        //判断该城市是否存在一级合伙人
        Integer regionProviderId = getFirstLevelLowOfficeByCityId(disposeProvider.getRegion());
        if (null != regionProviderId) {
            throw new BusinessException("该城市已有一级城市合伙人");
        }
        Integer tmp
                = addFirstLevelLowOffice(disposeProvider.getId(), disposeProvider.getRegion(), operatorId);
        return tmp;
    }

    private int replaceFirstPartner(Integer levelId, Integer providerId, Integer operatorId) {
        TDisposeLevel byId = getById(levelId);
        if (byId == null) {
            throw new BusinessException("参数异常");
        }
        byId.setReplaceTime(DateUtil.formatDate2Str(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT));
        int tag = disposeLevelDao.updateById(byId);
        if (tag > 0) {
            activityServiceProviderDao.removeFirstLevelAuctionDisposeProviderByProviderId(byId.getProviderId());
            int i = addFirstPartner(providerId, operatorId);
            return i;
        }
        return 0;
    }
}
