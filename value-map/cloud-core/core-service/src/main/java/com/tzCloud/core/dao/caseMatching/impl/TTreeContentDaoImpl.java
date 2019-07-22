
package com.tzCloud.core.dao.caseMatching.impl;

import javax.annotation.Resource;

import com.tzCloud.core.facade.legalEngine.vo.Brief;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.caseMatching.TTreeContentCondition;
import com.tzCloud.core.dao.caseMatching.mapper.TTreeContentMapper;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.caseMatching.TTreeContentDao;

import java.util.List;

@Service
public class TTreeContentDaoImpl extends AbstractDaoImpl<TTreeContent, TTreeContentCondition, BaseMapper<TTreeContent,TTreeContentCondition>> implements TTreeContentDao{
	
	@Resource
	private TTreeContentMapper tTreeContentMapper;
	
	@Override
	protected BaseMapper<TTreeContent, TTreeContentCondition> daoSupport() {
		return tTreeContentMapper;
	}

	@Override
	protected TTreeContentCondition blankCondition() {
		return new TTreeContentCondition();
	}

	@Override
	public List<TTreeContent> findAllBrief() {
		return tTreeContentMapper.findAllBrief();
	}

	@Override
	public Brief findBriefLevelBy(Integer id) {
		return tTreeContentMapper.findBriefLevelById(id);
	}

    @Override
    public List<TTreeContent> LikeByKeyWord(String name) {
        return tTreeContentMapper.likeByKeyWord(name);
    }
}
