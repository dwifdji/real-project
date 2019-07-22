package com.tzCloud.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.condition.caseMatching.TBriefCondition;
import com.tzCloud.core.dao.caseMatching.TBriefDao;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.BriefResp;
import com.tzCloud.core.model.caseMatching.TBrief;
import com.tzCloud.core.service.TBriefService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/7 16:31
 */
@Service
@Slf4j
public class TBriefServiceImpl implements TBriefService {

    @Resource
    private TBriefDao tBriefDao;

    @Override
    public PageInfoResp<BriefResp> getBriefList(CaseMatchingReq.BriefSearch req) {
        TBriefCondition tBriefCondition = new TBriefCondition();
        tBriefCondition.setName(req.getSearchStr());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TBrief>            tBriefs  = tBriefDao.selectList(tBriefCondition);
        PageInfo<TBrief>        pageInfo = new PageInfo<>(tBriefs);
        PageInfoResp<BriefResp> result   = new PageInfoResp<>();
        result.setList(convertList(pageInfo.getList()));
        result.setHasNextPage(pageInfo.isHasNextPage());
        result.setTotal(pageInfo.getTotal());
        return new PageInfoResp<>(pageInfo);
    }

    private List<BriefResp> convertList(List<TBrief> sourceList) {
        List<BriefResp> targetList = new ArrayList<>();
        for (TBrief source : sourceList) {
            targetList.add(convert(source));
        }
        return targetList;
    }

    private BriefResp convert(TBrief brief) {
        BriefResp briefResp = new BriefResp();
        BeanUtils.copyProperties(brief, briefResp);
        return briefResp;
    }

}
