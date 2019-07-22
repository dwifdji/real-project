
package com.tzCloud.core.dao.legalEngine.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.legalEngine.TCourtCondition;
import com.tzCloud.core.dao.legalEngine.TCourtDao;
import com.tzCloud.core.dao.legalEngine.mapper.TCourtMapper;
import com.tzCloud.core.facade.caseMatching.req.ViewCourtNumReq;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;
import com.tzCloud.core.model.legalEngine.TCourt;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TCourtDaoImpl extends AbstractDaoImpl<TCourt, TCourtCondition, BaseMapper<TCourt, TCourtCondition>> implements TCourtDao {

    @Resource
    private TCourtMapper tCourtMapper;

    @Override
    protected BaseMapper<TCourt, TCourtCondition> daoSupport() {
        return tCourtMapper;
    }

    @Override
    protected TCourtCondition blankCondition() {
        return new TCourtCondition();
    }



    @Override
    public TCourt findBy(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        TCourtCondition condition = new TCourtCondition();
        condition.setName(name);
        List<TCourt> list = tCourtMapper.selectByCondition(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TCourt> QueryList() {
        return tCourtMapper.queryList();
    }

    @Override
    public List<Map<String, String>> findDiffCourt() {
        return tCourtMapper.findDiffCourt();
    }
}
