
package com.tzCloud.core.dao.caseMatching.impl;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.caseMatching.TLawyerInfoCondition;
import com.tzCloud.core.dao.caseMatching.mapper.TLawyerInfoMapper;
import com.tzCloud.core.model.caseMatching.TLawyerInfo;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.caseMatching.TLawyerInfoDao;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class TLawyerInfoDaoImpl extends AbstractDaoImpl<TLawyerInfo, TLawyerInfoCondition, BaseMapper<TLawyerInfo, TLawyerInfoCondition>> implements TLawyerInfoDao {

    @Resource
    private TLawyerInfoMapper tLawyerInfoMapper;

    @Override
    protected BaseMapper<TLawyerInfo, TLawyerInfoCondition> daoSupport() {
        return tLawyerInfoMapper;
    }

    @Override

    protected TLawyerInfoCondition blankCondition() {
        return new TLawyerInfoCondition();
    }

    @Override
    @Async("taskExecutor")
    public Future<TLawyerInfo> asyncSelectFirst(TLawyerInfoCondition tLawyerCondition) {
        List<TLawyerInfo> tLawyerInfoList = tLawyerInfoMapper.selectByCondition(tLawyerCondition);
        if (CollectionUtils.isNotEmpty(tLawyerInfoList)) {
            return new AsyncResult<>(tLawyerInfoList.get(0));
        } else {
            return new AsyncResult<>(new TLawyerInfo());
        }
    }

    @Override
    public List<TLawyerInfo> getLawyerByName(String name, String firmShort) {
        return tLawyerInfoMapper.getLawyerByName(name, firmShort);
    }

    @Override
    public TLawyerInfo findBy(String xm, String lsswsmc) {
        TLawyerInfoCondition condition = new TLawyerInfoCondition();
        condition.setXm(xm);
        condition.setLsswsmc(lsswsmc);
        List<TLawyerInfo> list = tLawyerInfoMapper.selectByCondition(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
