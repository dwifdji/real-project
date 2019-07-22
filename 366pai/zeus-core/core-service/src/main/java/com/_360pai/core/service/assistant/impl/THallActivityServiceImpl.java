package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.assistant.THallActivityCondition;
import com._360pai.core.condition.assistant.THallCondition;
import com._360pai.core.condition.assistant.THallEnrollingActivityCondition;
import com._360pai.core.dao.assistant.THallActivityDao;
import com._360pai.core.dao.assistant.THallDao;
import com._360pai.core.dao.assistant.THallEnrollingActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.assistant.req.THallActivityReq;
import com._360pai.core.facade.assistant.req.THallEnrollingActivityReq;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.THall;
import com._360pai.core.model.assistant.THallActivity;
import com._360pai.core.model.assistant.THallEnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.assistant.THallActivityService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.vo.enrolling.web.HomeEnrollingActivityVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @author zxiao
 * @Title: THallActivityServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/18 18:42
 */
@Service
public class THallActivityServiceImpl implements THallActivityService {

    @Resource
    private THallActivityDao hallActivityDao;
    @Resource
    private THallDao hallDao;
    @Resource
    private THallEnrollingActivityDao hallEnrollingActivityDao;
    @Resource
    private AuctionActivityService auctionActivityService;
    @Resource
    private EnrollingActivityService enrollingActivityService;

    @Override
    public Object getTHallActivityList(Integer id) {
        List<Map> tHallActivityList = hallActivityDao.getTHallActivityList(id);

        List<Map> list = new ArrayList<>();
        for (Map<Object, Object> map : tHallActivityList) {
            if (map == null) {
                continue;
            }
            Date beginAt = (Date) map.get("beginAt");
            Date endAt = (Date) map.get("endAt");
            String status = (String) map.get("status");
            //判断当前活动的状态
            if (status.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), beginAt) < 0) {
                map.put("auctionStatus", ActivityEnum.OnlineStatus.UPCOMING.getValue()); //即将开拍
            } else if (status.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), beginAt) > 0) {
                map.put("auctionStatus", ActivityEnum.OnlineStatus.SALE.getValue());  //正在拍卖
            } else if (status.equals(ActivityEnum.Status.SUCCESS.getKey())) {
                map.put("auctionStatus", ActivityEnum.OnlineStatus.SUCCESS.getValue());   //成功
            } else if (status.equals(ActivityEnum.Status.FAILED.getKey())) {
                map.put("auctionStatus", ActivityEnum.OnlineStatus.FAILED.getValue());   //完成
            }

            if (map.get("auctionStatus") == null) {
                continue;
            }

            if (StringUtils.isEmpty((String) map.get("auctionStatus"))) {
                continue;
            }
            map.put("dataTime", System.currentTimeMillis() / 1000);
            map.put("endTime", endAt.getTime() / 1000);
            Map map2 = clone(map);
            list.add(map2);
        }
        return list;
    }

    //克隆方法：
    private static <T extends Serializable> T clone(Map obj) {
        T cloneObj = null;
        try {
            // 写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();

            // 分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            // 返回生成的新对象
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }


    @Override
    public Object getAssetType() {
        PageHelper.startPage(1, 6);
        PageHelper.orderBy("order_num asc");
        THallCondition condition = new THallCondition();
        condition.setHallType(1);
        condition.setDelFlag(0);
        return hallDao.selectList(condition);
    }

    @Override
    public Object getEnrollingType() {
        PageHelper.startPage(1, 3);
        PageHelper.orderBy("order_num asc");
        THallCondition condition = new THallCondition();
        condition.setHallType(2);
        condition.setDelFlag(0);
        return hallDao.selectList(condition);
    }

    @Override
    public ResponseModel getHallEnrollingActivities(Integer hallId) {

        List<HomeEnrollingActivityVO> homeList = hallEnrollingActivityDao.getHallEnrollingActivities(hallId);
        Map<String, Object> result = new HashMap<String, Object>();
        if (homeList == null || homeList.size() <= 0) {
            result.put("homeList", new ArrayList<HomeEnrollingActivityVO>());
            return ResponseModel.succ(result);
        }
        homeList = getHomeList(homeList);

        //首页状态暂时没有显示

        result.put("homeList", homeList);
        return ResponseModel.succ(result);
    }

    @Override
    public PageInfo tHallActivityList(THallActivityReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        THallActivityCondition condition = new THallActivityCondition();
        condition.setDelFlag(0);
        condition.setHallId(req.getHallId());
        List<THallActivity> tHallActivities = hallActivityDao.selectList(condition);
        for (THallActivity activity : tHallActivities) {
            Integer hallId = activity.getHallId();
            Integer activityId = activity.getActivityId();
            THall hall = hallDao.selectById(hallId);
            AuctionActivity auctionActivity = auctionActivityService.getById(activityId);
            activity.setHallName(hall.getHallName());
            activity.setActivityName(auctionActivity.getAssetName());
        }
        return new PageInfo<>(tHallActivities);
    }

    @Override
    public int addTHallActivity(THallActivity params) {
        findByActivityIdAndHallId(params);
        return hallActivityDao.insert(params);
    }

    private void findByActivityIdAndHallId(THallActivity params) {
        THallActivityCondition condition = new THallActivityCondition();
        condition.setActivityId(params.getActivityId());
        condition.setHallId(params.getHallId());
        condition.setDelFlag(0);
        List<THallActivity> tHallActivities = hallActivityDao.selectList(condition);
        if (!tHallActivities.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动已存在");
        }
    }

    private void findEnrollingByActivityIdAndHallId(THallEnrollingActivity params) {
        THallEnrollingActivityCondition condition = new THallEnrollingActivityCondition();
        condition.setEnrollingActivityId(params.getEnrollingActivityId());
        condition.setHallId(params.getHallId());
        condition.setDelFlag(0);
        List<THallEnrollingActivity> tHallActivities = hallEnrollingActivityDao.selectList(condition);
        if (!tHallActivities.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动已存在");
        }
    }

    @Override
    public int editTHallActivityList(THallActivity params) {
        THallActivity hallActivity = hallActivityDao.selectById(params.getId());
        if (hallActivity == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        if (hallActivity.getActivityId().equals(params.getActivityId())) {
            if (!hallActivity.getHallId().equals(params.getHallId())) {
                findByActivityIdAndHallId(params);
            }
        } else {
            findByActivityIdAndHallId(params);
        }
        return hallActivityDao.updateById(params);
    }

    @Override
    public int deleteTHallActivityList(THallActivity params) {
        THallActivity hallActivity = hallActivityDao.selectById(params.getId());
        if (hallActivity == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        hallActivity.setDelFlag(1);
        return hallActivityDao.updateById(hallActivity);
    }

    @Override
    public PageInfo tEnrollingHallActivityList(THallEnrollingActivityReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        THallEnrollingActivityCondition condition = new THallEnrollingActivityCondition();
        condition.setDelFlag(0);
        condition.setHallId(req.getHallId());
        List<THallEnrollingActivity> tHallEnrollingActivities = hallEnrollingActivityDao.selectList(condition);

        for (THallEnrollingActivity enrollingActivity : tHallEnrollingActivities) {
            Integer hallId = enrollingActivity.getHallId();
            Integer enrollingActivityId = enrollingActivity.getEnrollingActivityId();

            EnrollingActivity enrollingActivityById = enrollingActivityService.getEnrollingActivityById(enrollingActivityId.toString());
            THall hall = hallDao.selectById(hallId);
            enrollingActivity.setHallName(hall.getHallName());
            enrollingActivity.setEnrollingActivityName(enrollingActivityById.getName());
        }

        return new PageInfo<>(tHallEnrollingActivities);
    }

    @Override
    public int addEnrollingHallActivity(THallEnrollingActivity params) {
        findEnrollingByActivityIdAndHallId(params);
        params.setDelFlag(0);
        return hallEnrollingActivityDao.insert(params);
    }

    @Override
    public int editEnrollingHallActivity(THallEnrollingActivity params) {

        THallEnrollingActivity enrollingActivity = hallEnrollingActivityDao.selectById(params.getId());
        if (enrollingActivity == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        if (enrollingActivity.getEnrollingActivityId().equals(params.getEnrollingActivityId())) {
            if (!enrollingActivity.getHallId().equals(params.getHallId())) {
                findEnrollingByActivityIdAndHallId(params);
            }
        } else {
            findEnrollingByActivityIdAndHallId(params);
        }
        return hallEnrollingActivityDao.updateById(params);
    }

    @Override
    public int deleteEnrollingHallActivity(THallEnrollingActivity params) {
        THallEnrollingActivity enrollingActivity = hallEnrollingActivityDao.selectById(params.getId());
        if (enrollingActivity == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        enrollingActivity.setDelFlag(1);
        return hallEnrollingActivityDao.updateById(enrollingActivity);
    }

    @Override
    public Object detailTHallActivity(Integer id) {
        return hallActivityDao.selectById(id);
    }

    @Override
    public Object detailEnrollingTHallActivity(Integer id) {
        return hallEnrollingActivityDao.selectById(id);
    }

    @Override
    public Object getAssetLeaseType() {
        PageHelper.startPage(1, 5);
        PageHelper.orderBy("order_num asc");
        THallCondition condition = new THallCondition();
        condition.setHallType(3);
        condition.setDelFlag(0);
        return hallDao.selectList(condition);
    }


    //暂时以字符串为判断条件，后续根据不同的业务逻辑获取具体的环境
    private List<HomeEnrollingActivityVO> getHomeList(List<HomeEnrollingActivityVO> homeListVO) {
        for (HomeEnrollingActivityVO homeModel : homeListVO) {
            //设置数据时间截取秒数
            String endAt = homeModel.getEndAt();
            if (endAt != null && endAt.length() > 2) {
                endAt = endAt.substring(0, endAt.length() - 3);
            }
            homeModel.setEndAt(endAt);

            if (EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType()
                    .equals(homeModel.getType())) {
                homeModel.setCityLabel(EnrollingEnum.ENROLLING_LABEL.LOCATION.getTypeName());
                homeModel.setDepositLabel(EnrollingEnum.ENROLLING_LABEL.MARGIN.getTypeName());
                homeModel.setMortgageLabel(null);
                homeModel.setMortgageValue(null);
            } else if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType()
                    .equals(homeModel.getType())) {
                homeModel.setCityName(null);
                homeModel.setDepositLabel(EnrollingEnum.ENROLLING_LABEL.DEBT_PRINCIPAL.getTypeName());
                homeModel.setDeposit(homeModel.getRefPrice());
                homeModel.setMortgageLabel(EnrollingEnum.ENROLLING_LABEL.MORTGAGE.getTypeName());
            } else if (EnrollingEnum.ENROLLING_TYPE.CREDITOR_RIGHTS.getType()
                    .equals(homeModel.getType())) {
                homeModel.setCityName(null);
                homeModel.setCityLabel(EnrollingEnum.ENROLLING_LABEL.LOCATION.getTypeName());
                homeModel.setDepositLabel(EnrollingEnum.ENROLLING_LABEL.REFERENCE_PRICE.getTypeName());
                homeModel.setDeposit(homeModel.getRefPrice());
                homeModel.setMortgageLabel(null);
            }
        }
        return homeListVO;
    }
}
