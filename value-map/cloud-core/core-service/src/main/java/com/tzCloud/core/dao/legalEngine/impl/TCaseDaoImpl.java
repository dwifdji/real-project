
package com.tzCloud.core.dao.legalEngine.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.sysconfig.dataSource.DataType;
import com.tzCloud.core.condition.legalEngine.TCaseCondition;
import com.tzCloud.core.dao.legalEngine.TCaseDao;
import com.tzCloud.core.dao.legalEngine.mapper.TCaseMapper;
import com.tzCloud.core.model.legalEngine.TCase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TCaseDaoImpl extends AbstractDaoImpl<TCase, TCaseCondition, BaseMapper<TCase, TCaseCondition>> implements TCaseDao {

    @Resource
    private TCaseMapper tCaseMapper;

    @Override
    protected BaseMapper<TCase, TCaseCondition> daoSupport() {
        return tCaseMapper;
    }

    @Override
    protected TCaseCondition blankCondition() {
        return new TCaseCondition();
    }

    @Override
    public int migrationFromCpwswItem(Map<String, Object> params) {
        return tCaseMapper.migrationFromCpwswItem(params);
    }

    @Override
    public PageInfo<TCase> findNeedToRepair(int page, int perPage, Map<String, Object> params) {
        PageHelper.startPage(page, perPage);
        List<TCase> list = tCaseMapper.findNeedToRepair(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<TCase> findBriefIdIsNull(int page, int perPage, Map<String, Object> params) {
        PageHelper.startPage(page, perPage);
        List<TCase> list = tCaseMapper.findBriefIdIsNull(params);
        return new PageInfo<>(list);
    }

    @Override
    public int batchUpdateJudgementTypeBriefId(List<TCase> list) {
        return tCaseMapper.batchUpdateJudgementTypeBriefId(list);
    }

    @Override
    public Integer getMaxId() {
        return tCaseMapper.getMaxId();
    }

    @Override
    public PageInfo<TCase> findByIdGreaterThan(int page, int perPage, Integer id) {
        PageHelper.startPage(page, perPage);
        List<TCase> list = tCaseMapper.findByIdGreaterThan(id);
        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String, String>> findBriefByCourtName(String name) {
        return tCaseMapper.findBriefByCourtName(name);
    }

    @Override
    public List<Map<String, Object>> selectJudgeDateByCourtName(String name) {
        return tCaseMapper.selectJudgeDateByCourtName(name);
    }

    @Override
    public List<TCase> selectTypeAndJudgementType(String name) {
        return tCaseMapper.selectTypeAndJudgementType(name);
    }

    @Override
    public TCase findBy(String docId) {
        TCaseCondition condition = new TCaseCondition();
        condition.setDocId(docId);
        List<TCase> list = tCaseMapper.selectByCondition(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int getTotalCaseCount() {
        return tCaseMapper.getTotalCaseCount();
    }
}
