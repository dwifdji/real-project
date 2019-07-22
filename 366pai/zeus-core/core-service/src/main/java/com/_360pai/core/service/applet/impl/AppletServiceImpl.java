package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.core.condition.applet.TAppletViewShopCondition;
import com._360pai.core.dao.account.TAccountExtBindDao;
import com._360pai.core.dao.applet.TAppletViewShopDao;
import com._360pai.core.facade.applet.req.AppletReq;
import com._360pai.core.facade.applet.req.AssistantReq;
import com._360pai.core.facade.applet.vo.AppletVisitVo;
import com._360pai.core.facade.applet.vo.InviteRecord;
import com._360pai.core.model.applet.TAppletViewShop;
import com._360pai.core.service.applet.AppletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xdrodger
 * @Title: AppletServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/22 15:37
 */
@Service
public class AppletServiceImpl implements AppletService {

    @Autowired
    private TAccountExtBindDao tAccountExtBindDao;

    @Autowired
    private TAppletViewShopDao tAppletViewShopDao;

    @Override
    public PageInfoResp<InviteRecord> getInviteRecordList(AppletReq.InviteRecordReq req) {
        PageInfoResp<InviteRecord> resp   = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        params.put("inviteCode", req.getShopId() + "");
        PageInfo pageInfo  = tAccountExtBindDao.getInviteRecordListByPage(req.getPage(), req.getPerPage(), params, "");
        List<InviteRecord> itemsList = new ArrayList<>();
        List<InviteRecord>  list  = pageInfo.getList();
        for (InviteRecord record : list) {
            record.setMobile(ToolUtil.maskMobile(record.getMobile()));
            itemsList.add(record);
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }


    @Override
    public PageInfoResp<AppletVisitVo> getVisitList(AssistantReq.comReq req) {

        PageInfoResp<AppletVisitVo> resp   = new PageInfoResp<>();

        PageInfo appletVisit = getAppletVisitList(req);
        resp.setList(appletVisit.getList());
        resp.setTotal(appletVisit.getTotal());
        resp.setHasNextPage(appletVisit.isHasNextPage());
        return resp;

    }

    private PageInfo getAppletVisitList(AssistantReq.comReq req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());

        TAppletViewShopCondition shop = new TAppletViewShopCondition();
        shop.setIsDelete(false);
        shop.setShopId(Integer.valueOf(req.getShopId()));
        shop.setType(Integer.valueOf(req.getType()));
        shop.setAuctionId(StringUtils.isEmpty(req.getAuctionId())?null:Integer.valueOf(req.getAuctionId()));
        shop.setUpdateTime("1".equals(req.getTimeType())?DateUtil.formatDay(DateUtil.getSysTime(),DateUtil.NORM_DATE_PATTERN):null);

         List<AppletVisitVo> list = tAppletViewShopDao.getAppletVisitList(shop);
        for (AppletVisitVo vo :list) {
            vo.setName(StringEscapeUtils.unescapeJava(vo.getName()));
            vo.setTimeInfo(getFormatTime(vo.getTimeInfo()));
        }
        return new PageInfo<>(list);

    }





        /**
         *
         *格式化 展示数据
         */
    private  String getFormatTime(String timeInfo) {

        Date time = DateUtil.strToDateLong(timeInfo);

        int differentDay = DateUtil.differentDaysByMillisecond(DateUtil.formatDay(time,DateUtil.NORM_DATE_PATTERN),DateUtil.formatDay(new Date(),DateUtil.NORM_DATE_PATTERN));

        //超出一周显示
        if(differentDay>=7){

            return DateUtil.formatDate2Str(time,DateUtil.YYYY_DATE_PATTERN);
        }

        //周显示
        if(1<differentDay&&differentDay<7){

            return DateUtil.showTodayIs(time)+" "+DateUtil.formatDate2Str(time,DateUtil.NORM_TIME_FEN);

        }


        //两天
        if(1==differentDay){

            return "昨日 "+DateUtil.formatDate2Str(time,DateUtil.NORM_TIME_FEN);

        }

        //今日
        if(0==differentDay){

            return DateUtil.formatAmPm(time);

        }


        return timeInfo;
    }

    @Override
    public int saveOrUpdateViewShop(AssistantReq.comReq req) {
        int result=0;

        TAppletViewShop viewShop = checkIsExist(req);
        if(viewShop==null){

            TAppletViewShop shop = new TAppletViewShop();
            shop.setIsDelete(false);
            shop.setOpenId(req.getOpenId());
            shop.setShopId(Integer.valueOf(req.getReqShopId()));
            shop.setType(Integer.valueOf(req.getType()));
            shop.setAuctionId(StringUtils.isEmpty(req.getAuctionId()) ?null:Integer.valueOf(req.getAuctionId()));
            shop.setCreateTime(DateUtil.getSysTime());
            shop.setUpdateTime(DateUtil.getSysTime());
            tAppletViewShopDao.insert(shop);

        }else {
            viewShop.setUpdateTime(DateUtil.getSysTime());
            result = tAppletViewShopDao.updateById(viewShop);
        }


        return result;
    }

    private TAppletViewShop checkIsExist(AssistantReq.comReq req) {

        TAppletViewShopCondition condition = new TAppletViewShopCondition();
        condition.setIsDelete(false);
        condition.setType(Integer.valueOf(req.getType()));
        condition.setOpenId(req.getOpenId());
        condition.setShopId(Integer.valueOf(req.getReqShopId()));
        condition.setAuctionId(StringUtils.isEmpty(req.getAuctionId())?null:Integer.valueOf(req.getAuctionId()));
        return  tAppletViewShopDao.selectFirst(condition);

    }
}
