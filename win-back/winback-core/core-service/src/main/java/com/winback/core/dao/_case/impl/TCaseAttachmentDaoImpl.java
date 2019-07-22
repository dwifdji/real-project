
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseAttachmentCondition;
import com.winback.core.dao._case.mapper.TCaseAttachmentMapper;
import com.winback.core.model._case.TCaseAttachment;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseAttachmentDao;

@Service
public class TCaseAttachmentDaoImpl extends AbstractDaoImpl<TCaseAttachment, TCaseAttachmentCondition, BaseMapper<TCaseAttachment,TCaseAttachmentCondition>> implements TCaseAttachmentDao{
	
	@Resource
	private TCaseAttachmentMapper tCaseAttachmentMapper;
	
	@Override
	protected BaseMapper<TCaseAttachment, TCaseAttachmentCondition> daoSupport() {
		return tCaseAttachmentMapper;
	}

	@Override
	protected TCaseAttachmentCondition blankCondition() {
		return new TCaseAttachmentCondition();
	}

}
