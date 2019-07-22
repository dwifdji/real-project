
package com.tzCloud.core.dao.view.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.view.ViewCourtJudgePersonCondition;
import com.tzCloud.core.dao.view.ViewCourtJudgePersonDao;
import com.tzCloud.core.dao.view.mapper.ViewCourtJudgePersonMapper;
import com.tzCloud.core.model.view.ViewCourtJudgePerson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ViewCourtJudgePersonDaoImpl extends AbstractDaoImpl<ViewCourtJudgePerson, ViewCourtJudgePersonCondition, BaseMapper<ViewCourtJudgePerson, ViewCourtJudgePersonCondition>> implements ViewCourtJudgePersonDao {

    @Resource
    private ViewCourtJudgePersonMapper viewCourtJudgePersonMapper;

    @Override
    protected BaseMapper<ViewCourtJudgePerson, ViewCourtJudgePersonCondition> daoSupport() {
        return viewCourtJudgePersonMapper;
    }

    @Override
    protected ViewCourtJudgePersonCondition blankCondition() {
        return new ViewCourtJudgePersonCondition();
    }

    @Override
    public List<Map<String, Object>> selectJudgePersonByCourtName(String name) {
        return viewCourtJudgePersonMapper.selectJudgePersonByCourtName(name);
    }
}
