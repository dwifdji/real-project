package com._360pai.core.service.activity.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.condition.activity.BidCondition;
import com._360pai.core.dao.activity.BidDao;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.vo.BidRecord;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.Bid;
import com._360pai.core.model.assistant.Deposit;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.activity.BidService;
import com._360pai.core.service.assistant.DepositService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 9:48
 */
@Service
public class BidServiceImpl implements BidService {

    @Resource
    private BidDao bidDao;
    @Resource
    private AgencyService agencyService;
    @Resource
    private AccountService accountService;
    @Resource
    private DepositService depositService;
    @Resource
    private AuctionActivityService auctionActivityService;

    @Override
    public PageInfoResp<BidRecord> getAllBidRecordsByPage(ActivityReq.ActivityId req) {
        PageInfoResp<BidRecord> resp = new PageInfoResp<>();
        BidCondition condition = new BidCondition();
        condition.setActivityId(req.getActivityId());
        AuctionActivity auctionActivity = auctionActivityService.getById(req.getActivityId());
        //特殊处理   一口价暗标 出价记录只会显示自己的出价记录
        PageInfo pageInfo;
        if (ActivityEnum.ActivityMode.SEALED.getKey().equals(auctionActivity.getMode()) ||
                auctionActivity.getMode().equals(ActivityEnum.ActivityMode.PUBLIC.getKey())) {
            condition.setPartyId(req.getPartyId());
            pageInfo = getAllByActivityId(req.getPage(), req.getPerPage(), condition, " id desc ");
        } else {
            pageInfo = getAllByActivityId(req.getPage(), req.getPerPage(), condition, " id desc ");
        }

        List bids = pageInfo.getList();
        List<BidRecord> bidRecords = new ArrayList<>();
        for (int i = 0; i < bids.size(); i++) {
            Bid bid = (Bid) bids.get(i);
            BidRecord bidRecord = new BidRecord();
            if (i == 0) {
                ActivityEnum.Status status = auctionActivity.getStatus();
                if (status.getKey().equals(ActivityEnum.Status.ONLINE.getKey())) {
                    bidRecord.setStatus("领先");
                }
                if (status.getKey().equals(ActivityEnum.Status.SUCCESS.getKey())) {
                    bidRecord.setStatus("成交");
                }
                if (status.getKey().equals(ActivityEnum.Status.FAILED.getKey())) {
                    bidRecord.setStatus("出局");
                }
            } else {
                bidRecord.setStatus("出局");
            }

            bidRecord.setId(bid.getId());
            if (req.getPartyId() == null) {
                bidRecord.setMeFlag("0");
            } else {
                if (req.getPartyId().equals(bid.getPartyId())) {
                    bidRecord.setMeFlag("1");
                }
            }
            Deposit deposit = depositService.getByActivityIdPartId(bid.getActivityId(), bid.getPartyId());
            bidRecord.setCode(deposit.getCode());
            bidRecord.setCreatedAt(bid.getCreatedAt());
            bidRecord.setAmount(bid.getAmount().toString());
            TAgency agency = agencyService.findByAgencyId(deposit.getAgencyId());
            if (agency != null) {
                bidRecord.setAgencyName(agency.getName());
            }
            PartyAccount partyVo = accountService.getPartyAccountById(bid.getPartyId());
            if (partyVo != null) {
                bidRecord.setBidderName(partyVo.getName());
            }
            bidRecords.add(bidRecord);
        }

        resp.setList(bidRecords);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfo getAllByActivityId(int page, int perPage, BidCondition condition, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<Bid> list = bidDao.selectList(condition);
        return new PageInfo<>(list);
    }

    @Override
    public boolean saveBid(Bid bid) {
        return bidDao.insert(bid) == 1;
    }

    @Override
    public List<Bid> getBidListByActivityId(Integer activityId) {
        BidCondition condition = new BidCondition();
        condition.setActivityId(activityId);
        return bidDao.selectList(condition);
    }

    @Override
    public Bid getBidById(Integer bidId) {
        return bidDao.selectById(bidId);
    }

    @Override
    public List<Bid> getBidListByActivityIdAndPartyId(Integer activityId, Integer partyId) {
        BidCondition condition = new BidCondition();
        condition.setActivityId(activityId);
        condition.setPartyId(partyId);
        return bidDao.selectList(condition);
    }
}