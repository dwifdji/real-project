
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.InstructionsContentEnum;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.InstructionsContentCondition;
import com._360pai.core.dao.assistant.mapper.InstructionsContentMapper;
import com._360pai.core.model.assistant.InstructionsContent;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.InstructionsContentDao;

import java.util.List;

@Service
public class InstructionsContentDaoImpl extends AbstractDaoImpl<InstructionsContent, InstructionsContentCondition, BaseMapper<InstructionsContent,InstructionsContentCondition>> implements InstructionsContentDao{
	
	@Resource
	private InstructionsContentMapper instructionsContentMapper;
	
	@Override
	protected BaseMapper<InstructionsContent, InstructionsContentCondition> daoSupport() {
		return instructionsContentMapper;
	}

	@Override
	protected InstructionsContentCondition blankCondition() {
		return new InstructionsContentCondition();
	}

    @Override
    public int deleteInstructionsContent(Integer paramsId) {
        return instructionsContentMapper.deleteInstructionsContent(paramsId);
    }

	@Override
	public InstructionsContent getAppletAgreement(String name) {
		InstructionsContentCondition condition = new InstructionsContentCondition();
		condition.setName(name);
		condition.setStatus(InstructionsContentEnum.Status.APPLET_AGREEMENT.getKey());
		List<InstructionsContent> list = instructionsContentMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
