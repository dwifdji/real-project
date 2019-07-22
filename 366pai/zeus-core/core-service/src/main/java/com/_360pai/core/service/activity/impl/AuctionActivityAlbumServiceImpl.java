package com._360pai.core.service.activity.impl;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.activity.AuctionActivityAlbumDao;
import com._360pai.core.dao.activity.AuctionActivityAlbumMapDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.TAssetCategoryDao;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.AlbumReq;
import com._360pai.core.facade.activity.resp.AlbumResp;
import com._360pai.core.facade.activity.vo.AlbumVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.AuctionActivityAlbum;
import com._360pai.core.model.activity.AuctionActivityAlbumMap;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.activity.AuctionActivityAlbumService;
import com._360pai.core.service.asset.TAssetTemplateCategoryService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 15:14
 */
@Service
public class AuctionActivityAlbumServiceImpl implements AuctionActivityAlbumService {

    @Autowired
    private AuctionActivityAlbumDao auctionActivityAlbumDao;
    @Autowired
    private AuctionActivityAlbumMapDao auctionActivityAlbumMapDao;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private TAssetTemplateCategoryService assetTemplateCategoryService;
    @Autowired
    private TAssetCategoryDao assetCategoryDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EnrollingActivityDao enrollingActivityDao;

    @Override
    public PageInfoResp<AlbumVo> getAlbumListByPage(AlbumReq.QueryReq req) {
        PageInfoResp<AlbumVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotEmpty(req.getStatus())) {
            params.put("status", "1".equals(req.getStatus()) ? true : false);
        }
        PageInfo pageInfo = auctionActivityAlbumDao.getListByPage(req.getPage(), req.getPerPage(), params, "a.id desc");
        List<AuctionActivityAlbum> list = pageInfo.getList();
        List<AlbumVo> resultList = new ArrayList<>();
        for (AuctionActivityAlbum album : list) {
            try {
                AlbumVo albumVo = RespConvertUtil.convertToAlbumVo(album);
                albumVo.setActivityNumber(auctionActivityAlbumMapDao.getAuctionActivityCountByAlbumId(album.getId()));
                albumVo.setActivityList(getFrontRelatedActivityList(1, 3, album.getId()).getList());
                resultList.add(albumVo);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        resp.setList(resultList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public AlbumResp getAlbum(AlbumReq.AlbumIdReq req) {
        AlbumResp resp = new AlbumResp();
        AuctionActivityAlbum album = auctionActivityAlbumDao.selectById(req.getAlbumId());
        if (album == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AlbumVo albumVo = RespConvertUtil.convertToAlbumVo(album);
        albumVo.setActivityNumber(auctionActivityAlbumMapDao.getAuctionActivityCountByAlbumId(album.getId()));
        resp.setAlbum(albumVo);
        return resp;
    }

    @Override
    public PageInfoResp<Map> getRelatedActivityList(AlbumReq.AlbumIdReq req) {
        return getRelatedActivityList(req.getPage(), req.getPerPage(), req.getAlbumId());
    }

    @Override
    public PageInfoResp<Map> getFrontRelatedActivityList(AlbumReq.AlbumIdReq req) {
        return getFrontRelatedActivityList(req.getPage(), req.getPerPage(), req.getAlbumId());
    }

    private PageInfoResp<Map> getFrontRelatedActivityList(int page, int perPage, Integer albumId) {
        PageInfoResp<Map> resp = new PageInfoResp<>();
        PageInfo pageInfo = auctionActivityAlbumDao.getFrontRelatedActivityList(page, perPage, albumId);
        List<Map> list = pageInfo.getList();
        List<Map> resultList = new ArrayList<>();
        for (Map item : list) {
            try {
                if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(item.get("activityType"))) {
                    item.put("modeDesc", ActivityEnum.ActivityMode.getKeyByValue((String) item.get("mode")));
                    String categoryName = (String) item.get("categoryName");
                    if (StringUtils.isBlank(categoryName) && item.get("category").equals("-1")) {
                        item.put("categoryName", "租赁权拍卖");
                    }
                } else {
                    item.put("categoryName", EnrollingEnum.ENROLLING_TYPE.getDesc((String) item.get("category")));
                }
                item.put("statusDesc", getActivityStatus(item));
                resultList.add(item);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        resp.setList(resultList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    private String getActivityStatus(Map map) {
        String status = (String) map.get("status");
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(map.get("activityType"))) {
            Date beginAt = (Date) map.get("beginAt");
            //判断当前活动的状态`
            if (status.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), beginAt) < 0) {
                return  "即将开拍"; //即将开拍
            } else if (status.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), beginAt) > 0) {
                return "正在拍卖";  //正在拍卖
            } else if (status.equals(ActivityEnum.Status.SUCCESS.getKey())) {
                return "成交";   //成功
            } else if (status.equals(ActivityEnum.Status.FAILED.getKey())) {
                return "结束";   //完成
            }
        } else {
            if (EnrollingEnum.STATUS.ONLINE.getType().equals(status)) {
                return EnrollingEnum.STATUS.ONLINE.getTypeName();
            } else if (EnrollingEnum.STATUS.FINISHED.getType().equals(status)) {
                return EnrollingEnum.STATUS.FINISHED.getTypeName();
            } else if (EnrollingEnum.STATUS.CLOSED.getType().equals(status)) {
                return EnrollingEnum.STATUS.CLOSED.getTypeName();
            }
        }
        return status;
    }

    private PageInfoResp<Map> getRelatedActivityList(int page, int perPage, Integer albumId) {
        PageInfoResp<Map> resp = new PageInfoResp<>();
        PageInfo pageInfo = auctionActivityAlbumDao.getFrontRelatedActivityList(page, perPage, albumId);
        List<Map> list = pageInfo.getList();
        List<Map> resultList = new ArrayList<>();
        for (Map item : list) {
            try {
                if (StringUtils.isBlank((String) item.get("cityName"))) {
                    item.put("cityName", item.get("provinceName"));
                }
                if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(item.get("activityType"))) {
                    item.put("modeDesc", ActivityEnum.ActivityMode.getKeyByValue((String) item.get("mode")));
                    String categoryName = (String) item.get("categoryName");
                    if (StringUtils.isBlank(categoryName) && item.get("category").equals("-1")) {
                        item.put("categoryName", "租赁权拍卖");
                    }
                    AuctionActivity activity = auctionActivityDao.selectById(item.get("activityId"));
                    Asset asset = assetDao.selectById(activity.getAssetId());
                    String agencyName = agencyDao.getName(activity.getAgencyId());
                    item.put("agencyName", agencyName);
                    String sellerName;
                    if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
                        sellerName = agencyName;
                    } else {
                        PartyAccount partyAccount = accountService.getPartyAccountById(asset.getPartyId());
                        sellerName = partyAccount.getName();
                    }
                    item.put("sellerName", sellerName);

                } else {
                    item.put("categoryName", EnrollingEnum.ENROLLING_TYPE.getDesc((String) item.get("category")));
                    EnrollingActivity activity = enrollingActivityDao.selectById(item.get("activityId"));
                    String agencyName = agencyDao.getName(activity.getAgencyId());
                    item.put("agencyName", agencyName);
                    String sellerName;
                    if (EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType().equals(activity.getThirdPartyStatus())) {
                        sellerName = agencyName;
                    } else {
                        PartyAccount partyAccount = accountService.getPartyAccountById(activity.getPartyId());
                        sellerName = partyAccount.getName();
                    }
                    item.put("sellerName", sellerName);
                }
                item.put("statusDesc", getActivityStatus(item));

                resultList.add(item);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        resp.setList(resultList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public ListResp<AlbumVo> getStickyList(AlbumReq.AlbumIdReq req) {
        ListResp<AlbumVo> resp = new ListResp<>();
        List<AuctionActivityAlbum> list = auctionActivityAlbumDao.getStickyList();
        List<AlbumVo> resultList = new ArrayList<>();
        for (AuctionActivityAlbum album : list) {
            try {
                AlbumVo albumVo = RespConvertUtil.convertToAlbumVo(album);
                albumVo.setLinkUrl("https://www.360pai.com/albumsList?id=" + albumVo.getId());
                albumVo.setActivityNumber(auctionActivityAlbumMapDao.getAuctionActivityCountByAlbumId(album.getId()));
                resultList.add(albumVo);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public AlbumResp createAlbum(AlbumReq.AlbumCreateReq req) {
        AlbumResp resp = new AlbumResp();
        AuctionActivityAlbum auctionActivityAlbum = ReqConvertUtil.convertToAuctionActivityAlbum(req);
        int result = auctionActivityAlbumDao.insert(auctionActivityAlbum);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setAlbumId(auctionActivityAlbum.getId());
        return resp;
    }

    @Override
    public AlbumResp updateAlbum(AlbumReq.AlbumUpdateReq req) {
        AlbumResp resp = new AlbumResp();
        AuctionActivityAlbum auctionActivityAlbum = auctionActivityAlbumDao.selectById(req.getId());
        if (auctionActivityAlbum == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        auctionActivityAlbum = ReqConvertUtil.convertToAuctionActivityAlbum(req);
        int result = auctionActivityAlbumDao.updateById(auctionActivityAlbum);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setAlbumId(auctionActivityAlbum.getId());
        return resp;
    }

    @Transactional
    @Override
    public AlbumResp addActivityList(AlbumReq.AlbumIdReq req) {
        AlbumResp resp = new AlbumResp();
        AuctionActivityAlbum auctionActivityAlbum = auctionActivityAlbumDao.selectById(req.getAlbumId());
        if (auctionActivityAlbum == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        String activityType = req.getActivityType();
        Integer activityId = req.getActivityId();
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(activityType)) {
            AuctionActivity activity = auctionActivityDao.selectById(activityId);
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!ActivityEnum.Status.ONLINE.equals(activity.getStatus()) && !ActivityEnum.Status.SUCCESS.equals(activity.getStatus()) && !ActivityEnum.Status.FAILED.equals(activity.getStatus())) {
                throw new BusinessException(ApiCallResult.FAILURE, "只有已上线、成功、流拍的活动才能添加");
            }
        } else if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(activityType)) {
            EnrollingActivity activity = enrollingActivityDao.selectById(activityId);
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (!EnrollingEnum.STATUS.ONLINE.getType().equals(activity.getStatus()) && !EnrollingEnum.STATUS.FINISHED.getType().equals(activity.getStatus()) && !EnrollingEnum.STATUS.CLOSED.getType().equals(activity.getStatus())) {
                throw new BusinessException(ApiCallResult.FAILURE, "只有正在报名、报名结束、招商结束的活动才能添加");
            }
        } else {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AuctionActivityAlbumMap model = auctionActivityAlbumMapDao.getByAlbumIdAndActivityId(req.getAlbumId(), activityId, activityType);
        if (model != null) {
            throw new BusinessException("活动已添加");
        }
        model = new AuctionActivityAlbumMap();
        model.setAlbumId(req.getAlbumId());
        model.setActivityId(activityId);
        model.setActivityType(activityType);
        model.setOrderNum(req.getOrderNum());
        int result = auctionActivityAlbumMapDao.insert(model);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setAlbumId(req.getAlbumId());
        return resp;
    }

    @Override
    public AlbumResp editActivityList(AlbumReq.AlbumIdReq req) {
        // 暂不需要
        return null;
    }

    @Transactional
    @Override
    public AlbumResp deleteActivities(AlbumReq.AlbumIdReq req) {
        AlbumResp resp = new AlbumResp();
        AuctionActivityAlbum auctionActivityAlbum = auctionActivityAlbumDao.selectById(req.getAlbumId());
        if (auctionActivityAlbum == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        String activityType = req.getActivityType();
        Integer activityId = req.getActivityId();
        if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(activityType)) {
            AuctionActivity activity = auctionActivityDao.selectById(activityId);
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }

        } else if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(activityType)) {
            EnrollingActivity activity = enrollingActivityDao.selectById(activityId);
            if (activity == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        } else {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AuctionActivityAlbumMap model = new AuctionActivityAlbumMap();
        model.setAlbumId(req.getAlbumId());
        model.setActivityId(activityId);
        model.setActivityType(activityType);
        int result = auctionActivityAlbumMapDao.delete(model);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setAlbumId(req.getAlbumId());
        return resp;
    }

    @Override
    public AlbumResp online(AlbumReq.AlbumIdReq req) {
        AlbumResp resp = new AlbumResp();
        AuctionActivityAlbum auctionActivityAlbum = auctionActivityAlbumDao.selectById(req.getAlbumId());
        if (auctionActivityAlbum == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        } else if (!auctionActivityAlbum.getIsOnline()) {
            auctionActivityAlbum.setIsOnline(true);
            int result = auctionActivityAlbumDao.updateById(auctionActivityAlbum);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        resp.setAlbumId(req.getAlbumId());
        return resp;
    }

    @Override
    public AlbumResp offline(AlbumReq.AlbumIdReq req) {
        AlbumResp resp = new AlbumResp();
        AuctionActivityAlbum auctionActivityAlbum = auctionActivityAlbumDao.selectById(req.getAlbumId());
        if (auctionActivityAlbum == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        } else if (auctionActivityAlbum.getIsOnline()) {
            auctionActivityAlbum.setIsOnline(false);
            int result = auctionActivityAlbumDao.updateById(auctionActivityAlbum);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        resp.setAlbumId(req.getAlbumId());
        return resp;
    }
}