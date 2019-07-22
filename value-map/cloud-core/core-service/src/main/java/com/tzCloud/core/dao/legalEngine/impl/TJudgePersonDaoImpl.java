
package com.tzCloud.core.dao.legalEngine.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.legalEngine.TJudgePersonCondition;
import com.tzCloud.core.dao.legalEngine.TJudgePersonDao;
import com.tzCloud.core.dao.legalEngine.mapper.TJudgePersonMapper;
import com.tzCloud.core.model.legalEngine.TJudgePerson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TJudgePersonDaoImpl extends AbstractDaoImpl<TJudgePerson, TJudgePersonCondition, BaseMapper<TJudgePerson,TJudgePersonCondition>> implements TJudgePersonDao {
	
	@Resource
	private TJudgePersonMapper tJudgePersonMapper;
	
	@Override
	protected BaseMapper<TJudgePerson, TJudgePersonCondition> daoSupport() {
		return tJudgePersonMapper;
	}

	@Override
	protected TJudgePersonCondition blankCondition() {
		return new TJudgePersonCondition();
	}

	@Override
	public TJudgePerson findBy(String docId) {
		TJudgePersonCondition condition = new TJudgePersonCondition();
		condition.setDocId(docId);
		List<TJudgePerson> list = tJudgePersonMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TJudgePerson> findBy(List<String> docIds) {
		return tJudgePersonMapper.findByIds(docIds);
	}
}
